package it.polimi.ingsw.view.TUI;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.card.strategyPattern.*;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.model.gameDataManager.Player;
import it.polimi.ingsw.view.UI;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Cli implements UI {

    private static String mapToEmoji(HashMap<Item, Integer> resources) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Item, Integer> entry : resources.entrySet()) {
            Item resourceType = entry.getKey();
            int count = entry.getValue();
            String emoji = getResourceEmoji(resourceType);
            stringBuilder.append(emoji).append(" x ").append(count).append(", ");
        }
        // Remove the trailing comma and space
        if (!stringBuilder.isEmpty()) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }
        return stringBuilder.toString();
    }

    private static String listToEmoji(List<Item> resources) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Item resourceType : resources) {
            String emoji = getResourceEmoji(resourceType);
            stringBuilder.append(emoji).append(", ");
        }
        // Remove the trailing comma and space
        if (!stringBuilder.isEmpty()) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }
        return stringBuilder.toString();
    }

    public static String getAnsiCode(TypeOfCard type) {
        switch (type) {
            case ANIMAL:
                return "\u001B[36m"; // Light Blue
            case VEGETABLE:
                return "\u001B[32m"; // Green
            case MUSHROOM:
                return "\u001B[31m"; // Red
            case INSECT:
                return "\u001B[35m"; // Purple
            case STARTING:
                return "\u001B[33m"; // Yellow
            default:
                return "\u001B[0m"; // Default to reset color
        }
    }

    private static String getResourceEmoji(Item resourceType) {
        switch (resourceType) {
            case VEGETABLE:
                return "\uD83C\uDF40";
            case ANIMAL:
                return "\uD83E\uDD8A";
            case INSECT:
                return "\uD83E\uDD8B";
            case MUSHROOM:
                return "\uD83C\uDF44";
            case HIDDEN:
                return "\u2753";
            case EMPTY:
                return "\u274C";
            case POTION:
                return "\uD83D\uDCA7";
            case FEATHER:
                return "\uD83E\uDEB6";
            case PARCHMENT:
                return "\uD83D\uDCC4";
            case null:
                return "\uD83D\uDEAB";
        }
    }

    @Override
    public void show_startingMenu() {
        System.out.println("""
                 
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
        System.out.println(message);
    }

    @Override
    public void show_joinRandomGame() {
        System.out.println("""
                   
                   █ ▄▀▄ █ █▄ █ █ █▄ █ ▄▀    ▄▀▄   ▄▀  ▄▀▄ █▄ ▄█ ██▀
                 ▀▄█ ▀▄▀ █ █ ▀█ █ █ ▀█ ▀▄█   █▀█   ▀▄█ █▀█ █ ▀ █ █▄▄
                                                                
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

        System.out.println(stringBuilder.toString());
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
        System.out.println("""
                NO GAMES AVAILABLE
                
                CHOOSE AN OPTION:
                
                A-CREATE A NEW GAME
                B-EXIT
                """);
    }

    @Override
    public void show_createNewGame() {
        System.out.println("""
                CREATING A NEW GAME...
                """);
    }

    @Override
    public void show_reconnectGame() {
        System.out.println("""
                RECONNECTING TO THE GAME...
                """);
    }

    @Override
    public void show_invalidIdGame() {
        System.out.println("""
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
        System.out.println(str.toString());
    }

    @Override
    public void show_invalidNickToReconnect(String id){
        StringBuilder str = new StringBuilder();
        str.append("IN GAME WITH ID: ").append(id).append(" THERE WASN'T A PLAYER WITH THIS NICK\n");
        str.append("CHECK AND TRY AGAIN...");
        System.out.println(str.toString());
    }

    @Override
    public void show_currentPlayersStatus(GameView gameView) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for(Player p : gameView.getPlayers().keySet()){
            stringBuilder.append(p.getNick()).append(", STATUS: ").append(gameView.getPlayers().get(p)).append('\n');
        }
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void show_playerColors(GameView gameView) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n").append("Connected player: \n");
        for(Player p : gameView.getPlayers().keySet()){
            stringBuilder.append(p.getNick()).append(", COLOR: ").append(p.getColor()).append('\n');
        }
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void show_GameStatus(GameView gameView){
        System.out.println(gameView.getStatus().toString());
    }

    @Override
    public void show_RequestPlayerNickName() {
        System.out.println("\nENTER A NICKNAME:\n");
    }

    @Override
    public void show_RequestGameId() {
        System.out.println("""
                ENTER A GAME ID:
                """);
    }

    @Override
    public void show_RequestNumberOfPlayers() {
        System.out.println("""
                \nENTER MAX NUMBER OF PLAYERS. IT NEEDS TO BE >=2 AND <=4
                """);
    }

    @Override
    public void show_playerJoined(String id) {
        System.out.println("""
                   
                   █ ▄▀▄ █ █▄ █ ██▀ █▀▄   ▀█▀ █▄█ ██▀   ▄▀  ▄▀▄ █▄ ▄█ ██▀
                 ▀▄█ ▀▄▀ █ █ ▀█ █▄▄ █▄▀    █  █ █ █▄▄   ▀▄█ █▀█ █ ▀ █ █▄▄
                                                                
                """);
        System.out.println("THE ID OF THE GAME IS: " + id + ". DON'T FORGET IT! IT'S NEEDED IF YOU WANT TO RECONNECT");
    }

    @Override
    public void show_playerLeft(String playerNickName) {
        System.out.println(playerNickName + " LEFT");
    }

    @Override
    public void show_initialCard(InitialCard card){
                StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(""" 
                 
                 
                         
                ▀██▀                ▄    █                    ▄                     ▄               ▀██                     ██                     ▄█▄\s
                 ██         ▄▄▄▄  ▄██▄      ▄▄▄▄      ▄▄▄▄  ▄██▄   ▄▄▄▄   ▄▄▄ ▄▄  ▄██▄     ▄▄▄ ▄▄▄   ██   ▄▄▄▄    ▄▄▄▄ ▄▄▄ ▄▄▄  ▄▄ ▄▄▄     ▄▄▄ ▄   ███\s
                 ██       ▄█▄▄▄██  ██      ██▄ ▀     ██▄ ▀   ██   ▀▀ ▄██   ██▀ ▀▀  ██       ██▀  ██  ██  ▀▀ ▄██    ▀█▄  █   ██   ██  ██   ██ ██    ▀█▀\s
                 ██       ██       ██      ▄ ▀█▄▄    ▄ ▀█▄▄  ██   ▄█▀ ██   ██      ██       ██    █  ██  ▄█▀ ██     ▀█▄█    ██   ██  ██    █▀▀      █ \s
                ▄██▄▄▄▄▄█  ▀█▄▄▄▀  ▀█▄▀    █▀▄▄█▀    █▀▄▄█▀  ▀█▄▀ ▀█▄▄▀█▀ ▄██▄     ▀█▄▀     ██▄▄▄▀  ▄██▄ ▀█▄▄▀█▀     ▀█    ▄██▄ ▄██▄ ██▄  ▀████▄    ▀ \s
                                                                                            ██                    ▄▄  █                   ▄█▄▄▄▄▀  ▀█▀\s
                                                                                           ▀▀▀▀                     ▀▀                               \s
                                                                          
                            
                """).append("\nTHIS IS YOUR INITIAL CARD\n").append(show_playableCard(card)).append("""
                DO YOU WANNA PLAY IT FRONT OR BACK:
                
                ENTER TRUE TO PLAY IF FRONT, FALSE TO PLAY IF BACK
                """);
        show_message(stringBuilder.toString());

    }

    @Override
    public void show_gameStarting(String id) {
        System.out.println("\nGAME: " + id + " IS STARTING");
    }

    @Override
    public void show_isYourTurn(GameView immutableModel) {
            System.out.println("IT'S " + immutableModel.getCurrentPlayer().getNick() + " TURN");
    }

    public StringBuilder draw_playableCards(List<PlayableCard> cards){
        StringBuilder stringBuilder = new StringBuilder();
        for(PlayableCard card : cards){
            if (card instanceof GoldCard) {
                stringBuilder.append("GOLD CARD").append(" ".repeat(46));
            } else if (card instanceof ResourceCard) {
                stringBuilder.append("RESOURCE CARD").append(" ".repeat(42));
            }
        }
        stringBuilder.append("\n");
        for(PlayableCard card : cards){
            stringBuilder.append("CARD ID: ").append(card.getCardId()).append(" ".repeat(46 - String.valueOf(card.getCardId()).length()));
        }
        stringBuilder.append("\n");
        for(PlayableCard card : cards){
            if (card instanceof GoldCard) {
                stringBuilder.append("PERMANENT RESOURCE: ").append(getResourceEmoji(((GoldCard) card).getBackResource())).append(" ".repeat(33));
            } else if (card instanceof ResourceCard) {
                stringBuilder.append("PERMANENT RESOURCE: ").append(getResourceEmoji(((ResourceCard) card).getBackResource())).append(" ".repeat(33));
            }
        }
        stringBuilder.append("\n");
        for(PlayableCard card : cards){
            if (card instanceof GoldCard) {
                stringBuilder.append("POINTS: ").append(goldPoint((GoldCard) card)).append(" ".repeat(46));
                if(((GoldCard) card).getGoldType().equals(GoldType.ANGLE)){
                    stringBuilder.setLength(stringBuilder.length() - 23);
                }else if(((GoldCard) card).getGoldType().equals(GoldType.ITEM)){
                    stringBuilder.setLength(stringBuilder.length() - 10 - ((GoldCard) card).getBox().toString().length());
                }
            } else if (card instanceof ResourceCard) {
                stringBuilder.append("POINTS: ").append(((ResourceCard) card).getNumberOfPoints()).append(" ".repeat(46));
            }
        }
        stringBuilder.append("\n");
        for(PlayableCard card : cards){
            if (card instanceof GoldCard) {
                int spaces = ((GoldCard) card).getNeededResources().size();
                stringBuilder.append("REQUIREMENTS: ").append(mapToEmoji(((GoldCard) card).getNeededResources())).append(" ".repeat(40 - spaces*5 - (spaces-1)*2));
            } else if (card instanceof ResourceCard) {
                stringBuilder.append(" ".repeat(57));
            }
        }
        stringBuilder.append("\n");


        for(PlayableCard card : cards){
            stringBuilder.append("FRONT").append(" ".repeat(23)).append("BACK").append(" ".repeat(23));
        }
        stringBuilder.append("\n");
        for(PlayableCard card : cards){
            stringBuilder.append(getAnsiCode(card.getType())).append("┌──────────────────────┐    ┌──────────────────────┐").append("\u001B[0m").append("   ");
        }

        stringBuilder.append("\n");
        for(PlayableCard card : cards){
            String FHL = safeString(getResourceEmoji(card.getFront().get(Angle.HIGHLEFT)));
            String FHR = safeString(getResourceEmoji(card.getFront().get(Angle.HIGHRIGHT)));
            String BHL = safeString(getResourceEmoji(card.getBack().get(Angle.HIGHLEFT)));
            String BHR = safeString(getResourceEmoji(card.getBack().get(Angle.HIGHRIGHT)));
            stringBuilder.append(getAnsiCode(card.getType())).append("│").append(FHL).append(" ".repeat(18)).append(FHR).append("│").append("    ").append("│").append(BHL).append(" ".repeat(17)).append(BHR).append("│").append("\u001B[0m").append("   ");

        }
        stringBuilder.append("\n");
        for(PlayableCard card : cards){
            stringBuilder.append(getAnsiCode(card.getType())).append("│                      │    │                      │").append("\u001B[0m").append("   ");
        }

        stringBuilder.append("\n");
        for(PlayableCard card : cards){
            String BDL = safeString(getResourceEmoji(card.getBack().get(Angle.DOWNLEFT)));
            String BDR = safeString(getResourceEmoji(card.getBack().get(Angle.DOWNRIGHT)));
            String FDL = safeString(getResourceEmoji(card.getFront().get(Angle.DOWNLEFT)));
            String FDR = safeString(getResourceEmoji(card.getFront().get(Angle.DOWNRIGHT)));

            stringBuilder.append(getAnsiCode(card.getType())).append("│").append(FDL).append(" ".repeat(18)).append(FDR).append("│").append("    ").append("│").append(BDL).append(" ".repeat(17)).append(BDR).append("│").append("\u001B[0m").append("   ");

        }
        stringBuilder.append("\n");
        for(PlayableCard card : cards){
            stringBuilder.append(getAnsiCode(card.getType())).append("└──────────────────────┘    └──────────────────────┘").append("\u001B[0m").append("   ");
        }
        return stringBuilder;
    }

    @Override
    public void show_playerHand(GameView immutableModel) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("""
                ████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████
                """).append("""
                 
                 ▀█▀ █▄█ █ ▄▀▀   █ ▄▀▀   ▀▄▀ ▄▀▄ █ █ █▀▄   █▄█ ▄▀▄ █▄ █ █▀▄
                  █  █ █ █ ▄██   █ ▄██    █  ▀▄▀ ▀▄█ █▀▄   █ █ █▀█ █ ▀█ █▄▀
                                
                """);
//57 lunghezza carte più spazio (5 per ognuna più 2 per ognuna -1)
        stringBuilder.append(draw_playableCards(immutableModel.getCurrentPlayer().showCard()));
        stringBuilder.append("\n");
        stringBuilder.append("""
                ████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████
                """);
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void show_gameStation(GameView view){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("""
                ████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████
                 
                 ▄▀  ▄▀▄ █▄ ▄█ ██▀   ▄▀▀ ▀█▀ ▄▀▄ ▀█▀ █ ▄▀▄ █▄ █ ▄▀▀
                 ▀▄█ █▀█ █ ▀ █ █▄▄   ▄██  █  █▀█  █  █ ▀▄▀ █ ▀█ ▄██
                                
                """);

        HashMap<String, Point> gameDimension = new HashMap<>();

        int maxRow = 0;

        for(Player p : view.getPlayers().keySet()){
            Map<Point, PlayableCard> playedCards = p.getGameStation().getPlayedCards();

            int row = (findMaxValue(playedCards.keySet(), "x") +1) *2 +1;
            int column = (findMaxValue(playedCards.keySet(), "y") +1) *2 +1;

            if(row > maxRow){
                maxRow = row;
            }

            gameDimension.put(p.getNick(), new Point(row, column));

            stringBuilder.append(p.getNick()).append(" ".repeat((gameDimension.get(p.getNick()).y*6+1)-p.getNick().length() + 3)); //lascio 3 spazi tra una GM e quella successiva

        }
        maxRow = maxRow + 1;

        stringBuilder.append("\n");

        for(String nick : gameDimension.keySet()){

            stringBuilder.append("_".repeat(gameDimension.get(nick).y*6 +1)).append(" ".repeat(3));

        }
        stringBuilder.append("\n");

        for(int row = 1; row <= maxRow; row++){
            for(Player p : view.getPlayers().keySet()){
                if(gameDimension.get(p.getNick()).x + 1 == row) {
                    stringBuilder.append("_".repeat(gameDimension.get(p.getNick()).y*6 +1));
                } else if (gameDimension.get(p.getNick()).x < row){
                    stringBuilder.append(" ".repeat(gameDimension.get(p.getNick()).y * 6 + 1));
                } else if(gameDimension.get(p.getNick()).x == 3 && gameDimension.get(p.getNick()).y==3){
                    int spaces;

                    stringBuilder.append("|");

                    for(int column = 1; column <= gameDimension.get(p.getNick()).y; column++){
                        String value = determineValue(p.getGameStation(), new Point(column - gameDimension.get(p.getNick()).y + 1, gameDimension.get(p.getNick()).x - row - 1));
                        spaces = Math.round(2 - value.length()/2);
                        stringBuilder.append(" ".repeat(spaces) + value + " ".repeat(spaces));
                        if((2 * spaces + value.length()) != 5) stringBuilder.append(" ");
                        stringBuilder.append("|");
                    }

                }else{


                    int spaces;

                    stringBuilder.append("|");

                    for(int column = 1; column <= gameDimension.get(p.getNick()).y; column++){
                        String value = determineValue(p.getGameStation(), new Point(column - gameDimension.get(p.getNick()).y + 2, gameDimension.get(p.getNick()).x - row - 2));
                        spaces = Math.round(2 - value.length()/2);
                        stringBuilder.append(" ".repeat(spaces) + value + " ".repeat(spaces));
                        if((2 * spaces + value.length()) != 5) stringBuilder.append(" ");
                        stringBuilder.append("|");
                    }
                }
                stringBuilder.append(" ".repeat(3));
            }
            stringBuilder.append("\n");
        }

        stringBuilder.append(cardDraw(view));
        System.out.println(stringBuilder.toString());
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

    private static String cardDraw(GameView view){

        String HL;
        String HR;
        String DL;
        String DR;

        StringBuilder stringBuilder = new StringBuilder();

        for(Player p : view.getPlayers().keySet()){
            stringBuilder.append(p.getNick()).append(" CARDS PLAYED: \n");
            for(PlayableCard c : p.getGameStation().getPlayedCards().values()){
                stringBuilder.append("CARD ID: ").append(c.getCardId()).append(" ".repeat(18));
            }
            stringBuilder.append("\n");
            for(PlayableCard c : p.getGameStation().getPlayedCards().values()){
                stringBuilder.append(getAnsiCode(c.getType())).append("┌──────────────────────┐").append("\u001B[0m").append("   ");
            }
            stringBuilder.append("\n");
            for(PlayableCard c : p.getGameStation().getPlayedCards().values()){
            if(c.isFront()){
                HL = safeString(getResourceEmoji(c.getFront().get(Angle.HIGHLEFT)));
                HR = safeString(getResourceEmoji(c.getFront().get(Angle.HIGHRIGHT)));
            }else{
                stringBuilder.append(listToString(c.getAListOfBackResource()));
                HL = safeString(getResourceEmoji(c.getBack().get(Angle.HIGHLEFT)));
                HR = safeString(getResourceEmoji(c.getBack().get(Angle.HIGHRIGHT)));
            }
                stringBuilder.append(getAnsiCode(c.getType())).append(HL).append(" ".repeat(19)).append(HR).append(" ").append("\u001B[0m").append("   ");
            }
            stringBuilder.append("\n");
            for(PlayableCard c : p.getGameStation().getPlayedCards().values()){
                stringBuilder.append(getAnsiCode(c.getType())).append("                        ").append("\u001B[0m").append("   ");
            }
            stringBuilder.append("\n");
            for(PlayableCard c : p.getGameStation().getPlayedCards().values()){
            if(c.isFront()){
                DL = safeString(getResourceEmoji(c.getFront().get(Angle.DOWNLEFT)));
                DR = safeString(getResourceEmoji(c.getFront().get(Angle.DOWNRIGHT)));
            }else{
                stringBuilder.append(listToString(c.getAListOfBackResource()));
                DL = safeString(getResourceEmoji(c.getBack().get(Angle.DOWNLEFT)));
                DR = safeString(getResourceEmoji(c.getBack().get(Angle.DOWNRIGHT)));
            }
                stringBuilder.append(getAnsiCode(c.getType())).append(DL).append(" ".repeat(19)).append(DR).append(" ").append("\u001B[0m").append("   ");
            }
            stringBuilder.append("\n");
            for(PlayableCard c : p.getGameStation().getPlayedCards().values()){
                stringBuilder.append(getAnsiCode(c.getType())).append("└──────────────────────┘").append("\u001B[0m").append("   ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public void show_invalidPlay() {
        System.out.println("""
                INVALID PLAY...CHOOSE A VALID COORDINATE!!!
                """);
    }

    @Override
    public void show_notEnoughResources() {
        System.out.println("""
                NOT ENOUGH RESOURCES!!!
                """);
    }

    @Override
    public String show_goalCard(GoalCard card) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nGOAL CARD\n\n").append("CARD ID: ").append(card.getCardId()).append("\n").append("POINTS: ").append(card.getNumberOfPoints());
        stringBuilder.append(" EACH TIME THE REQUIREMENT IS SATISFIED").append("\n").append("REQUIREMENT:\n").append(goalPoint(card)).append("\n");
        stringBuilder.append("""
                
                ---------------------------------------------------------------------------------------------------------------------
                
                """ );
        return (stringBuilder.toString());
    }


    @Override
    public String show_playableCard(PlayableCard card) {
        StringBuilder stringBuilder = new StringBuilder();
        String FHL = safeString(getResourceEmoji(card.getFront().get(Angle.HIGHLEFT)));
        String FHR = safeString(getResourceEmoji(card.getFront().get(Angle.HIGHRIGHT)));
        String FDL = safeString(getResourceEmoji(card.getFront().get(Angle.DOWNLEFT)));
        String FDR = safeString(getResourceEmoji(card.getFront().get(Angle.DOWNRIGHT)));
        String BHL = safeString(getResourceEmoji(card.getBack().get(Angle.HIGHLEFT)));
        String BHR = safeString(getResourceEmoji(card.getBack().get(Angle.HIGHRIGHT)));
        String BDL = safeString(getResourceEmoji(card.getBack().get(Angle.DOWNLEFT)));
        String BDR = safeString(getResourceEmoji(card.getBack().get(Angle.DOWNRIGHT)));
        String ansiCode = getAnsiCode(card.getType());

        if (card instanceof GoldCard) { //manca esprimere metodo in points su come vengono guadagnati
            stringBuilder.append("""
                    \nGOLD CARD
                                        
                    CARD ID: """).append(card.getCardId()).append("\n").append("""
                    PERMANENT RESOURCE: """).append(getResourceEmoji(((GoldCard) card).getBackResource())).append("\n").append("""
                    POINTS: """).append(goldPoint((GoldCard) card)).append("\n").append("""
                    REQUIREMENTS: """).append("\n").append(mapToEmoji(((GoldCard) card).getNeededResources())).append("""
                                        
                    FRONT                      BACK
                    """).append(ansiCode).append("""
                    ┌──────────────────────┐    ┌────────────────────────┐
                    │""").append(FHL).append(" ".repeat(18)).append(FHR).append("│").append("   ").append(" │").append(BHL).append(" ".repeat(19)).append(BHR).append("│\n").append(ansiCode).append("""
                    │                      │    │                        │
                    │""").append(FDL).append(" ".repeat(18)).append(FDR).append("│").append("   ").append(" │").append(BDL).append(" ".repeat(19)).append(BDR).append("│\n").append("""
                    └──────────────────────┘    └────────────────────────┘
                    """).append("\u001B[0m");

        } else if (card instanceof ResourceCard) {
            stringBuilder.append("""
                    \nRESOURCE CARD
                                        
                    CARD ID:""").append(card.getCardId()).append("\n").append("""
                    PERMANENT RESOURCE:""").append(getResourceEmoji(((ResourceCard) card).getBackResource())).append("\n").append("""
                    POINTS:""").append(((ResourceCard) card).getNumberOfPoints()).append("\n").append("""
                                        
                    FRONT                      BACK
                    """).append(ansiCode).append("""
                    ┌──────────────────────┐    ┌────────────────────────┐
                    │""").append(FHL).append(" ".repeat(18)).append(FHR).append("│").append("   ").append(" │").append(BHL).append(" ".repeat(19)).append(BHR).append("│\n").append("""
                    │                      │    │                        │
                    │""").append(FDL).append(" ".repeat(18)).append(FDR).append("│").append("   ").append(" │").append(BDL).append(" ".repeat(19)).append(BDR).append("│\n").append("""
                    └──────────────────────┘    └────────────────────────┘
                    """).append("\u001B[0m");
        } else if (card instanceof InitialCard) {
            stringBuilder.append("""
                    \nINITIAL CARD
                                        
                    CARD ID:""").append(card.getCardId()).append("\n").append("""
                    BACK RESOURCES:""").append(listToEmoji(((InitialCard) card).getBackResources())).append("\n").append("""
                                        
                    FRONT                      BACK
                    """).append(ansiCode).append("""
                    ┌──────────────────────┐    ┌────────────────────────┐
                    │""").append(FHL).append(" ".repeat(18)).append(FHR).append("│").append("   ").append(" │").append(BHL).append(" ".repeat(19)).append(BHR).append("│\n").append("""
                    │                      │    │                        │
                    │""").append(FDL).append(" ".repeat(18)).append(FDR).append("│").append("   ").append(" │").append(BDL).append(" ".repeat(19)).append(BDR).append("│\n").append("""
                    └──────────────────────┘    └────────────────────────┘
                    """).append("\u001B[0m");
        }

        stringBuilder.append("""
                
                ---------------------------------------------------------------------------------------------------------------------
                
                """ );
        return stringBuilder.toString();
    }

    @Override
    public void show_tableOfDecks(GameView immutableModel) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("""
                ████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████
                """).append("""
                 
                 ▄▀▀ ▄▀▄ █▄ ▄█ █▄ ▄█ ▄▀▄ █▄ █   █▀▄ █▀▄ ▄▀▄ █   █ ▄▀▄ ██▄ █   ██▀   ▄▀▀ ▄▀▄ █▀▄ █▀▄ ▄▀▀
                 ▀▄▄ ▀▄▀ █ ▀ █ █ ▀ █ ▀▄▀ █ ▀█   █▄▀ █▀▄ █▀█ ▀▄▀▄▀ █▀█ █▄█ █▄▄ █▄▄   ▀▄▄ █▀█ █▀▄ █▄▀ ▄█▀
                                
                """);

        ArrayList<PlayableCard> resourceCards = immutableModel.getTableOfDecks().getResourceCards();
        ArrayList<PlayableCard> goldCards = immutableModel.getTableOfDecks().getGoldCards();
        stringBuilder.append(draw_playableCards(resourceCards));
        stringBuilder.append("""
                
                ---------------------------------------------------------------------------------------------------------------------
                
                """);
        stringBuilder.append(draw_playableCards(goldCards));
        stringBuilder.append("\n\n");

        stringBuilder.append("""
                 
                 ▄▀▀ ▄▀▄ █▄ ▄█ █▄ ▄█ ▄▀▄ █▄ █   ▄▀  ▄▀▄ ▄▀▄ █     ▄▀▀ ▄▀▄ █▀▄ █▀▄ ▄▀▀
                 ▀▄▄ ▀▄▀ █ ▀ █ █ ▀ █ ▀▄▀ █ ▀█   ▀▄█ ▀▄▀ █▀█ █▄▄   ▀▄▄ █▀█ █▀▄ █▄▀ ▄█▀
                                
                """);
        for(Card card : immutableModel.getTableOfDecks().getGoals()){
            stringBuilder.append(show_goalCard((GoalCard) card));
        }
        stringBuilder.append("""
                ████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████
                """);
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void show_lastTurn() {
        System.out.println("""
                ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
                
                 ▀█▀ █▄█ █ ▄▀▀   █ ▄▀▀   ▀█▀ █▄█ ██▀   █   ▄▀▄ ▄▀▀ ▀█▀   ▀█▀ █ █ █▀▄ █▄ █   █
                  █  █ █ █ ▄██   █ ▄██    █  █ █ █▄▄   █▄▄ █▀█ ▄██  █     █  ▀▄█ █▀▄ █ ▀█   ▄
                                
                ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀
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

        System.out.println("""
                
                ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
                 
                 █▀▄ ▄▀▄ █▄ █ █▄▀ █ █▄ █ ▄▀\s
                 █▀▄ █▀█ █ ▀█ █ █ █ █ ▀█ ▀▄█
                
                                
                """ + stringBuilder + """
                ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀
                
                """);
    }

    @Override
    public void show_gameOver() {//solo scritta gameover o anche nome vincitore?
        System.out.println("the game is over now we are calculating the winner...");
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
            return mapToEmoji(card.getListOfObjects());
        } else {
            return drawGoalRequirement(card.getGoalType());
        }
    }

    private static String drawGoalRequirement(CheckInterface checkInterface){
        if(checkInterface instanceof DiagonalAnimalCheck){
            return """
                                               ┌────────────┐
                                   ┌───────────│     🦊     │
                       ┌───────────│     🦊    └────────────┘
                       │     🦊    └────────────┘
                       └────────────┘
                       """;
        } else if (checkInterface instanceof DiagonalMushroomCheck){
            return """
                                               ┌────────────┐
                                   ┌───────────│     🍄     │
                       ┌───────────│     🍄    └────────────┘
                       │     🍄    └────────────┘
                       └────────────┘
                       """;

        }else if (checkInterface instanceof DiagonalInsectCheck){
            return """
                       );
                       ┌────────────┐
                       │     🦋    ┌────────────┐
                       └───────────│     🦋    ┌────────────┐
                                   └───────────│     🦋     │
                                               └────────────┘
                       """;
        }else if (checkInterface instanceof DiagonalVegetableCheck){
            return """
                       ┌─────────────┐
                       │     🍀      ┌─────────────┐
                       └─────────────│     🍀     ┌─────────────┐
                                     └────────────│     🍀      │
                                                  └─────────────┘
                       """;
        }else if (checkInterface instanceof LMushroomVegetableCheck){
            return """
                       ┌────────────┐
                       │     🍄     │
                       └────────────┘
                       ┌────────────┐
                       │     🍄    ┌─────────────┐
                       └───────────│      🍀     │
                                   └─────────────┘
                       """;
        }
        else if (checkInterface instanceof ReverseLVegetableInsectCheck){
            return """
                                   ┌─────────────┐
                                   │     🍀      │
                                   └─────────────┘
                                   ┌─────────────┐
                       ┌────────────┐    🍀      │
                       │     🦋     │────────────┘
                       └────────────┘
                       """;
        }
        else if (checkInterface instanceof UpsideDownLAnimalMushroomCheck){
            return """
                                   ┌────────────┐
                       ┌───────────│     🍄     │
                       │     🦊    └────────────┘
                       └────────────┘
                       ┌────────────┐
                       │     🦊     │
                       └────────────┘
                       """;
        }
        else if (checkInterface instanceof UpsideDownReverseLInsectAnimalCheck){
            return """
                       ┌────────────┐
                       │     🦊     │───────────┐
                       └────────────┘    🦋     │
                                   └────────────┘
                                   ┌────────────┐
                                   │     🦋     │
                                   └────────────┘
                       """;
        }
        return "error";
    }

    @Override
    public void show_youWin(){
        show_message("""
                                 
                                 ▄▄   ▄▄ ▄▄▄▄▄▄▄ ▄▄   ▄▄    ▄     ▄ ▄▄▄▄▄▄▄ ▄▄    ▄       ███\s
                                █  █ █  █       █  █ █  █  █ █ ▄ █ █       █  █  █ █         █
                                █  █▄█  █   ▄   █  █ █  █  █ ██ ██ █   ▄   █   █▄█ █   ██    █
                                █       █  █ █  █  █▄█  █  █       █  █ █  █       █         █
                                █▄     ▄█  █▄█  █       █  █       █  █▄█  █  ▄    █   ██    █
                                  █   █ █       █       █  █   ▄   █       █ █ █   █         █
                                  █▄▄▄█ █▄▄▄▄▄▄▄█▄▄▄▄▄▄▄█  █▄▄█ █▄▄█▄▄▄▄▄▄▄█▄█  █▄▄█      ███
                                 
                                                                """);
    }

    @Override
    public void show_youLose(){
        show_message("""
                                
                                 ▄▄   ▄▄ ▄▄▄▄▄▄▄ ▄▄   ▄▄    ▄▄▄     ▄▄▄▄▄▄▄ ▄▄▄▄▄▄▄ ▄▄▄▄▄▄▄        ███
                                █  █ █  █       █  █ █  █  █   █   █       █       █       █      █
                                █  █▄█  █   ▄   █  █ █  █  █   █   █   ▄   █  ▄▄▄▄▄█▄     ▄█  ██  █
                                █       █  █ █  █  █▄█  █  █   █   █  █ █  █ █▄▄▄▄▄  █   █        █
                                █▄     ▄█  █▄█  █       █  █   █▄▄▄█  █▄█  █▄▄▄▄▄  █ █   █    ██  █
                                  █   █ █       █       █  █       █       █▄▄▄▄▄█ █ █   █        █
                                  █▄▄▄█ █▄▄▄▄▄▄▄█▄▄▄▄▄▄▄█  █▄▄▄▄▄▄▄█▄▄▄▄▄▄▄█▄▄▄▄▄▄▄█ █▄▄▄█         ███
                                                                
                                
                                """);
    }

    @Override
    public void show_requestGoalCard(ArrayList<GoalCard> cards){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("""
                   CHOOSE A GOAL CARD
                   """);
        for(GoalCard goalCard : cards){
            stringBuilder.append(show_goalCard(goalCard));
        }
        stringBuilder.append("ENTER CARD ID:\n");
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void show_requestCardId() {
        System.out.println("ENTER CARD ID:");
    }

    @Override
    public void show_winner(String winner){
        show_message("the winner is: " + winner);
    }

    @Override
    public void show_requestTypeToDraw() {
        show_message("""
                WHICH TYPE OF CARD DO YOU WANT TO DRAW:
                
                RESOURCE
                GOLD
                """);
    }

    @Override
    public void show_drawFromWhere() {
        show_message("""
                FROM WHERE DO YOU WANT DRAW A CARD:
                
                A-DECK
                B-TABLE
                
                """);
    }

    @Override
    public void show_invalidChoice() {
        show_message("""
                        INVALID CHOICE...
                        """);
    }

    @Override
    public void show_requestSide() {
        System.out.println("""
                YOU WANNA PLAY IT FRONT OR BACK:
                    
                ENTER TRUE TO PLAY IF FRONT, FALSE TO PLAY IF BACK
                """);
    }

    @Override
    public void show_requestCoordinates() {
        System.out.println("""
                CHOOSE A COORD:
                
                ENTER X COORDINATE THAN Y COORDINATE:
                """);
    }

    @Override
    public void show_connectionError() {
        System.out.println("CONNECTION ERROR, GAME OVER...");
    }

    @Override
    public void show_invalidInput() {
        System.out.println("INVALID INPUT\n");
    }

    @Override
    public void show_invalidCommand() {
        System.out.println("INVALID COMMAND\n");
    }

    @Override
    public void show_waitingOtherPlayers() {
        System.out.println("Waiting for other players...");
    }

    @Override
    public void show_requestToLeave() {
        System.out.println("press any button to leave...");
    }
}