import {request} from "../axios";

export class MaterialService{
    static async getAllMaterial(){
        return request('/getAllMaterial',{},'get')
    }
    static async getAllMaterialById(id){
        return request('/getAllMaterialById',id,'post')
    }
    static async addMaterial(material){
        console.log(material)
        return request('/addMaterial',material,'post')
    }
    static async editMaterial(material){
        return request('/editMaterial',material,'post')
    }
    static async removeMaterial(id){
        return request('/removeMaterial',id,'post')
    }

    //新增
    //返回采购计划，返回List<Material>,扫描数据库，如果发现库存低于安全库存，则将其全部属性填到Material对象中并将其存入List<Material>，最后返回
    static async getPlanning(){
        return request('/getPlanning',{},'get')
    }
    //输入的参数是一个List<Material>列表，里面保存着入库信息，其中每个Material的number属性存着进货数，操作数据库将number和现有库存相加即可，将所有的Material的number都与其对应的库存相加并存入数据库当中
    static async commitWarehousing(Warehousing){
        return request('/commitWarehousing',Warehousing,'post')
    }
    //注意！！！所有的搜索search功能 一定要返回一个列表，一个数据也要存入列表当中
    //输入都是String类型
    //根据食材名搜索
    static async searchMaterialName(name){
        return request('/searchMaterialName',name,'post')
    }
    //根据供应商搜索
    static async searchMaterialSupplier(name){
        return request('/searchMaterialSupplier',name,'post')
    }

    static async getProcurelist(){
        return request('/getProcurelist',{},'get')
    }

    static async getProcurelistmonth(data){
        return request('/getProcurelistmonth',data,'post')
    }

}