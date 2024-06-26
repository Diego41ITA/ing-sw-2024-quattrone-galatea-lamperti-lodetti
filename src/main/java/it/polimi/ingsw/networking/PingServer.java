package it.polimi.ingsw.networking;

import it.polimi.ingsw.view.FsmGame;

import java.io.IOException;

/**
 * this class defines a way to constantly ping the server and avoid deadlocks, it extends thread and it has only one
 * method (run)
 */
public class PingServer extends Thread {
    private final FsmGame game;
    private ClientAction client;

    /**
     * the constructor links the FsmGame to this class
     * @param game the flow that needs to be linked
     * @param client the way to comunicate to the server
     */
    public PingServer(FsmGame game, ClientAction client) {
        this.game = game;
        this.client = client;
    }

    /**
     * this overried the run method of thread class, and it simply has the code to ping the server and avoid possible
     * error. It manages some exceptions in order to start this class in multiple points of the program.
     */
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

