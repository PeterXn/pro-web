package com.atguigu.book.service;

import com.atguigu.book.pojo.Cart;
import com.atguigu.book.pojo.CartItem;
import com.atguigu.book.pojo.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/4/3 10:44
 * @description TODO
 */
public interface CartItemService {
    void addCartItem(CartItem cartItem);

    void updateCartItem(CartItem cartItem);

    void addOrUpdateCartItem(CartItem cartItem, Cart cart);

    /**
     * 加载指定用户的购物车信息
     */
    Cart getCart(User user);

    /**
     * 获取指定用户的所有购物车列表，这个方法查询时，会把book的详细信息设置进去
     * @param user
     * @return
     */
    List<CartItem> getCartItemList(User user);

    void delCartItem(CartItem cartItemId);
}
