package com.dong.api.druid;

import com.dong.util.DruidUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 使用DruidUtil工具类的CRUD
 */
public class DruidCRUD {

    @Test
    public void testInsert() throws SQLException {
        Connection connection = DruidUtil.getConnection();

        //数据库CRUD操作

        DruidUtil.freeConnection(connection);
    }

}
