package com.zyf.easytestcontainer.annotation;

import java.lang.annotation.*;

/**
 * mysql测试容器的配置注解，必须使用，必须配置数据库脚本路径，其他的可以根据情况配置s
 * @author zhangyunfan@fiture.com
 * @version 1.0
 * @ClassName: InitMysql
 * @Description: tag to initialize mysql script
 * @date 2021/10/16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface MysqlTestContainerConfig {

    /**
     * 数据库脚本路径
     */
    String dbScript() default "";

    /**
     * mysql的X86架构下的镜像
     */
    String image() default "mysql:5.7.28";

    /**
     * mysql的ARM架构下的镜像
     */
    String armImage() default "mysql/mysql-server:5.7.28";

    /**
     * 数据库拓展配置
     */
    String urlParam() default "?createDatabaseIfNotExist=true&autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8";

}
