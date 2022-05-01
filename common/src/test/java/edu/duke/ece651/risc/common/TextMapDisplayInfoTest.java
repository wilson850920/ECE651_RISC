package edu.duke.ece651.risc.common;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;

import org.junit.jupiter.api.Test; 

public class TextMapDisplayInfoTest {
  
  // Tests the display of a one player map
  @Test
  public void test_displayMap() throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Player vader = new BasicPlayer("Vader");
    HashSet<Player> players = new HashSet<Player>();
    players.add(vader);
    Territory tatooine = new BasicTerritory("Tatooine", "Luke"); 
    Territory coruscant = new BasicTerritory("Coruscant", "Palpatine"); 
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    Territory kamino = new BasicTerritory("Kamino", "Sifo-Dyas");
    tatooine.addNeighbor(coruscant);
    tatooine.addNeighbor(hoth);
    tatooine.addNeighbor(kamino);
    hoth.addNeighbor(kamino);
    hoth.addNeighbor(coruscant);
    vader.addTerritory(tatooine);
    vader.addTerritory(hoth);
    MapDisplayInfo displayInfo = new TextMapDisplayInfo(players);
    displayInfo.displayMap(out);
    assertNotNull(out.toString());
    System.out.print(out.toString()); // Used for checking 
  }

}
