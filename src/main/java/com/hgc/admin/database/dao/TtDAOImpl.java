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
import com.hgc.admin.database.model.Tt;

@Repository
public class TtDAOImpl implements TtDAO {

	private static final Logger logger = LoggerFactory.getLogger(TtDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public List<Object> queryTt(String SQL_QUERY){
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
	public Integer addTt(Tt p) {

		Session s = this.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		Integer myID = (Integer)s.save(p);
		t.commit();
		s.close();
		if(Constants.daoLogger)
			logger.info("Tt saved successfully, Tt Details="+p);
		return myID;
	}

	@Override
	public void updateTt(Tt p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		if(Constants.daoLogger)
		logger.info("Tt updated successfully, Tt Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tt> listTts() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Tt> TtsList = session.createQuery("from Tt").list();
		for(Tt p : TtsList){
			if(Constants.daoLogger)
			logger.info("Tt List::"+p);
		}
		return TtsList;
	}

	@Override
	public Tt getTtById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Tt p = (Tt) session.load(Tt.class, new Integer(id));
		if(Constants.daoLogger)
		logger.info("Tt loaded successfully, Tt details="+p);
		return p;
	}

	@Override
	public void removeTt(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Tt p = (Tt) session.load(Tt.class, new Integer(id));
		if(null != p){
			session.delete(p);
		}
		if(Constants.daoLogger)
		logger.info("Tt deleted successfully, Tt details="+p);
	}

}
