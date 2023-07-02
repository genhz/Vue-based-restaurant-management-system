import {createStore} from "vuex";
import {OrderService} from "@/utils/service/orderService";
import {ElMessage} from "element-plus";

const reception=({
    namespaced:true,
    state(){
        return{
            desk:0,
            notes:undefined,
            dishes:[],
            car:[],
        }
    },
    mutations:{
        setDesk(state,desk){
            state.desk=desk
        },
        setDishes(state,ls){
            state.dishes=ls
            console.log(state.dishes)
        },
        addCar(state,id){
            var fa=true
            for (var i=0;i<state.car.length;i++){
                if (state.car[i].id==id){
                    state.car[i].number++
                    fa=false
                }
            }
            if (fa){
                for (var i=0;i<state.dishes.length;i++){
                    if (state.dishes[i].id==id){
                        var obj=state.dishes[i]
                        obj.number=1
                    }
                }
                state.car.push(obj)
            }
        },
        delCar(state,id){
            for(var i=0;i<state.car.length;i++){
                if ((state.car[i].id==id)&&(state.car[i].number>1)){
                    state.car[i].number--
                }else if ((state.car[i].id==id)&&(state.car[i].number==1)){
                    console.log(state.dishes[i].id)
                    state.car.splice(i,1)
                    console.log(111)
                }
            }
        },
        addOrder(state,total){
            const order = {
                id:undefined,
                desk: total.desk,
                data:undefined,
                notes: total.notes,
                total: total.total,
                dishesList: state.car,
            };
            console.log(order)
            OrderService.addMenuOrder(order).then(()=>{
                state.car=[]
                state.notes=undefined
                ElMessage.success('订单提交成功，有问题请咨询前台');
            })
        }
    },
    getters:{
        getTolal(state){
            let tolal=0
            if (state.car.length!=0){
                for(var i=0;i<state.car.length;i++){
                    tolal+=state.car[i].price*state.car[i].number
                }
            }
            return tolal
        }
    }
})

export default reception