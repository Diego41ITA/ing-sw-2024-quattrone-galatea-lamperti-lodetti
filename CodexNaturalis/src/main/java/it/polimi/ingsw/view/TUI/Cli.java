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
                 
                 ▄█▀▀▀▄█    ▄                     ▄    ██                                                          \s
                 ██▄▄  ▀  ▄██▄   ▄▄▄▄   ▄▄▄ ▄▄  ▄██▄  ▄▄▄  ▄▄ ▄▄▄     ▄▄▄ ▄    ▄▄ ▄▄ ▄▄     ▄▄▄▄  ▄▄ ▄▄▄   ▄▄▄ ▄▄▄ \s
                  ▀▀███▄   ██   ▀▀ ▄██   ██▀ ▀▀  ██    ██   ██  ██   ██ ██      ██ ██ ██  ▄█▄▄▄██  ██  ██   ██  ██ \s
                ▄     ▀██  ██   ▄█▀ ██   ██      ██    ██   ██  ██    █▀▀       ██ ██ ██  ██       ██  ██   ██  ██ \s
                █▀▄▄▄▄█▀   ▀█▄▀ ▀█▄▄▀█▀ ▄██▄     ▀█▄▀ ▄██▄ ▄██▄ ██▄  ▀████▄    ▄██ ██ ██▄  ▀█▄▄▄▀ ▄██▄ ██▄  ▀█▄▄▀█▄\s
                                                                    ▄█▄▄▄▄▀                                        \s
                                                                                                                   \s
                
                                                                                                                                              
                CHOOSE AN OPTION:
                
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
                   █ ▄▀▄ █ █▄ █ █ █▄ █ ▄▀    ▄▀▄   ▄▀  ▄▀▄ █▄ ▄█ ██▀     \s
                 ▀▄█ ▀▄▀ █ █ ▀█ █ █ ▀█ ▀▄█   █▀█   ▀▄█ █▀█ █ ▀ █ █▄▄ ▄ ▄ ▄
                                
                """);
    }

    @Override
    public void show_requestPlayerColor(GameView gameView) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nCHOOSE A COLOR:\n");

        for (Color c : freeColors(gameView)) {
            stringBuilder.append(c + ", ");
        }

        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append("\n");

        Println(stringBuilder.toString());
    }

    private static ArrayList<Color> freeColors(GameView gameView){
        ArrayList<Color> freeColors = new ArrayList<>(Arrays.asList(Color.YELLOW, Color.RED, Color.BLUE, Color.GREEN));
        if(gameView != null) {
            for (Player p : gameView.getPlayers().keySet()) {
                freeColors.remove(p.getColor());
            }
        }
        return freeColors;
    }

    @Override
    public void show_noAvailableGames() {
        Println("""
                NO GAMES AVAILABLE
                
                CHOOSE AN OPTION:
                
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
    public void show_NickAlreadyUsed(GameView gameView) {
        StringBuilder str = new StringBuilder();
        str.append("NICKNAME ALREADY USED!\n").append("\n").append("ALREADY USED NICKNAMES:\n");
        for(Player p : gameView.getPlayers().keySet()) {
            str.append(p.getNick()).append('\n');
        }
        Println(str.toString());
    }

    @Override
    public void show_invalidNickToReconnect(String id){
        StringBuilder str = new StringBuilder();
        str.append("IN GAME WITH ID: ").append(id).append(" THERE WASN'T A PLAYER WITH THIS NICK\n");
        str.append("CHECK AND TRY AGAIN...");
        Println(str.toString());
    }

    @Override
    public void show_currentPlayersStatus(GameView gameView) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for(Player p : gameView.getPlayers().keySet()){
            stringBuilder.append(p.getNick()).append(", STATUS: ").append(gameView.getPlayers().get(p)).append('\n');
        }
        Println(stringBuilder.toString());
    }

    @Override
    public void show_playerColors(GameView gameView) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for(Player p : gameView.getPlayers().keySet()){
            stringBuilder.append(p.getNick()).append(", COLOR: ").append(p.getColor()).append('\n');
        }
        Println(stringBuilder.toString());
    }

    @Override
    public void show_GameStatus(GameView gameView){
        Println(gameView.getStatus().toString());
    }

    @Override
    public void show_RequestPlayerNickName() {
        Println("\nENTER A NICKNAME:\n");
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
                \nENTER A NUMBER OF PLAYERS:
                """);
    }

    @Override
    public void show_playerJoined(String id) {
        Println("""
                   █ ▄▀▄ █ █▄ █ ██▀ █▀▄   ▄▀  ▄▀▄ █▄ ▄█ ██▀
                 ▀▄█ ▀▄▀ █ █ ▀█ █▄▄ █▄▀   ▀▄█ █▀█ █ ▀ █ █▄▄
                                
                """);
        Println("THE ID OF THE GAME IS: " + id + ". DON'T FORGET IT! IT'S NEEDED IF YOU WANT TO RECONNECT");
    }

    @Override
    public void show_playerLeft(String playerNickName) {
        Println(playerNickName + " LEFT");
    }

    @Override
    public void show_initialCard(InitialCard card){
                StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("""
                
                
                ▀██▀                ▄    █                    ▄                     ▄   ▄█▄\s
                 ██         ▄▄▄▄  ▄██▄      ▄▄▄▄      ▄▄▄▄  ▄██▄   ▄▄▄▄   ▄▄▄ ▄▄  ▄██▄  ███\s
                 ██       ▄█▄▄▄██  ██      ██▄ ▀     ██▄ ▀   ██   ▀▀ ▄██   ██▀ ▀▀  ██   ▀█▀\s
                 ██       ██       ██      ▄ ▀█▄▄    ▄ ▀█▄▄  ██   ▄█▀ ██   ██      ██    █ \s
                ▄██▄▄▄▄▄█  ▀█▄▄▄▀  ▀█▄▀    █▀▄▄█▀    █▀▄▄█▀  ▀█▄▀ ▀█▄▄▀█▀ ▄██▄     ▀█▄▀  ▄ \s
                                                                                        ▀█▀\s
                                                                                           \s
                                
                """).append("\nTHIS IS YOUR INITIAL CARD\n").append(show_playableCard(card)).append("""
                DO YOU WANNA PLAY IT FRONT OR BACK:
                
                ENTER TRUE TO PLAY IF FRONT, FALSE TO PLAY IF BACK
                """);
        show_message(stringBuilder.toString());

    }

    @Override
    public void show_gameStarting(String id) {
        Println("\nGAME: " + id + " IS STARTING");
    }

    @Override
    public void show_isYourTurn(GameView immutableModel) {
            Println(immutableModel.getCurrentPlayer().getNick() + " IS YOUR TURN");
    }

    @Override
    public void show_playerHand(GameView immutableModel) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nTHESE ARE YOUR CARDS:\n");
        for(PlayableCard c : immutableModel.getCurrentPlayer().showCard()){
            stringBuilder.append(show_playableCard(c));
        }
        Println(stringBuilder.toString());
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

        if(maxRow == 3 && maxColumn==3){
            for(int row = 1; row <= maxRow; row++){
                stringBuilder.append("|");
                for(int column = 1; column <= maxColumn; column++){
                    String value = determineValue(gameStation, new Point(column - maxColumn + 1, maxRow - row - 1));
                    spaces = Math.round(2 - value.length()/2);
                    stringBuilder.append(" ".repeat(spaces) + value + " ".repeat(spaces));
                    if((2 * spaces + value.length()) != 5) stringBuilder.append(" ");
                    stringBuilder.append("|");
                }
                stringBuilder.append("\n");
                stringBuilder.append("_".repeat(maxColumn*6 +1)).append("\n");
            }
        }else{
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
        }

        stringBuilder.append("\n");

        for(PlayableCard card: playedCards.values()){
            stringBuilder.append(cardDraw(card));
        }
        stringBuilder.append("\n");

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
                INVALID PLAY...CHOOSE A VALID COORDINATE!!!
                """);
    }

    @Override
    public void show_notEnoughResources() {
        Println("""
                NOT ENOUGH RESOURCES!!!
                """);
    }

    @Override
    public String show_goalCard(GoalCard card) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nGOAL CARD\n\n").append("CARD ID: ").append(card.getCardId()).append("\n").append("POINTS: ").append(card.getNumberOfPoints());
        stringBuilder.append(" EACH TIME THE REQUIREMENT IS SATISFIED").append("\n").append("REQUIREMENT:\n").append(goalPoint(card));
        return (stringBuilder.toString());
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
    public String show_playableCard(PlayableCard card) {
        StringBuilder stringBuilder = new StringBuilder();
        String FHL = safeString(card.getFront().get(Angle.HIGHLEFT));
        String FHR = safeString(card.getFront().get(Angle.HIGHRIGHT));
        String FDL = safeString(card.getFront().get(Angle.DOWNLEFT));
        String FDR = safeString(card.getFront().get(Angle.DOWNRIGHT));
        String BHL = safeString(card.getBack().get(Angle.HIGHLEFT));
        String BHR = safeString(card.getBack().get(Angle.HIGHRIGHT));
        String BDL = safeString(card.getBack().get(Angle.DOWNLEFT));
        String BDR = safeString(card.getBack().get(Angle.DOWNRIGHT));

        if (card instanceof GoldCard) { //manca esprimere metodo in points su come vengono guadagnati
            stringBuilder.append("""
                    \nGOLD CARD
                    
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
            stringBuilder.append("""
                    \nRESOURCE CARD
                    
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
            stringBuilder.append("""
                    \nINITIAL CARD
                    
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
        return stringBuilder.toString();
    }

    @Override
    public void show_tableOfDecks(GameView immutableModel) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nTABLE OF DECKS\n").append("\nPLAYABLE CARDS\n");
        for(Card card : immutableModel.getTableOfDecks().getCards()){
            stringBuilder.append(show_playableCard((PlayableCard) card));
        }

        stringBuilder.append("\nGOAL CARDS\n");
        for(Card card : immutableModel.getTableOfDecks().getGoals()){
            stringBuilder.append(show_goalCard((GoalCard) card));
        }
        Println(stringBuilder.toString());
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
        Println("the game is over now we are calculating the winner...");
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
            stringBuilder.append(i.toString()).append(", ");
        }
        stringBuilder.setLength(stringBuilder.length() - 2);
        return stringBuilder.toString();
    }

    private static String mapToString(Map<Item, Integer> map){
        StringBuilder stringBuilder = new StringBuilder();
        if(map != null) {
            for (Item i : map.keySet()) {
                stringBuilder.append(i.toString()).append(": ").append(map.get(i)).append('\n');
            }
            return stringBuilder.toString();
        }else {
            stringBuilder.append("\n");
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
