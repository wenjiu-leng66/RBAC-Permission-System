# 市政公司统一权限管理系统

基于 RBAC 的统一权限管理平台，提供用户、组织、角色、授权、门户与员工台账管理。当前交付为第一阶段 RBAC0 面向功能后端、完整界面原型及统一 API 设计。

## 运行与接口

服务器 API：[Swagger UI](http://124.70.42.35:8080/swagger-ui/index.html?url=/api-design/openapi.yaml)。[界面原型](http://124.70.42.35:8080/)、[OpenAPI 文件](http://124.70.42.35:8080/api-design/openapi.yaml)、[健康检查](http://124.70.42.35:8080/api/v1/health)。

2026-10-27 已核对服务器健康、静态契约、运行接口文档及首页的只读访问；登录业务调用、部署源码版本和重启持久化的核验状态见[验收记录](docs/stage1/verification-record.md)。界面原型使用合成内存数据，真实后端使用 Cookie 会话和数据库，两者分别验收。

## 技术结构

| 目录 | 职责 |
| --- | --- |
| `frontend/` | Vue 3、Vue Router、Element Plus 界面原型及共享权限策略 |
| `backend-procedural/` | JDK 21、Spring Boot 3.3.5、Spring JDBC 后端，Controller → Service → Mapper 分层 |
| `api/` | OpenAPI 0.3.0 接口契约 |
| `database/` | MySQL 8.4 迁移、离线导入和数据核对 |
| `deploy/` | Nginx、Java、MySQL 的容器部署配置 |
| `tests/`、`scripts/` | 自动测试、契约检查、HTTP 验收及部署脚本 |
| `docs/` | 需求、设计、数据字典、分工与验收材料 |

后端每次请求校验身份、权限及目标数据范围。同一权限的多角色范围按 `ALL > DEPT > SELF` 合并；授权整组替换使用数据库事务与版本校验，旧版本返回 `409`。接口前缀为 `/api/v1`，会话 Cookie 为 `JSESSIONID`。

## 部署

环境要求：Docker Compose 2.20+。在仓库根目录执行：

```bash
cp deploy/.env.example deploy/.env
# 配置数据库密码、初始密码和数据模式后启动
sh scripts/deploy-stage1.sh
```

首次空数据卷加载 V001 表结构；已有数据卷的升级使用版本迁移。原始资料导入会全量重建数据，执行前须备份并核对目标数据库。完整配置及恢复步骤见[部署说明](deploy/README.md)。独立开发运行见[后端说明](backend-procedural/README.md)和[前端说明](frontend/README.md)。

## 项目材料

| 材料 | 内容 |
| --- | --- |
| [需求规格](docs/requirements/req-spec.md) | 功能范围、业务规则及非功能要求 |
| [结构化设计](docs/design/structured-design.md) | 需求到模块、数据流、调用层次及测试的映射 |
| [后端详细设计](docs/design/detailed-design.md) | 认证、范围、保护规则、事务及并发 |
| [API 约定](docs/standards/api-contract.md)、[OpenAPI](api/openapi.yaml) | 方法、字段、错误码与权限映射 |
| [数据字典](docs/database/data-dictionary.md)、[导入规则](docs/database/import-rules.md) | 数据结构、清洗与质量核对 |
| [小组分工](docs/planning/team.md) | 六位成员的责任及产物 |
| [本次测试报告](docs/testing/test-report-2026-10-27.md) | 82 项后端、58 项前端、41 项导入测试与覆盖率 |
| [第一阶段验收](docs/stage1/README.md) | 助教准入、教师检查、41 条需求追溯、测试与证据 |

## 交付边界

第一阶段允许界面原型与真实后端分别检查。角色继承、约束、角色激活切换、审计、面向对象后端及函数式模块属于后续阶段；当前对应页面为静态原型。

全量数据基准为 111 个组织、18 个系统、115 个模块、359 个功能点、2154 个目录权限、10000 名员工加 `admin`；原清单五角色授权 3549 条，平台部门管理员另 6 条。合成数据测试不替代原始资料全量验收。原始 Excel、真实人员资料、密码、哈希、令牌、环境文件及数据库备份不提交。
