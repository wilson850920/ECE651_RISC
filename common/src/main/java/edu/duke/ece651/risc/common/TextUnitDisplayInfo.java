package edu.duke.ece651.risc.common;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class TextUnitDisplayInfo implements UnitDisplayInfo {
  
  private String info;
  private HashMap<Integer, String> unitInfoMap;

  /**
   * Creates an mapping of unit bonus to display string
   * Keys: bonus value of a unit
   * Values: text string to represent the unit when displayed
   *
   * @return HashMap<String, Integer> a map bonus to display string
   */
  protected HashMap<Integer, String> createUnitInfoMap() {
    HashMap<Integer, String> unitInfoMap = new HashMap<Integer, String>();
    unitInfoMap.put(0, "A");
    unitInfoMap.put(1, "B");
    unitInfoMap.put(3, "C");
    unitInfoMap.put(5, "D");
    unitInfoMap.put(8, "E");
    unitInfoMap.put(11, "F");
    unitInfoMap.put(15, "G");
    return unitInfoMap;
  }
 
  /**
   * Creates a TextUnitDisplayInfo object based on a given Unit
   * Given bonus -> displays String from map
   *
   * @param bonus is the int that will be used to generate display info
   */
  public TextUnitDisplayInfo(int bonus) {
    this.unitInfoMap = createUnitInfoMap();
    StringBuilder sb = new StringBuilder();
    sb.append(this.unitInfoMap.get(bonus));
    this.info = sb.toString();
  }

  /**
   * Writes Unit display information to a given output stream
   *
   * @throws IOException if the information could not be written to the given
   *                     OutputStream
   */
  @Override
  public void displayUnit(OutputStream out) throws IOException {
    out.write(info.getBytes());
  }
  
}
