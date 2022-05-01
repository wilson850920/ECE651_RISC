package edu.duke.ece651.risc.common;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a list of orders collected from a player for a turn 
 */
public class BasicOrderList implements OrderList {

  private final ArrayList<Order> orders;
  
  /**
   * Constructs a BasicOrderList
   */
  public BasicOrderList() {
    this.orders = new ArrayList<Order>();
  }

  /**
   * Adds an Order to the OrderList
   *
   * @param order is the order that is being added
   * @return boolean is true if the order was added, and false if it was not
   */
	@Override
	public boolean addOrder(Order order) {
		orders.add(order);
		return true;
	}

  /**
   * Gets access to the list of orders in an OrderList 
   *
   * @return Iterator<Order> is an iterator with access to the inner list of Orders
   */
	@Override
	public Iterator<Order> getOrders() {
		return this.orders.iterator();
	}

}
