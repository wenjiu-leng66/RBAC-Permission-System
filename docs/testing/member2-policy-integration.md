# 成员 2：Vue 路由接入成员 3 权限策略

## 接入范围

`frontend/src/policy-adapter.js` 加载成员 3 原有经典脚本，将 Vue 的
`isAuthenticated` 映射为策略要求的 `authenticated`，同时传入
`permissionsLoaded`、用户 ID 和有效权限。路由守卫调用 `canVisit`，
侧边栏调用 `visibleRoutes`。未登录跳转登录页并保留目标地址；已登录
但缺少权限或权限未加载时跳转 `/403`。

`/403` 和未知地址的 404 页面只要求登录，以免错误页面产生重定向循环。
当前未迁移成员 3 的 HTML、CSS 和页面交互，`/roles` 仍为占位页。
按钮判断、共享角色数据及授权变化后的权限刷新尚待后续接入。
前端判断不能替代后端授权。

## 验收方法

在 `frontend` 下运行 `npm run dev`，使用终端显示的地址。

1. 未登录直接访问 `/roles`，应进入 `/login?redirect=/roles`。
2. 使用 E001 / 123456 登录，应进入角色权限占位页；用户、部门页面可正常访问。
3. 退出后直接访问 `/roles`，使用 E002 / 123456 登录，应进入 `/403`；侧边栏不显示角色权限入口。
4. E002 点击“返回工作台”，应正常进入工作台，用户与部门页面可正常访问。
5. 权限未加载时不得放行，相关行为由下面的接入测试覆盖。

当前会话只保存在内存，浏览器刷新会退出登录；验证受限目标可使用
“未登录访问目标地址 → 登录 → 回到目标地址”的流程。

在仓库根目录运行：

```sh
node --test tests/frontend/rbac0-policy.test.cjs tests/frontend/policy-router.test.cjs
```

在 `frontend` 下运行 `npm run build`，确认生产构建能加载策略。
