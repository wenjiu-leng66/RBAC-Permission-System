# 项目文档

| 类别 | 文件 |
| --- | --- |
| 需求 | [需求规格说明书](requirements/req-spec.md) |
| 设计 | [结构化设计](design/structured-design.md)、[后端详细设计（AsciiDoc）](design/detailed-design.adoc)、[界面原型说明](design/stage1-ui-prototype.md) |
| 模型 | [UML图集](uml/README.md)、[数据库设计（AsciiDoc）](database/database-design.adoc) |
| 接口 | [Apifox 文档](https://7u8ub108r6.apifox.cn/)、[OpenAPI 契约](../api/openapi.yaml)、[接口与权限约定](standards/api-contract.md) |
| 数据 | [数据字典](database/data-dictionary.md)、[导入规则](database/import-rules.md)、[数据库操作](../database/README.md) |
| 运行 | [环境约定](standards/environment.md)、[部署与恢复](../deploy/README.md) |
| 质量 | [验收入口](stage1/README.md)、[执行步骤](stage1/self-test.md)、[41 条需求覆盖](stage1/requirement-coverage.md)、[执行记录](stage1/verification-record.md)、[2026-10-27 测试报告](testing/test-report-2026-10-27.md)、[验收矩阵](testing/acceptance-matrix.md) |
| 组织 | [小组分工](planning/team.md)、[阶段范围](planning/milestones.md) |

接口以当前 OpenAPI 为准，表结构以版本迁移为准；验收结果记录被测版本、环境和证据，不能由设计或测试用例的存在推定通过。

[成员整合决定](stage1/contribution-review-2026-10-27.md)、[原表及SQL历史记录](testing/full-data-verification-2026-10-18.md)、[服务器历史记录](testing/server-verification-2026-10-21.md)分别保留采纳依据与复验边界。
