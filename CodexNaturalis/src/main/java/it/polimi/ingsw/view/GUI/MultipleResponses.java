package it.polimi.ingsw.view.GUI;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

//Quando premiamo un pulsante nella view stiamo in realtà passando una data stringa come input.
//per esempio se decidiamo di schiacciarer il bottone relativo a Join Random Game stiamo passando
//come input la stringa A. Questa classe serve ad associare le azioni sui bottoni della view con
//le stringhe in input
//si salvano i valori di input all'interno di questa classe che po verranno adoperati dal gameflow
//per svolgere la logica di gioco
//questa classe verrà messa nella gui come attributo
public class MultipleResponses{

    private LinkedBlockingQueue<String> Responses;
    public MultipleResponses(){
        Responses = new LinkedBlockingQueue<>();
    }

    public void add(String c) {
        try {
            Responses.put(c);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public LinkedBlockingQueue<String> getResponses() {
        return Responses;
    }
    public String getFirst(){
        try {
            return Responses.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getLast(){
        String lastElement = null;
        LinkedBlockingQueue<String> newQueue = new LinkedBlockingQueue<>();
        while(this.Responses.peek() != null){
            lastElement = this.Responses.poll();

            //if it's not the last element
            if(this.Responses.peek() != null) {
                try {
                    newQueue.put(lastElement);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        this.Responses = newQueue;
        return lastElement;
    }

    public void clearResponses(){
        Responses.clear();
    }

    //this method could return the wrong element.
    public String get(int n){
        String nElement = null;
        LinkedBlockingQueue<String> newQueue = new LinkedBlockingQueue<>();
        for(int i = 0; i<(n - 1); i++){
            nElement = this.Responses.poll();
            try {
                newQueue.put(nElement);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        nElement = this.Responses.poll();
        return nElement;
    }
    public int size(){
        return Responses.size();

    }

}
