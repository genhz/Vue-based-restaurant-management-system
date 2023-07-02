import {createRouter, createWebHashHistory} from "vue-router";
import store from '../store/index'

const routes=[
    {
        path: '/',
        name:'login',
        meta:{
            title:'欢迎使用荣成餐饮店管理系统',
            roles:['user','admin','counter','procure']
        },
        component:()=>import('../views/login.vue')
    },
    {
        path: '/menu',
        name:'menu',
        meta:{
            title:'菜单',
            roles:['user','admin','counter','procure']
        },
        component:()=>import('../views/reception/menu/index.vue'),
    },
    {
        path: '/myOrder',
        name: 'myOrder',
        meta: {
            title:'我的订单',
            roles:['user','admin','counter','procure']
        },
        component:()=>import('../views/reception/menu/myOrder.vue'),
    },
    {
        path: '/admin',
        name:'Admin',
        component:()=>import('../views/admin/admin.vue'),
        children: [
            {
                path: 'payManagement',
                name:'payManagement',
                meta:{
                    title:'营业统计',
                    roles: ['admin']
                },
                component:()=>import('../views/admin/payManagement.vue')
            },
            {
              path: 'attendanceManagement',
              name: 'attendanceManagement',
              meta:{
                  title:'出勤管理',
                  roles: ['admin']
              },
                component:()=>import('../views/admin/attendanceManagement.vue')
            },
            {
              path: 'editDishes/:id',
              name: 'editDishes',
              meta: {
                  title:'编辑菜品',
                  roles:['admin']
              },
                component:()=>import('../views/admin/editDishes.vue'),
                props: true
            },
            {
                path: 'editPersonnel/:id',
                name:'editPersonnel',
                meta:{
                    title:'员工信息管理',
                    roles:['admin']
                },
                component:()=>import('../views/admin/editPersonnel.vue'),
                props: true
            },
            {
                path: 'addPersonnel',
                name:'addPersonnel',
                meta:{
                    title:'添加员工',
                    roles:['admin']
                },
                component:()=>import('../views/admin/addPersonnel.vue'),
            },
            {
                path:'',
                name:'adminsummary',
                meta:{
                    title:'后台管理系统',
                    roles:['admin']
                },
                component:()=>import('../views/admin/summary.vue'),
            },
            {
                path: 'dishesmanage',
                name: 'dishesmanage',
                meta:{
                    title:'菜品管理',
                    roles:['admin']
                },
                component:()=>import('../views/admin/dishesmanage.vue'),
            },
            {
                path: 'adddishes',
                name: 'adddishesmanage',
                meta:{
                    title:'添加菜品',
                    roles:['admin']
                },
                component:()=>import('../views/admin/adddishesmanage.vue'),
            },
            {
                path: 'staffmanage',
                name:'staffmanage',
                meta:{
                    title:'员工信息管理',
                    roles:['admin']
                },
                component:()=>import('../views/admin/staffmanage.vue'),
            },
            {
                path: 'editMaterial/:id',
                name:'editMaterialAdmin',
                meta:{
                    title:'编辑食材',
                    roles:['procure','admin']
                },
                component:()=>import('../views/procure/editMaterial.vue'),
                props: true
            },
            {
                path: 'addMaterial',
                name:'addMaterialAdmin',
                meta:{
                    title:'添加食材',
                    roles:['procure','admin']
                },
                component:()=>import('../views/procure/addMaterial.vue'),
            },
            {
                path: 'inventorymanage',
                name:'inventorymanageAdmin',
                meta:{
                    title:'库存管理',
                    roles:['procure','admin']
                },
                component:()=>import('../views/procure/inventorymanage.vue'),
            },
        ]
    },
    {
        path: '/procure',
        name:'procure',
        component:()=>import('../views/procure/procure.vue'),
        children: [
            {
                path: '',
                name:'procureSummary',
                meta:{
                    title:'库存管理',
                    roles:['procure']
                },
                component:()=>import('../views/procure/procureSummary.vue')
            },
            {
                path: 'HistoricalPurchasing',
                name: 'HistoricalPurchasing',
                meta:{
                    title:'历史采购',
                    roles:['procure']
                },
                component:()=>import('../views/procure/HistoricalPurchasing.vue')
            },
            {
                path: 'planning',
                name: 'planning',
                meta:{
                    title:'采购计划',
                    roles:['procure']
                },
                component:()=>import('../views/procure/planning.vue')
            },
            {
                path: 'warehousing',
                name: 'warehousing',
                meta:{
                    title:'入库管理',
                    roles:['procure']
                },
                component:()=>import('../views/procure/warehousing.vue')
            },
            {
                path: 'editMaterial/:id',
                name:'editMaterial',
                meta:{
                    title:'编辑食材',
                    roles:['procure','admin']
                },
                component:()=>import('../views/procure/editMaterial.vue'),
                props: true
            },
            {
                path: 'addMaterial',
                name:'addMaterial',
                meta:{
                    title:'添加食材',
                    roles:['procure','admin']
                },
                component:()=>import('../views/procure/addMaterial.vue'),
            },
            {
                path: 'inventorymanage',
                name:'inventorymanage',
                meta:{
                    title:'库存管理',
                    roles:['procure','admin']
                },
                component:()=>import('../views/procure/inventorymanage.vue'),
            },
        ]
    },
    {
        path: '/counter',
        name:'Counter',
        component:()=>import('../views/reception/counter/counter.vue'),
        // meta:{
        //     roles:['counter']
        // },
        children:[

            {
                path:'',
                name:'summary',
                meta:{
                    title:'前台管理系统',
                    roles:['counter']
                },
                component:()=>import('../views/reception/counter/summary.vue')
            },
            {
                path: 'orderList',
                name:'orderList',
                meta:{
                    title:'订单列表',
                    roles:['counter']
                },
                component:()=>import('../views/reception/counter/orderList.vue'),

            },
            {
                path: 'unOrderList',
                name:'unOrderList',
                meta:{
                    title:'订单列表',
                    roles:['counter']
                },
                component:()=>import('../views/reception/counter/unOrderList.vue')
            },
            {
                path: 'finOrderList',
                name:'finOrderList',
                meta:{
                    title:'历史订单',
                    roles:['counter']
                },
                component:()=>import('../views/reception/counter/finOrderList.vue')
            },
            {
                path:'editOrder/:id',
                name:'editOrder',
                meta:{title:'编辑订单',
                    roles:['counter']
                },
                component:()=>import('../views/reception/counter/editOrder.vue'),
                props:true
            }
        ]
    }
]
const router=createRouter({
    history:createWebHashHistory(),
    routes
})

// const role='procure'
// const role='counter'
router.beforeEach((to, from,next) => {
    const role=store.state.userStore.user.role
    document.title=to.meta.title
    if(to.meta.roles.includes(role)){
        next()	//放行
    }else{
        console.log(role)
        console.log('无权限，已重定向')
        next({path:"/"})
    }
})
export default router