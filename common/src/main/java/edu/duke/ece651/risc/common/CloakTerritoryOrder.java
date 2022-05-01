package edu.duke.ece651.risc.common;

import java.util.HashMap;

public class CloakTerritoryOrder extends BasicOrder{
    /**
     * Constructs a BasicOrder object
     *
     * @param sender   is the player that sent the order
     * @param source   is the territory where the order was sent from
     */
    public CloakTerritoryOrder(Player sender, Territory source) {
        super(sender, source, null, null);
    }

    @Override
    public void orderAction(OrderRuleChecker ruleChecker){
        String check = ruleChecker.checkRule(this);
        if(check != null){
            this.message = "Cloaking of planet " + this.source.getName() + "failed." + check;
            return;
        }
        this.source.cloakTerritory();
        this.sender.spendResources(20);
        this.message = "Successfully cloaked "+ this.source.getName() +". Spent 20 credits. This territory will be cloaked for 2 turns more .";
    }
}
