package edu.duke.ece651.risc.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;

/**
 * Implementation of MapDisplayInfo for basic text output format
 */
public class TextMapDisplayInfo implements MapDisplayInfo {

  private String info;

  /**
   * Creates a TextMapDisplayInfo object based on given set of players
   *
   * @param players is the HashSet of players that make up the Map
   * @throws IOExcpetion if data can't be written to info ByteArrayOutputStream
   */
  public TextMapDisplayInfo(HashSet<Player> players) throws IOException {
    // Stream used for player display infos
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    StringBuilder sb = new StringBuilder();
    for (Player p : players) {
      sb.append("\n----------------------------------\n");
      p.getDisplayInfo().displayPlayer(stream);
      sb.append(stream.toString());
      sb.append("\n----------------------------------\n");
    }
    this.info = sb.toString();
  }

  /**
   * Writes map display information to a given output stream
   *
   * @throws IOException if the information could not be written to the given
   *                     OutputStream
   */
  @Override
  public void displayMap(OutputStream out) throws IOException {
    out.write(info.getBytes());
  }

}
