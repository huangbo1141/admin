package com.hgc.admin.database.service;

import java.util.List;

import com.hgc.admin.database.model.Station;

public interface StationService {

	public Integer addStation(Station p);
	public void updateStation(Station p);
	public List<Station> listStations();
	public Station getStationById(int id);
	public void removeStation(int id);
	public List<Object> queryStation(String query);
}
