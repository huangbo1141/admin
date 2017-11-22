package com.hgc.admin.database.dao;

import java.util.List;

import com.hgc.admin.database.model.Ct;

public interface CtDAO {

	public Integer addCt(Ct p);
	public void updateCt(Ct p);
	public List<Ct> listCts();
	public Ct getCtById(int id);
	public void removeCt(int id);
	public List<Object> queryCt(String query);
}
