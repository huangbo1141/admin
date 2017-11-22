package com.hgc.admin.database.dao;

import java.util.List;

import com.hgc.admin.database.model.MenuAction;

public interface MenuActionDAO {

	public Integer addMenuAction(MenuAction p);
	public void updateMenuAction(MenuAction p);
	public List<MenuAction> listMenuActions();
	public MenuAction getMenuActionById(int id);
	public void removeMenuAction(int id);
	public List<Object> queryMenuAction(String query);
}
