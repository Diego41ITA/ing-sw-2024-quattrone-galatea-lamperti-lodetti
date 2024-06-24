package it.polimi.ingsw.networking.rmi;

import it.polimi.ingsw.controller.ControllerOfMatchesInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static it.polimi.ingsw.controller.ControllerOfMatches.getMainController;

/**
 * this class defines the server for RMI communication
 * @author Luca Lamperti
 */
public class ServerRMI{
    public ServerRMI(){
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            ControllerOfMatchesInterface mainController = getMainController();
            registry.rebind("server name", mainController);
            System.out.println("Server RMI is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startServer(){
        new ServerRMI();
    }

}
