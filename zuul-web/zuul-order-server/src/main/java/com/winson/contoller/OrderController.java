/**
 * @description:hystrix-web
 * @author: winsonxu
 * @createTime:2020/6/1
 */
package com.winson.contoller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    /**
     * 方法描述
     */
    @GetMapping("/order/get")
    public String getOrder() {
        return "调用 getOrder() 获得订单成功！";
    }
}
