package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.ErrorTypeDAO;
import com.hgc.admin.database.model.ErrorType;

@Service
public class ErrorTypeServiceImpl implements ErrorTypeService {

	private ErrorTypeDAO personDAO;

	public void setErrorTypeDAO(ErrorTypeDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addErrorType(ErrorType p) {
		return this.personDAO.addErrorType(p);
	}

	@Override
	@Transactional
	public void updateErrorType(ErrorType p) {
		this.personDAO.updateErrorType(p);
	}

	@Override
	@Transactional
	public List<ErrorType> listErrorTypes() {
		return this.personDAO.listErrorTypes();
	}

	@Override
	@Transactional
	public ErrorType getErrorTypeById(Integer id) {
		return this.personDAO.getErrorTypeById(id);
	}

	@Override
	@Transactional
	public void removeErrorType(Integer id) {
		this.personDAO.removeErrorType(id);
	}

	@Override
	public List<ErrorType> queryErrorType(String query,String[] db_fields){
		// TODO Auto-generated method stub
		return this.personDAO.queryErrorType(query, db_fields);

	}

	@Override
		public HashMap<Integer, ErrorType> mapErrorTypes() {
			// TODO Auto-generated method stub
			return this.personDAO.mapErrorTypes();
		}
}
