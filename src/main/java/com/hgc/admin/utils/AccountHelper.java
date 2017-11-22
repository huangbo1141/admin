package com.hgc.admin.utils;

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


public class AccountHelper {
	
	public boolean login(String username,String password,AccountService as){
		String sql = "select id,username,password from tbl_admin_user where username = '"+username+"' and password='"+password+"'";
		List<Object> list = as.queryAccount(sql);
		if(list!=null&&list.size()>0){
			return true;
		}
		return false;
	}	
	
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
		try{
			if(username.length()>0 && password.length()>0){
				
				return true;
			}
		}catch(Exception e){
			System.out.println(e.getMessage());			  
		}
		return false;
	}
	
	public void logout(){
		this.username = null;
		this.password = null;
	}
	
	
}
