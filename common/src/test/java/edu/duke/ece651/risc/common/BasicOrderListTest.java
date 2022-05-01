//package edu.duke.ece651.risc.common;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.ArrayList;
//
//import org.junit.jupiter.api.Test;
//
//public class BasicOrderListTest {
//
//  // Used for BasicOrderTest helper functions
//  private BasicOrderTest bot = new BasicOrderTest();
//
//  @Test
//  public void test_addOrder() {
//    Player p = new BasicPlayer("wilson");
//    Territory t1 = new BasicTerritory("duke", "wilson");
//    Territory t2 = new BasicTerritory("chapel", "wilson");
//    ArrayList<Unit> t1Units = bot.makeBasicUnits(8);
//    ArrayList<Unit> t2Units = bot.makeBasicUnits(8);
//    for (int i = 0; i < 8; i++) {
//      t1.addUnit(t1Units.get(i));
//      t2.addUnit(t2Units.get(i));
//    }
//    BasicOrderList bol = new BasicOrderList();
//    ArrayList<Unit> order1Units = bot.makeBasicUnits(3);
//    MoveOrder order1 = new MoveOrder(p, t1, t2, order1Units);
//    ArrayList<Unit> order2Units = bot.makeBasicUnits(3);
//    MoveOrder order2 = new MoveOrder(p, t1, t2, order2Units);
//    bol.addOrder(order1);
//    assertTrue(bol.addOrder(order2));
//    assertNotNull(bol.getOrders());
//  }
//
//
//}
