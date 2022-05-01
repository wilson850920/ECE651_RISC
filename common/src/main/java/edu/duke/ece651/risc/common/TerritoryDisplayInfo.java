package edu.duke.ece651.risc.common;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Represents the display information of a Territory object for the RISC game
 */
public interface TerritoryDisplayInfo extends Serializable {

  /**
   * Writes territory display information to a given output stream
   *
   * @throws IOException if the information could not be written to the given OutputStream
   */
  public void displayTerritory(OutputStream out) throws IOException;

}

