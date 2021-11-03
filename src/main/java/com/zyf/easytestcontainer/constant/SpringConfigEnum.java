package com.zyf.easytestcontainer.constant;

import lombok.Getter;

/**
 * @author zhangyunfan@fiture.com
 * @version 1.0
 * @ClassName: SpringConfigConstans
 * @Description: Spring配置类枚举
 * @date 2021/10/20
 */
@Getter
public enum SpringConfigEnum {

    DATASOURCE_URL("spring.datasource.url"),

    DATASOURCE_DRIVER_CLASS_NAME("spring.datasource.driver-class-name"),

    DATASOURCE_USERNAME("spring.datasource.username"),

    DATASOURCE_PASSWORD("spring.datasource.password"),

    DATASOURCE_SCHEMA("spring.datasource.schema"),

    DATASOURCE_DATA("spring.datasource.data"),

    REDIS_HOST("spring.redis.host"),

    REDIS_PORT("spring.redis.port"),

    REDIS_PASSWORD("spring.redis.password"),

    ELASTICSEARCH_PORT("elasticsearch.port"),

    ELASTICSEARCH_PASSWORD("elasticsearch.port"),

    ELASTICSEARCH_HOST("elasticsearch.host");

    private final String value;

    SpringConfigEnum(String value) {
        this.value = value;
    }
}
