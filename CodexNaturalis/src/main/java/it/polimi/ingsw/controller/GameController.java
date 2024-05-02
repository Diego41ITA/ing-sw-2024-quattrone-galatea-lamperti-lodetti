package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.exceptions.illegalOperationException;
import it.polimi.ingsw.model.gameDataManager.*;
import it.polimi.ingsw.model.gameDataManager.Color;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//IMPORTANTISSIMO!!!! LATO SERVER FAREMO UNA HASHMAP DI CLIENT E NICKNAME, LA MAGGIORPARTE DI QUESTI METODI SI BASA SUL NICKNAME
//DEL GIOCATORE E NON SULL'OGGETTO PLAYER
public class GameController implements Serializable {
    /**
     * the game to control
     */
    private Game game;

    public GameController(String id) {
        game = new Game(id);
        game.setPointTable(new PointTable());
        game.setTableOfDecks(new TableOfDecks());
        game.setTurn(new Turn(new ArrayList<Player>()));
    }

    //dire a diego che quando fa joinRandomgame deve aggiungere un giocatore alla partita
    //controllare gli attributi che vengono cambiati
    //all'interno di questo metodo vengono aggiunti i player all'interno della partita e anche il numero massimo di giocatori
    public void setMaxNumberPlayers(String name, int max) {
        game.setStatus(Status.WAITING);
        Player player = new Player();
        player.setNickname(name);
        game.setSinglePlayer(player);
        game.setMaxNumberPlayer(max);
    }

    //viene messo il colore nella pointtable e nel player
    public void setColor(String color, String name) {
        HashMap<Player, Boolean> players;
        PointTable pointTable = game.getPointTable();
        players = (HashMap) game.getPlayers();
        for (Player player : players.keySet()) {
            if (player.getNick().equals(name)) {
                player.setColor(Color.valueOf(color.toUpperCase()));
                pointTable.setColorPoints(Color.valueOf(color.toUpperCase()));
            }
            game.setPlayers(players);
            game.setPointTable(pointTable);

        }
    }

    //pescaggio di una delle carte scoperte sul tavolo
    public void playCard(PlayableCard card, Point cord, String nick) throws illegalOperationException {
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        for (Player player : players.keySet()) {
            if (player.getNick().equals(nick)) {
                player.playCard(card, cord);//l'rmi dovrà gestire l'eccezione o il gameflow evita che venga inserita una
                //coordinata non valida
            }

        }
        game.setPlayers(players);
    }

    //pescaggio da deck:
//immagino che il giocatore inserisca una stringa da riga di comando in cui dice il tipo di deck da cui vuole pescare
    public void draw(String tipo, String nick) {
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        TableOfDecks table = game.getTableOfDecks();
        for (Player player : players.keySet()) {
            if (player.getNick().equals(nick)) {
                if (tipo.equals("gold")) {
                    Deck deck = table.getDeckGold();
                    player.draw(deck);
                    table.setDeckGold(deck);
                    game.setTableOfDecks(table);

                } else if (tipo.equals("goal")) {
                    Deck deck = table.getDeckGoal();
                    player.draw(deck);
                    table.setDeckGoal(deck);
                    game.setTableOfDecks(table);

                } else if (tipo.equals("resource")) {
                    Deck deck = table.getDeckResource();
                    player.draw(deck);
                    table.setDeckResource(deck);
                    game.setTableOfDecks(table);

                } else if (tipo.equals("initial")) {
                    Deck deck = table.getDeckStart();
                    player.draw(deck);
                    table.setDeckStart(deck);
                    game.setTableOfDecks(table);
                } else {
                    //gestire l'eccezzione in cui non viene inserita una string corretta(se vogliamo)

                }
                game.setPlayers(players);
            }
        }
    }

    public void draw(Card cardSelected, String nick) {
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
    }

    //cambia lo stato dei giocatori(connesso o non connesso)
    public void changePlayerStatus(String nick, Boolean value) {
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        for (Player player : players.keySet()) {
            if (player.getNick().equals(nick)) {
                Boolean status = players.remove(player);
                players.put(player, value);
            }
        }
        game.setPlayers(players);
    }

    //cambia il modo di piazzare la carta
    public Card cardIsFrontChanger(Card card, Boolean value) {
        card.changeIsFront(value);
        return card;
    }

    //inizializza il tavolo da gioco, compreso i giocatori nei turni
    public void initializeTable() {
        TableOfDecks table = game.getTableOfDecks();
        table.initializeTable();
        ArrayList<Player> keysList = new ArrayList<>(game.getPlayers().keySet());
        Turn turn = new Turn(keysList);
        game.setTurn(turn);
        game.setTableOfDecks(table);
    }

    public boolean notify20PointReached() {
        return game.getPointTable().notify20PointReached();
    }

    //calcola i punti dei giocatori attraverso le carte obbiettivo, aggiorna la point table e ritorna  il giocatore con il punteggio più alto
    public String calculateWinner() {
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        PointTable pointTable = game.getPointTable();
        String winner = "nessuno";
        int maxpoint = 0;
        for (Player player : players.keySet()) {
            int point = 0;
            point = player.getGoal().getGoalPoints((HashMap) player.getGameStation().getPlayedCards(), (HashMap) player.getGameStation().calculateAvailableResources());
            ArrayList<GoalCard> goals = (ArrayList) game.getTableOfDecks().getGoals();
            for (GoalCard goalCard : goals) {
                point = point + goalCard.getGoalPoints((HashMap) player.getGameStation().getPlayedCards(), (HashMap) player.getGameStation().calculateAvailableResources());
            }
            point = point + pointTable.getPoint(player);
            pointTable.updatePoint(player, point);
            if (point > maxpoint) {
                maxpoint = point;
                winner = player.getNick();
            }
        }
        game.setPointTable(pointTable);
        return winner;
    }

    //ritorna il nick del giocatore attuale
    public String getCurrentPlayer() {
        return game.getTurn().getCurrentPlayerNick();
    }

    //passa al giocatore successivo cambiando il giocatore corrente
    public void goOn() {
        Turn turn = game.getTurn();
        turn.goOn();
        game.setTurn(turn);
    }

    //forse inutile vedere come funziona il patter observable
//ritorna le carte visibili sul TableOfDecks(quelle che il giocatore può pescare)
    public ArrayList<Card> cardsOnTableOfDecks() {
        return game.getTableOfDecks().getCards();
    }

    //calcola i punti della carta oro e li ritorna
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

    }

    //forse inutile vedere come funziona il patter observable
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
    public void chooseGoal(ArrayList<GoalCard> goals, int num, String nick) {//bisogna gestire l'eccezione nel caso num non vada bene(se vogliamo)
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        for (Player player : players.keySet()) {
            if (player.getNick().equals(nick)) {
                player.chooseGoal(goals, num);
            }
            game.setPlayers(players);
        }
    }

    public void initializeHandPlayer(String nick) {
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        TableOfDecks table = game.getTableOfDecks();
        PointTable pointTable = game.getPointTable();
        for (Player player : players.keySet()) {
            if (player.getNick().equals(nick)) {
                Deck deck = table.getDeckGold();
                player.draw(deck);
                table.setDeckGold(deck);
                table.setDeckGold(deck);
                deck = table.getDeckResource();
                player.draw(deck);
                player.draw(deck);
                table.setDeckResource(deck);
                game.setPlayers(players);
                game.setTableOfDecks(table);
            }

        }


    }

    //ritorna la lista di goalcard che l'utente può scegliere(dubbio, bisogna creare qualcosa del genere nel model e poi usare il
    //pattern observer?)
    public ArrayList<GoalCard> getPossibleGoals() {
        ArrayList<GoalCard> goals = new ArrayList<>();
        goals.add((GoalCard) game.getTableOfDecks().getGoals().getFirst());
        goals.add((GoalCard) game.getTableOfDecks().getGoals().getFirst());
        return goals;

    }
}


