import {request} from "../axios";


export class PersonnelService{
    static async getAllPersonnel(){
        return request('/getAllPersonnel',{},'get')
    }
    static async getPersonnelById(id){
        return request('/getPersonnelById',id,'post')
    }
    static async editPersonnel(Personnel){
        return request('/editPersonnel',Personnel,'post')
    }
    static async removePersonnel(id){
        return request('/removePersonnel',id,'post')
    }
    static async addPersonnel(Personnel){
        return request('/addPersonnel',Personnel,'post')
    }

    static async login(Personnel){
        return request('/login',Personnel,'post')
    }
    //新添加
    //注意！！！所有的搜索search功能 一定要返回一个列表，一个数据也要存入列表当中
    //输入都是String类型
    //输入姓名搜索
    static async searchPersonnelName(name){
        return request('/searchPersonnelName',name,'post')
    }

    //输入职位搜索，不是权限
    static async searchPersonnelType(name){
        return request('/searchPersonnelType',name,'post')
    }
    //出勤list为数组了,包含出勤的几个员工
    //如果今天已经登记过了,返回false
    //没有返回true
    static async Attendance(list){
        return request('/Attendance',list,'post')
    }
}