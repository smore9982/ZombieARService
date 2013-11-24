package com.zombiear.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class UserCollectionDAO {
	
	private static final String COLLECTION_NAME_USERS = "users";
	String username;
	String password;
	
	public UserCollectionDAO(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public String query(){
		DB db = MongoDAOManager.getInstance().getDatabase();
		DBCollection coll = db.getCollection(COLLECTION_NAME_USERS);
		
		BasicDBObject query = new BasicDBObject("username",this.username)
								  .append("password",this.password);
		
		DBObject result = (DBObject) coll.findOne(query);
		if(result!=null){
			return result.toString();
		}else{
			return null;
		}
	}
	
	public boolean append(){
		DB db = MongoDAOManager.getInstance().getDatabase();
		DBCollection coll = db.getCollection(COLLECTION_NAME_USERS);
				
		BasicDBObject query = new BasicDBObject("username",this.username);
		DBObject result = (DBObject) coll.findOne(query);		
		if(result ==null){
			BasicDBObject update = new BasicDBObject("username",this.username)
			  						  .append("password",this.password);
			coll.insert(update);
			return true;
		}else{
			return false;
		}
	}
}
