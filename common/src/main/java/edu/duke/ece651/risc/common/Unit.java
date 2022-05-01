
package edu.duke.ece651.risc.common;

import java.io.Serializable;

/**
 * Interface for different kinds of Units in the RISC game
 */
public interface Unit extends Serializable {

  /**
   * Gets the Unit cost value
   *
   * @return int the cost of the Unit
   */
  public int getCost();

  /**
   * Gets the Unit bonus value
   *
   * @return int the bonus value of the Unit
   */
  public int getBonus();

  /**
   * Gets the Units required technology level
   *
   * @return int the technology level of the Unit
   */
  public int getTechLevel();

  /**
   * Determines the cost of upgrading a Unit to a given level
   *
   * @return int the cost of the unit upgrade, 0 if not possible
   */
  public int upgradeCost(int techLevel);

  /**
   * Upgrades the Unit to a given tech level
   *
   * @param techLevel is the level the unit is to be upgraded to
   */
  public void upgradeUnit(int techLevel);

  /**
   * Gets the display information of a Unit object
   *
   * @return UnitDisplayInfo object containing info that describes a Unit
   */
  public UnitDisplayInfo getDisplayInfo();

}
