/**
 * @description:hystrix-web
 * @author: winsonxu
 * @createTime:2020/6/1
 */
package com.winson.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    /**
     * 方法描述
     */
    @GetMapping("/member/get")
    public String getMember() {
        return "调用 getMember() 获得会员信息成功！";
    }
}
