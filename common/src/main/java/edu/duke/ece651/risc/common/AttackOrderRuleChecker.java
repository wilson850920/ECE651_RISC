package edu.duke.ece651.risc.common;

import java.util.HashMap;

public class AttackOrderRuleChecker extends OrderRuleChecker {

    @Override
    public String checkRule(Order order) {
        Player p = order.getSender();
        Territory source = order.getSource();
        Territory target = order.getTarget();
        if (!sourceSenderValidation(source, p)) {
            return "Invalid order. Player doesn't own source planet";
        } else if (targetSenderValidation(target, p)) {
            return "Invalid order. Can't attack on own planet.";
        } else if (!validUnits(source, order.getUnits())) {
            return "Invalid order. Source doesn't have sufficient units to attack";
        } else if (!checkAdjacency(source, target)) {
            return "Invalid order. Cant attack as source and planet are not adjacent.";
        } else if( order.getNumUnits() > p.getResources()){
            return "Invalid order. Not enough credits to attack.";
        }
        return null;
    }

}
