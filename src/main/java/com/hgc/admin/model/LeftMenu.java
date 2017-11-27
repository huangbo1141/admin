package com.hgc.admin.model;

import java.util.ArrayList;
import java.util.List;

import com.hgc.admin.database.model.Menu;

public class LeftMenu {

	private Menu menu;
	private int mid;
	private List<Integer> actions = new ArrayList<Integer>();
	private List<LeftMenu> submenu = new ArrayList<LeftMenu>();
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public List<Integer> getActions() {
		return actions;
	}
	public void setActions(List<Integer> actions) {
		this.actions = actions;
	}
	public List<LeftMenu> getSubmenu() {
		return submenu;
	}
	public void setSubmenu(List<LeftMenu> submenu) {
		this.submenu = submenu;
	}
	
	
}
