package com.hgc.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgc.admin.constants.Constants;
import com.hgc.admin.database.model.Menu;
import com.hgc.admin.model.CatMenu;
import com.hgc.admin.model.LeftMenu;
import com.hgc.admin.utils.AccountHelper;
import com.hgc.admin.utils.BaseHelperImpl;
import com.hgc.admin.utils.BackendApiHelper;




public class BaseAdminController {

	@Autowired(required = true)
	@Qualifier(value = "transactionManager")
	public HibernateTransactionManager transactionManager;
	
	@Autowired
	@Qualifier(value="currentUser")
	public AccountHelper currentUser;
			
	@Resource
	public BackendApiHelper backendApiHelper;
	
	
	
	public HashMap<String,Object> checkAll(AccountHelper currentUser,String term_sum){
		HashMap<String,Object> ret = new HashMap<String,Object>();
		ret.put("url", "redirect:/account/login");
		ret.put("result", "redirect");
		if(!currentUser.hasLogin())
			return ret;
		if(currentUser.checkPermission(backendApiHelper)){
			String chkTerm = currentUser.checkTerm(term_sum);
			
			ret = new HashMap<String,Object>();
			ret.put("result", chkTerm);
			if(chkTerm.endsWith("no_permission")){
				ret.put("url", "nopermission");
			}else if(chkTerm.equals("invalid")){
				ret.put("url", "invalid");
			}else{
				ret.put("url", "redirect:/account/login");
			}
			return ret;
		}
		return ret;
	}
	
	public void setBaseModelData(Model model,String term,String subterm){
		model.addAttribute("pageName", currentUser.getCurMenu().getMenu().getName());
		model.addAttribute("pageID", currentUser.getCurMenu().getMenu().getId());
		model.addAttribute("rootdir", Constants.ROOT_DIR);
		model.addAttribute("pageTerm", term);
		model.addAttribute("pageSubTerm", subterm);
		model.addAttribute("currentUser", currentUser);
		
		HashMap<String,Object> cat = new HashMap<String,Object>();
		
		cat.put("username", currentUser.getUsername());
		
		List<CatMenu> cat_listmenu = new ArrayList<CatMenu>();
		for(int i=0; i<currentUser.getList_lmenu().size();i++){
			LeftMenu lm = currentUser.getList_lmenu().get(i);
			CatMenu cm = new CatMenu();
			cm.setMenu(lm.getMenu());
			List<Menu> cat_submenu = new ArrayList<Menu>();
			for(int j=0;j<lm.getSubmenu().size();j++){
				LeftMenu submenu = lm.getSubmenu().get(j);
				cat_submenu.add(submenu.getMenu());
			}
			cm.setSubmenu(cat_submenu);
			cat_listmenu.add(cm);
		}
		cat.put("listMenu",cat_listmenu);
		cat.put("curMenu",currentUser.getCurMenu().getMenu());
		cat.put("term",term);
		cat.put("subterm",subterm);
		cat.put("rootdir",Constants.ROOT_DIR);
		
		ObjectMapper mapper = new ObjectMapper();
		model.addAttribute("mapper", mapper);
		try {
			String p = mapper.writeValueAsString(cat);
			model.addAttribute("cat", mapper.writeValueAsString(cat));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
