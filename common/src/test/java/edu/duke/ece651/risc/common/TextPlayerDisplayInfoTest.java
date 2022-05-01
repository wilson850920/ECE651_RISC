package edu.duke.ece651.risc.common;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class TextPlayerDisplayInfoTest {

  // Test displayPlayer() for valid info
  @Test
  public void test_displayPlayer() throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Player p = new BasicPlayer("Yoda", 1337);
    PlayerDisplayInfo displayInfo = new TextPlayerDisplayInfo(p);
    displayInfo.displayPlayer(out);
    System.out.print(out.toString());
    assertTrue(out.toString().contains("Yoda"));
    assertTrue(out.toString().contains("1337"));
  }

}
