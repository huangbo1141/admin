package com.hgc.admin.database.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.AccountDAO;
import com.hgc.admin.database.model.Account;

@Service
public class AccountServiceImpl implements AccountService {
	
	private AccountDAO personDAO;

	public void setAccountDAO(AccountDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addAccount(Account p) {
		return this.personDAO.addAccount(p);
	}

	@Override
	@Transactional
	public void updateAccount(Account p) {
		this.personDAO.updateAccount(p);
	}

	@Override
	@Transactional
	public List<Account> listAccounts() {
		return this.personDAO.listAccounts();
	}

	@Override
	@Transactional
	public Account getAccountById(int id) {
		return this.personDAO.getAccountById(id);
	}

	@Override
	@Transactional
	public void removeAccount(int id) {
		this.personDAO.removeAccount(id);
	}

	@Override
	public List<Object> queryAccount(String query) {
		// TODO Auto-generated method stub
		return this.personDAO.queryAccount(query);
		
	}

}
