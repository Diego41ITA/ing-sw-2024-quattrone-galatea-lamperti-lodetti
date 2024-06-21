package it.polimi.ingsw.view.statusWaiting;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.UI;
import it.polimi.ingsw.view.input.InputParser;

import java.rmi.RemoteException;

public class StateColor extends StateWaiting {
    private UI ui;
    private ClientAction client;
    private String nick;
    private GameView view;

    public StateColor(FsmGame flow, InputParser input){
        super(flow, input);
        this.ui = flow.getUi();
        this.client = flow.getClient();
        this.nick = flow.getNickname();
        view = flow.getView();
    }

    @Override
    public void execute(){

        String color;

        do{
            ui.show_requestPlayerColor(StateWaiting.flow.getView());
            color = inputGetter.getColor();
        }while(!checkColor(color));

        String colorParsed = color.toLowerCase();
        try {
            client.setColor(colorParsed, StateWaiting.flow.getNickname());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        //nextState();
    }

    @Override
    public void nextState(){
        flow.setWaitingState(new DoesNothing(flow, inputGetter));
    }

    private boolean checkColor(String color){
        for(Color c: Color.values()){
            if(c.toString().equalsIgnoreCase(color))
                return true;
        }
        return false;
    }
}
