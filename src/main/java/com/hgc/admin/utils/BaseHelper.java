package com.hgc.admin.utils;

import java.lang.reflect.Field;
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

import com.hgc.admin.database.model.Dan;
import com.hgc.admin.database.model.Line;
import com.hgc.admin.database.service.*;

@Component
@Primary
public class BaseHelper implements ControllerHelper {

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

	@Override
	public Object[] login(String username, String password, SessionFactory sessionFactory) {
		String sql = "select id,username,password from tbl_admin_user where username = '" + username
				+ "' and password='" + password + "' and deleted = 0";
		List list = this.queryList(sql, sessionFactory);
		if (list != null && list.size() > 0) {
			return (Object[]) list.get(0);
		}
		return null;
	}

	@Override
	public boolean fakeLogin() {
		return true;
	}

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

	
}
