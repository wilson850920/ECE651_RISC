package edu.duke.ece651.risc.client.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.duke.ece651.risc.client.Client2;
import edu.duke.ece651.risc.client.NewClientController;
import edu.duke.ece651.risc.common.BasicPlayer;
import edu.duke.ece651.risc.common.Order;
import edu.duke.ece651.risc.common.OrderList;
import edu.duke.ece651.risc.common.Player;
import edu.duke.ece651.risc.common.SpyUnit;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class turn_action_controller {

  public UIMapDisplayInfo mapInfo;
  public int np;
  private Stage stage;
  public NewClientController clientController;
  public int gameid;

  public HashMap<Integer, Integer> units = new HashMap<Integer, Integer>();
  public ArrayList<Order> orders = new ArrayList<Order>();
  private HashMap<String, Integer> unitTypesToBonus = new HashMap<String, Integer>();
  private Map<String, Button> ui_territory_map = new HashMap<>();
  private Map<String, Text> ui_unit_map = new HashMap<>();
  private Map<String, Text> ui_size_map = new HashMap<>();
  private Map<String, Circle> ui_circle_map = new HashMap<>();
  private boolean justAssignedUnits = false;
  private boolean playerTechUpdateIssued = false;

  Map<Integer, Long> unitMap;
  String actioninput = null;
  String input_T_source = null;
  String input_T_target = null;
  int flag = 0;

  HashMap<String, String> orderTypes = new HashMap<>();

  private Map<String, Color> playerColorMap;
  private Set<String> visibleTerritories;
  private Map<String, String> territoryHoverInfoMap;

  public turn_action_controller(NewClientController cc, UIMapDisplayInfo info, int np, boolean value)
      throws IOException, ClassNotFoundException {
    this.clientController = cc;
    this.np = np;
    this.unitTypesToBonus.put("A", 0);
    this.unitTypesToBonus.put("B", 1);
    this.unitTypesToBonus.put("C", 3);
    this.unitTypesToBonus.put("D", 5);
    this.unitTypesToBonus.put("E", 8);
    this.unitTypesToBonus.put("F", 11);
    this.unitTypesToBonus.put("G", 15);
    this.orderTypes.put("Move", "M");
    this.orderTypes.put("Attack", "A");
    this.orderTypes.put("Revolt", "R");
    this.orderTypes.put("Player Tech Upgrade", "TU");
    this.orderTypes.put("Upgrade Unit", "UU");
    this.orderTypes.put("Request Spy", "US");
    this.orderTypes.put("Move Spy", "SM");
    this.orderTypes.put("Unlock Cloaking", "UC");
    this.orderTypes.put("Cloak Territory", "CT");
    this.mapInfo = info;
    this.actioninput = null;
    this.input_T_source = null;
    this.input_T_target = null;
    this.flag = 0;
    this.justAssignedUnits = value;
    this.playerColorMap = Map.of("player 0", Color.BLUE, "player 1", Color.YELLOW, "player 2", Color.GREEN, "player 3",
        Color.ORANGE, "player 4", Color.RED);
    this.visibleTerritories = new HashSet<String>();
    this.territoryHoverInfoMap = new HashMap<String, String>();
    System.out.println("Recieving map after assigning units");
  }

  public turn_action_controller(NewClientController clientController, int np, boolean value)
      throws IOException, ClassNotFoundException {
    this.clientController = clientController;
    this.np = np;
    this.unitTypesToBonus.put("A", 0);
    this.unitTypesToBonus.put("B", 1);
    this.unitTypesToBonus.put("C", 3);
    this.unitTypesToBonus.put("D", 5);
    this.unitTypesToBonus.put("E", 8);
    this.unitTypesToBonus.put("F", 11);
    this.unitTypesToBonus.put("G", 15);
    this.orderTypes.put("Move", "M");
    this.orderTypes.put("Attack", "A");
    this.orderTypes.put("Revolt", "R");
    this.orderTypes.put("Player Tech Upgrade", "TU");
    this.orderTypes.put("Upgrade Unit", "UU");
    this.orderTypes.put("Request Spy", "US");
    this.orderTypes.put("Move Spy", "SM");
    this.orderTypes.put("Unlock Cloaking", "UC");
    this.orderTypes.put("Cloak Territory", "CT");
    this.mapInfo = null;
    this.actioninput = null;
    this.input_T_source = null;
    this.input_T_target = null;
    this.flag = 0;
    this.justAssignedUnits = value;
    this.playerColorMap = Map.of("player 0", Color.BLUE, "player 1", Color.YELLOW, "player 2", Color.GREEN, "player 3",
        Color.ORANGE, "player 4", Color.RED);
    this.visibleTerritories = new HashSet<String>();
    this.territoryHoverInfoMap = new HashMap<String, String>();
  }

  @FXML
  private TextArea spy_info_id;

  @FXML
  private Button action_appear_id;

  @FXML
  private Text source_text_id;

  @FXML
  private Text source_unit_text_id;

  @FXML
  private Text target_text_id;

  @FXML
  private Text unit_num_text_id;

  @FXML
  private Text action_text_id;

  @FXML
  private Text target_unit_text_id;

  @FXML
  private ComboBox<String> select_TargetU_id;

  @FXML
  private ComboBox<String> select_action_id;

  @FXML
  private ComboBox<String> select_sourceU_id;

  @FXML
  private TextField unit_num_id;

  @FXML
  private TextArea broadcast_id;

  @FXML
  private Button switch_game_id;

  @FXML
  private Button lose_game_switch_id;

  @FXML
  private Rectangle lose_rec_id;

  @FXML
  private Button conti_watch_id;

  @FXML
  private Button abcd_id;

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
  private TextArea terri_info;

  @FXML
  private Button coffeeland_id;

  @FXML
  private Text coffeeland_size;

  @FXML
  private Text coffeeland_unit;

  @FXML
  private Button done_id;

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
  private Button more_action_id;

  @FXML
  private Button more_unit_id;

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
  private Text scadrial_size;

  @FXML
  private Text scadrial_unit;

  @FXML
  private Text server_id;

  @FXML
  private TextField sourceT_id;

  @FXML
  private TextField sourceT_id1;

  @FXML
  private Button ok_id;

  @FXML
  private Text lose_text_id;

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

  public void setplayerInfor() {
    Client2 client = clientController.clientMap.get(gameid);
    if (client.getPlayer().getName().equals("player 0")) {
      player_id.setText("Yellow");
    } else if (client.getPlayer().getName().equals("player 1")) {
      player_id.setText("Blue");
    } else if (client.getPlayer().getName().equals("player 2")) {
      player_id.setText("Green");
    } else if (client.getPlayer().getName().equals("player 3")) {
      player_id.setText("Orange");
    } else if (client.getPlayer().getName().equals("player 4")) {
      player_id.setText("Red");
    }
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
    for (Text t : ui_unit_map.values()) {
      t.setStroke(Color.BLACK);
      t.setStrokeWidth(1);
    }
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
    for (Text t : ui_size_map.values()) {
      t.setStroke(Color.BLACK);
      t.setStrokeWidth(1);
    }
  }

  // Initializes the Territory hover display info to unknown message
  protected void initHoverInfoMap() {
    String unknownTerritoryInfo = "???";
    territoryHoverInfoMap.put("Korriban", "Korriban\n" + unknownTerritoryInfo);
    territoryHoverInfoMap.put("Mustafar", "Mustafar\n" + unknownTerritoryInfo);
    territoryHoverInfoMap.put("Death Star", "Death Star\n" + unknownTerritoryInfo);
    territoryHoverInfoMap.put("Dagobah", "Dagobah\n" + unknownTerritoryInfo);
    territoryHoverInfoMap.put("Hoth", "Hoth\n" + unknownTerritoryInfo);
    territoryHoverInfoMap.put("Tatooine", "Tatooine\n" + unknownTerritoryInfo);
    territoryHoverInfoMap.put("Alderaan", "Alderaan\n" + unknownTerritoryInfo);
    territoryHoverInfoMap.put("Coruscant", "Coruscant\n" + unknownTerritoryInfo);
    territoryHoverInfoMap.put("Yavin4", "Yavin4\n" + unknownTerritoryInfo);
    territoryHoverInfoMap.put("Kamino", "Kaminl\n" + unknownTerritoryInfo);
    territoryHoverInfoMap.put("Naboo", "Naboo\n" + unknownTerritoryInfo);
    territoryHoverInfoMap.put("Kashyyyk", "Kashyyyk\n" + unknownTerritoryInfo);
    territoryHoverInfoMap.put("Bespin", "Bespin\n" + unknownTerritoryInfo);
    territoryHoverInfoMap.put("Dantoowin", "Dantoowin\n" + unknownTerritoryInfo);
    territoryHoverInfoMap.put("Felucia", "Felucia\n" + unknownTerritoryInfo);
  }

  public int getUnitTypeNumber(JSONArray ja_unit, int unit, String type) throws JSONException {
    int num = 0;
    try {
      if (ja_unit.getJSONObject(unit).has(type)) {
        num = ja_unit.getJSONObject(unit).getInt(type);
      } else {
        num = 0;
      }
    } catch (JSONException e) {
      return num;
    }
    return num;
  }

  public void setTerritoryTotalUnit(String T_name, JSONArray ja_unit) {
    String hover;
    int A, B, C, D, E, F, G;
    A = getUnitTypeNumber(ja_unit, 0, "A");
    B = getUnitTypeNumber(ja_unit, 1, "B");
    C = getUnitTypeNumber(ja_unit, 2, "C");
    D = getUnitTypeNumber(ja_unit, 3, "D");
    E = getUnitTypeNumber(ja_unit, 4, "E");
    F = getUnitTypeNumber(ja_unit, 5, "F");
    G = getUnitTypeNumber(ja_unit, 6, "G");

    hover = String.valueOf(A + B + C + D + E + F + G);
    ui_unit_map.get(T_name).setText(hover);
  }

  /**
   * Collects a set of the names of territories that are visible to the player by
   * onwership or adjacency
   * 
   * @param clientPlayer is the player object of the current client
   * @param gameMap      is a JSONArray of all the player in the game
   * @return Set<String> a set of all the territories that are visible to the
   *         clientPlayer
   */
  protected Set<String> collectOwnedAndAdjacentTerritories(Player clientPlayer, JSONArray gameMap) {
    Set<String> toDisplay = new HashSet<String>();
    for (int i = 0; i < gameMap.length(); i++) {
      JSONObject playerJSON = gameMap.getJSONObject(i);
      JSONObject playerAttributes = playerJSON.getJSONObject("Player");
      String playerName = playerAttributes.getString("Name");
      if (clientPlayer.getName().equals(playerName)) {
        JSONArray territoryArray = playerAttributes.getJSONArray("Territories");
        for (int j = 0; j < territoryArray.length(); j++) {
          JSONObject territoryJSON = territoryArray.getJSONObject(j);
          JSONObject territoryAttributes = territoryJSON.getJSONObject("Territory");
          String territoryName = territoryAttributes.getString("Name");
          toDisplay.add(territoryName);
          JSONArray neighborsArray = territoryAttributes.getJSONArray("Neighbors");
          for (int k = 0; k < neighborsArray.length(); k++) {
            String neighborName = neighborsArray.getString(k);
            toDisplay.add(neighborName);
          }
        }
      }
    }
    return toDisplay;
  }

  /**
   * Collects a set of all territories the clientPlayer has a spy in
   * 
   * @param clientPlayer is the player object of the current client
   * @return Set<String> of the names of territories the player has at least one
   *         spy in
   */
  protected Set<String> collectSpyLocations(Player clientPlayer) {
    StringBuilder sb = new StringBuilder();
    sb.append("Spys: ");
    Set<String> spyLocations = new HashSet<String>();
    Iterator<SpyUnit> it = clientPlayer.spyIterator();
    while (it.hasNext()) {
      SpyUnit spy = it.next();
      spyLocations.add(spy.getLocation().getName());
      sb.append(spy.getLocation().getName());
    }
    spy_info_id.setText(sb.toString());
    return spyLocations;
  }

  /**
   * Removes cloaked territories from a Set of territories
   * 
   * @param clientPlayer       is the player object of the current client
   * @param gameMap            is a JSONArray of all the player in the game
   * @param currentTerritories set of all territories adjacent to, owned, and
   *                           contains spy
   */
  protected void removeCloakedTerritories(Player clientPlayer, JSONArray gameMap, Set<String> visibleTerritories) {
    for (int i = 0; i < gameMap.length(); i++) {
      JSONObject playerJSON = gameMap.getJSONObject(i);
      JSONObject playerAttributes = playerJSON.getJSONObject("Player");
      JSONArray territoryArray = playerAttributes.getJSONArray("Territories");
      for (int j = 0; j < territoryArray.length(); j++) {
        JSONObject territoryJSON = territoryArray.getJSONObject(j);
        JSONObject territoryAttributes = territoryJSON.getJSONObject("Territory");
        String territoryName = territoryAttributes.getString("Name");
        String territoryOwner = territoryAttributes.getString("Owner");
        Boolean cloaked = territoryAttributes.getBoolean("Cloaked");
        if (!territoryOwner.equals(clientPlayer.getName()) && cloaked) {
          visibleTerritories.remove(territoryName);
        }
      }
    }
  }

  /**
   * Collects a set of the names of ALL territories that are visible to the player
   * 
   * @param clientPlayer is the player object of the current client
   * @param gameMap      is a JSONArray of all the player in the game
   * @return Set<String> a set of all the territories that are visible to the
   *         clientPlayer
   */
  protected Set<String> collectVisibleTerritories(Player clientPlayer, JSONArray gameMap) {
    Set<String> toDisplay = collectOwnedAndAdjacentTerritories(clientPlayer, gameMap);
    removeCloakedTerritories(clientPlayer, gameMap, toDisplay);
    Set<String> spyLocations = collectSpyLocations(clientPlayer);
    toDisplay.addAll(spyLocations);
    return toDisplay;
  }

  /**
   * Updates displayed information for a given territory (by its attributes
   * JSONObject)
   * 
   * @param territoryAttributes are the attributes of the territory to be updated
   *                            in the map display
   */
  protected void updateTerritoryDisplayInfo(JSONObject territoryAttributes) {
    String territoryName = territoryAttributes.getString("Name");
    String territoryOwner = territoryAttributes.getString("Owner");
    System.out.print("\nown: " + territoryOwner + "\n");
    System.out.print("\nterr: " + territoryName + "\n");
    JSONArray unitArray = territoryAttributes.getJSONArray("Units");
    ui_circle_map.get(territoryName).setStroke(playerColorMap.get(territoryOwner));
    ui_territory_map.get(territoryName).setTextFill(Color.WHITE);
    String territorySize = String.valueOf(territoryAttributes.getInt("Size"));
    ui_size_map.get(territoryName).setText(territorySize);
    setTerritoryTotalUnit(territoryName, unitArray);
  }

  /**
   * Updates the display information of the game map shown to the current client
   * based on their player information
   */
  public void updateMapDisplay() {
    Player clientPlayer = clientController.clientMap.get(gameid).getPlayer();
    JSONArray gameMap = mapInfo.getJSON().getJSONArray("Map");
    this.visibleTerritories = collectVisibleTerritories(clientPlayer, gameMap);
    for (int i = 0; i < gameMap.length(); i++) {
      JSONObject playerJSON = gameMap.getJSONObject(i);
      JSONObject playerAttributes = playerJSON.getJSONObject("Player");
      String playerName = playerAttributes.getString("Name");
      JSONArray territoryArray = playerAttributes.getJSONArray("Territories");
      // Update player for client player
      if (clientPlayer.getName().equals(playerName)) {
        String techLvl = playerAttributes.getString("Technology Level");
        String resources = playerAttributes.getString("Resources");
        player_id.setText(playerName);
        level_id.setText(techLvl);
        food_id.setText(resources);
      }
      // Update territory info if territory is visible
      for (int j = 0; j < territoryArray.length(); j++) {
        JSONObject territoryJSON = territoryArray.getJSONObject(j);
        JSONObject territoryAttributes = territoryJSON.getJSONObject("Territory");
        String territoryName = territoryAttributes.getString("Name");
        // ui_size_map.get(territoryName).setText("0");
        if (visibleTerritories.contains(territoryName)) {
          updateTerritoryDisplayInfo(territoryAttributes);
        } else {
          ui_circle_map.get(territoryName).setStroke(Color.GRAY);
          ui_territory_map.get(territoryName).setTextFill(Color.RED);
        }
      }
    }
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

  public void buildcombobox() {
    for (String orderType : this.orderTypes.keySet()) {
      select_action_id.getItems().add(orderType);
    }
    select_sourceU_id.getItems().add("A");
    select_sourceU_id.getItems().add("B");
    select_sourceU_id.getItems().add("C");
    select_sourceU_id.getItems().add("D");
    select_sourceU_id.getItems().add("E");
    select_sourceU_id.getItems().add("F");
    select_sourceU_id.getItems().add("G");

    select_TargetU_id.getItems().add("A");
    select_TargetU_id.getItems().add("B");
    select_TargetU_id.getItems().add("C");
    select_TargetU_id.getItems().add("D");
    select_TargetU_id.getItems().add("E");
    select_TargetU_id.getItems().add("F");
    select_TargetU_id.getItems().add("G");
  }

  public void invisible_actions(boolean boo) {
    action_text_id.setVisible(boo);
    select_action_id.setVisible(boo);
    ok_id.setVisible(boo);
    source_text_id.setVisible(boo);
    sourceT_id.setVisible(boo);
    target_text_id.setVisible(boo);
    sourceT_id1.setVisible(boo);
    source_unit_text_id.setVisible(boo);
    select_sourceU_id.setVisible(boo);
    target_unit_text_id.setVisible(boo);
    select_TargetU_id.setVisible(boo);
    unit_num_text_id.setVisible(boo);
    // select_unitNum_id.setVisible(boo);
    unit_num_id.setVisible(boo);
    more_unit_id.setVisible(boo);
    more_action_id.setVisible(boo);
    done_id.setVisible(boo);
  }

  @FXML
  public void initialize() {
    buildcombobox();
    init_territory_image();
    init_UI_territory_map();
    init_UI_unit_map();
    init_UI_size_map();
    init_UI_circle_map();
    initHoverInfoMap();
    invisible_actions(false);
    if (np == 2) {
      gameid = 2;
      invisible_whiteT();
      invisible_orangeT();
      invisible_greenT();
    } else if (np == 3) {
      gameid = 3;
      invisible_whiteT();
      invisible_orangeT();
    } else if (np == 4) {
      gameid = 4;
      invisible_whiteT();
    } else {
      gameid = 5;
    }
    setplayerInfor();
    sourceT_id.setDisable(true);
    sourceT_id1.setDisable(true);
    if (justAssignedUnits)
      // init_map_for_game();
      updateMapDisplay();
  }

  public void selectSourceTerritory(String territory_action_sourcename) {
    input_T_source = territory_action_sourcename;
    sourceT_id.setText(territory_action_sourcename);
    broadcast_id.setText("Source Territory picked: " + territory_action_sourcename
        + ", if applicable please click the territory you like to use as target");
  }

  public void selectTargetTerritory(String territory_action_targetname) {
    input_T_target = territory_action_targetname;
    sourceT_id1.setText(territory_action_targetname);
    broadcast_id.setText("Target Territory picked: " + territory_action_targetname
        + ", please input the types of units you like to move/attack");
  }

  public void source_or_target(String chosen_name) {
    if ((actioninput.equals("MA") || actioninput.equals("UU") || actioninput.equals("US")) && input_T_source == null) {
      selectSourceTerritory(chosen_name);
    } else if ((actioninput.equals("MA") || actioninput.equals("SM")) && input_T_source != null) {
      selectTargetTerritory(chosen_name);
    }
  }

  @FXML
  void click_appear(ActionEvent event) {
    if (flag == 0) {
      invisible_actions(true);
      flag = 1;
    } else {
      invisible_actions(false);
      flag = 0;
    }
  }

  @FXML
  void click_abcd(ActionEvent event) {
    source_or_target(abcd_id.getText());
  }

  @FXML
  void click_bagel(ActionEvent event) {
    source_or_target(bagel_id.getText());
  }

  @FXML
  void click_coffeeland(ActionEvent event) {
    source_or_target(coffeeland_id.getText());
  }

  @FXML
  void click_duke(ActionEvent event) {
    source_or_target(duke_id.getText());
  }

  @FXML
  void click_elantris(ActionEvent event) {
    source_or_target(elantris_id.getText());
  }

  @FXML
  void click_gondor(ActionEvent event) {
    source_or_target(gondor_id.getText());
  }

  @FXML
  void click_hogwarts(ActionEvent event) {
    source_or_target(hogwarts_id1.getText());
  }

  @FXML
  void click_lookout(ActionEvent event) {
    source_or_target(lookout_id.getText());
  }

  @FXML
  void click_midkemia(ActionEvent event) {
    source_or_target(midkemia_id.getText());
  }

  @FXML
  void click_mordor(ActionEvent event) {
    source_or_target(mordor_id.getText());
  }

  @FXML
  void click_narnia(ActionEvent event) {
    source_or_target(narnia_id.getText());
  }

  @FXML
  void click_outlook(ActionEvent event) {
    source_or_target(outlook_id.getText());
  }

  @FXML
  void click_oz(ActionEvent event) {
    source_or_target(oz_id.getText());
  }

  @FXML
  void click_roshar(ActionEvent event) {
    source_or_target(roshar_id.getText());
  }

  @FXML
  void click_scadrial(ActionEvent event) {
    source_or_target(scadrial_id.getText());
  }

  public void getmap(NewClientController cc, int gameId) throws IOException, ClassNotFoundException {
    System.out.println("Inside Map");
    UIMapDisplayInfo uiMap = cc.clientDisplayCommonMessage(gameId);
  }

  @FXML
  void onclick_done(ActionEvent event) throws IOException, ClassNotFoundException {
    // To Do funtion
    OrderList list = clientController.createOrdersList(np, orders);
    clientController.clientSendOrder(np, list);
    this.playerTechUpdateIssued = false;
    orders.clear();
    // clientController.clientDisplayCommonMessage(np);
    clientController.clientReceivePlayer(np);
    Iterator<SpyUnit> it = clientController.clientMap.get(np).getPlayer().spyIterator();
    System.out.println("Displaying Spies: ");
    while (it.hasNext()) {
      SpyUnit spy = it.next();
      System.out.println("Spy Location: " + spy.getLocation().getName());
    }
    System.out.println("\n");
    String summary = clientController.clientReceiveTurnSummary(np);
    broadcast_id.setText(summary);
    System.out.println("Turn summary: " + summary);
    HashMap<String, Boolean> checkLoseMap = clientController.clientReceiveCheckLoseMap(np);
    if (clientController.clientMap.get(np).getPlayer().playerCanCloak()) {
      select_action_id.getItems().remove("Unlock Cloaking");
    }
    boolean winOrLose = clientController.winOrLoseCheck(np, checkLoseMap);
    System.out.println("Did player lose in this round?? : " + winOrLose);
    if (winOrLose) {
      // bring up the window
      System.out.println("Player has lost, displaying the appropriate windows....");
      lose_rec_id.setVisible(true);
      // conti_watch_id.setVisible(true);
      lose_game_switch_id.setVisible(true);
      lose_text_id.setVisible(true);
      clientController.informLoser(np, "E");

    } else {
      clientController.informLoser(np, "continue");

    }
    String win = clientController.clientWin(np);
    System.out.println("WIN STATUS: " + win);
    if (win == null) {
      this.mapInfo = clientController.clientDisplayCommonMessage(np);
      // init_map_for_game();
      updateMapDisplay();
    } else {
      clientController.gameStatus.put(np, true);
      lose_rec_id.setVisible(true);
      lose_text_id.setVisible(true);
      lose_text_id.setText("You won the game :)");
      lose_game_switch_id.setVisible(true);
    }
  }

  @FXML
  void onclick_conti_watch(ActionEvent event) throws IOException, ClassNotFoundException {
    try {
      select_action_id.setDisable(true);
      ok_id.setDisable(true);
      sourceT_id.setDisable(true);
      sourceT_id1.setDisable(true);
      select_sourceU_id.setDisable(true);
      select_TargetU_id.setDisable(true);
      // select_unitNum_id.setDisable(true);
      unit_num_id.setDisable(true);
      more_unit_id.setDisable(true);
      more_action_id.setDisable(true);
      done_id.setDisable(true);
      clientController.clientSendOrder(np, null);
      clientController.clientReceivePlayer(np);
      String summary = clientController.clientReceiveTurnSummary(np);
      System.out.println("Turn summary: " + summary);
      HashMap<String, Boolean> checkLoseMap = clientController.clientReceiveCheckLoseMap(np);
      boolean winOrLose = clientController.winOrLoseCheck(np, checkLoseMap);
      System.out.println("Did player lose in this round?? : " + winOrLose);
      // bring up the widow
      System.out.println("Player has lost, displaying the appropriate windows....");
      lose_rec_id.setVisible(true);
      conti_watch_id.setVisible(true);
      lose_game_switch_id.setVisible(true);
      lose_text_id.setVisible(true);
      clientController.informLoser(np, "continue");

      clientController.clientWin(np);
      getmap(this.clientController, np);
    } catch (IllegalArgumentException e) {
      clientController.gameStatus.put(np, true);
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/back.fxml"));
      HashMap<Class<?>, Object> controllers = new HashMap<>();
      // controllers.put(choose_game_room.class, new choose_game_room(client));
      controllers.put(choose_game_room.class, new choose_game_room(clientController));

      loader.setControllerFactory(c -> {
        return controllers.get(c);
      });
      // BorderPane bp = loader.load();
      Pane bp = loader.load();
      stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      Scene scene = new Scene(bp, 600, 400);
      stage.setScene(scene);
      stage.show();

    }

  }

  @FXML
  void onclick_loseswitchgame(ActionEvent event) throws IOException, ClassNotFoundException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/back.fxml"));
    // clientController.informLoser(np,"E");
    clientController.gameStatus.put(np, true);
    Player testPlayer = new BasicPlayer("clientPlayer");
    InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    BufferedReader inputReader = new BufferedReader(inputStreamReader);
    String hostname = "vcm-24503.vm.duke.edu";
    int portNum = 1641;
    // Client client = new Client(testPlayer, inputReader, System.out, hostname,
    // portNum, null, null, null);

    HashMap<Class<?>, Object> controllers = new HashMap<>();
    // controllers.put(choose_game_room.class, new choose_game_room(client));
    controllers.put(choose_game_room.class, new choose_game_room(clientController));

    loader.setControllerFactory(c -> {
      return controllers.get(c);
    });
    // BorderPane bp = loader.load();
    Pane bp = loader.load();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(bp, 600, 400);
    stage.setScene(scene);
    stage.show();

  }

  @FXML
  void onclick_exit(ActionEvent event) throws IOException, ClassNotFoundException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/back.fxml"));
    Player testPlayer = new BasicPlayer("clientPlayer");
    InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    BufferedReader inputReader = new BufferedReader(inputStreamReader);
    String hostname = "vcm-24503.vm.duke.edu";
    int portNum = 1641;
    // Client client = new Client(testPlayer, inputReader, System.out, hostname,
    // portNum, null, null, null);

    HashMap<Class<?>, Object> controllers = new HashMap<>();
    // controllers.put(choose_game_room.class, new choose_game_room(client));
    controllers.put(choose_game_room.class, new choose_game_room(clientController));

    loader.setControllerFactory(c -> {
      return controllers.get(c);
    });
    // BorderPane bp = loader.load();
    Pane bp = loader.load();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(bp, 600, 400);
    stage.setScene(scene);
    stage.show();

  }

  @FXML
  void onclick_more_action(ActionEvent event) throws IOException {
    // TO DO METHOD
    // send the current action to client and create a order??
    try {
      // String orderType = action_id.getText();
      String orderType = select_action_id.getValue().toString();
      orderType = this.orderTypes.get(orderType);
      System.out.println("OrderType: " + orderType);
      if (orderType.equalsIgnoreCase("M")) {
        String sourceName = input_T_source;
        String TargetName = input_T_target;
        System.out.println("Source: " + sourceName + " Target: " + TargetName);
        HashMap<Integer, Integer> units_temp = new HashMap<Integer, Integer>();
        units_temp.putAll(units);
        HashMap<Integer, Integer> unitsMap = units_temp;
        Order o = clientController.createSingleMoveOrder(np, sourceName, TargetName, unitsMap);
        orders.add(o);
        units.clear();
      } else if (orderType.equalsIgnoreCase("A")) {
        String sourceName = input_T_source;
        String TargetName = input_T_target;
        System.out.println("Source: " + sourceName + " Target: " + TargetName);
        HashMap<Integer, Integer> units_temp = new HashMap<Integer, Integer>();
        units_temp.putAll(units);
        HashMap<Integer, Integer> unitsMap = units_temp;
        Order o = clientController.createSingleAttackOrder(np, sourceName, TargetName, unitsMap);
        orders.add(o);
        units.clear();
      } else if (orderType.equalsIgnoreCase("UU")) {
        String sourceName = input_T_source;
        String currentTypeName = select_sourceU_id.getValue().toString();
        String targetTypeName = select_TargetU_id.getValue().toString();
        Order o = clientController.createSingleUnitUpgradeOrder(np, sourceName, currentTypeName, targetTypeName);
        orders.add(o);
      } else if (orderType.equalsIgnoreCase("TU")) {
        Order o = clientController.createTechnologyUpgradeOrder(np);
        orders.add(o);
      } else if (orderType.equalsIgnoreCase("UC")) {
        Order o = clientController.createUnlockCloakOrder(np);
        orders.add(o);
      } else if (orderType.equalsIgnoreCase("US")) {
        String sourceName = input_T_source;
        Order o = clientController.createSingleSpyUpgradeOrder(np, sourceName);
        orders.add(o);
      } else if (orderType.equalsIgnoreCase("R")) {
        String targetName = sourceT_id.getText();
        // String fundingString = select_unitNum_id.getValue().toString();
        String fundingString = unit_num_id.getText();
        Order o = clientController.createRevolutionOrder(np, targetName, fundingString);
        orders.add(o);
      } else if (orderType.equalsIgnoreCase("SM")) {
        String sourceName = input_T_source;
        String targetName = input_T_target;
        Order o = clientController.createSingleSpyMoveOrder(np, sourceName, targetName);
        orders.add(o);
      } else if (orderType.equalsIgnoreCase("CT")) {
        String sourceName = input_T_source;
        Order o = clientController.createSingleCloakTerritoryOrder(np, sourceName);
        orders.add(o);
      }
      select_action_id.getSelectionModel().clearSelection();
      sourceT_id.clear();
      sourceT_id1.clear();
      select_sourceU_id.getSelectionModel().clearSelection();
      select_TargetU_id.getSelectionModel().clearSelection();
      // select_unitNum_id.getSelectionModel().clearSelection();
      unit_num_id.clear();
      actioninput = null;
      input_T_source = null;
      input_T_target = null;
      select_TargetU_id.setDisable(false);
      select_sourceU_id.setDisable(false);
      // select_unitNum_id.setDisable(false);
      unit_num_id.setDisable(false);
      more_unit_id.setDisable(false);
    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText(e.toString());
      alert.show();
      select_action_id.getSelectionModel().clearSelection();
      sourceT_id.clear();
      sourceT_id1.clear();
      input_T_source = null;
      input_T_target = null;
      select_sourceU_id.getSelectionModel().clearSelection();
      select_TargetU_id.getSelectionModel().clearSelection();
      // select_unitNum_id.getSelectionModel().clearSelection();
      unit_num_id.clear();
    }
  }

  @FXML
  void onclick_more_unit(ActionEvent event) throws IOException {
    // TO DO METHOD
    // send the current action to client and create a order??

    // String action = action_id.getText();
    String action = select_action_id.getValue().toString();
    action = this.orderTypes.get(action);
    // try {
    if (action.equalsIgnoreCase("M") || action.equalsIgnoreCase("A")) {
      String unitType = select_sourceU_id.getValue().toString();
      // String count = select_unitNum_id.getValue().toString();
      String count = unit_num_id.getText();
      Integer unitCount = clientController.getPositiveInteger(count);
      if (this.unitTypesToBonus.containsKey(unitType.toUpperCase())) {
        System.out.println("Number of Units Entered: " + count);
        Integer bonus = this.unitTypesToBonus.get(unitType.toUpperCase());
        units.put(bonus, unitCount);
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Invalid Unit Type");
        alert.show();
        select_sourceU_id.getSelectionModel().clearSelection();
        // select_unitNum_id.getSelectionModel().clearSelection();
        unit_num_id.clear();
      }

    }
  }

  @FXML
  void onclick_ok(ActionEvent event) {
    String action = select_action_id.getValue().toString();
    action = this.orderTypes.get(action);
    select_TargetU_id.setDisable(false);
    select_sourceU_id.setDisable(false);
    // select_unitNum_id.setDisable(false);
    unit_num_id.setDisable(false);
    more_action_id.setDisable(false);
    more_unit_id.setDisable(false);
    done_id.setDisable(false);
    if (action.equalsIgnoreCase("M") || action.equalsIgnoreCase("A")) {
      broadcast_id.setText("Move action selected, next please click your source territory");
      actioninput = "MA";
      select_TargetU_id.setDisable(true);
    } else if (action.equalsIgnoreCase("UU")) {
      broadcast_id
          .setText("Upgrade Unit action selected, next please click on territory whose unit you wish to upgrade");
      actioninput = "UU";
      // select_unitNum_id.setDisable(true);
      unit_num_id.setDisable(true);
      more_unit_id.setDisable(true);
      sourceT_id1.setDisable(true);
    } else if (action.equalsIgnoreCase("TU")) {
      if (this.playerTechUpdateIssued) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("You can only issue one player upgrade request per turn :(");
        alert.show();
        select_action_id.getSelectionModel().clearSelection();
      } else {
        broadcast_id.setText("Player Tech Upgrade action selected, order created!");
        this.playerTechUpdateIssued = true;
        sourceT_id.setDisable(true);
        sourceT_id1.setDisable(true);
        select_sourceU_id.setDisable(true);
        select_TargetU_id.setDisable(true);
        // select_unitNum_id.setDisable(true);
        unit_num_id.setDisable(true);
        more_unit_id.setDisable(true);
      }
    } else if (action.equalsIgnoreCase("US")) {
      broadcast_id.setText("Request Spy action selected, next select territory in which spy should be created.");
      actioninput = "US";
      sourceT_id1.setDisable(true);
      select_sourceU_id.setDisable(true);
      select_TargetU_id.setDisable(true);
      // select_unitNum_id.setDisable(true);
      unit_num_id.setDisable(true);
      more_unit_id.setDisable(true);
    } else if (action.equalsIgnoreCase("SM")) {
      broadcast_id.setText("Move Spy action selected, please click your source territory");
      actioninput = "MA";
      select_sourceU_id.setDisable(true);
      select_TargetU_id.setDisable(true);
      // select_unitNum_id.setDisable(true);
      unit_num_id.setDisable(true);
      more_unit_id.setDisable(true);
    } else if (action.equalsIgnoreCase("CT")) {
      broadcast_id
          .setText("CT (Cloak Territory) action selected, please select the territory you wish to cloak by clicking");
      actioninput = "US";
      sourceT_id1.setDisable(true);
      select_sourceU_id.setDisable(true);
      select_TargetU_id.setDisable(true);
      // select_unitNum_id.setDisable(true);
      unit_num_id.setDisable(true);
      more_unit_id.setDisable(true);
    } else if (action.equalsIgnoreCase("UC")) {
      broadcast_id.setText("UC action selected, order created!");
      actioninput = "UC";
      sourceT_id.setDisable(true);
      sourceT_id1.setDisable(true);
      select_sourceU_id.setDisable(true);
      select_TargetU_id.setDisable(true);
      // select_unitNum_id.setDisable(true);
      unit_num_id.setDisable(true);
      more_unit_id.setDisable(true);
    } else if (action.equalsIgnoreCase("R")) {
      broadcast_id
          .setText("R(Revolution)action selected. Next, select the territory where you want to instigate a Revolution");
      actioninput = "US";
      sourceT_id.setDisable(true);
      sourceT_id1.setDisable(true);
      select_sourceU_id.setDisable(true);
      select_TargetU_id.setDisable(true);
      // select_unitNum_id.setDisable(true);
      more_unit_id.setDisable(true);
    } else {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText("Invalid Order Type");
      alert.show();
      select_action_id.getSelectionModel().clearSelection();
    }
    sourceT_id.clear();
    sourceT_id1.clear();
    select_sourceU_id.getSelectionModel().clearSelection();
    select_TargetU_id.getSelectionModel().clearSelection();
    // select_unitNum_id.getSelectionModel().clearSelection();
    unit_num_id.clear();
  }

  @FXML
  void onclick_switchgame(ActionEvent event) throws IOException, ClassNotFoundException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/back.fxml"));
    HashMap<Class<?>, Object> controllers = new HashMap<>();
    // controllers.put(choose_game_room.class, new choose_game_room(client));
    controllers.put(choose_game_room.class, new choose_game_room(clientController));

    loader.setControllerFactory(c -> {
      return controllers.get(c);
    });
    // BorderPane bp = loader.load();
    Pane bp = loader.load();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(bp, 600, 400);
    stage.setScene(scene);
    stage.show();
  }

  public long getDifferentUnitTypeNum(Map<Integer, Long> unitMap, int i) {
    long num;
    if (unitMap.get(i) == null) {
      num = 0;
    } else {
      num = unitMap.get(i);
    }
    return num;
  }

  /**
   * Parses map display JSON for info about territory with target name and updates
   * territory display info map based on visible territories
   *
   * @param targetName is the name of the target territory whos info is to be
   *                   displayed
   */
  public void updateHoverMessage(String targetName) {
    if (!visibleTerritories.contains(targetName)) {
      return; // Not visible, do not update info
    }
    StringBuilder sb = new StringBuilder();
    sb.append(targetName + "\n");
    JSONObject mapJSON = mapInfo.getJSON();
    JSONArray mapPlayerArray = mapJSON.getJSONArray("Map");
    for (int i = 0; i < mapPlayerArray.length(); i++) {
      JSONObject playerJSON = mapPlayerArray.getJSONObject(i);
      JSONObject playerAttributes = playerJSON.getJSONObject("Player");
      JSONArray territoryArray = playerAttributes.getJSONArray("Territories");
      for (int k = 0; k < territoryArray.length(); k++) {
        JSONObject territoryJSON = territoryArray.getJSONObject(k);
        JSONObject territoryAttributes = territoryJSON.getJSONObject("Territory");
        String territoryName = territoryAttributes.getString("Name");
        if (territoryName.equals(targetName)) {
          JSONArray unitsArray = territoryAttributes.getJSONArray("Units");
          for (int h = 0; h < 7; h++) {
            int unit_num = getUnitTypeNumber(unitsArray, h, Character.toString((char) (h + 65)));
            sb.append(Character.toString((char) (h + 65)) + ": " + unit_num + "\n");
          }
          territoryHoverInfoMap.replace(targetName, sb.toString());
        }
      }
    }
  }

  @FXML
  void hover_abcd(MouseEvent event) {
    updateHoverMessage(abcd_id.getText());
    terri_info.setText(territoryHoverInfoMap.get(abcd_id.getText()));
  }

  @FXML
  void hover_bagel(MouseEvent event) {
    updateHoverMessage(bagel_id.getText());
    terri_info.setText(territoryHoverInfoMap.get(bagel_id.getText()));
  }

  @FXML
  void hover_coffeeland(MouseEvent event) {
    updateHoverMessage(coffeeland_id.getText());
    terri_info.setText(territoryHoverInfoMap.get(coffeeland_id.getText()));
  }

  @FXML
  void hover_duke(MouseEvent event) {
    updateHoverMessage(duke_id.getText());
    terri_info.setText(territoryHoverInfoMap.get(duke_id.getText()));
  }

  @FXML
  void hover_elantris(MouseEvent event) {
    updateHoverMessage(elantris_id.getText());
    terri_info.setText(territoryHoverInfoMap.get(elantris_id.getText()));
  }

  @FXML
  void hover_gondor(MouseEvent event) {
    updateHoverMessage(gondor_id.getText());
    terri_info.setText(territoryHoverInfoMap.get(gondor_id.getText()));
  }

  @FXML
  void hover_hogwarts(MouseEvent event) {
    updateHoverMessage(hogwarts_id1.getText());
    terri_info.setText(territoryHoverInfoMap.get(hogwarts_id1.getText()));
  }

  @FXML
  void hover_lookout(MouseEvent event) {
    updateHoverMessage(lookout_id.getText());
    terri_info.setText(territoryHoverInfoMap.get(lookout_id.getText()));
  }

  @FXML
  void hover_midkemia(MouseEvent event) {
    updateHoverMessage(midkemia_id.getText());
    terri_info.setText(territoryHoverInfoMap.get(midkemia_id.getText()));
  }

  @FXML
  void hover_mordor(MouseEvent event) {
    updateHoverMessage(mordor_id.getText());
    terri_info.setText(territoryHoverInfoMap.get(mordor_id.getText()));
  }

  @FXML
  void hover_narnia(MouseEvent event) {
    updateHoverMessage(narnia_id.getText());
    terri_info.setText(territoryHoverInfoMap.get(narnia_id.getText()));
  }

  @FXML
  void hover_outlook(MouseEvent event) {
    updateHoverMessage(outlook_id.getText());
    terri_info.setText(territoryHoverInfoMap.get(outlook_id.getText()));
  }

  @FXML
  void hover_oz(MouseEvent event) {
    updateHoverMessage(oz_id.getText());
    terri_info.setText(territoryHoverInfoMap.get(oz_id.getText()));
  }

  @FXML
  void hover_roshar(MouseEvent event) {
    updateHoverMessage(roshar_id.getText());
    terri_info.setText(territoryHoverInfoMap.get(roshar_id.getText()));
  }

  @FXML
  void hover_scadrial(MouseEvent event) {
    updateHoverMessage(scadrial_id.getText());
    terri_info.setText(territoryHoverInfoMap.get(scadrial_id.getText()));
  }

}
