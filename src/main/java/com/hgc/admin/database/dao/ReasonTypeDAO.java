package com.hgc.admin.database.dao;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.ReasonType;

public interface ReasonTypeDAO {

	public Integer addReasonType(ReasonType p);
	public void updateReasonType(ReasonType p);
	public List<ReasonType> listReasonTypes();
	public ReasonType getReasonTypeById(Integer id);
	public void removeReasonType(Integer id);
	public List<ReasonType> queryReasonType(String SQL_QUERY,String[] fields);
	public HashMap<Integer,ReasonType> mapReasonTypes();
}
