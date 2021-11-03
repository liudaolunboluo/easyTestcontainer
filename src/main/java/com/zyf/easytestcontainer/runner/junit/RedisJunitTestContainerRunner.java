package com.zyf.easytestcontainer.runner.junit;

import com.zyf.easytestcontainer.annotation.RedisTestContainerConfig;
import com.zyf.easytestcontainer.context.RedisJunitTestContainerContext;
import org.testcontainers.containers.GenericContainer;

import java.lang.reflect.InvocationTargetException;

/**
 * @author zhangyunfan@fiture.com
 * @version 1.0
 * @ClassName: RedisTestContainerRunner
 * @date 2021/10/26
 */
public class RedisJunitTestContainerRunner extends AbstractJunitTestContainerRunner {

    private static final String REDIS_IMAGE = "redis:latest";

    private static final int REDIS_PORT = 6379;

    /**
     * Creates a BlockJUnit4ClassRunner to run {@code klass}
     *
     * @param klass
     * @throws InvocationTargetException if the test class is malformed.
     */
    public RedisJunitTestContainerRunner(Class<?> klass) throws InvocationTargetException {
        super(klass);
    }

    @Override
    protected void containerCloseCallback() {
        RedisJunitTestContainerContext.clear();
    }

    @Override
    public GenericContainer<?> initContainer() {
        RedisTestContainerConfig redisTestContainerConfig = testClass.getAnnotation(RedisTestContainerConfig.class);
        GenericContainer<?> redis = new GenericContainer<>(redisTestContainerConfig != null ? redisTestContainerConfig.image() : REDIS_IMAGE);
        redis.withExposedPorts(REDIS_PORT).withCommand("--requirepass test");
        RedisJunitTestContainerContext.saveContainer(redis);
        return redis;
    }

    public static int getRedisPort() {
        return REDIS_PORT;
    }

}
