package edu.duke.ece651.risc.common;

import java.util.*;

public class SpyMoveOrderRuleChecker extends OrderRuleChecker {
  private HashMap<String, String> territoryOwners = new HashMap<String, String>();

  public SpyMoveOrderRuleChecker(HashSet<Territory> territories) {
    super(territories);
    this.territoryOwners = this.createOwnerNameMap();
  }

  // map of territory names and owners from the game up.
  private HashMap<String, String> createOwnerNameMap() {
    HashMap<String, String> map = new HashMap<String, String>();
    for (Territory t : this.territories) {
      map.put(t.getName(), t.getOwner());
    }
    return map;
  }

  // function to fetch actual owner name of a territory from game map
  private String getActualOwner(Territory t) {
    return this.territoryOwners.get(t.getName());
  }

  @Override
  public String checkRule(Order order) {
    Player p = order.getSender();
    Territory source = order.getSource();
    Territory target = order.getTarget();
    if (!spyExists(p, source)) {
      return "Invalid order. Spy not yet dispatched at the source.";
    }
    if (!targetSenderValidation(target, p) || !sourceSenderValidation(source, p)) {
      if (!source.hasNeighbor(target)) {
        return "Invalid order. Cannot move spy to enemy planet that's not adjacent.";
      }
      if (target.getSize() > p.getResources()) {
        return " Invalid order. Player doesn't have enough credits to move spy";
      }
      return String.valueOf(target.getSize());
    }
    if (sourceSenderValidation(source, p) && targetSenderValidation(target, p)) {
      if (!checkConnectivity(source, target)) {
        return "Invalid order. No path exists between planets to move the spy.";
      }
      int minCost = minPathCost(source, target);
      if (minCost > p.getResources()) {
        return " Invalid order. Player doesn't have enough credits to move spy";
      }
      return String.valueOf(minPathCost(source, target));
    }
    return null;
  }

  private boolean spyExists(Player p, Territory source) {
    Iterator<SpyUnit> it = p.spyIterator();
    while (it.hasNext()) {
      SpyUnit spy = it.next();
      if (spy.getLocation().getName().equals(source.getName()))
        return true;
    }
    return false;
  }

  /**
   * A modified BFS to find target starting from source by visiting only sender
   * owned territories.
   *
   * @param source is the source Territory (start of the path)
   * @param target is the target Territory (end of the path)
   * @return boolean true if a valid path was found, false if not
   */
  private boolean checkConnectivity(Territory source, Territory target) {
    Queue<Territory> queue = new LinkedList<Territory>();
    HashSet<Territory> visited = new HashSet<Territory>();
    queue.add(source);
    visited.add(source);
    while (!queue.isEmpty()) {
      Territory current = queue.remove();
      if (current.getName().equals(target.getName())) {
        return true;
      }
      visited.add(current);
      Iterator<Territory> it = current.getNeighbors();
      while (it.hasNext()) {
        Territory t = it.next();
        if (!visited.contains(t) && !queue.contains(t)) {
          if (getActualOwner(source).equals(getActualOwner(t))) {
            queue.add(t);
          } else {
            visited.add(t);
          }
        }
      }
    }
    return false;
  }

  protected class Path implements Comparable<SpyMoveOrderRuleChecker.Path> {
    public final ArrayList<Territory> territories;
    public Territory last;
    public int cost;

    public Path() {
      this.territories = new ArrayList<Territory>();
      this.cost = 0;
      this.last = null;
    }

    public Path(SpyMoveOrderRuleChecker.Path p) {
      this.territories = p.territories;
      this.cost = p.cost;
      this.last = p.last;
    }

    public void add(Territory t) {
      this.territories.add(t);
      this.cost += t.getSize();
      this.last = t;
    }

    @Override
    public int compareTo(SpyMoveOrderRuleChecker.Path p) {
      return this.cost - p.cost;
    }
  }

  /**
   * Uses Dijkstra's Algorithm to find the minimum cost path between target
   * starting from source by visiting only sender owned territories.
   *
   * @param source is the source Territory (start of the path)
   * @param target is the target Territory (end of the path)
   * @return int is the cost of the minimum cost path between source and target,
   *         -1 if no path exists
   */
  protected int minPathCost(Territory source, Territory target) {
    PriorityQueue<SpyMoveOrderRuleChecker.Path> queue = new PriorityQueue<SpyMoveOrderRuleChecker.Path>();
    HashSet<Territory> visited = new HashSet<Territory>();
    SpyMoveOrderRuleChecker.Path startPath = new SpyMoveOrderRuleChecker.Path();
    startPath.add(source);
    queue.add(startPath);
    while (!queue.isEmpty()) {
      SpyMoveOrderRuleChecker.Path currentPath = queue.remove();
      Territory currentTerritory = currentPath.last;
      if (currentTerritory.getName().equals(target.getName())) {
        return currentPath.cost;
      }
      if (!visited.contains(currentTerritory)) {
        visited.add(currentTerritory);
        Iterator<Territory> it = currentTerritory.getNeighbors();
        while (it.hasNext()) {
          Territory t = it.next();
          if (getActualOwner(source).equals(getActualOwner(t))) {
            SpyMoveOrderRuleChecker.Path newPath = new SpyMoveOrderRuleChecker.Path(currentPath);
            newPath.add(t);
            queue.add(newPath);
          }
        }
      }
    }
    return -1;
  }
}
