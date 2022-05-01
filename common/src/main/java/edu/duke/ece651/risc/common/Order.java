package edu.duke.ece651.risc.common;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Interface for different kinds of orders in RISC game.
 */
public interface Order extends Serializable {

  /**
   * Get the source territory of order
   * 
   * @return source territory object
   */
  public Territory getSource();

  /**
   * Get the target territory of order
   * 
   * @return target territory object
   */
  public Territory getTarget();

  /**
   * Sets the source Territory of the order
   *
   * @param newSource is the new source Territory
   */
  public void setSource(Territory newSource);

  /**
   * Sets the target territory of the order
   *
   * @param newTarget is the new target Territory
   */
  public void setTarget(Territory newTarget);

  /**
   * Get the Player giving order
   * 
   * @return a player object
   */
  public Player getSender();

  /**
   * Set the set Player giving order
   * 
   * @param a player object
   */
  public void setSender(Player sender);

  /**
   * Get the total number of units being used in the Order
   * 
   * @return an integer representing number of units
   */
  public int getNumUnits();

  /**
   * Get the access to the list of Units being used in the order
   * 
   * @return Iterator<Unit> for the list of Units in the order
   */
  public HashMap<Integer, Integer> getUnits();

  /**
   * Gets the message from an order that describes its outcome
   *
   * @return String the message with info about the outcome of the order
   */
  public String orderMessage();

  /**
   * Sets the message from an order that describes its outcome
   *
   * @param String the message with info about the outcome of the order
   */
  public void setMessage(String message);

  /**
   * Executes the logic of the Order on its source and target Territories
   * 
   * @param moveRuleChecker
   */
  public void orderAction(OrderRuleChecker moveRuleChecker);

}
