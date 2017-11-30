package com.hgc.admin.database.service;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.Order;

public interface OrderService {

	public Integer addOrder(Order p);
	public void updateOrder(Order p);
	public List<Order> listOrders();
	public Order getOrderById(Integer id);
	public void removeOrder(Integer id);
	public List<Order> queryOrder(String query,String[] db_fields);
	public HashMap<Integer,Order> mapOrders();
}
