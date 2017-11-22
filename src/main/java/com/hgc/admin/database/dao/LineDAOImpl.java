package com.hgc.admin.database.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hgc.admin.constants.Constants;
import com.hgc.admin.database.model.Line;

@Repository
public class LineDAOImpl implements LineDAO {

	private static final Logger logger = LoggerFactory.getLogger(LineDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public List<Object> queryLine(String SQL_QUERY){
		Session session = this.sessionFactory.openSession();
		List<Object> ret = new ArrayList<Object>();
		try{
			ret = session.createSQLQuery(SQL_QUERY).list();
			session.close();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return ret;
	}

	@Override
	public Integer addLine(Line p) {

		Session s = this.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		Integer myID = (Integer)s.save(p);
		t.commit();
		s.close();
		if(Constants.daoLogger)
			logger.info("Line saved successfully, Line Details="+p);
		return myID;
	}

	@Override
	public void updateLine(Line p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		if(Constants.daoLogger)
		logger.info("Line updated successfully, Line Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Line> listLines() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Line> LinesList = session.createQuery("from Line").list();
		for(Line p : LinesList){
			if(Constants.daoLogger)
			logger.info("Line List::"+p);
		}
		return LinesList;
	}

	@Override
	public Line getLineById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Line p = (Line) session.load(Line.class, new Integer(id));
		if(Constants.daoLogger)
		logger.info("Line loaded successfully, Line details="+p);
		return p;
	}

	@Override
	public void removeLine(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Line p = (Line) session.load(Line.class, new Integer(id));
		if(null != p){
			session.delete(p);
		}
		if(Constants.daoLogger)
		logger.info("Line deleted successfully, Line details="+p);
	}

}
