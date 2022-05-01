package edu.duke.ece651.risc.server;

import edu.duke.ece651.risc.common.MapDisplayInfo;
import edu.duke.ece651.risc.common.Player;
import java.io.IOException;
import java.io.Serializable;

/**
 * Represents the required functionality of a Map object in the risc game
 */
public interface Map extends Serializable {
  /**
   * Adds a player to the list of players inside the Map object
   *
   * @param newPlayer is the Player to be added to the map
   * @throws IllegalArguementException if the player is already added
   */
  public void addPlayer(Player newPlayer) throws IllegalArgumentException;

  /**
   * Removes a selected player from the list of players inside the Map object
   *
   * @param targetPlayer is the Player to be removed from the map
   * @throws IllegalArgumentException if the Map does not contain the given player
   */
  public void removePlayer(Player targetPlayer) throws IllegalArgumentException;

  /**
   * Checks if the map contains a selected player
   *
   * @param targetPlayer is the Player that the map is being checked for
   * @return Boolean true if the map contains the player and false if not
   */
  public Boolean hasPlayer(Player targetPlayer);
 
  /**
   * Gets the display information of a Map object
   *
   * @return MapDisplayInfo object containing info that describes a Map
   * @throws IOException if the display info could not be created
   */
  public MapDisplayInfo getDisplayInfo() throws IOException;

  /**
   * Executes the players orders from a turn of the risc game on the map
   *
   * @param currentTurn is the Turn that is being executed on the map
   * @throws IllegalArgumentException if the currentTurn cannot be executed
   */
  public void executeTurn(Turn currentTurn) throws IllegalArgumentException;
}
