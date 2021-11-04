package com.zyf.easytestcontainer.runner.spring;

import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.zyf.easytestcontainer.constant.SpringConfigEnum;
import org.junit.runners.model.InitializationError;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.function.Consumer;

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
        int hostPort = ES_PORT;
        int containerExposedPort = ES_PORT;
        Consumer<CreateContainerCmd> cmd = e -> e.withPortBindings(new PortBinding(Ports.Binding.bindPort(hostPort), new ExposedPort(containerExposedPort)));
        ElasticsearchContainer container = new ElasticsearchContainer(
                DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch-oss").withTag(ELASTICSEARCH_VERSION));
        container.withPassword("test");
        container.withExposedPorts(containerExposedPort);
        container.withCreateContainerCmdModifier(cmd);
        return container;
    }

    @Override
    protected void setSpringConfiguration(GenericContainer<?> container) {
        System.setProperty(SpringConfigEnum.ELASTICSEARCH_HOST.getValue(), container.getContainerIpAddress());
        System.setProperty(SpringConfigEnum.ELASTICSEARCH_PORT.getValue(), String.valueOf(container.getMappedPort(ES_PORT)));
        System.setProperty(SpringConfigEnum.ELASTICSEARCH_PASSWORD.getValue(), "test");
    }
}
