package com.hgc.admin.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Component;

import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgc.admin.constants.DbFields;
import com.hgc.admin.database.model.Ct;
import com.hgc.admin.database.model.Dan;
import com.hgc.admin.database.model.ErrorType;
import com.hgc.admin.database.model.Line;
import com.hgc.admin.database.model.Order;
import com.hgc.admin.database.model.OrderRelation;
import com.hgc.admin.database.model.Picture;
import com.hgc.admin.database.model.ReasonType;
import com.hgc.admin.database.model.RecDaily;
import com.hgc.admin.database.model.Report;
import com.hgc.admin.database.model.Station;
import com.hgc.admin.database.model.User;
import com.hgc.admin.database.model.UserRole;
import com.hgc.admin.database.service.*;
import com.hgc.admin.model.RestRequest;
import com.hgc.admin.model.TaOee;
import com.hgc.admin.push.AndroidPushMsgToAll;
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
	@Qualifier(value = "pictureService")
	public PictureService pictureService;

	@Autowired(required = true)
	@Qualifier(value = "reasonTypeService")
	public ReasonTypeService reasonTypeService;

	@Autowired(required = true)
	@Qualifier(value = "recDailyService")
	public RecDailyService recDailyService;

	@Autowired(required = true)
	@Qualifier(value = "recOacService")
	public RecOacService recOacService;

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
	@Qualifier(value = "userPartService")
	public UserPartService userPartService;

	@Autowired(required = true)
	@Qualifier(value = "userRoleService")
	public UserRoleService userRoleService;

	@Resource
	@Qualifier(value = "tablefields")
	public DbFields dbFields;

	public enum UserType {
		PRODUCTION, QUALITY, MAINTENANCE, SPECIALIST
	}
	

	public String[] USERTYPE_NAMES = { "tc_production", "tc_quality", "tc_maintenance", "tc_specialist" };

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

	public static String ufc(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
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

	public String getDateTime(int type) {
		DateTime dt = new DateTime();
		org.joda.time.format.DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		String d = dt.toString(fmt);
		if (type == 0) {
			fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
			d = dt.toString(fmt);
		} else if (type == 1) {
			dt = dt.minusDays(1);
			d = dt.toString(fmt);
		}else if (type == 2) {
			fmt = DateTimeFormat.forPattern("HH:mm");
			d = dt.toString(fmt);
		}

		return d;
	}

	public List<Object> getPastDays(int n, HashMap<String, Object> wf) {
		String T1 = wf.get("T1").toString();
		String T2 = wf.get("T2").toString();
		Integer time_type = Integer.valueOf(wf.get("time_type").toString());
		org.joda.time.format.DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

		List<Object> list_data = new ArrayList<Object>();
		if (time_type == 4) {
			DateTime dt = fmt.parseDateTime(T1);
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < n; i++) {
				String d = dt.toString(fmt);
				list.add(d);
				dt = dt.minusDays(1);
			}

			for (int i = list.size() - 1; i >= 0; i--) {
				list_data.add(list.get(i));
			}
		} else if (time_type == 3) {
			// t1-t2 week
			DateTime dt = fmt.parseDateTime(T2);
			List<Object> list = new ArrayList<Object>();
			for (int i = 0; i < n; i++) {
				DateTime second = dt;
				DateTime first = dt.minusDays(6);

				List<String> strings = new ArrayList<String>();
				strings.add(first.toString(fmt));
				strings.add(second.toString(fmt));
				
				int dayofyear = first.getDayOfYear();
				int week = dayofyear/7 + 1;
				int year = first.getYear();
				String p = String.format("%04d", year)+"-"+String.format("%02d", week);
				strings.add(p);
				
				list.add(strings);
				
				dt = dt.minusWeeks(1);
			}

			for (int i = list.size() - 1; i >= 0; i--) {
				list_data.add(list.get(i));
			}
		} else if (time_type == 2) {
			// t1-t2 month
			DateTime dt = fmt.parseDateTime(T2);
			List<Object> list = new ArrayList<Object>();
			for (int i = 0; i < n; i++) {
				List<String> strings = new ArrayList<String>();
				if (i == 0) {
					
					strings.add(T1);
					strings.add(T2);
				} else {
					DateTime start = dt.withDayOfMonth(1);
					DateTime end = dt.dayOfMonth().withMaximumValue();
					strings.add(start.toString(fmt));
					strings.add(end.toString(fmt));
				}
				
				int month = dt.getMonthOfYear();
				int year = dt.getYear();
				String p = String.format("%04d", year)+"-"+String.format("%02d", month);
				strings.add(p);
				list.add(strings);
				
				dt = dt.minusMonths(1);
			}
			for (int i = list.size() - 1; i >= 0; i--) {
				list_data.add(list.get(i));
			}
		} else if (time_type == 1) {
			// t1-t2 year based
			DateTime dt = fmt.parseDateTime(T2);
			List<Object> list = new ArrayList<Object>();
			for (int i = 0; i < 3; i++) {
				List<String> strings = new ArrayList<String>();
				if (i == 0) {
					strings.add(T1);
					strings.add(T2);
				} else {
					String d = dt.toString(fmt);
					strings.add(d.substring(0, 4) + "-01-01");
					strings.add(d.substring(0, 4) + "-12-31");
				}
				
				int year = dt.getYear();
				String p = String.format("%04d", year);
				strings.add(p);
				list.add(strings);
				
				
				dt = dt.minusYears(1);
			}
			for (int i = list.size() - 1; i >= 0; i--) {
				list_data.add(list.get(i));
			}
		}
		return list_data;
	}

	public int getTimeDifference(String first, String second, int type) {

		DateTime dt = new DateTime();
		org.joda.time.format.DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		int ret = 0;
		try {
			if (type == 0) {
				DateTime time_first = fmt.parseDateTime(first);
				DateTime time_second = fmt.parseDateTime(second);
				long diffInMillis = time_first.getMillis() - time_second.getMillis();

				ret = (int) (diffInMillis / 1000 / 60);
			} else if (type == 1) {
				DateTime time_first = new DateTime();
				DateTime time_second = new DateTime();

				String[] arr = first.split(":");
				int time = 0;
				int minute = 0;
				if(arr.length==2){
					time = this.parseInt(arr[0]);
					minute = this.parseInt(arr[1]);
				}
				
				time_first = time_first.withHourOfDay(time).withMinuteOfHour(minute).withSecondOfMinute(0);

				arr = second.split(":");
				time = 0;
				minute = 0;
				if(arr.length==2){
					time = this.parseInt(arr[0]);
					minute = this.parseInt(arr[1]);
				}
				time_second = time_first.withHourOfDay(time).withMinuteOfHour(minute).withSecondOfMinute(0);

				long diffInMillis = time_first.getMillis() - time_second.getMillis();
				ret = (int) (diffInMillis / 1000 / 60);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return ret;
	}
	public Integer parseInt(String p){
		try{
			int time = Integer.valueOf(p);
			return time;
		}catch(Exception ex){
			//ex.printStackTrace();
		}
		return 0;
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

	public static String getQuerySQL(String tableName, String[] fields, HashMap<String, Object> whereFields) {
		String sql = null;
		String part1 = "";
		for (int i = 0; i < fields.length; i++) {
			part1 = part1 + "`" + fields[i] + "`,";
		}
		if (part1.length() > 0) {
			part1 = part1.substring(0, part1.length() - 1);
		}
		sql = String.format("select %s from %s %s", part1, tableName, getWhereSQL(whereFields));
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
				String SQL_QUERY = BaseHelperImpl.getInsertSql(model,
						BaseHelperImpl.getTableName(ModelT.getSimpleName()), exceptions);
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

	public UserRole getRole(UserType userType) {
		String tr_name = USERTYPE_NAMES[userType.ordinal()];
		String sql = "SELECT `id`,`name`,`tr_name`,`deleted`,`create_datetime`,`modify_datetime` FROM `tbl_user_role` "
				+ " where tr_name='" + tr_name + "'";
		String[] ids = { "id", "name", "tr_name", "deleted", "create_datetime", "modify_datetime" };
		List<UserRole> list = this.userRoleService.queryUserRole(sql, ids);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List getTypedUser(User maker, UserType userType) {
		Integer dan = maker.getDan();
		UserRole ur = getRole(userType);
		String sql = "SELECT * FROM `tbl_user`" + " where dan=" + dan + " and type = " + ur.getId();
		List<User> list = this.sqlQuery(User.class, this.userService, sql, 1);
		return list;
	}

	public long modelCheck(Class<?> ModelT, Object service, Object non_safe, Integer mode) {
		long ret = 0;
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

			// Object param = filterObject(non_safe, ModelT);
			// String json_string = mapper.writeValueAsString(param);
			// Object model = mapper.readValue(json_string, ModelT);

			HashMap<String, Object> whereFields = (HashMap<String, Object>) non_safe;

			String SQL_QUERY = BaseHelperImpl.getCountSQL(BaseHelperImpl.getTableName(ModelT.getSimpleName()),
					whereFields);

			SessionFactory sessionFactory = this.transactionManager.getSessionFactory();

			Session session = sessionFactory.openSession();
			try {

				List<Object> temp = session.createSQLQuery(SQL_QUERY).list();
				if (temp.size() > 0) {
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

	public List modelQuery(Class<?> ModelT, Object service, Object non_safe, Integer mode) {

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

			// Object param = filterObject(non_safe, ModelT);
			// String json_string = mapper.writeValueAsString(param);
			// Object model = mapper.readValue(json_string, ModelT);

			HashMap<String, Object> whereFields = (HashMap<String, Object>) non_safe;
			String tablename = BaseHelperImpl.getTableName(ModelT.getSimpleName());
			Field field = this.dbFields.getClass().getField(tablename.toLowerCase());
			String[] field_names = (String[]) field.get(this.dbFields);

			String SQL_QUERY = BaseHelperImpl.getQuerySQL(tablename, field_names, whereFields);
			if (service != null) {
				// this.adminRoleService.queryAdminRole(query, db_fields)
				String method_name = "query" + ModelT.getSimpleName();
				Method method = service.getClass().getMethod(method_name, String.class, String[].class);
				;
				List list = (List) method.invoke(service, SQL_QUERY, field_names);
				return list;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public List sqlQuery(Class<?> ModelT, Object service, String SQL_QUERY, Integer mode) {

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

			String tablename = BaseHelperImpl.getTableName(ModelT.getSimpleName());
			Field field = this.dbFields.getClass().getField(tablename.toLowerCase());
			String[] field_names = (String[]) field.get(this.dbFields);

			if (service != null) {
				// this.adminRoleService.queryAdminRole(query, db_fields)
				String method_name = "query" + ModelT.getSimpleName();
				Method method = service.getClass().getMethod(method_name, String.class, String[].class);
				;
				List list = (List) method.invoke(service, SQL_QUERY, field_names);
				return list;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ArrayList();
	}

	public HashMap<String, Object> getHashMapOfObject(Object param, Class<?> ModelT, String[] allowed) {
		HashMap<String, Object> pp = new HashMap<String, Object>();
		List<String> list_allowed = null;
		if (allowed != null && allowed.length > 0) {
			list_allowed = Arrays.asList(allowed);
		}
		try {
			String tablename = BaseHelperImpl.getTableName(ModelT.getSimpleName());
			Field field_n = dbFields.getClass().getField(tablename.toLowerCase());
			Field field_t = dbFields.getClass().getField("type_" + tablename.toLowerCase());
			List<String> field_names = Arrays.asList((String[]) field_n.get(dbFields));
			List<String> field_types = Arrays.asList((String[]) field_t.get(dbFields));

			for (int i = 0; i < field_names.size(); i++) {
				String iname = field_names.get(i);
				String itype = field_types.get(i);
				String method_name = "get" + ufc(iname);
				Method method = ModelT.getMethod(method_name);
				Object p = method.invoke(param);
				if (list_allowed != null) {
					if (!list_allowed.contains(iname)) {
						// not allowed field
						continue;
					}
				}

				if (p != null) {
					if (itype.equals("datetime")) {
						int len = "2017-11-26 00:00:00".length();
						if (p.toString().length() > len) {
							p = p.toString().subSequence(0, len);
						}
					}

					pp.put(iname, p);
				}

			}
			// for (int i = 0; i < fields.length; i++) {
			// Field ifield = fields[i];
			// ifield.setAccessible(true);
			// String name = ifield.getName();
			// Object value;
			// if(field_names.contains(name)){
			// try {
			//
			// value = ifield.get(param);
			// pp.put(name, value);
			// } catch (Exception e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// fields = param.getClass().getFields();
		return pp;
	}

	public HashMap<String, Object> getOrderInfo(Order order, int mode) {
		HashMap<String, Object> map_model = this.getHashMapOfObject(order, Order.class, null);
		Station st = this.stationService.getStationById(order.getStation_id());
		map_model.put("model_station", this.getHashMapOfObject(st, Station.class, null));

		if (order.getReason_id() > 0) {
			ReasonType rt = this.reasonTypeService.getReasonTypeById(order.getReason_id());
			map_model.put("model_reason", this.getHashMapOfObject(rt, ReasonType.class, null));
		}
		if (order.getError_id() > 0) {
			ErrorType et = this.errorTypeService.getErrorTypeById(order.getError_id());
			map_model.put("model_error", this.getHashMapOfObject(et, ErrorType.class, null));
		}
		if (mode == 1) {
			// relation info
			List<Object> relation = new ArrayList<Object>();
			String[] filter = { "id", "name" };
			String[] filter_relation = { "id", "s_time", "r_time" };

			HashMap<String, Object> select = this.dbFields.getSelectFields("", OrderRelation.class);
			String str_select = select.get("select").toString();
			String[] ids = (String[]) select.get("fields");

			String sql = "select " + str_select + " from " + this.getTableName(OrderRelation.class.getSimpleName())
					+ " where order_id = " + order.getId() + " order by create_datetime asc";
			List list = this.sqlQuery(OrderRelation.class, this.orderRelationService, sql, 1);
			for (int i = 0; i < list.size(); i++) {
				OrderRelation or = (OrderRelation) list.get(i);
				User sender = this.userService.getUserById(or.getSender_id());
				User receiver = this.userService.getUserById(or.getReceiver_id());

				HashMap<String, Object> map_sender = this.getHashMapOfObject(sender, User.class, filter);
				HashMap<String, Object> map_receiver = this.getHashMapOfObject(receiver, User.class, filter);

				HashMap<String, Object> ihash = new HashMap<String, Object>();
				ihash.put("sender", map_sender);
				ihash.put("receiver", map_receiver);
				if (or.getS_time() != null) {
					ihash.put("s_time", or.getS_time());
				}
				if (or.getR_time() != null) {
					ihash.put("r_time", or.getR_time());
				}
				relation.add(ihash);
			}

			map_model.put("list_relation", relation);

			HashMap<String, Object> non_safe = new HashMap<String, Object>();
			non_safe.put("ref_type1", Order.class.getSimpleName());
			non_safe.put("ref_id1", order.getId());

			List list_picture = this.modelQuery(Picture.class, this.pictureService, non_safe, 1);
			if (list_picture != null) {
				Collections.sort(list_picture, new Comparator<Picture>() {
					@Override
					public int compare(Picture lhs, Picture rhs) {
						// -1 - less than, 1 - greater than, 0 - equal, all
						// inversed for
						// descending

						return lhs.getId() > rhs.getId() ? -1 : (lhs.getId() < rhs.getId()) ? 1 : 0;
						// return rhs.getId() > lhs.getId() ? -1 : (rhs.getId()
						// < lhs.getId()) ? 1 : 0;
					}

				});
				List<Object> list_temp = new ArrayList<Object>();
				String[] allowed = { "id", "filename", "type" };
				for (Object obj : list_picture) {
					Picture picture = (Picture) obj;
					list_temp.add(this.getHashMapOfObject(picture, Picture.class, allowed));
				}
				map_model.put("list_picture", list_temp);
			} else {
				map_model.put("list_picture", new ArrayList<Object>());
			}

		}
		return map_model;
	}
	public TaOee calculateMin(List<TaOee> ilist){
		double sum_ta = 0;
		double sum_oee = 0;
		double sum_output = 0;
		double ta = 0;
		double oee = 0;
		double output = 0;
		for (TaOee taoee : ilist) {
			sum_ta = sum_ta + taoee.getTa();
			sum_oee = sum_oee + taoee.getOee();
			sum_output = sum_output + taoee.getOutput();
		}
		if (sum_ta !=0 ) {
			ta = sum_ta / ilist.size();
		}
		if (sum_oee != 0) {
			oee = sum_oee / ilist.size();
		}
		if (sum_output != 0) {
			output = sum_output / ilist.size();
		}
		TaOee taoee = new TaOee();
		taoee.setTa(ta);
		taoee.setOee(oee);
		taoee.setOutput(output);
		return taoee;
	}
	public HashMap<String, Object> processUserForReport(List list, HashMap<String, Object> wf) {
		// ASSUME LIST REPORT AS SAME DAYS
		HashMap<String, Object> map_ret = new HashMap<String, Object>();
		HashMap<Integer, Object> map_linedata = new HashMap<Integer, Object>();
		HashMap<Integer, Object> map_dandata = new HashMap<Integer, Object>();
		HashMap<Integer, Object> map_order_min = new HashMap<Integer, Object>();
		ArrayList<Object> list_all = new ArrayList<Object>();
		Integer time_type = Integer.valueOf(wf.get("time_type").toString());
		String time_value = wf.get("time_type").toString();
		String T1 = wf.get("T1").toString();
		String T2 = wf.get("T2").toString();
		for (Object obj : list) {
			Report report = (Report) obj;
			String sql = "";
			String T = report.getCreate_datetime().substring(0,10);
			if (time_type == 4) {
				sql = "select O.id,TIMESTAMPDIFF(MINUTE, O.create_datetime, O.complete) as order_min1"
						+ " ,TIMESTAMPDIFF(MINUTE, O.create_datetime, NOW()) as order_min2"
						+ " ,complete "
						+ " from tbl_order as O" 
						+ " where O.user_id = " + report.getUser_id() 
						//+ " and O.status = 2 "
						+ " and substring(O.create_datetime,1,10) = '" + T + "'";
			}else{
				sql = "select O.id,TIMESTAMPDIFF(MINUTE, O.create_datetime, O.complete) as order_min1"
						+ " ,TIMESTAMPDIFF(MINUTE, O.create_datetime, NOW()) as order_min2"
						+ " ,complete "
						+ " from tbl_order as O" 
						+ " where O.user_id = " + report.getUser_id() 
						//+ " and O.status = 2 "
						+ " and substring(O.create_datetime,1,10) = '" + T + "'";
			}
			SessionFactory sessionFactory = this.transactionManager.getSessionFactory();
			Session session = sessionFactory.openSession();
			List<Object> ret = new ArrayList<Object>();

			double order_min = 0;
			String nulltime = "0000-00-00 00:00:00";
			try {
				ret = session.createSQLQuery(sql).list();
				for(Object irow:ret){
					Object[] iraw_row = (Object[]) irow;
					Integer irow_id = Integer.valueOf(iraw_row[0].toString());
					Integer irow_order_min1 = 0;
					Integer irow_order_min2 = 0;
					String complete = nulltime;
					
					if(iraw_row[1]!=null){irow_order_min1 = Integer.valueOf(iraw_row[1].toString()); }
					if(iraw_row[2]!=null){irow_order_min2 = Integer.valueOf(iraw_row[2].toString()); }
					if(iraw_row[3]!=null){complete = iraw_row[3].toString().substring(0, 19); }
					
					HashMap<String,Object> hash = new HashMap<String,Object>();
					hash.put("order_id", irow_id);
					if(complete.equals(nulltime)){
						order_min = order_min + irow_order_min2;
						hash.put("order_min", irow_order_min2);
					}else{
						order_min = order_min + irow_order_min1;
						hash.put("order_min", irow_order_min1);
					}
					map_order_min.put(irow_id, hash);
				}
//				if(order_min>0){
//					order_min = order_min/ret.size();
//				}
				session.close();

			} catch (Exception e) {
				session.close();
			}

			double work_min = this.getTimeDifference(report.getLast_unload_time(), report.getFirst_load_time(), 0);
			double lunch_min = report.getLunch_time();
			double ct_value = 0;
			// 2017-12-04 14:57:00
			//
			String report_time = report.getCreate_datetime();
			// get day part.
			User reporter = this.userService.getUserById(report.getUser_id());
			Dan dan = this.danService.getDanById(reporter.getDan());
			Line line = this.lineService.getLineById(dan.getLine());
			sql = "Select * from tbl_ct where line = " + line.getId();
			List list_ct = this.sqlQuery(Ct.class, this.ctService, sql, 1);
			if (list_ct.size() > 0) {
				Ct ct = (Ct) list_ct.get(0);
				String report_hr = report_time.substring(11, 13);
				if (Integer.parseInt(report_hr) > 12) {
					ct_value = ct.getV2();
				} else {
					ct_value = ct.getV1();
				}
			}
			double ta = (work_min - lunch_min - order_min) / (work_min - lunch_min);
			double oee = report.getOutput() * ct_value / (work_min - lunch_min);
			double output = report.getOutput();

			if (wf.containsKey("calculate_line")) {
				if (map_linedata.containsKey(line.getId())) {
					List<TaOee> ilist = (List<TaOee>) map_linedata.get(line.getId());
					TaOee taoee = new TaOee();
					taoee.setTa(ta);
					taoee.setOee(oee);
					taoee.setOutput(output);
					ilist.add(taoee);
				} else {
					List<TaOee> ilist = new ArrayList<TaOee>();
					TaOee taoee = new TaOee();
					taoee.setTa(ta);
					taoee.setOee(oee);
					taoee.setOutput(output);
					ilist.add(taoee);
					map_linedata.put(line.getId(), ilist);
				}
			}
			if (wf.containsKey("calculate_dan")) {
				if (map_dandata.containsKey(dan.getId())) {
					List<TaOee> ilist = (List<TaOee>) map_dandata.get(dan.getId());
					TaOee taoee = new TaOee();
					taoee.setTa(ta);
					taoee.setOee(oee);
					taoee.setOutput(output);
					ilist.add(taoee);
				} else {
					List<TaOee> ilist = new ArrayList<TaOee>();
					TaOee taoee = new TaOee();
					taoee.setTa(ta);
					taoee.setOee(oee);
					taoee.setOutput(output);
					ilist.add(taoee);
					map_dandata.put(dan.getId(), ilist);
				}
			}
			if (wf.containsKey("calculate_all")) {
				TaOee taoee = new TaOee();
				taoee.setTa(ta);
				taoee.setOee(oee);
				taoee.setOutput(output);
				list_all.add(taoee);
			}
		}

		//

		HashMap<Integer, Object> map_line_ret = new HashMap<Integer, Object>();
		Iterator it = map_linedata.entrySet().iterator();
		if (wf.containsKey("calculate_line")) {
			while (it.hasNext()) {
				HashMap.Entry pair = (HashMap.Entry) it.next();
				Integer key = (Integer) pair.getKey();
				List<TaOee> ilist = (List<TaOee>)pair.getValue();
				TaOee taoee = this.calculateMin(ilist);

				HashMap<String, Object> hash = new HashMap<String, Object>();
				Line line = this.lineService.getLineById(key);
				hash.put("model_line", this.getHashMapOfObject(line, Line.class, null));
				hash.put("cal_data", taoee);

				// list_data.add(hash);
				map_line_ret.put(key, hash);
			}
		}
		

		HashMap<Integer, Object> map_dan_ret = new HashMap<Integer, Object>();
		if (wf.containsKey("calculate_dan")) {
			it = map_dandata.entrySet().iterator();
			while (it.hasNext()) {
				HashMap.Entry pair = (HashMap.Entry) it.next();
				Integer key = (Integer) pair.getKey();
				List<TaOee> ilist = (List<TaOee>) pair.getValue();
				TaOee taoee = this.calculateMin(ilist);

				HashMap<String, Object> hash = new HashMap<String, Object>();
				Dan dan = this.danService.getDanById(key);
				hash.put("model_dan", this.getHashMapOfObject(dan, Dan.class, null));
				hash.put("cal_data", taoee);

				// list_data.add(hash);
				map_dan_ret.put(key, hash);
			}
			
			// get orders by order min time
			List<Object> values = new ArrayList<Object>();
			values.addAll(map_order_min.values());
			Collections.sort(values, new Comparator<Object>() {
				@Override
				public int compare(Object lhs, Object rhs) {
					// -1 - less than, 1 - greater than, 0 - equal, all inversed for
					// descending
					HashMap<String, Object> hash_lhs = (HashMap<String, Object>) lhs;
					Integer lhs_order_min = (Integer) hash_lhs.get("order_min");

					HashMap<String, Object> hash_rhs = (HashMap<String, Object>) rhs;
					Integer rhs_order_min = (Integer) hash_rhs.get("order_min");

					return lhs_order_min > rhs_order_min ? -1 : (lhs_order_min < rhs_order_min) ? 1 : 0;
				}

			});
			int loop = 0;
			List<Object> list_bar = new ArrayList<Object>();
			for(int i=0; i<values.size(); i++){
				HashMap<String, Object> hash = (HashMap<String, Object>) values.get(i);
				Integer order_min = (Integer) hash.get("order_min");
				Integer order_id = (Integer) hash.get("order_id");
				Order order = this.orderService.getOrderById(order_id);
				
				//String[] allowed = {"id"};
				
				HashMap<String, Object> idata = this.getOrderInfo(order, 0);
				idata.put("order_min", order_min);
				
				list_bar.add(idata);
								
				loop++;
				if(loop==5){
					break;
				}
			}			
			map_ret.put("list_bar", list_bar);
		}
		map_ret.put("line", map_line_ret);
		map_ret.put("dan", map_dan_ret);
		map_ret.put("list_all", list_all);
		return map_ret;
	}
	
	public Object generate_daily_report(){
				
		String day = this.getDateTime(1).substring(0, 10);		
		Integer A_REASON = 1;
		Integer Q_REASON = 2;
		Integer P_REASON = 3;
		Integer N_REASON = 4;
		
		HashMap<String, Object> non_safe = new HashMap<String, Object>();
		String sql = "select O.* from tbl_order as O"
				+" where O.reason_id = "+A_REASON
				+" and substring(O.create_datetime,1,10) = '"+day+"'"
				;
		List list_temp = this.sqlQuery(Order.class, this.orderService, sql, 1);
		Integer A_CNT = list_temp.size();
		Integer A_ERR = 0;
		for(Object obj:list_temp){
			Order order = (Order)obj;
			if(order.getError_id()>0){
				A_ERR = A_ERR+1;
			}
		}
		
		sql = "select O.* from tbl_order as O"
				+" where O.reason_id = "+Q_REASON
				+" and substring(O.create_datetime,1,10) = '"+day+"'"
				;
		list_temp = this.sqlQuery(Order.class, this.orderService, sql, 1);
		Integer Q_CNT = list_temp.size();
		Integer Q_ERR = 0;
		for(Object obj:list_temp){
			Order order = (Order)obj;
			if(order.getError_id()>0){
				Q_ERR = Q_ERR+1;
			}
		}
		
		sql = "select O.* from tbl_order as O"
				+" where O.reason_id = "+P_REASON
				+" and substring(O.create_datetime,1,10) = '"+day+"'"
				;
		list_temp = this.sqlQuery(Order.class, this.orderService, sql, 1);
		Integer P_CNT = list_temp.size();
		Integer P_ERR = 0;
		for(Object obj:list_temp){
			Order order = (Order)obj;
			if(order.getError_id()>0){
				P_ERR = P_ERR+1;
			}
		}
		
		sql = "select O.* from tbl_order as O"
				+" where O.reason_id = "+N_REASON
				+" and substring(O.create_datetime,1,10) = '"+day+"'"
				;
		list_temp = this.sqlQuery(Order.class, this.orderService, sql, 1);
		Integer N_CNT = list_temp.size();
		Integer N_ERR = 0;
		for(Object obj:list_temp){
			Order order = (Order)obj;
			if(order.getError_id()>0){
				N_ERR = N_ERR+1;
			}
		}
			
		
		List list_report = this.getReportForPeriod(day, day, null);
		
		HashMap<String, Object> input_param = new HashMap<String, Object>();
		input_param.put("time_type", 4);
		input_param.put("T1", day);
		input_param.put("T2", day);
		input_param.put("calculate_all", 1);
		HashMap<String, Object> map_ret = this.processUserForReport(list_report, input_param);
		List<TaOee> list_all = (List<TaOee>) map_ret.get("list_all");
		TaOee taoee = this.calculateMin(list_all);
		
		String content  ="Today's daily "
				+A_CNT +" equipment failure," //+ 0 +" reasons"
				+Q_CNT +" quality reasons," //+ "failure " + A_FAIL 
				+P_CNT +" production failure. " //+ "failure " + A_FAIL
				+"TA value was " + taoee.getTa() +"%,"
				+"OEE value was " + taoee.getOee()+"%."
				//+"OUTPUT " + taoee.getOutput()
				;
		RecDaily an = new RecDaily();
		String d = this.getDateTime(0);
		an.setCreate_datetime(d);
		an.setModify_datetime(day);
		an.setTime(day);
		an.setContent(content);
		an.setDeleted(0);
		an.setPcnt(P_CNT);
		an.setAcnt(A_CNT);
		an.setQcnt(Q_CNT);
		an.setNcnt(N_CNT);
		an.setOutput(taoee.getOutput());
		an.setTa(taoee.getTa());
		an.setOee(taoee.getOee());
		
		sql = "select * from tbl_rec_daily "
				+" where substring(time,1,10) = '"+day+"'";
		List list = this.sqlQuery(RecDaily.class, this.recDailyService, sql, 1);
		if(list.size()>0){
			for(Object obj:list){
				RecDaily rec = (RecDaily)obj;
				this.recDailyService.removeRecDaily(rec.getId());
			}
		}
		
		int id = this.recDailyService.addRecDaily(an);
		if( id>0 ){
			an.setId(id);
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("model", an);
			ret.put("response", 200);
			return ret;
		}
		return null;
		
	}
	public List getReportForPeriod(String T1, String T2, HashMap<String, Object> wf) {
		List list = new ArrayList();
		if (wf != null) {

			if (wf.containsKey("mode_id")) {
				Integer mode_id = Integer.valueOf(wf.get("mode_id").toString());
				Integer id = Integer.valueOf(wf.get("id").toString());
				if(mode_id == 1){
					// line mode
					String sql = "select R.* from tbl_report as R" + " join tbl_user as U on R.user_id = U.id"
							+ " join tbl_dan as D on U.dan = D.id" + " where substring(R.create_datetime,1,10) >= '" + T1
							+ "'" + " and substring(R.create_datetime,1,10) <= '" + T2 + "'" + " and D.line = " + id;
					List listemp = this.sqlQuery(Report.class, this.reportService, sql, 1);
					list = listemp;
				}else if(mode_id == 2){
					// dan mode
					String sql = "select R.* from tbl_report as R" + " join tbl_user as U on R.user_id = U.id"
							+ " join tbl_dan as D on U.dan = D.id" + " where substring(R.create_datetime,1,10) >= '" + T1
							+ "'" + " and substring(R.create_datetime,1,10) <= '" + T2 + "'" + " and D.id = " + id;
					List listemp = this.sqlQuery(Report.class, this.reportService, sql, 1);
					list = listemp;
				}
				
				
			}

		} else {
			String sql = "select R.* from tbl_report as R" + " where substring(R.create_datetime,1,10) >= '" + T1 + "'"
					+ " and substring(R.create_datetime,1,10) <= '" + T2 + "'";
			list = this.sqlQuery(Report.class, this.reportService, sql, 1);
		}
		return list;

	}
	
	public Object push_all(HashMap<String,Object> map_msg){
		
		AndroidPushMsgToAll push = new AndroidPushMsgToAll();
		try {
			
			push.sendPush(map_msg);
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("response", 200);
			return ret;
		} catch (PushClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PushServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
