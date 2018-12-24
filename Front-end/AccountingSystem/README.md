# AccountingSystem

- [AccountingSystem](#accountingsystem)
  - [安装](#%E5%AE%89%E8%A3%85)
    - [开发环境](#%E5%BC%80%E5%8F%91%E7%8E%AF%E5%A2%83)
    - [生产环境编译](#%E7%94%9F%E4%BA%A7%E7%8E%AF%E5%A2%83%E7%BC%96%E8%AF%91)
  - [切换Mock与真实后端](#%E5%88%87%E6%8D%A2mock%E4%B8%8E%E7%9C%9F%E5%AE%9E%E5%90%8E%E7%AB%AF)

## 安装
```
npm install
```

### 开发环境
```
npm run serve
```

### 生产环境编译
```
npm run build
```

## 切换Mock与真实后端
* 进入src/util/UrlMapper.js
* 文件前几行内容大致如下

```javascript
export function mapUrl(inputUrl,debug=true){
        
        // var prefix = "http://localhost:5000";   //调试
        
        var prefix = "http://"+window.location.host;    //部署
```
* 将`debug=true`改为`debug=false`
* 如果是跨域，则将prefix的内容改为后端API的路径，如`http://116.62.11.111:9999`