package com.pawpals.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.pawpals.beans.Dog;

public class DogDao {
    public static final DogDao dogDao = new DogDao();
    private final String DOG_ID = "dog_id";
    private final String OWNER_ID = "owner_id";
    private final String NAME = "name";
    private final String SIZE = "size";
    private final String SPECIAL_NEEDS = "special_needs";
    private final String IMMUNIZED = "immunized";

    private DogDao() {}
    
    public Dog getDogById(int dogId) {
    	String sql = "SELECT * FROM " + ApplicationDao.DOGS_TABLE + " WHERE " + DOG_ID + " = ?";
        try (
                Connection conn = DBConnection.getDBInstance();
                PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
            stmt.setInt(1, dogId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int ownerId = rs.getInt("owner_id");
            String dogName = rs.getString("name");
            String dogSize = rs.getString("size");
            String dogSpecialNeeds = rs.getString("special_needs");
            Boolean immunized = rs.getBoolean("immunized");
            return new Dog(dogId, ownerId, dogName, dogSize, dogSpecialNeeds, immunized);
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.err.println("Could not get dog by ID");
    	return null;
    }

    public void addDog(int ownerId, String name, String size, String specialNeeds, boolean immunized) {
        String sql = "INSERT INTO " + ApplicationDao.DOGS_TABLE + " (" + OWNER_ID + "," + NAME + "," + SIZE + "," + SPECIAL_NEEDS + "," + IMMUNIZED + ") VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conn = DBConnection.getDBInstance();
                PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
            stmt.setInt(1, ownerId);
            stmt.setString(2, name);
            stmt.setString(3, size);
            stmt.setString(4, specialNeeds);
            stmt.setBoolean(5, immunized);

            stmt.executeUpdate();

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Dog> getDogsByOwnerUserId(int userId) {
        List<Dog> dogs = new ArrayList<>();
        String sql = "SELECT " + DOG_ID + ", " + OWNER_ID + ", " + NAME + ", " + SIZE + ", " + SPECIAL_NEEDS + ", " + IMMUNIZED + " FROM " + ApplicationDao.DOGS_TABLE + " WHERE " + OWNER_ID + " = ?";

        try (
                Connection conn = DBConnection.getDBInstance();
                PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs != null && rs.next()) {
                Dog dog = new Dog(
                    rs.getInt(DOG_ID),				rs.getInt(OWNER_ID),
                    rs.getString(NAME),				rs.getString(SIZE),
                    rs.getString(SPECIAL_NEEDS),	rs.getBoolean(IMMUNIZED)
                );
                dogs.add(dog);
            }

            if (rs != null) rs.close();

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return dogs;
    }
}
