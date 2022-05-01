package edu.duke.ece651.risc.server;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.jupiter.api.Disabled;
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
import edu.duke.ece651.risc.common.SpyUnit;
import edu.duke.ece651.risc.common.TechnologyUpgradeOrder;
import edu.duke.ece651.risc.common.Territory;
import edu.duke.ece651.risc.common.UnitUpgradeOrder;

public class BasicMapTest {

  /**
   * Helper for making a BasicMap for testing
   *
   * Three players, each with one territory. Each territory is neighbored by the
   * other two. 1 unit in each.
   */
  @Test
  public void test_BasicMap() {
    Player vader = new BasicPlayer("Vader");
    Player luke = new BasicPlayer("Luke");
    Player leia = new BasicPlayer("Leia");
    Territory mustafar = new BasicTerritory("Mustafar", "Vader");
    Territory tatooine = new BasicTerritory("Tatooine", "Luke");
    Territory alderaan = new BasicTerritory("Alderaan", "Leia");
    mustafar.setNeighbors(alderaan, tatooine);
    tatooine.setNeighbors(alderaan, mustafar);
    alderaan.setNeighbors(mustafar, tatooine);
    vader.addTerritory(mustafar);
    luke.addTerritory(tatooine);
    leia.addTerritory(alderaan);
    ArrayList<Player> playerList = new ArrayList<Player>();
    playerList.add(vader);
    playerList.add(luke);
    playerList.add(leia);
    BasicMap thisMap = new BasicMap(playerList);
    // TODO - ???
  }

  // Test addPlayer() for valid and invalid input
  @Test
  public void test_iterable_constructor() {
    Player player1 = new BasicPlayer("Player1");
    Player player2 = new BasicPlayer("Player2");
    Player player3 = new BasicPlayer("Player3");
    HashSet<Player> players = new HashSet<Player>();
    players.add(player1);
    players.add(player2);
    players.add(player3);
    Territory mustafar = new BasicTerritory("Mustafar", "Vader");
    Territory tatooine = new BasicTerritory("Tatooine", "Luke");
    Territory alderaan = new BasicTerritory("Alderaan", "Leia");
    mustafar.setNeighbors(alderaan, tatooine);
    tatooine.setNeighbors(alderaan, mustafar);
    alderaan.setNeighbors(mustafar, tatooine);
    Map m = new BasicMap(players);
    player1.addTerritory(mustafar);
    player2.addTerritory(tatooine);
    player3.addTerritory(alderaan);
    assertTrue(m.hasPlayer(player1));
    assertTrue(m.hasPlayer(player2));
    assertTrue(m.hasPlayer(player3));
  }

  @Test
  void test_returnMapDisplay() throws IOException {
    Player player1 = new BasicPlayer("Player1");
    Map m = new BasicMap(player1);
    m.getDisplayInfo();
  }

  // Test addPlayer() for valid and invalid input
  @Test
  public void test_addPlayer() {
    Player player1 = new BasicPlayer("Player1");
    Player player2 = new BasicPlayer("Player2");
    Player player3 = new BasicPlayer("Player3");
    Map m = new BasicMap(player1, player2);
    assertThrows(IllegalArgumentException.class, () -> m.addPlayer(player1));
    assertDoesNotThrow(() -> m.addPlayer(player3));
    Map empty = new BasicMap();
    assertDoesNotThrow(() -> empty.addPlayer(player3));
  }

  // Test removePlayer() for valid and invalid input
  @Test
  public void test_removePlayer() {
    Player player1 = new BasicPlayer("Player1");
    Player player2 = new BasicPlayer("Player2");
    Player player3 = new BasicPlayer("Player3");
    Map m = new BasicMap(player1, player2);
    assertThrows(IllegalArgumentException.class, () -> m.removePlayer(player3));
    assertDoesNotThrow(() -> m.removePlayer(player2));
    Map empty = new BasicMap();
    assertThrows(IllegalArgumentException.class, () -> empty.removePlayer(player3));
  }

  // Test hasPlayer() for valid and invalid input
  @Test
  public void test_hasPlayer() {
    Player player1 = new BasicPlayer("Player1");
    Player player2 = new BasicPlayer("Player2");
    Map m = new BasicMap(player1);
    assertEquals(true, m.hasPlayer(player1));
    assertEquals(false, m.hasPlayer(player2));
    Map empty = new BasicMap();
    assertEquals(false, empty.hasPlayer(player1));
  }

  // Test combineAttacks() for vaild sorting of orders
  @Test
  public void test_combineAttacks() {
    Player vader = new BasicPlayer("Vader");
    Player luke = new BasicPlayer("Luke");
    Territory mustafar = new BasicTerritory("Mustafar", "Vader");
    Territory tatooine = new BasicTerritory("Tatooine", "Luke");
    Territory alderaan = new BasicTerritory("Alderaan", "Vader");
    mustafar.addNeighbor(alderaan);
    mustafar.addNeighbor(tatooine);
    tatooine.addNeighbor(alderaan);
    tatooine.addNeighbor(mustafar);
    alderaan.addNeighbor(mustafar);
    alderaan.addNeighbor(tatooine);
    vader.addTerritory(mustafar);
    vader.addTerritory(alderaan);
    luke.addTerritory(tatooine);
    for (int i = 0; i < 10; i++) {
      mustafar.addUnit(0);
      tatooine.addUnit(0);
      alderaan.addUnit(0);
    }
    BasicMap m = new BasicMap(vader, luke);

    ArrayList<Order> orders = new ArrayList<Order>();
    HashMap<Integer, Integer> order1Units = new HashMap<Integer, Integer>();
    order1Units.put(0, 5);
    HashMap<Integer, Integer> order2Units = new HashMap<Integer, Integer>();
    order2Units.put(0, 6);
    HashMap<Integer, Integer> order3Units = new HashMap<Integer, Integer>();
    order3Units.put(0, 3);

    Order order1 = new AttackOrder(vader, mustafar, tatooine, order1Units);
    Order order2 = new AttackOrder(luke, tatooine, alderaan, order2Units);
    Order order3 = new AttackOrder(vader, alderaan, tatooine, order3Units);

    orders.add(order1);
    orders.add(order2);
    orders.add(order3);

    ArrayList<Order> sortedOrders = m.combineAttacks(orders);
    assertEquals(0, sortedOrders.indexOf(order1));
    assertEquals(2, sortedOrders.indexOf(order2));
    assertEquals(1, sortedOrders.indexOf(order3));
  }

  // Test combineAttacks() for vaild sorting of orders
  @Test
  public void test_concurrentAttacks() {
    Player vader = new BasicPlayer("Vader");
    Player luke = new BasicPlayer("Luke");
    Territory mustafar = new BasicTerritory("Mustafar", "Vader");
    Territory tatooine = new BasicTerritory("Tatooine", "Luke");
    Territory alderaan = new BasicTerritory("Alderaan", "Vader");
    mustafar.addNeighbor(alderaan);
    mustafar.addNeighbor(tatooine);
    tatooine.addNeighbor(alderaan);
    tatooine.addNeighbor(mustafar);
    alderaan.addNeighbor(mustafar);
    alderaan.addNeighbor(tatooine);
    vader.addTerritory(mustafar);
    vader.addTerritory(alderaan);
    luke.addTerritory(tatooine);
    for (int i = 0; i < 10; i++) {
      mustafar.addUnit(0);
      tatooine.addUnit(0);
      alderaan.addUnit(0);
    }
    BasicMap m = new BasicMap(vader, luke);

    ArrayList<Order> orders = new ArrayList<Order>();
    HashMap<Integer, Integer> order1Units = new HashMap<Integer, Integer>();
    order1Units.put(0, 5);
    HashMap<Integer, Integer> order2Units = new HashMap<Integer, Integer>();
    order2Units.put(0, 6);
    HashMap<Integer, Integer> order3Units = new HashMap<Integer, Integer>();
    order3Units.put(0, 3);

    Order order1 = new AttackOrder(vader, mustafar, tatooine, order1Units);
    Order order2 = new AttackOrder(vader, mustafar, alderaan, order2Units);
    Order order3 = new AttackOrder(luke, tatooine, mustafar, order3Units);

    orders.add(order1);
    orders.add(order2);
    orders.add(order3);

    ArrayList<Order> sortedOrders = m.concurrentAttacks(orders);
    assertEquals(0, sortedOrders.indexOf(order1));
    assertEquals(2, sortedOrders.indexOf(order2));
    assertEquals(1, sortedOrders.indexOf(order3));
  }

  // Test executeMoveOrders()
  @Test
  public void test_executeMoveOrders() {
    Player vader = new BasicPlayer("Vader", 1000);
    Territory mustafar = new BasicTerritory("Mustafar", "Vader");
    Territory tatooine = new BasicTerritory("Tatooine", "Vader");
    Territory alderaan = new BasicTerritory("Alderaan", "Vader");
    mustafar.addNeighbor(alderaan);
    mustafar.addNeighbor(tatooine);
    tatooine.addNeighbor(alderaan);
    tatooine.addNeighbor(mustafar);
    alderaan.addNeighbor(mustafar);
    alderaan.addNeighbor(tatooine);
    vader.addTerritory(mustafar);
    vader.addTerritory(alderaan);
    vader.addTerritory(tatooine);
    for (int i = 0; i < 10; i++) {
      mustafar.addUnit(0);
      tatooine.addUnit(0);
      alderaan.addUnit(0);
    }
    BasicMap m = new BasicMap(vader);

    ArrayList<Order> orders = new ArrayList<Order>();
    HashMap<Integer, Integer> order1Units = new HashMap<Integer, Integer>();
    order1Units.put(0, 2);
    HashMap<Integer, Integer> order2Units = new HashMap<Integer, Integer>();
    order2Units.put(0, 9);
    Order order1 = new MoveOrder(vader, mustafar, tatooine, order1Units);
    Order order2 = new MoveOrder(vader, mustafar, alderaan, order2Units);
    orders.add(order1);
    orders.add(order2);
    m.executeMoveOrders(orders);

    // Valid move
    String expected = "Vader moved 2 units from Mustafar into Tatooine, costing them 4 credits";
    assertEquals(expected, order1.orderMessage());
    // Invalid move
    expected = "Vader tried to move 9 units from Mustafar into Alderaan but failed: Invalid order. Source doesn't have sufficient units to move";
    assertEquals(expected, order2.orderMessage());
  }

  // Tests minPathCost() for invalid path detection on 2 player graph
  @Disabled // NOT USED IN BASIC MAP ANYMORE (in move rule checker)
  @Test
  public void test_invalid_minPathCost() {
    Player luke = new BasicPlayer("Luke", 1000);
    Player vader = new BasicPlayer("Vader", 1000000);
    Territory hoth = new BasicTerritory("Hoth", "Luke", 3);
    Territory mustafar = new BasicTerritory("Mustafar", "Luke", 10);
    Territory tatooine = new BasicTerritory("Tatooine", "Vader", 2);
    Territory dagobah = new BasicTerritory("Dagobah", "Luke", 3);
    // Set up graph
    hoth.addNeighbor(mustafar);
    hoth.addNeighbor(tatooine);
    tatooine.addNeighbor(hoth);
    tatooine.addNeighbor(dagobah);
    mustafar.addNeighbor(hoth);
    mustafar.addNeighbor(dagobah);
    luke.addTerritory(hoth);
    luke.addTerritory(dagobah);
    luke.addTerritory(mustafar);
    vader.addTerritory(tatooine);
    for (int i = 0; i < 2; i++) {
      hoth.addUnit(0);
      hoth.addUnit(0);
    }
    BasicMap m = new BasicMap(vader, luke);
    HashMap<Integer, Integer> units = new HashMap<Integer, Integer>();
    units.put(0, 1);
    MoveOrder o = new MoveOrder(luke, hoth, dagobah, units);
    ArrayList<Order> orders = new ArrayList<Order>();
    orders.add(o);
    m.executeMoveOrders(orders);
    String expected = "Luke moved 1 units from Hoth into Dagobah, costing them 16 credits";
    assertEquals(expected, o.orderMessage());

    // Test no valid path
    luke.removeTerritory(mustafar);
    vader.addTerritory(mustafar);
    mustafar.setOwner("Vader");
    m.executeMoveOrders(orders);
    String invalid = "Luke tried to move 1 units from Hoth into Dagobah but failed: Invalid order. Cannot move units from source to target as no path owned by player exists.";
    assertEquals(invalid, o.orderMessage());
  }

  // Test executeAttackOrders()
  @Test
  public void test_executeAttackOrders() {
    Player vader = new BasicPlayer("Vader");
    Player luke = new BasicPlayer("Luke");
    Territory mustafar = new BasicTerritory("Mustafar", "Vader");
    Territory tatooine = new BasicTerritory("Tatooine", "Luke");
    Territory alderaan = new BasicTerritory("Alderaan", "Vader");
    mustafar.addNeighbor(alderaan);
    mustafar.addNeighbor(tatooine);
    tatooine.addNeighbor(alderaan);
    tatooine.addNeighbor(mustafar);
    alderaan.addNeighbor(mustafar);
    alderaan.addNeighbor(tatooine);
    vader.addTerritory(mustafar);
    vader.addTerritory(alderaan);
    luke.addTerritory(tatooine);
    for (int i = 0; i < 10; i++) {
      mustafar.addUnit(0);
      tatooine.addUnit(0);
      alderaan.addUnit(0);
    }
    BasicMap m = new BasicMap(vader, luke);

    ArrayList<Order> orders = new ArrayList<Order>();
    HashMap<Integer, Integer> order1Units = new HashMap<Integer, Integer>();
    order1Units.put(0, 2);
    HashMap<Integer, Integer> order2Units = new HashMap<Integer, Integer>();
    order2Units.put(0, 3);
    Order order1 = new AttackOrder(vader, mustafar, tatooine, order1Units);
    Order order2 = new AttackOrder(luke, tatooine, mustafar, order2Units);
    orders.add(order1);
    orders.add(order2);
    m.executeAttackOrders(orders);

    // Swap attack vader
    String expected = "Tatooine was captured by Vader";
    assertEquals(expected, order1.orderMessage());
    // Swap attack luke
    expected = "Mustafar was captured by Luke";
    assertEquals(expected, order2.orderMessage());
  }

  // Test executeMoveOrders()
  @Test
  public void test_executeUnitUpgradeOrders() {
    Player vader = new BasicPlayer("Vader", 10000);
    Territory mustafar = new BasicTerritory("Mustafar", "Vader");
    vader.addTerritory(mustafar);
    for (int i = 0; i < 10; i++) {
      mustafar.addUnit(0);
    }
    BasicMap m = new BasicMap(vader);
    vader.upgradeMaxTechLevel();
    vader.upgradeMaxTechLevel();
    vader.upgradeMaxTechLevel();

    ArrayList<Order> orders = new ArrayList<Order>();
    Order order1 = new UnitUpgradeOrder(vader, mustafar, 0, 3);
    Order order2 = new UnitUpgradeOrder(vader, mustafar, 0, 5);
    orders.add(order1);
    orders.add(order2);
    m.executeUnitUpgradeOrders(orders);

    // Valid upgrade
    String expected = "Unit in Mustafar upgraded from level 0 to 2 successfully, costing Vader 11 credits";
    assertEquals(expected, order1.orderMessage());
    // Invalid upgrade
    expected = "Unit in Mustafar upgraded from level 0 to 3 successfully, costing Vader 30 credits";
    assertEquals(expected, order2.orderMessage());
  }

  // Test executeTechUpgradeOrders()
  @Test
  public void test_executeTechUpgradeOrders() {
    Player vader = new BasicPlayer("Vader", 10000);
    Territory mustafar = new BasicTerritory("Mustafar", "Vader");
    vader.addTerritory(mustafar);
    for (int i = 0; i < 10; i++) {
      mustafar.addUnit(0);
    }
    BasicMap m = new BasicMap(vader);

    ArrayList<Order> orders = new ArrayList<Order>();
    Order order1 = new TechnologyUpgradeOrder(vader);
    Order order2 = new TechnologyUpgradeOrder(vader);
    orders.add(order1);
    orders.add(order2);
    m.executeTechUpgradeOrders(orders);

    // Valid upgrade
    String expected = "Vader upgraded to technology level: 2";
    assertEquals(expected, order1.orderMessage());
    expected = "Vader upgraded to technology level: 3";
    assertEquals(expected, order2.orderMessage());
  }

  // Test postTurnAddUnit() for correct behavior
  @Test
  public void test_postTurnAddUnit() {
    Player vader = new BasicPlayer("Vader");
    Player luke = new BasicPlayer("Luke");
    Player leia = new BasicPlayer("Leia");
    Territory mustafar = new BasicTerritory("Mustafar", "Vader");
    Territory tatooine = new BasicTerritory("Tatooine", "Luke");
    Territory alderaan = new BasicTerritory("Alderaan", "Leia");
    mustafar.addNeighbor(alderaan);
    mustafar.addNeighbor(tatooine);
    tatooine.addNeighbor(alderaan);
    tatooine.addNeighbor(mustafar);
    alderaan.addNeighbor(mustafar);
    alderaan.addNeighbor(tatooine);
    vader.addTerritory(mustafar);
    luke.addTerritory(tatooine);
    leia.addTerritory(alderaan);
    mustafar.addUnit(0);
    tatooine.addUnit(0);
    alderaan.addUnit(0);
    BasicMap m = new BasicMap(vader, luke, leia);

    m.postTurnAddUnit();
    assertEquals(2, alderaan.getNumberOfUnits());
    assertEquals(2, tatooine.getNumberOfUnits());
    assertEquals(2, mustafar.getNumberOfUnits());
  }

  // Test executeTurn() on BasicMap
  @Test
  public void test_executeTurn() {
    Player vader = new BasicPlayer("Vader");
    Player luke = new BasicPlayer("Luke");
    Territory mustafar = new BasicTerritory("Mustafar", "Vader");
    Territory tatooine = new BasicTerritory("Tatooine", "Vader");
    Territory alderaan = new BasicTerritory("Alderaan", "Luke");
    mustafar.addNeighbor(alderaan);
    mustafar.addNeighbor(tatooine);
    tatooine.addNeighbor(alderaan);
    tatooine.addNeighbor(mustafar);
    alderaan.addNeighbor(mustafar);
    alderaan.addNeighbor(tatooine);
    vader.addTerritory(mustafar);
    vader.addTerritory(tatooine);
    luke.addTerritory(alderaan);
    for (int i = 0; i < 10; i++) {
      mustafar.addUnit(0);
      tatooine.addUnit(0);
      alderaan.addUnit(0);
    }
    BasicMap m = new BasicMap(vader, luke);

    OrderList orders = new BasicOrderList();
    HashMap<Integer, Integer> order1Units = new HashMap<Integer, Integer>();
    order1Units.put(0, 2);
    HashMap<Integer, Integer> order2Units = new HashMap<Integer, Integer>();
    order2Units.put(0, 2);
    HashMap<Integer, Integer> order3Units = new HashMap<Integer, Integer>();
    order3Units.put(0, 3);

    Order order1 = new AttackOrder(vader, tatooine, alderaan, order1Units);
    Order order2 = new MoveOrder(vader, mustafar, tatooine, order2Units);
    Order order3 = new AttackOrder(luke, alderaan, mustafar, order3Units);
    orders.addOrder(order1);
    orders.addOrder(order2);
    orders.addOrder(order3);

    Turn t = new Turn();
    t.addOrders(orders);
    m.executeTurn(t);
    assertThrows(IllegalArgumentException.class, () -> m.executeTurn(null));
    assertEquals(3, t.getOrderMessages().size());
  }

  // Tests checkRevolution function for correct behavior 
  @Test
  public void test_checkRevolution() {
    Player luke = new BasicPlayer("Luke", 100);
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    SpyUnit spy = new SpyUnit(luke, hoth);
    BasicMap map = new BasicMap(luke);
    assertFalse(map.checkRevolution(spy));
    spy.progressRevolution();
    assertFalse(map.checkRevolution(spy));
    // TODO - check for true? how with random?
  }

  // Tests stopRevolution function for correct behavior
  @Test
  public void test_stopRevolution() {
    Player luke = new BasicPlayer("Luke", 100);
    Player vader = new BasicPlayer("Vader", 1000);
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    SpyUnit spy = new SpyUnit(luke, hoth);
    luke.addSpy(spy);
    vader.addTerritory(hoth);
    BasicMap map = new BasicMap(luke, vader);
    Turn currentTurn = new Turn();
    map.stopRevolution(spy, currentTurn);
    Iterator<SpyUnit> it = luke.spyIterator();
    assertFalse(it.hasNext());
    String expected = "A spy from Luke was caught trying to start a rebellion in Hoth and has been executed for treason";
    assertTrue(currentTurn.getOrderMessages().contains(expected));
  }

  // Tests executeRevolution function for correct behavior
  @Test
  public void test_executeRevolution() {
    Player luke = new BasicPlayer("Luke", 100);
    Player vader = new BasicPlayer("Vader", 1000);
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    SpyUnit spy = new SpyUnit(luke, hoth);
    spy.setFunding(10);
    spy.progressRevolution();
    luke.addSpy(spy);
    vader.addTerritory(hoth);
    BasicMap map = new BasicMap(luke, vader);
    Turn currentTurn = new Turn();
    map.executeRevolution(spy, currentTurn);
    Iterator<SpyUnit> it = luke.spyIterator();
    assertEquals(spy, it.next());
    assertEquals(0, spy.revolutionStage());
    assertEquals(0, spy.getFunding());
    assertEquals(hoth, spy.getLocation());
    assertEquals("Luke", hoth.getOwner());
    assertTrue(luke.ownsTerritory(hoth));
    assertFalse(vader.ownsTerritory(hoth));
    String expected = "A rebellion has crippled Hoth and Vader has lost control of the planet! Luke has mysteriously taken control of Hoth with minimal resistance...";
    assertTrue(currentTurn.getOrderMessages().contains(expected));
  }

  // Tests resolveRevolutions function for correct behavior
  @Test
  public void test_resolveRevolutions() throws IOException {
    Player luke = new BasicPlayer("Luke", 100);
    Player vader = new BasicPlayer("Vader", 1000);
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    Territory tatooine = new BasicTerritory("Hoth", "Luke");
    SpyUnit spy1 = new SpyUnit(luke, hoth);
    SpyUnit spy2 = new SpyUnit(vader, tatooine);
    luke.addSpy(spy1);
    vader.addSpy(spy2);
    spy1.progressRevolution();
    spy1.progressRevolution();
    vader.addTerritory(hoth);
    BasicMap map = new BasicMap(luke, vader);
    Order o1 = new RevolutionOrder(luke, hoth, 20);
    Order o2 = new RevolutionOrder(vader, tatooine, 20);
    Turn currentTurn = new Turn();
    OrderList orders = new BasicOrderList();
    orders.addOrder(o1);
    orders.addOrder(o2);
    currentTurn.addOrders(orders);
    map.resolveRevolutions(currentTurn);
    map.getDisplayInfo().displayMap(System.out);
  }
  
}
