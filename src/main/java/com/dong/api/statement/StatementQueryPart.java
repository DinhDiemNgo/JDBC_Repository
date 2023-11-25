package com.dong.api.statement;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;

public class StatementQueryPart {
    /**
     * TODO:
     * DriverManager
     * Connection
     * Statement
     * ResultSet
     *
     * @param args
     */
    public static void main(String[] args) throws SQLException {
        //1.注册驱动
        /*
         * 注册驱动
         * 依赖：驱动版本 8+ com.mysql.cj.jdbc.Driver
         */
        DriverManager.registerDriver(new Driver());
        //2.获取连接
        /*
         * Java程序和数据库建立连接
         * 数据库IP地址:jdbc:mysql://127.0.0.1:3306/db_dong
         * 数据库端口号:3306
         * 用户:root
         * 密码:123456
         * 数据库的名称
         */
        String connectionURL = "jdbc:mysql://127.0.0.1:3306/db_dong";
        String dbUser = "root";
        String dbPassword = "123456";
        Connection connection = DriverManager.getConnection(connectionURL,dbUser,dbPassword);
        //3.创建Statement对象
        Statement statement = connection.createStatement();
        //4.发送SQL语句并获取返回结果
        String SQL = "SELECT username,password,nickname FROM t_users;";
        ResultSet resultSet = statement.executeQuery(SQL);
        //5.进行结果集解析
        while(resultSet.next()){
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String nickname = resultSet.getString("nickname");
            System.out.println(username+" "+password+" "+nickname);
        }
        //6.关闭资源
        resultSet.close();
        statement.close();
        connection.close();
    }

}
