package com.atguigu.qqzone.dao;

import com.atguigu.qqzone.pojo.UserBasic;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/29 22:40
 * @description TODO
 */
public interface UserBasicDAO {
    // 根据账号和密码获取特定用户信息
    public UserBasic getUserBasic(String loginId, String pwd);

    // 获取指定用户的所有好友列表
    public List<UserBasic> getUserBasicList(UserBasic userBasic);

    // 根据id查询UserBasic的信息
    UserBasic getUserBasicById(Integer id);
}
