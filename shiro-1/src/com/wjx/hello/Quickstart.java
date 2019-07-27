package com.wjx.hello;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Simple Quickstart application showing how to use Shiro's API.
 *
 * @since 0.9 RC2
 */
public class Quickstart {

    private static final transient Logger log = LoggerFactory.getLogger(Quickstart.class);


    public static void main(String[] args) {

        // The easiest way to create a Shiro SecurityManager with configured
        // realms, users, roles and permissions is to use the simple INI config.
        // We'll do that by using a factory that can ingest a .ini file and
        // return a SecurityManager instance:

        // Use the shiro.ini file at the root of the classpath
        // (file: and url: prefixes load from files and urls respectively):
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();

        // for this simple example quickstart, make the SecurityManager
        // accessible as a JVM singleton.  Most applications wouldn't do this
        // and instead rely on their container configuration or web.xml for
        // webapps.  That is outside the scope of this simple quickstart, so
        // we'll just do the bare minimum so you can continue to get a feel
        // for things.
        SecurityUtils.setSecurityManager(securityManager);

        // Now that a simple Shiro environment is set up, let's see what you can do:

        // get the currently executing user:
        // TODO：获取当前的Subject,调用SecurityUtils.getSubject();
        Subject currentUser = SecurityUtils.getSubject();

        // Do some stuff with a Session (no need for a web or EJB container!!!)
        //TODO: 测试使用Session
        //获取 Session：Subject#getSession()
        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            log.info("--------------> Retrieved the correct value! [" + value + "]");
        }

        // let's login the current user so we can check against roles and permissions:
        //TODO:测试当前的用户是否已经被认证，即是否已经登录。
        //TODO:调动 Subject 的 isAuthenticated()
        if (!currentUser.isAuthenticated()) {
            //TODO:把用户和密码封装为UsernamePasswordToken对象
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            //TODO:“记住我”功能
            token.setRememberMe(true);
            try {
                // TODO:执行登录，
                currentUser.login(token);
            }
            //TODO:若账户不存在，则 shiro 将会抛出 UnknownAccountException 异常
            catch (UnknownAccountException uae) {
                log.error("----->There is no user with username of " + token.getPrincipal());
                return;
            }
            // TODO:若密码不对，则 shiro 将会抛出 IncorrectCredentialsException 异常
            catch (IncorrectCredentialsException ice) {
                log.error("----->Password for account " + token.getPrincipal() + " was incorrect!");
                return;
            }
            // TODO:若用户被锁定，则 shiro 抛出 LockedAccountException 异常
            catch (LockedAccountException lae) {
                log.error("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            //TODO:兜底异常：所有认证异常的父类
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }

        //say who they are:
        //print their identifying principal (in this case, a username):
        log.info("---------> User [" + currentUser.getPrincipal() + "] logged in successfully.");

        //test a role:
        // TODO:测试是否有某一个角色, 调用Subject的 hasRole 方法
        if (currentUser.hasRole("schwartz")) {
            log.info(" ---------> May the Schwartz be with you!");
        } else {
            log.info("----------->Hello, mere mortal.");
            return;
        }

        //test a typed permission (not instance-level)
        // TODO:测试用户是否具备某一个权限(即用户是否具备某一个行为)。调用Subject的 isPermitted() 方法
        if (currentUser.isPermitted("lightsaber:wieldxxxx")) {
            log.info("------------->You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }

        //a (very powerful) Instance Level permission:
        // TODO:测试用户是否具备某一个权限(即用户是否具备某一个行为)。调用Subject的 isPermitted() 方法
        if (currentUser.isPermitted("user:delete:zhangsan")) {
            log.info("-----> You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        System.out.println("-------->" + currentUser.isAuthenticated());

        //all done - log out!
        //TODO:执行“登出”，调用 Subject 的 Logout() 方法
        currentUser.logout();

        System.out.println("-------->" + currentUser.isAuthenticated());

        System.exit(0);
    }
}
