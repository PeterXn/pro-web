package com.atguigu.jdbc;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/17 17:12
 * @description TODO
 */
public class Demo02Druid {
    public static void main(String[] args) throws SQLException {
        final String DRIVER = "com.mysql.cj.jdbc.Driver";
        final String URL = "jdbc:mysql://localhost:3306/fruitdb?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8";
        final String USER = "root";
        final String PWD = "123456";

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(DRIVER);
        druidDataSource.setUrl(URL);
        druidDataSource.setUsername(USER);
        druidDataSource.setPassword(PWD);

        Connection conn = druidDataSource.getConnection();

        System.out.println("conn = " + conn);
    }
}
