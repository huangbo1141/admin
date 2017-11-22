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
import com.hgc.admin.database.model.AdminRole;

@Repository
public class AdminRoleDAOImpl implements AdminRoleDAO {

	private static final Logger logger = LoggerFactory.getLogger(AdminRoleDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public List<Object> queryAdminRole(String SQL_QUERY){
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
	public Integer addAdminRole(AdminRole p) {

		Session s = this.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		Integer myID = (Integer)s.save(p);
		t.commit();
		s.close();
		if(Constants.daoLogger)
			logger.info("AdminRole saved successfully, AdminRole Details="+p);
		return myID;
	}

	@Override
	public void updateAdminRole(AdminRole p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		if(Constants.daoLogger)
		logger.info("AdminRole updated successfully, AdminRole Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdminRole> listAdminRoles() {
		Session session = this.sessionFactory.getCurrentSession();
		List<AdminRole> AdminRolesList = session.createQuery("from AdminRole").list();
		for(AdminRole p : AdminRolesList){
			if(Constants.daoLogger)
			logger.info("AdminRole List::"+p);
		}
		return AdminRolesList;
	}

	@Override
	public AdminRole getAdminRoleById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		AdminRole p = (AdminRole) session.load(AdminRole.class, new Integer(id));
		if(Constants.daoLogger)
		logger.info("AdminRole loaded successfully, AdminRole details="+p);
		return p;
	}

	@Override
	public void removeAdminRole(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		AdminRole p = (AdminRole) session.load(AdminRole.class, new Integer(id));
		if(null != p){
			session.delete(p);
		}
		if(Constants.daoLogger)
		logger.info("AdminRole deleted successfully, AdminRole details="+p);
	}

}
