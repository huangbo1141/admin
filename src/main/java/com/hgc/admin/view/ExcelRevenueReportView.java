package com.hgc.admin.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.hgc.admin.database.model.*;
import com.hgc.admin.utils.AccountHelper;

public class ExcelRevenueReportView extends AbstractExcelView{
	
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String term = model.get("term").toString();
		String subterm = model.get("subterm").toString();
		AccountHelper currentUser = (AccountHelper)model.get("currentUser");
		HashMap<Integer, Menu> map_menu = currentUser.getMap_menu();
		
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
		
		if(term.equals(workorder)){
			if(subterm.equals(workorder)){
				//create a wordsheet
				HSSFSheet sheet = workbook.createSheet("Report");
				
				HSSFRow header = sheet.createRow(0);
				header.createCell(0).setCellValue("时间");
				header.createCell(1).setCellValue("总线名称");
				header.createCell(2).setCellValue("工段名称");
				header.createCell(3).setCellValue("生产段长");
				header.createCell(4).setCellValue("原因描述");
				header.createCell(5).setCellValue("工位");
				
				HashMap<String,Object> pageData = (HashMap<String,Object>)model.get("pageData");
				List<Object> list_data = (List<Object>)pageData.get("list_data");
				
				int rowNum = 1;
				for (Object obj:list_data) {
					HashMap<String,Object> ih = (HashMap<String,Object>)obj;
					Dan idan = (Dan)ih.get("dan");
					Line iline = (Line)ih.get("line");
					HashMap<String,Object> imodel = (HashMap<String,Object>)ih.get("model");
					HashMap<String,Object> maker = (HashMap<String,Object>)ih.get("maker");
					User user_pro = (User)ih.get("user_pro");
					HashMap<String,Object> station = (HashMap<String,Object>)ih.get("station");
					
					//create the row data
					HSSFRow row = sheet.createRow(rowNum++);
					row.createCell(0).setCellValue(imodel.get("create_datetime").toString().substring(0,10));
					row.createCell(1).setCellValue(iline.getName());
					row.createCell(2).setCellValue(idan.getName());
					row.createCell(3).setCellValue(user_pro.getName());
					row.createCell(4).setCellValue(imodel.get("p_desc").toString());
					row.createCell(5).setCellValue(station.get("serial").toString());
		        }
			}
		}else if(term.equals(report)){
			if(subterm.equals(report)){
				//create a wordsheet
				HSSFSheet sheet = workbook.createSheet("Report");
				
				HSSFRow header = sheet.createRow(0);
				header.createCell(0).setCellValue("时间");
				header.createCell(1).setCellValue("总线名称");
				header.createCell(2).setCellValue("工段名称");
				header.createCell(3).setCellValue("生产段长");
				header.createCell(4).setCellValue("第一台上线时间");
				header.createCell(5).setCellValue("最后一台下线时间");
				header.createCell(6).setCellValue("吃饭时间min");
				header.createCell(7).setCellValue("等待上下游时间min");
				header.createCell(8).setCellValue("产量");
				
				HashMap<String,Object> pageData = (HashMap<String,Object>)model.get("pageData");
				List<Object> list_data = (List<Object>)pageData.get("list_data");
				
				int rowNum = 1;
				for (Object obj:list_data) {
					HashMap<String,Object> ih = (HashMap<String,Object>)obj;
					Dan idan = (Dan)ih.get("dan");
					Line iline = (Line)ih.get("line");
					Report imodel = (Report)ih.get("model");
					User maker = (User)ih.get("maker");
					User user_pro = (User)ih.get("user_pro");
					
					
					//create the row data
					HSSFRow row = sheet.createRow(rowNum++);
					row.createCell(0).setCellValue(imodel.getCreate_datetime().substring(0,10));
					row.createCell(1).setCellValue(iline.getName());
					row.createCell(2).setCellValue(idan.getName());
					row.createCell(3).setCellValue(user_pro.getName());
					row.createCell(4).setCellValue(imodel.getFirst_load_time().substring(0, 10));
					row.createCell(5).setCellValue(imodel.getLast_unload_time().substring(0, 10));
					row.createCell(6).setCellValue(imodel.getLunch_time());
					row.createCell(7).setCellValue(imodel.getWait_time());
					row.createCell(8).setCellValue(imodel.getOutput());
					
		        }
			}
		}
		
		//create a wordsheet
//		HSSFSheet sheet = workbook.createSheet("Revenue Report");
//		
//		HSSFRow header = sheet.createRow(0);
//		header.createCell(0).setCellValue("Month");
//		header.createCell(1).setCellValue("Revenue");
//		
//		int rowNum = 1;
//		for (Map.Entry<String, String> entry : revenueData.entrySet()) {
//			//create the row data
//			HSSFRow row = sheet.createRow(rowNum++);
//			row.createCell(0).setCellValue(entry.getKey());
//			row.createCell(1).setCellValue(entry.getValue());
//			
//        }
	}
}