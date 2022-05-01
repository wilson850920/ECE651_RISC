package edu.duke.ece651.risc.server;

public interface AbstractTerritoryFactory {
  /**
   * @return a list of territories for a 2-player game
   */
  public Iterable makeTerrFor2Players();
  /**
   * @return a list of territories for a 3-player game
   */
  public Iterable makeTerrFor3Players();
  /**
   * @return a list of territories for a 4-player game
   */
  public Iterable makeTerrFor4Players();
  /**
   * @return a list of territories for a 5-player game
   */
  public Iterable makeTerrFor5Players();
}
