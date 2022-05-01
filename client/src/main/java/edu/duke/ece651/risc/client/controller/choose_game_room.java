package edu.duke.ece651.risc.client.controller;

import java.io.IOException;
import java.util.HashMap;

import edu.duke.ece651.risc.client.Client;
import edu.duke.ece651.risc.client.NewClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class choose_game_room {

  public Client client;
  public NewClientController clientController;
  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML
  private Button fiveplayergame_id;

  @FXML
  private Button fourplayergame_id;

  @FXML
  private Button threeplayergame_id;

  @FXML
  private Button twoplayergame_id;

  @FXML
  private Circle circle_id;

  @FXML
  private Circle circle_3p_id;
  
  @FXML
  private Circle circle_4p_id;
  
  @FXML
  private Circle circle_5p_id;
  
  @FXML
  public void initialize() {
    Image im1 = new Image("/images/star1.jpeg", false);
    Image im2 = new Image("/images/star2.jpeg", false);
    Image im3 = new Image("/images/star3.jpeg", false);
    Image im4 = new Image("/images/star4.jpeg", false);
    circle_id.setFill(new ImagePattern(im1));
    circle_3p_id.setFill(new ImagePattern(im2));
    circle_4p_id.setFill(new ImagePattern(im3));
    circle_5p_id.setFill(new ImagePattern(im4));
  }
  
  public choose_game_room(Client client) throws IOException, ClassNotFoundException {
    this.client = client;

  }

  public choose_game_room(NewClientController clientController) throws IOException, ClassNotFoundException {
    this.clientController = clientController;

  }

  public void switch_to_login(ActionEvent event, int np) throws IOException, ClassNotFoundException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/newlogin.fxml"));
    //FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/back.fxml"));
    HashMap<Class<?>, Object> controllers = new HashMap<>();
    controllers.put(switch_loginTostart_Controller.class, new switch_loginTostart_Controller(clientController, np));

    loader.setControllerFactory((c) -> {
      return controllers.get(c);
    });
    //BorderPane bp = loader.load();
    Pane bp = loader.load();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(bp, 600, 400);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  void onclick_fiveP(ActionEvent event) throws IOException, ClassNotFoundException {
    if(!clientController.gameStatus.get(5)){
      switch_to_login(event, 5);
    } else{
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText("Game ended! Nothing to watch here.");
      alert.show();

    }

  }

  @FXML
  void onclick_fourP(ActionEvent event) throws IOException, ClassNotFoundException {
    if(!clientController.gameStatus.get(4)){
      switch_to_login(event, 4);
    } else{
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText("Game ended! Nothing to watch here.");
      alert.show();

    }
  }

  @FXML
  void onclick_threeP(ActionEvent event) throws IOException, ClassNotFoundException {
    if(!clientController.gameStatus.get(3)){
      switch_to_login(event, 3);
    } else{
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText("Game ended! Nothing to watch here.");
      alert.show();

    }
  }

  @FXML
  void onclick_twoP(ActionEvent event) throws IOException, ClassNotFoundException {
    if(!clientController.gameStatus.get(2)){
      switch_to_login(event, 2);
    } else{
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText("Game ended! Nothing to watch here.");
      alert.show();

    }
  }

}
