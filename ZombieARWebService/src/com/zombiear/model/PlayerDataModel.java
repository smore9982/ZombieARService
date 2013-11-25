package com.zombiear.model;

import java.util.List;

public class PlayerDataModel {
	
	private String username;
	private boolean zombie;
	private List<PlayerItem> playerItems;
		
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
	
	

}
