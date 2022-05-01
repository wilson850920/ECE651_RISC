package edu.duke.ece651.risc.common;

import java.io.Serializable;
import java.util.Iterator;

public interface OrderList extends Serializable{
	/**
	 * Interface for list of orders in RISC game.
	 */
	
	/**
	 * adds an order to the list of orders
	 * @param order is the new order to be added to the list
	 * @return true if addition of order after validation is successful
	 */
	public boolean addOrder(Order order);
  
	/**
	 * get iterator to the list of orders
	 * @return iterator to the list of orders
	 */
	public Iterator<Order> getOrders();

}
