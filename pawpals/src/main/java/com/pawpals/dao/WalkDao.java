package com.pawpals.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pawpals.beans.Dog;
import com.pawpals.beans.User;
import com.pawpals.beans.Walk;

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
			Walk newWalk = new Walk(walkId, newStatus, owner.getId(), date_time, location, length);
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

	public List<Dog> getDogs(Walk walk) {
		List<Dog> dogsList = new ArrayList<>();

		String sql = "SELECT " + DOG_ID + " FROM " + WALKDOGS_TABLE + " WHERE " + WALK_ID + " = ?";
		try (Connection conn = DBConnection.getDBInstance(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, walk.getWalkId());
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

			return new Walk(walkId, status, dog_owner, date, location, length);

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
				userWalks.add(new Walk(walkId, status, dog_owner, date, location, length));
			}
			return userWalks;
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Walk> getWalksPostedForReceivingOffers(int walkerId) {
		List<Walk> postedWalks = new ArrayList<>();
		String sql = "SELECT * FROM " + WALKS_TABLE + " AS WT LEFT JOIN " + WALKOFFERS_TABLE + " AS WO ON WO." + WALK_ID + " = WT." + WALK_ID + " AND WO." + WALKER_ID + " = ? WHERE WT." + STATUS + "= ? AND (WO." + DECLINED + " != TRUE OR WO." + DECLINED + " IS NULL)"; 
		
		System.out.println(sql);
		
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
				postedWalks.add(new Walk(walkId, status, dog_owner, date, location, length));
			}
			return postedWalks;
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void cancel(int walkId) {
		setStatus(walkId, EnumStatus.CANCELLED);
	}
	public void setStatus(int walkId, EnumStatus status) {
		String sql = "UPDATE " + WALKS_TABLE + " SET " + STATUS + " = ? WHERE " + WALK_ID + " = ?";
		System.out.println(sql);
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
				System.out.println("Walks Table exists");
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
				System.out.println("WalkDogs Table exists");
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
						+ WALKER_ID + " INT NOT NULL, " + "declined BOOLEAN, PRIMARY KEY (" + WALK_ID + ", " + WALKER_ID
						+ "));";
				stmt.executeUpdate(sql);
				System.out.println("Created WalkOffers Table");
			} else {
				System.out.println("WalkOffers Table exists");
			}
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public enum EnumStatus {
		OWNER_INITIALIZED(1), // Dog owner has created walk but can still edit details before it's visible
		OWNER_POSTED(2), // Dog owner has posted the walk for walkers to propose on
		WALKER_CHOSEN(3), // Dog owner has selected a walker
		WALKER_STARTED(4), // Walker has marked the walk as started (optional)
		WALKER_COMPLETED(5), // Walker has marked the walk as completed
		CANCELLED(6); // Walker or Dog owner have cancelled

		private final int statusCode;

		EnumStatus(int statusCode) {
			this.statusCode = statusCode;
		}

		public int toInt() {
			return statusCode;
		}

		public static EnumStatus fromInt(int statusCode) {
			for (EnumStatus type : EnumStatus.values()) {
				if (type.statusCode == statusCode) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid EnumStatus: " + statusCode);
		}
	}

}