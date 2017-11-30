package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.Ct;

public interface CtService {

	public Integer addCt(Ct p);
	public void updateCt(Ct p);
	public List<Ct> listCts();
	public Ct getCtById(Integer id);
	public void removeCt(Integer id);
	public List<Ct> queryCt(String query,String[] db_fields);
	public HashMap<Integer,Ct> mapCts();
}
