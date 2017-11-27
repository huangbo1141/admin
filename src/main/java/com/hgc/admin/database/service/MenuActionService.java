package com.hgc.admin.database.service;

import java.util.List;

import com.hgc.admin.database.model.MenuAction;

public interface MenuActionService {

	public Integer addMenuAction(MenuAction p);
	public void updateMenuAction(MenuAction p);
	public List<MenuAction> listMenuActions();
	public MenuAction getMenuActionById(Integer id);
	public void removeMenuAction(Integer id);
	public List<Object> queryMenuAction(String query);
}