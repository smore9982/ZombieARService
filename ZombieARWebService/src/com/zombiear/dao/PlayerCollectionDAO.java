package com.zombiear.dao;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.zombiear.model.PlayerDataModel;
import com.zombiear.model.PlayerItem;

public class PlayerCollectionDAO {
	
	private static final String COLLECTION_NAME = "playerdata";	
	public PlayerCollectionDAO(){		
	}
	
	public boolean addPlayerData(PlayerDataModel playerData){
		DB db = MongoDAOManager.getInstance().getDatabase();
		DBCollection coll = db.getCollection(COLLECTION_NAME);
		
		BasicDBObject query = new BasicDBObject("username", playerData.getUsername());
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
	
	public PlayerDataModel getPlayerData(String username){
		DB db = MongoDAOManager.getInstance().getDatabase();
		DBCollection coll = db.getCollection(COLLECTION_NAME);
		
		BasicDBObject query = new BasicDBObject("username", username);
		DBObject result = coll.findOne(query);
		if(result !=null){
			JSONObject playerData = JSONObject.fromObject(result);
			String user = playerData.optString("username");
			boolean isZombie = playerData.optBoolean("isZombie", false);
			double infectionRange = playerData.optDouble("infectionRange", 10.0);
			
			PlayerDataModel dataModel = new PlayerDataModel(user,isZombie,new ArrayList<PlayerItem>(),infectionRange);
			return dataModel;			
		}else{
			return null;
		}				
	}
}
