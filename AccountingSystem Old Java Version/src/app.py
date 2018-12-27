from contextlib import redirect_stdout, redirect_stderr
from io import StringIO
from flask import Flask
from flask import render_template
from flask import request
from flask import jsonify
import sys
import json
import jpype
from jpype import *
import os.path


app = Flask(__name__)

PORT = 5015

jvmPath = jpype.getDefaultJVMPath()
jarpath = os.path.join(os.path.abspath('.'), 'D:\\jar\\')
if not jpype.isJVMStarted():
    jpype.startJVM(jvmPath, '-ea', "-Djava.class.path=%s" % (jarpath + 'AccountingSystem.jar'))
print(jarpath + 'AccountingSystem.jar')
jpype.java.lang.System.out.println("Hello World")
JDClass = jpype.JPackage('program').AccountManager  # 类
# jd = JDClass()  # 对象


@app.route("/")
def show_web():
    return render_template('main.html')


@app.route("/user/login", methods=['POST'])
def user_login():
    data = request.form
    return JDClass.user_login(data['username'], data['pwd'])


@app.route("/user/logout", methods=['POST'])
def user_logout():
    return JDClass.user_logout()


@app.route("/item/query", methods=['POST'])
def item_query():
    data = request.form
    return JDClass.currUser.item_query(data['query'])


@app.route("/item/sell", methods=['POST'])
def item_sell():
    data = request.form
    return JDClass.currUser.item_sell(data['id'], data['amount'])


@app.route("/item/buy", methods=['POST'])
def item_buy():
    data = request.form
    return JDClass.currUser.item_buy(data['id'], data['amount'])


@app.route("/item/getList", methods=['POST'])
def item_getList():
    return JDClass.currUser.item_getList()


@app.route("/accountant/item/getInfo", methods=['POST'])
def accountant_item_getInfo():
    data = request.form
    return JDClass.currUser.accountant_item_getInfo(data['id'])


@app.route("/accountant/item/getRecord", methods=['POST'])
def accountant_item_getRecord():
    data = request.form
    return JDClass.currUser.accountant_item_getRecord(data['id'])


@app.route("/accountant/getSummary", methods=['POST'])
def accountant_getSummary():
    data = request.form
    return


@app.route("/admin/getUserList", methods=['POST'])
def admin_getUserList():
    return JDClass.currUser.admin_getUserList()


@app.route("/admin/removeUser", methods=['POST'])
def admin_removeUser():
    data = request.form
    return JDClass.currUser.admin_removeUser(data['id'])


@app.route("/admin/addUser", methods=['POST'])
def admin_addUser():
    data = request.form
    return JDClass.currUser.admin_addUser(data['password'], data['kind'])


if __name__ == "__main__":
    app.run(host='0.0.0.0', port=PORT)
