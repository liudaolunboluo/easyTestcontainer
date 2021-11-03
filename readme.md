# EasyTestContainer

1、在本机（以笔者的MacOs为例）上安装Docker：`brew install --cask --appdir=/Applications docker`

然后启动docker hub，然后在命令行中`docker run testcontainers/ryuk:0.3.0`来安装并运行ryuk这个镜像。

2、pom文件里引入：

```xml
<dependency>
  <groupId>com.zyf</groupId>
  <artifactId>EasyTestContainer</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <scope>test</scope>
</dependency>
```

然后新建一个单元测试类，在单元测试类上加上注解：

```java
@RunWith(MysqlSpringTestContainerRunner.class)
@InitDatabaseScript("init.sql")
```

`InitDatabaseScript`注解是指定mysql初始化脚本，存放在resource目录下的db目录下，这样可以每个单元测试类的脚本都可以分开。

`@RunWith(MysqlSpringTestContainerRunner.class)`是指定用mysql环境，目前只支持mysql和redis，redis的是`RedisSpringTestContainerRunner`
注意这两个Runner都是基于Spring-test的都必须在spring的环境下才能使用。
然后在当前类下就可以愉快的操作数据库和redis了，不用做任何其他多余的操作
