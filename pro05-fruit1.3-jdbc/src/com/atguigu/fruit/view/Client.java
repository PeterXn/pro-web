package com.atguigu.fruit.view;

import com.atguigu.fruit.controller.Menu;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/16 15:55
 * @description TODO
 */
public class Client {
    public static void main(String[] args) {
        Menu menu = new Menu();

        boolean flag = true;
        while (flag) {
            // 调用显示主菜单的方法
            int slt = menu.showMainMenu();
            switch (slt) {
                case 1:
                    // 显示库存列表
                    menu.showFruitList();
                    break;
                case 2:
                    menu.AddFruit();
                    break;
                case 3:
                    // 查询指定水果信息
                    menu.showFruitInfo();
                    break;
                case 4:
                    // 下架水果
                    menu.deleteFruit();
                    break;
                case 5:
                    flag = menu.exit();
                    break;

                default:
                    System.out.println("你不按套路出牌~");
                    break;
            }
        }

        System.out.println("谢谢使用，再见~");

    }
}
