package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.MenuDAO;
import com.hgc.admin.database.model.Menu;

@Service
public class MenuServiceImpl implements MenuService {

	private MenuDAO personDAO;

	public void setMenuDAO(MenuDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addMenu(Menu p) {
		return this.personDAO.addMenu(p);
	}

	@Override
	@Transactional
	public void updateMenu(Menu p) {
		this.personDAO.updateMenu(p);
	}

	@Override
	@Transactional
	public List<Menu> listMenus() {
		return this.personDAO.listMenus();
	}

	@Override
	@Transactional
	public Menu getMenuById(Integer id) {
		return this.personDAO.getMenuById(id);
	}

	@Override
	@Transactional
	public void removeMenu(Integer id) {
		this.personDAO.removeMenu(id);
	}

	@Override
	public List<Menu> queryMenu(String query,String[] db_fields){
		// TODO Auto-generated method stub
		return this.personDAO.queryMenu(query, db_fields);

	}

	@Override
		public HashMap<Integer, Menu> mapMenus() {
			// TODO Auto-generated method stub
			return this.personDAO.mapMenus();
		}
}
