package edu.duke.ece651.risc.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

public class TextPlayerDisplayInfo implements PlayerDisplayInfo {

  private String info;

  /**
   * Creates a TextPlayerDisplayInfo object based on a given Player
   *
   * @param player is the Player that will be used to generate display info
   * @throws IOExcpetion if data can't be written to info ByteArrayOutputStream
   */
  public TextPlayerDisplayInfo(Player player) throws IOException {
    // Stream used for territory display infos
    ByteArrayOutputStream stream = new ByteArrayOutputStream(); 
    StringBuilder sb = new StringBuilder();
    sb.append("Player: " + player.getName() + "\n");
    sb.append("\tMax Technology Level: " + player.getMaxTechLevel() + "\n");
    sb.append("\tCurrent Resources: " + player.getResources() + "\n");
    sb.append("Territories Owned:\n");
    Iterator<Territory> it = player.getTerritories();
    while (it.hasNext()) {
      it.next().getDisplayInfo().displayTerritory(stream);
    }
    sb.append(stream.toString());
    this.info = sb.toString();
  }

  /**
   * Writes Player display information to a given output stream
   *
   * @throws IOException if the information could not be written to the given
   *                     OutputStream
   */
  @Override
  
  public void displayPlayer(OutputStream out) throws IOException {
    out.write(info.getBytes());
  }
}
