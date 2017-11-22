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
import com.hgc.admin.database.model.TimeType;

@Repository
public class TimeTypeDAOImpl implements TimeTypeDAO {

	private static final Logger logger = LoggerFactory.getLogger(TimeTypeDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public List<Object> queryTimeType(String SQL_QUERY){
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
	public Integer addTimeType(TimeType p) {

		Session s = this.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		Integer myID = (Integer)s.save(p);
		t.commit();
		s.close();
		if(Constants.daoLogger)
			logger.info("TimeType saved successfully, TimeType Details="+p);
		return myID;
	}

	@Override
	public void updateTimeType(TimeType p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		if(Constants.daoLogger)
		logger.info("TimeType updated successfully, TimeType Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TimeType> listTimeTypes() {
		Session session = this.sessionFactory.getCurrentSession();
		List<TimeType> TimeTypesList = session.createQuery("from TimeType").list();
		for(TimeType p : TimeTypesList){
			if(Constants.daoLogger)
			logger.info("TimeType List::"+p);
		}
		return TimeTypesList;
	}

	@Override
	public TimeType getTimeTypeById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		TimeType p = (TimeType) session.load(TimeType.class, new Integer(id));
		if(Constants.daoLogger)
		logger.info("TimeType loaded successfully, TimeType details="+p);
		return p;
	}

	@Override
	public void removeTimeType(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		TimeType p = (TimeType) session.load(TimeType.class, new Integer(id));
		if(null != p){
			session.delete(p);
		}
		if(Constants.daoLogger)
		logger.info("TimeType deleted successfully, TimeType details="+p);
	}

}
