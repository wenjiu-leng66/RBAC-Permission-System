# RBAC Permission System

“有请下一组”的 OOAD 课程设计：市政公司 RBAC 权限管理系统。

当前阶段：**RBAC0 准备与界面设计**。已建立协作框架，成员 3 的角色权限交互原型及前端权限判断模块已准备。尚未初始化正式 Vue / Spring Boot 应用，尚无真实后端或业务数据联调。

## 从这里开始

- 六位成员先阅读 [GitHub 使用手册](docs/guides/github-team-manual.md)。
- 日常提交遵守 [贡献流程](CONTRIBUTING.md)。
- 组长填写 [成员责任表](docs/planning/team.md)，按 [首次任务清单](docs/planning/first-tasks.md) 分配工作。
- 开工前完成 [需求范围](docs/requirements/scope.md)、[待确认问题](docs/requirements/questions.md) 和 [接口约定](docs/standards/api-contract.md)。
- 检查进度与完成标准：[阶段计划](docs/planning/milestones.md)、[验收矩阵](docs/testing/acceptance-matrix.md)。
- 查看成员 3 的 [RBAC0 原型](frontend/prototypes/README.md) 与 [完成内容解释](docs/guides/member3-rbac0-explained.md)。

## 目录

```text
frontend/                 前端，一套界面连接两种后端实现
backend-procedural/       面向功能/过程的后端
backend-oo/               面向对象后端与适合的函数式计算
api/                      OpenAPI 契约与接口变更记录
database/                 数据库迁移、脱敏种子数据说明
data/                     合成样例；原始数据目录被忽略
docs/                     需求、UML、数据字典、计划、测试、手册
tests/                    单元、契约、集成、端到端测试的预留位置
deploy/                   部署说明与不含秘密的配置示例
scripts/                  仓库结构、文档链接和文件检查
.github/                  Issue、PR、模块责任和 CI 配置
```

Git 不跟踪空文件夹。源码目录中的 `.gitkeep` 只用于保留位置，正式添加源码后可删除对应占位文件。

## 架构与课程阶段

前端经 HTTP/JSON 调用后端，后端完成身份、权限和业务校验后访问数据库。两套后端遵循同一 API 契约，核心授权逻辑分别实现，测试时使用等价的独立初始数据。

候选技术栈为 Vue 3、Java、Spring Boot、Spring Security 和 MySQL。精确版本、老师认可的范式边界以及能否复用基础设施尚需确认，见 [环境约定](docs/standards/environment.md)。当前没有通过文件名或目录宣称已实现这些技术。

| 课程阶段 | 验收窗口（暂按 2026 年） | 主交付 |
| --- | --- | --- |
| RBAC0 | 10/27—10/29 | 界面设计、数据库设计、面向功能基础后端 |
| RBAC1 | 11/24—11/27 | 层级、两种后端、部分前端与比较验证 |
| 约束与最终验收 | 12/22—12/24 | 两种约束实现、前端、部分函数式设计 |

理论上的 RBAC2 增加约束，RBAC1 与 RBAC2 合并属于 RBAC3；最终以老师的课程口径命名，并解释理论关系。

## 本地检查

已安装 Python 3.11 或以上时，在仓库根目录运行：

```powershell
python scripts/check_repository.py
node --test tests/frontend/rbac0-policy.test.cjs
git diff --check
```

CI 检查目录、UTF-8、Markdown 本地文件链接、合成样例关联及不应提交的文件，并运行成员 3 的前端权限判断测试。它**不代表完整应用能运行、后端业务测试通过或已成功部署**。应用初始化后再增加前端构建与后端测试。

## 数据与公开仓库

当前仓库公开。原始员工表、电话、密码、数据库备份、云密钥和访问令牌不得提交。`data/samples/` 只有明确标注的合成数据；真实数据的获取与导入通过小组受控方式完成，见 [数据说明](data/README.md)。

`.gitignore` 只影响尚未跟踪的文件，不能清除已经进入 Git 历史的秘密。发现泄露后先撤销或轮换凭证，再由组长处理历史。
