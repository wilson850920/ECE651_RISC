package edu.duke.ece651.risc.common;

import java.util.HashMap;
import java.util.Iterator;

public class SpyMoveOrder extends BasicOrder {
  /**
   * Constructs a BasicOrder object
   *
   * @param sender   is the player that sent the order
   * @param source   is the territory where the order was sent from
   * @param target   is the territory where the order was sent to
   * @param unitsMap is the list of units bonus to count used in the order
   */
  public SpyMoveOrder(Player sender, Territory source, Territory target) {
    super(sender, source, target, null);
  }

  @Override
  public void orderAction(OrderRuleChecker ruleChecker) {
    String check = ruleChecker.checkRule(this);
    if (check != null && check.contains("Invalid order.")) {
      this.message = "Spy not cleared to move. " + check;
      return;
    }
    int cost = Integer.parseInt(check);
    SpyUnit spy = null;
    Iterator<SpyUnit> it = this.sender.spyIterator();
    while (it.hasNext()) {
      SpyUnit tempSpy = it.next();
      if (tempSpy.getLocation().getName().equals(source.getName())) {
        spy = tempSpy;
      }
    }
    // Check for revolution
    if (spy.revolutionStage() != 0) {
      this.message = "Spy not cleared to move.. they are busy with a top secret operation!";
      return;
    }
    System.out.println("Moving Spy");
    this.sender.spendResources(cost);
    spy.setLocation(target);
    this.message = this.sender.getName() + " has given new orders to one of their spies costing " + cost
        + " credits... lookout!";
    return;
  }
  
}
