/**
 * @description:hystrix-web
 * @author: winsonxu
 * @createTime:2020/6/1
 */
package com.winson.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MemberController {
    // 1. 设置回调方法；设置忽略的异常，如果是报这个异常，Hystrix 不会进行捕获并执行降级方法，接口会直接抛出异常
    @HystrixCommand(fallbackMethod = "inserMemberFallback", ignoreExceptions = {ArithmeticException.class})
    @GetMapping("/member/insert")
    public String insertMember() {
        int i = 10 / 0;
        return "添加会员信息成功！";
    }

    // 2.设置 commandProperties 参数，设置窗口时间内允许的错误请求阈值，错误请求百分比，熔断器持续时间配置；
    // 设置 threadPoolProperties 参数，设置线程隔离的线程池线程数，最大等待请求数
    @HystrixCommand(fallbackMethod = "deleteMemberFallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
            })
    @GetMapping("/member/delete/{id}")
    public String deleteMember(@PathVariable("id") String id) {
        if ("1".equals(id)) {
            int i = 10 / 0;
        }
        return "删除会员信息成功";
    }

    public String inserMemberFallback() {
        return "调用 insertMember() 失败，执行降级方法！";
    }

    public String deleteMemberFallback(String id) {
        return "调用 deleteMember() 失败，执行降级方法！";
    }

}
