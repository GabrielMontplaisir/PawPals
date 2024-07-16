package com.pawpals.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pawpals.beans.*;
import com.pawpals.interfaces.WalkStatus;

public class WalkDao {
	public static final WalkDao dao = new WalkDao();
	public static final String WALK_ID = "walk_id";
	public static final String STATUS = "status";
	public static final String OWNER_ID = "owner_id";
	public static final String START_TIME = "start_time";
	public static final String LOCATION = "location";
	public static final String LENGTH = "length";
	public static final String WALKER_ID = "walker_id";
	public static final String DECLINED = "declined";

	private WalkDao() {}

	public Walk createWalk(int ownerId, HttpServletRequest req) {
        String startTime = req.getParameter("starttime");
        String location = req.getParameter("location");
        String length = req.getParameter("length");
		Walk newWalk = null;
		
		String sql = "INSERT INTO " + ApplicationDao.WALKS_TABLE + " (" + STATUS + "," + OWNER_ID + "," + START_TIME + "," + LOCATION
				+ "," + LENGTH + ") VALUES (?, ?, ?, ?, ?)";

		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			) {

			int newStatus = WalkStatus.OWNER_POSTED.toInt();
			
			stmt.setInt(1, newStatus);
			stmt.setInt(2, ownerId);
			stmt.setString(3, startTime);
			stmt.setString(4, location);
			stmt.setString(5, length);
			
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			
			if (rs != null && rs.next()) {
				newWalk = new Walk(
						rs.getInt(1), 
						newStatus, 
						ownerId, 
						startTime, 
						location, 
						length, 
						0
				);
			};
			
			if (rs != null) rs.close();
			 
			return newWalk;

		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void acceptWalkOffer(int walkId, int walkOfferUserId) {
		String sql = "UPDATE " + ApplicationDao.WALKS_TABLE + " SET " + WALKER_ID + " = ?, " + STATUS + " = "
					+ WalkStatus.WALKER_CHOSEN.toInt() + " WHERE " + WALK_ID + " = ?;" ;
		
		try (
				Connection conn = DBConnection.getDBInstance(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			
			stmt.setInt(1, walkOfferUserId);
			stmt.setInt(2, walkId);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	public Walk getWalkById(int walkId) {
		String sql = "SELECT * FROM "+ApplicationDao.WALKS_TABLE+" WHERE "+WALK_ID+" = ?";
		Walk walk = null;
		try (
				Connection conn = DBConnection.getDBInstance(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			
			stmt.setInt(1, walkId);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				walk = new Walk(
						walkId, 
						rs.getInt(STATUS), 
						rs.getInt(OWNER_ID), 
						rs.getString(START_TIME), 
						rs.getString(LOCATION), 
						rs.getString(LENGTH), 
						rs.getInt(WALKER_ID)
				);
				
				if (walk.getWalkerId() > 0) walk.setWalker(UserDao.dao.getUserById(walk.getWalkerId()));
				
			};
			
			if (rs != null) rs.close();

		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return walk;
	}

	public List<Walk> getWalksByOwnerId(int ownerId) {
		return getWalksBySwitchable(ownerId, OWNER_ID);
	}
	public List<Walk> getWalksByWalkerId(int walkerId) {
		return getWalksBySwitchable(walkerId, WALKER_ID);
	}	

	public List<Walk> getWalksBySwitchable(int userId, String userId_ColumnName) {
		String sql = "SELECT * FROM "+ApplicationDao.WALKS_TABLE+" WHERE "+userId_ColumnName+" = ?";
		List<Walk> userWalks = new ArrayList<>();
		
		try (
				Connection conn = DBConnection.getDBInstance(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				userWalks.add(new Walk(
						rs.getInt(WALK_ID), 
						rs.getInt(STATUS), 
						userId, 
						rs.getString(START_TIME),
						rs.getString(LOCATION), 
						rs.getString(LENGTH), 
						rs.getInt(WALKER_ID)
						)
				);
			}
			
			if (rs != null) rs.close();
			
			return userWalks;
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	public List<Walk> getWalksPostedForReceivingOffers(int walkerId, HashMap<Integer, Boolean> walkOffers) {
		List<Walk> postedWalks = new ArrayList<>();
		String sql = "SELECT *, (NOT (WO.walker_id IS NULL)) AS OfferPending FROM " + ApplicationDao.WALKS_TABLE + " AS WT LEFT JOIN " + ApplicationDao.WALKOFFERS_TABLE + " AS WO ON WO." + WALK_ID + " = WT." + WALK_ID + " AND WO." + WALKER_ID + " = ? WHERE WT." + STATUS + "= ? AND (WO." + DECLINED + " != TRUE OR WO." + DECLINED + " IS NULL)"; 
			
		try (
				Connection conn = DBConnection.getDBInstance(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			
			stmt.setInt(1, walkerId);
			stmt.setInt(2, WalkStatus.OWNER_POSTED.toInt()); // Can't be too safe casting an int xD
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {	
				postedWalks.add(new Walk(
						rs.getInt(WALK_ID), 
						rs.getInt(STATUS), 
						rs.getInt(OWNER_ID), 
						rs.getString(START_TIME), 
						rs.getString(LOCATION), 
						rs.getString(LENGTH), 
						rs.getInt(WALKER_ID)
					)
				);
				
				walkOffers.put(rs.getInt(WALK_ID), rs.getBoolean("OfferPending"));
			}
			
			if (rs != null) rs.close();
			
			return postedWalks;
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void cancel(int walkId) {
		setStatus(walkId, WalkStatus.CANCELLED);
	}
	
	public void setStatus(int walkId, WalkStatus status) {
		String sql = "UPDATE " + ApplicationDao.WALKS_TABLE + " SET " + STATUS + " = ? WHERE " + WALK_ID + " = ?";
		try (
				Connection conn = DBConnection.getDBInstance(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, status.toInt());
			stmt.setInt(2, walkId);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteWalksNoDogFromID(int dogId) {
		String sql = "DELETE FROM "+ApplicationDao.WALKS_TABLE+" WHERE "+WALK_ID+" IN ( SELECT "+WALK_ID+" "
				+ "FROM "+ApplicationDao.WALKDOGS_TABLE+" "
				+ "WHERE " + WALK_ID + " IN ( "
				+ "SELECT " + WALK_ID + " "
				+ "FROM "+ApplicationDao.WALKDOGS_TABLE+" "
				+ "GROUP BY " + WALK_ID + " "
				+ "HAVING COUNT(DISTINCT " + DogDao.DOG_ID + ") = 1 AND " + DogDao.DOG_ID + " = ?));";
		
		try (
				Connection conn = DBConnection.getDBInstance(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, dogId);
			
			stmt.execute();
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	
}