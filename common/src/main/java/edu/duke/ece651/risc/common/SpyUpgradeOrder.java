package edu.duke.ece651.risc.common;

import java.net.PortUnreachableException;
import java.util.HashMap;

public class SpyUpgradeOrder extends BasicOrder{

    public SpyUpgradeOrder(Player sender, Territory source){
        super(sender, source, null, null);
    }

    @Override
    public void orderAction(OrderRuleChecker ruleChecker){
        String check = ruleChecker.checkRule(this);
        if(check != null){
            this.message = "Spy creation Failed: " + check;
            return;
        }
        SpyUnit spy = new SpyUnit(this.sender, this.source);
        this.sender.addSpy(spy);
        this.sender.spendResources(20);
        this.message = this.sender.getName() + " has hired a new spy... watch out!";
    }
}
