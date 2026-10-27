# 界面原型

Vue 3、Vue Router、Element Plus 构建的第一阶段完整界面原型。原型通过 `src/api/mock.js` 操作浏览器内存合成数据，使用共享 RBAC0 策略控制菜单、路由、按钮与数据范围；真实后端另通过统一 API 验收。

## 构建与运行

```bash
npm --prefix frontend ci
npm --prefix frontend run dev
npm --prefix frontend run build
```

开发地址以 Vite 输出为准，默认 `http://localhost:5173`。生产产物为 `frontend/dist/`，包含一期入口与静态 OpenAPI 文件，由 Nginx 发布。Node 22+，依赖版本以锁文件为准。

## 页面与身份

门户、用户、部门、角色、授权、资源目录、员工台账和个人权限组成一期页面。完整路由、身份与功能边界见[界面说明](../docs/design/stage1-ui-prototype.md)。登录页提供合成身份，原型固定密码为 `123456`；此密码与真实后端环境口令无关。

原型刷新后业务内存数据恢复初始值；`sessionStorage` 只保留模拟身份编号。角色继承、约束、激活角色及审计页面为后续阶段静态内容，不执行真实保存。

## 关键代码

| 位置 | 职责 |
| --- | --- |
| `src/authorization/rbac0-policy.js` | 默认拒绝、精确权限匹配、角色并集、范围合并与目标判断 |
| `src/policy-adapter.js`、`src/router/index.js` | Vue 会话适配、路由权限和首次改密跳转 |
| `src/stores/auth.js` | 模拟登录身份及权限状态 |
| `src/api/mock.js` | 原型接口、内存读写、版本冲突及拒绝规则 |
| `src/views/` | 页面、筛选分页、授权草稿和操作状态 |
| `scripts/` | 目录生成及构建时复制一期发布材料 |

```bash
node --test tests/frontend/*.test.cjs
```

测试从仓库根目录执行，验证原型策略与模拟 API；真实 Cookie、Java 事务、MySQL 持久化按[一期验收](../docs/stage1/self-test.md)另行执行。
