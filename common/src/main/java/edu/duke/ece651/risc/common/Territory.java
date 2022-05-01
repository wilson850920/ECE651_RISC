package edu.duke.ece651.risc.common;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/**
 * This interface represents any type of territory in the RISC game It is
 * generic in typename T, which is the the type of information the view needs to
 * display this territory
 */
public interface Territory extends Serializable {

  /**
   * Get the name of the territory
   * 
   * @return a string which is the name of the territory
   */
  public String getName();

  /**
   * Gets the size of the Territory
   * 
   * @return int size of the Territory
   */
  public int getSize();

  /**
   * Gets the resource production of the Territory
   * 
   * @return int resource production value of the Territory
   */
  public int getResourceProd();

  /**
   * Get the name of the owner of the territory
   * 
   * @return a string which is the name of the owner of the territory
   */
  public String getOwner();

  /**
   * Sets the name of the owner of the Territory
   *
   * @param name is the name of the new Territory owner
   */
  public void setOwner(String name);

  /**
   * Sets the neighbor of the Territory (for initializing Territory graph
   * connectivity in a Map)
   *
   * @param territories is the terriory adjacent to this territory
   */
  public void setNeighbors(Territory... territories);

  /**
   * Adds a Unit to the Territory
   *
   * @param bonus is the bonus of the Unit that will be added to the Territory
   */
  public void addUnit(int bonus);

  /**
   * Removes a Unit from the Territory
   *
   * @param bonus is the bonus of the Unit that will be removed to the Territory
   */
  public void removeUnit(int bonus);

  /**
   * Gets the TOTAL number of uints in the Territory
   *
   * @return int the number of units in the Territory
   */
  public int getNumberOfUnits();

  /**
   * Gets the number of units that have the same bonus as the given bonus value in
   * the Territory
   * 
   * @param bonus is bonus (identifies type) of unit to return the count of in the
   *              Territory
   * @return int the number of units with the specified bonus value in the
   *         Territory
   */
  public int getUnitCount(int bonus);

  /**
   * Provides a Map of Units in the territory and their count
   * 
   * @return Iterator<Unit> iterates through the list of units in the territory
   */
  public Iterator<Unit> getUnits();

  /**
   * Provides a Map of Units in the territory and their count
   * 
   * @return Map<Integer, Long> Map of Units (by bonus) and their count
   */
  public Map<Integer, Long> getUnitMap();

  /**
   * Adds a Territory to the list of neighboring Territories
   *
   * @param newTerritory is the Territory being added
   */
  public void addNeighbor(Territory newTerritory);

  /**
   * Removes a Territory from the list of neighboring Territories
   *
   * @param targetTerritory is the Territory being removed
   */
  public void removeNeighbor(Territory targetTerritory);

  /**
   * Checks if the Territory has the target as a neighbor
   *
   * @param targetTerritory is the Territory being tested as a neighbor
   * @return boolean true if neighbors contains target, false if not
   */
  public boolean hasNeighbor(Territory targetTerritory);

  /**
   * Provides an interator with access to the list of neighbors
   * 
   * @return Iterator<Territory> iterates through the list of neighbors
   */
  public Iterator<Territory> getNeighbors();

  /**
   * Gets the display information of a Territory object
   *
   * @return TerritoryDisplayInfo object containing info that describes a
   *         Territory
   * @throws IOException if TerritoryDisplayInfo could not be read
   */
  public TerritoryDisplayInfo getDisplayInfo() throws IOException;

  boolean isCloaked();

  void cloakTerritory();

  void resetCloaking();

  int getCloakingTurn();

  void incrementCloakingTurn();

  void resolveCloaking();
}
