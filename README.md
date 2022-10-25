# wiki

#### 介绍
wiki 电子书知识库系统项目
Springboot+vue3
项目采用了前后端分离技术；后端采用 springBoot 基本框架，数据库连接池技
术，Redis+token权限验证；前端使用 vue3+vueCil 开发模式；使用了 axio+json，vuex，
Ant Design Vue,router来实现页面功能。


#### 软件架构
基于 SSM 开发模式，模拟实现电子书库系统，利用，Interceptor过滤器技术进
行登录校验，通过登录到账号，管理电子书的管理和分类。所有用户可以对页面进行点赞功
能，利用websocket，完成点赞通知功能，设置定时任务，完成每隔一段时间更新点赞数，阅
读数页面显示，使用异步化解耦点赞功能缓解服务器压力，采用 axio+json 与后端数据进行
交互，管理者也能通过该系统了解各个书本的情况，通过 Echarts图表显示分析后的数据，让
读者清晰的知道30天的用户访问曲线图。



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
