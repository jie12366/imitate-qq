# 广告广告
字节跳动广告系统下的穿山甲平台大量招人，有兴趣的直接发简历到我邮箱：xiongyijie.monkjay@bytedance.com。也可以加我 QQ：2263509062
![ddd](https://user-gold-cdn.xitu.io/2020/6/9/17296d23354015c8?w=1202&h=655&f=png&s=811332)

# 基于javaFX的仿QQ桌面软件
> 这是一个模仿QQ的即时聊天软件，可以通过运行在本地的服务
端，实现两个客服端之间的通信，即聊天。采用的是javafx架构
作为GUI设计架构，个人认为优点是可以自己设计css，使界面达
到美观的目的。本项目共有登录、注册、重置密码、主界面发消
息、添加好友、好友列表项、查看聊天记录、删除聊天记录、未
读消息提醒、好友主页、我的主页等模块。该项目还调用了短信
验证码的api和图灵机器人的api。在进行注册和重置密码时，都
会有发送验证码的按钮，通过输入的手机号接收验证码。每个人
的账号都会有个聊天助手，聊天助手就是接入的图灵机器人，可
实现自动智能回复。

## 技术栈
 - Java
 - JavaFx
 - MySQL
 - css

## 详细介绍
详情请见博客：[Java实现仿QQ即时聊天](https://blog.csdn.net/qq_40663357/article/details/85465652)  
服务端地址：[Java仿QQ服务端](https://github.com/jie12366/imitate-qq-server)
## 项目准备
### 项目工具
 - IDEA
 - Navicat
 - JavaFX scene Builder
### 准备数据库(MySQL)
 - 创建数据库wechat
 - 使用Navicat或其他MySQL可视化工具运行database路径下的SQL文件

### 导入项目
 - 下载一个JavaFX scene Builder，附上链接。
 链接：https://pan.baidu.com/s/1b__UVMt82zYK9MrwypTvgQ

提取码：hd4j。将JavaFX配置到idea中(具体操作自己Google)
 - 从github克隆项目到本地
```
git clone https://github.com/jie12366/imitate-qq.git
```
 - 新建一个JavaFX项目，路径指向刚刚clone下来的项目地址。将
 自动生成的sample文件夹删掉。并在Model.database下配置自己的数据库信息。
 短信服务的话，自己想要就去买一个，不想要就直接打印在控制台

 - 将jar包导入(都放在了jar文件夹)。

 ![image.png](http://cdn.jie12366.xyz/FnLhyM-hRzOCG_ytYvirHKW80wtb)

### 项目运行
右键Main.Mian运行项目，没有报错并出现类QQ登录界面则成功。

**如果该项目对你有帮助，欢迎fork与star哦。**
