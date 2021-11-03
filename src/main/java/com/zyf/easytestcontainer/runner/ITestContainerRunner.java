package com.zyf.easytestcontainer.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;

/**
 * @author zhangyunfan@fiture.com
 * @version 1.0
 * @ClassName: ITestContainerRunner
 * @date 2021/10/27
 */
public interface ITestContainerRunner {

    Logger log = LoggerFactory.getLogger(ITestContainerRunner.class.getName());

    /**
     * 是否需要忽略此单元测试
     *
     * @return boolean
     * @author zhangyunfan
     * @date 2021/10/17
     */
    default boolean needToIgnore() {
        if (!DockerClientFactory.instance().isDockerAvailable()) {
            log.warn("no Docker environment,ignore this junit test,className:{}", getTestClassName());
            return true;
        }
        return false;
    }

    /**
     * 获取当前测试类的名字
     *
     * @return String
     * @author zhangyunfan
     * @date 2021/10/27
     */
    String getTestClassName();

    /**
     * 初始化容器
     *
     * @return GenericContainer
     * @author zhangyunfan
     * @date 2021/10/27
     */
    GenericContainer<?> initContainer();
}
