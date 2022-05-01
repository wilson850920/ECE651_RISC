package edu.duke.ece651.risc.server;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class BasicTerritoyFactoryTest {
  @Test
  public void test_MakeTerritory() {
    BasicTerritoyFactory make2 = new BasicTerritoyFactory();
    assertNotNull(make2.makeTerritory(2));
    BasicTerritoyFactory make3 = new BasicTerritoyFactory();
    assertNull(make2.makeTerritory(6));
  }
}
