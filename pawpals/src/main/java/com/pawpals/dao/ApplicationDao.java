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
	public static final String WALKS_TABLE = "walks";
	public static final String WALKDOGS_TABLE = "walkdogs";
	public static final String WALKOFFERS_TABLE = "walkoffers";
	
	private ApplicationDao() {}
	
	public static void createDatabase() {		
		try (
				Connection conn = DBConnection.getDBInstance();
				ResultSet resultSet = conn.getMetaData().getCatalogs();
				Statement stmt = conn.createStatement();
			) {
			if (!dbExists(DB_NAME, resultSet)) {
				System.out.print("Creating DB...");
				String sql = "CREATE DATABASE IF NOT EXISTS "+ DB_NAME +" DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci";
				stmt.executeUpdate(sql);
				System.out.println("Created DB");		
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
			if (!tableExists(conn, USERS_TABLE)) {
				System.out.print("Creating User Table...");
				String sql = "CREATE TABLE IF NOT EXISTS "+ USERS_TABLE +" ("
						+ "user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
						+ "email_address VARCHAR(128) NOT NULL UNIQUE, "
						+ "first_name VARCHAR(25) NOT NULL, "
						+ "last_name VARCHAR(25) NOT NULL, "
						+ "date_of_birth DATE NOT NULL, "
						+ "password VARCHAR(64) NOT NULL, "
						+ "createTimestamp TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP()))";
				stmt.executeUpdate(sql);
				System.out.println("Created User Table");
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
	            if (!tableExists(conn, DOGS_TABLE)) {
	                System.out.print("Creating Dogs Table...");
	                String sql = "CREATE TABLE IF NOT EXISTS " + DOGS_TABLE + " ("
	                    + "dog_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
	                    + "owner_id INT NOT NULL, "
	                    + "name VARCHAR(50) NOT NULL, "
	                    + "size ENUM('sm', 'md', 'lg') NOT NULL, "
	                    + "special_needs TEXT, "
	                    + "immunized BOOLEAN, "
	                    + "FOREIGN KEY (owner_id) REFERENCES " + USERS_TABLE + "(user_id))";
	                stmt.executeUpdate(sql);
	                System.out.println("Created Dogs Table");
	            }
	        } catch (SQLException e) {
	            DBUtil.processException(e);
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	}
	
	public void createWalksTable() {
		try (
				Connection conn = DBConnection.getDBInstance(); 
				Statement stmt = conn.createStatement();
			) {
			if (!tableExists(conn, WALKS_TABLE)) {

				String sql = "CREATE TABLE IF NOT EXISTS " + WALKS_TABLE + " ("
						+ "walk_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " 
						+ "status INT NOT NULL, "
						+ "owner_id INT NOT NULL, " 
						+ "start_time VARCHAR(100) NOT NULL, " 
						+ "location VARCHAR(100) NOT NULL, " 
						+ "length VARCHAR(25) NOT NULL, " 
						+ "walker_id INT, "
						+ "FOREIGN KEY ( owner_id ) REFERENCES " + USERS_TABLE + "(user_id),"
						+ "FOREIGN KEY ( walker_id ) REFERENCES " + USERS_TABLE + "(user_id))";
				stmt.executeUpdate(sql);
				System.out.println("Created Walks Table");
			} else {
//				System.out.println("Walks Table exists");
			}
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void createWalkDogsTable() {
		try (
				Connection conn = DBConnection.getDBInstance(); 
				Statement stmt = conn.createStatement();
			) {
			if (!tableExists(conn, WALKDOGS_TABLE)) {
				System.out.print("Creating WalkDogs Table...");
				String sql = "CREATE TABLE IF NOT EXISTS " + WALKDOGS_TABLE + " (" 
						+ "walk_id INT NOT NULL, "
						+ "dog_id INT NOT NULL, " 
						+ "PRIMARY KEY (walk_id, dog_id))";
				stmt.executeUpdate(sql);
				System.out.println("Created WalkDogs Table");
			}
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void createWalkOffersTable() {
		try (
				Connection conn = DBConnection.getDBInstance(); 
				Statement stmt = conn.createStatement();
			) {
			if (!tableExists(conn, WALKOFFERS_TABLE)) {
				System.out.print("Creating WalkOffers Table...");
				String sql = "CREATE TABLE IF NOT EXISTS " + WALKOFFERS_TABLE + " (" 
						+ "walk_id INT NOT NULL, "
						+ "walker_id INT NOT NULL, " 
						+ "declined BOOLEAN, "
						+ "PRIMARY KEY (walk_id, walker_id))";
				stmt.executeUpdate(sql);
				System.out.println("Created WalkOffers Table");
			}
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static boolean dbExists(String dbName, ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			if (resultSet.getString(1).equals(dbName)) return true;
		}
		
		return false;
	}
	
	public boolean tableExists(Connection conn, String tableName) throws SQLException {
        return conn.getMetaData().getTables(null, null, tableName, new String[] {"TABLE"}).next();
    }

}
