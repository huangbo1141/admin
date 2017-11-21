package com.hgc.admin;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hgc.admin.database.model.Account;
import com.hgc.admin.database.model.AccountModel;
import com.hgc.admin.database.service.AccountService;

@Controller
@RequestMapping({"/","account"})
public class AccountController {

private AccountService accountService;
	
	@Autowired
	@Qualifier(value="currentUser")
	private AccountModel currentUser;

	@Autowired(required=true)
	@Qualifier(value="accountService")
	public void setAccountService(AccountService ps){
		this.accountService = ps;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(){
		
		
		return "redirect:account/login";
	}
	
	@RequestMapping(value="login",method = RequestMethod.GET)
	public String login(Model model,HttpSession session,HttpServletRequest request){
		
				
		Account acc = checkCookie(request);
		if(acc == null){
			model.addAttribute("account", new Account());
			return "common/index";
		}else{
			AccountModel accModel = new AccountModel();
			if(accModel.login(acc.getUsername(), acc.getPassword(),this.accountService)){
				session.setAttribute("username", acc.getUsername());
				currentUser.setUsername(acc.getUsername());
				currentUser.setPassword(acc.getPassword());
				return "redirect:/home";
			}else{
				model.addAttribute("error", "Account's Invalid");
				return "common/index";
			}
		}
	}
	
	@RequestMapping(value="login",method = RequestMethod.POST)
	public String login(@ModelAttribute(value = "account") Account account, 
			Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response){
		
		AccountModel accModel = new AccountModel();
		if(accModel.login(account.getUsername(), account.getPassword(),this.accountService)){
			session.setAttribute("username", account.getUsername());
			if(request.getParameter("remember")!=null){
				Cookie ckUsername = new Cookie("username",account.getUsername());
				ckUsername.setMaxAge(3600);
				response.addCookie(ckUsername);
				Cookie ckPassword = new Cookie("password",account.getPassword());
				ckPassword.setMaxAge(3600);
				response.addCookie(ckPassword);
			}
			currentUser.setUsername(account.getUsername());
			currentUser.setPassword(account.getPassword());
			return "redirect:/home";
		}else{
			model.addAttribute("error", "Account's Invalid");
			return "common/index";
		}
	}
	@RequestMapping(value="logout",method = RequestMethod.GET)
	public String logout(HttpSession session,HttpServletRequest request,HttpServletResponse response){
		session.removeAttribute("username");
		for(Cookie ck:request.getCookies()){
			if(ck.getName().equalsIgnoreCase("username")){
				ck.setMaxAge(0);
				response.addCookie(ck);
			}
			if(ck.getName().equalsIgnoreCase("password")){
				ck.setMaxAge(0);
				response.addCookie(ck);
			}
		}
		return "redirect:login";
	}
	
	public Account checkCookie(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		Account ac = null;
		String username = "",password = "";
		for(Cookie ck: cookies){
			if(ck.getName().equalsIgnoreCase("username"))
				username = ck.getValue();
			if(ck.getName().equalsIgnoreCase("password"))
				password = ck.getValue();
		}
		if(!username.isEmpty() && !password.isEmpty()){
			ac = new Account(username,password);
		}
		return ac;
	}
}






















