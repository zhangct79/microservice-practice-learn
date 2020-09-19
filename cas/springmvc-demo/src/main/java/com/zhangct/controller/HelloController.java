package com.zhangct.controller;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class HelloController {


    @RequestMapping("index")
    public String index(HttpSession session, String user) {
        if (session.getAttribute("user")==null)
            session.setAttribute("user",user);
        else {
            user = session.getAttribute("user").toString();
        }
        return "springmvc index\n" + user+"\n"+session.getMaxInactiveInterval();
    }

    /**
     * hello
     * @return
     */
    @RequestMapping("hello")
    public String hello(HttpSession session, String user) {
        if (session.getAttribute("user")==null)
            session.setAttribute("user",user);
        else {
            user = session.getAttribute("user").toString();
        }
        System.out.println("hello");
        return "springmvc hello world\n" + user+"\n"+session.getMaxInactiveInterval();
    }

    @RequestMapping("setcookie")
    public String setcookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("cookie10","cookie10");//创建新cookie
        cookie.setMaxAge(10);
        response.addCookie(cookie);

        cookie = new Cookie("cookie60","cookie60");//创建新cookie
        cookie.setMaxAge(60);
        response.addCookie(cookie);

        return "springmvc setcookie\n" ;
    }

    @RequestMapping("getcookie")
    public String getcookie(@CookieValue(name="cookie10",defaultValue = "null")String cookie10,@CookieValue(name="cookie60",defaultValue = "null")String cookie60) {
        StringBuilder builder = new StringBuilder();
        builder.append("cookie10="+cookie10+"\n");
        builder.append("cookie60="+cookie60);

        return builder.toString();
    }
}
