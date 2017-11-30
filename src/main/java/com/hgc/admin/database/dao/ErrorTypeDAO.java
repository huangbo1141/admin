package com.hgc.admin.database.dao;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.ErrorType;

public interface ErrorTypeDAO {

	public Integer addErrorType(ErrorType p);
	public void updateErrorType(ErrorType p);
	public List<ErrorType> listErrorTypes();
	public ErrorType getErrorTypeById(Integer id);
	public void removeErrorType(Integer id);
	public List<ErrorType> queryErrorType(String SQL_QUERY,String[] fields);
	public HashMap<Integer,ErrorType> mapErrorTypes();
}
