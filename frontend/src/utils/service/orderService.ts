import {request} from "../axios";

export class OrderService{
    //传递order类型参数,其中id,日期,在后端赋值,id根据日期生成格式为年月日时分秒,日期为年月日
    //order中不包含编号和日期以及状态,后端生成并为其赋值
    static async addOrder(order){
        return request('/addOrder',order,'post')
    }
    //selectOrder即可
    static async getAllOrder(){
        return request('/getAllOrder',{},'get')
    }
    //select status为true的订单
    static async getfinOrder(){
        return request('/getfinOrder',{},'get')
    }
    //select status为false的订单
    static async getunOrder(){
        return request('/getunOrder',{},'get')
    }
    //根据id删除对应订单,与订单对应的几个菜品也需要清除
    static async removeOrder(id){
        return request('/deleteOrder',id,'post')
    }
    //status已经改好,根据orderID,给Status赋予新值即可
    static async updateOrderStatus(order){
        return request('/updateOrderStatus',order,'post')
    }
    //传递id,后端根据id搜索订单,其中包括订单的菜谱信息存入dishesList
    //selectOrderWithDishes
    static async getOrder(id){
        return request('/getOrderById',id,'post')
    }
    //传递order类型参数,从order中获取id,剩下的依次赋值,其中菜品信息也进行修改,不要忘记修改菜品
    //updateOrderWithDishes
    static async editOrder(order){
        return request('/editOrder',order,'post')
    }

    //从菜单下订单，order部分与addOrder类似，id等需要后端生成，而且还需要将dishesList也存进数据库
    static async addMenuOrder(order){
        return request('/addMenuOrder',order,'post')
    }
    //根据桌号拉取订单信息,对应桌号且状态为false的订单的菜品列表（dishesList）
    static async getOrderByDesk(desk){
        return request('/getOrderByDesk',desk,'post')
    }
}