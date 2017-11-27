package com.hgc.admin.model;

import java.util.List;

import com.hgc.admin.database.model.Menu;

public class Cat {
	private String username;
	private Menu curMenu;
	private List<CatMenu> listMenu;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Menu getCurMenu() {
		return curMenu;
	}
	public void setCurMenu(Menu curMenu) {
		this.curMenu = curMenu;
	}
	public List<CatMenu> getListMenu() {
		return listMenu;
	}
	public void setListMenu(List<CatMenu> listMenu) {
		this.listMenu = listMenu;
	} 
	
	
}
