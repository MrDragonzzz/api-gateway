package com.ljy.gateway.engin;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @description:启动服务
 * @author: 龙嘉翼
 * @Date: 2023/5/17
 */

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Configurable
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
