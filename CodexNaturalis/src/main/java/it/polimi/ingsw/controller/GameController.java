package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.gameDataManager.*;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.observer.GameObserver;
import it.polimi.ingsw.observer.HandleObserver;
import it.polimi.ingsw.view.GameFlow;

import java.awt.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

//IMPORTANTISSIMO!!!! LATO SERVER FAREMO UNA HASHMAP DI CLIENT E NICKNAME,
// LA MAGGIORPARTE DI QUESTI METODI SI BASA SUL NICKNAME
//DEL GIOCATORE E NON SULL'OGGETTO PLAYER

/**
 * This class handles all the operations relative to a single CodexNaturalis game, exposing
 * several methods that are called by {@link GameFlow}. Each method is also in charge to correctly
 * notify the Players affected the action performed.
 */
public class GameController implements GameControllerInterface, Serializable {
    /**The model of the game to control*/
    private final Game game;

    /**An HashMap that associates each player with a {@link HandleObserver} object*/
    private HashMap<String, HandleObserver> observers;

    /**
     * Constructor of the class. It's called by {@link MainController} when creating a new game
     * @param id unique code associated with the specific game
     * @param maxNumPlayers maximum number of players in the game
     */
    public GameController(String id, int maxNumPlayers) {
        game = new Game(id);
        game.setMaxNumberPlayer(maxNumPlayers);

        game.setPointTable(new PointTable());

        game.setTableOfDecks(new TableOfDecks());

        game.setTurn(new Turn(new ArrayList<Player>()));
        observers = new HashMap<>();
    }

    /**
     * Adds a {@link Player}, specifying its nickname, and a {@link GameObserver} to the
     * {@link GameController#observers} HashMap
     * @param obs Observer of the Player
     * @param p Player
     * @throws RemoteException
     */
    public void addObserver(GameObserver obs, Player p) throws RemoteException {
        if (observers.containsKey(p.getNick())){
            observers.remove(p.getNick());
            observers.put(p.getNick(), (new HandleObserver(obs)));
        } else {
            observers.put(p.getNick(), (new HandleObserver(obs)));
        }
    }

    //lasciate questo metodo che mi serve per i test sul controller
    public Game returnGame(){
        return game;
    }

    /**
     * Remove a {@link Player}, specifying its nickname from the {@link GameController#observers} HashMap
     * @param p Player
     * @throws RemoteException
     */
    public void removeObserver(Player p) {
       observers.remove(p.getNick());
    }

    /**
     * Method that sets the {@link GameController#game} Status to {@link Status#ACTIVE} and notify all the Players about
     * the beginning.
     */
    //prova
    public void start_Game() throws RemoteException{
        this.game.setStatus(Status.ACTIVE);

        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            HandleObserver obs = entry.getValue();
            obs.notify_startGame(game);
        }
        this.initializeTable();
        for (Player p : game.getPlayers().keySet()){
            this.getPossibleGoals(p.getNick());
            this.initializeHandPlayer(p.getNick());
        }
        //prova per sistemare turn
        ArrayList<Player> keysList = new ArrayList<>(game.getPlayers().keySet());
        Turn turn = new Turn(keysList);
        game.setTurn(turn);

        observers.get(this.getCurrentPlayer()).notify_CurrentPlayerUpdated(game);
    }


    /**
     * Set the Player's color
     * @param color {@link Color} chosen by the Player
     * @param name nickname of the Player
     */
    @Override
    public void setColor(String color, String name) {
        synchronized (this.game) {
            PointTable pointTable = game.getPointTable();
            HashMap<Player, Boolean> players = (HashMap<Player, Boolean>) game.getPlayers();

            if (!this.game.isColorAvailable(color)) {
                System.out.println("Color " + color + " is not available.");
                this.game.printAvailableColors();

                ArrayList<Color> notAvailableColor = new ArrayList<>(game.getPlayers().keySet().stream()
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

            for (Player player : players.keySet()) {
                if (player.getNick().equals(name)) {
                    player.setColor(Color.valueOf(color.toUpperCase()));
                    pointTable.setColorPoints(Color.valueOf(color.toUpperCase()));
                }
            }
                game.setPlayers(players);
                game.setPointTable(pointTable);
                for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
                    HandleObserver obs = entry.getValue();
                    obs.notify_color(game);
                }
        }
    }

    /**
     * Play a {@link PlayableCard} in a specific coordinate of the Player's {@link GameStation}
     *
     * @param playedCard
     * @param nick       The Player's nickname.
     * @param front      A boolean flag indicating whether the card should be played facing front or not.
     * @param cord       A {@link Point} object representing the chosen coordinate.
     */
    @Override
    public void playCard(PlayableCard playedCard, String nick, boolean front, Point cord){ //problema caso coordinata sbagliata, nel catch la carta rimossa va riaggiunta(altrimenti o non si usa la notify o si crea un altro metodo)
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        GameStation gamestation = null;
        for (Player player : players.keySet()) {
            if (player.getNick().equals(nick)) {
                try {
                    cardIsFrontChanger(playedCard, front);
                    player.playCard(playedCard, cord);
                    if(playedCard.getCardId()> 40 && front){
                        addPoints2Player(nick,calculateGoldPoints((GoldCard) playedCard,nick));

                    }else{
                        Set<Integer> validCardIds = new HashSet<>(Arrays.asList(8, 9, 10, 19, 20, 21, 28, 29, 30, 38, 39, 40));
                        if(front && validCardIds.contains(playedCard.getCardId())) {
                            addPoints2Player(nick, 1);
                        }
                    }
                    gamestation = player.getGameStation();
                    if(notify20PointReached() && !this.game.getTurn().checkIfLast()){
                        Turn turn = this.game.getTurn();
                        turn.setIsLast(true);
                        turn.setEndingPlayer(nick);
                        this.game.setTurn(turn);
                        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
                            HandleObserver obs = entry.getValue();
                            obs.notify_20PointsReached(game);
                        }
                    }
                }catch(illegalOperationException e) {
                    observers.get(nick).notify_invalidCardPlacement();
                    return;
                }
            }
        }
        game.setPlayers(players);

        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            //String chiave = entry.getKey();
            HandleObserver obs = entry.getValue();
            obs.notify_PlayCard(game,gamestation);
        }
    }

    //pescaggio da deck:
    //immagino che il giocatore inserisca una stringa da riga di comando in cui dice il tipo di deck da
    // cui vuole pescare

    /**
     * This method draw a PlayableCard from tableOfDecks and put it in the hand of the player.
     * It manages also the consistency of the model.
     * @param typo is the textual representation of the deck type.
     * @param nick is the nickname of the player
     */
    @Override
    public /*synchronized*/ void drawPlayableCardFromTableOfDecks(String typo, String nick) {
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        TableOfDecks table = game.getTableOfDecks();
        for (Player player : players.keySet()) {
            if (player.getNick().equals(nick)) {
                switch (typo) {
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
            obs.notify_DrawCard(game);
        }
    }

    /**
     * This method draws a {@link PlayableCard} from the face-up cards in {@link TableOfDecks}
     * @param cardSelected The card drawn.
     * @param nick The nickname of the Player that draws the card.
     */
    public /*synchronized*/ void drawFromTable(Card cardSelected, String nick) {
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        TableOfDecks table = game.getTableOfDecks();
        for (Player player : players.keySet()) {
            if (player.getNick().equals(nick)) {
                player.draw((PlayableCard) cardSelected);
                table.setCards(cardSelected);
            }
        }
        game.setTableOfDecks(table);
        game.setPlayers(players);
        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            HandleObserver obs = entry.getValue();
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
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        for (Player player : players.keySet()) {
            if (player.getNick().equals(nick)) {
                //Boolean status = players.remove(player); non serve perchè il valore viene sostituito da put()
                players.put(player, value);
            }
        }
        game.setPlayers(players);
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
    //@Override
    public void cardIsFrontChanger(Card card, Boolean value) {
        card.changeIsFront(value);
    }

    /**
     * This method initializes the {@link TableOfDecks} and the {@link Turn} object
     */
    //@Override
    public void initializeTable() {
        TableOfDecks table = game.getTableOfDecks();
        table.initializeTable();
        //ArrayList<Player> keysList = new ArrayList<>(game.getPlayers().keySet()); //non posso inizializzare turn senza goalCards
        //Turn turn = new Turn(keysList);
        //game.setTurn(turn);
        game.setTableOfDecks(table);
        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            HandleObserver obs = entry.getValue();
            obs.notify_InitializeTable(game);//capire che argomenti mettergli
        }
    }

    /**
     * This method checks if the 20 point threshold is reached.
     * @return A boolean flag representing the reaching of the threshold.
     */
    //@Override
    public boolean notify20PointReached() {
        return game.getPointTable().notify20PointReached();
    }

    //calcola i punti dei giocatori attraverso le carte obbiettivo, aggiorna la point table e ritorna  il giocatore con il punteggio più alto

    /**
     * This method calculates each Player's point and updates the {@link PointTable}.
     * @return The nickname of the winner.
     */
    @Override
    public String calculateWinner() {
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        PointTable pointTable = game.getPointTable();
        String winner = "nobody";
        int maxpoint = 0;
        for (Player player : players.keySet()) {
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
                winner = player.getNick();
            }
        }
        game.setPointTable(pointTable);
        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            HandleObserver obs = entry.getValue();
            obs.notify_FinalsPoint(game);
        }

        return winner;
    }

    /**
     * @return The nickname of the current Player.
     */
    public synchronized String getCurrentPlayer() {
        return game.getTurn().getCurrentPlayerNick();
    }

    /**
     * This method allows the transition to the next player at the end of each turn
     */
    @Override
    public void goOn() {
        Turn turn = game.getTurn();
        turn.goOn();
        if(turn.checkIfLast() && turn.getCurrentPlayer().getNick().equals(turn.getEndingPlayer())){
            this.game.setStatus(Status.FINISHED);
            String winner = calculateWinner();
            for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
                HandleObserver obs = entry.getValue();
                obs.notify_winner(game, winner);//capire che argomenti mettergli
            }
        }
        game.setTurn(turn);
        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            HandleObserver obs = entry.getValue();
            obs.notify_CurrentPlayerUpdated(game);//capire che argomenti mettergli
        }
    }

    //forse inutile vedere come funziona il patter observable
    //ritorna le carte visibili sul TableOfDecks(quelle che il giocatore può pescare)
    public synchronized ArrayList<Card> getCardsOnTableOfDecks() {
        return game.getTableOfDecks().getCards();
    }

    /**
     * Support method for the internal logic of {@link GameController} class. It calculates the score of a specific gold card.
     * @param card Specified card.
     * @param nick Nickname of the Player.
     * @return Calculated points.
     */
    //@Override
    public int calculateGoldPoints(GoldCard card, String nick) {
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        for (Player player : players.keySet()) {
            if (player.getNick().equals(nick)) {
                return player.getGameStation().calculateGoldPoints(card);
            }
        }
        return 0;
    }

    /**
     * Support method for the internal logic of {@link GameController} class. It increments the points of a specific Player.
     * @param nick Nickname of the Player.
     * @param point Amount of point to be added.
     */
    //@Override
    public void addPoints2Player(String nick, int point) {
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        PointTable pointTable = game.getPointTable();
        for (Player player : players.keySet()) {
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

    /**
     * Getter method.
     * @param nick Nickname of the Player.
     * @return The Player's hand.
     */
    //@Override
    public ArrayList<PlayableCard> showPlayerHand(String nick) {
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        for (Player player : players.keySet()) {
            if (player.getNick().equals(nick)) {
                return (ArrayList<PlayableCard>) player.showCard();
            }
        }
        return null;
    }

    /**
     * This method sets the personal {@link GoalCard}s of a Player.
     * @param goals The two possible {@link GoalCard}.
     * @param num The index of the selected {@link GoalCard}
     * @param nick The nickname of the Player.
     */
    @Override
    public void chooseGoal(ArrayList<GoalCard> goals, int num, String nick) {//bisogna gestire l'eccezione nel caso num non vada bene(se vogliamo)
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        for (Player player : players.keySet()) {
            if (player.getNick().equals(nick)) {
                player.chooseGoal(goals, num);
            }
        }
            game.setPlayers(players);
            observers.get(nick).notify_chooseGoal(game);
    }

    /**
     * This method initializes the Player's hand
     * @param nick Nickname of the Player.
     */
    @Override
    public void initializeHandPlayer(String nick) {
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        TableOfDecks table = game.getTableOfDecks();
        PointTable pointTable = game.getPointTable();
        for (Player player : players.keySet()) {
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
        observers.get(nick).notify_updatedHandAndTable(game, nick);//capire che argomenti mettere
    }

    //ritorna la lista di goalcard che l'utente può scegliere(dubbio, bisogna creare qualcosa del genere nel model e poi usare il
    //pattern observer?)

    /**
     * This method draws two {@link GoalCard} from the {@link TableOfDecks} to let the Player decide which one to choose.
     * @param nickname Nickname of the Player.
     */
    @Override
    public void getPossibleGoals(String nickname) {
        ArrayList<GoalCard> goals = new ArrayList<>();
        TableOfDecks table = game.getTableOfDecks();
        goals.add((GoalCard) table.getGoals().getFirst());
        goals.add((GoalCard) table.getGoals().getFirst());
        this.game.setTableOfDecks(table);
        HandleObserver observer = observers.get(nickname);
        observer.notify_goalCardsDrawed(goals);
        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            HandleObserver obs = entry.getValue();
            obs.notify_DrawCard(game);
        }
    }

    /**
     * This method adds a Player to a {@link Game}
     * @param p Player to be added
     * @throws MaxPlayersInException
     */
    //@Override
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
        return game.getPlayers().size()==game.getMaxNumberPlayer();
    }

    //metodo che ritorna i punti della carta risorsa
    //@Override
    public int getResourcePoint(ResourceCard card){
        return card.getNumberOfPoints();
    }
    //ritorna id del gioco
    //@Override
    public String getGameId() {
        return game.getId();
    }
    //@Override
    public HashMap<Player, Boolean> getPlayers(){
        return (HashMap<Player, Boolean>) game.getPlayers();
    }

    public Status getStatus(){
        return this.game.getStatus();
    }

    /**
     * This method reconnects a {@link Player} to a {@link Game}
     * @param player Player to be reconnected
     * @throws GameEndedException
     * @throws MaxPlayersInException
     */
    public void reconnectPlayer(Player player) throws GameEndedException, MaxPlayersInException {
        this.game.reconnectPlayer(player);
        changePlayerStatus(player.getNick(), true);
    }

    /**
     * This method allows a Player to leave the Game.
     * @param nick Player that wants to leave.
     */
    public void leave(String nick){
        this.observers.remove(nick);
        this.game.removePlayer(nick);
    }

    //la carta iniziale si trova in mano al giocatore come prima carta
    @Override
    public void setGameStation(String nick, int num, boolean front) {
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        for (Player player : players.keySet()) {
            if (player.getNick().equals(nick)) {
                Card card =player.showCard().get(0);
                cardIsFrontChanger(card,front);
                player.setGameStation(new GameStation((InitialCard) card));
                player.setCards(new ArrayList<>());
                this.game.setPlayers(players);
            }
        }
        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            HandleObserver allObs = entry.getValue();
            allObs.notify_updateGameStations(game);
        }
    }
    public void setGameStatus(Status status){
        this.game.setStatus(status);
        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            HandleObserver allObs = entry.getValue();
            allObs.notify_changedGameStatus(game);
        }
    }

    /**
     * This method assigns the black color to the first Player.
     */
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
    }

    public int getNumOfOnlinePlayers(){return this.game.getNumOfOnlinePlayers();}

    public HashMap<String, HandleObserver> getObservers() {
        return observers;
    }
}