package com.zyf.easytestcontainer.runner.spring;

import com.zyf.easytestcontainer.annotation.MysqlTestContainerConfig;
import com.zyf.easytestcontainer.annotation.Neo4jTestContainerConfig;
import com.zyf.easytestcontainer.constant.SpringConfigEnum;
import org.junit.runners.model.InitializationError;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.shaded.org.apache.commons.lang3.StringUtils;
import org.testcontainers.utility.DockerImageName;

/**
 * @author zhangyunfan@fiture.com
 * @version 1.0
 * @ClassName: Neo4jSpringTestContainerRunner
 * @Description: neo4j数据库
 * @date 2022/7/6
 */
public class Neo4jSpringTestContainerRunner extends AbstractSpringTestContainerRunner {

    private static final String DEFAULT_VERSION = "4.4.2";

    public Neo4jSpringTestContainerRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    public GenericContainer<?> initContainer() {
        Neo4jTestContainerConfig neo4jTestContainerConfig = getTestClass().getJavaClass().getAnnotation(Neo4jTestContainerConfig.class);
        return new Neo4jContainer<>(neo4jTestContainerConfig == null ?
                DockerImageName.parse("neo4j:" + DEFAULT_VERSION) :
                DockerImageName.parse("neo4j:" + neo4jTestContainerConfig.version())).withAdminPassword(null);
    }

    @Override
    protected void setSpringConfiguration(GenericContainer<?> container) {
        System.setProperty(SpringConfigEnum.NEO4J_URI.getValue(), ((Neo4jContainer) container).getBoltUrl());
    }
}
