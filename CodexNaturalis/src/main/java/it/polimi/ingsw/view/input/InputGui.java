package it.polimi.ingsw.view.input;

import it.polimi.ingsw.view.GUI.MultipleResponses;

import java.awt.*;

public class InputGui implements InputParser {
    //questa arrayList contiene i valori degli input che l'utente ha inserito tramite i bottoni della gui
    protected static MultipleResponses multipleResponses = new MultipleResponses();
    //idea: creare dei metodi che ciclano sugli ultimi elementi dell'array multipleResponses e fin quando
    //non hanno un valori validi aspettano a restiture qualcosa
    //esempio:
    @Override
    public int getNumberOfPlayer() {
        //ciclo infinito fin quando l'ultimo elemento dell'array non è un numero
        while(!(Character.isDigit( multipleResponses.get(multipleResponses.size()-1).charAt(0)))){
        }
        //ritorno il numero
        return Integer.parseInt(multipleResponses.get(multipleResponses.size()-1));
    }

    //devono essere implementati
    @Override
    public boolean getSideOfTheCard() {
        String result = multipleResponses.getFirst();
        boolean var;
        var = result.equals("true");
        return var;
    }

    /*
    deve ritornare un intero che rappresenta l'id della carta -> si salvano gli id nel controller di table of decks
    e quando viene scelto invece che ritornare pos1 o pos2 si ritorna l'id della carta.
     */
    @Override
    public int getCardId() {
        int position = Integer.parseInt(multipleResponses.getFirst());
        //int cardId =
        return 0;
    }

    @Override
    public String getColor() {
        return multipleResponses.getFirst();
    }

    @Override
    public String getTypeOfCard() {
        return multipleResponses.getFirst();
    }

    @Override
    public String getDrawFromDeckOrTable() {
        return multipleResponses.getFirst();
    }

    //bisognerà fare una cosa analoga a getCardId()
    @Override
    public Point getCoordinate() {
        return null;
    }

    @Override
    public String getNickName() {
        return multipleResponses.getFirst();
    }

    @Override
    public String getOption() {
        return multipleResponses.getFirst();
    }

    @Override
    public String getGameId() {
        return multipleResponses.getFirst();
    }

}
