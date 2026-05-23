import { getUser, setUser, removeUser, isAuthenticated } from '@/utils/auth'

const savedUser = getUser()

const userStore = {
  namespaced: true,
  state() {
    return {
      user: savedUser || {
        role: 'user',
        p_name: ''
      }
    }
  },
  getters: {
    role(state) {
      return state.user.role
    },
    pName(state) {
      return state.user.p_name
    },
    isLoggedIn() {
      return isAuthenticated()
    }
  },
  mutations: {
    setUser(state, user) {
      state.user = { ...user }
      setUser(user)
    },
    setRole(state, role) {
      state.user.role = role
    },
    logout(state) {
      state.user = { role: 'user', p_name: '' }
      removeUser()
    }
  }
}

export default userStore
