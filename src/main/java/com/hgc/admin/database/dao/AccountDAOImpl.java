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

import com.hgc.admin.Constants;
import com.hgc.admin.database.model.Account;

@Repository
public class AccountDAOImpl implements AccountDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public List<Object> queryAccount(String SQL_QUERY){
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
	public Integer addAccount(Account p) {
		
		Session s = this.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		Integer myID = (Integer)s.save(p);
		t.commit();
		s.close();
		if(Constants.daoLogger)
			logger.info("Account saved successfully, Account Details="+p);
		return myID;
	}

	@Override
	public void updateAccount(Account p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		if(Constants.daoLogger)
		logger.info("Account updated successfully, Account Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> listAccounts() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Account> accountsList = session.createQuery("from Account").list();
		for(Account p : accountsList){
			if(Constants.daoLogger)
			logger.info("Account List::"+p);
		}
		return accountsList;
	}

	@Override
	public Account getAccountById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		Account p = (Account) session.load(Account.class, new Integer(id));
		if(Constants.daoLogger)
		logger.info("Account loaded successfully, Account details="+p);
		return p;
	}

	@Override
	public void removeAccount(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Account p = (Account) session.load(Account.class, new Integer(id));
		if(null != p){
			session.delete(p);
		}
		if(Constants.daoLogger)
		logger.info("Account deleted successfully, account details="+p);
	}

}
