package com.hgc.admin.database.dao;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.MenuAction;

public interface MenuActionDAO {

	public Integer addMenuAction(MenuAction p);
	public void updateMenuAction(MenuAction p);
	public List<MenuAction> listMenuActions();
	public MenuAction getMenuActionById(Integer id);
	public void removeMenuAction(Integer id);
	public List<MenuAction> queryMenuAction(String SQL_QUERY,String[] fields);
	public HashMap<Integer,MenuAction> mapMenuActions();
}
