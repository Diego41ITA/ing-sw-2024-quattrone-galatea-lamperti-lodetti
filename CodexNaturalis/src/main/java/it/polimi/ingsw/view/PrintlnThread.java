package it.polimi.ingsw.view;

public class PrintlnThread{

    public static void Println(String message) {
        new Thread(()->System.out.println(message)).start();
    }
}
