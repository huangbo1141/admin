package com.hgc.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import com.hgc.admin.database.service.PersonService;
import com.hgc.admin.utils.AccountHelper;

public class BaseController {

	@Autowired(required = true)
	@Qualifier(value = "transactionManager")
	public HibernateTransactionManager transactionManager;
	
	public String checkLogin(AccountHelper currentUser){
		if(!currentUser.hasLogin())
			return "redirect:account/login";
		return null;
	}
}
