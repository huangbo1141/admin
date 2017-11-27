package com.hgc.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgc.admin.constants.Constants;
import com.hgc.admin.database.model.AdminRole;
import com.hgc.admin.database.model.Menu;
import com.hgc.admin.model.Cat;
import com.hgc.admin.model.CatMenu;
import com.hgc.admin.model.LeftMenu;
import com.hgc.admin.utils.AccountHelper;

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
		HashMap<Integer, Menu> map_menu = this.currentUser.getMap_menu();
		try{
			String menu = map_menu.get(1).getTerm();
			String menu_smenu = map_menu.get(13).getTerm();
			String menu_role = map_menu.get(14).getTerm();
			if(term.equals(menu)){
				if(subterm.equals(menu_smenu)){
					// need to fetch all menu data.
					pageData.put("list_data", baseHelper.menuService.listMenus());
				}else if(subterm.equals(menu_role)){
					List<AdminRole> list_adminRole = baseHelper.adminRoleService.listAdminRoles();
					
					List<Object> list_data = new ArrayList<Object>();
					for(int i=0; i<list_adminRole.size(); i++){
						AdminRole ar = list_adminRole.get(i);
						List<LeftMenu> tlist = AccountHelper.parseRoles(ar,this.currentUser.getMap_menu());
						HashMap<Integer,LeftMenu> m = new HashMap<Integer,LeftMenu>();
						for(int j=0; j<tlist.size();j++){
							LeftMenu lm = tlist.get(j);
							m.put(lm.getMenu().getId(), lm);
							for(int k=0;k<lm.getSubmenu().size();k++){
								LeftMenu lm_s = lm.getSubmenu().get(k);
								m.put(lm_s.getMenu().getId(), lm_s);
							}
						}
						Iterator it = m.entrySet().iterator();
						while (it.hasNext()) {
							HashMap.Entry pair = (HashMap.Entry) it.next();
							Integer key = (Integer) pair.getKey();
							LeftMenu lm = (LeftMenu) pair.getValue();
							lm.setSubmenu(null);
						}
						HashMap<String,Object> item = new HashMap<String,Object>();
						
						item.put("model", ar);
						item.put("roles", m);
						list_data.add(item);
					}
					pageData.put("list_data", list_data);
					
					// get all menu
					List<Menu> list_menu = new ArrayList<Menu>(map_menu.values());
					HashMap<Integer, LeftMenu> t_menu = new HashMap<Integer, LeftMenu>();
					for(int i=0; i<list_menu.size();i++){
						Menu imenu = list_menu.get(i);
						if(imenu.getParent() == 0){
							// search parent first
							LeftMenu lm = new LeftMenu();
							lm.setMid(imenu.getId());
							try {
								ObjectMapper objectMapper = new ObjectMapper();
								List<Integer> acs = objectMapper.readValue(imenu.getAction(),
										new TypeReference<List<Integer>>() {
										});

								lm.setActions(acs);
							} catch (Exception e) {
								e.printStackTrace();
							}
							lm.setMenu(imenu);
							t_menu.put(imenu.getId(), lm);
						}
					}
					for(int i=0; i<list_menu.size();i++){
						Menu imenu = list_menu.get(i);
						if(imenu.getParent() != 0){
							// search sub menu
							if(t_menu.containsKey(imenu.getParent())){
								LeftMenu lm_parent = t_menu.get(imenu.getParent());
								LeftMenu lm = new LeftMenu();
								lm.setMid(imenu.getId());
								lm.setMenu(imenu);
								try {
									ObjectMapper objectMapper = new ObjectMapper();
									List<Integer> acs = objectMapper.readValue(imenu.getAction(),
											new TypeReference<List<Integer>>() {
											});

									lm.setActions(acs);
								} catch (Exception e) {
									e.printStackTrace();
								}
								lm_parent.getSubmenu().add(lm);
							}
						}
					}
					List<LeftMenu> list_lmenu = new ArrayList<LeftMenu>(t_menu.values());
					Collections.sort(list_lmenu, new Comparator<LeftMenu>() {
						@Override
						public int compare(LeftMenu lhs, LeftMenu rhs) {
							// -1 - less than, 1 - greater than, 0 - equal, all inversed for
							// descending
							return rhs.getMenu().getSort() > lhs.getMenu().getSort() ? -1 : (rhs.getMenu().getSort() < lhs.getMenu().getSort()) ? 1 : 0;
						}
					});
					pageData.put("list_lmenu", list_lmenu);
					
					
					
				}else if(subterm.equals("adminuser")){
					pageData.put("list_data", baseHelper.adminUserService.listAdminUsers());
				}
			}
		}catch(Exception ex){
			
		}
		
		model.addAttribute("pageData",pageData);
		
	}
	
}
