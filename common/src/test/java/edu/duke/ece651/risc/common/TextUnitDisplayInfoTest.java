package edu.duke.ece651.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class TextUnitDisplayInfoTest {

  // Test displayUnit() for valid info
  @Test
  public void test_displayUnit() throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    UnitDisplayInfo displayInfo = new TextUnitDisplayInfo(3);
    displayInfo.displayUnit(out);
    assertEquals("C", out.toString());
  }

}
