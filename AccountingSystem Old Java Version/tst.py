# coding=utf-8
# !/usr/bin/python
import jpype
from jpype import *
import os.path

jvmPath = jpype.getDefaultJVMPath()
jarpath = os.path.join(os.path.abspath('.'), 'D:\\jar\\')
if not jpype.isJVMStarted():
    jpype.startJVM(jvmPath, '-ea', "-Djava.class.path=%s" % (jarpath + 'AccountingSystem.jar'))
print(jarpath + 'AccountingSystem.jar')
jpype.java.lang.System.out.println("Hello World")
# JDClass = JClass("account.Account")
#jd = JDClass()
jprint = java.lang.System.out.println
getUserTab = jpype.JPackage('Account').account.getUserTable  # 方法
JDClass = jpype.JPackage('Account').account # 类
jd = JDClass() # 对象
print(jd)
#getu()
jpype.shutdownJVM()
