package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.Menu;

public interface MenuService {

	public Integer addMenu(Menu p);
	public void updateMenu(Menu p);
	public List<Menu> listMenus();
	public Menu getMenuById(Integer id);
	public void removeMenu(Integer id);
	public List<Menu> queryMenu(String query,String[] db_fields);
	public HashMap<Integer,Menu> mapMenus();
}
