export function mapUrl(inputUrl){
        
        // var prefix = "http://localhost:5000";   //调试
        
        var prefix = "http://"+window.location.host;    //部署

        var suffix = "";

        var m = new Map([
            //模板：
            //['',''],
            
        ]);
        var  res = prefix+m.get(inputUrl)+suffix;
        return res;
};
