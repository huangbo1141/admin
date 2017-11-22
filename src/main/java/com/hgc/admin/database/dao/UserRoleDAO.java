package com.hgc.admin.database.dao;

import java.util.List;

import com.hgc.admin.database.model.UserRole;

public interface UserRoleDAO {

	public Integer addUserRole(UserRole p);
	public void updateUserRole(UserRole p);
	public List<UserRole> listUserRoles();
	public UserRole getUserRoleById(int id);
	public void removeUserRole(int id);
	public List<Object> queryUserRole(String query);
}
