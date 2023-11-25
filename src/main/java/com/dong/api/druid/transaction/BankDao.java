package com.dong.api.druid.transaction;

import com.dong.util.DruidUtil;
import com.dong.util.DruidUtilV2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankDao {

    /**
     *
     * @param account
     * @param money
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void add(String account, double money) throws ClassNotFoundException, SQLException {
        Connection connection = DruidUtilV2.getConnection();
        //3.编写SQL语句,动态值部分用?代替
        String SQL = "UPDATE t_bank SET amount=amount+? WHERE account=?;";
        //4.创建PreparedStatement对象并传入SQL语句
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        //5.占位符赋值
        preparedStatement.setObject(1, money);
        preparedStatement.setObject(2, account);
        //6.发送SQL语句
        preparedStatement.executeUpdate();
        //7.关闭资源
        preparedStatement.close();

        System.out.println("加钱成功");
    }

    /**
     *
     * @param account
     * @param money
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void sub(String account, double money) throws ClassNotFoundException, SQLException {
        Connection connection = DruidUtilV2.getConnection();
        //3.编写SQL语句,动态值部分用?代替
        String SQL = "UPDATE t_bank SET amount=amount-? WHERE account=?;";
        //4.创建PreparedStatement对象并传入SQL语句
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        //5.占位符赋值
        preparedStatement.setObject(1, money);
        preparedStatement.setObject(2, account);
        //6.发送SQL语句
        preparedStatement.executeUpdate();
        //7.关闭资源
        preparedStatement.close();

        System.out.println("减钱成功");
    }
}
