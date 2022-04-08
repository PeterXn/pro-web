package com.atguigu.jdbc;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/16 14:36
 * @description TODO
 */
public class Demo06 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1. 添加jar
        //2. 加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //3. 通过驱动加载器获取连接对象
        String url = "jdbc:mysql://localhost:3306/fruitdb?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8";
        String user = "root";
        String pwd = "123456";
        Connection conn = DriverManager.getConnection(url, user, pwd);

        String sql = "select count(*) from t_fruit";

        PreparedStatement psmt = conn.prepareStatement(sql);

        // 执行查询，返回结果集
        ResultSet rs = psmt.executeQuery(sql);

        while (rs.next()) {
            int count = rs.getInt(1);
            System.out.println("count = " + count);

        }

        // 释放资源
        rs.close();
        psmt.close();
        conn.close();
    }
}
