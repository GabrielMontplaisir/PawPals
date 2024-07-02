package com.pawpals.services;

import com.pawpals.beans.*;
import com.pawpals.dao.UserDao;

public class UserService {
	public static final UserService svc = new UserService();
	public User getUserById(int userId) {
		return UserDao.userDao.getUserById(userId)	;
	}
	
}
