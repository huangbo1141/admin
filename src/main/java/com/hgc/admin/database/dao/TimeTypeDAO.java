package com.hgc.admin.database.dao;

import java.util.List;

import com.hgc.admin.database.model.TimeType;

public interface TimeTypeDAO {

	public Integer addTimeType(TimeType p);
	public void updateTimeType(TimeType p);
	public List<TimeType> listTimeTypes();
	public TimeType getTimeTypeById(int id);
	public void removeTimeType(int id);
	public List<Object> queryTimeType(String query);
}
