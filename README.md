
项目介绍
David's Github

基于Springboot + Vue 开发的前后端分离外卖点单系统

           

项目说明|目录结构|项目特点|技术选型|开发环境|项目截图|快速开始|注意事项|项目总结|如何反馈

项目说明
基于 SSM 开发模式，模拟实现电子书库系统，利用，Interceptor过滤器技术进
行登录校验，通过登录到账号，管理电子书的管理和分类。所有用户可以对页面进行点赞功
能，利用websocket，完成点赞通知功能，设置定时任务，完成每隔一段时间更新点赞数，阅
读数页面显示，使用异步化解耦点赞功能缓解服务器压力，采用 axio+json 与后端数据进行
交互，管理者也能通过该系统了解各个书本的情况，通过 Echarts图表显示分析后的数据，让
读者清晰的知道30天的用户访问曲线图。

目录结构
后台前端项目位于renren-ui下

小程序前端项目位于takeout_mp下

SQL文件位于根目录下的takeout_mysql8.sql，需要MYSQL8以上版本。

ps：请先运行后端项目，再启动前端项目。

take_out
│ 
├─renren-admin      管理后台
│    ├─db  数据库SQL脚本
│    │ 
│    ├─modules  模块
│    │    ├─job 定时任务
│    │    ├─log 日志管理
│    │    ├─oss 文件存储
│    │    ├─security 安全模块
│    │    ├─sys 系统管理(核心)
|    |    └─takeout 外卖业务模块(核心)
│    │ 
│    └─resources 
│        ├─mapper   MyBatis文件
│        ├─public  静态资源
│        └─application.yml   全局配置文件
│       
│ 
├─renren-api        外卖小程序API服务
│ 
├─renren-common     公共模块
├─renren-generator  代码生成器
│        └─resources 
│           ├─mapper   MyBatis文件
│           ├─template 代码生成器模板（可增加或修改相应模板）
│           ├─application.yml    全局配置文件
│           └─generator.properties   代码生成器，配置文件
│
├─renren-ui     后台管理VUe前端项目
├─takeout_mp    uniapp微信小程序项目
项目特点
友好的代码结构及注释，便于阅读及二次开发
实现前后端分离，通过token进行数据交互
支持动态权限修改，采用RBAC模型，前端菜单和后台权限实时更新。
提供CrudService接口，对增删改查进行封装，代码更简洁
页面交互使用Vue2.x，极大的提高了开发效率
完善的部门管理及数据权限，通过注解实现数据权限的控制
完善的XSS防范及脚本过滤，彻底杜绝XSS攻击
完善的代码生成机制，可在线生成entity、xml、dao、service、vue、sql代码，减少70%以上的开发任务
引入quartz定时任务，可动态完成任务的添加、修改、删除、暂停、恢复及日志查看等功能
引入Hibernate Validator校验框架，轻松实现后端校验
引入云存储服务，已支持：七牛云、阿里云、腾讯云等
引入swagger文档支持，方便编写API接口文档
新增AOP注解实现日志管理。
代码遵循阿里巴巴开发规范，利于开发者学习。
技术选型
核心框架：Spring Boot 2.7.1
安全框架：Apache Shiro 1.9
持久层框架：MyBatis 3.5
定时器：Quartz 2.3
数据库连接池：Druid 1.2
日志管理：Logback
页面交互：Vue2.x
微信小程序：uni-app
开发环境
开发工具	说明
IDEA	Java开发工具IDE
WebStrom或者VS Code	Vue开发工具IDE
Navicat	MySQL远程连接工具
Another Redis Desktop Manager	Redis远程连接工具
FinalShell	Linux远程连接工具
HBuilder X	编译微信小程序工具
开发环境	版本
JDK	1.8
MySQL	8.0.29
Redis	6.0.5
Maven	3.8
NodeJs	14.x.x
项目截图
image image image image image

快速开始
通过git下载源码

idea、eclipse需安装lombok插件，不然会提示找不到entity的get set方法

创建数据库takeout，数据库编码为UTF-8mb4

执行takeout_mysql8.sql文件，初始化数据

修改application-dev.yml文件，更新MySQL账号和密码、Redis的相关配置信息

修改application-dev.yml文件，更新Minio配置（下面有安装Minio的教程）

minio:
  # minio配置的地址，端口9000
  url: http://你安装minio的ip:9000
  # 账号
  accessKey: minio
  # 密码
  secretKey: minio123
  # MinIO桶名字
  bucketName: images
在renren-api模块下，修改application.yml文件，更新微信小程序配置

weixin:
  getPhoneNumberUrl:
  getAccessTokenUrl:
  jscode2sessionUrl:
  appid:
  secret:
在takeout目录下，执行mvn clean install

Eclipse、IDEA运行AdminApplication.java，则可启动项目【renren-admin】

再启动前端项目

账号密码：admin/admin

安装Minio
用docker拉取Minio镜像

docker pull minio/minio
安装镜像

docker run \
--name minio \
-p 9000:9000  \
-p 9090:9090  \
-d --restart=always \
-e "MINIO_ROOT_USER=minio" \
-e "MINIO_ROOT_PASSWORD=minio123" \
-v /opt/docker_minio/data:/data \
-v /opt/docker_minio/config:/root/.minio \
minio/minio server  /data --console-address ":9090" --address ":9000"
登录（这里的ip地址填自己的就可以了）

页面地址 http://192.168.65.100:9090/login

创建一个名字为images的桶

image

image image

更改Yml配置文件

image

ps：url改成你自己的，账号和密码如果之前安装docker镜像的时候没动就不用改，bucketName可以不用修改。

安装Nodejs环境(如果有请忽略)
NodeJs版本不要安装太高，13.x-14.x最好
附上一个下载链接：https://nodejs.org/download/release/v13.10.0/
具体配置环境变量等请自行百度一下找一下教程，步骤很简单。
运行后台管理系统前端项目
配置好Nodejs环境之后就可以打开后台管理前端源代码

打开终端输入

npm install
等待安装依赖完成后，输入

npm run serve
等待编译完成后，会自动打开页面，或者访问 http://localhost:8001/

安装以及修改HBuilder X配置以及微信开发者工具配置
修改微信开发者工具的设置，将服务端口打开，不打开，HBuilder X在编译之后不会自动唤醒微信开发者工具

image

将图片中的AppId修改为你自己申请的小程序测试号AppId

image

启动微信小程序项目
完成了以上配置之后就可以启动小程序项目了

image

注意事项
如果使用minio存储文件，需要先安装好minio
如果要运行小程序，需要先提前申请一个小程序开发测试账号
项目总结
这个作为新手入门项目是十分不错的，项目所用的技术栈覆盖的也比较广，适合初学者学习。做的不好的地方请大家见谅，有问题的或者有好的建议可以私聊联系我。

如何反馈
有问题可以扫下面的二维码添加我的微信(添加好友时，请备注来意)，我会尽力帮您解答！

WeChat

Footer
© 2022 GitHub, Inc.
Footer navigation
Terms
Privacy
Security
Status
Docs
Contact GitHub
Pricing
API
Training
Blog
About
MP_take_out/README.md at master · yx159247/MP_take_out