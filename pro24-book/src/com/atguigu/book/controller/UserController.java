package com.atguigu.book.controller;

import com.atguigu.book.pojo.User;
import com.atguigu.book.service.UserService;

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

    public String login(String uname, String pwd) {
        User user = userService.login(uname, pwd);

        System.out.println("user = " + user);

        return "index";
    }
}
