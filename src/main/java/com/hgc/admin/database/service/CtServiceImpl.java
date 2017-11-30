package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.CtDAO;
import com.hgc.admin.database.model.Ct;

@Service
public class CtServiceImpl implements CtService {

	private CtDAO personDAO;

	public void setCtDAO(CtDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addCt(Ct p) {
		return this.personDAO.addCt(p);
	}

	@Override
	@Transactional
	public void updateCt(Ct p) {
		this.personDAO.updateCt(p);
	}

	@Override
	@Transactional
	public List<Ct> listCts() {
		return this.personDAO.listCts();
	}

	@Override
	@Transactional
	public Ct getCtById(Integer id) {
		return this.personDAO.getCtById(id);
	}

	@Override
	@Transactional
	public void removeCt(Integer id) {
		this.personDAO.removeCt(id);
	}

	@Override
	public List<Ct> queryCt(String query,String[] db_fields){
		// TODO Auto-generated method stub
		return this.personDAO.queryCt(query, db_fields);

	}

	@Override
		public HashMap<Integer, Ct> mapCts() {
			// TODO Auto-generated method stub
			return this.personDAO.mapCts();
		}
}
