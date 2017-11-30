package com.hgc.admin.database.dao;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.Station;

public interface StationDAO {

	public Integer addStation(Station p);
	public void updateStation(Station p);
	public List<Station> listStations();
	public Station getStationById(Integer id);
	public void removeStation(Integer id);
	public List<Station> queryStation(String SQL_QUERY,String[] fields);
	public HashMap<Integer,Station> mapStations();
}
