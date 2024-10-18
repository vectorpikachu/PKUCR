# Backend of PKUCR Project

## 项目结构

前后端之间通过RESTful API进行通信，前端通过HTTP请求与后端进行交互。传递JSON数据。

前端使用前端的服务器(Nginx)，后端使用后端的服务器(Tomcat)。

后端在PKUCRProject.PKUCR.backend下加包。

## 使用说明

使用Gradle构建项目。

请先替换Gradle的源为阿里云：在`gradle/wrapper/gradle-wrapper.properties`中修改`distributionUrl`为`hhttps://mirrors.aliyun.com/macports/distfiles/gradle/gradle-8.10.2-bin.zip`。

然后使用下面的命令构建项目：
```shell
./gradlew bootRun
```
或者
```shell
.\gradlew.bat bootRun
```

应该可以看到下面的结果：
```shell
PS YourProjectDirectory\backend> ./gradlew bootRun

> Task :bootRun

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v3.3.4)

2024-10-18T08:54:38.783+08:00  INFO 6744 --- [backend] [           main] P.PKUCR.backend.DemoApplication          : Starting DemoApplication using Java 23.0.1 with PID 6744 (YourProjectDirectory\backend\build\classes\java\main started by User in YourProjectDirectory\backend)
2024-10-18T08:54:38.785+08:00  INFO 6744 --- [backend] [           main] P.PKUCR.backend.DemoApplication          : No active profile set, falling back to 1 default profile: "default"
2024-10-18T08:54:39.454+08:00  INFO 6744 --- [backend] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
2024-10-18T08:54:39.468+08:00  INFO 6744 --- [backend] [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2024-10-18T08:54:39.469+08:00  INFO 6744 --- [backend] [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.30]
2024-10-18T08:54:39.516+08:00  INFO 6744 --- [backend] [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2024-10-18T08:54:39.517+08:00  INFO 6744 --- [backend] [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 673 ms
2024-10-18T08:54:39.759+08:00  INFO 6744 --- [backend] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
2024-10-18T08:54:39.765+08:00  INFO 6744 --- [backend] [           main] P.PKUCR.backend.DemoApplication          : Started DemoApplication in 1.298 seconds (process running for 1.643)
2024-10-18T08:54:53.923+08:00  INFO 6744 --- [backend] [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherSerst].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2024-10-18T08:54:53.923+08:00  INFO 6744 --- [backend] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2024-10-18T08:54:53.924+08:00  INFO 6744 --- [backend] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms
<==========---> 80% EXECUTING [1m 17s]
> :bootRun
```

随后在浏览器中输入`http://localhost:8080/hello`，应该可以看到后端的主页。