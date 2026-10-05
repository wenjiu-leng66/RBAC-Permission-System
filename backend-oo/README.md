# 面向对象后端与函数式计算

主责：成员 5，成员 4 交叉评审。当前只预留目录，没有构建配置与启动类，尚不能运行。

在老师确认复用边界后初始化构建项目。以领域概念和职责组织授权：用户、角色、权限、角色层级、约束策略及授权服务。

预留 `com.municipal.rbac.oo` 下的 controller、application、domain/model、domain/policy、infrastructure、dto。domain 负责核心概念与规则，application 协调用例，infrastructure 处理持久化等副作用。

适合函数式设计的候选模块是基于快照的权限合并和冲突检测；输入输出明确、不修改共享状态。数据库访问不冒充纯函数。

接口遵守 [API 契约](../api/README.md)，先读 [需求问题](../docs/requirements/questions.md) 与 [验收矩阵](../docs/testing/acceptance-matrix.md)。
