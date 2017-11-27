package com.hgc.admin;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hgc.admin.model.LeftMenu;

@Controller
@RequestMapping("{term}")
public class TermController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(TermController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(@PathVariable("term") String term,Model model){
		
		HashMap<String,Object> chkAll = checkAll(currentUser,term); 
//		String chkResult = chkAll.get("result").toString();
//		String url = chkAll.get("url").toString();
//		if(chkResult.equals("redirect")){
//			return url;
//		}else{
//			// no permission or other
//			if(!chkResult.equals("ok")){
//				return url;
//			}
//		}
//		System.out.println("current User " + currentUser.getUsername());
//		model.addAttribute("pageName", currentUser.curMenu.menu.getName());
//		model.addAttribute("pageID", currentUser.curMenu.menu.getId());
//		model.addAttribute("pageTerm", term);
//		model.addAttribute("currentUser", currentUser);
		
		if(term.toLowerCase().equals("home")){
			String url = "redirect:/menu/smenu";
			if(currentUser.getList_lmenu().size()>0){
				for(int i=0; i<currentUser.getList_lmenu().size();i++){
					LeftMenu lm = currentUser.getList_lmenu().get(i);
					if(lm.getSubmenu().size()>0){
						LeftMenu lm_s = lm.getSubmenu().get(0);
						
						url = "redirect:/"+lm.getMenu().getTerm()+"/"+lm_s.getMenu().getTerm();
					}
				}
			}
			
			return url;
		}else{
			return "redirect:/account/login";
		}
		
	}
	
}
