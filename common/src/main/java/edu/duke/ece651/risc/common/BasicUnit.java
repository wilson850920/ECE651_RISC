package edu.duke.ece651.risc.common;

import java.util.ArrayList;

/**
 * Represents a basic implementation of a Unit object
 */
public class BasicUnit implements Unit {
  private int cost;
  private int bonus;
  private int techLevel;
  private ArrayList<Pair<Integer>> upgradeMap;

  /**
   * Creates an mapping of each unit techlevel to bonus and cost
   * 
   * @return ArrayList<Pair<Integer>> is a mapping of index (techlevel) with bonus
   *         and cost of a Unit
   */
  protected ArrayList<Pair<Integer>> createUpgradeMap() {
    ArrayList<Pair<Integer>> upgradeMap = new ArrayList<Pair<Integer>>();
    upgradeMap.add(new Pair<Integer>(0, 0));
    upgradeMap.add(new Pair<Integer>(1, 3));
    upgradeMap.add(new Pair<Integer>(3, 8));
    upgradeMap.add(new Pair<Integer>(5, 19));
    upgradeMap.add(new Pair<Integer>(8, 25));
    upgradeMap.add(new Pair<Integer>(11, 35));
    upgradeMap.add(new Pair<Integer>(15, 50));
    return upgradeMap;
  }

  /**
   * Constructs a new BasicUnit object
   */
  public BasicUnit() {
    this.cost = 0;
    this.bonus = 0;
    this.techLevel = 0;
    this.upgradeMap = createUpgradeMap();
  }

  /**
   * Constructs a new BasicUnit object for testing
   *
   * @param bonus,     the bonus of the Unit
   * @param techLevel, the tech level of the Unit
   * @param cost,      the cost of the Unit
   */
  public BasicUnit(int bonus, int techLevel, int cost) {
    this.techLevel = techLevel;
    this.bonus = bonus;
    this.cost = cost;
    this.upgradeMap = createUpgradeMap();
  }

  /**
   * Gets the Unit cost value
   *
   * @return int the cost of the Unit
   */
  @Override
  public int getCost() {
    return this.cost;
  }

  /**
   * Gets the Unit bonus value
   *
   * @return int the bonus value of the Unit
   */
  @Override
  public int getBonus() {
    return this.bonus;
  }

  /**
   * Gets the Units required technology level
   *
   * @return int the technology level of the Unit
   */
  @Override
  public int getTechLevel() {
    return this.techLevel;
  }

  /**
   * Determines the cost of upgrading a Unit to a given level
   *
   * @return int the cost of the unit upgrade, 0 if not possible
   */
  @Override
  public int upgradeCost(int techLevel) {
    int cost = 0;
    if (techLevel > this.techLevel && !(techLevel > this.upgradeMap.size() - 1)) {
      for (int i = this.techLevel; i < techLevel; i++) {
        cost += upgradeMap.get(i + 1).getSecond();
      }
    }
    return cost;
  }

  /**
   * Upgrades the Unit to a given tech level
   *
   * @param techLevel is the level the unit is to be upgraded to
   */
  @Override
  public void upgradeUnit(int techLevel) {
    if (techLevel > this.techLevel && !(techLevel > this.upgradeMap.size() - 1)) {
      this.bonus = this.upgradeMap.get(techLevel).getFirst();
      this.cost = this.upgradeMap.get(techLevel).getSecond();
      this.techLevel = techLevel;
    }
  }

  /**
   * Gets the display information of a Unit object
   *
   * @return UnitDisplayInfo object containing info that describes a Unit
   */
  @Override
  public UnitDisplayInfo getDisplayInfo() {
    return new TextUnitDisplayInfo(this.bonus);
  }

}
