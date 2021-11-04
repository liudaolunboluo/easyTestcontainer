package com.zyf.easytestcontainer.runner.junit;

import com.zyf.easytestcontainer.annotation.RedisTestContainerConfig;
import com.zyf.easytestcontainer.context.RedisJunitTestContainerContext;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author zhangyunfan@fiture.com
 * @version 1.0
 * @ClassName: RedisJunitTestContainerRunnerTest
 * @date 2021/10/27
 */
@RunWith(RedisJunitTestContainerRunner.class)
public class RedisJunitTestContainerRunnerTest {

    @Test
    public void testContext() {
        assertNotNull(RedisJunitTestContainerContext.getRedisHost());
        assertNotNull(RedisJunitTestContainerContext.getRedisPort());
    }
}
