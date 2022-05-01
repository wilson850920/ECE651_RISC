package edu.duke.ece651.risc.common;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Interface for different kinds of players in RISC game.
 */
public interface Player extends Serializable {
  /**
   * Get unique name of player
   * 
   * @return a string representing name of the player
   */
  public String getName();

  /**
   * Sets the name of a Player object
   *
   * @param name is the new name of the Player
   */
  public void setName(String name);

  /**
   * Add a territory to the list of territories owned by the player
   * 
   * @param add_T is the territory to be added
   */
  public void addTerritory(Territory add_T);

  /**
   * Remove a territory from the list of territories owned by the player
   * 
   * @param del_T is the territory to be removed
   */
  public void removeTerritory(Territory del_T);

  /**
   * Provides an iterator for access to a the territories owned by a Player
   *
   * @return Iterator<Territory> that iterates over Player's ArrayList of
   *         Territories
   */
  public Iterator<Territory> getTerritories();

  /**
   * set the available units for a single player
   * 
   * @param num is the number of units for each player
   */
  public void setAvailableUnits(int num);

  /**
   * get the available units for each player to process
   */
  public Integer getAvailableUnits();

  /**
   * Checks if a player owns a specific territory
   * 
   * @param T is the territory being checked for ownership
   * @return true if they own T and false if the do not
   */
  public boolean ownsTerritory(Territory T);

  /**
   * Checks if a player owns a specific territory by name
   * 
   * @param territoryName is the name of the territory being checked for ownership
   * @return true if they own a territory with the given name and false if they do
   *         not
   */
  public boolean ownsTerritory(String territoryName);

  /**
   * Gets the number of Territories the Player currently owns
   *
   * @return int that represent the size of the territory list
   */
  public int numberOfTerritories();

  /**
   * get the boolean value of the player true represent still in the game flase
   * represent loser
   */
  public boolean getIngameOrLoseFlag();

  /**
   * set the ingame_or_lose_flag for a player true represent still in the game
   * flase represent loser
   */
  public void setIngameOrLoseFlag(boolean flag);

  /**
   * Gets the current ammout of resources the player owns
   *
   * @return int is the current total number of resources the player owns
   */
  public int getResources();

  /**
   * update the ammount of resources of the player according to the number of
   * territories it owns
   */
  public void updateResources();

  /**
   * Spends/deducts resources from a Player's total resources
   *
   * @param cost is the ammout of resources being spent
   */
  public void spendResources(int cost);

  /**
   * Upgrade the max technology level upgrade cost according to the instruction
   * 
   * @return true if succeeds
   */
  public boolean upgradeMaxTechLevel();

  /**
   * return the max technology level
   *
   * @return the level of player's max technology level
   */
  public Integer getMaxTechLevel();

  /**
   * Gets the display information of a Player object
   *
   * @return PlayerDisplayInfo object containing info that describes a Player
   * @throws IOException if TerritoryDisplayInfo could not be read
   */
  public PlayerDisplayInfo getDisplayInfo() throws IOException;

  public void setMaxTechLevel(int level);

  /**
   * Adds a SpyUnit to the players list of spies
   *
   * @param spy is the SpyUnit to be added
   */
  public void addSpy(SpyUnit spy);

  /**
   * Removes a SpyUnit to the players list of spies
   *
   * @param spy is the SpyUnit to be removed
   */
  public void removeSpy(SpyUnit spy);

  /**
   * Provides access to the players list of spies
   *
   * @param spy is the SpyUnit to be removed
   */
  public Iterator<SpyUnit> spyIterator();

  public boolean playerCanCloak();

  /**
   * enable cloak ability
   */
  public void enableCloakAbility();

  /**
   * disable cloak ability
   */
  public void disableCloakAbility();

}
