package com.hgc.admin.utils;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.hgc.admin.database.service.AccountService;

@Component
@Primary
public class BaseHelper implements ControllerHelper {
	/* (non-Javadoc)
	 * @see com.hgc.admin.utils.ControllerHelper#login(java.lang.String, java.lang.String, com.hgc.admin.database.service.AccountService)
	 */
	@Override
	public Object[] login(String username,String password,SessionFactory sessionFactory){
		String sql = "select id,username,password from tbl_admin_user where username = '"+username+"' and password='"+password+"'";
		List list = this.queryList(sql, sessionFactory);
		if(list!=null&&list.size()>0){
			return (Object[]) list.get(0);
		}
		return null;
	}

	@Override
	public boolean fakeLogin() {
		return true;
	}	
	
	@Override
	public List<Object> queryList(String SQL_QUERY,SessionFactory sessionFactory){
		Session session = sessionFactory.openSession();
		List<Object> ret = new ArrayList<Object>();
		try{
			ret = session.createSQLQuery(SQL_QUERY).list();
			session.close();
			  
		}catch(Exception e){
			System.out.println(e.getMessage());			  
		}
		return ret;
	}
}
