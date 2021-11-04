package com.zyf.easytestcontainer.runner.spring;

import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.zyf.easytestcontainer.annotation.RedisTestContainerConfig;
import com.zyf.easytestcontainer.constant.SpringConfigEnum;
import org.junit.runners.model.InitializationError;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;

import java.util.function.Consumer;

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
        int hostPort = REDIS_PORT;
        int containerExposedPort = REDIS_PORT;
        Consumer<CreateContainerCmd> cmd = e -> e.withPortBindings(new PortBinding(Ports.Binding.bindPort(hostPort), new ExposedPort(containerExposedPort)));
        GenericContainer<?> redis = new GenericContainer<>(redisTestContainerConfig != null ? redisTestContainerConfig.image() : REDIS_IMAGE);
        redis.withCommand("--requirepass test");
        redis.withExposedPorts(containerExposedPort);
        redis.withCreateContainerCmdModifier(cmd);
        return redis;
    }

    @Override
    protected void setSpringConfiguration(GenericContainer<?> container) {
        System.setProperty(SpringConfigEnum.REDIS_HOST.getValue(), container.getContainerIpAddress());
        System.setProperty(SpringConfigEnum.REDIS_PORT.getValue(), container.getMappedPort(REDIS_PORT).toString());
        System.setProperty(SpringConfigEnum.REDIS_PASSWORD.getValue(), "test");
    }

}
