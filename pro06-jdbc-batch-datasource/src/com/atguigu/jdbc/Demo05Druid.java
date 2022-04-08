package com.atguigu.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
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
public class Demo05Druid {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        // jdbc.properties在src根目录下。
        InputStream is = Demo05Druid.class.getClassLoader().getResourceAsStream("jdbc2.properties");
        properties.load(is);

        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        Connection conn = dataSource.getConnection();

        System.out.println("conn = " + conn);

        conn.close();
    }
}
