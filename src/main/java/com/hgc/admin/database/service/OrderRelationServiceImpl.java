package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.OrderRelationDAO;
import com.hgc.admin.database.model.OrderRelation;

@Service
public class OrderRelationServiceImpl implements OrderRelationService {

	private OrderRelationDAO personDAO;

	public void setOrderRelationDAO(OrderRelationDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addOrderRelation(OrderRelation p) {
		return this.personDAO.addOrderRelation(p);
	}

	@Override
	@Transactional
	public void updateOrderRelation(OrderRelation p) {
		this.personDAO.updateOrderRelation(p);
	}

	@Override
	@Transactional
	public List<OrderRelation> listOrderRelations() {
		return this.personDAO.listOrderRelations();
	}

	@Override
	@Transactional
	public OrderRelation getOrderRelationById(Integer id) {
		return this.personDAO.getOrderRelationById(id);
	}

	@Override
	@Transactional
	public void removeOrderRelation(Integer id) {
		this.personDAO.removeOrderRelation(id);
	}

	@Override
	public List<OrderRelation> queryOrderRelation(String query,String[] db_fields){
		// TODO Auto-generated method stub
		return this.personDAO.queryOrderRelation(query, db_fields);

	}

	@Override
		public HashMap<Integer, OrderRelation> mapOrderRelations() {
			// TODO Auto-generated method stub
			return this.personDAO.mapOrderRelations();
		}
}
