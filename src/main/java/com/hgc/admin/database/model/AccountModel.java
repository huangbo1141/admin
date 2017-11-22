package com.hgc.admin.database.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.hgc.admin.HibernateUtil;
import com.hgc.admin.database.service.AccountService;


public class AccountModel {
	
	
	
	public boolean login(String username,String password,AccountService as){
		String sql = "select id,username,password from Account where username = '"+username+"' and password='"+password+"'";
		List<Object> list = as.queryAccount(sql);
		if(list!=null&&list.size()>0){
			return true;
		}
		return false;
//		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//		Session session = sessionFactory.getCurrentSession();
//
//		// Get All Employees
//		Transaction tx = session.beginTransaction();
//		SQLQuery query = session.createSQLQuery("select id,username,password from Account where username = 'admin'");
//		List<Object[]> rows = query.list();
//		Account emp = null;
//		for(Object[] row : rows){
//			
//			emp = new Account();
//			
//			emp.setId(Integer.parseInt(row[0].toString()));
//			emp.setUsername(row[1].toString());
//			emp.setPassword(row[2].toString());
//			
//			break;
//		}
//		if(emp!=null && username.equalsIgnoreCase(emp.getUsername()) && password.equalsIgnoreCase(emp.getPassword())){
//			return true;
//		}
//		return false;
	}
	
//	public boolean login2(String username,String password){
//		
//		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//		Session session = sessionFactory.getCurrentSession();
//
//		// Get All Employees
//		Transaction tx = session.beginTransaction();
//		SQLQuery query = session.createSQLQuery("select id,username,password from Account where username = 'admin'");
//		List<Object[]> rows = query.list();
//		Account emp = null;
//		for(Object[] row : rows){
//			
//			emp = new Account();
//			
//			emp.setId(Integer.parseInt(row[0].toString()));
//			emp.setUsername(row[1].toString());
//			emp.setPassword(row[2].toString());
//			
//			break;
//		}
//		if(emp!=null && username.equalsIgnoreCase(emp.getUsername()) && password.equalsIgnoreCase(emp.getPassword())){
//			return true;
//		}
//		return false;
//	}
	
	
	private int id;
	
	private String username;
	private String password;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean hasLogin(){
		System.out.println("滴滴货车后台管理系统");	
		return true;
//		try{
//			if(username.length()>0 && password.length()>0){
//				return true;
//			}
//		}catch(Exception e){
//			System.out.println(e.getMessage());			  
//		}
//		return false;
	}
	
	public void logout(){
		this.username = null;
		this.password = null;
	}
	
}
