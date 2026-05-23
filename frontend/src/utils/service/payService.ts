import {request} from "../axios";
export class PayService{
    static async getAllPay(){
        return request('/getAllPay',{},'get')
    }
    static async getAllProfit(){
        return request('/getAllProfit',{},'get')
    }
    static async getOrderById(id){
        return request('/getProfitId',id,'post')
    }
    static async getPurchasingById(id){
        return request('/getPayIdprocure',id,'post')
    }
    static async getSalaryById(id){
        return request('/getPayIdsalary',id,'post')
    }
}