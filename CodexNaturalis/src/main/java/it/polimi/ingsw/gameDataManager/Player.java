package it.polimi.ingsw.gameDataManager;
import java.util.List;

public class Player {
    private String nickname;
    private List<Card> cards;
    private GameStation gamestation;
    private Color color;
    private Optional<Color> optionalColor;
    private GoalCard goal;

    public Player(String nickname, GameStation gamestation, Color color,Optional<Color> optionalColor) {
        this.nickname = nickname;
        this.gamestation = gamestation;
        this.color = color;
        this.optionalColor = optionalColor;
    }

    public String getNick() {
        return nickname;
    }

    public void setNickname(String newNickname){
        this.nickname= newNickname;
    }
    public GameStation getGameStation(){
        return gamestation;
    }
    public void setGameStation(GameStation newGamestation){
        this.gamestation = newGamestation;
    }
    public Optional<Color> getOptionalColor() {
        return optionalColor;
    }
    public void setOptionalColor(Optional<Color> optionalColor) {
        this.optionalColor = optionalColor;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public GoalCard getGoal() {
        return goal;
    }

    public void draw(Card card) {
            if (cards.size() >= 3) {
                throw new IllegalStateException("Hand is full. Cannot draw more cards.");
            } else {
                cards.add(card);
            }
    }
    public void draw(Deck deck) {
            if (cards.size() >= 3) {
                throw new IllegalStateException("Hand is full. Cannot draw more cards.");
            } else {
                if (deck.isEmpty()) {
                    throw new IllegalStateException("Deck is empty. Cannot draw more cards.");
                }
                Card card = deck.getFirst();
                cards.add(card);
            }
    }
    public List<Card> showCard() throws EmptyDeckException {
        if (cards.isEmpty()) {
            throw new EmptyDeckException("Il mazzo è vuoto");
        }
        return cards;
    }
    public void chooseGoal(List<GoalCard> goals, int index) {
        if (index >= 0 && index < goals.size()) {
            goal = goals.get(index);
        } else {
            throw new IllegalArgumentException("Index out of bounds");
        }
    }
    public void removeCards() {
        cards.clear();
    }
}
