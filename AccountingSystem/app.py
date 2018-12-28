from contextlib import redirect_stdout, redirect_stderr
from io import StringIO
from flask import Flask
from flask import render_template
from flask import request
from flask import jsonify
from flask_cors import CORS
import sys
import json
import os.path
from AccountingSystem import *


app = Flask(__name__)
CORS(app, supports_credentials=True)

PORT = 5015

acc = Account()


@app.route("/")
def show_web():
    return render_template('main.html')


@app.route("/user/login", methods=['POST'])
def user_login():
    data = request.form
    try:
        acc.login(data['username'], data['pwd'])
        ans = {
            "code": 200,  # 状态码
            "msg": "OK",
            "data": {
                "username": data['username'],
                "kind": acc.kind  # 用户类型: seller:售货员 buyer:采购员 accountant 会计   admin管理员
            }
        }
    except RuntimeError:
        ans = {
            "code": 100,  # 状态码
            "msg": "ERROR",
            "data": {
                "username": '',
                "kind": ''  # 用户类型: seller:售货员 buyer:采购员 accountant 会计   admin管理员
            }
        }

    return json.dumps(ans)


@app.route("/user/logout", methods=['POST'])
def user_logout():
    acc.logout()
    ans = {
        "code": 200,  # 状态码
        "msg": "ok",
        "data": {}
    }
    return json.dumps(ans)


@app.route("/item/query", methods=['POST'])
def item_query():
    data = request.form
    try:
        item = acc.getitem(data['query'])
    except RuntimeError:
        item = []
    if item:
        ans = {
            "code": 200,  # 状态码
            "msg": "OK",
            "data": {
                "result": [item]
            }
        }
    else:
        ans = {
            "code": 110,  # 状态码
            "msg": "ERROR",
            "data": {
                "data": []
            }
        }
    return json.dumps(ans)


@app.route("/item/sell", methods=['POST'])
def item_sell():
    data = request.form
    try:
        acc.sellgoods(data['id'], data['amount'])
        ans = {
            "code": 200,  # 状态码
            "msg": "OK",
            "data": {}
        }
    except RuntimeError:
        ans = {
            "code": 120,  # 状态码
            "msg": "ERROR",
            "data": {}
        }
    return json.dumps(ans)


@app.route("/item/buy", methods=['POST'])
def item_buy():
    data = request.form
    try:
        acc.buygoods(data['id'], data['amount'])
        ans = {
            "code": 200,  # 状态码
            "msg": "OK",
            "data": {}
        }
    except RuntimeError:
        ans = {
            "code": 130,  # 状态码
            "msg": "ERROR",
            "data": {}
        }
    return json.dumps(ans)


@app.route("/item/add", methods=['POST'])
def item_add():
    data = request.form
    try:
        acc.additem(data['id'], float(data['price']))
        ans = {
            "code": 200,  # 状态码
            "msg": "OK",
            "data": {}
        }
    except RuntimeError:
        ans = {
            "code": 140,  # 状态码
            "msg": "ERROR",
            "data": {}
        }
    return json.dumps(ans)


@app.route("/item/getList", methods=['POST'])
def item_getList():
    try:
        items = acc.getitemlist()
        ans = {
            "code": 200,  # 状态码
            "msg": "OK",
            "data": {
                "items": items
            }
        }
    except RuntimeError:
        ans = {
            "code": 150,  # 状态码
            "msg": "ERROR",
            "data": {
                "items": []
            }
        }
    return json.dumps(ans)


@app.route("/accountant/item/getInfo", methods=['POST'])
def accountant_item_getInfo():
    data = request.form
    try:
        ans = {
            "code": 200,  # 状态码
            "msg": "OK",
            "data": acc.getiteminfo(data['id'])
        }
    except RuntimeError:
        ans = {
            "code": 150,  # 状态码
            "msg": "ERROR",
            "data": {}
        }
    return json.dumps(ans)


# @app.route("/accountant/item/getRecord", methods=['POST'])
# def accountant_item_getRecord():
#     data = request.form
#     return JDClass.currUser.accountant_item_getRecord(data['id'])


@app.route("/accountant/getSummary", methods=['POST'])
def accountant_getSummary():
    try:
        balance = acc.getbalance()
        ans = {
            "code": 200,  # 状态码
            "msg": "OK",
            "data": {
                "detailList": [balance]
            }
        }
    except RuntimeError:
        ans = {
            "code": 160,  # 状态码
            "msg": "ERROR",
            "data": {
                "detailList": []
            }
        }
    return json.dumps(ans)


@app.route("/admin/getUserList", methods=['POST'])
def admin_getUserList():
    try:
        user_list = acc.getuserlist()
    except RuntimeError:
        user_list = []
    if user_list:
        ans = {
            "code": 200,  # 状态码
            "msg": "OK",
            "data": {
                "userList": user_list
            }
        }
    else:
        ans = {
            "code": 170,  # 状态码
            "msg": "ERROR",
            "data": {
                "userList": []
            }
        }
    return json.dumps(ans)


@app.route("/admin/removeUser", methods=['POST'])
def admin_removeUser():
    data = request.form
    try:
        acc.removeuser(data['id'])
        ans = {
            "code": 200,  # 状态码
            "msg": "OK",
            "data": {}
        }
    except RuntimeError:
        ans = {
            "code": 180,  # 状态码
            "msg": "ERROR",
            "data": {}
        }
    return json.dumps(ans)


@app.route("/admin/addUser", methods=['POST'])
def admin_addUser():
    data = request.form
    try:
        acc.adduser(data['name'], data['password'], data['kind'])
        ans = {
            "code": 200,  # 状态码
            "msg": "OK",
            "data": {}
        }
    except RuntimeError:
        ans = {
            "code": 190,  # 状态码
            "msg": "ERROR",
            "data": {}
        }
    return json.dumps(ans)


if __name__ == "__main__":
    app.run(host='0.0.0.0', port=PORT)
