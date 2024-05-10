package it.polimi.ingsw.view.statusActive;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.UI;

import java.util.Optional;
import java.util.Scanner;

public class DrawCardState extends StateActive{

    private GameFlow flow;
    private GameView view = flow.getView();
    private UI ui = flow.getUi();
    private ClientAction client = flow.getClient();
    private String nickName = flow.getNickname();

    public DrawCardState(GameFlow flow){
        super(flow);
    }

    @Override
    public void execute(){ //manca exception in gameController
        Scanner scanner = new Scanner(System.in);
        ui.show_tableOfDecks(view);
        ui.show_message("""
                WHICH TYPE OF CARD DO YOU WANT TO DRAW:
                
                RESOURCE
                GOLD
                """);
        String typeOfCard = scanner.nextLine();

        ui.show_message("""
                FROM WHERE DO YOU WANT DRAW A CARD:
                
                A-DECK
                B-TABLE
                
                """);
        String input = scanner.nextLine();

        switch (input){
            case "A":
                client.drawPlayableCardFromTableOfDecks(nickName,typeOfCard.toLowerCase());
                break;
            case "B":
                Optional<Card> cardCheck;
                do {
                    ui.show_message("ENTER CARD ID");
                    int cardId = scanner.nextInt();

                    cardCheck = view.getTableOfDecks().getCards().stream()
                            .filter(card -> card.getCardId()==cardId)
                            .findFirst();

                }while (!cardCheck.isPresent());

                client.drawFromTable(nickName,cardCheck.get());
                break;
            default:
                ui.show_message("""
                        INVALID CHOICE...
                        """);
                this.execute();
        }
    }

    @Override
    public void nextState(){
        client.goOn();
    }

}
