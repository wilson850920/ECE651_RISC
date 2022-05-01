package edu.duke.ece651.risc.server;

import java.util.ArrayList;
import java.util.Iterator;

import edu.duke.ece651.risc.common.AttackOrder;
import edu.duke.ece651.risc.common.MoveOrder;
import edu.duke.ece651.risc.common.Order;
import edu.duke.ece651.risc.common.OrderList;
import edu.duke.ece651.risc.common.RevolutionOrder;
import edu.duke.ece651.risc.common.TechnologyUpgradeOrder;
import edu.duke.ece651.risc.common.UnitUpgradeOrder;
import edu.duke.ece651.risc.common.*;

/**
 * Turn Interface for maintaining a list of all orders from players
 */
public class Turn {
  private final ArrayList<Order> moveOrders;
  private final ArrayList<Order> attackOrders;
  private final ArrayList<Order> unitUpgradeOrders;
  private final ArrayList<Order> techUpgradeOrders;
  private final ArrayList<Order> spyUpgradeOrders;
  private final ArrayList<Order> spyMoveOrders;
  private final ArrayList<Order> revolutionOrders;
  private final ArrayList<Order> cloakTerritoryOrders;
  private final ArrayList<Order> unlockcloakOrders;
  private final ArrayList<String> orderMessages;

  /**
   * Creates an empty Turn object
   */
  public Turn() {
    this.moveOrders = new ArrayList<Order>();
    this.attackOrders = new ArrayList<Order>();
    this.unitUpgradeOrders = new ArrayList<Order>();
    this.techUpgradeOrders = new ArrayList<Order>();
    this.spyUpgradeOrders = new ArrayList<Order>();
    this.spyMoveOrders = new ArrayList<Order>();;
    this.revolutionOrders = new ArrayList<Order>();
    this.cloakTerritoryOrders = new ArrayList<Order>();
    this.unlockcloakOrders =  new ArrayList<Order>();
    this.orderMessages = new ArrayList<String>();
    
  }

  /**
   * Add list of orders of a player to the priority list of orders
   * 
   * @param order list is the list of orders given by a single player
   * 
   */
  public void addOrders(OrderList orderList) throws IllegalArgumentException {
    Iterator<Order> it = orderList.getOrders();
    while (it.hasNext()) {
      Order currOrder = it.next();
      if (currOrder instanceof MoveOrder) {
        moveOrders.add(currOrder);
      } else if (currOrder instanceof AttackOrder) {
        attackOrders.add(currOrder);
      } else if (currOrder instanceof UnitUpgradeOrder) {
        unitUpgradeOrders.add(currOrder);
      } else if (currOrder instanceof TechnologyUpgradeOrder) {
        techUpgradeOrders.add(currOrder);
      } else if (currOrder instanceof SpyUpgradeOrder) {
        spyUpgradeOrders.add(currOrder);
      } else if (currOrder instanceof SpyMoveOrder) {
        spyMoveOrders.add(currOrder);
      } else if (currOrder instanceof RevolutionOrder) {
        revolutionOrders.add(currOrder);
      } else if (currOrder instanceof CloakTerritoryOrder) {
        cloakTerritoryOrders.add(currOrder);
      } else if (currOrder instanceof UnlockCloakOrder){
        unlockcloakOrders.add(currOrder);
      }
    }
  }

  /**
   * Access the list of MoveOrders
   * 
   * @return the list of MoveOrders
   */
  public ArrayList<Order> getMoveOrders() {
    return this.moveOrders;
  }

  /**
   * Access the list of AttackOrders
   * 
   * @return the list of AttackOrders
   */
  public ArrayList<Order> getAttackOrders() {
    return this.attackOrders;
  }

  /**
   * Access the list of UnitUpgradeOrders
   * 
   * @return the list of UnitUpgradeOrders
   */
  public ArrayList<Order> getUnitUpgradeOrders() {
    return this.unitUpgradeOrders;
  }

  /**
   * Access the list of TechnologyUpgradeOrders
   * 
   * @return the list of TechnologyUpgradeOrders
   */
  public ArrayList<Order> getTechUpgradeOrders() {
    return this.techUpgradeOrders;
  }

  /**
   * Access the list of SpyUpgradeOrders
   *
   * @return the list of SpyUpgradeOrders
   */
  public ArrayList<Order> getSpyUpgradeOrders() {
    return this.spyUpgradeOrders;
  }

  /**
   * Access the list of SpyMoveOrders
   *
   * @return the list of SpyMoveOrders
   */
  public ArrayList<Order> getSpyMoveOrders() {
    return this.spyMoveOrders;
  }

  /**
   * Access the list of RevolutionOrders
   *
   * @return the list of RevolutionOrders
   */
  public ArrayList<Order> getRevolutionOrders() {
    return this.revolutionOrders;
  }
  /**
   * Access the list of RevolutionOrders
   *
   * @return the list of RevolutionOrders
   */
  public ArrayList<Order> getUnlockCloakOrders() {
    return this.unlockcloakOrders;
  }

  /**
   * Access the list of CloakTerritoryOrders
   *
   * @return the list of CloakTerritoryOrders
   */
  public ArrayList<Order> getCloakTerritoryOrders() {
    return this.cloakTerritoryOrders;
  }

  /**
   * Add a message to the list of Order messages in the turn
   * 
   * @param the message to add
   */
  public void addOrderMessage(String message) {
    this.orderMessages.add(message);
  }

  /**
   * Access the list of Order messages in the turn
   * 
   * @return the list of messages describing the outcome of the Orders in the Turn
   */
  public ArrayList<String> getOrderMessages() {
    return this.orderMessages;
  }
  
}
