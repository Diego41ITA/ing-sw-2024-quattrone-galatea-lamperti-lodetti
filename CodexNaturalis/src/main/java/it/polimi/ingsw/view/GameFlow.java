package it.polimi.ingsw.view;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.model.gameDataManager.Status;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.observer.GameObserver;
import it.polimi.ingsw.view.statusActive.DrawCardState;
import it.polimi.ingsw.view.statusActive.PlaceCardState;
import it.polimi.ingsw.view.statusActive.StateActive;
import it.polimi.ingsw.view.statusWaiting.StateMenu;
import it.polimi.ingsw.view.statusWaiting.*;
import static it.polimi.ingsw.view.PrintlnThread.Println;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameFlow implements Runnable, /*ClientAction,*/ GameObserver {

    public boolean waitingForNewPlayers = false;
    private final Object lock = new Object();
    private ClientAction client;
    private String nickname;
    private final UI ui;
    private GameView view;
    public boolean inGame;

    private String winner = null;

    //un attributo per uscire dal ciclo (viene settato dall'ultimo stato)
    private boolean stay = true;

    //metto 4 attributi State
    private StateWaiting state1 = new StateMenu(this);
    private StateActive state2 = new PlaceCardState(this);

    //costruttore
    public GameFlow(UI ui){
        this.ui = ui;
        inGame = false;
    }


    //contiene tutto il flusso di gioco.
    public void run(){
        synchronized (lock) {
            boolean stay = true;

            ui.show_RequestPlayerNickName();
            Scanner scanner = new Scanner(System.in);
            nickname = scanner.nextLine();

            //inizializza gli state di partenza.
            initializeStates();

            while (stay) {
                if (view == null || view.getStatus() == Status.WAITING) {

                    state1.execute();

                    //se la view ha i giocatori giusti la partita può iniziare
                    if(view.getPlayers().size() == view.getMaxNumOfPlayer()){
                        try {
                            client.startGame();
                        } catch (IOException e) { // da sistemare non dovrebbe riceverla
                            throw new RuntimeException(e);
                        }
                    }

                    //se invece i giocatori non sono ancora del numero corretto si aspetta
                    while (waitingForNewPlayers) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Println("this game is aborted");
                        }
                    }
                } else if (view.getStatus() == Status.ACTIVE) {
                    if (view.getCurrentPlayer().getNick().equals(nickname)) { //da inserire gestione caso che non è il tuo turno
                        state2.execute();
                    }
                    while(!view.getCurrentPlayer().getNick().equals(nickname)){
                        try{
                            lock.wait();
                        }catch(InterruptedException e){
                            Println("game interrupted");
                        }
                    }
                } else if (view.getStatus() == Status.SUSPENDED) {
                    ui.show_GameStatus(view);
                    ui.show_message("this game is temporally suspended :(\n");
                    ui.show_message("these are your cards, goal and game station.");
                    ui.show_goalCard(view.getPlayer(nickname).getGoal());
                    ui.show_gameStation(view.getMyGameStation(nickname));
                    ui.show_playerHand(view);

                    while(view.getStatus() == Status.SUSPENDED) {
                        try {
                            lock.wait();    //need to add some notify when the gameStatus change.
                        } catch (InterruptedException e) {
                            Println("this game got interrupted");
                        }
                    }
                } else if (view.getStatus() == Status.FINISHED) {
                    ui.show_GameStatus(view);
                    ui.show_gameOver();
                    while(winner == null) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    ui.show_message("the winner is: " + winner);
                    if(winner.equals(nickname))
                        ui.show_message("congrats you won");
                    else
                        ui.show_message("loser");
                    askToLeave();
                    stay = false;
                }
            }
        }
    }

    private void askToLeave(){
        ui.show_message("press any button to leave the game");
        //Bisogna verificare che la ui sia una cli
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            char character = (char) reader.read();
            client.leaveGame(nickname, view.getId());
        } catch (IOException | NotBoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
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


    //serve a inizializzare gli stati
    public void initializeStates(){
        state1 = new StateMenu(this);
        state2 = new PlaceCardState(this);
    }

    //serve ad andare allo waiting state successivo
    public void setWaitingState(StateWaiting sw){
        this.state1 = sw;
    }

    //serve ad andare allo stato attivo successivo.
    public void setActiveState(StateActive sa){
        this.state2 = sa;
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
    }

    @Override
    public void randomGameJoined(String GameID) throws RemoteException {
        ui.show_playerJoined(GameID);
        inGame = true;
        /*ui.show_requestPlayerColor(view);
        Scanner scanner = new Scanner(System.in);
        String color = scanner.nextLine();
        client.setColor(color, nickname);*/
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
    public void updateGameStatus(GameView game) throws RemoteException { //non va bene
        setGameView(game);
        ui.show_GameStatus(game);
        if(view.getMaxNumOfPlayer() == view.getPlayers().size()) {
            waitingForNewPlayers = false;
            lock.notify();
        }
    }

    @Override
    public void startGame(GameView game) throws RemoteException {
        synchronized (lock){
        setGameView(game);
        ui.show_gameStarting(game.getId());
        waitingForNewPlayers = false;
        lock.notifyAll();}
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
        waitingForNewPlayers = true;
        ui.show_playerColors(game);
        Println("waiting for new players");
    }

    @Override
    public void updateSetAvailableColors(GameView game, ArrayList<Color> colors) throws RemoteException{
        setGameView(game);
        waitingForNewPlayers = false;
        Println("the choose color is not available anymore! please, select another one");
        state1 = new StateColor(this);
    }

    @Override
    public void updateTableAndTurn(GameView game) throws RemoteException {
        setGameView(game);
        state2 = new PlaceCardState(this);
        notifyAll();
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
        switch (msg){
            case("No games currently available to join..."):
                this.inGame = false;
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
