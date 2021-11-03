package com.zyf.easytestcontainer.annotation;

import java.lang.annotation.*;

/**
 * 初始化redis测试容器的配置注解，主要是有场景需要用指定版本的redis镜像或者指定端口
 * @author zhangyunfan@fiture.com
 * @version 1.0
 * @ClassName: TestContainerRun
 * @date 2021/10/28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface RedisTestContainerConfig {

    String image() default "redis:latest";

}

