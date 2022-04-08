package com.atguigu.book.service;

import com.atguigu.book.pojo.User;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/4/2 17:34
 * @description TODO
 */
public interface UserService {
    User login(String uname, String pwd);
}
