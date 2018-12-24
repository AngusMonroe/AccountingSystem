from contextlib import redirect_stdout, redirect_stderr
from io import StringIO
from flask import Flask
from flask import render_template
from flask import request
from flask import jsonify
import sys
import json

app = Flask(__name__)

PORT = 5015

rules = opa.load_data('data/opa.txt')


@app.route("/")
def show_parser():
    return render_template('main.html')


@app.route("/user/login", methods=['POST'])
def user_login(username, pwd):
    data = request.form
    return


@app.route("/user/logout", methods=['POST'])
def user_logout():
    data = request.form
    return


@app.route("/item/query", methods=['POST'])
def item_query(query):
    data = request.form
    return


@app.route("/item/sell", methods=['POST'])
def item_sell(id, amount):
    data = request.form
    return


@app.route("/item/buy", methods=['POST'])
def item_buy(id, amount):
    data = request.form
    return


@app.route("/item/getList", methods=['POST'])
def item_getList():
    data = request.form
    return


@app.route("/accountant/item/getInfo", methods=['POST'])
def accountant_item_getInfo(id):
    data = request.form
    return


@app.route("/accountant/item/getRecord", methods=['POST'])
def accountant_item_getRecord(id):
    data = request.form
    return


@app.route("/accountant/getSummary", methods=['POST'])
def accountant_getSummary():
    data = request.form
    return


@app.route("/admin/getUserList", methods=['POST'])
def admin_getUserList():
    data = request.form
    return


@app.route("/admin/removeUser", methods=['POST'])
def admin_removeUser():
    data = request.form
    return


@app.route("/admin/addUser", methods=['POST'])
def admin_addUser(password, kind):
    data = request.form
    return


if __name__ == "__main__":
    app.run(host='0.0.0.0', port=PORT)
