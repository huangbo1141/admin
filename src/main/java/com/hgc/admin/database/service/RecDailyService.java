package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.RecDaily;

public interface RecDailyService {

	public Integer addRecDaily(RecDaily p);
	public void updateRecDaily(RecDaily p);
	public List<RecDaily> listRecDailys();
	public RecDaily getRecDailyById(Integer id);
	public void removeRecDaily(Integer id);
	public List<RecDaily> queryRecDaily(String query,String[] db_fields);
	public HashMap<Integer,RecDaily> mapRecDailys();
}
