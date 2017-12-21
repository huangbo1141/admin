package com.hgc.admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgc.admin.constants.Constants;
import com.hgc.admin.database.model.Menu;
import com.hgc.admin.model.BackendRequest;
import com.hgc.admin.model.RestRequest;
import com.hgc.admin.utils.AccountHelper;
import com.hgc.admin.utils.AppApiHelper;
import com.hgc.admin.utils.BackendApiHelper;

@RestController
@RequestMapping("webservice/{v}/{term}")
public class ApiAppController extends BaseApiController {

	@Resource
	public AppApiHelper appApiHelper;
	private static final Logger logger = LoggerFactory.getLogger(ApiAppController.class);
	
	public Object filterOutput(String version,String term,boolean isSuccess,Object nonsafe_model,Object message) {
		Object ret = new HashMap<String,Object>();
		HashMap<String,Object> temp = new HashMap<String,Object>();
		temp.put("response", 400);
		ret = temp;
		try {
			temp = new HashMap<String,Object>();
			if(isSuccess){
				temp.put("response", 200);
			}else{
				temp.put("response", 400);
			}
			if(nonsafe_model!=null){
				temp.put("model",nonsafe_model);	
			}
			if(message!=null){
				temp.put("message",message);	
			}
			
			ret = temp;
		} catch (Exception ex) {
		}
		return ret;
	}
	@RequestMapping(value = Constants.ACTION_APP_FORMPOST, method = RequestMethod.POST)
	public @ResponseBody Object formPostObject(@PathVariable("term") String term
			, @PathVariable("v") String v,
			@RequestBody Object p) {
		// p format [Model] or {Model}
		// currently target {Model}
		logger.info("Start formPostObject.");
		ObjectMapper mapper = new ObjectMapper();
		String json_string;
		HashMap<String, Object> ret = new HashMap<String, Object>();
		ret.put("response", 400);
		try {
			json_string = mapper.writeValueAsString(p);
			RestRequest apiRequest = mapper.readValue(json_string, RestRequest.class);
			Object processed_obj = this.processRestRequest(apiRequest);
			if(processed_obj!=null){
				if(processed_obj.getClass().equals((new HashMap<String,Object>()).getClass())){
					HashMap<String,Object> pp = (HashMap<String,Object>)processed_obj;
					Iterator it = pp.entrySet().iterator();

					HashMap<String, Object> gen = new HashMap<String, Object>();
					while (it.hasNext()) {
						HashMap.Entry pair = (HashMap.Entry) it.next();
						String key = (String) pair.getKey();
						Object value = pair.getValue();
						ret.put(key, pair.getValue());
					}
				}
			}
			

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret.put("message", "Invalid Request Formst");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}
		
	
	public Object processRestRequest(RestRequest apiRequest) {
		Object ret = null;
		
		try{
			if(apiRequest.action !=null){
				String fname = apiRequest.action;
				Method method = appApiHelper.getClass().getMethod(fname, RestRequest.class);
				ret = method.invoke(appApiHelper, apiRequest);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ret;
	}
	
	
	
	@RequestMapping(value = Constants.ACTION_APP_UPLOAD, method = RequestMethod.POST)
	public @ResponseBody Object uploadObject(@PathVariable("v") String v
			,@RequestParam("file") MultipartFile[] files
			,@PathVariable("term") String term
			,HttpServletRequest request) {
		logger.info("Start createObject.");
		
		
		Map map = request.getParameterMap();
		String[] names = request.getParameterValues("name");
		
		
		String fileName = null;
    	String msg = "";
    	Object ret =null;
    	HashMap<String,Object> temp = new HashMap<String,Object>();
    	temp.put("response", 400);
    	ret = temp;
    	try{
    		List<String> file_names = new ArrayList<String>();
    		if (files != null && files.length >0 && files.length == names.length) {
        		for(int i =0 ;i< files.length; i++){
        			fileName = files[i].getOriginalFilename();
        			fileName = names[i];
	                byte[] bytes = files[i].getBytes();
	                File file = new File(Constants.UPLOAD_DIR + fileName);
	                BufferedOutputStream buffStream = 
	                        new BufferedOutputStream(new FileOutputStream(file));
	                buffStream.write(bytes);
	                buffStream.flush();
	                buffStream.close();
	                msg += "You have successfully uploaded " + fileName +"<br/>";
	                file_names.add(fileName);
        		}
        		msg = "You have successfully uploaded";
        		        		
        		// process files
        		Object proc_result = this.appApiHelper.processMultipartRequest(request, term, v,file_names);
        		if(proc_result!=null){
        			ret = proc_result;
        		}
            } else {
                return "Unable to upload. File is empty.";
            }
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	return ret;
	}
	
	
}
