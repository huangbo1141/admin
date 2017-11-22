package com.hgc.admin;

import java.util.List;

import javax.annotation.Resource;

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
import com.hgc.admin.database.model.Person;
import com.hgc.admin.database.service.PersonService;
import com.hgc.admin.utils.AccountHelper;
import com.hgc.admin.utils.ControllerHelper;

@Controller
@RequestMapping("{term}")
public class DefaultController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);
	
	@Autowired
	@Qualifier(value="currentUser")
	private AccountHelper currentUser;
			
	@Resource
	private ControllerHelper baseHelper;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(@PathVariable("term") String term,Model model){
		String login_redirect = checkLogin(currentUser); 
		if(login_redirect!=null){
			return login_redirect;
		}
		System.out.println("current User " + currentUser.getUsername());
		model.addAttribute("pageName", "Home");
		model.addAttribute("pageID", "1");
		model.addAttribute("pageTerm", term);
		
		//System.out.println(currentUser.getUsername());
		List ps = baseHelper.queryList("select * from tbl_time_type", transactionManager.getSessionFactory());
		return "layout/default";
	}
	
}
