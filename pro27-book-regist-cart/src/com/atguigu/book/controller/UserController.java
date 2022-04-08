package com.atguigu.book.controller;

import com.atguigu.book.pojo.Cart;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.CartItemService;
import com.atguigu.book.service.UserService;
import com.atguigu.myssm.util.StringUtil;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/4/2 17:22
 * @description TODO
 */
public class UserController {
    private UserService userService;
    private CartItemService cartItemService;

    public String login(String uname, String pwd, HttpSession session) {
        User user = userService.login(uname, pwd);
        System.out.println("user = " + user);

        if (user != null) {
            // 加载购物车
            Cart cart = cartItemService.getCart(user);
            user.setCart(cart);
            session.setAttribute("currUser", user);
            return "redirect:book.do";
        }

        return "user/login";
    }

    public String regist(String uname, String pwd, String email, String verifyCode,
                         HttpSession session, HttpServletResponse response) throws IOException {
        Object kaptchaSessionKey = session.getAttribute("KAPTCHA_SESSION_KEY");
        if (kaptchaSessionKey == null || !verifyCode.equals(kaptchaSessionKey)) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.println("<script language='javascript'>alert('验证码不正确，请重试！');</script> ");
            return "user/regist";
        } else {
            if (verifyCode.equals(kaptchaSessionKey)) {
                // 注册之前先查询这个用户名是否存在
                userService.register(new User(uname, pwd, email, 0));
                return "user/login";
            }
        }
        return "user/login";
    }

    /**
     * 用户名异步认证
     * @param uname
     * @return
     */
    public String checkUname(String uname) {
        if (StringUtil.isNotEmpty(uname)) {
            User user = userService.getUserByName(uname);
            if (user != null) {
                // 用户名已占用，不能注册
                return "json:{'uname':'1'}";
            } else {
                // 用户名未占用，能注册
                return "json:{'uname':'0'}";
            }
        }
        return "json:{'errMsg':'uname为空，请重试。'}";
    }
}
