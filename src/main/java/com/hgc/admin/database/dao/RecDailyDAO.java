package com.hgc.admin.database.dao;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.RecDaily;

public interface RecDailyDAO {

	public Integer addRecDaily(RecDaily p);
	public void updateRecDaily(RecDaily p);
	public List<RecDaily> listRecDailys();
	public RecDaily getRecDailyById(Integer id);
	public void removeRecDaily(Integer id);
	public List<RecDaily> queryRecDaily(String SQL_QUERY,String[] fields);
	public HashMap<Integer,RecDaily> mapRecDailys();
}
