package com.hgc.admin.constants;

public class Constants {

	public static final String ROOT_DIR = "admin";
	public static final boolean daoLogger = true;
//	public static final String UPLOAD_DIR = "C:\\tc\\admin\\src\\main\\webapp\\resources\\uploads\\";
	public static final String UPLOAD_DIR = "C:\\xampp\\tomcat\\webapps\\admin\\resources\\uploads\\";
	
	public static final String DUMMY_EMP = "/rest/emp/dummy";
	public static final String GET_EMP = "/rest/emp/{id}";
	public static final String GET_ALL_EMP = "/rest/emps";
	public static final String CREATE_EMP = "/rest/emp/create";
	public static final String DELETE_EMP = "/rest/emp/delete/{id}";
	
	public static final String TERM_NEW = "ac_new";
	public static final String TERM_MODIFY = "ac_modify";
	public static final String TERM_DELETE = "ac_delete";
	public static final String TERM_AUTH = "ac_auth";
	public static final String TERM_ENABLE = "ac_enable";
	public static final String TERM_DISABLE = "ac_disable";
	public static final String TERM_SEARCH = "ac_search";
	public static final String TERM_GET = "{id}";
	public static final String TERM_DETAIL = "ac_detail";
	public static final String TERM_ALL = "ac_all";
	public static final String TERM_CHECK = "ac_check";
	public static final String TERM_REPORT = "report";
	
	public static final String TERM_UPLOAD = "ac_upload";
	public static final String TERM_FORMPOST = "formpost";
	
	public static final String ACTION_WEB_NEW = 	"/"+TERM_NEW;
	public static final String ACTION_WEB_MODIFY = 	"/"+TERM_MODIFY;
	public static final String ACTION_WEB_DELETE = 	"/"+TERM_DELETE;
	public static final String ACTION_WEB_AUTH = 	"/"+TERM_AUTH;
	public static final String ACTION_WEB_ENABLE = 	"/"+TERM_ENABLE;
	public static final String ACTION_WEB_DISABLE = "/"+TERM_DISABLE;
	public static final String ACTION_WEB_SEARCH = 	"/"+TERM_SEARCH;
	public static final String ACTION_WEB_GET = 	"/"+TERM_GET;
	public static final String ACTION_WEB_DETAIL = 	"/"+TERM_DETAIL;
	public static final String ACTION_WEB_ALL = 	"/"+TERM_ALL;
	
	public static final String ACTION_WEB_CHECK = 	"/"+TERM_CHECK;
	public static final String ACTION_WEB_REPORTD = 	"/"+TERM_REPORT;
	//public static final String ACTION_CHECK = 	"/";
	
	public static final String ACTION_APP_UPLOAD = 	"/"+TERM_UPLOAD;
	public static final String ACTION_APP_FORMPOST = 	"/"+TERM_FORMPOST;
	
	public static final String PUSH_APIKEY = 	"smGStwBYsGgCLfLlO9Dr8iT7";
	public static final String PUSH_SECRETKEY = 	"ABGrBzKTgfPF9QG39Q3tIyKC3d8wAGCH";
	
	public static final Integer USERTYPE_PRODUCTION = 1;
	public static final Integer USERTYPE_QUALITY = 2;
	public static final Integer USERTYPE_MAINTENANCE = 3;
	public static final Integer USERTYPE_SPECIALIST = 4;
	public static final Integer USERTYPE_MANAGER = 5;
}
