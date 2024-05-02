package it.polimi.ingsw.view.TUI;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.view.UI;

import java.util.HashMap;

import static it.polimi.ingsw.view.PrintlnThread.Println;

public class Cli implements UI {

    @Override
    public void show_startingMenu() {
        Println("""
                STARTING MENU
                
                A- JOIN RANDOM GAME
                B- RECONNECT TO AN EXISTING GAME
                
                """);
    }

    @Override
    public void show_joinRandomGame() {
        Println("""
                JOINING A GAME...
                """);
    }

    @Override
    public void show_noAvailableGames() {
        Println("""
                NO GAMES AVAILABLE
                
                A-CREATE A NEW GAME
                B-EXIT
                """);
    }

    @Override
    public void show_createNewGame() {
        Println("""
                CREATING A NEW GAME...
                """);
    }

    @Override
    public void show_reconnectGame() {
        Println("""
                RECONNECTING TO THE GAME...
                """);
    }

    @Override
    public void show_RequestPlayerNickName() {
        Println("""
                ENTER A NICKNAME:
                """);
    }

    @Override
    public void show_RequestGameId() {
        Println("""
                ENTER A GAME ID:
                """);
    }

    @Override
    public void show_RequestNumberOfPlayers() {
        Println("""
                ENTER A NUMBER OF PLAYERS:
                """);
    }

    @Override
    public void show_playerJoined(String playerNickName) {
        Println( playerNickName + " JOINED");
    }

    @Override
    public void show_playerLeft(String playerNickName) {
        Println(playerNickName + " LEFT");
    }

    @Override
    public void show_gameStarting(String id) {
        Println("GAME " + id + " STARTING");
    }

    @Override
    public void show_isYourTurn(GameView immutableModel) {
            Println(immutableModel.getCurrentPlayer().getNick() + "IS YOUR TURN");
    }

    @Override
    public void show_playerHand(GameView immutableModel) {
        for(PlayableCard c : immutableModel.getCurrentPlayer().showCard()){
            show_playableCard(c);
        }
    }

    @Override
    public void show_gameStation(GameView immutableModel) {
    }

    @Override
    public void show_invalidPlay() {
    }

    @Override
    public void show_goalCard(GameView immutableModel) {
    }

    @Override
    public void show_playableCard(PlayableCard card) {
        String FHL = safeString(card.getFront().get(Angle.HIGHLEFT));
        String FHR = safeString(card.getFront().get(Angle.HIGHRIGHT));
        String FDL = safeString(card.getFront().get(Angle.DOWNLEFT));
        String FDR = safeString(card.getFront().get(Angle.DOWNRIGHT));
        String BHL = safeString(card.getFront().get(Angle.HIGHLEFT));
        String BHR = safeString(card.getFront().get(Angle.HIGHRIGHT));
        String BDL = safeString(card.getFront().get(Angle.DOWNLEFT));
        String BDR = safeString(card.getFront().get(Angle.DOWNRIGHT));

        if(card instanceof GoldCard){
            Println("""
                    MAIN RESOURCE: """ + card.getType().toString() + """
                    POINTS: """ + ((GoldCard) card).getNumberOfPoints() + """
                    REQUIREMENTS:
                    
                    FRONT                      BACK
                    ┌──────────────────────┐   ┌──────────────────────┐
                    │""" + FHL + " ".repeat(22-FHL.length()-FHR.length()) + FHR + "│"+ "   " + "│" + BHL + " ".repeat(22-BHL.length()-BHR.length()) + BHR + "│\n"+"""
                    │                      │   │                      │
                    │""" + FDL + " ".repeat(22-FDL.length()-FDR.length()) + FDR + "│"+ "   " + "│" + BDL + " ".repeat(22-BDL.length()-BDR.length()) + BDR + "│\n"+"""
                    └──────────────────────┘   └──────────────────────┘
                    """);

        } else if (card instanceof ResourceCard) {
            Println("""
                    MAIN RESOURCE: """ + card.getType().toString() + """
                    
                    FRONT                      BACK
                    ┌──────────────────────┐   ┌──────────────────────┐
                    │""" + FHL + " ".repeat(22-FHL.length()-FHR.length()) + FHR + "│"+ "   " + "│" + BHL + " ".repeat(22-BHL.length()-BHR.length()) + BHR + "│\n"+"""
                    │                      │   │                      │
                    │""" + FDL + " ".repeat(22-FDL.length()-FDR.length()) + FDR + "│"+ "   " + "│" + BDL + " ".repeat(22-BDL.length()-BDR.length()) + BDR + "│\n"+"""
                    └──────────────────────┘   └──────────────────────┘
                    """);
        }
    }

    @Override
    public void show_tableOfDecks(GameView immutableModel) {
    }

    @Override
    public void show_lastTurn() {
    }

    @Override
    public void show_pointTable(GameView immutableModel) {
    }

    @Override
    public void show_gameOver() {
    }

    private static String safeString(Object o){
        if(o == null){
            return "";
        }else {
            return o.toString();
        }
    }
}
