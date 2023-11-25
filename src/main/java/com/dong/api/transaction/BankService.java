package com.dong.api.transaction;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BankService {

    @Test
    public void testTransfer() throws SQLException, ClassNotFoundException {
        transfer("KoiwaTNKHeights","Chunghwa1997",350000,60000000);
    }

    /**
     *
     * @param addAccount
     * @param subAccount
     * @param salary
     * @param rent
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void transfer(String addAccount,String subAccount,double salary,double rent) throws SQLException, ClassNotFoundException {
        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.建立连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///db_dong?user=root&password=123456");
        try {
            //开启事务
            //1.关闭事务提交
            connection.setAutoCommit(false);
            //执行数据库动作
            BankDao bankdao = new BankDao();
            bankdao.add(subAccount,salary,connection);
            System.out.println("收到工资");
            bankdao.sub(subAccount,rent,connection);
            System.out.println("转出房租");
            bankdao.add(addAccount,rent,connection);
            System.out.println("收到房租");
            //事务提交
            connection.commit();
        }catch (Exception e){
            //交易失败的话,事务回滚
            connection.rollback();
            //抛出异常
            throw e;
        }finally {
            //关闭连接
            connection.close();
        }

    }

}
