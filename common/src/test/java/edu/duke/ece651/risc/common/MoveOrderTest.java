package edu.duke.ece651.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class MoveOrderTest {

  // Tests minPathCost() for valid cost calculation on single player graph
  @Test
  public void test_valid_minPathCost() {
    Player luke = new BasicPlayer("Luke", 1000);
    Territory hoth = new BasicTerritory("Hoth", "Luke", 1);
    Territory mustafar = new BasicTerritory("Mustafar", "Luke", 10);
    Territory tatooine = new BasicTerritory("Tatooine", "Luke", 1);
    Territory dagobah = new BasicTerritory("Dagobah", "Luke", 1);
    MoveOrder o = new MoveOrder(null, null, null, null);
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
    luke.addTerritory(tatooine);

    assertEquals(3, o.minPathCost(hoth, dagobah));
  }

  // Tests minPathCost() for invalid path detection on 2 player graph
  @Test
  public void test_invalid_minPathCost() {
    Player luke = new BasicPlayer("Luke", 1000);
    Player vader = new BasicPlayer("Vader", 1000000);
    Territory hoth = new BasicTerritory("Hoth", "Luke", 3);
    Territory mustafar = new BasicTerritory("Mustafar", "Luke", 10);
    Territory tatooine = new BasicTerritory("Tatooine", "Vader", 2);
    Territory dagobah = new BasicTerritory("Dagobah", "Luke", 3);
    MoveOrder o = new MoveOrder(null, null, null, null);
    
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
  
    assertEquals(16, o.minPathCost(hoth, dagobah));

    // Test no valid path
    luke.removeTerritory(mustafar);
    vader.addTerritory(mustafar);
    mustafar.setOwner("Vader");
    assertEquals(-1, o.minPathCost(hoth, dagobah));

    // Test vaild reintroduced (testing for invalid path bug)
    vader.removeTerritory(tatooine);
    luke.addTerritory(tatooine);
    tatooine.setOwner("Luke");
    assertEquals(8, o.minPathCost(hoth, dagobah));
  }

  // Test toString() for correct output
  @Test
  public void test_toString() {
    Player vader = new BasicPlayer("Vader");
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    Territory tatooine = new BasicTerritory("Tatooine", "Vader");
    HashMap<Integer, Integer> order1Units = new HashMap<Integer, Integer>();
    order1Units.put(0, 5);
    Order order1 = new MoveOrder(vader, hoth, tatooine, order1Units);
    String expected = "Type: Move, Source: Hoth, Target: Tatooine";
    assertEquals(expected, order1.toString());
  }

  // Tests moveAction() for the vailid exectution of an MoveOrder
 @Test
 public void test_moveAction() {
   HashSet<Territory> territories = new HashSet<Territory>();
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
   for (int i = 0; i < 10; i++) {
     hoth.addUnit(0);
   }
   territories.add(hoth);
   territories.add(tatooine);
   territories.add(dagobah);
   territories.add(mustafar);
   MoveOrderRuleChecker moveRuleChecker = new MoveOrderRuleChecker(territories);

   // Invalid order
   HashMap<Integer, Integer> order1Units = new HashMap<Integer, Integer>();
   order1Units.put(0, 11);
   Order order1 = new MoveOrder(luke, hoth, mustafar, order1Units);
   String expected = "Luke tried to move 11 units from Hoth into Mustafar but failed: Invalid order. Source doesn't have sufficient units to move";
   order1.orderAction(moveRuleChecker);
   assertEquals(expected, order1.orderMessage());

   // Successful Move
   HashMap<Integer, Integer> order2Units = new HashMap<Integer, Integer>();
   order2Units.put(0, 5);
   Order order2 = new MoveOrder(luke, hoth, dagobah, order2Units);
   expected = "Luke moved 5 units from Hoth into Dagobah, costing them 80 credits";
   order2.orderAction(moveRuleChecker);
   assertEquals(expected, order2.orderMessage());
 }

}
