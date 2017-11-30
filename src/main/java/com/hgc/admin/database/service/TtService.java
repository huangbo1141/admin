package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.Tt;

public interface TtService {

	public Integer addTt(Tt p);
	public void updateTt(Tt p);
	public List<Tt> listTts();
	public Tt getTtById(Integer id);
	public void removeTt(Integer id);
	public List<Tt> queryTt(String query,String[] db_fields);
	public HashMap<Integer,Tt> mapTts();
}
