package edu.duke.ece651.risc.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

public class UITerritoryDisplayInfoTest {

  // Test for vaild behavior
  @Test
  public void test_UITerritoryDisplayInfo() throws IOException {
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    Territory tatooine = new BasicTerritory("Tatooine", "Luke");
    Territory alderaan = new BasicTerritory("Alderaan", "Leia");
    hoth.addNeighbor(tatooine);
    tatooine.addNeighbor(hoth);
    hoth.addNeighbor(alderaan);
    alderaan.addNeighbor(hoth);
    alderaan.addNeighbor(tatooine);
    tatooine.addNeighbor(alderaan);
    for(int i = 0; i < 3; i++) {
      hoth.addUnit(0);
      hoth.addUnit(0);
      hoth.addUnit(0);
      hoth.addUnit(3);
      hoth.addUnit(3);
      hoth.addUnit(11);
    }
    TerritoryDisplayInfo hothInfo = new UITerritoryDisplayInfo(hoth);
    OutputStream stream = new ByteArrayOutputStream();
    hothInfo.displayTerritory(stream);
    String output = stream.toString();
    System.out.println(output);
  }

}
