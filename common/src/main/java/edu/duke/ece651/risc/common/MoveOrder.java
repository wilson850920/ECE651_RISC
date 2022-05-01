package edu.duke.ece651.risc.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * Represents an order which moves units between territories on a RISC Map
 */
public class MoveOrder extends BasicOrder {

  public MoveOrder(Player sender, Territory source, Territory target, HashMap<Integer, Integer> unitsMap) {
    super(sender, source, target, unitsMap);
    super.message = null;
  }

  /**
   * Represents a Path between territories by a list of territories crossed and
   * the total cost of the path (total size)
   */
  protected class Path implements Comparable<Path> {
    public final ArrayList<Territory> territories;
    public Territory last;
    public int cost;

    public Path() {
      this.territories = new ArrayList<Territory>();
      this.cost = 0;
      this.last = null;
    }

    public Path(Path p) {
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
    public int compareTo(Path p) {
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
    PriorityQueue<Path> queue = new PriorityQueue<Path>();
    HashSet<Territory> visited = new HashSet<Territory>();
    Path startPath = new Path();
    startPath.add(source);
    queue.add(startPath);
    while (!queue.isEmpty()) {
      Path currentPath = queue.remove();
      Territory currentTerritory = currentPath.last;
      if (currentTerritory.getName().equals(target.getName())) {
        return currentPath.cost;
      }
      if (!visited.contains(currentTerritory)) {
        visited.add(currentTerritory);
        Iterator<Territory> it = currentTerritory.getNeighbors();
        while (it.hasNext()) {
          Territory t = it.next();
          if (source.getOwner().equals(t.getOwner())) {
            Path newPath = new Path(currentPath);
            newPath.add(t);
            queue.add(newPath);
          }
        }
      }
    }
    return -1;
  }

  /**
   * Checks the order to ensure it is valid and then executes the action of the
   * order message is set to reflect the outcome of the order
   *
   * @param ruleChecker is the rule checker used to check the validity of the
   *                    order
   */
  @Override
  public void orderAction(OrderRuleChecker ruleChecker) {
    String check = ruleChecker.checkRule(this);
    if (check.contains("Invalid order.")) {
      this.message = this.sender.getName() + " tried to move " + this.getNumUnits() + " units from " + source.getName()
          + " into " + target.getName() + " but failed: " + check;
      return;
    }
    int cost = Integer.parseInt(check) * this.getNumUnits();
    if (cost < 0) {
      this.message = this.sender.getName() + " tried to move " + this.getNumUnits() + " units from " + source.getName()
          + " into " + target.getName() + " but failed: No valid path exists";
      return;
    }
    if (cost > this.sender.getResources()) {
      this.message = this.sender.getName() + " tried to move " + this.getNumUnits() + " units from " + source.getName()
          + " into " + target.getName() + " but failed: Not enough credits.";
      return;
    }
    for (Integer bonus : this.unitsMap.keySet()) {
      for (int i = 0; i < this.unitsMap.get(bonus); i++) {
        source.removeUnit(bonus);
        target.addUnit(bonus);
      }
    }
    this.sender.spendResources(cost); // resource cost deducted from sender
    this.message = this.sender.getName() + " moved " + this.getNumUnits() + " units from " + source.getName() + " into "
        + target.getName() + ", costing them " + cost + " credits";
  }

  /**
   * Format in which an order should be converted to string
   *
   * @return details of an order in form of string
   */
  @Override
  public String toString() {
    String s = "Type: Move, Source: " + source.getName() + ", Target: " + target.getName();
    return s;
  }

}
