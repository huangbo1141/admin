package com.hgc.admin.database.dao;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.Line;

public interface LineDAO {

	public Integer addLine(Line p);
	public void updateLine(Line p);
	public List<Line> listLines();
	public Line getLineById(Integer id);
	public void removeLine(Integer id);
	public List<Line> queryLine(String SQL_QUERY,String[] fields);
	public HashMap<Integer,Line> mapLines();
}
