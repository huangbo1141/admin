package com.hgc.admin.database.dao;

import java.util.List;

import com.hgc.admin.database.model.Dan;

public interface DanDAO {

	public Integer addDan(Dan p);
	public void updateDan(Dan p);
	public List<Dan> listDans();
	public Dan getDanById(int id);
	public void removeDan(int id);
	public List<Object> queryDan(String query);
}
