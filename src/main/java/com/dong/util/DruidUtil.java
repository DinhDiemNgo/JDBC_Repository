package com.dong.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.dong.api.druid.DruidUsePart;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 工具类
 * 内部包含一个连接池对象,并且对外提供获取连接和回收连接的方法
 * 工具类的方法建议写成静态,外部调用会更遍历
 * <p>
 * 实现：
 * 属性 连接池对象[实例化一次]
 * 单例模式
 * 方法
 */
public class DruidUtil {
    private static DataSource dataSource = null;//连接池对象

    static {
        //初始化连接池对象
        //1.读取外部配置文件 Properties
        Properties properties = new Properties();
        //src下的文件,可以使用类加载器提供的方法
        InputStream resourceAsStream = DruidUtil.class.getClassLoader().getResourceAsStream("druid.properties");
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
        return dataSource.getConnection();
    }

    public static void freeConnection(Connection connection) throws SQLException {
        //连接池的连接,调用close就是回收
        connection.close();
    }
}
