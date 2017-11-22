package com.hgc.admin.database.service;

import java.util.List;

import com.hgc.admin.database.model.User;

public interface UserService {

	public Integer addUser(User p);
	public void updateUser(User p);
	public List<User> listUsers();
	public User getUserById(int id);
	public void removeUser(int id);
	public List<Object> queryUser(String query);
}
