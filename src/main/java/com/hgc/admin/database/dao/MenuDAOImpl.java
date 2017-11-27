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
import com.hgc.admin.database.model.Menu;

@Repository
public class MenuDAOImpl implements MenuDAO {

	private static final Logger logger = LoggerFactory.getLogger(MenuDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public List<Object> queryMenu(String SQL_QUERY){
		Session session = this.sessionFactory.openSession();
		List<Object> ret = new ArrayList<Object>();
		try{
			ret = session.createSQLQuery(SQL_QUERY).list();
			for(Object p : ret){
				if(Constants.daoLogger)
				logger.info("Menu List::"+p);
			}
			session.close();
		}catch(Exception e){
			session.close();
		}
		return ret;
	}

	@Override
	public Integer addMenu(Menu p) {

		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			Integer myID = (Integer)session.save(p);
			if(Constants.daoLogger)
				logger.info("Menu saved successfully, Menu Details="+p);
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
	public void updateMenu(Menu p) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			session.update(p);
			if(Constants.daoLogger)
			logger.info("Menu updated successfully, Menu Details="+p);
			t.commit();
			session.close();
		}catch(Exception e){
			t.rollback();
			session.close();
		}


	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> listMenus() {
		Session session = this.sessionFactory.openSession();
		try{
			List<Menu> MenusList = session.createQuery("from Menu").list();
			for(Menu p : MenusList){
				if(Constants.daoLogger)
				logger.info("Menu List::"+p);
			}
			session.close();
			return MenusList;
		}catch(Exception e){
			session.close();
			return null;
		}
	}

	@Override
	public Menu getMenuById(Integer id) {
		Session session = this.sessionFactory.openSession();
		try{
			Menu p = (Menu) session.load(Menu.class, new Integer(id));
			if(Constants.daoLogger)
			logger.info("Menu loaded successfully, Menu details="+p);
			session.close();
			return p;
		}catch(Exception e){
			session.close();
			return null;
		}
	}

	@Override
	public void removeMenu(Integer id) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			Menu p = (Menu) session.load(Menu.class, new Integer(id));
			if(null != p){
				session.delete(p);
			}
			if(Constants.daoLogger)
			logger.info("Menu deleted successfully, Menu details="+p);
			t.commit();
			session.close();
		}catch(Exception e){
			t.rollback();
			session.close();
		}
	}

}
