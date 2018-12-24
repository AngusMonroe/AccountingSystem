
import ToastComponent from './Toast.vue'

const Toast = {};

// 注册Toast
Toast.install = function (Vue) {
   

    // 通过Vue的原型注册一个方法
    // 让所有实例共享这个方法 
    Vue.prototype.$toastOld = (msg, duration = 1500) => {
         // 生成一个Vue的子类
        // 同时这个子类也就是组件
        const ToastConstructor = Vue.extend(ToastComponent)
        // 生成一个该子类的实例
        const instance = new ToastConstructor();

        // 将这个实例挂载在我创建的div上
        // 并将此div加入全局挂载点内部
        instance.$mount(document.createElement('div'))
        document.getElementById("msg").appendChild(instance.$el)


        instance.message = msg;
        instance.visible = true;

        setTimeout(() => {
            instance.visible = false;
        }, duration);
    }
    Vue.prototype.$toast = (msg)=>{
        M = require("materialize-css");
        M.toast({html: msg});
    }
}

export default Toast;