package it.polimi.ingsw.view.TUI;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.card.strategyPattern.*;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.model.gameDataManager.Player;
import it.polimi.ingsw.view.UI;

import java.awt.*;
import java.util.*;
import java.util.List;

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
    public void show_message(String message) {
        Println(message);
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
    public void show_invalidIdGame() {
        Println("""
                INVALID GAME ID!!!
                """);
    }

    @Override
    public void show_currentPlayersStatus(GameView gameView) {
        StringBuilder stringBuilder = new StringBuilder();
        for(Player p : gameView.getPlayers().keySet()){
            stringBuilder.append(p.getNick()).append(" color: " + p.getColor()).append(", status: ").append(gameView.getPlayers().get(p)).append('\n');
        }
        Println(stringBuilder.toString());
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
    public void show_gameStation(GameStation gameStation){
        Map<Point, PlayableCard> playedCards = gameStation.getPlayedCards();

        int spaces;

        int maxRow = (findMaxValue(playedCards.keySet(), "x") +1) *2 +1;
        int maxColumn = (findMaxValue(playedCards.keySet(), "y") +1) *2 +1;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GAME STATION\n");
        stringBuilder.append("_".repeat(maxColumn*6 +1)).append("\n");
        for(int row = 1; row <= maxRow; row++){
            stringBuilder.append("|");
            for(int column = 1; column <= maxColumn; column++){
                String value = determineValue(gameStation, new Point(column - maxColumn + 2, maxRow - row - 2));
                spaces = Math.round(2 - value.length()/2);
                stringBuilder.append(" ".repeat(spaces) + value + " ".repeat(spaces));
                if((2 * spaces + value.length()) != 5) stringBuilder.append(" ");
                stringBuilder.append("|");
            }
            stringBuilder.append("\n");
            stringBuilder.append("_".repeat(maxColumn*6 +1)).append("\n");
        }

        stringBuilder.append("\n");

        for(PlayableCard card: playedCards.values()){
            stringBuilder.append(cardDraw(card));
        }

        Println(stringBuilder.toString());
    }

    private static int findMaxValue(Set<Point> set, String coord) {
        if(coord.equals("x")) {
            Point maxPoint = set.stream()
                    .reduce((p1, p2) -> Math.abs(p1.getX()) > Math.abs(p2.getX()) ? p1 : p2)
                    .orElse(new Point(0,0));
            return Math.abs(maxPoint.x);
        }else if(coord.equals("y")) {
            Point maxPoint = set.stream()
                    .reduce((p1, p2) -> Math.abs(p1.getY()) > Math.abs(p2.getY()) ? p1 : p2)
                    .orElse(new Point(0, 0));
            return Math.abs(maxPoint.y);
        }
        return 0;
    }

    private static String determineValue(GameStation gameStation, Point point){
        if(gameStation.getPlayedCards().containsKey(point)){
            return String.valueOf(gameStation.getPlayedCards().get(point).getCardId());
        }else if(gameStation.getFreeCords().contains(point)){
            return point.x + "," + point.y;
        }else {
            return "X";
        }
    }

    private static String cardDraw(PlayableCard card){
        String HL;
        String HR;
        String DL;
        String DR;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GAME ID: ").append(card.getCardId()).append("\n");

        if(card.isFront()){
            HL = safeString(card.getFront().get(Angle.HIGHLEFT));
            HR = safeString(card.getFront().get(Angle.HIGHRIGHT));
            DL = safeString(card.getFront().get(Angle.DOWNLEFT));
            DR = safeString(card.getFront().get(Angle.DOWNRIGHT));
        }else{
            stringBuilder.append(listToString(card.getAListOfBackResource()));
            HL = safeString(card.getBack().get(Angle.HIGHLEFT));
            HR = safeString(card.getBack().get(Angle.HIGHRIGHT));
            DL = safeString(card.getBack().get(Angle.DOWNLEFT));
            DR = safeString(card.getBack().get(Angle.DOWNRIGHT));
        }
        stringBuilder.append("""
                ┌──────────────────────┐
                │""" + HL + " ".repeat(22 - HL.length() - HR.length()) + HR + "│\n" + """
                │                      │
                │""" + DL + " ".repeat(22 - DL.length() - DR.length()) + DR + "│\n" + """
                └──────────────────────┘
                """);
        return stringBuilder.toString();
    }

    @Override
    public void show_invalidPlay() {
        Println("""
                INVALID PLAY...
                """);
    }

    @Override
    public void show_notEnoughResources() {
        Println("""
                NOT ENOUGH RESOURCES!!!
                """);
    }

    @Override
    public void show_goalCard(GoalCard card) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GOAL CARD\n\n").append("GAME ID: ").append(card.getCardId()).append("\n").append("POINTS: ").append(card.getNumberOfPoints());
        stringBuilder.append(" EACH TIME THE REQUIREMENT IS SATISFIED").append("\n").append("REQUIREMENT:\n").append(goalPoint(card));
        Println(stringBuilder.toString());
        /*
        Println("""
                GOAL CARD
                
                CARD ID:""" + card.getCardId() + "\n" + """
                POINTS:""" + card.getNumberOfPoints() + " EACH TIME THE REQUIREMENT IS SATISFIED" + "\n" + """
                REQUIREMENT:
                """ + "\n" + goalPoint(card) + """
                
                """);
                */

    }

    @Override
    public void show_playableCard(PlayableCard card) {
        String FHL = safeString(card.getFront().get(Angle.HIGHLEFT));
        String FHR = safeString(card.getFront().get(Angle.HIGHRIGHT));
        String FDL = safeString(card.getFront().get(Angle.DOWNLEFT));
        String FDR = safeString(card.getFront().get(Angle.DOWNRIGHT));
        String BHL = safeString(card.getBack().get(Angle.HIGHLEFT));
        String BHR = safeString(card.getBack().get(Angle.HIGHRIGHT));
        String BDL = safeString(card.getBack().get(Angle.DOWNLEFT));
        String BDR = safeString(card.getBack().get(Angle.DOWNRIGHT));

        if (card instanceof GoldCard) { //manca esprimere metodo in points su come vengono guadagnati
            Println("""
                    GOLD CARD
                    
                    CARD ID: """ + card.getCardId() + "\n" + """
                    PERMANENT RESOURCE: """ + ((GoldCard) card).getBackResource().toString() + "\n" + """
                    POINTS: """ + goldPoint((GoldCard) card) + "\n" + """
                    REQUIREMENTS: """ + "\n" + mapToString(((GoldCard) card).getNeededResources()) + """
                    
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
                    
                    CARD ID:""" + card.getCardId() + "\n" + """
                    PERMANENT RESOURCE:""" + ((ResourceCard) card).getBackResource() + "\n" + """
                    POINTS:""" + ((ResourceCard) card).getNumberOfPoints() + "\n" + """
                    
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
                    
                    CARD ID:""" + card.getCardId() + "\n" + """
                    BACK RESOURCES:""" + listToString(((InitialCard) card).getBackResources()) + "\n" + """
                    
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
