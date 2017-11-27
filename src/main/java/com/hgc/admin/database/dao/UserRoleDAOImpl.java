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
import com.hgc.admin.database.model.UserRole;

@Repository
public class UserRoleDAOImpl implements UserRoleDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserRoleDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public List<Object> queryUserRole(String SQL_QUERY){
		Session session = this.sessionFactory.openSession();
		List<Object> ret = new ArrayList<Object>();
		try{
			ret = session.createSQLQuery(SQL_QUERY).list();
			for(Object p : ret){
				if(Constants.daoLogger)
				logger.info("UserRole List::"+p);
			}
			session.close();
		}catch(Exception e){
			session.close();
		}
		return ret;
	}

	@Override
	public Integer addUserRole(UserRole p) {

		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			Integer myID = (Integer)session.save(p);
			if(Constants.daoLogger)
				logger.info("UserRole saved successfully, UserRole Details="+p);
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
	public void updateUserRole(UserRole p) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			session.update(p);
			if(Constants.daoLogger)
			logger.info("UserRole updated successfully, UserRole Details="+p);
			t.commit();
			session.close();
		}catch(Exception e){
			t.rollback();
			session.close();
		}


	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserRole> listUserRoles() {
		Session session = this.sessionFactory.openSession();
		try{
			List<UserRole> UserRolesList = session.createQuery("from UserRole").list();
			for(UserRole p : UserRolesList){
				if(Constants.daoLogger)
				logger.info("UserRole List::"+p);
			}
			session.close();
			return UserRolesList;
		}catch(Exception e){
			session.close();
			return null;
		}
	}

	@Override
	public UserRole getUserRoleById(Integer id) {
		Session session = this.sessionFactory.openSession();
		try{
			UserRole p = (UserRole) session.load(UserRole.class, new Integer(id));
			if(Constants.daoLogger)
			logger.info("UserRole loaded successfully, UserRole details="+p);
			session.close();
			return p;
		}catch(Exception e){
			session.close();
			return null;
		}
	}

	@Override
	public void removeUserRole(Integer id) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			UserRole p = (UserRole) session.load(UserRole.class, new Integer(id));
			if(null != p){
				session.delete(p);
			}
			if(Constants.daoLogger)
			logger.info("UserRole deleted successfully, UserRole details="+p);
			t.commit();
			session.close();
		}catch(Exception e){
			t.rollback();
			session.close();
		}
	}

}
