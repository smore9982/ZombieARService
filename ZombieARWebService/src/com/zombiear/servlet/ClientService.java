package com.zombiear.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zombiear.dao.LocationCollectionDAO;
import com.zombiear.dao.MongoDAOManager;
import com.zombiear.dao.PlayerCollectionDAO;
import com.zombiear.dao.UserCollectionDAO;
import com.zombiear.model.PlayerDataModel;
import com.zombiear.model.PlayerItem;



@WebServlet("/ZombieARService")
public class ClientService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ClientService() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String payload = request.getParameter("payload");
		JSONObject parameters = JSONObject.fromObject(payload);
				
		String action =  parameters.getString("action");
		
		JSONObject responsePayload = JSONObject.fromObject("{}");
		if(action.equalsIgnoreCase("login")){
			System.out.println("Login request");			
			responsePayload = doLogin(parameters.optString("username"),parameters.optString("password"));
		}else if(action.equalsIgnoreCase("register")){
			System.out.println("Register request");
			responsePayload = doRegister(parameters.optString("username"),parameters.optString("password"));
		}else if(action.equalsIgnoreCase("LOCATIONUPDATE")){
			System.out.println("Location Update");
			String username = parameters.optString("username");
			double longitude = parameters.optDouble("longitude");
			double latitude = parameters.optDouble("latitude");
			responsePayload = doLocationUpdate(username,longitude,latitude);
		}else if(action.equals("findzombies")){
			System.out.println("Find Zombies");
			responsePayload = doFindZombies();
		}
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(responsePayload.toString());
		out.flush();
	}
	
	public JSONObject doLogin(String username, String password){
		UserCollectionDAO loginDAO = new UserCollectionDAO(username,password);
		String result = loginDAO.query();
		if(result != null){
			return JSONObject.fromObject("{result:0,username:'" + username +"'}");
		}else{
			return JSONObject.fromObject("{result:1}");
		}
	}
	
	public JSONObject doRegister(String username, String password){
		UserCollectionDAO loginDAO = new UserCollectionDAO(username,password);
		boolean result = loginDAO.append();
		if(result){
			PlayerDataModel playerData = null;
			if(username.contains("zombie")){
				playerData = new PlayerDataModel(username,true,new ArrayList<PlayerItem>());
			}else{
				playerData = new PlayerDataModel(username,false,new ArrayList<PlayerItem>());
			}
			PlayerCollectionDAO playerCollectionDAO = new PlayerCollectionDAO(playerData);
			playerCollectionDAO.addPlayerData();			
			return JSONObject.fromObject("{result:0,username:'" + username +"'}");
		}else{
			return JSONObject.fromObject("{result:1}");
		}
	}
	
	public JSONObject doLocationUpdate(String username, double longitude, double latitude){
		LocationCollectionDAO locationDAO = new LocationCollectionDAO(username,longitude,latitude);
		locationDAO.updateLocation();
		return JSONObject.fromObject("{result:0}");		
	}
	
	public JSONObject doFindZombies(){
		DB db = MongoDAOManager.getInstance().getDatabase();
		DBCollection coll = db.getCollection("playerdata");
		
		DBCursor cursor = coll.find();
		
		JSONArray arr =new JSONArray();
		while(cursor.hasNext()){
			DBObject playerdata = cursor.next();
			if(playerdata == null) continue;
			JSONObject json = JSONObject.fromObject(playerdata.toString());
			boolean isZombie = json.optBoolean("isZombie", false);
			if(isZombie){
				
				JSONObject obj = new JSONObject();
				String userName = json.optString("username", "");
				if(userName.length() > 0){
					DBCollection locColl = db.getCollection("locations");										
					DBObject locationObject = locColl.findOne(new BasicDBObject("username",userName));
					JSONObject locJSON = JSONObject.fromObject(locationObject.toString());
					double longitude = locJSON.optDouble("longitude", 0.0);
					double latitude = locJSON.optDouble("latitude", 0.0);										
					obj.put("username", userName);
					obj.put("longitude", longitude);
					obj.put("latitude",latitude);
					arr.add(obj);
				}
			}			
		}
		
		JSONObject result = new JSONObject();
		result.put("result", 0);
		result.put("locdata", arr);		
		return result;
		
	}
}
