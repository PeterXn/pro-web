package com.atguigu.book.controller;

import com.atguigu.book.pojo.OrderBean;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.OrderService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/4/4 0:55
 * @description TODO
 */
public class OrderController {
    private OrderService orderService;

    /**
     * 结账
     */
    public String checkout(HttpSession session) {
        OrderBean orderBean = new OrderBean();
        LocalDateTime datetime = LocalDateTime.now();
        String timeForm = "yyyyMMddHHmmssSSS";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(timeForm);
        String dateTimeStr = dateTimeFormatter.format(datetime);
        System.out.println("dateTimeStr = " + dateTimeStr);

        orderBean.setOrderNo(dateTimeStr + "-" + UUID.randomUUID().toString().toUpperCase().replaceAll("-", ""));
        orderBean.setOrderDate(new Date());

        User user = (User) session.getAttribute("currUser");
        orderBean.setOrderUser(user);
        orderBean.setOrderMoney(user.getCart().getTotalMoney());
        orderBean.setOrderStatus(0);

        // 订单表中添加购物书本的总数量
        orderBean.setTotalBookCount(user.getCart().getTotalBookCount());

        // orderBean保存到session以取订单号
        session.setAttribute("orderBean", orderBean);

        orderService.addOrderBean(orderBean);

        //return "index";
        return "cart/checkout";
    }

    /**
     * 查看订单列表
     */
    public String getOrderList(HttpSession session) {
        User user = (User) session.getAttribute("currUser");

        List<OrderBean> orderList = orderService.getOrderList(user);
        user.setOrderList(orderList);

        session.setAttribute("currUser", user);

        // pages/order/order.html
        return "order/order";
    }
}
