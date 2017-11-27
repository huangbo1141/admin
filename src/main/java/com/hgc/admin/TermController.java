package com.hgc.admin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("{term}")
public class TermController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(TermController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(@PathVariable("term") String term,Model model){
		
//		HashMap<String,Object> chkAll = checkAll(currentUser,term); 
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
//			System.out.println("current User " + currentUser.getUsername());
//			model.addAttribute("pageName", "Home");
//			model.addAttribute("pageID", "1");
//			model.addAttribute("pageTerm", "menu");
			
			return "redirect:/menu/menu";
		}else{
			return "redirect:/account/login";
		}
		
	}
	
}
