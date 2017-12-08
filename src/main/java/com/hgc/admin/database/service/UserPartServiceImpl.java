package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.UserPartDAO;
import com.hgc.admin.database.model.UserPart;

@Service
public class UserPartServiceImpl implements UserPartService {

	private UserPartDAO personDAO;

	public void setUserPartDAO(UserPartDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addUserPart(UserPart p) {
		return this.personDAO.addUserPart(p);
	}

	@Override
	@Transactional
	public void updateUserPart(UserPart p) {
		this.personDAO.updateUserPart(p);
	}

	@Override
	@Transactional
	public List<UserPart> listUserParts() {
		return this.personDAO.listUserParts();
	}

	@Override
	@Transactional
	public UserPart getUserPartById(Integer id) {
		return this.personDAO.getUserPartById(id);
	}

	@Override
	@Transactional
	public void removeUserPart(Integer id) {
		this.personDAO.removeUserPart(id);
	}

	@Override
	public List<UserPart> queryUserPart(String query,String[] db_fields){
		// TODO Auto-generated method stub
		return this.personDAO.queryUserPart(query, db_fields);

	}

	@Override
		public HashMap<Integer, UserPart> mapUserParts() {
			// TODO Auto-generated method stub
			return this.personDAO.mapUserParts();
		}
}
