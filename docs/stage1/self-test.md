# 第一阶段验收执行规程

命令在仓库根目录执行；记录源码 SHA、日期、环境、数据来源、实际结果和证据。写入性验收使用独立合成环境，先核对目标实例与数据库。需求编号见[覆盖表](requirement-coverage.md)。

## 1. 固定版本和环境

```bash
git status --short
git rev-parse HEAD
java -version
mvn -version
node --version
python --version
docker compose version
```

开发检查要求 JDK 21、Maven、Node 22.12+、Python 3.11+；容器部署要求 Compose 2.20+。后端 H2 测试、MySQL 验收和云服务器分别记录。

## 2. 分层测试、集成测试与覆盖率

```bash
mvn -f backend-procedural/pom.xml clean verify
python -m unittest discover -s tests/unit -p 'test_*.py'
node --test tests/frontend/*.test.cjs
python -m pip install -r scripts/requirements-validation.txt
python scripts/check_openapi.py
python scripts/check_repository.py
npm --prefix frontend ci
npm --prefix frontend run build
git diff --check
```

现场说明 Controller、Service、Mapper 等层的测试隔离对象，指出正常、参数错误、未登录、越权、越界、版本冲突、保护与回滚断言。`Stage1ApiTest` 使用 H2 合成数据库验证跨层 API，不替代真实 MySQL。

核对 `backend-procedural/target/surefire-reports/` 的用例数、失败与跳过情况。打开 `backend-procedural/target/site/jacoco/index.html`，记录行、分支覆盖率及重要未覆盖代码；覆盖率须对应刚执行的版本，不能引用旧报告或以百分比代替业务断言。

## 3. 运行实例与指定 API

部署按[部署说明](../../deploy/README.md)执行。[指定 Swagger UI](http://124.70.42.35:8080/swagger-ui/index.html?url=/api-design/openapi.yaml)应加载 `/api-design/openapi.yaml`；健康检查为 `/api/v1/health`。

使用部署负责人提供的合成账号在同一 Cookie 会话中现场调用：

| 顺序 | 操作 | 必须核对 |
| --- | --- | --- |
| 1 | 未登录请求 `/api/v1/me`；错误密码登录 | 401；失败信息不暴露账号是否存在 |
| 2 | 登录，查询本人及有效权限 | 成功建立 JSESSIONID；字段不含密码或哈希 |
| 3 | 首次登录账号访问业务，再改密 | 改密前 403；改密后可用；旧密码失效 |
| 4 | 普通员工查询台账并篡改目标 ID | SELF 仅本人；范围外写入 403，数据不变且不泄露字段 |
| 5 | 部门管理员查看用户/部门子树 | DEPT 含全部下级，筛选或直接 ID 不能突破范围 |
| 6 | 禁用用户、撤角色、停用角色或改授权 | 下一次请求生效；旧会话/权限不缓存放行 |
| 7 | 两管理员用同一旧 version 替换授权 | 恰一成功，另一 409；无部分写入或版本误增 |
| 8 | 未知/重复权限、缺 read、SELF 管理范围 | 参数或授权拒绝，集合和版本不变 |
| 9 | 停用/删除自己或admin、修改自己/admin角色；删除内置角色、停用ADMIN或改其授权 | 仅这些保护操作拒绝；普通资料编辑按自身权限和范围判断 |
| 10 | 退出后复用旧 Cookie | 401 |

Swagger 可打开不等于上述请求已执行。`check_openapi.py --base-url URL`仅比较方法/路径；字段、状态、权限与范围须结合请求和测试核对。

## 4. 真实 HTTP 与持久化

独立合成环境执行：

```bash
python scripts/smoke_http.py --base-url http://127.0.0.1 --env-file deploy/.env --check-web
python scripts/smoke_http.py --base-url http://127.0.0.1 --env-file deploy/.env --mode persistence-create
docker compose --env-file deploy/.env -f deploy/compose.yaml down
docker compose --env-file deploy/.env -f deploy/compose.yaml up -d --wait --wait-timeout 300
python scripts/smoke_http.py --base-url http://127.0.0.1 --env-file deploy/.env --mode persistence-verify
python scripts/smoke_http.py --base-url http://127.0.0.1 --env-file deploy/.env --mode persistence-cleanup
```

`down`保留命名卷，不加 `--volumes`。保留 create 生成的同一标记文件；重启后重新登录，核对同一合成账号仍存在，再精确清理。冒烟创建临时账号/角色并尝试清理，故障中断后检查残留。

另核对仅重建 backend 后 Nginx 仍能代理、容器健康、实际部署 SHA、数据库迁移版本、公开端口及备份恢复记录。当前后端内存会话重启失效是预期，数据库业务数据应保留。

## 5. 数据与性能

正式输入目标为 111 组织、18 系统、115 模块、359 功能点、2154 权限、10000 员工加 admin；清单五角色授权 3549，平台另 6 条。检查工号/非空手机号唯一、组织单根无环、负责人 ID、权限稳定码、经理 read=ALL/edit=DEPT 与员工 SELF。

`import_excel.py`只生成离线 SQL。员工失败行可跳过，生成成功不代表全量成功；逐项审查失败、未关联负责人及警告。生成 SQL 使用 `TRUNCATE` 全量重建，MySQL DDL 不能事务回滚；先备份并确认目标库。`--update-codes`可能在后续失败前已写编号表，需审查差异。

同输入重复导入比较业务字段与关系，排除随机盐哈希和时间；核对 SQL 指纹仅作快速回归，关键差异按主键逐字段核验。全量 MySQL 记录 `EXPLAIN ANALYZE` 鉴权查询目标 ≤10 ms、常用列表/保存目标 ≤1 s，包含机器、数据量、参数、冷热状态与统计。未执行原始全量或测量时记“待执行”。

## 6. 签收记录

助教准入需原型运行、服务器后端、指定 API 现场调用、分层测试现场执行及覆盖率报告齐备。教师检查再按[结构化设计](../design/structured-design.md)逐项关联需求、数据流、模块、代码、API 和测试，不重复原型展示。

将实际结果写入[验收记录](verification-record.md)，以“通过 / 失败 / 待执行 / 不在一期”表示；证据不记录密码、Cookie、哈希或真实人员信息。
