# RBAC 权限管理项目答辩手册

面向全组的整体讲解、个人追问、代码证据与现场排练。

本手册针对当前仓库整理254道有建议回答和深入追问的问题。阅读目标是把项目需求、设计、代码和测试讲成一条可追溯的链路，并让六位成员能分别解释本人方向及相邻模块。

建议回答是依据仓库形成的答辩准备稿，不代表老师一定会原样提问。个人开场必须按本人实际贡献调整；后续设计、已有报告和本轮新增核验均明确区分。

编制日期 2026年10月27日  面向对象分析与设计课程第一阶段及合并JavaEE口试

GitHub快照 4a2bed3b2857aefcb7831c9179f0f30b2d0afa23

Gitee快照 ceadcaff176eb2c94f71c50f72c21aa6f1c68bdb

内容范围 14章问答 7份开场稿 26项易错口径 33个API操作 29项集成测试索引 代码与官方资料入口

当前事实摘要

| 部分 | 能够据仓库确认 | 仍需区分 |
|---|---|---|
| 后端 | Spring Boot 3.3.5 Java 21 Spring JDBC RBAC0 | 尚无可运行独立OO后端 |
| 原型 | Vue3与Element Plus 内存mock | 尚未完成Vue到Java真实联调 |
| 接口 | OpenAPI 0.3.0 25路径33操作 Apifox主入口 | 平台内容与部署接口仍应逐版本核对 |
| 测试 | 报告记90项Java 60项前端 41项导入通过 | 本手册编制没有新增一次业务测试执行 |
| 覆盖率 | 报告记行90.89% 分支75.31% | 不是权限正确性或MySQL性能的充分证明 |
| 部署 | 已有公开实例和历史自测记录 | 当前服务器SHA 镜像及真实业务独立复验待核对 |

## 阅读导航

先读第一章形成统一口径，再按职责表选择主读章节。第二轮把自己的答案交给相邻成员追问，最后用第十四章做跨成员模拟。Word导航窗格可按问题编号定位，Markdown适合全文搜索和继续补充。

| 对象 | 主读范围 | 优先掌握 |
|---|---|---|
| 全体 | 第一 三 四章 以及自己的成员章 | 项目边界 RBAC0 范围与动作 需求设计代码测试的对应 |
| 组长 | 第二 十二 十四章 | 分工 集成 证据 缺口 部署版本 现场组织 |
| 成员一 郑凯 | 第五章 并读第八 十章 | 十表 稳定编号 负责人 导入失败 3549与3555 |
| 成员二 陈清香 | 第六章 并读第一 三章 | mock边界 会话 路由 首次改密 用户组织交互 |
| 成员三 杨昕桐 | 第七章 并读第八 十章 | 权限并集 完整草稿 过期响应 409 500与read依赖 |
| 成员四 王信凯 | 第八 十一 十二章 | current ready require target 事务 CAS 行锁及部署 |
| 成员五 胡俊朗 | 第九章 并读第四 八 十一章 | 范式 职责 模型 不变量 后续OO和函数式方案 |
| 成员六 孙逸轩 | 第十章 并读第十二 十三章 | 契约 独立预期 分层证据 覆盖率及真实HTTP |

组长身份登记

责任表只确认组长账号wenjiu-leng66，尚未明确对应成员编号。因此本手册把组长职责独立成章，不擅自把组长认定为某一成员。成员姓名按当前责任表列示。

回答方法

先说结论，再说需求依据和当前实现；老师继续问时指出一个方法、一条断言和一个限制。不会的细节先明确不确定点，再由相关成员补充，避免把未来方案或框架常识讲成已实现事实。

目录

- 第一章 全组必须统一的项目认识 共12题
- 第二章 组长的整体设计与协作追问 共18题
- 第三章 需求业务与RBAC规则 共20题
- 第四章 结构化分析与模型解释 共15题
- 第五章 成员一郑凯的数据与数据库方向 共22题
- 第六章 成员二陈清香的前端基础方向 共18题
- 第七章 成员三杨昕桐的权限前端方向 共20题
- 第八章 成员四王信凯的功能后端方向 共26题
- 第九章 成员五胡俊朗的面向对象与设计方向 共18题
- 第十章 成员六孙逸轩的API测试与集成方向 共21题
- 第十一章 JavaEE与代码技术追问 共19题
- 第十二章 服务器容器与交付追问 共17题
- 第十三章 实验三性能与监控追问 共16题
- 第十四章 六位成员之间的交叉追问 共12题

## 第一章 全组必须统一的项目认识

所有成员先读 本章决定后面每个答案是否站得住

### Q001 用一分钟介绍这个项目

**建议回答** 我们做的是市政公司统一权限管理平台。员工、组织、角色和功能权限统一维护，员工登录后按授权看到菜单，并且真实后端对每次业务请求核对身份、动作权限和数据范围。第一阶段交付是RBAC0面向功能后端、可独立运行的Vue原型、API设计、数据库及部署测试材料。

**继续追问** 老师追问价值时，举“经理可以查看全部台账，但只能编辑本部门及下级员工联系方式”的例子，把统一授权和具体数据控制一起讲清楚。不要只说做了增删改查。

**代码和依据** [S01](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/README.md#L1) · [S02](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/requirements/req-spec.md#L1) · [S04](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/structured-design.md#L1)

### Q002 当前真正完成了哪些部分

**建议回答** 可以从源码确认Java与Spring JDBC后端、会话认证、用户角色授权、门户和台账、离线导入、Vue内存原型及容器部署文件。仓库报告记录了90项后端测试、60项前端测试、41项导入测试；这些是相应报告的执行结果。服务器当前代码版本、现场业务调用和持久化仍需实际核对。

**继续追问** 按“实现存在、报告曾执行、当前部署待确认”分开回答。不要把源码存在直接讲成服务器部署完成，也不要把旧报告说成从未执行。

**代码和依据** [S01](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/README.md#L1) · [S36](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/test-report-2026-10-27.md#L1) · [S42](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/verification-record.md#L1)

### Q003 前端到底有没有连接真实后端

**建议回答** 当前Vue应用通过frontend/src/api/mock.js操作浏览器内存合成数据。真实Java后端连接数据库并使用JSESSIONID会话，现场通过Swagger或HTTP脚本单独验收。两条链路各自可运行，第一阶段约定允许分别检查。

**继续追问** 如果老师问保存后为什么数据库没变化，应说明刚才操作的是原型；要证明真实保存，切到真实API并核对数据库或重新查询。

**代码和依据** [S22](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/api/mock.js#L1) · [S23](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/router/index.js#L1) · [S40](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/scripts/smoke_http.py#L1) · [S41](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1)

### Q004 RBAC0在本项目中是什么意思

**建议回答** 用户被分配角色，角色被授予权限，用户得到已分配且启用角色的直接权限并集。本阶段没有角色继承和主动激活角色子集；ALL、DEPT、SELF是本项目附加的数据范围规则，不等同于RBAC层级。

**继续追问** 可以写出U→UA→R→PA→P的关系，再解释同一个权限码怎样合并范围。不要把多角色并集说成继承。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1) · [S25](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/authorization/rbac0-policy.js#L1)

### Q005 系统是不是实现了十八个业务系统

**建议回答** 十八个系统、115个模块和359个功能点构成权限目录。大多数功能点提供菜单、动作按钮和范围展示；员工台账实现真实查询及电话邮箱编辑，用于验证数据范围。权限目录完整不等于十八套业务流程全部实现。

**继续追问** 被问审批或导出时先看该功能是原型占位还是真实API；不能把“点击后提示”描述为审批流或文件导出完成。

**代码和依据** [S02](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/requirements/req-spec.md#L1) · [S03](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/requirement-coverage.md#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1)

### Q006 系统管理员和部门管理员有什么区别

**建议回答** 系统管理员拥有全局管理能力并受强保护；部门管理员在自己部门及全部下级范围管理用户，只能增撤允许的业务角色，不能借自建管理角色提权，也不能直接修改角色授权。管理边界由服务端检查。

**继续追问** 补充角色查看权不等于查看全部角色成员，成员列表还要按用户查看范围裁剪；普通资料更新和停用删除保护也不能混为一谈。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20)

### Q007 哪些页面属于后续阶段

**建议回答** 角色继承属于后续RBAC1；职责约束、会话激活角色和审计属于更后续阶段。当前对应页面是静态原型，backend-oo目录也只有规划，尚无可运行的独立领域后端。

**继续追问** 老师看到页面时明确它展示未来交互结构，不把静态开关说成已保存到数据库。代码少的部分应解释规划与缺口。

**代码和依据** [S01](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/README.md#L1) · [S23](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/router/index.js#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1)

### Q008 项目规模应该报哪些数字

**建议回答** 正式输入基准是111个组织、18个系统、115个模块、359个功能点、2154个功能动作权限、10000名员工加admin。原清单五角色授权3549条，平台部门管理员另补6条，完整输入条件下共3555条角色权限关系。

**继续追问** 2154是359乘6得到的目录组合数，3555是角色与权限的关联数；不能互换。合成目录达到规模也不能证明原始员工资料已导入。

**代码和依据** [S02](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/requirements/req-spec.md#L1) · [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1) · [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1) · [S48](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/full-data-verification-2026-10-18.md#L1)

### Q009 Apifox Swagger和OpenAPI各自干什么

**建议回答** api/openapi.yaml是仓库中可追溯版本的接口契约；Apifox公开项目用于管理分享接口文档；Swagger UI用于运行实例的在线调试，/v3/api-docs由后端生成。现场应核对平台文档、静态契约与实际接口一致。

**继续追问** 不能只凭三个页面都能打开认定字段状态码一致。特别核对登录account、用户username、Cookie及授权集合version。

**代码和依据** [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1) · [S50](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/README.md#L1)

### Q010 GitHub和Gitee为什么都保留

**建议回答** GitHub用于内部阅读、解释资料、历史追溯和开发；Gitee用于正式检查，保留代码、必要设计、分工与测试记录。共同运行代码应同步，文档布局可以不同，因此不能用镜像强推直接覆盖。

**继续追问** 修改远端地址只改变后续取代码来源，本身不会改变已运行进程。服务器更新与源码仓库同步是两件要分别登记的事。

**代码和依据** [S01](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/README.md#L1) · [S41](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1) · [S49](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/planning/team.md#L1)

### Q011 设计文档为什么换成adoc

**建议回答** 课程要求数据库设计和详细设计采用AsciiDoc，因此当前权威文件是docs/database/database-design.adoc和docs/design/detailed-design.adoc。它们依据实际迁移与代码整理，原Markdown入口已更新，避免维护两套相互漂移的设计。

**继续追问** AsciiDoc是文档源格式，格式合格还要看设计与代码一致。图、表、链接都应能定位真实对象，不恢复含虚构机制的旧快照。

**代码和依据** [S05](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/database/database-design.adoc#L1) · [S06](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/detailed-design.adoc#L1)

### Q012 答不出问题时应该怎么处理

**建议回答** 先确认老师问的是需求、设计、实现还是证据，再说已确认的部分和具体不确定点。能定位代码就打开方法，相关成员补充跨模块内容。尚未测量或尚未实现的能力，应说明验证方案或后续计划。

**继续追问** 不要用“应该是框架自动处理”掩盖不了解的边界。每个人至少完整讲清一个用例、一段核心代码和一个有明确断言的测试。

**代码和依据** [S03](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/requirement-coverage.md#L1) · [S42](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/verification-record.md#L1) · [S49](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/planning/team.md#L1)

## 第二章 组长的整体设计与协作追问

组长统筹职责单列 不推定组长对应哪位成员

### Q013 组长怎样概括整体架构

**建议回答** 静态Vue原型、真实Java接口和MySQL分别承担交互、业务规则和持久化职责；Nginx作为统一HTTP入口。后端按Controller、Service、Mapper组织，OpenAPI描述对外契约，离线导入负责原始资料清洗装载。

**继续追问** 老师问为何这样拆时，说明输入校验、业务授权、SQL访问和部署配置的变更原因不同，分开后可独立测试与定位。

**代码和依据** [S04](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/structured-design.md#L1) · [S06](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/detailed-design.adoc#L1) · [S37](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/compose.yaml#L1) · [S38](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/nginx.conf#L1)

### Q014 为什么第一阶段采用面向功能设计

**建议回答** 当前重点是把认证、授权、用户维护、授权集合替换等用例分解为清晰处理步骤，再集中复用权限和范围判定。服务类是Java组织代码的手段，是否面向功能主要看分解依据与业务规则如何组合。

**继续追问** 不能说“用了Java类所以已经完成OO设计”，也不能说面向功能实现完全不能有对象。用结构图和一次服务调用顺序说明。

**代码和依据** [S04](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/structured-design.md#L1) · [S06](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/detailed-design.adoc#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1)

### Q015 六个人怎样分工又怎样避免接口割裂

**建议回答** 责任表分为数据、前端基础、权限前端、功能后端、OO与函数式规划、API测试集成六个方向。接口与数据库作为共享契约，公共前端策略统一；作者维护自己模块的说明和测试，集成人协调兼容性与证据。

**继续追问** 分工表说明责任，不授予目录独占权。不能由组长包办全部测试，也不能用“不是我模块”回避跨接口理解。

**代码和依据** [S49](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/planning/team.md#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1) · [S04](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/structured-design.md#L1)

### Q016 组长如何证明小组真正集成过

**建议回答** 展示当前主分支SHA、成员整合记录、统一API字段、数据库迁移与对应测试报告，再选择一条跨模块链路定位代码。服务器实例另记录版本、镜像、迁移和真实请求。

**继续追问** 仅展示几个合并提交不够；要能说明某个贡献解决什么问题、如何验证以及为什么未原样采纳过时内容。

**代码和依据** [S01](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/README.md#L1) · [S42](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/verification-record.md#L1) · [S51](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/contribution-review-2026-10-27.md#L1)

### Q017 需求有冲突时由谁决定

**建议回答** 先区分课程来源、已确认需求、API细节和实现差异。记录问题及影响，由相关成员统一口径，必要时向老师核实，再同步需求、契约、数据字典、代码和测试。不能让实现偶然行为悄悄变成需求。

**继续追问** 举查看ALL但编辑DEPT、read依赖、403与404的例子。当前需求文件仍含历史待确认表述，应以已归并结论和当前一期范围解释。

**代码和依据** [S02](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/requirements/req-spec.md#L1) · [S03](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/requirement-coverage.md#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1) · [S51](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/contribution-review-2026-10-27.md#L1)

### Q018 为什么没有把所有未来能力提前实现

**建议回答** 本期先证明直接授权、范围与维护流程正确，继承和约束会改变模型、权限计算与测试空间，不能用静态页面假装完成。明确阶段边界能让验收对象和责任可追溯。

**继续追问** 如果老师问可扩展性，指出后续需要改哪些模型和算法，而不是承诺“加一张表就行”。

**代码和依据** [S03](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/requirement-coverage.md#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1) · [S45](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/uml/domain-model.md#L1)

### Q019 如何定义一个任务真正完成

**建议回答** 代码进入约定版本，接口及设计同步，相关测试和构建有实际结果，影响与限制有记录；涉及部署的任务还要证明实例版本和真实调用。完成标准按任务约定，不能把push等同于部署完成。

**继续追问** 界面原型任务和后端任务证据不同，合成测试与正式全量导入也不同。用具体交付物回答，不报“完成百分之八十”。

**代码和依据** [S03](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/requirement-coverage.md#L1) · [S36](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/test-report-2026-10-27.md#L1) · [S42](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/verification-record.md#L1)

### Q020 为什么允许所有组员直接更新main

**建议回答** 现行协作规则允许有写权限的成员编辑所有目录、直接推送main或自行合并PR，分支和PR可选。仍应同步远端、检查差异与相关测试，保留他人提交，不用force push覆盖共享历史。

**继续追问** main开放是权限和协作选择，代码质量仍靠清晰提交、自动检查和作者修复；不意味着给匿名访问者写权限。

**代码和依据** [S49](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/planning/team.md#L1) · [S52](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/CONTRIBUTING.md#L1)

### Q021 别人改动是否都应该合并

**建议回答** 按当前需求、代码兼容、验证结果、文档准确性和维护成本判断。有效修复可以采纳，旧快照或重复讲解可提炼，含虚构机制或不当结论的内容要修正后吸收。保留作者与原分支历史。

**继续追问** 举策略导出修复、来源配置透传、SQL计时口径修正和设计图校订，说明“有贡献”与“原样发布”不是同一个决定。

**代码和依据** [S51](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/contribution-review-2026-10-27.md#L1) · [S47](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/scripts/measure_full_db_perf.ps1#L1)

### Q022 测试报告与当前提交对不上怎么办

**建议回答** 先列明报告实际运行时间、源码指纹和测试环境，再核对当前后端文件集合。当前报告包含整合后的新增测试，但正文仍引用较早基础SHA，不能仅靠该SHA判断被测树；交付前应补全准确版本对应关系。

**继续追问** 不要事后把报告日期或旧SHA改写成一次未发生的新测试。当前源码指纹可核对，但现场仍须能重跑。

**代码和依据** [S36](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/test-report-2026-10-27.md#L1) · [S42](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/verification-record.md#L1)

### Q023 组长应如何安排每个人发言

**建议回答** 每人先讲本人模块的输入输出和一个关键决策，再准备一项拒绝或失败测试；跨模块追问由熟悉者补充。组长负责连接项目目标、分工、证据与缺口，不替所有人作答。

**继续追问** 建议每人先有一分钟稳定开场，再按老师追问展开。个人贡献以实际提交和产物核实，不能按目录分工自动认领全部代码。

**代码和依据** [S49](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/planning/team.md#L1) · [S51](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/contribution-review-2026-10-27.md#L1)

### Q024 如果检查当天服务器出问题怎么办

**建议回答** 先用健康、静态入口、后端日志、数据库就绪和配置逐层定位，保留错误与版本。准备当前源码、构建与测试命令、脱敏配置说明和已归档证据，尽快恢复受控实例。

**继续追问** 不要把本地成功冒充服务器成功，也不要为了恢复而直接删库或重置真实数据。演示环境与正式数据环境先确认。

**代码和依据** [S40](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/scripts/smoke_http.py#L1) · [S41](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1) · [S42](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/verification-record.md#L1)

### Q025 为什么不直接让前端连接数据库

**建议回答** 前端环境不可信，不能持有数据库凭证或直接执行任意数据操作。真实后端负责认证、权限、范围、事务与响应字段，数据库留在内部网络，前端通过受约束的HTTP接口访问。

**继续追问** 浏览器隐藏按钮只能改善交互；真实后端必须在用户绕过页面直接发请求时仍拒绝越权。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1) · [S37](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/compose.yaml#L1)

### Q026 上线时最需要确认哪几件事

**建议回答** 确认部署源码和镜像、MySQL迁移与数据模式、环境密码和Cookie配置、公开端口及Nginx代理、真实登录与拒绝场景、持久化和备份恢复。检查按证据登记，不从Compose文件推定已经执行。

**继续追问** 这不是要求现场随意重启服务器。执行变更前明确目标与数据，操作后核对业务结果和版本。

**代码和依据** [S37](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/compose.yaml#L1) · [S40](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/scripts/smoke_http.py#L1) · [S41](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1) · [S42](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/verification-record.md#L1)

### Q027 项目最大的设计代价是什么

**建议回答** 逐请求查库使撤权下一请求生效，代价是额外查询；部分列表Java过滤分页使范围语义清晰，代价是全量读取；内存会话实现简单，代价是重启失效和多实例共享问题。

**继续追问** 回答改进时先守住正确性，再提出带测量的SQL下推、批量查询、权限版本缓存或共享会话设计。不要声称这些已经落地。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S41](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1)

### Q028 你们怎样证明做的是课程要求而非只做页面

**建议回答** 按需求编号映射到结构化模型、服务方法、API定义和测试断言；现场运行真实后端接口与分层测试，并打开覆盖率细节。页面只是原型交互的证据之一。

**继续追问** 优先用授权保存与越界编辑讲通完整链路，避免只展示截图和菜单。

**代码和依据** [S03](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/requirement-coverage.md#L1) · [S04](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/structured-design.md#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1)

### Q029 组长应该怎样解释尚未完成的事情

**建议回答** 把缺口按阶段区分：本期实现待复验的部署和MySQL证据、本期非功能待测的端到端性能、后续阶段的继承约束与OO后端。说明风险、责任方向和下一项可验证产物。

**继续追问** 不能把全部缺口都归为后续，也不能把后续设计误报为本期缺陷。以当前课程范围和代码事实为依据。

**代码和依据** [S03](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/requirement-coverage.md#L1) · [S42](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/verification-record.md#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1)

### Q030 如果老师质疑你们代码由工具生成怎么办

**建议回答** 解释具体需求决策、每层职责、关键边界与测试断言，现场改一个小场景并说明影响。工具辅助不能代替成员理解、评审和独立验证，个人只认领自己实际完成与掌握的工作。

**继续追问** 不要编造独立开发经历或隐瞒不会的机制。能复现、定位、解释取舍才是项目答辩的核心证据。

**代码和依据** [S04](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/structured-design.md#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1) · [S49](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/planning/team.md#L1)

## 第三章 需求业务与RBAC规则

所有成员都应掌握 基础需求理解是首次检查重点

### Q031 需求与接口文档有什么区别

**建议回答** 需求说明要解决什么问题、谁使用以及怎样算完成；接口文档规定HTTP路径、字段、状态与权限；详细设计解释内部职责和处理步骤。三者要互相追溯，API存在不自动证明需求已实现。

**继续追问** 举USER-02新建用户：需求包括初始密码和不能顺便授角，接口DTO和服务验证分别落实这些规则。

**代码和依据** [S02](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/requirements/req-spec.md#L1) · [S03](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/requirement-coverage.md#L1) · [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1)

### Q032 角色与岗位是不是同一个概念

**建议回答** 岗位是人员资料，角色是授权载体。正式导入根据部门负责人关系、组织类型及已确认规则推导初始角色，不仅看岗位文字；副职等不自动获得经理角色。

**继续追问** 岗位名称变化不能自动改变权限，后续需要显式的角色分配与审计策略。

**代码和依据** [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1) · [S49](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/planning/team.md#L1)

### Q033 同一个人有多个角色怎样处理

**建议回答** 每个已分配且启用角色的直接授权按权限码合并，同码范围取ALL大于DEPT大于SELF，来源角色可用于解释。不同权限码分别计算，不把一个动作的ALL扩散到另一个动作。

**继续追问** 举read=ALL、edit=DEPT，并让老师任选一名部门外员工测试读允许、写拒绝。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S25](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/authorization/rbac0-policy.js#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q034 停用角色和删除角色有何不同

**建议回答** 停用保留角色和分配关系，但下一请求不再贡献权限；恢复后可重新贡献。删除自建角色会清理授权与分配并调整受影响用户版本，内置角色不可删除。

**继续追问** 显示用户已分配角色时包含停用角色，计算有效权限时排除停用角色，不能混用集合。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S13](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java#L1)

### Q035 无角色用户能不能登录

**建议回答** 账号启用且凭据正确可以建立身份，但没有任何直接有效业务权限。能访问哪些仅会话入口与改密状态有关，不能因为无角色而默认赋管理员权限。

**继续追问** 后端对权限为空默认拒绝；前端菜单空态应解释为无授权，不作为网络加载失败。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S08](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthService.java#L18) · [S25](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/authorization/rbac0-policy.js#L1)

### Q036 首次改密为什么是独立状态

**建议回答** 账号启用状态与mustChangePassword分别表示能否登录以及是否完成初始密码更新。用户可能启用但必须改密。后端业务入口使用ready限制，改密等入口使用current允许完成密码生命周期。

**继续追问** 不要把首次改密画成与启用停用互斥的第三种账号状态，状态图应表达两个维度。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S08](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthService.java#L18) · [S46](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/uml/state-diagrams.md#L1)

### Q037 改密码后其他会话为什么失效

**建议回答** 服务端会话保存登录时凭据标记，current每次比较数据库中的密码摘要。新摘要生成后旧会话标记不匹配，下次请求被销毁；当前主动改密会话更新为新标记，可以继续使用。

**继续追问** 这不是推送通知，也不是全部浏览器立即关闭页面；生效点是下一请求。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S08](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthService.java#L18) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q038 管理员重置密码与本人改密有什么不同

**建议回答** 本人改密验证旧密码，并更新当前会话标记；管理员重置使用环境配置的初始密码，重新开启必须改密，目标旧会话因摘要变化失效。重置接口不接受前端传入任意初始密码。

**继续追问** 非超级管理员重置ADMIN或DEPT_ADMIN账号还被额外拒绝。不要把重置接口和普通资料编辑混为一项。

**代码和依据** [S08](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthService.java#L18) · [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1)

### Q039 DEPT到底包括哪些人

**建议回答** DEPT是当前用户所属部门及全部下级部门的员工集合，不只直接下属部门。范围以稳定部门ID和组织父子关系计算，查询列表与单对象修改都需应用该规则。

**继续追问** 用户自己没有部门、目标部门不存在等边界应默认不扩成ALL。组织深度变化要有测试。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S11](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/DepartmentMapper.java#L54) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q040 SELF能否创建本部门其他员工

**建议回答** 不能。SELF表达本人记录，不是“自己所在部门”。UserService.create明确拒绝SELF创建他人；部门节点可见并不等于该部门所有成员可见。

**继续追问** 同样，SELF查询部门成员只能返回本人。这个边界已有集成用例，适合现场展示。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q041 经理看全部为何不能编辑全部

**建议回答** 查看和编辑使用不同权限码与范围。某经理的台账read可以是ALL，但edit为DEPT。写入必须重新使用编辑权限对目标判定，不能沿用列表查询得到的查看范围。

**继续追问** 把部门外员工ID放进PATCH请求应拒绝且数据不变；这是典型横向越权场景。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S29](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/LedgerService.java#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q042 用户管理与员工台账为何分开

**建议回答** 用户管理涉及账号资料、状态、重置与角色分配，使用rbac管理权限。员工台账是业务示例，使用hr:f046的动作权限，当前真实编辑只允许电话与邮箱。

**继续追问** 同一张用户表不代表两个接口有相同权限或允许修改相同字段。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1) · [S29](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/LedgerService.java#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1)

### Q043 为什么非read动作必须伴随read

**建议回答** 本项目规则要求角色对某功能授予编辑等动作时同时具备该功能查看权限。前端勾选写动作会补read，后端校验READ_REQUIRED兜底；这是约定的业务约束。

**继续追问** 规则只要求read码存在，不要求read和edit范围相等或read范围自动覆盖edit。不能擅自补一条未实现的范围约束。

**代码和依据** [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1)

### Q044 为什么权限码不使用中文名称

**建议回答** 名称可能修改且不同系统可能重名。稳定系统短码、功能编号和动作组成权限码，可用于导入、API、后端判定和前端展示。显示名称调整不应使授权失效。

**继续追问** 固定编号CSV不应按当前Excel行序重新生成。系统码也要人工核对唯一性。

**代码和依据** [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1) · [S21](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/function_codes.csv#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1)

### Q045 rbac管理权限从哪里来

**建议回答** 系统18清单中的固定功能动作码在AuthorizationService.managementCodes映射为rbac管理动作，保留来源范围。派生码不写入sys_permission，也不能由授权请求直接授予。

**继续追问** 这种映射是当前需求契约，不是所有RBAC系统必然如此；固定编号改变必须同步映射和测试。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1)

### Q046 同名员工会不会合并

**建议回答** 正式员工导入以工号为稳定账号标识，并用所属组织帮助负责人匹配，不能仅按姓名合并。负责人出现同名候选时记录未解析项，不能任意挑第一人。

**继续追问** 不提供员工表的演示回退有不同逻辑，不能把那条回退当正式全量导入依据。

**代码和依据** [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1) · [S34](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/tests/unit/test_import_excel.py#L1)

### Q047 清单角色和平台角色是什么关系

**建议回答** 原始权限清单提供五类角色的授权规则；平台补充部门管理员角色以满足管理边界，六个内置角色受到删除保护。平台新增的六条授权要和原清单3549条分开统计。

**继续追问** 公司领导是只读管理能力，不等于超级管理员；不能因为名称含领导就允许写入。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1)

### Q048 系统管理员为何不能被随意改角色

**建议回答** 当前强保护拒绝修改自己角色，且用户名admin或任何持有ADMIN的账号均受到停用、删除、改角色保护。它不只是“最后一位管理员”规则。服务也禁止把ADMIN授给其他账号。

**继续追问** 基本资料编辑与密码生命周期另按各自接口处理。不能泛化成管理员账号所有字段都不能修改。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q049 组织架构是否允许网页修改

**建议回答** 一期组织与资源目录是只读接口，来源是迁移和离线资料导入。没有提供部门增删改API，因此不能声称已经实现复杂组织调整业务。

**继续追问** 未来开放组织移动时，需要无环检查、范围影响、经理归属与权限变化的事务设计。

**代码和依据** [S02](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/requirements/req-spec.md#L1) · [S11](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/DepartmentMapper.java#L54) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1)

### Q050 需求中的性能数字怎样验收

**建议回答** 鉴权查询目标与列表保存目标必须明确测量对象、硬件、数据量和负载。数据库SQL用时不能代替Java服务或HTTP端到端时间；目标值写进需求不代表已经达标。

**继续追问** 老师问现在每秒能扛多少时，回答尚无本轮端到端压测结论，展示已有SQL历史数据的适用范围。

**代码和依据** [S03](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/requirement-coverage.md#L1) · [S47](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/scripts/measure_full_db_perf.ps1#L1) · [S48](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/full-data-verification-2026-10-18.md#L1)

## 第四章 结构化分析与模型解释

从需求转到后端设计时使用

### Q051 DFD和流程图有什么区别

**建议回答** DFD表达外部实体、处理、数据存储与数据流，重点是数据怎样被转换。流程图强调步骤顺序与分支。上下文DFD只有系统边界及主要输入输出，一层DFD再分解内部处理。

**继续追问** 不能把Controller调用Service的箭头全当DFD，也不能用页面跳转图替代数据流模型。

**代码和依据** [S04](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/structured-design.md#L1)

### Q052 上下文图的系统边界包含什么

**建议回答** 当前结构化设计把RBAC0服务与离线导入作为处理边界，员工管理人员提交查询或变更，运维数据人员提供受控原表和配置。系统返回业务结果、拒绝、清洗和就绪结果。

**继续追问** 后续OO后端、真实十八套业务系统和课程实验三产品不自动纳入当前实现边界。

**代码和依据** [S04](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/structured-design.md#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1)

### Q053 一层DFD怎样保证与上下文一致

**建议回答** 外部输入输出应在分解后找到对应处理，内部新增流要有明确数据来源和去向。文档中认证、权限判定、用户组织、角色授权、门户台账、目录和离线导入各有职责。

**继续追问** 老师可随机挑一条输入追到存储和输出，不能出现凭空生成权限或没有来源的用户身份。

**代码和依据** [S04](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/structured-design.md#L1)

### Q054 D1 D2 D3是三个数据库吗

**建议回答** 它们是同一业务数据库内的逻辑数据分组，分别覆盖用户部门、角色授权和目录。D4会话是应用内服务端状态，不是另建的数据库业务表。

**继续追问** 数据存储模型和物理部署模型不是一一对应。答辩时打开V001确认真实十表，避免把图标数量当数据库实例数。

**代码和依据** [S04](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/structured-design.md#L1) · [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1)

### Q055 功能分解如何映射到模块

**建议回答** 认证流程由AuthService，统一权限与范围由AuthorizationService，用户维护由UserService，角色授权由RoleService，其余部门目录门户台账各有功能服务。Controller处理HTTP，Mapper负责持久化。

**继续追问** 模块边界看业务变化和可复用步骤，不按页面数机械建立相同数量的服务。

**代码和依据** [S04](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/structured-design.md#L1) · [S06](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/detailed-design.adoc#L1)

### Q056 为什么授权判定要集中

**建议回答** 认证、角色并集、范围与目标检查如果散落在Controller和页面容易漏查或口径不同。集中后统一拒绝策略与管理码映射，业务服务仍按操作传入准确权限码和目标。

**继续追问** 集中服务不是自动全局拦截；当前各业务入口必须实际调用它，新增接口应补拒绝测试。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q057 高内聚低耦合在这里如何体现

**建议回答** UserService围绕用户用例组织规则，Mapper不决定谁有权限，Views只选响应字段，Requests只声明请求白名单。依赖通过构造器注入，单元测试可以替换Mapper与编码器。

**继续追问** AuthorizationService仍依赖数据库访问，不能把所有服务都称为无状态纯函数；也要承认某些列表和角色视图存在查询耦合。

**代码和依据** [S06](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/detailed-design.adoc#L1) · [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1) · [S15](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/vo/Views.java#L1)

### Q058 为什么DTO PO VO分开

**建议回答** DTO限制客户端可写字段和格式；PO承载数据库读取结果；VO或Views显式选择响应内容。三者分开能避免把passwordHash或内部字段自动暴露，也避免客户端通过多传字段修改角色。

**继续追问** Record是不可变数据载体，不自动使整个系统成为领域驱动设计。

**代码和依据** [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1) · [S15](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/vo/Views.java#L1)

### Q059 一条授权保存时序应该画哪些调用

**建议回答** 客户端提交角色ID、完整grants和旧version，Controller绑定校验，RoleService鉴权并锁角色，验证目录与read依赖，Mapper做版本条件更新和关系替换，事务提交后返回新集合与版本。

**继续追问** 错误分支要短路返回；不要画不存在的前端网络调用，因为当前Vue仍用mock。可把调用方标为Swagger或HTTP客户端。

**代码和依据** [S06](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/detailed-design.adoc#L1) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S13](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1)

### Q060 状态图怎样避免概念混杂

**建议回答** 用户账号有启用停用维度，首次改密是独立标记，会话有认证与失效状态；角色也有启用停用及删除限制。分别建模，再标明改变状态的操作与守卫。

**继续追问** “必须改密”不等于账号停用，“停用角色”不等于删除角色关系。

**代码和依据** [S46](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/uml/state-diagrams.md#L1) · [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20)

### Q061 ER图中的多对多怎样落表

**建议回答** 用户与角色通过sys_user_role，角色与权限通过sys_role_permission连接。组合主键去重，外键保证引用存在；data_scope附在角色权限关联而非用户或角色主表上。

**继续追问** 一个角色可以对不同权限有不同范围，因此把scope仅放在角色表会丢失表达能力。

**代码和依据** [S05](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/database/database-design.adoc#L1) · [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1)

### Q062 数据库视图和业务授权服务为什么并存

**建议回答** 视图便于按启用账号角色核对清单权限及最大范围；业务服务还处理会话有效性、必须改密、派生管理码和目标对象。视图不是完整授权入口。

**继续追问** 继承加入后需要同步数据库模型和Java算法，不能只改视图就声称全链路支持继承。

**代码和依据** [S05](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/database/database-design.adoc#L1) · [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1)

### Q063 模型与代码不一致以谁为准

**建议回答** 先登记差异并确认需求意图，再同步修订。当前实现事实通过源码确认，课程应交付范围通过已确认需求判断；不能把设计图错误合理化为“只是示意”，也不能让代码偶然行为反向修改需求。

**继续追问** 举虚构审计、客户端凭据版本、台账canEdit字段等模型风险，打开当前接口和类职责核对。

**代码和依据** [S02](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/requirements/req-spec.md#L1) · [S04](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/structured-design.md#L1) · [S44](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/uml/class-responsibility.md#L1) · [S51](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/contribution-review-2026-10-27.md#L1)

### Q064 如何从设计找到测试

**建议回答** 选一个设计规则，例如整组授权原子替换，定位RoleService与Mapper，再找正常保存、旧版409、缺read、失败回滚等测试。需求覆盖表记录入口，测试断言提供实际证据。

**继续追问** 不能只列测试类名，必须说明输入、预期、数据不变断言和测试环境。

**代码和依据** [S03](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/requirement-coverage.md#L1) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q065 为什么二期不能直接复制当前后端改名字

**建议回答** 课程希望比较面向功能与面向对象的组织方式，独立领域后端应按实体职责、策略和用例协作设计。可以共享契约与表结构，业务规则实现要保持独立的可比较性。

**继续追问** 复制Service加几个Domain类不会自然形成范式差异；真正判断点是规则由谁持有、对象如何维护不变量。

**代码和依据** [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1) · [S44](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/uml/class-responsibility.md#L1) · [S45](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/uml/domain-model.md#L1)

## 第五章 成员一郑凯的数据与数据库方向

主讲数据模型 原表清洗 导入迁移和核对

### Q066 数据库为什么设计十张表

**建议回答** 组织、用户、角色、系统、模块、功能点、动作、权限、用户角色、角色权限分别表达不同实体或关系，再提供一个权限核对视图。目录层次与角色授权分开，减少同一名称和动作信息重复存放。

**继续追问** 老师要求列全时按业务分组说明，不要把视图算成第十一张业务表，也不要把后续继承或审计表说成现有表。

**代码和依据** [S05](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/database/database-design.adoc#L1) · [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1)

### Q067 data_scope为什么放在角色权限关系表

**建议回答** 范围描述某角色对某项权限覆盖哪些对象，同一角色可能查看全部但只能编辑本部门。不同角色授同一个权限也可能有不同范围，所以它属于关联事实。

**继续追问** 若放用户表会限制一个用户所有动作使用同一范围；若只放角色表也无法表达同角色不同动作的差异。

**代码和依据** [S05](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/database/database-design.adoc#L1) · [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1)

### Q068 为什么要组合主键和反向索引

**建议回答** 组合主键保证同一用户角色或角色权限关系不重复，并支持以前导列查询；反向索引支持按角色找用户、按权限找角色等访问。约束和访问路径有不同目的。

**继续追问** 不能说有任何索引就所有查询快。要看查询条件、列顺序、选择性和执行计划，尤其当前很多列表仍在Java过滤。

**代码和依据** [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1) · [S12](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/UserMapper.java#L1) · [S13](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java#L1)

### Q069 手机号为什么能空但非空唯一

**建议回答** 手机号是可选资料，也是登录标识之一。应用与导入把空串归一为NULL，MySQL唯一约束允许多个NULL而限制重复非空值。保留文本类型避免前导零或格式被数字转换破坏。

**继续追问** 并发预检查仍可能竞态，数据库唯一键是兜底；错误映射为409而不是把原始数据库异常返回。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S16](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/error/GlobalExceptionHandler.java#L1) · [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1) · [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1)

### Q070 工号与数据库ID分别解决什么

**建议回答** 工号是稳定业务登录标识，数字ID是内部关系引用键；HTTP统一用字符串表达ID。授权和负责人关系使用ID，工号受唯一约束。姓名不能替代两者。

**继续追问** 工号字段在用户资料叫username，登录请求叫account；不要把接口字段employeeNo兼容别名当成又一个数据库列。

**代码和依据** [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1) · [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1)

### Q071 部门如何表示父子关系

**建议回答** sys_dept使用parent_id邻接表，外键保证父级存在，名称全称用于区分分公司同名部门。单根、无环和层级一致由导入规则核对，单靠自引用外键无法保证。

**继续追问** 递归或循环展开应有已访问集合，但遍历不死循环不等于组织图本身合法。

**代码和依据** [S11](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/DepartmentMapper.java#L54) · [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1) · [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1)

### Q072 负责人关联为何不只保存姓名

**建议回答** 原始manager_name可以用于来源核对，实际manager_user_id指向明确员工。负责人匹配先考虑同名及所属组织候选，无法唯一确定时记录问题，不乱关联。

**继续追问** 一个用户可被多个部门引用为负责人，当前表没有负责人唯一约束；不要按“一人只能管一部门”误解ER基数。

**代码和依据** [S05](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/database/database-design.adoc#L1) · [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1) · [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1)

### Q073 部门和用户互相引用怎样建表

**建议回答** 先建部门且暂不加负责人外键，再建带dept_id的用户表，最后ALTER TABLE添加部门负责人到用户的外键。装载时也按依赖顺序处理，避免引用尚不存在对象。

**继续追问** 删除用户时负责人外键SET NULL，用户角色关联可级联清理；服务还负责范围和账号保护。

**代码和依据** [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1) · [S12](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/UserMapper.java#L1) · [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1)

### Q074 清单为何有系统模块功能三级

**建议回答** 同一模块名称可在不同系统重名，功能名称也应结合父模块定位；系统与稳定功能码共同组成权限标识。规范化关系避免单表重复存放系统模块字符串。

**继续追问** 数据库的唯一键是父级内名称组合，不应只给模块name全局唯一。

**代码和依据** [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1) · [S21](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/function_codes.csv#L1)

### Q075 固定功能编号如何维护

**建议回答** function_codes.csv是稳定编号来源，导入按系统模块功能的既定映射取编号。已有功能改名需要维护映射，不按输入行顺序重编号；新增项需显式确认更新编号表。

**继续追问** --update-codes可能在后续步骤失败前已写编号文件，必须查看Git差异，不能只看到导入失败就认为无任何副作用。

**代码和依据** [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1) · [S21](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/function_codes.csv#L1)

### Q076 2154条权限怎样生成

**建议回答** 每个功能点对应固定六动作，因此359个功能形成2154个权限记录；只有角色与这些权限的关联才表达实际授予。目录有动作不意味着每个角色都有该动作。

**继续追问** sys_action的主键是数字ID，动作code有唯一约束；不要把动作文本说成实际主键。

**代码和依据** [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1) · [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1)

### Q077 如何处理权限单元格中的本人和view

**建议回答** 清洗器按已确定语义识别动作与范围，英文view按查看处理并报告混写。经理等角色的动作范围有专门规则，不只照抄单元格所有文字。

**继续追问** 回答时打开parse_cell和grant_scope，说明未知值报错或警告的策略以及源表定位，不能静默丢弃无法识别动作。

**代码和依据** [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1) · [S34](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/tests/unit/test_import_excel.py#L1)

### Q078 员工表某一行有问题会怎样

**建议回答** 员工级错误逐行收集，合法行仍可进入生成结果；重复工号手机号、空姓名或不匹配部门等会记录失败行。程序能生成SQL不等于全量10000人都成功。

**继续追问** 检查报告中的失败、警告和未解析负责人，再与预期数量逐项核对。坏行不应提前占用唯一集合阻挡后续有效行。

**代码和依据** [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1) · [S34](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/tests/unit/test_import_excel.py#L1)

### Q079 没有员工表时的演示回退能正式验收吗

**建议回答** 不能。脚本允许无员工表时生成负责人及演示员工的回退数据，但这不具备正式员工消歧与10000人全量证据。已提供空表与完全未提供表也被区分。

**继续追问** 不要用负责人姓名去重的演示逻辑证明正式员工去重正确。正式运行要明确传入受控员工表。

**代码和依据** [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1) · [S48](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/full-data-verification-2026-10-18.md#L1)

### Q080 初始角色怎样分配

**建议回答** 正式导入根据负责人管理的组织类型分配经理或项目经理等角色，公司领导部门成员按已确认规则分配领导角色，其余没有角色的人默认普通员工。admin单独预置，平台部门管理员规则另处理。

**继续追问** 副职岗位文本不自动升级为负责人角色；一人负责多部门时合并角色，不复制员工。

**代码和依据** [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1) · [S34](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/tests/unit/test_import_excel.py#L1)

### Q081 Python生成的哈希为什么Java能校验

**建议回答** 导入器使用SpringSecurity_v5_8约定的PBKDF2标识，参数为16字节随机盐、310000次HMAC-SHA256和32字节派生结果，以前缀加十六进制保存。Java使用对应兼容编码器，集成测试验证导入哈希能登录。

**继续追问** 有一致参数还要真实交叉测试，不能只比较字符串长度；相同密码每次盐不同，摘要本就不应相同。

**代码和依据** [S18](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/config/SecurityConfig.java#L1) · [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q082 重复导入为何不能比较SQL文件完全相同

**建议回答** 随机盐会改变密码摘要，时间字段也可能不同。应比较排除这些字段后的业务主键、属性与关联快照，核对数量、负责人及授权范围。指纹只能作为快速回归的一部分。

**继续追问** 若比较前忽略了真正业务字段，就会隐藏问题。明确排除规则，关键差异按主键逐项核验。

**代码和依据** [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1) · [S48](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/full-data-verification-2026-10-18.md#L1)

### Q083 导入SQL是增量更新吗

**建议回答** 当前render_sql生成TRUNCATE后重装十表的全量重建脚本，会丢弃已有业务修改。它不是数据库迁移器，也不能依靠普通事务回滚恢复TRUNCATE操作。

**继续追问** 执行前备份并确认目标空库或专用环境；MySQL官方说明TRUNCATE属于会隐式提交的DDL。

**代码和依据** [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1) · [S41](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1) · [R02](https://dev.mysql.com/doc/refman/8.4/en/truncate-table.html)

### Q084 V001命名是否表示已用Flyway

**建议回答** 不是。当前文件采用版本化命名，首次空MySQL数据卷通过容器初始化挂载执行V001，仓库没有因此自动集成Flyway。已有数据卷不会自动重放初始脚本。

**继续追问** 后续结构升级需明确新增迁移、记录执行与恢复方案，不能通过删除数据库卷来升级。

**代码和依据** [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1) · [S37](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/compose.yaml#L1) · [S41](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1)

### Q085 v_user_permission能直接负责全部鉴权吗

**建议回答** 不能。视图汇总启用用户和角色的清单码及最大范围，主要服务核对。会话凭据、首次改密、派生管理码、具体对象与写入保护仍由Java业务处理。

**继续追问** 视图结果准确也不证明调用方没有绕过范围检查。以业务入口的授权链为准。

**代码和依据** [S05](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/database/database-design.adoc#L1) · [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1)

### Q086 一万员工的性能是否已经达标

**建议回答** 仓库有来源明确的历史SQL核对与测量，不能直接推定当前HTTP和真实COMMIT达到目标。部分findAll后Java分页可能有全量读取与N加一查询风险，应在真实规模下测量定位。

**继续追问** 先用执行计划和端到端指标找到瓶颈，再讨论索引、SQL下推、批量查询与缓存，避免凭数量或本机毫秒数下结论。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S47](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/scripts/measure_full_db_perf.ps1#L1) · [S48](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/full-data-verification-2026-10-18.md#L1)

### Q087 成员一如何回应别人对数据正确性的质疑

**建议回答** 展示源表规则、稳定编号、失败报告、核对SQL和对应自动测试，解释111与10001、3549与3555的来源。对当前未独立复验的原始资料明确引用历史报告及版本。

**继续追问** 老师可让你追一名经理或一个功能点：从源行到部门ID、用户角色、权限记录和最终范围完整说明。

**代码和依据** [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1) · [S21](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/function_codes.csv#L1) · [S34](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/tests/unit/test_import_excel.py#L1) · [S48](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/full-data-verification-2026-10-18.md#L1)

## 第六章 成员二陈清香的前端基础方向

主讲登录 公共会话 路由 用户和组织页面

### Q088 Vue界面通过哪些模块组织

**建议回答** views承载页面，router定义入口与元数据，stores/auth维护模拟会话，policy-adapter对接共享权限策略，api/mock模拟数据读写，fixtures保存合成目录。公共状态与页面局部草稿分开。

**继续追问** 老师问真实API客户端时，说明当前没有完成Vue到Java的替换链路，不能把mock的异步方法称为真实网络请求。

**代码和依据** [S22](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/api/mock.js#L1) · [S23](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/router/index.js#L1) · [S24](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/stores/auth.js#L1) · [S27](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/UsersView.vue#L1)

### Q089 为什么使用reactive和ref

**建议回答** auth是多字段共享会话对象，reactive便于整体响应；页面的loading、选中对象和Map草稿使用ref等局部状态。computed表达派生结果，避免多个手动维护的权限副本。

**继续追问** 响应式只让界面更新，不提供安全性或后端一致性。引用被错误共享仍可能互相修改。

**代码和依据** [S24](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/stores/auth.js#L1) · [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1) · [S27](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/UsersView.vue#L1)

### Q090 登录成功后页面如何更新身份

**建议回答** auth模块把当前用户、角色、授权、范围和必须改密标记整体替换，避免账号切换沿用上一个人的权限。原型只在sessionStorage保留模拟用户编号，不保存口令。

**继续追问** 篡改模拟编号只能说明原型不是真实认证；真实后端会话身份来自服务器，不能使用这个存储方案直接上线。

**代码和依据** [S22](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/api/mock.js#L1) · [S24](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/stores/auth.js#L1)

### Q091 为什么路由守卫先恢复再刷新

**建议回答** 刷新页面后先恢复模拟身份，再从当前数据重新取得权限，最后判断目标路由。若先判权限会误认为未登录或沿用旧权限；首次改密检查也要在业务路由放行前完成。

**继续追问** 异步守卫失败应回到明确的登录或拒绝状态，不能为了避免闪烁直接放行受保护页面。

**代码和依据** [S23](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/router/index.js#L1) · [S24](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/stores/auth.js#L1)

### Q092 sessionOnly是不是不需要登录

**建议回答** 不是。它表示该页面不要求具体业务权限，但仍需登录状态和适用的改密守卫。登录页public规则与普通业务权限规则不同，不能混为一项。

**继续追问** 首次改密账户可走的真实API也由后端current与ready区分；前端路径不能决定服务端权限。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S23](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/router/index.js#L1) · [S25](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/authorization/rbac0-policy.js#L1)

### Q093 直接输入受保护URL怎么办

**建议回答** 路由守卫仍会读取目标页面要求的权限并决定重定向，功能点页还根据真实目录编号取得对应read码。未找到页面和没有权限要给清楚状态。

**继续追问** 这只能证明交互层不会轻易进入页面；真实API仍须在请求绕过路由时独立拒绝。

**代码和依据** [S23](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/router/index.js#L1) · [S25](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/authorization/rbac0-policy.js#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q094 角色授权页为什么要求多个查看权限

**建议回答** 进入页面需要角色查看与权限目录查看，是否能保存还由独立的role:grant决定。查看页面和修改授权不是同一动作，公司领导等只读身份不应出现可写状态。

**继续追问** 前端必须与后端rolePermissions的双查看检查对应；按钮禁用不能代替服务校验。

**代码和依据** [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S23](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/router/index.js#L1) · [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1)

### Q095 筛选后为何要回到第一页

**建议回答** 筛选改变结果集，保留旧页码可能展示空页或漏掉匹配项。页面应先重置页码再查询，统计和当前页结果基于同一过滤条件。

**继续追问** 这不等于后端已做数据库分页；当前真实用户列表先做范围和筛选再Java分页。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S27](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/UsersView.vue#L1)

### Q096 空列表和加载失败为何区分

**建议回答** 空列表是成功查询且结果为零，失败是未取得可信数据。若把失败显示成空集合，用户可能误以为无授权甚至保存覆盖。loading、错误、空态和已加载数据需要明确区分。

**继续追问** 授权页失败时会禁用编辑并清旧授权；其他页面也应逐页核对，不能泛称全部页面已覆盖所有异步边界。

**代码和依据** [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1) · [S27](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/UsersView.vue#L1) · [S28](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RolesView.vue#L1)

### Q097 表单校验可以代替服务端校验吗

**建议回答** 不能。前端校验提供即时反馈，用户可修改请求绕过。DTO限制格式长度，Service验证权限与业务关系，数据库约束处理并发完整性，三者作用不同。

**继续追问** 举普通资料表单多传roleIds或password，应由后端未知字段策略拒绝，不应被默默绑定到实体。

**代码和依据** [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1) · [S16](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/error/GlobalExceptionHandler.java#L1) · [S27](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/UsersView.vue#L1)

### Q098 用户创建为何不能在一个表单里顺便授角

**建议回答** 新建基本账号和授角是不同权限与风险。真实CreateUser不接受角色或任意初始密码，新账号使用环境初始密码并要求改密，角色分配要另带版本控制。

**继续追问** 如果原型交互提供额外操作，应明确调用的是独立授权流程，不绕过服务端边界。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1) · [S27](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/UsersView.vue#L1)

### Q099 页面切换身份需要清哪些状态

**建议回答** 共享身份、有效权限、范围和改密标记要整体替换，局部列表选中项、授权草稿和在途请求也不能沿用。权限编辑页已有身份变化监听及请求失效处理。

**继续追问** 不能仅换头像和姓名。老师可用管理员打开页面后切普通员工，检查旧数据和按钮是否残留。

**代码和依据** [S24](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/stores/auth.js#L1) · [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1) · [S28](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RolesView.vue#L1)

### Q100 组织页的树与成员表如何配合

**建议回答** 组织树显示合法节点关系，选中节点后成员查询应应用调用者的数据范围和下级部门规则。树可见并不自动表示该部门所有人员可见。

**继续追问** 展示直接员工数和含下级人数时解释统计口径，不把二者混用。真实后端与mock分别核对。

**代码和依据** [S11](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/DepartmentMapper.java#L54) · [S30](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/DepartmentsView.vue#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q101 为什么刷新深层页面可能404

**建议回答** Vue使用history路由，服务器收到深层URL时需要回退index.html交给前端匹配。Nginx对页面路由做回退，对API、静态契约和资源文件则保持真实404。

**继续追问** 不能用统一SPA回退吞掉API错误，否则拿到200 HTML会误判接口成功。

**代码和依据** [S23](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/router/index.js#L1) · [S38](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/nginx.conf#L1)

### Q102 为什么构建成功仍要在浏览器检查

**建议回答** 构建只证明依赖和打包过程完成，运行时导出适配、路由和交互仍可能出错。项目曾修复CommonJS与全局导出配合问题，所以要检查实际生产入口和控制台。

**继续追问** 只在测试中手动注入全局对象会掩盖真实ESM导入问题，应该覆盖实际适配器路径。

**代码和依据** [S25](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/authorization/rbac0-policy.js#L1) · [S33](https://github.com/wenjiu-leng66/RBAC-Permission-System/tree/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/tests/frontend) · [S51](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/contribution-review-2026-10-27.md#L1)

### Q103 原型刷新后数据为何恢复

**建议回答** 业务数据保存在浏览器进程内存，刷新重新初始化；sessionStorage仅保存模拟身份。此行为是原型边界，真实持久化由Java和MySQL链路另验收。

**继续追问** 向老师说明哪个保存是原型内存，哪个是真实HTTP写入，避免演示时产生错误承诺。

**代码和依据** [S22](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/api/mock.js#L1) · [S24](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/stores/auth.js#L1) · [S41](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1)

### Q104 前端包超过一兆意味着项目不能运行吗

**建议回答** 构建报告中的大块提示是体积与加载性能风险，不等于构建失败。当前静态引入多页面和组件，可考虑按路由懒加载与依赖拆分，但必须再检查权限守卫与交互不受影响。

**继续追问** 不能只提高警告阈值冒充优化，也不能凭体积直接推算加载耗时；还要看压缩、网络和缓存。

**代码和依据** [S23](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/router/index.js#L1) · [S36](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/test-report-2026-10-27.md#L1)

### Q105 成员二如何向组长说明前后端联调还缺什么

**建议回答** 列出需要替换的mock调用、真实Cookie携带、字段与分页适配、错误包络、首次改密、权限刷新和保存冲突处理；使用契约与受控测试账号做端到端验证。

**继续追问** 不能简单把baseURL改成服务器就宣称联调完成，现有原型API与真实接口可能存在返回结构和演示辅助方法差异。

**代码和依据** [S22](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/api/mock.js#L1) · [S24](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/stores/auth.js#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1) · [S40](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/scripts/smoke_http.py#L1)

## 第七章 成员三杨昕桐的权限前端方向

主讲共享策略 角色授权 草稿和复杂交互边界

### Q106 前端共享权限策略的价值是什么

**建议回答** 同一套策略集中处理菜单、路由、按钮、角色并集和数据范围，减少各页面自己拼规则导致不一致。适配层把Vue状态转换为策略需要的结构，策略本身便于Node测试。

**继续追问** 共享的是前端展示逻辑，Java后端仍独立实现真实安全判定，不能把浏览器策略当唯一权限中心。

**代码和依据** [S25](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/authorization/rbac0-policy.js#L1) · [S23](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/router/index.js#L1) · [S33](https://github.com/wenjiu-leng66/RBAC-Permission-System/tree/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/tests/frontend)

### Q107 权限尚未加载时为什么默认拒绝

**建议回答** 身份已登录不代表授权已可信取得。permissionsLoaded等状态不足或权限规则非法时，策略拒绝受保护入口，避免加载期间短暂放行。

**继续追问** 空权限成功加载与加载失败都不应获得默认权限，但界面应显示不同说明。

**代码和依据** [S25](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/authorization/rbac0-policy.js#L1) · [S24](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/stores/auth.js#L1)

### Q108 为什么只按精确权限码匹配

**建议回答** 权限码代表一个明确功能动作，前缀相似不能自然形成授权。除非契约另有通配符语义，否则使用includes或字符串前缀放行会造成跨动作或跨资源权限扩大。

**继续追问** 同样不能把展示名称“管理员”当授权来源；以当前角色和具体码计算。

**代码和依据** [S25](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/authorization/rbac0-policy.js#L1) · [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16)

### Q109 有效授权为何保存sources

**建议回答** 同一权限可能由多个角色提供，合并后保留来源角色和原scope便于解释权限从哪里来。最终scope按同一码取最大值，来源不会使不同码的范围互相扩散。

**继续追问** 撤销一个来源后，若另一个启用角色仍提供该码，权限仍存在；适合展示角色并集而非权限独占。

**代码和依据** [S25](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/authorization/rbac0-policy.js#L1) · [S22](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/api/mock.js#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q110 为什么授权草稿必须覆盖完整集合

**建议回答** 保存接口是全集合替换。如果只发送当前页或当前筛选结果，会把不可见页授权误删。draft Map保存所有已选码与范围，分页只影响展示，保存序列化整张Map。

**继续追问** 老师可让你第1页勾选、第2页取消再筛选，检查diff和最终payload是否包含完整选择。

**代码和依据** [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1)

### Q111 original和draft为何不能是同一个Map

**建议回答** original代表最后成功读取或保存的快照，draft是编辑副本。共用引用会让修改草稿同时改掉基准，导致取消无效和差异统计错误；new Map能隔离两者。

**继续追问** Map便于按权限码去重与查询，但仍要在后端拒绝重复或未知授权，前端结构不是安全保证。

**代码和依据** [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20)

### Q112 computed与watch在授权页各负责什么

**建议回答** computed根据过滤条件和Map计算可见行、差异及只读状态；watch在筛选变化时重置页码、系统切换时清模块筛选、身份变化时失效旧草稿。派生结果和副作用分开表达。

**继续追问** 不要在computed里发保存请求或改共享状态；老师可问为什么页面变化触发多次加载，应追具体watch和请求号。

**代码和依据** [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1)

### Q113 勾选编辑为何自动勾查看

**建议回答** 这是read依赖在交互层的实现，降低用户提交无效组合的概率。取消read时一并撤销该功能的其它动作，后端仍通过READ_REQUIRED检查输入。

**继续追问** 自动勾read的默认范围与用户编辑的scope可以不同；不要声称前端已经强制所有动作范围相等。

**代码和依据** [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20)

### Q114 保存按钮为什么有这么多禁用条件

**建议回答** 读取失败、正在加载、角色上下文不匹配、无grant权限、ADMIN只读或正在保存时，编辑上下文不可信或重复提交风险高。组合禁用减少对旧快照写入的机会。

**继续追问** 禁用是用户体验，真实后端必须再次判断ADMIN保护、权限与version。

**代码和依据** [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20)

### Q115 快速切换角色为什么可能出现串数据

**建议回答** 旧角色请求可能晚于新角色请求返回。授权页通过请求序号、发起身份、目标角色和组件存活状态共同判断，只接受当前上下文响应。

**继续追问** 仅loading布尔值不解决竞态；取消网络请求也不能替代响应归属检查。其它页面需分别验证。

**代码和依据** [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1) · [S28](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RolesView.vue#L1)

### Q116 离开页面为什么要确认未保存内容

**建议回答** 未保存的草稿属于用户当前意图，路由切换、角色切换和刷新都可能丢失。授权页统一确认，保存中暂不允许离开，防止用户不知道请求结果。

**继续追问** beforeunload只能请求浏览器提示，不能保证文案或持久保存；不能把它当草稿备份机制。

**代码和依据** [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1)

### Q117 409冲突时页面具体做了什么

**建议回答** 后端拒绝旧版本且不写入，页面提示授权已被他人修改，确认后重新加载最新集合和版本，再由用户复核。当前实现没有自动把旧草稿与新集合合并。

**继续追问** 不能说“再次点保存就会强制覆盖”；若要保留和比较冲突草稿，需要另设计三方差异与确认流程。

**代码和依据** [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q118 500保存失败能直接自动重试吗

**建议回答** 不应盲目重试，因为网络或服务端错误时客户端可能不知道事务是否提交。当前授权页保留草稿并尝试回读服务器集合，提示复核；真实环境还要核对版本和业务结果。

**继续追问** 当前回读比较主要看授权内容，不能由“内容一样”推出版本和全部副作用都未改变。模拟失败也不等于真实网络不确定提交。

**代码和依据** [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q119 模拟失败按钮证明了数据库回滚吗

**建议回答** 没有。原型的模拟失败发生在内存赋值前，主要验证错误提示和草稿保留。数据库原子性由真实Service、JDBC写路径失败注入和事务测试证明，MySQL还需实际环境验证。

**继续追问** 明确三个证据层次：前端失败交互、H2事务回滚、MySQL与部署。不能混用。

**代码和依据** [S22](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/api/mock.js#L1) · [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q120 为什么ADMIN在授权页只读

**建议回答** 服务返回readOnly标记，页面还检查实际grant权限。ADMIN授权受到服务端保护，前端只读把该规则明确呈现给用户，避免无意义提交。

**继续追问** 伪造readOnly=false或直接调用PUT仍应得到服务端拒绝；现场可解释双重检查。

**代码和依据** [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1)

### Q121 读取失败时为何要先清旧授权

**建议回答** 旧角色或旧身份数据不能误当成本次读取结果。clearGrants清版本和快照，失败状态阻止编辑，避免把“没读到”当成“当前角色授权为空”保存出去。

**继续追问** 老师问为什么不显示旧数据时可答：可以另设计只读旧缓存，但必须明确标记过期，不能继续当可信基线。

**代码和依据** [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1)

### Q122 角色编码和角色ID为什么不混用

**建议回答** 编码是角色业务标识，ID是当前数据集关系键。原型里可能使用R05等合成ID，真实API使用数字字符串ID；名称可改而稳定编码一般不改。

**继续追问** 联调时逐字段适配，不能把演示R05直接传到真实后端，也不能把数组位置当角色ID。

**代码和依据** [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1) · [S22](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/api/mock.js#L1)

### Q123 CJS全局导出问题为何能让生产原型失败

**建议回答** 旧策略文件在CommonJS分支只写module.exports，适配器却从globalThis读取，实际打包环境可能找不到策略。当前修复让导出路径同时可用，并加入真实ESM适配器导入回归。

**继续追问** 不要靠测试手动设置globalThis掩盖运行时差异；解释测试怎样覆盖真实入口。

**代码和依据** [S25](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/authorization/rbac0-policy.js#L1) · [S33](https://github.com/wenjiu-leng66/RBAC-Permission-System/tree/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/tests/frontend) · [S51](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/contribution-review-2026-10-27.md#L1)

### Q124 继承和约束页为什么不能参与当前授权计算

**建议回答** 后续页面的stage元数据只标示规划，不把未实现的继承边、SSD或DSD加到一期角色并集。否则前后端会产生不可解释的权限差异。

**继续追问** 未来接入时要同步模型、持久化、算法、界面来源解释和拒绝测试，不能只改页面树。

**代码和依据** [S23](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/router/index.js#L1) · [S25](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/authorization/rbac0-policy.js#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1)

### Q125 成员三怎样判断前端权限功能真正可用

**建议回答** 覆盖已加载与未加载、空角色、多角色同码、禁用角色、read依赖、全量草稿、跨页、取消、409、500、切换身份和过期响应，再在生产构建真实打开主要路径。

**继续追问** 单元测试通过之外，记录浏览器版本、构建版本与控制台；当前报告的浏览器证明只覆盖记录列出的路径。

**代码和依据** [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1) · [S33](https://github.com/wenjiu-leng66/RBAC-Permission-System/tree/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/tests/frontend) · [S36](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/test-report-2026-10-27.md#L1)

## 第八章 成员四王信凯的功能后端方向

主讲认证 授权 用户维护 事务及边界代码

### Q126 一次真实请求经过哪些步骤

**建议回答** 请求经Nginx代理到Spring MVC，Controller绑定DTO并做格式校验，Service确认身份和动作权限，再检查范围与业务保护，Mapper执行参数化SQL，最后显式组织响应或统一错误。写集合的事务包住版本和关系替换。

**继续追问** 选一个方法逐行讲，不要只背Controller Service Mapper三个词。匿名、无权和越界要分别定位。

**代码和依据** [S04](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/structured-design.md#L1) · [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1)

### Q127 当前是否使用完整Spring Security鉴权链

**建议回答** POM只引入spring-security-crypto用于密码编码，没有配置完整的Spring Security FilterChain。身份和授权通过HttpSession及业务服务内的AuthorizationService调用完成，来源过滤另行配置。

**继续追问** 新增接口若漏调授权辅助方法会产生风险，因此匿名和越权测试很重要。不能说“加了Spring Security依赖所以所有接口自动安全”。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S17](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/config/BrowserSecurityConfig.java#L1) · [S18](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/config/SecurityConfig.java#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1) · [S53](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/pom.xml#L1)

### Q128 current ready require target如何区别

**建议回答** current确认服务端会话用户仍有效；ready再限制必须改密状态；require要求一个具体权限并返回它的范围；target继续查找员工对象并检查范围。不同入口按需求组合调用。

**继续追问** 改密用current避免把首次改密用户挡在改密接口外；普通业务应使用ready或require。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S08](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthService.java#L18)

### Q129 为什么每次请求还要查用户和角色

**建议回答** 账号停用、密码重置、角色停用和授权调整都需要在下一请求生效。会话只保留身份和凭据标记，不缓存有效授权集合；每次读当前数据库状态。

**继续追问** 代价是查询负担；未来缓存要设计权限版本和失效策略，不能无条件缓存整个会话生命周期。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S08](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthService.java#L18)

### Q130 登录失败为什么清除已有身份

**建议回答** 同一会话尝试另一个账号登录失败时，不能继续保留上一个账号权限。AuthService先去掉身份和凭据属性，再统一验证工号或手机号与密码。

**继续追问** 不存在、停用和错误密码使用相同凭据错误；不存在账号也走一次慢哈希，但不能声称完全消除计时侧信道。

**代码和依据** [S08](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthService.java#L18) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q131 登录成功在哪里换sessionId

**建议回答** AuthController在auth.login成功后调用request.changeSessionId，保留服务端身份属性同时轮换标识，降低固定会话攻击风险。失败登录不执行成功后的轮换。

**继续追问** 用AuthControllerTest和手机登录集成用例说明调用时机；不要把sessionId当用户ID或权限令牌。

**代码和依据** [S08](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthService.java#L18) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1) · [S54](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/controller/AuthController.java#L1)

### Q132 Session中为什么保存密码摘要标记

**建议回答** 当前实现用登录时摘要作为凭据版本标记，与数据库当前摘要比较以识别改密或重置。它只在服务端Session中，不返回JSON或Cookie正文。

**继续追问** 这有实现耦合，未来可用独立credential_version；不能把这个规划字段画成现有数据库列。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S08](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthService.java#L18) · [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1)

### Q133 密码为什么不用简单SHA256

**建议回答** 快速哈希不适合直接抵抗口令穷举。项目用带随机盐和成本参数的PBKDF2，导入与后端保持一致，密码摘要带算法前缀以支持历史格式校验。

**继续追问** 明文密码、哈希和盐都不应放进公开演示材料。旧格式兼容校验不等于登录后已自动迁移。

**代码和依据** [S18](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/config/SecurityConfig.java#L1) · [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1)

### Q134 为什么改掉了新密码的BCrypt写入

**建议回答** 项目允许8到128字符密码，需要保留完整输入。当前新摘要采用PBKDF2，避免BCrypt传统72字节限制带来的长尾差异问题，并有长密码和Unicode集成测试。

**继续追问** 兼容器仍能验证历史带前缀BCrypt摘要；upgradeEncoding声明升级需求，但当前登录没自动重写旧摘要。

**代码和依据** [S18](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/config/SecurityConfig.java#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q135 用户资料修改为什么检查原目标和新部门

**建议回答** 先确认操作者对原员工有update权限及范围，再检查迁入部门存在且属于同一写动作范围，防止把范围内员工移动到范围外。DTO也不允许顺便改工号、密码或角色。

**继续追问** 只检查旧记录会漏掉目标部门越界，只检查新部门又会允许操作本来无权触碰的人。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1)

### Q136 账号保护具体保护了哪些操作

**建议回答** 停用、删除和改角色前拒绝当前本人、用户名admin或持有ADMIN的目标；ADMIN也不能由普通授角接口分配给其他人。普通资料更新和密码重置有各自规则，不能一概禁止。

**继续追问** 老师问“最后一个管理员”时说明当前采用更强保护，而不是通过剩余数量动态决定。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q137 部门管理员怎样被防止提权

**建议回答** 对用户操作先裁剪DEPT范围；授角时计算新增和撤销角色的对称差，非超级管理员不能改变管理类角色。管理类识别包括内置ADMIN DEPT_ADMIN以及含管理清单授权的自建角色。

**继续追问** 只按角色名字识别会被复制权限换名绕过。撤销管理角色也属受保护变更，不只新增要查。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q138 角色授权为什么采用PUT整组替换

**建议回答** 客户端提交当前目标全集及读取时版本，服务在事务中验证并替换旧关联。语义明确，可表达全部撤销；版本用于避免管理员基于旧快照静默覆盖别人的修改。

**继续追问** 它不是单项追加接口。空grants表示清空允许清空的授权，但ADMIN保护和read依赖仍须检查。

**代码和依据** [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S13](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1)

### Q139 乐观锁version究竟是什么

**建议回答** 用户角色集合对应sys_user.role_version，角色权限集合对应sys_role.permission_version。条件UPDATE只有id和旧version都匹配才递增，影响零行视为冲突并返回409。

**继续追问** 它不是密码版本、资料版本或全局权限快照。角色启停改变有效权限也不意味着必须改变用户分配集合版本。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S12](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/UserMapper.java#L1) · [S13](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java#L1)

### Q140 有version为什么还需要行锁

**建议回答** version保护客户端基于旧快照的编辑意图；角色行锁协调授角与角色删除等并发业务，避免检查与删除之间穿透；事务保证版本与关联写入原子提交。三者处理的问题不同。

**继续追问** 不能说锁与CAS重复，也不能说@Transactional自然防止所有丢失更新。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S12](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/UserMapper.java#L1) · [S13](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java#L1)

### Q141 为什么角色锁要按数字ID排序

**建议回答** 用户授角取原新角色并集并按数字ID升序锁定，使并发流程尽量遵循一致顺序，降低交叉持锁风险。角色删除也使用共同角色行锁协调。

**继续追问** 字典序会把10排在2前面，因此代码按数字比较；固定顺序仍不证明全库所有流程绝无死锁。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S13](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java#L1)

### Q142 READ_COMMITTED在这里解决什么

**建议回答** 授权集合与角色删除事务显式声明READ_COMMITTED，配合行锁后重新读取角色和成员状态，避免把MySQL默认可重复读快照误当当前结果。冲突返回的版本还使用锁定读取得当前值。

**继续追问** 不能把这个方法级隔离级别推广到全部业务方法；其它事务取决于运行配置。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S12](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/UserMapper.java#L1) · [S13](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java#L1)

### Q143 角色删除如何处理确认竞态

**建议回答** 服务先锁角色，再读取引用用户；内置角色拒绝删除，自建角色仍有用户而未confirm时返回409。确认删除在同一事务中清理关系并递增受影响用户role_version。

**继续追问** 如果另一管理员刚授予该角色，共享锁规则要求删除检查读到协调后的状态；H2并发用例之外仍需MySQL复验。

**代码和依据** [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S13](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q144 替换过程中删完旧授权再失败怎么办

**建议回答** Mapper的删除与插入必须在外层Service事务内执行，失败连同版本递增一起回滚。失败注入用例断言旧授权集合及版本均不变，不能只断言HTTP报错。

**继续追问** 单独调用replaceGrants没有完整事务保证；不要把Mapper方法注释当成自动事务。

**代码和依据** [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S13](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q145 为什么冲突响应要带currentVersion

**建议回答** 客户端可以知道自己草稿已过期，需要重新读取并复核，而不是静默覆盖。读取当前版本使用锁定查询，避免在旧快照上返回仍旧的版本值。

**继续追问** currentVersion只是辅助信息，不代表客户端可以直接换版本原样重提；必须处理别人已修改的内容。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S12](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/UserMapper.java#L1) · [S13](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java#L1)

### Q146 如何避免SQL注入和批量赋值

**建议回答** Mapper用JdbcTemplate参数绑定，不把输入拼入SQL语句结构；DTO列出允许字段，Jackson配置拒绝未知JSON，Views显式选择返回内容。三项分别约束SQL、写入字段与输出字段。

**继续追问** Bean Validation无法单独解决SQL注入；字符串ID也不是天然安全。

**代码和依据** [S12](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/UserMapper.java#L1) · [S13](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java#L1) · [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1) · [S15](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/vo/Views.java#L1) · [S16](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/error/GlobalExceptionHandler.java#L1)

### Q147 为什么参数错用400而冲突用409

**建议回答** 400表示请求格式、值域或授权组合不合法；401是身份无效，403是无权或目标范围拒绝，409用于旧版本、受保护对象或唯一约束冲突。状态与error.code共同表达具体问题。

**继续追问** 用户或台账目标不存在也统一OUT_OF_SCOPE 403以降低枚举风险；角色等资源可以按契约返回404，不能全项目一刀切。

**代码和依据** [S16](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/error/GlobalExceptionHandler.java#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1) · [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16)

### Q148 异常处理有没有泄露数据库细节

**建议回答** GlobalExceptionHandler把已知约束名映射到固定错误码，意外异常服务端记录，客户端只收到通用500。错误包络包含requestId便于报告定位，不回传原始SQL或堆栈。

**继续追问** 当前requestId是错误响应标识，不等于已经建立完整分布式链路追踪或业务审计。

**代码和依据** [S16](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/error/GlobalExceptionHandler.java#L1) · [S15](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/vo/Views.java#L1)

### Q149 为什么ID用字符串又限制为正INT

**建议回答** 契约统一字符串避免前端数字表示和类型混杂，实际数据库主键仍是正INT。Ids.valid先校验格式长度与Integer上界，避免任意长ID变成JDBC转换异常。

**继续追问** DTO正则与Mapper上界各有检查位置；不要说字符串可以无限大，也不要把演示R05当真实数据库ID。

**代码和依据** [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1) · [S55](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/Ids.java#L1) · [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q150 健康检查UP究竟证明什么

**建议回答** HealthController先检查应用接受流量状态，再执行SELECT 1，成功返回UP；数据库失败会走错误响应。它证明当时的基本就绪与数据库连接，不证明业务权限、事务或负载性能全部正确。

**继续追问** Nginx的nginx-health只证明静态服务自己可响应；两种探针边界不同。

**代码和依据** [S54](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/controller/AuthController.java#L1) · [S38](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/nginx.conf#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q151 来源白名单能否代替身份认证或完整CSRF防护

**建议回答** 不能。CORS限制浏览器跨源读取及携带凭据，Origin Guard检查显式来源的写请求；没有Origin的非浏览器请求仍进入业务鉴权。项目没有完整CSRF令牌方案。

**继续追问** 同源比较依赖scheme Host及可信代理配置；生产Compose显式空来源，不继承本地开发白名单。

**代码和依据** [S17](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/config/BrowserSecurityConfig.java#L1) · [S37](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/compose.yaml#L1)

## 第九章 成员五胡俊朗的面向对象与设计方向

区分当前参与的设计工作 与尚未落地的独立OO后端

### Q152 成员五应该怎样说明当前工作边界

**建议回答** 责任规划包括领域模型、类职责、时序设计及后续OO和函数式后端；当前backend-oo尚无可运行构建。应结合实际提交说明自己做过的设计评审或协作代码，不能把所有RoleService实现自动认作本人产物。

**继续追问** 建议展示一张按实际后端校订的模型图和一条事务测试，再明确二期独立实现计划。

**代码和依据** [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1) · [S44](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/uml/class-responsibility.md#L1) · [S45](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/uml/domain-model.md#L1) · [S49](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/planning/team.md#L1) · [S51](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/contribution-review-2026-10-27.md#L1)

### Q153 面向功能和面向对象根本差异是什么

**建议回答** 面向功能主要按处理任务与数据流分解，规则通过用例步骤组合；面向对象更强调领域对象职责、协作与不变量。二者都可用Java类和分层，判断不能仅看文件后缀或类的数量。

**继续追问** 用同一授权保存用例分别说明“功能服务串流程”和“领域策略维护规则”怎样组织，比较可测试性和变化影响。

**代码和依据** [S04](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/structured-design.md#L1) · [S06](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/detailed-design.adoc#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1)

### Q154 当前PO是不是领域实体

**建议回答** 当前PO主要是数据库行的数据载体，核心规则集中在Service，不因名叫UserPO就拥有完整用户领域行为。领域实体应有身份、生命周期及与自身职责匹配的不变量。

**继续追问** 也不能为了OO把全部SQL塞进实体。应用编排、领域规则与基础设施仍应有边界。

**代码和依据** [S06](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/detailed-design.adoc#L1) · [S44](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/uml/class-responsibility.md#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1)

### Q155 以后怎样划分OO后端层次

**建议回答** 规划可分controller、application、domain/model、domain/policy和infrastructure。应用层协调用例与事务，领域层表达角色授权等规则，基础设施实现持久化；对外仍遵守相同API契约。

**继续追问** 这是后续设计方案，不是当前已运行类结构。先定义哪些规则是纯计算、哪些需要数据库事实。

**代码和依据** [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1)

### Q156 权限计算适合哪种函数式设计

**建议回答** 可以把用户角色、角色授权和组织关系作为不可变快照输入，输出有效权限与拒绝原因，计算过程不访问数据库、不修改共享状态。读取快照和写回事务在边界处理。

**继续追问** 现有AuthorizationService直接读Mapper，不是完整纯函数；Java Stream语法本身也不等于函数式架构。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S25](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/authorization/rbac0-policy.js#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1)

### Q157 不可变对象解决了哪些问题

**建议回答** 不可变输入降低隐藏修改和共享引用风险，结果更容易复现与测试。Record可表达数据快照，但记录中若含可变集合仍需防御性拷贝。

**继续追问** 不可变对象不提供数据库并发一致性，集合保存仍需事务、锁和版本控制。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1)

### Q158 角色继承为什么必须是有向无环图

**建议回答** 后续senior继承junior权限，允许多父级会形成有向图。环会使层级语义和遍历结果难以解释，因此新增边需检查可达性，删除边需重新计算受影响权限。

**继续追问** 当前没有继承边表和服务算法，不能演示一个树形UI就宣称完成。多父图也不能简单存单个parent_id。

**代码和依据** [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1) · [S45](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/uml/domain-model.md#L1) · [S02](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/requirements/req-spec.md#L1)

### Q159 SSD和DSD分别在什么时机检查

**建议回答** SSD约束用户分配角色集合，禁止静态互斥角色共存；DSD允许同时分配但限制某个会话的激活集合。它们的约束对象、写入入口和并发边界不同。

**继续追问** 本期所有启用已分配角色直接生效，没有独立激活子集。以上是后续规则设计，不计作现有拒绝能力。

**代码和依据** [S02](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/requirements/req-spec.md#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1) · [S23](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/router/index.js#L1)

### Q160 基数约束为什么不只在页面计数

**建议回答** 多人同时授角时都可能看到剩余一个名额，前端计数无法防止并发突破上限。后端需要在受控事务中检查与更新计数或锁定相关约束对象。

**继续追问** 当前未实现基数约束表和事务方案；提出方案时说明锁范围、失败反馈和性能代价。

**代码和依据** [S02](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/requirements/req-spec.md#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1)

### Q161 应该用继承还是组合表达不同范围策略

**建议回答** SELF DEPT ALL可作为独立策略或明确枚举分支，选择依据是规则数量、变化频率和可理解性。当前switch实现直接清楚，后续增加复杂目标类型时再考虑策略组合。

**继续追问** 不要为了展示设计模式增加无业务意义的层次；策略接口要能说明测试替身与替换收益。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1)

### Q162 类图和数据库ER图为什么不能完全一样

**建议回答** ER关注数据关系和约束，类图关注职责、行为和对象协作。关联表可能在领域中表现为值对象或集合，服务策略也未必对应数据库表。

**继续追问** 当前Mapper PO图是实现结构，不等同于已经完成的丰富领域模型；图名和范围要准确。

**代码和依据** [S05](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/database/database-design.adoc#L1) · [S44](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/uml/class-responsibility.md#L1) · [S45](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/uml/domain-model.md#L1)

### Q163 领域对象怎样保护授权集合不变量

**建议回答** 后续可由集合或策略对象校验未知码、重复码、read依赖及范围值域，输出完整有效变更；应用层再协调版本与事务。当前这些校验在RoleService中。

**继续追问** 校验依赖的目录快照必须有可信版本，不能把过期快照结果无条件写入。

**代码和依据** [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1)

### Q164 怎样保证两套后端外部一致

**建议回答** 使用统一OpenAPI和相同独立测试预期，分别装载等价数据状态，对正常、越权、范围、版本冲突和错误体进行对照。业务实现独立，不通过调用另一套核心服务达成表面一致。

**继续追问** 只比较两套输出相等可能共同犯错，因此需要独立的需求预期和数据库不变断言。

**代码和依据** [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1) · [S03](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/requirement-coverage.md#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1)

### Q165 为什么H2通过仍要验证MySQL事务

**建议回答** H2与MySQL在锁、隔离、DDL、约束错误和SQL方言上可能不同。当前测试证明给定测试环境中的逻辑和原子性，生产数据库语义要用专用MySQL复验。

**继续追问** 老师问并发测试时明确用了哪些线程同步与断言，不把H2一次成功说成所有数据库无死锁。

**代码和依据** [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1) · [S36](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/test-report-2026-10-27.md#L1) · [S41](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1)

### Q166 代码使用Stream有哪些好处与局限

**建议回答** 过滤映射和集合合并可以更直观，便于表达无副作用的数据变换；但带数据库查询的map可能形成N加一，捕获可变共享状态也可能产生问题。parallelStream不是自动性能优化。

**继续追问** 现有findAll后流过滤要结合规模测量；不要把“链式写法更短”直接等同更高性能。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S25](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/authorization/rbac0-policy.js#L1)

### Q167 详细设计为何需要写失败路径

**建议回答** 权限不足、目标越界、旧版本、唯一冲突和中途失败决定了安全及一致性。只画成功请求无法证明拒绝时没有写入，也无法指导测试构造。

**继续追问** 可让老师挑任一失败点，说明在哪层抛出、对应HTTP码、事务是否开始以及数据是否应改变。

**代码和依据** [S06](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/detailed-design.adoc#L1) · [S16](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/error/GlobalExceptionHandler.java#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q168 设计文档转换时最容易出现什么问题

**建议回答** 机械转换可能保留过时字段、虚构客户端凭据或审计能力，并让链接与图失效。当前两份权威AsciiDoc依据实际代码和迁移整理，引用已更新，旧长篇快照不直接恢复。

**继续追问** 文档扩展名符合要求之后，还要核对每条业务规则和真实接口。不能用页数替代准确性。

**代码和依据** [S05](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/database/database-design.adoc#L1) · [S06](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/detailed-design.adoc#L1) · [S51](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/contribution-review-2026-10-27.md#L1)

### Q169 成员五如何和成员四互相补充

**建议回答** 成员四主讲当前功能流程和实现，成员五可从职责划分、模型与规则扩展、事务不变量和后续独立领域设计角度补充。共同以当前源码为事实依据，明确设计建议和已实现机制。

**继续追问** 不要一人讲“有继承”另一人讲“无继承”。同一个用例应共享需求和对外契约，内部范式再分别说明。

**代码和依据** [S04](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/structured-design.md#L1) · [S06](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/detailed-design.adoc#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1) · [S49](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/planning/team.md#L1)

## 第十章 成员六孙逸轩的API测试与集成方向

主讲独立预期 契约一致性 覆盖率及证据组织

### Q170 测试负责人是不是负责写全组所有测试

**建议回答** 不是。各模块作者维护本模块测试和设计，成员六统筹需求追溯、独立预期、契约一致性、执行组织及缺陷定位。集成测试需要各方提供清晰输入和预期。

**继续追问** 老师问个人贡献时打开自己真实维护的用例与记录，不按统筹职责认领全部90项。

**代码和依据** [S49](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/planning/team.md#L1) · [S03](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/requirement-coverage.md#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q171 如何从41条需求建立测试

**建议回答** 先提取参与者、前置条件、输入、预期与拒绝边界，映射到代码和API，再选择单元、集成、HTTP或数据核对层。覆盖表是追溯索引，不是41条全部通过的声明。

**继续追问** 一条需求通常需要多条测试，一条测试也可能关联多个规则。不能只按需求数量凑同样数量的测试函数。

**代码和依据** [S03](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/requirement-coverage.md#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q172 单元测试和集成测试怎么区别

**建议回答** 隔离单元测试对单层对象与替身条件验证规则；Stage1ApiTest启动Spring上下文、MVC和H2并执行真实JDBC跨层行为。直接调用Controller方法也不是完整HTTP路由验证。

**继续追问** MockMvc没有经过公网、Nginx和真实MySQL，因此仍要运行真实HTTP和部署检查。

**代码和依据** [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1) · [S32](https://github.com/wenjiu-leng66/RBAC-Permission-System/tree/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/service) · [S36](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/test-report-2026-10-27.md#L1)

### Q173 90项测试由什么组成

**建议回答** 仓库报告记录61项隔离单元测试加29项MockMvc H2整合测试，失败错误跳过均为零。报告按类列出数量并提供源码指纹；这是该轮记录，不是当前编写手册时新跑出的结果。

**继续追问** BrowserSecurityConfigTest的8项包含在61项内；不要把前端60或导入41加进Java后端数量。

**代码和依据** [S36](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/test-report-2026-10-27.md#L1) · [S42](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/verification-record.md#L1)

### Q174 为什么测试要有独立预期

**建议回答** 如果测试直接调用被测算法算expected，或复制实现同样错误，无法发现共同偏差。预期应来自需求规则、手工可核对的小数据和明确允许拒绝场景。

**继续追问** 例如经理read ALL edit DEPT应预先指定范围内外员工，不能用同一个covers方法生成预期集合。

**代码和依据** [S03](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/requirement-coverage.md#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1) · [S34](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/tests/unit/test_import_excel.py#L1)

### Q175 越权用例至少检查什么

**建议回答** 检查未登录401、无动作权限403、对象范围外拒绝、篡改路径或请求体不能突破，并核对数据库未变化且响应不泄露范围外字段。仅断言状态码不足以证明没有部分写入。

**继续追问** 区分横向越权与管理提权，再覆盖SELF与DEPT子树边界、迁入部门及角色成员列表。

**代码和依据** [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1) · [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22)

### Q176 如何测试撤权下一请求生效

**建议回答** 先用同一会话成功调用，再由受控操作撤角色、改授权或停角色，复用会话再次请求应按新状态允许或拒绝。账号停用和密码重置还需核对旧会话失效。

**继续追问** 不要重新登录后再测，那样无法证明已有会话即时重新鉴权。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q177 409测试为何必须检查数据不变

**建议回答** 冲突代表旧草稿没有覆盖成功提交。测试要核对版本与角色授权或用户角色集合仍为成功者结果，而非只看返回409。重复或未知授权错误也应保持原集合。

**继续追问** 客户端自动换version重试会破坏此语义，前端应重新读取并让用户复核。

**代码和依据** [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1)

### Q178 并发CAS测试应该怎样设计

**建议回答** 使用明确的线程同步让两个请求基于同一旧版本竞争，断言恰一成功、另一冲突，最终集合和版本与成功者一致。仅启动两个线程不保证真的同时进入关键窗口。

**继续追问** 报告中有对应H2并发用例，真实MySQL锁语义仍需独立验证，执行失败还要分析是否测试同步本身出错。

**代码和依据** [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1) · [S12](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/UserMapper.java#L1) · [S13](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java#L1)

### Q179 回滚测试为什么要中途失败

**建议回答** 正常成功不覆盖删旧关系后插新失败的风险。用受控失败注入经过真实Service JDBC写路径，断言事务结束后旧授权及版本恢复。

**继续追问** 生产API不提供任意失败开关；mock在赋值前抛异常只能证明前端错误交互。

**代码和依据** [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1) · [S22](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/api/mock.js#L1) · [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1)

### Q180 行覆盖率怎样计算

**建议回答** 报告中的已覆盖行998加未覆盖100共1098，行覆盖率为998除1098。分支覆盖360除478约75.31%。数字来自JaCoCo XML并应与同轮测试报告对应。

**继续追问** 行执行过不代表全部分支和业务断言正确，类100%也只说明每类有代码被执行。

**代码和依据** [S36](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/test-report-2026-10-27.md#L1)

### Q181 哪些未覆盖位置最值得补

**建议回答** 报告中异常处理分支和Service分支仍有空间。优先按风险补权限拒绝、保护边界、异常后的不变性及并发路径，再覆盖一般格式分支；不是盲目追求每个构造器满覆盖。

**继续追问** error包行58.70%、分支25%可作为分析起点，但要打开报告定位具体行后再决定测试。

**代码和依据** [S16](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/error/GlobalExceptionHandler.java#L1) · [S36](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/test-report-2026-10-27.md#L1)

### Q182 为什么现场要先clean verify

**建议回答** clean清除旧产物，verify走完整约定生命周期并生成本轮结果，减少误开旧JaCoCo页面。现场记录SHA、JDK和Maven环境，再查看Surefire失败跳过和覆盖细节。

**继续追问** 不要为了得到全绿跳过失败测试；Docker打包使用-DskipTests也不能当测试通过证据。

**代码和依据** [S36](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/test-report-2026-10-27.md#L1) · [S39](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/backend.Dockerfile#L1) · [S53](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/pom.xml#L1)

### Q183 Maven启动失败是否意味着后端代码失败

**建议回答** 不一定。先区分工具启动、JDK路径、依赖下载、编译错误和测试断言失败。本地上一轮审查有Maven启动访问被拒绝，不能把这次失败改写成新的测试通过或代码断言失败。

**继续追问** 现场应预先在实际检查电脑验证工具和依赖，保留可执行命令及错误记录。

**代码和依据** [S36](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/test-report-2026-10-27.md#L1) · [S42](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/verification-record.md#L1) · [S53](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/pom.xml#L1)

### Q184 OpenAPI检查脚本能证明哪些一致性

**建议回答** 静态规范检查验证语法引用等结构；与运行实例比较主要核对HTTP方法和路径集合。它不自动证明字段、错误码、鉴权和数据范围全部相同。

**继续追问** Apifox项目更新后也要核对版本和实际请求样例，不能把文档链接存在当契约完全一致。

**代码和依据** [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1) · [S50](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/README.md#L1) · [S56](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/scripts/check_openapi.py#L1)

### Q185 登录字段为什么容易联调错

**建议回答** 登录请求使用account，可接受工号或手机号；用户资料中的工号是username，创建用户还有employeeNo兼容别名。不能把username直接当登录请求键，也不能自己套一层data响应。

**继续追问** 用实际OpenAPI和DTO对照一次成功与一次错误请求，再解释Cookie会话而非Bearer令牌。

**代码和依据** [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1)

### Q186 为什么接口返回不统一包一层data

**建议回答** 当前契约成功时直接返回对象，分页为items page pageSize total，错误时用error包络。保持既定约定比临时引入不同包装更重要，客户端按各操作明确定义解析。

**继续追问** 个别操作返回success或204要逐项看契约，不能假设所有响应都有相同业务字段。

**代码和依据** [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1) · [S15](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/vo/Views.java#L1) · [S16](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/error/GlobalExceptionHandler.java#L1)

### Q187 Cookie会话在接口工具里怎么保持

**建议回答** 先调用真实登录接口，让工具保存服务器JSESSIONID，后续请求在同一Cookie容器中发送。未登录、退出或密码变化后复用旧会话，应按契约拒绝。

**继续追问** Apifox公开文档可读不代表浏览器已获准跨源携带Cookie；同域Swagger与独立客户端的跨域行为不同。

**代码和依据** [S08](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthService.java#L18) · [S17](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/config/BrowserSecurityConfig.java#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1) · [S40](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/scripts/smoke_http.py#L1)

### Q188 真实HTTP脚本覆盖什么

**建议回答** smoke_http验证运行进程、Cookie、代理以及允许拒绝等关键业务链，并在合成环境创建可清理对象。持久化模式通过同一标记对象在保留卷重启后复查。

**继续追问** 脚本会写入和清理，应先确认目标环境；中断后检查残留，不能直接向真实员工库跑。

**代码和依据** [S40](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/scripts/smoke_http.py#L1) · [S41](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1)

### Q189 历史报告怎样在答辩中使用

**建议回答** 报告必须带来源日期、被测版本、数据和环境，作为当时执行的证据。成员一历史全量导入和成员四历史部署不能被说成从未发生，也不能自动证明最新服务器状态。

**继续追问** SQL回放ROLLBACK不等于COMMIT成本，H2不等于MySQL，合成数据不等于原始10000员工。

**代码和依据** [S36](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/test-report-2026-10-27.md#L1) · [S42](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/verification-record.md#L1) · [S48](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/full-data-verification-2026-10-18.md#L1)

### Q190 成员六如何面对测试很多但仍有漏洞的质疑

**建议回答** 承认测试是有范围的证据，说明风险驱动的用例选择、明确未测项和持续补充策略。展示一个具体缺陷如何从失败复现到修复再回归，比单纯报测试数量有说服力。

**继续追问** 不要承诺绝无漏洞。能够说明默认拒绝、失败后数据不变、部署差异和覆盖率局限，才说明理解测试。

**代码和依据** [S03](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/requirement-coverage.md#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1) · [S36](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/test-report-2026-10-27.md#L1) · [S42](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/verification-record.md#L1)

## 第十一章 JavaEE与代码技术追问

围绕项目实际技术补充课程问答 不冒充完整视频讲义

### Q191 IoC和依赖注入在项目中在哪里

**建议回答** Spring创建并管理Service Mapper等Bean，通过构造器把依赖注入AuthService和UserService。业务类不在方法里随意new数据库访问对象，依赖关系因此更明确，也能在单元测试里替换依赖。

**继续追问** 依赖注入不等于业务对象没有状态，也不自动使依赖线程安全。先指出构造器再解释职责。

**代码和依据** [S08](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthService.java#L18) · [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20)

### Q192 SpringBoot自动配置替我们做了什么

**建议回答** 根据web jdbc validation等依赖和application配置建立常用基础设施，提供嵌入式Web运行能力。项目仍自己定义业务Service、Mapper、密码编码和来源过滤，业务授权不是自动生成。

**继续追问** 自动配置是否生效要看当前依赖和条件；例如没有引入完整Security starter，不能假定存在默认安全链。

**代码和依据** [S53](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/pom.xml#L1) · [S17](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/config/BrowserSecurityConfig.java#L1) · [S18](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/config/SecurityConfig.java#L1)

### Q193 Component Service Repository和Bean有什么区别

**建议回答** 它们都可让对象成为容器管理Bean，Service和Repository表达层次意图；Bean用于配置类中通过方法显式构造对象。项目用@Bean定义PasswordEncoder和过滤器，用@Service组织业务。

**继续追问** 注解本身不替你完成业务分层；Repository名称也不表示项目用了JPA Repository接口。

**代码和依据** [S17](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/config/BrowserSecurityConfig.java#L1) · [S18](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/config/SecurityConfig.java#L1) · [S06](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/detailed-design.adoc#L1)

### Q194 REST Controller如何接收请求

**建议回答** RequestMapping确定基础路径，GetMapping PostMapping等确定方法与子路径，RequestBody把JSON绑定DTO，Valid触发约束校验；路径和查询参数按方法声明取得。返回对象经JSON序列化。

**继续追问** 直接Java调用Controller的单测不经过完整路由与JSON解析，这些由MockMvc和真实HTTP层另外覆盖。

**代码和依据** [S54](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/controller/AuthController.java#L1) · [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q195 Bean Validation和业务校验怎样分工

**建议回答** 注解约束非空、长度、格式和数值下限等；Service根据数据库事实判断唯一性、角色存在、管理边界和版本；数据库约束最终保证持久化完整性。

**继续追问** 嵌套grants元素需要@Valid，外层List非空不意味着每一项合法。

**代码和依据** [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1) · [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1)

### Q196 Java record在这里有什么用途

**建议回答** Requests和PO等记录类型简化只读数据载体，访问器与字段约定清楚，适合请求或快照。它不把内部可变集合自动变成不可变，也不负责权限或持久化。

**继续追问** 不能直接返回含passwordHash的PO给客户端；响应字段应由Views或安全视图显式构造。

**代码和依据** [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1) · [S15](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/vo/Views.java#L1) · [S06](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/detailed-design.adoc#L1)

### Q197 checked异常和RuntimeException与事务有什么关系

**建议回答** 当前业务ApiException属于运行时异常，传播出事务方法时通常触发默认回滚。Spring默认规则并不把所有受检异常都视为回滚条件，必要时应明确rollbackFor并有测试。

**继续追问** 不是方法写throws Exception就保证任何异常回滚；以本项目Spring配置与真实调用路径为准。

**代码和依据** [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1) · [R01](https://docs.spring.io/spring-framework/docs/6.1.14/javadoc-api/org/springframework/transaction/annotation/Transactional.html)

### Q198 Transactional实际由谁执行

**建议回答** Spring事务基础设施通过代理在调用前后开启提交或回滚事务，Mapper使用参与同一事务的数据库连接。业务代码的多条SQL才因此形成一致边界。

**继续追问** 手动new服务或直接在同类里自调用，可能不经过代理；不能只看到注解就认定任何调用方式都受控。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [R01](https://docs.spring.io/spring-framework/docs/6.1.14/javadoc-api/org/springframework/transaction/annotation/Transactional.html) · [R09](https://docs.spring.io/spring-framework/reference/core/aop/proxying.html)

### Q199 事务默认传播与隔离是同一回事吗

**建议回答** 传播讨论调用已有事务时怎样加入或新建，隔离讨论并发事务能观察什么数据。当前集合替换显式指定READ_COMMITTED，其余未指定项使用框架和数据源默认设置。

**继续追问** 不要把“默认REQUIRED”解释为“默认可重复读”，更不能用隔离级别取代业务版本检查。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [R01](https://docs.spring.io/spring-framework/docs/6.1.14/javadoc-api/org/springframework/transaction/annotation/Transactional.html)

### Q200 为什么JdbcTemplate可以写事务

**建议回答** 事务属于数据库连接与Spring事务管理，不要求使用JPA。JdbcTemplate负责连接和SQL执行配合，事务边界在Service；当前Mapper显式SQL使表、锁和CAS操作可直接定位。

**继续追问** JPA也不是不需要事务。选择JDBC是当前实现事实，不证明JPA不可用或性能一定更差。

**代码和依据** [S12](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/UserMapper.java#L1) · [S13](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java#L1) · [S53](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/pom.xml#L1)

### Q201 JDBC和JPA应该怎么比较

**建议回答** JDBC更直接控制SQL与结果映射；JPA关注实体映射和持久化上下文，可由实现框架管理部分SQL。项目当前用JDBC，没有实体注解或JpaRepository，因此应拿实际Mapper解释查询。

**继续追问** 若课程实验要求JPA，要准备独立实验代码，不把JdbcTemplate改口叫JPA。

**代码和依据** [S12](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/UserMapper.java#L1) · [S13](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1) · [S53](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/pom.xml#L1)

### Q202 PreparedStatement为什么有用

**建议回答** SQL结构与参数值分开绑定，避免把用户输入当成语句结构执行，并让类型处理更明确。项目Mapper使用占位符传参；动态表名排序列若未来开放，应另用白名单。

**继续追问** 参数绑定不自动保证用户有权修改该行，授权和范围校验仍由Service完成。

**代码和依据** [S12](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/UserMapper.java#L1) · [S13](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java#L1) · [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16)

### Q203 连接池是不是越大越好

**建议回答** 连接池复用连接，减少频繁创建开销，并限制同时访问数据库的数量。过大可能增加数据库竞争和内存消耗，过小可能等待；合理值需结合数据库承载、应用实例数和测量。

**继续追问** 不要把Web线程数等同连接数，也不要在未读取当前生效配置前报一个固定池大小。

**代码和依据** [S53](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/pom.xml#L1) · [S37](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/compose.yaml#L1) · [S48](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/full-data-verification-2026-10-18.md#L1)

### Q204 HttpSession和Cookie各保存什么

**建议回答** Cookie通常携带会话标识，用户身份与凭据标记保存在服务器会话中。浏览器持Cookie不代表它可以决定userId或角色；服务每次重新查库确认。

**继续追问** HttpOnly减少脚本读取Cookie的机会，SameSite与Secure有各自作用；它们不能消除所有XSS或CSRF风险。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S08](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthService.java#L18) · [S57](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/resources/application.yml#L1)

### Q205 Servlet应用是否天然单线程

**建议回答** 不是。Web容器通常并发处理请求，同一Service单例会被多线程调用，同一用户也可并发发请求。局部变量与共享可变状态要区分，数据库更新不能只靠Java里先读后写。

**继续追问** 项目Service的集合与版本并发保护主要在数据库事务层，不能说“同一账号请求串行所以无需锁”。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q206 AOP在本项目有哪些实际体现

**建议回答** 当前可讨论Spring事务代理这个横切机制，但仓库没有课程产品示例要求的自定义业务切面。AOP概念包括切点、通知和织入时机，应结合实际例子解释而不虚报实现。

**继续追问** 被问@Before @Around等时可说明原理及实验例子，再明确当前RBAC实际使用边界。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1)

### Q207 Maven的test package verify install怎样区分

**建议回答** test执行测试阶段，package生成打包产物，verify继续执行验证阶段，install把构件安装到本地仓库供其它工程依赖。实际执行内容仍由POM插件绑定决定。

**继续追问** 当前JaCoCo report绑在test阶段，clean verify是现场约定命令；Docker里的-DskipTests package不产生测试通过证据。

**代码和依据** [S53](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/pom.xml#L1) · [S39](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/backend.Dockerfile#L1)

### Q208 父子工程为什么要先安装依赖库

**建议回答** 课程AOP产品例子依赖cloud factory与core时，Maven必须能解析这些构件；可通过正确反应堆构建或先install依赖到本地仓库解决。当前RBAC后端是自己的单模块POM，不能照抄另一工程目录。

**继续追问** 先看pom的parent modules dependency坐标与版本，再决定命令，不要只按口头顺序盲目执行。

**代码和依据** [S53](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/pom.xml#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1)

### Q209 JavaEE章节没有在项目使用的点怎样回答

**建议回答** 先解释技术概念及课程实验对应实现，再说明本项目用了什么替代或尚未使用。当前仓库不能代表视频1至6章及实验1至3所有代码，成员仍需按课程材料补齐。

**继续追问** JPA AOP Swarm等被问到时不可伪装已经在RBAC落地，也不要以没用到为理由完全不复习。

**代码和依据** [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1) · [S53](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/pom.xml#L1) · [S49](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/planning/team.md#L1)

## 第十二章 服务器容器与交付追问

组长 成员四 成员六共同准备 其他成员掌握入口和边界

### Q210 Docker镜像和容器怎样区分

**建议回答** 镜像是包含运行文件与配置的构建产物，容器是基于镜像启动的隔离进程实例。源码更新不会自动修改已运行容器，需要按部署流程重新构建或拉取镜像并更新实例。

**继续追问** 容器可替换不意味着数据库数据可以丢弃，持久数据放命名卷并另做备份。

**代码和依据** [S37](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/compose.yaml#L1) · [S39](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/backend.Dockerfile#L1) · [S41](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1)

### Q211 为什么采用多阶段Docker构建

**建议回答** 后端构建阶段使用Maven与JDK，运行阶段只携带JRE应用和探针；前端构建阶段用Node生成dist，运行阶段由Nginx托管。这样减少运行环境不必要的构建文件。

**继续追问** 镜像小不等于绝对安全；版本固定、依赖更新、非root和配置管理仍需核对。

**代码和依据** [S39](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/backend.Dockerfile#L1)

### Q212 为什么只对外公开Web端口

**建议回答** Nginx统一提供原型与API代理，MySQL和Java只在Compose网络内部通信，减少直接公开入口，也方便同源Cookie会话。Compose服务名用于内部访问。

**继续追问** 内部网络是网络边界，不替代数据库账号权限和业务鉴权；宿主机仍需正确安全组与防火墙。

**代码和依据** [S37](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/compose.yaml#L1) · [S38](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/nginx.conf#L1)

### Q213 depends_on就代表服务可用了吗

**建议回答** 当前Compose使用service_healthy等待数据库和后端探针，而不只是进程启动顺序。但启动后依赖仍可能失败，运行时健康和业务自测要持续观察。

**继续追问** Web探针只检Nginx自己，不能因为Web healthy就说后端数据库一定正常。

**代码和依据** [S37](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/compose.yaml#L1) · [S38](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/nginx.conf#L1) · [S54](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/controller/AuthController.java#L1)

### Q214 数据库卷和备份有什么区别

**建议回答** 命名卷让容器删除或重建时数据仍存在，备份提供独立恢复副本。卷损坏、误删数据或错误SQL仍会影响当前数据，因此持久化不等于备份。

**继续追问** 重启验证保留卷，down不加-v或--volumes；恢复应先在独立库演练。

**代码和依据** [S37](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/compose.yaml#L1) · [S40](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/scripts/smoke_http.py#L1) · [S41](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1)

### Q215 修改env密码为何没有重置现有账号

**建议回答** RBAC_INITIAL_PASSWORD用于创建重置和空库合成种子，不在每次启动覆盖现有账号。MySQL镜像的初始化变量也主要针对空数据卷，已有数据库账号不会因改文本配置自动同步。

**继续追问** 数据库实际密码与应用连接配置必须一致；不要遇到连接失败就删除卷重建。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S37](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/compose.yaml#L1) · [S41](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1) · [S58](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/data/DataInitializer.java#L1)

### Q216 合成种子什么时候装载

**建议回答** 必须显式开启RBAC_DEMO_ENABLED且初始密码合格，已有用户或系统时跳过。它从合成JSON装载示例，不读取课程原始员工Excel。

**继续追问** 默认关闭避免误初始化真实库；当前合成目录规模不能代表原始员工全量证据。

**代码和依据** [S58](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/data/DataInitializer.java#L1) · [S37](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/compose.yaml#L1) · [S48](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/full-data-verification-2026-10-18.md#L1)

### Q217 HTTP和HTTPS下Cookie配置有什么区别

**建议回答** HTTPS场景可启用Secure使Cookie只经安全连接传输，HTTP入口若直接开启Secure会导致浏览器不回传登录Cookie。代理协议、Host和转发头必须配套。

**继续追问** 当前公开入口是HTTP，不应声称已完成TLS；应解释实际环境而非背理想生产配置。

**代码和依据** [S57](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/resources/application.yml#L1) · [S37](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/compose.yaml#L1) · [S38](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/nginx.conf#L1)

### Q218 Nginx为什么要动态解析backend

**建议回答** 仅重建后端容器时内部IP可能变化，配置使用Docker DNS并定期解析服务名，避免Nginx长期持有旧地址。更新后仍要实际请求验证代理与Cookie。

**继续追问** 这不是数据库重试或集群服务发现的完整方案，单机Compose与Swarm部署能力需分开。

**代码和依据** [S38](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/nginx.conf#L1) · [S41](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1)

### Q219 反向代理哪些头会影响安全

**建议回答** Host与协议用于同源Origin比较，X-Forwarded-Proto等用于正确理解外部访问。若不约束可信代理，恶意转发头可能干扰来源或安全Cookie判断。

**继续追问** 当前application启用framework转发处理，实际入口必须检查谁能直接访问后端、谁能提供转发头。

**代码和依据** [S17](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/config/BrowserSecurityConfig.java#L1) · [S37](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/compose.yaml#L1) · [S38](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/nginx.conf#L1) · [S57](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/resources/application.yml#L1)

### Q220 切换GitHub到Gitee一定要重新部署吗

**建议回答** 单纯改Git remote不会改变当前进程，也不必然要求立即重建；若要让服务器后续从Gitee更新，应配置其访问权限并记录来源。若代码或配置有新变化，要按流程构建部署才生效。

**继续追问** 现在仓库同步不能证明服务器已换源。应读取服务器remote 当前SHA和镜像，而非凭外网首页推断。

**代码和依据** [S01](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/README.md#L1) · [S41](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1) · [S42](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/verification-record.md#L1)

### Q221 服务器用了GitHub SSH能从哪里看出来

**建议回答** 可从服务器Git remote、部署脚本、构建记录或受控配置核对来源。公开页面通常不提供这些证据，不能凭静态API文件一致就证明Git来源已切换。

**继续追问** 需要部署负责人实际读取环境；不要为隐藏来源改写提交作者或破坏历史，两个仓库都保留。

**代码和依据** [S41](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1) · [S42](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/verification-record.md#L1) · [S51](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/contribution-review-2026-10-27.md#L1)

### Q222 怎样证明当前部署的是这次提交

**建议回答** 记录源码SHA、工作区是否有未提交差异、构建镜像摘要或标签、启动时间与数据库迁移版本，再用真实接口检查关键行为。静态OpenAPI相同只能证明该文件版本一致。

**继续追问** 后续可考虑暴露受控的构建信息或镜像标签，但当前没有就不要声称已自动标识。

**代码和依据** [S42](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/verification-record.md#L1) · [S41](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1)

### Q223 升级失败怎样回退

**建议回答** 先确认失败涉及应用代码还是数据库迁移。应用可切回已验证镜像，数据库有结构或数据变化时必须按兼容与恢复方案处理，保留备份及执行日志。

**继续追问** 旧镜像不一定兼容新表，不能把回退解释成随便git reset或删除卷。

**代码和依据** [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1) · [S41](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1)

### Q224 多实例部署当前会遇到什么问题

**建议回答** 会话当前在单应用进程内，扩多个实例时请求可能落到没有该会话的实例，重启也失效。需要共享会话或明确的会话路由与失效策略，同时考虑授权一致性和数据库连接总数。

**继续追问** 不要直接承诺加副本就线性扩容，实际瓶颈还可能在数据库和慢密码校验。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S37](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/compose.yaml#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1)

### Q225 Compose和Swarm有什么区别

**建议回答** 当前仓库是单套Compose服务编排，没有多机Swarm stack部署文件。Swarm用管理节点调度服务任务到工作节点，支持副本或global服务，课程实验的四机拓扑需另行准备。

**继续追问** 只运行docker compose up不能说明已经形成多机集群。

**代码和依据** [S37](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/compose.yaml#L1) · [R03](https://docs.docker.com/engine/swarm/how-swarm-mode-works/services/)

### Q226 部署问题的排查顺序如何组织

**建议回答** 先定位入口是否可达和错误类型，再查Nginx代理、后端就绪与日志、数据库连接配置及卷状态。课程实验另有目录挂载、节点标签和安全组问题，按对应环境检查。

**继续追问** 不要在排查前打印整个env或把口令发群；保留脱敏错误和准确命令。

**代码和依据** [S38](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/nginx.conf#L1) · [S40](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/scripts/smoke_http.py#L1) · [S41](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1)

## 第十三章 实验三性能与监控追问

按用户提供的课堂内容准备 与RBAC现状分开说明

### Q227 实验三核心想说明什么

**建议回答** 课堂例子先建立未经额外业务优化的MySQL简单读写性能基线，只测read product和write product等简单API，客户端发压同时观测服务器资源，以便后续优化有对照。

**继续追问** 它是独立产品示例，当前RBAC包含会话与权限查询，不能把该例子的结果直接当RBAC容量。

**代码和依据** [S48](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/full-data-verification-2026-10-18.md#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1) · [R04](https://jmeter.apache.org/usermanual/test_plan.html)

### Q228 负载测试和压力测试怎样区别

**建议回答** 负载测试观察不同负载下响应时间吞吐和错误变化；压力测试逐步提高负载，寻找预设不可接受性能或资源瓶颈。通常不要求把系统打崩。

**继续追问** 先定义可接受指标和停止条件，再解释负载曲线，不能只报一个最大线程数。

**代码和依据** [S03](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/requirement-coverage.md#L1) · [S48](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/full-data-verification-2026-10-18.md#L1)

### Q229 Threads Rampup和Loop Count各是什么

**建议回答** Threads是虚拟用户线程数，Rampup控制这些线程逐步启动的时长，Loop Count决定每线程执行计划的次数。三者共同影响请求时序，但不是直接指定稳定每秒到达率。

**继续追问** 1000线程10秒启动只是平均每秒启动100线程；实际发请求还受采样器、等待与响应影响。

**代码和依据** [R06](https://jmeter.apache.org/usermanual/component_reference.html)

### Q230 Loop设为2为何不等于均匀发两轮

**建议回答** 通常同一线程完成前一轮的请求和处理后再开始下一轮，下一次发出时刻受响应时间影响。若课堂要隔离这种反馈效应，可按指导固定Loop 1并明确发压模型。

**继续追问** 闭环线程模型下服务变慢会降低线程发下一请求的频率，不能把计划启动速率误当实际吞吐。

**代码和依据** [R06](https://jmeter.apache.org/usermanual/component_reference.html)

### Q231 并发数和QPS是什么关系

**建议回答** 并发是某时刻仍在处理或等待的请求数，QPS是每秒完成或到达的请求量，必须说明统计口径。相同并发下响应变慢可能降低吞吐，线程数也不等于全程真实请求并发。

**继续追问** 稳定条件下可用并发约等于吞吐乘平均在系统时间理解关系，但不能直接套到升压瞬间或有思考时间的全线程数。

**代码和依据** [R04](https://jmeter.apache.org/usermanual/test_plan.html) · [S48](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/full-data-verification-2026-10-18.md#L1)

### Q232 为何需要JMeter与服务器监控同时使用

**建议回答** JMeter展示客户端可见响应和错误，服务器监控帮助定位CPU 内存 GC 磁盘 网络或数据库连接等资源瓶颈。单看一端无法判断慢在哪里。

**继续追问** 发压机也可能先耗尽资源，因此要监控客户端并和APP MySQL节点分开分析。

**代码和依据** [R04](https://jmeter.apache.org/usermanual/test_plan.html) · [R05](https://prometheus.io/docs/introduction/overview/)

### Q233 四台服务器怎样分工

**建议回答** 按课堂最终口径，编译机兼JMeter客户端，节点一沿用MySQL，节点二放应用，管理机放Prometheus。让被测APP和MySQL尽量少混入其它负载，便于归因。

**继续追问** 这是课堂规划，当前仓库不含已完成该四机部署的证明；同安全组也不能代替具体端口和网络连通检查。

**代码和依据** [S37](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/compose.yaml#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1) · [R03](https://docs.docker.com/engine/swarm/how-swarm-mode-works/services/)

### Q234 Swarm标签和global有什么意义

**建议回答** 节点标签与调度约束让APP和MySQL落到指定节点；global模式在符合条件的每个节点运行一份任务，适合课堂的node_exporter。replicated模式按设定副本数调度。

**继续追问** global不会自动提供所有网络与抓取配置，Prometheus仍需正确发现或配置目标。

**代码和依据** [R03](https://docs.docker.com/engine/swarm/how-swarm-mode-works/services/) · [R05](https://prometheus.io/docs/introduction/overview/)

### Q235 Actuator Micrometer和node exporter各看什么

**建议回答** Actuator提供应用管理与监控端点，Micrometer把应用/JVM指标接到监控后端，node_exporter主要暴露宿主机资源指标。Prometheus抓取这些时间序列。

**继续追问** 当前RBAC只有自定义健康接口，不能把SELECT 1的UP称为已经具备这些监控依赖和指标。

**代码和依据** [S53](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/pom.xml#L1) · [S54](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/controller/AuthController.java#L1) · [R05](https://prometheus.io/docs/introduction/overview/)

### Q236 抓取2秒计算5秒有什么含义

**建议回答** scrape_interval控制获取原始样本频率，evaluation_interval控制规则计算频率。课堂短轮次使用较密采样便于观察，但并不保证捕获所有瞬时峰值，也会增加采集开销。

**继续追问** 计算rate等函数还依赖查询时间窗口，不能把两项配置简单当成系统响应速度。

**代码和依据** [R07](https://prometheus.io/docs/prometheus/latest/configuration/configuration/)

### Q237 Targets全部UP是否表示性能合格

**建议回答** UP说明采集目标在该时刻可被抓取，不表示业务延迟、错误率和资源余量已达标。还要看APP和各节点指标，以及JMeter实际完成请求。

**继续追问** 课堂口误端口99110应与实际配置核对，node_exporter常用9100；Prometheus常用9090，端口不是性能结论。

**代码和依据** [R05](https://prometheus.io/docs/introduction/overview/)

### Q238 线程栈能准确算出JMeter最大线程数吗

**建议回答** 可用内存除以线程栈大小只是粗略上界，实际还要留JVM堆、元空间、原生内存、线程对象及系统开销，并受线程和进程限制。缩小-Xss可能引发栈溢出。

**继续追问** 不能用2GB除512KB得到的理论值直接当稳定可用并发，应逐步升压观测发压机。

**代码和依据** [R08](https://jmeter.apache.org/usermanual/best-practices.html)

### Q239 写压测为什么要关注真实COMMIT

**建议回答** 事务提交、日志刷盘和数据库持久化策略会影响真实写入成本。SQL回放后ROLLBACK测到的是不同路径，不能等同成功提交或HTTP保存耗时。

**继续追问** 课堂write product每请求插入还会增加数据量，要准备独立库、清理策略和测试前后状态记录。

**代码和依据** [S47](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/scripts/measure_full_db_perf.ps1#L1) · [S48](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/full-data-verification-2026-10-18.md#L1)

### Q240 如何判断是APP还是MySQL先瓶颈

**建议回答** 对照客户端延迟吞吐错误变化，查看APP CPU GC 线程连接池等待和数据库CPU IO锁慢SQL等。需要多台机器时间同步、同一轮次标记与原始数据。

**继续追问** CPU没满不表示没有瓶颈，可能在锁、连接等待或磁盘；也不能只看管理机负载。

**代码和依据** [R05](https://prometheus.io/docs/introduction/overview/) · [S48](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/full-data-verification-2026-10-18.md#L1)

### Q241 冷热缓存为什么影响结果

**建议回答** 首次连接、JIT、数据库缓冲池和操作系统页缓存都可能改变响应。区分预热与正式测量，重复多轮记录条件，不能挑最快一轮作为系统能力。

**继续追问** 比较优化前后时尽量保持硬件、数据量、请求比例和并发模型相同，否则难以归因。

**代码和依据** [S48](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/full-data-verification-2026-10-18.md#L1) · [R04](https://jmeter.apache.org/usermanual/test_plan.html)

### Q242 性能报告至少应该保留什么

**建议回答** 保存代码版本、硬件拓扑、数据规模、工具配置、读写比例、实际负载曲线、成功率、吞吐、平均及P95 P99、资源时间序列、瓶颈判断和停止条件。没有测过就不填写虚构QPS。

**继续追问** 项目现有历史SQL报告不满足完整端到端压测证据，可用于解释已测部分和下一步设计。

**代码和依据** [S48](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/full-data-verification-2026-10-18.md#L1) · [S47](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/scripts/measure_full_db_perf.ps1#L1) · [R04](https://jmeter.apache.org/usermanual/test_plan.html) · [R05](https://prometheus.io/docs/introduction/overview/)

## 第十四章 六位成员之间的交叉追问

把同一个场景从多个责任方向拆开 适合小组模拟口试

### Q243 老师让你们共同解释登录链路

**建议回答** 成员二讲原型登录与路由状态，成员四讲真实会话认证和成功轮换sessionId，成员一讲账号唯一性与摘要格式，成员六展示首次改密及错误凭据测试。组长先说明原型和真实API分别验收。

**继续追问** 成员三补充权限未加载默认拒绝，成员五补充身份认证与授权职责分离。不要把原型登录作为MySQL登录证据。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S08](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthService.java#L18) · [S23](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/router/index.js#L1) · [S24](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/stores/auth.js#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q244 老师质疑经理能查看全部会不会越权修改

**建议回答** 成员一解释角色权限关系上的read ALL edit DEPT，成员四定位target及covers，成员三解释按钮和范围展示，成员六展示篡改目标ID后的拒绝及数据不变。

**继续追问** 成员二补充台账只允许电话邮箱，成员五说明范围是动作级策略而非用户统一属性。

**代码和依据** [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S19](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1) · [S29](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/LedgerService.java#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q245 两个管理员同时保存授权由谁负责解释

**建议回答** 成员三说明original draft version和409交互，成员四说明锁 CAS与事务，成员一说明permission_version和关联表，成员六说明并发测试恰一成功断言。

**继续追问** 成员五比较编辑意图冲突与存储原子性，组长说明现场用合成角色，不改真实管理员授权。

**代码和依据** [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S13](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java#L1) · [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q246 老师问一名员工为什么成为项目经理

**建议回答** 成员一从负责人匹配和部门类型追到初始角色，成员四说明有效角色如何贡献权限，成员六用核对SQL与预期验证，组长说明正式原表和合成演示的证据范围。

**继续追问** 成员三不能用界面当前选中角色替代数据库事实；成员五补充后续领域职责应如何封装该规则。

**代码和依据** [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1) · [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S34](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/tests/unit/test_import_excel.py#L1) · [S48](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/full-data-verification-2026-10-18.md#L1)

### Q247 老师指出Apifox字段与代码不同

**建议回答** 成员六先展示当前api/openapi.yaml和Requests，确认实际版本；后端作者说明当前行为，前端作者说明受影响调用，组长协调以已确认需求修正并同步三处。

**继续追问** 不要争论哪个页面更漂亮，也不要临时在一端兼容所有错误字段掩盖契约漂移。

**代码和依据** [S14](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1) · [S50](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/README.md#L1)

### Q248 老师让你们证明失败没有写一半

**建议回答** 成员四打开事务方法和Mapper，成员一说明版本及关联表，成员六运行或解释真实失败注入用例；成员三只展示草稿保留的前端证据，不替代数据库证明。

**继续追问** 成员五追问异常类型与代理调用是否影响回滚，组长说明MySQL独立复验状态。

**代码和依据** [S10](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20) · [S13](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java#L1) · [S26](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1) · [R01](https://docs.spring.io/spring-framework/docs/6.1.14/javadoc-api/org/springframework/transaction/annotation/Transactional.html)

### Q249 老师问为什么数据库一万员工但页面只有几个人

**建议回答** 先确认展示的是内存原型还是后端数据库，再确认数据模式和过滤范围。成员二解释原型样例，成员一说明正式规模与导入报告，成员六核对真实接口和实例。

**继续追问** 组长避免把合成样例说成丢数据，也不能把目录规模当原始员工规模。

**代码和依据** [S22](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/api/mock.js#L1) · [S20](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1) · [S42](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/verification-record.md#L1) · [S48](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/full-data-verification-2026-10-18.md#L1)

### Q250 老师要求解释一个没有权限的按钮

**建议回答** 成员三定位精确码和前端状态，成员二说明菜单与路由，成员四说明同一动作服务端require，成员六展示直接请求仍被拒绝。

**继续追问** 如果按钮属于后续占位能力，先说明阶段范围；不能把不可点击本身当完整安全证明。

**代码和依据** [S25](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/authorization/rbac0-policy.js#L1) · [S23](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/router/index.js#L1) · [S07](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16) · [S31](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)

### Q251 老师问数据库重启以后账号是否还在

**建议回答** 成员四解释MySQL命名卷与初始化跳过，成员六展示持久化脚本的同一标记对象，成员一说明数据库恢复与迁移，组长提供实际部署版本和脱敏日志。

**继续追问** 原型刷新恢复初始内存、后端会话重启失效、数据库业务数据保留是三个不同现象。

**代码和依据** [S37](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/compose.yaml#L1) · [S40](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/scripts/smoke_http.py#L1) · [S58](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/data/DataInitializer.java#L1)

### Q252 老师问为什么现在没有OO后端

**建议回答** 成员五说明当前目录只是规划及二期领域设计方案，成员四说明一期功能分解实现，组长对照当前验收范围，成员六说明未来双实现统一契约与独立预期测试。

**继续追问** 不能把当场换一个类名当完成二期；也不要把所有模型图说成完全没做。

**代码和依据** [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1) · [S04](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/structured-design.md#L1) · [S44](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/uml/class-responsibility.md#L1) · [S35](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1)

### Q253 老师问代码是不是每个人都懂

**建议回答** 每人选择本人主责的一段代码、一个规则与一条测试解释，随后回答相邻模块如何对接。组长提供责任与真实贡献记录，成员间补充跨层机制。

**继续追问** 全员理解基本需求是底线，深入实现可有分工；不能让一个人答完或编造个人提交经历。

**代码和依据** [S49](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/planning/team.md#L1) · [S51](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/contribution-review-2026-10-27.md#L1) · [S03](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/requirement-coverage.md#L1)

### Q254 老师问现在还有什么值得改进

**建议回答** 可以选择已确认的实际限制：Java过滤分页查询负担、错误处理分支测试不足、内存会话多实例、部署版本证据、后续OO继承约束。每项说明影响、验证与计划，不泛说完善系统。

**继续追问** 每个成员从自己方向给一个具体改进，组长给优先级；不要把未来设计当成已经实现。

**代码和依据** [S09](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22) · [S36](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/test-report-2026-10-27.md#L1) · [S42](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/verification-record.md#L1) · [S43](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1)

## 第十五章 个人开场与全组接力

以下是准备模板，不是已经核实的个人贡献声明。请把“负责”替换为本人实际任务，把已做与计划分开，开场后立即准备打开对应代码。组长应兼顾时间和全员发言。

### 组长的整体开场

**一分钟开场** 我们围绕市政公司的员工、角色和功能目录建立统一权限平台。第一阶段重点是可运行的RBAC0后端、独立界面原型和API设计。后端按功能分解，身份、动作与范围逐请求校验，授权集合用事务与版本处理并发。各成员负责数据、前端、权限交互、功能后端、领域设计和测试集成。接下来我们用授权保存与台账越界两个场景，把需求、模型、代码和测试对应起来。

**本人准备** 准备展示当前SHA与报告；服务器实际SHA另核对。组长账号为wenjiu-leng66，责任表尚未登记其成员编号，本手册不代替本人确认身份。

### 成员一的数据开场

**一分钟开场** 我负责数据与数据库方向。设计重点是把原始组织、员工和功能清单转成稳定ID关系，并把同名消歧、负责人关联、动作范围和失败行变成可核对规则。数据库当前十表加一个权限核对视图，范围附在角色权限关系上。接下来我可以从一名经理或一个功能点，说明源表到角色、权限和最终范围的过程。

**本人准备** 只认领本人实际完成部分。准备111组织 10000员工加admin 3549原清单授权加6平台授权的来源。

### 成员二的前端开场

**一分钟开场** 我负责前端公共结构、登录以及用户组织交互。Vue原型采用统一会话与路由守卫，身份、权限、范围和改密标记一起更新，未取得可信授权时默认拒绝。当前使用内存mock，真实后端另用Cookie接口验收。我会说明登录跳转、首次改密、直接URL访问和范围受限列表的交互依据。

**本人准备** 准备打开router auth与UsersView，避免把sessionStorage恢复模拟身份说成真实认证。

### 成员三的权限开场

**一分钟开场** 我负责角色权限前端方向。共享策略按精确权限码合并有效授权，同码取最大范围，不同动作互不扩大。角色授权页保留完整Map草稿与已保存快照，翻页不丢授权，保存带版本，409后重新读取，失败时明确保留或复核结果。我会展示read依赖和快速切换角色时如何抑制旧响应。

**本人准备** 准备解释原型模拟失败与真实数据库回滚不同，并指出ADMIN只读同时有后端保护。

### 成员四的后端开场

**一分钟开场** 我负责面向功能后端方向。请求通过Controller DTO Service Mapper逐层处理，AuthorizationService统一身份、必须改密、动作权限及目标范围。用户和角色维护各有保护规则，角色集合保存结合行锁、版本CAS与事务。接下来我从current到target说明一次越界拒绝，再解释旧版本和中途失败时数据如何保持一致。

**本人准备** 准备用一个具体方法逐行讲，避免只背分层名称；说明没有完整Security FilterChain和当前MySQL复验边界。

### 成员五的设计开场

**一分钟开场** 我负责领域模型与后续OO函数式方向，并按实际贡献参与设计核对。当前可运行后端按功能步骤组织，PO主要承载数据，backend-oo还处于规划。我会说明当前类职责与数据关系的差异，以及将来怎样把授权合并、继承和约束组织为可测试的领域策略与快照计算。

**本人准备** 不能把后续方案说成已实现。准备解释为什么不能复制Service改名就算完成第二套后端。

### 成员六的测试开场

**一分钟开场** 我负责API统筹与独立验收方向。我们按41条一期需求追溯到接口、实现和测试，区分隔离单元、MockMvc H2、真实HTTP和MySQL持久化证据。报告记录90项后端测试与行90.89百分比 分支75.31百分比覆盖，但覆盖率不代替业务断言。我会展示一个拒绝用例和一个失败后数据版本都不变的用例。

**本人准备** 准备解释Apifox OpenAPI Swagger三处一致性；当前报告与现场新跑结果分开。

### 二十分钟小组排练安排

| 时段 | 参与者 | 练习内容 |
|---|---|---|
| 第1至2分钟 | 组长 全组 | 项目目标 当前边界 阶段和证据 |
| 第3至5分钟 | 成员一 四 | 一名经理的源表 角色 范围与目标判定 |
| 第6至8分钟 | 成员二 三 | 登录路由 授权草稿和跨页保存 |
| 第9至12分钟 | 成员三 四 六 | 双管理员保存409 中途失败 数据不变 |
| 第13至15分钟 | 成员五 四 | 功能分解与OO规划 职责及事务不变量 |
| 第16至18分钟 | 成员六 一 | 测试证据 JaCoCo MySQL及原表复验边界 |
| 第19至20分钟 | 每人一句 | 一个真实限制 一个下一步验证 互相补充 |

这只是内部排练分配，老师可能随时打断或换问题。不要依赖固定发言顺序；任何成员都应先理解全组的基本需求和当前实现。

## 第十六章 易错口径与检查前准备

| 容易答错的说法 | 准确口径 |
|---|---|
| 多角色并集就是继承 | 当前只合并直接启用角色，继承尚未实现。 |
| 前端按钮隐藏所以接口安全 | 后端必须独立确认动作和范围。 |
| 经理查看ALL所以编辑也ALL | 查看编辑使用不同权限码及scope。 |
| SELF就是本部门 | SELF只指本人，不能创建他人。 |
| 无角色默认管理员 | 有效业务权限为空，默认拒绝。 |
| 首次改密是第三种账号状态 | mustChangePassword与启停是独立维度。 |
| 系统管理员只能保护最后一个 | 当前对本人 admin及ADMIN持有者采用强保护。 |
| 所有404和403统一就行 | 按具体契约及资源安全语义处理。 |
| 授角只检查新增角色 | 撤销管理类角色同样受限制。 |
| 角色状态变化就是用户角色集合变化 | 有效权限和已分配关系版本要区分。 |
| 有事务就不会并发覆盖 | 事务 CAS 行锁分别处理原子性 旧草稿和协调。 |
| Mapper删插天然原子 | 需要外层真实事务边界。 |
| 原型500模拟证明了MySQL回滚 | 只证明前端交互；真实数据库另有证据层。 |
| 覆盖率100就无漏洞 | 覆盖率统计执行，不证明预期和全部状态空间正确。 |
| H2通过等于MySQL通过 | 方言 隔离 锁和部署要独立验证。 |
| Swagger能开等于接口管理归档完整 | Apifox作为文档入口，仍核对契约版本。 |
| SQL几毫秒等于HTTP保存几毫秒 | Java 认证 网络与真实COMMIT成本另算。 |
| 一万个JMeter线程等于每秒一万请求 | 线程 启动速率 并发和吞吐分别测量。 |
| Targets UP就是性能合格 | UP只说明抓取可达。 |
| 有V001文件就自动用了Flyway | 当前首次空卷初始化，已有卷升级另执行。 |
| TRUNCATE可以普通事务回滚 | MySQL全量重建前备份，不依赖ROLLBACK恢复。 |
| 卷持久化等于有备份 | 卷和独立恢复副本解决不同风险。 |
| 切换Git远端就完成更新部署 | 运行进程需要实际构建更新并核对版本。 |
| PO就是丰富领域模型 | 当前规则集中Service，OO后端尚在规划。 |
| Spring Security crypto自动保护所有接口 | 只有密码组件，业务鉴权需实际调用。 |
| 职责表等于个人实际贡献证明 | 核对本人提交 产物和能解释的工作。 |

### 到场前逐项确认

确认已按课程安排先完成助教检查，再参加老师预约。时间地点与课程通知重新核实，避免把历史教室信息当新通知。

每人知道本人实际提交与产物，能解释一个用例 一段核心代码 一条拒绝或失败断言。

在实际检查电脑提前试过JDK Maven Node依赖和报告打开路径，不把本地启动访问被拒绝当作已完成新测试。

打开Gitee的数据库设计与后端详细设计AsciiDoc入口，确认文件并非空白且链接指向当前主分支。

准备Apifox公开文档与Swagger实例入口，对照OpenAPI版本 方法 路径 account字段及Cookie。

部署负责人记录服务器remote SHA 工作区 镜像与迁移，业务调用仅用明确授权的合成对象和可清理账号。

报告与执行版本一致。已有90项结果可作为报告证据，现场新执行成功后才登记本轮新结果。

测试与截图不含密码 Cookie 哈希 原始人员表或数据库备份。本人课程未选JavaEE时按老师要求说明。

### 常用命令和入口

以下命令是准备材料，不代表本次已经全部执行。真实HTTP脚本有写入与清理，必须先确认目标环境。持久化重启按部署说明执行，不在正式库随意试验。

#### 固定本地版本

```text
git status --short
git rev-parse HEAD
java -version
mvn -version
node --version
```

#### 后端完整测试及报告

```text
mvn -f backend-procedural/pom.xml clean verify
# 查看 backend-procedural/target/surefire-reports/
# 打开 backend-procedural/target/site/jacoco/index.html
```

#### 单独查看跨层集成

```text
mvn -f backend-procedural/pom.xml -Dtest=Stage1ApiTest test
```

#### 前端与导入测试

```text
node --test tests/frontend/*.test.cjs
python -m unittest discover -s tests/unit -p "test_*.py"
```

#### 原型构建和本地运行

```text
npm --prefix frontend ci
npm --prefix frontend run build
npm --prefix frontend run dev
```

#### 文档及静态契约

```text
python scripts/check_repository.py
python scripts/check_openapi.py
git diff --check
```

#### 服务器只读状态检查

```text
git remote -v
git rev-parse HEAD
docker compose --env-file deploy/.env -f deploy/compose.yaml ps
# 切勿把env原文或凭据发到公开材料
```

#### 受控合成环境的真实HTTP检查

```text
python3 scripts/smoke_http.py --base-url http://127.0.0.1 --env-file deploy/.env --check-web
# 该脚本会创建与清理合成对象 先核对环境和备份
```

Apifox公开文档 https://7u8ub108r6.apifox.cn/  该地址由用户提供，本次未独立读取平台全部内容。

Swagger实例 http://124.70.42.35:8080/swagger-ui/index.html?url=/api-design/openapi.yaml  实例与仓库的当前版本仍由部署负责人核对。

## 第十七章 API操作快速索引

以下33项来自当前api/openapi.yaml，统一基础路径为/api/v1。成功对象 字段权限 错误码和Cookie要求以该操作完整契约为准。本表只帮助定位，不表示每项已在当前服务器独立调用。

| 方法 | 路径 | 操作 |
|---|---|---|
| POST | /api/v1/auth/login | 工号或手机号登录 |
| POST | /api/v1/auth/logout | 退出并使旧会话失效 |
| GET | /api/v1/me | 当前用户 |
| PATCH | /api/v1/me/password | 校验旧密码并修改本人密码 |
| GET | /api/v1/me/permissions | 当前有效权限、范围及来源角色 |
| GET | /api/v1/portal/menu | 按查看权限返回系统、模块、功能点树 |
| GET | /api/v1/portal/functions/{functionCode} | 功能占位页及六个操作状态 |
| GET | /api/v1/ledger/employees | 按查看范围分页查询员工 |
| PATCH | /api/v1/ledger/employees/{id} | 按编辑范围修改电话、邮箱 |
| GET | /api/v1/users | 筛选管理范围内的用户 |
| POST | /api/v1/users | 新增用户，不同时分配角色 |
| GET | /api/v1/users/{id} | 查看范围内用户详情 |
| PUT | /api/v1/users/{id} | 编辑用户基本信息 |
| DELETE | /api/v1/users/{id} | 删除用户并清理关联 |
| PATCH | /api/v1/users/{id}/status | 启用或停用用户 |
| POST | /api/v1/users/{id}/password-reset | 重置密码并要求首次改密 |
| GET | /api/v1/users/{id}/roles | 读取用户完整角色集合和版本 |
| PUT | /api/v1/users/{id}/roles | 带版本号整组替换用户角色 |
| GET | /api/v1/departments | 查询管理范围内的只读部门树 |
| GET | /api/v1/departments/{id}/members | 某部门及全部下级的员工 |
| GET | /api/v1/roles | 角色分页列表 |
| POST | /api/v1/roles | 新增自建角色 |
| GET | /api/v1/roles/{id} | 角色详情 |
| PATCH | /api/v1/roles/{id} | 编辑角色名称与说明 |
| DELETE | /api/v1/roles/{id} | 删除自建角色及关联授权 |
| PATCH | /api/v1/roles/{id}/status | 启用或停用角色 |
| GET | /api/v1/roles/{id}/users | 查询角色下的用户 |
| GET | /api/v1/roles/{id}/permissions | 读取角色直接授权和集合版本 |
| PUT | /api/v1/roles/{id}/permissions | 带范围与版本整组替换角色授权 |
| GET | /api/v1/catalog/functions | 按功能点分页查询完整权限目录 |
| GET | /api/v1/permissions | 兼容：按单条权限分页查询 |
| GET | /api/v1/resources | 兼容：逐级查询资源节点 |
| GET | /api/v1/health | 应用及数据库就绪检查 |

## 第十八章 集成测试定位索引

下列29个方法位于Stage1ApiTest，使用Spring MockMvc和H2。它们与61项隔离测试合计为报告中的90项后端测试。方法名称是便于现场搜索的锚点，断言细节必须打开源码阅读。

| 测试方法 | 检查含义 |
|---|---|
| allProtectedFamiliesRejectAnonymous | 所有受保护接口族拒绝匿名 |
| credentialFailuresAreIndistinguishable | 不同凭据失败统一提示 |
| phoneLoginSessionRotationAndNoSecretFields | 手机号登录 会话轮换且不返回秘密 |
| logoutInvalidatesCookieSession | 退出使旧会话失效 |
| firstLoginMustChangeAndChecksOldPassword | 首次改密限制及旧密码检查 |
| disableInvalidatesExistingSessionOnNextRequest | 停用后旧会话下一请求失效 |
| resetPasswordInvalidatesExistingSessionAndRequiresChange | 重置后旧会话失效且必须改密 |
| ledgerSelfScopeAndUnknownFieldProtection | 台账SELF与未知字段保护 |
| managerReadAllEditDepartmentAndChildren | 经理read ALL edit DEPT及下级 |
| departmentUserFiltersCannotEscapeScope | 用户过滤不能突破管理范围 |
| createUniqueUserEditResetAndDeleteManagerReference | 用户唯一性维护重置删除及负责人清理 |
| selfAndUniqueAdminProtection | 本人和管理员保护 |
| departmentAdminCannotGrantOrRemoveManagementRoles | 部门管理员不得增撤管理角色 |
| businessRoleUnionAndImmediateRevocation | 多业务角色并集和立即撤权 |
| userRoleVersionConflictAndDuplicateValidation | 用户角色版本冲突与重复校验 |
| roleCrudStatusDeleteAndBuiltinGuards | 角色CRUD启停删除及内置保护 |
| grantValidationVersionAndImmediateChange | 授权校验版本与立即生效 |
| managementRoleClassificationPreventsCustomPrivilegeEscalation | 自建角色管理类识别防提权 |
| portalCatalogAndRoleSourcesAreComplete | 门户目录与权限来源完整 |
| departmentTreeMembersAndRoleUsersAreScoped | 部门成员与角色成员裁剪 |
| importedPbkdf2HashesAreAccepted | 导入PBKDF2哈希可登录 |
| invalidPaginationAndContactDataReturn400 | 分页与联系方式非法返回400 |
| failedReplacementRollsBackGrantsAndVersion | 授权替换中途失败回滚集合及版本 |
| simultaneousCasHasExactlyOneWinner | 并发CAS恰一成功 |
| longPasswordsDoNotAcceptDifferentSuffixBeyondBcryptLimit | 长密码不同尾缀不会误匹配 |
| unicodePasswordsPreserveAllUtf8BytesAndLegacyBcryptStillMatches | Unicode完整输入及旧BCrypt兼容 |
| oversizedAndMalformedNumericIdsDoNotBecomeSqlErrors | 超长错误数字ID不会落成SQL异常 |
| concurrentAssignmentMakesUnconfirmedRoleDeleteRefuse | 并发授角使未确认删角色拒绝 |
| selfManagementScopesNeverExposeDepartmentPeersOrCreateOtherAccounts | SELF不泄露同事且不能创建他人 |

### 当前报告的覆盖率明细

| 指标 | 已覆盖 | 总数 | 比例 |
|---|---|---|---|
| 行 | 998 | 1098 | 90.89% |
| 分支 | 360 | 478 | 75.31% |
| 方法 | 245 | 274 | 89.42% |
| 类 | 49 | 49 | 100.00% |
| 指令 | 6293 | 7067 | 89.05% |
| 复杂度 | 407 | 521 | 78.12% |

本手册重新计算后端src scripts及pom文件的LF归一化指纹，结果与报告记录一致。这证明文件集合与报告标识吻合，不代替一次新的执行；报告正文的基础SHA和最终整合SHA仍应明确区分。

ad5b76f70a344301ca309b67a004fa759f1b143acc3e26b2d129fe9f8c2504ea

## 第十九章 代码与资料入口

S编号链接固定到本次GitHub提交，防止后续main变化使答案失去依据。正文中点S编号可直接打开源码；若现场使用Gitee，则用同一路径在ceadcaf版本定位。代码行号基于本次快照，部署实例另核对。

- **S01 当前实现与仓库说明** [README.md](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/README.md#L1)
- **S02 需求规格与阶段边界** [docs/requirements/req-spec.md](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/requirements/req-spec.md#L1)
- **S03 41条一期需求追溯** [docs/stage1/requirement-coverage.md](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/requirement-coverage.md#L1)
- **S04 结构化设计与数据流** [docs/design/structured-design.md](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/structured-design.md#L1)
- **S05 AsciiDoc数据库设计** [docs/database/database-design.adoc](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/database/database-design.adoc#L1)
- **S06 AsciiDoc后端详细设计** [docs/design/detailed-design.adoc](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/design/detailed-design.adoc#L1)
- **S07 身份权限范围统一入口** [backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthorizationService.java#L16)
- **S08 登录退出与密码生命周期** [backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthService.java](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/AuthService.java#L18)
- **S09 用户维护与角色集合事务** [backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/UserService.java#L22)
- **S10 角色与直接授权事务** [backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/RoleService.java#L20)
- **S11 组织只读访问和下级展开** [backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/DepartmentMapper.java](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/DepartmentMapper.java#L54)
- **S12 用户SQL与角色版本CAS** [backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/UserMapper.java](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/UserMapper.java#L1)
- **S13 角色锁和授权SQL** [backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/RoleMapper.java#L1)
- **S14 请求DTO白名单及校验** [backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/dto/Requests.java#L1)
- **S15 显式响应字段与分页** [backend-procedural/src/main/java/com/municipal/rbac/procedural/vo/Views.java](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/vo/Views.java#L1)
- **S16 统一异常和错误包络** [backend-procedural/src/main/java/com/municipal/rbac/procedural/error/GlobalExceptionHandler.java](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/error/GlobalExceptionHandler.java#L1)
- **S17 CORS及写请求来源检查** [backend-procedural/src/main/java/com/municipal/rbac/procedural/config/BrowserSecurityConfig.java](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/config/BrowserSecurityConfig.java#L1)
- **S18 PBKDF2及历史哈希校验** [backend-procedural/src/main/java/com/municipal/rbac/procedural/config/SecurityConfig.java](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/config/SecurityConfig.java#L1)
- **S19 MySQL权威十表及视图** [database/migrations/V001__create_core_tables.sql](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/migrations/V001__create_core_tables.sql#L1)
- **S20 原表导入与稳定编号** [database/seeds/import_excel.py](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/import_excel.py#L1)
- **S21 稳定功能编号** [database/seeds/function_codes.csv](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/database/seeds/function_codes.csv#L1)
- **S22 前端内存模拟接口** [frontend/src/api/mock.js](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/api/mock.js#L1)
- **S23 Vue路由及前置守卫** [frontend/src/router/index.js](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/router/index.js#L1)
- **S24 模拟会话共享状态** [frontend/src/stores/auth.js](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/stores/auth.js#L1)
- **S25 共享RBAC0前端策略** [frontend/src/authorization/rbac0-policy.js](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/authorization/rbac0-policy.js#L1)
- **S26 角色授权完整草稿交互** [frontend/src/views/RoleGrantsView.vue](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RoleGrantsView.vue#L1)
- **S27 用户维护页面** [frontend/src/views/UsersView.vue](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/UsersView.vue#L1)
- **S28 角色页面及异步上下文** [frontend/src/views/RolesView.vue](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/RolesView.vue#L1)
- **S29 真实台账业务范围** [backend-procedural/src/main/java/com/municipal/rbac/procedural/service/LedgerService.java](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/LedgerService.java#L1)；补充 [frontend/src/views/LedgerView.vue](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/LedgerView.vue)
- **S30 组织页面** [frontend/src/views/DepartmentsView.vue](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/frontend/src/views/DepartmentsView.vue#L1)；补充 [backend-procedural/src/main/java/com/municipal/rbac/procedural/service/DepartmentService.java](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/service/DepartmentService.java)
- **S31 29项真实跨层集成测试** [backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/Stage1ApiTest.java#L1)
- **S32 隔离Service单元测试目录** [backend-procedural/src/test/java/com/municipal/rbac/procedural/service](https://github.com/wenjiu-leng66/RBAC-Permission-System/tree/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/test/java/com/municipal/rbac/procedural/service)
- **S33 前端策略及mock测试** [tests/frontend](https://github.com/wenjiu-leng66/RBAC-Permission-System/tree/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/tests/frontend)
- **S34 原表导入合成测试** [tests/unit/test_import_excel.py](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/tests/unit/test_import_excel.py#L1)
- **S35 25路径33操作契约** [api/openapi.yaml](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/openapi.yaml#L1)
- **S36 已有执行结果与JaCoCo** [docs/testing/test-report-2026-10-27.md](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/test-report-2026-10-27.md#L1)
- **S37 单套Compose部署** [deploy/compose.yaml](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/compose.yaml#L1)
- **S38 Nginx代理与页面回退** [deploy/nginx.conf](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/nginx.conf#L1)
- **S39 后端多阶段镜像** [deploy/backend.Dockerfile](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/backend.Dockerfile#L1)；补充 [deploy/web.Dockerfile](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/web.Dockerfile)
- **S40 真实HTTP与持久化脚本** [scripts/smoke_http.py](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/scripts/smoke_http.py#L1)
- **S41 部署配置备份与恢复** [deploy/README.md](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/deploy/README.md#L1)
- **S42 已验证与待核验项目** [docs/stage1/verification-record.md](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/verification-record.md#L1)
- **S43 尚未实现的OO与函数式规划** [backend-oo/README.md](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-oo/README.md#L1)
- **S44 当前类职责关系** [docs/uml/class-responsibility.md](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/uml/class-responsibility.md#L1)
- **S45 业务概念与领域关系** [docs/uml/domain-model.md](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/uml/domain-model.md#L1)
- **S46 账号角色和会话状态** [docs/uml/state-diagrams.md](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/uml/state-diagrams.md#L1)
- **S47 只读SQL测量工具** [scripts/measure_full_db_perf.ps1](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/scripts/measure_full_db_perf.ps1#L1)
- **S48 历史原表全量与SQL测量** [docs/testing/full-data-verification-2026-10-18.md](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/full-data-verification-2026-10-18.md#L1)；补充 [docs/testing/server-verification-2026-10-21.md](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/testing/server-verification-2026-10-21.md)
- **S49 六人主责及账号登记** [docs/planning/team.md](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/planning/team.md#L1)
- **S50 API文档与调试入口** [api/README.md](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/api/README.md#L1)
- **S51 成员贡献整合与修订依据** [docs/stage1/contribution-review-2026-10-27.md](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/docs/stage1/contribution-review-2026-10-27.md#L1)
- **S52 当前协作与main规则** [CONTRIBUTING.md](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/CONTRIBUTING.md#L1)
- **S53 Maven依赖和JaCoCo配置** [backend-procedural/pom.xml](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/pom.xml#L1)
- **S54 会话登录Controller** [backend-procedural/src/main/java/com/municipal/rbac/procedural/controller/AuthController.java](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/controller/AuthController.java#L1)；补充 [backend-procedural/src/main/java/com/municipal/rbac/procedural/controller/HealthController.java](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/controller/HealthController.java)
- **S55 正INT字符串ID防线** [backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/Ids.java](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/mapper/Ids.java#L1)
- **S56 契约及运行路径比较脚本** [scripts/check_openapi.py](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/scripts/check_openapi.py#L1)
- **S57 会话Cookie与开发默认配置** [backend-procedural/src/main/resources/application.yml](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/resources/application.yml#L1)
- **S58 明确开启的空库合成种子** [backend-procedural/src/main/java/com/municipal/rbac/procedural/data/DataInitializer.java](https://github.com/wenjiu-leng66/RBAC-Permission-System/blob/4a2bed3b2857aefcb7831c9179f0f30b2d0afa23/backend-procedural/src/main/java/com/municipal/rbac/procedural/data/DataInitializer.java#L1)
### 官方技术资料

这些资料用于核对通用技术语义，不证明本项目已实现其中全部功能。Spring事务注解采用6.1.14 API资料，代理说明等其它页面只用于跨版本稳定概念；项目依赖版本以POM为准。

- **R01** [Spring Framework 6.1.14事务注解语义](https://docs.spring.io/spring-framework/docs/6.1.14/javadoc-api/org/springframework/transaction/annotation/Transactional.html)
- **R02** [MySQL 8.4 TRUNCATE及隐式提交](https://dev.mysql.com/doc/refman/8.4/en/truncate-table.html)
- **R03** [Docker Swarm服务任务和global模式](https://docs.docker.com/engine/swarm/how-swarm-mode-works/services/)
- **R04** [JMeter测试计划基本结构](https://jmeter.apache.org/usermanual/test_plan.html)
- **R05** [Prometheus监控与抓取模型](https://prometheus.io/docs/introduction/overview/)
- **R06** [JMeter线程组与组件参数](https://jmeter.apache.org/usermanual/component_reference.html)
- **R07** [Prometheus抓取与规则间隔配置](https://prometheus.io/docs/prometheus/latest/configuration/configuration/)
- **R08** [JMeter运行及资源实践](https://jmeter.apache.org/usermanual/best-practices.html)
- **R09** [Spring代理与自调用说明](https://docs.spring.io/spring-framework/reference/core/aop/proxying.html)
课堂实验三相关要求还使用了用户提供的课堂录音整理，包括四机角色、2秒采样、5秒规则计算及read write产品接口。未提供的完整视频第1至6章和实验一二材料无法从当前仓库完整还原，应另对照课程资料补齐。

