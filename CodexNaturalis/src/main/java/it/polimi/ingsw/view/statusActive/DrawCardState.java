package it.polimi.ingsw.view.statusActive;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.UI;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class DrawCardState extends StateActive{

    private GameView view = flow.getView();
    private UI ui = flow.getUi();
    private ClientAction client = flow.getClient();
    private String nickName = flow.getNickname();

    public DrawCardState(GameFlow flow){
        super(flow);
        this.view = flow.getView();
        this.ui = flow.getUi();
        this.client = flow.getClient();
        this.nickName = flow.getNickname();
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
                try {
                    client.drawPlayableCardFromTableOfDecks(nickName,typeOfCard.toLowerCase());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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

                try {
                    client.drawFromTable(nickName,cardCheck.get());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                ui.show_message("""
                        INVALID CHOICE...
                        """);
                this.execute();
        }
        nextState();
    }

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
