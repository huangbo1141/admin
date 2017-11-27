package com.hgc.admin.database.service;

import java.util.List;

import com.hgc.admin.database.model.TimeType;

public interface TimeTypeService {

	public Integer addTimeType(TimeType p);
	public void updateTimeType(TimeType p);
	public List<TimeType> listTimeTypes();
	public TimeType getTimeTypeById(Integer id);
	public void removeTimeType(Integer id);
	public List<Object> queryTimeType(String query);
}