package com.zyf.easytestcontainer.runner.spring;

import com.zyf.easytestcontainer.constant.SpringConfigEnum;
import com.zyf.easytestcontainer.annotation.MysqlTestContainerConfig;
import com.zyf.easytestcontainer.exception.InitTestContainerException;
import com.zyf.easytestcontainer.utils.EnvironmentUtils;
import org.junit.runners.model.InitializationError;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;
import org.testcontainers.utility.DockerImageName;

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
        MySQLContainer<?> mysql = EnvironmentUtils.isArm() ?
                new MySQLContainer<>(DockerImageName.parse(mysqlTestContainerConfig.armImage()).asCompatibleSubstituteFor("mysql")) :
                new MySQLContainer<>(mysqlTestContainerConfig.image());
        mysql.withInitScript(initScript);
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
