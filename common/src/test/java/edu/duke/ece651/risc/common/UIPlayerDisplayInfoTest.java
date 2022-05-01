package edu.duke.ece651.risc.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

public class UIPlayerDisplayInfoTest {
  
// Test for vaild behavior
  @Test
  public void test_UIPlayerDisplayInfo() throws IOException {
    Player vader = new BasicPlayer("Vader", 1337);
    Territory hoth = new BasicTerritory("Hoth", "Vader");
    Territory mustafar = new BasicTerritory("Mustafar", "Vader");
    Territory tatooine = new BasicTerritory("Tatooine", "Luke");
    Territory alderaan = new BasicTerritory("Alderaan", "Leia");
    hoth.addNeighbor(mustafar);
    mustafar.addNeighbor(hoth);
    hoth.addNeighbor(tatooine);
    tatooine.addNeighbor(hoth);
    hoth.addNeighbor(alderaan);
    alderaan.addNeighbor(hoth);
    alderaan.addNeighbor(tatooine);
    tatooine.addNeighbor(alderaan);
    vader.addTerritory(hoth);
    vader.addTerritory(mustafar);
    for(int i = 0; i < 3; i++) {
      mustafar.addUnit(0);
      mustafar.addUnit(5);
      mustafar.addUnit(8);
      hoth.addUnit(0);
      hoth.addUnit(0);
      hoth.addUnit(0);
      hoth.addUnit(3);
      hoth.addUnit(3);
      hoth.addUnit(11);
    }
    PlayerDisplayInfo vaderInfo = new UIPlayerDisplayInfo(vader);
    OutputStream stream = new ByteArrayOutputStream();
    vaderInfo.displayPlayer(stream);
    String output = stream.toString();
    System.out.println(output);
  }

}
