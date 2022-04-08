package com.atguigu.book.service.impl;

import com.atguigu.book.dao.CartItemDAO;
import com.atguigu.book.pojo.Book;
import com.atguigu.book.pojo.Cart;
import com.atguigu.book.pojo.CartItem;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.BookService;
import com.atguigu.book.service.CartItemService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/4/3 10:48
 * @description TODO
 */
public class CartItemServiceImpl implements CartItemService {
    private CartItemDAO cartItemDAO;
    private BookService bookService;

    @Override
    public void addCartItem(CartItem cartItem) {
        cartItemDAO.addCartItem(cartItem);
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        cartItemDAO.updateCartItem(cartItem);
    }

    /**
     * 1.如果当前用户的购物车中已经存在这个购物车中，那么将购物车中的这本图书的数量加1
     * 2.否则，在我的购物车中新增一个这本图书的CartItem的数量
     *
     * @param cartItem
     * @param cart
     */
    @Override
    public void addOrUpdateCartItem(CartItem cartItem, Cart cart) {
        // 判断当前用户的购物车中是否有这本书的CartItem，有则修改，没有就添加
        if (cart != null) {
            Map<Integer, CartItem> cartItemMap = cart.getCartItemMap();
            if (cartItemMap == null) {
                cartItemMap = new HashMap<>();
            }

            // 购物车存在这本书的cartItem，则修改
            if (cartItemMap.containsKey(cartItem.getBook().getId())) {
                CartItem cartItemTemp = cartItemMap.get(cartItem.getBook().getId());
                cartItemTemp.setBuyCount(cartItemTemp.getBuyCount() + 1);
                updateCartItem(cartItemTemp);
            } else {
                // 购物车不存在这本书的cartItem，则添加
                addCartItem(cartItem);
            }
        } else {
            // 购物车不存在
            addCartItem(cartItem);
        }
    }

    @Override
    public Cart getCart(User user) {
        List<CartItem> cartItemList = getCartItemList(user);
        Map<Integer, CartItem> cartItemMap = new HashMap<>();
        for (CartItem cartItem : cartItemList) {
            cartItemMap.put(cartItem.getBook().getId(), cartItem);
        }
        Cart cart = new Cart();
        cart.setCartItemMap(cartItemMap);

        // 为给这三个totalMoney,totalCount,totalBookCount属性赋值
        cart.getTotalBookCount();
        cart.getTotalMoney();
        cart.getTotalCount();

        return cart;
    }

    @Override
    public List<CartItem> getCartItemList(User user) {
        List<CartItem> cartItemList = cartItemDAO.getCartItemList(user);
        for (CartItem cartItem : cartItemList) {
            Book book = bookService.getBook(cartItem.getBook().getId());
            cartItem.setBook(book);
            // 此处调用getXj(),为了给xj属性赋值
            cartItem.getXj();
        }
        return cartItemList;
    }

    @Override
    public void delCartItem(CartItem cartItemId) {
        cartItemDAO.delCartItem(cartItemId);
    }
}
