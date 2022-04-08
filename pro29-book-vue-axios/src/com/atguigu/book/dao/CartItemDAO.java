package com.atguigu.book.dao;

import com.atguigu.book.pojo.CartItem;
import com.atguigu.book.pojo.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/4/3 10:26
 * @description TODO
 */
public interface CartItemDAO {
    /**
     * 新增购物车项
     * @param cartItem
     */
    void addCartItem(CartItem cartItem);

    /**
     * 修改指定的购物车项
     * @param cartItem
     */
    void updateCartItem(CartItem cartItem);

    /**
     * 获取指定用户的所有购物车项
     */
    List<CartItem> getCartItemList(User user);

    /**
     * 删除指定的购物车项
     */
    void delCartItem(CartItem cartItem);

}
