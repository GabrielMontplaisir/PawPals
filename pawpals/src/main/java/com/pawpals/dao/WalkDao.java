package com.pawpals.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pawpals.beans.*;
import com.pawpals.beans.Walk.EnumStatus;
import com.pawpals.services.UserService;
import com.pawpals.services.WalkService;

public class WalkDao {
	public static final WalkDao dao = new WalkDao();
	public static final String WALKS_TABLE = "walks";
	public static final String WALKOFFERS_TABLE = "walkoffers";
	public static final String WALKDOGS_TABLE = "walkdogs";
	public static final String DOG_ID = "dog_id";
	public static final String WALK_ID = "walk_id";
	public static final String STATUS = "status";
	public static final String OWNER_ID = "owner_id";
	public static final String START_TIME = "start_time";
	public static final String LOCATION = "location";
	public static final String LENGTH = "length";
	public static final String WALKER_ID = "walker_id";
	public static final String DECLINED = "declined";

	private WalkDao() {
	}

	public Walk addWalk(User owner, String date_time, String location, String length) {
		String sql = "INSERT INTO " + WALKS_TABLE + " (" + STATUS + "," + OWNER_ID + "," + START_TIME + "," + LOCATION
				+ "," + LENGTH + ")"

				+ "VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = DBConnection.getDBInstance();
				PreparedStatement sqlAddWalk = conn.prepareStatement(sql);
				PreparedStatement sqlGetNewID = conn.prepareStatement("SELECT @@IDENTITY");) {

			int newStatus = EnumStatus.OWNER_INITIALIZED.toInt();
			sqlAddWalk.setInt(1, newStatus);
			sqlAddWalk.setInt(2, owner.getId());
			sqlAddWalk.setString(3, date_time);
			sqlAddWalk.setString(4, location);
			sqlAddWalk.setString(5, length);
			sqlAddWalk.executeUpdate();
			ResultSet rs = sqlGetNewID.executeQuery();
			rs.next();
			int walkId = rs.getInt(1);
			Walk newWalk = new Walk(walkId, newStatus, owner.getId(), date_time, location, length, 0);
			return newWalk;

		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addDog(Walk walk, Dog dog) {
		String sql = "INSERT INTO " + WALKDOGS_TABLE + " (" + DOG_ID + ", " + WALK_ID + ") VALUES (?, ?);";
		try (Connection conn = DBConnection.getDBInstance(); PreparedStatement sqlAddDog = conn.prepareStatement(sql);
//				PreparedStatement sqlGetNewID = conn.prepareStatement("SELECT @@IDENTITY");
		) {
			sqlAddDog.setInt(1, dog.getDogId());
			sqlAddDog.setInt(2, walk.getWalkId());
			sqlAddDog.execute();
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public List<Dog> getDogs(int walkId) {
		List<Dog> dogsList = new ArrayList<>();

		String sql = "SELECT " + DOG_ID + " FROM " + WALKDOGS_TABLE + " WHERE " + WALK_ID + " = ?";
		try (Connection conn = DBConnection.getDBInstance(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, walkId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				dogsList.add(DogDao.dogDao.getDogById(rs.getInt(DOG_ID)));
			}
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
		String sql = "INSERT INTO " + WALKOFFERS_TABLE + " (" + WALK_ID + ", " + WALKER_ID + ") VALUES (? , ?);";
		try (Connection conn = DBConnection.getDBInstance(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, walkId);
			stmt.setInt(2, walkerUserId);
			stmt.executeUpdate();
			return;
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void acceptWalkOffer(Walk walk, User walkOfferUser) {
		String sql = "UPDATE " + WALKS_TABLE + " SET " + WALKER_ID + " = ?, " + STATUS + " = "
					+ Walk.EnumStatus.WALKER_CHOSEN.toInt() + " WHERE " + WALK_ID + " = ?;" ;
		
		try (Connection conn = DBConnection.getDBInstance(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, walkOfferUser.getId());
			stmt.setInt(2, walk.getWalkId());
			stmt.executeUpdate();
			return;
		} catch (SQLException e) {
			DBUtil.processException(e);
			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}	
		
		/*
		sql = "UPDATE " + WALKOFFERS_TABLE + " (" + WALK_ID + ", " + WALKER_ID + ") VALUES (? , ?);";
		try (Connection conn = DBConnection.getDBInstance(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, walkId);
			stmt.setInt(2, walkerUserId);
			stmt.executeUpdate();
			return;
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		*/
	}
	public void cancelWalkOffer(int walkId, int walkerUserId) {
		String sql = "DELETE FROM " + WALKOFFERS_TABLE + " WHERE " + WALK_ID + " = ? AND " + WALKER_ID + " = ?;";
		try (Connection conn = DBConnection.getDBInstance(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, walkId);
			stmt.setInt(2, walkerUserId);
			stmt.executeUpdate();
			return;
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public int checkWalkOffer(int walkId, int walkerUserId) {
		String sql = "SELECT COUNT(*) FROM " + WALKOFFERS_TABLE + " WHERE " + WALK_ID + " = ? AND " + WALKER_ID + " = ?;";
		try (Connection conn = DBConnection.getDBInstance(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, walkId);
			stmt.setInt(2, walkerUserId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	public Walk getWalkById(int walkId) {
		String sql = "SELECT * FROM walks WHERE walk_id = ?";

		try (Connection conn = DBConnection.getDBInstance(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, walkId);
			ResultSet rs = stmt.executeQuery();
			rs.next();

			int status = rs.getInt(STATUS);
			int dog_owner = rs.getInt(OWNER_ID);
			String date = rs.getString(START_TIME);
			String location = rs.getString(LOCATION);
			String length = rs.getString(LENGTH);
			int walkerUserId = rs.getInt(WALKER_ID);

			return new Walk(walkId, status, dog_owner, date, location, length, walkerUserId);

		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Walk> getWalksByOwnerId(int ownerId) {
		String sql = "SELECT * FROM walks WHERE owner_id = ?";
		List<Walk> userWalks = new ArrayList<>();
		try (Connection conn = DBConnection.getDBInstance(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, ownerId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int walkId = rs.getInt(WALK_ID);
				int status = rs.getInt(STATUS);
				int dog_owner = rs.getInt(OWNER_ID); // maybe redundant but u never kno
				String date = rs.getString(START_TIME);
				String location = rs.getString(LOCATION);
				String length = rs.getString(LENGTH);
				int walkerUserId = rs.getInt(WALKER_ID);
				
				userWalks.add(new Walk(walkId, status, dog_owner, date, location, length, walkerUserId));
			}
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
		String sql = "SELECT *, (NOT (WO.walker_id IS NULL)) AS OfferPending FROM " + WALKS_TABLE + " AS WT LEFT JOIN " + WALKOFFERS_TABLE + " AS WO ON WO." + WALK_ID + " = WT." + WALK_ID + " AND WO." + WALKER_ID + " = ? WHERE WT." + STATUS + "= ? AND (WO." + DECLINED + " != TRUE OR WO." + DECLINED + " IS NULL)"; 
			
		try (Connection conn = DBConnection.getDBInstance(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, walkerId);
			stmt.setInt(2, EnumStatus.OWNER_POSTED.toInt()); // Can't be too safe casting an int xD
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int walkId = rs.getInt(WALK_ID);
				int status = rs.getInt(STATUS);
				int dog_owner = rs.getInt(OWNER_ID); // maybe redundant but u never kno
				String date = rs.getString(START_TIME);
				String location = rs.getString(LOCATION);
				String length = rs.getString(LENGTH);
				int walkerUserId = rs.getInt(WALKER_ID);
				postedWalks.add(new Walk(walkId, status, dog_owner, date, location, length, walkerUserId));
				walkOffers.put(walkId, rs.getBoolean("OfferPending"));
			}
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
		String sql = "SELECT * FROM " + WALKOFFERS_TABLE + " WHERE " + WALK_ID + " = ? AND (" + DECLINED + " != TRUE OR " + DECLINED + " IS NULL)"; 
		try (Connection conn = DBConnection.getDBInstance(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, walkId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Walk walk = WalkService.svc.getWalk_by_WalkId(walkId);
				int walkerUserId = rs.getInt(WALKER_ID); // maybe redundant but u never kno
				int declined = rs.getInt(DECLINED);
				User walkUser = UserService.svc.getUserById(walkerUserId);
				WalkOffer walkOffer = new WalkOffer(walk, walkUser, declined);
				walkOffers.add(walkOffer);
			}
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return walkOffers;
	}
	
	
	public void cancel(int walkId) {
		setStatus(walkId, EnumStatus.CANCELLED);
	}
	public void setStatus(int walkId, EnumStatus status) {
		String sql = "UPDATE " + WALKS_TABLE + " SET " + STATUS + " = ? WHERE " + WALK_ID + " = ?";
		try (Connection conn = DBConnection.getDBInstance(); PreparedStatement stmt = conn.prepareStatement(sql);) {
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
		String sql = "SELECT COUNT(*) FROM " +WALKOFFERS_TABLE+ " WHERE " + WALK_ID + " = ? AND " + DECLINED + " is null OR " + DECLINED + " = 0;";
		try (Connection conn = DBConnection.getDBInstance(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, walkId);
			ResultSet rs =  stmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// DDL
	public void createWalksTable() {
		try (Connection conn = DBConnection.getDBInstance(); Statement stmt = conn.createStatement();) {
			if (!ApplicationDao.dao.tableExists(conn, WALKS_TABLE)) {

				String sql = "CREATE TABLE IF NOT EXISTS " + WALKS_TABLE + " (" + WALK_ID
						+ " INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " + STATUS + " INT NOT NULL, " + OWNER_ID
						+ " INT NOT NULL, " + START_TIME + " VARCHAR(100) NOT NULL, " + LOCATION
						+ " VARCHAR(100) NOT NULL, " + LENGTH + " VARCHAR(25) NOT NULL, " + WALKER_ID + " INT, "
						+ "FOREIGN KEY (" + OWNER_ID + ") REFERENCES " + ApplicationDao.USERS_TABLE + "(user_id),"
						+ "FOREIGN KEY (" + WALKER_ID + ") REFERENCES " + ApplicationDao.USERS_TABLE + "(user_id)"
						+ ");";
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
		try (Connection conn = DBConnection.getDBInstance(); Statement stmt = conn.createStatement();) {
			if (!ApplicationDao.dao.tableExists(conn, WALKDOGS_TABLE)) {
				System.out.print("Creating WalkDogs Table...");
				String sql = "CREATE TABLE IF NOT EXISTS " + WALKDOGS_TABLE + " (" + "walk_id	 	INT NOT NULL, "
						+ "dog_id 		INT NOT NULL, " + "PRIMARY KEY (walk_id, dog_id)" + ");";
				stmt.executeUpdate(sql);
				System.out.println("Created WalkDogs Table");
			} else {
//				System.out.println("WalkDogs Table exists");
			}
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void createWalkOffersTable() {
		try (Connection conn = DBConnection.getDBInstance(); Statement stmt = conn.createStatement();) {
			if (!ApplicationDao.dao.tableExists(conn, WALKOFFERS_TABLE)) {
				System.out.print("Creating WalkOffers Table...");
				String sql = "CREATE TABLE IF NOT EXISTS " + WALKOFFERS_TABLE + "(" + WALK_ID + " INT NOT NULL, "
						+ WALKER_ID + " INT NOT NULL, " + "declined TINYINT(1), PRIMARY KEY (" + WALK_ID + ", " + WALKER_ID
						+ "));";
				stmt.executeUpdate(sql);
				System.out.println("Created WalkOffers Table");
			} else {
//				System.out.println("WalkOffers Table exists");
			}
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


}