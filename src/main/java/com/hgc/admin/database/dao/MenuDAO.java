package com.hgc.admin.database.dao;

import java.util.List;

import com.hgc.admin.database.model.Menu;

public interface MenuDAO {

	public Integer addMenu(Menu p);
	public void updateMenu(Menu p);
	public List<Menu> listMenus();
	public Menu getMenuById(int id);
	public void removeMenu(int id);
	public List<Object> queryMenu(String query);
}
