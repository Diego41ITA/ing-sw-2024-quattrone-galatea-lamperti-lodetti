package it.polimi.ingsw.networking;

//per risolvere i problemi correlati basta aggiungere import static it.polimi.ingsw.networking.PrintAsync.*; a MainController
public class PrintAsync {
    public static void printAsync(String msg){
        new Thread(()->{System.out.println(msg);}).start();
    }
}
