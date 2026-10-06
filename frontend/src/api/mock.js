const users = [
  { id: 'user-001', name: '系统管理员', employeeNo: 'E001', branch: '总公司', departmentId: 'dept-01', departmentName: '办公室', position: '系统管理员', status: 'active', accountType: 'super-admin', phone: '13800000001', roles: ['超级管理员'], permissionCodes: ['rbac:user:read', 'rbac:user:create', 'rbac:role:read', 'rbac:permission:read'] },
  { id: 'user-002', name: '李文', employeeNo: 'E002', branch: '总公司', departmentId: 'dept-02', departmentName: '人事部', position: '部门管理员', status: 'active', accountType: 'department-admin', phone: '13800000002', roles: ['人事部管理员'], permissionCodes: ['rbac:user:read', 'rbac:department:read'] },
  { id: 'user-003', name: '王强', employeeNo: 'E003', branch: '总公司', departmentId: 'dept-03', departmentName: '财务部', position: '会计', status: 'inactive', accountType: 'employee', phone: '13800000003', roles: ['财务查看员'], permissionCodes: ['finance:report:read'] },
  { id: 'user-004', name: '赵敏', employeeNo: 'E004', branch: '总公司', departmentId: 'dept-02', departmentName: '人事部', position: '人事专员', status: 'active', accountType: 'employee', phone: '13800000004', roles: ['普通员工'], permissionCodes: ['hr:self:read'] },
  { id: 'user-005', name: '陈工', employeeNo: 'OPS001', branch: '总公司', departmentId: 'dept-07', departmentName: '工程技术部', position: '临时运维人员', status: 'active', accountType: 'temporary-ops', phone: '13800000005', roles: ['临时运维查看员'], permissionCodes: ['ops:system-status:read', 'ops:log:read'] }
]

const departments = [
  { id: 'dept-root', name: '总公司', type: 'company', manager: '薛峰', branch: '总公司', parentName: '', path: '总公司', employeeCount: 120, children: [
      { id: 'dept-01', name: '办公室', type: 'department', manager: '薛峰', parentName: '总公司', branch: '总公司', path: '总公司 / 办公室', employeeCount: 12, children: [] },
      { id: 'dept-02', name: '人力资源部', type: 'department', manager: '曹瑞', parentName: '总公司', branch: '总公司', path: '总公司 / 人力资源部', employeeCount: 18, children: [] },
      { id: 'dept-03', name: '财务部', type: 'department', manager: '薛福', parentName: '总公司', branch: '总公司', path: '总公司 / 财务部', employeeCount: 21, children: [] },
      { id: 'branch-road', name: '道路工程分公司', type: 'branch', manager: '曾禄', parentName: '总公司', branch: '道路工程分公司', path: '总公司 / 道路工程分公司', employeeCount: 69, children: [
          { id: 'dept-road-office', name: '综合办公室', type: 'department', manager: '卢晓超', parentName: '道路工程分公司', branch: '道路工程分公司', path: '总公司 / 道路工程分公司 / 综合办公室', employeeCount: 16, children: [] },
          { id: 'dept-road-tech', name: '工程技术部', type: 'department', manager: '苏武瑞', parentName: '道路工程分公司', branch: '道路工程分公司', path: '总公司 / 道路工程分公司 / 工程技术部', employeeCount: 53, children: [] }
        ] },
      { id: 'branch-water', name: '水务运营分公司', type: 'branch', manager: '肖志华', parentName: '总公司', branch: '水务运营分公司', path: '总公司 / 水务运营分公司', employeeCount: 44, children: [
          { id: 'dept-water-ops', name: '运营管理部', type: 'department', manager: '陆永', parentName: '水务运营分公司', branch: '水务运营分公司', path: '总公司 / 水务运营分公司 / 运营管理部', employeeCount: 44, children: [] }
        ] }
    ] }
]

function flattenDepartments(nodes, result = []) {
  nodes.forEach(node => { result.push(node); if (node.children?.length) flattenDepartments(node.children, result) })
  return result
}
const departmentOptions = flattenDepartments(departments).filter(item => item.type === 'department')

const roleOptions = [
  { id: 'role-employee', name: '普通员工', permissionCodes: ['hr:self:read'] },
  { id: 'role-department-admin', name: '部门管理员', permissionCodes: ['rbac:user:read', 'rbac:user:create', 'rbac:user:update', 'rbac:department:read'] },
  { id: 'role-ops-reader', name: '临时运维查看员', permissionCodes: ['ops:system-status:read', 'ops:log:read'] },
  { id: 'role-finance-reader', name: '财务查看员', permissionCodes: ['finance:report:read'] },
  { id: 'role-document-reader', name: '公文查看员', permissionCodes: ['oa:document:read'] },
  { id: 'role-document-editor', name: '公文编辑员', permissionCodes: ['oa:document:read', 'oa:document:edit'] },
  { id: 'role-admin', name: '超级管理员', permissionCodes: ['rbac:user:read', 'rbac:user:create', 'rbac:user:update', 'rbac:department:read', 'rbac:role:read', 'rbac:permission:read'] }
]

// 演示密码仅保存在本次页面运行内存中，不存入用户列表或浏览器存储。
const passwords = new Map(users.map(user => [user.id, '123456']))
let currentUser = null
const publicUser = user => ({ ...user, roles: [...user.roles], permissionCodes: [...user.permissionCodes] })
function inScope(user) { return currentUser?.roleIds.includes('role-admin') || user.departmentId === currentUser?.departmentId }
function requirePermission(code) {
  if (!currentUser || currentUser.status !== 'active') throw new Error('请重新登录')
  if (!currentUser.permissionCodes.includes(code)) throw new Error('没有执行此操作的权限')
}
users.forEach(user => {
  user.roleIds = user.accountType === 'super-admin' ? ['role-admin'] : user.accountType === 'department-admin' ? ['role-department-admin'] : user.accountType === 'temporary-ops' ? ['role-ops-reader'] : user.id === 'user-003' ? ['role-finance-reader'] : ['role-employee']
  if (user.id === 'user-002') user.roleIds.push('role-employee')
  user.accountType = user.accountType === 'temporary-ops' ? 'temporary-ops' : 'employee'
  if (user.id === 'user-001') user.position = '信息技术专员'
  if (user.id === 'user-002') user.position = '人事专员'
  if (user.id === 'user-005') user.position = '运维工程师'
  const roles = roleOptions.filter(role => user.roleIds.includes(role.id))
  user.roles = roles.map(role => role.name)
  user.permissionCodes = [...new Set(roles.flatMap(role => role.permissionCodes))]
  if (user.departmentId === 'dept-02') user.departmentName = '人力资源部'
  if (user.id === 'user-005') { user.departmentId = 'dept-road-tech'; user.branch = '道路工程分公司' }
})

export function demoMode() {
  return new URLSearchParams(window.location.search).get('demo') || 'normal'
}

const wait = (value, ms = 350) => new Promise(resolve => setTimeout(() => resolve(value), ms))
const demoResponse = async (value) => {
  const mode = demoMode()
  if (mode === 'error') { await wait(null, 350); throw new Error('演示请求失败') }
  if (mode === 'loading') return wait(value, 1800)
  if (mode === 'empty') return wait(Array.isArray(value) ? [] : { items: [], total: 0 })
  return wait(value)
}
export const api = {
  async login(account, password) {
    await wait(null)
    const user = users.find(item => item.employeeNo === account.trim() || item.phone === account.trim())
    if (!user || passwords.get(user.id) !== password) throw new Error('员工号或手机号不存在，或密码错误')
    if (user.status !== 'active') throw new Error('账号已停用，请联系管理员')
    currentUser = user
    return publicUser(user)
  },
  logout() { currentUser = null },
  async getOverview() { requirePermission('rbac:user:read'); const visible = users.filter(inScope); return demoResponse({ totalUsers: visible.length, temporaryUsers: visible.filter(user => user.accountType === 'temporary-ops').length, totalRoles: roleOptions.length }) },
  async listUsers() { requirePermission('rbac:user:read'); const visible = users.filter(inScope); return demoResponse({ items: visible.map(publicUser), total: visible.length }) },
  async createUser(payload) {
    requirePermission('rbac:user:create')
    if (!inScope(payload)) throw new Error('只能创建本部门账号')
    if (typeof payload.initialPassword !== 'string' || payload.initialPassword.length < 6 || payload.initialPassword.length > 64) throw new Error('初始密码须为 6–64 位')
    if (payload.roleIds?.includes('role-admin')) throw new Error('系统只保留一个超级管理员账号，不能新增或授予此角色')
    if (!['employee', 'temporary-ops'].includes(payload.accountType)) throw new Error('请选择员工账号或临时运维账号')
    if (users.some(item => item.employeeNo === payload.employeeNo)) throw new Error('员工号已存在，请使用唯一员工号')
    if (users.some(item => item.phone === payload.phone)) throw new Error('手机号已存在，请使用唯一手机号')
    if (!Array.isArray(payload.roleIds) || payload.roleIds.some(id => !roleOptions.some(role => role.id === id))) throw new Error('请选择有效角色')
    const roleIds = [...new Set(payload.roleIds)]
    const assignedRoles = roleOptions.filter(role => roleIds.includes(role.id))
    const { initialPassword, ...fields } = payload
    const created = { ...fields, status: fields.status || 'active', roleIds, id: `user-${String(users.length + 1).padStart(3, '0')}`, roles: assignedRoles.map(role => role.name), permissionCodes: [...new Set(assignedRoles.flatMap(role => role.permissionCodes))].sort() }
    const response = await demoResponse({ ...created })
    users.push(created)
    passwords.set(created.id, initialPassword)
    return response
  },
  async updateUserStatus(id, status) {
    requirePermission('rbac:user:update')
    const user = users.find(item => item.id === id)
    if (!user) throw new Error('用户不存在')
    if (user.roleIds.includes('role-admin')) throw new Error('唯一超级管理员账号不能停用')
    if (!inScope(user)) throw new Error('不能修改其他部门账号')
    if (user.id === currentUser.id) throw new Error('不能停用当前登录账号')
    const response = await demoResponse({ ...user, status })
    user.status = status
    return response
  },
  async listDepartments() { requirePermission('rbac:department:read'); return demoResponse(currentUser.roleIds.includes('role-admin') ? departments : departmentOptions.filter(item => item.id === currentUser.departmentId)) },
  roleOptions: roleOptions.filter(role => role.id !== 'role-admin'),
  departmentOptions
}
