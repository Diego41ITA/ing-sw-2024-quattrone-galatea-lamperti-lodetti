package it.polimi.ingsw.view.GUI.controller;
import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.model.gameDataManager.Player;
import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.GUI.DbCardInfo;
import it.polimi.ingsw.view.GUI.MultipleResponses;
import it.polimi.ingsw.view.GUI.controller.abstractControllers.InGameController;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;

import java.util.HashMap;
import java.util.List;

import static it.polimi.ingsw.view.GUI.ImageAssociator.associatorPng2Card;
import static it.polimi.ingsw.view.GUI.ImageAssociator.makerAssociator;

/**
 * Controller for the EndScene.
 */
public class EndController extends InGameController {
    @FXML
    private Text winnerNick;
    @FXML
    private Text youWon;
    @FXML
    private TabPane tabPane;
    @FXML
    private ImageView image0;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;
    @FXML
    private ImageView image4;
    @FXML
    private ImageView image5;
    @FXML
    private ImageView image6;
    @FXML
    private ImageView image7;
    @FXML
    private ImageView image8;
    @FXML
    private ImageView image9;
    @FXML
    private ImageView image10;
    @FXML
    private ImageView image11;
    @FXML
    private ImageView image12;
    @FXML
    private ImageView image13;
    @FXML
    private ImageView image14;
    @FXML
    private ImageView image15;
    @FXML
    private ImageView image16;
    @FXML
    private ImageView image17;
    @FXML
    private ImageView image18;
    @FXML
    private ImageView image19;
    @FXML
    private ImageView image20;
    @FXML
    private ImageView image21;
    @FXML
    private ImageView image22;
    @FXML
    private ImageView image23;
    @FXML
    private ImageView image24;
    @FXML
    private ImageView image25;
    @FXML
    private ImageView image26;
    @FXML
    private ImageView image27;
    @FXML
    private ImageView image28;
    @FXML
    private Text nick1;
    @FXML
    private Text nick2;
    @FXML
    private Text nick3;
    @FXML
    private Text nick4;
    @FXML
    private ImageView commonGoal1;
    @FXML
    private ImageView commonGoal2;
    @FXML
    private ImageView goal1;
    @FXML
    private ImageView goal2;
    @FXML
    private ImageView goal3;
    @FXML
    private ImageView goal4;
    @FXML
    private ImageView maker1;
    @FXML
    private ImageView maker2;
    @FXML
    private ImageView maker3;
    @FXML
    private ImageView maker4;
    /**
     * Needed for the zooming feature in the GameStation tab.
     */
    @FXML
    private double initialTranslateX, initialTranslateY;
    /**
     * Needed for the panning feature in the GameStation tab.
     */
    @FXML
    private double dragStartX, dragStartY;
    /**
     * Needed for the zooming feature in the GameStation tab.
     */
    @FXML
    private Scale scaleTransform = new Scale(1, 1);
    /**
     * An array that holds all the ImageViews that places the makers on the PointTable
     */
    private ImageView[] imageViews = new ImageView[29];
    /**
     * An array that holds all the ImageViews of the Goal Cards.
     */
    private ImageView[] goalCardImageViews = new ImageView[4];
    /**
     * An array that holds all the ImageViews of the makers.
     */
    private ImageView[] makerImageViews = new ImageView[4];
    /**
     * An array that holds all the text to set the nicknames.
     */
    private Text[] nickArray = new Text[4];

    /**
     * Initialize the tab of the Goal Cards, setting all the ImageViews.
     */
    private void initializeGoalCardsTab(){
        GameView gameView = getGameView();
        List<Player> players = gameView.getPlayers();
        List<GoalCard> commonGoals = gameView.getTableOfDecks().getGoals();

        int i = 0;

        for (Player p : players) {
            GoalCard card = p.getGoal();
            int cardId = card.getCardId();
            String cardIdStr = Integer.toString(cardId);
            String playerNick = p.getNick();
            Color playerColor = p.getColor();

            goalCardImageViews[i].setImage(new Image(associatorPng2Card(cardIdStr, true)));
            nickArray[i].setText(playerNick);
            makerImageViews[i].setImage(new Image(makerAssociator(playerColor)));
            i++;
        }

        commonGoal1.setImage(new Image(associatorPng2Card(String.valueOf(commonGoals.getFirst().getCardId()), true)));
        commonGoal2.setImage(new Image(associatorPng2Card(String.valueOf(commonGoals.get(1).getCardId()), true)));
    }

    /**
     * Private helper method that initializes all the arrays.
     */
    private void initializeArrays(){
        imageViews[0] = image0;
        imageViews[1] = image1;
        imageViews[2] = image2;
        imageViews[3] = image3;
        imageViews[4] = image4;
        imageViews[5] = image5;
        imageViews[6] = image6;
        imageViews[7] = image7;
        imageViews[8] = image8;
        imageViews[9] = image9;
        imageViews[10] = image10;
        imageViews[11] = image11;
        imageViews[12] = image12;
        imageViews[13] = image13;
        imageViews[14] = image14;
        imageViews[15] = image15;
        imageViews[16] = image16;
        imageViews[17] = image17;
        imageViews[18] = image18;
        imageViews[19] = image19;
        imageViews[20] = image20;
        imageViews[21] = image21;
        imageViews[22] = image22;
        imageViews[23] = image23;
        imageViews[24] = image24;
        imageViews[25] = image25;
        imageViews[26] = image26;
        imageViews[27] = image27;
        imageViews[28] = image28;

        goalCardImageViews[0] = goal1;
        goalCardImageViews[1] = goal2;
        goalCardImageViews[2] = goal3;
        goalCardImageViews[3] = goal4;

        nickArray[0] = nick1;
        nickArray[1] = nick2;
        nickArray[2] = nick3;
        nickArray[3] = nick4;

        makerImageViews[0] = maker1;
        makerImageViews[1] = maker2;
        makerImageViews[2] = maker3;
        makerImageViews[3] = maker4;
    }

    /**
     * Places a maker on a specific point.
     * @param color the color of the maker to place.
     * @param point the point on the PointTable.
     */
    private void MakerInPointTable(Color color, int point){
        Image imageMaker = new Image(makerAssociator(color));
        imageViews[point].setImage(imageMaker);
    }

    /**
     * Creates the GameStation Tab, adds it to the TabPane and assign the correct responses to the mouse events.
     * @param player the owner of the GameStation to create
     */
    private void createAndAddGameStationTab(Player player) {

        // Create a new Tab and set the Pane as its content
        Tab tab = new Tab(player.getNick() + "'s GameStation");

        Pane pane = createGameStationTabPane(player);
        tab.setContent(pane);
        tab.setId("tab" + (player.getNick()));

        // Add the Tab to the TabPane
        tabPane.getTabs().add(tab);

        // Zoom handling
        pane.getTransforms().add(scaleTransform);
        pane.getParent().addEventFilter(ScrollEvent.SCROLL, event -> {
            double zoomFactor = 1.05;
            if (event.getDeltaY() < 0) {
                zoomFactor = 1 / zoomFactor;
            }

            double scaleX = scaleTransform.getX() * zoomFactor;
            double scaleY = scaleTransform.getY() * zoomFactor;

            double deltaX = (event.getX() - (pane.getBoundsInParent().getWidth() / 2 + pane.getBoundsInParent().getMinX()));
            double deltaY = (event.getY() - (pane.getBoundsInParent().getHeight() / 2 + pane.getBoundsInParent().getMinY()));

            pane.setTranslateX(pane.getTranslateX() - deltaX * (zoomFactor - 1));
            pane.setTranslateY(pane.getTranslateY() - deltaY * (zoomFactor - 1));

            scaleTransform.setX(scaleX);
            scaleTransform.setY(scaleY);
            event.consume();
        });

        // Panning handling
        pane.setOnMousePressed(event -> {
            dragStartX = event.getSceneX();
            dragStartY = event.getSceneY();
            initialTranslateX = pane.getTranslateX();
            initialTranslateY = pane.getTranslateY();
            pane.setCursor(Cursor.CLOSED_HAND);
        });

        pane.setOnMouseDragged(event -> {
            double offsetX = event.getSceneX() - dragStartX;
            double offsetY = event.getSceneY() - dragStartY;

            pane.setTranslateX(initialTranslateX + offsetX);
            pane.setTranslateY(initialTranslateY + offsetY);
        });

        pane.setOnMouseReleased(event -> {
            pane.setCursor(Cursor.DEFAULT);
        });
    }

    /**
     * Sets the winner nickname.
     * @param winnerNick nickname of the winner.
     */
    private void setWinnerNick(String winnerNick){
        this.winnerNick.setText(winnerNick);
        if(getGameFsm().getNickname().equals(winnerNick))
            youWon.setText("YOU WON!!");
        else youWon.setText("YOU LOST!");
    }

    /**
     * this method set up additional attributes for the controller class if this is needed.
     *
     * @param updatedGame       is the new version of the game
     * @param multipleResponses
     */
    @Override
    public void setUpController(FsmGame updatedGame, MultipleResponses multipleResponses) {
        setGame(updatedGame);
        initializeArrays();
        initializeGoalCardsTab();
        GameView gameView = getGameView();
        for (HashMap.Entry<Color, Integer> entry : gameView.getPoints().getMap().entrySet()) {
            Color color = entry.getKey();
            Integer point = entry.getValue();
            this.MakerInPointTable(color, point);
        }
        for(Player p : gameView.getPlayers())
            this.createAndAddGameStationTab(p);
        setWinnerNick(DbCardInfo.getInstance().readWinners().getFirst());

        tabPane.getSelectionModel().select(1);
    }
}
