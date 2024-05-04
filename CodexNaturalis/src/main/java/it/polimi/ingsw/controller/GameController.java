package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.model.exceptions.MaxPlayersInException;
import it.polimi.ingsw.model.exceptions.PlayerAlreadyInException;
import it.polimi.ingsw.model.exceptions.illegalOperationException;
import it.polimi.ingsw.model.gameDataManager.*;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.observer.GameObserver;
import it.polimi.ingsw.observer.HandleObserver;

import java.awt.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

//IMPORTANTISSIMO!!!! LATO SERVER FAREMO UNA HASHMAP DI CLIENT E NICKNAME, LA MAGGIORPARTE DI QUESTI METODI SI BASA SUL NICKNAME
//DEL GIOCATORE E NON SULL'OGGETTO PLAYER
public class GameController implements GameControllerInterface, Serializable {
    /**
     * the game to control
     */
    private Game game;
    //ho una hashMap di string(nome del player) e di handleObserver
    private HashMap<String, HandleObserver> observers;

    public GameController(String id) {
        game = new Game(id);
        game.setPointTable(new PointTable());
        game.setTableOfDecks(new TableOfDecks());
        game.setTurn(new Turn(new ArrayList<Player>()));
        observers = new HashMap<>();
    }
    //aggiunge observer al model
    public void addObserver(GameObserver obs, Player p) throws RemoteException {
        if (observers.containsKey(p.getNick())){
            observers.remove(p.getNick());
            observers.put(p.getNick(), (new HandleObserver(obs)));
        } else {
            observers.put(p.getNick(), (new HandleObserver(obs)));
        }
    }

    //rimuove observer dal model
    public void removeObserver(Player p) {
       observers.remove(p.getNick());
    }

    //dire a diego che quando fa joinRandomgame deve aggiungere un giocatore alla partita
    //controllare gli attributi che vengono cambiati
    //all'interno di questo metodo vengono aggiunti i player all'interno della partita e anche il numero massimo di giocatori
    @Override
    public void setMaxNumberPlayers(String name, int max) {
        game.setStatus(Status.WAITING);
        Player player = new Player();
        player.setNickname(name);
        game.setSinglePlayer(player);
        game.setMaxNumberPlayer(max);
        observers.get(name).notify_setMaxNumberPlayers(game);//metodo da mettere nella gameListe
    }

    //viene messo il colore nella pointtable e nel player
    @Override
    public void setColor(String color, String name) {
        PointTable pointTable = game.getPointTable();
        HashMap<Player, Boolean> players = (HashMap<Player, Boolean>) game.getPlayers();
        for (Player player : players.keySet()) {
            if (player.getNick().equals(name)) {
                player.setColor(Color.valueOf(color.toUpperCase()));
                pointTable.setColorPoints(Color.valueOf(color.toUpperCase()));
            }
            game.setPlayers(players);
            game.setPointTable(pointTable);
            for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
                HandleObserver obs = entry.getValue();
                obs.notify_color(game);
            }
        }
    }

    //pgioca una carta sul tavolo da gioco
    @Override
    public void playCard(PlayableCard card, Point cord, String nick) throws illegalOperationException {
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        GameStation gamestation = null;
        for (Player player : players.keySet()) {
            if (player.getNick().equals(nick)) {
                try {
                    player.playCard(card, cord);
                    gamestation = player.getGameStation();
                }catch(illegalOperationException e) {
                    observers.get(nick).notify_notEnoughResource();
                    return;
                }//manca gestire la coordinata non valida
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
//immagino che il giocatore inserisca una stringa da riga di comando in cui dice il tipo di deck da cui vuole pescare

    /**
     * this method draw a card from tableOfDecks and put it in the hand of the player. It manages also the consistency
     * of the model.
     * @param typo is the textual representation of the deck type.
     * @param nick is the nickname of the player
     */
    public synchronized void drawFromDeck(String typo, String nick) {
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
                    case "goal" -> {
                        Deck<GoalCard> deck = table.getDeckGoal();
                        player.drawGoal(deck);
                        table.setDeckGoal(deck);
                        game.setTableOfDecks(table);

                    }
                    case "resource" -> {
                        Deck<ResourceCard> deck = table.getDeckResource();
                        player.drawResource(deck);
                        table.setDeckResource(deck);
                        game.setTableOfDecks(table);

                    }
                    case "initial" -> {
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

    //public synchronized void drawFromTable(int cardSelected, String nick)
    public synchronized void drawFromTable(Card cardSelected, String nick) {
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        TableOfDecks table = game.getTableOfDecks();
        ArrayList<Card> cards = table.getCards();
        for (Player player : players.keySet()) {//attenzione non viene gestita l'eccezzione in cui non c'è il giocatore con tale nick
            if (player.getNick().equals(nick)) {
                for (Card card : cards) {  //attenzione non viene gestita l'eccezzione in cui non c'è tale carta
                    if (card.equals(cardSelected)) {
                        player.draw((PlayableCard) card);
                        table.setCards(card);
                    }
                }


            }
        }
        game.setTableOfDecks(table);
        game.setPlayers(players);
        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            HandleObserver obs = entry.getValue();
            obs.notify_DrawCard(game);
        }
    }

    //cambia lo stato dei giocatori(connesso o non connesso)
    @Override
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

    //cambia il modo di piazzare la carta
    @Override
    public Card cardIsFrontChanger(Card card, Boolean value) {
        card.changeIsFront(value);
        return card;
    }

    //inizializza il tavolo da gioco, compreso i giocatori nei turni
    @Override
    public void initializeTable() {
        TableOfDecks table = game.getTableOfDecks();
        table.initializeTable();
        ArrayList<Player> keysList = new ArrayList<>(game.getPlayers().keySet());
        Turn turn = new Turn(keysList);
        game.setTurn(turn);
        game.setTableOfDecks(table);
        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            HandleObserver obs = entry.getValue();
            obs.notify_InitializeTable(game);//capire che argomenti mettergli
        }
    }
@Override
    public boolean notify20PointReached() {
        return game.getPointTable().notify20PointReached();
    }

    //calcola i punti dei giocatori attraverso le carte obbiettivo, aggiorna la point table e ritorna  il giocatore con il punteggio più alto
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

    //ritorna il nick del giocatore attuale
    public synchronized String getCurrentPlayer() {
        return game.getTurn().getCurrentPlayerNick();
    }

    //passa al giocatore successivo cambiando il giocatore corrente
    @Override
    public void goOn() {
        Turn turn = game.getTurn();
        turn.goOn();
        game.setTurn(turn);
        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            HandleObserver obs = entry.getValue();
            obs.notify_CurrentPlayerUpdated(game);//capire che argomenti mettergli
        }
    }

    //forse inutile vedere come funziona il patter observable
//ritorna le carte visibili sul TableOfDecks(quelle che il giocatore può pescare)
    public synchronized ArrayList<Card> cardsOnTableOfDecks() {
        return game.getTableOfDecks().getCards();
    }

    //calcola i punti della carta oro e li ritorna
    @Override
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
    //somma ai punti del giocatore tot punti
    @Override
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
            //String chiave = entry.getKey();
            HandleObserver obs = entry.getValue();
            obs.notify_UpdatePoints(game);
        }

    }

    //forse inutile vedere come funziona il patter observable
    @Override
    public ArrayList<PlayableCard> showPlayerHand(String nick) {
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        PointTable pointTable = game.getPointTable();
        for (Player player : players.keySet()) {
            if (player.getNick().equals(nick)) {
                return (ArrayList<PlayableCard>) player.showCard();

            }
        }
        return null;
    }

    //metodo per scegliere la carta obiettivo
    @Override
    public void chooseGoal(ArrayList<GoalCard> goals, int num, String nick) {//bisogna gestire l'eccezione nel caso num non vada bene(se vogliamo)
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        for (Player player : players.keySet()) {
            if (player.getNick().equals(nick)) {
                player.chooseGoal(goals, num);
            }
            game.setPlayers(players);
            observers.get(nick).notify_chooseGoal(game);
        }
    }
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
        observers.get(nick).notify_updatedHandAndTable(game);//capire che argomenti mettere


    }

    //ritorna la lista di goalcard che l'utente può scegliere(dubbio, bisogna creare qualcosa del genere nel model e poi usare il
    //pattern observer?)
     @Override
    public ArrayList<GoalCard> getPossibleGoals() {
        ArrayList<GoalCard> goals = new ArrayList<>();
        goals.add((GoalCard) game.getTableOfDecks().getGoals().getFirst());
        goals.add((GoalCard) game.getTableOfDecks().getGoals().getFirst());
        return goals;

    }
//aggiunge un giocatore alla partita(gestire il caso in cui non si possa aggiungere)
    @Override
    public void addPlayer(Player p) throws MaxPlayersInException, PlayerAlreadyInException {
        game.addPlayer(p);
        for (HashMap.Entry<String, HandleObserver> entry : observers.entrySet()) {
            //String chiave = entry.getKey();
            HandleObserver obs = entry.getValue();
            obs.notify_AddedPlayer(game);
        }
    }

    //controlla se abbiamo raggiunto il numero massimo di giocatori e possiamo iniziare
    public synchronized boolean checkIfStart(){
        return game.getPlayers().size()==game.getMaxNumberPlayer();
    }
    //metodo che ritorna i punti della carta risorsa
    @Override
    public int getResourcePoint(ResourceCard card){
        return card.getNumberOfPoints();
    }
    //ritorna id del gioco
    @Override
    public String getGameId() {
        return game.getId();
    }
    @Override
    public HashMap<Player, Boolean> getPlayers(){
        return (HashMap<Player, Boolean>) game.getPlayers();
    }

    public Status getStatus(){
        return this.game.getStatus();
    }


    //metodi da implementare
    public void reconnectPlayer(Player player) throws GameEndedException, MaxPlayersInException {}
    public void leave(GameObserver obs, String nick){}
    public int getNumOfOnlinePlayers(){return 0;}

}


