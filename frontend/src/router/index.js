import { createRouter, createWebHistory } from 'vue-router'
import { auth } from '../stores/auth'
import LoginView from '../views/LoginView.vue'
import DashboardView from '../views/DashboardView.vue'
import UsersView from '../views/UsersView.vue'
import DepartmentsView from '../views/DepartmentsView.vue'
import NotFoundView from '../views/NotFoundView.vue'
import ForbiddenView from '../views/ForbiddenView.vue'
import RoleIntegrationView from '../views/RoleIntegrationView.vue'

const routes = [
  { path: '/login', component: LoginView, meta: { public: true } },
  { path: '/', redirect: '/dashboard' },
  { path: '/dashboard', component: DashboardView, meta: { title: '管理首页', permission: 'rbac:user:read' } },
  { path: '/users', component: UsersView, meta: { title: '用户管理', permission: 'rbac:user:read' } },
  { path: '/departments', component: DepartmentsView, meta: { title: '部门架构', permission: 'rbac:department:read' } },
  { path: '/403', component: ForbiddenView, meta: { title: '无权访问' } },
  { path: '/roles', component: RoleIntegrationView, meta: { title: '角色权限', permission: 'rbac:role:read' } },
  // 成员3的角色、权限、继承和约束页面从这里扩展，不重复实现。
  { path: '/:pathMatch(.*)*', component: NotFoundView }
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to) => {
  if (to.meta.public) return auth.isAuthenticated ? '/dashboard' : true
  if (!auth.isAuthenticated) return { path: '/login', query: { redirect: to.fullPath } }
  if (to.meta.permission && !auth.permissions.includes(to.meta.permission)) return '/403'
  return true
})

export default router
