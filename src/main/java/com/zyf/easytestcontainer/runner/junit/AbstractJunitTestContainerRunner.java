package com.zyf.easytestcontainer.runner.junit;

import com.zyf.easytestcontainer.runner.ITestContainerRunner;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.notification.RunNotifier;
import org.mockito.junit.MockitoJUnitRunner;
import org.testcontainers.containers.GenericContainer;

import java.lang.reflect.InvocationTargetException;

/**
 * @author zhangyunfan@fiture.com
 * @version 1.0
 * @ClassName: AbstractJunitTestContainerRunner
 * @date 2021/10/27
 */
@Slf4j
public abstract class AbstractJunitTestContainerRunner extends MockitoJUnitRunner implements ITestContainerRunner {

    private String TEST_NAME;

    protected Class<?> testClass;

    /**
     * Creates a MockitoJUnitRunner to run {@code klass}
     *
     * @param klass
     * @throws InvocationTargetException if the test class is malformed.
     */
    protected AbstractJunitTestContainerRunner(Class<?> klass) throws InvocationTargetException {
        super(klass);
        this.testClass = klass;
        TEST_NAME = klass.getSimpleName();
    }

    @Override
    public void run(RunNotifier notifier) {
        if (needToIgnore()) {
            return;
        }
        try (GenericContainer<?> container = initContainer()) {
            container.start();
            super.run(notifier);
        }
        containerCloseCallback();
    }

    protected abstract void containerCloseCallback();

    @Override
    public String getTestClassName() {
        return TEST_NAME;
    }
}
