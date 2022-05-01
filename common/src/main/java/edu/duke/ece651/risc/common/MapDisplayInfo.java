package edu.duke.ece651.risc.common;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Interface that represents the required functionality for display information of a Map object
 * Serializable so that it may be communcated from server to clients 
 */
public interface MapDisplayInfo extends Serializable {

  /**
   * Writes map display information to a given output stream
   *
   * @throws IOException if the information could not be written to the given OutputStream
   */ 
  public void displayMap(OutputStream out) throws IOException;
    
}
