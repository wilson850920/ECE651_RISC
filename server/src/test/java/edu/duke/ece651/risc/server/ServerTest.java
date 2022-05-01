 package edu.duke.ece651.risc.server;

 import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
 import static org.junit.jupiter.api.Assertions.assertNotNull;
 import static org.junit.jupiter.api.Assertions.assertThrows;
 import static org.junit.jupiter.api.Assertions.fail;

 import java.io.BufferedReader;
 import java.io.ByteArrayOutputStream;
 import java.io.IOException;
 import java.io.ObjectInputStream;
 import java.io.ObjectOutputStream;
 import java.io.StringReader;
 import java.net.ConnectException;
 import java.net.Socket;
 import java.util.HashSet;

 import org.junit.jupiter.api.Disabled;
 import org.junit.jupiter.api.Test;

 import edu.duke.ece651.risc.common.AttackOrder;
 import edu.duke.ece651.risc.common.BasicOrder;
 import edu.duke.ece651.risc.common.BasicOrderList;
 import edu.duke.ece651.risc.common.BasicPlayer;
 import edu.duke.ece651.risc.common.BasicTerritory;
 import edu.duke.ece651.risc.common.MapDisplayInfo;
 import edu.duke.ece651.risc.common.MoveOrder;
 import edu.duke.ece651.risc.common.OrderList;
 import edu.duke.ece651.risc.common.Player;
 import edu.duke.ece651.risc.common.Territory;

 public class ServerTest {
   @Disabled
   @Test
   public void test_socketConnection() throws IOException, ClassNotFoundException, InterruptedException {
     // Server s = new Server();

     Thread th1 = new Thread() {
       @Override()
       public void run() {
         try {
           // Server.main(new String[] { "1641" });
           // Server.numOfPlayers = 3;
           Server.main(new String[0]);
           // Server.numOfPlayers = 3;
         } catch (Exception e) {
         }
       }
     };

     th1.start();
     Thread.sleep(100);
     Socket s1 = new Socket("localhost", 1641);
     Socket s2 = new Socket("localhost", 1641);
     Socket s3 = new Socket("localhost", 1641);
     Player p1 = new BasicPlayer("p1");

     ObjectOutputStream out1 = new ObjectOutputStream(s1.getOutputStream());
     ObjectOutputStream out2 = new ObjectOutputStream(s2.getOutputStream());
     ObjectOutputStream out3 = new ObjectOutputStream(s3.getOutputStream());
     out1.writeObject(p1);
     out2.writeObject(p1);
     out3.writeObject(p1);
     ObjectInputStream in1 = new ObjectInputStream(s1.getInputStream());
     ObjectInputStream in2 = new ObjectInputStream(s2.getInputStream());
     ObjectInputStream in3 = new ObjectInputStream(s3.getInputStream());

     p1 = (Player) in1.readObject();
     p1 = (Player) in2.readObject();
     p1 = (Player) in3.readObject();

     ObjectInputStream in4 = new ObjectInputStream(s1.getInputStream());
     ObjectInputStream in5 = new ObjectInputStream(s2.getInputStream());
     ObjectInputStream in6 = new ObjectInputStream(s3.getInputStream());
     MapDisplayInfo myDisplay = (MapDisplayInfo) in4.readObject();
     myDisplay = (MapDisplayInfo) in5.readObject();
     myDisplay = (MapDisplayInfo) in6.readObject();

     out3.writeObject(p1);
     out2.writeObject(p1);
     out1.writeObject(p1);
     // p1 = (Player) in5.readObject();
     // p1 = (Player) in6.readObject();
   }

  


   @Test
   public void test_getNumberOfPlayers() throws IOException, ClassNotFoundException {
    
     BufferedReader reader = new BufferedReader(new StringReader("haha\n-1\n2\n"));
     Server.getNumofPlayers(reader);
    
   }

   @Test
   void test_buildPlayerMap() throws IOException, ClassNotFoundException, InterruptedException {

     Thread th1 = new Thread() {
       @Override()
       public void run() {
         try {
           Server s = new Server(1641, 1);
           s.buildPlayerMap();

         } catch (Exception e) {
         }
       }
     };
     th1.start();
     Thread.sleep(100);
     Socket s1 = new Socket("localhost", 1641);
     ObjectOutputStream out = new ObjectOutputStream(s1.getOutputStream());
     //ObjectInputStream in = new ObjectInputStream(s1.getInputStream());

   }


   @Test
   public void test_textmapdisplayinfo() throws IOException {
     Server s = new Server(1665, 3);
     Player bp1 = new BasicPlayer("wilson");
     Player bp2 = new BasicPlayer("vicky");
     Player bp3 = new BasicPlayer("sara");
     HashSet<Player> bp_set = new HashSet<Player>();
     ByteArrayOutputStream info = new ByteArrayOutputStream();
     bp_set.add(bp1);
     bp_set.add(bp2);
     bp_set.add(bp3);

     s.stringPlayerMap.put("vicky", bp2);

     BasicMap bm = new BasicMap(bp1, bp2, bp3);
     s.initTerritory(3);

     int terrIndex = 0;
     for (Player bp : bp_set) {
       int count = 0;

       while (terrIndex < s.initTerritories.size() && count < 3) {
         bp.addTerritory(s.getTerritory(terrIndex));
         terrIndex++;
         count++;
       }
     }
     assertThrows(NullPointerException.class, () -> s.closeServer());
   }
   @Disabled
   @Test
   public void test_checklose() throws IOException, ClassNotFoundException {
     Player p1 = new BasicPlayer("wilson");
     Player p2 = new BasicPlayer("harrison");
     Player p3 = new BasicPlayer("jerry");
     Territory t1 = new BasicTerritory("duke", "wilson");
     Territory t2 = new BasicTerritory("chapel", "harrison");
     Territory t3 = new BasicTerritory("durham", "jerry");
     int portNum = 5643;
     Server se = new Server(5000, 3);
     Server se2 = new Server(portNum + 10, 1);
     p1.addTerritory(t1);
     p2.addTerritory(t2);
     p3.addTerritory(t3);
     se.playerInGameOrLoseMap.put("ads", true);
     se.playerInGameOrLoseMap.put("sads", true);
     se.playerInGameOrLoseMap.put("aad", true);
     Player p4 = new BasicPlayer("noTerr");
     se2.playerInGameOrLoseMap.put("da", true);
     assertNotNull(se.checkLose());
     assertNotNull(se2.checkLose());
   }

   @Disabled // TODO - FIX WHEN NEW UNITS AND COMBAT ARE IMPLEMENTED
   @Test
   public void test_executeTurn() throws IOException, ClassNotFoundException, IllegalArgumentException {
     Server newServer = new Server(1655, 2);
     Turn gameTurn = new Turn();
     Player p1 = new BasicPlayer("p1");
     Player p2 = new BasicPlayer("p2");
     // Player p3 = new BasicPlayer("p3");
     Territory t1 = new BasicTerritory("t1", "p1");
     Territory t2 = new BasicTerritory("t2", "p2");
     Territory t3 = new BasicTerritory("t3", "p2"); // error territory for testing not owning by player3
     p1.addTerritory(t1);
     p2.addTerritory(t2);
     p2.addTerritory(t3);
     t1.addNeighbor(t2);
     t2.addNeighbor(t1);
     t3.addNeighbor(t2);
     t2.addNeighbor(t3);
     Map map = new BasicMap(p1, p2);
     BasicOrder attackOrder = new AttackOrder(p1, t1, t2, null);
     BasicOrder moveOrder = new MoveOrder(p2, t2, t3, null);
     // BasicOrder errorMoveOrder = new MoveOrder(p3, t2, t3, 0);
     // BasicOrder errorAttackOrder = new AttackOrder(p1, t1, t3, 0);
     OrderList list = new BasicOrderList();
     list.addOrder(moveOrder);
     list.addOrder(attackOrder);
     // list.addOrder(errorMoveOrder);
     // list.addOrder(errorAttackOrder);
     gameTurn.addOrders(list);
     // indext out of bound error
     map.executeTurn(gameTurn);
   }

   @Test
   public void test_2_player() throws IOException, ClassNotFoundException, InterruptedException {

     Thread th1 = new Thread() {
       @Override()
       public void run() {
         try {
           Server s = new Server(1652, 2);
           s.buildPlayerMap();
           s.initGame();
         } catch (Exception e) {
         }
       }
     };
     th1.start();
     Thread.sleep(100);
     Socket s1 = new Socket("localhost", 1652);
     ObjectOutputStream out1 = new ObjectOutputStream(s1.getOutputStream());
     ObjectInputStream in1 = new ObjectInputStream(s1.getInputStream());
     Socket s2 = new Socket("localhost", 1652);
     ObjectOutputStream out2 = new ObjectOutputStream(s2.getOutputStream());
     ObjectInputStream in2 = new ObjectInputStream(s2.getInputStream());
     out1.writeObject(new BasicPlayer("p1"));
     out2.writeObject(new BasicPlayer("p2"));
     // s.initGame();
   }

   @Test
   public void test_3_player() throws IOException, ClassNotFoundException {
     Server s = new Server(1682, 3);
     s.initGame();
   }

   @Test
   public void test_4_player() throws IOException, ClassNotFoundException {
     Server s = new Server(1683, 4);
     s.initGame();
   }

   @Test
   public void test_5_player() throws IOException, ClassNotFoundException {
     Server s = new Server(1684, 5);
     s.initGame();
   }
 }
