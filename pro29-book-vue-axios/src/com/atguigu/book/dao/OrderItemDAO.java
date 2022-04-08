package com.atguigu.book.dao;

import com.atguigu.book.pojo.OrderItem;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/4/4 0:12
 * @description TODO
 */
public interface OrderItemDAO {
    /**
     * 添加订单项
     */
    void addOrderItem(OrderItem orderItem);
}
