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
import com.hgc.admin.database.model.AdminUser;

@Repository
public class AdminUserDAOImpl implements AdminUserDAO {

	private static final Logger logger = LoggerFactory.getLogger(AdminUserDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public List<Object> queryAdminUser(String SQL_QUERY){
		Session session = this.sessionFactory.openSession();
		List<Object> ret = new ArrayList<Object>();
		try{
			ret = session.createSQLQuery(SQL_QUERY).list();
			for(Object p : ret){
				if(Constants.daoLogger)
				logger.info("AdminUser List::"+p);
			}
			session.close();
		}catch(Exception e){
			session.close();
		}
		return ret;
	}

	@Override
	public Integer addAdminUser(AdminUser p) {

		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			Integer myID = (Integer)session.save(p);
			if(Constants.daoLogger)
				logger.info("AdminUser saved successfully, AdminUser Details="+p);
			t.commit();
			session.close();
			return myID;
		}catch(Exception e){
			t.rollback();
			session.close();
			return null;
		}


	}

	@Override
	public void updateAdminUser(AdminUser p) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			session.update(p);
			if(Constants.daoLogger)
			logger.info("AdminUser updated successfully, AdminUser Details="+p);
			t.commit();
			session.close();
		}catch(Exception e){
			t.rollback();
			session.close();
		}


	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdminUser> listAdminUsers() {
		Session session = this.sessionFactory.openSession();
		try{
			List<AdminUser> AdminUsersList = session.createQuery("from AdminUser").list();
			for(AdminUser p : AdminUsersList){
				if(Constants.daoLogger)
				logger.info("AdminUser List::"+p);
			}
			session.close();
			return AdminUsersList;
		}catch(Exception e){
			session.close();
			return null;
		}
	}

	@Override
	public AdminUser getAdminUserById(Integer id) {
		Session session = this.sessionFactory.openSession();
		try{
			AdminUser p = (AdminUser) session.load(AdminUser.class, new Integer(id));
			if(Constants.daoLogger)
			logger.info("AdminUser loaded successfully, AdminUser details="+p);
			session.close();
			return p;
		}catch(Exception e){
			session.close();
			return null;
		}
	}

	@Override
	public void removeAdminUser(Integer id) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			AdminUser p = (AdminUser) session.load(AdminUser.class, new Integer(id));
			if(null != p){
				session.delete(p);
			}
			if(Constants.daoLogger)
			logger.info("AdminUser deleted successfully, AdminUser details="+p);
			t.commit();
			session.close();
		}catch(Exception e){
			t.rollback();
			session.close();
		}
	}

}
