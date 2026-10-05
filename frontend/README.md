# 前端

主责：成员 2、成员 3。成员 3 的 RBAC0 交互原型与可复用权限判断模块已准备；正式 Vue / React 应用尚未初始化，没有 `package.json`，不能执行 npm 开发或构建命令。

## 成员 3 的第一阶段交付

- [打开交互原型](prototypes/member3-rbac0.html)：角色授权、资源目录、用户角色分配、导航和按钮权限演示。下载仓库后双击 HTML 即可使用，不调用真实后端。
- [原型操作说明](prototypes/README.md)：演示入口、状态模拟与范围。
- [设计与成员衔接](../docs/design/member3-rbac0-design.md)：页面规则、权限码及后续阶段边界。
- [接口使用方提案](../api/proposals/member3-rbac0-consumer-needs.md)：供成员 4、6 统一接口约定，不替代权威 OpenAPI。
- [成员 3 解释文档](../docs/guides/member3-rbac0-explained.md)：逐步解释完成内容与如何演示。

权限判断核心位于 [rbac0-policy.js](src/authorization/rbac0-policy.js)。在仓库根目录运行 `node --test tests/frontend/rbac0-policy.test.cjs` 验证拒绝、角色并集与路由判定；无需 npm 安装依赖。

原型使用明确标记的合成数据，保存仅影响本次浏览器页面，刷新或重置恢复。正式应用应从后端获取身份及有效权限，不能信任本地角色集合来做服务器授权。

待团队确认后初始化 Vue 3 或已经熟悉的 React；统一 Node.js、包管理器、锁文件和 UI 规范。两位前端在同一个项目中协作，不分别建立两个前端应用。

| 目录 | 用途 |
| --- | --- |
| `src/api/` | 统一 HTTP 客户端与接口调用 |
| `src/components/` | 可复用组件 |
| `src/views/` | 登录、用户、角色、层级、约束等页面 |
| `src/router/` | 页面路由与导航守卫 |
| `src/stores/` | 当前用户、激活角色、界面状态 |
| `src/assets/` | 可提交的静态资源 |
| `src/styles/` | 公共样式和规范 |

遵守 [接口约定](../docs/standards/api-contract.md)。开发时通过 `/api` 代理连接所选后端。前端隐藏按钮不代替后端授权，切换后端时明确会话隔离。
