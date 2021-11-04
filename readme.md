
EasyTestContainers是我编写的基于TestContainers的单元测试小工具，集成了springTest和MockitoJUnit这两个比较主流的单元测试框架。对你的单元测试代码无侵入，使用非常简单快捷方便。目前只支持SpringBoot 2.X或以上版本，后面如果有人用的话会支持其它spring版本。

testcontainers地址：https://www.testcontainers.org/
### 1、电脑环境准备：

1、首先使用testcontainers的前提就是本机安装docker，mac上安装很简单，直接`brew install --cask --appdir=/Applications docker`使用brew安装就可以了，windows麻烦一下，建议windows环境使用WLS。

2、安装docker之后运行`docker run testcontainers/ryuk:0.3.0`启动这个testcontainers依赖的容器

### 2、代码中使用

首先在pom文件里：

```xml
<dependency>
  <groupId>io.github.liudaolunboluo</groupId>
  <artifactId>easytestcontainers</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <scope>test</scope>
</dependency>
```

引入笔者的easytestcontainers

然后在你的单元测试类上加上注解

```java
@RunWith(MysqlSpringTestContainerRunner.class)
@MysqlTestContainerConfig(dbScript="init.sql")
```

`@RunWith(MysqlSpringTestContainerRunner.class)`表明的是你用的是哪个runner在进行单元测试，目前暂时支持mysql、redis、es三个中间件，后面笔者会不断完善支持的中间件的，如果你想用其他中间件的那么可以看后面的3.2节扩展相关内容

`@MysqlTestContainerConfig(dbScript="init.sql")`

是配置类，dbScript是你的该单元测试类的数据库初始化脚本的存放路径，应该存放在你的test目录的resource目录下

mysql还可以配置你的mysql镜像，缺省值是5.7.28这个版本的镜像，特别的因为mysql的镜像不支持arm架构，只有oracle维护的mysql镜像支持arm架构，考虑到部分同学是使用的是arm架构的M1的mac book电脑，所以加了个配置`armImage`，来指定在arm架构下的mysql镜像，同样的缺省版本还是5.7.28，大家可以根据自己的需求来改变。最后，还可以配置的你的数据库的url的扩展参数，比如说`erverTimezone=GMT%2b8`这样来设置时区，缺省值的就是比较全的配置，大家可以根据自己项目的情况来更改。

然后在此单元测试类下你就可以正常的使用你的数据库操作相关类来做单元测试了，例如：

```java
@SpringBootTest(classes = TestApplication.class)
@RunWith(MysqlSpringTestContainerRunner.class)
@MysqlTestContainerConfig(dbScript="init.sql")
public class UserRepositoryImplTest extends BaseTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test() {
      UserBO user = new UserBO();
      user.setName("Tom");
      user.setAge(15);
      userRepository.save(user);
      UserBO userNew = userRepository.getByName("Tom");
      assertNotNull(userNew);
      assertEquals(15, userNew.getAge());
    }
}
```

对就这么简单，下面的test方法中的sava、getByName方法都是真正的货真价实的对数据库在操作了。

目前有三个spring test的runner：`MysqlSpringTestContainerRunner`,`EsSpringTestContainerRunner`,`RedisSpringTestContainerRunner`，分别对应mysql、es、redis环境的单元测试。

如果是非spring下的单元测试，目前只支持redis环境：`RedisJunitTestContainerRunner`，因为mysql和es基本都是在spring中使用，而redis可能有的同学会不使用spring而直接使用jdis。

RedisJunitTestContainerRunner中，需要使用`RedisJunitTestContainerContext`来获取redis的端口和host来初始化你的jdis客户端：

```java
@RunWith(RedisJunitTestContainerRunner.class)
public class RedisJunitTestContainerRunnerTest {

    @Test
    public void testContext() {
        assertNotNull(RedisJunitTestContainerContext.getRedisHost());
        assertNotNull(RedisJunitTestContainerContext.getRedisPort());
    }
}
```



### 3、扩展

如果你想使用其他的testcontainers支持的中间件而笔者的easytestcontainers又没有的话，则可以自己扩展之。

新建一个runner继承`AbstractSpringTestContainerRunner`或者`AbstractJunitTestContainerRunner`这两个类，然后实现方法就可以了。使用的话就和上面的一样。

