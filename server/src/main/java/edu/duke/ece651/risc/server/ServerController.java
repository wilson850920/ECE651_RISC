
package edu.duke.ece651.risc.server;

import edu.duke.ece651.risc.common.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.*;

public class ServerController {
  public static HashMap<Integer, Thread> threadMap = new HashMap<Integer, Thread>();
  public static HashMap<Integer, NewServer> serverMap = new HashMap<Integer, NewServer>();
  public static int numOfGames = 0;

  static boolean isCreateInput(String input) {
    String[] toCheck = input.split(", ");
    if (toCheck.length != 2) {
      System.err.println("Invalid input, please enter again <port number, number of players>");
      return false;
    }
    try {
      int portNum = Integer.parseInt(toCheck[0]);
      int numOfPlayer = Integer.parseInt(toCheck[1]);
      if (numOfPlayer < 2 || numOfPlayer > 5) {
        System.err.println("Invalid number of players");
        return false;
      }
    } catch (NumberFormatException e) {
      System.err.println("Invalid input, please enter again <port number, number of players>");
      return false;
    }
    return true;
  }

  public static NewServer serverFactory(int portNumber, int numOfPlayer) throws IOException {
    ServerSocket serverSocket = new ServerSocket(portNumber);
    HashMap<String, ObjectInputStream> playerInStreamMap = new HashMap<String, ObjectInputStream>();
    HashMap<String, ObjectOutputStream> playerOutStreamMap = new HashMap<String, ObjectOutputStream>();
    HashMap<String, Player> stringPlayerMap = new HashMap<String, Player>();
    HashMap<String, Boolean> playerInGameOrLoseMap = new HashMap<String, Boolean>();
    ArrayList<Territory> initTerritories = new ArrayList<Territory>();
    BasicMap gameMap = new BasicMap();
    NewServer ns = new NewServer(1, ++numOfGames, numOfPlayer, portNumber, serverSocket, playerInStreamMap,
        playerOutStreamMap,
        stringPlayerMap, playerInGameOrLoseMap, initTerritories, gameMap);
    return ns;
  }

  public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
    boolean firstTime = true;
    while (true) {
      if (firstTime) {
        for (int i = 0; i < 4; i++) {
          NewServer ns = serverFactory(1641 + i, 2 + i);
          Thread t = new Thread(ns);
          threadMap.put(i + 1, t);
          serverMap.put(i + 1, ns);
        }
        for (int i = 0; i < 4; i++) {
          threadMap.get(i + 1).start();
          ;
        }
        firstTime = false;
      }
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      String input = reader.readLine();
      for (int i = 0; i < serverMap.size(); i++) {
        if (threadMap.get(i + 1).isAlive()) {
          System.out.println("\n Thread " + i + " is still running");
        } else {
          System.out.println("\n Thread " + i + " is dead");
        }
      }
    }
  }
}
