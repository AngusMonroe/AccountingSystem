import Vue from 'vue'
import Router from 'vue-router'
import MainPage from './components/MainPage'
Vue.use(Router)

export default new Router({

  routes: [
    {
      path:"/",
      redirect:"/main"
    },
    {
      path: '/main',
      name: 'main',
      component: () =>import('./components/MainPage.vue'),
      children: [
        {
          path:'',
          component:()=>import('./components/pages/DashBoard.vue')
        },
        {
          path: 'cargoInfo',
          component:() =>import('./components/pages/CargoInfo.vue')
        },
        {
          path: 'stockPurchase',
          component:() =>import('./components/pages/StockPurchase.vue')
        },
        {
          path: 'shipment',
          component:() =>import('./components/pages/Shipment.vue')
        },
        {
          path: 'accountant/cargoInfo',
          component:() =>import('./components/pages/AccountantCargo.vue')
        },
        {
          path: 'accountant/cargoDetail',
          component:() =>import('./components/pages/CargoDetail.vue')
        },
        {
          path:'accountant/generalJournal',
          component:() => import('./components/pages/Journal.vue')
        },
        {
          path:'admin/userList',
          component:()=> import('./components/pages/UserList.vue')
        },
        {
          path:'admin/addUser',
          component:()=> import('./components/pages/AddUser.vue')
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './components/Login.vue')
    }

  ]
})
