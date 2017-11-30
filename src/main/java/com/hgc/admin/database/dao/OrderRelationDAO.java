package com.hgc.admin.database.dao;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.OrderRelation;

public interface OrderRelationDAO {

	public Integer addOrderRelation(OrderRelation p);
	public void updateOrderRelation(OrderRelation p);
	public List<OrderRelation> listOrderRelations();
	public OrderRelation getOrderRelationById(Integer id);
	public void removeOrderRelation(Integer id);
	public List<OrderRelation> queryOrderRelation(String SQL_QUERY,String[] fields);
	public HashMap<Integer,OrderRelation> mapOrderRelations();
}
