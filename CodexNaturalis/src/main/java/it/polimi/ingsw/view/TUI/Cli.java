package it.polimi.ingsw.view.TUI;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.card.strategyPattern.*;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.model.gameDataManager.Player;
import it.polimi.ingsw.view.UI;

import java.util.*;

import static it.polimi.ingsw.view.PrintlnThread.Println;

public class Cli implements UI {
    private final Object lock = new Object();

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
        Println("""
                INVALID PLAY...
                """);
    }

    @Override
    public void show_goalCard(GoalCard card) {
        Println("""
                GOAL CARD
                
                CARD ID: """ + card.getCardId() + """
                POINTS: """ + card.getNumberOfPoints() + "EACH TIME THE REQUIREMENT IS SATISFIED" + """
                REQUIREMENT:
                """ + goalPoint(card) + """
                
                """);
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

        if (card instanceof GoldCard) { //manca esprimere metodo in points su come vengono guadagnati
            Println("""
                    GOLD CARD
                    
                    CARD ID: """ + card.getCardId() + """
                    PERMANENT RESOURCE: """ + ((GoldCard) card).getBackResource().toString() + """
                    POINTS: """ + goldPoint((GoldCard) card) + """
                    REQUIREMENTS:"""
                    + mapToString(((GoldCard) card).getNeededResources()) + """
                    
                    FRONT                      BACK
                    ┌──────────────────────┐   ┌──────────────────────┐
                    │""" + FHL + " ".repeat(22 - FHL.length() - FHR.length()) + FHR + "│" + "   " + "│" + BHL + " ".repeat(22 - BHL.length() - BHR.length()) + BHR + "│\n" + """
                    │                      │   │                      │
                    │""" + FDL + " ".repeat(22 - FDL.length() - FDR.length()) + FDR + "│" + "   " + "│" + BDL + " ".repeat(22 - BDL.length() - BDR.length()) + BDR + "│\n" + """
                    └──────────────────────┘   └──────────────────────┘
                    """);

        } else if (card instanceof ResourceCard) {
            Println("""
                    RESOURCE CARD
                    
                    CARD ID: """ + card.getCardId() + """
                    PERMANENT RESOURCE: """ + ((ResourceCard) card).getBackResource() + """
                    POINTS: """ + ((ResourceCard) card).getNumberOfPoints() + """
                    
                    FRONT                      BACK
                    ┌──────────────────────┐   ┌──────────────────────┐
                    │""" + FHL + " ".repeat(22 - FHL.length() - FHR.length()) + FHR + "│" + "   " + "│" + BHL + " ".repeat(22 - BHL.length() - BHR.length()) + BHR + "│\n" + """
                    │                      │   │                      │
                    │""" + FDL + " ".repeat(22 - FDL.length() - FDR.length()) + FDR + "│" + "   " + "│" + BDL + " ".repeat(22 - BDL.length() - BDR.length()) + BDR + "│\n" + """
                    └──────────────────────┘   └──────────────────────┘
                    """);
        } else if (card instanceof InitialCard) {
            Println("""
                    INITIAL CARD
                    
                    CARD ID: """ + card.getCardId() + """
                    BACK RESOURCES  : """ + listToString(((InitialCard) card).getBackResources()) + """
                    
                    FRONT                      BACK
                    ┌──────────────────────┐   ┌──────────────────────┐
                    │""" + FHL + " ".repeat(22 - FHL.length() - FHR.length()) + FHR + "│" + "   " + "│" + BHL + " ".repeat(22 - BHL.length() - BHR.length()) + BHR + "│\n" + """
                    │                      │   │                      │
                    │""" + FDL + " ".repeat(22 - FDL.length() - FDR.length()) + FDR + "│" + "   " + "│" + BDL + " ".repeat(22 - BDL.length() - BDR.length()) + BDR + "│\n" + """
                    └──────────────────────┘   └──────────────────────┘
                    """);
        }
    }

    @Override
    public void show_tableOfDecks(GameView immutableModel) {

        Println("PLAYABLE CARDS");
        for(Card card : immutableModel.getTableOfDecks().getCards()){
            show_playableCard((PlayableCard) card);
        }

        Println("GOAL CARDS");
        for(Card card : immutableModel.getTableOfDecks().getGoals()){
            show_goalCard((GoalCard) card);
        }
    }

    @Override
    public void show_lastTurn() {
        Println("""
                LAST TURN!!!
                """);
    }

    @Override
    public void show_pointTable(GameView immutableModel) {
        StringBuilder stringBuilder = new StringBuilder();
        HashMap<Color, Integer> rankColors = new HashMap<>(immutableModel.getPoints().getMap());
        Map<String, Integer> rankPlayers = new HashMap<>();
        for(Player p : immutableModel.getPlayers().keySet()){
            rankPlayers.put(p.getNick(), rankColors.get(p.getColor()));
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<>(rankPlayers.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        int rank=1;
        for(Map.Entry<String, Integer> entry : list){
            stringBuilder.append(rank + "- ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" POINTS\n");
            rank++;
        }

        Println("""
                RANKING
                
                """ + stringBuilder + """
                """);
    }

    @Override
    public void show_gameOver() {//solo scritta gameover o anche nome vincitore?
    }

    private static String safeString(Object o){
        if(o == null){
            return "";
        }else {
            return o.toString();
        }
    }

    private static String listToString(List<Item> list){
        StringBuilder stringBuilder = new StringBuilder();
        for(Item i : list){
            stringBuilder.append(i.toString());
        }
        return stringBuilder.toString();
    }

    private static String mapToString(Map<Item, Integer> map){
        StringBuilder stringBuilder = new StringBuilder();
        for(Item i : map.keySet()){
            stringBuilder.append(i.toString()).append(": ").append(map.get(i)).append('\n');
        }
        return stringBuilder.toString();
    }

    private static String goldPoint(GoldCard card){
        StringBuilder stringBuilder = new StringBuilder();
        if (card.getGoldType().equals(GoldType.CLASSIC)){
            stringBuilder.append(card.getNumberOfPoints());
        } else if (card.getGoldType().equals(GoldType.ITEM)){
            stringBuilder.append(card.getNumberOfPoints()).append(" FOR EACH ").append(card.getBox().toString());
        } else if (card.getGoldType().equals(GoldType.ANGLE)){
            stringBuilder.append(card.getNumberOfPoints()).append(" FOR EACH ANGLE COVERED");
        }
        return stringBuilder.toString();
    }

    private static String goalPoint(GoalCard card){
        if (card.getGoalType() instanceof ItemCheck){
            return mapToString(card.getListOfObjects());
        } else {
            return drawGoalRequirement(card.getGoalType());
        }
    }

    private static String drawGoalRequirement(CheckInterface checkInterface){
        if(checkInterface instanceof DiagonalAnimalCheck){
            return """
                                               ┌────────────┐
                                   ┌───────────│   ANIMAL   │
                       ┌───────────│   ANIMAL  └────────────┘
                       │   ANIMAL  └────────────┘
                       └────────────┘
                       """;
        } else if (checkInterface instanceof DiagonalMushroomCheck){
            return """
                                               ┌────────────┐
                                   ┌───────────│  MUSHROOM  │
                       ┌───────────│  MUSHROOM └────────────┘
                       │  MUSHROOM └────────────┘
                       └────────────┘
                       """;
        }else if (checkInterface instanceof DiagonalInsectCheck){
            return """
                       ┌────────────┐
                       │   INSECT  ┌────────────┐
                       └───────────│   INSECT  ┌────────────┐
                                   └───────────│   INSECT   │
                                               └────────────┘
                       """;
        }else if (checkInterface instanceof DiagonalVegetableCheck){
            return """
                       ┌─────────────┐
                       │  VEGETABLE  ┌─────────────┐
                       └─────────────│  VEGETABLE ┌─────────────┐
                                     └────────────│  VEGETABLE  │
                                                  └─────────────┘
                       """;
        }else if (checkInterface instanceof LMushroomVegetableCheck){
            return """
                       ┌────────────┐
                       │  MUSHROOM  │
                       └────────────┘
                       ┌────────────┐
                       │  MUSHROOM ┌─────────────┐
                       └───────────│  VEGETABLE  │
                                   └─────────────┘
                       """;
        }
        else if (checkInterface instanceof ReverseLVegetableInsectCheck){
            return """
                                   ┌─────────────┐
                                   │  VEGETABLE  │
                                   └─────────────┘
                                   ┌─────────────┐
                       ┌────────────┐ VEGETABLE  │
                       │   INSECT   │────────────┘
                       └────────────┘
                       """;
        }
        else if (checkInterface instanceof UpsideDownLAnimalMushroomCheck){
            return """
                                   ┌────────────┐
                       ┌───────────│  MUSHROOM  │
                       │   ANIMAL  └────────────┘
                       └────────────┘
                       ┌────────────┐
                       │   ANIMAL   │
                       └────────────┘
                       """;
        }
        else if (checkInterface instanceof UpsideDownReverseLInsectAnimalCheck){
            return """
                       ┌────────────┐
                       │   ANIMAL   │───────────┐
                       └────────────┘  INSECT   │
                                   └────────────┘
                                   ┌────────────┐
                                   │   INSECT   │
                                   └────────────┘
                       """;
        }
        return "error";
    }
}
