# Project-T

## 项目简介
（以下摘自软件项目开发计划）
PKUCR，又称为 PKU(Calendar|Resources)*，这是一个正则表达式，最后的 * 算符表示 闭包运算，意味着用户可以多次在日程功能(Calendar)和资源功能(Resources)两者之 间多次使用，来回切换。这个项目包括两个部分，分别是日程功能和资源功能。用户使用 日程功能时，可以在一段时间内设置自己的日程。用户登录账号后，可以导入自己的课程 表，也可以自己设置自己的日程，也可以同时使用。系统中内置节假日、调休等信息，弥补 了当前一些软件无法识别调休等的缺憾。用户使用资源功能时，可以选择某一门课程，查 看这门课程历年的信息，今年的开课情况，以及查阅所有其他用户发布的关于这门课的资 料。用户也可以上传自己的资料，并且发布所上传的资料，当经过管理员审核后，资料便 会出现在课程的信息中。除此之外，系统还可以根据所有发布的资料生成知识库，当用户 只是想要查阅某个知识点时，便可以在此搜索。

## 项目结构 
- vite.config.js
- vitest.config.js
- jsconfig.json
- .prettierrc.json
- .eslintrc.cjs
- public
    - favicon.ico
- package.json （记录环境配置信息）
- package-lock.json （貌似是记录了更详细的环境配置信息）
- index.html（运行src/main.js脚本）
- src
    - main.js（创建使用MainContainer的App）
    - ~~assets~~
    - ~~App.vue~~
    - components（页面组件）
        - ~~\_\_tests__~~
        - ~~HelloWorld.vue~~
        - ~~TheWelcome.vue~~
        - ~~WelcomeItem.vue~~
        - SideBar.vue（左侧导航栏，用来切换不同的view，完成view后需要更新）
        - PageHeader（顶部栏，待完成）
        - MainContainer（使用SiderBar, PageHeader和一系列view创建页面）
        - icons（好像是运行时下载icon自动保存的）
        - views（页面的主体部分）
            - index.js（记录路由转跳信息，完成view后需要更新）
            - home.vue（默认页面/初始页面，待完成）
            - setting.vue（设置页面，待完成）
    

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Compile and Minify for Production

```sh
npm run build
```

### Run Unit Tests with [Vitest](https://vitest.dev/)

```sh
npm run test:unit
```

### Lint with [ESLint](https://eslint.org/)

```sh
npm run lint
```
