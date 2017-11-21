package com.hgc.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hgc.admin.database.dao.PersonDAOImpl;
import com.hgc.admin.database.model.AccountModel;
import com.hgc.admin.database.model.Person;
import com.hgc.admin.database.service.PersonService;

@Controller
@RequestMapping({"home"})
public class DefaultController {
	private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);
	
	@Autowired
	@Qualifier(value="currentUser")
	private AccountModel currentUser;
		
	@RequestMapping(method = RequestMethod.GET)
	public String index(){
		return "layout/default";
	}
	
}
