//package edu.duke.ece651.risc.client;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//import edu.duke.ece651.risc.common.*;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ClientTest {
//
//
//  public Client setUp(String inputData, ByteArrayOutputStream bytes) throws IOException, ClassNotFoundException {
//    int portNum = 1642;
//    Player testPlayer = new BasicPlayer("clientPlayer");
//    BufferedReader input = new BufferedReader(new StringReader(inputData));
//    PrintStream output = new PrintStream(bytes, true);
//    String hostname = "localhost";
//    Client thisClient = new Client(testPlayer, input, output, hostname, portNum,null, null, null);
//    return thisClient;
//  }
//
//  @Disabled
//  @Test
//  public void test_getPlayer() throws IOException, ClassNotFoundException, InterruptedException {
//    int portNum = 1642;
//    ServerSocket ss = new ServerSocket(portNum);
//    // ServerSocket mockServerSocket = mock();
//    Player testPlayer = new BasicPlayer("clientPlayer");
//    InputStreamReader inputStreamReader = new InputStreamReader(System.in);
//    BufferedReader inputReader = new BufferedReader(inputStreamReader);
//    String hostname = "localhost";
//
////    Socket clientSocket = new Socket(hostname, portNum);
////    ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
////    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
//    // ObjectOutputStream out = new ObjectOutputStream(acceptedSocekt.getOutputStream());
//    Client thisClient = new Client(testPlayer, inputReader, System.out, hostname, portNum,null, null, null);
//    //Socket acceptedSocekt = ss.accept();
//    // assertEquals("clientPlayer", thisClient.getPlayer().getName());
//  }
//
//  @Disabled
//  @Test
//  public void test_main() throws IOException, ClassNotFoundException, InterruptedException {
//    int portNum = 1641;
//    ServerSocket ss = new ServerSocket(portNum);
//    // Socket localSocekt = new Socket("localhost", portNum);
//    Player testPlayer = new BasicPlayer("clientPlayer");
//    InputStreamReader inputStreamReader = new InputStreamReader(System.in);
//    BufferedReader inputReader = new BufferedReader(inputStreamReader);
//    Thread th = new Thread() {
//      @Override()
//      public void run() {
//        try {
//          Client.main(new String[0]);
//        } catch (Exception e) {
//
//        }
//      }
//    };
//    th.start();
//    Thread.sleep(100);
//    Socket acceptedSocekt = ss.accept();
//    ObjectOutputStream out = new ObjectOutputStream(acceptedSocekt.getOutputStream());
//    // ObjectInputStream in = new
//    // ObjectInputStream(acceptedSocekt.getInputStream());
//    // Player myDisplay = (BasicPlayer) in.readObject();
//
//    // Socket acceptedSocekt = ss.accept();
//    // Thread.sleep(100);
//    // ObjectInputStream in = new
//    // ObjectInputStream(acceptedSocekt.getInputStream());
//    // Player serverGameplayer = (Player) in.readObject();
//    // assertNotNull(acceptedSocekt);
//  }
//
//  @Test
//  public void test_getOrderType_validOrder() throws IOException, ClassNotFoundException {
//    String inputData = "D\nm\nA";
//    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//    Client client = setUp(inputData, bytes);
//    String prompt = "You are " + client.player.getName() +", what would you like to do?\n(M)ove\n(A)ttack\n(D)one";
//      String s = client.getOrderType();
//      assertEquals("D", s);
//      assertEquals(prompt + "\n", bytes.toString());
//      s = client.getOrderType();
//      assertEquals("M", s);
//    s = client.getOrderType();
//      assertEquals("A", s);
//  }
//
//  @Test
//  public void test_getOrderType_invalidOrder() throws IOException, ClassNotFoundException {
//    String inputData = "1\nD";
//    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//    Client client = setUp(inputData, bytes);
//    String prompt1 = "You are " + client.player.getName() +", what would you like to do?\n(M)ove\n(A)ttack\n(D)one";
//    String prompt2 = "Invalid order type.";
//    String s = client.getOrderType();
//    assertEquals("D", s);
//    assertEquals(prompt1 + "\n" + prompt2 + "\n" + prompt1+ "\n", bytes.toString());
//  }
//  @Test
//  public void test_getOrderType_invalidOrderException() throws IOException, ClassNotFoundException {
//    String inputData = "\na";
//    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//    Client client = setUp(inputData, bytes);
//    String prompt1 = "You are " + client.player.getName() +", what would you like to do?\n(M)ove\n(A)ttack\n(D)one";
//    String prompt2 = "Invalid order type.";
//    String s = client.getOrderType();
//    assertEquals("A", s);
//    assertEquals(prompt1 + "\n" + prompt2 + "\n" + prompt1+ "\n", bytes.toString());
//  }
//
//  @Test
//  public void test_findSource() throws IOException, ClassNotFoundException {
//    String inputData = "p\na";
//    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//    int portNum = 1642;
//    Player p1 = new BasicPlayer("clientPlayer");
//    BufferedReader input = new BufferedReader(new StringReader(inputData));
//    PrintStream output = new PrintStream(bytes, true);
//    String hostname = "localhost";
//    Client client = new Client(p1, input, output, hostname, portNum,null, null, null);
////    Player p2 = new BasicPlayer("p2");
////    Player p3 = new BasicPlayer("p3");
//    Territory t1 = new BasicTerritory("t1", "p1");
//    Territory t2 = new BasicTerritory("t2", "p2");
//    Territory t3 = new BasicTerritory("t3", "p3"); // error territory for testing not owning by player3
//    p1.addTerritory(t1);
//    assertEquals(t1, client.findSourceTerritory("t1"));
//    assertEquals(null, client.findSourceTerritory("t3"));
//
//  }
//
//  @Test
//  public void test_findTarget() throws IOException, ClassNotFoundException {
//    String inputData = "p\na";
//    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//    int portNum = 1642;
//    Player p1 = new BasicPlayer("clientPlayer");
//    BufferedReader input = new BufferedReader(new StringReader(inputData));
//    PrintStream output = new PrintStream(bytes, true);
//    String hostname = "localhost";
//    Client client = new Client(p1, input, output, hostname, portNum,null, null, null);
//    Player p2 = new BasicPlayer("p2");
////    Player p3 = new BasicPlayer("p3");
//    Territory t1 = new BasicTerritory("t1", "p1");
//    Territory t2 = new BasicTerritory("t2", "p2");
//    Territory t3 = new BasicTerritory("t3", "p3"); // error territory for testing not owning by player3
//    p1.addTerritory(t1);
//    p2.addTerritory(t2);
//    p2.addTerritory(t3);
//    t1.addNeighbor(t2);
//    t2.addNeighbor(t1);
//    t2.addNeighbor(t3);
//    t3.addNeighbor(t2);
//    assertEquals(t3, client.findTargetTerritory("t3"));
//    assertEquals(null, client.findTargetTerritory("t4"));
//
//  }
//
//  //@Disabled
//  @Test
//  public void test_getPositiveNumber() throws IOException, ClassNotFoundException {
//
//    String inputData = "a\n-1\n1";
//    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//    Client client = setUp(inputData, bytes);
//    String prompt1 = "Enter Units.";
//    String prompt2 = "Invalid. Please enter a positive integer.";
//    String prompt3 = "Please enter a positive integer";
//    int num = client.getPositiveInteger(prompt1);
//    assertEquals(1, num);
//    assertEquals(prompt1 + "\n" + prompt3 + "\n" + prompt1+ "\n" + prompt2 + "\n" + prompt1+ "\n", bytes.toString());
//  }
//
//  @Test
//  public void test_getTerritorySource() throws IOException, ClassNotFoundException {
//    String inputData = "t0\nt1";
//    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//    int portNum = 1642;
//    Player p1 = new BasicPlayer("clientPlayer");
//    BufferedReader input = new BufferedReader(new StringReader(inputData));
//    PrintStream output = new PrintStream(bytes, true);
//    String hostname = "localhost";
//    Client client = new Client(p1, input, output, hostname, portNum,null, null, null);
//    Territory t1 = new BasicTerritory("t1", "p1");
//    Territory t2 = new BasicTerritory("t2", "p2");
//    Territory t3 = new BasicTerritory("t3", "p3"); // error territory for testing not owning by player3
//    p1.addTerritory(t1);
//    String prompt1 = "Enter name of Source. If it's a source, territory must be owned by you.";
//    String prompt2 = "Invalid source territory. You either don't own the entered territory or the territory doesn't exist.";
//    assertEquals(t1, client.getTerritory("Source"));
//    assertEquals(prompt1+"\n" + prompt2+ "\n"  + prompt1+ "\n", bytes.toString());
//  }
//
//  @Test
//  public void test_getTerritoryTarget() throws IOException, ClassNotFoundException {
//    String inputData = "t0\nt3";
//    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//    int portNum = 1642;
//    Player p1 = new BasicPlayer("clientPlayer");
//    BufferedReader input = new BufferedReader(new StringReader(inputData));
//    PrintStream output = new PrintStream(bytes, true);
//    String hostname = "localhost";
//    Client client = new Client(p1, input, output, hostname, portNum,null, null, null);
//    Player p2 = new BasicPlayer("p2");
////    Player p3 = new BasicPlayer("p3");
//    Territory t1 = new BasicTerritory("t1", "p1");
//    Territory t2 = new BasicTerritory("t2", "p2");
//    Territory t3 = new BasicTerritory("t3", "p3"); // error territory for testing not owning by player3
//    p1.addTerritory(t1);
//    p2.addTerritory(t2);
//    p2.addTerritory(t3);
//    t1.addNeighbor(t2);
//    t2.addNeighbor(t1);
//    t2.addNeighbor(t3);
//    t3.addNeighbor(t2);
//    String prompt1 = "Enter name of Target. If it's a source, territory must be owned by you.";
//    String prompt2 = "Invalid target territory. The territory may not exist or can not be a target as there is no path to the territory.";
//    assertEquals(t3, client.getTerritory("Target"));
//    assertEquals(prompt1+"\n" + prompt2+ "\n"  + prompt1+ "\n", bytes.toString());
//  }
//
//  @Test
//  public void test_createSingleOrder() throws IOException, ClassNotFoundException {
//    String inputData = "t0\nt3";
//    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//    int portNum = 1642;
//    Player p1 = new BasicPlayer("clientPlayer");
//    BufferedReader input = new BufferedReader(new StringReader(inputData));
//    PrintStream output = new PrintStream(bytes, true);
//    String hostname = "localhost";
//    Client client = new Client(p1, input, output, hostname, portNum,null, null, null);
//    Player p2 = new BasicPlayer("p2");
////    Player p3 = new BasicPlayer("p3");
//    Territory t1 = new BasicTerritory("t1", "p1");
//    Territory t2 = new BasicTerritory("t2", "p2");
//    Territory t3 = new BasicTerritory("t3", "p2");
//    Territory t4 = new BasicTerritory("t4", "p1");
//    Territory t5 = new BasicTerritory("t5", "p1");// error territory for testing not owning by player3
//    /* TODO - ADD BACK AFTER NEW UNITS IMPLEMENTED
//    t1.addUnits(1);
//    t1.addUnits(1);
//    t2.addUnits(1);
//    t2.addUnits(1);
//    t3.addUnits(1);
//    t3.addUnits(1);
//    */
//    p1.addTerritory(t1);
//    p1.addTerritory(t4);
//    p1.addTerritory(t5);
//    p2.addTerritory(t2);
//    p2.addTerritory(t3);
//    t1.addNeighbor(t2);
//    t1.addNeighbor(t5);
//    t2.addNeighbor(t1);
//    t2.addNeighbor(t3);
//    t3.addNeighbor(t2);
////    assertThrows(IllegalArgumentException.class,()-> client.createSingleOrder("M", t1, t2, 1));
////    assertThrows(IllegalArgumentException.class,()-> client.createSingleOrder("M", t1, t5, 4));
////    assertThrows(IllegalArgumentException.class,()-> client.createSingleOrder("M", t3, t1, 1));
////    assertThrows(IllegalArgumentException.class,()-> client.createSingleOrder("M", t1, t4, 1));
//
//    // TODO - ADD BACK AFTER NEW UNITS IMPLEMENTED
//    // Order order = client.createSingleOrder("M", t1, t5, 1);
//    // assertNotNull(order);
//
//    // order = client.createSingleOrder("A", t1, t2, 1);
//    // assertNotNull(order);
////    assertThrows(IllegalArgumentException.class, ()->client.createSingleOrder("A", t1, t3, 1));
////    assertThrows(IllegalArgumentException.class, ()->client.createSingleOrder("A", t1, t5, 1));
////    assertThrows(IllegalArgumentException.class, ()->client.createSingleOrder("A", t1, t2, 10));
////    assertThrows(IllegalArgumentException.class, ()->client.createSingleOrder("A", t2, t3, 1));
//  }
//
//  @Test
//  public void test_getSingleOrder() throws IOException, ClassNotFoundException {
//    String inputData = "a\nt1\nt2\n1";
//    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//    int portNum = 1642;
//    Player p1 = new BasicPlayer("clientPlayer");
//    BufferedReader input = new BufferedReader(new StringReader(inputData));
//    PrintStream output = new PrintStream(bytes, true);
//    String hostname = "localhost";
//    Client client = new Client(p1, input, output, hostname, portNum,null, null, null);
//    Player p2 = new BasicPlayer("p2");
////    Player p3 = new BasicPlayer("p3");
//    Territory t1 = new BasicTerritory("t1", "p1");
//    Territory t2 = new BasicTerritory("t2", "p2");
//    Territory t3 = new BasicTerritory("t3", "p3"); // error territory for testing not owning by player3
//    // TODO - ADD BACK AFTER NEW UNITS IMPLEMENTED
//    // t1.addUnits(1);
//    // t1.addUnits(1);
//    p1.addTerritory(t1);
//    p2.addTerritory(t2);
//    p2.addTerritory(t3);
//    t1.addNeighbor(t2);
//    t2.addNeighbor(t1);
//    t2.addNeighbor(t3);
//    t3.addNeighbor(t2);
//    String prompt1 = "You are " + p1.getName() +", what would you like to do?\n(M)ove\n(A)ttack\n(D)one";
//    String prompt2 = "Enter name of Source. If it's a source, territory must be owned by you.";
//    String prompt3 = "Enter name of Target. If it's a source, territory must be owned by you.";
//    String prompt4 = "Enter Units.";
//    Order order = client.getSingleOrder();
//    assertNotNull(order);
//    // TODO - ADD BACK AFTER NEW UNITS IMPLEMENTED
//    // assertEquals(prompt1+"\n" + prompt2+ "\n"  + prompt3+ "\n"+ prompt4+ "\n", bytes.toString());
//  }
//
//  @Disabled // TODO - ADD BACK AFTER NEW UNITS IMPLEMENTED
//  @Test
//  public void test_assignUnits() throws IOException, ClassNotFoundException {
//    String inputData = "11\n2\n4\na\n3";
//    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//    int portNum = 1642;
//    Player p1 = new BasicPlayer("clientPlayer");
//    BufferedReader input = new BufferedReader(new StringReader(inputData));
//    PrintStream output = new PrintStream(bytes, true);
//    String hostname = "localhost";
//    Client client = new Client(p1, input, output, hostname, portNum,null, null, null);
//    Territory t1 = new BasicTerritory("t1", "p1");
//    Territory t2 = new BasicTerritory("t2", "p2");
//    Territory t3 = new BasicTerritory("t3", "p3"); // error territory for testing not owning by player3
//    p1.addTerritory(t1);
//    p1.addTerritory(t2);
//    p1.setAvailableUnits(5);
//    String prompt0 = "You are clientPlayer. Please assign units to your territory";
//    String prompt1 = "You have 5 units available. Enter the number of units to be assigned to Territory t1";
//    String prompt2 = "You have 3 units available. Enter the number of units to be assigned to Territory t2";
//    String prompt3 = "Entered number of units unavailable or No units will be available for remaining territories.";
//    String prompt4 = "Please enter Integer number of units";
//    // TODO - ADD BACK AFTER NEW UNITS IMPLEMENTED
//    // client.assignUnits();
//    assertEquals(2, t1.getNumberOfUnits());
//    assertEquals(3, t2.getNumberOfUnits());
//    assertEquals(prompt0+"\n"+ prompt1+"\n" + prompt3+"\n" +prompt1+"\n" + prompt2+ "\n"  + prompt3+ "\n"+ prompt2+ "\n"+ prompt4+ "\n"+ prompt2+ "\n", bytes.toString());
//  }
//
//  @Test
//  public void test_getplayer() throws IOException, ClassNotFoundException {
//    Player p1 = new BasicPlayer("wilson");
//    //Player p2 = new BasicPlayer("jerry");
//    int portNum = 1640;
//    String hostname = "localhost";
//    String inputData = "2\n4\na\n3";
//    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//    BufferedReader input = new BufferedReader(new StringReader(inputData));
//    PrintStream output = new PrintStream(bytes, true);
//    Client client = new Client(p1, input, output, hostname, portNum, null, null, null);
//    assertNotNull(client.getPlayer().getName());
//
//    Territory t1 = new BasicTerritory("duke", "wilson");
//    Territory t2 = new BasicTerritory("unc", "wilson");
//
//    t1.addNeighbor(t2);
//    p1.addTerritory(t1);
//    p1.addTerritory(t2);
//    client.displayBeginningInfo();
//    assertNotNull(bytes.toString());
//  }
//
//}
//
//
