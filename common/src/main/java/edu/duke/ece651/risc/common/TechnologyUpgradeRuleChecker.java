package edu.duke.ece651.risc.common;

import java.util.HashMap;

public class TechnologyUpgradeRuleChecker extends OrderRuleChecker {

  private HashMap<Integer, Integer> costMap = createCostMap();

  protected HashMap<Integer, Integer> createCostMap() {
    HashMap<Integer, Integer> costMap = new HashMap<Integer, Integer>();
    costMap.put(1, 50);
    costMap.put(2, 75);
    costMap.put(3, 125);
    costMap.put(4, 200);
    costMap.put(5, 300);
    return costMap;
  }

  /**
   * Checks the order to ensure the player isnt tring to upgrade past the max level (6)
   *
   * @param sender is the player who sent the order
   * @return boolean true if rule passed, false if not
   */
  protected boolean maxLevel(Player sender) {
    return (sender.getMaxTechLevel() < 6);
  }

  /**
   * Checks the order to ensure the player has enough resources to execute it 
   *
   * @param sender is the player who sent the order
   * @return boolean true if rule passed, false if not
   */
  protected boolean sufficientResources(Player sender) {
    return (sender.getResources() >= costMap.get(sender.getMaxTechLevel()));
  }
   
  @Override
  public String checkRule(Order order) {
    Player p = order.getSender();
    if (!maxLevel(p)) {
      return "Cannot upgrade Tech level of player " + p.getName() + " because player is at max level";
    }
    if (!sufficientResources(p)) {
      return "Cannot upgrade Tech level of player " + p.getName() + " due to insufficient credits";
    }
    return null;
  }

}
