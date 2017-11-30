package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.ReasonTypeDAO;
import com.hgc.admin.database.model.ReasonType;

@Service
public class ReasonTypeServiceImpl implements ReasonTypeService {

	private ReasonTypeDAO personDAO;

	public void setReasonTypeDAO(ReasonTypeDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addReasonType(ReasonType p) {
		return this.personDAO.addReasonType(p);
	}

	@Override
	@Transactional
	public void updateReasonType(ReasonType p) {
		this.personDAO.updateReasonType(p);
	}

	@Override
	@Transactional
	public List<ReasonType> listReasonTypes() {
		return this.personDAO.listReasonTypes();
	}

	@Override
	@Transactional
	public ReasonType getReasonTypeById(Integer id) {
		return this.personDAO.getReasonTypeById(id);
	}

	@Override
	@Transactional
	public void removeReasonType(Integer id) {
		this.personDAO.removeReasonType(id);
	}

	@Override
	public List<ReasonType> queryReasonType(String query,String[] db_fields){
		// TODO Auto-generated method stub
		return this.personDAO.queryReasonType(query, db_fields);

	}

	@Override
		public HashMap<Integer, ReasonType> mapReasonTypes() {
			// TODO Auto-generated method stub
			return this.personDAO.mapReasonTypes();
		}
}
