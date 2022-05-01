package edu.duke.ece651.risc.common;

import java.util.Iterator;

public class UnitUpgradeRuleChecker extends OrderRuleChecker {

  /**
   * Checks if the order is requesting a valid upgrade by checking for if Player
   * has a high enough tech leve for the upgrade
   *
   * @param sender           is the player who sent the order
   * @param currentTechLevel is the current tech level of the unit being upgraded
   * @param targetTechLevel  is the target tech level of the unit being upgraded
   * @return boolean true if valid, false if not
   */
  protected boolean validTechLevel(Player sender, int currentTechLevel, int targetTechLevel) {
    if (targetTechLevel > sender.getMaxTechLevel()) {
      return false;
    } else if (targetTechLevel == -1) { // Check for max tech level 
      return false;
    }
    return true;
  }

  /**
   * Checks if the order is requesting a valid upgrade from by checking if the
   * sender has enough resources
   *
   * @param sender           is the player who sent the order
   * @param currentTechLevel is the current tech level of the unit being upgraded
   * @param targetTechLevel  is the target tech level of the unit being upgraded
   * @return boolean true if valid, false if not
   */
  protected boolean sufficentResources(Player sender, int currentTechLevel, int targetTechLevel) {
    Unit temp = new BasicUnit();
    temp.upgradeUnit(currentTechLevel);
    int cost = temp.upgradeCost(targetTechLevel);
    if (cost > sender.getResources()) {
      return false;
    }
    return true;
  }

  /**
   * Checks if the order is requesting a valid upgrade from by checking by
   * checking if a source has a unit with currentTechLevel for the upgrade
   *
   * @param source           is the source territory of the order
   * @param currentTechLevel is the current tech level of the unit being upgraded
   * @return boolean true if valid, false if not
   */
  protected boolean sourceContainsUnit(Territory source, int currentTechLevel) {
    Iterator<Unit> it = source.getUnits();
    while (it.hasNext()) {
      Unit u = it.next();
      if (u.getTechLevel() == currentTechLevel) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String checkRule(Order order) {
    Player p = order.getSender();
    Territory source = order.getSource();
    if (!sourceSenderValidation(source, p)) {
      return "Invalid order. Player doesn't own source planet";
    } else if (!validTechLevel(p, ((UnitUpgradeOrder) order).getCurrentTechLevel(),
        ((UnitUpgradeOrder) order).getTargetTechLevel())) {
      return "Invalid order. Player tech level not sufficient";
    } else if (!sufficentResources(p, ((UnitUpgradeOrder) order).getCurrentTechLevel(),
        ((UnitUpgradeOrder) order).getTargetTechLevel())) {
      return "Invalid order. Sender does not have enough credits";
    } else if (!sourceContainsUnit(source, ((UnitUpgradeOrder) order).getCurrentTechLevel())) {
      return "Invalid order. Source does not have the specified unit to upgrade";
    }
    return null;
  }

}
