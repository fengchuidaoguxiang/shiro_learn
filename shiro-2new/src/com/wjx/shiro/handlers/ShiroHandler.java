package com.wjx.shiro.handlers;

import com.wjx.shiro.services.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@RequestMapping("/shiro")
@Controller
public class ShiroHandler {

    @Autowired
    private ShiroService shiroService;

    @GetMapping("/testShiroAnnotation")
    public String testShiroAnnotation(HttpSession session){
        session.setAttribute("Mykey", "value12345");
        shiroService.testMethod();
        return "redirect:/list.jsp";
    }

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password){

        // TODO：获取当前的Subject,调用SecurityUtils.getSubject();
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            //TODO:把用户和密码封装为UsernamePasswordToken对象
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //TODO:“记住我”功能
            token.setRememberMe(true);
            try {
                System.out.println("1." + token.hashCode());
                // TODO:执行登录，
                currentUser.login(token);
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            //TODO:兜底异常：所有认证异常的父类
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
                System.err.println( "登录失败：" + ae.getMessage() );
            }
        }
        return "redirect:/list.jsp";
    }
}
