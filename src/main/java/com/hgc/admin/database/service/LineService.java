package com.hgc.admin.database.service;

import java.util.List;

import com.hgc.admin.database.model.Line;

public interface LineService {

	public Integer addLine(Line p);
	public void updateLine(Line p);
	public List<Line> listLines();
	public Line getLineById(int id);
	public void removeLine(int id);
	public List<Object> queryLine(String query);
}
