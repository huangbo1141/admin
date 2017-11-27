package com.hgc.admin.database.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.UserDAO;
import com.hgc.admin.database.model.User;

@Service
public class UserServiceImpl implements UserService {

	private UserDAO personDAO;

	public void setUserDAO(UserDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addUser(User p) {
		return this.personDAO.addUser(p);
	}

	@Override
	@Transactional
	public void updateUser(User p) {
		this.personDAO.updateUser(p);
	}

	@Override
	@Transactional
	public List<User> listUsers() {
		return this.personDAO.listUsers();
	}

	@Override
	@Transactional
	public User getUserById(Integer id) {
		return this.personDAO.getUserById(id);
	}

	@Override
	@Transactional
	public void removeUser(Integer id) {
		this.personDAO.removeUser(id);
	}

	@Override
	public List<Object> queryUser(String query) {
		// TODO Auto-generated method stub
		return this.personDAO.queryUser(query);

	}

}
