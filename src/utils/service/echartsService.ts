import {request} from "../axios";
export class EchartsService{
    static async getWeekIncome(){
        return request('/getWeekIncome',{},'get')
    }
    static async getWeekOrder(){
        return request('/getWeekOrder',{},'get')
    }
    static async getInfor(){
        return request('/getInfor',{},'get')
    }
    //统计各个物料的库存信息
    //object{int:value,String:name} 返回List<object>
    static async getMaterialNamePidService(){
        return request('/getMateialNamePidservice',{},'get')
    }
    //统计每个供应商的食材数量
    //object{int:value,String:name} 返回List<object>
    static async getMaterialSuppliedPidService(){
        return request('/getMateialSuppliedPidservice',{},'get')
    }

    static async getMonthIncome(){
        return request('/getMonthprofit',{},'get')
    }
    static async getMonthExpenses(){
        return request('/getMonthpay',{},'get')
    }
    static async getmonthall(){
        return request('/getmonthall',{},'get')
    }
}