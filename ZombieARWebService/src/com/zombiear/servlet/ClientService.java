package com.zombiear.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.zombiear.dao.UserCollectionDAO;



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
			return JSONObject.fromObject("{result:0}");
		}else{
			return JSONObject.fromObject("{result:1}");
		}
	}
	
	public JSONObject doRegister(String username, String password){
		UserCollectionDAO loginDAO = new UserCollectionDAO(username,password);
		boolean result = loginDAO.append();
		if(result){
			return JSONObject.fromObject("{result:0}");
		}else{
			return JSONObject.fromObject("{result:1}");
		}
	}
}
