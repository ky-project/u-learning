<p align="center">
    <img src="https://raw.githubusercontent.com/Daye1112/u-learning/dev/doc/modules/pic/项目logo.jpg" width="150">
    <h1 align="center">U-Learning</h1>
    <p align="center">
        U-Learning——Studying With Anytime, Anywhere, Anyone, Anydevice, Anything
        <br>
        <br>
         <a href="https://github.com/Daye1112/u-learning">
             <img src="https://img.shields.io/github/watchers/Daye1112/u-learning?style=social" >
         </a>
         <a href="https://github.com/Daye1112/u-learning">
             <img src="https://img.shields.io/github/stars/Daye1112/u-learning?style=social" >
         </a>
         <a href="https://github.com/Daye1112/u-learning">
             <img src="https://img.shields.io/github/forks/Daye1112/u-learning?style=social" >
         </a>
    </p>    
</p>

## 项目背景

移动设备和移动技术的应用已经改变了人们的学习方式和获取信息的途径，随时随刻拿出手机打开app或者浏览器即可进行想要资源的获取。在这样一个信息爆炸的时代中，学习系统的功能已经不仅仅是如何全面的将资源快速展现给学习者，更为重要的是如何针对性的将资源进行囊括。

该系统是一次将理论与实践相结合的创新设计，它结合当下在线教育形势，未来泛在学习发展趋势，在完美融合学校教育需求下进行的一次实践探索。泛在学习（U-learning）继承了移动学习的所有优势，被认为是能够真正实现“5A”（Anytime， Anywhere，Anyone，Any device，Anything）的理想学习方式，是时下众多教育工作者探讨和研究的热点。

## 项目介绍

本项目属于基于泛在学习的教学系统服务端项目。

特点：

1. 前后端分离架构，客户端和服务端纯Token交互；
2. 微服务防护，客户端请求资源只能通过微服务网关获取；
3. 集成Spring Boot Admin监控微服务
4. 集成xxl-job-admin，实现分布式任务调度，并作为客户端使用；
5. 集成fastdfs作为分布式文件系统；
6. 集成redis高速缓存中间件；
7. 集成swagger2，swagger-bootstrap-ui自动生成接口文档

## 文档与教程



## 服务模块

ulearning模块

| 服务名称                 | 端口                  | 启动优先级 | 描述               |
| ------------------------ | --------------------- | ---------- | ------------------ |
| ulearning-config         | 14002                 | 1          | 微服务配置中心     |
| ulearning-gateway        | 8082                  | 3          | 微服务网关         |
| ulearning-monitor-manage | 14004、24004(xxl-job) | 3          | 微服务监控系统     |
| ulearning-register       | 14001                 | 0          | 微服务注册中心     |
| ulearning-system-manage  | 14003                 | 2          | 微服务后台系统     |
| ulearning-teacher        | 14005                 | 3          | 微服务教师系统     |
| xxl-job-admin            | 14007                 | 3          | 微服务任务调度平台 |

第三方模块

| 服务名称 | 端口         | 描述           |
| -------- | ------------ | -------------- |
| MySQL    | 3306         | MySQL数据库    |
| Redis    | 6379         | 高速缓存数据库 |
| FastDfs  | 8888、221122 | 分布式文件系统 |

## 目录结构

```
​```
├─doc                             ------ 系统开发文档、sql脚本和发布记录等
├─ulearning-common-core           ------ 系统核心模块
├─ulearning-config                ------ 微服务配置中心
├─ulearning-gateway               ------ 微服务网关
├─ulearning-monitor-manage        ------ 微服务监控系统
├─ulearning-parent                ------ 整个项目的父模块
├─ulearning-register              ------ 微服务注册中心
├─ulearning-spi                   ------ 系统entity、dto和vo等
├─ulearning-system-manage         ------ 微服务后台系统
├─ulearning-teacher               ------ 微服务教师系统
├─xxl-job-admin                   ------ 微服务任务调度平台
​```
```

