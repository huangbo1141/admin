package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.ErrorType;

public interface ErrorTypeService {

	public Integer addErrorType(ErrorType p);
	public void updateErrorType(ErrorType p);
	public List<ErrorType> listErrorTypes();
	public ErrorType getErrorTypeById(Integer id);
	public void removeErrorType(Integer id);
	public List<ErrorType> queryErrorType(String query,String[] db_fields);
	public HashMap<Integer,ErrorType> mapErrorTypes();
}
