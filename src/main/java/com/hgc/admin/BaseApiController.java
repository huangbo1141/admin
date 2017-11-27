package com.hgc.admin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgc.admin.database.model.*;
import com.hgc.admin.database.service.AdminUserService;
import com.hgc.admin.database.service.AdminRoleService;
import com.hgc.admin.database.service.CtService;
import com.hgc.admin.database.service.DanService;
import com.hgc.admin.database.service.LineService;
import com.hgc.admin.database.service.MenuActionService;
import com.hgc.admin.database.service.MenuService;
import com.hgc.admin.database.service.PersonService;
import com.hgc.admin.database.service.StationService;
import com.hgc.admin.database.service.TimeTypeService;
import com.hgc.admin.database.service.TtService;
import com.hgc.admin.database.service.UserRoleService;
import com.hgc.admin.database.service.UserService;
import com.hgc.admin.utils.BaseHelper;
import java.math.BigInteger;

public class BaseApiController {

	@Autowired(required = true)
	@Qualifier(value = "transactionManager")
	public HibernateTransactionManager transactionManager;

	@Resource
	public BaseHelper baseHelper;

	@Autowired(required = true)
	@Qualifier(value = "personService")
	public PersonService personService;

	@Autowired(required = true)
	@Qualifier(value = "adminUserService")
	public AdminUserService adminUserService;

	@Autowired(required = true)
	@Qualifier(value = "adminRoleService")
	public AdminRoleService adminRoleService;

	@Autowired(required = true)
	@Qualifier(value = "ctService")
	public CtService ctService;

	@Autowired(required = true)
	@Qualifier(value = "danService")
	public DanService danService;

	@Autowired(required = true)
	@Qualifier(value = "lineService")
	public LineService lineService;

	@Autowired(required = true)
	@Qualifier(value = "menuService")
	public MenuService menuService;

	@Autowired(required = true)
	@Qualifier(value = "menuActionService")
	public MenuActionService menuActionService;

	@Autowired(required = true)
	@Qualifier(value = "stationService")
	public StationService stationService;

	@Autowired(required = true)
	@Qualifier(value = "timeTypeService")
	public TimeTypeService timeTypeService;

	@Autowired(required = true)
	@Qualifier(value = "ttService")
	public TtService ttService;

	@Autowired(required = true)
	@Qualifier(value = "userService")
	public UserService userService;

	@Autowired(required = true)
	@Qualifier(value = "userRoleService")
	public UserRoleService userRoleService;

	public HashMap<String, Class<?>> getModelClasses() {
		HashMap<String, Class<?>> hash = new HashMap<String, Class<?>>();
		hash.put("menu_menu", Menu.class);
		hash.put("menu_role", AdminRole.class);
		hash.put("menu_adminuser", AdminUser.class);
		return hash;
	}

	public HashMap<String, Object> getServiceInstances() {
		HashMap<String, Object> methods = new HashMap<String, Object>();
		methods.put("menu_menu", this.menuService);
		methods.put("menu_role", this.adminRoleService);
		methods.put("menu_adminuser", this.adminUserService);
		return methods;
	}
	public Object filterObject(Object param,Class<?> ModelT){
		Object obj = null;
		HashMap<String,Object> pp =(HashMap<String,Object>) param;
		Field[] fields = ModelT.getDeclaredFields();
		List<String> allowed_names = new ArrayList<String>();
		for(int i=0;i<fields.length;i++){
			String e = fields[i].getName();
			allowed_names.add(e);
		}
		Iterator it = pp.entrySet().iterator();
		
		HashMap<String,Object> gen = new HashMap<String,Object>();
		while (it.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry) it.next();
			String key = (String) pair.getKey();
			if(allowed_names.contains(key)){
				// ok
				Object val = pair.getValue();
				if(val!=null){
					gen.put(key, val);
				}
			}
		}
		return gen;
	}
	
	public Object parseNew(String term, String subterm, Object non_safe) {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		HashMap<String, Class<?>> hash = this.getModelClasses();

		HashMap<String, Object> methods = this.getServiceInstances();
		Object ret = "";
		try {
			
			String key = term + "_" + subterm;
			if (hash.containsKey(key)) {
				Class<?> ModelT = hash.get(key);
				Object param = filterObject(non_safe,ModelT);
				String json_string = mapper.writeValueAsString(param);
				
				Object model = mapper.readValue(json_string, ModelT);

				Object service = methods.get(key);
				Class<?> ServiceClass = service.getClass();
				// Method[] allMethods = ServiceClass.getDeclaredMethods();

				switch (1) {
				case 1: {
					List<String> exceptions = new ArrayList<String>();
					exceptions.add("id");
					String SQL_QUERY = BaseHelper.getInsertSql(model, BaseHelper.getTableName(ModelT.getSimpleName()),
							exceptions);
					SessionFactory sessionFactory = this.transactionManager.getSessionFactory();
					Session session = sessionFactory.openSession();
					Transaction t = session.beginTransaction();
					try {
						int rowsAffect = session.createSQLQuery(SQL_QUERY).executeUpdate();
						BigInteger kkk = ((BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()")
								.uniqueResult());
						int id = kkk.intValue();
						t.commit();
						session.close();

						String method_name = "setId";
						Method method = ModelT.getMethod(method_name, Integer.class);
						method.invoke(model, id);
					} catch (Exception e) {
						t.rollback();
						session.close();
					}

					break;
				}
				case 0: {
					// use standard function
					String method_name = "add" + model.getClass().getSimpleName();
					// System.out.println(menu.toString());
					// System.out.println(menu.getClass().getSimpleName());
					// System.out.println(menu.getClass().getTypeName());

					Method method = ServiceClass.getMethod(method_name, ModelT);
					int id = (Integer) method.invoke(service, model);

					method_name = "setId";
					method = ModelT.getMethod(method_name, Integer.class);
					method.invoke(model, id);
					break;
				}
				default:
					break;
				}

				ret = model;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return ret;
	}

	public Object parseDelete(String term, String subterm, Object param) {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		HashMap<String, Class<?>> hash = this.getModelClasses();

		HashMap<String, Object> methods = this.getServiceInstances();
		Object ret = "";
		try {
			String json_string = mapper.writeValueAsString(param);
			String key = term + "_" + subterm;
			if (hash.containsKey(key)) {
				Class<?> ModelT = hash.get(key);
				Object model = mapper.readValue(json_string, ModelT);
				String method_name = "getId";
				Method method = ModelT.getMethod(method_name);
				int id = (Integer) method.invoke(model);

				Object service = methods.get(key);
				Class<?> ServiceClass = service.getClass();

				method_name = "remove" + model.getClass().getSimpleName();
				method = ServiceClass.getMethod(method_name, Integer.class);
				method.invoke(service, id);
				ret = model;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return ret;
	}

	public Object parseModify(String term, String subterm, Object non_safe) {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		HashMap<String, Class<?>> hash = this.getModelClasses();

		HashMap<String, Object> methods = this.getServiceInstances();
		Object ret = "";
		try {
			
			String key = term + "_" + subterm;
			if (hash.containsKey(key)) {
				Class<?> ModelT = hash.get(key);
				Object param = this.filterObject(non_safe, ModelT);
				String json_string = mapper.writeValueAsString(param);
				Object model = mapper.readValue(json_string, ModelT);
				String method_name = "getId";
				Method method = ModelT.getMethod(method_name);
				int id = (Integer) method.invoke(model);

				HashMap<String, Object> whereFields = new HashMap<String, Object>();
				whereFields.put("id", id);
				String SQL_QUERY = BaseHelper.getModifySQL(model, BaseHelper.getTableName(ModelT.getSimpleName()),
						whereFields);

				SessionFactory sessionFactory = this.transactionManager.getSessionFactory();

				Session session = sessionFactory.openSession();
				Transaction t = session.beginTransaction();
				try {

					int rowsAffect = session.createSQLQuery(SQL_QUERY).executeUpdate();

					t.commit();
					session.close();

				} catch (Exception e) {
					t.rollback();
					session.close();
				}

				ret = model;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return ret;
	}

}
