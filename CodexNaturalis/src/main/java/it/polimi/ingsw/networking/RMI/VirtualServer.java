package it.polimi.ingsw.networking.RMI;

import it.polimi.ingsw.controller.FSM.State;
import it.polimi.ingsw.controller.MainController;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {

    //metodi per modificare il game presenti in state

    public void connect(VirtualView client) throws RemoteException;
    public MainController getMainController();
    public String start() throws RemoteException;

    public int handleInput(String input) throws RemoteException;

    public void goNextState()throws RemoteException;

    public void goToSpecifiedState(State state);

}
