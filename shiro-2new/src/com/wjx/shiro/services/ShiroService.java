package com.wjx.shiro.services;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;

import javax.security.auth.Subject;
import java.util.Date;

public class ShiroService {

    @RequiresRoles({"admin"})
    public void testMethod(){
        System.out.println("testMethod, time: " + new Date());

        Session session = SecurityUtils.getSubject().getSession();
        Object value = session.getAttribute("Mykey");
        System.out.println("Service Session Value:" + value);
    }
}
