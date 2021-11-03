package com.zyf.easytestcontainer.utils;

import lombok.experimental.UtilityClass;
import org.testcontainers.DockerClientFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * @author zhangyunfan@fiture.com
 * @version 1.0
 * @ClassName: EnvironmentUtils
 * @Description: 获取本机环境工具类
 * @date 2021/10/17
 */
@UtilityClass
public class EnvironmentUtils {

    private static final String ARM_KERNEL = "ARM64";

    private static final String DOCKER_VERSION = "Docker version";

    public boolean isArm() {
        try {
            return exec("uname -a").contains(ARM_KERNEL);
        } catch (IOException e) {
            return false;
        }
    }

    public boolean hasDocker() {
        try {
            return exec("docker -v").contains(DOCKER_VERSION);
        } catch (IOException e) {
            return false;
        }
    }

    private String exec(String command) throws IOException {
        String[] cmdA = { "/bin/sh", "-c", command };
        Process process = Runtime.getRuntime().exec(cmdA);
        try (LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        }
    }
}
