package edu.duke.ece651.risc.common;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONObject;

public class UIMapDisplayInfo implements MapDisplayInfo {

  private String info;

  /**
   * Creates a UIMapDisplayInfo object based on given set of players
   *
   * @param players is the HashSet of players that make up the Map
   * @throws IOExcpetion if data can't be written to info ByteArrayOutputStream
   */
  public UIMapDisplayInfo(HashSet<Player> players) throws IOException {
    JSONObject map = new JSONObject();
    JSONArray playerArray = new JSONArray();
    for (Player p : players) {
      UIPlayerDisplayInfo playerInfo = new UIPlayerDisplayInfo(p);
      playerArray.put(playerInfo.getJSON());
    }
    map.put("Map", playerArray);
    this.info = map.toString(4);
  }

  public String mapinfo() {
    return info;
  }
  
  /**
   * Writes map display information to a given output stream
   *
   * @throws IOException if the information could not be written to the given
   *                     OutputStream
   */
  @Override
  public void displayMap(OutputStream out) throws IOException {
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
