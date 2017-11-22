package com.hgc.admin.utils;

import java.util.List;

import org.hibernate.SessionFactory;

import com.hgc.admin.database.service.AccountService;

public interface ControllerHelper {

	public Object[] login(String username, String password, SessionFactory sessionFactory);
	public boolean fakeLogin();
	public List<Object> queryList(String SQL_QUERY,SessionFactory sessionFactory);
	
}