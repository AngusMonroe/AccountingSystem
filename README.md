# AccountingSystem

### 分工

- 数据库设计： zzx、wzf
- 后端接口设计：xjx
- 前端设计：qzz、gjw
- ppt：pyk
- presentation：lht

### DDL

- 12.20 前后端完成对接
- 12.27 presentation

### 数据库设计

- 充值表

	表名：recharge
	
	字段：
	
	| 字段名 | 中文描述 | 类型 | 长度 | 是否可以为空 | 备注 |
	|-----|------|----|----|--------|----|
	| requestID | 请求ID | int | 11 | 否 | 主键 |
	| userID | 用户账户ID | int | 11 | 否 | |
	| requestTime | 请求时间 | date |  | 否 | |
	| amount | 金额 | float |  | 否 |  单位：元<br>保留两位小数 |
	| method | 充值/提现方式 | tinyint | 4 | 否 |  0-微信<br>1-支付宝 |
	| operateStatus | 操作状态 | tinyint | 4 | 否 | 0-待清分<br>1-已清分 |
	| requestStatus | 请求状态 | tinyint | 4 | 否 | 若为0，进行缓存 |

- 提现表

	表名：withdraw
	
	字段：
	
	| 字段名 | 中文描述 | 类型 | 长度 | 是否可以为空 | 备注 |
	|-----|------|----|----|--------|----|
	| requestID | 请求ID | int | 11 | 否 | 主键 |
	| userID | 用户账户ID | int | 11 | 否 | |
	| requestTime | 请求时间 | date |  | 否 | |
	| amount | 金额 | float |  | 否 |  单位：元<br>保留两位小数 |
	| method | 充值/提现方式 | tinyint | 4 | 否 |  0-微信<br>1-支付宝 |
	| operateStatus | 操作状态 | tinyint | 4 | 否 | 0-待清分<br>1-已清分 |
	| requestStatus | 请求状态 | tinyint | 4 | 否 | 若为0，进行缓存 |

- 消费表

	表名：trade
	
	字段：
	
	| 字段名 | 中文描述 | 类型 | 长度 | 是否可以为空 | 备注 |
	|-----|------|----|----|--------|----|
	| requestID | 请求ID | int | 11 | 否 | 主键 |
	| userID | 用户账户ID | int | 11 | 否 | |
	| merchantID | 商户账户ID | int | 11 | 否 | |
	| requestTime | 请求时间 | date |  | 否 | |
	| amount | 金额 | float |  | 否 |  单位：元<br>保留两位小数 |
	| operateStatus | 操作状态 | tinyint | 4 | 否 | 0-待清分<br>1-已清分 |
	| requestStatus | 请求状态 | tinyint | 4 | 否 | 若为0，进行缓存 |