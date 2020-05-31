package com.hamlt.springboot_test.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 * @date 2020-05-31 14:43
 **/
@FeignClient(name = "hello")
public interface HelloApi {

    @RequestMapping("/test/2")
     String test2(@RequestParam(value = "name", defaultValue = "test2") String name, String k, HttpServletRequest request);

}
