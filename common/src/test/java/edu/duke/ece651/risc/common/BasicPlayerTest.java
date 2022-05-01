package edu.duke.ece651.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

class BasicPlayerTest {

  // Helper function for setting up a test player object
  private Player setUp() {
    String name = "Andrew";
    ArrayList<Territory> territories = new ArrayList<Territory>();
    ArrayList<Integer> units = new ArrayList<Integer>();
    units.add(1);
    units.add(2);
    units.add(3);
    ArrayList<Territory> neighbors = new ArrayList<Territory>();
    // neighbors.add("Terrotory2");
    // neighbors.add("Territory3");
    // neighbors.add("Territory4");
    Territory territory1 = new BasicTerritory("territory1", "Andrew");
    Territory territory2 = new BasicTerritory("territory2", "Andrew");

    Player p = new BasicPlayer(name);
    p.addTerritory(territory1);
    p.addTerritory(territory2);
    p.removeTerritory(territory1);

    return p;
  }

  @Test
  public void test_cloak() {
    BasicPlayer p = new BasicPlayer("testPlayer");
    assertTrue(!p.playerCanCloak());
    p.enableCloakAbility();
    assertTrue(p.playerCanCloak());
    p.disableCloakAbility();
    assertTrue(!p.playerCanCloak());
  }

  // Test getName() for vaild behavior
  @Test
  public void test_getName() {
    Player p = setUp();
    assertEquals("Andrew", p.getName());
    p.setName("Hilton");
    assertEquals("Hilton", p.getName());
    p.setAvailableUnits(1);
    assertEquals(true, p.getIngameOrLoseFlag());
    p.setIngameOrLoseFlag(false);
    assertEquals(false, p.getIngameOrLoseFlag());
    assertEquals(1, p.getAvailableUnits());
  }

  // Test getTerritories() for valid input
  @Test
  void test_getTerritories() throws IOException {
    String name = "testPlayer";
    BasicPlayer p = new BasicPlayer(name);
    Territory territory1 = new BasicTerritory("territory1", name);
    Territory territory2 = new BasicTerritory("territory2", name);
    p.addTerritory(territory1);
    p.addTerritory(territory2);
    Iterator<Territory> it = p.getTerritories();
    assertEquals(it.next(), territory1);
    assertEquals(it.next(), territory2);
    assertNotEquals(3, p.numberOfTerritories());
    assertEquals(2, p.numberOfTerritories());
  }

  // Tests setName() for vaild behavior
  @Test
  public void test_setName() {
    Player p = setUp();
    p.setName("Not Andrew");
    assertEquals("Not Andrew", p.getName());
  }

  // Tests numberOfTerritories() for vaild behavior
  @Test
  public void test_numberOfTerritories() {
    Player p = setUp();
    assertEquals(1, p.numberOfTerritories());
  }

  // Tests ownsTerritory() for vaild behavior
  @Test
  public void test_ownsTerritory() {
    String name = "testPlayer";
    BasicPlayer p = new BasicPlayer(name);
    Territory territory1 = new BasicTerritory("territory1", name);
    Territory territory2 = new BasicTerritory("territory2", name);
    p.addTerritory(territory1);
    assertTrue(p.ownsTerritory(territory1));
    assertFalse(p.ownsTerritory(territory2));
    assertTrue(p.ownsTerritory("territory1"));
    assertFalse(p.ownsTerritory("territory2"));
  }

  // Test fib helper function
  @Test
  public void test_fib() {
    BasicPlayer p = new BasicPlayer("test");
    assertEquals(1, p.fibo(1));
    assertEquals(1, p.fibo(2));
    assertEquals(2, p.fibo(3));
    assertEquals(4, p.fibo(4));
    assertEquals(7, p.fibo(5));
  }

  @Test
  public void test_getResources() {
    Player p = new BasicPlayer("test", 1000);
    assertEquals(1000, p.getResources());
  }

  @Test
  public void test_spendResources() {
    Player p = new BasicPlayer("test", 1000);
    p.spendResources(42);
    assertEquals(958, p.getResources());
  }
  
  @Test
  public void test_getMaxTechLevel() {
    Player p = new BasicPlayer("test");
    assertEquals(1, p.getMaxTechLevel());
  }

  @Test
  public void test_upgradeMaxTechLevel() {
    Player p = new BasicPlayer("test");
    Territory t = new BasicTerritory("testTerr", "test", 100);
    p.addTerritory(t);
    p.updateResources();
    assertTrue(p.upgradeMaxTechLevel());
    assertEquals(2, p.getMaxTechLevel());
    assertTrue(!p.upgradeMaxTechLevel());
    Territory t2 = new BasicTerritory("testTerr2", "test", 100);
    p.addTerritory(t2);
    p.updateResources();
    assertTrue(p.upgradeMaxTechLevel());
    assertEquals(3, p.getMaxTechLevel());
  }


  /**
   * Tests spy list functionality
   * addSpy()
   * removeSpy()
   * spyIterator()                              
   */
  @Test
  public void test_SpyList() {
    Player vader = new BasicPlayer("Vader");
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    SpyUnit viperProbe1 = new SpyUnit(vader, hoth);
    SpyUnit viperProbe2 = new SpyUnit(vader, hoth);
    vader.addSpy(viperProbe1);
    Iterator<SpyUnit> it = vader.spyIterator();
    assertEquals(it.next(), viperProbe1);    
    vader.addSpy(viperProbe2);
    vader.removeSpy(viperProbe1);
    it = vader.spyIterator();
    assertEquals(it.next(), viperProbe2);
  }

}
