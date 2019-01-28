### Tomcat简介
>Tomcat服务器是一个轻量级的Java代码实现的Web服务器。这篇文章会讲述如何用Java实现一个简单的Tomcat服务器。



### 1.简易原理
 1. Tomcat开始运行后，会在服务器上开一个端口(本文中用的是8888端口)，在所开辟的端口上运行一个ServerScoket,执行accpet()方法等待浏览器访问。

 2. 浏览器访问端口，ServerSocket的accept()方法返回一个运行在服务器端的socket，通过socket的getInputStream()方法和getOutputStream方法，可获得浏览器和服务器之间发送的内容和浏览器的响应内容()。

 3. 这里我们主要获取浏览器的请求地址和参数，响应时根据浏览器所传的参数和地址，到服务器上去寻找对应的资源(目前只支持静态资源)，将用户请求的资源转化为文件流，传输给前端(浏览器)。

 4. 浏览器收到服务器的响应后，向用户展示界面。

    

 ### 2.实现类
 > 主要分为以下几个类：
 > (1) Server 用于加载并启动服务器
 > (2) ServerThread 当接收到新请求时，开辟一个新的线程处理它
 > (3) Request 用于获取请求的数据(主要是请求的地址和请求参数)
 > (4) Response 用于响应请求(根据请求所获参数和地址，访问服务器上的资源)
 > (5) GetParam 用于获取环境变量参数



### 3.测试效果

![](https://img-blog.csdnimg.cn/20190128140924350.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2h1b2ppNTU1,size_16,color_FFFFFF,t_70)

