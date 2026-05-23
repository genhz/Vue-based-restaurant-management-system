import { OrderService } from '@/utils/service/orderService'
import { ElMessage } from 'element-plus'

const reception = {
  namespaced: true,
  state() {
    return {
      desk: 0,
      notes: undefined,
      dishes: [],
      car: []
    }
  },
  mutations: {
    setDesk(state, desk) {
      state.desk = desk
    },
    setDishes(state, ls) {
      state.dishes = ls
    },
    setNotes(state, notes) {
      state.notes = notes
    },
    addCar(state, id) {
      let found = false
      for (let i = 0; i < state.car.length; i++) {
        if (state.car[i].id === id) {
          state.car[i].number++
          found = true
          break
        }
      }
      if (!found) {
        let obj = null
        for (let i = 0; i < state.dishes.length; i++) {
          if (state.dishes[i].id === id) {
            obj = { ...state.dishes[i], number: 1 }
            break
          }
        }
        if (obj) {
          state.car.push(obj)
        }
      }
    },
    delCar(state, id) {
      for (let i = 0; i < state.car.length; i++) {
        if (state.car[i].id === id) {
          if (state.car[i].number > 1) {
            state.car[i].number--
          } else {
            state.car.splice(i, 1)
          }
          break
        }
      }
    },
    clearCar(state) {
      state.car = []
      state.notes = undefined
    }
  },
  actions: {
    async submitOrder({ state, commit }, total) {
      const order = {
        id: undefined,
        desk: state.desk,
        data: undefined,
        notes: state.notes,
        total: total,
        dishesList: [...state.car]
      }
      try {
        await OrderService.addMenuOrder(order)
        commit('clearCar')
        ElMessage.success('订单提交成功，有问题请咨询前台')
      } catch (err) {
        ElMessage.error('订单提交失败，请重试')
      }
    }
  },
  getters: {
    getTotal(state) {
      let total = 0
      if (state.car.length !== 0) {
        for (let i = 0; i < state.car.length; i++) {
          total += state.car[i].price * state.car[i].number
        }
      }
      return total
    }
  }
}

export default reception
