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

	public void addDogToWalk(int walkId, int dogId) {
		String sql = "INSERT INTO " + ApplicationDao.WALKDOGS_TABLE + " (" + WALK_ID + ", " + DogDao.DOG_ID + ") VALUES (?, ?);";
		try (
				Connection conn = DBConnection.getDBInstance(); 
				PreparedStatement sqlAddDog = conn.prepareStatement(sql);
		) {
			sqlAddDog.setInt(1, walkId);
			sqlAddDog.setInt(2, dogId);
			
			sqlAddDog.execute();
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public List<Dog> getWalkDogs(int walkId) {
		List<Dog> dogsList = new ArrayList<>();

		String sql = "SELECT * FROM " + ApplicationDao.WALKDOGS_TABLE + " LEFT JOIN "+ApplicationDao.DOGS_TABLE+" USING ("+DogDao.DOG_ID+") WHERE " + WALK_ID + " = ?";
		try (
				Connection conn = DBConnection.getDBInstance(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, walkId);
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				dogsList.add(new Dog(
						rs.getInt(DogDao.DOG_ID), 
	            		rs.getInt(DogDao.OWNER_ID), 
	            		rs.getString(DogDao.NAME), 
	            		rs.getString(DogDao.SIZE), 
	            		rs.getString(DogDao.SPECIAL_NEEDS), 
	            		rs.getBoolean(DogDao.IMMUNIZED)
					)
				);
			}
			
			if (rs != null) rs.close();
			
			return dogsList;
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		System.err.println("Could not get dogs list");
		return null;

	}

	public void addWalkOffer(int walkId, int walkerUserId) {
		String sql = "INSERT INTO " + ApplicationDao.WALKOFFERS_TABLE + " (" + WALK_ID + ", " + WALKER_ID + ") VALUES (? , ?);";
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
	
	public void rejectWalkOffer(int walkId, int walkOfferUserId) {
		String sql = "UPDATE " + ApplicationDao.WALKOFFERS_TABLE + " SET " + DECLINED + " = true "
					 + " WHERE " + WALK_ID + " = ? AND "+WALKER_ID+" = ?;";
		
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
	
	
	public void cancelWalkOffer(int walkId, int walkerUserId) {
		String sql = "DELETE FROM " + ApplicationDao.WALKOFFERS_TABLE + " WHERE " + WALK_ID + " = ? AND " + WALKER_ID + " = ?;";
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
	
	public boolean walkerOffered(int walkId, int walkerUserId) {
		boolean isTrue = false;
		String sql = "SELECT COUNT(*) FROM " + ApplicationDao.WALKOFFERS_TABLE + " WHERE " + WALK_ID + " = ? AND " + WALKER_ID + " = ?;";
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
	
	
	public Walk getWalkById(int walkId) {
		String sql = "SELECT * FROM walks WHERE walk_id = ?";

		try (
				Connection conn = DBConnection.getDBInstance(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			
			stmt.setInt(1, walkId);
			ResultSet rs = stmt.executeQuery();
			rs.next();

			Walk walk = new Walk(
					walkId, 
					rs.getInt(STATUS), 
					rs.getInt(OWNER_ID), 
					rs.getString(START_TIME), 
					rs.getString(LOCATION), 
					rs.getString(LENGTH), 
					rs.getInt(WALKER_ID)
			);
			
			if (walk.getWalkerId() > 0) walk.setWalker(UserDao.dao.getUserById(walk.getWalkerId()));
			
			if (rs != null) rs.close();
			
			return walk;

		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Walk> getWalksByOwnerId(int ownerId) {
		return getWalksBy_Switchable(ownerId, OWNER_ID);
	}
	public List<Walk> getWalksByWalkerId(int walkerId) {
		return getWalksBy_Switchable(walkerId, WALKER_ID);
	}	

	public List<Walk> getWalksBy_Switchable(int userId, String userId_ColumnName) {
		String sql = "SELECT * FROM walks WHERE "+userId_ColumnName+" = ?";
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
	
	
	public List<WalkOffer> getWalkOffers(int walkId) {
		ArrayList<WalkOffer> walkOffers = new ArrayList<>();
		String sql = "SELECT * FROM " + ApplicationDao.WALKOFFERS_TABLE + " AS wo LEFT JOIN "+ApplicationDao.WALKS_TABLE+" AS w USING ("+WALK_ID+") LEFT JOIN ( SELECT "+UserDao.USER_ID+", "+UserDao.EMAIL_ADDRESS+", "+UserDao.FIRST_NAME+", "+UserDao.LAST_NAME+", "+UserDao.DATE_OF_BIRTH+" FROM " +ApplicationDao.USERS_TABLE+") as u ON wo."+WALKER_ID+" = u."+UserDao.USER_ID+" WHERE " + WALK_ID + " = ? AND (" + DECLINED + " != TRUE OR " + DECLINED + " IS NULL)"; 
		try (
				Connection conn = DBConnection.getDBInstance(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, walkId);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Walk walk = new Walk(
						rs.getInt(WALK_ID), 
						rs.getInt(STATUS), 
						rs.getInt(OWNER_ID), 
						rs.getString(START_TIME), 
						rs.getString(LOCATION), 
						rs.getString(LENGTH), 
						rs.getInt(WALKER_ID)
						
				);
				User walkUser = new User(
						rs.getInt(UserDao.USER_ID), 
						rs.getString(UserDao.EMAIL_ADDRESS), 
						rs.getString(UserDao.FIRST_NAME), 
						rs.getString(UserDao.LAST_NAME), 
						rs.getString(UserDao.DATE_OF_BIRTH)
						);
						
				WalkOffer walkOffer = new WalkOffer(
						walk, 
						walkUser, 
						rs.getBoolean(DECLINED)
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
	
	public int getWalkOfferCount(int walkId) {
		String sql = "SELECT COUNT(*) FROM " + ApplicationDao.WALKOFFERS_TABLE + " WHERE " + WALK_ID + " = ? AND " + DECLINED + " is null OR " + DECLINED + " = 0;";
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
}