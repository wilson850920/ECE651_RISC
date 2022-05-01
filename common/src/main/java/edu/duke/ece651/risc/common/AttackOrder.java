package edu.duke.ece651.risc.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * Represents an order which attacks a territory on a RISC Map
 */
public class AttackOrder extends BasicOrder {

  public AttackOrder(Player sender, Territory source, Territory target, HashMap<Integer, Integer> unitsMap) {
    super(sender, source, target, unitsMap);
    super.message = null;
  }

  /**
   * Rolls a 20 sided dice and returns the resulting number
   *
   * @return result of the dice roll
   */
  protected int rollDice() {
    Random rand = new Random();
    return 1 + rand.nextInt(20);
  }

  /**
   * Resolves combat changes of the attack order using dice rolls  
   *
   * @param attackUnits is a sorted list of attacking units (defined by int bonus)
   * @param targetUnits is a sorted list of defending units (defined by int bonus)
   * @throws IOException if information about the units lost in the attack cannot be displayed 
   */
  protected void resolveCombat(ArrayList<Integer> attackUnits, ArrayList<Integer> targetUnits) throws IOException {
    ByteArrayOutputStream attackUnitsLost = new ByteArrayOutputStream();
    ByteArrayOutputStream targetUnitsLost = new ByteArrayOutputStream();
    for (Integer i : attackUnits) { // Remove attacking units from source
      source.removeUnit(i);
    }
    while (!(attackUnits.isEmpty() || targetUnits.isEmpty())) {
      int attackBonus = attackUnits.get(attackUnits.size() - 1);
      int targetBonus = targetUnits.get(targetUnits.size() - 1);
      int sourceRoll = rollDice() + attackBonus;
      int targetRoll = rollDice() + targetBonus;
      if (targetRoll >= sourceRoll) {
        UnitDisplayInfo uInfo = new TextUnitDisplayInfo(attackBonus);
        uInfo.displayUnit(attackUnitsLost);
        attackUnitsLost.write(" ".getBytes());
        attackUnits.remove(attackBonus);
        
      } else {
        UnitDisplayInfo uInfo = new TextUnitDisplayInfo(targetBonus);
        uInfo.displayUnit(targetUnitsLost);
        targetUnitsLost.write(" ".getBytes());
        target.removeUnit(targetBonus);
        targetUnits.remove(targetBonus);
      }
    }
    String unitsLost = "\n\tAttacking Units Lost: " + attackUnitsLost.toString() + "\n\tDefending Units Lost: " + targetUnitsLost.toString(); 
    if (attackUnits.isEmpty()) {
      this.message = this.sender.getName() + " attacked from " + source.getName() + " into " + target.getName()
              + " but the defense held strong" + unitsLost;
      return;
    }
    if (targetUnits.isEmpty()) {
      //If the territory is cloaked and gained by attacker, reset cloaking
      if(this.target.isCloaked()){
        this.target.resetCloaking();
      }
      this.message = this.sender.getName() + " attacked from " + source.getName() + " into " + target.getName()
              + " and was victorious" + unitsLost;
      this.target.setOwner(this.sender.getName());
      return;
    }
  }

  /**
   * Executes the logic of the AttackOrder on its source and target Territories
   * Sets order message to display outcome of the order
   * 
   * @param ruleChecker
   */
  @Override
  public void orderAction(OrderRuleChecker ruleChecker) {
    String check = ruleChecker.checkRule(this);
    if (check != null) {
      this.message = this.sender.getName() + " tried to attack from " + source.getName() + " into " + target.getName()
          + " but failed: " + check;
      return;
    }
    this.sender.spendResources(this.getNumUnits()); // cost of attack removed from sender
    ArrayList<Integer> attackUnits = new ArrayList<Integer>();
    for (Integer bonus : this.unitsMap.keySet()) {
      for (int i = 0; i < this.unitsMap.get(bonus); i++) {
        attackUnits.add(bonus);
      }
    }
    ArrayList<Integer> targetUnits = new ArrayList<Integer>();
    Iterator<Unit> it = target.getUnits();
    while (it.hasNext()) {
      Unit currUnit = it.next();
      targetUnits.add(currUnit.getBonus());
    }
    Collections.sort(attackUnits);
    Collections.sort(targetUnits);
    try {
    resolveCombat(attackUnits, targetUnits);
    } catch (IOException e) {
      this.message = this.message + "Unit Display Error";
    }
  }

  /**
   * Format in which an order should be converted to string
   * 
   * @return details of an order in form of string
   */
  @Override
  public String toString() {
    String s = "Type: Attack, Source: " + source.getName() + ", Target: " + target.getName();
    return s;
  }

}
