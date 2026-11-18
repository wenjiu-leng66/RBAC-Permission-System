# 阶段二（RBAC1 + 课程系统接入）AI 分步执行计划

> 依据：
> - 课程主要要求 PDF（V4，2026-11-08）：RBAC、JavaEE、Spring Data JPA、CloneFactory、logback、Docker Swarm、API、DDD、GRASP、Jacoco、Gitee/adoc、APIFOX；
> - 2026-11-10《软件设计课：AI skill 与 DDD 设计》总结：第二次检查的助教检查、老师检查、5 个权限 API、课程系统接入、两套设计比较；
> - 2026-11-17 课程录音：课程系统前后端接入方式、前端菜单/按钮权限、后端逐 API 鉴权、Nginx/Swarm 部署、logback 三层日志；
> - 老师示范项目的**流程和方法**（只借方法，不搬课程管理业务内容）；
> - 本组第一轮检查问题、当前代码与 `docs/reviews/2026-11-12-第二轮检查整改意见-方法论版.md`。
>
> 旧计划（`milestones.md`、`stage1-task-breakdown.md`）只作参考；本计划以课程最新要求为准。
>
> 日期：2026-11-17。目标窗口：**2026-11-24—11-26 第二次检查**。

---

## 0. 一页结论

阶段二不是“把阶段一功能补全”，而是同时完成四条主线：

1. **RBAC1 + 菜单/按钮模型**：角色继承、应用归属、菜单/按钮树、权限—API 映射、部门管理员。
2. **两套设计对比**：同一 API/数据库/技术栈，`structure` 与 `ddd` 两套 Service；顺序图只画差异；能按 GRASP 解释。
3. **课程系统接入**：用我们的权限系统控制课程系统的前端菜单/按钮和后端 API，实现 5 个权限 API。
4. **可部署、可验证**：Docker Swarm 部署前端/后端/数据库；logback 证明 API→Service→Infrastructure 三层调用；黑盒测试覆盖等价类/边界/异常。

**最重要的执行原则：**

> **不要让 AI 一次做完一个模块，更不要一次做完阶段二。**
> 每次只给 AI 一个“切片”（一个用例/一个 API/一张表/一个测试集），先让它输出差异清单，人工确认文件白名单；先写失败测试，确认 RED；再最小实现；跑绿；逐行 review；更新图和证据；合并。
>
> 一次投喂整份需求、整个仓库或“把阶段二做完”，AI 一定会混两套设计、漏边界、改错模块。

**时间非常紧：** 11/17—11/24 只有 7 个有效工作日。必须按第 6 节日历并行推进，先保主链，再补对比和文档。

### 0.1 一眼分工

| 成员 | 阶段二主责 | 最关键产出 |
| --- | --- | --- |
| 郑凯 | V002 表、回填/导入、角色继承表、Swarm MySQL | 一套权限模型 + 可迁移数据 + 持久化 |
| 陈清香 | 课程前端接入、本组前端真实化、Nginx 镜像 | 课程菜单/按钮受控 + 前端真实登录 |
| 杨昕桐 | 菜单/按钮权限码、角色授权/继承页 | 权限树 + 授权页真实数据 |
| 王信凯 | JPA、5 个权限 API、API 拦截器、部门管理员、logback、Swarm 后端 | 后端主链 + 可部署 + 三层日志 |
| 胡俊朗 | DDD 领域模型/Adapter、两设计契约、包图/类图/差异顺序图、GRASP | 两套设计 + 可讲解的图 |
| 孙逸轩 | 范围基线、OpenAPI 0.4.0、课程接入契约、黑盒测试、验收证据 | 契约 + 测试 + 现场证据 |

---

## 1. 课程要求拆解

### 1.1 助教检查：部署与运行

| 要求 | 本组对应产物 | 主责 | 验收证据 |
| --- | --- | --- | --- |
| 前端、后端、数据库全部部署在 Docker Swarm | `deploy/stack.yaml`、前端 Nginx 镜像、后端镜像、MySQL 服务、overlay 网络、持久卷 | 王信凯 + 郑凯 | `docker stack deploy` 后 `docker service ls`、`docker service ps`、浏览器访问 |
| 前端能正常运行 | 生产构建 `dist/` 放入 Nginx 镜像；接口指向 Swarm 服务名 | 陈清香 | 浏览器登录、菜单、页面操作 |
| logback 证明 API→Service→Infrastructure 三层调用 | `logback-spring.xml` + AOP 切面；同一 `requestId` 贯穿三层 | 王信凯 | `docker service logs` 中一次操作的 3 条日志，带同一 requestId |
| 本轮不允许只在本地跑接口测试 | 真实 Swarm + 真实 MySQL + 浏览器 E2E | 孙逸轩 | 部署记录、截图、日志、数据库快照 |

> 日志分层口径：老师有时说 api/controller/infrastructure，有时说 api/service/infrastructure。本组按 **API(Controller) → Service → Infrastructure** 输出；如果检查要求把 Controller 单列，可从同一 `requestId` 的日志中筛出 Controller 调用，不另做一套日志。

### 1.2 老师检查：需求、界面、提问

| 要求 | 本组对应产物 | 主责 | 验收证据 |
| --- | --- | --- | --- |
| 用 AI 生成的需求文档对照 AI 生成的测试代码，抽查等价类/边界/异常 | `docs/testing/stage2-blackbox.md`；每条需求有正常/边界/异常用例 | 孙逸轩 | 用例表、测试输出、未测项 |
| 前端界面抽查，菜单功能是否完成需求、前后端是否真正连通 | 真实 HTTP 客户端；关闭 mock；菜单/按钮由权限树控制 | 陈清香/杨昕桐 | 浏览器网络面板、403 场景 |
| 包图和类图全部生成 | 结构化、DDD 两套包图；按模块的类图；Service 以下，Controller 以上不重复画 | 胡俊朗 | PNG+SVG，目视检查 |
| 顺序图只画结构化与 DDD 不同的部分 | 登录、授角、角色授权、API 判定等差异用例；相同部分不画 | 胡俊朗 | 差异顺序图；两张一样算错 |
| 能解释每处不同对应哪个 GRASP 原则 | `docs/design/GRASP-说明.md`；创建者、信息专家、多态、间接、虚构 | 胡俊朗/全员 | 现场讲解 |
| 技术提问 | 只用课程 Java 技术；Spring Data JPA、事务、CloneFactory、logback；不引入 JDBC/MyBatis 作为主实现 | 王信凯/胡俊朗 | 代码、POM、现场回答 |
| 不用 JDBC/MyBatis | 验收入口不出现 `JdbcTemplate` 业务拼 SQL；只允许健康检查/初始化 | 王信凯 | `grep`、代码评审 |
| 使用课程所讲技术 | JavaEE/Spring Boot、Spring MVC、Spring Data JPA、事务、logback、Docker Swarm | 全员 | 代码与部署 |

> 录音/摘要里的 “Cloud Foundry” 按上下文和 skill 正文应是 **CloneFactory**（对象字段映射），不是云平台部署要求。不要新增 Cloud Foundry 平台任务；按 `projectmgt-code-conventions` 的实际规则使用 CloneFactory，或明确说明本组不使用、改用手写映射。

### 1.3 5 个权限 API 与课程系统接入

| 能力 | 本组接口草案（最终以课程下发契约为准） | 调用方 | 主责 |
| --- | --- | --- | --- |
| 登录 | `POST /api/v1/auth/login`：账号、密码、`appCode`；返回会话、用户、当前应用角色/权限 | 课程系统前端 | 王信凯 |
| 退出 | `POST /api/v1/auth/logout`：会话失效 | 课程系统前端 | 王信凯 |
| 当前用户权限和角色 | `GET /api/v1/me/permissions?appCode=`：菜单/按钮权限码、数据范围、角色 | 课程系统前端 | 王信凯 |
| 前端权限校验 | `POST /api/v1/authz/check-frontend-permission`：`appCode + permissionCode`；返回 allowed + 原因 | 课程系统前端 | 王信凯 |
| API 调用校验 | `POST /api/v1/authz/check-api-permission`：`appCode + method + path/operationId`；返回 allowed + 原因 | 课程系统后端 | 王信凯 |

**课程系统接入边界：**

- 课程管理系统是课程提供的**被控系统**；我们只做权限控制和接入适配，不改它的业务模型、页面业务逻辑和数据库。
- 课程系统前端：登录后拿当前用户权限码；菜单/按钮按权限码显示或置灰；每个页面动作对应我们登记的 API。
- 课程系统后端：每个受保护 API 调用前向我们的“API 判定”接口询问；失败拒绝。
- 认证方式以课程下发契约为准；未下发前用 stub + 本组会话/服务令牌，不假设跨域共享 Cookie，不用长期静态权限 Token 代替实时撤权。
- 课程系统代码到位前先用本地 stub 验证契约；到位后替换为真实系统复验，stub 结果与真实结果分别记录。

### 1.4 文档、仓库与技术约束

- Gitee 仓库可访问；设计文档用 adoc（或同时保留 Markdown 源）。
- OpenAPI/APIFOX 契约同步；每个接口有 `operationId`、错误码、权限要求。
- Jacoco 报告保留；阶段二以黑盒为主，覆盖率不作主指标。
- RBAC1：角色继承（`sys_role_inherit`、多父 DAG、无环、继承闭包、有效权限来源）。
- 两套设计：`structure` 与 `ddd`，同 API、同数据库、同 Controller/DTO/VO，使用 profile 切换。
- 日志：logback + AOP，API/Service/Infrastructure 三层，同一 `requestId`，脱敏。
- 部署：Docker Swarm，5—6 台服务器按课程资源安排；前端 Nginx、后端 Spring Boot、MySQL、课程系统前后端。

---

## 2. 本组现状与差距

### 2.1 已完成（保留）

- BE-03：`X-Request-Id` + MDC + `LayerLoggingAspect` 三层日志；错误体 requestId 统一；CORS 暴露头。
- 登录标识歧义：多候选拒绝登录；创建/编辑检查工号与手机号跨列撞值。
- BE-02 前置：33 个 Controller 方法补齐 `operationId`，与 OpenAPI 0.3.0 一致；`ControllerOperationIdTest` 防漏标/重名。
- 后端 102 项测试通过（29 集成 + 73 隔离单元）。

### 2.2 阶段二必须补齐的差距

| 差距 | 现状 | 阶段二目标 |
| --- | --- | --- |
| 四套权限模型 | 设计/ER 用 `app_resource`；OpenAPI 0.3.0 用功能点×动作；V001 用 `app_system/app_module/app_function/sys_action`；前端用 mock functions | 统一为应用→菜单/按钮→权限；V002 迁移；删除旧逻辑实体 |
| 应用上下文 | 登录无 `appCode`，返回全部权限 | 登录带 `appCode`，只返回当前应用权限 |
| 权限—API 映射 | 没有 `sys_api/sys_permission_api`；Service 手写权限码 | DB 登记 + `operationId` 拦截器 + 默认拒绝 |
| 部门管理员 | 用户表没有 `admin_type/admin_dept_id` | 字段、范围子树、任命接口、管理类角色限制 |
| 持久化 | `JdbcTemplate` Mapper | Spring Data JPA；事务边界；两个设计共享 |
| 两套设计 | 只有 procedural/JDBC | `structure` + `ddd`，profile 切换，同一契约测试 |
| RBAC1 | 无角色继承 | `sys_role_inherit`、闭包、继承 API/页面/测试 |
| 课程系统接入 | 无 | 5 个权限 API + 前端菜单/按钮 + 后端 API 判定 |
| 前端 | 只有 mock | 真实 HTTP；appCode；权限树；403/409 |
| 部署 | Compose 单机 | Swarm stack + 镜像 + 持久化 + 日志 |
| 测试 | 旧一期测试 | 新菜单/API/课程接入/两设计/RBAC1 黑盒用例 |
| 图 | 设计类图；实现图旧 | 两套包图/类图；差异顺序图；GRASP 说明 |

---

## 3. 阶段二目标架构

```text
课程系统前端 ──登录/当前权限/前端判定──┐
课程系统后端 ──API 判定───────────────┤
                                      ▼
                         权限系统 API（共享 Controller/DTO/VO）
                                      │
                     ┌────────────────┴────────────────┐
                     ▼                                 ▼
              structure Service                  ddd Service
           （事务脚本编排）                  （应用服务 + 领域对象）
                     │                                 │
                     └──────────────┬──────────────────┘
                                    ▼
                        共用 UseCase / Command / Result
                                    ▼
                  Spring Data JPA / Repository / Adapter
                                    ▼
                                 MySQL
```

- 同一工程：`api`（Controller/DTO/VO/HTTP 错误）、`service/port`、`service/structure`、`service/ddd`、`infrastructure/persistence`。
- `structure` 与 `ddd` 共用 Controller、DTO/VO、UseCase 签名、表结构、底层技术；只切换 Service 实现。
- `ddd` 的 Repository Adapter 组合多表返回领域对象；领域对象承载规则；应用服务只编排。
- 课程系统作为 `app_application` 中的一个应用；它的菜单/按钮/API 登记在权限目录中。

### 3.1 目标仓库结构

```text
backend-procedural/src/main/java/com/municipal/rbac/procedural/
  api/
    controller/          # 共享 Controller，带 operationId
    dto/                 # 按用例拆分的请求 DTO
    vo/                  # 按场景拆分的响应 VO
    error/               # 统一错误包络与业务异常
  service/
    port/                # 共享 UseCase / Command / Result / 权限端口
    structure/           # 结构化事务脚本实现
    ddd/
      application/       # DDD 用例编排
      domain/            # 纯 Java 领域对象、仓储端口
  infrastructure/
    persistence/         # JPA Entity、Repository、Adapter/Assembler
    security/            # 会话/权限 provider（如需要）
    logging/             # 三层日志与 requestId（已有 config 实现可迁移）
  config/                # 拦截器、CORS、启动校验

frontend/src/
  api/client.js          # 真实 HTTP 客户端（替代 mock）
  stores/auth.js         # 会话 + currentApp + 权限树
  router/index.js        # 路由守卫
  navigation.js          # 菜单/按钮权限
  views/                 # 页面

deploy/
  stack.yaml             # Docker Swarm stack
  backend.Dockerfile
  web.Dockerfile
  nginx.conf
  .env.example

docs/
  design/*.adoc          # 课程要求的 adoc
  design/figures/        # 包图/类图/差异顺序图 PNG+SVG
  api-map.md             # 页面→权限码→operationId→测试
  testing/stage2-blackbox.md
  planning/stage2-ai-execution-plan-2026-11-17.md
```

---

## 4. AI 分步执行法（核心）

### 4.1 铁律

1. **一次只做一个切片。** 一个用例、一个 API、一张表、一组测试；禁止“帮我把阶段二做完”。
2. **结构化输入。** 每次给 AI：需求编号、界面原型/页面、OpenAPI 的 `operationId`、相关表、领域规则、测试用例；不给整份需求文档。
3. **先测试后实现。** 先写会失败的测试，确认 RED，再写最小实现；禁止先写实现再补测试。
4. **文件白名单。** 在 prompt 中列出“只允许修改这些文件”；AI 越界就回退。
5. **先计划后代码。** 第一轮只让 AI 输出“文件清单 + 失败测试清单 + 风险点”，人工确认后再写代码。
6. **逐行 review。** 重点看权限码、事务、DTO/VO、JPA、异常码、日志脱敏、是否混入另一套设计。
7. **不引入未经确认的技术。** 不引入 JDBC/MyBatis 作为主实现；Redis/消息队列/MongoDB/函数式编程未经确认不要加，除非全组能讲清。
8. **不虚构接口/字段。** 以 OpenAPI/课程下发契约为准；缺契约先 stub 并标 `待确认`。
9. **不一次改两套设计。** 先做 `structure` 完整闭环，再让 DDD 实现同一契约；不要让 AI 同时生成两套。
10. **每个切片结束更新证据。** 测试结果、日志、图、迭代记录、未测项。

### 4.2 每个切片的 9 步

| 步骤 | 做什么 | AI 输出 | 人工门禁 |
| --- | --- | --- | --- |
| 1 | 生成差异清单/实施计划 | 涉及文件、失败测试清单、风险点、依赖 | 确认范围，不写代码 |
| 2 | 确认文件白名单 | 文件列表 | 只允许这些文件 |
| 3 | 写失败测试 | 测试代码 + 预期 RED 原因 | 确认测试能因行为缺失失败 |
| 4 | 运行 RED | 测试输出 | 不是语法错误/环境错误 |
| 5 | 最小实现 | 代码 diff | 不改无关模块，不引入 JDBC/MyBatis |
| 6 | 运行 GREEN | 测试输出 | 新测试通过，旧测试不回归 |
| 7 | 逐行 review | 评审意见 | 权限、事务、DTO、日志、命名、异常码 |
| 8 | 更新文档/图/标签 | 文档 diff、图、标签 | 图与代码一致 |
| 9 | 合并 | PR/commit | 证据齐全 |

### 4.3 通用 AI 指令模板

**模板 A：只出计划，不写代码**

```text
你只做计划，不写代码。
输入：需求编号 {REQ}；页面/原型 {PAGE}；OpenAPI operationId {OPS}；相关表 {TABLES}；领域规则 {RULES}；现有代码文件 {FILES}。
任务：输出 1) 需要修改/新增的文件清单 2) 失败测试清单（测试名带需求编号）3) 风险点 4) 依赖的其他切片。
约束：不要改代码；不要发明接口/字段；不要引入 JDBC/MyBatis；不要同时做 structure 和 ddd。
```

**模板 B：只写失败测试**

```text
只写测试，不写实现。
范围：切片 {SLICE}；文件白名单 {FILES}。
要求：每个测试名带需求编号；覆盖正常、边界、异常；断言用字面量，不镜像实现；先说明每个测试防的是哪种破坏。
输出：测试文件 diff；不要改生产代码。
```

**模板 C：最小实现**

```text
只实现让以下失败测试通过的最小代码。
范围：切片 {SLICE}；文件白名单 {FILES}；测试清单 {TESTS}。
约束：不改其他模块；不引入新依赖；使用 Spring Data JPA；Service 事务；DTO/VO 分离；中文 Javadoc；不写 JDBC/MyBatis；不混入另一套设计。
输出：代码 diff + 运行 `mvn test` 的结果。
```

**模板 D：逐行 review**

```text
只 review，不改代码。
检查：权限码是否来自契约；事务边界；DTO/VO 是否泄漏 Entity；JPA 是否 N+1/内存分页；异常码是否稳定；日志是否脱敏；是否混入另一套设计；是否破坏已有 CAS/保护规则。
输出：按严重程度列问题，附文件:行号；没有问题的项也说明。
```

### 4.4 切片清单

| 切片 | 内容 | 输入 | 产出 | 依赖 | 主责 |
| --- | --- | --- | --- | --- | --- |
| S2-01 | 阶段二范围/课程系统清单 | 课程要求、课程系统代码/契约、现有需求 | 范围基线、课程菜单/按钮/API 清单、未决项 | 无 | 孙逸轩 |
| S2-02 | 5 个权限 API 契约 + page→API 映射 | S2-01、界面原型 | OpenAPI 0.4.0、`docs/api-map.md`、契约测试清单 | S2-01 | 孙逸轩 |
| S2-03 | V002 数据模型 | S2-02、设计 ER | `app_application/app_resource/sys_permission/sys_api/sys_permission_api`、`admin_type/admin_dept_id`、`sys_role_inherit` | S2-02 | 郑凯 |
| S2-04 | 数据回填/导入 | S2-03、旧 V001、导入脚本 | 回填 SQL、导入脚本、数据字典、数量核对 | S2-03 | 郑凯 |
| S2-05 | JPA 基础设施 | S2-03、现有 Mapper | Entity/Repository/Adapter、共享 UseCase/Command/Result 骨架 | S2-03 | 王信凯 |
| S2-06 | 登录 + appCode + 当前权限（structure） | S2-02、S2-05 | 登录/退出/当前权限/前端判定/API 判定 5 个 API（structure） | S2-05 | 王信凯 |
| S2-07 | 菜单树 + 权限—API 拦截器 | S2-02、S2-03、S2-05 | 菜单/按钮树、`operationId` 拦截器、未映射默认拒绝、启动校验 | S2-05 | 王信凯 |
| S2-08 | 部门管理员 | S2-03、S2-05 | `admin_type/admin_dept_id`、任命接口、范围子树、管理类角色限制 | S2-03 | 王信凯/郑凯 |
| S2-09 | RBAC1 角色继承 | S2-03、S2-05 | `sys_role_inherit`、闭包、继承 API、有效权限来源、测试 | S2-03 | 郑凯/胡俊朗 |
| S2-10 | 课程系统前端接入（stub→真实） | S2-01、S2-02、课程前端代码 | 登录/菜单/按钮/403；真实 HTTP；stub 记录 | S2-02、S2-06 | 陈清香/杨昕桐 |
| S2-11 | 课程系统后端 API 判定接入 | S2-01、S2-02、课程后端代码 | 每个受保护 API 调判定；失败拒绝；stub→真实 | S2-02、S2-06 | 王信凯/孙逸轩 |
| S2-12 | DDD 领域模型/Repository Adapter | S2-05、S2-06 契约 | 领域对象、仓储端口、Adapter、领域单测 | S2-05 | 胡俊朗 |
| S2-13 | DDD Service + 两设计契约测试 | S2-12、S2-06 | DDD 用例服务；同一契约测试跑两套 | S2-12 | 胡俊朗 |
| S2-14 | 两设计切换 + 差异说明 | S2-13 | profile 切换；差异清单；GRASP 说明 | S2-13 | 胡俊朗/王信凯 |
| S2-15 | 前端真实化 | S2-06、S2-10 | 关 mock；appCode；权限树；按钮/路由；401/403/409 | S2-06 | 陈清香/杨昕桐 |
| S2-16 | logback + AOP 三层日志 | S2-06、部署 | `logback-spring.xml`；requestId；三层日志；脱敏 | S2-05 | 王信凯 |
| S2-17 | Swarm 部署 | S2-06、S2-10、S2-11 | 镜像、stack、overlay、MySQL 卷、健康检查、日志证据 | S2-16 | 王信凯/郑凯 |
| S2-18 | 黑盒测试集 | S2-02、S2-06、S2-10、S2-11 | 等价类/边界/异常；5 API；课程接入；RBAC1 | S2-06、S2-11 | 孙逸轩 |
| S2-19 | 包图/类图/差异顺序图/GRASP | S2-06、S2-13 | 两套包图、类图、差异顺序图、GRASP 文档 | S2-13 | 胡俊朗 |
| S2-20 | 证据/ rehearsal/检查清单 | 全部 | 验收记录、截图、日志、未测项、演示脚本 | 全部 | 孙逸轩 |

---

### 4.5 两个 skill 的使用方式

老师 11/10 课程明确：**画图和写代码都可以用 skill；月底交图不用 skill 导致图有问题要扣分。** 本项目按以下方式使用：

**画图 skill（`uml-class-sequence-diagrams`）：**

- 只画 Service 以下，以 Service 为中心；Controller 以上两套设计相同，不重复画。
- 包图/类图全部生成；顺序图只生成 structure 与 ddd 不同的部分；两张一样要扣分。
- 生成 SVG + PNG，打开 PNG 目视检查；类框不重叠、线不穿框、执行条/异常路径正确。
- 每处差异对应 GRASP 原则；图必须表达实际代码，不能把未实现设计标成现有实现。

**写代码 skill（`projectmgt-code-conventions` 的通用部分）：**

- 技术约定：Spring Data JPA，不用 JDBC/MyBatis 作为主实现；Service 事务；DTO/VO 分离；Bean Validation；中文 Javadoc；显式类型；Flyway 只增不改。
- 组内共享约定：把本组的 API 契约、表结构、权限码规则、测试命名写进一份组内 skill/说明，每次让 AI 先读；不要让每个人各写一套。
- 不照搬示例项目专属内容：CloneFactory/Lombok 可按需使用，但不要照抄示例项目 ID、包名、私人路径、AOP 实现和课程业务。
- AI 幻觉与长期记忆：AI 可能把两套设计混在一起、忘记早期约定；必须逐行 review，重要约定写进 skill，需求按迭代顺序讲。

**使用频率建议：**

- 每次让 AI 写代码前，先贴“组内约定 + 本切片输入”；不要让 AI 自己决定分层、表名、权限码。
- 生成后先跑测试，再人工 review；测试失败或 diff 越界就停止，不要继续叠加功能。

---

## 5. 全组分工

### 5.1 郑凯（数据库/导入/ER）

**阶段二任务：**

- S2-03：V002 表结构（应用、菜单/按钮、权限、API、映射、管理员字段、角色继承）。
- S2-04：旧数据回填、导入脚本、数据字典、数量核对。
- S2-09：`sys_role_inherit` 表、继承闭包查询、数据约束。
- S2-17：MySQL Swarm 服务、持久卷、备份/恢复。

**AI 输入包：** 设计 ER、V001、导入脚本、课程系统菜单/按钮清单、角色继承规则。
**AI 输出：** 迁移 SQL、回填 SQL、数据字典 diff、约束测试。
**验收：** 18/115/359/2154 数量不变；权限码稳定；旧实体退出逻辑模型；ER/表/字典一致。

### 5.2 陈清香（前端公共/登录/用户部门）

**阶段二任务：**

- S2-10：课程系统前端接入；登录、菜单、按钮、403。
- S2-15：本组前端真实 HTTP、appCode、401/403/409、会话清理。
- 前端 Nginx 镜像与构建配置（与王信凯）。

**AI 输入包：** 课程前端代码、5 API 契约、页面/菜单/按钮清单、现有 `auth.js/router`。
**AI 输出：** HTTP 客户端、登录/权限树适配、按钮/路由守卫、网络面板证据。
**验收：** 关 mock 后登录、菜单、用户/部门页面真实可用；无权限按钮置灰；直接 URL 403。

### 5.3 杨昕桐（角色/权限前端）

**阶段二任务：**

- S2-10：课程系统菜单/按钮权限码与页面动作映射。
- S2-15：角色授权页、菜单/按钮树、角色继承页真实接入。
- 维护 `docs/api-map.md` 的前端部分。

**AI 输入包：** 菜单/按钮清单、权限码、角色授权原型、OpenAPI。
**AI 输出：** 权限树组件、按钮状态、授权页真实数据、继承页。
**验收：** 授权后下一次请求生效；撤权后菜单/按钮变化；角色继承树与菜单树区分清楚。

### 5.4 王信凯（面向功能后端）

**阶段二任务：**

- S2-05：JPA Entity/Repository/Adapter、共享 UseCase/Command/Result。
- S2-06：5 个权限 API、登录 appCode、当前应用权限。
- S2-07：菜单树、权限—API 拦截器、`operationId`、未映射默认拒绝、启动校验。
- S2-08：部门管理员字段/范围/任命。
- S2-16：logback + AOP 三层日志（已有 BE-03，补 logback 配置和 Swarm 日志）。
- S2-17：后端镜像、Swarm stack、部署脚本。

**AI 输入包：** OpenAPI、V002、领域规则、现有 Service/Controller、测试清单。
**AI 输出：** JPA 实现、5 API、拦截器、事务、日志配置、部署文件。
**验收：** Postman 越权 403；未映射 403；Swarm 三层日志同一 requestId；102+ 测试不回归。

### 5.5 胡俊朗（面向对象/DDD/UML）

**阶段二任务：**

- S2-12：DDD 领域对象、仓储端口、Adapter；创建者/信息专家等职责分配。
- S2-13：DDD Service + 与 structure 共用契约测试。
- S2-14：两设计 profile 切换、差异清单、GRASP 说明。
- S2-19：包图、类图、差异顺序图；PNG/SVG；目视检查。

**AI 输入包：** UseCase 契约、表结构、领域规则、structure 实现、UML skill。
**AI 输出：** 领域模型、Adapter、DDD Service、两设计测试、图源/图。
**验收：** 同一契约测试两套通过；顺序图只画差异；每处差异能对应 GRASP；图与代码一致。

### 5.6 孙逸轩（API/集成/验收）

**阶段二任务：**

- S2-01：阶段二范围基线、课程系统菜单/按钮/API 清单。
- S2-02：OpenAPI 0.4.0、5 API 契约、page→API 映射、APIFOX 同步。
- S2-11：课程系统后端接入契约与联调。
- S2-18：黑盒测试集（等价类/边界/异常）、需求↔测试追溯。
- S2-20：验收记录、截图、日志、未测项、演示脚本。

**AI 输入包：** 课程要求、课程系统代码/契约、OpenAPI、测试用例。
**AI 输出：** 契约、映射表、测试集、验收证据。
**验收：** 5 API 与课程系统联调；黑盒用例覆盖正常/边界/异常；证据可复现。

---

## 6. 11/17—11/24 日历

> 两条主线并行：**权限系统主链**（王信凯/郑凯/胡俊朗）与 **课程系统接入**（陈清香/杨昕桐/孙逸轩）。每天结束开 15 分钟同步，只保留“已完成/阻塞/明日切片”。

| 日期 | 主链 | 课程接入/前端 | 出口 |
| --- | --- | --- | --- |
| 11/17（一） | S2-01 范围/清单；S2-02 5 API 契约草案 | 等课程代码；先用 stub 列菜单/按钮/API | 范围基线、5 API 草案、课程清单 |
| 11/18（二） | S2-03 V002 表；S2-04 回填；S2-05 JPA 骨架 | 课程前端 stub 跑通登录/菜单 | V002、JPA 骨架、stub 页面 |
| 11/19（三） | S2-06 登录/appCode/5 API（structure）；S2-07 拦截器 | 课程前端接 5 API；菜单/按钮权限 | 5 API 可用、越权 403、课程菜单受控 |
| 11/20（四） | S2-08 部门管理员；S2-09 角色继承；S2-12 DDD 领域模型 | 课程后端 API 判定接入（stub） | 管理员边界、继承、DDD 骨架 |
| 11/21（五） | S2-13 DDD Service + 两设计契约；S2-14 切换 | 课程系统真实代码接入（如已下发）；S2-15 前端真实化 | 两套通过同一契约；课程真实链路 |
| 11/22（六） | S2-16 logback/AOP；S2-17 Swarm 部署 | 前端镜像、Nginx、课程系统镜像 | Swarm 可访问、三层日志 |
| 11/23（日） | S2-18 黑盒测试；S2-19 图/GRASP；S2-20 证据 | 联调复验、浏览器 E2E | 测试/图/证据/未测项 |
| 11/24（一） | 冻结、全量回归、备份、演示排练 | 课程系统最终联调 | tag + 验收包 |

**每日固定节奏：**

- 09:30 同步 15 分钟：昨天完成/今天切片/阻塞。
- 10:00—12:00 AI 切片 1（先计划，后测试，后实现）。
- 14:00—17:00 AI 切片 2；17:00 前跑全量测试。
- 17:30 证据入库：测试输出、日志、截图、未测项。
- 当天不合并“测试未过/图未更新”的切片。

---

## 7. 验收清单

### 7.1 助教检查

- [ ] `docker stack deploy` 成功；前端、后端、MySQL、课程系统服务都在 Swarm 中运行。
- [ ] 浏览器可访问前端；登录、菜单、按钮、用户/角色页面可用。
- [ ] 一次前端操作能在 `docker service logs` 看到 API→Service→Infrastructure 三条日志，`requestId` 相同。
- [ ] 后端重启/容器重建后 MySQL 数据仍在；数据库卷绑定/放置方案明确。
- [ ] 课程系统前端/后端与我们系统真实联通；stub 结果与真实结果分开记录。

### 7.2 老师检查

- [ ] 5 个权限 API 可用：登录、退出、当前权限/角色、前端判定、API 判定。
- [ ] 课程系统菜单按权限显示/置灰；无权限按钮不能操作；直接调 API 被拒绝。
- [ ] 菜单/按钮/API 目录与数据库一致；父菜单无权限 → 子菜单/按钮不可用。
- [ ] 角色继承：多父 DAG、无环、有效权限含来源；继承树与菜单树区分。
- [ ] 两套设计：同一 API/DB/契约测试；顺序图只画差异；每处差异对应 GRASP。
- [ ] 包图/类图全部；Service 以下；Controller 以上不重复；PNG/SVG 目视检查。
- [ ] 黑盒测试覆盖等价类/边界/异常；需求编号↔测试↔证据可追溯。
- [ ] 技术问答：JPA、事务、CloneFactory、logback、Swarm；无 JDBC/MyBatis 主实现；不引入无法解释的技术。
- [ ] Gitee/adoc/OpenAPI/APIFOX/Jacoco 材料齐全。

### 7.3 安全与数据

- [ ] 未登录 401；无权限 403；未登记/未映射 API 403；版本冲突 409。
- [ ] 撤权/停用/退出后旧凭据下一次请求失效。
- [ ] 日志不打印密码、Cookie、Token、哈希、完整响应、员工隐私。
- [ ] 数据库不暴露公网；Swarm secrets/环境变量不提交仓库。

---

## 8. 风险与裁剪

| 风险 | 影响 | 应对 |
| --- | --- | --- |
| 课程系统代码晚到 | 无法真实联调 | 先用 stub + 契约测试；代码到位当天替换；保留 stub 证据 |
| 7 天做不完两套设计 | 老师检查扣分 | 先完成 structure 全主链 + DDD 一个纵向闭环（登录/授角）；其余模块 DDD 后续补；不要两套都半成品 |
| Swarm 集群资源不足 | 无法部署 | 提前和助教确认节点/镜像仓库；先 3 节点跑通，再扩到 5—6 台；单副本后端 + Spring Session JDBC |
| V002 迁移拖慢 | 主链阻塞 | V002 只做最小闭环：应用/菜单/按钮/权限/API/映射/管理员字段/角色继承；旧表回填后可保留 |
| AI 混两套设计 | 代码不可讲 | 每次只让 AI 做一个设计；prompt 明确“不要生成另一套”；逐行 review |
| 黑盒测试来不及 | 老师抽查扣分 | 先覆盖 5 API、菜单/按钮、课程接入、管理员、继承；其余记未测项 |
| 数据库/权限模型返工 | 时间浪费 | 先冻结模型（S2-01/02/03），再写 JPA/Service；模型变更走小 PR |

**裁剪顺序（从先砍到后砍）：**

1. 非核心 17 个应用的深度功能（保留占位/目录）。
2. 角色继承的高级 UI（先 API + 树 + 测试）。
3. DDD 全模块（先登录/授角/角色授权一个闭环）。
4. 性能优化、审计日志、约束、激活角色（阶段三）。
5. 课程系统非核心页面（先登录/菜单/一个业务 API 闭环）。

**不能砍：** 5 API、课程系统前端菜单/后端 API 控制、Swarm、三层日志、两套设计至少一个闭环、包图/类图、黑盒主链。

---

## 9. 旧计划与本计划的关系

- `docs/planning/milestones.md` 的日期和阶段划分只作参考；以课程 PDF 的 11/24—11/26 第二次检查为准。
- `docs/planning/stage1-task-breakdown.md` 是阶段一任务，不再作为阶段二依据。
- `docs/reviews/2026-11-12-第二轮检查整改意见-方法论版.md` 的问题清单仍有效；本计划把它排进阶段二切片。
- 老师示范项目的课程管理业务不作为本组需求；课程管理系统只是**被控系统/接入目标**，我们不改其业务。

---

## 附录 A：每个切片的 AI 输入包清单

| 切片 | 必给 AI 的输入 | 禁止给 AI 的内容 |
| --- | --- | --- |
| S2-01 | 课程要求、课程系统菜单/按钮清单、现有需求 | 整份需求文档、整个仓库 |
| S2-02 | S2-01、原型页面、现有 OpenAPI | 旧版不相关接口 |
| S2-03 | 设计 ER、V001、S2-02 | 导入的真实数据 |
| S2-04 | V002、旧表、导入脚本 | 真实员工姓名/电话/密码 |
| S2-05 | V002、现有 Mapper、UseCase 契约 | 前端代码、课程业务 |
| S2-06 | 5 API 契约、V002、现有 AuthService | 另一套设计 |
| S2-07 | API 映射、operationId、菜单/按钮清单 | 课程系统业务代码 |
| S2-08 | 用户表、部门树、管理规则 | 真实组织数据 |
| S2-09 | 角色表、继承规则、测试用例 | 另一套设计 |
| S2-10 | 课程前端代码、5 API、菜单/按钮码 | 课程业务规则改动 |
| S2-11 | 课程后端代码、API 判定契约 | 课程业务逻辑改动 |
| S2-12 | UseCase 契约、表、领域规则 | structure 实现细节（避免照抄） |
| S2-13 | DDD 领域模型、契约测试 | 另一套设计 |
| S2-14 | 两套实现、差异清单 | 整份代码库 |
| S2-15 | 5 API、页面/按钮清单 | mock 业务数据 |
| S2-16 | 现有日志配置、部署配置 | 密码/Token/隐私 |
| S2-17 | 镜像、stack、环境变量清单 | 真实密钥 |
| S2-18 | 需求、API、页面、已实现 | 未确认的猜测 |
| S2-19 | 两套代码、UML skill | 未实现的设计 |
| S2-20 | 全部证据 | 未执行的结果 |

## 附录 B：现场演示脚本（10 分钟）

1. 打开 Swarm 中的课程系统前端，登录；展示登录调用权限系统。
2. 展示当前用户菜单/按钮；无权限按钮置灰。
3. 用 Postman 复制凭据直接调无权限课程 API → 403；调未登记 API → 403。
4. 管理员撤权 → 用户下一次请求 403；退出 → 旧凭据失效。
5. 展示 `docker service logs` 同一 `requestId` 的 API→Service→Infrastructure 三条日志。
6. 展示 `structure` 与 `ddd` 同一契约测试通过；打开差异顺序图讲 GRASP。
7. 展示包图/类图；说明 Controller 以上相同不重复画。
8. 展示 MySQL 重启/容器重建后数据仍在。
9. 展示黑盒用例：正常/边界/异常各一条；说明未测项。

## 附录 C：一个切片的完整 AI 对话示例（S2-06 登录/appCode）

> 目的：示范“不要一次做完”，把登录切片拆成 5 轮 AI 对话。每一轮都要求 AI 停下等确认。

**第 1 轮：只出计划**

```text
只做计划，不写代码。
切片：S2-06 登录 + appCode + 当前应用权限（structure 实现）。
输入：
- 需求：LOGIN-01/02、AUTHZ-01；5 API 中的登录/当前权限；
- 契约：api/openapi.yaml 的 post_auth_login / get_me_permissions；
- 表：sys_user、sys_user_role、sys_role、sys_role_permission、sys_permission、app_application、app_resource；
- 现有代码：AuthService、AuthorizationService、MeService、UserMapper、RoleMapper。
输出：1) 文件清单 2) 失败测试清单 3) 风险点 4) 依赖。
约束：不写代码；不改 DDD；不引入 JDBC/MyBatis；不发明字段。
```

**第 2 轮：只写失败测试**

```text
只写测试，不写实现。
范围：AuthService/MeService 的 appCode 过滤；文件白名单：AuthServiceTest、MeServiceTest、Requests。
要求：
- 登录缺 appCode 返回 400；
- 登录 appCode=hr 只返回 hr 权限；
- 同一用户 appCode=oa 不返回 hr 权限；
- 当前权限按 appCode 过滤；
- 未登录 401。
测试名带需求编号；断言用字面量；先说明每个测试防的破坏。
```

**第 3 轮：最小实现**

```text
只实现让上述测试通过的最小代码。
文件白名单：Requests.Login、AuthService、AuthorizationService、MeService、AuthController。
约束：Session 保存 currentAppCode；权限按 appCode 过滤；不改其他 API；不引入新依赖；中文 Javadoc。
输出 diff + `mvn test` 结果。
```

**第 4 轮：逐行 review**

```text
只 review，不改代码。
检查：appCode 是否来自契约；是否只返回当前应用；是否泄露其他应用；Session 是否轮换；错误码是否 400/401；是否破坏撤权/停用/改密；是否有测试遗漏。
按严重程度列问题，附文件:行号。
```

**第 5 轮：更新证据**

```text
只更新文档/测试记录，不改业务代码。
输出：S2-06 的验收记录、测试输出、未测项；更新 docs/api-map.md 中登录/当前权限两行。
```

## 附录 D：课程系统菜单/按钮/API 清单模板

课程系统前端到位后，先做一张表，再让 AI 写代码；不要直接让 AI 读整个前端项目。

| 字段 | 说明 | 示例 |
| --- | --- | --- |
| `appCode` | 课程系统在权限系统中的应用码 | `course` |
| `menuCode` | 菜单稳定编码 | `course:menu:course` |
| `menuName` | 显示名 | 课程管理 |
| `parentCode` | 父菜单编码 | `course:menu:root` |
| `route` | 前端路由 | `/teacher/courses` |
| `permissionCode` | 菜单权限码 | `course:menu:course:read` |
| `buttonCode` | 按钮稳定编码 | `course:course:create` |
| `buttonName` | 按钮显示名 | 新建课程 |
| `buttonPermission` | 按钮权限码 | `course:course:create` |
| `apiMethod` | 按钮对应方法 | `POST` |
| `apiPath` | 路径模板 | `/course/courses` |
| `operationId` | 稳定 API 标识 | `post_course_courses` |
| `checkMode` | 前端/后端/两者 | 两者 |

**提取步骤：**

1. 让 AI 只读课程系统前端路由/菜单配置，输出菜单树和按钮清单；人工核对页面。
2. 让 AI 只读课程系统后端 Controller，输出 method/path/operationId；人工核对前端调用。
3. 人工给每个菜单/按钮分配稳定权限码；不要让 AI 临时起名。
4. 写入权限系统 V002 种子/导入脚本；同步 OpenAPI/APIFOX。
5. 前端按权限码显示/置灰；后端每个受保护 API 调权限系统判定。
6. 用 stub 先跑通，再用真实课程系统替换；两套结果分别记录。
