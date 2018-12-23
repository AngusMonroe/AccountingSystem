import * as UrlMapper from './UrlMapper.js';
import "vue";

const Connector = {};
Connector.install = function (Vue){
    Vue.prototype.$post = function(url,  object,callback,errHandler=null){
        
        this.$http.post(UrlMapper.mapUrl(url), object,{
            "emulateJSON":true,
            "headers":{
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            }
        }).then((response)=>{

            if(response.data.code == 4001)
            {
                callback(response.data.payload);
            }else
            {
                //全局错误处理策略
                let time = 1500;
                switch(response.data.code){
                    case 4002://用户未登录
                        if(response.data.msg.indexOf("login first") != -1){
                            setTimeout(()=>{
                                window.location.href="#/";
                            },2000)
                        }
                        break;
                    
                }
                this.$toast(response.data.msg,time);
            }
        },(response)=>{
            console.log("fail");
            console.log(response);
            
            this.$toast("服务器连接中断");          

        });
    }
}
export default Connector;
