'''
from jpype import *
import os.path


jarpath = os.path.join(os.path.abspath('.'), 'D:\\jar')
# startJVM("C:\\Program Files\\Java\\jdk1.8.0_112\\jre\\bin\\jvm.dll", "-ea")
startJVM("C:\\Program Files\\Java\\jdk1.8.0_112\\jre\\bin\\jvm.dll", "-ea", "-Djava.class.path=%s" % (jarpath + 'AccountingSystem.jar'))
#ubuntu 中startJVM("/home/geek/Android/jdk1.6.0_43/jre/lib/i386/server/libjvm.so","-ea", "-Djava.class.path=%s" % (jarpath + 'XXX.jar'))
JDClass = JClass("program.AccountManager")
jd = JDClass()  
#jd = JPackage("jpype").JpypeDemo() #两种创建jd的方法  
jprint = java.lang.System.out.println  
# jprint(jd.sayHello("waw"))
# jprint(jd.calc(2,4))
jprint("233")
shutdownJVM()
'''
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
