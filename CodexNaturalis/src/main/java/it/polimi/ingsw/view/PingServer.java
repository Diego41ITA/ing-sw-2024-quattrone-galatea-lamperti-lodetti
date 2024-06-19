package it.polimi.ingsw.view;

import it.polimi.ingsw.networking.ClientAction;

import java.io.IOException;
import java.rmi.RemoteException;

public class PingServer extends Thread {
    private final FsmGame game;
    private ClientAction client;
    private final Object lock;

    public PingServer(FsmGame game, ClientAction client, Object lock) {
        this.game = game;
        this.client = client;
        this.lock = lock;
    }

    @Override
    public void run() {
        while(!this.isInterrupted()){
            try{
                if(this.client != null)
                    client.ping(game.getNickname());
                else
                    this.client = game.getClient();
                Thread.sleep(10000);
            }catch(InterruptedException | IOException e){
                System.err.println("impossible to reach the server due to exception: " + e.getCause());
                this.interrupt();
                this.game.interruptDueToDisconnection();
                throw new RuntimeException();
            }
        }
    }
}
