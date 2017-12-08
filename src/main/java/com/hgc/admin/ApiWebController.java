package com.hgc.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgc.admin.constants.Constants;
import com.hgc.admin.database.model.Menu;
import com.hgc.admin.model.BackendRequest;
import com.hgc.admin.utils.AccountHelper;
import com.hgc.admin.utils.BackendApiHelper;

import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("webapi/{v}/{term}/{subterm}")
public class ApiWebController extends BaseApiController {

	@Resource
	public BackendApiHelper backendApiHelper;
	
	private static final Logger logger = LoggerFactory.getLogger(ApiWebController.class);

	@RequestMapping(value = Constants.ACTION_WEB_CHECK, method = RequestMethod.POST)
	public @ResponseBody Object checkObject(@PathVariable("v") String version,@PathVariable("term") String term, @PathVariable("subterm") String subterm,
			@RequestBody MultiValueMap<String, String> formData) {
		logger.info("Start createObject.");
		ObjectMapper mapper = new ObjectMapper();
		String json_string;
		Object ret = new Object();
		try {
			HashMap<String, Object> filtered_model = new HashMap<String, Object>();
			Iterator<String> it = formData.keySet().iterator();
			while (it.hasNext()) {
				String theKey = (String) it.next();
				filtered_model.put(theKey, formData.getFirst(theKey));
			}
			Object t = null;
			t = this.backendApiHelper.parseCheck(term, subterm, filtered_model);
			ret = this.filterOutput(term, subterm, t);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	@RequestMapping(value = Constants.ACTION_WEB_REPORTD, method = RequestMethod.POST)
	public ModelAndView downloadObject(@PathVariable("v") String version,
			@PathVariable("term") String term,
			@PathVariable("subterm") String subterm,Model model,
			HttpServletRequest request){
		Map<String,String> revenueData = new HashMap<String,String>();
		revenueData.put("Jan-2010", "$100,000,000");
		revenueData.put("Feb-2010", "$110,000,000");
		revenueData.put("Mar-2010", "$130,000,000");
		revenueData.put("Apr-2010", "$140,000,000");
		revenueData.put("May-2010", "$200,000,000");
		
		if(term.equals("xxx")){
			return new ModelAndView("ExcelRevenueSummary","revenueData",revenueData);
		}
		
		return new ModelAndView("ExcelRevenueSummary","revenueData",revenueData);
	}
	
	@RequestMapping(value = "test", method = RequestMethod.POST)
	public String testObject(@PathVariable("v") String version,
			@PathVariable("term") String term,
			@PathVariable("subterm") String subterm,Model model,
			HttpServletRequest request){
		if(term.equals("xxx")){
			return "login";
		}
		
		return "test";
	}
	
	@RequestMapping(value = "cronjob", method = RequestMethod.GET)
	public String cronjobObject(@PathVariable("v") String version,
			@PathVariable("term") String term,
			@PathVariable("subterm") String subterm,Model model,
			HttpServletRequest request){
		if(subterm.endsWith("daily_report")){
			Object dailyReport = this.backendApiHelper.generate_daily_report();
			if(dailyReport!=null){
				return "OK";
			}
			return "dailyReport";
		}else if(subterm.endsWith("pushall")){
			HashMap<String,Object> map_msg = new HashMap<String,Object>();
			map_msg.put("title", "Test");
			map_msg.put("description", "message");
			this.backendApiHelper.push_all(map_msg);
			return "OK";
		}
		
		return "cronjob";
	}

	public Object filterOutput(String term, String subterm, Object nonsafe_model) {
		Object ret = new Object();
		HashMap<Integer, Menu> map_menu = AccountHelper.getAllMenu(backendApiHelper);
		try {
			String menu = map_menu.get(1).getTerm();
			String menu_smenu = map_menu.get(13).getTerm();
			String menu_role = map_menu.get(14).getTerm();
			String menu_adminuser = map_menu.get(15).getTerm();
			String line = map_menu.get(3).getTerm();
			String workstation = map_menu.get(4).getTerm();
			String userguanli = map_menu.get(5).getTerm();
			String password = map_menu.get(6).getTerm();
			String ct = map_menu.get(7).getTerm();
			String tt = map_menu.get(8).getTerm();
			String faultlib = map_menu.get(9).getTerm();
			String workorder = map_menu.get(10).getTerm();
			String report = map_menu.get(11).getTerm();
			String announce = map_menu.get(12).getTerm();
			if (term.equals(menu)) {
				if (subterm.equals(menu_adminuser)) {
					Long p = (Long) nonsafe_model;
					if (p > 0) {
						ret = "false";
					}else{
						ret = "true";
					}
				}
			}
		} catch (Exception ex) {

		}
		return ret;
	}
}
