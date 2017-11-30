package com.hgc.admin.database.dao;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.TimeType;

public interface TimeTypeDAO {

	public Integer addTimeType(TimeType p);
	public void updateTimeType(TimeType p);
	public List<TimeType> listTimeTypes();
	public TimeType getTimeTypeById(Integer id);
	public void removeTimeType(Integer id);
	public List<TimeType> queryTimeType(String SQL_QUERY,String[] fields);
	public HashMap<Integer,TimeType> mapTimeTypes();
}
