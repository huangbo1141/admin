package com.hgc.admin.constants;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.hgc.admin.utils.BaseHelperImpl;

@Component("tablefields")
public class DbFields {

	public String[] tbl_admin_role = {"id","name","roles","deleted","create_datetime","modify_datetime"};
	public String[] type_tbl_admin_role = {"int","varchar","varchar","int","datetime","datetime"};
	public String[] tbl_admin_user = {"id","name","phone","log_time","role","username","password","deleted","create_datetime","modify_datetime"};
	public String[] type_tbl_admin_user = {"int","varchar","varchar","datetime","int","varchar","varchar","int","datetime","datetime"};
	public String[] tbl_announce = {"id","time","content","deleted","create_datetime","modify_datetime"};
	public String[] type_tbl_announce = {"int","datetime","varchar","int","datetime","datetime"};
	public String[] tbl_ct = {"id","line","v1","v2","deleted","create_datetime","modify_datetime"};
	public String[] type_tbl_ct = {"int","int","double","double","int","datetime","datetime"};
	public String[] tbl_dan = {"id","line","name","sort","deleted","create_datetime","modify_datetime"};
	public String[] type_tbl_dan = {"int","int","varchar","int","int","datetime","datetime"};
	public String[] tbl_error_type = {"id","reason_id","name","desc","deleted","create_datetime","modify_datetime"};
	public String[] type_tbl_error_type = {"int","int","varchar","varchar","int","datetime","datetime"};
	public String[] tbl_line = {"id","name","sort","deleted","create_datetime","modify_datetime"};
	public String[] type_tbl_line = {"int","varchar","int","int","datetime","datetime"};
	public String[] tbl_menu = {"id","name","sort","action","parent","term","deleted","create_datetime","modify_datetime"};
	public String[] type_tbl_menu = {"int","varchar","int","varchar","int","varchar","int","datetime","datetime"};
	public String[] tbl_menu_action = {"id","ac","name","deleted","create_datetime","modify_datetime"};
	public String[] type_tbl_menu_action = {"int","varchar","varchar","int","datetime","datetime"};
	public String[] tbl_order = {"id","station_id","reason_id","start_t","end_t","p_desc","user_id","complete","status","error_id","feedback","deleted","create_datetime","modify_datetime"};
	public String[] type_tbl_order = {"int","int","int","varchar","varchar","varchar","int","datetime","int","int","varchar","int","datetime","datetime"};
	public String[] tbl_order_relation = {"id","sender_id","receiver_id","order_id","s_time","r_time","deleted","create_datetime","modify_datetime"};
	public String[] type_tbl_order_relation = {"int","int","int","int","datetime","datetime","int","datetime","datetime"};
	public String[] tbl_picture = {"id","filename","type","ref_id1","ref_type1","deleted","create_datetime","modify_datetime"};
	public String[] type_tbl_picture = {"int","varchar","int","int","varchar","int","datetime","datetime"};
	public String[] tbl_reason_type = {"id","name","deleted","create_datetime","modify_datetime"};
	public String[] type_tbl_reason_type = {"int","varchar","int","datetime","datetime"};
	public String[] tbl_report = {"id","user_id","first_load_time","last_unload_time","lunch_time","wait_time","output","deleted","create_datetime","modify_datetime"};
	public String[] type_tbl_report = {"int","int","datetime","datetime","int","int","int","int","datetime","datetime"};
	public String[] tbl_station = {"id","serial","dan","deleted","create_datetime","modify_datetime"};
	public String[] type_tbl_station = {"int","varchar","int","int","datetime","datetime"};
	public String[] tbl_time_type = {"id","name","ty_name","deleted","create_datetime","modify_datetime"};
	public String[] type_tbl_time_type = {"int","varchar","varchar","int","datetime","datetime"};
	public String[] tbl_tt = {"id","time_value","time_type","ta","oee","deleted","create_datetime","modify_datetime"};
	public String[] type_tbl_tt = {"int","varchar","int","int","int","int","datetime","datetime"};
	public String[] tbl_user = {"id","name","serial","password","type","dan","deleted","create_datetime","modify_datetime","phone","head"};
	public String[] type_tbl_user = {"int","varchar","varchar","varchar","int","int","int","datetime","datetime","varchar","varchar"};
	public String[] tbl_user_role = {"id","name","tr_name","deleted","create_datetime","modify_datetime"};
	public String[] type_tbl_user_role = {"int","varchar","varchar","int","datetime","datetime"};
	
	
	public HashMap<String,Object> getSelectFields(String alias,Class<?> ModelT){
		HashMap<String,Object> ret = new HashMap<String,Object>();
		
		Field field;
		try {
			String tablename = BaseHelperImpl.getTableName(ModelT.getSimpleName());
			field = this.getClass().getField(tablename.toLowerCase());
			String[] field_names = (String[]) field.get(this);
			
			String temp = "";
			for(int i=0; i<field_names.length; i++){
				if(alias.length()>0){
					temp = temp+ alias +"."+field_names[i]+",";	
				}else{
					temp = temp+ field_names[i]+",";
				}
				
			}
			if(temp.length()>0){
				temp = temp.substring(0, temp.length()-1);
			}
			ret.put("select", temp);
			ret.put("fields", field_names);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return ret;
	}
}
