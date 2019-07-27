package com.wjx.hello;


import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloController2 implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("hello2......");
        ModelAndView mv = new ModelAndView();
        //设置视图名称
        mv.setViewName("WEB-INF/views/welcome.jsp");

        //设置共享数据
        mv.addObject("msg","你好SpringMVC  22222");
        return mv;
    }
}
