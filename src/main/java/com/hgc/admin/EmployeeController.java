package com.hgc.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hgc.admin.database.model.Person;
import com.hgc.admin.database.service.PersonService;

/**
 * Handles requests for the Person service.
 */
@Controller
public class EmployeeController {
	
	private PersonService personService;
	
	@Autowired(required=true)
	@Qualifier(value="personService")
	public void setPersonService(PersonService ps){
		this.personService = ps;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	
	
	
	@RequestMapping(value = Constants.DUMMY_EMP, method = RequestMethod.GET)
	public @ResponseBody Person getDummyPerson() {
		logger.info("Start getDummyPerson");
		Person emp = new Person();
//		emp.setId(9999);
//		emp.setName("Dummy");
//		empData.put(9999, emp);
		return emp;
	}
	
	@RequestMapping(value = Constants.GET_EMP, method = RequestMethod.GET)
	public @ResponseBody Person getPerson(@PathVariable("id") int empId) {
		logger.info("Start getPerson. ID="+empId);
		
		return this.personService.getPersonById(empId);
	}
	
	@RequestMapping(value = Constants.GET_ALL_EMP, method = RequestMethod.GET)
	public @ResponseBody List<Person> getAllPersons() {
		logger.info("Start getAllPersons.");
		return this.personService.listPersons();
		
	}
	
	@RequestMapping(value = Constants.CREATE_EMP, method = RequestMethod.POST)
	public @ResponseBody Person createPerson(@RequestBody Person p) {
		logger.info("Start createPerson.");
		Integer pid = 0;
		if(p.getId() == 0){
			//new person, add it
			pid = this.personService.addPerson(p);
		}else{
			//existing person, call update
			this.personService.updatePerson(p);
			pid = p.getId();
		}
		p.setId(pid);
		return p;
		
	}
	
	@RequestMapping(value = Constants.DELETE_EMP, method = RequestMethod.POST)
	public @ResponseBody Map<String,String> deletePerson(@PathVariable("id") int empId) {
		logger.info("Start deletePerson.");
		Map<String,String> map = new HashMap();
		try{
			this.personService.removePerson(empId);
			map.put("response","200");
		}catch(Exception ex){
			map.put("response","300");
		}
		
		return map;
		
	}
	
}
