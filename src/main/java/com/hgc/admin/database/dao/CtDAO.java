package com.hgc.admin.database.dao;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.Ct;

public interface CtDAO {

	public Integer addCt(Ct p);
	public void updateCt(Ct p);
	public List<Ct> listCts();
	public Ct getCtById(Integer id);
	public void removeCt(Integer id);
	public List<Ct> queryCt(String SQL_QUERY,String[] fields);
	public HashMap<Integer,Ct> mapCts();
}
