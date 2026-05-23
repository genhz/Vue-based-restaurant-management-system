import {request} from "../axios";

export class MenuService{
    static async getAllMenu(){
        return request('/getAllDishes',{},'get')
    }
}