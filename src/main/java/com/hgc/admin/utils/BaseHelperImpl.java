package com.hgc.admin.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgc.admin.database.model.Dan;
import com.hgc.admin.database.model.Line;
import com.hgc.admin.database.model.User;
import com.hgc.admin.database.model.UserRole;
import com.hgc.admin.database.service.*;
import com.hgc.admin.utils.BaseHelperImpl.UserType;

@Component
@Primary
public class BaseHelperImpl implements BaseHelper {

	@Autowired(required = true)
	@Qualifier(value = "transactionManager")
	public HibernateTransactionManager transactionManager;
	
	@Autowired(required = true)
	@Qualifier(value = "adminRoleService")
	public AdminRoleService adminRoleService;

	@Autowired(required = true)
	@Qualifier(value = "adminUserService")
	public AdminUserService adminUserService;

	@Autowired(required = true)
	@Qualifier(value = "announceService")
	public AnnounceService announceService;

	@Autowired(required = true)
	@Qualifier(value = "ctService")
	public CtService ctService;

	@Autowired(required = true)
	@Qualifier(value = "danService")
	public DanService danService;

	@Autowired(required = true)
	@Qualifier(value = "errorTypeService")
	public ErrorTypeService errorTypeService;

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
	@Qualifier(value = "orderService")
	public OrderService orderService;

	@Autowired(required = true)
	@Qualifier(value = "orderRelationService")
	public OrderRelationService orderRelationService;

	@Autowired(required = true)
	@Qualifier(value = "reasonTypeService")
	public ReasonTypeService reasonTypeService;

	@Autowired(required = true)
	@Qualifier(value = "reportService")
	public ReportService reportService;

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

	public enum UserType {
	    PRODUCTION, QUALITY,MAINTENANCE,SPECIALIST
	}
	
	public String[] USERTYPE_NAMES = {"tc_production","tc_quality","tc_maintenance","tc_specialist"};
	
	@Override
	public List<Object> queryList(String SQL_QUERY, SessionFactory sessionFactory) {
		Session session = sessionFactory.openSession();
		List<Object> ret = new ArrayList<Object>();
		Transaction t = session.beginTransaction();
		try {
			ret = session.createSQLQuery(SQL_QUERY).list();
			t.commit();
			session.close();

		} catch (Exception e) {
			t.rollback();
			session.close();
		}
		return ret;
	}

	public static String getTableName(String s) {
		String ret = "";
		String[] r = s.split("(?=\\p{Upper})");
		for (int i = 0; i < r.length; i++) {
			ret = ret + r[i] + "_";
		}
		if (ret.length() > 0) {
			ret = ret.substring(0, ret.length() - 1);
			ret = "tbl_" + ret;
		}
		return ret;
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
	
	public static String getModifySQL(Object targetClass, String tableName, HashMap<String, Object> whereFields) {
		String sql = null;
		String part1 = "";
		Field[] fields = targetClass.getClass().getDeclaredFields();
		for (Field f : fields) {
			try {
				String name = f.getName();
				if (whereFields.containsKey(name)) {
					continue;
				}
				f.setAccessible(true);
				Object value = f.get(targetClass);
				if (value != null) {
					String item1 = String.format("`%s`='%s',", name, value);
					part1 = part1 + item1;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		if (part1.length() > 0) {
			part1 = part1.substring(0, part1.length() - 1);
			sql = String.format("update %s set %s %s", tableName, part1, getWhereSQL(whereFields));
		}
		return sql;
	}
	public static String getCountSQL(String tableName, HashMap<String, Object> whereFields) {
		String sql = null;
		
		sql = String.format("select count(*) from %s %s", tableName, getWhereSQL(whereFields));
		return sql;
	}

	public static String getWhereSQL(HashMap<String, Object> whereFields) {
		String sql = " where ";
		Iterator it = whereFields.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			String name = pair.getKey().toString();
			String value = pair.getValue().toString();
			sql = sql + String.format(" `%s` = '%s' and", name, value);
		}
		if (sql.length() > 0) {
			sql = sql.substring(0, sql.length() - 3);
		}
		return sql;
	}

	public static String escapeString(String string) {
		String ret = string;
		if (string != null) {
			ret = string.replaceAll("'", "''");
		}
		return ret;
	}

	public static String getInsertSql(Object targetClass, String tableName, List<String> exceptions) {
		String sql = null;
		String part1 = "";
		String part2 = "";

		for (Field f : targetClass.getClass().getDeclaredFields()) {
			String name = f.getName();
			if (exceptions != null && exceptions.indexOf(name) >= 0) {
				continue;
			}
			try {
				f.setAccessible(true);
				Object obj = f.get(targetClass);
				if (obj == null) {
					// String item1 = String.format("`%s`,",name);
					// String item2 = "'',";
					// part1 = part1 + item1;
					// part2 = part2 + item2;
				} else {
					String item1 = String.format("`%s`,", name);
					String item2 = String.format("'%s',", obj);
					part1 = part1 + item1;
					part2 = part2 + item2;
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		if (part1.length() > 0) {
			part1 = part1.substring(0, part1.length() - 1);
			part2 = part2.substring(0, part2.length() - 1);

			sql = String.format("insert into %s (%s) values (%s)", tableName, part1, part2);
		}

		return sql;
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
				String SQL_QUERY = BaseHelperImpl.getInsertSql(model, BaseHelperImpl.getTableName(ModelT.getSimpleName()),
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
			String SQL_QUERY = BaseHelperImpl.getModifySQL(model, BaseHelperImpl.getTableName(ModelT.getSimpleName()),
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
			
			String SQL_QUERY = BaseHelperImpl.getCountSQL(BaseHelperImpl.getTableName(ModelT.getSimpleName()), whereFields);

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
