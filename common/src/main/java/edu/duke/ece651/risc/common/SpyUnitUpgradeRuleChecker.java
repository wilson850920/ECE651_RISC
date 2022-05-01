package edu.duke.ece651.risc.common;

import java.util.Map;

public class SpyUnitUpgradeRuleChecker extends OrderRuleChecker{
    @Override
    public String checkRule(Order order) {
        Player p = order.getSender();
        Territory s = order.getSource();
        if(!sourceSenderValidation(s, p)){
            return "Invalid Order. Player doesn't own planet";
        } else if(!canCreateSpy(p,s)){
            return "Invalid Order. Player does not not have units eligible for upgrade to spy.";
        } else if(p.getResources() < 20){
            return "Invalid Order. Player doesn't have enough credits to deploy a spy";
        }
        return null;
    }

    protected boolean canCreateSpy(Player player, Territory t){
        Map<Integer, Long> units = t.getUnitMap();
        boolean result = false;
        if(t.getNumberOfUnits() == units.get(0))
            return result;
        if((units.get(1) != null) && (units.get(1) > 0)){
            t.removeUnit(1);
            result = true;
        } else if((units.get(3) != null) && (units.get(3) > 0)){
            t.removeUnit(3);
            result = true;
        }else if((units.get(5) != null) && (units.get(5) > 0)){
            t.removeUnit(5);
            result = true;
        }else if((units.get(8) != null) && (units.get(8) > 0)){
            t.removeUnit(8);
            result = true;
        }else if((units.get(11) != null) && (units.get(11) > 0)){
            t.removeUnit(11);
            result = true;
        }else if((units.get(15) != null) && (units.get(15) > 0)){
            t.removeUnit(15);
            result = true;
        }
        return result;
    }
}
