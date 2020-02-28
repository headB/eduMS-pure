# 叩丁狼教学管理系统

## 模块简介
- edums-core：核心模块，存放系统管理以及项目基础框架功能，如代码生成、各种工具类、以及一些公共服务。以 jar 的形式提供，其他各模块都会依赖 core 模块。
- edums-finance：财务模块，存放系统财务相关功能。以 jar 的形式提供，依赖 core 模块。
- edums-market：市场模块，存放系统市场相关功能。以 jar 的形式提供，依赖 core 模块。
- edums-teaching：教学模块，存放系统教学相关功能。以 jar 的形式提供，依赖 core 模块。
- edums-web：WEB 项目，存放 UI 界面以及管理整个项目的公共配置。集成 core、finance、market、teaching 模块，通过 SpringBoot Jar 的方式进行部署。

## 技术选型
    ● 核心框架：Sring boot + Spring Framework
    ● 视图层框架： Spring MVC
    ● 安全框架：Apache Shiro
    ● 持久层框架：MyBatis + MyBatis-Plus
    ● 数据库连接池：Alibaba Druid
    ● 缓存框架：EhCache
    ● 日志管理：SLF4J、Logback
    ● 前端框架：LayUI + jQuery

## 启动说明
    * 启动方法：
            EdumsApplication.java
    * 测试环境打包命令：
         clean package -P test
    * 生产环境打包命令：
         clean package -P prod

## 开发步骤
    1. 通过 edums-core 下面的 CodeGenerator 类生成相关表格的基础文件
    2. 确定 CodeGenerator 中的 DataSourceConfig 为自己的目标数据库信息
    3. 运行 CodeGenerator 的 main 方法，当中需要输入模块名（core|finance|market|teaching），html 模块名（访问页面链接时的模块路径，如：/system/employee/list，中 sytem 为 html 模块名）
    4. CodeGenerator 可以根据 edums-core 项目中的 resources/templates 中的模板文件生成实体类、Mapper.xml、Mapper 接口、Service 接口以及实现类、Controller 类以及页面和基础 js 文件
    5. 根据生成的文件进行相应的开发

## WEB 项目结构
    edums-web
    ├── dist                            # web 页面以及基础 js 文件所在目录
    │   ├── controller                  # 控制器文件，相当与模块化文件，对页面的相关操作进行拆分并封装在此
    │   │   ├── common.js               # 公共功能
    │   │   └── employee.js             # 员工视图控制器
    │   ├── lib                         # 项目依赖库
    │   │   ├── extend                  # 依赖的第三方拓展库
    │   │   ├── admin.js                # 项目主要的功能的封装，如弹框 admin.popup、请求后台 admin.req 等等
    │   │   └── view.js                 # 项目路由总控制文件，管理项目的 ajax 请求，视图展现，权限控制等
    │   ├── style                       # 项目样式所在目录
    │   ├── views                       # 视图页面文件夹，路由规则会自动找到 views 下面的文件进行访问，如 /system/employee/list 会访问到 /views/system/employee/list.html 文件
    │   │   ├── system                  # 依赖的第三方拓展库
    │   │   │   ├── employee            # 员工模块视图
    │   │   │   │   ├── list.html       # 员工模块列表视图
    │   │   │   │   └── form.html       # 员工模块表单视图
    │   │   │   └── department          # 部门模块视图
    │   │   ├── layout.html             # 项目布局视图
    │   │   └── login.html              # 登陆视图
    │   ├── config.js                   # LayUI 项目配置文件
    │   └── index.js                    # 主 js 文件
    ├── layui                           # LayUI 框架目录
    ├── index.html                      # UI 界面入口文件
    └── robots.txt                      # 禁止爬虫爬取页面