package edu.duke.ece651.risc.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Represents an order that upgrades a selected unit in a given territory
 */
public class UnlockCloakOrder extends BasicOrder {


  /**
   * Constructs a UnitUpgradeOrder object
   *
   * @param sender      is the player that sent the order
   */
  public UnlockCloakOrder(Player sender) {
    super(sender, null, null, null);
  }


  /**
   * Executes the Uncloak in the territory
   * 
   * @param ruleChecker is the rule checker used to determine if the uncloak is
   *                    valid
   */
  @Override
  public void orderAction(OrderRuleChecker ruleChecker) {
    String check = ruleChecker.checkRule(this);
    if (check != null) {
      this.message = this.sender.getName()+", Unlock cloak order Failed: " + check;
      this.sender.disableCloakAbility();
      return;
    }
    int cost = 100;
    this.sender.spendResources(cost);
    this.sender.enableCloakAbility();
    this.message = this.sender.getName()+" Successfully Unlocked cloaking ability, costing " + cost + " resources";
  }

}
