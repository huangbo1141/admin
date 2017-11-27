package com.hgc.admin.model;

import java.util.ArrayList;
import java.util.List;

import com.hgc.admin.database.model.Menu;

public class CatMenu {
	private List<Menu> submenu;
	private Menu menu;
	
	
	public List<Menu> getSubmenu() {
		return submenu;
	}
	public void setSubmenu(List<Menu> submenu) {
		this.submenu = submenu;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	
}
