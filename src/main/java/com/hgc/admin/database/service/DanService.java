package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.Dan;

public interface DanService {

	public Integer addDan(Dan p);
	public void updateDan(Dan p);
	public List<Dan> listDans();
	public Dan getDanById(Integer id);
	public void removeDan(Integer id);
	public List<Dan> queryDan(String query,String[] db_fields);
	public HashMap<Integer,Dan> mapDans();
}
