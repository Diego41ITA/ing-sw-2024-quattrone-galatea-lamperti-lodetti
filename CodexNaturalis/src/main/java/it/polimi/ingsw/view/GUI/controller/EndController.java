package it.polimi.ingsw.view.GUI.controller;
import it.polimi.ingsw.view.FsmGame;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
public class EndController extends AbstractController{
    @FXML
    private Text winner;
    @FXML
    private Button exit;

    //mi fa vedere il nome del vincitore
    public void show(String name) {
        winner.setText(name);
    }

    //Quando clicco il mouse eseguo questo metodo che mi esce dall'app
    public void exit(ActionEvent e){
        Platform.exit();
    }

    /**
     * this method set up additional attributes for the controller class if this is needed.
     *
     * @param updatedGame is the new version of the game
     */
    @Override
    public void setUpController(FsmGame updatedGame) {
        setGame(updatedGame);
    }
}
