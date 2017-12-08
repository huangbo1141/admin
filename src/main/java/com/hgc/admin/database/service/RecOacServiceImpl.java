package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.RecOacDAO;
import com.hgc.admin.database.model.RecOac;

@Service
public class RecOacServiceImpl implements RecOacService {

	private RecOacDAO personDAO;

	public void setRecOacDAO(RecOacDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addRecOac(RecOac p) {
		return this.personDAO.addRecOac(p);
	}

	@Override
	@Transactional
	public void updateRecOac(RecOac p) {
		this.personDAO.updateRecOac(p);
	}

	@Override
	@Transactional
	public List<RecOac> listRecOacs() {
		return this.personDAO.listRecOacs();
	}

	@Override
	@Transactional
	public RecOac getRecOacById(Integer id) {
		return this.personDAO.getRecOacById(id);
	}

	@Override
	@Transactional
	public void removeRecOac(Integer id) {
		this.personDAO.removeRecOac(id);
	}

	@Override
	public List<RecOac> queryRecOac(String query,String[] db_fields){
		// TODO Auto-generated method stub
		return this.personDAO.queryRecOac(query, db_fields);

	}

	@Override
		public HashMap<Integer, RecOac> mapRecOacs() {
			// TODO Auto-generated method stub
			return this.personDAO.mapRecOacs();
		}
}
