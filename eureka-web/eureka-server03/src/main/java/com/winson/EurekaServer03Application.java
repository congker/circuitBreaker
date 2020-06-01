/**
 * @description:hystrix-web
 * @author: winsonxu
 * @createTime:2020/6/1
 */
package com.winson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServer03Application {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer03Application.class, args);
    }
}
