package com.atguigu.book.dao.impl;

import com.atguigu.book.dao.OrderItemDAO;
import com.atguigu.book.pojo.OrderItem;
import com.atguigu.myssm.basedao.BaseDAO;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/4/4 0:21
 * @description TODO
 */
public class OrderItemDAOImpl extends BaseDAO<OrderItem> implements OrderItemDAO {
    @Override
    public void addOrderItem(OrderItem orderItem) {
        String sql = "insert into t_order_item values(0,?,?,?)";
        int orderItemId = super.executeUpdate(sql, orderItem.getBook().getId(), orderItem.getBuyCount(), orderItem.getOrderBean().getId());
        /**
         * 此处为什么需要接受executeUpdate的返回值，然后设置到orderItem的id属性上？
         * 此处不需要
         */
        orderItem.setId(orderItemId);
    }
}
