package com.atguigu.book.dao.impl;

import com.atguigu.book.dao.OrderDAO;
import com.atguigu.book.pojo.OrderBean;
import com.atguigu.book.pojo.User;
import com.atguigu.myssm.basedao.BaseDAO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/4/4 0:15
 * @description TODO
 */
public class OrderDAOImpl extends BaseDAO<OrderBean> implements OrderDAO {
    @Override
    public void addOrderBean(OrderBean orderBean) {
        String sql = "insert into t_order values(0,?,?,?,?,?,?)";
        int orderBeanId = super.executeUpdate(sql, orderBean.getOrderNo(), orderBean.getOrderDate(),
                orderBean.getOrderUser().getId(), orderBean.getOrderMoney(),
                orderBean.getOrderStatus(), orderBean.getTotalBookCount());
        // 此处为什么需要接受executeUpdate的返回值，然后设置到orderItem的id属性上？
        orderBean.setId(orderBeanId);
    }

    @Override
    public List<OrderBean> getOrderList(User user) {
        return executeQuery("select * from t_order where orderUser = ?", user.getId());
    }
}
