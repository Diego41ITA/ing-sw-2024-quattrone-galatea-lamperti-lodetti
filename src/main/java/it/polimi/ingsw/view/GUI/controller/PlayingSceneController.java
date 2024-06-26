package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.model.gameDataManager.Player;
import it.polimi.ingsw.model.gameDataManager.TableOfDecks;
import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.GUI.MultipleResponses;
import it.polimi.ingsw.view.GUI.controller.abstractControllers.InGameController;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static it.polimi.ingsw.view.GUI.ImageAssociator.*;

/**
 * Controller of the PlayingScene.
 */
public class PlayingSceneController extends InGameController {
    @FXML
    private Label gameId;
    @FXML
    private TabPane tabPane;
    @FXML
    private ImageView firstCard;
    @FXML
    private ImageView secondCard;
    @FXML
    private ImageView thirdCard;
    @FXML
    private ImageView firstCardBack;
    @FXML
    private ImageView secondCardBack;
    @FXML
    private ImageView thirdCardBack;
    @FXML
    private Text yourLastTurn;
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
    private double dragStartX, dragStartY;
    @FXML
    private double initialTranslateX, initialTranslateY;
    @FXML
    private Scale scaleTransform = new Scale(1, 1, 0, 0);
    @FXML
    private ImageView deckResource;
    @FXML
    private ImageView deckGold;
    @FXML
    private ImageView card1;
    @FXML
    private ImageView card2;
    @FXML
    private ImageView card3;
    @FXML
    private ImageView card4;
    @FXML
    private ImageView goal1;
    @FXML
    private ImageView goal2;
    private Map<ImageView, Integer> mapping = new HashMap<>();
    @FXML
    private ImageView personalCard;

    /**
     * An attribute that stores all the input corresponding to the mouse events.
     */
    private MultipleResponses multipleResponses;

    /**
     * It stores the index inside the imageViews array of the chosen card to play by the user.
     */
    private int indexCardToPlay;
    /**
     * An array that holds all the ImageViews of the makers.
     */
    private ImageView[] imageViews = new ImageView[29];
    /**
     * An array that holds all the ImageViews of the front side of the cards in the hand tab.
     */
    @FXML
    private ImageView[] handCardsFront = new ImageView[3];
    /**
     * An array that holds all the ImageViews of the back side of the cards in the hand tab.
     */
    @FXML
    private ImageView[] handCardsBack = new ImageView[3];

    /**
     * This method initializes additional attributes and sets up all the consequences of the mouse events on
     * the different components.
     *
     * @param updatedGame       updated FSM of the Game.
     * @param multipleResponses
     */
    @Override
    public void setUpController(FsmGame updatedGame, MultipleResponses multipleResponses) {
        setGame(updatedGame);
        this.multipleResponses=multipleResponses;
        GameView gameView = updatedGame.getView();
        yourLastTurn.setVisible(false);
        GoalCard goalCard = gameView.getPlayerByNick(updatedGame.getNickname()).getGoal();
        Image imagePersonalGoalCard = new Image(associatorPng2Card(String.valueOf(goalCard.getCardId()), true));
        personalCard.setImage(imagePersonalGoalCard);
        this.initializeArrays();
        this.setGameId(gameView.getId());
        for(Player p : gameView.getPlayers())
            this.createAndAddGameStationTab(p);

        for (HashMap.Entry<Color, Integer> entry : gameView.getPoints().getMap().entrySet()) {
            Color color = entry.getKey();
            Integer point = entry.getValue();
            this.MakerInPointTable(color, point);
        }

        List<PlayableCard> playerHand;
        playerHand = gameView.getPlayerByNick(updatedGame.getNickname()).showCard();
        for (int i = 0; i < playerHand.size(); i++) {
            int playerCardId = playerHand.get(i).getCardId();
            setHand(playerCardId, i);
        }
        List<PlayableCard> cards = gameView.getPlayerByNick(updatedGame.getNickname()).showCard();
        this.makeHandCardsPlayable(cards);
        this.setUpTableOfDecks();
        createRulebookTab();
        tabPane.getSelectionModel().select(2);
        this.showYourTurnAlert();
        if(getGameFsm().isPointsThresholdReached())
            showLastTurnAlert();
    }

    /**
     * Generates all the responses to the mouse events in the TableOfDecks Tab
     */
    private void makeTableOfDecksTabResponsive(){
        deckGold.setOnMouseClicked(event -> {
            if(showConfirmationDrawAlert()){
                multipleResponses.add("A");
                multipleResponses.add("gold");
            }
        });
        deckResource.setOnMouseClicked(event -> {
            if(showConfirmationDrawAlert()){
                multipleResponses.add("A");
                multipleResponses.add("resource");
            }
        });
        card1.setOnMouseClicked(event -> {
            if(showConfirmationDrawAlert()){
                multipleResponses.add("B");
                multipleResponses.add(String.valueOf(mapping.get(card1)));
            }
        });
        card2.setOnMouseClicked(event -> {
            if(showConfirmationDrawAlert()){
                multipleResponses.add("B");
                multipleResponses.add(String.valueOf(mapping.get(card2)));
            }
        });
        card3.setOnMouseClicked(event -> {
            if(showConfirmationDrawAlert()){
                multipleResponses.add("B");
                multipleResponses.add(String.valueOf(mapping.get(card3)));
            }
        });
        card4.setOnMouseClicked(event -> {
            if(showConfirmationDrawAlert()){
                multipleResponses.add("B");
                multipleResponses.add(String.valueOf(mapping.get(card4)));
            }
        });

        applyHoverEffects(deckGold);
        applyHoverEffects(deckResource);
        applyHoverEffects(card1);
        applyHoverEffects(card2);
        applyHoverEffects(card3);
        applyHoverEffects(card4);
    }

    /**
     * Private helper method that generates the hover effects over the cards.
     */
    private void applyHoverEffects(ImageView component) {
        component.setCursor(Cursor.HAND);
        component.setOnMouseEntered(mouseEvent -> {
            component.setScaleX(1.2);
            component.setScaleY(1.2);
        });
        component.setOnMouseExited(mouseEvent -> {
            component.setScaleX(1);
            component.setScaleY(1);
        });
    }

    /**
     * Sets the imageViews in the TableOfDecks tab to the actual cards stored in the model.
     */
    private void setUpTableOfDecks() {
        FsmGame updatedGame = getGameFsm();
        TableOfDecks tableOfDecks = updatedGame.getView().getTableOfDecks();

        // Extracting decks
        Deck<ResourceCard> resource = tableOfDecks.getDeckResource();
        Deck<GoldCard> gold = tableOfDecks.getDeckGold();

        // Extracting goals and cards
        ArrayList<Integer> goals = tableOfDecks.getGoals().stream()
                .map(Card::getCardId)
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Integer> cards = tableOfDecks.getCards().stream()
                .map(c -> c != null ? c.getCardId() : 0)
                .collect(Collectors.toCollection(ArrayList::new));

        // Extracting decks dimensions
        ArrayList<Integer> decks = new ArrayList<>();
        decks.add(resource.getDimension() != 0 ? resource.getFirst().getCardId() : resource.getDimension());
        decks.add(gold.getDimension() != 0 ? gold.getFirst().getCardId() : gold.getDimension());

        // Setting images for cards and decks
        setImageWithDefault(card1, cards.get(0), true);
        setImageWithDefault(card2, cards.get(1), true);
        setImageWithDefault(card3, cards.get(2), true);
        setImageWithDefault(card4, cards.get(3), true);
        setImageWithDefault(deckResource, decks.get(0), false);
        setImageWithDefault(deckGold, decks.get(1), false);

        // Setting images for goals
        goal1.setImage(new Image(associatorPng2Card(String.valueOf(goals.get(0)), true)));
        goal2.setImage(new Image(associatorPng2Card(String.valueOf(goals.get(1)), true)));

        // Mapping ImageView to card IDs
        mapping.put(card1, cards.get(0));
        mapping.put(card2, cards.get(1));
        mapping.put(card3, cards.get(2));
        mapping.put(card4, cards.get(3));
    }

    /**
     * Private helper method that sets an ImageView to its default if the parameters is an invalid card id,
     * (meaning that there's no card in that position), to the actual card otherwise.
     * @param imageView the ImageView to set.
     * @param cardId the cardId that needs to be shown.
     * @param isFront side of the card.
     */
    private void setImageWithDefault(ImageView imageView, int cardId, boolean isFront) {
        if (cardId == 0) {
            imageView.setImage(null); // Set ImageView to display no image
            imageView.setVisible(false);
        } else {
            imageView.setImage(new Image(associatorPng2Card(String.valueOf(cardId), isFront)));
            imageView.setVisible(true); // Make ImageView visible when setting a specific image
        }
    }

    /**
     * Sets the GameId and adds a tooltip with useful information.
     * @param id the GameId to set.
     */
    private void setGameId(String id){
        gameId.setText(id);
        Tooltip tooltip = new Tooltip("This is the id of the game you are connected to. Don't forget it! " + "\n" +
                "It is neeed if you want to reconnect to this game");
        // Create a tooltip with custom style
        tooltip.getStyleClass().add("custom-tooltip"); // Assign custom style class
        tooltip.setShowDelay(Duration.millis(100));
        gameId.setTooltip(tooltip); // Directly set the tooltip on the label
    }

    /**
     * Creates an ImageView, specifying the dimensions and assigning an Image.
     * @param imagePath relative path to the image.
     * @return the created ImageView.
     */
    private ImageView createImageView(String imagePath) {
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(50);
        imageView.setFitHeight(90);
        imageView.setOnMouseClicked(Event::consume);
        return imageView;
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
        pane.setOnScroll(event -> {
            double zoomFactor = 1.05;
            if (event.getDeltaY() < 0) {
                zoomFactor = 1 / zoomFactor;
            }

            // Get the cursor position relative to the pane
            double cursorX = event.getX();
            double cursorY = event.getY();

            // Get the current scale
            double currentScaleX = scaleTransform.getX();
            double currentScaleY = scaleTransform.getY();

            // Calculate new scale
            double newScaleX = currentScaleX * zoomFactor;
            double newScaleY = currentScaleY * zoomFactor;

            // Adjust pivot to cursor position
            scaleTransform.setPivotX(cursorX);
            scaleTransform.setPivotY(cursorY);

            // Update the scale transform
            scaleTransform.setX(newScaleX);
            scaleTransform.setY(newScaleY);

            // Adjust the translation to keep the cursor position fixed
            double f = (zoomFactor - 1) / zoomFactor;
            double newTranslateX = pane.getTranslateX() - f * (cursorX - pane.getLayoutBounds().getWidth() / 2 - pane.getTranslateX());
            double newTranslateY = pane.getTranslateY() - f * (cursorY - pane.getLayoutBounds().getHeight() / 2 - pane.getTranslateY());

            pane.setTranslateX(newTranslateX);
            pane.setTranslateY(newTranslateY);

            event.consume();
        });

        // Variables for panning
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
     * Generates some rectangles with opacity = 0, placing them where the new card can be played, and
     * assigns the correct responses to the mouse events.
     * @param nick nickname of the owner of the GameStation tab.
     */
    private void generateFreeCords(String nick) {
        // Get the tab associated with the player's nickname
        String tabId = "tab" + nick;
        Tab playerTab = findTabById(tabId);

        if (playerTab != null) {
            // Find or create a Pane within the tab to add rectangles
            Pane playerPane = findOrCreatePaneInTab(playerTab);

            // Example logic to add rectangles (adjust as per your game logic)
            GameStation gameStation = getGameView().getMyGameStation(nick);
            for (Point point : gameStation.getFreeCords()) {
                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(55);
                rectangle.setHeight(33);
                rectangle.setFill(Paint.valueOf("FFFFFF"));

                double layoutX = 615 + (point.getX() * 37);
                double layoutY = 364 + (-(point.getY() * 21));

                rectangle.setLayoutX(layoutX);
                rectangle.setLayoutY(layoutY);
                rectangle.setOpacity(0);
                rectangle.setCursor(Cursor.HAND);

                // Capture the current point coordinates
                final int x = point.x;
                final int y = point.y;


                rectangle.setOnMouseEntered(event -> showCardChosen(playerPane, new Point(x, y)));
                rectangle.setOnMouseExited(event -> hideCardChosen(playerPane));

                rectangle.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    if (showConfirmationPlayAlert()){
                        multipleResponses.add(String.valueOf(idChosenCardToPlay));
                        multipleResponses.add(String.valueOf(sideChosenCardToPlay));
                        multipleResponses.add(String.valueOf(x));
                        multipleResponses.add(String.valueOf(y));
                        showCardChosen(playerPane, new Point(x,y));
                    } else {
                        hideCardChosen(playerPane);
                    }}
                });

                playerPane.getChildren().add(rectangle); // Add rectangle to the player's pane
            }
        }
    }

    /**
     * Shows an alert that ask for confirmation of the card placement.
     * @return the button selected (yes and no as true and false).
     */
    private boolean showConfirmationPlayAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Stage");
        alert.setHeaderText("Do you confirm your choice?");
        alert.setContentText("Think well! This action is irreversible");

        // Custom buttons
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.initStyle(StageStyle.UTILITY);

        // Show the alert and wait for a response
        var result = alert.showAndWait();


        // Return true if "Yes" is clicked, false if "No" is clicked
        return result.isPresent() && result.get() == yesButton;
    }

    /**
     * Shows an alert that ask for confirmation of the card to be drawn.
     * @return the button selected (yes and no as true and false).
     */
    private boolean showConfirmationDrawAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Stage");
        alert.setHeaderText("Do you confirm your choice?");
        alert.setContentText("Think well! This action is irreversible");

        // Custom buttons
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.initStyle(StageStyle.UTILITY);

        // Show the alert and wait for a response
        var result = alert.showAndWait();

        // Return true if "Yes" is clicked, false if "No" is clicked
        return result.isPresent() && result.get() == yesButton;
    }

    /**
     * Return the tab corresponding to a specified id.
     * @param tabId the specified id.
     * @return the founded tab.
     */
    private Tab findTabById(String tabId) {
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getId() != null && tab.getId().equals(tabId)) {
                return tab;
            }
        }
        return null;
    }

    /**
     * Returns the Pane inside the tab. It is used for modifying the GameStation Tab, so we can safely perform
     * a type casting.
     */
    private Pane findOrCreatePaneInTab(Tab tab) {
        Pane contentPane = (Pane) tab.getContent();
        if (contentPane == null) {
            contentPane = new Pane();
            tab.setContent(contentPane);
        }
        return contentPane;
    }

    /**
     * ImageView of the chosen card to play
     */
    private ImageView chosenCardToPlay;

    /**
     * Shows the ImageView of the chosen card to play, applying some fancy but functional effects.
     * @param pane the pane that holds all the played card in the GameStation.
     * @param point the coordinates to place the card.
     */
    private void showCardChosen(Pane pane, Point point){
        if (idChosenCardToPlay == 0)
            return;
        chosenCardToPlay = createImageView(associatorPng2Card(String.valueOf(idChosenCardToPlay), sideChosenCardToPlay));
        chosenCardToPlay.setFitHeight(33);
        chosenCardToPlay.setFitWidth(65);
        double layoutX = 615 + (point.getX() * 37);
        double layoutY = 364 + (- point.getY() * 21);
        chosenCardToPlay.setLayoutX(layoutX);
        chosenCardToPlay.setLayoutY(layoutY);
        chosenCardToPlay.setPreserveRatio(true);
        chosenCardToPlay.setOpacity(0.0); // Start with full transparency
        chosenCardToPlay.setMouseTransparent(true);

        pane.getChildren().add(chosenCardToPlay);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), chosenCardToPlay);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    /**
     * Private helper method for the fancy effects in the GameStation Tab.
     * @param root the pane inside the tab.
     */
    private void hideCardChosen(Pane root) {
        if (idChosenCardToPlay == 0)
            return;
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), chosenCardToPlay);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {
            root.getChildren().remove(chosenCardToPlay);
            chosenCardToPlay = null;
        });
        fadeOut.play();
    }


    /**
     * Sets the ImageView of the hand tab to the actual images of the cards stored in the model.
     * @param num id of the card.
     * @param pos position of the card in the array of personal cards.
     */
    private void setHand(int num, int pos){
        if(pos == 0){
            Image imageFront = new Image(associatorPng2Card(String.valueOf(num), true));
            Image imageBack = new Image(associatorPng2Card(String.valueOf(num), false));
            firstCard.setImage(imageFront);
            firstCardBack.setImage(imageBack);

        }else if(pos == 1){
            Image imageFront = new Image(associatorPng2Card(String.valueOf(num), true));
            Image imageBack = new Image(associatorPng2Card(String.valueOf(num), false));
            secondCard.setImage(imageFront);
            secondCardBack.setImage(imageBack);

        }else{
            Image imageFront = new Image(associatorPng2Card(String.valueOf(num), true));
            Image imageBack = new Image(associatorPng2Card(String.valueOf(num), false));
            thirdCard.setImage(imageFront);
            thirdCardBack.setImage(imageBack);
        }

    }

    /**
     * Stores the id of the chosen card to play.
     */
    private int idChosenCardToPlay = 0;
    /**
     * Stores the side of the chosen card to play.
     */
    private boolean sideChosenCardToPlay = false;

    /**
     * Applies some fancy but function effects to the card in "your hand" tab.
     * @param cards list of the player's personal Playable Cards.
     */
    private void makeHandCardsPlayable(List<PlayableCard> cards) {
        int index = 0 ;
        for (PlayableCard card : cards){
            setCardEvent(handCardsBack[index], card, false, index);
            setCardEvent(handCardsFront[index], card, true, index);
            index += 1;
        }
    }

    /**
     * Private helper method for the card's effects.
     * @param cardView The ImageView of the card.
     * @param card The actual card object.
     * @param isFront The side of the card.
     * @param index the index of the card inside the cards' ImageViews array.
     */
    private void setCardEvent(ImageView cardView, PlayableCard card, boolean isFront, int index) {
        applyHoverEffects(cardView);
        cardView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                makeHandCardsPlayable(getGameView().getPlayerByNick(getGameFsm().getNickname()).showCard());
                idChosenCardToPlay = card.getCardId();
                sideChosenCardToPlay = isFront;
                chosenCardToPlay = cardView;
                cardView.setScaleX(1.2);
                cardView.setScaleY(1.2);
                cardView.setOnMouseExited(null);
                // Reset scale for all other card views
                resetOtherCardViewsScale(cardView);
                indexCardToPlay = index;
                this.generateFreeCords(getGameFsm().getNickname());
            }
        });
        cardView.setCursor(Cursor.HAND);
    }

    /**
     * Private helper method for the clicked card's effect.
     * @param clickedCardView The clicked card.
     */
    private void resetOtherCardViewsScale(ImageView clickedCardView) {
        // List of all card views
        List<ImageView> allCardViews = Arrays.asList(firstCard, firstCardBack, secondCard, secondCardBack, thirdCard, thirdCardBack);

        // Loop through all card views
        for (ImageView cardView : allCardViews) {
            if (cardView != clickedCardView) {
                cardView.setScaleX(1.0);
                cardView.setScaleY(1.0);
            }
        }
    }


    /**
     * Sets the "This is the last turn" text as visible.
     */
    public void setLastTurn(){
        yourLastTurn.setVisible(true);

    }

    /**
     * Initializes all the arrays.
     */
    @FXML
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

        handCardsFront[0] = firstCard;
        handCardsFront[1] = secondCard;
        handCardsFront[2] = thirdCard;

        handCardsBack[0] = firstCardBack;
        handCardsBack[1] = secondCardBack;
        handCardsBack[2] = thirdCardBack;
    }

    /**
     * Places the maker in the PointTable.
     * @param color The color of the maker to place.
     * @param point The point to be covered.
     */
    private void MakerInPointTable(Color color, int point){
        Image imageMaker = new Image(makerAssociator(color));
        imageViews[point].setImage(imageMaker);
    }

    /**
     * Creates a tab containing the rulebook and adds it to the TabPane.
     */
    private void createRulebookTab() {
        Tab rulebookTab = new Tab();
        rulebookTab.setText("Rulebook");

        // Create ImageView
        ImageView rulebookImageView = new ImageView();
        rulebookImageView.setFitWidth(842.0);
        rulebookImageView.setFitHeight(736.0);
        rulebookImageView.setLayoutX(272.0);
        rulebookImageView.setLayoutY(13.0);
        rulebookImageView.setPreserveRatio(true);
        rulebookImageView.setPickOnBounds(true);

        // Load image
        Image image = createRulebookImage().getFirst();
        rulebookImageView.setImage(image);

        // Create Buttons
        Button previousButton = new Button();
        previousButton.setLayoutX(206.0);
        previousButton.setLayoutY(355.0);
        previousButton.setPrefWidth(53.0);
        previousButton.setPrefHeight(53.0);
        previousButton.setText("<");
        previousButton.setOnMouseClicked(mouseEvent -> showPreviousImage(rulebookImageView));

        Button nextButton = new Button();
        nextButton.setLayoutX(1019.0);
        nextButton.setLayoutY(354.0);
        nextButton.setPrefWidth(53.0);
        nextButton.setPrefHeight(53.0);
        nextButton.setText(">");
        nextButton.setOnMouseClicked(mouseEvent -> showNextImage(rulebookImageView));

        // Create AnchorPane for content
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(200.0);
        anchorPane.setPrefHeight(180.0);
        anchorPane.getChildren().addAll(rulebookImageView, previousButton, nextButton);

        // Set content to Tab
        rulebookTab.setContent(anchorPane);

        tabPane.getTabs().add(rulebookTab);
    }

    /**
     * Stores the index of the rulebook's page.
     */
    private int currentIndex = 0;

    /**
     * Private helper method to navigate the rulebook.
     * @param rulebook
     */
    private void showPreviousImage(ImageView rulebook) {
        if (currentIndex > 0) {
            currentIndex--;
            rulebook.setImage(createRulebookImage().get(currentIndex));
        }
    }

    /**
     * Private helper method to navigate the rulebook
     * @param rulebook
     */
    private void showNextImage(ImageView rulebook) {
        if (currentIndex < createRulebookImage().size() - 1) {
            currentIndex++;
            rulebook.setImage(createRulebookImage().get(currentIndex));
        }
    }

    /**
     * Shows a popup saying that to the player that it's his turn.
     */
    public void showYourTurnAlert() {
        // Create a new alert dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        // Set the title and header text
        alert.setTitle("Playing stage");
        alert.setHeaderText("This is your turn");

        // Create a grid pane to allow for more complex content
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(Double.MAX_VALUE);

        // Set padding and gap properties
        gridPane.setPadding(new javafx.geometry.Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        // Create a label for each item in the list
        Label label1 = new Label("• Select the card you want to play from YourHand.");
        Label label2 = new Label("• Click on the position in your GameStation tab to play the card.");

        // Add labels to the grid pane
        gridPane.add(label1, 0, 0);
        gridPane.add(label2, 0, 1);

        // Set the content of the alert to the grid pane
        alert.getDialogPane().setContent(gridPane);

        // Optional: Customize the style of the alert dialog (utility style)
        alert.initStyle(StageStyle.UTILITY);

        // Set the button types (OK button only)
        ButtonType okButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okButton);

        alert.show();

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(3000),
                ae -> alert.close()
        ));
        timeline.play();
        // Add an event handler for the OK button
        alert.resultProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == okButton) {
                timeline.stop(); // Stop the timeline if the OK button is pressed
                alert.close(); // Close the alert
            }
        });

    }

    /**
     * Resets to default the mouse events responses of the ImageView in the GameStation Tab.
     * @param imageView The ImageView to reset.
     */
    private void resetEventsImageView(ImageView imageView){
        imageView.setOnMouseExited(null);
        imageView.setOnMouseEntered(null);
        imageView.setOnMouseClicked(null);
        imageView.setCursor(Cursor.DEFAULT);
    }

    /**
     * Shows an alert saying that is possible to draw a card. It also handles the responses to the mouse events
     * in the personal GameStation tab and in the "Your hand" tab.
     */
    public void showDrawAlert() {
        handCardsBack[indexCardToPlay].setOpacity(0.5);
        handCardsFront[indexCardToPlay].setOpacity(0.5);
        for(int i = 0; i <3 ; i++){
            resetEventsImageView(handCardsBack[i]);
            resetEventsImageView(handCardsFront[i]);
        }

        // Create a new alert dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        // Set the title and header text
        alert.setTitle("Drawing stage");
        alert.setHeaderText("Now you can draw a card");

        // Create a grid pane to allow for more complex content
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(Double.MAX_VALUE);

        // Set padding and gap properties
        gridPane.setPadding(new javafx.geometry.Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        // Create a label for each item in the list
        Label label1 = new Label("• Select the card you want to draw from TableOfDecks tab.");

        // Add labels to the grid pane
        gridPane.add(label1, 0, 0);

        // Set the content of the alert to the grid pane
        alert.getDialogPane().setContent(gridPane);

        // Optional: Customize the style of the alert dialog (utility style)
        alert.initStyle(StageStyle.UTILITY);

        // Set the button types (OK button only)
        ButtonType okButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okButton);

        // Show the alert and wait for the user to close it
        alert.showAndWait();

        tabPane.getSelectionModel().select(1);
        makeTableOfDecksTabResponsive();

        String tabId = "tab" + getGameFsm().getNickname();

        // Reset mouse entered and exited events for all rectangles
        for (Node node : findOrCreatePaneInTab(findTabById(tabId)).getChildren()) {
            if (node instanceof Rectangle) {
                Rectangle rect = (Rectangle) node;
                rect.setOnMouseEntered(null);
                rect.setOnMouseExited(null);
                rect.setOnMouseClicked(null);
                rect.setCursor(Cursor.DEFAULT);
            }
        }
    }

    /**
     * It shows an alert saying that the chosen card positioning is not allowed by the game's rules.
     */
    public void showInvalidPlayAlert() {
        // Create a new alert dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        // Set the title and header text
        alert.setTitle("Invalid position stage");
        alert.setHeaderText("The positioning you choose is not allowed!");

        // Create a grid pane to allow for more complex content
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(Double.MAX_VALUE);

        // Set padding and gap properties
        gridPane.setPadding(new javafx.geometry.Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        // Create a label for each item in the list
        Label label1 = new Label("• Select a different card or positioning.");

        // Add labels to the grid pane
        gridPane.add(label1, 0, 0);

        // Set the content of the alert to the grid pane
        alert.getDialogPane().setContent(gridPane);

        // Optional: Customize the style of the alert dialog (utility style)
        alert.initStyle(StageStyle.UTILITY);

        // Set the button types (OK button only)
        ButtonType okButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okButton);

        // Show the alert and wait for the user to close it
        alert.showAndWait();

        String tabId = "tab" + getGameFsm().getNickname();
        Tab tab = findTabById(tabId);
        Pane pane = findOrCreatePaneInTab(tab);
        hideCardChosen(pane);
    }

}