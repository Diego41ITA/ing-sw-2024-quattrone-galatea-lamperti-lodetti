package it.polimi.ingsw.model.gameDataManager;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.awt.Point;

import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.exceptions.illegalOperationException;
/**
 * represents the class associated with the player
 * @author Lorenzo galatea
 */
public class Player {

    /** nickname is the player's name and is a string */
    private String nickname;
    /** cards is a list and represents the cards the player has in his hand*/
    private ArrayList<PlayableCard> cards;
    /** gamestation is an object that represents the level in which the cards played by the player are located*/
    private GameStation gamestation;
    /** color is a data item in an enum and indicates the marker associated with the player*/
    private Color color;
    /** optionalColor represents the black marker associated with the first player*/
    private Optional<Color> optionalColor;
    /** goal is a class type of data and represents the goal card chosen by the player*/
    private GoalCard goal;

    /**
     * this constructor does nothing
     */
    public Player(){}

    /** 
     * is the constructor of the class
     * @author Lorenzo Galatea
    */
    public Player(String nickname, GameStation gamestation, Color color, ArrayList<PlayableCard> cards) {
        this.cards = new ArrayList<>(cards);
        this.nickname = nickname;
        this.gamestation = new GameStation(gamestation);
        this.color = color;
        this.optionalColor = Optional.empty();
    }

    public Player(Player player){
        this.nickname = player.getNick();
        this.cards = new ArrayList<>(player.showCard());    //no need for the empty exception, we will see it later
        this.gamestation = new GameStation(player.getGameStation());
        this.color = player.getColor();
        this.optionalColor = player.getOptionalColor();
        this.goal = new GoalCard(player.getGoal()); //this is the safest approach
    }

    /**
     * This is a method that returns the player's nickname
     * @author Lorenzo Galatea
     * @return nickname a String which is the nickname of the player
     */
    public String getNick() {
        return nickname;
    }

    /**
     * Method for set the nickname
     * @author Lorenzo Galatea
     * @param newNickname is the name of the player
     */
    public void setNickname(String newNickname){
        this.nickname= newNickname;
    }

    /**
     * method that returns the gamestation of the player
     * @author Lorenzo Galatea
     * @return gamestation
     */
    public GameStation getGameStation(){
        return new GameStation(this.gamestation);
    }

    /**
     * a method for setting the gamestation
     * @author Lorenzo Galatea
     * @param newGamestation is the instance of the new GameStation to be set
     */
    public void setGameStation(GameStation newGamestation){
        this.gamestation = new GameStation(newGamestation);
    }

    /**
     * method that return the maker of the first player
     * @author Lorenzo Galatea
     * @return optionalColor that is the maker null or the maker black if is associated with the first player
     */
    public Optional<Color> getOptionalColor() {
        return optionalColor;
    }

    /**
     * method that set the maker black if I am the first player
     * @author Lorenzo Galatea
     */
    public void setOptionalColor() {
        this.optionalColor = Optional.of(Color.BLACK);
    }

    /**
     * method return the maker associated with the player
     * @author Lorenzo Galatea
     * @return color is maker of the player
     */
    public Color getColor() {
        return color;
    }

    /**
     * setter for the attribute color
     * @author Lorenzo Galatea
     * @param color: the maker
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * getter of the attribute goal
     * @author Lorenzo Galatea
     * @return goal which is the GoalCard chosen by the player
     */
    public GoalCard getGoal() {
        return new GoalCard(goal);
    }

    /**
     * draw a card from the TableOfDecks
     * @author Lorenzo Galatea
     * @param card: one of the 4 cards visible in the TableOfDecks
     * @throws IllegalStateException if the player already has 3 cards
     */
    public void draw(PlayableCard card) {
            if (cards.size() >= 3) {
                throw new IllegalStateException("Hand is full. Cannot draw more cards.");
            } else {
                cards.add(card);
            }
    }

    /**
     * draw a card from one of the decks of the TableOfDecks. This method uses the covariance of generifcs: PlayableCard
     * is a super-type of GoldCard, InitialCard and ResourceCard; thus, Deck<\GoldCard> is a subtype of Deck<\PlayableCard>
     * @author Lorenzo Galatea
     * @param deck: Deck associated with TableOfDecks
     * @throws IllegalStateException if the player already has 3 cards in his hand or the deck is empty
     */
    public void draw(Deck<PlayableCard> deck) {
            if (cards.size() >= 3) {
                throw new IllegalStateException("Hand is full. Cannot draw more cards.");
            } else {
                if (deck.getDimension() == 0) {
                    throw new IllegalStateException("Deck is empty. Cannot draw more cards.");
                }
                PlayableCard card = deck.getFirst();
                cards.add(card);
            }
    }

    /**
     * returns the list of cards in the player's hand
     * @author Lorenzo Galatea
     * @return cards is  List of cards in the player's hand
     */
    public List<PlayableCard> showCard() {
        if (cards != null) {
            return new ArrayList<>(cards);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * choose the goal
     * @author Lorenzo Galatea
     * @param goals is a list of objective cards
     * @param index is the index of the GoalCard chosen
     * @throws IllegalArgumentException if index is not a number between 0 and 1 (inclusive)
     */
    public void chooseGoal(List<GoalCard> goals, int index) {
        if (index >= 0 && index < goals.size()) {
            goal = goals.get(index);
        } else {
            throw new IllegalArgumentException("Index out of bounds");
        }
    }

    /**
     * removes cards from the player's hand
     * @author Lorenzo Galatea
     */
    public void removeCards() {
        cards.clear();
    }

    /**
     * allows the player to play a card.
     * It verifies that, in case of playing a goldCard, there are all the resources needed to do it
     * @param card is the card he wants to play
     * @param cord is the cord where he wants to play the card
     * @throws IllegalStateException if it's not possible to play the card
     */
    public void playCard(PlayableCard card, Point cord) throws illegalOperationException {
        if (card.verifyResources(this.gamestation)) {
            this.gamestation.placeCard(card, cord);
            this.cards.remove(card);
        }
        else {
            throw new illegalOperationException("There are not enough resources to play this card");
        }
    }

    /**
     * this method is an override useful to put a player into a hash-map and to use Map methods like: containsKey() ecc...
     * @return this method returns the hash code of every Player object with the same nickname, which is a unique id.
     */
    @Override
    public int hashCode(){
        return this.getNick().hashCode();
    }

    /**
     * this method compares this object with another Object passed as parameter.
     * @param player it's the only parameter and its type is Object. That's because this method override superclass
     *             method. Be aware that you should pass an object that has the same type of "this"
     * @return true if the two object have the same id (same nickname), false otherwise.
     */
    @Override
    public boolean equals(Object player){
        try{
            Player newPlayer = (Player) player;
            if(this.getNick().equals(newPlayer.getNick()))
                return true;
            else
                return false;
        }catch(ClassCastException e){
            return false;
        }
    }
}

