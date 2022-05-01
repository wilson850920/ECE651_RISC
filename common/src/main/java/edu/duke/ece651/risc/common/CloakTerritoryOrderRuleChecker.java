package edu.duke.ece651.risc.common;

public class CloakTerritoryOrderRuleChecker extends OrderRuleChecker{
    @Override
    public String checkRule(Order order) {
        Player p = order.getSender();
        Territory territory = order.getSource();
        if(!p.playerCanCloak()){
            return "Invalid order. Player hasn't unlocked cloaking ability yet.";
        } if (territory.isCloaked()){
            return "Invaild Order. Territory already cloaked.";
        } if(!sourceSenderValidation(territory, p)){
            return "Invalid order. Player doesn't own source planet to cloak.";
        } if(p.getResources() < 20){
            return "Invalid order. Player doesn't have enough credits to cloak " + territory.getName()+".";
        }
        return null;
    }
}
