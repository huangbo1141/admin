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
import com.hgc.admin.database.model.MenuAction;

@Repository
public class MenuActionDAOImpl implements MenuActionDAO {

	private static final Logger logger = LoggerFactory.getLogger(MenuActionDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public List<Object> queryMenuAction(String SQL_QUERY){
		Session session = this.sessionFactory.openSession();
		List<Object> ret = new ArrayList<Object>();
		try{
			ret = session.createSQLQuery(SQL_QUERY).list();
			for(Object p : ret){
				if(Constants.daoLogger)
				logger.info("MenuAction List::"+p);
			}
			session.close();
		}catch(Exception e){
			session.close();
		}
		return ret;
	}

	@Override
	public Integer addMenuAction(MenuAction p) {

		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			Integer myID = (Integer)session.save(p);
			if(Constants.daoLogger)
				logger.info("MenuAction saved successfully, MenuAction Details="+p);
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
	public void updateMenuAction(MenuAction p) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			session.update(p);
			if(Constants.daoLogger)
			logger.info("MenuAction updated successfully, MenuAction Details="+p);
			t.commit();
			session.close();
		}catch(Exception e){
			t.rollback();
			session.close();
		}


	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuAction> listMenuActions() {
		Session session = this.sessionFactory.openSession();
		try{
			List<MenuAction> MenuActionsList = session.createQuery("from MenuAction").list();
			for(MenuAction p : MenuActionsList){
				if(Constants.daoLogger)
				logger.info("MenuAction List::"+p);
			}
			session.close();
			return MenuActionsList;
		}catch(Exception e){
			session.close();
			return null;
		}
	}

	@Override
	public MenuAction getMenuActionById(Integer id) {
		Session session = this.sessionFactory.openSession();
		try{
			MenuAction p = (MenuAction) session.load(MenuAction.class, new Integer(id));
			if(Constants.daoLogger)
			logger.info("MenuAction loaded successfully, MenuAction details="+p);
			session.close();
			return p;
		}catch(Exception e){
			session.close();
			return null;
		}
	}

	@Override
	public void removeMenuAction(Integer id) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			MenuAction p = (MenuAction) session.load(MenuAction.class, new Integer(id));
			if(null != p){
				session.delete(p);
			}
			if(Constants.daoLogger)
			logger.info("MenuAction deleted successfully, MenuAction details="+p);
			t.commit();
			session.close();
		}catch(Exception e){
			t.rollback();
			session.close();
		}
	}

}
