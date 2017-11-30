package com.hgc.admin.database.dao;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.User;

public interface UserDAO {

	public Integer addUser(User p);
	public void updateUser(User p);
	public List<User> listUsers();
	public User getUserById(Integer id);
	public void removeUser(Integer id);
	public List<User> queryUser(String SQL_QUERY,String[] fields);
	public HashMap<Integer,User> mapUsers();
}
