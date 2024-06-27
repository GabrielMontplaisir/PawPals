package com.pawpals.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getDBInstance() throws ClassNotFoundException {
        Connection connection = null;

        try {
        	connection = DBUtil.getConnection(DBType.MYSQL);
        } catch (SQLException e) {
        	DBUtil.processException(e);
        }
        return connection;
    }

}