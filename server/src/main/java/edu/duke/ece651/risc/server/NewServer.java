
package edu.duke.ece651.risc.server;

import edu.duke.ece651.risc.common.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.*;

public class NewServer implements Runnable {
  public int stage;
  public int serverID;
  public int numOfPlayers;
  public int portNumber;
  public String win;
  public ServerSocket serverSocket;
  public HashMap<String, Boolean> playerInGameOrLoseMap;
  public HashMap<String, ObjectInputStream> playerInStreamMap;
  public HashMap<String, ObjectOutputStream> playerOutStreamMap;
  public HashMap<String, Player> stringPlayerMap;
  public ArrayList<Territory> initTerritories;
  public BasicMap gameMap;

  @Override
  public void run() {
    String welcome;
    if (this.stage == 1) {
      welcome = "\n--------\nWelcome to the RISC game, this is a game for " + this.numOfPlayers
          + " player(s) on port "
          + this.portNumber + " \n--------\n\n";
    } else {
      welcome = "\n--------\nWelcome back to the RISC game, this is a game for " + this.numOfPlayers
          + " player(s) on port "
          + this.portNumber + " \n--------\n\n";
    }
    System.out.println(welcome);
    System.out.println("Starting game with " + numOfPlayers + " players\n--------\n");
    System.out.println("Server starts successfully, waiting for players to connect");
    try {
      if (this.stage == 1) {
        this.buildPlayerMap();
        this.initGame();
        this.sendMapDisplayToPlayer();
        this.updateMap();
        this.sendMapDisplayToPlayer();
      }
      int i = 1;
      while (win.equals("false")) {
        System.out.println("round " + i);
        System.out.println("Wait for players to finish issuing their orders");
        Turn gameTurn = this.collectOrders();
        this.gameMap.executeTurn(gameTurn);
        String turn_summary = this.TurnSummary(gameTurn);
        System.out.println(turn_summary);
        this.sendUpdatedPlayer();
        this.sendTurnSummary(turn_summary);
        this.checkLose();
        this.sendCheckLosemap();
        HashMap<String, String> playerChoseResult = this.recevieString();
        this.removeLoserFromPlayermap(playerChoseResult);
        // thisServer.sendMapDisplayToPlayer();
        try {
          this.checkWin();
          this.sendMapDisplayToPlayer();
        } catch (Exception e) {
          System.out.println(e.getMessage());
          break;
        }
        i++;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      stage = -1;
    }
    System.out.println("Game over~");
    try {
      this.closeServer();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * This is the constructor for Server class
   *
   * @param portNumber is the port number on this server lisenting connection from
   *                   players
   */
  public NewServer(int stage, int serverID, int numOfPlayer, int portNumber, ServerSocket serverSocket,
      HashMap<String, ObjectInputStream> playerInStreamMap,
      HashMap<String, ObjectOutputStream> playerOutStreamMap, HashMap<String, Player> stringPlayerMap,
      HashMap<String, Boolean> playerInGameOrLoseMap, ArrayList<Territory> initTerritories, BasicMap gameMap)
      throws IOException {
    this.stage = stage;
    this.numOfPlayers = numOfPlayer;
    this.serverID = serverID;
    this.portNumber = portNumber;
    this.serverSocket = serverSocket;
    this.playerInStreamMap = playerInStreamMap;
    this.playerOutStreamMap = playerOutStreamMap;
    this.stringPlayerMap = stringPlayerMap;
    this.playerInGameOrLoseMap = playerInGameOrLoseMap;
    this.initTerritories = initTerritories;
    this.win = "false";
    this.gameMap = gameMap;
  }

  /**
   * This method builds all the maps needed for game playing, including socket,
   * input/outputstream
   */
  public void buildPlayerMap() throws IOException {
    for (int i = 0; i < this.numOfPlayers; i++) {
      Socket clientSocket = serverSocket.accept();
      System.out.println("player " + i + " connected!");
      // Pout.println("player " + i + " connected!");
      Player player = new BasicPlayer("player " + String.valueOf(i), 2000);
      player.setAvailableUnits(9);
      // setting up object output & input streams
      ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
      ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
      this.playerInGameOrLoseMap.put(player.getName(), true);
      this.stringPlayerMap.put(player.getName(), player);
      this.playerInStreamMap.put(player.getName(), in);
      this.playerOutStreamMap.put(player.getName(), out);
      this.gameMap.addPlayer(player);
    }
  }

  /**
   * A method that close all the existing connections and input/output stream
   */
  public void closeServer() throws IOException {
    for (String key : this.stringPlayerMap.keySet()) {
      this.playerInStreamMap.get(key).close();
      this.playerOutStreamMap.get(key).close();
    }
    this.serverSocket.close();
  }

  /**
   * this function will initialize the territory and their given neighbors
   * according to the number of players
   * 
   * @param num is the number of players of the game
   * 
   */
  public void initTerritory(int num) {
    BasicTerritoyFactory btf = new BasicTerritoyFactory();
    for (Territory t : btf.makeTerritory(num)) {
//      this.gameMap.territories.add(t);
      this.initTerritories.add(t);
    }
  }

  public Territory getTerritory(int index) {
    return this.initTerritories.get(index);
  }

  /*
   * this method distribute ALL exisiting territories to every player
   */
  public void initGame() throws IOException, ClassNotFoundException {
    switch (this.numOfPlayers) {
      case 2:
        this.initTerritory(2);
        break;
      case 3:
        this.initTerritory(3);
        break;
      case 4:
        this.initTerritory(4);
        break;
      case 5:
        this.initTerritory(5);
        break;
    }

    Iterator<Territory> t = initTerritories.iterator();
    for (Map.Entry<String, Player> entry : this.stringPlayerMap.entrySet()) {

      Player p = entry.getValue();
      int i = 1;

      while (i < 4 && t.hasNext()) { // change back to 4
        Territory toAdd = t.next();
        toAdd.setOwner(p.getName());
        p.addTerritory(toAdd);
        i++;
      }

    }
    for (String s : this.stringPlayerMap.keySet()) {
      ObjectOutputStream out = this.playerOutStreamMap.get(s);
      out.writeObject(this.stringPlayerMap.get(s));
    }

  }

  /*
   * this method sends map to all players
   */
  public void sendMapDisplayToPlayer() throws IOException, ClassNotFoundException {
    System.out.println("sendMapToPlayer");
    for (String key : this.stringPlayerMap.keySet()) {
      ObjectOutputStream out = this.playerOutStreamMap.get(key);
      MapDisplayInfo mapInfo = gameMap.getDisplayInfo();
      out.writeObject(mapInfo);
    }
  }

  /*
   * this method indicate which user's turn to update their territory then updates
   * the player objects in the map
   */

  public void updateMap() throws IOException, ClassNotFoundException {
    System.out.println("Waiting for players to deploy units");
    ArrayList<Player> playersList = new ArrayList<Player>();
    for (String key : this.stringPlayerMap.keySet()) {
      ObjectInputStream in = this.playerInStreamMap.get(key);
      Player updatedPlayer = (Player) in.readObject();
      playersList.add(updatedPlayer);
      this.stringPlayerMap.replace(key, updatedPlayer);
    }
    this.gameMap = new BasicMap(playersList);
    System.out.println("Players finished units deployment");
  }

  /**
   * this method will send the hashmap that contains the player and booleans to
   * the client
   */
  public void sendCheckLosemap() throws IOException, ClassNotFoundException {
    // System.out.println("checklosemap");
    for (String key : this.stringPlayerMap.keySet()) {
      System.out.println(key);
      ObjectOutputStream out = this.playerOutStreamMap.get(key);
      out.writeObject(playerInGameOrLoseMap);
    }
  }

  /**
   * @function checklose() checks if a player has lose the game or not by checking
   *           if the territory list in a player is empty, we can know that the
   *           player loses all the territory and loses the game we then broadcast
   *           a message to inform all the players that a player has lose the game
   */
  public HashMap<String, Boolean> checkLose() throws IOException, ClassNotFoundException {
    for (Player p : this.gameMap.players) {
      if ((p.numberOfTerritories() == 0) && (playerInGameOrLoseMap.get(p.getName()) == true)) {
        playerInGameOrLoseMap.replace(p.getName(), false);
      }
    }
    return playerInGameOrLoseMap;
  }

  /**
   * @function checkwin() checks if a player has win the game or not by checking
   *           if the ingame_or_lose_Map contains only one true in the value
   *           meaning the other are false, which means loses. Then we broadcast a
   *           meesage to inform all the player that a player has win the game
   */
  public void checkWin() throws IOException, ClassNotFoundException, InterruptedException {
    int count = 0;
    for (String key : this.stringPlayerMap.keySet()) {
      ObjectOutputStream out = this.playerOutStreamMap.get(key);
      for (Player p : this.gameMap.players) {
        if (playerInGameOrLoseMap.get(p.getName()) == true) {
          count++;
        }
      }
      if (count == 1) {
        for (Player p : this.gameMap.players) {
          if (playerInGameOrLoseMap.get(p.getName()) == true) {
            System.out.println("Player " + p.getName() + " has won the game :)");
            win = "true";
            out.writeObject(win);
            closeServer();
          }
        }
      }
      out.writeObject(win);
    }
  }

  /**
   * this function collects all the ordr from the clients
   * 
   * @return a turn
   */
  public Turn collectOrders() throws IOException, ClassNotFoundException {
    Turn gameTurn = new Turn();
    for (String key : this.stringPlayerMap.keySet()) {
      ObjectInputStream in = this.playerInStreamMap.get(key);
      OrderList playerOrderList = (OrderList) in.readObject();
      gameTurn.addOrders(playerOrderList);
    }
    return gameTurn;
  }

  public void removeLoserFromPlayermap(HashMap<String, String> result) throws IOException, ClassNotFoundException {
    for (String key : result.keySet()) {
      System.out.println(key + ": " + result.get(key));
      if (result.get(key).equals("disconnect")) {
        // System.out.println(key + " removed");
        this.stringPlayerMap.remove(key);
        this.playerInStreamMap.remove(key);
        this.playerOutStreamMap.remove(key);
      } else if (result.get(key).equals("lost and watching")) {
        for (Player p : this.gameMap.players) {
          if (key.equals(p.getName())) {
            p.setIngameOrLoseFlag(false);
          }
        }
      }
    }
  }

  public String TurnSummary(Turn turn) {
    StringBuilder summary = new StringBuilder();
    for (String message : turn.getOrderMessages()) {
      summary.append(message + "\n");
    }
    return summary.toString();
  }

  /**
   * this function receives the message from client indicating whether a loser
   * decide to watch the game or disconnect the game
   * 
   * @return a hashmap of the player's name and the message
   */
  public HashMap<String, String> recevieString() throws IOException, ClassNotFoundException {
    HashMap<String, String> result = new HashMap<String, String>();
    for (String key : this.stringPlayerMap.keySet()) {
      ObjectInputStream in = this.playerInStreamMap.get(key);
      String receivedString = (String) in.readObject();
      result.put(key, receivedString);
    }
    return result;
  }

  public void sendUpdatedPlayer() throws IOException, ClassNotFoundException {

    // System.out.println("Sending updated player objects");
    for (Player p : this.gameMap.players) {
      if (this.stringPlayerMap.containsKey(p.getName())) {
        ObjectOutputStream out = this.playerOutStreamMap.get(p.getName());
        out.flush();
        out.reset();
        out.writeObject(p);
      }
    }
    // System.out.println("finished");
  }

  public void sendTurnSummary(String summary) throws IOException {
    for (String key : this.stringPlayerMap.keySet()) {
      ObjectOutputStream out = this.playerOutStreamMap.get(key);
      out.writeObject(summary);
    }
  }

  public static boolean isValidPlayerInput(String numplayer) {
    try {
      int num = Integer.parseInt(numplayer);
      if (num > 5 || num < 2) {
        System.out.println("Invalid player number. Please input a number between 2 to 5");
        return false;
      }
    } catch (NumberFormatException e) {
      System.out.println("Invalid player number. Please input a number between 2 to 5");
      return false;
    }
    return true;

  }

  public static int getNumofPlayers(BufferedReader reader) throws IOException, ClassNotFoundException {
    System.out.println("How many players are there going to be in the game?\n");
    while (true) {
      String numplayer = reader.readLine();
      if (isValidPlayerInput(numplayer)) {
        return Integer.parseInt(numplayer);
      }
    }
  }

}
