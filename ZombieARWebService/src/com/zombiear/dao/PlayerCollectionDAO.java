package com.zombiear.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.zombiear.model.PlayerDataModel;

public class PlayerCollectionDAO {
	
	private static final String COLLECTION_NAME = "playerdata";
	private PlayerDataModel playerData;
	
	public PlayerCollectionDAO(PlayerDataModel playerData){
		this.playerData = playerData;
		
	}
	
	public boolean addPlayerData(){
		DB db = MongoDAOManager.getInstance().getDatabase();
		DBCollection coll = db.getCollection(COLLECTION_NAME);
		
		BasicDBObject query = new BasicDBObject("username", this.playerData.getUsername());
		DBObject result = coll.findOne(query);
		if(result !=null){
			BasicDBObject update = new BasicDBObject("username",playerData.getUsername()).append("isZombie", playerData.isZombie());						
			coll.update(query,update);
		}else{
			BasicDBObject insert = new BasicDBObject("username",playerData.getUsername()).append("isZombie", playerData.isZombie());						
			coll.insert(insert);
		}
		return true;					
	}
}
