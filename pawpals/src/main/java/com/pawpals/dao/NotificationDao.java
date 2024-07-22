package com.pawpals.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pawpals.beans.Notification;
import com.pawpals.interfaces.NotificationBuilder;

public class NotificationDao {
	private static NotificationDao dao;
	public static final String USER_ID = "user_id";
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	public static final String URL = "url";
	public static final String READ_STATUS = "read_status";
	public static final String DATETIME = "datetime";
	
	private NotificationDao() {}
	
	public static synchronized NotificationDao getDao() {
		if (dao == null) dao = new NotificationDao();
		return dao;
	}
	
	public void createNotificationForUser(Notification notif) {
		String sql = "INSERT INTO " + ApplicationDao.NOTIFICATIONS_TABLE + " (" + USER_ID + "," + TITLE + "," + DESCRIPTION
		+ ", "+URL+") VALUES (?, ?, ?, ?)";
		
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			
			stmt.setInt(1, notif.getUserId());
			stmt.setString(2, notif.getTitle());
			stmt.setString(3, notif.getDescription());
			stmt.setString(4, notif.getUrl());
			
			stmt.executeUpdate();

		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Notification> getNotificationsByUser(int userId) {
		ArrayList<Notification> notifications = new ArrayList<>();
		
		String sql = "SELECT * FROM " + ApplicationDao.NOTIFICATIONS_TABLE + " WHERE " + USER_ID + " = ?";
		
		try (
                Connection conn = DBConnection.getDBInstance();
                PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs != null && rs.next()) {
                Notification notif = new NotificationBuilder()
                    .setTitle(rs.getString(TITLE))
                    .setDescription(rs.getString(DESCRIPTION))
                    .setDate(rs.getString(DATETIME))
                    .setReadStatus(rs.getBoolean(READ_STATUS))
                    .setUrl(rs.getString(URL))
                    .create();
                
                notifications.add(notif);
            }

            if (rs != null) rs.close();

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		
		
		return notifications;
	}
}
