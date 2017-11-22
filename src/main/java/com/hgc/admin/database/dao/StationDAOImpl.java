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
import com.hgc.admin.database.model.Station;

@Repository
public class StationDAOImpl implements StationDAO {

	private static final Logger logger = LoggerFactory.getLogger(StationDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public List<Object> queryStation(String SQL_QUERY){
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
	public Integer addStation(Station p) {

		Session s = this.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		Integer myID = (Integer)s.save(p);
		t.commit();
		s.close();
		if(Constants.daoLogger)
			logger.info("Station saved successfully, Station Details="+p);
		return myID;
	}

	@Override
	public void updateStation(Station p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		if(Constants.daoLogger)
		logger.info("Station updated successfully, Station Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Station> listStations() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Station> StationsList = session.createQuery("from Station").list();
		for(Station p : StationsList){
			if(Constants.daoLogger)
			logger.info("Station List::"+p);
		}
		return StationsList;
	}

	@Override
	public Station getStationById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Station p = (Station) session.load(Station.class, new Integer(id));
		if(Constants.daoLogger)
		logger.info("Station loaded successfully, Station details="+p);
		return p;
	}

	@Override
	public void removeStation(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Station p = (Station) session.load(Station.class, new Integer(id));
		if(null != p){
			session.delete(p);
		}
		if(Constants.daoLogger)
		logger.info("Station deleted successfully, Station details="+p);
	}

}
