package com.hgc.admin.database.service;

import java.util.List;

import com.hgc.admin.database.model.Ct;

public interface CtService {

	public Integer addCt(Ct p);
	public void updateCt(Ct p);
	public List<Ct> listCts();
	public Ct getCtById(int id);
	public void removeCt(int id);
	public List<Object> queryCt(String query);
}
