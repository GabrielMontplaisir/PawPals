package com.pawpals.services;

import java.util.HashMap;
import java.util.List;
import com.pawpals.beans.*;
import com.pawpals.dao.WalkDao;

public class WalkService {
	public static final WalkService svc = new WalkService();
	
	public List<Dog> getDogs(int walkId){
		return WalkDao.dao.getDogs(walkId);
	}
	public void doCancel(Walk walk) {
		walk.setIntStatus(Walk.EnumStatus.CANCELLED.toInt());
		WalkDao.dao.cancel(walk.getWalkId());
	}
	public int getOfferCount(int walkId) {
		return WalkDao.dao.getWalkOfferCount(walkId);
	}	
	public List<Walk> getWalks_as_DogOwner(int userId){
		return WalkDao.dao.getWalksByOwnerId(userId);
	}
	public Walk getWalk_by_WalkId(int walkId) {//  validates user is dog owner of requested walk 
		return WalkDao.dao.getWalkById(walkId);
	}	
	public Walk getWalk_by_WalkId_as_Owner(int userId, int walkId) {//  validates user is dog owner of requested walk 
		Walk walk = WalkDao.dao.getWalkById(walkId);
		if ( walk.getOwnerId() == userId ) {
			return walk;
		}
		System.err.println(" * * " + userId + " Tried to get walkId as owner but isn't owner " + walkId) ;
		return null;
	}
	public List<WalkOffer> getWalkOffers_by_WalkId(int walkId) {
		return WalkDao.dao.getWalkOffers(walkId); 
	}
	public void cancelWalkOffer(int walkId, int walkerUserId) {
		WalkDao.dao.cancelWalkOffer(walkId, walkerUserId);
	}
	public List<Walk> getWalks_for_Soliciting_WalkOffers(int userId, HashMap<Integer, Boolean> walkOffers){
		return WalkDao.dao.getWalksPostedForReceivingOffers(userId, walkOffers);
	}	
}
