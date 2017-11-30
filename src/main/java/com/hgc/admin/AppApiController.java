package com.hgc.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.hgc.admin.model.ApiRequest;
import com.hgc.admin.utils.AccountHelper;

@RestController
@RequestMapping("webservice/{v}")
public class AppApiController extends BaseApiController {

	private static final Logger logger = LoggerFactory.getLogger(AppApiController.class);

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
			t = this.userHelper.parseCheck(term, subterm, filtered_model);
			ret = this.filterOutput(term, subterm, t);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	

	public Object filterOutput(String term, String subterm, Object nonsafe_model) {
		Object ret = new Object();
		HashMap<Integer, Menu> map_menu = AccountHelper.getAllMenu(userHelper);
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
