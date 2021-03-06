# 前后端通信协议
- [前后端通信协议](#%E5%89%8D%E5%90%8E%E7%AB%AF%E9%80%9A%E4%BF%A1%E5%8D%8F%E8%AE%AE)
  - [说明](#%E8%AF%B4%E6%98%8E)
  - [* 前后端通信采用统一返回json格式，请求格式为form-data，返回格式如下面API所定义](#%E5%89%8D%E5%90%8E%E7%AB%AF%E9%80%9A%E4%BF%A1%E9%87%87%E7%94%A8%E7%BB%9F%E4%B8%80%E8%BF%94%E5%9B%9Ejson%E6%A0%BC%E5%BC%8F%E8%AF%B7%E6%B1%82%E6%A0%BC%E5%BC%8F%E4%B8%BAform-data%E8%BF%94%E5%9B%9E%E6%A0%BC%E5%BC%8F%E5%A6%82%E4%B8%8B%E9%9D%A2api%E6%89%80%E5%AE%9A%E4%B9%89)
  - [用户登陆](#%E7%94%A8%E6%88%B7%E7%99%BB%E9%99%86)
    - [地址: /user/login](#%E5%9C%B0%E5%9D%80-userlogin)
    - [方式：POST](#%E6%96%B9%E5%BC%8Fpost)
    - [权限：所有人](#%E6%9D%83%E9%99%90%E6%89%80%E6%9C%89%E4%BA%BA)
    - [参数](#%E5%8F%82%E6%95%B0)
    - [响应](#%E5%93%8D%E5%BA%94)
  - [用户登出](#%E7%94%A8%E6%88%B7%E7%99%BB%E5%87%BA)
    - [地址： /user/logout](#%E5%9C%B0%E5%9D%80-userlogout)
    - [方式：POST](#%E6%96%B9%E5%BC%8Fpost-1)
    - [权限：所有人](#%E6%9D%83%E9%99%90%E6%89%80%E6%9C%89%E4%BA%BA-1)
    - [参数](#%E5%8F%82%E6%95%B0-1)
    - [响应](#%E5%93%8D%E5%BA%94-1)
  - [查询货物名称库存列表](#%E6%9F%A5%E8%AF%A2%E8%B4%A7%E7%89%A9%E5%90%8D%E7%A7%B0%E5%BA%93%E5%AD%98%E5%88%97%E8%A1%A8)
    - [地址： /item/query](#%E5%9C%B0%E5%9D%80-itemquery)
    - [方式：POST](#%E6%96%B9%E5%BC%8Fpost-2)
    - [权限：所有人](#%E6%9D%83%E9%99%90%E6%89%80%E6%9C%89%E4%BA%BA-2)
    - [参数](#%E5%8F%82%E6%95%B0-2)
    - [响应](#%E5%93%8D%E5%BA%94-2)
  - [卖出货物](#%E5%8D%96%E5%87%BA%E8%B4%A7%E7%89%A9)
    - [地址： /item/sell](#%E5%9C%B0%E5%9D%80-itemsell)
    - [方式：POST](#%E6%96%B9%E5%BC%8Fpost-3)
    - [权限：售货员|管理员](#%E6%9D%83%E9%99%90%E5%94%AE%E8%B4%A7%E5%91%98%E7%AE%A1%E7%90%86%E5%91%98)
    - [参数](#%E5%8F%82%E6%95%B0-3)
    - [响应](#%E5%93%8D%E5%BA%94-3)
  - [买入货物](#%E4%B9%B0%E5%85%A5%E8%B4%A7%E7%89%A9)
    - [地址： /item/buy](#%E5%9C%B0%E5%9D%80-itembuy)
    - [方式：POST](#%E6%96%B9%E5%BC%8Fpost-4)
    - [权限：采购员|管理员](#%E6%9D%83%E9%99%90%E9%87%87%E8%B4%AD%E5%91%98%E7%AE%A1%E7%90%86%E5%91%98)
    - [参数](#%E5%8F%82%E6%95%B0-4)
  - [### 响应](#%E5%93%8D%E5%BA%94)
  - [添加货物](#%E6%B7%BB%E5%8A%A0%E8%B4%A7%E7%89%A9)
    - [地址： /item/add](#%E5%9C%B0%E5%9D%80-itemadd)
    - [方式：POST](#%E6%96%B9%E5%BC%8Fpost-5)
    - [权限：采购员|管理员](#%E6%9D%83%E9%99%90%E9%87%87%E8%B4%AD%E5%91%98%E7%AE%A1%E7%90%86%E5%91%98-1)
    - [参数](#%E5%8F%82%E6%95%B0-5)
    - [响应](#%E5%93%8D%E5%BA%94-4)
  - [获取货物列表](#%E8%8E%B7%E5%8F%96%E8%B4%A7%E7%89%A9%E5%88%97%E8%A1%A8)
    - [地址： /item/getList](#%E5%9C%B0%E5%9D%80-itemgetlist)
    - [方式：POST](#%E6%96%B9%E5%BC%8Fpost-6)
    - [权限：所有人](#%E6%9D%83%E9%99%90%E6%89%80%E6%9C%89%E4%BA%BA-3)
    - [参数](#%E5%8F%82%E6%95%B0-6)
    - [响应](#%E5%93%8D%E5%BA%94-5)
  - [会记查看某个货物单价库存、交易记录、总盈亏](#%E4%BC%9A%E8%AE%B0%E6%9F%A5%E7%9C%8B%E6%9F%90%E4%B8%AA%E8%B4%A7%E7%89%A9%E5%8D%95%E4%BB%B7%E5%BA%93%E5%AD%98%E4%BA%A4%E6%98%93%E8%AE%B0%E5%BD%95%E6%80%BB%E7%9B%88%E4%BA%8F)
    - [地址： /accountant/item/getInfo](#%E5%9C%B0%E5%9D%80-accountantitemgetinfo)
    - [方式：POST](#%E6%96%B9%E5%BC%8Fpost-7)
    - [权限：会计|管理员](#%E6%9D%83%E9%99%90%E4%BC%9A%E8%AE%A1%E7%AE%A1%E7%90%86%E5%91%98)
    - [参数](#%E5%8F%82%E6%95%B0-7)
    - [响应](#%E5%93%8D%E5%BA%94-6)
  - [会计查询某个物品所有库存记录（可选）](#%E4%BC%9A%E8%AE%A1%E6%9F%A5%E8%AF%A2%E6%9F%90%E4%B8%AA%E7%89%A9%E5%93%81%E6%89%80%E6%9C%89%E5%BA%93%E5%AD%98%E8%AE%B0%E5%BD%95%E5%8F%AF%E9%80%89)
    - [地址： /accountant/item/getInfo](#%E5%9C%B0%E5%9D%80-accountantitemgetinfo-1)
    - [方式：POST](#%E6%96%B9%E5%BC%8Fpost-8)
    - [权限：会计|管理员](#%E6%9D%83%E9%99%90%E4%BC%9A%E8%AE%A1%E7%AE%A1%E7%90%86%E5%91%98-1)
    - [参数](#%E5%8F%82%E6%95%B0-8)
    - [响应](#%E5%93%8D%E5%BA%94-7)
  - [查看一段时间的盈亏](#%E6%9F%A5%E7%9C%8B%E4%B8%80%E6%AE%B5%E6%97%B6%E9%97%B4%E7%9A%84%E7%9B%88%E4%BA%8F)
    - [地址： /accountant/getSummary](#%E5%9C%B0%E5%9D%80-accountantgetsummary)
    - [方式：POST](#%E6%96%B9%E5%BC%8Fpost-9)
    - [权限：会计|管理员](#%E6%9D%83%E9%99%90%E4%BC%9A%E8%AE%A1%E7%AE%A1%E7%90%86%E5%91%98-2)
    - [参数](#%E5%8F%82%E6%95%B0-9)
    - [响应](#%E5%93%8D%E5%BA%94-8)
  - [获取用户列表](#%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%88%97%E8%A1%A8)
    - [地址： /admin/getUserList](#%E5%9C%B0%E5%9D%80-admingetuserlist)
    - [方式：POST](#%E6%96%B9%E5%BC%8Fpost-10)
    - [权限：管理员](#%E6%9D%83%E9%99%90%E7%AE%A1%E7%90%86%E5%91%98)
    - [参数](#%E5%8F%82%E6%95%B0-10)
    - [响应](#%E5%93%8D%E5%BA%94-9)
  - [删除用户](#%E5%88%A0%E9%99%A4%E7%94%A8%E6%88%B7)
    - [地址： /admin/removeUser](#%E5%9C%B0%E5%9D%80-adminremoveuser)
    - [方式：POST](#%E6%96%B9%E5%BC%8Fpost-11)
    - [权限：管理员](#%E6%9D%83%E9%99%90%E7%AE%A1%E7%90%86%E5%91%98-1)
    - [参数](#%E5%8F%82%E6%95%B0-11)
    - [响应](#%E5%93%8D%E5%BA%94-10)
  - [添加用户](#%E6%B7%BB%E5%8A%A0%E7%94%A8%E6%88%B7)
    - [地址： /admin/addUser](#%E5%9C%B0%E5%9D%80-adminadduser)
    - [方式：POST](#%E6%96%B9%E5%BC%8Fpost-12)
    - [权限：管理员](#%E6%9D%83%E9%99%90%E7%AE%A1%E7%90%86%E5%91%98-2)
    - [参数](#%E5%8F%82%E6%95%B0-12)
    - [响应](#%E5%93%8D%E5%BA%94-11)

## 说明
* 为了一切从简，可以直接基于session做登陆状态保存
* 前后端分离设计可能会导致跨域请求，可以通过后端设置header或者直接把页面放在后端静态目录中解决
* 前后端通信采用统一返回json格式，请求格式为form-data，返回格式如下面API所定义
---

## 用户登陆
### 地址: /user/login
### 方式：POST
### 权限：所有人
### 函数名: Login()
### 参数
* username 用户姓名
* pwd   密码

### 响应
无返回值
登录失败抛出RuntimeExceptoin

---

## 用户登出
### 地址：  /user/logout
### 方式：POST
### 权限：所有人
### 函数名: Logout()
### 参数
无

### 响应
无


## 查询货物名称库存列表
### 地址：  /item/query
### 方式：POST
### 权限：所有人
### 函数名: getItem()
### 参数
* id 货物id

### 响应
```json
{
    "code":200,  //状态码
    "msg":"OK",
    "data":{
        "data":[
            {
                "id":123455,
                "name":"ebook",
                "price":12.5,
                "amount":12
            }
        ]
    }
}
```

---

## 卖出货物
### 地址：  /item/sell
### 方式：POST
### 权限：售货员|管理员
### 函数名: sellGoods()
### 参数
* id 货物ID
* amount  数量

### 响应
没有权限抛出RuntimeExceptoin
无

---

## 买入货物
### 地址：  /item/buy
### 方式：POST
### 权限：采购员|管理员
### 函数名: buyGoods()
### 参数
* id    货物ID
* amount 数量

### 响应
没有权限抛出RuntimeExceptoin
无
---

## 添加货物
### 地址：  /item/add
### 方式：POST
### 权限：采购员|管理员
### 函数名: addItem()
### 参数
* name    货物名称
* price 价格

### 响应
无
没有权限抛出RuntimeExceptoin
表中已有货物抛出RuntiomException

---

## 获取货物列表
### 地址：  /item/getList
### 方式：POST
### 权限：所有人
### 函数名: getItemList()
### 参数
无
### 响应
```json
{
    "code":200,  //状态码
    "msg":"OK",
    "data":{
        "items":[
            {
                "id":123546,
                "name":"xxx",
                "price":12.5,
                "amount":12
            }
        ]
    }
}
```

---

## 会记查看某个货物单价库存、交易记录、总盈亏
### 地址：  /accountant/item/getInfo
### 方式：POST
### 权限：会计|管理员
### 函数名: getItemInfo()
### 参数
// TODO:
* id    物品ID

### 响应
没有权限抛出RuntimeExceptoin
```json
{
    "code":200,  //状态码
    "msg":"OK",
    "data":{
        "item":{
            "id":123456,
            "name":"xxx",
            "price":12.3,
            "amount":2,
            "transaction":[
                {
                    "id":123345345,
                    "kind":"in/out",
                    "amount":12,
                    "totalPrice":12312.2,
                    "time":"yyyy-mm-dd",
                    "operator":{    //操作者
                        "id":123,
                        "name":"tom"
                    }
                }
            ],
            "sum":123.23   //总盈亏
        }
    }
}
```

---

## 会计查询某个物品所有库存记录（可选）
### 地址：  /accountant/item/getRecord
### 方式：POST
### 权限：会计|管理员
### 函数名: getItemRecord()
### 参数
// TODO:
* id 物品ID
* 

### 响应
没有权限抛出RuntimeExceptoin
```json
{
    "code":200,  //状态码
    "msg":"OK",
    "data":{
        "infoList":[
            {
           
            }
        ]
    }
}
```

---

## 查看当天的盈亏
### 地址：  /accountant/getSummary
### 方式：POST
### 权限：会计|管理员
### 函数名: getBalance()
### 参数

### 响应
没有权限抛出RuntimeExceptoin
```json
{
    "code":200,  //状态码
    "msg":"OK",
    "data":{
        "detailList":[
            {
                "date":"yyyy-mm-dd",
                "sum":12
            }
        ]
    }
}
```

---

## 获取用户列表
### 地址：  /admin/getUserList
### 方式：POST
### 权限：管理员
### 函数名: getUserList()
### 参数


### 响应
没有权限抛出RuntimeExceptoin
说明:
"kind":0  ->管理员
"kind":1  ->销售员
"kind":2  ->采购员
"kind":3  ->会计
```json
{
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
}
```


---

## 删除用户
### 地址：  /admin/removeUser
### 方式：POST
### 权限：管理员
### 函数名: removeUser()
### 参数
* id    用户ID

### 响应
没有权限抛出RuntimeExceptoin
无


---

## 添加用户
### 地址：  /admin/addUser
### 方式：POST
### 权限：管理员
### 函数名: addUser()
### 参数
* name  用户名
* password  密码
* kind  类型

### 响应
没有权限抛出RuntimeExceptoin
无

