﻿# -*- coding: utf-8 -*-  
import pymysql
import json
import datetime


def demo():  # 一个样例
    acc = Account()
    acc.login("admin", "admin")
    acc.sellgoods(1,10)
    print("finished")
    

class Sql:  # 数据库连接
    connect = pymysql.connect("localhost", "root", "jiaxing+", "AccountingSystem")  # host, user, password, database
    cur = connect.cursor()

    def initdata(self):
        sql = \
            "set sql_safe_updates = 0; " +\
            "delete from user; " +\
            "delete from item; " +\
            "delete from transaction; " +\
            "delete from balance; " +\
            "insert into user values(1, 'admin', 'admin', 'Administrator'); " +\
            "insert into user values(2, 'zhao', '111111', 'Seller'); " +\
            "insert into user values(3, 'qian', '222222', 'Buyer'); " +\
            "insert into user values(4, 'sun', '333333', 'Accountant'); " +\
            "insert into item values(1, 'apple', 2.25, 5000); " +\
            "insert into item values(2, 'banana', 1.8, 3000); " +\
            "insert into item values(3, 'orange', 3.5, 2000); " +\
            "insert into item values(4, 'grape', 4.5, 1000); " +\
            "insert into item values(5, 'melon', 12.0, 200); " +\
            "insert into item values(6, 'coconut', 25.0, 100); "
        Sql.cur.execute(sql)
        Sql.connect.commit()


class User:  # 用户

    def __init__(self, id, name, password, kind):
        self.id = id                # 序号
        self.name = name            # 名称
        self.password = password    # 密码
        self.kind = kind            # 类型

    def todict(self):
        return {"id": self.id, "name": self.name, "password": self.password, "kind": self.kind}


class Item:  # 货物

    def __init__(self, id, name, price, amount):
        self.id = id                # 序号
        self.name = name            # 名称
        self.price = price          # 单价
        self.amount = amount        # 库存

    def todict(self):
        return {"id": self.id, "name": self.name, "price": self.price, "amount": self.amount}


class Transaction:  # 交易记录

    def __init__(self, id, kind, itemID, amount, totalPrice, userID, time):
        self.id = id                     # 序号
        self.kind = kind                 # 类型
        self.itemID = itemID             # 货物序号
        self.amount = amount             # 数量
        self.totalPrice = totalPrice     # 交易额
        self.userID = userID             # 用户序号
        self.time = time                 # 时间

    def todict(self):
        return {"id": self.id, "kind": self.kind, "itemID": self.itemID, "amount": self.amount, "totalPrice": self.totalPrice, "userID": self.userID, "time": str(self.time)}


class Balance:  # 收支

    def __init__(self, id, profit, date):
        self.id = id            # 序号
        self.profit = profit    # 盈亏
        self.date = date        # 日期

    def todict(self):
        return {"date": self.date, "sum": self.profit}


class Account:  # 账户

    def __init__(self):
        self.kind = ''        # Administrator, Seller, Buyer, Accountant
        self.userID = 0
        self.state = False

    def getitembyname(self, name):
        if not self.state:
            raise RuntimeError
        Sql.cur.execute("SELECT * FROM Item WHERE Name = '%s'" % name)
        data = Sql.cur.fetchone()
        item = Item(data[0], data[1], data[2], data[3])
        return item.todict()

    def getitembyid(self, id):
        id = int(id)
        if not self.state:
            raise RuntimeError
        Sql.cur.execute("SELECT * FROM Item WHERE ID = %d" % id)
        data = Sql.cur.fetchone()
        item = Item(data[0], data[1], data[2], data[3])
        return item.todict()

    def getiteminfo(self, id):
        id = int(id)
        if (self.kind != "Administrator") and (self.kind != "Accountant"): raise RuntimeError("Permission denied.")
        sum = 0.0
        trandicts = []
        Sql.cur.execute("SELECT * FROM Transaction WHERE ItemID = %d" % id)
        for data in Sql.cur.fetchall():
            trans = Transaction(data[0], data[1], data[2], data[3], data[4], data[5], data[6])
            dic = trans.todict().copy()
            dic.update({"operator": {"id": id}})
            trandicts.append(dic)
            sum += data[4]
        dic = self.getitembyid(id).copy()
        dic.update({"transaction": trandicts, "sum": sum})
        print(dic)
        print({"transaction": trandicts, "sum": sum})
        return dic

    def sellgoods(self, id, amount):
        id = int(id)
        amount = int(amount)
        if not self.state:
            raise RuntimeError
        if (self.kind != "Administrator") and (self.kind != "Seller"): raise RuntimeError("Permission denied.")
        Sql.cur.execute("SELECT * FROM Item Where ID = %d" % id)
        data = Sql.cur.fetchone()
        oldamount = data[3]
        if (oldamount < amount): raise RuntimeError("Insufficient goods.")
        Sql.cur.execute("UPDATE Item SET Amount = %f Where ID = %d" % (oldamount - amount, id))
        Sql.connect.commit()
        price = data[2]
        date = datetime.datetime.now().strftime("%Y-%m-%d")
        date = str(date)
        Sql.cur.execute("INSERT INTO Transaction VALUES(%d, '%s', %d, %d, %f, %d, '%s')" % (self.nextid("Transaction"), "Sell", id, amount, price * amount, self.userID, date))
        Sql.connect.commit()
        
    def buygoods(self, id, amount):
        id = int(id)
        amount = int(amount)
        if not self.state:
            raise RuntimeError
        if (self.kind != "Administrator") and (self.kind != "Buyer"):
            raise RuntimeError("Permission denied.")
        Sql.cur.execute("SELECT * FROM Item Where ID = %d" % id)
        data = Sql.cur.fetchone()
        oldamount = data[3]
        Sql.cur.execute("UPDATE Item SET Amount = %f Where ID = %d" % (oldamount + amount, id))
        Sql.connect.commit()
        price = data[2]
        date = datetime.datetime.now().strftime("%Y-%m-%d")
        date = str(date)
        Sql.cur.execute("INSERT INTO Transaction VALUES(%d, '%s', %d, %d, %f, %d, '%s')" % (self.nextid("Transaction"), "Buy", id, amount, - price * amount, self.userID, date))
        Sql.connect.commit()

    def additem(self, name, price):
        price = float(price)
        if not self.state:
            raise RuntimeError
        if (self.kind != "Administrator") and (self.kind != "Buyer"): raise RuntimeError("Permission denied.")
        Sql.cur.execute("INSERT INTO Item VALUES(%d, '%s', %f, %d)" % (self.nextid("Item"), name, price, 0))
        Sql.connect.commit()
    
    def getitemlist(self):
        if not self.state:
            raise RuntimeError
        Sql.cur.execute("SELECT * FROM Item")
        res = []
        for data in Sql.cur.fetchall():
            item = Item(data[0], data[1], data[2], data[3])
            res.append(item.todict())
        return res

    def getbalance(self):
        if not self.state:
            raise RuntimeError
        if (self.kind != "Administrator") and (self.kind != "Accountant"): raise RuntimeError("Permission denied.")
        Sql.cur.execute("SELECT * FROM Transaction")
        balances = []
        res = dict()
        for data in Sql.cur.fetchall():
            if data[6][0:10] not in res.keys():
                res[data[6][0:10]] = 0.0
            res[data[6][0:10]] += data[4]
        for date in res.keys():
            balances.append({"date": date, "sum": res[date]})

        return balances

    def getuserlist(self):
        if not self.state:
            raise RuntimeError
        if (self.kind != "Administrator"): raise RuntimeError("Permission denied.")
        Sql.cur.execute("SELECT * FROM User")
        res = []
        for data in Sql.cur.fetchall():
            user = User(data[0], data[1], data[2], data[3])
            res.append(user.todict())
        return res

    def removeuser(self, id):
        id = int(id)
        if not self.state:
            raise RuntimeError
        if (self.kind != "Administrator"): raise RuntimeError("Permission denied.")
        Sql.cur.execute("DELETE FROM User WHERE ID = %d" % id)
        Sql.connect.commit()

    def adduser(self, name, password, kind):
        if not self.state:
            raise RuntimeError
        if (self.kind != "Administrator"): raise RuntimeError("Permission denied.")
        Sql.cur.execute("INSERT INTO User VALUES(%d, '%s', '%s', '%s')" % (self.nextid("User"), name, password, kind))
        Sql.connect.commit()

    def nextid(self, table):
        Sql.cur.execute("SELECT MAX(ID) FROM %s" % table)
        data = Sql.cur.fetchone()
        res = data[0]
        if res is None:
            return 1
        else:
            return res + 1

    def login(self, user, password):
        Sql.cur.execute("SELECT * FROM User WHERE Name = '%s' AND Password = '%s'" % (user, password))
        data = Sql.cur.fetchone()
        if data:
            self.kind = data[3]
            self.userID = data[0]
            self.state = True
        else:
            raise RuntimeError("Wrong username or password.")

    def logout(self):
        self.state = False


if __name__ == "__main__":
    demo()