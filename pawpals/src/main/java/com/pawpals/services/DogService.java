package com.pawpals.services;

import java.util.List;

import com.pawpals.beans.Dog;
import com.pawpals.dao.DogDao;

public class DogService {
	public static final DogService svc = new DogService();

	public List<Dog> getDogs_by_DogOwnerUserId(int userId) {
		return DogDao.dogDao.getDogsByOwnerUserId(userId);
	}	
}
