package com.hgc.admin.database.dao;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.Announce;

public interface AnnounceDAO {

	public Integer addAnnounce(Announce p);
	public void updateAnnounce(Announce p);
	public List<Announce> listAnnounces();
	public Announce getAnnounceById(Integer id);
	public void removeAnnounce(Integer id);
	public List<Announce> queryAnnounce(String SQL_QUERY,String[] fields);
	public HashMap<Integer,Announce> mapAnnounces();
}
