package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.Report;

public interface ReportService {

	public Integer addReport(Report p);
	public void updateReport(Report p);
	public List<Report> listReports();
	public Report getReportById(Integer id);
	public void removeReport(Integer id);
	public List<Report> queryReport(String query,String[] db_fields);
	public HashMap<Integer,Report> mapReports();
}
