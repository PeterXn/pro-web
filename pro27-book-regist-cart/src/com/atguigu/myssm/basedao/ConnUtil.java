package com.atguigu.myssm.basedao;

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
 * @date 2022/3/27 20:43
 * @description TODO
 */
public class ConnUtil {
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public static String driver;
    public static String url;
    public static String user;
    public static String pwd;

    static {
        InputStream is = ConnUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
            user = properties.getProperty("jdbc.user");
            pwd = properties.getProperty("jdbc.pwd");
            url = properties.getProperty("jdbc.url");
            driver = properties.getProperty("jdbc.driver");
        } catch (IOException e) {
            e.printStackTrace();
            throw new DAOException("获取jdbc.properties配置失败，请重试。");
        }
    }

    /**
     * 使用Druid连接池
     *
     * @return
     */
    public static Connection createConn() {
        try {
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setUsername(user);
            druidDataSource.setPassword(pwd);
            druidDataSource.setUrl(url);
            druidDataSource.setDriverClassName(driver);

            return druidDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("ConnUtil ... createConn() ... DruidDataSource初始化失败，请重试。");
        }
    }

    /**
    public static Connection createConnNoPool() {
        try {
            //1. 添加jar
            //2. 加载驱动
            Class.forName(driver);
            //3. 通过驱动加载器获取连接对象
            return DriverManager.getConnection(url, user, pwd);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
     **/

    public static Connection getConn() {
        Connection conn = threadLocal.get();
        if (conn == null) {
            conn = ConnUtil.createConn();
            threadLocal.set(conn);
        }
        return threadLocal.get();
    }

    public static void closeConn() throws SQLException {
        Connection conn = threadLocal.get();
        if (conn == null) {
            return;
        }
        if (!conn.isClosed()) {
            conn.close();
            threadLocal.set(null);
        }
    }
}
