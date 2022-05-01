package edu.duke.ece651.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

import org.junit.jupiter.api.Test;

public class TextTerritoryDisplayInfoTest {

  // Tests displaying text info to an outputstream
  @Test
  public void test_displayTerritory() throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    HashSet<Territory> neighbors = new LinkedHashSet<Territory>();
    neighbors.add(new BasicTerritory("Coruscant", "Palpatine"));
    neighbors.add(new BasicTerritory("Hoth", "Vader"));
    neighbors.add(new BasicTerritory("Kamino", "Sifo-Dyas"));
    TerritoryDisplayInfo displayInfo = new TextTerritoryDisplayInfo("Tatooine", 10, neighbors);
    displayInfo.displayTerritory(out);
    assertNotNull(out.toString());
    assertEquals(true, out.toString().contains("Kamino"));
    assertEquals(true, out.toString().contains("Hoth"));
    assertEquals(true, out.toString().contains("Coruscant"));
    assertEquals(true, out.toString().contains("Tatooine"));
    assertEquals(true, out.toString().contains("10"));
  }

  // Tests displaying text info to an outputstream
  @Test
  public void test_new_displayTerritory() throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    HashSet<Territory> neighbors = new LinkedHashSet<Territory>();
    Territory tatooine = new BasicTerritory("Tatooine", "Luke");
    neighbors.add(new BasicTerritory("Coruscant", "Palpatine"));
    neighbors.add(new BasicTerritory("Hoth", "Vader"));
    neighbors.add(new BasicTerritory("Kamino", "Sifo-Dyas"));
    for (Territory t : neighbors) {
      tatooine.addNeighbor(t);
    }
    ArrayList<Integer> units = new ArrayList<Integer>();
    for (int i = 0; i < 10; i++) {
      units.add(0);
    }
    units.add(1);
    units.add(1);
    units.add(1);
    units.add(11);
    for (Integer i : units) {
      tatooine.addUnit(i);
    }
    TerritoryDisplayInfo displayInfo = new TextTerritoryDisplayInfo(tatooine);
    displayInfo.displayTerritory(out);
    assertNotNull(out.toString());
    System.out.print(out.toString()); // Used for checking 
  }

}
