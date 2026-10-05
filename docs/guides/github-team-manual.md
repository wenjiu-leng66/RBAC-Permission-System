# RBAC 项目六人小组 GitHub 使用手册

适用仓库：[wenjiu-leng66/RBAC-Permission-System](https://github.com/wenjiu-leng66/RBAC-Permission-System)

整理日期：2026-10-05

对象：组长与六位成员，包括刚开始使用 Git 的同学。

这份手册同时说明“按钮在哪里”“命令做什么”和“六个人怎样配合”。先完成首次设置，再按日常任务流程操作。仓库当前处于骨架准备阶段，没有可运行的前后端业务应用。

## 1. 先理解你们在共享什么

每个人电脑上都有自己的项目副本，不是六个人同时编辑云上的同一份文件。Git 记录本地变更，GitHub 保存远程版本、讨论、任务和评审。

| 名称 | 可以怎样理解 | 在本项目里的用途 |
| --- | --- | --- |
| Repository / 仓库 | 项目文件与版本历史 | 本项目全部源码、设计、接口与说明 |
| Clone / 克隆 | 下载文件与 Git 历史，建立远程关联 | 每人第一次拿到项目 |
| Commit / 提交 | 把选中的改动记录为本地版本 | 保存一次清晰、完整的工作 |
| Branch / 分支 | 某项任务的独立工作线 | 修改期间不直接影响 main |
| Push / 推送 | 把本地提交上传 | 让别人可以查看和评审 |
| Pull / 拉取 | 取回并整合远程更新 | 同步队友已合并的工作 |
| Issue | 一个任务、缺陷或待确认问题 | 约定负责人、依赖和验收 |
| Pull Request / PR | 请求把任务分支合入目标分支 | 讨论、检查和评审变更 |
| Review / 评审 | 另一位同学核对变更 | 发现接口和逻辑问题 |
| Merge / 合并 | 把已确认变更接入目标分支 | 让 main 获得交付物 |
| Milestone | 阶段任务集合 | 三次课程验收 |
| Tag | 给某次提交取一个固定标记 | 保存阶段演示版本 |
| Actions / CI | 自动执行约定的检查 | 发现结构、文档或后续代码问题 |

下载 ZIP 适合临时看文件；参加协作应使用 Clone，否则没有完整的提交、分支和远程关联。保存文件、commit、push 是三个不同动作。

## 2. 六位成员如何对应仓库

真实姓名、成员编号、GitHub 用户名填到 [责任表](../planning/team.md)。六个人各用自己的账号，不共用组长账号。组长不是额外的第七个人。

| 成员 | 日常关注的目录 | 应找谁配合 | 最早的准备任务 |
| --- | --- | --- | --- |
| 1：数据与数据库 | `database/`、`data/`、`docs/database/` | 成员 4、6 | 字段字典、组织映射、负责人关联、ER 图 |
| 2：前端基础 | `frontend/`、相关原型与接口 | 成员 3、4、6 | 登录、用户、部门原型及前端规范 |
| 3：权限前端 | `frontend/`、层级与约束交互文档 | 成员 2、4、5 | 第一阶段角色权限页面原型 |
| 4：面向功能后端 | `backend-procedural/`、`api/` | 成员 1、5、6 | 功能分解与 API 草案 |
| 5：OO 与函数式后端 | `backend-oo/`、`docs/uml/` | 成员 4、6 | 领域模型、职责与序列图 |
| 6：测试与集成 | `tests/`、`docs/testing/`、`api/` | 全体作者 | 验收预期、问题定位与双后端比较 |

目录负责制是协作分工，不意味着 GitHub 已按文件夹限制谁能修改。评审人检查跨目录影响。成员 4、5 的后端需要同一外部契约，成员 2、3 在一套前端中合作。

## 3. 组长的首次设置

仓库已经存在，**不用重新创建同名仓库或重新初始化 Git**。后续骨架合入 main 后，新成员从同一个 main 克隆。

### 3.1 邀请另外五人

1. 收集五人的真实 GitHub 用户名。
2. 登录仓库所有者账号，打开仓库的 **Settings**。
3. 在访问设置中进入 **Collaborators**，选择 **Add people**。
4. 逐一找到并核对账号后邀请，提醒同学接受。
5. 回到访问列表确认邀请状态，再让他们进行 push 练习。

看得到公开仓库不等于有写权限。普通协作者有仓库级的权限，不能仅靠目录名隔离。成员用户名尚未提供时，不要把“成员 2”当作真实账号填写。

步骤参考 [GitHub 邀请协作者](https://docs.github.com/en/repositories/managing-your-repositorys-settings-and-features/repository-access-and-collaboration/inviting-collaborators-to-a-personal-repository)。

### 3.2 填写责任与评审

让每位成员通过自己的首次文档 PR 补上责任表一行。确认后维护 CODEOWNERS：例如真实前端账号负责 `/frontend/`，数据库账号负责 `/database/`。

当前 CODEOWNERS 只以 `wenjiu-leng66` 作为初始兜底，不表示以后所有评审都必须等组长。CODEOWNERS 的评审是否强制执行取决于保护规则设置；单独有这个文件不会阻止别人直接 push。

### 3.3 设置 main 分支保护

本仓库目前公开。GitHub 官方说明，免费套餐支持公开仓库的受保护分支；私有仓库的相应能力依套餐而定。如果以后改成私有，重新检查是否仍能强制执行，不把原有规则存在视为有效。[适用条件](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/managing-protected-branches/about-protected-branches)

在 **Settings → Branches** 或当前界面的 **Rules → Rulesets** 中为 main 创建规则。不要同时建立互相矛盾的多套规则。

建议在成员加入且首次 CI 成功运行后设置：

- 合并 main 前必须走 PR。
- 至少一个其他成员批准，作者不能批准自己的 PR。
- 合并前必须通过 `repository-check`；应用初始化后再加入真实构建与测试检查。
- 根据规则要求更新到最新 main，解决评审讨论。
- 禁止 main 的 force push 和分支删除。

不要给各成员默认的日常工作安排管理员绕过规则。紧急绕过应说明原因、记录变更并补评审。仓库骨架提交不等于已经开启这些网页设置，组长应实际核对。

### 3.4 阶段与看板

按 [阶段计划](../planning/milestones.md) 管理三个 Milestone：RBAC0 验收、RBAC1 验收、约束与最终验收。已有同名 Milestone 时复用，内部日期暂按 2026 年并以老师通知修正。

Issue 的阶段由 Milestone 管理，负责人用 Assignees 选择真实成员。Projects 看板可以使用 Todo、In Progress、Review、Done。也可以初期仅用 Issue 列表和标签，不强迫重复维护两套进度。

前期先认领 [首批任务](../planning/first-tasks.md)。状态变更必须对应实际产物：有文档或代码在审查时进入 Review，评审与验收完成后进入 Done。

## 4. 每位成员第一次在 Windows 上准备

### 4.1 安装与身份

安装 Git for Windows，浏览器能登录自己的 GitHub。IDEA、VS Code、GitHub Desktop 可以提供 Git 界面，但不能代替本地 Git 和基本概念。

在 PowerShell 检查：

```powershell
git --version
```

如果提示命令找不到，先确认 Git 已安装、终端已重新打开、安装路径已加入 PATH。不要在没有 Git 的状态下继续复制命令。

设置你自己的提交身份，双引号内必须替换：

```powershell
git config --global user.name "你的姓名或GitHub用户名"
git config --global user.email "你在GitHub设置中确认的提交邮箱"
```

这只设置提交署名，**不等于登录 GitHub**。为了保护邮箱，可在 GitHub **Settings → Emails** 查找自己的 noreply 邮箱并使用它，不能照抄其他同学的邮箱。检查：

```powershell
git config --get user.name
git config --get user.email
```

### 4.2 登录怎样完成

优先使用 Git Credential Manager 或 IDE / GitHub Desktop 提供的浏览器登录。执行需要写权限的 Git 操作时，按正常登录流程用自己的账号完成。

HTTPS 的 Git 操作不再使用 GitHub 网站密码进行传统密码认证。另一种方法是配置 SSH。初学阶段全组先统一 HTTPS＋凭据管理方式，减少混淆。[GitHub 身份验证](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/about-authentication-to-github)

不要把密码、验证码、SSH 私钥或访问令牌发到群里，也不要把令牌直接写进仓库 URL。若工具提示请求额外权限，先确认它来自正规工具且只涉及你需要访问的仓库。

### 4.3 克隆仓库

在你希望保存项目的文件夹打开 PowerShell，例如课程项目目录，运行：

```powershell
git clone https://github.com/wenjiu-leng66/RBAC-Permission-System.git
cd RBAC-Permission-System
git status
git remote -v
```

成功后应该看到当前 main 分支及 `origin` 指向本仓库。执行后面的所有 Git 命令时，都应位于这个克隆目录或它的子目录。

已经成功克隆就不用每次再次 clone。不要在已有项目中随意 `git init`，也不要把自己的仓库套进另一个仓库。

公开仓库通常无需登录即可 clone，push 需要正确登录和协作权限。未被邀请的同学先联系组长，不通过共用账号解决。

### 4.4 骨架阶段能运行什么

当前可以查看 Markdown、讨论接口、创建 Issue 和练习 PR。已安装 Python 3.11+ 时可运行：

```powershell
python scripts/check_repository.py
git diff --check
```

当前没有 `frontend/package.json`、后端构建配置和启动类，所以不能执行 npm dev、Maven 后端启动或声称系统已运行。应用初始化后，各模块 README 才会加入准确命令。

Python 只用于现在的仓库检查，不表示项目后端改成 Python。暂未安装 Python 的文档参与者可先阅读与提交，由 GitHub Actions 检查；需独立本地验证时再安装官方支持版本。

## 5. 每天做一个任务的完整流程

**main＋短期任务分支＋PR** 是全组默认流程。每个任务一个分支，不为每人维护一个永久分支。以下命令按步骤执行，一条成功后再执行下一条，看到错误先处理。

### 5.1 开始前：确认任务和最新版本

先认领或建立一个 Issue，明确完成标准，查看是否依赖 API 或数据库变更。然后：

```powershell
git status
git switch main
git pull --ff-only origin main
git switch -c docs/member2-profile
```

`git status` 应先确认没有未保存到提交或 stash 的修改。分支名是示例，成员 3 可以用 `docs/member3-profile`，开发任务用 `feat/...`，缺陷用 `fix/...`。不要多人同时修改同一个任务分支。

`--ff-only` 的作用是遇到本地 main 与远程分叉时停止，避免不知情产生合并提交。遇到停止应检查自己是不是误在 main 提交，见故障处理。

### 5.2 编辑后：查看差异

```powershell
git status
git diff
```

`git diff` 查看尚未暂存的改动。确认只修改了自己的任务内容。对责任表练习，应只编辑自己的行，不覆盖别人已经填写的内容。

### 5.3 暂存与提交

以补责任表为例：

```powershell
git add docs/planning/team.md
git diff --cached
git commit -m "docs: 填写成员2职责与GitHub账号"
```

`git add` 把指定文件放入暂存区；`git diff --cached` 核对将进入提交的内容；`git commit` 保存本地版本。先选具体文件，不在不清楚改动范围时盲目 `git add .`。

提交说明要让别人知道实际产物：`docs: 明确角色继承方向`、`feat: 增加用户角色分配接口`、`fix: 阻止访问他人薪酬记录`、`test: 补充间接继承环测试`。不要只写 update、修改、最终版。

### 5.4 上传自己的分支

```powershell
git push -u origin docs/member2-profile
```

第一次 `-u` 建立跟踪关系，后续在同一分支提交修订后用 `git push`。push 上传不代表已合并 main。

### 5.5 创建 PR

在 GitHub 的 **Pull requests → New pull request**，或推送后的 **Compare & pull request** 提示中，确认：

- base 是 `main`，即改动要进入的分支。
- compare 是你的任务分支。
- 标题说明结果，填写仓库自带 PR 模板。
- 在 **Files changed** 查看有没有意外文件、乱码、秘密或无关修改。
- 设置相关评审人，关联 Issue 和 Milestone。

未完成的 PR 可设为 Draft，适合早期确认接口；Draft 不能被当作完成任务。Ready 后请同学正式评审。

如果任务对应 Issue #12，PR 中写 `Closes #12`，合并到默认分支后可自动关闭对应 Issue。只关联而不想关闭时，写 `Related to #12`。

完整协作过程参考 [GitHub flow](https://docs.github.com/en/get-started/using-github/github-flow)。

### 5.6 按评审意见修改

继续在原任务分支编辑、检查、commit、push，现有 PR 自动包含新提交，不用为每次修订开一个 PR。

当评论需要解决时，先完成修订，再说明结果；没有采纳某建议也要解释理由。评审者确认行为，不只检查代码能否编译。

### 5.7 合并以后同步

确认 PR 已合并且工作区干净后：

```powershell
git switch main
git pull --ff-only origin main
git fetch --prune origin
```

然后从最新 main 开始下一个任务。网页可以删除已合并远程分支，提交历史和 PR 记录仍保留。

本地分支不要立即用 `-D` 强制删除。尤其采用 Squash 合并后，Git 未必认为原分支已经通过普通祖先关系合并，`git branch -d` 可能拒绝；先确认工作完整保存在 main 或 PR 中，保留本地分支也没关系。

## 6. Issue 怎么写，小组才不会互相等

**Issues → New issue** 有三种模板：Task、Bug、Question。它们解决不同问题。

### 6.1 一个任务示例

标题：`[Task] 编写 RBAC0 角色分配接口契约`

主责成员 4，评审成员 2 和 6。交付物为 OpenAPI 中正式定义的接口、请求与响应样例、必要权限和异常状态。依赖为 Q-11 的角色分配规则确认。

完成标准写成：前端能够依据契约建立 Mock；两套后端作者理解相同字段与更新语义；缺角色、无权限、冲突规则的预期已明确；契约经过 PR 合并。

这样的任务可以在业务代码开始前完成，前端也不必一直等后端写完。

### 6.2 一个缺陷示例

标题：`[Bug] 普通员工修改请求ID后可以读取他人记录`

写所用版本和后端、合成账号与角色、数据准备、请求步骤、预期拒绝、实际响应。日志和截图遮盖令牌与私人信息。越权问题优先于界面美化。

### 6.3 一个待确认问题示例

标题：`[Question] 查看/编辑本人是否同时限制查看范围`

记录源表位置、两种解释、对数据模型和测试的影响、需要老师确认。结论出来后更新需求、接口、字典与验收，不只在群里说一句。

### 6.4 进度怎样更新

每人日常更新三件事：已交付什么、下一步做什么、需要谁提供哪项信息。Issue 中写清阻塞和已尝试动作。只写“完成 80%”无法判断是否能够集成。

Done 要求 PR 合并与完成标准达到。一个 Task Issue 不宜同时容纳整个后端或整个月工作，尽量拆成半天到两天可以交付的任务。

## 7. 怎样做有效评审与合并

| 变更 | 优先评审人 | 重点 |
| --- | --- | --- |
| 前端公共结构 | 成员 2、3 互审 | 组件、交互、接口一致性、错误反馈 |
| 核心授权或后端 | 成员 4、5 互审 | 默认拒绝、继承、约束、范围及独立实现 |
| 数据和迁移 | 成员 1 加相关后端 | 唯一键、映射、事务、数据损失与恢复 |
| API 契约 | 提供方、使用方、成员 6 | 字段、语义、状态与两实现兼容 |
| 测试与需求 | 成员 6 加模块作者 | 独立预期、失败分支、可复现证据 |

在 PR 的 **Files changed** 针对具体行评论，必要时用 **Request changes** 提出阻断意见，确认后用 **Approve**。只发“收到”不等于完成评审。

合并前确认：CI 对当前提交通过；没有未处理冲突或阻断讨论；相关文档同步；应用变更进行了适当测试；没有私有数据。若只是文档变更，不要求编造业务测试结果。

建议使用 **Squash and merge**，把一个任务 PR 合成清楚的 main 提交。采用这种方式后，不从已经合并的旧任务分支继续下一个任务，重新从 main 建分支。

作者不能批准自己的 PR。组长写的日常 PR 也找其他同学评审。首次骨架初始化可由仓库所有者授权完成并留下初始化记录，之后按团队正式评审约定执行。

## 8. 两个人改到同一处，冲突怎么办

冲突是 Git 无法判断最终内容应该怎样，并不意味着项目坏了。权限码、接口、角色规则和迁移冲突，应找双方作者一起决定。

### 8.1 在任务分支接入最新 main

先确认当前在自己的任务分支、工作区干净，再执行：

```powershell
git fetch origin
git merge origin/main
```

如果自动合并成功，运行必要检查后 push。如果产生冲突：

1. `git status` 列出冲突文件。
2. 在 IDEA 或 VS Code 的合并编辑器中查看双方内容。
3. 按业务语义保留或重写最终内容，不盲目全部选“我的”或“对方的”。
4. 清除冲突标记，确认关联接口、测试、文档仍一致。
5. 仅暂存解决好的冲突文件，提交本次合并并 push。

责任表冲突示例命令：

```powershell
git add docs/planning/team.md
git diff --cached
git commit -m "chore: 合并main并保留已确认成员信息"
git push
```

如果不准备继续当前 merge，且合并开始前工作区是干净的，可用 `git merge --abort` 返回开始前状态，再联系相关同学。不在未理解当前状态时使用 reset、clean 或强制推送。

参考 [GitHub 解决合并冲突](https://docs.github.com/en/pull-requests/how-tos/merge-and-close-pull-requests/resolving-a-merge-conflict-using-the-command-line)。

### 8.2 怎样减少冲突

任务分支尽量短期合并；先确认公共接口、组件和表结构；避免在一个功能 PR 中顺便格式化整个项目；多人更新列表时只改自己的内容。迁移编号提前协调，已应用迁移用新文件修正。

两名后端交叉评审但各实现核心规则；两名前端共享公共规范与 API 客户端，按页面和组件拆任务。

## 9. 用 IDEA 或 VS Code 操作 Git

命令行不是唯一方法。界面的动作仍对应 branch、stage、commit、push 和 pull，PR 可统一到 GitHub 网页完成。

### 9.1 VS Code

1. 打开已经 clone 的仓库根目录，不只打开单个文件。
2. 在 Source Control（源代码管理，常见快捷键 Ctrl+Shift+G）查看改动。
3. 在分支选择入口创建或切换任务分支，确认当前分支名字。
4. 编辑后点击具体文件查看差异，用暂存按钮选择需要提交的文件。
5. 输入说明并 Commit，再执行 Push / Publish Branch。
6. GitHub 网页创建 PR、评审和合并。

“Sync Changes”通常包含同步动作，不只是保存文件。初学时先分别执行 Pull 和 Push，确认是否有工作区改动和冲突，不盲目点同步。

VS Code 中 M 常表示已修改，U 常表示未跟踪。只保存文件不会上传。GitHub 扩展可改善 PR 操作，但不是课程项目的必要依赖。[VS Code 源代码管理](https://code.visualstudio.com/docs/sourcecontrol/overview)

### 9.2 IntelliJ IDEA

从版本控制获取项目或打开已克隆目录，确认 Git 设置能识别本地 Git。通过 Git 分支入口创建任务分支，在提交窗口查看文件和差异，Commit 后 Push。

更新项目或拉取前先检查当前分支和未提交改动。冲突出现时打开合并工具，双方作者共同确认，提交解决结果。具体菜单随版本、语言和布局变化，找 Git、Commit、Push、Update 等动作即可。

在骨架阶段还没有后端构建配置，IDEA 没有识别为可运行 Maven 项目是正常的。后续由后端作者初始化，再更新运行说明。

IDEA 和 VS Code 打开的应是同一份本地克隆，避免在两个软件里分别建一份同名项目后混淆提交。项目级本地设置默认不进仓库，团队需要的设置通过单独评审决定。

## 10. 前后端与数据库在 GitHub 中怎样配合

GitHub 同步源码和文档，运行时的连接由 API 和配置完成。成员 2 push 前端、成员 4 push 后端，并不会自动让页面调用正确服务器。

### 10.1 先确认契约，再并行开发

假设成员 4 要提供用户分页 API：

1. 成员 4 和成员 6 在 Issue 说明字段、分页、认证和失败状态。
2. 成员 2 核对页面需要的字段，成员 5 核对另一后端能采用相同契约。
3. 契约进入 `api/openapi.yaml`，经 PR 评审。
4. 前端按 Mock 先做交互，两位后端按同一契约分别实现。
5. 用真实后端和数据库联调；确认过滤、分页、错误、授权和数据状态。

不要让前端在代码里临时猜字段。接口变更提交说明里明确哪些使用方受影响，并同步文档、两后端和测试。

### 10.2 数据库协作

成员 1 提出迁移 PR，相关后端核对影响。各成员本地执行相同迁移构建自己的数据库，不要求全体连接同一个实例。

已应用的迁移不直接改写，用新迁移修正。集成数据库由指定负责人备份后执行；测试两种后端时使用等价的独立状态，避免互相改变测试结果。

### 10.3 GitHub 上的完成与应用可用

“代码上传”“PR 合并”“CI 通过”“前后端联调通过”“部署成功”是不同状态。Task 的完成标准应写明需要达到哪一步。

本仓库当前 CI 只验证框架和资料。后续真正运行应用时，各模块 README 必须提供环境、配置、启动、测试与数据准备说明；最早完成一条“登录→查询员工→页面展示→权限拒绝”的真实链路。

## 11. Actions 怎样看，失败怎样查

仓库 **Actions** 页面显示 `Repository checks` 工作流；PR 页面也显示检查状态。进入当前提交的运行记录，再打开失败的步骤读取错误。

当前检查包括：必需文件、UTF-8、Markdown 本地文件链接、合成样例关联、禁止提交的明确文件类型、冲突标记及提交中的空白错误。

常见失败：文档链接目标不存在、改了目录没更新链接、提交了 Excel 或本地 `.env`、文件编码错误、存在未解决冲突标记。按日志修复，commit 和 push 后会重新检查。

绿色仅说明检查覆盖的部分通过。未运行的业务测试不能写成通过。工作流使用只读 `contents` 权限，没有云部署凭据或自动部署操作；后续增加权限前说明实际需要。[GitHub 工作流令牌最小权限](https://docs.github.com/en/actions/tutorials/authenticate-with-github_token)

若任务分支尚未开 PR，当前配置不保证其每一次 push 都触发 CI；创建 PR 后会对 PR 更新运行。main push 与手动触发也运行。

## 12. 常见错误速查

| 现象 | 常见原因 | 处理 |
| --- | --- | --- |
| `not a git repository` | 终端不在克隆目录 | 用 `Get-Location` 确认后进入正确仓库 |
| Git 命令找不到 | 未安装或 PATH / 终端未刷新 | 安装官方 Git，重新打开终端 |
| `Please tell me who you are` | 未设提交身份 | 配置自己的 name 和 email；这不是 GitHub 登录 |
| 公开仓库能 clone，push 被拒绝 | 没有协作权限或登录了其他账号 | 接受邀请并使用自己的正确账号登录 |
| HTTP 403 / permission denied | 权限、凭证或保护规则不满足 | 核对账号与 PR 规则，不借用组长令牌 |
| `Could not resolve host` | DNS、网络或代理问题 | 排查网络和本机代理，不改业务代码或关闭 TLS 校验 |
| `non-fast-forward` | 远程分支有自己没取得的提交 | fetch 查看差异；共享分支合并更新，不能 force 覆盖 |
| `nothing to commit` | 没有改动或没有暂存 | 用 status、diff、diff --cached 检查，不强行造提交 |
| 切换分支被修改阻止 | 当前存在未提交改动 | 按任务 commit，或暂存到 stash；不要直接丢弃 |
| PR 没有差异 | base / compare 选错、未 push 或已合并 | 核对分支、提交和目标 main |
| main 的 pull --ff-only 失败 | 本地 main 有额外提交或历史分叉 | 先保护自己的提交，见下面误在 main 的处理 |
| 网页目录不见了 | Git 不跟踪空目录 | 加 README、真实文件或 .gitkeep |
| 添加了 gitignore，秘密仍被跟踪 | 文件已进入 Git 索引 / 历史 | 停止提交，通知组长，撤销秘密并处理历史 |
| 换行提示 CRLF / LF | Windows 本地与仓库换行规则不同 | 检查 .gitattributes 和 diff，不大范围盲目重格式化 |

### 12.1 工作没提交，但需要临时切换

先用 status 检查内容，确有必要时：

```powershell
git stash push -u -m "临时保存当前任务"
git stash list
```

`-u` 包括未跟踪文件，但默认不包括被忽略文件。stash 存在本地，不会帮你备份到 GitHub。回到原任务分支后使用 `git stash apply` 恢复，确认无误再决定是否清理 stash；恢复也可能产生冲突。

### 12.2 误在 main 上提交了自己的工作

如果还没 push，先在当前提交上创建任务分支以保留工作，再推送该分支和开 PR：

```powershell
git switch -c fix/move-work-to-task-branch
git push -u origin fix/move-work-to-task-branch
```

不要立刻 reset 本地 main。先确认分支已保存自己的工作，并请熟悉 Git 的同学帮助恢复本地 main 与 origin/main 的关系。避免复制一个会删除工作的通用命令。

如果错误已经进入远程 main，建立修复或 revert PR，不 force push 改写全组历史。

### 12.3 误把不需要的文件放进暂存区

例如误暂存根目录 README：

```powershell
git restore --staged README.md
```

这只撤销暂存，保留工作区的修改；不同于丢弃文件内容。尚未加入 Git 的真实数据应移动到受控且被忽略的目录，并重新核对待提交文件。

### 12.4 秘密已经 push

删除文件或提交一个新版本不能消除历史里的秘密。立即通知组长、撤销或轮换凭证，再评估历史清理与副本影响。已泄露的密码不因为仓库改成私有而恢复安全。

公开仓库中的员工姓名电话、登录凭证、原始 Excel、云密钥与数据库备份需要保护。样例采用合成信息，截图也遮盖秘密。仓库检查不是完整敏感信息扫描，提交者仍应人工核对差异。

## 13. 三次验收如何保存版本

主责由组长指定。确认 main 中包含该阶段已验证内容、工作区干净、测试和演示材料对应后，给该提交打 tag。

第一阶段示例：

```powershell
git switch main
git pull --ff-only origin main
git status
git tag -a v0.1-rbac0 -m "RBAC0 阶段验收版本"
git push origin v0.1-rbac0
```

后续可用 `v0.2-rbac1`、`v1.0-final`。标记已存在时先检查，不覆盖旧 tag。写阶段说明并链接实际测试与演示，不只创建空标签。

在 Releases 中选择 tag 并填写交付说明，材料只包含可公开内容。真实数据、凭证和未允许公开的课程材料另行受控交付。

查看阶段版本可以在 GitHub 切换 tag；本地需要重现时保留当前工作，再检出指定版本到独立工作位置或分支。不要在答辩现场把唯一工作区切来切去而丢失未提交内容。

## 14. 每个人的首次练习

这次练习只改文档，不需要写业务代码，能验证权限、身份、分支、评审和同步。

1. 接受邀请，clone 并阅读根 README 与本手册。
2. 从最新 main 建立自己的 `docs/memberN-profile` 分支。
3. 只更新责任表中自己的姓名、账号和对应职责。
4. 查看差异，暂存该文件，commit 并 push。
5. 创建目标为 main 的 PR，找约定同学评审。
6. CI 通过后按评审意见修改，等待合并。
7. 回到 main 拉取，看到自己的那行和队友的更新。

建议依次合并责任表练习，或使用最新 main 更新后再合并，减少六个人同时改同一表造成的冲突。每人再评审一次其他人的文档 PR。

练习完成的标准不是截图“我已经注册 GitHub”，而是自己的提交署名正确、PR 可查看、评审已完成、main 包含交付物。

## 15. 每周协作与组长检查

每周约定一次规划与至少一次集成检查；第一阶段可以增加一次联调。各成员用 Issue 记录交付、阻塞和下一步，关键决定写入纪要。

组长每周检查：

- [ ] 当前阶段的任务是否都有主责、依赖与完成标准。
- [ ] 前端是否有已确认的 API，而不是靠猜测字段。
- [ ] 两种后端是否使用相同预期与独立数据，而不只彼此对比。
- [ ] 迁移、UML、API、测试是否随代码更新。
- [ ] PR 是否长期无人评审；相关评审能否分给模块负责人。
- [ ] main 是否可以按文档复现；当前 CI 实际覆盖什么。
- [ ] 数据和日志是否脱敏，是否有人提交机器专属配置。
- [ ] 距离验收是否留出冻结、回归和排练时间。

成员 6 负责统筹，不代替所有作者维护质量。组长负责协调，不负责替五个人重写所有模块。

## 16. 命令速查与正式约定

| 目的 | 命令 | 注意 |
| --- | --- | --- |
| 看工作区 | `git status` | 每次切支、提交和合并前先看 |
| 看未暂存差异 | `git diff` | 已暂存内容看 cached |
| 看待提交差异 | `git diff --cached` | 检查秘密与无关文件 |
| 同步 main | `git pull --ff-only origin main` | 在 main 且状态明确时使用 |
| 新任务分支 | `git switch -c docs/example` | 从更新后的 main 建立 |
| 切换已有分支 | `git switch 分支名` | 不会自动拉取远程 |
| 暂存指定文件 | `git add 文件路径` | 优先具体文件 |
| 本地提交 | `git commit -m "说明"` | 不等于已经上传 |
| 首次上传任务分支 | `git push -u origin 分支名` | 之后原分支可直接 push |
| 获取远程更新 | `git fetch origin` | 不自动合入当前分支 |
| 任务分支接入 main | `git merge origin/main` | 先保证工作区状态明确 |
| 查看最近提交 | `git log --oneline -8` | 用提交号说明具体版本 |
| 查看远程关联 | `git remote -v` | 不把令牌嵌入 URL |
| 结构与文档检查 | `python scripts/check_repository.py` | Python 3.11+，不是业务测试 |

全组正式约定：一人一账号；一项任务一个短期分支；main 不作为日常编辑分支；先核对再 commit；通过 PR 和必要检查交付；接口与数据变更同步使用方；出错先保护工作再处理；原始数据与秘密不进公开仓库。

## 17. 参考资料

优先使用正文中的官方资料：GitHub flow、协作者邀请、分支保护、身份验证、冲突解决、Actions 令牌，以及 VS Code 源代码管理。界面文字可能随版本变化，动作和协作流程保持相同。

本手册只包含本组仓库与协作的约定。未填写的成员账号、尚未确认的需求和未初始化的应用，都应通过准备任务补齐，不能视为已完成业务功能。
