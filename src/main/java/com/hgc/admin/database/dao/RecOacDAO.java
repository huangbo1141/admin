package com.hgc.admin.database.dao;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.RecOac;

public interface RecOacDAO {

	public Integer addRecOac(RecOac p);
	public void updateRecOac(RecOac p);
	public List<RecOac> listRecOacs();
	public RecOac getRecOacById(Integer id);
	public void removeRecOac(Integer id);
	public List<RecOac> queryRecOac(String SQL_QUERY,String[] fields);
	public HashMap<Integer,RecOac> mapRecOacs();
}
