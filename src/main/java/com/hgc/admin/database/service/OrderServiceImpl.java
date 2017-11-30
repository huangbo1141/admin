package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hgc.admin.database.dao.OrderDAO;
import com.hgc.admin.database.model.Order;

@Service
public class OrderServiceImpl implements OrderService {

	private OrderDAO personDAO;

	public void setOrderDAO(OrderDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public Integer addOrder(Order p) {
		return this.personDAO.addOrder(p);
	}

	@Override
	@Transactional
	public void updateOrder(Order p) {
		this.personDAO.updateOrder(p);
	}

	@Override
	@Transactional
	public List<Order> listOrders() {
		return this.personDAO.listOrders();
	}

	@Override
	@Transactional
	public Order getOrderById(Integer id) {
		return this.personDAO.getOrderById(id);
	}

	@Override
	@Transactional
	public void removeOrder(Integer id) {
		this.personDAO.removeOrder(id);
	}

	@Override
	public List<Order> queryOrder(String query,String[] db_fields){
		// TODO Auto-generated method stub
		return this.personDAO.queryOrder(query, db_fields);

	}

	@Override
		public HashMap<Integer, Order> mapOrders() {
			// TODO Auto-generated method stub
			return this.personDAO.mapOrders();
		}
}
