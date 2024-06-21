package it.polimi.ingsw.networking;

import it.polimi.ingsw.view.FsmGame;

import java.io.IOException;

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
                Thread.sleep(50 );
            }catch (NullPointerException e){
                System.err.println("the controller is still not initialized ");
                e.printStackTrace();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    System.err.println("exception caught during sleep");
                    this.interrupt();
                }
            }catch(InterruptedException | IOException e) {
                System.err.println("impossible to reach the server due to exception: " + e.getCause());
                this.interrupt();
                throw new RuntimeException();
            }
        }
    }
}

