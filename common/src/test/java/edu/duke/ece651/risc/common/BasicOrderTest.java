package edu.duke.ece651.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class BasicOrderTest {

  // Tests AttackOrder implementation
  @Test
  public void test_attackOrder() {
    Territory source = new BasicTerritory("Source", "Andrew");
    Territory target = new BasicTerritory("Target", "Hilton");
    Player p = new BasicPlayer("Andrew");
    HashMap<Integer, Integer> orderUnits = new HashMap<Integer, Integer>();
    orderUnits.put(0, 5);
    Order order = new AttackOrder(p, source, target, orderUnits);
    assertEquals(p, order.getSender());
    assertEquals(source, order.getSource());
    assertEquals(target, order.getTarget());
    assertEquals(5, order.getNumUnits());
    String s = "Type: Attack, Source: Source, Target: Target";
    assertEquals(s, order.toString());
  }

  // Tests MoveOrder implementation
  @Test
  public void test_moveOrder() {
    Territory source = new BasicTerritory("Source", "Andrew");
    Territory target = new BasicTerritory("Target", "Hilton");
    Player p = new BasicPlayer("Andrew");
    HashMap<Integer, Integer> orderUnits = new HashMap<Integer, Integer>();
    orderUnits.put(0, 5);
    Order order = new MoveOrder(p, source, target, orderUnits);
    assertEquals(p, order.getSender());
    assertEquals(source, order.getSource());
    assertEquals(target, order.getTarget());
    assertEquals(5, order.getNumUnits());
    String s = "Type: Move, Source: Source, Target: Target";
    assertEquals(s, order.toString());
  }

  // Tests orderMessage() implementation
  @Test
  public void test_orderMessage() {
    Territory source = new BasicTerritory("Source", "Andrew");
    Territory target = new BasicTerritory("Target", "Hilton");
    Player p = new BasicPlayer("Andrew");
    HashMap<Integer, Integer> orderUnits = new HashMap<Integer, Integer>();
    orderUnits.put(0, 5);
    Order order = new MoveOrder(p, source, target, orderUnits);
    assertEquals(null, order.orderMessage());
    order.setMessage("new message");
    assertEquals("new message", order.orderMessage());
  }

  // Tests setSource() and setTarget() implementations
  @Test
  public void test_set_source_target() {
    Territory source = new BasicTerritory("Source", "Andrew");
    Territory target = new BasicTerritory("Target", "Hilton");
    Territory newSource = new BasicTerritory("New Source", "player");
    Territory newTarget = new BasicTerritory("New Target", "player");
    Player p = new BasicPlayer("Andrew");
    HashMap<Integer, Integer> orderUnits = new HashMap<Integer, Integer>();
    orderUnits.put(0, 5);
    Order order = new MoveOrder(p, source, target, orderUnits);
    order.setSource(newSource);
    order.setTarget(newTarget);
    assertTrue(order.getSource().equals(newSource));
    assertTrue(order.getTarget().equals(newTarget));
  }

  // Tests setSender() and getSender() implementations
  @Test
  public void test_set_get_sender() {
    Player vader = new BasicPlayer("Vader");
    Player luke = new BasicPlayer("Luke");
    Order order = new MoveOrder(vader, null, null, null);
    assertEquals(vader, order.getSender());
    order.setSender(luke);
    assertEquals(luke, order.getSender());
  }
  
}
