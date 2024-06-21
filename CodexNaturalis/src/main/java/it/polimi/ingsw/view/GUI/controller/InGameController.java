package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.model.gameDataManager.Player;
import it.polimi.ingsw.view.FsmGame;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javax.management.ImmutableDescriptor;
import java.awt.*;
import java.util.HashMap;

import static it.polimi.ingsw.view.GUI.ImageAssociator.*;

public abstract class InGameController extends AbstractController {
    @Override
    public void setUpController(FsmGame updatedGame) {
    }

    protected Pane createGameStationTabPane(Player player) {
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

        ImageView imageMakerPlayer = createImageView(makerAssociator(player.getColor()));
        if (player.getNick().equals(getGameView().getTurn().getFirstPlayerNick())) {
            ImageView imageMakerBlack = createImageView(makerAssociator(Color.BLACK));
            imageMakerBlack.setFitWidth(15);
            imageMakerBlack.setFitHeight(15);
            imageMakerBlack.setLayoutX(621);
            imageMakerBlack.setLayoutY(373);
            imageMakerBlack.setPreserveRatio(true);
            imageMakerPlayer.setFitWidth(15);
            imageMakerPlayer.setFitHeight(15);
            imageMakerPlayer.setLayoutX(645);
            imageMakerPlayer.setLayoutY(373);
            imageMakerPlayer.setPreserveRatio(true);
            pane.getChildren().add(imageMakerBlack);
            pane.getChildren().add(imageMakerPlayer);
        } else {
            imageMakerPlayer.setFitWidth(15);
            imageMakerPlayer.setFitHeight(15);
            imageMakerPlayer.setLayoutX(621);
            imageMakerPlayer.setLayoutY(373);
            imageMakerPlayer.setPreserveRatio(true);
            pane.getChildren().add(imageMakerPlayer);
        }
        return pane;

    }
    private ImageView createImageView (String imagePath){
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(50);
        imageView.setFitHeight(90);
        // Gestione dell'evento del click del mouse sull'ImageView (disabilitata temporaneamente)
        // Consuma l'evento per evitare ulteriori azioni
        imageView.setOnMouseClicked(Event::consume);
        return imageView;
    }

    protected AnchorPane createRulebookTab(int currentIndex) {
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
        previousButton.setOnMouseClicked(mouseEvent -> showPreviousImage(rulebookImageView, currentIndex));

        Button nextButton = new Button();
        nextButton.setLayoutX(1019.0);
        nextButton.setLayoutY(354.0);
        nextButton.setPrefWidth(53.0);
        nextButton.setPrefHeight(53.0);
        nextButton.setText(">");
        nextButton.setOnMouseClicked(mouseEvent -> showNextImage(rulebookImageView, currentIndex));

        // Create AnchorPane for content
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(200.0);
        anchorPane.setPrefHeight(180.0);
        anchorPane.getChildren().addAll(rulebookImageView, previousButton, nextButton);

        return anchorPane;
    }
    private void showPreviousImage(ImageView rulebook, int currentIndex) {
        if (currentIndex > 0) {
            currentIndex--;
            rulebook.setImage(createRulebookImage().get(currentIndex));
        }
    }

    private void showNextImage(ImageView rulebook, int currentIndex) {
        if (currentIndex < createRulebookImage().size() - 1) {
            currentIndex++;
            rulebook.setImage(createRulebookImage().get(currentIndex));
        }
    }
}
