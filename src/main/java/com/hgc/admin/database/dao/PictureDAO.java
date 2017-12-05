package com.hgc.admin.database.dao;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.Picture;

public interface PictureDAO {

	public Integer addPicture(Picture p);
	public void updatePicture(Picture p);
	public List<Picture> listPictures();
	public Picture getPictureById(Integer id);
	public void removePicture(Integer id);
	public List<Picture> queryPicture(String SQL_QUERY,String[] fields);
	public HashMap<Integer,Picture> mapPictures();
}
