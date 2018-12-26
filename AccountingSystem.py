# -*- coding: utf-8 -*-  
import pymysql
import json
import datetime

# 一个样例
def demo():

    acc = Account("Administrator", 1)
    # print(acc.getitem(1))
    # acc.sellgoods(1, 2)
    print(acc.getbalance())
    print("finished")
    
# 数据库连接
class Sql:
    connect = pymysql.connect("localhost", "root", "root", "AccountingSystem") # host, user, password, database
    cur = connect.cursor()

# 用户
class User:

    def __init__(self, id, name, password, kind):
        self.id = id                # 序号
        self.name = name            # 名称
        self.password = password    # 密码
        self.kind = kind            # 类型

    def tojson(self):
        return json.dumps([{"id" : self.id, "name" : self.name, "password" : self.password, "kind" : self.kind}])

# 货物
class Item:

    def __init__(self, id, name, price, amount):
        self.id = id                # 序号
        self.name = name            # 名称
        self.price = price          # 单价
        self.amount = amount        # 库存

    def tojson(self):
        return json.dumps([{"id" : self.id, "name" : self.name, "price" : self.price, "amount" : self.amount}])

# 交易记录
class Transaction:

    def __init__(self, id, kind, itemID, amount, totalPrice, userID, time):
        self.id = id                     # 序号
        self.kind = kind                 # 类型
        self.itemID = itemID             # 货物序号
        self.amount = amount             # 数量
        self.totalPrice = totalPrice     # 交易额
        self.userID = userID             # 用户序号
        self.time = time                 # 时间
    def tojson(self):
        return json.dumps([{"id" : self.id, "kind" : self.kind, "itemID" : self.itemID, "amount" : self.amount, "totalPrice" : self.totalPrice, "userID" : self.userID, "time" : self.time}])

# 收支
class Balance:

    def __init__(self, id, profit, date):
        self.id = id            # 序号
        self.profit = profit    # 盈亏
        self.date = date        # 日期

    def tojson(self):
        return json.dumps([{"date" : self.date, "sum" : self.profit}])

# 账户
class Account:

    def __init__(self, kind, userID):
        self.kind = kind        # Administrator, Seller, Buyer, Accountant
        self.userID = userID

    def getitem(self, id):
        Sql.cur.execute("SELECT * FROM Item WHERE ID = '%s'" % id)
        data = Sql.cur.fetchone()
        item = Item(data[0], data[1], data[2], data[3])
        return item.tojson()

    def sellgoods(self, id, amount):
        if (self.kind != "Administrator") and (self.kind != "Seller"): raise RuntimeError("Permission denied.")
        Sql.cur.execute("SELECT * FROM Item Where ID = '%s'" % id)
        data = Sql.cur.fetchone()
        oldamount = data[3]
        if (oldamount < amount): raise RuntimeError("Insufficient goods.")
        Sql.cur.execute("UPDATE Item SET Amount = %f Where ID = '%s'" % (oldamount - amount, id))
        Sql.connect.commit()
        price = data[2]
        date = datetime.datetime.now().strftime("%Y-%m-%d")
        Sql.cur.execute("INSERT INTO Transaction VALUES(%d, '%s', %d, %d, %f, %d, '%s')" % (self.nextid("Transaction"), "Sell", id, amount, price * amount, self.userID, date))
        Sql.connect.commit()
        
    def buygoods(self, id, amount):
        if (self.kind != "Administrator") and (self.kind != "Buyer"): raise RuntimeError("Permission denied.")
        Sql.cur.execute("SELECT * FROM Item Where ID = '%s'" % id)
        data = Sql.cur.fetchone()
        oldamount = data[3]
        Sql.cur.execute("UPDATE Item SET Amount = %f Where ID = '%s'" % (oldamount + amount, id))
        Sql.connect.commit()
        price = data[2]
        date = datetime.datetime.now().strftime("%Y-%m-%d")
        Sql.cur.execute("INSERT INTO Transaction VALUES(%d, '%s', %d, %d, %f, %d, '%s')" % (self.nextid("Transaction"), "Buy", id, amount, - price * amount, self.userID, date))
        Sql.connect.commit()

    def additem(self, name, price):
        if (self.kind != "Administrator") and (self.kind != "Buyer"): raise RuntimeError("Permission denied.")
        Sql.cur.execute("INSERT INTO Item VALUES(%d, '%s', %f, %d)" % (self.nextid("Item"), name, price, 0))
        Sql.connect.commit()
    
    def getitemlist(self):
        Sql.cur.execute("SELECT * FROM Item")
        res = []
        for data in Sql.cur.fetchall():
            item = Item(data[0], data[1], data[2], data[3])
            res.append(item.tojson())
        return res

    def getbalance(self):
        if (self.kind != "Administrator") and (self.kind != "Accountant"): raise RuntimeError("Permission denied.")
        Sql.cur.execute("SELECT * FROM Transaction")
        profit = 0.0
        date = datetime.datetime.now().strftime("%Y-%m-%d")
        for data in Sql.cur.fetchall():
            if date == data[6]:
                if data[1] == "Sell":
                    profit -= data[4]
                else:
                    profit += data[4]
        balance = Balance(self.nextid("Balance"), profit, date)
        return balance.tojson()

    def getuserlist(self):
        if (self.kind != "Administrator"): raise RuntimeError("Permission denied.")
        Sql.cur.execute("SELECT * FROM User")
        res = []
        for data in Sql.cur.fetchall():
            item = Item(data[0], data[1], data[2], data[3])
            res.append(item.tojson())
        return res

    def removeuser(self, id):
        if (self.kind != "Administrator"): raise RuntimeError("Permission denied.")
        Sql.cur.execute("DELETE FROM User WHERE ID = %d" % id)
        Sql.connect.commit()

    def adduser(self, name, password, kind):
        if (self.kind != "Administrator"): raise RuntimeError("Permission denied.")
        Sql.cur.execute("INSERT INTO User VALUES(%d, '%s', '%s', '%s')" % (self.nextid("User"), name, password, kind))
        Sql.connect.commit()

    def nextid(self, table):
        Sql.cur.execute("SELECT MAX(ID) FROM %s" % table)
        data = Sql.cur.fetchone()
        res = data[0]
        if res == None:
            return 1
        else:
            return res + 1

if __name__=="__main__":
    demo()