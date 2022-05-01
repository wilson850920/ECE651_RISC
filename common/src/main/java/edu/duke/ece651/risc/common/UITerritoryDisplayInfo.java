package edu.duke.ece651.risc.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class UITerritoryDisplayInfo implements TerritoryDisplayInfo {

  private String info;

  /**
   * Creates a UITerritoryDisplayInfo object based on a given Territory
   *
   * @param territory is the territory that will be used to generate display info
   */
  public UITerritoryDisplayInfo(Territory territory) throws IOException {
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    JSONObject territoryJSON = new JSONObject();
    JSONObject attributes = new JSONObject();
    
    // Name
    attributes.put("Name", territory.getName());

    // Owner
    attributes.put("Owner", territory.getOwner());

    // Size
    attributes.put("Size", territory.getSize());

    // Cloaking
    attributes.put("Cloaked", territory.isCloaked());
    attributes.put("Cloaking Turn", territory.getCloakingTurn());
    
    // Neighbors
    JSONArray neighborsArray = new JSONArray();
    Iterator<Territory> it = territory.getNeighbors();
    while(it.hasNext()) {
      Territory t = it.next();
      neighborsArray.put(t.getName());
    }
    attributes.put("Neighbors", neighborsArray);

    // Units
    JSONArray unitArray = new JSONArray();
    Map<Integer, Long> unitMap = territory.getUnitMap();
    for (Integer bonus : unitMap.keySet()) {
      JSONObject unit = new JSONObject();
      UnitDisplayInfo unitInfo = new TextUnitDisplayInfo(bonus);
      unitInfo.displayUnit(stream);
      unit.put(stream.toString(), unitMap.get(bonus));
      unitArray.put(unit);
      stream.reset();
    }
    
    // Make Territory JSON with attributes 
    attributes.put("Units", unitArray);
    territoryJSON.put("Territory", attributes);
    this.info = territoryJSON.toString(4);
  }

  /**
   * Writes territory display information to a given output stream
   *
   * @throws IOException if the information could not be written to the given
   *                     OutputStream
   */
  @Override
  public void displayTerritory(OutputStream out) throws IOException {
    out.write(this.info.getBytes());
  }

  /**
   * Gets JSON info for display
   * 
   * @return JSONObject this.info for display
   */
  public JSONObject getJSON() {
    JSONObject jsonInfo = new JSONObject(this.info);
    return jsonInfo;
  }

}
