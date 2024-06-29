package com.pawpals.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ApplicationDao {
	public static final ApplicationDao dao = new ApplicationDao();
	public static final String DB_NAME = "pawpals";
	public static final String USERS_TABLE = "users";
	public static final String DOGS_TABLE = "dogs";
		
	private ApplicationDao() {}
	
	public void createDatabase() {		
		try (
				Connection conn = DBConnection.getDBInstance();
				ResultSet resultSet = conn.getMetaData().getCatalogs();
				Statement stmt = conn.createStatement();
			) {
			if (!dbExists(DB_NAME, resultSet)) {
				System.out.println("Created DB");
				String sql = "CREATE DATABASE IF NOT EXISTS "+ DB_NAME +" DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci";
				stmt.executeUpdate(sql);
			} else {
				System.out.println("DB Exists");
			}
			
			DBUtil.setConnStr();
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void createUserTable() {		
		try (
				Connection conn = DBConnection.getDBInstance();
				Statement stmt = conn.createStatement();
			) {
			if (!userTableExists(conn, USERS_TABLE)) {
				System.out.println("Created User Table");
				String sql = "CREATE TABLE IF NOT EXISTS "+ USERS_TABLE +" (user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, email_address VARCHAR(128) NOT NULL UNIQUE, first_name VARCHAR(25) NOT NULL, last_name VARCHAR(25) NOT NULL, date_of_birth DATE NOT NULL, password VARCHAR(64) NOT NULL, createTimestamp TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP()))";
				stmt.executeUpdate(sql);
			} else {
				System.out.println("User Table exists");
			}
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void createDogsTable() {
		 try (
	                Connection conn = DBConnection.getDBInstance();
	                Statement stmt = conn.createStatement();
	            ) {
	            if (!dogsTableExists(conn, DOGS_TABLE)) {
	                System.out.println("Created Dogs Table");
	                String sql = "CREATE TABLE IF NOT EXISTS " + DOGS_TABLE + " ("
	                    + "dog_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
	                    + "owner_id INT NOT NULL, "
	                    + "name VARCHAR(50) NOT NULL, "
	                    + "size ENUM('small', 'medium', 'large') NOT NULL, "
	                    + "special_needs TEXT, "
	                    + "immunized BOOLEAN, "
	                    + "FOREIGN KEY (owner_id) REFERENCES " + USERS_TABLE + "(user_id))";
	                stmt.executeUpdate(sql);
	            } else {
	                System.out.println("Dogs Table exists");
	            }
	        } catch (SQLException e) {
	            DBUtil.processException(e);
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	}
	
	private boolean dbExists(String dbName, ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			if (resultSet.getString(1).equals(dbName)) return true;
		}
		
		return false;
	}
	
	
	private boolean userTableExists(Connection conn, String userTable) throws SQLException {
		return conn.getMetaData().getTables(null, null, userTable, new String[] {"TABLE"}).next();
	}
	
	private boolean dogsTableExists(Connection conn, String dogsTable) throws SQLException {
        return conn.getMetaData().getTables(null, null, dogsTable, new String[] {"TABLE"}).next();
    }
	
	
}
