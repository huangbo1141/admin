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
import com.hgc.admin.database.model.User;

@Repository
public class UserDAOImpl implements UserDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public List<Object> queryUser(String SQL_QUERY){
		Session session = this.sessionFactory.openSession();
		List<Object> ret = new ArrayList<Object>();
		try{
			ret = session.createSQLQuery(SQL_QUERY).list();
			for(Object p : ret){
				if(Constants.daoLogger)
				logger.info("User List::"+p);
			}
			session.close();
		}catch(Exception e){
			session.close();
		}
		return ret;
	}

	@Override
	public Integer addUser(User p) {

		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			Integer myID = (Integer)session.save(p);
			if(Constants.daoLogger)
				logger.info("User saved successfully, User Details="+p);
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
	public void updateUser(User p) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			session.update(p);
			if(Constants.daoLogger)
			logger.info("User updated successfully, User Details="+p);
			t.commit();
			session.close();
		}catch(Exception e){
			t.rollback();
			session.close();
		}


	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listUsers() {
		Session session = this.sessionFactory.openSession();
		try{
			List<User> UsersList = session.createQuery("from User").list();
			for(User p : UsersList){
				if(Constants.daoLogger)
				logger.info("User List::"+p);
			}
			session.close();
			return UsersList;
		}catch(Exception e){
			session.close();
			return null;
		}
	}

	@Override
	public User getUserById(Integer id) {
		Session session = this.sessionFactory.openSession();
		try{
			User p = (User) session.load(User.class, new Integer(id));
			if(Constants.daoLogger)
			logger.info("User loaded successfully, User details="+p);
			session.close();
			return p;
		}catch(Exception e){
			session.close();
			return null;
		}
	}

	@Override
	public void removeUser(Integer id) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			User p = (User) session.load(User.class, new Integer(id));
			if(null != p){
				session.delete(p);
			}
			if(Constants.daoLogger)
			logger.info("User deleted successfully, User details="+p);
			t.commit();
			session.close();
		}catch(Exception e){
			t.rollback();
			session.close();
		}
	}

}
