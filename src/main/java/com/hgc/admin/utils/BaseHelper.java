package com.hgc.admin.utils;

import java.util.List;

import org.hibernate.SessionFactory;

public interface BaseHelper {

	
	public List<Object> queryList(String SQL_QUERY,SessionFactory sessionFactory);
	
}