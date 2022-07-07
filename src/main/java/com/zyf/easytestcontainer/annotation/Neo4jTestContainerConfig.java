package com.zyf.easytestcontainer.annotation;

/**
 * @author zhangyunfan@fiture.com
 * @version 1.0
 * @ClassName: Neo4jTestContainerConfig
 * @Description: neo4j配置
 * @date 2022/7/7
 */
public @interface Neo4jTestContainerConfig {

    /**
     * 镜像版本
     */
    String version() default "";
}
