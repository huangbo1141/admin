package com.hgc.admin.database.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.AdminRoleDAO;
import com.hgc.admin.database.model.AdminRole;

@Service
public class AdminRoleServiceImpl implements AdminRoleService {

	private AdminRoleDAO personDAO;

	public void setAdminRoleDAO(AdminRoleDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addAdminRole(AdminRole p) {
		return this.personDAO.addAdminRole(p);
	}

	@Override
	@Transactional
	public void updateAdminRole(AdminRole p) {
		this.personDAO.updateAdminRole(p);
	}

	@Override
	@Transactional
	public List<AdminRole> listAdminRoles() {
		return this.personDAO.listAdminRoles();
	}

	@Override
	@Transactional
	public AdminRole getAdminRoleById(int id) {
		return this.personDAO.getAdminRoleById(id);
	}

	@Override
	@Transactional
	public void removeAdminRole(int id) {
		this.personDAO.removeAdminRole(id);
	}

	@Override
	public List<Object> queryAdminRole(String query) {
		// TODO Auto-generated method stub
		return this.personDAO.queryAdminRole(query);

	}

}
