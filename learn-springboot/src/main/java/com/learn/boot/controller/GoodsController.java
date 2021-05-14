package com.learn.boot.controller;

import com.learn.boot.config.VipCheck;
import org.springframework.web.bind.annotation.*;

/**
 * @author xu.rb
 * @description: TODO
 * @since 2021-05-15 01:24
 */
@RestController
public class GoodsController {

    @VipCheck
    @RequestMapping(value = "/getGoods")
    public String queryGoods(@RequestParam("vipLevel") Integer vipLevel,
                             @RequestParam("serviceType") String serviceType) {
        return "the result of normal by vipLevel = " + vipLevel + ", serviceType" + serviceType;
    }

    @RequestMapping(value = "/getOthers")
    public String queryOthers(@RequestParam("vipLevel") Integer vipLevel,
                             @RequestParam("serviceType") String serviceType) {
        return "query others vipLevel = " + vipLevel + ", serviceType = " + serviceType;
    }
}
