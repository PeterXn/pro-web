package com.atguigu.book.dao;

import com.atguigu.book.pojo.OrderBean;
import com.atguigu.book.pojo.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/4/4 0:11
 * @description TODO
 */
public interface OrderDAO {
    /**
     * 添加订单
     */
    void addOrderBean(OrderBean orderBean);

    /**
     * 获取指定用户的订单列表
     */
    List<OrderBean> getOrderList(User user);

}
