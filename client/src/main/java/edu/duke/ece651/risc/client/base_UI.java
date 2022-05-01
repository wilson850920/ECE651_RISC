package edu.duke.ece651.risc.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import edu.duke.ece651.risc.common.BasicPlayer;
import edu.duke.ece651.risc.common.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class base_UI extends Application {
//public class base_UI {

  @Override
  public void start(Stage stage) throws IOException{
    URL xmlResource = getClass().getResource("/ui/each_turn.fxml");
    //URL xmlResource = getClass().getResource("base.fxml");

    //try {
      FXMLLoader loader = new FXMLLoader(xmlResource);
      BorderPane bp = loader.load();
      Scene scene = new Scene(bp, 600, 400);
      stage.setScene(scene);
      stage.show();
    //}
    //catch (IOException e) {
    //  e.printStackTrace();
    //}
  }

  public static void main(String[] args) throws IOException {
    launch(args);
    //System.out.println("hello, I'm right here");
    //Player testPlayer = new BasicPlayer("clientPlayer");
    //InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    //BufferedReader inputReader = new BufferedReader(inputStreamReader);
    //String hostname = "vcm-24503.vm.duke.edu";
    //int portNum = 1641; 
    //Client client = new Client(testPlayer, inputReader, System.out, hostname, portNum, null, null, null);
    //client.main(args);
  }

  //public void start(Stage stage) {
  //        
  //  String javaVersion = System.getProperty("java.version");
  //  String javafxVersion = System.getProperty("javafx.version");
  //  Label l = new Label("Hello, JavaFX " + javafxVersion +
  //      	   ", running on Java " +
  //      	   javaVersion + ".");
  //  Scene scene = new Scene(new StackPane(l), 640, 480);
  //  stage.setScene(scene);
  //  stage.show();
  //}
}
