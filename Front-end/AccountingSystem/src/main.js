import Vue from 'vue'
import App from './App.vue'
import router from './router'
import Toast from './util/Toast/Toast'
import Connector from './util/ErpConnection'
import VueResource from 'vue-resource'

Vue.config.productionTip = false;

Vue.use(Toast);
Vue.use(Connector);
Vue.use(VueResource);
//require('./materialize');

  


new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
