package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.AnnounceDAO;
import com.hgc.admin.database.model.Announce;

@Service
public class AnnounceServiceImpl implements AnnounceService {

	private AnnounceDAO personDAO;

	public void setAnnounceDAO(AnnounceDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addAnnounce(Announce p) {
		return this.personDAO.addAnnounce(p);
	}

	@Override
	@Transactional
	public void updateAnnounce(Announce p) {
		this.personDAO.updateAnnounce(p);
	}

	@Override
	@Transactional
	public List<Announce> listAnnounces() {
		return this.personDAO.listAnnounces();
	}

	@Override
	@Transactional
	public Announce getAnnounceById(Integer id) {
		return this.personDAO.getAnnounceById(id);
	}

	@Override
	@Transactional
	public void removeAnnounce(Integer id) {
		this.personDAO.removeAnnounce(id);
	}

	@Override
	public List<Announce> queryAnnounce(String query,String[] db_fields){
		// TODO Auto-generated method stub
		return this.personDAO.queryAnnounce(query, db_fields);

	}

	@Override
		public HashMap<Integer, Announce> mapAnnounces() {
			// TODO Auto-generated method stub
			return this.personDAO.mapAnnounces();
		}
}
