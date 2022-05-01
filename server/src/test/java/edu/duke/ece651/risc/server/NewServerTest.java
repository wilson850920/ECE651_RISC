package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.risc.common.Player;
import edu.duke.ece651.risc.common.Territory;

public class NewServerTest {
  public static NewServer serverFactory(int portNumber, int numOfPlayer) throws IOException {
    ServerSocket serverSocket = new ServerSocket(portNumber);
    HashMap<String, ObjectInputStream> playerInStreamMap = new HashMap<String, ObjectInputStream>();
    HashMap<String, ObjectOutputStream> playerOutStreamMap = new HashMap<String, ObjectOutputStream>();
    HashMap<String, Player> stringPlayerMap = new HashMap<String, Player>();
    HashMap<String, Boolean> playerInGameOrLoseMap = new HashMap<String, Boolean>();
    ArrayList<Territory> initTerritories = new ArrayList<Territory>();
    BasicMap gameMap = new BasicMap();
    NewServer ns = new NewServer(1, 1, numOfPlayer, portNumber, serverSocket, playerInStreamMap, playerOutStreamMap,
        stringPlayerMap, playerInGameOrLoseMap, initTerritories, gameMap);
    return ns;
  }

  @Test
  public void test_newServer() throws IOException {
    NewServer ns = serverFactory(4000, 2);

  }

  @Test
  public void test_threadRun() throws IOException {
    NewServer ns = serverFactory(4001, 2);
    Thread t = new Thread(ns);
    t.start();
  }

}
