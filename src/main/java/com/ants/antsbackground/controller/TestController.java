package com.ants.antsbackground.controller;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@RestController
@RequestMapping(value = "/background/test")
public class TestController {

    @GetMapping(value = "/ipTest")
    public void tes(HttpServletRequest request){
        if (request.getHeader("x-forwarded-for") == null) {
            System.out.println(request.getRemoteAddr());
        }
        System.out.println(request.getHeader("x-forwarded-for"));
    }
}
