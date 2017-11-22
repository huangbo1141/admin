package com.hgc.admin.database.service;

import java.util.List;

import com.hgc.admin.database.model.AdminRole;

public interface AdminRoleService {

	public Integer addAdminRole(AdminRole p);
	public void updateAdminRole(AdminRole p);
	public List<AdminRole> listAdminRoles();
	public AdminRole getAdminRoleById(int id);
	public void removeAdminRole(int id);
	public List<Object> queryAdminRole(String query);
}
