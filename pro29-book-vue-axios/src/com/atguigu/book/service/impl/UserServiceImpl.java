package com.atguigu.book.service.impl;

import com.atguigu.book.dao.UserDAO;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.UserService;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/4/2 17:35
 * @description TODO
 */
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    @Override
    public User login(String uname, String pwd) {
        return userDAO.getUser(uname, pwd);
    }

    @Override
    public void register(User user) {
        userDAO.addUser(user);
    }

    @Override
    public User getUserByName(String uname) {
        return userDAO.getUserByName(uname);
    }
}
