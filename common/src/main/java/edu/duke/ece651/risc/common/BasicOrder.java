/**
 * Basic Order class that has sender, source territory, target territory, units in order 
 * and type of order info.
 */
package edu.duke.ece651.risc.common;

import java.util.HashMap;

/**
 * Basic implementation of the Order interface
 */
public class BasicOrder implements Order {

  protected Player sender;
  protected Territory source;
  protected Territory target;
  protected HashMap<Integer, Integer> unitsMap;
  protected String message;

  /**
   * Constructs a BasicOrder object
   * 
   * @param sender   is the player that sent the order
   * @param source   is the territory where the order was sent from
   * @param target   is the territory where the order was sent to
   * @param unitsMap is the list of units bonus to count used in the order
   *
   */
  public BasicOrder(Player sender, Territory source, Territory target, HashMap<Integer, Integer> unitsMap) {
    this.sender = sender;
    this.source = source;
    this.target = target;
    this.unitsMap = unitsMap;
    this.message = null;
  }

  /**
   * Get the source territory of order
   * 
   * @return source territory object
   */
  @Override
  public Territory getSource() {
    return this.source;
  }

  /**
   * Get the target territory of order
   * 
   * @return target territory object
   */
  @Override
  public Territory getTarget() {
    return this.target;
  }

  /**
   * Sets the source Territory of the order
   *
   * @param newSource is the new source Territory
   */
  @Override
  public void setSource(Territory newSource) {
    this.source = newSource;
  }

  /**
   * Sets the target territory of the order
   *
   * @param newTarget is the new target Territory
   */
  @Override
  public void setTarget(Territory newTarget) {
    this.target = newTarget;
  }

  /**
   * Get the name of player giving order
   * 
   * @return a player object
   */
  @Override
  public Player getSender() {
    return this.sender;
  }

  /**
   * Set the set Player giving order
   * 
   * @param a player object
   */
  @Override
  public void setSender(Player sender) {
    this.sender = sender;
  }

  /**
   * Get the number of units being used in the Order
   * 
   * @return an integer representing number of units
   */
  @Override
  public int getNumUnits() {
    int total = 0;
    for (Integer bonus : this.unitsMap.keySet()) {
      total += this.unitsMap.get(bonus);
    }
    return total;
  }

  /**
   * Get the access to the list of Units being used in the order
   * 
   * @return Iterator<Unit> for the list of Units in the order
   */
  @Override
  public HashMap<Integer, Integer> getUnits() {
    return this.unitsMap;
  }

  /**
   * Gets the message from an order that describes its outcome
   *
   * @return String the message with info about the outcome of the order
   */
  @Override
  public String orderMessage() {
    return this.message;
  }

  /**
   * Sets the message from an order that describes its outcome
   *
   * @param String the message with info about the outcome of the order
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Executes the logic of the AttackOrder on its source and target Territories
   *
   * @param moveRuleChecker
   */
  @Override
  public void orderAction(OrderRuleChecker ruleChecker) {
    // does nothing and cannot be called (smelly but it works)
  }

}
