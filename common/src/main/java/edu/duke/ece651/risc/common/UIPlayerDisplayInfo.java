
package edu.duke.ece651.risc.common;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class UIPlayerDisplayInfo implements PlayerDisplayInfo {

  private String info;

  /**
   * Creates a JSONArray of display information of the spies owned by a player
   *
   * @param player is the Player that will be used to generate display info
   * @return JSONArray containing display info of all spys owned by player
   */
  private JSONArray makeJSONSpyArray(Player player) {
    JSONArray spyArray = new JSONArray();
    Iterator<SpyUnit> it = player.spyIterator();
    while (it.hasNext()) {
      SpyUnit s = it.next();
      JSONObject spy = new JSONObject();
      spy.put("Location", s.getLocation().getName());
      spy.put("Revolution Stage", s.revolutionStage());
    }
    return spyArray;
  }

  /**
   * Creates a JSONArray of display information of the territories owned by a
   * player
   *
   * @param player is the Player that will be used to generate display info
   * @return JSONArray containing display info of all territories owned by player
   * @throws IOException if territory info could not be read
   */
  private JSONArray makeJSONTerritoryArray(Player player) throws IOException {
    JSONArray terrArray = new JSONArray();
    Iterator<Territory> it = player.getTerritories();
    while (it.hasNext()) {
      Territory t = it.next();
      UITerritoryDisplayInfo terrInfo = new UITerritoryDisplayInfo(t);
      terrArray.put(terrInfo.getJSON());
    }
    return terrArray;
  }

  /**
   * Creates a UIlayerDisplayInfo object based on a given Player
   *
   * @param player is the Player that will be used to generate display info
   * @throws IOExcpetion if data can't be written to info ByteArrayOutputStream
   */
  public UIPlayerDisplayInfo(Player player) throws IOException {
    JSONObject playerJSON = new JSONObject();
    JSONObject attributes = new JSONObject();

    // Name
    attributes.put("Name", player.getName());

    // Tech level
    attributes.put("Technology Level", String.valueOf(player.getMaxTechLevel()));

    // Resources
    attributes.put("Resources", String.valueOf(player.getResources()));

    // Spies
    attributes.put("Spies", makeJSONSpyArray(player));  
    
    // Territories
    attributes.put("Territories", makeJSONTerritoryArray(player));  

    // Collect attributes in to playerJSON
    playerJSON.put("Player", attributes);
    this.info = playerJSON.toString(4);
  }

  /**
   * Writes Player display information to a given output stream
   *
   * @throws IOException if the information could not be written to the given
   *                     OutputStream
   */
  @Override
  public void displayPlayer(OutputStream out) throws IOException {
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
