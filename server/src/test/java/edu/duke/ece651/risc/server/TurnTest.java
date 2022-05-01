package edu.duke.ece651.risc.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.risc.common.AttackOrder;
import edu.duke.ece651.risc.common.BasicOrderList;
import edu.duke.ece651.risc.common.BasicPlayer;
import edu.duke.ece651.risc.common.BasicTerritory;
import edu.duke.ece651.risc.common.MoveOrder;
import edu.duke.ece651.risc.common.Order;
import edu.duke.ece651.risc.common.OrderList;
import edu.duke.ece651.risc.common.Player;
import edu.duke.ece651.risc.common.RevolutionOrder;
import edu.duke.ece651.risc.common.TechnologyUpgradeOrder;
import edu.duke.ece651.risc.common.Territory;
import edu.duke.ece651.risc.common.UnitUpgradeOrder;

public class TurnTest {

  // Helper for creating OrderList
  private OrderList makeOrderList() {
    Player vader = new BasicPlayer("Vader");
    Player luke = new BasicPlayer("Luke");
    Territory mustafar = new BasicTerritory("Mustafar", "Vader");
    Territory tatooine = new BasicTerritory("Tatooine", "Luke");
    Territory alderaan = new BasicTerritory("Alderaan", "Vader");
    OrderList orders = new BasicOrderList();
    HashMap<Integer, Integer> order1Units = new HashMap<Integer, Integer>();
    order1Units.put(0, 2);
    HashMap<Integer, Integer> order2Units = new HashMap<Integer, Integer>();
    order2Units.put(0, 2);
    HashMap<Integer, Integer> order3Units = new HashMap<Integer, Integer>();
    order3Units.put(0, 3);
    HashMap<Integer, Integer> order4Units = new HashMap<Integer, Integer>();
    order4Units.put(0, 3);
    Order order1 = new AttackOrder(vader, mustafar, tatooine, order1Units);
    Order order2 = new MoveOrder(vader, mustafar, alderaan, order2Units);
    Order order3 = new AttackOrder(vader, alderaan, mustafar, order3Units);
    Order order4 = new MoveOrder(luke, alderaan, tatooine, order4Units);
    orders.addOrder(order1);
    orders.addOrder(order2);
    orders.addOrder(order3);
    orders.addOrder(order4);
    return orders;
  }

  /**
   * Tests the correct creation of a turn with a given OrderList
   */
  @Test
  public void test_create_turn() {
    OrderList orders = makeOrderList();
    Turn t = new Turn();
    t.addOrders(orders);
    assertEquals(2, t.getAttackOrders().size());
    assertEquals(2, t.getMoveOrders().size());
    assertEquals("Alderaan", t.getMoveOrders().listIterator(0).next().getTarget().getName());
    assertEquals("Tatooine", t.getAttackOrders().listIterator(0).next().getTarget().getName());
  }

  /**
   * Tests the order message functionality of a Turn
   */
  @Test
  public void test_order_messages() {
    Turn t = new Turn();
    String m1 = "order message 1";
    String m2 = "order message 2";
    t.addOrderMessage(m1);
    assertEquals(true, t.getOrderMessages().contains(m1));
    assertEquals(1, t.getOrderMessages().size());
    t.addOrderMessage(m2);
    assertEquals(true, t.getOrderMessages().contains(m2));
    assertEquals(2, t.getOrderMessages().size());
  }

  /**
   * Tests MoveOrders in Turn
   */
  @Test
  public void test_MoveOrders() {
    Player vader = new BasicPlayer("Vader");
    Territory mustafar = new BasicTerritory("Mustafar", "Vader");
    Territory alderaan = new BasicTerritory("Alderaan", "Vader");
    Order move = new MoveOrder(vader, mustafar, alderaan, null);
    Turn t = new Turn();
    OrderList orders = new BasicOrderList();
    orders.addOrder(move);
    t.addOrders(orders);
    assertTrue(t.getMoveOrders().contains(move));
  }

  /**
   * Tests AttackOrders in Turn
   */
  @Test
  public void test_AttackOrders() {
    Player vader = new BasicPlayer("Vader");
    Territory mustafar = new BasicTerritory("Mustafar", "Vader");
    Territory alderaan = new BasicTerritory("Alderaan", "Vader");
    Order attack = new AttackOrder(vader, mustafar, alderaan, null);
    Turn t = new Turn();
    OrderList orders = new BasicOrderList();
    orders.addOrder(attack);
    t.addOrders(orders);
    assertTrue(t.getAttackOrders().contains(attack));
  }

  /**
   * Tests UnitUpgradeOrders in Turn
   */
  @Test
  public void test_UnitUpgradeOrders() {
    Player vader = new BasicPlayer("Vader");
    Territory mustafar = new BasicTerritory("Mustafar", "Vader");
    Order unit = new UnitUpgradeOrder(vader, mustafar, 0, 0);
    Turn t = new Turn();
    OrderList orders = new BasicOrderList();
    orders.addOrder(unit);
    t.addOrders(orders);
    assertTrue(t.getUnitUpgradeOrders().contains(unit));
  }

  /**
   * Tests TechnologyUpgradeOrders in Turn
   */
  @Test
  public void test_TechnologyUpgradeOrders() {
    Player vader = new BasicPlayer("Vader");
    Order tech = new TechnologyUpgradeOrder(vader);
    Turn t = new Turn();
    OrderList orders = new BasicOrderList();
    orders.addOrder(tech);
    t.addOrders(orders);
    assertTrue(t.getTechUpgradeOrders().contains(tech));
  }

  /**
   * Tests RevolutionOrders in Turn
   */
  @Test
  public void test_RevolutionOrders() {
    Player vader = new BasicPlayer("Vader");
    Territory mustafar = new BasicTerritory("Mustafar", "Vader");
    Order revolution = new RevolutionOrder(vader, mustafar, 0);
    Turn t = new Turn();
    OrderList orders = new BasicOrderList();
    orders.addOrder(revolution);
    t.addOrders(orders);
    assertTrue(t.getRevolutionOrders().contains(revolution));
  }

}
