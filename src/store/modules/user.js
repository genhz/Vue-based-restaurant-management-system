
const userStore=({
    namespaced:true,
    state(){
        return{
            user:{
                role:'user',
                p_name:''
            }
        }
    },
    mutations:{
        setUser(state,user){
            console.log(user)
            state.user=user
        },
        setRole(state,role){
            console.log(role)
            state.user.role=role
        }
    },

})

export default userStore