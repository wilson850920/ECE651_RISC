package edu.duke.ece651.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class AttackOrderTest {

  // Test rollDice() for vaild output
  @Test
  public void test_rollDice() {
    AttackOrder order = new AttackOrder(null, null, null, null);
    int num = order.rollDice();
    assertTrue(num <= 20 && num >= 1);
  }

  // Tests orderAction() for the vailid exectution of an AttackOrder
  @Test
  public void test_orderAction() {
    AttackOrderRuleChecker attackRuleChecker = new AttackOrderRuleChecker();
    Player vader = new BasicPlayer("Vader", 1000);
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    Territory tatooine = new BasicTerritory("Tatooine", "Luke");
    vader.addTerritory(hoth);
    hoth.addNeighbor(tatooine);
    tatooine.addNeighbor(hoth);
    for (int i = 0; i < 10; i++) {
      hoth.addUnit(0);
      tatooine.addUnit(0);
    }
    String error = "but failed:";
    // Invalid order
    HashMap<Integer, Integer> order1Units = new HashMap<Integer, Integer>();
    order1Units.put(0, 11);
    Order order1 = new AttackOrder(vader, hoth, tatooine, order1Units);
    String expected = "Vader tried to attack from Hoth into Tatooine but failed: Invalid order. Source doesn't have sufficient units to attack";
    order1.orderAction(attackRuleChecker);
    assertEquals(expected, order1.orderMessage());

    // Defend/Attack success
    HashMap<Integer, Integer> order2Units = new HashMap<Integer, Integer>();
    order2Units.put(0, 5);
    Order order2 = new AttackOrder(vader, hoth, tatooine, order2Units);
    order2.orderAction(attackRuleChecker);
    assertTrue(!(order2.orderMessage()).contains(error));
    System.out.println(order2.orderMessage());
    HashMap<Integer, Integer> order3Units = new HashMap<Integer, Integer>();
    order3Units.put(0, 99);

    // Attack/Defend success
    for (int i = 0; i < 100; i++) {
      hoth.addUnit(0);
    }
    Order order3 = new AttackOrder(vader, hoth, tatooine, order3Units);
    order3.orderAction(attackRuleChecker);
    assertTrue(!(order3.orderMessage()).contains(error));
  }

  // Test toString() for correct output
  @Test
  public void test_toString() {
    Player vader = new BasicPlayer("Vader", 1000);
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    Territory tatooine = new BasicTerritory("Tatooine", "Vader");
    HashMap<Integer, Integer> order1Units = new HashMap<Integer, Integer>();
    order1Units.put(0, 5);
    Order order1 = new AttackOrder(vader, hoth, tatooine, order1Units);
    String expected = "Type: Attack, Source: Hoth, Target: Tatooine";
    assertEquals(expected, order1.toString());
  }
}
