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
      component: MainPage,
      children: [
        {
          path: 'cargoInfo',
          component:() =>import('./components/pages/CargoInfo.vue')
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
