package com.hgc.admin;

import com.hgc.admin.database.model.AccountModel;
import com.hgc.admin.database.service.PersonService;

public class BaseController {

	public String checkLogin(AccountModel currentUser){
		if(!currentUser.hasLogin())
			return "redirect:account/login";
		return null;
	}
}
