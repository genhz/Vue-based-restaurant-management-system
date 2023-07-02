import {createStore} from "vuex";
import reception from "@/store/modules/reception";
import userStore from "@/store/modules/user";

const store=createStore({
    modules:{
        reception,
        userStore
    }
})
export default store