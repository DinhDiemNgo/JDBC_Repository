package com.dong.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 工具类
 * 利用线程本地变量存储连接信息,确保一个线程的多个方法可以获取同一个Connection
 * 优势：事务操作的时候 Service和 Dao 属于同一个线程,无需再次传递参数
 * 大家都可以调用getConnection自动获取的是相同的连接池
 */
public class DruidUtilV2 {
    private static DataSource dataSource = null;//连接池对象
    private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        //初始化连接池对象
        //1.读取外部配置文件 Properties
        Properties properties = new Properties();
        //src下的文件,可以使用类加载器提供的方法
        InputStream resourceAsStream = DruidUtilV2.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //2.使用连接池的工具类的工程模式,创建连接池
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //对外提供连接的方法
    public static Connection getConnection() throws SQLException {
        //线程本地变量中是否存在
        Connection connection = threadLocal.get();
        //首次没有
        if (connection == null) {
            //不存在线程本地变量,获取连接池
            connection = dataSource.getConnection();
            threadLocal.set(connection);
        }
        return connection;
    }

    public static void freeConnection() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection != null) {
            //清空线程本地变量数据
            threadLocal.remove();
            //事务状态回归
            connection.setAutoCommit(true);
            //回收到连接池即可
            connection.close();
        }
    }
}
