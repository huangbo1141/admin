package com.hgc.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgc.admin.constants.Constants;
import com.hgc.admin.database.model.*;
import com.hgc.admin.model.ApiRequest;
import com.hgc.admin.model.Role;
import com.hgc.admin.utils.AccountHelper;

/**
 * Handles requests for the Object service.
 */
@RestController
@RequestMapping("{term}/{subterm}")
public class BackendPanelApiController extends BaseApiController {

	public HashMap<String, Object> filterModel(ApiRequest apiRequest, String term, String subterm,
			HashMap<String, Object> nonsafe_model) {
		HashMap<String, Object> ret = new HashMap<String, Object>();
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
				if (subterm.equals(menu_smenu)) {
					if (nonsafe_model.containsKey("menu_level")) {
						// parent should be same
						Integer parent = Integer.valueOf(nonsafe_model.get("parent").toString());
						List<Menu> list = userHelper.menuService.listMenus();
						for (Menu item : list) {
							if (item.getId() == parent) {
								nonsafe_model.put("parent", item.getParent());
								break;
							}
						}
					}
				} else if (subterm.equals(menu_role)) {
					HashMap<Integer, Role> map_ac = new HashMap<Integer, Role>();
					Iterator it = nonsafe_model.entrySet().iterator();
					while (it.hasNext()) {
						HashMap.Entry pair = (HashMap.Entry) it.next();
						String key = (String) pair.getKey();
						Object value = pair.getValue();
						String[] pieces = key.split("_");

						if (pieces.length >= 1) {
							String path1 = pieces[0];
							if (path1.equals("lm")) {
								Integer mid = Integer.parseInt(pieces[1]);
								Role role = new Role();
								role.setMid(mid);
								map_ac.put(mid, role);
							}
						}
					}

					it = nonsafe_model.entrySet().iterator();
					while (it.hasNext()) {
						HashMap.Entry pair = (HashMap.Entry) it.next();
						String key = (String) pair.getKey();
						Object value = pair.getValue();
						String[] pieces = key.split("_");
						if (pieces.length >= 2) {
							String path1 = pieces[0];
							if (path1.equals("role")) {
								Integer mid = Integer.parseInt(pieces[1]);
								Integer roleid = Integer.parseInt(pieces[2]);
								if (map_ac.containsKey(mid)) {
									// checked menu
									Role role = map_ac.get(mid);
									role.getActions().add(roleid);
								}
							}
						}
					}
					List<Role> list = new ArrayList<Role>(map_ac.values());
					ObjectMapper mapper = new ObjectMapper();
					String json_roles = "";
					try {
						json_roles = mapper.writeValueAsString(list);
						if(list.size()>0){
							nonsafe_model.put("roles", json_roles);	
						}else{
							nonsafe_model.put("roles", "0");
						}
						
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}else if(subterm.equals(menu_adminuser)){
					if(apiRequest.classname.equals("ac_new")){
//						String[] fields = {"username","password"};
//						for(String field:fields){
//							if(!nonsafe_model.containsKey(field)){
//								
//							}
//						}
					}
				}
			} else if (term.equals(announce)) {
				if (subterm.equals(announce)) {
					// no need
				}
			}else if (term.equals(ct)) {
				if (subterm.equals(ct)) {
					// no need
				}
			}
		} catch (Exception ex) {

		}

		DateTime dt = new DateTime();
		//String a = dt.toString();
		//String b = dt.toString("dd:MM:yy");
		//String c = dt.toString("EEE", Locale.CHINA);
		org.joda.time.format.DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		String d = dt.toString(fmt);

		nonsafe_model.put("modify_datetime", d);
		if (apiRequest.classname.equals("ac_new")) {
			nonsafe_model.put("create_datetime", d);
		}

		if (apiRequest.classname.equals("ac_enable")) {
			nonsafe_model.put("deleted", "0");
		}

		if (apiRequest.classname.equals("ac_disable")) {
			nonsafe_model.put("deleted", "1");
		}

		ret.put("model", nonsafe_model);
		return ret;
	}

	private static final Logger logger = LoggerFactory.getLogger(BackendPanelApiController.class);

	
	
	@RequestMapping(value = Constants.ACTION_WEB_NEW, method = RequestMethod.POST)
	public @ResponseBody Object createObject(@PathVariable("term") String term, @PathVariable("subterm") String subterm,
			@RequestBody Object p) {
		logger.info("Start createObject.");
		ObjectMapper mapper = new ObjectMapper();
		String json_string;
		HashMap<String, Object> ret = new HashMap<String, Object>();
		ret.put("response", 400);
		try {
			json_string = mapper.writeValueAsString(p);
			ApiRequest apiRequest = mapper.readValue(json_string, ApiRequest.class);
			HashMap<String, Object> filtered_model = filterModel(apiRequest, term, subterm, (HashMap<String, Object>)apiRequest.model);
			// menu_level
			if(apiRequest.modeltype==null||apiRequest.modeltype.length()==0){
				Object t = this.userHelper.parseNew(term, subterm, filtered_model.get("model"));
				ret.put("model", t);
			}else{
				Class<?> ModelT = this.getModelClasses(apiRequest.modeltype);
				Object service = this.getServiceInstances(apiRequest.modeltype);
				Object t = this.userHelper.modelNew(ModelT, service, filtered_model.get("model"), 1);
				ret.put("model", t);
			}			
			
			ret.put("response", 200);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret.put("error_message", e.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret.put("error_message", e.toString());
		}

		return ret;
	}

	@RequestMapping(value = Constants.ACTION_WEB_DELETE, method = RequestMethod.POST)
	public @ResponseBody Object deleteObject(@PathVariable("term") String term, @PathVariable("subterm") String subterm,
			@RequestBody Object p) {
		// p format [Model] or {Model}
		// currently target {Model}
		logger.info("Start deleteObject.");
		ObjectMapper mapper = new ObjectMapper();
		String json_string;
		HashMap<String, Object> ret = new HashMap<String, Object>();
		ret.put("response", 400);
		try {
			json_string = mapper.writeValueAsString(p);
			ApiRequest apiRequest = mapper.readValue(json_string, ApiRequest.class);
			HashMap<String, Object> filtered_model = filterModel(apiRequest, term, subterm, (HashMap<String, Object>)apiRequest.model);
			// menu_level
			if(apiRequest.modeltype==null||apiRequest.modeltype.length()==0){
				Object t = this.userHelper.parseDelete(term, subterm, filtered_model.get("model"));
				ret.put("model", t);
			}else{
				Class<?> ModelT = this.getModelClasses(apiRequest.modeltype);
				Object service = this.getServiceInstances(apiRequest.modeltype);
				Object t = this.userHelper.modelDelete(ModelT, service, filtered_model.get("model"), 1);
				ret.put("model", t);
			}	
			ret.put("response", 200);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}

	@RequestMapping(value = Constants.ACTION_WEB_MODIFY, method = RequestMethod.POST)
	public @ResponseBody Object modifyObject(@PathVariable("term") String term, @PathVariable("subterm") String subterm,
			@RequestBody Object p) {
		logger.info("Start createObject.");
		ObjectMapper mapper = new ObjectMapper();
		String json_string;
		HashMap<String, Object> ret = new HashMap<String, Object>();
		ret.put("response", 400);
		try {
			json_string = mapper.writeValueAsString(p);
			ApiRequest apiRequest = mapper.readValue(json_string, ApiRequest.class);
			HashMap<String, Object> filtered_model = filterModel(apiRequest, term, subterm, (HashMap<String, Object>)apiRequest.model);
			// menu_level
			if(apiRequest.modeltype==null||apiRequest.modeltype.length()==0){
				Object t = this.userHelper.parseModify(term, subterm, filtered_model.get("model"));
				ret.put("model", t);
			}else{
				Class<?> ModelT = this.getModelClasses(apiRequest.modeltype);
				Object service = this.getServiceInstances(apiRequest.modeltype);
				Object t = this.userHelper.modelModify(ModelT, service, filtered_model.get("model"), 1);
				ret.put("model", t);
			}
			ret.put("response", 200);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}

	@RequestMapping(value = Constants.ACTION_WEB_AUTH, method = RequestMethod.POST)
	public @ResponseBody Object authObject(@PathVariable("term") String term, @PathVariable("subterm") String subterm,
			@RequestBody Object p) {
		logger.info("Start createObject.");
		ObjectMapper mapper = new ObjectMapper();
		String json_string;
		HashMap<String, Object> ret = new HashMap<String, Object>();
		ret.put("response", 400);
		try {
			json_string = mapper.writeValueAsString(p);
			ApiRequest apiRequest = mapper.readValue(json_string, ApiRequest.class);
			HashMap<String, Object> filtered_model = filterModel(apiRequest, term, subterm, (HashMap<String, Object>)apiRequest.model);

			if(apiRequest.modeltype==null||apiRequest.modeltype.length()==0){
				Object t = this.userHelper.parseModify(term, subterm, filtered_model.get("model"));
				ret.put("model", t);
			}else{
				Class<?> ModelT = this.getModelClasses(apiRequest.modeltype);
				Object service = this.getServiceInstances(apiRequest.modeltype);
				Object t = this.userHelper.modelModify(ModelT, service, filtered_model.get("model"), 1);
				ret.put("model", t);
			}
			
			ret.put("response", 200);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}

	@RequestMapping(value = Constants.ACTION_WEB_ENABLE, method = RequestMethod.POST)
	public @ResponseBody Object enableObject(@PathVariable("term") String term, @PathVariable("subterm") String subterm,
			@RequestBody Object p) {
		// p format [Model] or {Model}
		// currently target {Model}
		logger.info("Start deleteObject.");
		ObjectMapper mapper = new ObjectMapper();
		String json_string;
		HashMap<String, Object> ret = new HashMap<String, Object>();
		ret.put("response", 400);
		try {
			json_string = mapper.writeValueAsString(p);
			ApiRequest apiRequest = mapper.readValue(json_string, ApiRequest.class);
			HashMap<String, Object> filtered_model = filterModel(apiRequest, term, subterm, (HashMap<String, Object>)apiRequest.model);
			// menu_level
			if(apiRequest.modeltype==null||apiRequest.modeltype.length()==0){
				Object t = this.userHelper.parseModify(term, subterm, filtered_model.get("model"));
				ret.put("model", t);
			}else{
				Class<?> ModelT = this.getModelClasses(apiRequest.modeltype);
				Object service = this.getServiceInstances(apiRequest.modeltype);
				Object t = this.userHelper.modelModify(ModelT, service, filtered_model.get("model"), 1);
				ret.put("model", t);
			}
			ret.put("response", 200);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}

	@RequestMapping(value = Constants.ACTION_WEB_DISABLE, method = RequestMethod.POST)
	public @ResponseBody Object disableObject(@PathVariable("term") String term,
			@PathVariable("subterm") String subterm, @RequestBody Object p) {
		// p format [Model] or {Model}
		// currently target {Model}
		logger.info("Start deleteObject.");
		ObjectMapper mapper = new ObjectMapper();
		String json_string;
		HashMap<String, Object> ret = new HashMap<String, Object>();
		ret.put("response", 400);
		try {
			json_string = mapper.writeValueAsString(p);
			ApiRequest apiRequest = mapper.readValue(json_string, ApiRequest.class);
			HashMap<String, Object> filtered_model = filterModel(apiRequest, term, subterm, (HashMap<String, Object>)apiRequest.model);
			// menu_level
			
			if(apiRequest.modeltype==null||apiRequest.modeltype.length()==0){
				Object t = this.userHelper.parseModify(term, subterm, filtered_model.get("model"));
				ret.put("model", t);
			}else{
				Class<?> ModelT = this.getModelClasses(apiRequest.modeltype);
				Object service = this.getServiceInstances(apiRequest.modeltype);
				Object t = this.userHelper.modelModify(ModelT, service, filtered_model.get("model"), 1);
				ret.put("model", t);
			}
			
			ret.put("response", 200);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}
	
	@RequestMapping(value = Constants.ACTION_WEB_CHECK, method = RequestMethod.POST)
	public @ResponseBody Object checkObject(@PathVariable("term") String term, @PathVariable("subterm") String subterm,
			@RequestBody Object p) {
		logger.info("Start createObject.");
		ObjectMapper mapper = new ObjectMapper();
		String json_string;
		Object ret = new Object();
		try {
			json_string = mapper.writeValueAsString(p);
			ApiRequest apiRequest = mapper.readValue(json_string, ApiRequest.class);
			//HashMap<String, Object> filtered_model = filterModel(apiRequest, term, subterm, (HashMap<String, Object>)apiRequest.model);
			// menu_level
			Object t=null;
			if(apiRequest.modeltype==null||apiRequest.modeltype.length()==0){
				t = this.userHelper.parseCheck(term, subterm, apiRequest.model);
			}else{
				Class<?> ModelT = this.getModelClasses(apiRequest.modeltype);
				Object service = this.getServiceInstances(apiRequest.modeltype);
				t = this.userHelper.modelModify(ModelT, service, apiRequest.model, 1);
			}

			ret = this.filterOutput(apiRequest, term, subterm, t);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public Object filterOutput(ApiRequest apiRequest, String term, String subterm,
			Object nonsafe_model) {
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
				if(subterm.equals(menu_adminuser)){
					ret = nonsafe_model;
				}
			} 
		} catch (Exception ex) {

		}
		return ret;
	}
}
