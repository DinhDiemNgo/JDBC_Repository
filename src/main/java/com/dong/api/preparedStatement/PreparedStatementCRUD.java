package com.dong.api.preparedStatement;

import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreparedStatementCRUD {

    //测试方法需要导入junit的测试包
    @Test
    public void testInsert() throws ClassNotFoundException, SQLException {
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
        //5.占位符赋值
        preparedStatement.setObject(1, "董政博");
        preparedStatement.setObject(2, "Dzb315926");
        preparedStatement.setObject(3, "老牛");
        //6.发送SQL语句
        int row = preparedStatement.executeUpdate();
        //7.返回结果
        if (row > 0) {
            System.out.println("数据插入成功");
        } else {
            System.out.println("数据插入失败");
        }
        //8.关闭资源
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testUpdate() throws ClassNotFoundException, SQLException {
        /**
         * 更新一条数据
         */
        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.建立连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///db_dong?user=root&password=123456");
        //3.编写SQL语句,动态值部分用?代替
        String SQL = "UPDATE t_users SET password=? WHERE username=?;";
        //4.创建PreparedStatement对象并传入SQL语句
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        //5.占位符赋值
        preparedStatement.setObject(1, "Dzb15022633254");
        preparedStatement.setObject(2, "董政博");
        //6.发送SQL语句
        int row = preparedStatement.executeUpdate();
        //7.返回结果
        if (row > 0) {
            System.out.println("数据更新成功");
        } else {
            System.out.println("数据更新失败");
        }
        //8.关闭资源
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testDelete() throws ClassNotFoundException, SQLException {
        /**
         * 删除一条数据
         */
        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.建立连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///db_dong?user=root&password=123456");
        //3.编写SQL语句,动态值部分用?代替
        String SQL = "DELETE FROM t_users WHERE username=?;";
        //4.创建PreparedStatement对象并传入SQL语句
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        //5.占位符赋值
        preparedStatement.setObject(1, "董政博");
        //6.发送SQL语句
        int row = preparedStatement.executeUpdate();
        //7.返回结果
        if (row > 0) {
            System.out.println("数据删除成功");
        } else {
            System.out.println("数据删除失败");
        }
        //8.关闭资源
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testSelect() throws ClassNotFoundException, SQLException {
        /**
         * 查询数据
         * 遍历行数据，一行对应一个Map，获取一行的列名和对应列的数值，装配即可
         * 将Map装进List里即可
         */
        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.建立连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///db_dong?user=root&password=123456");
        //3.编写SQL语句,动态值部分用?代替
        String SQL = "SELECT id,username AS name,password,nickname FROM t_users;";
        //4.创建PreparedStatement对象并传入SQL语句
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        //5.发送SQL语句
        ResultSet resultSet = preparedStatement.executeQuery();
        //6.返回结果
        List<Map> list = new ArrayList<>();
        //获取列的信息对象
        //TODO:metaData装的是当前结果集列的信息对象，可以获取列的名称和数量
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (resultSet.next()) {
            Map map = new HashMap();
            //纯手动取值
//          map.put("id", resultSet.getInt("id"));
//          map.put("username", resultSet.getString("username"));
//          map.put("password", resultSet.getString("password"));
//          map.put("nickname", resultSet.getString("nickname"));
            //自动遍历列，要从1开始，并且小于等于总列数
            for (int i = 1; i <= columnCount; i++) {
                //获取指定列下角标的字段名称
                String columnLabel = metaData.getColumnLabel(i);
                //获取指定列下角标的数值
                Object value = resultSet.getObject(i);
                map.put(columnLabel, value);
            }
            list.add(map);
        }
        System.out.println("返回结果：" + list);
        //7.关闭资源
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
