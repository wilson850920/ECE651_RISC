package edu.duke.ece651.risc.common;

public class UnlockCloakRuleChecker extends OrderRuleChecker{
    @Override
    public String checkRule(Order order) {
        Player p = order.getSender();
        if (p.getMaxTechLevel() < 3){
            return "Your tech Level is not enough to uncloak";
        }
        if (p.playerCanCloak()){
            return "You have enabled yourself to cloak already "+ p.getName();
        }
        if (p.getResources() < 100){
            return "You don't have enough resource";
        }
        return null;
    }

}
