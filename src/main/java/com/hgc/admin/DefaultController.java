package com.hgc.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgc.admin.constants.Constants;
import com.hgc.admin.database.model.Menu;
import com.hgc.admin.model.Cat;
import com.hgc.admin.model.CatMenu;
import com.hgc.admin.model.LeftMenu;

@Controller
@RequestMapping("{term}/{subterm}")
public class DefaultController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(@PathVariable("term") String term,@PathVariable("subterm") String subterm,Model model){
		
		HashMap<String,Object> chkAll = checkAll(currentUser,term+"_"+subterm); 
		String chkResult = chkAll.get("result").toString();
		String url = chkAll.get("url").toString();
		if(chkResult.equals("redirect")){
			return url;
		}else{
			// no permission or other
			if(!chkResult.equals("ok")){
				return url;
			}
		}
		System.out.println("current User " + currentUser.getUsername());
		
		setBaseModelData(model,term,subterm);
		setContentModelData(model,term,subterm);
		return "layout/default";
	}
	
	public void setContentModelData(Model model,String term,String subterm){
		HashMap<String,Object> pageData = new HashMap<String,Object>();
		
		try{
			if(term.equals("menu")){
				if(subterm.equals("menu")){
					// need to fetch all menu data.
					pageData.put("list_data", baseHelper.menuService.listMenus());
				}else if(subterm.equals("role")){
					pageData.put("list_data", baseHelper.adminRoleService.listAdminRoles());
				}else if(subterm.equals("adminuser")){
					pageData.put("list_data", baseHelper.adminUserService.listAdminUsers());
				}
			}
		}catch(Exception ex){
			
		}
		
		model.addAttribute("pageData",pageData);
		
	}
	
}
