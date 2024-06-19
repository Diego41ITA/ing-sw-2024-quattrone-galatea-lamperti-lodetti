package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.gameDataManager.*;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.observer.GameObserver;
import it.polimi.ingsw.observer.HandleObserver;
import it.polimi.ingsw.parse.SaverWriter;
import it.polimi.ingsw.view.FsmGame;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class handles all the operations relative to a single CodexNaturalis game, exposing
 * several methods that are called by {@link FsmGame}. Each method is also in charge to correctly
 * notify the Players affected the action performed.
 */
public class ControllerOfGame extends UnicastRemoteObject implements ControllerOfGameInterface/*, Serializable*/ {
    /**The model of the game to control*/
    private Game game;

    /**An HashMap that associates each player with a {@link HandleObserver} object*/
    private HashMap<String, HandleObserver> observers;

    /**
     * this attribute contains the name of the player and an indicator that holds the count of how many setup steps the
     * specific client has done.
     */
    public final HashMap<String, Integer> readiness = new HashMap<>();
    public boolean checkTurn = false;

    /**
     * Constructor of the class. It's called by {@link ControllerOfMatches} when creating a new game
     * @param id unique code associated with the specific game
     * @param maxNumPlayers maximum number of players in the game
     */
    public ControllerOfGame(String id, int maxNumPlayers) throws  RemoteException{
        game = new Game(id);
        game.setMaxNumberPlayer(maxNumPlayers);

        game.setPointTable(new PointTable());

        game.setTableOfDecks(new TableOfDecks());

        game.setTurn(new Turn(new ArrayList<Player>()));
        observers = new HashMap<>();
        this.checkTurn = false;
    }

    /**
     * this constructor builds this object by passing an existing game.
     * @param g is the saved game.
     * @throws RemoteException
     */
    public ControllerOfGame(Game g) throws RemoteException{
        this.game = g;
        this.observers = new HashMap<>();
        this.checkTurn = true;
    }

    /**
     * Adds a {@link Player}, specifying its nickname, and a {@link GameObserver} to the
     * {@link ControllerOfGame#observers} HashMap
     * @param obs Observer of the Player
     * @param p Player
     * @throws RemoteException
     */
    public void addObserver(GameObserver obs, String p) throws RemoteException {
        if (observers.containsKey(p)){
            observers.remove(p);
            observers.put(p, (new HandleObserver(obs)));
        } else {
            observers.put(p, (new HandleObserver(obs)));
        }
    }

    /**
     * This class is only used for test.
     * @return the game saved in this controller
     */
    public Game returnGame(){
        return game;
    }

    /**
     * Remove a {@link Player}, specifying its nickname from the {@link ControllerOfGame#observers} HashMap
     * @param p Player
     * @throws RemoteException
     */
    public void removeObserver(Player p) {
       observers.remove(p.getNick());
    }

    /**
     * this method is called by this.definePlayer(nick) and it notifies the client that the initial card got drawn.
     * @param nick is the name of the client that it will notify.
     */
    @Override
    public void initializePlayers(String nick){

        TableOfDecks table = game.getTableOfDecks();
        Deck<InitialCard> deck = table.getDeckStart();
        InitialCard card = deck.getFirst();
        observers.get(nick).notify_initialCardsDrawn(card);
        table.setDeckStart(deck);
        this.game.setTableOfDecks(table);
    }

    /**
     * Method that sets the {@link ControllerOfGame#game} Status to {@link Status#ACTIVE} and notify all the Players about
     * the beginning.
     */
    public void start_Game() throws RemoteException {
        this.game.setStatus(Status.ACTIVE);

        this.initializeTable();

        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            HandleObserver obs = entry.getValue();
            obs.notify_startGame(game);
        }

    }

    /**
     * this method is used to initialize the player object to avoid NullPointerException.
     * @param nick it's the name of the client that has called this method.
     * @throws RemoteException
     */
    @Override
    public void definePlayer(String nick) throws RemoteException{
        this.initializePlayers(nick);
        this.getPossibleGoals(nick);
        this.initializeHandPlayer(nick);
    }

    /**
     * this method initializes the Turn object only after all the players have been initialized.
     * @param nick
     * @throws RemoteException
     */
    @Override
    public synchronized void initializeTurn(String nick) throws RemoteException{
        if(checkReadiness()) {
            ArrayList<Player> keysList = new ArrayList<>(game.getPlayers());
            Turn turn = new Turn(keysList);
            turn.sort();
            game.setTurn(turn);

            //we need to save the game for the first time
            Thread saveThread = new Thread(()-> SaverWriter.saveGame(this.game));
            saveThread.start();
            this.checkTurn = true;

            observers.get(this.getCurrentPlayer()).notify_CurrentPlayerUpdated(game);
        }
    }

    /**
     * this method checks if all the players have completed all the initialization process
     * @return true if and only if all the player are ready, false otherwise.
     */
    private boolean checkReadiness(){
        boolean check = true;
        for(String n: readiness.keySet())
            if(readiness.get(n) < 3 )
                check = false;
        return check;
    }

    /**
     * Set the Player's color and it notifies the player (nick).
     * @param color {@link Color} chosen by the Player
     * @param name nickname of the Player
     */
    @Override
    public synchronized void setColor(String color, String name) {
        synchronized (this.game) {
            PointTable pointTable = game.getPointTable();
            List<Player> players = game.getPlayers();

            if (!this.game.isColorAvailable(color)) {
                System.out.println("Color " + color + " is not available.");
                this.game.printAvailableColors();

                ArrayList<Color> notAvailableColor = new ArrayList<>(game.getPlayers().stream()
                        .map(Player::getColor)
                        .collect(Collectors.toList()));

                ArrayList<Color> availableColor = new ArrayList<>();

                for(Color c: Color.values()){
                    if(!notAvailableColor.contains(c))
                        availableColor.add(c);
                }

                for(HashMap.Entry<String, HandleObserver> entry : observers.entrySet()){
                    HandleObserver obs = entry.getValue();
                    if(entry.getKey().equals(name))
                        obs.notify_colorTaken(game, availableColor );
                }
                return;
            }

            for (Player player : players) {
                if (player.getNick().equals(name)) {
                    player.setColor(Color.valueOf(color.toUpperCase()));
                    pointTable.setColorPoints(Color.valueOf(color.toUpperCase()));
                }
            }
                game.setPlayers(players);
                game.setPointTable(pointTable);
                for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
                    HandleObserver obs = entry.getValue();
                    if(entry.getKey().equals(name))
                        obs.notify_color(game);
                }
        }
    }

    /**
     * Play a {@link PlayableCard} in a specific coordinate of the Player's {@link GameStation}
     *
     * @param playedCard The PlayableCard to play.
     * @param nick       The Player's nickname.
     * @param front      A boolean flag indicating whether the card should be played facing front or not.
     * @param cord       A {@link Point} object representing the chosen coordinate.
     */
    @Override
    public void playCard(PlayableCard playedCard, String nick, boolean front, Point cord) throws RemoteException, illegalOperationException { //problema caso coordinata sbagliata, nel catch la carta rimossa va riaggiunta(altrimenti o non si usa la notify o si crea un altro metodo)
        List<Player> players;
        players = game.getPlayers();
        GameStation gamestation = null;
        Turn turn = game.getTurn();
        for (Player player : players) {
            if (player.getNick().equals(nick)) {
                try {
                    cardIsFrontChanger(playedCard, front);
                    player.playCard(playedCard, cord);
                    if(playedCard.getCardId()> 40 && front){
                        addPoints2Player(nick,calculateGoldPoints((GoldCard) playedCard,nick));

                    }else{
                        Set<Integer> validCardIds = new HashSet<>(Arrays.asList(8, 9, 10, 18, 19, 20, 28, 29, 30, 38, 39, 40));
                        if(front && validCardIds.contains(playedCard.getCardId())) {
                            addPoints2Player(nick, 1);
                        }
                    }
                    gamestation = player.getGameStation();
                    //now checks if it isn't the last lap and if someone has reached 20 points.
                    if(notify20PointReached() && !turn.checkIfLast()){
                        turn.setIsLast(true);
                        turn.setEndingPlayer(turn.getFirstPlayerNick());
                        this.game.setTurn(turn);

                        //if the player that reached 20 points is the last player I don't forward any notification to the other clients.
                        if(!turn.getLastPlayerNick().equals(nick)) {
                            for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
                                HandleObserver obs = entry.getValue();
                                obs.notify_20PointsReached(game);
                            }
                        }
                    }
                    //ora notifico che la carta è stata pescata
                    game.setPlayers(players);

                    for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
                        //String chiave = entry.getKey();
                        HandleObserver obs = entry.getValue();
                        if(entry.getKey().equals(nick))
                            obs.notify_PlayCard(game,gamestation);
                    }
                    break;

                }catch(illegalOperationException e) {
                    observers.get(nick).notify_invalidCardPlacement();
                    throw new illegalOperationException("invalid card placement");
                }
            }
        }
    }

    /**
     * This method draw a PlayableCard from tableOfDecks and put it in the hand of the player.
     * It manages also the consistency of the model.
     * @param typo is the textual representation of the deck type.
     * @param nick is the nickname of the player
     */
    @Override
    public /*synchronized*/ void drawPlayableCardFromTableOfDecks(String typo, String nick) {
        String parsedTypo = typo.toLowerCase();

        List<Player> players = game.getPlayers();
        TableOfDecks table = game.getTableOfDecks();
        for (Player player : players) {
            if (player.getNick().equals(nick)) {
                switch (parsedTypo) {
                    case "gold" -> {
                        Deck<GoldCard> deck = table.getDeckGold();
                        player.drawGold(deck);
                        table.setDeckGold(deck);
                        game.setTableOfDecks(table);

                    }
                    case "resource" -> {
                        Deck<ResourceCard> deck = table.getDeckResource();
                        player.drawResource(deck);
                        table.setDeckResource(deck);
                        game.setTableOfDecks(table);

                    }
                    case "initial" ->{
                        Deck<InitialCard> deck = table.getDeckStart();
                        player.drawInitial(deck);
                        table.setDeckStart(deck);
                        game.setTableOfDecks(table);
                    }
                    default -> {
                        //gestire l'eccezzione in cui non viene inserita una string corretta(se vogliamo)
                    }
                }
                game.setPlayers(players);
            }
        }
        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            HandleObserver obs = entry.getValue();
            if(entry.getKey().equals(nick))
                obs.notify_DrawCard(game);
        }
    }

    /**
     * This method draws a {@link PlayableCard} from the face-up cards in {@link TableOfDecks}
     * @param cardSelected The card drawn.
     * @param nick The nickname of the Player that draws the card.
     */
    public /*synchronized*/ void drawFromTable(Card cardSelected, String nick) {
        List<Player> players;
        players = game.getPlayers();
        TableOfDecks table = game.getTableOfDecks();
        for (Player player : players) {
            if (player.getNick().equals(nick)) {
                player.draw((PlayableCard) cardSelected);
                table.setCards(cardSelected);
            }
        }
        game.setTableOfDecks(table);
        game.setPlayers(players);
        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            HandleObserver obs = entry.getValue();
            if(entry.getKey().equals(nick))
                obs.notify_DrawCard(game);
        }
    }

    /**
     * This method changes the {@link Player}'s connection status.
     * @param nick The nickname of the Player.
     * @param value A boolean flag representing the connection status.
     */
    //@Override
    public void changePlayerStatus(String nick, Boolean value) {
        /*List<Player> players;
        players = game.getPlayers();
        for (Player player : players) {
            if (player.getNick().equals(nick)) {
                //Boolean status = players.remove(player); non serve perchè il valore viene sostituito da put()
                players.put(player, value);
            }
        }
        game.setPlayers(players);*/

        Map<String, Boolean> activity = game.getActivity();
        for (String player : activity.keySet()) {
            if (player.equals(nick)) {
                //Boolean status = players.remove(player); non serve perchè il valore viene sostituito da put()
                activity.put(player, value);
            }
        }
        game.setActivity(activity);

        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            HandleObserver obs = entry.getValue();
            obs.notify_ChangedPlayerStatus(game);
        }
    }

    /**
     * This method changes the attribute of the card related to whether it is played front or back.
     *
     * @param card  The card to be modified.
     * @param value A boolean flag: true for front, false for back.
     */
    public void cardIsFrontChanger(Card card, Boolean value) {
        card.changeIsFront(value);
    }

    /**
     * This method initializes the {@link TableOfDecks}
     */
    public void initializeTable() {
        TableOfDecks table = game.getTableOfDecks();
        table.initializeTable();
        game.setTableOfDecks(table);
    }

    /**
     * This method checks if the 20 point threshold is reached.
     * @return A boolean flag representing the reaching of the threshold.
     */
    public boolean notify20PointReached() {
        return game.getPointTable().notify20PointReached();
    }

    /**
     * This method calculates each Player's point and updates the {@link PointTable}.
     * @return The nickname of the winner.
     */
    @Override
    public String calculateWinner() {
        List<Player> players = game.getPlayers();
        PointTable pointTable = game.getPointTable();
        String winnerNick = "nobody";
        int maxpoint = 0;
        for (Player player : players) {
            int point = 0;
            point = player.getGoal()
                    .getGoalPoints(
                            (HashMap<Point, PlayableCard>) player.getGameStation().getPlayedCards(),
                            (HashMap<Item, Integer>) player.getGameStation().calculateAvailableResources()
                    );
            ArrayList<GoalCard> goals = (ArrayList<GoalCard>) game.getTableOfDecks().getGoals();
            for (GoalCard goalCard : goals) {
                point = point + goalCard.getGoalPoints(
                                (HashMap<Point, PlayableCard>) player.getGameStation().getPlayedCards(),
                                (HashMap<Item, Integer>) player.getGameStation().calculateAvailableResources()
                        );
            }
            point = point + pointTable.getPoint(player);
            pointTable.updatePoint(player, point);
            if (point > maxpoint) {
                maxpoint = point;
                winnerNick = player.getNick();
            }
        }
        game.setPointTable(pointTable);
        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            HandleObserver obs = entry.getValue();
            obs.notify_FinalsPoint(game);
        }

        return winnerNick;
    }

    /**
     * @return The nickname of the current Player.
     */
    public synchronized String getCurrentPlayer() {
        return game.getTurn().getCurrentPlayerNick();
    }

    /**
     * This method allows the transition to the next player at the end of each turn it's invoked by the client.
     * This method also notifies all the player about the new current player.
     */
    @Override
    public void goOn() {
        Turn turn = game.getTurn();
        turn.goOn();
        String currentPlayer = turn.getCurrentPlayerNick();
        String endingPlayer = turn.getEndingPlayer();
        game.setTurn(turn);

        if(turn.checkIfLast() && currentPlayer.equals(endingPlayer)){
            game.setStatus(Status.FINISHED);
            String winnerNick = calculateWinner();
            for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
                HandleObserver obs = entry.getValue();
                obs.notify_winner(game, winnerNick);//capire che argomenti mettergli
            }
            return;
        }
        if(checkEnoughPlayer()) {
            for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
                HandleObserver obs = entry.getValue();
                obs.notify_CurrentPlayerUpdated(game);//capire che argomenti mettergli
            }
        }else{
            //if there are not enough player the game is suspended and we do not notify the client.
            this.game.suspend();
            System.out.println("this game " + this.getGameId() + "is suspended");
        }
        //this should save the entire game.
        Thread saveThread = new Thread(()-> SaverWriter.saveGame(this.game));
        saveThread.start();
    }

    private boolean checkEnoughPlayer(){
        Map<String, Boolean> activity = game.getActivity();
        long numOnlinePlayer = activity.values().stream()
                .filter(b -> b)
                .count();
        return (numOnlinePlayer >= 2);
    }

    /**
     * Support method for the internal logic of {@link ControllerOfGame} class. It calculates the score of a specific gold card.
     * @param card Specified card.
     * @param nick Nickname of the Player.
     * @return Calculated points.
     */
    //@Override
    private int calculateGoldPoints(GoldCard card, String nick) {
        List<Player> players;
        players = game.getPlayers();
        for (Player player : players) {
            if (player.getNick().equals(nick)) {
                return player.getGameStation().calculateGoldPoints(card);
            }
        }
        return 0;
    }

    /**
     * Support method for the internal logic of {@link ControllerOfGame} class. It increments the points of a specific Player.
     * @param nick Nickname of the Player.
     * @param point Amount of point to be added.
     */
    //leave public because it's used in testController
    public void addPoints2Player(String nick, int point) {
        List<Player> players;
        players = game.getPlayers();
        PointTable pointTable = game.getPointTable();
        for (Player player : players) {
            if (player.getNick().equals(nick)) {
                point = point + pointTable.getPoint(player);
                pointTable.updatePoint(player, point);
            }
        }

        game.setPointTable(pointTable);
        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            HandleObserver obs = entry.getValue();
            obs.notify_UpdatePoints(game);
        }

    }

    //----------------------------------------------------------------------------------------------------------
    /**
     * Getter method.
     * @param nick Nickname of the Player.
     * @return The Player's hand.
     */
    //@Override
    public ArrayList<PlayableCard> showPlayerHand(String nick) {
        List<Player> players;
        players = game.getPlayers();
        for (Player player : players) {
            if (player.getNick().equals(nick)) {
                return (ArrayList<PlayableCard>) player.showCard();
            }
        }
        return null;
    }
    //----------------------------------------------------------------------------------------------------------


    /**
     * This method sets the personal {@link GoalCard}s of a Player. It is invoked by the player and it notifies him
     * that the card got actually chosen.
     * @param goals The two possible {@link GoalCard}.
     * @param num The index of the selected {@link GoalCard}
     * @param nick The nickname of the Player.
     */
    @Override
    public void chooseGoal(ArrayList<GoalCard> goals, int num, String nick) {//bisogna gestire l'eccezione nel caso num non vada bene(se vogliamo)
        List<Player> players;
        players = game.getPlayers();
        GoalCard card = null;
        for (Player player : players) {
            if (player.getNick().equals(nick)) {
                player.chooseGoal(goals, num);
                card = new GoalCard(player.getGoal());
            }
        }
        game.setPlayers(players);

        observers.get(nick).notify_chooseGoal(game, card);

        readiness.put(nick, readiness.get(nick) + 1);

        try {
            this.initializeTurn(nick);
        } catch (RemoteException e) {
            System.out.println("something went wrong during turn initialization process");
        }
    }

    /**
     * This method initializes the Player's hand, it is called by GameController.definePlayer(nick).
     * @param nick Nickname of the Player.
     */
    @Override
    public void initializeHandPlayer(String nick) {
        List<Player> players;
        players = game.getPlayers();
        TableOfDecks table = game.getTableOfDecks();
        PointTable pointTable = game.getPointTable();
        for (Player player : players) {
            if (player.getNick().equals(nick)) {
                Deck<GoldCard> deck = table.getDeckGold();
                player.drawGold(deck);
                table.setDeckGold(deck);
                table.setDeckGold(deck);
                Deck<ResourceCard> deckR = table.getDeckResource();
                player.drawResource(deckR);
                player.drawResource(deckR);
                table.setDeckResource(deckR);
                game.setPlayers(players);
                game.setTableOfDecks(table);
            }

        }
        observers.get(nick).notify_updatedHandAndTable(game, nick);

        readiness.put(nick, readiness.get(nick) + 1);

        try {
            this.initializeTurn(nick);
        } catch (RemoteException e) {
            System.out.println("something went wrong during turn initialization process");
        }
    }

    /**
     * This method draws two {@link GoalCard} from the {@link TableOfDecks} to let the Player decide which one to choose.
     * @param nickname Nickname of the Player.
     */
    @Override
    public void getPossibleGoals(String nickname) {
        ArrayList<GoalCard> goals = new ArrayList<>();
        TableOfDecks table = game.getTableOfDecks();
        goals.add(table.getDeckGoal().getFirst());
        this.game.setTableOfDecks(table);
        goals.add(table.getDeckGoal().getFirst());
        this.game.setTableOfDecks(table);
        HandleObserver observer = observers.get(nickname);
        observer.notify_goalCardsDrawed(goals);
    }

    /**
     * This method adds a Player to a {@link Game} and notify other players that a new player joined the game.
     * @param p Player to be added
     * @throws MaxPlayersInException
     */
    public void addPlayer(Player p) throws MaxPlayersInException {
        game.addPlayer(p);
        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            HandleObserver obs = entry.getValue();
            obs.notify_AddedPlayer(game);
        }
    }

    /**
     * This method check if there are enough player to start the Game.
     * @return boolean flag.
     */
    public synchronized boolean checkIfStart(){
        return game.getPlayers().size()==game.getMaxNumberPlayer()
                && game.getActivity().values().stream().filter(b -> b).count() >= 2;
    }

    //----------------------------------------------------------------------------------------------------------
    //ritorna id del gioco
    public String getGameId() {
        return game.getId();
    }
    public HashMap<Player, Boolean> getPlayers(){

        HashMap<Player, Boolean> activityPlayer = new HashMap<>();

        for(Player player : game.getPlayers())
        {
            Boolean activity = game.getActivity().get(player.getNick());
            activityPlayer.put(player, activity);
        }

        return activityPlayer;
    }

    public Status getStatus(){
        return this.game.getStatus();
    }
    //----------------------------------------------------------------------------------------------------------


    /**
     * This method reconnects a {@link Player} to a {@link Game}
     * @param player Player to be reconnected
     * @throws GameEndedException
     * @throws MaxPlayersInException
     */
    public synchronized void reconnectPlayer(String player) throws GameEndedException, MaxPlayersInException {
        this.game.reconnectPlayer(player);
        //if there are enough player to restart playing it should be done.
        if(checkEnoughPlayer()) {
            game.setStatus(Status.ACTIVE);
            for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
                HandleObserver obs = entry.getValue();
                obs.notify_CurrentPlayerUpdated(game);//capire che argomenti mettergli
            }
        }
        changePlayerStatus(player, true);
    }

    /**
     * This method allows a Player to leave the Game.
     * @param nick Player that wants to leave.
     */
    public void leave(String nick){
        this.observers.remove(nick);
        this.game.removePlayer(nick);
    }

    /**
     * This method is called by the client when he chose the initial card (the side where he likes to place it);
     * and it sets the card on the player's GameStation and at the end it notifies the client about the success
     * of the operation.
     * @param nick it is the name of the client
     * @param card it is the initial card to place
     * @param front indicates if the card needs to be displaced on its front or its back.
     */
    @Override
    public void setGameStation(String nick, InitialCard card, boolean front) {

        List<Player> players;
        players = game.getPlayers();
        TableOfDecks table = game.getTableOfDecks();

        for (Player player : players) {
            if (player.getNick().equals(nick)) {
                cardIsFrontChanger(card, front);
                player.setGameStation(new GameStation(card));
                this.game.setPlayers(players);
                this.game.setTableOfDecks(table);
            }
        }
        observers.get(nick).notify_updateGameStations(game);

        readiness.put(nick, readiness.get(nick) + 1);
        try {
            this.initializeTurn(nick);
        } catch (RemoteException e) {
            System.out.println("something went wrong in turn initialization process");
        }
    }

    /**
     * This method assigns the black color to the first Player.

    public void assignBlackColor(){
        String nick = this.game.getTurn().getFirstPlayerNick();
        HashMap<Player, Boolean> players = (HashMap<Player, Boolean>) this.game.getPlayers();
        for (Player player : players.keySet()) {
            if (player.getNick().equals(nick)) {
                player.setOptionalColor();
                this.game.setPlayers(players);
                observers.get(nick).notify_color(game);
            }
        }
    }*/

    /**
     * @return it returns the number of online player.
     */
    public int getNumOfOnlinePlayers(){return this.game.getNumOfOnlinePlayers();}

    /**
     * it's a getter method for the observer map
     * @return the observer map: so the name and the object that's used to notify the observer.
     */
    public HashMap<String, HandleObserver> getObservers() {
        return observers;
    }

    /**
     * allows the client to ping the server.
     * @param nick the nickname of the client
     */
    public void ping(String nick){
        //Bisogna fare in modo che il client setti qualcosa che faccia capire che è online.
        //Probabilmente non serve fare nulla al limite settare che è online.

        /*
        Si potrebbe aggiungere un metodo analogo a quello del client che pinga tutti i client e se sono irraggiungibili
        catcha l'eccezione e li setta a false nella activity.
        Possibili problemi sul ritardo e l'asincronismo: basterebbe pingare il current player però potrebbe
        disconnettersi subito dopo il ping.
         */
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object controller){
        if(controller instanceof ControllerOfGame){
            ControllerOfGame otherController = (ControllerOfGame) controller;
            if(otherController.getGameId().equals(this.getGameId()))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.getGameId().hashCode();
    }
}