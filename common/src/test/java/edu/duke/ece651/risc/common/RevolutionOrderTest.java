package edu.duke.ece651.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RevolutionOrderTest {

  // Tests a RevolutionOrder with a target that is owned by the player
  @Test
  public void test_friendly_target() {

    Player vader = new BasicPlayer("Vader", 1000);
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    Order o = new RevolutionOrder(vader, hoth, 100);
    OrderRuleChecker ruleChecker = new RevolutionOrderRuleChecker();
    o.orderAction(ruleChecker);
    String expected = "Rebellion Failed: target must be owned by an enemy player";
    assertEquals(expected, o.orderMessage());
  }

  // Tests a RevolutionOrder with a target that is already in a revolution started
  // by the player
  @Test
  public void test_already_revolution_in_target() {
    Player vader = new BasicPlayer("Vader", 100);
    Territory hoth = new BasicTerritory("Hoth", "Luke");
    SpyUnit spy = new SpyUnit(vader, hoth);
    spy.progressRevolution();
    vader.addSpy(spy);
    Order o = new RevolutionOrder(vader, hoth, 10);
    OrderRuleChecker ruleChecker = new RevolutionOrderRuleChecker();
    o.orderAction(ruleChecker);
    String expected = "Rebellion Failed: a rebellion is already in progress in Hoth";
    assertEquals(expected, o.orderMessage());
  }

  // Tests a RevolutionOrder with a sender who does not have a spy that can
  // preform the order
  @Test
  public void test_no_spy() {
    Player vader = new BasicPlayer("Vader", 100);
    Territory hoth = new BasicTerritory("Hoth", "Luke");
    Territory tatooine = new BasicTerritory("Tatooine", "Luke");
    SpyUnit spy = new SpyUnit(vader, hoth);
    vader.addSpy(spy);
    Order o = new RevolutionOrder(vader, tatooine, 10);
    OrderRuleChecker ruleChecker = new RevolutionOrderRuleChecker();
    o.orderAction(ruleChecker);
    String expected = "Rebellion Failed: sender does not own a spy in Tatooine";
    assertEquals(expected, o.orderMessage());
  }

  // Tests a RevolutionOrder with not enough resources to fund
  @Test
  public void test_not_enough_funding() {
    Player vader = new BasicPlayer("Vader", 100);
    Territory hoth = new BasicTerritory("Hoth", "Luke");
    SpyUnit spy = new SpyUnit(vader, hoth);
    vader.addSpy(spy);
    Order o = new RevolutionOrder(vader, hoth, 101);
    OrderRuleChecker ruleChecker = new RevolutionOrderRuleChecker();
    o.orderAction(ruleChecker);
    String expected = "Rebellion Failed: not enough credits for funding";
    assertEquals(expected, o.orderMessage());
  }

  // Tests a valid RevolutionOrder
  @Test
  public void test_vaild_revolution_order() {
    Player vader = new BasicPlayer("Vader", 100);
    Territory hoth = new BasicTerritory("Hoth", "Luke");
    SpyUnit spy = new SpyUnit(vader, hoth);
    vader.addSpy(spy);
    Order o = new RevolutionOrder(vader, hoth, 10);
    OrderRuleChecker ruleChecker = new RevolutionOrderRuleChecker();
    o.orderAction(ruleChecker);
    String expected = "Vader planted the seeds of a rebellion in Hoth";
    assertEquals(expected, o.orderMessage());
  }

  // Tests RevoltionOrder specific fucntion getSpy()
  @Test
  public void test_getSpy() {
    Player vader = new BasicPlayer("Vader", 100);
    Territory hoth = new BasicTerritory("Hoth", "Luke");
    Territory tatooine = new BasicTerritory("Tatooine", "Luke");
    SpyUnit spy = new SpyUnit(vader, hoth);
    SpyUnit spy2 = new SpyUnit(vader, tatooine);
    vader.addSpy(spy2);
    vader.addSpy(spy);
    RevolutionOrder o = new RevolutionOrder(vader, hoth, 10);
    OrderRuleChecker ruleChecker = new RevolutionOrderRuleChecker();
    o.orderAction(ruleChecker);
    assertEquals(spy, o.getSpy());
  }

}
