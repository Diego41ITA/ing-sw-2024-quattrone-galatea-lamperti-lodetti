package it.polimi.ingsw.view;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.model.gameDataManager.Player;
import it.polimi.ingsw.model.gameDataManager.Status;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.observer.GameObserver;
import it.polimi.ingsw.view.statusActive.StateActive;
import it.polimi.ingsw.view.statusFinished.StateFinished;
import it.polimi.ingsw.view.statusSuspended.StateSuspended;
import it.polimi.ingsw.view.statusWaiting.StateWaiting;

import java.rmi.RemoteException;
import java.util.Optional;
import java.util.Scanner;

public class GameFlow implements Runnable, /*ClientAction,*/ GameObserver {
    private ClientAction client;
    private String nickname;
    private final UI ui;
    private GameView view;

    //un attributo per uscire dal ciclo (viene settato dall'ultimo stato)
    boolean stay = true;

    //metto 4 attributi State
    private StateWaiting state1 = new StateWaiting();
    private StateActive state2;
    private StateSuspended state3;
    private StateFinished state4;

    //tutto il flusso di gioco...

    //implementa i metodi di gameObserver per svolgere finalmente delle operazioni concrete in risposta alle notifiche
    //che arrivano dal model

    //implementa runnable perché il cuore starà dentro ad un thread

    //implementa clientAction perchè chiamerà quei metodi sull'oggetto client (Socket o RMI)

    public GameFlow(/*String nick,*/ UI ui, ClientAction client){
        //this.nickname = nick;       il nickName quando viene inserito?
        this.ui = ui;
        this.client = client;
    }

    public UI getUi() {
        return ui;
    }

    public GameView getView(){
        return view;
    }

    public ClientAction getClient() {
        return client;
    }

    public String getNickname(){
        return nickname;
    }

    public void run(){
        boolean stay = true;

        state1.setFlow(this);
        ui.show_RequestPlayerNickName();
        Scanner scanner = new Scanner(System.in);
        nickname = scanner.nextLine();

        while(stay) {//ad ogni ciclo farà qualcosa, quando lo status cambierà cambierà anche l'azione effettuata
            if(view.getStatus() == Status.WAITING) {
                state1.execute();
                //avanzamento di stato
            } else if (view.getStatus() == Status.ACTIVE) {
                state2.execute();
            } else if (view.getStatus() == Status.SUSPENDED) {
                state3.execute();
            } else if (view.getStatus() == Status.FINISHED) {
                state4.execute();
            }
        }
    }

    public void exit(){
        this.stay = false;
    }

    public void setGameView(GameView game){
        this.view = game;
    }

    //IMPORTANTE
    //la gameView viene aggiornata ad ogni notifica oppure in momenti determinati(e quindi mostriamo solo con la TUI gli aggiornamenti)?
    //cosa pià importante manca una notifica quando il game viene creato per inizializzare la gameView

    @Override
    public void notEnoughResource() throws RemoteException {
        ui.show_notEnoughResources();
    }

    //basta notificare che la partita è stata creata?
    @Override
    public void updatePlayerAndMaxNumberPlayer(GameView game) throws RemoteException {
    }

    //  solo quando è il mio turno vedo il tavolo aggiornato? ( ora mentre giocano gli altri vedo gli aggiornamenti in diretta)
    @Override
    public void updateTableOfDecks(GameView game) throws RemoteException {
        ui.show_tableOfDecks(game);
    }

    //dubbio in GameController per quanto riguarda a chi viene notificato il cambiamento
    @Override
    public void updateGamestation(GameView game, GameStation gameStation) throws RemoteException {
        ui.show_gameStation(gameStation);
    }

    @Override
    public void updatePlayerStatus(GameView game) throws RemoteException {
        ui.show_currentPlayersStatus(game);
    }

    @Override
    public void updateColor(GameView game) throws RemoteException {
        ui.show_playerColors(game);
    }

    @Override
    public void updateTableAndTurn(GameView game) throws RemoteException {
        ui.show_tableOfDecks(game);
    }

    @Override
    public void updateCurrentPlayer(GameView game) throws RemoteException {
        ui.show_isYourTurn(game);
    }

    @Override
    public void updatePoints(GameView game) throws RemoteException {
        ui.show_pointTable(game);
    }

    @Override
    public void updateGoalPlayer(GameView game) throws RemoteException {
        ui.show_goalCard(game.getCurrentPlayer().getGoal());
    }

    @Override
    public void updateHandAndTable(GameView game, String nick) throws RemoteException {
        ui.show_gameStation(game.getMyGameStation(nick));
    }

    @Override
    public void updatePlayerInGame(GameView game) throws RemoteException {
        ui.show_currentPlayersStatus(game);
    }

    //manca
    @Override
    public void updateGameStations(GameView game) throws RemoteException {
    }

    @Override
    public void updateGameStatus(GameView game) throws RemoteException {
        ui.show_GameStatus(game);
    }

    @Override
    public void genericErrorWhenEnteringGame(String msg) throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        String input;
                switch (msg){
                    case("No games currently available to join..."):
                        ui.show_noAvailableGames();
                        input = scanner.nextLine();
                        switch (input){
                            case ("A"):
                                ui.show_RequestNumberOfPlayers();
                                int numberOfPlayer = scanner.nextInt();
                                client.createGame(nickname, numberOfPlayer);
                                break;
                            case ("B"):
                                exit();
                                break;
                        }
                        break;
                    case (""): //invalid maxNumberOfPlayer
                        break;
                    case (""): //playerAlreadyIn
                        break;

        }
    }

    @Override
    public void gameIdNotExists(String gameId) throws RemoteException {
        ui.show_invalidIdGame();
    }

    //bisogna implementare le robe di GameObserver e bisogna capire se gestire con eventi che contengono il tipo di
    //notifica o che fanno operazioni dirette in teoria sarebbe meglio utilizzare una coda ordinata poichè in questo
    //modo ogni evento è gestito dallo stato specifico.
    //

}
