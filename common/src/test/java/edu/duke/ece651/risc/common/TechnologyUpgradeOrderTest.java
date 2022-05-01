package edu.duke.ece651.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TechnologyUpgradeOrderTest {
  
  // Tests player tech upgrade orderAction() for valid orders
  @Test
  public void test_valid_orderAction() {
    OrderRuleChecker ruleChecker = new TechnologyUpgradeRuleChecker();
    Player vader = new BasicPlayer("Vader", 10000);
    Order order = new TechnologyUpgradeOrder(vader);
    order.orderAction(ruleChecker);
    String expected = "Vader upgraded to technology level: 2";
    assertEquals(expected, order.orderMessage());
    order.orderAction(ruleChecker);
    expected = "Vader upgraded to technology level: 3";
    assertEquals(expected, order.orderMessage());
    order.orderAction(ruleChecker);
    order.orderAction(ruleChecker);
    order.orderAction(ruleChecker);
    expected = "Vader upgraded to technology level: 6";
    assertEquals(expected, order.orderMessage());     
  }

  // Tests player tech upgrade orderAction() for invalid orders
  @Test
  public void test_invalid_orderAction() {
    OrderRuleChecker ruleChecker = new TechnologyUpgradeRuleChecker();
    Player maul = new BasicPlayer("Darth Maul", 25);
    Player vader = new BasicPlayer("Darth Vader", 1000);
    Order order = new TechnologyUpgradeOrder(maul);
    order.orderAction(ruleChecker);
    String expected = "Player Technology Upgrade Failed: Cannot upgrade Tech level of player Darth Maul due to insufficient credits";
    assertEquals(expected, order.orderMessage());
    order = new TechnologyUpgradeOrder(vader);
    order.orderAction(ruleChecker);
    order.orderAction(ruleChecker);
    order.orderAction(ruleChecker);
    order.orderAction(ruleChecker);
    order.orderAction(ruleChecker);
    order.orderAction(ruleChecker);
    expected = "Player Technology Upgrade Failed: Cannot upgrade Tech level of player Darth Vader because player is at max level";
    assertEquals(expected, order.orderMessage());     
  }

}
