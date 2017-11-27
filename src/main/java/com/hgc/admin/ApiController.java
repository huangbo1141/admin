package com.hgc.admin;

import java.io.IOException;
import java.util.HashMap;
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
import com.hgc.admin.database.model.Menu;
import com.hgc.admin.model.ApiRequest;

/**
 * Handles requests for the Object service.
 */
@RestController
@RequestMapping("{term}/{subterm}")
public class ApiController extends BaseApiController{
	
	public HashMap<String,Object> filterModel(String classname,String term,String subterm,HashMap<String,Object> nonsafe_model){
		HashMap<String,Object> ret = new HashMap<String,Object>();
		if(term.equals("menu")){
			if(subterm.equals("menu")){
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
			}
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
	
}
