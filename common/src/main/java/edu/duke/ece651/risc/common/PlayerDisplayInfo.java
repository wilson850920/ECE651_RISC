package edu.duke.ece651.risc.common;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Represents the display information of a Player object for the RISC game
 */
public interface PlayerDisplayInfo extends Serializable {

  /**
   * Writes Player display information to a given output stream
   *
   * @throws IOException if the information could not be written to the given
   *                     OutputStream
   */
  public void displayPlayer(OutputStream out) throws IOException;
  
}
