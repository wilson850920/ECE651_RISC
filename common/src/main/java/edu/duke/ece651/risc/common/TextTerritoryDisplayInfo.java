package edu.duke.ece651.risc.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class TextTerritoryDisplayInfo implements TerritoryDisplayInfo {

  private String info;

  /**
   * Creates a TextTerritoryDisplayInfo object based on given Territory attributes
   *
   * @param name      is the name of the Territory
   * @param neighbors is the list of neighboring Territories
   * @param unitMap   is a map of the type and number of each unit in the
   *                  territory (type by bonus)
   */
  public TextTerritoryDisplayInfo(String name, int numUnits, HashSet<Territory> neighbors) {
    StringBuilder sb = new StringBuilder();
    sb.append(numUnits + " in " + name);
    sb.append(" (next to: ");
    for (Territory t : neighbors) {
      sb.append(t.getName() + ", ");
    }
    sb.setLength(sb.length() - 2);
    sb.append(")");
    this.info = sb.toString();
  }

  /**
   * Creates a TextTerritoryDisplayInfo object based on a given Territory
   *
   * @param territory is the territory that will be used to generate display info
   */
  public TextTerritoryDisplayInfo(Territory territory) throws IOException {
    // Stream used for unit display infos
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    StringBuilder sb = new StringBuilder();
    // Name and neighbors
    sb.append(territory.getName() + " (next to: ");
    Iterator<Territory> it = territory.getNeighbors();
    while (it.hasNext()) {
      Territory t = it.next();
      sb.append(t.getName() + ", ");
    }
    sb.setLength(sb.length() - 2);
    sb.append(")\n");
    // Size and resource production
    sb.append("\tSize: " + territory.getSize() + "\n");
    sb.append("\tResource Production (per-turn): " + territory.getResourceProd() + "\n");
    // Units
    sb.append("\tUnits:\n");
    Map<Integer, Long> unitMap = territory.getUnitMap();
    for (Integer i : unitMap.keySet()) {
      UnitDisplayInfo unitInfo = new TextUnitDisplayInfo(i);
      sb.append("\t\t");
      unitInfo.displayUnit(stream);
      sb.append(": " + unitMap.get(i) + "\n");
    }
    this.info = sb.toString();
  }

  /**
   * Writes territory display information to a given output stream
   *
   * @throws IOException if the information could not be written to the given
   *                     OutputStream
   */
  @Override
  public void displayTerritory(OutputStream out) throws IOException {
    out.write(info.getBytes());
  }

}
