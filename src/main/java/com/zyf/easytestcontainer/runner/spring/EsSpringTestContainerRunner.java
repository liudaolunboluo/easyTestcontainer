package com.zyf.easytestcontainer.runner.spring;

import com.zyf.easytestcontainer.constant.SpringConfigEnum;
import org.junit.runners.model.InitializationError;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * @author zhangyunfan@fiture.com
 * @version 1.0
 * @ClassName: EsSpringTestContainerRunner
 * @date 2021/10/27
 */
public class EsSpringTestContainerRunner extends AbstractSpringTestContainerRunner {

    private static final String ELASTICSEARCH_VERSION = "7.10.1";

    private static final int ES_PORT = 9300;

    public EsSpringTestContainerRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    public GenericContainer<?> initContainer() {
        ElasticsearchContainer container = new ElasticsearchContainer(
                DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch-oss").withTag(ELASTICSEARCH_VERSION));
        container.withPassword("test");
        return container;
    }

    @Override
    protected void setSpringConfiguration(GenericContainer<?> container) {
        System.setProperty(SpringConfigEnum.ELASTICSEARCH_HOST.getValue(), container.getContainerIpAddress());
        System.setProperty(SpringConfigEnum.ELASTICSEARCH_PORT.getValue(), String.valueOf(container.getMappedPort(ES_PORT)));
        System.setProperty(SpringConfigEnum.ELASTICSEARCH_PASSWORD.getValue(), "test");
    }
}
