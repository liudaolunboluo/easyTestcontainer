package com.zyf.easytestcontainer.runner.spring;

import com.zyf.easytestcontainer.runner.ITestContainerRunner;
import com.zyf.easytestcontainer.utils.EnvironmentUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;

/**
 * @author zhangyunfan@fiture.com
 * @version 1.0
 * @ClassName: AbstractTestContainerRunner
 * @Description: Abstract TestContainer Runner
 * @date 2021/10/17
 */
@Slf4j
public abstract class AbstractSpringTestContainerRunner extends SpringJUnit4ClassRunner implements ITestContainerRunner {

    protected AbstractSpringTestContainerRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    public void run(RunNotifier notifier) {
        if (needToIgnore()) {
            return;
        }
        try (GenericContainer<?> container = initContainer()) {
            container.start();
            setSpringConfiguration(container);
            super.run(notifier);
        }
    }

    @Override
    public String getTestClassName() {
        return getTestClass().getJavaClass().getName();
    }

    /**
     * 覆盖spring的配置
     *
     * @param container:容器
     * @author zhangyunfan
     * @date 2021/10/17
     */
    protected abstract void setSpringConfiguration(GenericContainer<?> container);

}
