package edu.duke.ece651.risc.client.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class switch_loginTostart_Controller {

  public NewClientController clientController;
  public Client client;
  public ObjectInputStream ois;
  public ObjectOutputStream oos;
  private Stage stage;
  private Scene scene;
  private Parent root;
  public int np;
  //public UIMapDisplayInfo mapInfo;  
  
  public switch_loginTostart_Controller(Client client, int np) throws IOException, ClassNotFoundException{
    this.client = client;
    this.np = np;
  }
  public switch_loginTostart_Controller(NewClientController clientController, int np) throws IOException, ClassNotFoundException{
//    this.client = client;
    this.np = np;
    this.clientController = clientController;
  }
  
    @FXML
    private TextField account_id;

    @FXML
    private Button login_id;

    @FXML
    private PasswordField password_id;

    @FXML
    private Button signup_id;
    
    void switchToAssign(ActionEvent event) throws IOException, ClassNotFoundException {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/newassign.fxml"));
      HashMap<Class<?>, Object> controllers = new HashMap<>();
      System.out.println("before switch to assgin");
      controllers.put(assign_unitsController.class, new assign_unitsController(clientController, np));
      loader.setControllerFactory(c -> {return controllers.get(c);});
      
      Pane bp = loader.load();
      
      stage = (Stage)((Node)event.getSource()).getScene().getWindow();
      scene = new Scene(bp, 1280, 720);
      stage.setScene(scene);
      stage.show();
    }

  void switchToTurn(ActionEvent event) throws IOException, ClassNotFoundException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/newturn.fxml"));

    HashMap<Class<?>, Object> controllers = new HashMap<>();
    controllers.put(turn_action_controller.class, new turn_action_controller(clientController, np, false));
    loader.setControllerFactory(c -> {return controllers.get(c);});

    Pane bp = loader.load();

    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(bp, 1280, 720);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  void onclick_login(ActionEvent event) throws IOException, ClassNotFoundException {
    //TO DO METHOD 
    //check user with client
    try{
      String UID = account_id.getText();
      String password = password_id.getText();
      int result = clientController.signUpOrLogin(np,UID, password);
      if(result ==1 ){
        switchToAssign(event);
      } else{
        switchToTurn(event);
      }
    } catch(IllegalArgumentException e) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText(e.getMessage());
      alert.show();
      account_id.clear();
      password_id.clear();
    }
  }

  
    @FXML
    void onclick_signup(ActionEvent event) throws IOException, ClassNotFoundException{
      try{
        String UID = account_id.getText();
        String password = password_id.getText();
        int result = clientController.signUpOrLogin(np,UID, password);
        if(result == 1){
          switchToAssign(event);
        } else{
          switchToTurn(event);
        }
      } catch(IllegalArgumentException e) {
        //System.out.println("Wrong Password!");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getMessage());
        alert.show();
        account_id.clear();
        password_id.clear();
      }
    }
}
