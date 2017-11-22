package com.hgc.admin.database.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.TimeTypeDAO;
import com.hgc.admin.database.model.TimeType;

@Service
public class TimeTypeServiceImpl implements TimeTypeService {

	private TimeTypeDAO personDAO;

	public void setTimeTypeDAO(TimeTypeDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addTimeType(TimeType p) {
		return this.personDAO.addTimeType(p);
	}

	@Override
	@Transactional
	public void updateTimeType(TimeType p) {
		this.personDAO.updateTimeType(p);
	}

	@Override
	@Transactional
	public List<TimeType> listTimeTypes() {
		return this.personDAO.listTimeTypes();
	}

	@Override
	@Transactional
	public TimeType getTimeTypeById(int id) {
		return this.personDAO.getTimeTypeById(id);
	}

	@Override
	@Transactional
	public void removeTimeType(int id) {
		this.personDAO.removeTimeType(id);
	}

	@Override
	public List<Object> queryTimeType(String query) {
		// TODO Auto-generated method stub
		return this.personDAO.queryTimeType(query);

	}

}
