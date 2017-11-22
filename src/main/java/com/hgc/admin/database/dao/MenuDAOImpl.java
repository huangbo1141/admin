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
			session.close();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return ret;
	}

	@Override
	public Integer addMenu(Menu p) {

		Session s = this.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		Integer myID = (Integer)s.save(p);
		t.commit();
		s.close();
		if(Constants.daoLogger)
			logger.info("Menu saved successfully, Menu Details="+p);
		return myID;
	}

	@Override
	public void updateMenu(Menu p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		if(Constants.daoLogger)
		logger.info("Menu updated successfully, Menu Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> listMenus() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Menu> MenusList = session.createQuery("from Menu").list();
		for(Menu p : MenusList){
			if(Constants.daoLogger)
			logger.info("Menu List::"+p);
		}
		return MenusList;
	}

	@Override
	public Menu getMenuById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Menu p = (Menu) session.load(Menu.class, new Integer(id));
		if(Constants.daoLogger)
		logger.info("Menu loaded successfully, Menu details="+p);
		return p;
	}

	@Override
	public void removeMenu(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Menu p = (Menu) session.load(Menu.class, new Integer(id));
		if(null != p){
			session.delete(p);
		}
		if(Constants.daoLogger)
		logger.info("Menu deleted successfully, Menu details="+p);
	}

}
