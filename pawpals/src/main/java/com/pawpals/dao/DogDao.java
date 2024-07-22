package com.pawpals.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.pawpals.beans.Dog;
import com.pawpals.beans.User;

public class DogDao {
    private static DogDao dao;
    public static final String DOG_ID = "dog_id";
    public static final String OWNER_ID = "owner_id";
    public static final String NAME = "name";
    public static final String SIZE = "size";
    public static final String SPECIAL_NEEDS = "special_needs";
    public static final String IMMUNIZED = "immunized";

    private DogDao() {}
    
	public static synchronized DogDao getDao() {
		if (dao == null) dao = new DogDao();
		return dao;
	}
    
    public Dog getDogById(int dogId) {
    	String sql = "SELECT * FROM " + ApplicationDao.DOGS_TABLE + " WHERE " + DOG_ID + " = ?";
        try (
                Connection conn = DBConnection.getDBInstance();
                PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
            stmt.setInt(1, dogId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            
            Dog dog = new Dog(
            		dogId, 
            		rs.getInt(OWNER_ID), 
            		rs.getString(NAME), 
            		rs.getString(SIZE), 
            		rs.getString(SPECIAL_NEEDS), 
            		rs.getBoolean(IMMUNIZED)
            );
            
            rs.close();
            
            return dog;
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.err.println("Could not get dog by ID");
    	return null;
    }

    public void addDog(User user, HttpServletRequest req) {
        String name = req.getParameter("name");
        String size = req.getParameter("size");
        String specialNeeds = req.getParameter("specialneeds");
        boolean immunized = req.getParameter("immunized") != null;
        String sql = "INSERT INTO " + ApplicationDao.DOGS_TABLE + " (" + OWNER_ID + "," + NAME + "," + SIZE + "," + SPECIAL_NEEDS + "," + IMMUNIZED + ") VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conn = DBConnection.getDBInstance();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ) {
            stmt.setInt(1, user.getId());
            stmt.setString(2, name);
            stmt.setString(3, size);
            stmt.setString(4, specialNeeds);
            stmt.setBoolean(5, immunized);

            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            
            if (rs != null && rs.next()) {
            	Dog dog = new Dog(
            			rs.getInt(1),
            			user.getId(),
            			name,
            			size,
            			specialNeeds,
            			immunized
            	);
            	
            	user.getDogList().put(rs.getInt(1), dog);
            }
            
            if (rs != null) rs.close();

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Dog> getDogsByOwner(int userId) {
        Map<Integer, Dog> dogs = new HashMap<>();
        String sql = "SELECT * FROM " + ApplicationDao.DOGS_TABLE + " WHERE " + OWNER_ID + " = ?";

        try (
                Connection conn = DBConnection.getDBInstance();
                PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs != null && rs.next()) {
                Dog dog = new Dog(
                    rs.getInt(DOG_ID),
                    rs.getInt(OWNER_ID),
                    rs.getString(NAME),
                    rs.getString(SIZE),
                    rs.getString(SPECIAL_NEEDS),
                    rs.getBoolean(IMMUNIZED)
                );
                
                dogs.put(dog.getDogId(), dog);
            }

            if (rs != null) rs.close();

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return dogs;
    }
    
    public void removeDog(int dogId) {
        String sql = "DELETE FROM " + ApplicationDao.DOGS_TABLE + " WHERE " + DOG_ID + " = ?;";

        try (
                Connection conn = DBConnection.getDBInstance();
                PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
            stmt.setInt(1, dogId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
