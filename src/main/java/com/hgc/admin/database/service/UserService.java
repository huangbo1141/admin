package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.User;

public interface UserService {

	public Integer addUser(User p);
	public void updateUser(User p);
	public List<User> listUsers();
	public User getUserById(Integer id);
	public void removeUser(Integer id);
	public List<User> queryUser(String query,String[] db_fields);
	public HashMap<Integer,User> mapUsers();
}
