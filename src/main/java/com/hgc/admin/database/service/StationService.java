package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.Station;

public interface StationService {

	public Integer addStation(Station p);
	public void updateStation(Station p);
	public List<Station> listStations();
	public Station getStationById(Integer id);
	public void removeStation(Integer id);
	public List<Station> queryStation(String query,String[] db_fields);
	public HashMap<Integer,Station> mapStations();
}
