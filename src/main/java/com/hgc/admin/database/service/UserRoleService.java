package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.UserRole;

public interface UserRoleService {

	public Integer addUserRole(UserRole p);
	public void updateUserRole(UserRole p);
	public List<UserRole> listUserRoles();
	public UserRole getUserRoleById(Integer id);
	public void removeUserRole(Integer id);
	public List<UserRole> queryUserRole(String query,String[] db_fields);
	public HashMap<Integer,UserRole> mapUserRoles();
}
