package com.hgc.admin.database.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;

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
	public List<Dan> queryDan(String SQL_QUERY,String[] db_fields){
		Session session = this.sessionFactory.openSession();
		List<Dan> ret = new ArrayList<Dan>();
		try{
			List<Object> temp = session.createSQLQuery(SQL_QUERY).list();
			for(Object p : temp){
				if(Constants.daoLogger)
				logger.info("Dan List::"+p);

				Date date = new Date();

				Object[] ptemp = (Object[])p;
				if(ptemp.length == db_fields.length){
					Class<?> T =  Dan.class;
					Dan station = new Dan();
					Field[] fields = T.getDeclaredFields();
					for(int i=0; i<db_fields.length;i++){
						String d_name = db_fields[i];
						Object d_value = ptemp[i];

						List<String> allowed_names = new ArrayList<String>();
						if(d_value!=null)
						for (Field f:fields) {
							String m_name = f.getName();
							if(m_name.equals(d_name)){
								f.setAccessible(true);
								String d_type = d_value.getClass().getSimpleName().toLowerCase();

								if(d_type.equals("integer")){
									String method_name = "set"+m_name.substring(0, 1).toUpperCase() + m_name.substring(1);
									Method method = T.getMethod(method_name, Integer.class);
									method.invoke(station, (Integer) d_value);
								}else if(d_type.equals("timestamp")){
									Timestamp ts = (Timestamp) d_value;
									date.setTime(ts.getTime());
									String formattedDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);

									String method_name = "set"+m_name.substring(0, 1).toUpperCase() + m_name.substring(1);
									Method method = T.getMethod(method_name, String.class);
									method.invoke(station, formattedDate);
								}else{
									String method_name = "set"+m_name.substring(0, 1).toUpperCase() + m_name.substring(1);
									Method method = T.getMethod(method_name, String.class);
									method.invoke(station, (String)d_value);
								}
							}
						}
					}
					ret.add(station);
				}


			}
			session.close();
		}catch(Exception e){
			session.close();
		}
		return ret;
	}

	@Override
	public Integer addDan(Dan p) {

		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			Integer myID = (Integer)session.save(p);
			if(Constants.daoLogger)
				logger.info("Dan saved successfully, Dan Details="+p);
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
	public void updateDan(Dan p) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			session.update(p);
			if(Constants.daoLogger)
			logger.info("Dan updated successfully, Dan Details="+p);
			t.commit();
			session.close();
		}catch(Exception e){
			t.rollback();
			session.close();
		}


	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dan> listDans() {
		Session session = this.sessionFactory.openSession();
		try{
			List<Dan> DansList = session.createQuery("from Dan").list();
			for(Dan p : DansList){
				if(Constants.daoLogger)
				logger.info("Dan List::"+p);
			}
			session.close();
			return DansList;
		}catch(Exception e){
			session.close();
			return null;
		}
	}

	@Override
	public Dan getDanById(Integer id) {
		Session session = this.sessionFactory.openSession();
		try{
			Dan p = (Dan) session.load(Dan.class, new Integer(id));
			if(Constants.daoLogger)
			logger.info("Dan loaded successfully, Dan details="+p);
			session.close();
			return p;
		}catch(Exception e){
			session.close();
			return null;
		}
	}

	@Override
	public void removeDan(Integer id) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			Dan p = (Dan) session.load(Dan.class, new Integer(id));
			if(null != p){
				session.delete(p);
			}
			if(Constants.daoLogger)
			logger.info("Dan deleted successfully, Dan details="+p);
			t.commit();
			session.close();
		}catch(Exception e){
			t.rollback();
			session.close();
		}
	}

	@Override
	public HashMap<Integer, Dan> mapDans() {
		Session session = this.sessionFactory.openSession();
		try{
			List<Dan> DansList = session.createQuery("from Dan").list();
			HashMap<Integer,Dan> mapDan = new HashMap<Integer,Dan>();
			for(Dan p : DansList){
				if(Constants.daoLogger)
				logger.info("Dan List::"+p);
				mapDan.put(p.getId(), p);
			}
			session.close();
			return mapDan;
		}catch(Exception e){
			session.close();
			return null;
		}
	}

}
