package edu.duke.ece651.risc.common;

public class TechnologyUpgradeOrder extends BasicOrder {

  /**
   * Constructs a BasicOrder object
   *
   * @param sender   is the player that sent the order
   * @param source   is the territory where the order was sent from
   * @param target   is the territory where the order was sent to
   * @param unitsMap
   */
  public TechnologyUpgradeOrder(Player sender) {
    super(sender, null, null, null);
  }

  /**
   * Executes the technology level upgrade on the Player
   * 
   * @param ruleChecker is the rule checker used to determine if the upgrade is
   *                    valid
   */
  @Override
  public void orderAction(OrderRuleChecker ruleChecker) {
    String check = ruleChecker.checkRule(this);
    if (check != null) {
      this.message = "Player Technology Upgrade Failed: " + check;
      return;
    }
    this.sender.upgradeMaxTechLevel();
    this.message = this.sender.getName() + " upgraded to technology level: " + this.sender.getMaxTechLevel();
  }

}
