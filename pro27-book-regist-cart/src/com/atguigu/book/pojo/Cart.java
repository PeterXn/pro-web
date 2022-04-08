package com.atguigu.book.pojo;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/4/3 10:56
 * @description 购物车类
 */
public class Cart {
    /**
     * 购物车中购物车项的集合，这个map集合中的key是book的id
     */
    private Map<Integer, CartItem> cartItemMap;
    /**
     * 购物车的总金额
     */
    private Double totalMoney;
    /**
     * 购物车中购物项的数量
     */
    private Integer totalCount;

    /**
     * 购物车中书本的总数量
     */
    private Integer totalBookCount;

    public Cart() {
    }

    public Map<Integer, CartItem> getCartItemMap() {
        return cartItemMap;
    }

    public void setCartItemMap(Map<Integer, CartItem> cartItemMap) {
        this.cartItemMap = cartItemMap;
    }

    public Double getTotalMoney() {
        totalMoney = 0.0;
        BigDecimal bdTotalMoney = BigDecimal.ZERO;
        if (cartItemMap != null && cartItemMap.size() > 0) {
            Set<Map.Entry<Integer, CartItem>> entries = cartItemMap.entrySet();
            for (Map.Entry<Integer, CartItem> cartItemEntry : entries) {
                CartItem cartItem = cartItemEntry.getValue();
                /**
                 * Double会有精度不准问题，例如：总金额248.85000000000002元
                 * totalMoney = totalMoney + cartItem.getBook().getPrice() * cartItem.getBuyCount();
                 */

                BigDecimal price = new BigDecimal(cartItem.getBook().getPrice().toString());
                BigDecimal buyCount = new BigDecimal(cartItem.getBuyCount().toString());
                BigDecimal multiply = price.multiply(buyCount);
                bdTotalMoney = multiply.add(bdTotalMoney);
            }
        }

        totalMoney = bdTotalMoney.doubleValue();

        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getTotalCount() {
        totalCount = 0;
        if (cartItemMap != null && cartItemMap.size() > 0) {
            return totalCount = cartItemMap.size();
        }
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalBookCount() {
        totalBookCount = 0;
        if (cartItemMap != null && cartItemMap.size() > 0) {
            for (CartItem cartItem : cartItemMap.values()) {
                totalBookCount = totalBookCount + cartItem.getBuyCount();
            }
        }
        return totalBookCount;
    }

    public void setTotalBookCount(Integer totalBookCount) {
        this.totalBookCount = totalBookCount;
    }
}
