package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.Announce;

public interface AnnounceService {

	public Integer addAnnounce(Announce p);
	public void updateAnnounce(Announce p);
	public List<Announce> listAnnounces();
	public Announce getAnnounceById(Integer id);
	public void removeAnnounce(Integer id);
	public List<Announce> queryAnnounce(String query,String[] db_fields);
	public HashMap<Integer,Announce> mapAnnounces();
}
