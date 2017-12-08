package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.RecDailyDAO;
import com.hgc.admin.database.model.RecDaily;

@Service
public class RecDailyServiceImpl implements RecDailyService {

	private RecDailyDAO personDAO;

	public void setRecDailyDAO(RecDailyDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addRecDaily(RecDaily p) {
		return this.personDAO.addRecDaily(p);
	}

	@Override
	@Transactional
	public void updateRecDaily(RecDaily p) {
		this.personDAO.updateRecDaily(p);
	}

	@Override
	@Transactional
	public List<RecDaily> listRecDailys() {
		return this.personDAO.listRecDailys();
	}

	@Override
	@Transactional
	public RecDaily getRecDailyById(Integer id) {
		return this.personDAO.getRecDailyById(id);
	}

	@Override
	@Transactional
	public void removeRecDaily(Integer id) {
		this.personDAO.removeRecDaily(id);
	}

	@Override
	public List<RecDaily> queryRecDaily(String query,String[] db_fields){
		// TODO Auto-generated method stub
		return this.personDAO.queryRecDaily(query, db_fields);

	}

	@Override
		public HashMap<Integer, RecDaily> mapRecDailys() {
			// TODO Auto-generated method stub
			return this.personDAO.mapRecDailys();
		}
}
