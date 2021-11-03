package com.zyf.easytestcontainer.context;

import com.zyf.easytestcontainer.runner.junit.RedisJunitTestContainerRunner;
import lombok.experimental.UtilityClass;
import org.testcontainers.containers.GenericContainer;

/**
 * @author zhangyunfan@fiture.com
 * @version 1.0
 * @ClassName: RedisJunitTestContainerContext
 * @date 2021/10/27
 */
@UtilityClass
public class RedisJunitTestContainerContext {

    private static final ThreadLocal<GenericContainer<?>> THREAD_LOCAL = new ThreadLocal<>();

    public void saveContainer(GenericContainer<?> genericContainer) {
        THREAD_LOCAL.set(genericContainer);
    }

    public void clear() {
        THREAD_LOCAL.remove();
    }

    public String getRedisHost() {
        GenericContainer<?> container = THREAD_LOCAL.get();
        return container.getContainerIpAddress();
    }

    public String getRedisPort() {
        GenericContainer<?> container = THREAD_LOCAL.get();
        return container.getMappedPort(RedisJunitTestContainerRunner.getRedisPort()).toString();
    }

    public String getRedisPassword() {
        return "test";
    }
}
