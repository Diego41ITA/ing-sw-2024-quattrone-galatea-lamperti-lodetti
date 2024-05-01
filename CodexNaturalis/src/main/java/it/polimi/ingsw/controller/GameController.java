package it.polimi.ingsw.controller;


import it.polimi.ingsw.controller.FSM.*;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.Deck;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.exceptions.illegalOperationException;
import it.polimi.ingsw.model.gameDataManager.*;
import it.polimi.ingsw.model.gameDataManager.Color;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class GameController implements Serializable{
    /** the game to control
     */
    private Game game;

    private final Random random = new Random();

    public GameController(){
        game = new Game();
    }


    //all'interno di questo metodo vengono aggiunti i player all'interno della partita e anche il numero massimo di giocatori
    public void setMaxNumberPlayers(String name, int max){
        game.setStatus(Status.WAITING);
        Player player = new Player();
        player.setNickname(name);
        game.setSinglePlayer(player);
        game.setMaxNumberPlayer(max);
    }
    public void setColor(String color, String name) {
        HashMap<Player, Boolean> players;
        players = (HashMap) game.getPlayers();
        for (Player player : players.keySet()) {
            if (player.getNick().equals(name)) {
                player.setColor(Color.valueOf(color));
            }
            game.setPlayers(players);
        }
    }

    public void playCard(PlayableCard card, Point cord, String nick) throws illegalOperationException {
        HashMap<Player, Boolean> players;
        players = (HashMap<Player, Boolean>) game.getPlayers();
        for (Player player : players.keySet()) {
            if (player.getNick().equals(nick)) {
                player.playCard(card, cord);//l'rmi dovrà gestire l'eccezione
            }

        }
        game.setPlayers(players);
    }
//pescaggio da deck:
//immagino che il giocatore inserisca una stringa da riga di comando in cui dice il tipo di deck da cui vuole pescare
    public void draw(String tipo,String nick) {
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
                    Deck deck = table.getDeckGoal();
                    player.draw(deck);
                    table.setDeckResource(deck);
                    game.setTableOfDecks(table);

                } else if (tipo.equals("initial")) {
                    Deck deck = table.getDeckGoal();
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
}