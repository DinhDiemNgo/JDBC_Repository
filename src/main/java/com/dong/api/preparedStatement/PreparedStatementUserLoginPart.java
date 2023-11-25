package com.dong.api.preparedStatement;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * 使用预编译的Statement进行用户登录验证
 * TODO:防止SQL注入攻击 AND 演示PreparedStatement的使用
 */
public class PreparedStatementUserLoginPart {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        /**
         * 获取用户输入信息
         */
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名");
        String inputusername = scanner.nextLine();
        System.out.println("请输入密码");
        String inputpassword = scanner.nextLine();

        //1.注册驱动
        /*
         * 注册驱动
         * 依赖：驱动版本 8+ com.mysql.cj.jdbc.Driver
         * 问题：注册两次驱动，资源消耗
         * 解决：通过Java反射机制只注册一次驱动
         */
        // 触发类加载,触发静态代码块的调用
        // 封装到外部配置文件里更加便利
        Class.forName("com.mysql.cj.jdbc.Driver");

        //2.获取连接
        /*
         * Java程序和数据库建立连接
         * 数据库IP地址:jdbc:mysql://127.0.0.1:3306/db_dong
         * 数据库端口号:3306
         * 用户:root
         * 密码:123456
         * 数据库的名称
         */
        String connectionURL = "jdbc:mysql:///db_dong?user=root&password=123456";
        Connection connection = DriverManager.getConnection(connectionURL);

        //3.编写SQL语句结构 不包含动态值部分 动态值部分使用占位符 ?
        //创建PreparedStatement对象,并且传入动态值
        //动态值 占位符 赋值 ? 单独赋值即可
        //发送SQL语句即可,获取并返回结果
        String SQL = "SELECT username,password,nickname FROM t_users WHERE username = ? AND password = ? ; ";

        //4.创建预编译Statement对象并设置SQL语句结果
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

        //5.单独的占位符进行赋值
        preparedStatement.setObject(1,inputusername);
        preparedStatement.setObject(2,inputpassword);

        //6.发送SQL语句并获取返回结果
        ResultSet resultSet = preparedStatement.executeQuery();

        //7.进行结果集解析
        if(resultSet.next()){
            System.out.println("登录成功");
        }else{
            System.out.println("登录失败");
        }

        //8.关闭资源
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

}
