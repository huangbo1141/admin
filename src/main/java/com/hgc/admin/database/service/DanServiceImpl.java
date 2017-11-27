package com.hgc.admin.database.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.DanDAO;
import com.hgc.admin.database.model.Dan;

@Service
public class DanServiceImpl implements DanService {

	private DanDAO personDAO;

	public void setDanDAO(DanDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addDan(Dan p) {
		return this.personDAO.addDan(p);
	}

	@Override
	@Transactional
	public void updateDan(Dan p) {
		this.personDAO.updateDan(p);
	}

	@Override
	@Transactional
	public List<Dan> listDans() {
		return this.personDAO.listDans();
	}

	@Override
	@Transactional
	public Dan getDanById(Integer id) {
		return this.personDAO.getDanById(id);
	}

	@Override
	@Transactional
	public void removeDan(Integer id) {
		this.personDAO.removeDan(id);
	}

	@Override
	public List<Object> queryDan(String query) {
		// TODO Auto-generated method stub
		return this.personDAO.queryDan(query);

	}

}
