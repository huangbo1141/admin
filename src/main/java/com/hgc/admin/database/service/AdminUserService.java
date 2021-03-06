package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.AdminUser;

public interface AdminUserService {

	public Integer addAdminUser(AdminUser p);
	public void updateAdminUser(AdminUser p);
	public List<AdminUser> listAdminUsers();
	public AdminUser getAdminUserById(Integer id);
	public void removeAdminUser(Integer id);
	public List<AdminUser> queryAdminUser(String query,String[] db_fields);
	public HashMap<Integer,AdminUser> mapAdminUsers();
}
