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
    private ArrayList<Card> cards;
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
    *@author Lorenzo Galatea
    * is the constructor of the class
    */
    public Player(String nickname, GameStation gamestation, Color color,Optional<Color> optionalColor, ArrayList<Card> cards) {
        this.cards = new ArrayList<Card>(cards);
        this.nickname = nickname;
        this.gamestation = gamestation;
        this.color = color;
        this.optionalColor = optionalColor;
    }

    public Player(Player player){
        this.nickname = player.getNick();
        this.cards = new ArrayList<>(player.showCard());    //no need for the empty exception, we will see it later
        this.gamestation = new GameStation(player.getGameStation());
        this.color = player.getColor();
        this.optionalColor = player.getOptionalColor();
        this.goal = new GoalCard(player.getGoal()); //this is the safest approach
    }

    /**@author Lorenzo Galatea
    *is a method that returns the player's nickname
    * @return nickname a String which is the nickname of the player
    */
    public String getNick() {
        return nickname;
    }

    /** @author Lorenzo Galatea
    *Method for set the nickname
    *@param newNickname is the name of the player
    */
    public void setNickname(String newNickname){
        this.nickname= newNickname;
    }

    /** @author Lorenzo Galatea
    *method that returns the gamestation of the player
    *@return gamestation
    */
    public GameStation getGameStation(){
        return new GameStation(this.gamestation);
    }

    /** @author Lorenzo Galatea
    *method for setting the gamestation
    *@param newGamestation is the instance of the new GameStation to be set
    */
    public void setGameStation(GameStation newGamestation){
        this.gamestation = newGamestation;
    }

    /** @author Lorenzo Galatea
    *method that return the maker of the first player
    *@return optionalColor that is the maker null or the maker black if is associated with the first player
    */
    public Optional<Color> getOptionalColor() {
        return optionalColor;
    }

    /**@author Lorenzo Galatea
    *method that set the maker black if I am the first player
    *@param optionalColor is the black maker or null
    */
    public void setOptionalColor(Optional<Color> optionalColor) {
        this.optionalColor = optionalColor;
    }

    /**@author Lorenzo Galatea
    *method return the maker associated with the player
    @return color is maker of the player
    */
    public Color getColor() {
        return color;
    }

    /**@author Lorenzo Galatea
    *setter for the attribute color
    *@param color: the maker
    */
    public void setColor(Color color) {
        this.color = color;
    }

    /**@author Lorenzo Galatea
    *getter of the attribute goal
    @return goal which is the GoalCard chosen by the player
        */
    public GoalCard getGoal() {
        return goal;
    }

    /**@author Lorenzo Galatea
    *draw a card from the TableOfDecks
    *@param card: one of the 4 cards visible in the TableOfDecks
    *@throws IllegalStateException if the player already has 3 cards
    */
    public void draw(Card card) {
            if (cards.size() >= 3) {
                throw new IllegalStateException("Hand is full. Cannot draw more cards.");
            } else {
                cards.add(card);
            }
    }

    /**@author Lorenzo Galatea
    *draw a card from one of the decks of the TableOfDecks
    *@param deck: Deck associated with TableOfDecks
    *@throws IllegalStateException if the player already has 3 cards in his hand or the deck is empty
    */
    public void draw(Deck deck) {
            if (cards.size() >= 3) {
                throw new IllegalStateException("Hand is full. Cannot draw more cards.");
            } else {
                if (deck.getDimension() == 0) {
                    throw new IllegalStateException("Deck is empty. Cannot draw more cards.");
                }
                Card card = deck.getFirst();
                cards.add(card);
            }
    }

    /**@author Lorenzo Galatea
    *returns the list of cards in the player's hand
    *@return cards is  List of cards in the player's hand
    *@throws illegalOperationException if the player's hand is empty
    */
    public List<Card> showCard() throws illegalOperationException {
        if (cards.isEmpty()) {
            throw new illegalOperationException("The player has no cards in his hand");
        }
        return new ArrayList<Card>(cards);
    }

    /**@author Lorenzo Galatea
    *choose the goal
    *@param goals is a list of objective cards
    *@param index is the index of the GoalCard chosen
    *@throws IllegalArgumentException if index is not a number between 0 and 1 (inclusive)
    */
    public void chooseGoal(List<GoalCard> goals, int index) {
        if (index >= 0 && index < goals.size()) {
            goal = goals.get(index);
        } else {
            throw new IllegalArgumentException("Index out of bounds");
        }
    }

    /**@author Lorenzo Galatea
    *removes cards from the player's hand
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
}

