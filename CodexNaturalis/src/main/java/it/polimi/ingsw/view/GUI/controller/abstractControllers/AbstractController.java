package it.polimi.ingsw.view.GUI.controller.abstractControllers;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.GUI.MultipleResponses;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;

/**
 * this is a class that generalize the controller concept
 */
public abstract class AbstractController {
    private FsmGame game;

    private GameView gameView;

    protected static MultipleResponses multipleResponses = MultipleResponses.getInstance();

    /**
     * a subclass can use this method to get the private FsmGame attribute
     * @return the attribute
     */
    protected FsmGame getGameFsm(){
        return game;
    }

    protected GameView getGameView() {return gameView;}

    /**
     * this method sets a new fresh updated game.
     * @param updatedGame is the new version of the game
     */
    protected void setGame(FsmGame updatedGame){
        this.game = updatedGame;
        this.gameView = updatedGame.getView();
    }

    /**
     * this method set up additional attributes for the controller class if this is needed.
     */
    public abstract void setUpController(FsmGame updatedGame);

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
