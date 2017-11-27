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

import com.hgc.admin.database.model.Person;
import com.hgc.admin.database.service.PersonService;
import com.hgc.admin.utils.AccountHelper;

@Controller
public class PersonController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	private PersonService personService;
	
	@Autowired(required=true)
	@Qualifier(value="personService")
	public void setPersonService(PersonService ps){
		this.personService = ps;
	}
	
	
	
	@RequestMapping(value = "/persons", method = RequestMethod.GET)
	public String listPersons(Model model) {
//		String login_redirect = checkAll(currentUser); 
//		if(login_redirect!=null){
//			return login_redirect;
//		}
//		String name = currentUser.getUsername();
//		String pass = currentUser.getPassword();
//		if(name!=null&&pass!=null){
//			logger.info("Session      name = "+name+"   pass "+pass);
//		}else{
//			logger.info("Session      nullnullnull");
//		}
		
		model.addAttribute("person", new Person());
		model.addAttribute("listPersons", this.personService.listPersons());
		return "person";
	}
	
	//For add and update person both
	@RequestMapping(value= "/person/add", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("person") Person p){
		
		if(p.getId() == 0){
			//new person, add it
			this.personService.addPerson(p);
		}else{
			//existing person, call update
			this.personService.updatePerson(p);
		}
		
		return "redirect:/persons";
		
	}
	
	@RequestMapping("/remove/{id}")
    public String removePerson(@PathVariable("id") int id){
		
        this.personService.removePerson(id);
        return "redirect:/persons";
    }
 
    @RequestMapping("/edit/{id}")
    public String editPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", this.personService.getPersonById(id));
        model.addAttribute("listPersons", this.personService.listPersons());
        return "person";
    }
	
}
