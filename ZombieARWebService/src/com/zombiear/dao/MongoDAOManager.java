package com.zombiear.dao;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDAOManager {
	
	static MongoDAOManager instance;
	
	private MongoClient client;
	private DB database;
	
	public static MongoDAOManager getInstance(){
		if(instance == null){
			try {
				instance = new MongoDAOManager();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	public MongoDAOManager() throws UnknownHostException{
		this.client =  new MongoClient( "ec2-54-204-47-139.compute-1.amazonaws.com" , 27017 );
		this.database = client.getDB("ZombieARDatabase");
	}

	public MongoClient getClient() {
		return client;
	}

	public void setClient(MongoClient client) {
		this.client = client;
	}

	public DB getDatabase() {
		return database;
	}

	public void setDatabase(DB database) {
		this.database = database;
	}
}
