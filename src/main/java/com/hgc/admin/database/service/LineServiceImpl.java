package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.LineDAO;
import com.hgc.admin.database.model.Line;

@Service
public class LineServiceImpl implements LineService {

	private LineDAO personDAO;

	public void setLineDAO(LineDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addLine(Line p) {
		return this.personDAO.addLine(p);
	}

	@Override
	@Transactional
	public void updateLine(Line p) {
		this.personDAO.updateLine(p);
	}

	@Override
	@Transactional
	public List<Line> listLines() {
		return this.personDAO.listLines();
	}

	@Override
	@Transactional
	public Line getLineById(Integer id) {
		return this.personDAO.getLineById(id);
	}

	@Override
	@Transactional
	public void removeLine(Integer id) {
		this.personDAO.removeLine(id);
	}

	@Override
	public List<Line> queryLine(String query,String[] db_fields){
		// TODO Auto-generated method stub
		return this.personDAO.queryLine(query, db_fields);

	}

	@Override
		public HashMap<Integer, Line> mapLines() {
			// TODO Auto-generated method stub
			return this.personDAO.mapLines();
		}
}
