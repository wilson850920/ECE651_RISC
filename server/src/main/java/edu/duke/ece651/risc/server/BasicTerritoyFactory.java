package edu.duke.ece651.risc.server;

import edu.duke.ece651.risc.common.BasicTerritory;
import edu.duke.ece651.risc.common.Territory;
import java.util.ArrayList;

public class BasicTerritoyFactory implements AbstractTerritoryFactory {

  /**
   * Creates a connected graph of Territories for a Map depending on the number of
   * players in a game
   * 
   * @param numberofPlayers is the number of players in the game
   * @return a list of territories for a according to number of players
   */
  public Iterable<Territory> makeTerritory(int numOfPlayers) {
    switch (numOfPlayers) {
    case 2:
      return makeTerrFor2Players();
    case 3:
      return makeTerrFor3Players();
    case 4:
      return makeTerrFor4Players();
    case 5:
      return makeTerrFor5Players();
    }
    return null;
  }

  /**
   * @return a list of territories for a 2-player game
   */
  @Override
  public Iterable<Territory> makeTerrFor2Players() {
    Territory bt1 = new BasicTerritory("Korriban", null, 3);
    Territory bt2 = new BasicTerritory("Mustafar", null, 4);
    Territory bt3 = new BasicTerritory("Death Star", null, 5);
    Territory bt4 = new BasicTerritory("Dagobah", null, 3);
    Territory bt5 = new BasicTerritory("Hoth", null, 4);
    Territory bt6 = new BasicTerritory("Tatooine", null, 5);
    bt1.setNeighbors(bt2, bt4);
    bt2.setNeighbors(bt1, bt4, bt5, bt3);
    bt3.setNeighbors(bt2, bt5);
    bt4.setNeighbors(bt1, bt2, bt5, bt6);
    bt5.setNeighbors(bt2, bt3, bt4, bt6);
    bt6.setNeighbors(bt4, bt5);
    ArrayList<Territory> allTerr = new ArrayList<Territory>();
    allTerr.add(bt1);
    allTerr.add(bt2);
    allTerr.add(bt3);
    allTerr.add(bt4);
    allTerr.add(bt5);
    allTerr.add(bt6);
    return allTerr;
  }

  /**
   * @return a list of territories for a 3-player game
   */
  @Override
  public Iterable<Territory> makeTerrFor3Players() {
    Territory bt1 = new BasicTerritory("Korriban", null, 3);
    Territory bt2 = new BasicTerritory("Mustafar", null, 3);
    Territory bt3 = new BasicTerritory("Death Star", null, 5);
    Territory bt4 = new BasicTerritory("Dagobah", null, 3);
    Territory bt5 = new BasicTerritory("Hoth", null, 4);
    Territory bt6 = new BasicTerritory("Tatooine", null, 5);
    Territory bt7 = new BasicTerritory("Alderaan", null, 3);
    Territory bt8 = new BasicTerritory("Coruscant", null, 4);
    Territory bt9 = new BasicTerritory("Yavin4", null, 5);

    bt1.setNeighbors(bt2, bt4);
    bt2.setNeighbors(bt1, bt3, bt4, bt5);
    bt3.setNeighbors(bt2, bt5, bt7, bt8);
    bt4.setNeighbors(bt1, bt2, bt5, bt6);
    bt5.setNeighbors(bt2, bt3, bt4, bt6, bt8, bt9);
    bt6.setNeighbors(bt5, bt6, bt9);
    bt7.setNeighbors(bt3, bt8);
    bt8.setNeighbors(bt3, bt5, bt7, bt9);
    bt9.setNeighbors(bt5, bt6, bt8);

    ArrayList<Territory> allTerr = new ArrayList<Territory>();
    allTerr.add(bt1);
    allTerr.add(bt2);
    allTerr.add(bt3);
    allTerr.add(bt4);
    allTerr.add(bt5);
    allTerr.add(bt6);
    allTerr.add(bt7);
    allTerr.add(bt8);
    allTerr.add(bt9);
    return allTerr;
  }
  // @Override
  // public Iterable<Territory> makeTerrFor2Players() {
  //   Territory bt1 = new BasicTerritory("Narnia", null);
  //   Territory bt2 = new BasicTerritory("Midkemia", null);
  //   Territory bt3 = new BasicTerritory("Oz", null);
   

  //   bt1.setNeighbors(bt2);
  //   bt2.setNeighbors(bt1, bt3);
  //   bt3.setNeighbors(bt2);
   

  //   ArrayList<Territory> allTerr = new ArrayList<Territory>();
  //   allTerr.add(bt1);
  //   allTerr.add(bt2);
  //   allTerr.add(bt3);
  
  //   return allTerr;
  // }

  /**
   * @return a list of territories for a 4-player game
   */
  @Override
  public Iterable<Territory> makeTerrFor4Players() {
    Territory bt1 = new BasicTerritory("Korriban", null, 3);
    Territory bt2 = new BasicTerritory("Mustafar", null, 4);
    Territory bt3 = new BasicTerritory("Death Star", null, 5);
    Territory bt4 = new BasicTerritory("Dagobah", null, 3);
    Territory bt5 = new BasicTerritory("Hoth", null, 4);
    Territory bt6 = new BasicTerritory("Tatooine", null, 5);
    Territory bt7 = new BasicTerritory("Alderaan", null, 3);
    Territory bt8 = new BasicTerritory("Coruscant", null, 4);
    Territory bt9 = new BasicTerritory("Yavin4", null, 5);
    Territory bt10 = new BasicTerritory("Kamino", null, 3);
    Territory bt11 = new BasicTerritory("Naboo", null, 4);
    Territory bt12 = new BasicTerritory("Kashyyyk", null, 5);

    bt1.setNeighbors(bt2, bt4, bt10);
    bt2.setNeighbors(bt1, bt3, bt4, bt5, bt10);
    bt3.setNeighbors(bt2, bt5, bt7, bt8, bt10, bt11);
    bt4.setNeighbors(bt1, bt2, bt5, bt6);
    bt5.setNeighbors(bt2, bt3, bt4, bt6, bt8, bt9);
    bt6.setNeighbors(bt4, bt5, bt9);
    bt7.setNeighbors(bt3, bt8, bt11, bt12);
    bt8.setNeighbors(bt3, bt5, bt7, bt9, bt12);
    bt9.setNeighbors(bt5, bt6, bt8);
    bt10.setNeighbors(bt1, bt2, bt3, bt11);
    bt11.setNeighbors(bt3, bt7, bt10, bt12);
    bt12.setNeighbors(bt7, bt8, bt11);

    ArrayList<Territory> allTerr = new ArrayList<Territory>();
    allTerr.add(bt1);
    allTerr.add(bt2);
    allTerr.add(bt3);
    allTerr.add(bt4);
    allTerr.add(bt5);
    allTerr.add(bt6);
    allTerr.add(bt7);
    allTerr.add(bt8);
    allTerr.add(bt9);
    allTerr.add(bt10);
    allTerr.add(bt11);
    allTerr.add(bt12);
    return allTerr;
  }

  /**
   * @return a list of territories for a 5-player game
   */

  @Override
  public Iterable<Territory> makeTerrFor5Players() {
    BasicTerritory bt1 = new BasicTerritory("Korriban", null, 3);
    BasicTerritory bt2 = new BasicTerritory("Mustafar", null, 4);
    BasicTerritory bt3 = new BasicTerritory("Death Star", null, 5);
    BasicTerritory bt4 = new BasicTerritory("Dagobah", null, 3);
    BasicTerritory bt5 = new BasicTerritory("Hoth", null, 4);
    BasicTerritory bt6 = new BasicTerritory("Tatooine", null, 5);
    BasicTerritory bt7 = new BasicTerritory("Alderaan", null, 3);
    BasicTerritory bt8 = new BasicTerritory("Coruscant", null, 4);
    BasicTerritory bt9 = new BasicTerritory("Yavin4", null, 5);
    BasicTerritory bt10 = new BasicTerritory("Kamino", null, 3);
    BasicTerritory bt11 = new BasicTerritory("Naboo", null, 4);
    BasicTerritory bt12 = new BasicTerritory("Kashyyyk", null, 5);
    BasicTerritory bt13 = new BasicTerritory("Bespin", null, 3);
    BasicTerritory bt14 = new BasicTerritory("Dantoowin", null, 4);
    BasicTerritory bt15 = new BasicTerritory("Felucia", null, 5);

    bt1.setNeighbors(bt2, bt4, bt10);
    bt2.setNeighbors(bt1, bt3, bt4, bt5, bt10);
    bt3.setNeighbors(bt2, bt5, bt7, bt8, bt10, bt11);
    bt4.setNeighbors(bt1, bt2, bt5, bt6, bt13);
    bt5.setNeighbors(bt2, bt3, bt4, bt6, bt8, bt9);
    bt6.setNeighbors(bt4, bt5, bt9, bt13, bt14);
    bt7.setNeighbors(bt3, bt8, bt11, bt12);
    bt8.setNeighbors(bt3, bt5, bt7, bt9, bt12, bt15);
    bt9.setNeighbors(bt5, bt6, bt8, bt14, bt15);
    bt10.setNeighbors(bt1, bt2, bt3, bt11);
    bt11.setNeighbors(bt3, bt7, bt10, bt12);
    bt12.setNeighbors(bt7, bt8, bt11, bt15);
    bt13.setNeighbors(bt4, bt6, bt14);
    bt14.setNeighbors(bt6, bt9, bt13, bt15);
    bt15.setNeighbors(bt8, bt9, bt12, bt14);
 
    ArrayList<Territory> allTerr = new ArrayList<Territory>();
    allTerr.add(bt1);
    allTerr.add(bt2);
    allTerr.add(bt3);
    allTerr.add(bt4);
    allTerr.add(bt5);
    allTerr.add(bt6);
    allTerr.add(bt7);
    allTerr.add(bt8);
    allTerr.add(bt9);
    allTerr.add(bt10);
    allTerr.add(bt11);
    allTerr.add(bt12);
    allTerr.add(bt13);
    allTerr.add(bt14);
    allTerr.add(bt15);
    return allTerr;
  }
}
