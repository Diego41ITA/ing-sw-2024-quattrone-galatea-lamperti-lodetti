package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.networking.rmi.ClientRMI;
import it.polimi.ingsw.networking.socket.client.ClientSocket;
import it.polimi.ingsw.view.GUI.controller.EndController;
import it.polimi.ingsw.view.GUI.controller.abstractControllers.AbstractController;
import it.polimi.ingsw.view.GUI.controller.PlayingSceneController;
import it.polimi.ingsw.view.GUI.controller.WaitingSceneController;
import javafx.application.Platform;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.view.UI;
import it.polimi.ingsw.view.input.InputGui;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import it.polimi.ingsw.view.FsmGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* All'interno di questa classe implementeremo i metodi della UI per la GUI.
All'interno di questi metodi ci preoccuperemo anche di caricare la scena attraverso un FXMLLoader.
Useremo all'interno di questi metodi i vari controllers delle scene.
La grafica verrà runnata su thread
 */
public class Gui extends Application implements UI {
    private Stage primaryStage;
    private FsmGame flow;
    private ClientAction client;

    private AbstractController abstractController;

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
        Image icon = new Image("/scenes/images/01.png");
        this.primaryStage.getIcons().add(icon);
        this.primaryStage.setResizable(false);
        this.primaryStage.setY(0);
        this.primaryStage.setX(150);
        //Thread myThread = new Thread(flow);
        //myThread.start();
        flow.start();

        //we need to catch the exception to show that the server crashed
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
            @Override
            public void uncaughtException(Thread t, Throwable e){
                System.out.println("connection to server" + client.getClass() + "lost");
                //System.out.println("you are going to be disconnected, please wait some moment and try to " +
                //        "restore you game!!!");
                //e.printStackTrace();
                //myThread.interrupt();
                flow.interrupt();
                //dovrà mostrare la scena di chiusura che gestirà close
                //primaryStage.close();
                Platform.exit();
            }
        });
    }

    public void loadScene(String path) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                Parent root = loader.load();

                AbstractController controller = loader.getController();

                primaryStage.setScene(new Scene(root));

                controller.setUpController(this.flow);

                abstractController = controller;
                primaryStage.show();
            } catch (IOException | NullPointerException e) {
                System.out.println("the scene: " + path +", doesn't provide a controller");
            }
        });
    }

    @Override
    public void show_startingMenu() {
        if(primaryStage != null)
            loadScene("/scenes/Menu.fxml");
        else
            System.out.println("error primary stage is null");;
    }

    @Override
    public void show_message(String message) {
        //non dovrebbe servire nel caso mettere un pop up
    }

    @Override
    public void show_initialCard(InitialCard card) {
        if(primaryStage != null) {
            loadScene("/scenes/InitialCard.fxml");
        }
        else
            System.out.println("error primary stage is null");
    }

    //mettere una scena oppure niente
    @Override
    public void show_joinRandomGame() {
    }

    @Override
    public void show_requestPlayerColor(GameView gameView) {
        if(primaryStage != null)
            loadScene("/scenes/SetColor.fxml");
        else
            System.out.println("error primary stage is null");
    }

    @Override
    public void show_noAvailableGames() {
        if(primaryStage != null)
            loadScene("/scenes/CreateGame.fxml");
        else
            System.out.println("error primary stage is null");
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
        /*if(primaryStage != null)
            loadScene("/scenes/waitStart.fxml");
        else
            System.out.println("error primary stage is null");*/
    }

    @Override
    public void show_GameStatus(GameView gameView) {
        if(primaryStage != null) {
            loadScene("/scenes/WaitingScene.fxml");
            WaitingSceneController controller = (WaitingSceneController) abstractController;
            Platform.runLater(controller::showGameSuspendedAlert);
        }
        else
            System.out.println("error primary stage is null");
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
        if(primaryStage != null)
            loadScene("/scenes/SetGameIdToRejoin.fxml");
        else
            System.out.println("error primary stage is null");
    }

    @Override
    public void show_RequestNumberOfPlayers() {
        if(primaryStage != null)
            loadScene("/scenes/SetNumberOfPlayers.fxml");
        else
            System.out.println("error primary stage is null");
    }

    @Override
    public void show_playerJoined(String id) {
        //un pop-up
    }

    @Override
    public void show_playerLeft(String playerNickName) {
        //un pop-up
    }

    @Override
    public void show_gameStarting(String id) {

    }

    @Override
    public void show_playingScene(GameView immutableModel) {
        if(primaryStage != null) {
            loadScene("/scenes/PlayingScene.fxml");
        }
        else
            System.out.println("error primary stage is null");
    }

    //si potrebbe modificare WaitingScene.fxml in modo che stampi il nome del giocatore corrente e basta.
    @Override
    public void show_isYourTurn(GameView immutableModel) {
        if(immutableModel.getCurrentPlayer().getNick().equals(this.flow.getNickname())){
            //un pop-up che dica è il tuo turno
            //oppure si aggiorna la gameStation
        }else{
            if(primaryStage != null) {
                loadScene("/scenes/WaitingScene.fxml");
            }
            else
                System.out.println("error primary stage is null");
        }
    }

    @Override
    public void show_playerHand(GameView immutableModel, String nickname) {

    }

    @Override
    public void show_gameStation(GameView view) {
    }

    @Override
    public void show_invalidPlay() {
        PlayingSceneController controller = (PlayingSceneController) abstractController;
        Platform.runLater(controller::showInvalidPlayAlert);
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

    //un pop-up che dica che è iniziato l'ultimo giro.
    @Override
    public void show_lastTurn() {
        Platform.runLater(abstractController::showLastTurnAlert);
    }

    //point table is in game station, if we have multiple show() methods that display the "everything" we can
    //refresh more frequently the interface.
    @Override
    public void show_pointTable(GameView immutableModel) {
    }

    @Override
    public void show_gameOver() {
        if(primaryStage != null)
            loadScene("/scenes/EndScene.fxml");
        else
            System.out.println("error primary stage is null");

    }

    @Override
    public void show_youWin() {
        //nothing, end scene already shows the winner
    }

    @Override
    public void show_youLose() {
        //nothing, end scene already shows the winner
    }

    @Override
    public void show_requestGoalCard(ArrayList<GoalCard> cards) {
        if(primaryStage != null) {
            loadScene("/scenes/PlayerGoal.fxml");
        }
        else
            System.out.println("error primary stage is null");
    }

    @Override
    public void show_requestCardId() {
    }

    @Override
    public void show_winner(List<String> name) {
        EndController controller = (EndController) abstractController;
        Platform.runLater(() -> controller.setWinnerNick(name.getFirst()));

    }

    @Override
    public void show_requestTypeToDraw() {
        //non serve
    }

    @Override
    public void show_drawFromWhere() {
        PlayingSceneController controller = (PlayingSceneController) abstractController;
        Platform.runLater(controller::showDrawAlert);
    }

    @Override
    public void show_invalidChoice() {

    }

    @Override
    public void show_requestSide() {
        //non serve?
    }

    @Override
    public void show_requestCoordinates() {
        //non serve
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
        if(primaryStage != null)
            loadScene("/scenes/waitStart.fxml");
        else
            System.out.println("error primary stage is null");
    }

    @Override
    public void show_requestToLeave(){
    }

    @Override
    public void show_abortGame(){
        if(primaryStage != null)
            loadScene("/scenes/AbortedGame.fxml");
        else
            System.out.println("error primary stage is null");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
