package com.zyf.easytestcontainer.runner.spring;

import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.zyf.easytestcontainer.constant.SpringConfigEnum;
import com.zyf.easytestcontainer.annotation.MysqlTestContainerConfig;
import com.zyf.easytestcontainer.exception.InitTestContainerException;
import com.zyf.easytestcontainer.utils.EnvironmentUtils;
import org.junit.runners.model.InitializationError;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;
import org.testcontainers.utility.DockerImageName;

import java.util.function.Consumer;

/**
 * @author zhangyunfan@fiture.com
 * @version 1.0
 * @ClassName: BaseMysqlTestContainer
 * @Description: Use the runner of the MySQL part of the test container
 * @date 2021/10/16
 */
public class MysqlSpringTestContainerRunner extends AbstractSpringTestContainerRunner {

    public MysqlSpringTestContainerRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    public GenericContainer<?> initContainer() {
        MysqlTestContainerConfig mysqlTestContainerConfig = getTestClass().getJavaClass().getAnnotation(MysqlTestContainerConfig.class);
        if (mysqlTestContainerConfig == null) {
            throw new InitTestContainerException("数据库测试类必须要自己配置数据库信息，至少设置一个初始化脚本");
        }
        String initScript = mysqlTestContainerConfig.dbScript();
        if (StringUtils.isBlank(initScript)) {
            throw new InitTestContainerException("必须设置初始化数据库的脚本路径");
        }
        /*
         * HACK testcontainers官方并不推荐设置非随机端口映射，所以withPortBindings方法被弃用了，但是这里必须要用非随机端口，因为如果是两个类一起运行单元测试，
         * 如果是随机端口的话第二个测试类就无法链接到数据库了，因为两次开启的mysql镜像的暴露端口并不一致并且spring的datasource不支持运行时修改链接信息
         * 其实这里根本问题是一次性运行多个单元测试类应该只开启一次mysql容器，但是目前还没有解决此问题，先给个todo吧，后面解决
         * 具体请看此issue：https://github.com/testcontainers/testcontainers-java/issues/256
         * */
        int hostPort = MySQLContainer.MYSQL_PORT;
        int containerExposedPort = MySQLContainer.MYSQL_PORT;
        Consumer<CreateContainerCmd> cmd = e -> e.withPortBindings(new PortBinding(Ports.Binding.bindPort(hostPort), new ExposedPort(containerExposedPort)));
        MySQLContainer<?> mysql = EnvironmentUtils.isArm() ?
                new MySQLContainer<>(DockerImageName.parse(mysqlTestContainerConfig.armImage()).asCompatibleSubstituteFor("mysql")) :
                new MySQLContainer<>(mysqlTestContainerConfig.image());
        mysql.withInitScript(initScript);
        mysql.withExposedPorts(containerExposedPort);
        mysql.withCreateContainerCmdModifier(cmd);
        return mysql;
    }

    @Override
    protected void setSpringConfiguration(GenericContainer<?> container) {
        MySQLContainer<?> mysql = (MySQLContainer<?>) container;
        MysqlTestContainerConfig initMysqlTestContainer = getTestClass().getJavaClass().getAnnotation(MysqlTestContainerConfig.class);
        System.setProperty(SpringConfigEnum.DATASOURCE_URL.getValue(), mysql.getJdbcUrl() + initMysqlTestContainer.urlParam());
        System.setProperty(SpringConfigEnum.DATASOURCE_DRIVER_CLASS_NAME.getValue(), mysql.getDriverClassName());
        System.setProperty(SpringConfigEnum.DATASOURCE_USERNAME.getValue(), mysql.getUsername());
        System.setProperty(SpringConfigEnum.DATASOURCE_PASSWORD.getValue(), mysql.getPassword());
        System.setProperty(SpringConfigEnum.DATASOURCE_SCHEMA.getValue(), "");
        System.setProperty(SpringConfigEnum.DATASOURCE_DATA.getValue(), "");
    }
}
