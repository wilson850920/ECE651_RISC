package edu.duke.ece651.risc.client.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.duke.ece651.risc.client.Client;
import edu.duke.ece651.risc.client.Client2;
import edu.duke.ece651.risc.client.NewClientController;
import edu.duke.ece651.risc.common.Territory;
import edu.duke.ece651.risc.common.UIMapDisplayInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class assign_unitsController {

  public Client client;
  public NewClientController clientController;
  public int np;
  public int gameid;
  private Stage stage;
  private Map<String, Button> ui_territory_map = new HashMap<>();
  private Map<String, Text> ui_unit_map = new HashMap<>();
  private Map<String, Circle> ui_circle_map = new HashMap<>();
  private Map<String, Text> ui_size_map = new HashMap<>();

  @FXML
    private Line line_bag_abcd_id;

    @FXML
    private Line line_coffee_bag_id;

    @FXML
    private Line line_duke_coffee_id;

    @FXML
    private Line line_ela_ros_id;

    @FXML
    private Line line_ela_sca_id;

    @FXML
    private Line line_elan_look_id;

    @FXML
    private Line line_gon_bag_id;

    @FXML
    private Line line_gon_coffee_id;

    @FXML
    private Line line_gon_mor_id;

    @FXML
    private Line line_hog_abcd_id;

    @FXML
    private Line line_hog_out_id;

    @FXML
    private Line line_look_out_id;

    @FXML
    private Line line_mid_duke_id;

    @FXML
    private Line line_mid_elan_id;

    @FXML
    private Line line_mid_oz_id;

    @FXML
    private Line line_mid_sca_id;

    @FXML
    private Line line_mor_abcd_id;

    @FXML
    private Line line_mor_bag_id;

    @FXML
    private Line line_mor_hog_id;

    @FXML
    private Line line_nar_duke_id;

    @FXML
    private Line line_nar_ela_id;

    @FXML
    private Line line_nar_mid_id;

    @FXML
    private Line line_out_abcd_id;

    @FXML
    private Line line_oz_coffee_id;

    @FXML
    private Line line_oz_duke_id;

    @FXML
    private Line line_oz_gon_id;

    @FXML
    private Line line_oz_mor_id;

    @FXML
    private Line line_oz_sca_id;

    @FXML
    private Line line_ros_hog_id;

    @FXML
    private Line line_ros_look_id;

    @FXML
    private Line line_ros_out_id;

    @FXML
    private Line line_sca_hog_id;

    @FXML
    private Line line_sca_mor_id;

    @FXML
    private Line line_sca_ros_id;
  
  @FXML
    private Circle circle_abcd_id;

    @FXML
    private Circle circle_bagel_id;

    @FXML
    private Circle circle_coffeeland_id;

    @FXML
    private Circle circle_duke_id;

    @FXML
    private Circle circle_elantris_id;

    @FXML
    private Circle circle_gondor_id;

    @FXML
    private Circle circle_hogwarts_id;

    @FXML
    private Circle circle_lookout_id;

    @FXML
    private Circle circle_midkemia_id;

    @FXML
    private Circle circle_mordor_id;

    @FXML
    private Circle circle_narnia_id;

    @FXML
    private Circle circle_outlook_id;

    @FXML
    private Circle circle_oz_id;

    @FXML
    private Circle circle_roshar_id;

    @FXML
    private Circle circle_scadrial_id;
  
  @FXML
      private ComboBox<String> select_unit_id;

    @FXML
    private Button abcd_id;

    @FXML
    private Text abcd_size;

    @FXML
    private Text abcd_unit;

    @FXML
    private Button bagel_id;

    @FXML
    private Text bagel_size;

    @FXML
    private Text bagel_unit;

    @FXML
    private TextArea broadcast_id;

    @FXML
    private Button coffeeland_id;

    @FXML
    private Text coffeeland_size;

    @FXML
    private Text coffeeland_unit;

    @FXML
    private Button duke_id;

    @FXML
    private Text duke_size;

    @FXML
    private Text duke_unit;

    @FXML
    private Button elantris_id;

    @FXML
    private Text elantris_size;

    @FXML
    private Text elantris_unit;

    @FXML
    private Button endplacement_id;

    @FXML
    private Button enter_id;

    @FXML
    private Button exit_game_id;

    @FXML
    private Text food_id;

    @FXML
    private Button gondor_id;

    @FXML
    private Text gondor_size;

    @FXML
    private Text gondor_unit;

    @FXML
    private Button hogwarts_id1;

    @FXML
    private Text hogwarts_size;

    @FXML
    private Text hogwarts_unit;

    @FXML
    private Text level_id;

    @FXML
    private Button lookout_id;

    @FXML
    private Text lookout_size;

    @FXML
    private Text lookout_unit;

    @FXML
    private Button midkemia_id;

    @FXML
    private Text midkemia_size;

    @FXML
    private Text midkemia_unit;

    @FXML
    private Button mordor_id;

    @FXML
    private Text mordor_size;

    @FXML
    private Text mordor_unit;

    @FXML
    private Button narnia_id;

    @FXML
    private Text narnia_size;

    @FXML
    private Text narnia_unit;

    @FXML
    private Button outlook_id;

    @FXML
    private Text outlook_size;

    @FXML
    private Text outlook_unit;

    @FXML
    private Button oz_id;

    @FXML
    private Text oz_size;

    @FXML
    private Text oz_unit;

    @FXML
    private Text player_id;

    @FXML
    private Button roshar_id;

    @FXML
    private Text roshar_size;

    @FXML
    private Text roshar_unit;

    @FXML
    private Button scadrial_id;

    @FXML
    private Rectangle after_assign_block_id;

    @FXML
    private Text after_assign_message_id;

    @FXML
    private Rectangle after_assign_rec_id;
  
    @FXML
    private Text scadrial_size;

    @FXML
    private Text scadrial_unit;

    @FXML
    private Text server_id;

    @FXML
    private Button switch_game_id;

    @FXML
    private TextField terri_name_id;

    @FXML
    private TextField unit_id;

  public void invisible_whiteT() {
    lookout_id.setVisible(false);
    lookout_unit.setVisible(false);
    lookout_size.setVisible(false);
    circle_lookout_id.setVisible(false);
    line_elan_look_id.setVisible(false);
    line_ros_look_id.setVisible(false);
    line_look_out_id.setVisible(false);
    outlook_id.setVisible(false);
    outlook_unit.setVisible(false);
    outlook_size.setVisible(false);
    circle_outlook_id.setVisible(false);
    line_ros_out_id.setVisible(false);
    line_hog_out_id.setVisible(false);
    line_out_abcd_id.setVisible(false);
    abcd_id.setVisible(false);
    abcd_unit.setVisible(false);
    abcd_size.setVisible(false);
    circle_abcd_id.setVisible(false);
    line_hog_abcd_id.setVisible(false);
    line_mor_abcd_id.setVisible(false);
    line_bag_abcd_id.setVisible(false);
  }
  public void invisible_orangeT() {
    duke_id.setVisible(false);
    duke_size.setVisible(false);
    duke_unit.setVisible(false);
    circle_duke_id.setVisible(false);
    line_nar_duke_id.setVisible(false);
    line_mid_duke_id.setVisible(false);
    line_oz_duke_id.setVisible(false);
    line_duke_coffee_id.setVisible(false); 
    coffeeland_id.setVisible(false);
    coffeeland_size.setVisible(false);
    coffeeland_unit.setVisible(false);
    circle_coffeeland_id.setVisible(false);
    line_oz_coffee_id.setVisible(false);
    line_gon_coffee_id.setVisible(false);
    line_coffee_bag_id.setVisible(false); 
    bagel_id.setVisible(false);
    bagel_size.setVisible(false);
    bagel_unit.setVisible(false);
    circle_bagel_id.setVisible(false);
    line_gon_bag_id.setVisible(false);
    line_mor_bag_id.setVisible(false);
  }
  public void invisible_greenT() {
    gondor_id.setVisible(false);
    gondor_size.setVisible(false);
    gondor_unit.setVisible(false);
    circle_gondor_id.setVisible(false);
    line_oz_gon_id.setVisible(false);
    line_gon_mor_id.setVisible(false);
    mordor_id.setVisible(false);
    mordor_size.setVisible(false);
    mordor_unit.setVisible(false);
    circle_mordor_id.setVisible(false);
    line_oz_mor_id.setVisible(false);
    line_sca_mor_id.setVisible(false);
    line_mor_hog_id.setVisible(false);
    hogwarts_id1.setVisible(false);
    hogwarts_size.setVisible(false);
    hogwarts_unit.setVisible(false);
    circle_hogwarts_id.setVisible(false);
    line_ros_hog_id.setVisible(false);
    line_sca_hog_id.setVisible(false);
  }

  // NOT USED WITH FOG OF WAR CHANGES
  public void setplayerInfor() {
    Client2 client = clientController.clientMap.get(gameid);
    String str = client.getPlayer().getName();
    
    System.out.print("\n" + str);
    Iterator<Territory> it_t = client.getPlayer().getTerritories();
    while (it_t.hasNext()) {
      System.out.print(" name: " + it_t.next().getName());
    }


    if (str.equals("player 0")) {

      Iterator<Territory> it_t1 = client.getPlayer().getTerritories();
      while (it_t1.hasNext()) {
        //ui_territory_map.get(it_t1.next().getName()).setStyle("-fx-background-color: ADD8E6");
        ui_circle_map.get(it_t1.next().getName()).setStroke(Color.BLUE); 
      }
    }
    if (str.equals("player 1")) {
      Iterator<Territory> it_t2 = client.getPlayer().getTerritories();
      while (it_t2.hasNext()) {
        //ui_territory_map.get(it_t2.next().getName()).setStyle("-fx-background-color: ffdb58");
        ui_circle_map.get(it_t2.next().getName()).setStroke(Color.YELLOW);
      }
    }
    if (str.equals("player 2")) {
      Iterator<Territory> it_t3 = client.getPlayer().getTerritories();
      while (it_t3.hasNext()) {
        //ui_territory_map.get(it_t3.next().getName()).setStyle("-fx-background-color: green");
        ui_circle_map.get(it_t3.next().getName()).setStroke(Color.GREEN); 
      }
    }
    if (str.equals("player 3")) {
      Iterator<Territory> it_t4 = client.getPlayer().getTerritories();
      while (it_t4.hasNext()) {
        //ui_territory_map.get(it_t4.next().getName()).setStyle("-fx-background-color: orange");
        ui_circle_map.get(it_t4.next().getName()).setStroke(Color.ORANGE);
      }
    }
    if (str.equals("player 4")) {
      Iterator<Territory> it_t5 = client.getPlayer().getTerritories();
      while (it_t5.hasNext()) {
        //ui_territory_map.get(it_t5.next().getName()).setStyle("-fx-background-color: white");
        ui_circle_map.get(it_t5.next().getName()).setStroke(Color.RED);
      }
    }
    
    //broadcast_id.setText("Please fill in the units you want to dispatch on your territory");
  }

  public void init_UI_territory_map() {
    ui_territory_map.put("Korriban", narnia_id);
    ui_territory_map.put("Mustafar", midkemia_id);
    ui_territory_map.put("Death Star", oz_id);
    ui_territory_map.put("Dagobah", elantris_id);
    ui_territory_map.put("Hoth", scadrial_id);
    ui_territory_map.put("Tatooine", roshar_id);
    ui_territory_map.put("Alderaan", gondor_id);
    ui_territory_map.put("Coruscant", mordor_id);
    ui_territory_map.put("Yavin4", hogwarts_id1);
    ui_territory_map.put("Kamino", duke_id);
    ui_territory_map.put("Naboo", coffeeland_id);
    ui_territory_map.put("Kashyyyk", bagel_id);
    ui_territory_map.put("Bespin", lookout_id);
    ui_territory_map.put("Dantoowin", outlook_id);
    ui_territory_map.put("Felucia", abcd_id);
  }

  public void init_UI_unit_map() {
    ui_unit_map.put("Korriban", narnia_unit);
    ui_unit_map.put("Mustafar", midkemia_unit);
    ui_unit_map.put("Death Star", oz_unit);
    ui_unit_map.put("Dagobah", elantris_unit);
    ui_unit_map.put("Hoth", scadrial_unit);
    ui_unit_map.put("Tatooine", roshar_unit);
    ui_unit_map.put("Alderaan", gondor_unit);
    ui_unit_map.put("Coruscant", mordor_unit);
    ui_unit_map.put("Yavin4", hogwarts_unit);
    ui_unit_map.put("Kamino", duke_unit);
    ui_unit_map.put("Naboo", coffeeland_unit);
    ui_unit_map.put("Kashyyyk", bagel_unit);
    ui_unit_map.put("Bespin", lookout_unit);
    ui_unit_map.put("Dantoowin", outlook_unit);
    ui_unit_map.put("Felucia", abcd_unit);
  }

  public void init_UI_circle_map() {
    ui_circle_map.put("Korriban", circle_narnia_id);
    ui_circle_map.put("Mustafar", circle_midkemia_id);
    ui_circle_map.put("Death Star", circle_oz_id);
    ui_circle_map.put("Dagobah", circle_elantris_id);
    ui_circle_map.put("Hoth", circle_scadrial_id);
    ui_circle_map.put("Tatooine", circle_roshar_id);
    ui_circle_map.put("Alderaan", circle_gondor_id);
    ui_circle_map.put("Coruscant", circle_mordor_id);
    ui_circle_map.put("Yavin4", circle_hogwarts_id);
    ui_circle_map.put("Kamino", circle_duke_id);
    ui_circle_map.put("Naboo", circle_coffeeland_id);
    ui_circle_map.put("Kashyyyk", circle_bagel_id);
    ui_circle_map.put("Bespin", circle_lookout_id);
    ui_circle_map.put("Dantoowin", circle_outlook_id);
    ui_circle_map.put("Felucia", circle_abcd_id);
  }
  
  public void init_UI_size_map() {
    ui_size_map.put("Korriban", narnia_size);
    ui_size_map.put("Mustafar", midkemia_size);
    ui_size_map.put("Death Star", oz_unit);
    ui_size_map.put("Dagobah", elantris_size);
    ui_size_map.put("Hoth", scadrial_size);
    ui_size_map.put("Tatooine", roshar_size);
    ui_size_map.put("Alderaan", gondor_size);
    ui_size_map.put("Coruscant", mordor_size);
    ui_size_map.put("Yavin4", hogwarts_size);
    ui_size_map.put("Kamino", duke_size);
    ui_size_map.put("Naboo", coffeeland_size);
    ui_size_map.put("Kashyyyk", bagel_size);
    ui_size_map.put("Bespin", lookout_size);
    ui_size_map.put("Dantoowin", outlook_size);
    ui_size_map.put("Felucia", abcd_size);
  }

  public void buildcombobox() {
    select_unit_id.getItems().add("1");
    select_unit_id.getItems().add("2");
    select_unit_id.getItems().add("3");
    select_unit_id.getItems().add("4");
    select_unit_id.getItems().add("5");
    select_unit_id.getItems().add("6");
    select_unit_id.getItems().add("7");
    select_unit_id.getItems().add("8");
    select_unit_id.getItems().add("9");
  }

  public void init_territory_image() {
    Image im1 = new Image("/images/korriban.png", false);
    Image im2 = new Image("/images/mustafar.png", false);
    Image im3 = new Image("/images/death_star.png", false);
    Image im4 = new Image("/images/dagobah.png", false);
    Image im5 = new Image("/images/hoth.png", false);
    Image im6 = new Image("/images/tatooine.png", false);
    Image im7 = new Image("/images/alderaan.png", false);
    Image im8 = new Image("/images/coruscant.png", false);
    Image im9 = new Image("/images/yavin4.png", false);
    Image im10 = new Image("/images/kamino.png", false);
    Image im11 = new Image("/images/naboo.png", false);
    Image im12 = new Image("/images/kashyyyk.png", false);
    Image im13 = new Image("/images/bespin.png", false);
    Image im14 = new Image("/images/dantoowin.png", false);
    Image im15 = new Image("/images/felucia.png", false);

    circle_narnia_id.setFill(new ImagePattern(im1));
    circle_midkemia_id.setFill(new ImagePattern(im2));
    circle_oz_id.setFill(new ImagePattern(im3));
    circle_elantris_id.setFill(new ImagePattern(im4));
    circle_scadrial_id.setFill(new ImagePattern(im5));
    circle_roshar_id.setFill(new ImagePattern(im6));
    circle_gondor_id.setFill(new ImagePattern(im7));
    circle_mordor_id.setFill(new ImagePattern(im8));
    circle_hogwarts_id.setFill(new ImagePattern(im9));
    circle_duke_id.setFill(new ImagePattern(im10));
    circle_coffeeland_id.setFill(new ImagePattern(im11));
    circle_bagel_id.setFill(new ImagePattern(im12));
    circle_lookout_id.setFill(new ImagePattern(im13));
    circle_outlook_id.setFill(new ImagePattern(im14));
    circle_abcd_id.setFill(new ImagePattern(im15));  
  }

  public void invisible_size() {
    narnia_size.setVisible(false);
    midkemia_size.setVisible(false);
    oz_size.setVisible(false);
    elantris_size.setVisible(false);
    scadrial_size.setVisible(false);
    roshar_size.setVisible(false);
    gondor_size.setVisible(false);
    mordor_size.setVisible(false);
    hogwarts_size.setVisible(false);
    duke_size.setVisible(false);
    coffeeland_size.setVisible(false);
    bagel_size.setVisible(false);
    lookout_size.setVisible(false);
    outlook_size.setVisible(false);
    abcd_size.setVisible(false);
  }
  
  @FXML
  public void initialize() {
    buildcombobox();
    init_territory_image();
    invisible_size();
    //after_assign_block_id.setVisible(false);
    //after_assign_message_id.setVisible(false);
    //after_assign_rec_id.setVisible(false);
    init_UI_territory_map();
    init_UI_unit_map();
    init_UI_circle_map();
    init_UI_size_map();
    if (np == 2) {
      gameid = 2;
      invisible_whiteT();
      invisible_orangeT();
      invisible_greenT();
    }
    else if (np == 3) {
      gameid = 3;
      invisible_whiteT();
      invisible_orangeT();
    }
    else if (np == 4) {
      gameid = 4;
      invisible_whiteT();
    }
    else {
      gameid = 5;
    }
     setplayerInfor();
     broadcast_id.setText("Please fill in the units you want to dispatch on your territory");
  }
  
  public assign_unitsController(Client client, int np) throws IOException, ClassNotFoundException{
    this.client = client;
    this.np = np;
  }

  public assign_unitsController(NewClientController clientController, int np) throws IOException, ClassNotFoundException{
    this.clientController = clientController;
    this.np = np;
  }

  public void selectTerritoryToAssign(String Territory_name) {
    terri_name_id.setText(Territory_name);
  }

    @FXML
    void choose_unit(ActionEvent event) {
      
    }
    
    @FXML
    void click_abcd(ActionEvent event) {
      selectTerritoryToAssign(abcd_id.getText());
    }

    @FXML
    void click_bagel(ActionEvent event) {
      selectTerritoryToAssign(bagel_id.getText());
    }

    @FXML
    void click_coffeeland(ActionEvent event) {
      selectTerritoryToAssign(coffeeland_id.getText());
    }

    @FXML
    void click_duke(ActionEvent event) {
      selectTerritoryToAssign(duke_id.getText());
    }

    @FXML
    void click_elantris(ActionEvent event) {
      selectTerritoryToAssign(elantris_id.getText());
    }

    @FXML
    void click_gondor(ActionEvent event) {
      selectTerritoryToAssign(gondor_id.getText());
    }

    @FXML
    void click_hogwarts(ActionEvent event) {
      selectTerritoryToAssign(hogwarts_id1.getText());
    }

    @FXML
    void click_lookout(ActionEvent event) {
      selectTerritoryToAssign(lookout_id.getText());
    }

    @FXML
    void click_midkemia(ActionEvent event) {
      selectTerritoryToAssign(midkemia_id.getText());
    }

    @FXML
    void click_mordor(ActionEvent event) {
      selectTerritoryToAssign(mordor_id.getText());
    }

    @FXML
    void click_narnia(ActionEvent event) {
      selectTerritoryToAssign(narnia_id.getText());
    }

    @FXML
    void click_outlook(ActionEvent event) {
      selectTerritoryToAssign(outlook_id.getText());
    }

    @FXML
    void click_oz(ActionEvent event) {
      selectTerritoryToAssign(oz_id.getText());
    }

    @FXML
    void click_roshar(ActionEvent event) {
      selectTerritoryToAssign(roshar_id.getText());
    }

    @FXML
    void click_scadrial(ActionEvent event) {
      selectTerritoryToAssign(scadrial_id.getText());
    }

  public void testJSON(JSONObject jo) {
    JSONArray ja_game_map = jo.getJSONArray("game_Map");
    for (int i = 0; i < ja_game_map.length(); i ++) {
      JSONArray temp = ja_game_map.getJSONObject(i).getJSONArray("Player");
      String tech_level = temp.getJSONObject(0).getString("Technology Level");
      String resource = temp.getJSONObject(0).getString("Resources");
      JSONArray ter = temp.getJSONObject(0).getJSONArray("Territories");
      String pname = temp.getJSONObject(0).getString("Name");
      for (int k = 0; k < ter.length(); k ++) {
        JSONArray ii = ter.getJSONArray(k);
        String t_na = ii.getJSONObject(0).getString("Name");
        JSONArray unit = ii.getJSONObject(0).getJSONArray("Units");
        
        for (int l = 0; l < unit.length(); l ++) {
          JSONObject uu = unit.getJSONObject(l);

          JSONArray me = ter.getJSONArray(l);
          int t = unit.getJSONObject(l).getInt("A");

          System.out.print("\n");
          System.out.print("t: " + t);
        }
      }
    }
  }
  
    @FXML
    void onclick_endplace(ActionEvent event) throws IOException, ClassNotFoundException {
      Iterator<Territory> it = clientController.clientMap.get(np).getPlayer().getTerritories();
      while(it.hasNext()){
        Territory t = it.next();
        System.out.println(t.getNumberOfUnits());
      }
      clientController.unitsAssignedSuccessfully(np);
      clientController.clientsendPlayer(np);
      UIMapDisplayInfo mapInfo = clientController.clientDisplayCommonMessage(np);
      System.out.print(mapInfo.mapinfo());
      JSONObject jo = mapInfo.getJSON();
      //testJSON(jo);
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/newturn.fxml"));
      HashMap<Class<?>, Object> controllers = new HashMap<>();
      controllers.put(turn_action_controller.class, new turn_action_controller(clientController, mapInfo, np, true));
      loader.setControllerFactory(c -> {return controllers.get(c);});

      Pane bp = loader.load();
      stage = (Stage)((Node)event.getSource()).getScene().getWindow();
      Scene scene = new Scene(bp, 1280, 720);
      stage.setScene(scene);
      stage.show();
    }

    @FXML
    void onclick_enter(ActionEvent event) throws IOException {

      //TO DO METHOD 
      // need a function at client side that feed on territory and unit for assignment
      try {
        String territoryName = terri_name_id.getText();
        String unitsCount = select_unit_id.getValue().toString();
        String confirmT_name = clientController.validateAndAssignUnits(np, unitsCount, territoryName);
        ui_unit_map.get(confirmT_name).setText(unitsCount);
        terri_name_id.clear();
        select_unit_id.getSelectionModel().clearSelection();
      } catch(IllegalArgumentException e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getMessage());
        alert.show();
        terri_name_id.clear();
        unit_id.clear();
      }
    }

    @FXML
    void onclick_exit(ActionEvent event) throws IOException, ClassNotFoundException{
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/pickroom.fxml"));
      HashMap<Class<?>, Object> controllers = new HashMap<>();
      controllers.put(choose_game_room.class, new choose_game_room(clientController));
      loader.setControllerFactory(c -> {return controllers.get(c);});
      BorderPane bp = loader.load();
      stage = (Stage)((Node)event.getSource()).getScene().getWindow();
      Scene scene = new Scene(bp, 600, 400);
      stage.setScene(scene);
      stage.show();
     
    }

    @FXML
    void onclick_switchgame(ActionEvent event) throws IOException, ClassNotFoundException {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/pickroom.fxml"));

      HashMap<Class<?>, Object> controllers = new HashMap<>();
      controllers.put(choose_game_room.class, new choose_game_room(clientController));
      loader.setControllerFactory(c -> {return controllers.get(c);});
      BorderPane bp = loader.load();
      stage = (Stage)((Node)event.getSource()).getScene().getWindow();
      Scene scene = new Scene(bp, 600, 400);
      stage.setScene(scene);
      stage.show();
    }

}
