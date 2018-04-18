#单点登录

该项目是单点登录的server端

采用的架构

springMVC+mybatis+kisso



1、首先使用sql文件夹下的ddl语句创建数据库

2、修改jdbc.properties中的数据库连接

3、修改applicationContext-redis.xml的redis配置

4、配置自己的host

127.0.0.1 sso.jjc.com


5、使用maven tomcat启动的方式进行启动项目。 注册、登录。


详细说明 参考一下这个博客吧。里面有kisso作者的地址。 最好还是把文档下载下来看吧。

https://blog.csdn.net/q826qq1878/article/details/76179591


