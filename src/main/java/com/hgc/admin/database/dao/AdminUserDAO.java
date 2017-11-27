package com.hgc.admin.database.dao;

import java.util.List;

import com.hgc.admin.database.model.AdminUser;

public interface AdminUserDAO {

	public Integer addAdminUser(AdminUser p);
	public void updateAdminUser(AdminUser p);
	public List<AdminUser> listAdminUsers();
	public AdminUser getAdminUserById(Integer id);
	public void removeAdminUser(Integer id);
	public List<Object> queryAdminUser(String query);
}
