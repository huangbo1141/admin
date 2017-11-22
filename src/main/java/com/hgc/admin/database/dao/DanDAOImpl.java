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
import com.hgc.admin.database.model.Dan;

@Repository
public class DanDAOImpl implements DanDAO {

	private static final Logger logger = LoggerFactory.getLogger(DanDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public List<Object> queryDan(String SQL_QUERY){
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
	public Integer addDan(Dan p) {

		Session s = this.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		Integer myID = (Integer)s.save(p);
		t.commit();
		s.close();
		if(Constants.daoLogger)
			logger.info("Dan saved successfully, Dan Details="+p);
		return myID;
	}

	@Override
	public void updateDan(Dan p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		if(Constants.daoLogger)
		logger.info("Dan updated successfully, Dan Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dan> listDans() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Dan> DansList = session.createQuery("from Dan").list();
		for(Dan p : DansList){
			if(Constants.daoLogger)
			logger.info("Dan List::"+p);
		}
		return DansList;
	}

	@Override
	public Dan getDanById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Dan p = (Dan) session.load(Dan.class, new Integer(id));
		if(Constants.daoLogger)
		logger.info("Dan loaded successfully, Dan details="+p);
		return p;
	}

	@Override
	public void removeDan(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Dan p = (Dan) session.load(Dan.class, new Integer(id));
		if(null != p){
			session.delete(p);
		}
		if(Constants.daoLogger)
		logger.info("Dan deleted successfully, Dan details="+p);
	}

}
