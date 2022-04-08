package com.atguigu.jdbc;

import com.alibaba.druid.pool.DruidDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/17 17:12
 * @description TODO
 */
public class Demo04Druid {
    public static void main(String[] args) throws SQLException, IOException {
        Properties properties = new Properties();
        // jdbc.properties在src根目录下。
        InputStream is = Demo04Druid.class.getClassLoader().getResourceAsStream("jdbc.properties");
        properties.load(is);

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(properties.getProperty("jdbc.driver"));
        druidDataSource.setUrl(properties.getProperty("jdbc.url"));
        druidDataSource.setUsername(properties.getProperty("jdbc.user"));
        druidDataSource.setPassword(properties.getProperty("jdbc.password"));

        Connection conn = druidDataSource.getConnection();

        System.out.println("conn = " + conn);
    }
}
