package com.pawpals.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.pawpals.beans.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserDao {
	public static final UserDao dao = new UserDao();
	public static final String USER_ID = "user_id";
	public static final String EMAIL_ADDRESS = "email_address";
	public static final String FIRST_NAME = "first_name";
	public static final String LAST_NAME = "last_name";
	public static final String DATE_OF_BIRTH = "date_of_birth";
	private final String PASSWORD = "password";
	
	private UserDao() {}	
	
	public void createUser(HttpServletRequest req) {
		String email = req.getParameter("email");
		String firstName = req.getParameter("firstname");
		String lastName = req.getParameter("lastname");
		String dob = req.getParameter("dob");
		String password = req.getParameter("password");
		
		String sql = "INSERT INTO "+ApplicationDao.USERS_TABLE+" ("+EMAIL_ADDRESS+","+FIRST_NAME+","+LAST_NAME+","+DATE_OF_BIRTH+","+PASSWORD+") VALUES (?, ?, ?, ?, ?)";
		
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			) {
    		stmt.setString(1, email);
    		stmt.setString(2, firstName);
    		stmt.setString(3, lastName);
    		stmt.setDate(4, java.sql.Date.valueOf(dob));
    		stmt.setString(5, password);
    		
    		stmt.executeUpdate();
    		
    		ResultSet rs = stmt.getGeneratedKeys();
    		
    		if (rs != null && rs.next()) {
    			HttpSession session = req.getSession();
    			session.setAttribute("user", new User(rs.getInt(1), email, firstName, lastName, dob));
    		}
    		
    		if (rs != null) rs.close();
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public User getUserById(int userId) {
		User user = null;
		String sql = "SELECT "+USER_ID+", "+EMAIL_ADDRESS+", "+FIRST_NAME+", "+LAST_NAME+", "+DATE_OF_BIRTH+" FROM " +ApplicationDao.USERS_TABLE+" WHERE " + USER_ID + " = ?";
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			
			if (rs != null && rs.next()) {
				user = new User(
						userId, 
						rs.getString(EMAIL_ADDRESS), 
						rs.getString(FIRST_NAME), 
						rs.getString(LAST_NAME), 
						rs.getString(DATE_OF_BIRTH)
				);
			}
			
			user.setDogList(DogDao.dao.getDogsByOwner(userId));
			
			if (rs != null) rs.close();
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		}
		return user;
	}
	
	public void authenticateUser(HttpServletRequest req) {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		String sql = "SELECT "+USER_ID+", "+FIRST_NAME+", "+LAST_NAME+", "+DATE_OF_BIRTH+" FROM "+ApplicationDao.USERS_TABLE+" WHERE "+EMAIL_ADDRESS+" = ? AND "+PASSWORD+" = ?";
		
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setString(1, email);
			stmt.setString(2, password);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs != null && rs.next()) {
				HttpSession session = req.getSession();
				
				User user = new User(
						rs.getInt(USER_ID), 
						email, 
						rs.getString(FIRST_NAME), 
						rs.getString(LAST_NAME), 
						rs.getDate(DATE_OF_BIRTH).toString()
						);
				
				session.setAttribute("user", user);
				
				user.setDogList(DogDao.dao.getDogsByOwner(user.getId()));
			}
			
			if (rs != null) rs.close();
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean userExists(String email) {
		boolean isTrue = false;
		
		String sql = "SELECT COUNT(*) FROM "+ApplicationDao.USERS_TABLE+" WHERE "+EMAIL_ADDRESS+" = ?";
		
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setString(1, email);
			
			ResultSet rs = stmt.executeQuery();
			if (rs != null && rs.next() && rs.getInt(1) > 0) {
				isTrue = true;
			}
			
			if (rs != null) rs.close();
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return isTrue;
	}
	
	public boolean passwordMatches(String email, String password) {
		boolean isTrue = false;
		
		String sql = "SELECT COUNT(*) FROM "+ApplicationDao.USERS_TABLE+" WHERE "+EMAIL_ADDRESS+" = ? AND "+PASSWORD+" = ?";
		
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setString(1, email);
			stmt.setString(2, password);
			
			ResultSet rs = stmt.executeQuery();
			if (rs != null && rs.next() && rs.getInt(1) > 0) {
				isTrue = true;
			}
			
			
			if (rs != null) rs.close();
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return isTrue;
	}
	
}
