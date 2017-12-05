package com.hgc.admin.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgc.admin.constants.DbFields;
import com.hgc.admin.database.model.*;
import com.hgc.admin.model.BackendRequest;
import com.hgc.admin.model.LeftMenu;
import com.hgc.admin.model.RestRequest;
import com.hgc.admin.model.Role;
import com.hgc.admin.model.TaOee;

@Component
public class AppApiHelper extends BaseHelperImpl {

	public Object login(RestRequest apiRequest) {
		HashMap<String, Object> whereFields = (HashMap<String, Object>) apiRequest.model;
		if (whereFields.containsKey("serial") && whereFields.containsKey("password")) {
			List list = this.modelQuery(User.class, this.userService, apiRequest.model, 1);
			if (list.size() > 0) {
				User user = (User) list.get(0);
				HashMap<String, Object> uf = this.getUserInfo(user);
				// ret.put("user", user);
				HashMap<String, Object> map_user = this.getHashMapOfObject(user, User.class, null);
				if (uf.containsKey("dan")) {
					map_user.put("model_dan", uf.get("dan"));
				}
				if (uf.containsKey("line")) {
					map_user.put("model_line", uf.get("line"));
				}
				if (uf.containsKey("userrole")) {
					map_user.put("model_userrole", uf.get("userrole"));
				}
				HashMap<String, Object> ret = new HashMap<String, Object>();

				String sql = "select * from tbl_announce " + " order by time desc";
				List list_announce = this.sqlQuery(Announce.class, this.announceService, sql, 1);

				ret.put("response", 200);
				ret.put("user", map_user);
				ret.put("list_announce", list_announce);
				return ret;
			}
		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "serial or password not come");
			return ret;
		}
		return null;
	}

	public HashMap<String, Object> filterModel(RestRequest apiRequest) {
		HashMap<String, Object> ret = new HashMap<String, Object>();
		HashMap<String, Object> nonsafe_model = (HashMap<String, Object>) apiRequest.model;

		DateTime dt = new DateTime();
		// String a = dt.toString();
		// String b = dt.toString("dd:MM:yy");
		// String c = dt.toString("EEE", Locale.CHINA);
		org.joda.time.format.DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		String d = dt.toString(fmt);
		String[] actions1 = { "create_workorder", "save_report" };
		List<String> list_actions1 = Arrays.asList(actions1);
		if (list_actions1.contains(apiRequest.action)) {
			if (!nonsafe_model.containsKey("create_datetime")) {
				nonsafe_model.put("create_datetime", d);
			}
			if (!nonsafe_model.containsKey("modify_datetime")) {
				nonsafe_model.put("modify_datetime", d);
			}
		}

		ret.put("model", nonsafe_model);
		return ret;
	}

	public HashMap<String, Object> getUserInfo(User user) {
		Dan dan = this.danService.getDanById(user.getDan());
		Line line = this.lineService.getLineById(dan.getLine());
		UserRole userRole = this.userRoleService.getUserRoleById(user.getType());
		HashMap<String, Object> ret = new HashMap<String, Object>();

		String p = dan.getName();
		p = dan.getCreate_datetime();
		Object obj = getHashMapOfObject(dan, Dan.class, null);
		ret.put("dan", obj);

		obj = getHashMapOfObject(line, Line.class, null);
		ret.put("line", obj);

		obj = getHashMapOfObject(userRole, UserRole.class, null);
		ret.put("userrole", obj);

		return ret;
	}

	public Object forgot(RestRequest apiRequest) {
		HashMap<String, Object> whereFields = (HashMap<String, Object>) apiRequest.model;
		if (whereFields.containsKey("serial") && whereFields.containsKey("password")) {
			String sql = "select * from tbl_user where serial='" + whereFields.get("serial").toString() + "'";
			List list_user = this.sqlQuery(User.class, this.userService, sql, 1);
			if (list_user.size() > 0) {
				User temp_user = (User) list_user.get(0);
				whereFields.put("id", temp_user.getId());

				Object user = this.modelModify(User.class, this.userService, apiRequest.model, 1);
				if (!user.equals("")) {
					HashMap<String, Object> ret = new HashMap<String, Object>();
					ret.put("model", user);
					ret.put("response", 200);
					return ret;
				}
			}

		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "serial or password not come");
			return ret;
		}
		return null;
	}

	public Object get_workorder(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;
		if (wf.containsKey("dan") && wf.containsKey("current_date") && wf.containsKey("user_id")) {

			Integer user_id = Integer.valueOf(wf.get("user_id").toString());
			Integer dan = Integer.valueOf(wf.get("dan").toString());
			String current_date = wf.get("current_date").toString();

			String temp_date = current_date.substring(0, 10);
			// get workorder for dan
			String sql = "select O.* from tbl_order as O" + " join tbl_user as U ON O.user_id = U.id"
					+ " WHERE U.dan = " + dan + " and substring(O.create_datetime,1,10) = '" + temp_date + "'"
					+ " and U.id = " + user_id;
			List list = this.sqlQuery(Order.class, this.orderService, sql, 1);

			List<Object> list_data = new ArrayList<Object>();
			for (Object obj : list) {
				Order order = (Order) obj;
				HashMap<String, Object> map_order = this.getOrderInfo(order, 0);
				list_data.add(map_order);
			}
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("list", list_data);
			ret.put("response", 200);
			return ret;
		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "dan or current_date, user_id not come");
			return ret;
		}
	}

	public Object save_workorder_report(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;
		if (wf.size() > 0) {
			Object object = this.modelModify(Report.class, this.reportService, apiRequest.model, 1);
			if (!object.equals("")) {
				HashMap<String, Object> ret = new HashMap<String, Object>();
				ret.put("model", object);
				ret.put("response", 200);
				return ret;
			}
		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "Invalid Model");
			return ret;
		}
		return null;
	}

	public Object get_position(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;
		if (wf.containsKey("dan")) {
			Integer dan = Integer.valueOf(wf.get("dan").toString());

			String sql = "select * from tbl_station where dan = " + dan;
			List list = this.sqlQuery(Station.class, this.stationService, sql, 1);
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("list", list);
			ret.put("response", 200);
			return ret;
		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "line or dan not come");
			return ret;
		}
	}

	public Object workorder_pos(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;
		if (wf.containsKey("station_id") && wf.containsKey("user_id")) {
			List list = this.modelQuery(Order.class, this.orderService, apiRequest.model, 1);
			List<Object> list_data = new ArrayList<Object>();
			for (Object obj : list) {
				Order order = (Order) obj;
				HashMap<String, Object> map_order = this.getOrderInfo(order, 0);
				list_data.add(map_order);
			}

			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("list", list_data);
			ret.put("response", 200);
			return ret;

		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "station_id or user_id not come");
			return ret;
		}
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

	public Object create_workorder(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;
		if (wf.containsKey("receiver_id")) {
			// hgc
			HashMap<String, Object> non_safe = this.filterModel(apiRequest);
			Object model_order = this.modelNew(Order.class, this.orderService, non_safe.get("model"), 1);
			String receiver_id = wf.get("receiver_id").toString();
			if (model_order != null) {
				Order temp_order = (Order) model_order;
				Order order = this.orderService.getOrderById(temp_order.getId());
				// create order Relation
				OrderRelation or = new OrderRelation();
				or.setSender_id(order.getUser_id());
				or.setReceiver_id(Integer.valueOf(receiver_id));
				or.setOrder_id(order.getId());
				String d = this.getCurrentTime(0);
				or.setS_time(d);
				or.setDeleted(0);
				or.setCreate_datetime(d);
				or.setModify_datetime(d);
				Object model_or = this.modelNew(OrderRelation.class, this.orderService,
						this.getHashMapOfObject(or, OrderRelation.class, null), 1);
				if (model_or != null) {
					HashMap<String, Object> ret = new HashMap<String, Object>();

					HashMap<String, Object> map_model = this.getOrderInfo(order, 0);
					ret.put("model", map_model);
					ret.put("response", 200);
					return ret;
				}
			}
		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "Invalid Model");
			return ret;
		}
		return null;
	}

	public Object order_detail(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;
		if (wf.size() > 0) {
			List list = this.modelQuery(Order.class, this.orderService, apiRequest.model, 1);
			if (list.size() > 0) {
				Order order = (Order) list.get(0);
				HashMap<String, Object> map_order = this.getOrderInfo(order, 1);

				HashMap<String, Object> ret = new HashMap<String, Object>();
				ret.put("model", map_order);

				ret.put("response", 200);
				return ret;
			}
		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "Invalid Model");
			return ret;
		}
		return null;
	}

	public Object order_update(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;
		if (wf.size() > 0) {
			String order_id = wf.get("id").toString();
			String reason_id = wf.get("reason_id").toString();
			ReasonType rt = this.reasonTypeService.getReasonTypeById(Integer.valueOf(reason_id));
			if (reason_id.equals("3")) {
				// this is p
				wf.put("complete", this.getCurrentTime(0));
			}

			wf.put("status", "2");
			Object object = this.modelModify(Order.class, this.orderService, apiRequest.model, 1);
			if (!object.equals("")) {
				if (reason_id.equals("3")) {
					// this is p
					String sql = "select * from tbl_order_relation " + " where order_id = '" + order_id + "'";
					List list = this.sqlQuery(OrderRelation.class, this.orderRelationService, sql, 1);
					String d = this.getCurrentTime(0);
					for (Object obj : list) {
						OrderRelation or = (OrderRelation) obj;
						or.setR_time(d);
						// update this
						this.orderRelationService.updateOrderRelation(or);
					}
				}

				HashMap<String, Object> ret = new HashMap<String, Object>();
				ret.put("model", object);
				ret.put("response", 200);

				return ret;
			}
		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "Invalid Model");
			return ret;
		}
		return null;
	}

	public Object save_report(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;
		if (wf.size() > 0) {
			HashMap<String, Object> non_safe = this.filterModel(apiRequest);
			Object model = this.modelNew(Report.class, this.reportService, non_safe.get("model"), 1);
			if (model != null) {
				HashMap<String, Object> ret = new HashMap<String, Object>();
				ret.put("model", model);
				ret.put("response", 200);
				return ret;
			}
		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "Invalid Model");
			return ret;
		}
		return null;
	}

	public Object set_password(RestRequest apiRequest) {
		HashMap<String, Object> whereFields = (HashMap<String, Object>) apiRequest.model;
		if (whereFields.containsKey("serial") && whereFields.containsKey("password")) {
			String sql = "select * from tbl_user where serial='" + whereFields.get("serial").toString() + "'";
			List list_user = this.sqlQuery(User.class, this.userService, sql, 1);
			if (list_user.size() > 0) {
				User temp_user = (User) list_user.get(0);
				whereFields.put("id", temp_user.getId());

				Object user = this.modelModify(User.class, this.userService, apiRequest.model, 1);
				if (!user.equals("")) {
					HashMap<String, Object> ret = new HashMap<String, Object>();
					ret.put("model", user);
					ret.put("response", 200);
					return ret;
				}
			}

		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "serial or password not come");
			return ret;
		}
		return null;
	}

	public Object set_phone(RestRequest apiRequest) {
		HashMap<String, Object> whereFields = (HashMap<String, Object>) apiRequest.model;
		if (whereFields.containsKey("id") && whereFields.containsKey("phone")) {
			Object user = this.modelModify(User.class, this.userService, apiRequest.model, 1);
			if (user != null) {
				HashMap<String, Object> ret = new HashMap<String, Object>();
				ret.put("model", user);
				ret.put("response", 200);
				return ret;
			}
		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "id or phone not come");
			return ret;
		}
		return null;
	}

	public Object set_head(RestRequest apiRequest) {
		HashMap<String, Object> whereFields = (HashMap<String, Object>) apiRequest.model;
		if (whereFields.containsKey("id") && whereFields.containsKey("head")) {
			Object user = this.modelModify(User.class, this.userService, apiRequest.model, 1);
			if (user != null) {
				HashMap<String, Object> ret = new HashMap<String, Object>();
				ret.put("model", user);
				ret.put("response", 200);
				return ret;
			}
		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "id or head not come");
			return ret;
		}
		return null;
	}

	public String getNearestTime() {
		String sql = "select R.id,(TIMESTAMPDIFF(	DAY ,DATE_FORMAT( NOW() ,'%Y-%c-%d'	) ,	DATE_FORMAT( R.create_datetime ,'%Y-%c-%d' ))) as DAY"
				+ " ,R.create_datetime " + " from tbl_report as R" + " order by DAY asc limit 5";
		SessionFactory sf = this.transactionManager.getSessionFactory();
		Session session = sf.openSession();
		List<Object> temp = session.createSQLQuery(sql).list();
		String T = null;
		if (temp.size() > 0) {
			Object[] row = (Object[]) temp.get(0);
			String report_id = row[0].toString();
			String sample = "2017-12-05 17:00:00";
			T = row[2].toString().substring(0, 10);
		}
		session.close();
		return T;
	}

	public List getReportForPeriod(String T1, String T2, HashMap<String, Object> wf) {
		List list = new ArrayList();
		if (wf != null) {

			if (wf.containsKey("mode_line")) {
				Integer line_id = Integer.valueOf(wf.get("user_id").toString());
				String sql = "select R.* from tbl_report as R" + " join tbl_user as U on R.user_id = U.id"
						+ " join tbl_dan as D on U.dan = D.id" + " where substring(R.create_datetime,1,10) >= '" + T1
						+ "'" + " and substring(R.create_datetime,1,10) <= '" + T2 + "'" + " and D.line = " + line_id;
				list = this.sqlQuery(Report.class, this.reportService, sql, 1);
			}

		} else {
			String sql = "select R.* from tbl_report as R" + " where substring(R.create_datetime,1,10) >= '" + T1 + "'"
					+ " and substring(R.create_datetime,1,10) <= '" + T2 + "'";
			list = this.sqlQuery(Report.class, this.reportService, sql, 1);
		}
		return list;

	}

	public List sortCalculatedData1(HashMap map_data) {
		List<Object> values = new ArrayList<Object>();
		values.addAll(map_data.values());
		Collections.sort(values, new Comparator<Object>() {
			@Override
			public int compare(Object lhs, Object rhs) {
				// -1 - less than, 1 - greater than, 0 - equal, all inversed for
				// descending
				HashMap<String, Object> hash_lhs = (HashMap<String, Object>) lhs;
				Line line_lhs = (Line) hash_lhs.get("line");

				HashMap<String, Object> hash_rhs = (HashMap<String, Object>) lhs;
				Line line_rhs = (Line) hash_rhs.get("line");

				return line_rhs.getId() > line_lhs.getId() ? -1 : (line_rhs.getId() < line_lhs.getId()) ? 1 : 0;
			}

		});
		return values;
	}

	public Object get_line_detail(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;

		HashMap<String, Object> ret = new HashMap<String, Object>();
		ret.put("response", 400);
		String sql = "";
		String T = this.getNearestTime();

		if (wf.containsKey("T1") && wf.containsKey("T2") && wf.containsKey("time_type") && wf.containsKey("time_value")
				&& wf.containsKey("line_id")) {
			// time_type 1 2 3 4
			// year month week day
			String T1 = wf.get("T1").toString();
			String T2 = wf.get("T2").toString();
			Integer time_type = Integer.valueOf(wf.get("time_type").toString());
			String time_value = wf.get("time_value").toString();
			Integer line_id = Integer.valueOf(wf.get("line_id").toString());

			if (time_type == 4) {
				// day mode
				if (T1.equals("") && T2.equals("")) {
					T1 = T;
					T2 = T;
				} else {
					// T1 T2 will be same
				}

				// get 6 days strings
				List<String> list_pastday = this.getPastDays(6, T1);
				List<Object> list_graph = new ArrayList<Object>();
				for (int i = 0; i < list_pastday.size(); i++) {
					HashMap<String, Object> cond = new HashMap<String, Object>();
					cond.put("mode_line", 1);
					cond.put("line_id", line_id);
					String iT = list_pastday.get(i);
					List list_report = this.getReportForPeriod(iT, iT, cond);
					// this is report table

					HashMap<String, Object> input_param = new HashMap<String, Object>();
					input_param.put("time_type", 4);
					input_param.put("T1", iT);
					input_param.put("T2", iT);
					
					// get tt value
					input_param.put("time_value", iT);
					TaOee tt_forday = this.getTt(input_param);
					
					HashMap<String, Object> map_ret = this.processUserForReport(list_report, input_param);
					HashMap<Integer, Object> map_line = (HashMap<Integer, Object>) map_ret.get("line");
					if (map_line.containsKey(line_id)) {
						// has data
						HashMap<String, Object> hash = (HashMap<String, Object>) map_line.get(line_id);
						HashMap<String, Object> cal_data = (HashMap<String, Object>) hash.get("cal_data");
						HashMap<String, Object> idata = new HashMap<String, Object>();
						idata.put("model_tt", tt_forday);
						idata.put("model_cal", cal_data);
						idata.put("time_value", iT);
						idata.put("time_type", 4);
						list_graph.add(idata);
					} else {
						// no data
						TaOee taoee = new TaOee();
						HashMap<String, Object> idata = new HashMap<String, Object>();
						idata.put("model_tt", tt_forday);
						idata.put("model_cal", taoee);
						idata.put("time_value", iT);
						idata.put("time_type", 4);
						list_graph.add(idata);
					}
					
				}

				HashMap<String, Object> cond = new HashMap<String, Object>();
				cond.put("mode_line", 1);
				cond.put("line_id", line_id);

				List list_report = this.getReportForPeriod(T1, T2, cond);

				HashMap<String, Object> input_param = new HashMap<String, Object>();
				input_param.put("time_type", 4);
				input_param.put("T1", T1);
				input_param.put("T2", T2);
				input_param.put("calculate_dan", 1);
				HashMap<String, Object> map_ret = this.processUserForReport(list_report, input_param);
				HashMap<Integer, Object> map_dan = (HashMap<Integer, Object>) map_ret.get("dan");

				// get dan list for this line
				sql = "select * from tbl_dan where line = " + line_id;
				List list_dan = this.sqlQuery(Dan.class, this.danService, sql, 1);
				List<Object> list_data_dan = new ArrayList<Object>();
				for (Object obj : list_dan) {
					Dan dan = (Dan) obj;
					
					if (map_dan.containsKey(dan.getId())) {
						HashMap<String, Object> hash = (HashMap<String, Object>) map_dan.get(dan.getId());
						HashMap<String, Object> cal_data = (HashMap<String, Object>) hash.get("cal_data");

						HashMap<String, Object> idata = new HashMap<String, Object>();
						idata.put("model_dan", dan);
						idata.put("model_cal", cal_data);
						idata.put("time_value", T1);
						idata.put("time_type", 4);
						list_data_dan.add(idata);
					} else {
						HashMap<String, Object> idata = new HashMap<String, Object>();
						idata.put("model_dan", dan);
						idata.put("model_cal", new TaOee());
						idata.put("time_value", T1);
						idata.put("time_type", 4);
						list_data_dan.add(idata);
					}
				}

				// ret.put("list_data", list_data);
				ret.put("response", 200);
			}

		} else {
			ret.put("message", "T1 T2 time_type time_value line_id not come");
		}

		return ret;
	}

	public Object get_line_detail22(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;

		HashMap<String, Object> ret = new HashMap<String, Object>();
		ret.put("response", 400);
		String sql = "";
		String T = this.getNearestTime();

		if (wf.containsKey("mode")) {
			List<TimeType> list_timetype = this.timeTypeService.listTimeTypes();
			TimeType timetype_year = list_timetype.get(0);
			TimeType timetype_month = list_timetype.get(1);
			TimeType timetype_week = list_timetype.get(2);
			TimeType timetype_day = list_timetype.get(3);

			String mode = wf.get("mode").toString();
			if (mode.equals("1")) {
				// now mode

				List list_report = this.getReportForPeriod(T, T, null);
				// this is report table
				HashMap<String, Object> map_ret = this.processUserForReport(list_report, null);
				HashMap<Integer, Object> map_data = (HashMap<Integer, Object>) map_ret.get("line");
				// get tt value
				List<Object> values = new ArrayList<Object>();
				if (T != null) {

					Iterator it = map_data.entrySet().iterator();
					while (it.hasNext()) {
						HashMap.Entry pair = (HashMap.Entry) it.next();
						Integer key = (Integer) pair.getKey();
						HashMap<String, Object> hash = (HashMap<String, Object>) pair.getValue();
						HashMap<String, Object> line = (HashMap<String, Object>) hash.get("model_");
						TaOee taoee = (TaOee) hash.get("cal_data");

						// get tt value
						sql = "select T.* from tbl_tt as T" + " where time_value = '" + T.substring(0, 10) + "'"
								+ " and time_type = " + timetype_day.getId();
						List list_tt = this.sqlQuery(Tt.class, this.ttService, sql, 1);
						if (list_tt.size() > 0) {
							Tt tt = (Tt) list_tt.get(0);
							hash.put("model_tt", tt);
						} else {
							Tt tt = new Tt();
							tt.setTa(0);
							tt.setOee(0);
							hash.put("model_tt", tt);
						}
					}
					values.addAll(map_data.values());
				}

				ret.put("list_data", values);
				ret.put("response", 200);
			} else if (mode.equals("2")) {
				if (wf.containsKey("T1") && wf.containsKey("T2") && wf.containsKey("time_type")
						&& wf.containsKey("time_value")) {
					// time_type 1 2 3 4
					// year month week day
					String T1 = wf.get("T1").toString();
					String T2 = wf.get("T2").toString();
					String time_type = wf.get("time_type").toString();
					String time_value = wf.get("time_value").toString();
					sql = "select T.* from tbl_tt as T" + " where time_value = '" + time_value + "'"
							+ " and time_type = " + time_type;
					List list_tt = this.sqlQuery(Tt.class, this.ttService, sql, 1);
					if (list_tt.size() > 0) {
						Tt tt = (Tt) list_tt.get(0);
						ret.put("model_tt", tt);
					}

					List list_report = this.getReportForPeriod(T1, T2, null);
					// this is report table
					HashMap map_data = this.processUserForReport(list_report, null);
					List list_data = this.sortCalculatedData1(map_data);
					ret.put("list_data", list_data);
					ret.put("response", 200);
				}

			}
		}

		return ret;
	}

	public Object get_line_report(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;

		HashMap<String, Object> ret = new HashMap<String, Object>();
		ret.put("response", 400);
		String sql = "";
		String T = this.getNearestTime();

		List<TimeType> list_timetype = this.timeTypeService.listTimeTypes();
		TimeType timetype_year = list_timetype.get(0);
		TimeType timetype_month = list_timetype.get(1);
		TimeType timetype_week = list_timetype.get(2);
		TimeType timetype_day = list_timetype.get(3);

		// get tt value
		List<Object> values = new ArrayList<Object>();
		if (T != null) {
			List list_report = this.getReportForPeriod(T, T, null);
			// this is report table
			HashMap<String, Object> input_param = new HashMap<String, Object>();
			input_param.put("time_type", 4);
			input_param.put("T1", T);
			input_param.put("T2", T);
			HashMap<String, Object> map_ret = this.processUserForReport(list_report, input_param);
			HashMap<Integer, Object> map_data = (HashMap<Integer, Object>) map_ret.get("line");

			Iterator it = map_data.entrySet().iterator();
			while (it.hasNext()) {
				HashMap.Entry pair = (HashMap.Entry) it.next();
				Integer key = (Integer) pair.getKey();
				HashMap<String, Object> hash = (HashMap<String, Object>) pair.getValue();
				HashMap<String, Object> line = (HashMap<String, Object>) hash.get("model_");
				TaOee taoee = (TaOee) hash.get("cal_data");

				// get tt value
				sql = "select T.* from tbl_tt as T" + " where time_value = '" + T.substring(0, 10) + "'"
						+ " and time_type = " + timetype_day.getId();
				List list_tt = this.sqlQuery(Tt.class, this.ttService, sql, 1);
				if (list_tt.size() > 0) {
					Tt tt = (Tt) list_tt.get(0);
					hash.put("model_tt", tt);
				} else {
					Tt tt = new Tt();
					tt.setTa(0);
					tt.setOee(0);
					hash.put("model_tt", tt);
				}
			}
			values.addAll(map_data.values());
		}

		ret.put("list_data", values);
		ret.put("response", 200);

		return ret;
	}

	public TaOee getTt(HashMap<String, Object> wf) {
		String T1 = wf.get("T1").toString();
		String T2 = wf.get("T2").toString();
		String time_type = wf.get("time_type").toString();
		String time_value = wf.get("time_value").toString();
		String sql = "select T.* from tbl_tt as T" + " where time_value = '" + time_value + "'"
				+ " and time_type = " + time_type;
		List list_tt = this.sqlQuery(Tt.class, this.ttService, sql, 1);
		if (list_tt.size() > 0) {
			Tt tt = (Tt) list_tt.get(0);
			TaOee taoee = new TaOee();
			taoee.setTa(tt.getTa());
			return taoee;
		}else{
			TaOee taoee = new TaOee();
			return taoee;
		}
	}
	public HashMap<String, Object> processUserForReport(List list, HashMap<String, Object> wf) {
		// ASSUME LIST REPORT AS SAME DAYS
		HashMap<Integer, Object> map_linedata = new HashMap<Integer, Object>();
		HashMap<Integer, Object> map_dandata = new HashMap<Integer, Object>();
		HashMap<Integer, Object> map_order = new HashMap<Integer, Object>();
		Integer time_type = Integer.valueOf(wf.get("time_type").toString());
		String T1 = wf.get("T1").toString();
		String T2 = wf.get("T2").toString();
		for (Object obj : list) {
			Report report = (Report) obj;
			String sql = "";
			if (time_type == 4) {
				sql = "select sum(TIMESTAMPDIFF(MINUTE, O.create_datetime, O.complete)) as sum1"
						+ " from tbl_order as O" 
						+ " where O.user_id = " + report.getUser_id() 
						+ " and O.status = 2 "
						+ " and substring(O.create_datetime,1,10) = '" + T1 + "'";
			}
			SessionFactory sessionFactory = this.transactionManager.getSessionFactory();
			Session session = sessionFactory.openSession();
			List<Object> ret = new ArrayList<Object>();

			int order_min = 0;
			try {
				ret = session.createSQLQuery(sql).list();
				if (ret.size() > 0) {
					Object[] iraw_row = (Object[]) ret.get(0);
					Float sum1 = Float.parseFloat(iraw_row[0].toString());
					order_min = sum1.intValue();
				}
				session.close();

			} catch (Exception e) {
				session.close();
			}

			int work_min = this.getTimeDifference(report.getLast_unload_time(), report.getFirst_load_time(), 1);
			int lunch_min = report.getLunch_time();
			double ct_value = 0;
			// 2017-12-04 14:57:00
			//
			String report_time = report.getCreate_datetime();
			// get day part.
			User reporter = this.userService.getUserById(report.getUser_id());
			Dan dan = this.danService.getDanById(reporter.getDan());
			Line line = this.lineService.getLineById(dan.getLine());
			sql = "Select C.* from ct where line = " + line.getId();
			List list_ct = this.sqlQuery(Ct.class, this.ctService, sql, 1);
			if (list_ct.size() > 0) {
				Ct ct = (Ct) list_ct.get(0);
				String report_hr = report_time.substring(12, 13);
				if (Integer.parseInt(report_hr) > 12) {
					ct_value = ct.getV2();
				} else {
					ct_value = ct.getV1();
				}
			}
			double ta = (work_min - lunch_min - order_min) / (work_min - lunch_min);
			double oee = report.getOutput() * ct_value / (work_min - lunch_min);

			if (map_linedata.containsKey(line.getId())) {
				List<TaOee> ilist = (List<TaOee>) map_linedata.get(line.getId());
				TaOee taoee = new TaOee();
				taoee.setTa(ta);
				taoee.setOee(oee);
				ilist.add(taoee);
			} else {
				List<TaOee> ilist = new ArrayList<TaOee>();
				TaOee taoee = new TaOee();
				taoee.setTa(ta);
				taoee.setOee(oee);
				ilist.add(taoee);
				map_linedata.put(line.getId(), ilist);
			}

			if (wf.containsKey("calculate_dan")) {
				if (map_dandata.containsKey(dan.getId())) {
					List<TaOee> ilist = (List<TaOee>) map_dandata.get(dan.getId());
					TaOee taoee = new TaOee();
					taoee.setTa(ta);
					taoee.setOee(oee);
					ilist.add(taoee);
				} else {
					List<TaOee> ilist = new ArrayList<TaOee>();
					TaOee taoee = new TaOee();
					taoee.setTa(ta);
					taoee.setOee(oee);
					ilist.add(taoee);
					map_dandata.put(dan.getId(), ilist);
				}
			}
		}

		//

		HashMap<Integer, Object> map_line_ret = new HashMap<Integer, Object>();
		Iterator it = map_linedata.entrySet().iterator();

		while (it.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry) it.next();
			Integer key = (Integer) pair.getKey();
			List<TaOee> ilist = (List<TaOee>) pair.getValue();
			double sum_ta = 0;
			double sum_oee = 0;
			double ta = 0;
			double oee = 0;
			for (TaOee taoee : ilist) {
				sum_ta = sum_ta + taoee.getTa();
				sum_oee = sum_oee + taoee.getOee();
			}
			if (sum_ta > 0) {
				ta = sum_ta / ilist.size();
			}
			if (sum_oee > 0) {
				oee = sum_oee / ilist.size();
			}
			TaOee taoee = new TaOee();
			taoee.setOee(sum_oee);
			taoee.setTa(sum_ta);

			HashMap<String, Object> hash = new HashMap<String, Object>();
			Line line = this.lineService.getLineById(key);
			hash.put("model_line", this.getHashMapOfObject(line, Line.class, null));
			hash.put("cal_data", taoee);

			// list_data.add(hash);
			map_line_ret.put(key, hash);
		}

		HashMap<Integer, Object> map_dan_ret = new HashMap<Integer, Object>();
		if (wf.containsKey("calculate_dan")) {
			it = map_dan_ret.entrySet().iterator();
			while (it.hasNext()) {
				HashMap.Entry pair = (HashMap.Entry) it.next();
				Integer key = (Integer) pair.getKey();
				List<TaOee> ilist = (List<TaOee>) pair.getValue();
				double sum_ta = 0;
				double sum_oee = 0;
				double ta = 0;
				double oee = 0;
				for (TaOee taoee : ilist) {
					sum_ta = sum_ta + taoee.getTa();
					sum_oee = sum_oee + taoee.getOee();
				}
				if (sum_ta > 0) {
					ta = sum_ta / ilist.size();
				}
				if (sum_oee > 0) {
					oee = sum_oee / ilist.size();
				}
				TaOee taoee = new TaOee();
				taoee.setOee(sum_oee);
				taoee.setTa(sum_ta);

				HashMap<String, Object> hash = new HashMap<String, Object>();
				Dan dan = this.danService.getDanById(key);
				hash.put("model_dan", this.getHashMapOfObject(dan, Dan.class, null));
				hash.put("cal_data", taoee);

				// list_data.add(hash);
				map_dan_ret.put(key, hash);
			}
		}

		HashMap<String, Object> map_ret = new HashMap<String, Object>();
		map_ret.put("line", map_line_ret);
		map_ret.put("dan", map_dan_ret);
		return map_ret;
	}

	public Object search(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;
		if (wf.containsKey("key")) {
			String key = wf.get("key").toString();

			String term = "%" + key + "%";
			String sql = "select U.* from tbl_user as U " + " where U.name like '" + term + "'" + " order by U.id asc";
			List list = this.sqlQuery(Order.class, this.orderService, sql, 1);

			HashMap<String, Object> ret = new HashMap<String, Object>();
			if (list.size() > 0) {
				// get info for these users
				String T = this.getNearestTime();
				HashMap map_data = new HashMap();
				if (T != null) {
					List list_report = this.getReportForPeriod(T, T, null);
					HashMap<String, Object> input_param = new HashMap<String, Object>();
					input_param.put("time_type", 4);
					input_param.put("T1", T);
					input_param.put("T2", T);
					map_data = this.processUserForReport(list_report, input_param);
				}
				List<Object> list_data = new ArrayList<Object>();
				for (Object obj : list) {
					User user = (User) obj;
					Dan dan = this.danService.getDanById(user.getDan());
					Line line = this.lineService.getLineById(dan.getLine());

					HashMap<String, Object> map_user = new HashMap<String, Object>();
					if (map_data.containsKey(line.getId())) {
						HashMap<String, Object> hash = (HashMap<String, Object>) map_data.get(line.getId());
						map_user.put("cal_data", hash);
					} else {
						TaOee taoee = new TaOee();

						HashMap<String, Object> hash = new HashMap<String, Object>();
						hash.put("model_line", this.getHashMapOfObject(line, Line.class, null));
						hash.put("cal_data", taoee);
						map_user.put("cal_data", hash);
					}
					list_data.add(user);
				}
				ret.put("list_user", list_data);

			} else {
				// work
				sql = "select O.* from tbl_order as O" + " where O.p_desc like '" + term + "'";
				list = this.sqlQuery(Order.class, this.orderService, sql, 1);
				ret.put("list_order", list);
			}

			ret.put("response", 200);
			return ret;
		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "key not come");
			return ret;
		}
	}

	public Object get_repair_worker(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;
		if (wf.containsKey("id")) {
			String id = wf.get("id").toString();
			User user = this.userService.getUserById(Integer.parseInt(id));
			Dan dan = this.danService.getDanById(user.getDan());
			List list = this.getTypedUser(user, UserType.SPECIALIST);
			if (list != null) {
				HashMap<String, Object> ret = new HashMap<String, Object>();
				List<Object> list_data = new ArrayList<Object>();
				for (Object obj : list) {
					User iuser = (User) obj;
					HashMap<String, Object> ihash = this.getHashMapOfObject(iuser, User.class, null);
					UserRole ur = this.userRoleService.getUserRoleById(iuser.getType());
					ihash.put("model_userrole", this.getHashMapOfObject(ur, UserRole.class, null));
					list_data.add(ihash);
				}
				ret.put("list", list_data);
				ret.put("response", 200);
				return ret;
			}
		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "id not come");
			return ret;
		}
		return null;
	}

	public Object assign_workorder(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;
		if (wf.containsKey("sender_id") && wf.containsKey("receiver_id") && wf.containsKey("order_id")) {
			// &&wf.containsKey("r_time")
			Integer sender_id = Integer.valueOf(wf.get("sender_id").toString());
			Integer receiver_id = Integer.valueOf(wf.get("receiver_id").toString());
			Integer order_id = Integer.valueOf(wf.get("order_id").toString());

			String nulltime = "0000-00-00 00:00:00";
			String sql = "select R.* from tbl_order_relation as R" + " where R.order_id = '" + order_id + "'"
					+ " and R.receiver_id = " + sender_id;
			List list = this.sqlQuery(OrderRelation.class, this.orderRelationService, sql, 1);
			String d = this.getCurrentTime(0);
			if (list.size() > 0) {
				OrderRelation temp = (OrderRelation) list.get(0);
				temp.setR_time(d);
				this.orderRelationService.updateOrderRelation(temp);

				OrderRelation or = new OrderRelation();
				or.setSender_id(sender_id);
				or.setReceiver_id(receiver_id);
				or.setOrder_id(order_id);
				or.setS_time(d);
				or.setDeleted(0);
				or.setCreate_datetime(d);
				or.setModify_datetime(d);
				if (sender_id == receiver_id) {
					or.setR_time(d);
				}
				Object model_or = this.modelNew(OrderRelation.class, this.orderService,
						this.getHashMapOfObject(or, OrderRelation.class, null), 1);
				if (model_or != null) {
					HashMap<String, Object> ret = new HashMap<String, Object>();
					ret.put("response", 200);
					return ret;
				}
			}
		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "sender_id receiver_id order_id not come");
			return ret;
		}
		return null;
	}

	public Object get_workorder_create_info(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;
		if (wf.containsKey("id")) {
			Integer userid = Integer.valueOf(wf.get("id").toString());
			User user = this.userService.getUserById(userid);
			// expect user is boss
			Dan dan = this.danService.getDanById(user.getDan());
			// Line line = this.lineService.getLineById(dan.getLine());
			UserRole ur_main = getRole(UserType.MAINTENANCE);
			UserRole ur_quality = getRole(UserType.QUALITY);
			UserRole ur_specialist = getRole(UserType.SPECIALIST);

			String sql = "SELECT * FROM `tbl_user`" + " where dan=" + dan.getId() + " and type = " + ur_main.getId();
			List<User> list = this.sqlQuery(User.class, this.userService, sql, 1);
			User user_repair = null;
			if (list != null && list.size() > 0) {
				user_repair = list.get(0);
			}

			sql = "SELECT * FROM `tbl_user`" + " where dan=" + dan.getId() + " and type = " + ur_quality.getId();
			List list_quality = this.sqlQuery(User.class, this.userService, sql, 1);

			sql = "SELECT * FROM `tbl_user`" + " where dan=" + dan.getId() + " and type = " + ur_specialist.getId();
			List list_worker = this.sqlQuery(User.class, this.userService, sql, 1);

			// get feedback list for P
			ReasonType et = this.reasonTypeService.getReasonTypeById(3);
			HashMap<String, Object> wFields = new HashMap<String, Object>();
			wFields.put("reason_id", et.getId());
			List feedback_list = this.modelQuery(ErrorType.class, this.errorTypeService, wFields, 1);

			sql = "select * from tbl_reason_type order by id desc";
			List list_reasontype = this.sqlQuery(ReasonType.class, this.reasonTypeService, sql, 1);

			HashMap<String, Object> ret = new HashMap<String, Object>();
			if (user_repair != null) {
				ret.put("user_repair", user_repair);
			}
			ret.put("list_worker", list_worker);
			if (list_reasontype != null) {
				ret.put("list_reasontype", list_reasontype);
			}
			if (list_quality != null) {
				ret.put("list_quality", list_quality);
			}
			if (feedback_list != null) {
				ret.put("list_feedback", feedback_list);
			}
			ret.put("response", 200);
			return ret;
		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "dan or current_date not come");
			return ret;
		}

	}

	public Object workorder_list(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;
		if (wf.containsKey("start_date") && wf.containsKey("end_date") && wf.containsKey("user_id")) {

			String start_date = wf.get("start_date").toString();
			String end_date = wf.get("end_date").toString();
			Integer user_id = Integer.valueOf(wf.get("user_id").toString());

			start_date = start_date.substring(0, 10);
			end_date = end_date.substring(0, 10);

			// get workorder for dan
			String sql = "select O.* from tbl_order as O" + " where substring(O.create_datetime,1,10) >= '" + start_date
					+ "'" + " and substring(O.create_datetime,1,10) <= '" + end_date + "'" + " and O.user_id = "
					+ user_id;
			List list = this.sqlQuery(Order.class, this.orderService, sql, 1);
			HashMap<String, Object> ret = new HashMap<String, Object>();
			List<Object> list_data = new ArrayList<Object>();
			for (Object obj : list) {
				Order order = (Order) obj;
				list_data.add(this.getOrderInfo(order, 0));
			}
			ret.put("list", list_data);
			ret.put("response", 200);
			return ret;
		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "start_date or end_date, user_id not come");
			return ret;
		}
	}

	public Object get_order_for_worker(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;
		if (wf.containsKey("id")) {

			String nulltime = "0000-00-00 00:00:00";
			Integer userid = Integer.valueOf(wf.get("id").toString());
			// userid tc_maintenance
			// order for user , initial status
			//
			String sql = "select O.* from tbl_order as O" + " join tbl_order_relation as R on O.id = R.order_id"
					+ " where R.r_time = '" + nulltime + "'" + " and R.receiver_id = " + userid
					+ " and R.sender_id != R.receiver_id"
					+ " group by O.id ";
			List list0 = this.sqlQuery(Order.class, this.orderService, sql, 1);

			sql = "select O.* from tbl_order as O" + " join tbl_order_relation as R on O.id = R.order_id"
					+ " where R.r_time != '" + nulltime + "'" + " and O.complete = '" + nulltime + "'"
					+ " and R.receiver_id = " + userid
					+ " group by O.id "
					;
			List list1 = this.sqlQuery(Order.class, this.orderService, sql, 1);

			sql = "select O.* from tbl_order as O" + " join tbl_order_relation as R on O.id = R.order_id"
					+ " where R.r_time != '" + nulltime + "'" + " and O.complete != '" + nulltime + "'"
					+ " and R.receiver_id = " + userid
					+ " group by O.id ";
			List list2 = this.sqlQuery(Order.class, this.orderService, sql, 1);

			List<Object> list_data0 = new ArrayList<Object>();
			for (int i = 0; i < list0.size(); i++) {
				Order order = (Order) list0.get(i);
				list_data0.add(this.getOrderInfo(order, 0));
			}

			List<Object> list_data1 = new ArrayList<Object>();
			for (int i = 0; i < list1.size(); i++) {
				Order order = (Order) list1.get(i);
				list_data1.add(this.getOrderInfo(order, 0));
			}

			List<Object> list_data2 = new ArrayList<Object>();
			for (int i = 0; i < list2.size(); i++) {
				Order order = (Order) list2.get(i);
				list_data2.add(this.getOrderInfo(order, 0));
			}

			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("list0", list_data0);
			ret.put("list1", list_data1);
			ret.put("list2", list_data2);
			ret.put("response", 200);
			return ret;
		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "start_date or end_date not come");
			return ret;
		}
	}

	public Object get_error_type(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;
		if (wf.containsKey("reason_id")) {

			Integer reason_id = Integer.valueOf(wf.get("reason_id").toString());
			// get workorder for dan
			String sql = "select * from tbl_error_type " + " where reason_id= '" + reason_id + "'";
			List list = this.sqlQuery(ErrorType.class, this.errorTypeService, sql, 1);

			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("list", list);
			ret.put("response", 200);
			return ret;
		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "reason_id  not come");
			return ret;
		}
	}

	public Object get_order_detail(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;
		if (wf.containsKey("id")) {

			Integer id = Integer.valueOf(wf.get("id").toString());

			Order order = this.orderService.getOrderById(id);

			// get workorder for dan
			String sql = "select * from tbl_order_relation " + " where order_id = '" + order.getId() + "'";
			List list = this.sqlQuery(OrderRelation.class, this.orderRelationService, sql, 1);

			HashMap<String, Object> ret = new HashMap<String, Object>();
			if (list.size() > 0) {
				ret.put("relation", list.get(0));
			}
			ret.put("order", order);
			ret.put("response", 200);
			return ret;
		} else {
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("message", "start_date or end_date not come");
			return ret;
		}
	}

	public Object get_all_user(RestRequest apiRequest) {
		HashMap<String, Object> wf = (HashMap<String, Object>) apiRequest.model;

		List<Line> list_line = this.lineService.listLines();
		HashMap<Integer, Dan> map_dan = this.danService.mapDans();

		List<Object> list_data = new ArrayList<Object>();
		for (Line line : list_line) {
			String sql = "select U.* from tbl_user as U " + " JOIN tbl_dan as D ON D.id = U.dan"
					+ " JOIN tbl_line as L ON L.id = D.line" + " where L.id =" + line.getId();
			List list = this.sqlQuery(User.class, this.userService, sql, 1);

			List<Object> ilist_user = new ArrayList<Object>();
			for (Object obj : list) {
				User user = (User) obj;
				Integer dan_id = Integer.valueOf(user.getDan());
				Dan dan = map_dan.get(dan_id);
				HashMap<String, Object> map_user = this.getHashMapOfObject(user, User.class, null);
				map_user.put("model_dan", this.getHashMapOfObject(dan, Dan.class, null));
				ilist_user.add(map_user);
			}
			HashMap<String, Object> map_line = this.getHashMapOfObject(line, Line.class, null);
			map_line.put("list_user", ilist_user);
			list_data.add(map_line);
		}

		HashMap<String, Object> ret = new HashMap<String, Object>();
		ret.put("list", list_data);
		ret.put("response", 200);
		return ret;
	}

	public HashMap<String, Object> processMultipartRequest(HttpServletRequest request, String term, String v,
			List<String> file_names) {
		HashMap<String, Object> ret = new HashMap<String, Object>();
		ret.put("response", 400);

		if (term.equals("set_head")) {
			String user_id = request.getParameter("user_id");
			if (file_names.size() > 0) {
				String filename = file_names.get(0);

				HashMap<String, Object> non_safe = new HashMap<String, Object>();
				non_safe.put("ref_type1", User.class.getSimpleName());
				non_safe.put("ref_id1", user_id);
				non_safe.put("type", "0");

				List list = this.modelQuery(Picture.class, this.pictureService, non_safe, 1);
				if (list.size() > 0) {
					// update
					Picture picture = (Picture) list.get(0);
					Picture updateObj = this.pictureService.getPictureById(picture.getId());
					updateObj.setFilename(filename);
					this.pictureService.updatePicture(updateObj);

					non_safe = new HashMap<String, Object>();
					non_safe.put("id", user_id);
					non_safe.put("head", filename);
					Object model_user = this.modelModify(User.class, this.userService, non_safe, 1);

					ret = new HashMap<String, Object>();
					ret.put("model", this.getHashMapOfObject(updateObj, Picture.class, null));
					ret.put("response", 200);
				} else {
					// add new
					Picture picture = new Picture();
					picture.setRef_id1(Integer.valueOf(user_id));
					picture.setRef_type1(User.class.getSimpleName());
					picture.setType(0);
					picture.setFilename(filename);
					picture.setDeleted(0);
					String time = getCurrentTime(0);
					picture.setCreate_datetime(time);
					picture.setModify_datetime(time);
					int id = this.pictureService.addPicture(picture);

					picture.setId(id);
					ret.put("model", picture);

					non_safe = new HashMap<String, Object>();
					non_safe.put("id", user_id);
					non_safe.put("head", filename);
					Object model_user = this.modelModify(User.class, this.userService, non_safe, 1);

					ret.put("response", 200);
				}
			} else {
				ret.put("message", "no file uploaded");
				ret.put("response", 400);
			}
		} else if (term.equals("workorder_feedback")) {
			String order_id = request.getParameter("order_id");
			String error_id = request.getParameter("error_id");
			String feedback = request.getParameter("feedback");

			String time = getCurrentTime(0);
			HashMap<String, Object> non_safe = new HashMap<String, Object>();
			non_safe.put("id", order_id);
			non_safe.put("error_id", error_id);
			non_safe.put("feedback", feedback);
			non_safe.put("complete", time);
			non_safe.put("status", 2);
			Object model = this.modelModify(Order.class, this.orderService, non_safe, 1);
			if (!model.equals("")) {
				// add images

				// first delete all images
				HashMap<String, Object> whereF = new HashMap<String, Object>();
				whereF.put("ref_type1", Order.class.getSimpleName());
				whereF.put("ref_id1", order_id);
				// non_safe.put("type", "0");

				List list = this.modelQuery(Picture.class, this.pictureService, whereF, 1);
				for (Object obj : list) {
					Picture pic = (Picture) obj;
					// delete these items first
					this.pictureService.removePicture(pic.getId());
				}

				// now add images
				List<Picture> list_picture = new ArrayList<Picture>();
				for (int i = 0; i < file_names.size(); i++) {
					String fname = file_names.get(i);

					Picture picture = new Picture();
					picture.setRef_id1(Integer.valueOf(order_id));
					picture.setRef_type1(Order.class.getSimpleName());
					if (fname.endsWith(".jpg")) {
						picture.setType(0);
					} else {
						picture.setType(1);
					}
					
					picture.setFilename(file_names.get(i));
					picture.setDeleted(0);
					time = getCurrentTime(0);
					picture.setCreate_datetime(time);
					picture.setModify_datetime(time);
					int id = this.pictureService.addPicture(picture);

					picture.setId(id);

					list_picture.add(picture);

				}

				// ret.put("list_picture", list_picture);
				// ret.put("model", model);
				Order order = this.orderService.getOrderById(Integer.parseInt(order_id));
				ret.put("model", this.getOrderInfo(order, 1));
				ret.put("response", 200);
			}
		}

		return ret;
	}

}
