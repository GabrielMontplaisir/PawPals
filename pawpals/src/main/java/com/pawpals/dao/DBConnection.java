package com.pawpals.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.pawpals.interfaces.DBType;

public class DBConnection {
	
	private static boolean initializedDB = false;
	
	private static void initDB() {
		if (initializedDB) { return; }
		initializedDB = true;
		ApplicationDao.createDatabase();
		ApplicationDao.getDao().createUserTable();
		ApplicationDao.getDao().createDogsTable();
		ApplicationDao.getDao().createWalksTable();
		ApplicationDao.getDao().createWalkOffersTable();
		ApplicationDao.getDao().createWalkDogsTable();
		ApplicationDao.getDao().createNotificationsTable();
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