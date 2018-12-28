# -*- coding: utf-8 -*-  
import pymysql
import json
import datetime


def demo():  # 一个样例
    acc = Account()
    acc.login("admin", "admin")
    acc.adduser("li", "4444", "Accountant")
    print("finished")
    

class Sql:  # 数据库连接
    connect = pymysql.connect("localhost", "root", "root", "AccountingSystem")  # host, user, password, database
    cur = connect.cursor()


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


class ItemInfo:   # 货物信息

    def __init__(self, id, name, price, amount, transaction, sum):
        self.id = id                    # 序号
        self.name = name                # 名称
        self.price = price              # 单价
        self.amount = amount            # 库存
        self.transaction = transaction  # 相关交易记录
        self.sum = sum                  # 该货物盈亏

    def todict(self):
        return {"id": self.id, "name": self.name, "price": self.price, "amount": self.amount, "transaction": self.transaction, "sum": self.sum}

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
        return {"id": self.id, "kind": self.kind, "itemID": self.itemID, "amount": self.amount, "totalPrice": self.totalPrice, "userID": self.userID, "time": self.time}


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

    def getitem(self, name):
        if self.state:
            raise RuntimeError
        Sql.cur.execute("SELECT * FROM Item WHERE Name = '%s'" % name)
        data = Sql.cur.fetchone()
        item = Item(data[0], data[1], data[2], data[3])
        return item.todict()

    def gettransaction(self, itemid):
        Sql.cur.execute("SELECT * FROM Transaction WHERE ID = %d" % itemid)
        transactions
        for data in Sql.cur.fetchall():
        

    def getiteminfo(self, id):
        if (self.kind != "Administrator") and (self.kind != "Accountant"): raise RuntimeError("Permission denied.")
        sum = 0.0
        trans = []
        Sql.cur.execute("SELECT * FROM Transaction WHERE ItemID = %d" % id)
        for data in Sql.cur.fetchall():
            trans.append({})
            if data[1] == "Sell":
                sum += data[4]
            elif data[1] == "Buy":
                sum -= data[4]
            else:
                raise RuntimeError("Unknown Transaction Kind.")
        iteminfo = getitem(id) + {"transaction": }
        return 

    def sellgoods(self, name, amount):
        if self.state:
            raise RuntimeError
        if (self.kind != "Administrator") and (self.kind != "Seller"): raise RuntimeError("Permission denied.")
        Sql.cur.execute("SELECT * FROM Item Where Name = '%s'" % name)
        data = Sql.cur.fetchone()
        oldamount = data[3]
        if (oldamount < amount): raise RuntimeError("Insufficient goods.")
        Sql.cur.execute("UPDATE Item SET Amount = %f Where Name = '%s'" % (oldamount - amount, name))
        Sql.connect.commit()
        price = data[2]
        date = datetime.datetime.now().strftime("%Y-%m-%d")
        Sql.cur.execute("INSERT INTO Transaction VALUES(%d, '%s', %d, %d, %f, %d, '%s')" % (self.nextid("Transaction"), "Sell", name, amount, price * amount, self.userID, date))
        Sql.connect.commit()
        
    def buygoods(self, name, amount):
        if self.state:
            raise RuntimeError
        if (self.kind != "Administrator") and (self.kind != "Buyer"): raise RuntimeError("Permission denied.")
        Sql.cur.execute("SELECT * FROM Item Where Name = '%s'" % name)
        data = Sql.cur.fetchone()
        oldamount = data[3]
        Sql.cur.execute("UPDATE Item SET Amount = %f Where Name = '%s'" % (oldamount + amount, name))
        Sql.connect.commit()
        price = data[2]
        date = datetime.datetime.now().strftime("%Y-%m-%d")
        Sql.cur.execute("INSERT INTO Transaction VALUES(%d, '%s', %d, %d, %f, %d, '%s')" % (self.nextid("Transaction"), "Buy", name, amount, - price * amount, self.userID, date))
        Sql.connect.commit()

    def additem(self, name, price):
        if self.state:
            raise RuntimeError
        if (self.kind != "Administrator") and (self.kind != "Buyer"): raise RuntimeError("Permission denied.")
        Sql.cur.execute("INSERT INTO Item VALUES(%d, '%s', %f, %d)" % (self.nextid("Item"), name, price, 0))
        Sql.connect.commit()
    
    def getitemlist(self):
        if self.state:
            raise RuntimeError
        Sql.cur.execute("SELECT * FROM Item")
        res = []
        for data in Sql.cur.fetchall():
            item = Item(data[0], data[1], data[2], data[3])
            res.append(item.todict())
        return res

    def getbalance(self):
        if self.state:
            raise RuntimeError
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
        return balance.todict()

    def getuserlist(self):
        if self.state:
            raise RuntimeError
        if (self.kind != "Administrator"): raise RuntimeError("Permission denied.")
        Sql.cur.execute("SELECT * FROM User")
        res = []
        for data in Sql.cur.fetchall():
            user = User(data[0], data[1], data[2], data[3])
            res.append(user.todict())
        return res

    def removeuser(self, id):
        if self.state:
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