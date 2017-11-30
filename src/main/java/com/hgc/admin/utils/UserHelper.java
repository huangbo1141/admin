package com.hgc.admin.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgc.admin.database.model.*;

@Component
public class UserHelper extends BaseHelper {
	
	@Override
	public boolean fakeLogin() {
		return false;
	}
	
	
	public HashMap<String, Class<?>> getModelClasses() {

		HashMap<String, Class<?>> hash = new HashMap<String, Class<?>>();
		HashMap<Integer, Menu> map_menu = AccountHelper.getAllMenu(this);
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

		hash.put(menu + "_" + menu_smenu, Menu.class);
		hash.put(menu + "_" + menu_role, AdminRole.class);
		hash.put(menu + "_" + menu_adminuser, AdminUser.class);
		hash.put(workstation + "_" + workstation, Station.class);
		hash.put(userguanli + "_" + userguanli, User.class);
		hash.put(password + "_" + password, User.class);
		hash.put(ct + "_" + ct, Ct.class);
		hash.put(tt + "_" + tt, Tt.class);
		hash.put(faultlib + "_" + faultlib, ErrorType.class);
		hash.put(workorder + "_" + workorder, Order.class);
		hash.put(report + "_" + report, Report.class);
		hash.put(announce + "_" + announce, Announce.class);
		return hash;
	}

	public HashMap<String, Object> getServiceInstances() {

		HashMap<String, Object> methods = new HashMap<String, Object>();
		HashMap<Integer, Menu> map_menu = AccountHelper.getAllMenu(this);
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
		
		methods.put(menu + "_" + menu_smenu, this.menuService);
		methods.put(menu + "_" + menu_role, this.adminRoleService);
		methods.put(menu + "_" + menu_adminuser, this.adminUserService);
		methods.put(workstation + "_" + workstation, this.stationService);
		methods.put(userguanli + "_" + userguanli, this.userService);
		methods.put(password + "_" + password, this.userService);
		methods.put(ct + "_" + ct, this.ctService);
		methods.put(tt + "_" + tt, this.ttService);
		methods.put(faultlib + "_" + faultlib, this.errorTypeService);
		methods.put(workorder + "_" + workorder, this.orderService);
		methods.put(report + "_" + report, this.reportService);
		methods.put(announce + "_" + announce, this.announceService);
		return methods;
	}

	public Object filterObject(Object param, Class<?> ModelT) {
		Object obj = null;
		HashMap<String, Object> pp = (HashMap<String, Object>) param;
		Field[] fields = ModelT.getDeclaredFields();
		List<String> allowed_names = new ArrayList<String>();
		for (int i = 0; i < fields.length; i++) {
			String e = fields[i].getName();
			allowed_names.add(e);
		}
		Iterator it = pp.entrySet().iterator();

		HashMap<String, Object> gen = new HashMap<String, Object>();
		while (it.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry) it.next();
			String key = (String) pair.getKey();
			if (allowed_names.contains(key)) {
				// ok
				Object val = pair.getValue();
				if (val != null) {
					gen.put(key, val);
				}
			}
		}
		return gen;
	}

	public Object modelNew(Class<?> ModelT, Object service, Object non_safe, Integer mode) {
		Object ret = "";
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		try {
			Object param = filterObject(non_safe, ModelT);
			String json_string = mapper.writeValueAsString(param);
			Object model = mapper.readValue(json_string, ModelT);

			switch (mode) {
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
					BigInteger kkk = ((BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult());
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
				Class<?> ServiceClass = service.getClass();
				// Method[] allMethods = ServiceClass.getDeclaredMethods();
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
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	public Object parseNew(String term, String subterm, Object non_safe) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		HashMap<String, Class<?>> hash = this.getModelClasses();

		HashMap<String, Object> methods = this.getServiceInstances();
		Object ret = "";
		String key = term + "_" + subterm;
		if (hash.containsKey(key)) {
			Class<?> ModelT = hash.get(key);
			Object service = methods.get(key);
			ret = this.modelNew(ModelT, service, non_safe, 1);
		}
		return ret;
	}

	public Object modelDelete(Class<?> ModelT, Object service, Object non_safe, Integer mode) {
		Object ret = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			
			Object param = filterObject(non_safe, ModelT);
			String json_string = mapper.writeValueAsString(param);
			Object model = mapper.readValue(json_string, ModelT);
			
			String method_name = "getId";
			Method method = ModelT.getMethod(method_name);
			int id = (Integer) method.invoke(model);
			
			Class<?> ServiceClass = service.getClass();
			method_name = "remove" + model.getClass().getSimpleName();
			method = ServiceClass.getMethod(method_name, Integer.class);
			method.invoke(service, id);
			ret = model;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	public Object parseDelete(String term, String subterm, Object non_safe) {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		HashMap<String, Class<?>> hash = this.getModelClasses();

		HashMap<String, Object> methods = this.getServiceInstances();
		Object ret = "";
		String key = term + "_" + subterm;
		if (hash.containsKey(key)) {
			Class<?> ModelT = hash.get(key);
			Object service = methods.get(key);
			ret = this.modelDelete(ModelT, service, non_safe, 1);
		}
		return ret;
	}

	public Object modelModify(Class<?> ModelT, Object service, Object non_safe, Integer mode) {
		Object ret = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			
			Object param = filterObject(non_safe, ModelT);
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
		} catch (Exception ex) {
			ex.printStackTrace();
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
				Object service = methods.get(key);
				ret = this.modelModify(ModelT, service, non_safe, 1);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return ret;
	}
	public Object parseCheck(String term, String subterm, Object non_safe) {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		HashMap<String, Class<?>> hash = this.getModelClasses();
		HashMap<String, Object> methods = this.getServiceInstances();
		Object ret = "";
		try {
			String key = term + "_" + subterm;
			if (hash.containsKey(key)) {
				Class<?> ModelT = hash.get(key);
				Object service = methods.get(key);
				ret = this.modelCheck(ModelT, service, non_safe, 1);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return ret;
	}
	public HashMap<Integer,Object> getMapDanPerLineID(){
		HashMap<Integer,Object> hash = new HashMap<Integer,Object>();
		HashMap<Integer,Line> map_line = lineService.mapLines();
		for(Dan dan:danService.listDans()){
			if(hash.containsKey(dan.getLine())){
				List<Dan> list = (List<Dan>) hash.get(dan.getLine());
				list.add(dan);
			}else{
				List<Dan> list = new ArrayList<Dan>();
				list.add(dan);
				hash.put(dan.getLine(), list);
			}
		}
		return hash;
	}
	
	public UserRole getRole(UserType userType){
		String tr_name = USERTYPE_NAMES[userType.ordinal()];
		String sql = "SELECT `id`,`name`,`tr_name`,`deleted`,`create_datetime`,`modify_datetime` FROM `tbl_user_role` "
				+" where tr_name='"+tr_name+"'";	
		String[] ids = {"id","name","tr_name","deleted","create_datetime","modify_datetime"};
		List<UserRole> list = this.userRoleService.queryUserRole(sql, ids);
		if(list!=null&& list.size()>0){
			return list.get(0);
		}
		return null;
	}
	public User getTypedUser(User maker,UserType userType){
		Integer dan = maker.getDan();
		UserRole ur = getRole(userType);
		String sql = "SELECT `id`,`name`,`serial`,`type`,`dan`,`deleted`,`create_datetime`,`modify_datetime` FROM `tbl_user`"
				+" where dan="+dan+" and type = " + ur.getId();
		String[] ids = {"id","name","serial","type","dan","deleted","create_datetime","modify_datetime"};
		List<User> list = this.userService.queryUser(sql, ids);
		if(list!=null&& list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public long modelCheck(Class<?> ModelT, Object service, Object non_safe, Integer mode) {
		long ret = 0;
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			
//			Object param = filterObject(non_safe, ModelT);
//			String json_string = mapper.writeValueAsString(param);
//			Object model = mapper.readValue(json_string, ModelT);

			HashMap<String, Object> whereFields = (HashMap<String, Object>)non_safe;
			
			String SQL_QUERY = BaseHelper.getCountSQL(BaseHelper.getTableName(ModelT.getSimpleName()), whereFields);

			SessionFactory sessionFactory = this.transactionManager.getSessionFactory();

			Session session = sessionFactory.openSession();
			try {

				List<Object> temp = session.createSQLQuery(SQL_QUERY).list();
				if(temp.size()>0){
					BigInteger ob = (BigInteger) temp.get(0);
					ret = ob.longValue();
				}
				
				session.close();

			} catch (Exception e) {
				session.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}
}
