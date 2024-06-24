package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.networking.rmi.ClientRMI;
import it.polimi.ingsw.networking.socket.client.ClientSocket;
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

/**
 * This class is responsible for loading the JavaFX scenes and eventually calling methods of the relative controllers,
 * if needed. It extends {@link Application} and implements {@link UI}, to override all the methods needed to make the
 * application works both with GUI and TUI. Only some show_ methods are overridden, because all the others are needed
 * by the {@link it.polimi.ingsw.view.TUI.Cli}. This class handles various stages of the FSM with few scenes.
 */
public class Gui extends Application implements UI {
    /**
     *Primary stage of the scene.
     */
    private Stage primaryStage;
    /**
     *Fined state machine of the game.
     */
    private FsmGame flow;
    /**
     * It's needed to start the chosen type of protocol design.
     */
    private ClientAction client;
    /**
     * It stores the controller of the loaded scene.
     */
    private AbstractController abstractController;

    /**
     * It's the override of the start(Stage primaryStage) method of the {@link Application} class. It handles various
     * operations relative to JavaFX (setting the primaryStage, the title, the icon..); it also initializes and starts
     * the fined state machine of the game.
     * @param primaryStage
     * @throws Exception
     */
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

        flow.start();

        //we need to catch the exception to show that the server crashed
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
            @Override
            public void uncaughtException(Thread t, Throwable e){
                System.out.println("connection to server" + client.getClass() + "lost");

                flow.interrupt();

                Platform.exit();
            }
        });
    }

    /**
     * Loads a .fxml file, setting the corresponding scene to the primaryStage. It also calls the
     * setUpController(FsmGame flow) methods that initializes all the components and possible interaction in the scene.
     * @param path the relative path to the scene to load.
     */
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

    /**
     * It shows the nickname selection menu.
     */
    @Override
    public void show_RequestPlayerNickName() {
        if(primaryStage != null)
            loadScene("/scenes/NickNameSetUp.fxml");
        else
            System.out.println("error primary stage is null");
    }

    /**
     * It shows the starting menu.
     */
    @Override
    public void show_startingMenu() {
        if(primaryStage != null)
            loadScene("/scenes/Menu.fxml");
        else
            System.out.println("error primary stage is null");;
    }

    /**
     * It shows the initial card choosing menu.
     */
    @Override
    public void show_initialCard(InitialCard card) {
        if(primaryStage != null) {
            loadScene("/scenes/InitialCard.fxml");
        }
        else
            System.out.println("error primary stage is null");
    }

    /**
     * It shows the personal color choosing menu.
     */
    @Override
    public void show_requestPlayerColor(GameView gameView) {
        if(primaryStage != null)
            loadScene("/scenes/SetColor.fxml");
        else
            System.out.println("error primary stage is null");
    }

    /**
     * It shows the personal objective card selection screen.
     * @param cards the goal cards.
     */
    @Override
    public void show_requestGoalCard(ArrayList<GoalCard> cards) {
        if(primaryStage != null) {
            loadScene("/scenes/PlayerGoal.fxml");
        }
        else
            System.out.println("error primary stage is null");
    }

    /**
     * It shows the creation game menu.
     */
    @Override
    public void show_noAvailableGames() {
        if(primaryStage != null)
            loadScene("/scenes/CreateGame.fxml");
        else
            System.out.println("error primary stage is null");
    }

    /**
     * It shows a scene with all the relevant information to the players that are not currently waiting for the
     * game to resume after a disconnection.
     * @param gameView the immutable version of the model that contains all the information.
     */
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

    /**
     * It shows a scene that allows to insert the GameId to reconnect.
     */
    @Override
    public void show_RequestGameId() {
        if(primaryStage != null)
            loadScene("/scenes/SetGameIdToRejoin.fxml");
        else
            System.out.println("error primary stage is null");
    }

    /**
     * It shows a scene that allows the player to set the maximum number of players in the game.
     */
    @Override
    public void show_RequestNumberOfPlayers() {
        if(primaryStage != null)
            loadScene("/scenes/SetNumberOfPlayers.fxml");
        else
            System.out.println("error primary stage is null");
    }

    /**
     * It shows a waiting scene, until the game is ready to start.
     */
    @Override
    public void show_waitingOtherPlayers() {
        if(primaryStage != null)
            loadScene("/scenes/waitStart.fxml");
        else
            System.out.println("error primary stage is null");
    }

    /**
     * It shows a scene with all the relevant information needed by the player to perform the action of playing and then
     * drawing a card.
     * @param immutableModel the immutable version of the model that contains all the information.
     */
    @Override
    public void show_playingScene(GameView immutableModel) {
        if(primaryStage != null) {
            loadScene("/scenes/PlayingScene.fxml");
        }
        else
            System.out.println("error primary stage is null");
    }

    /**
     * It shows a popup to communicate that is now possible to draw a card from the table.
     */
    @Override
    public void show_drawFromWhere() {
        PlayingSceneController controller = (PlayingSceneController) abstractController;
        Platform.runLater(controller::showDrawAlert);
    }

    /**
     * It shows a scene with all the relevant information about the game to the players that are waiting for their turn.
     * @param immutableModel the immutable version of the model that contains all the information.
     */
    @Override
    public void show_isYourTurn(GameView immutableModel) {
        if(!immutableModel.getCurrentPlayer().getNick().equals(this.flow.getNickname())){
            if(primaryStage != null) {
                loadScene("/scenes/WaitingScene.fxml");
            }
            else
                System.out.println("error primary stage is null");
        }
    }

    /**
     * It shows a popup saying that the selected card placement is invalid.
     */
    @Override
    public void show_invalidPlay() {
        PlayingSceneController controller = (PlayingSceneController) abstractController;
        Platform.runLater(controller::showInvalidPlayAlert);
    }

    /**
     * It shows a scene with the summary of the finished game.
     * @param name all the names
     */
    @Override
    public void show_winner(List<String> name) {
        if(primaryStage != null)
            loadScene("/scenes/EndScene.fxml");
        else
            System.out.println("error primary stage is null");
    }

    /**
     * It shows a scenes to communicate that the game is being aborted.
     */
    @Override
    public void show_abortGame(){
        if(primaryStage != null)
            loadScene("/scenes/AbortedGame.fxml");
        else
            System.out.println("error primary stage is null");
    }

    @Override
    public void show_message(String message) {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_joinRandomGame() {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_invalidIdGame() {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_invalidNickToReconnect(String id) {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_currentPlayersStatus(GameView gameView) {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_playerColors(GameView gameView) {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_playerJoined(String id) {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_gameStarting(String id) {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_playerHand(GameView immutableModel, String nickname) {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_gameStation(GameView view) {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public String show_goalCard(GoalCard card) {
        //no need to implement this method, the other scenes are sufficient.
        return null;
    }

    @Override
    public String show_playableCard(PlayableCard card) {
        //no need to implement this method, the other scenes are sufficient.
        return null;
    }

    @Override
    public void show_tableOfDecks(GameView immutableModel) {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_lastTurn() {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_pointTable(GameView immutableModel) {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_gameOver() {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_youWin() {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_youLose() {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_requestCardId() {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_requestTypeToDraw() {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_invalidChoice() {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_requestSide() {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_requestCoordinates() {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_connectionError() {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_invalidInput() {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_invalidCommand() {
        //no need to implement this method, the other scenes are sufficient.
    }

    @Override
    public void show_requestToLeave(){
        //no need to implement this method, the other scenes are sufficient.
    }

    public static void main(String[] args) {
        launch(args);
    }
}
