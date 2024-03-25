package it.polimi.ingsw.gameDataManager;
import java.util.List;
import java.util.Optional;

import it.polimi.ingsw.card.Card;
import it.polimi.ingsw.card.GoalCard;
import it.polimi.ingsw.card.Deck;
/**
*    @author Lorenzo galatea
* represents the class associated with the player
* Nickname is the player's name and is a string
* Cards is a list and represents the cards the player has in his hand
* gamestation is a class type of data and represents the level in which the cards played by the player are located
* color is a data item in an enum and indicates the marker associated with the player
* optionalColor represents the black marker associated with the first player
* goal is a class type of data and represents the goal card chosen by the player
*/
public class Player {
    private String nickname;
    private List<Card> cards;
    private GameStation gamestation;
    private Color color;
    private Optional<Color> optionalColor;
    private GoalCard goal;

    /** 
    *@author Lorenzo Galatea
    * is the constructor of the class
    */
    public Player(String nickname, GameStation gamestation, Color color,Optional<Color> optionalColor) {
        this.nickname = nickname;
        this.gamestation = gamestation;
        this.color = color;
        this.optionalColor = optionalColor;
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
        return gamestation;
    }
    /** @author Lorenzo Galatea
    *method for set the gamestation
    *@param Gamestation
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
    *@throws IllegaleStateException if the player already has 3 cards
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
    *@throws IllegaleStateException if the player already has 3 cards in his hand or the deck is empty
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
    *@throws EmptyDeckException if the deck is empty
    */
    public List<Card> showCard() throws EmptyDeckException {
        if (cards.isEmpty()) {
            throw new EmptyDeckException("Il mazzo è vuoto");
        }
        return cards;
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
}
