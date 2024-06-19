package it.polimi.ingsw.view;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.model.gameDataManager.Game;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.model.gameDataManager.Status;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.networking.PingServer;
import it.polimi.ingsw.observer.GameObserver;
import it.polimi.ingsw.view.GUI.CardRecord;
import it.polimi.ingsw.view.GUI.DbCardInfo;
import it.polimi.ingsw.view.input.InputParser;
import it.polimi.ingsw.view.statusActive.PlaceCardState;
import it.polimi.ingsw.view.statusActive.StateActive;
import it.polimi.ingsw.view.statusWaiting.StateMenu;
import it.polimi.ingsw.view.statusWaiting.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class FsmGame implements Runnable, /*ClientAction,*/ GameObserver, Serializable {

    public boolean waitingForNewPlayers = false;
    public boolean notStarted;
    public boolean myTurn;
    private final Object lock = new Object();
    private ClientAction client;
    private String nickname;
    private final UI ui;
    private GameView view;
    public boolean inGame;
    private InputParser input;

    private String winner = null; //puo essere estesa la classe DbCardInfo, magari rinominandola DbGameInfo
    private boolean stay = true;

    //metto 4 attributi State
    private StateWaiting state1 = new StateMenu(this, this.input);
    private StateActive state2 = new PlaceCardState(this, this.input);

    //costruttore
    public FsmGame(UI ui, InputParser input) throws RemoteException{
        this.ui = ui;
        this.input = input;
        inGame = false;
        notStarted = true;
        myTurn = false;
        new PingServer(this, this.client, this.lock).start();
    }


    //contiene tutto il flusso di gioco.
    @Override
    public void run(){
        boolean stay = true;

        ui.show_RequestPlayerNickName();
        nickname = this.input.getNickName();

        //inizializza gli state di partenza.
        initializeStates();
        try{
        while (stay) {
            if (view == null || view.getStatus() == Status.WAITING) {
                if(notStarted)
                    state1.execute();
                if(view == null) break; //condizione per uscita dal game dopo scelta joingame
                //se la view ha i giocatori giusti la partita può iniziare
                if(view.getPlayers().size() == view.getMaxNumOfPlayer() && view.getPlayer(nickname).getColor() != null && notStarted){
                    try {
                        client.startGame();
                        notStarted = false;

                    } catch (IOException e) { // da sistemare non dovrebbe riceverla
                        throw new RuntimeException(e);
                    }
                }

                //se invece i giocatori non sono ancora del numero corretto si aspetta
                while (waitingForNewPlayers) {
                    try {
                        synchronized (lock) {
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException();
                    }
                }
            } else if (view.getStatus() == Status.ACTIVE) {
                if(view.getTurn() != null && !view.getTurn().getPlayers().isEmpty()){
                    if (view.getCurrentPlayer().getNick().equals(nickname) && myTurn) { //da inserire gestione caso che non è il tuo turno
                        state2 = new PlaceCardState(this, input);
                        state2.setView(view);
                        state2.execute();
                        myTurn = false;
                    }
                    while(!view.getCurrentPlayer().getNick().equals(nickname) && view.getStatus() == Status.ACTIVE){
                        ui.show_playerHand(view);
                        ui.show_gameStation(view);
                        System.out.println("it's not your turn. Wait");
                        try{
                            synchronized (lock) {
                                lock.wait();
                            }
                        }catch(InterruptedException e){
                            System.out.println("game interrupted");
                        }
                    }
                }
            } else if (view.getStatus() == Status.SUSPENDED) {
                ui.show_GameStatus(view);
                ui.show_playerHand(view);
                ui.show_gameStation(view);

                while(view.getStatus() == Status.SUSPENDED) {
                    try {
                        synchronized (lock) {
                            lock.wait();
                        }    //need to add some notify when the gameStatus change.
                    } catch (InterruptedException e) {
                        System.out.println("this game got interrupted");
                    }
                }
            } else if (view.getStatus() == Status.FINISHED) {
                ui.show_GameStatus(view);
                ui.show_gameOver();
                while(winner == null) {
                    try {
                        synchronized (lock) {
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                ui.show_winner(winner);
                if(winner.equals(nickname))
                    ui.show_youWin();
                else
                    ui.show_youLose();
                askToLeave();
                stay = false;
            }
        }
        System.out.println("application is closing...");
    }catch(Exception e){
        //e.printStackTrace();
        //e.getCause();
        System.out.println("exception caught in FsmGame thread.");
        e.printStackTrace();
        e.getCause();
        throw new RuntimeException();
    }}

    private void askToLeave(){
        ui.show_requestToLeave();
        //Bisogna verificare che la ui sia una cli
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            char character = (char) reader.read();
            client.leaveGame(nickname, view.getId());
        } catch (IOException | NotBoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //---------------------------------------------------------------------
    //Getter, setter and initializer methods
    //---------------------------------------------------------------------
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
        state1 = new StateMenu(this, this.input);
        state2 = new PlaceCardState(this, this.input);
    }

    public void exit(){
        this.stay = false;
    }

    public void setGameView(GameView game){
        this.view = game;
    }

    //----------------------------------------------------------------
    //Notification from the server
    //----------------------------------------------------------------

    @Override
    public void newGameCreated(String GameID) throws RemoteException {
        ui.show_playerJoined(GameID);
    }

    @Override
    public void randomGameJoined(String GameID) throws RemoteException {
        ui.show_playerJoined(GameID);
        inGame = true;
    }

    @Override
    public void reconnectedToGame(GameView view) throws RemoteException {
        setGameView(view);
        //ui.show_gameStation(view);
        inGame = true;
        //questo dovrebbe fare in modo che non si vada nello stato color -> si potrebbe aggiungere un nuovo stato
        //di StateWaiting che non fa nulla.
        notStarted = false; //la partita era chiaramente iniziata questo dovrebbe risolvere i problemi senza dover
        //aggiungere uno stato d'attesa.
    }

    @Override
    public void gameIdNotExists(String gameId) throws RemoteException { //da sistemare manca procedura dopo notifica errore
        ui.show_invalidIdGame();
    }

    @Override
    public void updatePlayerInGame(GameView game) throws RemoteException {
        setGameView(game);
        ui.show_currentPlayersStatus(game);
        //if this notification comes from a rejoin procedure we need to set myTurn = true in some cases
        if(game.getStatus() == Status.ACTIVE && game.getCurrentPlayer().getNick().equals(nickname))
            myTurn = true;
        synchronized (lock){
            lock.notifyAll();
        }
    }

    @Override
    public void updatePlayerStatus(GameView game) throws RemoteException {
        setGameView(game);
        ui.show_currentPlayersStatus(game);
        synchronized (lock){
            lock.notifyAll();
        }
    }

    @Override
    public void updateGameStatus(GameView game) throws RemoteException {
        setGameView(game);
        ui.show_GameStatus(game);
        if(view.getMaxNumOfPlayer() == view.getPlayers().size()) {
            waitingForNewPlayers = false;
            lock.notify();
        }
    }

    @Override
    public void startGame(GameView game) throws IOException {
        setGameView(game);
        ui.show_gameStarting(game.getId());
        waitingForNewPlayers = false;
        client.definePlayer(nickname);
        synchronized (lock){
            lock.notifyAll();
        }
    }

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
        ui.show_gameStation(game);
    }

    @Override
    public void updateColor(GameView game) throws RemoteException {
        setGameView(game);
        waitingForNewPlayers = true;
        ui.show_playerColors(game);
        ui.show_waitingOtherPlayers();
    }

    @Override
    public void updateSetAvailableColors(GameView game, ArrayList<Color> colors) throws RemoteException{
        setGameView(game);
        waitingForNewPlayers = false;
        System.out.println("the choose color is not available anymore! please, select another one");
        state1 = new StateColor(this, this.input);
    }

    @Override
    public void updateTableAndTurn(GameView game) throws RemoteException {
        setGameView(game);
        state2 = new PlaceCardState(this, input);
        ui.show_tableOfDecks(game);
    }

    @Override
    public void updateHandAndTable(GameView game, String nick) throws RemoteException {
        setGameView(game);
    }

    @Override
    public void goalCardsDrawed(ArrayList<GoalCard> cards) throws RemoteException {

        //this inserts the relevant attributes inside the Record
        DbCardInfo.getInstance().addRecord(new CardRecord(cards.getFirst().getType(), cards.getFirst().getCardId()));
        DbCardInfo.getInstance().addRecord(new CardRecord(cards.getLast().getType(), cards.getLast().getCardId()));

        int cardId;
        do {
            ui.show_requestGoalCard(cards);
            cardId = input.getCardId();
        }while(!checkIfValidId(cards, cardId));

        client.chooseGoal(cards, cardId, nickname);
    }
    private boolean checkIfValidId(ArrayList<GoalCard> cards, int cardId){
        List<Integer> id = cards.stream()
                .map(Card::getCardId)
                .toList();
        return id.contains(cardId);
    }

    @Override
    public void updateGoalPlayer(GameView game, GoalCard card) throws RemoteException {
        setGameView(game);
        ui.show_goalCard(card);
        ui.show_waitingOtherPlayers();
    }

    @Override
    public void updateInitialCardsDrawn(InitialCard card) throws RemoteException {

        //this inserts the relevant attributes in the Record
        DbCardInfo.getInstance().addRecord(new CardRecord(card.getType(), card.getCardId()));

        ui.show_initialCard(card);
        boolean isFrontOrBack = false;
        boolean isValid = false;
        do {
            try {
                isFrontOrBack = input.getSideOfTheCard();
                isValid = true;
            }catch (InputMismatchException e){
                ui.show_invalidInput();
            }
        }while (!isValid);
        client.setGameStation(nickname, card, isFrontOrBack);
    }

    @Override
    public void updateCurrentPlayer(GameView game) throws RemoteException {
        setGameView(game);
        synchronized (lock) {
            lock.notifyAll();
        }
        ui.show_isYourTurn(game);
        if(game.getCurrentPlayer().getNick().equals(nickname))
            myTurn = true;
    }
    @Override
    public void invalidCardPlacement() throws RemoteException {
        ui.show_invalidPlay();
    }

    @Override
    public void updateGameStations(GameView game) throws RemoteException {
        setGameView(game);
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
                this.inGame = false;
                break;
            case("game not found"):
                ui.show_noAvailableGames();
                this.inGame = false;
                //per ora se non è possibile riconnettersi alla partita si può solo crearne una nuova quindi
                //non è possibile joinare un game random.
        }
    }

    @Override
    public void winner(GameView game, String winner){
        this.winner = winner;
        setGameView(game);
        synchronized (lock){
            lock.notify();
        }
    }

    public void interruptDueToDisconnection(){
    }

    @Override
    public void pingTheClient(GameView game) throws RemoteException {
        System.out.println("i recived a ping from the server");
        setGameView(game);
    }
}
