package com.hgc.admin.database.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.TtDAO;
import com.hgc.admin.database.model.Tt;

@Service
public class TtServiceImpl implements TtService {

	private TtDAO personDAO;

	public void setTtDAO(TtDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addTt(Tt p) {
		return this.personDAO.addTt(p);
	}

	@Override
	@Transactional
	public void updateTt(Tt p) {
		this.personDAO.updateTt(p);
	}

	@Override
	@Transactional
	public List<Tt> listTts() {
		return this.personDAO.listTts();
	}

	@Override
	@Transactional
	public Tt getTtById(int id) {
		return this.personDAO.getTtById(id);
	}

	@Override
	@Transactional
	public void removeTt(int id) {
		this.personDAO.removeTt(id);
	}

	@Override
	public List<Object> queryTt(String query) {
		// TODO Auto-generated method stub
		return this.personDAO.queryTt(query);

	}

}
