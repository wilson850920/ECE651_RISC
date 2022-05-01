package edu.duke.ece651.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UnitUpgradeOrderTest {

  // Tests unit upgrade order action for vaild upgrade orders
  @Test
  public void test_valid_orderAction() {
    OrderRuleChecker ruleChecker = new UnitUpgradeRuleChecker();
    Player vader = new BasicPlayer("Vader", 10000);
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    for (int i = 0; i < 2; i++) {
      hoth.addUnit(0);
    }
    vader.addTerritory(hoth);
    Order upgrade1 = new UnitUpgradeOrder(vader, hoth, 0, 5);
    Order upgrade2 = new UnitUpgradeOrder(vader, hoth, 5, 8);
    Order upgrade3 = new UnitUpgradeOrder(vader, hoth, 8, 15);
    
    vader.upgradeMaxTechLevel();
    vader.upgradeMaxTechLevel();
    vader.upgradeMaxTechLevel();
    upgrade1.orderAction(ruleChecker);
    String expected1 = "Unit in Hoth upgraded from level 0 to 3 successfully, costing Vader 30 credits";
    assertEquals(expected1, upgrade1.orderMessage());

    vader.upgradeMaxTechLevel();
    upgrade2.orderAction(ruleChecker);
    String expected2 = "Unit in Hoth upgraded from level 3 to 4 successfully, costing Vader 25 credits";
    assertEquals(expected2, upgrade2.orderMessage());

    vader.upgradeMaxTechLevel();
    vader.upgradeMaxTechLevel();
    upgrade3.orderAction(ruleChecker);
    String expected3 = "Unit in Hoth upgraded from level 4 to 6 successfully, costing Vader 85 credits";
    assertEquals(expected3, upgrade3.orderMessage());
  }

  // Tests unit upgrade order action for invaild upgrade orders
  @Test
  public void test_invalid_orderAction() {
    OrderRuleChecker ruleChecker = new UnitUpgradeRuleChecker();
    Player vader = new BasicPlayer("Vader", 10000);
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    for (int i = 0; i < 2; i++) {
      hoth.addUnit(0);
    }
    vader.addTerritory(hoth);
    Order upgrade1 = new UnitUpgradeOrder(vader, hoth, 0, 20);
    Order upgrade2 = new UnitUpgradeOrder(vader, hoth, 1, 5);
    Order upgrade3 = new UnitUpgradeOrder(vader, hoth, 3, 8);
    
    upgrade1.orderAction(ruleChecker);
    String expected1 = "Unit Upgrade Failed: Invalid order. Player tech level not sufficient";
    assertEquals(expected1, upgrade1.orderMessage());

    vader.upgradeMaxTechLevel();
    vader.upgradeMaxTechLevel();
    upgrade2.orderAction(ruleChecker);
    String expected2 = "Unit Upgrade Failed: Invalid order. Source does not have the specified unit to upgrade";
    assertEquals(expected2, upgrade2.orderMessage());

    upgrade3.orderAction(ruleChecker);
    assertEquals(expected1, upgrade3.orderMessage());
  }

}
