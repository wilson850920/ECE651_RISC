package edu.duke.ece651.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class BasicTerritoryTest {

  // Tests getName() for correct behavior
  @Test
  public void test_getName() {
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    assertEquals("Hoth", hoth.getName());
  }

  // Tests setOwner() and getOwner() for correct behavior
  @Test
  public void test_set_get_owner() {
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    assertEquals("Vader", hoth.getOwner());
    hoth.setOwner("Han Solo");
    assertEquals("Han Solo", hoth.getOwner());
  }

  // Tests addUnit() for correct behavior
  @Test
  public void test_addUnit() {
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    hoth.addUnit(0);
    hoth.addUnit(8);
    assertEquals(2, hoth.getNumberOfUnits());
  }

  // Tests removeUnit() for correct behavior
  @Test
  public void test_removeUnit() {
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    hoth.addUnit(0);
    hoth.addUnit(8);
    hoth.addUnit(8);
    assertEquals(3, hoth.getNumberOfUnits());
    hoth.removeUnit(0);
    assertEquals(2, hoth.getNumberOfUnits());
    hoth.removeUnit(8);
    assertEquals(1, hoth.getNumberOfUnits());
    hoth.removeUnit(15); // Test unit not in list
    assertEquals(1, hoth.getNumberOfUnits());
    hoth.removeUnit(8);
    assertEquals(0, hoth.getNumberOfUnits());
  }

  // Tests getNumberOfUnits() for correct behavior
  @Test
  public void test_getNumberOfUnits() {
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    hoth.addUnit(0);
    hoth.addUnit(8);
    assertEquals(2, hoth.getNumberOfUnits());
  }

  // Tests getUnitCount() for correct behavior
  @Test
  public void test_getUnitCount() {
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    hoth.addUnit(0);
    hoth.addUnit(0);
    hoth.addUnit(8);
    assertEquals(2, hoth.getUnitCount(0));
    assertEquals(1, hoth.getUnitCount(8));
    assertEquals(0, hoth.getUnitCount(15));
  }

  // Tests setNeighbors() for correct behavior
  @Test
  public void test_setNeighbors() {
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    Territory tatooine = new BasicTerritory("Tatooine", "Luke");
    Territory dagobah = new BasicTerritory("Dagobah", "Yoda");
    hoth.setNeighbors(tatooine, dagobah);
    assertEquals(true, hoth.hasNeighbor(dagobah));
    assertEquals(true, hoth.hasNeighbor(tatooine));
  }

  // Tests addNeighbor() and removeNeighbor() for correct behavior
  @Test
  public void test_add_remove_neighbor() {
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    Territory tatooine = new BasicTerritory("Tatooine", "Luke");
    hoth.addNeighbor(tatooine);
    assertEquals(true, hoth.hasNeighbor(tatooine));
    hoth.removeNeighbor(tatooine);
    assertEquals(false, hoth.hasNeighbor(tatooine));
  }

  // Tests getUnits() for correct behavior
  @Test
  public void test_getUnits() {
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    hoth.addUnit(0);
    hoth.addUnit(8);
    hoth.addUnit(8);
    int count = 0;
    Iterator<Unit> it = hoth.getUnits();
    while (it.hasNext()) {
      it.next();
      count++;
    }
    assertEquals(3, count);
  }

  // Tests getUnitMap() for correct behavior
  @Test
  public void test_getUnitMap() {
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    hoth.addUnit(0);
    hoth.addUnit(8);
    hoth.addUnit(8);
    Map<Integer, Long> unitsMap = hoth.getUnitMap();
    assertEquals(2, unitsMap.size());
    assertEquals(2, unitsMap.get(8));
  }

  // Tests getSize() and getResourceProd() for correct behavior
  @Test
  public void test_size_and_resource_prod() {
    Territory hoth = new BasicTerritory("Hoth", "Vader", 10);
    assertEquals(10, hoth.getSize());
    assertEquals(5, hoth.getResourceProd());
  }

}
