export function mapUrl(inputUrl,debug=true){
        
        // var prefix = "http://localhost:5000";   //调试
        
        var prefix = "http://"+window.location.host;    //部署

        var suffix = "";

        var m = new Map([
            //模板：
            //['',''],
            ['login','/user/login'],
            ['logout','/user/logout'],
            ['item_query','/item/query'],
            ['item_sell','/item/sell'],
            ['item_buy','/item/buy'],
            ['item_getList','/item/getList'],
            ['item_getInfo','/accountant/item/getInfo'],
            ['getSummary','/accaountant/getSummary'],
            ['admin_getUserList','/admin/getUserList'],
            ['admin_removeUser','/admin/removeUser'],
            ['admin_addUser','/admin/addUser'],
            ['item_add','/item/add']
            
            
        ]);
        var  res = prefix+m.get(inputUrl)+suffix;
        if(debug) 
        {
            return null;
        }else{
            return res;
        }
        
};

export function mockResult(inputUrl){
    var m = new Map([
        ['login',{
            "code":200,  //状态码
            "msg":"OK",
            "data":{
                "username":"tom",
                "kind":"seller"   //用户类型: seller:售货员 buyer:采购员 accountant 会计   admin管理员
            }
        }],
        ['logout',{
            "code":200,  //状态码
            "msg":"OK",
            "data":{
               
            }
        }],
        ['item_query',{
            "code":200,  //状态码
            "msg":"OK",
            "data":{
                "result":[
                    {
                        "id":123455,
                        "name":"ebook",
                        "price":12.5,
                        "amount":12
                    }
                ]
            }
        }],
        ['item_sell',{
            "code":200,  //状态码
            "msg":"OK",
            "data":{}
        }],
        ['item_buy',{
            "code":200,  //状态码
            "msg":"OK",
            "data":{}
        }],
        ['item_getList',{
            "code":200,  //状态码
            "msg":"OK",
            "data":{
                "items":[
                    {
                        "id":123455,
                        "name":"ebook",
                        "price":12.5,
                        "amount":12
                    },
                    {
                        "id":12356,
                        "name":"ebook-2",
                        "price":18,
                        "amount":7
                    }
                ]
            }
        }],
        ['item_getInfo',{
            "code":200,  //状态码
            "msg":"OK",
            "data":{
                "item":{
                    "id":123456,
                    "name":"Mac Pro",
                    "price":12.3,
                    "amount":2,
                    "transaction":[
                        {
                            "id":123345345,
                            "kind":"in",
                            "amount":12,
                            "totalPrice":123.2,
                            "time":"2012-11-20",
                            "operator":{    //操作者
                                "id":123,
                                "name":"tom"
                            }
                        },
                        {
                            "id":123345346,
                            "kind":"out",
                            "amount":2,
                            "totalPrice":1232.2,
                            "time":"2012-11-21",
                            "operator":{    //操作者
                                "id":124,
                                "name":"jerry"
                            }
                        }
                    ],
                    "sum":123.23   //总盈亏
                }
            }
        }],
        ['getSummary',{
            "code":200,  //状态码
            "msg":"OK",
            "data":{
                "detailList":[
                    {
                        "date":"2012-11-20",
                        "sum":12
                    }
                ]
            }
        }],
        ['admin_getUserList',{
            "code":200,  //状态码
            "msg":"OK",
            "data":{
                "userList":[
                    {
                       "id":123,
                       "kind":"admin",
                       "name":"tom"
                    }
                ]
            }
        }],
        ['admin_removeUser',{
            "code":200,  //状态码
            "msg":"OK",
            "data":{
               
            }
        }],
        ['admin_addUser',{
            "code":200,  //状态码
            "msg":"OK",
            "data":{
                
            }
        }],
        ['item_add',{
            "code":200,  //状态码
            "msg":"OK",
            "data":{
               
            }
        }]
    ]);
    return m.get(inputUrl);
}
