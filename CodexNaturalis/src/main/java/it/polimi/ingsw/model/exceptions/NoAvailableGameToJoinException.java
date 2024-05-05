package it.polimi.ingsw.model.exceptions;

public class NoAvailableGameToJoinException extends Exception{
    public NoAvailableGameToJoinException(){
        super("No available game to join. Please insert detail for creation of a new game");
    }
}
