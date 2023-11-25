package com.dong.api.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 */
public class DruidUsePart {
    /**
     * 直接使用代码设置连接池连接参数方式
     * 1.创建一个druid连接池对象
     * 2.设置连接池参数
     * 3.获取连接
     * 4.回收连接
     */
    public void testHard() throws SQLException {

        //连接池对象
        DruidDataSource dataSource = new DruidDataSource();
        //设置参数
        //必须：连接数据库驱动类的全限定符[注册驱动]/url/user/password
        dataSource.setUrl("jdbc:mysql:///db_dong");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        //帮助我们进行驱动注册和获取连接
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        //非必须： 初始化连接数量，最大连接数量
        dataSource.setInitialSize(5);
        dataSource.setMaxActive(10);
        
        //获取连接
        DruidPooledConnection connection = dataSource.getConnection();

        //数据库CRUD

        //回收连接
        //连接池提供的连接,close就是回收连接
        connection.close();

    }

    /**
     *通过读取外部配置文件的方式,实例化druid连接池对象
     * @throws SQLException
     */
    public void testSoft() throws Exception {

        //1.读取外部配置文件 Properties
        Properties properties = new Properties();
        //src下的文件,可以使用类加载器提供的方法
        InputStream resourceAsStream = DruidUsePart.class.getClassLoader().getResourceAsStream("druid.properties");
        properties.load(resourceAsStream);
        //2.使用连接池的工具类的工程模式,创建连接池
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();

        //数据库CRUD

        //关闭连接
        connection.close();
    }
}
