# PKUCR

![logo](./src/assets/PKUCR-logo.svg)

## 项目简介
> PKUCR，又称为 PKU(Calendar|Resources)*，这是一个正则表达式，最后的 * 算符表示 闭包运算，意味着用户可以多次在日程功能(Calendar)和资源功能(Resources)两者之 间多次使用，来回切换。这个项目包括两个部分，分别是日程功能和资源功能。用户使用 日程功能时，可以在一段时间内设置自己的日程。用户登录账号后，可以导入自己的课程 表，也可以自己设置自己的日程，也可以同时使用。系统中内置节假日、调休等信息，弥补 了当前一些软件无法识别调休等的缺憾。用户使用资源功能时，可以选择某一门课程，查 看这门课程历年的信息，今年的开课情况，以及查阅所有其他用户发布的关于这门课的资 料。用户也可以上传自己的资料，并且发布所上传的资料，当经过管理员审核后，资料便 会出现在课程的信息中。除此之外，系统还可以根据所有发布的资料生成知识库，当用户 只是想要查阅某个知识点时，便可以在此搜索。

## 项目结构 
```
PKUCR
├── src
│   ├── components
│   │   ├── icons
│   │   │   ├── IconCommunity.vue
│   │   │   ├── IconDocumentation.vue
│   │   │   ├── IconEcosystem.vue
│   │   │   ├── IconSupport.vue
│   │   │   └── IconTooling.vue
│   │   ├── __tests__
│   │   │   └── HelloWorld.spec.js
│   │   ├── SideBar.vue
│   │   ├── PageHeader.vue
│   │   └── MainContainer.vue
│   ├── views
│   │   ├── index.js
│   │   ├── home.vue
│   │   ├── taskTable.vue
│   │   ├── calendar.vue
│   │   ├── setting.vue
│   │   ├── login.vue
│   │   ├── register.vue
│   │   └── resource.vue
│   ├── assets
│   │   ├── base.css
│   │   ├── logo.svg
│   │   ├── main.css
│   │   └── PKUCR-logo.svg
│   ├── store
│   │   └── auth.ts
│   ├── axios.ts
│   ├── main.js
│   └── App.vue
├── public
│   └── favicon.ico
├── index.html
├── jsconfig.json
├── vite.config.js
├── vitest.config.js
├── package.json
└── package-lock.json
```   
重要文件介绍：
- `src/components/MainContainer.vue`为项目界面的主体结构，分为`sidebar`，`header`以及`main`三个部分，其中`main`负责主体功能界面的呈现，`sidebar`负责`main`的路由，`header`负责账号相关功能以及资源搜索
- `src/components/SideBar.vue`为项目`sidebar`的实现
- `src/components/PageHeader.vue`为项目`header`的实现
- `src/views/*.vue`为项目`main`各功能页面的实现
- `src/views/index.js`创建并记录了项目的路由数组
- `src/store/auth.ts`实现了注册登陆以及`token`的存储
- `src/axios.ts`实现了附带`token`的`axios`封装
- `src/App.vue`为项目软件及根路由
- `src/main.js`为项目软件的初始化
- `src/assets/PKUCR-logo.svg`为项目logo
- `index.html`项目web文件

## 项目环境配置
项目使用Vue3框架，Vue3脚手架以及ElementPlus配套工具，使用npm进行环境管理。
本地安装有npm的情况下，在项目根目录下运行如下命令即可配置完环境：
```sh
npm install
```

## 项目运行
项目环境配置完后，在项目根目录下运行如下命令：
```sh
npm run dev
```
运行成功后命令行会出现类似如下文字：
```
VITE v5.4.8  ready in 203 ms

➜  Local:   http://localhost:5173/
➜  Network: use --host to expose
➜  press h + enter to show help
```
根据输入的不同命令可以执行不同操作：
- 输入`h + enter` 显示帮助
- 输入`o + enter` 在浏览器打开项目web界面
- 输入`q + enter` 停止项目运行