package it.polimi.ingsw.view;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.model.gameDataManager.Status;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.networking.PingServer;
import it.polimi.ingsw.observer.GameObserver;
import it.polimi.ingsw.view.GUI.CardRecord;
import it.polimi.ingsw.view.GUI.DbCardInfo;
import it.polimi.ingsw.view.input.InputParser;
import it.polimi.ingsw.view.statusActive.PlaceCardState;
import it.polimi.ingsw.view.statusActive.PlaceNewCardState;
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
import java.util.Objects;

/**
 * this class is the heart of the project: it manages the flow of the application and it defines al the possible notify
 * method that the server can call (by implementing GameObserver), this class extends Thread in order to allow multithreading.
 * This class also extends Serializable thus it can be passed as an object through the network (the server can call the
 * method of this class).
 * It starts two thread: the first one is the most important one, and it manages the actual flow with the execution of
 * the various states; the second one is activated in the constructor, and actually it refers to the PingServer object.
 */
public class FsmGame extends Thread implements GameObserver, Serializable {

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
    private boolean pointsThresholdReached = false;
    private boolean isGameSuspended = false;
    private List<String> winner = null; //be aware that this list is also put in the DbCardInfo class
    private boolean stay = true;

    private StateWaiting state1;
    private StateActive state2;


    /**
     * this is the only constructor, and it initializes some of the attribute of the class
     * @param ui it is the specific user interface; chosen by the client.
     * @param input it is the input associated to the interface: actually it can be customized.
     * @throws RemoteException
     */
    public FsmGame(UI ui, InputParser input) throws RemoteException{
        this.ui = ui;
        this.input = input;
        inGame = false;
        notStarted = true;
        myTurn = false;

    }


    /**
     * this method contains the logic to run in a separated thread the sequence of the game
     */
    @Override
    public void run(){

        ui.show_RequestPlayerNickName();
        nickname = this.input.getNickName();

        //first of all the state need to be initialized
        initializeStates();
        try{
        while (stay) {
            if (view == null || view.getStatus() == Status.WAITING) {
                if(notStarted)
                    state1.execute();
                if(view == null) break; //it is the condition to leave the game after joinGame

                //if the view has the right number of player, all players chose a color and the game in not started
                //the player can notify the controller to start the game
                if(view.getPlayers().size() == view.getMaxNumOfPlayer() && checkAllColor() && notStarted){
                    try {
                        client.startGame();
                        notStarted = false;

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                //if the game is not ready the thread needs to wait
                while (waitingForNewPlayers && notStarted) {
                    try {
                        synchronized (lock) {
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException();
                    }
                }
            } else if (view.getStatus() == Status.ACTIVE) {
                this.isGameSuspended = false;
                if(view.getTurn() != null && !view.getTurn().getPlayers().isEmpty()){
                    if (view.getCurrentPlayer().getNick().equals(nickname) && myTurn) {
                        state2 = new PlaceCardState(this, input);
                        state2.setView(view);
                        state2.execute();
                        myTurn = false;
                    }
                    while(!view.getCurrentPlayer().getNick().equals(nickname) && view.getStatus() == Status.ACTIVE){
                        ui.show_playerHand(view, nickname);
                        ui.show_gameStation(view);
                        try{
                            synchronized (lock) {
                                lock.wait();
                            }
                        }catch(InterruptedException e){
                            System.out.println("game interrupted");
                            throw new RuntimeException();
                        }
                    }
                }
            } else if (view.getStatus() == Status.SUSPENDED) {
                ui.show_GameStatus(view);
                ui.show_gameStation(view);
                this.isGameSuspended=true;
                while(view.getStatus() == Status.SUSPENDED) {
                    try {
                        synchronized (lock) {
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        System.out.println("this game got interrupted");
                        throw new RuntimeException();
                    }
                }
            } else if (view.getStatus() == Status.FINISHED) {
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
                if(winner.contains(nickname))
                    ui.show_youWin();
                else
                    ui.show_youLose();
                askToLeave();
                stay = false;
            }
        }
        System.out.println("application is closing...");
    }catch(Exception e){
        System.err.println("exception caught in FsmGame thread.");
        e.printStackTrace();
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

    /**
     * setter method for the client. It needs a client, and it sets it in the FsmGame object
     * @param client it is the instance of the client.
     */
    public void setClient(ClientAction client){
        this.client = client;
    }

    /**
     * getter method for the user interface
     * @return it returns the user interface (the specific type)
     */
    public UI getUi() {
        return ui;
    }

    /**
     * getter method for the view
     * @return it returns the immutable view of the game
     */
    public GameView getView(){
        return view;
    }

    /**
     * getter method for the client
     * @return it returns the client (the specific type)
     */
    public ClientAction getClient() {
        return client;
    }

    /**
     * getter method for the nickname
     * @return it returns the nickname of this specific user.
     */
    public String getNickname(){
        return nickname;
    }

    /**
     * verify if the point threshold is reached
     * @return it returns true if and only if one of the player has reached the point threshold.
     */
    public boolean isPointsThresholdReached(){return this.pointsThresholdReached;}

    /**
     * verify if the game is suspended
     * @return true if and only if the game is suspended
     */
    public boolean isGameSuspended(){return this.isGameSuspended;}

    /**
     * this method initialized the states for the thread.
     */
    public void initializeStates(){
        state1 = new StateMenu(this, this.input);
        state2 = new PlaceCardState(this, this.input);
    }

    /**
     * setter method for the game view
     * @param game the immutable view that needs to be set.
     */
    public void setGameView(GameView game){
        this.view = game;
    }

    /**
     * checks if all the player have already chosen a color
     * @return true if and only if all the player have a color.
     */
    private boolean checkAllColor(){
        return (this.view.getPlayers().stream()
                .map(p -> {
            if(p.getColor() != null)
                return p.getColor();
            else
                return null;
        }).filter(Objects::nonNull)
                .count() == this.view.getMaxNumOfPlayer());
    }

    //----------------------------------------------------------------
    //Notification from the server
    //----------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public void newGameCreated(String GameID) throws RemoteException {
        ui.show_playerJoined(GameID);
        new PingServer(this, this.client).start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void randomGameJoined(String GameID) throws RemoteException {
        ui.show_playerJoined(GameID);

        new PingServer(this, this.client).start();

        inGame = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reconnectedToGame(GameView view) throws RemoteException {
        setGameView(view);

        inGame = true;

        notStarted = false; //la partita era chiaramente iniziata questo dovrebbe risolvere i problemi senza dover cambiare il resto

        new PingServer(this, this.client).start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void gameIdNotExists(String gameId) throws RemoteException { //da sistemare manca procedura dopo notifica errore
        ui.show_invalidIdGame();
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePlayerStatus(GameView game) throws RemoteException {
        setGameView(game);
        ui.show_currentPlayersStatus(game);
        synchronized (lock){
            lock.notifyAll();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateGameStatus(GameView game) throws RemoteException {
        setGameView(game);
        ui.show_GameStatus(game);
        /*if(view.getMaxNumOfPlayer() == view.getPlayers().size()) {
            waitingForNewPlayers = false;
            lock.notify();
        }*/
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePlayerAndMaxNumberPlayer(GameView game) throws RemoteException {
        setGameView(game);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update20PointsReached(GameView game) throws RemoteException {
        ui.show_lastTurn();
        pointsThresholdReached = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateTableOfDecks(GameView game) throws RemoteException {
        setGameView(game);
        ui.show_tableOfDecks(game);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateGamestation(GameView game, GameStation gameStation) throws RemoteException {
        setGameView(game);
        ui.show_gameStation(game);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateColor(GameView game) throws RemoteException {
        setGameView(game);
        waitingForNewPlayers = true;
        ui.show_playerColors(game);
        ui.show_waitingOtherPlayers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateSetAvailableColors(GameView game, ArrayList<Color> colors) throws RemoteException{
        setGameView(game);
        waitingForNewPlayers = false;
        System.out.println("the choose color is not available anymore! please, select another one");
        state1 = new StateColor(this, this.input);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateTableAndTurn(GameView game) throws RemoteException {
        setGameView(game);
        state2 = new PlaceCardState(this, input);
        ui.show_tableOfDecks(game);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateHandAndTable(GameView game, String nick) throws RemoteException {
        setGameView(game);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goalCardsDrawed(ArrayList<GoalCard> cards) throws RemoteException {

        //this inserts the relevant attributes inside the Record
        DbCardInfo.getInstance().addCardRecord(new CardRecord(cards.getFirst().getType(), cards.getFirst().getCardId()), view.getId());
        DbCardInfo.getInstance().addCardRecord(new CardRecord(cards.getLast().getType(), cards.getLast().getCardId()), view.getId());

        int cardId;
        do {
            ui.show_requestGoalCard(cards);
            cardId = input.getCardId();
        }while(!checkIfValidId(cards, cardId));

        client.chooseGoal(cards, cardId, nickname);
    }

    /**
     * this  method checks if the chosen id is a valid id: there should be a card in the list that has the same id
     * @param cards the list of possible card
     * @param cardId the chosen card
     * @return true if and only if the id of the chosen card is contained in the list.
     */
    private boolean checkIfValidId(ArrayList<GoalCard> cards, int cardId){
        if(cardId < 0) return false;
        List<Integer> id = cards.stream()
                .map(Card::getCardId)
                .toList();
        return id.contains(cardId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateGoalPlayer(GameView game, GoalCard card) throws RemoteException {
        setGameView(game);
        ui.show_goalCard(card);
        ui.show_waitingOtherPlayers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateInitialCardsDrawn(InitialCard card) throws RemoteException {

        //this inserts the relevant attributes in the Record
        DbCardInfo.getInstance().addCardRecord(new CardRecord(card.getType(), card.getCardId()), view.getId());

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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void invalidCardPlacement() throws RemoteException {
        ui.show_invalidPlay();
        state2 = new PlaceNewCardState(this, this.input);
        state2.execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateGameStations(GameView game) throws RemoteException {
        setGameView(game);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePoints(GameView game) throws RemoteException {
        setGameView(game);
        ui.show_pointTable(game);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void genericErrorWhenEnteringGame(String msg, String id) throws RemoteException {
        //the switch has the same branches if the games wants to be extended this class could be easily modified.
        switch (msg){
            case("No games currently available to join..."):
                this.inGame = false;
                break;
            case ("The nickname used was not connected in the running game."):
                ui.show_invalidNickToReconnect(id);
                this.inGame = false;
                break;
            case("game not found"):
                this.inGame = false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void winner(GameView game, List<String> winners){
        this.winner = new ArrayList<>(winners);
        DbCardInfo.getInstance().addWinners(winners, view.getId());
        setGameView(game);
        synchronized (lock){
            lock.notify();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pingTheClient(GameView game) throws RemoteException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void abortGame() throws RemoteException{
        ui.show_abortGame();
        stay = false;
        Thread.getAllStackTraces().keySet().forEach(Thread::interrupt);
    }
}
