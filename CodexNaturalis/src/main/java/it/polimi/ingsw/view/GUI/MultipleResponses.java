package it.polimi.ingsw.view.GUI;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * this class is used to hold various input, and it works like a bridge between the gui and the FsmGame, clearly to do so
 * all the get-methods are blocking.
 * To optimize the coupling it implements the Singleton Pattern
 */
public class MultipleResponses{
    private  LinkedBlockingQueue<String> Responses;
    private static MultipleResponses instance = null;
    private MultipleResponses(){
        Responses = new LinkedBlockingQueue<>();
    }

    public static MultipleResponses getInstance(){
        if(instance == null)
            instance = new MultipleResponses();
        return instance;
    }

    /**
     * it adds a String to the queue
     * @param c the string that you want to add
     */
    public void add(String c) {
        try {
            Responses.put(c);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * it gets the first element in the list, this is a blocking method
     * @return the first element (with a FIFO logic)
     */
    public String getFirst(){
        try {
            return Responses.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
