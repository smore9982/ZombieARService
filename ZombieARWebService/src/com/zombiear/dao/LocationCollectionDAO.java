package com.zombiear.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class LocationCollectionDAO {
	
	private static final String COLLECTION_NAME = "locations";
	String username;
	double longitude;
	double latitude;
	
	public LocationCollectionDAO(String username, double longitude, double latitude){
		this.username = username;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	
	public boolean updateLocation(){
		DB db = MongoDAOManager.getInstance().getDatabase();
		DBCollection coll = db.getCollection(COLLECTION_NAME);
		
		BasicDBObject query = new BasicDBObject("username",this.username);
		
		DBObject result = coll.findOne(query);
		if(result != null){
			BasicDBObject update = new BasicDBObject("$set", new BasicDBObject("longitude",longitude).append("latitude",latitude));
			coll.update(query, update);			
		}else{
			BasicDBObject insert = new BasicDBObject("username",username).append("longitude", longitude).append("latitude", latitude);
			coll.insert(query, insert);
		}
		return true;
	}
}
