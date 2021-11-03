package com.zyf.easytestcontainer.runner.spring;

import com.zyf.easytestcontainer.annotation.RedisTestContainerConfig;
import com.zyf.easytestcontainer.constant.SpringConfigEnum;
import org.junit.runners.model.InitializationError;
import org.testcontainers.containers.GenericContainer;

/**
 * @author zhangyunfan@fiture.com
 * @version 1.0
 * @ClassName: RedisTestContainerRunner
 * @Description: Use the runner of the redis part of the test container
 * @date 2021/10/17
 */
public class RedisSpringTestContainerRunner extends AbstractSpringTestContainerRunner {

    private static final String REDIS_IMAGE = "redis:latest";

    private static final int REDIS_PORT = 6379;

    public RedisSpringTestContainerRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    public GenericContainer<?> initContainer() {
        RedisTestContainerConfig redisTestContainerConfig = getTestClass().getJavaClass().getAnnotation(RedisTestContainerConfig.class);
        GenericContainer<?> redis = new GenericContainer<>(redisTestContainerConfig != null ? redisTestContainerConfig.image() : REDIS_IMAGE);
        redis.withExposedPorts(REDIS_PORT).withCommand("--requirepass test");
        return redis;
    }

    @Override
    protected void setSpringConfiguration(GenericContainer<?> container) {
        System.setProperty(SpringConfigEnum.REDIS_HOST.getValue(), container.getContainerIpAddress());
        System.setProperty(SpringConfigEnum.REDIS_PORT.getValue(), container.getMappedPort(REDIS_PORT).toString());
        System.setProperty(SpringConfigEnum.REDIS_PASSWORD.getValue(), "test");
    }

}
