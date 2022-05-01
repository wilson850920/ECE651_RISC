package edu.duke.ece651.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SpyUnitTest {
  
  /** 
   * Tests creation of a SpyUnit
   * Tests getOwner()
   * Tests getLocation()
   * Tests setLocation()
   */
  @Test
  public void test_SpyUnit() {
    Player vader = new BasicPlayer("Vader", 100);
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    Territory tatooine = new BasicTerritory("Tatooine", "Vader");
    SpyUnit spy = new SpyUnit(vader, hoth);
    assertEquals(vader, spy.getOwner());
    assertEquals(hoth, spy.getLocation());
    spy.setLocation(tatooine);
    assertEquals(tatooine, spy.getLocation());
  }

  /** 
   * Tests revolution functions of a SpyUnit
   * Tests progressRevolution()
   * Tests resetRevolution()
   * Tests revolutionStage()
   */
  @Test
  public void test_revolution() {
    Player vader = new BasicPlayer("Vader", 100);
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    SpyUnit spy = new SpyUnit(vader, hoth);
    assertEquals(0, spy.revolutionStage());
    spy.progressRevolution();
    assertEquals(1, spy.revolutionStage());
    spy.resetRevolution();
    assertEquals(0, spy.revolutionStage());
  }

  /** 
   * Test the funding tracking functionality of a spy
   */
  @Test
  public void test_funding() {
    Player vader = new BasicPlayer("Vader", 100);
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    SpyUnit spy = new SpyUnit(vader, hoth);
    assertEquals(0, spy.getFunding());
    spy.setFunding(100);
    assertEquals(100, spy.getFunding());
  }
  
}
