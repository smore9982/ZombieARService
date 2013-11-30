package com.zombiear.dao;

import net.sf.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class LocationCollectionDAO {
	
	private static final String COLLECTION_NAME = "locations";
	
	public boolean updateLocation(String username, double longitude, double latitude){
		DB db = MongoDAOManager.getInstance().getDatabase();
		DBCollection coll = db.getCollection(COLLECTION_NAME);
		
		BasicDBObject query = new BasicDBObject("username",username);
		
		DBObject result = coll.findOne(query);
		if(result != null){
			BasicDBObject update = new BasicDBObject("$set", new BasicDBObject("longitude",longitude).append("latitude",latitude));
			coll.update(query, update);			
		}else{
			BasicDBObject insert = new BasicDBObject("username",username).append("longitude", longitude).append("latitude", latitude);
			coll.insert(insert);
		}
		return true;
	}
	
	public double[] getLocation(String username){
		double[] coord = new double[2];
		DB db = MongoDAOManager.getInstance().getDatabase();
		DBCollection coll = db.getCollection(COLLECTION_NAME);
		BasicDBObject query = new BasicDBObject("username",username);
		DBObject result = coll.findOne(query);

		if(result !=null){
			JSONObject locObject = JSONObject.fromObject(result.toString());
			double longitude = locObject.optDouble("longitude");
			double latitude = locObject.optDouble("latitude");
			coord[0]=latitude;
			coord[1]=longitude;
			return coord;			
		}else{
			return null;
		}
	}
	
	
}
