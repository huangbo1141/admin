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
import com.hgc.admin.database.model.Ct;

@Repository
public class CtDAOImpl implements CtDAO {

	private static final Logger logger = LoggerFactory.getLogger(CtDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public List<Object> queryCt(String SQL_QUERY){
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
	public Integer addCt(Ct p) {

		Session s = this.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		Integer myID = (Integer)s.save(p);
		t.commit();
		s.close();
		if(Constants.daoLogger)
			logger.info("Ct saved successfully, Ct Details="+p);
		return myID;
	}

	@Override
	public void updateCt(Ct p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		if(Constants.daoLogger)
		logger.info("Ct updated successfully, Ct Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ct> listCts() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Ct> CtsList = session.createQuery("from Ct").list();
		for(Ct p : CtsList){
			if(Constants.daoLogger)
			logger.info("Ct List::"+p);
		}
		return CtsList;
	}

	@Override
	public Ct getCtById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Ct p = (Ct) session.load(Ct.class, new Integer(id));
		if(Constants.daoLogger)
		logger.info("Ct loaded successfully, Ct details="+p);
		return p;
	}

	@Override
	public void removeCt(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Ct p = (Ct) session.load(Ct.class, new Integer(id));
		if(null != p){
			session.delete(p);
		}
		if(Constants.daoLogger)
		logger.info("Ct deleted successfully, Ct details="+p);
	}

}
