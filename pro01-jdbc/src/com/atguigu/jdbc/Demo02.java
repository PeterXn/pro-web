package com.atguigu.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/16 14:01
 * @description TODO
 */
public class Demo02 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1. 添加jar
        //2. 加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //3. 通过驱动加载器获取连接对象
        String url = "jdbc:mysql://localhost:3306/fruitdb?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8";
        String user = "root";
        String pwd = "123456";
        Connection conn = DriverManager.getConnection(url, user, pwd);

        String sql = "insert into t_fruit values(0,?,?,?,?)";

        // 创建处理命令
        PreparedStatement psmt = conn.prepareStatement(sql);
        // 填充参数
        psmt.setString(1, "苹果");
        psmt.setInt(2, 15);
        psmt.setInt(3, 100);
        psmt.setString(4, "苹果是一种神奇的水果。");

        // 执行更新
        int count = psmt.executeUpdate();
        System.out.println(count > 0?"添加成功":"添加失败");

        // 释放资源
        psmt.close();
        conn.close();
    }
}
