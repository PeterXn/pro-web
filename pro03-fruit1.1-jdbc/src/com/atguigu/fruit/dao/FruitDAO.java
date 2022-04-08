package com.atguigu.fruit.dao;

import com.atguigu.fruit.pojo.Fruit;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/16 16:23
 * @description TODO
 */
public interface FruitDAO {
    // 查询库存列表
    List<Fruit> getFruitList();

    // 新增库存
    boolean addFruit(Fruit fruit);

    // 修改库存
    boolean updateFruit(Fruit fruit);

    // 根据名称查询特定库存
    Fruit getFruitByFname(String fname);

    // 删除特定库存记录
    boolean delFruit(String fname);
}
