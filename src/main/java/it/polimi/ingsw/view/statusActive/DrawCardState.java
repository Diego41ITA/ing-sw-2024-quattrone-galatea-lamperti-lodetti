package it.polimi.ingsw.view.statusActive;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.UI;
import it.polimi.ingsw.view.input.InputParser;

import java.io.IOException;
import java.util.Optional;

/**
 * this is the state that allows the player to draw a card from the table.
 */
public class DrawCardState extends StateActive{

    private GameView view = flow.getView();
    private UI ui = flow.getUi();
    private ClientAction client = flow.getClient();
    private String nickName = flow.getNickname();

    /**
     * this is the only constructor: it has the FsmGame associated and the input getter.
     * @param flow
     * @param input
     */
    public DrawCardState(FsmGame flow, InputParser input){
        super(flow, input);
        this.view = flow.getView();
        this.ui = flow.getUi();
        this.client = flow.getClient();
        this.nickName = flow.getNickname();
    }

    /**
     * this method ask the user to draw a card and manages all the possible input combinations.
     */
    @Override
    public void execute(){

        String typeOfCard, input;
        ui.show_tableOfDecks(view);

        ui.show_drawFromWhere();
        do {
            input = inputGetter.getDrawFromDeckOrTable().toUpperCase(); //getDrawFromDeckOrTable()
        }while(!(input.equalsIgnoreCase("A") || input.equalsIgnoreCase("B")));

        switch (input){
            case "A":
                try {
                    do {
                        ui.show_requestTypeToDraw();
                        typeOfCard = inputGetter.getTypeOfCard();
                    }while(!(typeOfCard.equalsIgnoreCase("resource") || typeOfCard.equalsIgnoreCase("gold")));
                    client.drawPlayableCardFromTableOfDecks(nickName,typeOfCard.toLowerCase());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "B":
                Optional<Card> cardCheck;
                do {
                    ui.show_requestCardId();
                    int cardId = inputGetter.getCardId();

                    cardCheck = view.getTableOfDecks().getCards().stream()
                            .filter(card -> card.getCardId()==cardId)
                            .findFirst();

                }while (!cardCheck.isPresent());

                try {
                    client.drawFromTable(nickName,cardCheck.get());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                ui.show_invalidChoice();
                this.execute();
        }
        nextState();
    }

    /**
     * the concrete "next state" doesn't exist: it should just go on and another player will play.
     */
    @Override
    public void nextState(){
        try {
            client.goOn();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setView(GameView view) {
        this.view = view;
    }

}
