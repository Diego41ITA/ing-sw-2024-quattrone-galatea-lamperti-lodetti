package it.polimi.ingsw.view;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.model.gameDataManager.Player;
import it.polimi.ingsw.model.gameDataManager.Status;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.observer.GameObserver;
import it.polimi.ingsw.view.statusActive.PlaceCardState;
import it.polimi.ingsw.view.statusActive.StateActive;
import it.polimi.ingsw.view.statusFinished.StateFinished;
import it.polimi.ingsw.view.statusSuspended.StateSuspended;
import it.polimi.ingsw.view.statusWaiting.StateWaiting;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class GameFlow implements Runnable, /*ClientAction,*/ GameObserver {
    private ClientAction client;
    private String nickname;
    private final UI ui;
    private GameView view;

    private String winner = null;

    //un attributo per uscire dal ciclo (viene settato dall'ultimo stato)
    boolean stay = true;

    //metto 4 attributi State
    private StateWaiting state1 = new StateWaiting(this);
    private StateActive state2 = new PlaceCardState(this);
    private StateSuspended state3;
    private StateFinished state4;

    //tutto il flusso di gioco...

    //implementa i metodi di gameObserver per svolgere finalmente delle operazioni concrete in risposta alle notifiche
    //che arrivano dal model

    //implementa runnable perché il cuore starà dentro ad un thread

    //implementa clientAction perchè chiamerà quei metodi sull'oggetto client (Socket o RMI)

    public GameFlow(UI ui){
        this.ui = ui;
        //this.client = client;
    }

    public void setClient(ClientAction client){
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

    public String getWinner(){
        return this.winner;
    }

    public void run(){
        boolean stay = true;

        ui.show_RequestPlayerNickName();
        Scanner scanner = new Scanner(System.in);
        nickname = scanner.nextLine();

        while(stay) {
            if(view.getStatus() == Status.WAITING) {
                state1.execute();
            } else if (view.getStatus() == Status.ACTIVE) {
                if(view.getCurrentPlayer().getNick().equals(nickname)) { //da inserire gestione caso che non è il tuo turno
                    state2.execute();
                }
            } else if (view.getStatus() == Status.SUSPENDED) {
                state3.execute();
            } else if (view.getStatus() == Status.FINISHED) {
                state4.execute();
                stay = false;
            }
        }
    }

    public void exit(){
        this.stay = false;
    }

    public void setGameView(GameView game){
        this.view = game;
    }

    @Override
    public void newGameCreated(String GameID) throws RemoteException {
        ui.show_playerJoined(GameID);
        ui.show_requestPlayerColor(view);
        Scanner scanner = new Scanner(System.in);
        String color = scanner.nextLine();
        client.setColor(color, nickname);
    }

    @Override
    public void randomGameJoined(String GameID) throws RemoteException {
        ui.show_playerJoined(GameID);
        ui.show_requestPlayerColor(view);
        Scanner scanner = new Scanner(System.in);
        String color = scanner.nextLine();
        client.setColor(color, nickname);
    }

    @Override
    public void reconnectedToGame(String GameID) throws RemoteException {
        ui.show_playerJoined(GameID);
    }

    @Override
    public void gameIdNotExists(String gameId) throws RemoteException { //da sistemare manca procedura dopo notifica errore
        ui.show_invalidIdGame();
    }

    @Override
    public void updatePlayerInGame(GameView game) throws RemoteException {
        setGameView(game);
        ui.show_currentPlayersStatus(game);
    }

    @Override
    public void updatePlayerStatus(GameView game) throws RemoteException {
        setGameView(game);
        ui.show_currentPlayersStatus(game);
    }

    @Override
    public void updateGameStatus(GameView game) throws RemoteException {
        setGameView(game);
        ui.show_GameStatus(game);
    }

    @Override
    public void startGame(GameView game) throws RemoteException {
        ui.show_gameStarting(game.getId());
    }

    //basta notificare che la partita è stata creata?
    @Override
    public void updatePlayerAndMaxNumberPlayer(GameView game) throws RemoteException {
        setGameView(game);
    }

    @Override
    public void update20PointsReached(GameView game) throws RemoteException {
        ui.show_lastTurn();
    }

    @Override
    public void updateTableOfDecks(GameView game) throws RemoteException {
        setGameView(game);
        ui.show_tableOfDecks(game);
    }

    @Override
    public void updateGamestation(GameView game, GameStation gameStation) throws RemoteException {
        setGameView(game);
        ui.show_gameStation(gameStation);
    }

    @Override
    public void updateColor(GameView game) throws RemoteException {
        setGameView(game);
        ui.show_playerColors(game);
    }

    @Override
    public void updateTableAndTurn(GameView game) throws RemoteException {
        setGameView(game);
        ui.show_tableOfDecks(game);
    }

    @Override
    public void updateHandAndTable(GameView game, String nick) throws RemoteException {
        setGameView(game);
        ui.show_gameStation(game.getMyGameStation(nick));
    }

    @Override
    public void goalCardsDrawed(ArrayList<GoalCard> cards) throws RemoteException {
        for(GoalCard goalCard : cards){
            ui.show_goalCard(goalCard);
        }
        Scanner scanner = new Scanner(System.in);
        int cardId = scanner.nextInt();
        client.chooseGoal(cards, cardId, nickname);
    }

    @Override
    public void updateGoalPlayer(GameView game) throws RemoteException {
        setGameView(game);
        ui.show_goalCard(game.getCurrentPlayer().getGoal());
    }

    @Override
    public void updateCurrentPlayer(GameView game) throws RemoteException {
        setGameView(game);
        ui.show_isYourTurn(game);
    }

    @Override
    public void invalidCardPlacement() throws RemoteException {
        ui.show_invalidPlay();
        //metodo correzione coordinata oppure riinvoco playCard ma va modificato il catch in gameController
    }

    @Override
    public void updateGameStations(GameView game) throws RemoteException {
        setGameView(game);
        ui.show_gameStation(game.getMyGameStation(game.getCurrentPlayer().getNick())); //da sistemare (fare in modo che mostri gamestation con nome)
    }

    @Override
    public void updatePoints(GameView game) throws RemoteException {
        setGameView(game);
        ui.show_pointTable(game);
    }

    //si potrebbe gestire con i messaggi delle exception (o le exception)
    @Override
    public void genericErrorWhenEnteringGame(String msg, String id) throws RemoteException {
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
                                try {
                                    client.createGame(nickname, numberOfPlayer);
                                } catch (NotBoundException e) {
                                    ui.show_message("CONNECTION ERROR, GAME OVER...");
                                    try {
                                        this.wait(100); // non sono sicuro
                                        this.exit();
                                    }catch (InterruptedException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                                break;
                            case ("B"):
                                exit();
                                break;
                        }
                        break;
                    case ("The nickname used was not connected in the running game."):
                        ui.show_invalidNickToReconnect(id);
                        break;
        }
    }

    @Override
    public void winner(GameView game, String winner){
        this.winner = winner;
        this.view = game;
        notifyAll();
    }

    //serve la notifica per il vincitore


    //bisogna implementare le robe di GameObserver e bisogna capire se gestire con eventi che contengono il tipo di
    //notifica o che fanno operazioni dirette in teoria sarebbe meglio utilizzare una coda ordinata poichè in questo
    //modo ogni evento è gestito dallo stato specifico.
    //

}
