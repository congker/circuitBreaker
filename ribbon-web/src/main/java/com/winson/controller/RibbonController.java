/**
 * @description:hystrix-web
 * @author: winsonxu
 * @createTime:2020/6/1
 */
package com.winson.controller;

import com.winson.ribbon.MyRibbon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Ribbon")
@Slf4j
public class RibbonController {
    @Autowired
    private MyRibbon myRibbon;

    @ResponseBody
    @GetMapping(value = "/ribbon/test")
    public String ribbonMember() {
        // 1.获取服务名称对应服务列表
        String res = myRibbon.ribbonGet("user-server", "/user/get");
        return res;
    }
}
