package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.networking.rmi.ClientRMI;
import it.polimi.ingsw.networking.socket.client.ClientSocket;
import javafx.application.Platform;
import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.view.UI;
import it.polimi.ingsw.view.input.InputGui;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import it.polimi.ingsw.view.FsmGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/* All'interno di questa classe implementeremo i metodi della UI per la GUI.
All'interno di questi metodi ci preoccuperemo anche di caricare la scena attraverso un FXMLLoader.
Useremo all'interno di questi metodi i vari controllers delle scene.
La grafica verrà runnata su thread
 */
public class Gui extends Application implements UI {
    private Stage primaryStage;
    private FsmGame flow;
    private ClientAction client;

    @Override
    public void start(Stage primaryStage) throws Exception {

        flow = new FsmGame(this, new InputGui());
        String typeConnection = getParameters().getUnnamed().getFirst();
        switch (typeConnection){
            case "rmi" -> this.client = new ClientRMI(flow, getParameters().getUnnamed().get(1));
            case "socket" -> this.client = new ClientSocket(flow, getParameters().getUnnamed().get(1));
        }
        flow.setClient(client);

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CodexNaturalis - PSP21");
        this.primaryStage.show();
        Thread myThread = new Thread(flow);
        myThread.start();
    }

    public void loadScene(String path) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                Parent root = loader.load();
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            } catch (IOException | NullPointerException e) {
                System.out.println("qualcosa si è rotto");
                e.getCause();
                e.printStackTrace();
            }
        });
    }

    @Override
    public void show_startingMenu() {
        loadScene("/scenes/Menu.fxml");
    }

    @Override
    public void show_message(String message) {

    }

    @Override
    public void show_initialCard(InitialCard card) {

    }

    @Override
    public void show_joinRandomGame() {

    }

    @Override
    public void show_requestPlayerColor(GameView gameView) {

    }

    @Override
    public void show_noAvailableGames() {

    }

    @Override
    public void show_createNewGame() {

    }

    @Override
    public void show_reconnectGame() {

    }

    @Override
    public void show_invalidIdGame() {

    }

    @Override
    public void show_NickAlreadyUsed(GameView gameView) {

    }

    @Override
    public void show_invalidNickToReconnect(String id) {

    }

    @Override
    public void show_currentPlayersStatus(GameView gameView) {

    }

    @Override
    public void show_playerColors(GameView gameView) {

    }

    @Override
    public void show_GameStatus(GameView gameView) {

    }

    @Override
    public void show_RequestPlayerNickName() {
        if(primaryStage != null)
            loadScene("/scenes/NickNameSetUp.fxml");
        else
            System.out.println("error primary stage is null");
    }

    @Override
    public void show_RequestGameId() {

    }

    @Override
    public void show_RequestNumberOfPlayers() {

    }

    @Override
    public void show_playerJoined(String id) {

    }

    @Override
    public void show_playerLeft(String playerNickName) {

    }

    @Override
    public void show_gameStarting(String id) {

    }

    @Override
    public void show_isYourTurn(GameView immutableModel) {

    }

    @Override
    public void show_playerHand(GameView immutableModel) {

    }

    @Override
    public void show_gameStation(GameView view) {

    }

    @Override
    public void show_invalidPlay() {

    }

    @Override
    public void show_notEnoughResources() {

    }

    @Override
    public String show_goalCard(GoalCard card) {

        return null;
    }

    @Override
    public String show_playableCard(PlayableCard card) {

        return null;
    }

    @Override
    public void show_tableOfDecks(GameView immutableModel) {

    }

    @Override
    public void show_lastTurn() {

    }

    @Override
    public void show_pointTable(GameView immutableModel) {

    }

    @Override
    public void show_gameOver() {

    }

    @Override
    public void show_youWin() {

    }

    @Override
    public void show_youLose() {

    }

    @Override
    public void show_requestGoalCard(ArrayList<GoalCard> cards) {

    }

    @Override
    public void show_requestCardId() {

    }

    @Override
    public void show_winner(String name) {

    }

    @Override
    public void show_requestTypeToDraw() {

    }

    @Override
    public void show_drawFromWhere() {

    }

    @Override
    public void show_invalidChoice() {

    }

    @Override
    public void show_requestSide() {

    }

    @Override
    public void show_requestCoordinates() {

    }

    @Override
    public void show_connectionError() {

    }

    @Override
    public void show_invalidInput() {

    }

    @Override
    public void show_invalidCommand() {

    }

    @Override
    public void show_waitingOtherPlayers() {

    }

    @Override
    public void show_requestToLeave(){

    }

    public static void main(String[] args) {
        launch(args);
    }
}
