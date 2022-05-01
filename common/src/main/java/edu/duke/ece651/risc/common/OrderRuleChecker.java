package edu.duke.ece651.risc.common;

import javax.lang.model.type.IntersectionType;
import java.awt.event.TextEvent;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public abstract class OrderRuleChecker implements Serializable {
    protected HashSet<Territory> territories = new HashSet<Territory>() ;
    public OrderRuleChecker(){
        territories = null;
    }
    public OrderRuleChecker(HashSet<Territory> territories){
        this.territories = territories;
    }
    public abstract String checkRule(Order order);
    public boolean validUnits(Territory source, HashMap<Integer, Integer> unitsMap){
        Map<Integer, Long> sourceUnits = source.getUnitMap();
        for (HashMap.Entry<Integer, Integer> entry : unitsMap.entrySet()) {
            if(sourceUnits.get(entry.getKey()) == null){
                return false;
            }
            if(entry.getValue() > sourceUnits.get(entry.getKey())){
                return false;
            }
        }
        return true;
    }

    public boolean sourceSenderValidation(Territory source , Player sender){
        return sender.ownsTerritory(source);
    }

    public boolean targetSenderValidation(Territory target , Player sender){
        return sender.ownsTerritory(target);
    }

    public boolean checkAdjacency(Territory source, Territory target){
        return source.hasNeighbor(target);
    }
}
