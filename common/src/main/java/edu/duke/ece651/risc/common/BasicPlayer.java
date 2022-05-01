package edu.duke.ece651.risc.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Basic implementation of the Player interface
 */
public class BasicPlayer implements Player {
  private String name;
  private ArrayList<Territory> territories;
  private Integer availableUnits;
  private Integer maxTechLevel;
  private Integer resources;
  private boolean ingame_or_lose_flag;
  private ArrayList<SpyUnit> spyList;
  private boolean canCloak;

  /**
   * This is a constructor for BasicPlayer
   * 
   * @param name is the name of the player and is fixed
   */
  public BasicPlayer(String name) {
    this.name = name;
    this.territories = new ArrayList<Territory>();
    this.availableUnits = 0;
    this.maxTechLevel = 1;
    this.resources = 0;
    this.ingame_or_lose_flag = true;
    this.spyList = new ArrayList<SpyUnit>();
    this.canCloak = false;
  }

  /**
   * This is a constructor for BasicPlayer
   * 
   * @param name          is the name of the player and is fixed
   * @param initResources is the inital ammout of resources the player is given
   */
  public BasicPlayer(String name, int initResources) {
    this.name = name;
    this.territories = new ArrayList<Territory>();
    this.availableUnits = 0;
    this.maxTechLevel = 1;
    this.resources = initResources;
    this.ingame_or_lose_flag = true;
    this.spyList = new ArrayList<SpyUnit>();
    this.canCloak = false;
  }

  public boolean playerCanCloak() {
    return this.canCloak;
  }

  /**
   * enable cloak ability
   */
  public void enableCloakAbility() {
    this.canCloak = true;
  }

  /**
   * disable cloak ability
   */
  public void disableCloakAbility() {
    this.canCloak = false;
  }

  /**
   * @return the name of the player
   */
  @Override
  public void setName(String name) {
    this.name = name;
  }

  /**
   * get the name of the player
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * add a territory T to the list of territories
   */
  @Override
  public void addTerritory(Territory T) {
    territories.add(T);
  }

  /**
   * remove a territory T from the list of territories
   */
  @Override
  public void removeTerritory(Territory T) {
    territories.remove(T);
  }

  /**
   * Provides an iterator for access to a the territories owned by a Player
   *
   * @return Iterator<Territory> that iterates over Player's ArrayList of
   *         Territories
   */
  @Override
  public Iterator<Territory> getTerritories() {
    return this.territories.iterator();
  }

  /**
   * @param num is the unit we want to set for the player
   */
  @Override
  public void setAvailableUnits(int num) {
    this.availableUnits = num;
  }

  /**
   * get the available units owned by the player for the next action
   * 
   * @return the available units that can by process
   */
  @Override
  public Integer getAvailableUnits() {
    return this.availableUnits;
  }

  /**
   * Checks if a player owns a specific territory
   * 
   * @param T is the territory being checked for ownership
   * @return true if they own T and false if the do not
   */
  @Override
  public boolean ownsTerritory(Territory T) {
    for (Territory t : territories) {
      if (t.getName().equals(T.getName())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if a player owns a specific territory by name
   * 
   * @param territoryName is the name of the territory being checked for ownership
   * @return true if they own a territory with the given name and false if they do
   *         not
   */
  @Override
  public boolean ownsTerritory(String territoryName) {
    for (Territory t : territories) {
      if (t.getName().equals(territoryName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Gets the number of Territories the Player currently owns
   *
   * @return int that represent the size of the territory list
   */
  @Override
  public int numberOfTerritories() {
    return this.territories.size();
  }

  /**
   * get the status of the player true means still in the game false means this
   * player is a loser
   */
  @Override
  public boolean getIngameOrLoseFlag() {
    return this.ingame_or_lose_flag;
  }

  /**
   * set the status of the player
   * 
   * @param flag : true means still in the game : false means this player is a
   *             loser
   */
  @Override
  public void setIngameOrLoseFlag(boolean flag) {
    this.ingame_or_lose_flag = flag;
  }

  /**
   * returns the number determining the cost of upgrading max tech level using
   * modified Fibonacci
   * 
   * @param level is the level of this game
   */
  protected int fibo(int level) {
    if (level <= 2) {
      return 1;
    }
    if (level == 3) {
      return fibo(level - 1) + fibo(level - 2);
    }
    return fibo(level - 1) + fibo(level - 2) + fibo(level - 3);
  }

  /**
   * Gets the current ammout of resources the player owns
   *
   * @return int is the current total number of resources the player owns
   */
  @Override
  public int getResources() {
    return this.resources;
  }

  /**
   * Spends/deducts resources from a Player's total resources
   *
   * @param cost is the ammout of resources being spent
   */
  @Override
  public void spendResources(int cost) {
    this.resources -= cost;
  }

  /**
   * update the ammount of resources of the player according to the number of
   * territories it owns
   */
  @Override
  public void updateResources() {
    Iterator<Territory> it = this.getTerritories();
    int newResources = 0;
    while (it.hasNext()) {
      newResources += it.next().getResourceProd();
    }
    this.resources += newResources;
  }

  /**
   * Upgrade the max technology level upgrade cost according to the instruction
   * 
   * @return true if succeeds
   */
  @Override
  public boolean upgradeMaxTechLevel() {
    int cost = (this.maxTechLevel + fibo(this.maxTechLevel)) * 25;
    if (this.resources >= cost) {
      this.maxTechLevel++;
      this.spendResources(cost);
      return true;
    }
    return false;
  }

  /**
   * return the max technology level
   *
   * @return the level of player's max technology level
   */
  @Override
  public Integer getMaxTechLevel() {
    return this.maxTechLevel;
  }

  @Override
  public void setMaxTechLevel(int level) {
    this.maxTechLevel = level;
  }

  /**
   * Gets the display information of a Player object
   *
   * @return PlayerDisplayInfo object containing info that describes a Player
   * @throws IOException if TerritoryDisplayInfo could not be read
   */
  @Override
  public PlayerDisplayInfo getDisplayInfo() throws IOException {
    return new UIPlayerDisplayInfo(this);
  }

  /**
   * Adds a SpyUnit to the players list of spies
   *
   * @param spy is the SpyUnit to be added
   */
  @Override
  public void addSpy(SpyUnit spy) {
    this.spyList.add(spy);
  }

  /**
   * Removes a SpyUnit to the players list of spies
   *
   * @param spy is the SpyUnit to be removed
   */
  @Override
  public void removeSpy(SpyUnit spy) {
    this.spyList.remove(spy);
  }

  /**
   * Provides access to the players list of spies
   *
   * @param spy is the SpyUnit to be removed
   */
  @Override
  public Iterator<SpyUnit> spyIterator() {
    return this.spyList.iterator();
  }

}
