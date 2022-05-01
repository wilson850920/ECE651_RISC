package edu.duke.ece651.risc.client;

import edu.duke.ece651.risc.common.*;
import javafx.util.Pair;

import javax.management.monitor.GaugeMonitor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class NewClientController {
    public static HashMap<Integer, ObjectOutputStream> outMap = new HashMap<>();
    public static HashMap<Integer, ObjectInputStream> inMap = new HashMap<>();
    public static HashMap<Integer, Client2> clientMap = new HashMap<>();
    public static HashMap<Integer, Integer> GameToPortNumber = new HashMap<>();
    public static HashMap<Integer, Pair<String, String>> loginDetails = new HashMap<Integer, Pair<String, String>>();
    public static HashMap<Integer, Boolean> gameStatus = new HashMap<>();
    public static int numOfGames = 4;
    private String hostname = "vcm-25468.vm.duke.edu";
  //private String hostname = "vcm-24503.vm.duke.edu";
    // private String hostname = "localhost";
//    private String hostname = "192.168.1.134";
    //private String hostname = "10.194.28.124";

    public NewClientController() {
        GameToPortNumber.put(2, 1641);
        GameToPortNumber.put(3, 1642);
        GameToPortNumber.put(4, 1643);
        GameToPortNumber.put(5, 1644);

        gameStatus.put(2, false);
        gameStatus.put(3, false);
        gameStatus.put(4, false);
        gameStatus.put(5, false);
    }

    public int signUpOrLogin(int gameID, String UID, String password) throws IOException, ClassNotFoundException {
        if (loginDetails.containsKey(gameID)) {
            Pair<String, String> pair = loginDetails.get(gameID);
            if (!pair.getKey().equals(UID) || !pair.getValue().equals(password)) {
                System.out.println("Logging into " + gameID + "  Failed!");
                throw new IllegalArgumentException("Invalid Credentials");
            }
            if (pair.getKey().equals(UID) && pair.getValue().equals(password)) {
                System.out.println("Logging into " + gameID + "  Successful!");
                return -1;
            }
        } else {
            Pair<String, String> pair = new Pair(UID, password);
            loginDetails.put(gameID, pair);
            int portNum = GameToPortNumber.get(gameID);
            Socket clientSocket = new Socket(hostname, portNum);
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            this.outMap.put(gameID, out);
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            this.inMap.put(gameID, in);
            Player player = new BasicPlayer("client_player", 2000);
            Client2 client = new Client2(player);
            this.clientMap.put(gameID, client);
            System.out.println("SignUp for " + gameID + " Successful!");
            client.receivePlayer(inMap.get(gameID));
            UIMapDisplayInfo mapInfo = client.displayCommonMessage(inMap.get(gameID));
            return 1;
        }
        return 0;
    }

    public boolean areUnitsAssigned(Integer gameID) {
        Client2 client = clientMap.get(gameID);
        return client.areUnitsAssigned();
    }

    public void unitsAssignedSuccessfully(Integer gameID) {
        Client2 client = clientMap.get(gameID);
        client.unitsAssignedSuccessfully();
    }

    public void clientSendOrder(Integer gameID, OrderList ordersList) throws IOException {
        Client2 client = clientMap.get(gameID);
        ObjectOutputStream out = outMap.get(gameID);
        client.sendOrders(ordersList, out);
    }

    public MapDisplayInfo recieveUIDisplayInfo(Integer gameID, ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        Client2 client = clientMap.get(gameID);
        MapDisplayInfo mapInfo = client.recieveUIDisplayInfo(in);
        return mapInfo;
    }

    // call assginUnit
    //public void validateAndAssignUnits(Integer gameID, String units, String territoryName) throws IOException {
  public String validateAndAssignUnits(Integer gameID, String units, String territoryName) throws IOException {
        Client2 client = clientMap.get(gameID);
        String t_name = client.validateAndAssignUnits(units, territoryName);
        return t_name;
    }

    public OrderList createOrdersList(Integer gameID, ArrayList<Order> orders) {
        Client2 client = clientMap.get(gameID);
        OrderList list = client.createOrdersList(orders);
        return list;
    }

    public Order createSingleMoveOrder(Integer gameID, String sourceName, String targetName,
            HashMap<Integer, Integer> unitsMap) {
        Client2 client = clientMap.get(gameID);
        Order order = client.createSingleMoveOrder(sourceName, targetName, unitsMap);
        return order;
    }

    public Order createSingleAttackOrder(Integer gameID, String sourceName, String targetName,
            HashMap<Integer, Integer> unitsMap) {
        Client2 client = clientMap.get(gameID);
        Order order = client.createSingleAttackOrder(sourceName, targetName, unitsMap);
        return order;
    }

    public Order createSingleUnitUpgradeOrder(Integer gameID, String territoryName, String currentType,
            String targetType) {
        Client2 client = clientMap.get(gameID);
        Order order = client.createSingleUnitUpgradeOrder(territoryName, currentType, targetType);
        return order;
    }

    public Order createTechnologyUpgradeOrder(Integer gameID) {
        Client2 client = clientMap.get(gameID);
        Order order = client.createTechnologyUpgradeOrder();
        return order;
    }

    public Order createUnlockCloakOrder(Integer gameID) {
        Client2 client = clientMap.get(gameID);
        Order order = client.createUnlockCloakOrder();
        return order;
    }

    public Order createSingleSpyUpgradeOrder(Integer gameID, String territoryName) {
        Client2 client = clientMap.get(gameID);
        Order o = client.createSingleSpyUpgradeOrder(territoryName);
        return o;
    }

    public Order createSingleSpyMoveOrder(Integer gameID, String sourceName, String targetName) {
        Client2 client = clientMap.get(gameID);
        Order order = client.createSingleSpyMoveOrder(sourceName, targetName);
        return order;
    }

    public Order createSingleCloakTerritoryOrder(Integer gameID, String sourceName) {
        Client2 client = clientMap.get(gameID);
        Order order = client.createCloakTerritoryOrder(sourceName);
        return order;
    }

  /**
   * Creates a RevolutionOrder object
   *
   * @param gameId is the game ID
   * @param territoryName is the name of the target territory
   * @param funding is the ammount of resources allocated to the revolution
   * @return Order is the completed RevolutionOrder
   */
  public Order createRevolutionOrder(Integer gameID, String territoryName, String fundingString) throws IOException {
        Client2 client = clientMap.get(gameID);
        int funding = getPositiveInteger(fundingString);
        Order o = client.createRevolutionOrder(territoryName, funding);
        return o;
    }

    public String clientWin(Integer gameID) throws IOException, ClassNotFoundException {
        Client2 client = clientMap.get(gameID);
        ObjectInputStream in = inMap.get(gameID);
        return client.winner(in);

    }

    public void clientReceivePlayer(Integer gameID) throws IOException, ClassNotFoundException {
        Client2 client = clientMap.get(gameID);
        ObjectInputStream in = inMap.get(gameID);
        client.receivePlayer(in);
    }

    public void clientsendPlayer(Integer gameID) throws IOException, ClassNotFoundException {
        Client2 client = clientMap.get(gameID);
        ObjectOutputStream out = outMap.get(gameID);
        client.sendPlayer(out);
    }

    public void clientClose(Integer gameID)
            throws IOException, ClassNotFoundException {
        Client2 client = clientMap.get(gameID);
        ObjectInputStream in = inMap.get(gameID);
        ObjectOutputStream out = outMap.get(gameID);
        client.close(in, out);
    }

    public UIMapDisplayInfo clientDisplayCommonMessage(Integer gameID)
            throws IOException, ClassNotFoundException {
        Client2 client = clientMap.get(gameID);
        ObjectInputStream in = inMap.get(gameID);
        return client.displayCommonMessage(in); // check output to which stream!
    }

    public HashMap<String, Boolean> clientReceiveCheckLoseMap(Integer gameID)
            throws IOException, ClassNotFoundException {
        Client2 client = clientMap.get(gameID);
        ObjectInputStream in = inMap.get(gameID);
        HashMap<String, Boolean> losemap = client.receivechecklosemap(in);
        return losemap;
    }

    public boolean winOrLoseCheck(Integer gameID, HashMap<String, Boolean> losemap){
        Player p = clientMap.get(gameID).getPlayer();
        if ((!losemap.get(p.getName())) && (p.getIngameOrLoseFlag())) {
            p.setIngameOrLoseFlag(false);
            return true;
        } else {
            if (p.getIngameOrLoseFlag()) {
                return false;
            }
        }
        return false;
    }

    public String informLoser(Integer gameID, String loserinput) throws IOException {
        Client2 client = clientMap.get(gameID);
        Player p = client.getPlayer();
        ObjectInputStream in = inMap.get(gameID);
        ObjectOutputStream out = outMap.get(gameID);
            if (loserinput.equals("E")) {
                out.writeObject("disconnect");
                System.out.println("disconnect");
                client.close(in, out);
            } else if (loserinput.equals("W")) {
                out.writeObject("lost and watching");
                return "You lost but you are watching";
            } else {
                out.writeObject("still playing");
            }
        return "You are still playing please wait as server is processing";
    }

    public String clientReceiveTurnSummary(Integer gameID) throws IOException, ClassNotFoundException {
        Client2 client = clientMap.get(gameID);
        ObjectInputStream in = inMap.get(gameID);
        return client.recieveTurnSummary(in);
    }

    public int getPositiveInteger(String s) throws IOException {
        try {
            int count = Integer.parseInt(s);
            if (count <= 0) {
                throw new IllegalArgumentException("A non-positive integer entered. Less than 0");
            }
            return count;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("A non-positive integer entered. Number format exception");
        }

    }
}
