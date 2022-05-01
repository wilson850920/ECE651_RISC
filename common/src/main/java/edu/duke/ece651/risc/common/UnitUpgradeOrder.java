package edu.duke.ece651.risc.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Represents an order that upgrades a selected unit in a given territory
 */
public class UnitUpgradeOrder extends BasicOrder {

  private Unit unit;
  private int currentTechLevel;
  private int targetTechLevel;
  
  /**
   * Constructs a UnitUpgradeOrder object
   *
   * @param sender      is the player that sent the order
   * @param source      is the territory that where the unit upgrade is taking
   *                    place
   * @param currentunit is the unit that is being upgraded
   */
  public UnitUpgradeOrder(Player sender, Territory source, int currentBonusLevel, int targetBonusLevel) {
    super(sender, source, null, null); // Smelly but not using target or unit list
    // Simple hard coded map to convert bonus (used on client) into techlevel (used on server) 
    ArrayList<Integer> bonusToTech = new ArrayList<Integer>(Arrays.asList(0, 1, 3, 5, 8, 11, 15));
    this.currentTechLevel = bonusToTech.indexOf(currentBonusLevel);
    this.targetTechLevel = bonusToTech.indexOf(targetBonusLevel);
    this.unit = new BasicUnit();
    this.unit.upgradeUnit(currentTechLevel);
  }

  /**
   * Gets the current tech level of the target unit
   *
   * @return int the current tech level of the target unit
   */
  public int getCurrentTechLevel() {
    return this.currentTechLevel;
  }

  /**
   * Gets the target tech level of the target unit
   *
   * @return int the target tech level of the target unit
   */
  public int getTargetTechLevel() {
    return this.targetTechLevel;
  }

  /**
   * Executes the upgrades on the given units in the territory
   * 
   * @param ruleChecker is the rule checker used to determine if the upgrade is
   *                    valid
   */
  @Override
  public void orderAction(OrderRuleChecker ruleChecker) {
    String check = ruleChecker.checkRule(this);
    if (check != null) {
      this.message = "Unit Upgrade Failed: " + check;
      return;
    }
    Iterator<Unit> it = this.source.getUnits();
    while (it.hasNext()) {
      Unit u = it.next();
      if (u.getTechLevel() == currentTechLevel) {
        u.upgradeUnit(targetTechLevel);
        break;
      }
    }
    int cost = this.unit.upgradeCost(targetTechLevel);
    this.sender.spendResources(cost);
    this.message = "Unit in " + source.getName() + " upgraded from level " + currentTechLevel + " to " + targetTechLevel + " successfully, costing " + this.sender.getName() + " " + cost + " credits";
  }

}
