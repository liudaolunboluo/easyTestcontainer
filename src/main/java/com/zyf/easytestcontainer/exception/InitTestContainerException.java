package com.zyf.easytestcontainer.exception;

/**
 * @author zhangyunfan@fiture.com
 * @version 1.0
 * @ClassName: InitTestContainerException
 * @Description: 初始化测试容器异常
 * @date 2021/10/17
 */
public class InitTestContainerException extends RuntimeException {

    private static final long serialVersionUID = -6049212654571864598L;

    public InitTestContainerException(String message) {super(message);}
}
