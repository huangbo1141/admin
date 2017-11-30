package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.ReportDAO;
import com.hgc.admin.database.model.Report;

@Service
public class ReportServiceImpl implements ReportService {

	private ReportDAO personDAO;

	public void setReportDAO(ReportDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addReport(Report p) {
		return this.personDAO.addReport(p);
	}

	@Override
	@Transactional
	public void updateReport(Report p) {
		this.personDAO.updateReport(p);
	}

	@Override
	@Transactional
	public List<Report> listReports() {
		return this.personDAO.listReports();
	}

	@Override
	@Transactional
	public Report getReportById(Integer id) {
		return this.personDAO.getReportById(id);
	}

	@Override
	@Transactional
	public void removeReport(Integer id) {
		this.personDAO.removeReport(id);
	}

	@Override
	public List<Report> queryReport(String query,String[] db_fields){
		// TODO Auto-generated method stub
		return this.personDAO.queryReport(query, db_fields);

	}

	@Override
		public HashMap<Integer, Report> mapReports() {
			// TODO Auto-generated method stub
			return this.personDAO.mapReports();
		}
}
