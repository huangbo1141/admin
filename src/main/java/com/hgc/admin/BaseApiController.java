package com.hgc.admin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgc.admin.database.model.*;
import com.hgc.admin.database.service.*;
import com.hgc.admin.utils.AccountHelper;
import com.hgc.admin.utils.BaseHelper;
import com.hgc.admin.utils.UserHelper;

import java.math.BigInteger;

public class BaseApiController {

	@Autowired(required = true)
	@Qualifier(value = "transactionManager")
	public HibernateTransactionManager transactionManager;

	@Resource
	public UserHelper userHelper;

	@Autowired(required = true)
	@Qualifier(value = "personService")
	public PersonService personService;

	public Class<?> getModelClasses(String type) {
		String p = type.toLowerCase();
		if(p.equals("line")){
			return Line.class;
		}else if(p.equals("dan")){
			return Dan.class;
		}else{
			return Object.class;
		}
	}

	public Object getServiceInstances(String type) {
		String p = type.toLowerCase();
		if(p.equals("line")){
			return this.userHelper.lineService;
		}else if(p.equals("dan")){
			return this.userHelper.danService;
		}else{
			return Object.class;
		}
	}
}
