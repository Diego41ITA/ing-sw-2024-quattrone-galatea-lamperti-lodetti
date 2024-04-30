package it.polimi.ingsw.controller.example;
import it.polimi.ingsw.model.gameDataManager.Game;
import java.io.Serializable;

public class GameController implements Serializable{
    private Game game;
    private State state;
    //un attributo thread per attivare un thread che monitori le attività dei giocatori (se sono attivi o no)

    //devo aggiungere tutti i metodi per effettuare operazioni tipo:
    public Card drawCardFromDeck(String player, Deck<Gold> d){
        state.drawCardFromDeck();
    }
    //nota che queste operazioni sono dipendneti dallo stato. Altre operazioni come
    public Status getStatus(){
        this.game.getStatus();
    }
    //non sono dipendenti dallo stato.

    //alternativamente non si usa State ma si implementano semplicemente i metodi e poi la
    //logica di controllo viene implementata nella view
}
