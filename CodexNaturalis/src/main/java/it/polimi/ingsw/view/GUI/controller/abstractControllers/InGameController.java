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
    private ImageView createImageView (String imagePath){
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(50);
        imageView.setFitHeight(90);
        // Gestione dell'evento del click del mouse sull'ImageView (disabilitata temporaneamente)
        // Consuma l'evento per evitare ulteriori azioni
        imageView.setOnMouseClicked(Event::consume);
        return imageView;
    }

    public void showLastTurnAlert() {
        // Create a new alert dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        // Set the title and header text
        alert.setTitle("Last turn stage");
        alert.setHeaderText("This is the last turn!");

        // Create a grid pane to allow for more complex content
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(Double.MAX_VALUE);

        // Set padding and gap properties
        gridPane.setPadding(new javafx.geometry.Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        // Create a label for each item in the list
        javafx.scene.control.Label label1 = new javafx.scene.control.Label("• Everybody will play one more hand.");
        javafx.scene.control.Label label2 = new Label("• The final points will be calculated.");

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

        // Show the alert and wait for the user to close it
        alert.showAndWait();
    }
}
