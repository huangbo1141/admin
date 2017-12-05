package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.PictureDAO;
import com.hgc.admin.database.model.Picture;

@Service
public class PictureServiceImpl implements PictureService {

	private PictureDAO personDAO;

	public void setPictureDAO(PictureDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addPicture(Picture p) {
		return this.personDAO.addPicture(p);
	}

	@Override
	@Transactional
	public void updatePicture(Picture p) {
		this.personDAO.updatePicture(p);
	}

	@Override
	@Transactional
	public List<Picture> listPictures() {
		return this.personDAO.listPictures();
	}

	@Override
	@Transactional
	public Picture getPictureById(Integer id) {
		return this.personDAO.getPictureById(id);
	}

	@Override
	@Transactional
	public void removePicture(Integer id) {
		this.personDAO.removePicture(id);
	}

	@Override
	public List<Picture> queryPicture(String query,String[] db_fields){
		// TODO Auto-generated method stub
		return this.personDAO.queryPicture(query, db_fields);

	}

	@Override
		public HashMap<Integer, Picture> mapPictures() {
			// TODO Auto-generated method stub
			return this.personDAO.mapPictures();
		}
}
