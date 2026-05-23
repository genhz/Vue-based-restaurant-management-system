const TOKEN_KEY = 'restaurant_token'
const USER_KEY = 'restaurant_user'

const DEFAULT_TOKEN = '80c483d59ca86ad0393cf8a98416e2a1'

export function getToken() {
  return localStorage.getItem(TOKEN_KEY) || DEFAULT_TOKEN
}

export function setToken(token) {
  localStorage.setItem(TOKEN_KEY, token)
}

export function removeToken() {
  localStorage.removeItem(TOKEN_KEY)
}

export function getUser() {
  const raw = localStorage.getItem(USER_KEY)
  if (raw) {
    try {
      return JSON.parse(raw)
    } catch {
      return null
    }
  }
  return null
}

export function setUser(user) {
  localStorage.setItem(USER_KEY, JSON.stringify(user))
}

export function removeUser() {
  localStorage.removeItem(USER_KEY)
}

export function isAuthenticated() {
  return !!getUser()
}
