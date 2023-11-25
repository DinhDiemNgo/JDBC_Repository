package com.dong.api.druid.transaction;

import com.dong.util.DruidUtilV2;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BankService {

    @Test
    public void testTransfer() throws SQLException, ClassNotFoundException {
        transfer("KoiwaTNKHeights", "Chunghwa1997", 350000, 60000000);
    }

    /**
     * @param addAccount
     * @param subAccount
     * @param salary
     * @param rent
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void transfer(String addAccount, String subAccount, double salary, double rent) throws SQLException, ClassNotFoundException {
        BankDao bankdao = new BankDao();
        Connection connection = DruidUtilV2.getConnection();
        try {
            //开启事务
            //1.关闭事务提交
            connection.setAutoCommit(false);
            //执行数据库动作
            bankdao.add(subAccount, salary);
            System.out.println("收到工资");
            bankdao.sub(subAccount, rent);
            System.out.println("转出房租");
            bankdao.add(addAccount, rent);
            System.out.println("房东收到房租");
            //事务提交
            connection.commit();
        } catch (Exception e) {
            //交易失败的话,事务回滚
            connection.rollback();
            //抛出异常
            throw e;
        } finally {
            //关闭连接
            DruidUtilV2.freeConnection();
        }

    }

}
