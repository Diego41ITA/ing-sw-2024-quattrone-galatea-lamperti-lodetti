package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.model.gameDataManager.Player;
import it.polimi.ingsw.view.FsmGame;

import it.polimi.ingsw.view.GUI.controller.abstractControllers.AbstractController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class WaitStartController extends AbstractController {

    @FXML
    private TableView<PlayerViewModel> tableView;

    @FXML
    private TableColumn<PlayerViewModel, String> nicknameColumn;

    @FXML
    private TableColumn<PlayerViewModel, Color> colorColumn;

    @FXML
    private Text gameid;

    public static class PlayerViewModel {
        private final Player player;
        private final SimpleStringProperty nickname;
        private final ObjectProperty<Color> color;

        public PlayerViewModel(Player player) {
            this.player = player;
            this.nickname = new SimpleStringProperty(player.getNick());
            this.color = new SimpleObjectProperty<>(player.getColor());
        }

        public String getNickname() {
            return nickname.get();
        }

        public SimpleStringProperty nicknameProperty() {
            return nickname;
        }

        public Color getColor() {
            return color.get();
        }

        public ObjectProperty<Color> colorProperty() {
            return color;
        }

        public Player getPlayer() {
            return player;
        }
    }

    private void fillTableView() {
        // Initialize columns to work with PlayerViewModel
        nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));

        // Create list of PlayerViewModel
        List<Player> playerList =  getGameView().getPlayers();
        List<PlayerViewModel> viewModelList = new ArrayList<>();

        for (Player player : playerList) {
            viewModelList.add(new PlayerViewModel(player));
        }

        // Create ObservableList of PlayerViewModel
        ObservableList<PlayerViewModel> players = FXCollections.observableArrayList(viewModelList);

        // Set the items for the table view
        tableView.setItems(players);
    }

    @Override
    public void setUpController(FsmGame updatedGame) {
        setGame(updatedGame);
        fillTableView();
        gameid.setText("Wait for the start of: " + getGameView().getId());
    }
}
