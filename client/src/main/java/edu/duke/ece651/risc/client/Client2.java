package edu.duke.ece651.risc.client;

import edu.duke.ece651.risc.common.*;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Client2 {
    public Player player;

    public Socket clientSocket;
    private HashMap<String, Integer> unitTypesToBonus = new HashMap<String, Integer>();
    private boolean signedUp = false;
    private boolean unitsAssigned = false;

    public Client2(Player player) throws IOException, ClassNotFoundException {
        this.player = player;
        this.unitTypesToBonus.put("A", 0);
        this.unitTypesToBonus.put("B", 1);
        this.unitTypesToBonus.put("C", 3);
        this.unitTypesToBonus.put("D", 5);
        this.unitTypesToBonus.put("E", 8);
        this.unitTypesToBonus.put("F", 11);
        this.unitTypesToBonus.put("G", 15);
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean areUnitsAssigned() {
        return this.unitsAssigned;
    }

    public void unitsAssignedSuccessfully() {
        Iterator<Territory> it = this.player.getTerritories();
        while(it.hasNext()){
            Territory T = it.next();
            if(T.getNumberOfUnits() <= 0)
                this.unitsAssigned = false;
        }
        this.unitsAssigned = true;
    }

    public UIMapDisplayInfo recieveUIDisplayInfo(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // System.out.println("receive turn summary");
        MapDisplayInfo mapInfo = (MapDisplayInfo) in.readObject();
        return (UIMapDisplayInfo) mapInfo;
    }

  //public void validateAndAssignUnits(String units, String territoryName) throws IOException {
  public String validateAndAssignUnits(String units, String territoryName) throws IOException {
        // TODO get territory for Player
        Player thisPlayer = this.getPlayer();
        Iterator<Territory> it = thisPlayer.getTerritories();
        boolean found = false;
        while (it.hasNext()) {
            Territory T = it.next();
            if (T.getName().equalsIgnoreCase(territoryName)) {
                System.out.println("Assigning units to: " + territoryName);
                int u = getPositiveInteger(units);
                while (u > 0) {
                    T.addUnit(0);
                    u--;
                }
                System.out.println("Assigned " + T.getNumberOfUnits()+" units to" + T.getName());
                found = true;
                //break;
                return T.getName();
            }
        }
        if(!found) {
            throw new IllegalArgumentException("You don't own the territory Dumbass!");
        }
        return "";
    }

    public OrderList createOrdersList(ArrayList<Order> orders) {
        OrderList list = new BasicOrderList();
        if (this.player.numberOfTerritories() == 0) {
            return list;
        }
        for (Order o : orders) {
//            System.out.println("ADDING ORDER. SOURCE: "+ o.getSource().getName()+ " TARGET: "+o.getTarget().getName() + " UNITS: "+o.getNumUnits());
            list.addOrder(o);
        }
        return list;
    }

    /**
     * by inputting source, target, and the units to actions
     * the function will return a move order with all the information that was sent
     * into
     * it
     */
    public Order createSingleMoveOrder(String sourceName, String targetName, HashMap<Integer, Integer> unitsMap) {

        Territory source = getTerritory("Source", sourceName);
        Territory target = getTerritory("Target", targetName);
        HashMap<Integer, Integer> units = unitsMap;
//        for(Integer i: unitsMap.keySet()){
//            System.out.println("BONUS TYPE: " + i + " Units count: "+unitsMap.get(i));
//        }
        BasicOrder order = new MoveOrder(this.player, source, target, units);
        return order;
    }

    

    public void sendOrders(OrderList ordersList, ObjectOutputStream out) throws IOException {
        out.writeObject(ordersList);
    }

    /**
     * by inputting source, target, and the units to actions
     * the function will return an attack order with all the information that was
     * sent into
     * it
     */
    public Order createSingleAttackOrder(String sourceName, String targetName, HashMap<Integer, Integer> unitsMap) {
        Territory source = getTerritory("Source", sourceName);
        Territory target = getTerritory("Target", targetName);
        HashMap<Integer, Integer> units = unitsMap;
//        for(Integer i: unitsMap.keySet()){
//            System.out.println("BONUS TYPE: " + i + " Units count: "+unitsMap.get(i));
//        }
        BasicOrder order = new AttackOrder(this.player, source, target, units);
        return order;
    }

    /**
     * by inputting source, current type of unit and target type of unit
     * the function will return an unit upgrade order with all the information that
     * was sent into
     * it
     */
    public Order createSingleUnitUpgradeOrder(String territoryName, String currentType, String targetType) {
        try {
            Territory source = getTerritory("Source", territoryName);
            String currentUnitType = currentType;
            String targetUnitType = targetType;
            BasicOrder order = new UnitUpgradeOrder(this.player, source, this.unitTypesToBonus.get(currentUnitType.toUpperCase()),
                    this.unitTypesToBonus.get(targetUnitType.toUpperCase()));
            return order;
        }catch (NullPointerException E){
            throw new IllegalArgumentException("Please enter all the required details for the order.");
        }
    }

    /**
     * the function will return a technology upgrade order with all the information
     */
    public Order createTechnologyUpgradeOrder() {
        BasicOrder order = new TechnologyUpgradeOrder(this.player);
        return order;
    }

    /**
     * the function will return a UnlockCloak Order with all the information
     */
    public Order createUnlockCloakOrder() {
        BasicOrder order = new UnlockCloakOrder(this.player);
        return order;
    }

    public Order createSingleSpyUpgradeOrder(String territoryName){
        Territory source = getTerritory("Source", territoryName);
        BasicOrder order = new SpyUpgradeOrder(this.player, source);
        return order;
    }

    public Order createSingleSpyMoveOrder(String sourceName, String targetName){
        Territory source = getTerritory("Target", sourceName);
        Territory target = getTerritory("Target", targetName);
        BasicOrder order = new SpyMoveOrder(this.player, source, target);
        return order;
    }


    public Order createCloakTerritoryOrder(String sourceName){
        Territory source = getTerritory("Source", sourceName);
        BasicOrder order = new CloakTerritoryOrder(this.player, source);
        return order;
    }

  /**
   * Creates a RevolutionOrder object
   * 
   * @param territoryName is the name of the target territory for the revolution 
   * @param funding is the ammount of resources allocated to the revolution
   * @return Order is the completed RevolutionOrder object
   */
  public Order createRevolutionOrder(String territoryName, int funding){
        Territory target = getTerritory("Target", territoryName);
        BasicOrder order = new RevolutionOrder(this.player, target, funding);
        return order;
    }

    /**
     * by inputting a source or target string
     * if is source, then check whether do a player own the territory or not
     * if is target, then check whether the territory is a neighbor of the player of
     * not
     *
     * @throw if the condition isn't valid
     */
    public Territory getTerritory(String sourceOrTarget, String territoryName) {
        System.out.println("Entered " + sourceOrTarget +
                ": " + territoryName);
        Territory T;
        if (sourceOrTarget.equals("Source")) {
            T = findSourceTerritory(territoryName);
            if (T == null) {
                throw new IllegalArgumentException(
                        "Invalid source territory. You either don't own the entered territory or the territory doesn't exist.");
            }
            return T;
        } else if (sourceOrTarget.equals("Target")) {
            T = findTargetTerritory(territoryName);
            if (T == null) {
                throw new IllegalArgumentException(
                        "Invalid target territory. The territory doesn't not exist.");
            }
            return T;
        }
        return null;
    }

    public String winner(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // this.in = new ObjectInputStream(this.clientSocket.getInputStream());
        String winner = (String) in.readObject();
        if (winner.equals("true") && this.player.getIngameOrLoseFlag()) {
            System.out.println("You win!");
            return "won";
            //close();
        }
        return null;
    }

    /**
     * this function will check if the input string is a territory of a player
     *
     * @return a territory object correspond to the input string
     */
    public Territory findSourceTerritory(String s) {
        Iterator<Territory> it = this.player.getTerritories();
        while (it.hasNext()) {
            Territory T = it.next();
            if ((T.getName()).equalsIgnoreCase(s))
                return T;
        }
        return null;
    }

    /**
     * this function will check if the input string is a territory of a player
     *
     * @return a territory object correspond to the input string
     *         if the action is move, the target would be a territory own by a
     *         player
     *         if the action is attack, the target would be a territory that is a
     *         neighbor of the player's territory
     *         but not own by the player
     */
    public Territory findTargetTerritory(String s) {
        Iterator<Territory> it = this.player.getTerritories();
        Territory firstTerritory = it.next();
        Stack<Territory> tracker = new Stack<Territory>();
        HashSet<Territory> visited = new HashSet<Territory>();
        visited.add(firstTerritory);
        tracker.push(firstTerritory);
        while (!tracker.empty()) {
            Territory current = tracker.pop();
            if ((current.getName()).equalsIgnoreCase(s)) {
                return current;
            }
            visited.add(current);
            // Getting neighbours that are only owned by the sender
            // TODO - does not check this? ^^^
            Iterator<Territory> neighbors = current.getNeighbors();
            while (neighbors.hasNext()) {
                Territory t = neighbors.next();
                if (!visited.contains(t) && !tracker.contains(t)) {
                    tracker.push(t);
                }
            }
        }
        return null;
    }

    public int getPositiveInteger(String s) throws IOException {
        try {
            int count = Integer.parseInt(s);
            if (count <= 0) {
                throw new IllegalArgumentException("A non-positive integer entered.");
            }
            return count;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("A non-positive integer entered.");
        }

    }

    /**
     * A method that receives player object to the server
     */
    public void receivePlayer(ObjectInputStream in) throws IOException, ClassNotFoundException {
        Player p = (Player) in.readObject();
        this.player = p;
    }

    /**
     * A method that sends player object to the server
     */
    public void sendPlayer(ObjectOutputStream out) throws IOException {
        out.writeObject(this.player);
    }

    /**
     * A method that close all the existing connections and input/output stream
     */
    public void close(ObjectInputStream in, ObjectOutputStream out) throws IOException {
        in.close();
        out.close();
    }

    /**
     * A method that displays entire map received from server
     *
     * @throws InterruptedException
     */
    public UIMapDisplayInfo displayCommonMessage(ObjectInputStream in) throws IOException, ClassNotFoundException {
        MapDisplayInfo myDisplay = (MapDisplayInfo) in.readObject();
        return (UIMapDisplayInfo) myDisplay;
        // myDisplay.displayMap();
    }

    /**
     * receive the hashmap from server which contains the player and the boolean
     * status which represent the status of each player
     * an iterator will then loop through the map and see whether the loser is a new
     * loser or a old loser
     */
    public HashMap<String, Boolean> receivechecklosemap(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        HashMap<String, Boolean> losemap = (HashMap<String, Boolean>) in.readObject();
        return losemap;

    }

    public String recieveTurnSummary(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // System.out.println("receive turn summary");
        String summary = (String) in.readObject();
        return "Summary of latest turn executed:\n" + summary;
    }
}
