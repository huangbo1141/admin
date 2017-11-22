package com.hgc.admin.database.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.MenuActionDAO;
import com.hgc.admin.database.model.MenuAction;

@Service
public class MenuActionServiceImpl implements MenuActionService {

	private MenuActionDAO personDAO;

	public void setMenuActionDAO(MenuActionDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addMenuAction(MenuAction p) {
		return this.personDAO.addMenuAction(p);
	}

	@Override
	@Transactional
	public void updateMenuAction(MenuAction p) {
		this.personDAO.updateMenuAction(p);
	}

	@Override
	@Transactional
	public List<MenuAction> listMenuActions() {
		return this.personDAO.listMenuActions();
	}

	@Override
	@Transactional
	public MenuAction getMenuActionById(int id) {
		return this.personDAO.getMenuActionById(id);
	}

	@Override
	@Transactional
	public void removeMenuAction(int id) {
		this.personDAO.removeMenuAction(id);
	}

	@Override
	public List<Object> queryMenuAction(String query) {
		// TODO Auto-generated method stub
		return this.personDAO.queryMenuAction(query);

	}

}
