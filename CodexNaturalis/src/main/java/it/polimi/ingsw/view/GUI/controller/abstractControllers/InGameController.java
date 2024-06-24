package it.polimi.ingsw.view.GUI.controller.abstractControllers;

import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.model.gameDataManager.Player;
import it.polimi.ingsw.view.FsmGame;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static it.polimi.ingsw.view.GUI.ImageAssociator.*;
import it.polimi.ingsw.view.GUI.controller.*;

/**
 * An abstract class, that holds the common methods needed by {@link PlayingSceneController},
 * {@link WaitingSceneController} and {@link EndController}.
 */
public abstract class InGameController extends AbstractController {
    /**
     * {@inheritDoc}
     * @param updatedGame
     */
    @Override
    public void setUpController(FsmGame updatedGame) {
    }

    /**
     * Create a pane containing all the {@link PlayableCard}s placed in the {@link GameStation}.
     * @param player needed to obtain the correct GameStation.
     * @return
     */
    protected Pane createGameStationTabPane(Player player) {
        Pane pane = new Pane();  // Create a single Pane
        GameStation gameStation = getGameView().getMyGameStation(player.getNick());
        for (Map.Entry<Point, PlayableCard> entry : gameStation.getPlayedCards().entrySet()) {
            Point point = entry.getKey();
            PlayableCard card = entry.getValue();

            double layoutX = 615 + (point.getX() * (37));
            double layoutY = 364 + (-(point.getY())) * (21);

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

    /**
     * Private helper method that creates an {@link ImageView} of a specific image.
     * @param imagePath the relative path to the specific image.
     */
    private ImageView createImageView (String imagePath){
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(50);
        imageView.setFitHeight(90);
        // Gestione dell'evento del click del mouse sull'ImageView (disabilitata temporaneamente)
        // Consuma l'evento per evitare ulteriori azioni
        imageView.setOnMouseClicked(Event::consume);
        return imageView;
    }

}
