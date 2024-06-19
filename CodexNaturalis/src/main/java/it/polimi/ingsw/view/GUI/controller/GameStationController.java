package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.model.gameDataManager.Player;
import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.GUI.Gui;
import it.polimi.ingsw.view.input.InputGui;
import javafx.animation.FadeTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.event.*;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.*;
import java.util.*;
import java.util.List;

import static it.polimi.ingsw.view.GUI.ImageAssociator.associatorPng2Card;
import static it.polimi.ingsw.view.GUI.ImageAssociator.makerAssociator;
//non è definitivo ma è una buona base
public class GameStationController extends AbstractController {
    @FXML
    private Text gameId;
    @FXML
    private Pane anchor1;
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
    //sono tutte le immagini che conterrano i maker nella point table
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
    private Scale scaleTransform = new Scale(1, 1);

    //è un array che contiene tuelle le imageview della pointTable
    //mi serve per poter selezionare la imageview corrispondente al punteggio
    private ImageView[] imageViews = new ImageView[29];

    //mi aggiorna il contenuto di gameId(serve a settare id del game nella gamestation)
    public void setGameId(String id){
        gameId.setText(id);
    }

    //crea un immagine e ne assegna le dimensioni, restituisce un immagine che verrà settata per le carte iniziali
    private ImageView createImageView(String imagePath) {
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(50);
        imageView.setFitHeight(90);
        // Gestione dell'evento del click del mouse sull'ImageView (disabilitata temporaneamente)
        // Consuma l'evento per evitare ulteriori azioni
        imageView.setOnMouseClicked(Event::consume);
        return imageView;
    }

    public void createGameStationTabPane(Player player) {
        Pane pane = new Pane();  // Create a single Pane
        GameStation gameStation = getGameView().getMyGameStation(player.getNick());
        for (HashMap.Entry<Point, PlayableCard> entry : gameStation.getPlayedCards().entrySet()) {
            Point point = entry.getKey();
            PlayableCard card = entry.getValue();

            double layoutX = 615 + (point.getX() * (37));
            double layoutY = 364 + (point.getY()) * (21);

            // Create ImageView and add it to the Pane
            ImageView imageView = createImageView(associatorPng2Card(String.valueOf(card.getCardId()), card.isFront()));
            imageView.setFitHeight(33);
            imageView.setFitWidth(65);
            imageView.setLayoutX(layoutX);
            imageView.setLayoutY(layoutY);
            imageView.setPreserveRatio(true);

            pane.getChildren().add(imageView);
        }

        // Create a new Tab and set the Pane as its content
        Tab tab = new Tab(player.getNick() + "'s GameStation");
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
            if (event.getButton() == MouseButton.PRIMARY) {
                pane.setCursor(Cursor.DEFAULT); // Change cursor to open hand when dragging stops
            }
        });
    }

    public void generateFreeCords(String nick) {
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
                rectangle.setWidth(33);
                rectangle.setHeight(15);
                rectangle.setFill(Paint.valueOf("FFFFFF"));

                double layoutX = 615 + (point.getX() * 37);
                double layoutY = 364 + (point.getY() * 21);

                rectangle.setLayoutX(layoutX);
                rectangle.setLayoutY(layoutY);

                rectangle.setCursor(Cursor.HAND);

                rectangle.setOnMouseEntered(event -> showCardChosen(playerPane, point));
                rectangle.setOnMouseExited(event -> hideCardChosen(playerPane));

                rectangle.setOnMouseClicked(mouseEvent -> {
                    multipleResponses.add(String.valueOf(idChosenCardToPlay));
                    multipleResponses.add(String.valueOf(sideChosenCardToPlay));
                });

                playerPane.getChildren().add(rectangle); // Add rectangle to the player's pane
            }
        }
    }

    private Tab findTabById(String tabId) {
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getId() != null && tab.getId().equals(tabId)) {
                return tab;
            }
        }
        return null;
    }

    private Pane findOrCreatePaneInTab(Tab tab) {
        // Here you can define how to find or create the Pane within the tab
        // For simplicity, assuming the content of each tab is a Pane or similar
        Pane contentPane = (Pane) tab.getContent();
        if (contentPane == null) {
            contentPane = new Pane();
            tab.setContent(contentPane);
        }
        return contentPane;
    }

    ImageView chosenCardToPlay;

    private void showCardChosen(Pane pane, Point point){
        chosenCardToPlay = createImageView(associatorPng2Card(String.valueOf(idChosenCardToPlay), sideChosenCardToPlay));
        chosenCardToPlay.setFitHeight(33);
        chosenCardToPlay.setFitWidth(65);
        double layoutX = 615 + (point.getX() * 37);
        double layoutY = 364 + (point.getY() * 21);
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

    private void hideCardChosen(Pane root) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), chosenCardToPlay);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {
            root.getChildren().remove(chosenCardToPlay);
            chosenCardToPlay = null;
        });
        fadeOut.play();
    }



    //mi setta la mano in base alla carta pescata utilizzando il suo num e la posizione in cui devo sostituire la carta
    public void setHand(int num, int pos){
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

    private int idChosenCardToPlay;
    private boolean sideChosenCardToPlay;

    public void makeHandCardsPlayable(List<PlayableCard> cards) {
        setCardEvent(firstCard, cards.get(0), true);
        setCardEvent(firstCardBack, cards.get(0), false);
        setCardEvent(secondCard, cards.get(1), true);
        setCardEvent(secondCardBack, cards.get(1), false);
        setCardEvent(thirdCard, cards.get(2), true);
        setCardEvent(thirdCardBack, cards.get(2), false);
    }

    private void setCardEvent(ImageView cardView, PlayableCard card, boolean isFront) {
        cardView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                idChosenCardToPlay = card.getCardId();
                sideChosenCardToPlay = isFront;
                cardView.setScaleX(1.2);
                cardView.setScaleY(1.2);

                // Reset scale for all other card views
                resetOtherCardViewsScale(cardView);
            }
        });
        cardView.setCursor(Cursor.HAND);
    }

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


    //mi rende visibile il messaggio che è l'ultimo turno
    public void setLastTurn(){
        yourLastTurn.setVisible(true);

    }
    //inserisce le image0 all'interno del imageView array
    //il tag@FXML indica che questo metodo verrà lanciato in automatico quando si aprirà l'interfaccia associata a questo controller
    @FXML
    public void initializeImageArray(){
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
    }

    //assegna alla imageview corrispondende al punteggio il maker del colore adeguato
    public void MakerInPointTable(Color color, int point){
        Image imageMaker = new Image(makerAssociator(color));
        imageViews[point].setImage(imageMaker);
    }

    @Override
    public void setUpController(FsmGame updatedGame) {
        setGame(updatedGame);
        GameView gameView = updatedGame.getView();
        yourLastTurn.setVisible(false);
        this.initializeImageArray();
        this.setGameId(gameView.getId());
        for(Player p : gameView.getPlayers())
            this.createGameStationTabPane(p);

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
        this.generateFreeCords(getGameFsm().getNickname());
    }
}