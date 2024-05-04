package it.polimi.ingsw.model.exceptions;

public class MaxPlayersInException extends Exception{
    public MaxPlayersInException(){
        super();
    }

    public MaxPlayersInException(String msg){
        super(msg);
    }
}
