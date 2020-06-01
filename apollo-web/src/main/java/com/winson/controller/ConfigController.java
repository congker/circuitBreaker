/**
 * @description:hystrix-web
 * @author: winsonxu
 * @createTime:2020/6/1
 */
package com.winson.controller;

import com.winson.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {
    @Autowired
    private ApplicationConfig applicationConfig;

    @GetMapping("/config/get")
    public String getConfigValue() {
        return applicationConfig.getApolloHost();
    }
}
