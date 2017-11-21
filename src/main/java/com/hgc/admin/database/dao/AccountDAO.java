package com.hgc.admin.database.dao;

import java.util.List;

import com.hgc.admin.database.model.Account;

public interface AccountDAO {

	public Integer addAccount(Account p);
	public void updateAccount(Account p);
	public List<Account> listAccounts();
	public Account getAccountById(int id);
	public void removeAccount(int id);
	public List<Object> queryAccount(String query);
}
