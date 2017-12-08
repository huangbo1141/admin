package com.hgc.admin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgc.admin.constants.Constants;
import com.hgc.admin.database.model.*;

@Component
public class BackendApiHelper extends BaseHelperImpl {

	public Object[] login(String username, String password, SessionFactory sessionFactory) {
		String sql = "select id,username,password from tbl_admin_user where username = '" + username
				+ "' and password='" + password + "' and deleted = 0";
		List<Object> list = this.queryList(sql, sessionFactory);
		if (list != null && list.size() > 0) {
			return (Object[]) list.get(0);
		}
		return null;
	}

	public boolean fakeLogin() {
		return true;
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

	public HashMap<Integer, Object> getMapDanPerLineID() {
		HashMap<Integer, Object> hash = new HashMap<Integer, Object>();
		HashMap<Integer, Line> map_line = lineService.mapLines();
		for (Dan dan : danService.listDans()) {
			if (hash.containsKey(dan.getLine())) {
				List<Dan> list = (List<Dan>) hash.get(dan.getLine());
				list.add(dan);
			} else {
				List<Dan> list = new ArrayList<Dan>();
				list.add(dan);
				hash.put(dan.getLine(), list);
			}
		}
		return hash;
	}

	public Object removeOrderAndRelation(Integer orderId) {
		Object ret = null;
		SessionFactory sf = this.transactionManager.getSessionFactory();
		Session ss = sf.openSession();
		Transaction t = ss.beginTransaction();
		try {
			String sql = "delete from tbl_order_relation where order_id = " + orderId;
			ss.createSQLQuery(sql).executeUpdate();

			sql = "delete from tbl_order where id = " + orderId;
			ss.createSQLQuery(sql).executeUpdate();
			t.commit();
			ss.close();
			ret = 1;
		} catch (Exception ex) {
			t.rollback();
			ss.close();
		}

		return ret;
	}

	public Object removeErrorAndRelatedOnes(Integer errorId) {
		Object ret = null;
		String sql = "select * from tbl_order where error_id = " + errorId;
		List list = this.sqlQuery(Order.class, this.orderService, sql, 1);
		for (Object obj : list) {
			Order order = (Order) obj;
			Object rm_ret = this.removeOrderAndRelation(order.getId());
		}
		this.errorTypeService.removeErrorType(errorId);
		ret = 1;

		return ret;
	}

	public Object removeStationAndRelatedOnes(Integer stationId) {
		Object ret = null;
		String sql = "select * from tbl_order where station_id = " + stationId;
		List list = this.sqlQuery(Order.class, this.orderService, sql, 1);
		for (Object obj : list) {
			Order order = (Order) obj;
			Object rm_ret = this.removeOrderAndRelation(order.getId());
		}
		this.stationService.removeStation(stationId);

		ret = 1;

		return ret;
	}

	public Object removeDanAndRelatedOnes(Integer danId) {
		Object ret = null;
		String sql = "select * from tbl_station where dan = " + danId;

		List list = this.sqlQuery(Station.class, this.stationService, sql, 1);
		for (Object obj : list) {
			Station station = (Station) obj;
			Object rm_ret = this.removeStationAndRelatedOnes(station.getId());

		}
		sql = "select * from tbl_user where dan = " + danId;
		list = this.sqlQuery(User.class, this.userService, sql, 1);
		for (Object obj : list) {
			User user = (User) obj;
			Object rm_ret = this.removeUser(user.getId());
		}
		this.danService.removeDan(danId);

		ret = 1;

		return ret;
	}

	public Object removeLineAndRelatedOnes(Integer lineId) {
		Object ret = null;
		String sql = "select * from tbl_dan where line = " + lineId;
		List list = this.sqlQuery(Dan.class, this.danService, sql, 1);
		for (Object obj : list) {
			Dan dan = (Dan) obj;
			Object rm_ret = this.removeDanAndRelatedOnes(dan.getId());
		}

		sql = "select * from tbl_ct where line = " + lineId;
		list = this.sqlQuery(Ct.class, this.ctService, sql, 1);
		for (Object obj : list) {
			Ct ct = (Ct) obj;
			this.ctService.removeCt(ct.getId());
		}
		this.lineService.removeLine(lineId);

		ret = 1;

		return ret;
	}

	public Object removeUser(Integer userId) {
		Object ret = null;
		User user = this.userService.getUserById(userId);
		if (user != null) {
			if (user.getType() == Constants.USERTYPE_SPECIALIST) {
				this.removeWorkerUserAndRelatedOnes(user.getId());
			} else if (user.getType() == Constants.USERTYPE_MAINTENANCE) {
				this.removeMaintenanceUserAndRelatedOnes(userId);
			} else if (user.getType() == Constants.USERTYPE_PRODUCTION) {
				this.removeProductionUserAndRelatedOnes(userId);
			} else {
				// other users quality manager
				this.removeWorkerUserAndRelatedOnes(user.getId());
			}
		}

		return ret;
	}

	public Object removeWorkerUserAndRelatedOnes(Integer userId) {
		Object ret = null;
		User user = this.userService.getUserById(userId);
		if (user != null) {
			List list = this.getTypedUser(user, UserType.PRODUCTION);
			User user_pro = null;
			if (list.size() > 0) {
				user_pro = (User) list.get(0);
			}

			String sql = "select * from tbl_order where user_id = " + userId;
			list = this.sqlQuery(Order.class, this.orderService, sql, 1);
			for (Object obj : list) {
				Order order = (Order) obj;
				this.removeOrderAndRelation(order.getId());
			}

			// relation table remove
			sql = "select * from tbl_order_relation" + " where receiver_id = " + userId;
			list = this.sqlQuery(OrderRelation.class, this.orderRelationService, sql, 1);
			for (Object obj : list) {
				OrderRelation orderRelation = (OrderRelation) obj;
				if (user_pro != null) {
					if (orderRelation.getSender_id() == user_pro.getId()) {
						// when order is sent to direct to worker
						this.removeOrderAndRelation(orderRelation.getOrder_id());
					} else {
						this.orderRelationService.removeOrderRelation(orderRelation.getId());
					}
				} else {
					this.orderRelationService.removeOrderRelation(orderRelation.getId());
				}
			}
		}

		// remove worker
		this.userService.removeUser(userId);
		ret = 1;

		return ret;
	}

	public Object removeMaintenanceUserAndRelatedOnes(Integer userId) {
		Object ret = null;
		User user = this.userService.getUserById(userId);
		if (user != null && (user.getType() == Constants.USERTYPE_MAINTENANCE
				|| user.getType() == Constants.USERTYPE_PRODUCTION)) { // maintenance
			
			List list = this.getTypedUser(user, UserType.PRODUCTION);
			User user_pro = null;
			if (list.size() > 0) {
				user_pro = (User) list.get(0);
			}
			// User
			// first find user under him
			String sql = "";
			sql = "select * from tbl_user " + " where dan = " + user.getDan() + " and type = "
					+ Constants.USERTYPE_SPECIALIST;

			list = this.sqlQuery(User.class, this.userService, sql, 1);
			for (Object obj : list) {
				User worker = (User) obj;
				this.removeWorkerUserAndRelatedOnes(worker.getId());
			}

			// remove orders related user
			sql = "select * from tbl_order where user_id = " + userId;
			list = this.sqlQuery(Order.class, this.orderService, sql, 1);
			for (Object obj : list) {
				Order order = (Order) obj;
				this.removeOrderAndRelation(order.getId());
			}

			// relation table remove
			sql = "select * from tbl_order_relation" + " where receiver_id = " + userId;
			list = this.sqlQuery(OrderRelation.class, this.orderRelationService, sql, 1);
			for (Object obj : list) {
				OrderRelation orderRelation = (OrderRelation) obj;
				if (user_pro != null) {
					if (orderRelation.getSender_id() == user_pro.getId()) {
						// when order is sent to direct to worker
						this.removeOrderAndRelation(orderRelation.getOrder_id());
					} else {
						this.orderRelationService.removeOrderRelation(orderRelation.getId());
					}
				} else {
					this.orderRelationService.removeOrderRelation(orderRelation.getId());
				}
			}

			// remove user
			this.userService.removeUser(user.getId());
		}
		ret = 1;

		return ret;
	}

	public Object removeProductionUserAndRelatedOnes(Integer userId) {
		Object ret = null;
		User user = this.userService.getUserById(userId);
		if (user != null && user.getType() == Constants.USERTYPE_PRODUCTION) { // maintenance
																				// User
			// first find user under him
			String sql = "";
			sql = "select * from tbl_user " + " where dan = " + user.getDan() + " and type = "
					+ Constants.USERTYPE_MAINTENANCE;
			;

			List list = this.sqlQuery(User.class, this.userService, sql, 1);
			for (Object obj : list) {
				User maintenance = (User) obj;
				this.removeMaintenanceUserAndRelatedOnes(maintenance.getId());
			}

			this.removeMaintenanceUserAndRelatedOnes(userId);

		}
		ret = 1;

		return ret;
	}
}
