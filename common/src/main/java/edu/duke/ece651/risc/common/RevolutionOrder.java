package edu.duke.ece651.risc.common;

import java.util.Iterator;

public class RevolutionOrder extends BasicOrder{

  private SpyUnit spy;
  private int funding;
  
  /**
   * Constructs a RevolutionOrder object
   *
   * @param sender is the player who issued the order
   * @param target is the target Territory for the revolution
   * @param funding is the ammount of resoruces spent on the order
   */
  public RevolutionOrder(Player sender, Territory target, int funding) {
    super(sender, null, target, null);
    this.funding = funding;
    this.spy = null; 
  }

  /**
   * Checks if the player has enough resources to conduct the revolution ordered
   *
   * @return booelan true if the player can afford the order, false if not
   */
  protected boolean sufficientFunding() {
    return (this.funding <= this.sender.getResources());
  }

  /**
   * Finds the spy conducting the revolution and sets the it as the spy in the order
   */
  protected void setSpy() {
    Iterator<SpyUnit> it = sender.spyIterator();
    while (it.hasNext()) {
      SpyUnit currSpy = it.next();
      if (currSpy.getLocation().getName() == target.getName()) {
        this.spy = currSpy;
        return;
      }
    } 
  }
  
  /**
   * Executes the revolution order on the terrytory the spy is in 
   * 
   * @param ruleChecker is the rule checker used to determine if the revoution is
   *                    valid
   */
  @Override
  public void orderAction(OrderRuleChecker ruleChecker) {
    String check = ruleChecker.checkRule(this);
    if (check != null) {
      this.message = "Rebellion Failed: " + check;
      System.out.println("Rebellion Failed: " + check);
      return;
    }
    if (!sufficientFunding()) {
      this.message = "Rebellion Failed: not enough credits for funding";
      System.out.println(this.message);
      return;
    }
    setSpy();
    this.sender.spendResources(funding);
    this.spy.setFunding(funding);
    this.spy.progressRevolution();
    this.message = this.sender.getName() + " planted the seeds of a rebellion in " + this.getTarget().getName();
    System.out.println( this.sender.getName() + " planted the seeds of a rebellion in " + this.getTarget().getName());
  }

  /**
   * Gets the spy that is preforming the revolution order
   *
   * @return SpyUnit is the spy that is preforming the revolution order
   */
  public SpyUnit getSpy() {
    return this.spy;
  }

}
