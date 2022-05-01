package edu.duke.ece651.risc.common;

import java.util.Iterator;

public class RevolutionOrderRuleChecker extends OrderRuleChecker {

  /**
   * Checks if the target territory is owned by an enemy
   *
   * @param sender is the player that sent the order
   * @param target is the target territory
   * @return boolean true if valid, false if not
   */
  protected boolean targetIsEnemy(Player sender, Territory target) {
    return !(sender.getName().equals(target.getOwner()));
  }

  /**
   * Checks if the target territory is not already going through a revolution
   * started by the sender
   *
   * @param sender is the player that sent the order
   * @param target is the target territory
   * @return boolean true if valid, false if not
   */
  protected boolean targetNotInRevolution(Player sender, Territory target) {
    Iterator<SpyUnit> it = sender.spyIterator();
    while (it.hasNext()) {
      SpyUnit spy = it.next();
      if (spy.getLocation().equals(target)) {
        if (spy.revolutionStage() != 0) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Checks if the sender has a spy that can preform the order
   *
   * @param sender is the player that sent the order
   * @param target is the target territory for the order
   * @return boolean true if valid, false if not
   */
  protected boolean senderHasSpy(Player sender, Territory target) {
    Iterator<SpyUnit> it = sender.spyIterator();
    while (it.hasNext()) {
      SpyUnit spy = it.next();
      if (spy.getLocation().equals(target)) {
        if (spy.revolutionStage() == 0) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Checks to make sure a RevolutionOrder is valid
   *
   * @param order is the order being checked
   * @return String is a message describing why the order is invalid, or null if
   *         it is vaild
   */
  @Override
  public String checkRule(Order order) {
    Player sender = order.getSender();
    Territory target = order.getTarget();
    if (!targetIsEnemy(sender, target)) {
      return "target must be owned by an enemy player";
    }
    if (!targetNotInRevolution(sender, target)) {
      return "a rebellion is already in progress in " + target.getName();
    }
    if (!senderHasSpy(sender, target)) {
      return "sender does not own a spy in " + target.getName();
    }
    return null;
  }

}
