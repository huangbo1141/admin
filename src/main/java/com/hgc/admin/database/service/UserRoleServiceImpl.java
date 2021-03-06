package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.UserRoleDAO;
import com.hgc.admin.database.model.UserRole;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	private UserRoleDAO personDAO;

	public void setUserRoleDAO(UserRoleDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addUserRole(UserRole p) {
		return this.personDAO.addUserRole(p);
	}

	@Override
	@Transactional
	public void updateUserRole(UserRole p) {
		this.personDAO.updateUserRole(p);
	}

	@Override
	@Transactional
	public List<UserRole> listUserRoles() {
		return this.personDAO.listUserRoles();
	}

	@Override
	@Transactional
	public UserRole getUserRoleById(Integer id) {
		return this.personDAO.getUserRoleById(id);
	}

	@Override
	@Transactional
	public void removeUserRole(Integer id) {
		this.personDAO.removeUserRole(id);
	}

	@Override
	public List<UserRole> queryUserRole(String query,String[] db_fields){
		// TODO Auto-generated method stub
		return this.personDAO.queryUserRole(query, db_fields);

	}

	@Override
		public HashMap<Integer, UserRole> mapUserRoles() {
			// TODO Auto-generated method stub
			return this.personDAO.mapUserRoles();
		}
}
