
package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import edu.duke.ece651.risc.common.*;

/**
 * Basic implementation of the Map interface
 */
public class BasicMap implements Map {
  public HashSet<Player> players;
  public HashSet<Territory> territories;

  /**
   * Constructs a basic map using a list of players in the game
   *
   * @param newPlayers is a list of players to add to the map
   * @throws IllegalArguementException if a player cannot be added to the game
   */
  public BasicMap(Player... newPlayers) throws IllegalArgumentException {
    this.players = new HashSet<Player>();
    this.territories = new HashSet<Territory>();
    for (Player p : newPlayers) {
      addPlayer(p);
      Iterator<Territory> it = p.getTerritories();
      while (it.hasNext()) {
        Territory t = it.next();
        this.territories.add(t);
      }
    }
  }

  /**
   * Constructs a basic map using a list of players in the game
   *
   * @param newPlayers is a list of players to add to the map
   * @throws IllegalArguementException if a player cannot be added to the game
   */
  public BasicMap(Iterable<Player> newPlayers) throws IllegalArgumentException {
    this.players = new HashSet<Player>();
    this.territories = new HashSet<Territory>();
    for (Player p : newPlayers) {
      addPlayer(p);
      Iterator<Territory> it = p.getTerritories();
      while (it.hasNext()) {
        Territory t = it.next();
        this.territories.add(t);
      }
    }
  }

  /**
   * Adds a player to the list of players inside the Map object
   *
   * @param newPlayer is the Player that is being added to the map
   * @throws IllegalArguementException if the player is already in the Map
   */
  @Override
  public void addPlayer(Player newPlayer) throws IllegalArgumentException {
    if (!players.add(newPlayer)) {
      throw new IllegalArgumentException("galaxy already contains player: " + newPlayer.getName());
    }
  }

  /**
   * Removes a selected player from the list of players inside the Map object
   *
   * @param targetPlayer is the Player that is being removed from the map
   * @throws IllegalArgumentException if the Map does not contain the given player
   */
  @Override
  public void removePlayer(Player targetPlayer) throws IllegalArgumentException {
    if (!players.remove(targetPlayer)) {
      throw new IllegalArgumentException("galaxy does not contain player: " + targetPlayer.getName());
    }
  }

  /**
   * Checks if the map contains a selected player
   *
   * @param targetPlayer is the Player that the map is being checked for
   * @return Boolean true if the map contains the player and false if not
   */
  @Override
  public Boolean hasPlayer(Player targetPlayer) {
    return players.contains(targetPlayer);
  }

  /**
   * Gets the display information of a Map object
   *
   * @return MapDisplayInfo object containing info that describes a Map
   * @throws IOException if the display info could not be created
   */
  @Override
  public MapDisplayInfo getDisplayInfo() throws IOException {
    return new UIMapDisplayInfo(players);
  }

  /**
   * Executes list of move orders from the players
   * 
   * @param orders is the list of MoveOrders
   */
  protected void executeMoveOrders(ArrayList<Order> orders) {
    OrderRuleChecker moveRuleChecker = new MoveOrderRuleChecker(this.territories);
    for (Order o : orders) {
      o.orderAction(moveRuleChecker);
    }
  }

  /**
   * Executes list of move orders from the players
   *
   * @param orders is the list of MoveOrders
   */
  protected void executeSpyUpgradeOrders(ArrayList<Order> orders) {
    OrderRuleChecker spyUpgradeRuleChecker = new SpyUnitUpgradeRuleChecker();
    for (Order o : orders) {
      o.orderAction(spyUpgradeRuleChecker);
    }
  }

  /**
   * Executes list of move orders from the players
   *
   * @param orders is the list of MoveOrders
   */
  protected void executeSpyMoveOrders(ArrayList<Order> orders) {
    OrderRuleChecker spyMoveRuleChecker = new SpyMoveOrderRuleChecker(this.territories);
    for (Order o : orders) {
      o.orderAction(spyMoveRuleChecker);
    }
  }

  /**
   * Executes list of unit upgrade orders from the players
   * 
   * @param orders is the list of UnitUpgradeOrders
   */
  protected void executeUnitUpgradeOrders(ArrayList<Order> orders) {
    OrderRuleChecker ruleChecker = new UnitUpgradeRuleChecker();
    for (Order o : orders) {
      o.orderAction(ruleChecker);
    }
  }

  protected void executeCloakTerritoryOrders(ArrayList<Order> orders) {
    OrderRuleChecker ruleChecker = new CloakTerritoryOrderRuleChecker();
    for (Order o : orders) {
      o.orderAction(ruleChecker);
    }
  }

  /**
   * Executes list of technology upgrade orders from the players
   * 
   * @param orders is the list of TechnologyUpgradeOrders
   */
  protected void executeTechUpgradeOrders(ArrayList<Order> orders) {
    OrderRuleChecker ruleChecker = new TechnologyUpgradeRuleChecker();
    for (Order o : orders) {
      o.orderAction(ruleChecker);
      // resolveOwnerTechChanges(o);
    }
  }

  /**
   * Executes list of unlock cloak orders from the players
   * 
   * @param orders is the list of UnlockCloakOrders
   */
  protected void executeUnlockCloakOrders(ArrayList<Order> orders) {
    OrderRuleChecker ruleChecker = new UnlockCloakRuleChecker();
    for (Order o : orders) {
      o.orderAction(ruleChecker);
      // resolvePlayerCloakingChanges(o);
    }
  }

  private void resolvePlayerCloakingChanges(Order order) {
    for (Player p : players) {
      if (order.getSender().getName().equalsIgnoreCase(p.getName())) {
        if (order.getSender().playerCanCloak()) {
          p.enableCloakAbility();
          p.spendResources(100);
        }
      }
    }
  }

  /**
   * Sorts orders so that attacks by by one player on the same territory are next
   * to eachother in the order list
   *
   * @param orders is the list of orders
   * @return ArrayList<Order> is the sorted list orders
   */
  protected ArrayList<Order> combineAttacks(ArrayList<Order> orders) {
    ArrayList<Order> sortedOrders = new ArrayList<Order>();
    for (Order first : orders) {
      for (Order second : orders) {
        if (first != second) {
          if ((first.getSender() == second.getSender()) && (first.getTarget() == second.getTarget())) {
            if (!sortedOrders.contains(second)) {
              sortedOrders.add(second);
            }
          }
        }
        if (!sortedOrders.contains(first)) {
          sortedOrders.add(first);
        }
      }
    }
    return sortedOrders;
  }

  /**
   * Sorts orders so that attacks by two players on eachothers territories are
   * next to eachother in the order list
   *
   * @param orders is the list of orders
   * @return ArrayList<Order> is the sorted list orders
   */
  protected ArrayList<Order> concurrentAttacks(ArrayList<Order> orders) {
    ArrayList<Order> sortedOrders = new ArrayList<Order>();
    for (Order first : orders) {
      for (Order second : orders) {
        if (first != second) {
          if ((first.getSource() == second.getTarget()) && (first.getTarget() == second.getSource())) {
            if (!sortedOrders.contains(second)) {
              sortedOrders.add(second);
            }
          }
        }
        if (!sortedOrders.contains(first)) {
          sortedOrders.add(first);
        }
      }
    }
    return sortedOrders;
  }

  /**
   * Checks a given order for changes in the territory owner and updated the map
   *
   * @param order is the order being checked
   */
  protected void resolveOwnerChanges(Order order) {
    if (order.getSender().getName().equals(order.getTarget().getOwner())) {
      String newOwner = order.getSender().getName();
      for (Territory t : this.territories) {
        String targetName = order.getTarget().getName();
        if (t.getName().equals(targetName)) {
          for (Player p : this.players) {
            if (p.getName().equals(newOwner) && (!p.ownsTerritory(t))) {
              p.addTerritory(t);
            }
            if (p.ownsTerritory(t) && !p.getName().equals(newOwner)) {
              p.removeTerritory(t);
            }
          }
        }
      }
    }
  }

  /**
   * Checks a given order for changes in the sender and updates that player object
   *
   * @param order is the order being checked
   */
  protected void resolveOwnerTechChanges(Order order) {
    for (Player p : players) {
      if (order.getSender().getName().equalsIgnoreCase(p.getName())) {
        p.setMaxTechLevel(order.getSender().getMaxTechLevel());
      }
    }
  }

  /**
   * Executes the list of AttackOrders on the Map
   *
   * @param orders is the list of AttackOrders
   */
  protected void executeAttackOrders(ArrayList<Order> orders) {
    OrderRuleChecker attackRuleChecker = new AttackOrderRuleChecker();
    ArrayList<Order> sortedOrders = orders;
    sortedOrders = combineAttacks(sortedOrders);
    sortedOrders = concurrentAttacks(sortedOrders);
    int i;
    for (i = 0; i < sortedOrders.size() - 1; i++) {
      Order order = sortedOrders.get(i);
      Order next_order = sortedOrders.get(i + 1);
      if (checkIfSwap(order, next_order)) { // handles concurrent attacks
        String message1 = order.getTarget().getName() + " was captured by " + order.getSender().getName();
        String message2 = order.getSource().getName() + " was captured by " + order.getTarget().getOwner();
        sortedOrders.get(i).setMessage(message1);
        sortedOrders.get(i + 1).setMessage(message2);
        swapTerritories(order.getSource(), order.getTarget());
        resolveOwnerChanges(order);
        resolveOwnerChanges(next_order);
        i++;
        continue;
      }
      order.orderAction(attackRuleChecker);
      resolveOwnerChanges(order);
    }
    if (i == sortedOrders.size() - 1) {
      Order order = sortedOrders.get(i);
      order.orderAction(attackRuleChecker);
      resolveOwnerChanges(order);
    }
  }

  /**
   * Checks each spy in the game for their revolution status and progresses
   * accordingly
   *
   * @param spy is the SpyUnit whos revolution stage is being processed
   * @return boolean true if the reovlution was caught, false if not
   */
  protected boolean checkRevolution(SpyUnit spy) {
    Random r = new Random();
    float discoveryChance = 0;
    if (spy.revolutionStage() == 1) {
      discoveryChance = (3 / (2*(float) spy.getFunding())) * spy.getLocation().getNumberOfUnits();
    } else if (spy.revolutionStage() == 2) {
      discoveryChance = (1 / ( ((float) spy.getFunding()))) * spy.getLocation().getNumberOfUnits();
    } else if (spy.revolutionStage() == 3) {
      discoveryChance = (1 / ((2) * ((float) spy.getFunding()))) * spy.getLocation().getNumberOfUnits();
    }
    if (discoveryChance >= 100 || r.nextFloat() <= discoveryChance) {
      return true;
    }
    return false;
  }

  /**
   * Stops a revolution of a spy that has been caught
   *
   * @param spy is the SpyUnit whos revolution has been caught
   * @param the currentTurn of the game
   */
  protected void stopRevolution(SpyUnit spy, Turn currentTurn) {
    Player owner = spy.getOwner();
    String message = "A spy from " + owner.getName() + " was caught trying to start a rebellion in "
        + spy.getLocation().getName() + " and has been executed for treason";
    currentTurn.addOrderMessage(message);
    owner.removeSpy(spy);
  }

  /**
   * Executes a successful revolution of a spy
   *
   * @param spy is the SpyUnit whos revolution has finished
   * @param the currentTurn of the game
   */
  protected void executeRevolution(SpyUnit spy, Turn currentTurn) {
    Player owner = spy.getOwner();
    Territory location = spy.getLocation();
    String message = "A rebellion has crippled " + location.getName() + " and " + location.getOwner()
        + " has lost control of the planet! " + owner.getName() + " has mysteriously taken control of "
        + location.getName() + " with minimal resistance...";
    for (Player p : this.players) {
      if (p.ownsTerritory(location)) {
        p.removeTerritory(location);
      }
    }
    owner.addTerritory(location);
    location.setOwner(owner.getName());
    spy.resetRevolution();
    currentTurn.addOrderMessage(message);
  }

  /**
   * Resolves the progression of Revolutions in the game
   *
   * @param currentTurn is the current turn of the game
   */
  protected void resolveRevolutions(Turn currentTurn) {
    System.out.println("Executing Revolution Orders!");
    HashSet<SpyUnit> spiesFromOrders = new HashSet<SpyUnit>();
    // Execute Revolution Orders
    OrderRuleChecker revolutionRuleChecker = new RevolutionOrderRuleChecker();
    for (Order o : currentTurn.getRevolutionOrders()) {
      System.out.println("Getting Revolution Orders");
      o.orderAction(revolutionRuleChecker);
      spiesFromOrders.add(((RevolutionOrder) o).getSpy());
      currentTurn.addOrderMessage(o.orderMessage());
    }
    System.out.println("Resolving Revolutions!");
    // Update all spies in map
    for (Player p : this.players) {
      ArrayList<SpyUnit> spiesToBeDeleted = new ArrayList<>();
      Iterator<SpyUnit> it = p.spyIterator();
      while (it.hasNext()) {
        SpyUnit spy = it.next();
        if (!spiesFromOrders.contains(spy) && spy.revolutionStage() != 0) {
          if (checkRevolution(spy)) {
            spiesToBeDeleted.add(spy);
//            stopRevolution(spy, currentTurn);
          } else if (spy.revolutionStage() == 3) {
            executeRevolution(spy, currentTurn);
          } else {
            spy.progressRevolution();
            System.out.println("A revolution from a spy in " + spy.getLocation().getName()
                + " progressed to the next stage! Stage : " + spy.revolutionStage());
          }
        }
      }
      for(SpyUnit s: spiesToBeDeleted){
        stopRevolution(s, currentTurn);
      }
    }
  }

  /**
   * Resolves the cloaking of territories as game progresses each territory is
   * resolved after all the orders have been resolved/executed
   */
  private void resolveTerritoryCloaking(ArrayList<Order> orders) {
    ArrayList<Territory> territoriesInTurn = new ArrayList<>();
    for( Order o: orders){
      territoriesInTurn.add(o.getSource());
    }
    for (Territory t : this.territories) {
      if(!territoriesInTurn.contains(t)){
        t.resolveCloaking();
      }

    }
  }

  /**
   * Checks if two orders signify concurrent attacks
   *
   * @param o1 is the first Order
   * @param o2 is the second Order
   * @return boolean true if the orders siginify concurrent attacks, false if not
   */
  protected boolean checkIfSwap(Order o1, Order o2) {
    if (!(o1.getSource().getName().equals(o2.getTarget().getName()))) {
      return false;
    }
    if (!(o2.getSource().getName().equals(o1.getTarget().getName()))) {
      return false;
    }
    return true;
  }

  /**
   * Removes all units from a Territory and returns them in new list
   *
   * @param t is the Territory being stripped of its units
   * @return ArrayList<Unit> a new list containing the units from the territory
   */
  protected ArrayList<Unit> stripUnits(Territory t) {
    ArrayList<Unit> units = new ArrayList<Unit>();
    Iterator<Unit> it = t.getUnits();
    while (it.hasNext()) {
      Unit u = it.next();
      units.add(u);
    }
    for (Unit u : units) {
      t.removeUnit(u.getBonus());
    }
    return units;
  }

  /**
   * Swaps the owners and units of two Territories
   *
   * @param t1 is the first Territory
   * @param t2 is the second Territory
   */
  protected void swapTerritories(Territory t1, Territory t2) {
    ArrayList<Unit> t1Units = stripUnits(t1);
    ArrayList<Unit> t2Units = stripUnits(t2);
    for (Unit u : t1Units) {
      t2.addUnit(u.getBonus());
    }
    for (Unit u : t2Units) {
      t1.addUnit(u.getBonus());
    }
    String tempOwner = t2.getOwner();
    t2.setOwner(t1.getOwner());
    t1.setOwner(tempOwner);
  }

  /**
   * Adds units to a specified Territory
   *
   * @param t     is the specificed Territory
   * @param uints is the list of units to be added
   */
  protected void addUnits(Territory t, ArrayList<Unit> units) {
    for (Unit u : units) {
      t.addUnit(u.getBonus());
    }
  }

  /**
   * Deletes units from a specified Territory
   *
   * @param t     is the specificed Territory
   * @param units is the list of units to be removed
   */
  protected void deleteUnits(Territory t, ArrayList<Unit> units) {
    for (Unit u : units) {
      t.removeUnit(u.getBonus());
    }
  }

  /**
   * Adds 1 new unit to each Territory post turn Note: new units start at level 0
   */
  protected void postTurnAddUnit() {
    for (Territory t : this.territories) {
      t.addUnit(0);
    }
  }

  /**
   * Updates the objects in a given set of orders based on a given Turn
   *
   * @param orders is the list of orders used to update map objects
   */
  protected void updateOrders(ArrayList<Order> orders) {
    for (Order o : orders) {
      for (Player p : this.players) {
        if (o.getSender().getName().equals(p.getName())) {
          o.setSender(p);
        }
      }
      if (o instanceof TechnologyUpgradeOrder || o instanceof UnlockCloakOrder)
        continue;
      for (Territory t : this.territories) {
        if (!(o instanceof RevolutionOrder)) {
          if (o.getSource().getName().equals(t.getName())) {
            o.setSource(t);
          }
        }
        if (!(o instanceof UnitUpgradeOrder) && !(o instanceof SpyUpgradeOrder)
            && !(o instanceof CloakTerritoryOrder)) {
          if (o.getTarget().getName().equals(t.getName())) {
            o.setTarget(t);
          }
        }
      }
    }
  }

  /**
   * Updates Territories in the BasicMap based on a given Turn
   *
   * @param currentTurn is the given turn
   */
  protected void updateTurn(Turn currentTurn) {
    updateOrders(currentTurn.getMoveOrders());
    updateOrders(currentTurn.getAttackOrders());
    updateOrders(currentTurn.getUnitUpgradeOrders());
    updateOrders(currentTurn.getRevolutionOrders());
    updateOrders(currentTurn.getSpyUpgradeOrders());
    updateOrders(currentTurn.getSpyMoveOrders());
    updateOrders(currentTurn.getCloakTerritoryOrders());
    updateOrders(currentTurn.getTechUpgradeOrders());
    updateOrders(currentTurn.getUnlockCloakOrders());
  }

  /**
   * Updates resources of each player after a turn is played based on their owned
   * territories
   */
  protected void updateResources() {
    for (Player p : this.players) {
      p.updateResources();
    }
  }

  /**
   * Adds the messages from one type of order to list of messages in turn
   *
   * @param orders      is a list of orders thats messages are to be added
   * @param currentTurn is the given turn
   */
  protected void addOrderMessages(Turn currentTurn, ArrayList<Order> orders) {
    for (Order o : orders) {
      currentTurn.addOrderMessage(o.orderMessage());
    }
  }

  /**
   * Adds all of the the messages containing the resulting information of the turn
   * to the list of order messages in the order they were executed
   *
   * @param currentTurn is the given turn
   */
  protected void updateOrderMessages(Turn currentTurn) {
    addOrderMessages(currentTurn, currentTurn.getUnitUpgradeOrders());
    addOrderMessages(currentTurn, currentTurn.getSpyUpgradeOrders());
    addOrderMessages(currentTurn, currentTurn.getSpyMoveOrders());
    addOrderMessages(currentTurn, currentTurn.getMoveOrders());
    addOrderMessages(currentTurn, currentTurn.getAttackOrders());
    addOrderMessages(currentTurn, currentTurn.getUnlockCloakOrders());
    addOrderMessages(currentTurn, currentTurn.getCloakTerritoryOrders());
    addOrderMessages(currentTurn, currentTurn.getTechUpgradeOrders());
  }

  /**
   * Executes the players orders from a turn of the risc game on the map Compiles
   * list of order messages into currentTurn
   *
   * @param currentTurn is the Turn that is being executed on the map
   * @throws IllegalArgumentException if the currentTurn cannot be executed
   */
  @Override
  public void executeTurn(Turn currentTurn) throws IllegalArgumentException {
    if (currentTurn == null) {
      throw new IllegalArgumentException("currentTurn cannot be null");
    }
    updateTurn(currentTurn);
    executeUnitUpgradeOrders(currentTurn.getUnitUpgradeOrders());
    executeSpyUpgradeOrders(currentTurn.getSpyUpgradeOrders());
    executeSpyMoveOrders(currentTurn.getSpyMoveOrders());
    executeMoveOrders(currentTurn.getMoveOrders());
    executeAttackOrders(currentTurn.getAttackOrders());
    executeUnlockCloakOrders(currentTurn.getUnlockCloakOrders());
    executeCloakTerritoryOrders(currentTurn.getCloakTerritoryOrders());
    executeTechUpgradeOrders(currentTurn.getTechUpgradeOrders());
    resolveTerritoryCloaking(currentTurn.getCloakTerritoryOrders());
    updateOrderMessages(currentTurn);
    resolveRevolutions(currentTurn); // Adds order messages seperatly (should be after updateOrderMessages)
    updateResources();
    postTurnAddUnit();
  }

}
