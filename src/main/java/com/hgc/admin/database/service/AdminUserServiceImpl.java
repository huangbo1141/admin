package com.hgc.admin.database.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.AdminUserDAO;
import com.hgc.admin.database.model.AdminUser;

@Service
public class AdminUserServiceImpl implements AdminUserService {

	private AdminUserDAO personDAO;

	public void setAdminUserDAO(AdminUserDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addAdminUser(AdminUser p) {
		return this.personDAO.addAdminUser(p);
	}

	@Override
	@Transactional
	public void updateAdminUser(AdminUser p) {
		this.personDAO.updateAdminUser(p);
	}

	@Override
	@Transactional
	public List<AdminUser> listAdminUsers() {
		return this.personDAO.listAdminUsers();
	}

	@Override
	@Transactional
	public AdminUser getAdminUserById(Integer id) {
		return this.personDAO.getAdminUserById(id);
	}

	@Override
	@Transactional
	public void removeAdminUser(Integer id) {
		this.personDAO.removeAdminUser(id);
	}

	@Override
	public List<Object> queryAdminUser(String query) {
		// TODO Auto-generated method stub
		return this.personDAO.queryAdminUser(query);

	}

}
