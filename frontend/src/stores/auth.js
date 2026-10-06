import { reactive } from 'vue'
import { api } from '../api/mock'

export const auth = reactive({
  isAuthenticated: false, permissionsLoaded: false, user: null, permissions: []
})

export async function login(account, password) {
  const user = await api.login(account, password)
  Object.assign(auth, { isAuthenticated: true, permissionsLoaded: true, user, permissions: user.permissionCodes })
}

export function logout() {
  api.logout()
  Object.assign(auth, { isAuthenticated: false, permissionsLoaded: false, user: null, permissions: [] })
}
