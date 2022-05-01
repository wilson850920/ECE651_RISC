//package edu.duke.ece651.risc.common;
//
//import java.util.HashSet;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//public class OrderRuleCheckersTest {
//
//  // Used for BasicOrderTest helper functions
//  private BasicOrderTest bot = new BasicOrderTest();
//
//  @Disabled // TODO - implement order rulecheckers with new Units
//  @Test
//  public void test_checkers() {
//
//    HashSet<Territory> territories = new HashSet<Territory>();
//    Player p1 = new BasicPlayer("clientPlayer");
//    Player p2 = new BasicPlayer("p2");
//    // Player p3 = new BasicPlayer("p3");
//    Territory t1 = new BasicTerritory("t1", "p1");
//    Territory t2 = new BasicTerritory("t2", "p2");
//    Territory t3 = new BasicTerritory("t3", "p2");
//    Territory t4 = new BasicTerritory("t4", "p1");
//    Territory t5 = new BasicTerritory("t5", "p1");// error territory for testing not owning by player3
//    //t1.addUnits(1);
//    //t1.addUnits(1);
//    //t2.addUnits(1);
//    //t2.addUnits(1);
//    //t3.addUnits(1);
//    //t3.addUnits(1);
//    p1.addTerritory(t1);
//    p1.addTerritory(t4);
//    p1.addTerritory(t5);
//    p2.addTerritory(t2);
//    p2.addTerritory(t3);
//    t1.addNeighbor(t2);
//    t1.addNeighbor(t5);
//    t2.addNeighbor(t1);
//    t2.addNeighbor(t3);
//    t3.addNeighbor(t2);
//    territories.add(t1);
//    territories.add(t2);
//    territories.add(t3);
//    territories.add(t4);
//    territories.add(t5);
//
//    MoveOrderRuleChecker moveChecker = new MoveOrderRuleChecker(territories);
//    AttackOrderRuleChecker attackChecker = new AttackOrderRuleChecker();
//
//    /*
//    assertNotNull(moveChecker.checkRule(new MoveOrder(p1, t1, t2, 1)));
//    assertNotNull(moveChecker.checkRule(new MoveOrder(p1, t1, t5, 4)));
//    assertNotNull(moveChecker.checkRule(new MoveOrder(p1, t3, t1, 1)));
//    assertNotNull(moveChecker.checkRule(new MoveOrder(p1, t1, t4, 1)));
//
//    Order order = new MoveOrder(p1, t1, t5, 1);
//    assertNull(moveChecker.checkRule(order));
//
//    order = new AttackOrder(p1, t1, t2, 1);
//    assertNull(attackChecker.checkRule(order));
//    assertNotNull(attackChecker.checkRule(new AttackOrder(p1, t1, t3, 1)));
//    assertNotNull(attackChecker.checkRule(new AttackOrder(p1, t1, t5, 1)));
//    assertNotNull(attackChecker.checkRule(new AttackOrder(p1, t1, t2, 10)));
//    assertNotNull(attackChecker.checkRule(new AttackOrder(p1, t2, t3, 1)));
//    */
//  }
//}
