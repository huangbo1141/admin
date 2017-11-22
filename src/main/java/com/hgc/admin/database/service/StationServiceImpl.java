package com.hgc.admin.database.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.StationDAO;
import com.hgc.admin.database.model.Station;

@Service
public class StationServiceImpl implements StationService {

	private StationDAO personDAO;

	public void setStationDAO(StationDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addStation(Station p) {
		return this.personDAO.addStation(p);
	}

	@Override
	@Transactional
	public void updateStation(Station p) {
		this.personDAO.updateStation(p);
	}

	@Override
	@Transactional
	public List<Station> listStations() {
		return this.personDAO.listStations();
	}

	@Override
	@Transactional
	public Station getStationById(int id) {
		return this.personDAO.getStationById(id);
	}

	@Override
	@Transactional
	public void removeStation(int id) {
		this.personDAO.removeStation(id);
	}

	@Override
	public List<Object> queryStation(String query) {
		// TODO Auto-generated method stub
		return this.personDAO.queryStation(query);

	}

}
