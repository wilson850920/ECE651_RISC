package edu.duke.ece651.risc.common;

import java.io.Serializable;

/**
 * Represents a Spy type unit in risc game
 */
public class SpyUnit implements Serializable{
  
  private Player owner;
  private Territory location;
  private int revolutionStage;
  private int funding;

  /**
   * Constructs a SpyUnit with a given owner and location
   *
   * @param owner    is the Player that owns the
   * @param location is the Territory that the spy is in
   */
  public SpyUnit(Player owner, Territory location) {
    this.owner = owner;
    this.location = location;
    this.revolutionStage = 0; // Init at 0 -> no active revolution
    this.funding = 0;
  }

  /**
   * Gets the Player that owns the spy
   *
   * @return Player the owner of the spy
   */
  public Player getOwner() {
    return this.owner;
  }

  /**
   * Sets the location of the spy
   *
   * @param newLocation is the new territory the spy is in
   */
  public void setLocation(Territory newLocation) {
    this.location = newLocation;
  }

  /**
   * Gets the location of the spy
   *
   * @return Territory is the current location of the spy
   */
  public Territory getLocation() {
    return this.location;
  }

  /**
   * Incriments the revolution counter in the SpyUnit to progress the revolution
   */
  public void progressRevolution() {
    this.revolutionStage++;
  }

  /**
   * Resets the revolution counter and funding to 0
   */
  public void resetRevolution() {
    this.revolutionStage = 0;
    this.funding = 0;
  }

  /**
   * Gets the current revolution stage
   *
   * @return int is the current stage of the revolution being preformed by the spy
   */
  public int revolutionStage() {
    return this.revolutionStage;
  }

  /**
   * Sets the funding of the spy
   *
   * @param funding is the ammount of resources allocated to the spy for a revolution
   */
  public void setFunding(int funding) {
    this.funding = funding;
  }

  /**
   * Gets the funding of the spy
   *
   * @return int is the spys current funding for a revolution
   */
  public int getFunding() {
    return this.funding;
  }

}
