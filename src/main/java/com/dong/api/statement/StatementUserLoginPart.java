package com.dong.api.statement;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * 明确JDBC的使用流程和详细内部设计API方法
 * 发现问题，引出PreparedStatement
 *
 * 输入账号和密码进行数据库信息查询
 * 反馈登录成功或者失败
 *
 * 键盘输入事件,收集账号和密码信息
 */
public class StatementUserLoginPart {

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
        String connectionURL = "jdbc:mysql://localhost:3306/db_dong";
        Properties info = new Properties();
        info.put("user","root");
        info.put("password","123456");
        Connection connection = DriverManager.getConnection(connectionURL,info);
        //3.创建Statement对象
        //可以发送SQL语句到数据库并获取返回结果
        Statement statement = connection.createStatement();
        //4.发送SQL语句
        String SQL = "SELECT username,password,nickname FROM t_users WHERE username = '"+inputusername+"' AND password = '" +
                inputpassword+"';";
        ResultSet resultSet = statement.executeQuery(SQL);
        //5.进行结果集解析
        if(resultSet.next()){
            System.out.println("登录成功");
        }else{
            System.out.println("登录失败");
        }
        //6.关闭资源
        resultSet.close();
        statement.close();
        connection.close();
    }

}
