package com.pawpals.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pawpals.beans.Dog;

public class WalkDogDao {
	private static WalkDogDao dao;
	public static final String ID = "id";
	
	private WalkDogDao() {}
	
	public static synchronized WalkDogDao getDao() {
		if (dao == null) dao = new WalkDogDao();
		return dao;
	}
	
	public void addDogsToWalk(int walkId,String[] dogIds) {
		String sql = "DELETE FROM " + ApplicationDao.WALKDOGS_TABLE + " WHERE " + WalkDao.WALK_ID + " = ?;";
		try (
				Connection conn = DBConnection.getDBInstance(); 
		) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, walkId);
			stmt.executeUpdate();
			
			sql = "INSERT INTO " + ApplicationDao.WALKDOGS_TABLE + " (" + WalkDao.WALK_ID + ", " + DogDao.DOG_ID + ") VALUES (?, ?);";
			
			stmt = conn.prepareStatement(sql);
			
			for (String id: dogIds) {
				stmt.setInt(1, walkId);
				stmt.setInt(2, Integer.parseInt(id));
				stmt.addBatch();
			}

			stmt.executeBatch();
			
			stmt.close();
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public List<Dog> getWalkDogs(int walkId) {
		List<Dog> dogsList = new ArrayList<>();

		String sql = "SELECT * FROM " + ApplicationDao.WALKDOGS_TABLE + " LEFT JOIN "+ApplicationDao.DOGS_TABLE+" USING ("+DogDao.DOG_ID+") WHERE " + WalkDao.WALK_ID + " = ?";
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
	

}
