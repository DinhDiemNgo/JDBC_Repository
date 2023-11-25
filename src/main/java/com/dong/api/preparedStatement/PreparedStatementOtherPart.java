package com.dong.api.preparedStatement;

import org.junit.Test;

import java.sql.*;

public class PreparedStatementOtherPart {

    /**
     * 插入一条数据并获取数据库自增长的主键
     * Statement.RETURN_GENERATED_KEYS
     */
    @Test
    public void returnPrimaryKey() throws Exception {
        /**
         * 插入一条数据
         */
        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.建立连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///db_dong?user=root&password=123456");
        //3.编写SQL语句,动态值部分用?代替
        String SQL = "INSERT INTO t_users (username, password, nickname) VALUES (?, ?, ?);";
        //4.创建PreparedStatement对象并传入SQL语句
        PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        //5.占位符赋值
        preparedStatement.setObject(1, "越南共和国");
        preparedStatement.setObject(2, "VNCH19551025");
        preparedStatement.setObject(3, "南越");
        //6.发送SQL语句
        int row = preparedStatement.executeUpdate();
        //7.返回结果
        if (row > 0) {
            System.out.println("数据插入成功");

            //可以获取回显的主键
            //获取装主键的结果集对象，一行一列
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int id = resultSet.getInt(1);
            System.out.println("id = " + id);
        } else {
            System.out.println("数据插入失败");
        }
        //8.关闭资源
        preparedStatement.close();
        connection.close();
    }

    /**
     * 插入一条数据并获取数据库自增长的主键
     * Statement.RETURN_GENERATED_KEYS
     */
    @Test
    public void testInsertData() throws Exception {
        /**
         * 插入一条数据
         */
        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.建立连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///db_dong?user=root&password=123456");
        //3.编写SQL语句,动态值部分用?代替
        String SQL = "INSERT INTO t_users (username, password, nickname) VALUES (?, ?, ?);";
        //4.创建PreparedStatement对象并传入SQL语句
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            //5.占位符赋值
            preparedStatement.setObject(1, "South Vietnam" + i);
            preparedStatement.setObject(2, "Saigon" + i);
            preparedStatement.setObject(3, "Republic of Vietnam" + i);
            //6.发送SQL语句
            preparedStatement.executeUpdate();
        }

        long end = System.currentTimeMillis();

        System.out.println("插入一万条数据需要的时间：" + (end - start) / 1000 + "秒");

        //8.关闭资源
        preparedStatement.close();
        connection.close();
    }

    /**
     * 批量插入数据
     * rewritebatchedstatements=true的意义：允许批量插入
     * INSERT INTO VALUES,不能是VALUE
     */
    @Test
    public void testBatchInsertData() throws Exception {
        /**
         * 插入一条数据
         */
        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.建立连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///db_dong?rewritebatchedstatements=true&user=root&password=123456");
        //3.编写SQL语句,动态值部分用?代替
        String SQL = "INSERT INTO t_users (username, password, nickname) VALUES (?, ?, ?)";
        //4.创建PreparedStatement对象并传入SQL语句
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 30000; i++) {
            //5.占位符赋值
            preparedStatement.setObject(1, "South Korea" + i);
            preparedStatement.setObject(2, "Seoul" + i);
            preparedStatement.setObject(3, "Republic of Korea" + i);
            //6.发送SQL语句
            preparedStatement.addBatch();//不执行,追加到values后面
        }

        preparedStatement.executeBatch();//批量执行

        long end = System.currentTimeMillis();

        System.out.println("插入一万条数据需要的时间：" + (end - start) / 1000 + "秒");

        //8.关闭资源
        preparedStatement.close();
        connection.close();
    }

}
