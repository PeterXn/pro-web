package com.atguigu.jdbc;

import com.sun.org.apache.bcel.internal.classfile.PMGClass;

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
public class Demo01Batch {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1. 添加jar
        //2. 加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //3. 通过驱动加载器获取连接对象
        // 批处理操作一、如果执行批处理任务，须添加rewriteBatchedStatements=true
        String url = "jdbc:mysql://localhost:3306/fruitdb?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8&rewriteBatchedStatements=true";
        String user = "root";
        String pwd = "123456";
        Connection conn = DriverManager.getConnection(url, user, pwd);

        String sql = "insert into t_fruit values(0,?,?,?,?)";

        // 创建处理命令
        PreparedStatement psmt = conn.prepareStatement(sql);

        for (int i = 0; i < 100; i++) {
            // 填充参数
            psmt.setString(1, "苹果"+i);
            psmt.setInt(2, 15+i);
            psmt.setInt(3, 100+i);
            psmt.setString(4, "苹果是一种神奇的水果。");

            // 批处理操作二、
            psmt.addBatch();

            // 如果任务较多，分批次完成，每次完成1000次。
            if (i % 1000 == 0) {
                psmt.executeBatch();
                psmt.clearBatch();
            }
        }
        // 批处理三、
        int[] count = psmt.executeBatch();
        for (int i = 0; i < count.length; i++) {
            System.out.println(count[i]);
        }

        // 释放资源
        psmt.close();
        conn.close();
    }
}
