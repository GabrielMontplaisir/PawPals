package com.pawpals.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pawpals.beans.User;
import com.pawpals.beans.Walk;
import com.pawpals.beans.WalkOffer;
import com.pawpals.interfaces.WalkBuilder;
import com.pawpals.interfaces.UserBuilder;

public class WalkOfferDao {
	private static WalkOfferDao dao;
	
	private WalkOfferDao() {}
	
	public static synchronized WalkOfferDao getDao() {
		if (dao == null) dao = new WalkOfferDao();
		return dao;
	}
	
	public List<WalkOffer> getWalkOffers(int walkId) {
		ArrayList<WalkOffer> walkOffers = new ArrayList<>();
		String sql = "SELECT * FROM " + ApplicationDao.WALKOFFERS_TABLE + " AS wo LEFT JOIN "+ApplicationDao.WALKS_TABLE+" AS w USING ("+WalkDao.WALK_ID+") LEFT JOIN ( SELECT "+UserDao.USER_ID+", "+UserDao.EMAIL_ADDRESS+", "+UserDao.FIRST_NAME+", "+UserDao.LAST_NAME+", "+UserDao.DATE_OF_BIRTH+" FROM " +ApplicationDao.USERS_TABLE+") as u ON wo."+WalkDao.WALKER_ID+" = u."+UserDao.USER_ID+" WHERE " + WalkDao.WALK_ID + " = ? AND (" + WalkDao.DECLINED + " != TRUE OR " + WalkDao.DECLINED + " IS NULL)"; 
		try (
				Connection conn = DBConnection.getDBInstance(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, walkId);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Walk walk = new WalkBuilder()
						.setWalkId(rs.getInt(WalkDao.WALK_ID))
						.setStatus(rs.getInt(WalkDao.STATUS))
						.setOwnerId(rs.getInt(WalkDao.OWNER_ID))
						.setDate(rs.getString(WalkDao.START_TIME))
						.setLocation(rs.getString(WalkDao.LOCATION))
						.setLength(rs.getString(WalkDao.LENGTH))
						.setWalkerId(rs.getInt(WalkDao.WALKER_ID))
						.create();
				
				User walkUser = new UserBuilder()
						.setUserId(rs.getInt(UserDao.USER_ID))
						.setEmail(rs.getString(UserDao.EMAIL_ADDRESS))
						.setFirstName(rs.getString(UserDao.FIRST_NAME))
						.setLastName(rs.getString(UserDao.LAST_NAME)) 
						.setDOB(rs.getString(UserDao.DATE_OF_BIRTH))
						.create();
						
				WalkOffer walkOffer = new WalkOffer(
						walk, 
						walkUser, 
						rs.getBoolean(WalkDao.DECLINED)
				);
				
				walkOffers.add(walkOffer);
			}
			
			if (rs != null) rs.close();
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return walkOffers;
	}
	
	public boolean walkerOffered(int walkId, int walkerUserId) {
		boolean isTrue = false;
		String sql = "SELECT COUNT(*) FROM " + ApplicationDao.WALKOFFERS_TABLE + " WHERE " + WalkDao.WALK_ID + " = ? AND " + WalkDao.WALKER_ID + " = ?;";
		try (
				Connection conn = DBConnection.getDBInstance(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, walkId);
			stmt.setInt(2, walkerUserId);
			
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
	
	public int getWalkOfferCount(int walkId) {
		String sql = "SELECT COUNT(*) FROM " + ApplicationDao.WALKOFFERS_TABLE + " WHERE " + WalkDao.WALK_ID + " = ? AND " + WalkDao.DECLINED + " is null OR " + WalkDao.DECLINED + " = 0;";
		try (
				Connection conn = DBConnection.getDBInstance(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			
			stmt.setInt(1, walkId);
			ResultSet rs =  stmt.executeQuery();
			rs.next();
			
			int count = rs.getInt(1);
			
			rs.close();
			
			return count;
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public void cancelWalkOffer(int walkId, int walkerUserId) {
		String sql = "DELETE FROM " + ApplicationDao.WALKOFFERS_TABLE + " WHERE " + WalkDao.WALK_ID + " = ? AND " + WalkDao.WALKER_ID + " = ?;";
		try (
				Connection conn = DBConnection.getDBInstance(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, walkId);
			stmt.setInt(2, walkerUserId);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void rejectWalkOffer(int walkId, int walkOfferUserId) {
		String sql = "UPDATE " + ApplicationDao.WALKOFFERS_TABLE + " SET " + WalkDao.DECLINED + " = true "
					 + " WHERE " + WalkDao.WALK_ID + " = ? AND "+WalkDao.WALKER_ID+" = ?;";
		
		try (
				Connection conn = DBConnection.getDBInstance(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, walkId);
			stmt.setInt(2, walkOfferUserId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	public void addWalkOffer(int walkId, int walkerUserId) {
		String sql = "INSERT INTO " + ApplicationDao.WALKOFFERS_TABLE + " (" + WalkDao.WALK_ID + ", " + WalkDao.WALKER_ID + ") VALUES (? , ?);";
		try (
				Connection conn = DBConnection.getDBInstance(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, walkId);
			stmt.setInt(2, walkerUserId);
			
			stmt.executeUpdate();

		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
