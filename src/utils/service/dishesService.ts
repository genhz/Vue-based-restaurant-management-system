import {request} from "../axios";

export class DishesService{
    static async getAllDishes(){
        return request('/getAllDishes',{},'get')
    }
    static async getDishesById(id){
        return request('/getDishesById',id,'post')
    }
    static async editDishes(dishes){
        return request('/editDishes',dishes,'post')
    }
    static async removeDishes(id){
        return request('/removeDishes',id,'post')
    }
    //新增
    //更新菜品状态，菜品的state属性，实现方法参考订单
    static async updateDishesState(dishes){
        return request('/updateDishesState',dishes,'post')
    }
    //注意！！！所有的搜索search功能 一定要返回一个列表，一个数据也要存入列表当中
    //输入都是String类型
    //根据菜品名搜索
    static async searchDishesName(name){
        return request('/searchDishesName',name,'post')
    }
    //根据菜品类型搜索
    static async searchDishesType(name){
        return request('/searchDishesType',name,'post')
    }
}