package com.zombiear.model;

import java.util.List;

public class PlayerDataModel {
	
	private String username;
	private boolean zombie;
	private double longitude;
	private double latitude;
	private List<PlayerItem> playerItems;
	private double infectionRange = 0.0;
		
	public PlayerDataModel(String username, boolean isZombie, List<PlayerItem> items, double infectionRange){
		this(username, isZombie, items);
		this.infectionRange = infectionRange;
	}
	
	public PlayerDataModel(String username, boolean isZombie, List<PlayerItem> items){
		this.username = username;
		this.zombie = isZombie;
		this.playerItems = items;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isZombie() {
		return zombie;
	}

	public void setZombie(boolean zombie) {
		this.zombie = zombie;
	}

	public List<PlayerItem> getPlayerItems() {
		return playerItems;
	}

	public void setPlayerItems(List<PlayerItem> playerItems) {
		this.playerItems = playerItems;
	}

	public double getInfectionRange() {
		return infectionRange;
	}

	public void setInfectionRange(double infectionRange) {
		this.infectionRange = infectionRange;
	}
}
