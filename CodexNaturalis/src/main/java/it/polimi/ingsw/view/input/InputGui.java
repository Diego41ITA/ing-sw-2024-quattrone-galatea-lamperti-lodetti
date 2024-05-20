package it.polimi.ingsw.view.input;

import it.polimi.ingsw.view.GUI.MultipleResponses;

public class InputGui implements GetInput{
    //questa arrayList contiene i valori degli input che l'utente ha inserito tramite i bottoni della gui
    protected MultipleResponses multipleResponses = new MultipleResponses();
    //idea: creare dei metodi che ciclano sugli ultimi elementi dell'array multipleResponses e fin quando
    //non hanno un valori validi aspettano a restiture qualcosa
    //esempio:
    @Override
    public int getNumberOfPlayer() {
        //ciclo infinito fin quando l'ultimo elemento dell'array non è un numero
        while(!(Character.isDigit(multipleResponses.get(multipleResponses.size()-1).charAt(0)))){
        }
        //ritorno il numero
        return Integer.parseInt(multipleResponses.get(multipleResponses.size()-1));
    }

    //devono essere implementati
    @Override
    public boolean getSideOfTheCard() {
        return false;
    }

    @Override
    public int getCardId() {
        return 0;
    }

    @Override
    public String getNickName() { return null;

    }

    @Override
    public String getOption() {
        return null;
    }

    @Override
    public String getGameId() {
        return null;
    }

}
