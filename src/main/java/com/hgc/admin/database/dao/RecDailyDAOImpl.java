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
import com.hgc.admin.database.model.RecDaily;

@Repository
public class RecDailyDAOImpl implements RecDailyDAO {

	private static final Logger logger = LoggerFactory.getLogger(RecDailyDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public List<RecDaily> queryRecDaily(String SQL_QUERY,String[] db_fields){
		Session session = this.sessionFactory.openSession();
		List<RecDaily> ret = new ArrayList<RecDaily>();
		try{
			List<Object> temp = session.createSQLQuery(SQL_QUERY).list();
			for(Object p : temp){
				if(Constants.daoLogger)
				logger.info("RecDaily List::"+p);

				Date date = new Date();

				Object[] ptemp = (Object[])p;
				if(ptemp.length == db_fields.length){
					Class<?> T =  RecDaily.class;
					RecDaily station = new RecDaily();
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
									String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

									String method_name = "set"+m_name.substring(0, 1).toUpperCase() + m_name.substring(1);
									Method method = T.getMethod(method_name, String.class);
									method.invoke(station, formattedDate);
								}else if(d_type.equals("double")){
									String method_name = "set"+m_name.substring(0, 1).toUpperCase() + m_name.substring(1);
									Method method = T.getMethod(method_name, double.class);
									method.invoke(station, (Double) d_value);
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
	public Integer addRecDaily(RecDaily p) {

		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			Integer myID = (Integer)session.save(p);
			if(Constants.daoLogger)
				logger.info("RecDaily saved successfully, RecDaily Details="+p);
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
	public void updateRecDaily(RecDaily p) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			session.update(p);
			if(Constants.daoLogger)
			logger.info("RecDaily updated successfully, RecDaily Details="+p);
			t.commit();
			session.close();
		}catch(Exception e){
			t.rollback();
			session.close();
		}


	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RecDaily> listRecDailys() {
		Session session = this.sessionFactory.openSession();
		try{
			List<RecDaily> RecDailysList = session.createQuery("from RecDaily").list();
			for(RecDaily p : RecDailysList){
				if(Constants.daoLogger)
				logger.info("RecDaily List::"+p);
			}
			session.close();
			return RecDailysList;
		}catch(Exception e){
			session.close();
			return null;
		}
	}

	@Override
	public RecDaily getRecDailyById(Integer id) {
		Session session = this.sessionFactory.openSession();
		try{
			RecDaily p = (RecDaily) session.load(RecDaily.class, new Integer(id));
			if(Constants.daoLogger)
			logger.info("RecDaily loaded successfully, RecDaily details="+p);
			session.close();
			return p;
		}catch(Exception e){
			session.close();
			return null;
		}
	}

	@Override
	public void removeRecDaily(Integer id) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try{
			RecDaily p = (RecDaily) session.load(RecDaily.class, new Integer(id));
			if(null != p){
				session.delete(p);
			}
			if(Constants.daoLogger)
			logger.info("RecDaily deleted successfully, RecDaily details="+p);
			t.commit();
			session.close();
		}catch(Exception e){
			t.rollback();
			session.close();
		}
	}

	@Override
	public HashMap<Integer, RecDaily> mapRecDailys() {
		Session session = this.sessionFactory.openSession();
		try{
			List<RecDaily> RecDailysList = session.createQuery("from RecDaily").list();
			HashMap<Integer,RecDaily> mapRecDaily = new HashMap<Integer,RecDaily>();
			for(RecDaily p : RecDailysList){
				if(Constants.daoLogger)
				logger.info("RecDaily List::"+p);
				mapRecDaily.put(p.getId(), p);
			}
			session.close();
			return mapRecDaily;
		}catch(Exception e){
			session.close();
			return null;
		}
	}

}
