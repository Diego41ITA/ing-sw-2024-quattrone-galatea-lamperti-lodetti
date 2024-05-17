package it.polimi.ingsw.view.GUI;

import java.util.ArrayList;

//Quando premiamo un pulsante nella view stiamo in realtà passando una data stringa come input.
//per esempio se decidiamo di schiacciarer il bottone relativo a Join Random Game stiamo passando
//come input la stringa A. Questa classe serve ad associare le azioni sui bottoni della view con
//le stringhe in input
//si salvano i valori di input all'interno di questa classe che po verranno adoperati dal gameflow
//per svolgere la logica di gioco
//questa classe verrà messa nella gui come attributo
public class MultipleResponses{

    private ArrayList<String> Responses;
    public MultipleResponses(){
        Responses = new ArrayList<String>();
    }

    public void add(String c) {
        Responses.add(c);
    }

    public ArrayList<String> getResponses() {
        return Responses;
    }
    public String getFirst(){
        return Responses.getFirst();
    }

    public String getLast(){
        return Responses.getLast();
    }

    public void clearResponses(){
        Responses.clear();
    }

    public String get(int n){
        return Responses.get(n);
    }
    public int size(){
        return Responses.size();

    }

}
