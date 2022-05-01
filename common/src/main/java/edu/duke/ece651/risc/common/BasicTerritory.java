package edu.duke.ece651.risc.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class BasicTerritory implements Territory {
  private final String name;
  private final int size;
  private final HashSet<Territory> neighbors;
  private final HashMap<Integer, Pair<Integer>> unitMap;
  private String owner;
  private ArrayList<Unit> units;
  private boolean cloaked = false;
  private int cloakingTurn = 0;

  /**
   * Creates an mapping of a unit bounus to tech level and cost
   *
   * @return HashMap<Integer, Pai<Integer>> a map of bonus to tech level and
   *         cost
   */
  protected HashMap<Integer, Pair<Integer>> createUnitMap() {
    HashMap<Integer, Pair<Integer>> unitMap = new HashMap<Integer, Pair<Integer>>();
    unitMap.put(0, new Pair<Integer>(0, 0));
    unitMap.put(1, new Pair<Integer>(1, 3));
    unitMap.put(3, new Pair<Integer>(2, 8));
    unitMap.put(5, new Pair<Integer>(3, 19));
    unitMap.put(8, new Pair<Integer>(4, 25));
    unitMap.put(11, new Pair<Integer>(5, 35));
    unitMap.put(15, new Pair<Integer>(6, 50));
    return unitMap;
  }

  /**
   * Constructs a BasicTerritory object with an owner and name
   * 
   * @param name  is the namte of the territory, this would not be changed
   *              throughout the game
   * @param owner is the name of the current player, note that the owner of a
   *              territory may change correspond to the game
   */
  public BasicTerritory(String name, String owner) {
    this.name = name;
    this.size = 1;
    this.owner = owner;
    this.neighbors = new HashSet<Territory>();
    this.units = new ArrayList<Unit>();
    this.unitMap = createUnitMap();
  }

  /**
   * Constructs a BasicTerritory object with an owner, name, and size
   * 
   * @param name  is the namte of the territory, this would not be changed
   *              throughout the game
   * @param owner is the name of the current player, note that the owner of a
   *              territory may change correspond to the game
   * @param size  is the size of the Territory
   */
  public BasicTerritory(String name, String owner, int size) {
    this.name = name;
    this.size = size;
    this.owner = owner;
    this.neighbors = new HashSet<Territory>();
    this.units = new ArrayList<Unit>();
    this.unitMap = createUnitMap();
  }

  /**
   * Get the name of the territory
   * 
   * @return a string which is the name of the territory
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Gets the size of the Territory
   * 
   * @return int size of the Territory
   */
  @Override
  public int getSize() {
    return this.size;
  }

  /**
   * Gets the resource production of the Territory
   * 
   * @return int resource production value of the Territory
   */
  @Override
  public int getResourceProd() {
    // return this.size / 2;
    return (this.getNumberOfUnits() + 1) * this.size / 2;
  }

  /**
   * Get the name of the owner of the territory
   * 
   * @return a string which is the name of the owner of the territory
   */
  @Override
  public String getOwner() {
    return owner;
  }

  /**
   * Sets the name of the owner of the Territory
   *
   * @param name is the name of the new Territory owner
   */
  @Override
  public void setOwner(String name) {
    this.owner = name;
  }

  /**
   * Sets the neighbor of the Territory (for initializing Territory graph
   * connectivity in a Map)
   *
   * @param territories is the terriory adjacent to this territory
   */
  @Override
  public void setNeighbors(Territory... territories) {
    for (Territory t : territories) {
      this.neighbors.add(t);
    }
  }

  /**
   * Adds a Unit to the Territory
   *
   * @param bonus is the bonus of the Unit that will be added to the Territory
   */
  @Override
  public void addUnit(int bonus) {
    int techLevel = this.unitMap.get(bonus).getFirst();
    int cost = this.unitMap.get(bonus).getSecond();
    this.units.add(new BasicUnit(bonus, techLevel, cost));
  }

  /**
   * Removes a Unit from the Territory
   *
   * @param bonus is the bonus of the Unit that will be removed to the Territory
   */
  @Override
  public void removeUnit(int bonus) {
    for (Unit u : this.units) {
      if (u.getBonus() == bonus) {
        this.units.remove(u);
        return;
      }
    }
  }

  /**
   * Gets the TOTAL number of units that are in the Territory
   *
   * @return int the number of units in the Territory
   */
  @Override
  public int getNumberOfUnits() {
    return this.units.size();
  }

  /**
   * Gets the number of units that have the same bonus as the given bonus value in
   * the Territory
   * 
   * @param bonus is bonus (identifies type) of unit to return the count of in the
   *              Territory
   * @return int the number of units with the specified bonus value in the
   *         Territory
   */
  @Override
  public int getUnitCount(int bonus) {
    int count = 0;
    for (Unit u : this.units) {
      if (u.getBonus() == bonus) {
        count++;
      }
    }
    return count;
  }

  /**
   * Provides a Map of Units in the territory and their count
   * 
   * @return Iterator<Unit> iterates through the list of units in the territory
   */
  @Override
  public Iterator<Unit> getUnits() {
    return this.units.iterator();
  }

  /**
   * Provides a Map of Units in the territory and their count
   * 
   * @return Map<Integer, Long> Map of Units (by bonus) and their count
   */
  @Override
  public Map<Integer, Long> getUnitMap() {
    return this.units.parallelStream().collect(Collectors.groupingBy(Unit::getBonus, Collectors.counting()));
  }

  /**
   * Adds a Territory to the list of neighboring Territories
   *
   * @param newTerritory is the Territory being added
   */
  @Override
  public void addNeighbor(Territory newTerritory) {
    this.neighbors.add(newTerritory);
  }

  /**
   * Removes a Territory from the list of neighboring Territories
   *
   * @param targetTerritory is the Territory being removed
   */
  @Override
  public void removeNeighbor(Territory targetTerritory) {
    this.neighbors.remove(targetTerritory);
  }

  /**
   * Checks if the Territory has the target as a neighbor
   *
   * @param targetTerritory is the Territory being tested as a neighbor
   * @return boolean true if neighbors contains target, false if not
   */
  @Override
  public boolean hasNeighbor(Territory targetTerritory) {
    for (Territory t : this.neighbors) {
      if (t.getName().equals(targetTerritory.getName()))
        return true;
    }
    return false;
  }

  /**
   * Provides an interator with access to the list of neighbors
   * 
   * @return Iterator<Territory> iterates through the list of neighbors
   */
  @Override
  public Iterator<Territory> getNeighbors() {
    return this.neighbors.iterator();
  }

  /**
   * Gets the display information of a Territory object
   *
   * @return TerritoryDisplayInfo object containing info that describes a
   *         Territory
   * @throws IOException if UnitDisplayInfo could not be read
   */
  @Override
  public TerritoryDisplayInfo getDisplayInfo() throws IOException {
    return new UITerritoryDisplayInfo(this);
  }

  @Override
  public boolean isCloaked(){return this.cloaked;}

  @Override
  public void cloakTerritory(){
    this.cloaked = true;
    this.cloakingTurn = 1;
  }

  @Override
  public void resetCloaking(){
    this.cloaked = false;
    this.cloakingTurn = 0 ;
    System.out.println("Territory "+ this.name+ " Cloaking reset");

  }

  @Override
  public int getCloakingTurn(){return this.cloakingTurn;}

  @Override
  public void incrementCloakingTurn(){
    this.cloakingTurn++;
  }

  @Override
  public void resolveCloaking(){
    //if cloaking is applied for three turns, disable cloaking
    if(getCloakingTurn() >= 3){
      resetCloaking();
    } else{
      //if the territory is cloaked, keep track of turns executed after it's been activated
      if (isCloaked()){
        incrementCloakingTurn();
        System.out.println("Territory "+ this.name+ " Will be cloaked only for " + (4-getCloakingTurn()) + " turns more.");
      }
    }
  }
}
