package com.hgc.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
import com.hgc.admin.database.model.AdminRole;
import com.hgc.admin.database.model.Menu;
import com.hgc.admin.model.ApiRequest;
import com.hgc.admin.model.Role;
import com.hgc.admin.utils.AccountHelper;

/**
 * Handles requests for the Object service.
 */
@RestController
@RequestMapping("{term}/{subterm}")
public class ApiController extends BaseApiController{
	
	public HashMap<String,Object> filterModel(String classname,String term,String subterm,HashMap<String,Object> nonsafe_model){
		HashMap<String,Object> ret = new HashMap<String,Object>();
		HashMap<Integer, Menu> map_menu = AccountHelper.getAllMenu(baseHelper);
		try{
			String menu = map_menu.get(1).getTerm();
			String menu_smenu = map_menu.get(13).getTerm();
			String menu_role = map_menu.get(14).getTerm();
			if(term.equals(menu)){
				if(subterm.equals(menu_smenu)){
					if(nonsafe_model.containsKey("menu_level")){
						// parent should be same
						Integer parent = Integer.valueOf(nonsafe_model.get("parent").toString());
						List<Menu> list = menuService.listMenus();
						for(Menu item:list){
							if(item.getId() == parent){
								nonsafe_model.put("parent", item.getParent());
								break;
							}
						}
					}
				}else if(subterm.equals(menu_role)){
					HashMap<Integer,Role> map_ac = new HashMap<Integer,Role>();
					Iterator it = nonsafe_model.entrySet().iterator();
					while (it.hasNext()) {
						HashMap.Entry pair = (HashMap.Entry) it.next();
						String key = (String) pair.getKey();
						Object value = pair.getValue();
						String[] pieces = key.split("_");
						
						if(pieces.length>=1){
							String path1 = pieces[0];
							if(path1.equals("lm")){
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
						if(pieces.length>=2){
							String path1 = pieces[0];
							if(path1.equals("role")){
								Integer mid = Integer.parseInt(pieces[1]);
								Integer roleid = Integer.parseInt(pieces[2]);
								if(map_ac.containsKey(mid)){
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
						nonsafe_model.put("roles", json_roles);
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}catch(Exception ex){
			
		}
		
		ret.put("model", nonsafe_model);
		return ret;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	@RequestMapping(value = Constants.ACTION_NEW, method = RequestMethod.POST)
	public @ResponseBody Object createObject(@PathVariable("term") String term,@PathVariable("subterm") String subterm,@RequestBody Object p) {
		logger.info("Start createObject.");
		ObjectMapper mapper = new ObjectMapper();
		String json_string;
		HashMap<String,Object> ret = new HashMap<String,Object>();
		ret.put("response", 400);
		try {
			json_string = mapper.writeValueAsString(p);
			ApiRequest apiRequest = mapper.readValue(json_string, ApiRequest.class);
			String classname = apiRequest.classname;
			HashMap<String,Object> nonsafe_model = (HashMap<String,Object>)apiRequest.model;
			HashMap<String,Object> nonsafe_model2 = filterModel(classname,term,subterm,nonsafe_model);
			//menu_level
			Object t = this.parseNew(term, subterm,nonsafe_model2.get("model"));
			
			ret.put("model", t);
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
	
	@RequestMapping(value = Constants.ACTION_DELETE, method = RequestMethod.POST)
	public @ResponseBody Object deleteObject(@PathVariable("term") String term,@PathVariable("subterm") String subterm,@RequestBody Object p) {
		// p format [Model] or {Model}
		// currently target {Model}
		logger.info("Start deleteObject.");
		ObjectMapper mapper = new ObjectMapper();
		String json_string;
		HashMap<String,Object> ret = new HashMap<String,Object>();
		ret.put("response", 400);
		try {
			json_string = mapper.writeValueAsString(p);
			ApiRequest apiRequest = mapper.readValue(json_string, ApiRequest.class);
			String classname = apiRequest.classname;
			HashMap<String,Object> nonsafe_model = (HashMap<String,Object>)apiRequest.model;
			HashMap<String,Object> nonsafe_model2 = filterModel(classname,term,subterm,nonsafe_model);
			//menu_level
			Object t = this.parseDelete(term, subterm,nonsafe_model2.get("model"));
			
			ret.put("model", t);
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
	
	@RequestMapping(value = Constants.ACTION_MODIFY, method = RequestMethod.POST)
	public @ResponseBody Object modifyObject(@PathVariable("term") String term,@PathVariable("subterm") String subterm,@RequestBody Object p) {
		logger.info("Start createObject.");
		ObjectMapper mapper = new ObjectMapper();
		String json_string;
		HashMap<String,Object> ret = new HashMap<String,Object>();
		ret.put("response", 400);
		try {
			json_string = mapper.writeValueAsString(p);
			ApiRequest apiRequest = mapper.readValue(json_string, ApiRequest.class);
			String classname = apiRequest.classname;
			HashMap<String,Object> nonsafe_model = (HashMap<String,Object>)apiRequest.model;
			HashMap<String,Object> nonsafe_model2 = filterModel(classname,term,subterm,nonsafe_model);
			//menu_level
			Object t = this.parseModify(term, subterm,nonsafe_model2.get("model"));
			
			ret.put("model", t);
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
	
	@RequestMapping(value = Constants.ACTION_AUTH, method = RequestMethod.POST)
	public @ResponseBody Object authObject(@PathVariable("term") String term,@PathVariable("subterm") String subterm,@RequestBody Object p) {
		logger.info("Start createObject.");
		ObjectMapper mapper = new ObjectMapper();
		String json_string;
		HashMap<String,Object> ret = new HashMap<String,Object>();
		ret.put("response", 400);
		try {
			json_string = mapper.writeValueAsString(p);
			ApiRequest apiRequest = mapper.readValue(json_string, ApiRequest.class);
			String classname = apiRequest.classname;
			HashMap<String,Object> nonsafe_model = (HashMap<String,Object>)apiRequest.model;
			HashMap<String,Object> nonsafe_model2 = filterModel(classname,term,subterm,nonsafe_model);	
			
			Object t = this.parseModify(term, subterm,nonsafe_model2.get("model"));
				
			
			ret.put("model", t);
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
}
