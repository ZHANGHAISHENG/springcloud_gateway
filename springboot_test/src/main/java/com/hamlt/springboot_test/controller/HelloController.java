package com.hamlt.springboot_test.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 * @date 2020-05-25 23:55
 **/
@RestController
public class HelloController {

    @RequestMapping("/test/1")
    public String test1(@RequestParam(value = "name", defaultValue = "test1") String name, HttpServletRequest request) {
        return String.format("Hello %s! userId %s, userName %s, val %s", name, request.getHeader("userId"), request.getHeader("userName"), request.getAttribute("val1"));
    }

    @RequestMapping("/test/2")
    public String test2(@RequestParam(value = "name", defaultValue = "test2") String name, String k, HttpServletRequest request) {
        return String.format("Hello %s! userId %s, userName %s, k %s, val %s", name, request.getHeader("userId"), request.getHeader("userName"), k, request.getAttribute("val1"));
    }



}
