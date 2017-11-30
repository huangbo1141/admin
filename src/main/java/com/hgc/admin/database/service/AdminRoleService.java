package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.AdminRole;

public interface AdminRoleService {

	public Integer addAdminRole(AdminRole p);
	public void updateAdminRole(AdminRole p);
	public List<AdminRole> listAdminRoles();
	public AdminRole getAdminRoleById(Integer id);
	public void removeAdminRole(Integer id);
	public List<AdminRole> queryAdminRole(String query,String[] db_fields);
	public HashMap<Integer,AdminRole> mapAdminRoles();
}
