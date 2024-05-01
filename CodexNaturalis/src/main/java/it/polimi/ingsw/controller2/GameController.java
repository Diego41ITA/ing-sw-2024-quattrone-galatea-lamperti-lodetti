package it.polimi.ingsw.controller2;

import it.polimi.ingsw.model.gameDataManager.Game;
import it.polimi.ingsw.model.gameDataManager.Player;
import java.io.Serializable;

public class GameController implements Serializable, GameControllerInterface {
    private Game game;

    public GameController(){
        //logica per inizializzare il model
    }

    //tutti i metodi per agire sul model, come ad esempio:
    public void addPlayer(Player p) {
        game.addPlayer(p);
    }

    //ci vanno anche quelli per inizializzare il tutto.

    //la cosa da aggiungere è il modo per detectare le disconnessioni che non so come si faccia al momento.
}
