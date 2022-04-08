package com.atguigu.book.dao;

import com.atguigu.book.pojo.User;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/4/2 17:24
 * @description TODO
 */
public interface UserDAO {
    User getUser(String uname, String pwd);

    void addUser(User user);

    User getUserByName(String uname);
}
