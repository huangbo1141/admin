package com.hgc.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgc.admin.constants.Constants;
import com.hgc.admin.database.model.*;
import com.hgc.admin.model.*;
import com.hgc.admin.utils.AccountHelper;
import com.hgc.admin.utils.BaseHelperImpl;

@Controller
@RequestMapping("{term}/{subterm}")
public class AdminController extends BaseAdminController{
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(@PathVariable("term") String term,@PathVariable("subterm") String subterm,Model model,HttpServletRequest request){
		
		HashMap<String,Object> chkAll = checkAll(currentUser,term+"_"+subterm); 
		String chkResult = chkAll.get("result").toString();
		String url = chkAll.get("url").toString();
		if(chkResult.equals("redirect")){
			return url;
		}else{
			// no permission or other
			if(!chkResult.equals("ok")){
				return url;
			}
		}
		System.out.println("current User " + currentUser.getUsername());
		
		setBaseModelData(model,term,subterm);
		setContentModelData(model,term,subterm,request);
		return "layout/default";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String indexPost(@PathVariable("term") String term,@PathVariable("subterm") String subterm,Model model,HttpServletRequest request){
		
		HashMap<String,Object> chkAll = checkAll(currentUser,term+"_"+subterm); 
		String chkResult = chkAll.get("result").toString();
		String url = chkAll.get("url").toString();
		if(chkResult.equals("redirect")){
			return url;
		}else{
			// no permission or other
			if(!chkResult.equals("ok")){
				return url;
			}
		}
		System.out.println("current User " + currentUser.getUsername());
		
		setBaseModelData(model,term,subterm);
		setContentModelData(model,term,subterm,request);
		return "layout/default";
	}
	
	public void setContentModelData(Model model,String term,String subterm,HttpServletRequest request){
		HashMap<String,Object> pageData = new HashMap<String,Object>();
		HashMap<Integer, Menu> map_menu = this.currentUser.getMap_menu();
		try{
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
			
			if(term.equals(menu)){
				if(subterm.equals(menu_smenu)){
					// need to fetch all menu data.
					pageData.put("list_data", backendApiHelper.menuService.listMenus());
				}else if(subterm.equals(menu_role)){
					List<AdminRole> list_adminRole = backendApiHelper.adminRoleService.listAdminRoles();
					
					List<Object> list_data = new ArrayList<Object>();
					for(int i=0; i<list_adminRole.size(); i++){
						AdminRole ar = list_adminRole.get(i);
						List<LeftMenu> tlist = AccountHelper.parseRoles(ar,this.currentUser.getMap_menu());
						HashMap<Integer,LeftMenu> m = new HashMap<Integer,LeftMenu>();
						for(int j=0; j<tlist.size();j++){
							LeftMenu lm = tlist.get(j);
							m.put(lm.getMenu().getId(), lm);
							for(int k=0;k<lm.getSubmenu().size();k++){
								LeftMenu lm_s = lm.getSubmenu().get(k);
								m.put(lm_s.getMenu().getId(), lm_s);
							}
						}
						Iterator it = m.entrySet().iterator();
						while (it.hasNext()) {
							HashMap.Entry pair = (HashMap.Entry) it.next();
							Integer key = (Integer) pair.getKey();
							LeftMenu lm = (LeftMenu) pair.getValue();
							lm.setSubmenu(null);
						}
						HashMap<String,Object> item = new HashMap<String,Object>();
						
						item.put("model", ar);
						item.put("roles", m);
						list_data.add(item);
					}
					pageData.put("list_data", list_data);
					
					// get all menu
					List<Menu> list_menu = new ArrayList<Menu>(map_menu.values());
					HashMap<Integer, LeftMenu> t_menu = new HashMap<Integer, LeftMenu>();
					for(int i=0; i<list_menu.size();i++){
						Menu imenu = list_menu.get(i);
						if(imenu.getParent() == 0){
							// search parent first
							LeftMenu lm = new LeftMenu();
							lm.setMid(imenu.getId());
							try {
								ObjectMapper objectMapper = new ObjectMapper();
								List<Integer> acs = objectMapper.readValue(imenu.getAction(),
										new TypeReference<List<Integer>>() {
										});

								lm.setActions(acs);
							} catch (Exception e) {
								e.printStackTrace();
							}
							lm.setMenu(imenu);
							t_menu.put(imenu.getId(), lm);
						}
					}
					for(int i=0; i<list_menu.size();i++){
						Menu imenu = list_menu.get(i);
						if(imenu.getParent() != 0){
							// search sub menu
							if(t_menu.containsKey(imenu.getParent())){
								LeftMenu lm_parent = t_menu.get(imenu.getParent());
								LeftMenu lm = new LeftMenu();
								lm.setMid(imenu.getId());
								lm.setMenu(imenu);
								try {
									ObjectMapper objectMapper = new ObjectMapper();
									List<Integer> acs = objectMapper.readValue(imenu.getAction(),
											new TypeReference<List<Integer>>() {
											});

									lm.setActions(acs);
								} catch (Exception e) {
									e.printStackTrace();
								}
								lm_parent.getSubmenu().add(lm);
							}
						}
					}
					List<LeftMenu> list_lmenu = new ArrayList<LeftMenu>(t_menu.values());
					Collections.sort(list_lmenu, new Comparator<LeftMenu>() {
						@Override
						public int compare(LeftMenu lhs, LeftMenu rhs) {
							// -1 - less than, 1 - greater than, 0 - equal, all inversed for
							// descending
							return rhs.getMenu().getSort() > lhs.getMenu().getSort() ? -1 : (rhs.getMenu().getSort() < lhs.getMenu().getSort()) ? 1 : 0;
						}
					});
					pageData.put("list_lmenu", list_lmenu);
					
					
					
				}else if(subterm.equals(menu_adminuser)){
					List<AdminRole> list_role = backendApiHelper.adminRoleService.listAdminRoles();
					
					AdminRole ar = null;
					if(request.getParameter("role")!=null){
						String role_id = request.getParameter("role");
						try{
							ar = backendApiHelper.adminRoleService.getAdminRoleById(Integer.valueOf(role_id));	
						}catch(Exception ex){
							
						}
						
					}
					
					if(list_role.size()>0&&ar == null){
						ar = list_role.get(0);
					}
					if(ar!=null){
						String sql = "SELECT id,name,phone,log_time,role,username,password,deleted,create_datetime,modify_datetime" 
								+ " FROM `tbl_admin_user` "
								+" where role = "+ ar.getId();
						String[] ids = {"id","name","phone","log_time","role","username","password","deleted","create_datetime","modify_datetime"};
						List<AdminUser> list_adminuser = backendApiHelper.adminUserService.queryAdminUser(sql, ids);
						
						pageData.put("model_role", ar);
						pageData.put("list_adminuser", list_adminuser);
					}
					pageData.put("list_role", list_role);
				}
			}else if(term.equals(line)){
				if(subterm.equals(line)){
					List<Line> list_line = backendApiHelper.lineService.listLines();
					List<Object> list_data = new ArrayList<Object>();

					
					HashMap<Integer,Object> hash = new HashMap<Integer,Object>();
					HashMap<Integer,Line> map_line = backendApiHelper.lineService.mapLines();
					for(Dan dan:this.backendApiHelper.danService.listDans()){
						if(hash.containsKey(dan.getLine())){
							List<Dan> list = (List<Dan>) hash.get(dan.getLine());
							list.add(dan);
						}else{
							List<Dan> list = new ArrayList<Dan>();
							list.add(dan);
							hash.put(dan.getLine(), list);
						}
						Line iline = map_line.get(dan.getLine());
						HashMap<String,Object> ih = new HashMap<String,Object>();
						ih.put("line", iline);
						ih.put("dan", dan);
						list_data.add(ih);
					}
					pageData.put("list_line", list_line);
					pageData.put("list_data", list_data);
					
					String json_dan="";
					try {
						ObjectMapper objectMapper = new ObjectMapper();
						json_dan = objectMapper.writeValueAsString(hash);
					} catch (Exception e) {
						e.printStackTrace();
					}
					pageData.put("json_dan", json_dan);
					Integer tabIndex = 0;
					if (request.getParameter("mt") != null) {
						String mt = request.getParameter("mt").toLowerCase();
						if(mt.equals("line")){
							tabIndex = 0;
						}else if(mt.equals("dan")){
							tabIndex = 1;
						}
					}
					pageData.put("tabIndex", tabIndex);
				}
			}else if(term.equals(workstation)){
				if(subterm.equals(workstation)){
					Line model_line = null;
					Dan model_dan = null;
					List<Station> list_station_all = backendApiHelper.stationService.listStations();
					List<Line> list_line = backendApiHelper.lineService.listLines();
					List<Dan> list_dan = backendApiHelper.danService.listDans();
					List<Station> list_station = new ArrayList<Station>();
					if (request.getParameter("d") != null) {
						String dan_id = request.getParameter("d");
						model_dan = backendApiHelper.danService.getDanById(Integer.parseInt(dan_id));
						int line_id = model_dan.getLine();
						model_line = backendApiHelper.lineService.getLineById(line_id);
					}else{
						if(list_dan.size()>0){
							model_dan = list_dan.get(0);
							int line_id = model_dan.getLine();
							model_line = backendApiHelper.lineService.getLineById(line_id);
						}
					}
					
					if(model_dan!=null){
						String sql = "SELECT `id`,`serial`,`dan`,`deleted`,`create_datetime`,`modify_datetime` FROM `tbl_station` WHERE dan ="
								+model_dan.getId();
						String[] ids = {"id","serial","dan","deleted","create_datetime","modify_datetime"};
						list_station = backendApiHelper.stationService.queryStation(sql,ids);
												
						pageData.put("model_dan", model_dan);
						pageData.put("model_line", model_line);
					}
					pageData.put("list_station_all", list_station_all);
					pageData.put("list_line", list_line);
					pageData.put("list_dan", list_dan);
					
					HashMap<Integer,Dan> map_dan = backendApiHelper.danService.mapDans();
					HashMap<Integer,Line> map_line = backendApiHelper.lineService.mapLines();
					pageData.put("map_dan", map_dan);
					pageData.put("map_line", map_line);
					
					List<Object> list_data = new ArrayList<Object>();
					for(Station imodel:list_station){
						Dan idan = map_dan.get(imodel.getDan());
						Line iline = map_line.get(idan.getLine());
						
						HashMap<String,Object> ih = new HashMap<String,Object>();
						ih.put("dan", idan);
						ih.put("line", iline);
						ih.put("model", imodel);
						list_data.add(ih);
					}
					pageData.put("list_data", list_data);
					HashMap<Integer,Object> hash = backendApiHelper.getMapDanPerLineID();
					pageData.put("map_dan_pLine", hash);
					String json_dan="";
					try {
						ObjectMapper objectMapper = new ObjectMapper();
						json_dan = objectMapper.writeValueAsString(hash);
					} catch (Exception e) {
						e.printStackTrace();
					}
					pageData.put("json_dan", json_dan);
					
					
				}
			}else if(term.equals(userguanli)){
				if(subterm.equals(userguanli)){
					List<User> list_user = new ArrayList<User>();
					if (request.getParameter("s") != null) {
						String s = request.getParameter("s");
						// find user 
						String sql = "SELECT `id`,`name`,`serial`,`type`,`dan`,`deleted`,`create_datetime`,`modify_datetime` FROM `tbl_user`"
								+" where serial like '%"+s+"%' or name like '%"+s+"%'";
						String[] db_fields = {"id","name","serial","type","dan","deleted","create_datetime","modify_datetime"};
						list_user = backendApiHelper.userService.queryUser(sql, db_fields);
												
						pageData.put("s", s);
					}else{
						list_user = backendApiHelper.userService.listUsers();
					}
										
					HashMap<Integer,Dan> map_dan = backendApiHelper.danService.mapDans();
					HashMap<Integer,Line> map_line = backendApiHelper.lineService.mapLines();
					HashMap<Integer,UserRole> map_userrole = backendApiHelper.userRoleService.mapUserRoles();
					
					List<Object> list_data = new ArrayList<Object>();
					for(User imodel:list_user){
						Dan idan = map_dan.get(imodel.getDan());
						Line iline = map_line.get(idan.getLine());
						UserRole iuserrole = map_userrole.get(imodel.getType());
						
						HashMap<String,Object> ih = new HashMap<String,Object>();
						ih.put("dan", idan);
						ih.put("line", iline);
						ih.put("model", imodel);
						ih.put("userrole", iuserrole);
						list_data.add(ih);
					}
					backendApiHelper.lineService.listLines();
					pageData.put("list_userrole", backendApiHelper.userRoleService.listUserRoles());
					List<Line> list_line = backendApiHelper.lineService.listLines();
					
					pageData.put("list_line", list_line);
					pageData.put("list_data", list_data);
					
					HashMap<Integer,Object> hash = backendApiHelper.getMapDanPerLineID();
					pageData.put("map_dan_pLine", hash);
					String json_dan="";
					try {
						ObjectMapper objectMapper = new ObjectMapper();
						json_dan = objectMapper.writeValueAsString(hash);
					} catch (Exception e) {
						e.printStackTrace();
					}
					pageData.put("json_dan", json_dan);
					
					if(list_line.size()>0)
					{
						if(hash.containsKey(list_line.get(0).getId())){
							pageData.put("list_dan", hash.get(list_line.get(0).getId()));
						}
					}
				}
			}else if(term.equals(password)){
				if(subterm.equals(password)){
					if (request.getParameter("password") != null ) {
						String pwd = request.getParameter("password");
						String id = request.getParameter("id");
						// find user 
						HashMap<String,Object> hash = new HashMap<String,Object>();
						hash.put("password", pwd);
						hash.put("id", this.currentUser.getId());
						
						this.backendApiHelper.modelModify(AdminUser.class, backendApiHelper.adminRoleService, hash, 1);
						
						pageData.put("msg", "Password Changed!");
					}
				}
			}else if(term.equals(ct)){
				if(subterm.equals(ct)){
					List<Ct> list_ct = backendApiHelper.ctService.listCts();
					List<Line> list_line = backendApiHelper.lineService.listLines();
					List<Object> list_data = new ArrayList<Object>();
					for(Ct imodel:list_ct){
						Line iline = backendApiHelper.lineService.getLineById(imodel.getLine());
						HashMap<String,Object> hash = new HashMap<String,Object>();
						hash.put("model", imodel);
						hash.put("line", iline);
						list_data.add(hash);
					}
					
					pageData.put("list_ct", list_ct);
					pageData.put("list_line", list_line);
					pageData.put("list_data", list_data);
				}
			}else if(term.equals(tt)){
				if(subterm.equals(tt)){
					List<Tt> list_tt = backendApiHelper.ttService.listTts();
					List<TimeType> list_timetype = backendApiHelper.timeTypeService.listTimeTypes();
					
					// 1 2 3 4  y m w d 
					
					List<Object> list_tab = new ArrayList<Object>();
					for(TimeType timetype:list_timetype){
						List<Tt> list = new ArrayList<Tt>(); 
						for(Tt idata:list_tt){
							if(idata.getTime_type() == timetype.getId()){
								list.add(idata);
							}
						}
						HashMap<String,Object> ih = new HashMap<String,Object>();
						ih.put("list", list);
						ih.put("tab", timetype);
						list_tab.add(ih);
					}
					pageData.put("list_timetype", list_timetype);
					pageData.put("list_tab", list_tab);
					
					int tabIndex = 0;
					if (request.getParameter("mt") != null) {
						String mt = request.getParameter("mt").toLowerCase();
						for(int i=0; i<list_timetype.size();i++){
							TimeType timetype = list_timetype.get(i);
							if(timetype.getTy_name().equals(mt)){
								tabIndex = i;
								break;
							}
						}
					}
					pageData.put("tabIndex", tabIndex);
				}
			}else if(term.equals(faultlib)){
				if(subterm.equals(faultlib)){
					List<ErrorType> list_errortype = backendApiHelper.errorTypeService.listErrorTypes();
					List<ReasonType> list_reasontype = backendApiHelper.reasonTypeService.listReasonTypes();
					
					// 1 2 3 4  y m w d 
					
					List<Object> list_tab = new ArrayList<Object>();
					for(ReasonType type:list_reasontype){
						List<Object> list = new ArrayList<Object>(); 
						for(ErrorType idata:list_errortype){
							if(idata.getReason_id() == type.getId()){
								list.add(idata);
							}
						}
						HashMap<String,Object> ih = new HashMap<String,Object>();
						ih.put("list", list);
						ih.put("tab", type);
						list_tab.add(ih);
					}
					pageData.put("list_reasontype", list_reasontype);
					pageData.put("list_tab", list_tab);
					
					int tabIndex = 0;
					if (request.getParameter("mt") != null) {
						String mt = request.getParameter("mt").toLowerCase();
						for(int i=0; i<list_reasontype.size();i++){
							ReasonType type = list_reasontype.get(i);
							if(type.getId() == Integer.valueOf(mt)){
								tabIndex = i;
								break;
							}
						}
					}
					pageData.put("tabIndex", tabIndex);
				}
			}else if(term.equals(report)){
				if(subterm.equals(report)){
					Line model_line = null;
					List<Report> list_report_all = backendApiHelper.reportService.listReports();
					List<Line> list_line = backendApiHelper.lineService.listLines();
					List<Report> list_report = new ArrayList<Report>();
					if (request.getParameter("line_id") != null) {
						int line_id = Integer.valueOf(request.getParameter("line_id"));
						model_line = backendApiHelper.lineService.getLineById(line_id);
					}else{
						if(list_line.size()>0){
							model_line = list_line.get(0);
						}
					}
					
					String start_day = "";
					String end_day = "";
					if (request.getParameter("start_day") != null) {
						start_day = request.getParameter("start_day").trim();
						if(start_day.length() == 10){
							pageData.put("start_day", start_day);
							start_day = start_day + " 00:00:00";
						}
					}
					if (request.getParameter("end_day") != null) {
						end_day = request.getParameter("end_day").trim();
						if(end_day.length() == 10){
							pageData.put("end_day", end_day);
							end_day = end_day + " 00:00:00";
						}
					}
					
					if(model_line!=null){
						HashMap<String,Object> select = this.backendApiHelper.dbFields.getSelectFields("R", Report.class);
						String str_select = select.get("select").toString();
						String[] ids = (String[])select.get("fields");
						
						String sql = "SELECT "+str_select
								+" FROM tbl_report as R join tbl_user as U on R.user_id = U.id  "
								+" join tbl_dan as D on D.id = U.dan  "
								+" where D.line = " + model_line.getId();
						if(start_day.length()>10){
							sql = sql + " and R.create_datetime >= '"+ start_day+"'";
						}
						if(end_day.length()>10){
							sql = sql + " and R.create_datetime <= '"+ end_day+"'";
						}
						
						list_report = backendApiHelper.reportService.queryReport(sql,ids);
												
						pageData.put("model_line", model_line);
					}
					pageData.put("list_report_all", list_report_all);
					pageData.put("list_line", list_line);
					
					HashMap<Integer,Dan> map_dan = backendApiHelper.danService.mapDans();
					HashMap<Integer,Line> map_line = backendApiHelper.lineService.mapLines();
					pageData.put("map_dan", map_dan);
					pageData.put("map_line", map_line);
					
					List<Object> list_data = new ArrayList<Object>();
					
					for(Report imodel:list_report){
						User maker = backendApiHelper.userService.getUserById(imodel.getUser_id());
						List list = backendApiHelper.getTypedUser(maker, BaseHelperImpl.UserType.PRODUCTION);
						if(list!=null&&list.size()>0){
							User user_pro = (User)list.get(0);
							Dan idan = map_dan.get(maker.getDan());
							Line iline = map_line.get(idan.getLine());
							
							HashMap<String,Object> ih = new HashMap<String,Object>();
							ih.put("dan", idan);
							ih.put("line", iline);
							ih.put("model", imodel);
							ih.put("maker", maker);
							ih.put("user_pro", user_pro);
							list_data.add(ih);
						}
					}
					
					
					pageData.put("list_data", list_data);
					HashMap<Integer,Object> hash = backendApiHelper.getMapDanPerLineID();
					pageData.put("map_dan_pLine", hash);
					String json_dan="";
					try {
						ObjectMapper objectMapper = new ObjectMapper();
						json_dan = objectMapper.writeValueAsString(hash);
					} catch (Exception e) {
						e.printStackTrace();
					}
					pageData.put("json_dan", json_dan);
				}
			}else if(term.equals(workorder)){
				if(subterm.equals(workorder)){
					Line model_line = null;
					List<Order> list_order_all = backendApiHelper.orderService.listOrders();
					List<Line> list_line = backendApiHelper.lineService.listLines();
					List<Order> list_order = new ArrayList<Order>();
					if (request.getParameter("line_id") != null) {
						int line_id = Integer.valueOf(request.getParameter("line_id"));
						model_line = backendApiHelper.lineService.getLineById(line_id);
					}else{
						if(list_line.size()>0){
							model_line = list_line.get(0);
						}
					}
					
					String start_day = "";
					String end_day = "";
					if (request.getParameter("start_day") != null) {
						start_day = request.getParameter("start_day").trim();
						if(start_day.length() == 10){
							pageData.put("start_day", start_day);
							start_day = start_day + " 00:00:00";
						}
					}
					if (request.getParameter("end_day") != null) {
						end_day = request.getParameter("end_day").trim();
						if(end_day.length() == 10){
							pageData.put("end_day", end_day);
							end_day = end_day + " 00:00:00";
						}
					}
					
					if(model_line!=null){
						HashMap<String,Object> select = this.backendApiHelper.dbFields.getSelectFields("O", Order.class);
						String str_select = select.get("select").toString();
						String[] ids = (String[])select.get("fields");
						String sql = "SELECT " + str_select 
								+" FROM tbl_order O join tbl_user as U on O.user_id = U.id "
								+" join tbl_dan as D on D.id = U.dan "
								+" where D.line = " + model_line.getId();
						if(start_day.length()>10){
							sql = sql + " and O.create_datetime >= '"+ start_day+"'";
						}
						if(end_day.length()>10){
							sql = sql + " and O.create_datetime <= '"+ end_day+"'";
						}
						
						list_order = backendApiHelper.orderService.queryOrder(sql, ids);
												
						pageData.put("model_line", model_line);
					}
					pageData.put("list_order_all", list_order_all);
					pageData.put("list_line", list_line);
					
					HashMap<Integer,Dan> map_dan = backendApiHelper.danService.mapDans();
					HashMap<Integer,Line> map_line = backendApiHelper.lineService.mapLines();
					pageData.put("map_dan", map_dan);
					pageData.put("map_line", map_line);
					
					List<Object> list_data = new ArrayList<Object>();
					for(Order imodel:list_order){
						User maker = backendApiHelper.userService.getUserById(imodel.getUser_id());
						List list = backendApiHelper.getTypedUser(maker, BaseHelperImpl.UserType.PRODUCTION);
						if(list.size()>0){
							User user_pro = (User)list.get(0);
							Station station = backendApiHelper.stationService.getStationById(imodel.getStation_id());
							
							Dan idan = map_dan.get(maker.getDan());
							Line iline = map_line.get(idan.getLine());
							
							HashMap<String,Object> ih = new HashMap<String,Object>();
							ih.put("dan", idan);
							ih.put("line", iline);
							ih.put("model", imodel);
							ih.put("maker", maker);
							ih.put("user_pro", user_pro);
							ih.put("station", station);
							list_data.add(ih);
						}
						
					}
					pageData.put("list_data", list_data);
					HashMap<Integer,Object> hash = backendApiHelper.getMapDanPerLineID();
					pageData.put("map_dan_pLine", hash);
					String json_dan="";
					try {
						ObjectMapper objectMapper = new ObjectMapper();
						json_dan = objectMapper.writeValueAsString(hash);
					} catch (Exception e) {
						e.printStackTrace();
					}
					pageData.put("json_dan", json_dan);
				}
			}
			else if(term.equals(announce)){
				if(subterm.equals(announce)){
					List<Announce> list_data = backendApiHelper.announceService.listAnnounces();
					
					pageData.put("list_data", list_data);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		model.addAttribute("pageData",pageData);
		
	}
	
}
