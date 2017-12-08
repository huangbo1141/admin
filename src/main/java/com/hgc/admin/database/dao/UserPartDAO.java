package com.hgc.admin.database.dao;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.UserPart;

public interface UserPartDAO {

	public Integer addUserPart(UserPart p);
	public void updateUserPart(UserPart p);
	public List<UserPart> listUserParts();
	public UserPart getUserPartById(Integer id);
	public void removeUserPart(Integer id);
	public List<UserPart> queryUserPart(String SQL_QUERY,String[] fields);
	public HashMap<Integer,UserPart> mapUserParts();
}
