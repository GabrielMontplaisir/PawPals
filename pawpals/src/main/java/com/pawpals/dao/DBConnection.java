package com.pawpals.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
	
	private static boolean initializedDB = false;
	private static void initDB() {
		if (initializedDB) { return; }
		initializedDB = true;
		ApplicationDao.createDatabase();
		ApplicationDao.dao.createUserTable();
		ApplicationDao.dao.createDogsTable();
		WalkDao.dao.createWalkOffersTable();
		WalkDao.dao.createWalksTable();
		WalkDao.dao.createWalkDogsTable();
	}	
	
    public static Connection getDBInstance() throws ClassNotFoundException {
    	initDB();
        Connection connection = null;
        try {
        	connection = DBUtil.getConnection(DBType.MYSQL);
        } catch (SQLException e) {
        	DBUtil.processException(e);
        };
        return connection;
    }
}