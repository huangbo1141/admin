package com.hgc.admin.database.dao;

import java.util.List;
import java.util.HashMap;
import com.hgc.admin.database.model.Order;

public interface OrderDAO {

	public Integer addOrder(Order p);
	public void updateOrder(Order p);
	public List<Order> listOrders();
	public Order getOrderById(Integer id);
	public void removeOrder(Integer id);
	public List<Order> queryOrder(String SQL_QUERY,String[] fields);
	public HashMap<Integer,Order> mapOrders();
}
