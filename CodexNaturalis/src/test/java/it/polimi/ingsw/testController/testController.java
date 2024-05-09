package it.polimi.ingsw.testController;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.exceptions.MaxPlayersInException;
import it.polimi.ingsw.model.gameDataManager.*;
import it.polimi.ingsw.observer.GameObserver;
import it.polimi.ingsw.view.UI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class testController {

    private MainController mainController;

    private GameController gameController;
    @BeforeEach
    public void setUp() {
        mainController = MainController.getMainController();
        gameController = new GameController("Game1", 4);
    }

    @Test
    public void testCreateGame() throws RemoteException {
        UI ui = null;
        GameObserver obs = null;
        GameControllerInterface gameController = mainController.createGame(obs, "Player1", 4);
        assertNotNull(gameController);
        assertEquals(1, mainController.getActiveGames().size());
        assertEquals("Game1", gameController.getGameId());

    }

    @Test// manca verifica notify
    public void testJoinRandomGame() throws RemoteException {
        UI ui = null;
        GameObserver obs = null;
        GameControllerInterface gameController = mainController.joinRandomGame(obs, "Player1");
        assertNull(gameController);
        GameControllerInterface gameController1 = mainController.createGame(obs, "Player1", 2);
        UI ui2 = null;
        GameObserver obs2 = null;
        GameControllerInterface gameController2 = mainController.joinRandomGame(obs2, "Player2");
        assertNotNull(gameController2);
        assertEquals(2, gameController1.getPlayers().size());
    }

    @Test
    public void testLeaveGame() throws RemoteException {
        // Aggiungiamo un gioco attivo per il test
        UI ui = null;
        GameObserver obs = null;
        GameControllerInterface gameController = mainController.createGame(obs, "Player1", 4);
        assertNotNull(gameController);
        // Verifica che il metodo leaveGame funzioni correttamente
        GameControllerInterface leftGameController = mainController.leaveGame(obs, "Player1", gameController.getGameId());
        assertNull(leftGameController); // Il metodo restituisce null perché il gioco è ancora attivo
        assertEquals(0, gameController.getPlayers().size()); // Il giocatore "Player1" dovrebbe essere stato rimosso dal gioco
    }

    @Test
    public void testDeleteGame() throws RemoteException {
        // Aggiungiamo un gioco attivo per il test
        UI ui = null;
        GameObserver obs = null;
        GameControllerInterface gameController = mainController.createGame(obs, "Player1", 4);
        assertNotNull(gameController);
        // Verifica che il metodo deleteGame funzioni correttamente
        mainController.deleteGame(gameController.getGameId());
        assertEquals(0, mainController.getActiveGames().size()); // Il gioco dovrebbe essere stato rimosso dalla lista dei giochi attivi
    }

    @Test
    public void testAddObserver() throws RemoteException {
        UI ui = null;
        GameObserver obs = null;
        Player player = new Player("Player1");
        gameController.addObserver(obs,player);
        assertTrue(gameController.getObservers().containsKey(player.getNick()));
    }

    @Test
    public void testRemoveObserver() throws RemoteException {
        UI ui = null;
        GameObserver obs = null;
        Player player = new Player("Player1");
        gameController.addObserver(obs, player);
        assertFalse(gameController.getObservers().containsKey(player.getNick()));
    }

    @Test//manca verifica notify
    public void testStartGame() throws RemoteException {
        UI ui = null;
        GameObserver obs = null;
        Player player = new Player("Player1");
        gameController.addObserver(obs, player);
        assertEquals(Status.ACTIVE, gameController.getStatus());
    }
    @Test
    public void testGoOn(){
        // Aggiungere giocatori al gioco per testare il metodo goOn
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        try {
            gameController.addPlayer(player1);
            gameController.addPlayer(player2);
        } catch(MaxPlayersInException e){
            //
        }
        if(gameController.getCurrentPlayer().equals("Player1")){
            gameController.goOn();
            assertEquals("Player2", gameController.getCurrentPlayer());
        }else{
            gameController.goOn();
            assertEquals("Player1", gameController.getCurrentPlayer());
        }

    }
    @Test
    public void testAssignBlackColor() {
        // Aggiungiamo un giocatore al gioco per testare il metodo assignBlackColor
        Player player1 = new Player("Player1");
        try {
            gameController.addPlayer(player1);
        }catch( MaxPlayersInException e){
            //
        }
        // Verifica che il metodo assignBlackColor funzioni correttamente
            gameController.assignBlackColor();

        ArrayList<Player> players= new ArrayList<>(gameController.getPlayers().keySet());

        // Verifica che al giocatore sia stato assegnato correttamente il colore nero
        assertEquals(Color.BLACK, players.get(0).getOptionalColor());
    }


}