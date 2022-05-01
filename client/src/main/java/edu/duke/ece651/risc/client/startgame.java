package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;

import edu.duke.ece651.risc.client.controller.choose_game_room;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class startgame extends Application {
//public class base_UI {

  public Socket socket;
  private Stage stage;
  
  @Override
  public void start(Stage stage) throws IOException, ClassNotFoundException {

    try {
      //FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/pickroom.fxml"));
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/back.fxml"));
      //URL cssResources = getClass().getResource("/ui/loading.css");
      //Media buzzer = new Media(getClass().getResource("/media/start.mp3").toExternalForm());
      //AudioClip clip = new AudioClip(new File(url).toURI().toString());

      /*
      Media sound = new Media(getClass().getResource("/media/start.mp3").toExternalForm());
      MediaPlayer mediaPlayer = new MediaPlayer(sound);
      mediaPlayer.play();
      */
      NewClientController clientController = new NewClientController();
      HashMap<Class<?>, Object> controllers = new HashMap<>();
      controllers.put(choose_game_room.class, new choose_game_room(clientController));

      loader.setControllerFactory(c -> {return controllers.get(c);});
      
      Pane bp = loader.load();
      Scene scene = new Scene(bp, 600, 400);
      //scene.getStylesheets().add(cssResources.toString());
      stage.setScene(scene);
      stage.show();

    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {
    launch(args);
  }
}
