package com.hgc.admin.database.service;

import java.util.List;

import com.hgc.admin.database.model.Tt;

public interface TtService {

	public Integer addTt(Tt p);
	public void updateTt(Tt p);
	public List<Tt> listTts();
	public Tt getTtById(int id);
	public void removeTt(int id);
	public List<Object> queryTt(String query);
}
