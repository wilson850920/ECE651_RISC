package edu.duke.ece651.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class BasicUnitTest {

  // Tests createUpgradeMap() for the creation of the correct mappings
  @Test
  public void test_createUpgradeMap() {
    BasicUnit bu = new BasicUnit();
    ArrayList<Pair<Integer>> map = bu.createUpgradeMap();
    assertEquals(3, map.get(1).getSecond());
    assertEquals(8, map.get(4).getFirst());
    assertEquals(15, map.get(6).getFirst());
    assertEquals(50, map.get(6).getSecond());
  }

  // Tests getBonus() for valid behavior
  @Test
  public void test_getBonus() {
    Unit u = new BasicUnit();
    assertEquals(0, u.getBonus());
    Unit u2 = new BasicUnit(1, 2, 3);
    assertEquals(1, u2.getBonus());
  }

  // Tests getTechLevel() for valid behavior
  @Test
  public void test_getTechLevel() {
    Unit u = new BasicUnit();
    assertEquals(0, u.getTechLevel());
    Unit u2 = new BasicUnit(1, 2, 3);
    assertEquals(2, u2.getTechLevel());
  }

  // Tests getCost() for valid behavior
  @Test
  public void test_getCost() {
    Unit u = new BasicUnit();
    assertEquals(0, u.getCost());
    Unit u2 = new BasicUnit(1, 2, 3);
    assertEquals(3, u2.getCost());
  }

  // Tests upgradeCost() for valid behavior
  @Test
  public void test_upgradeCost() {
    Unit u = new BasicUnit();
    assertEquals(30, u.upgradeCost(3));
    assertEquals(3, u.upgradeCost(1));
    assertEquals(0, u.upgradeCost(0));
    assertEquals(140, u.upgradeCost(6));
    u.upgradeUnit(6);
    assertEquals(0, u.upgradeCost(5));
    assertEquals(0, u.upgradeCost(6));
    assertEquals(0, u.upgradeCost(7));
  }

  // Helper for testing unit attributes
  private void assertUnitHelper(Unit u, int bonus, int techLevel, int cost) {
    assertEquals(bonus, u.getBonus());
    assertEquals(techLevel, u.getTechLevel());
    assertEquals(cost, u.getCost());
  }

  // Tests upgradeUnit() for valid behavior
  @Test
  public void test_upgradeUnit() {
    Unit u = new BasicUnit();
    u.upgradeUnit(1);
    assertUnitHelper(u, 1, 1, 3);
    u.upgradeUnit(1);
    assertUnitHelper(u, 1, 1, 3);
    u.upgradeUnit(5);
    assertUnitHelper(u, 11, 5, 35);
    u.upgradeUnit(4);
    assertUnitHelper(u, 11, 5, 35);
    u.upgradeUnit(6);
    assertUnitHelper(u, 15, 6, 50);
  }

  // Tests getDisplayInfo() for valid behavior
  @Test
  public void test_getDisplayInfo() throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Unit u = new BasicUnit();
    u.getDisplayInfo().displayUnit(out);
    assertEquals("A", out.toString());
  }

}
