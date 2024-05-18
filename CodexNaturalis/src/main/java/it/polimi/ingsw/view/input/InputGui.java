package it.polimi.ingsw.view.input;

import it.polimi.ingsw.view.GUI.MultipleResponses;

public class InputGui implements GetInput{
    //questa arrayList contiene i valori degli input che l'utente ha inserito tramite i bottoni della gui
    protected MultipleResponses multipleResponses = new MultipleResponses();
    //idea: creare dei metodi che ciclano sugli ultimi elementi dell'array multipleResponses e fin quando
    //non hanno un valori validi aspettano a restiture qualcosa
    //esempio:
    @Override
    public String getNickName() {
        while(!(multipleResponses.get(multipleResponses.size()-1).length()>1)){
           //ciclo infinito fin quando non ho un nome(cioè una stringa di lunghezza >1)
        }
        //ritorno il nome
        return multipleResponses.get(multipleResponses.size()-1);
    }

    @Override
    public String getOption() {
        return null;
    }

    @Override
    public String getGameId() {
        return null;
    }

    @Override
    public int getNumberOfPlayer() {
        return 0;
    }
}
