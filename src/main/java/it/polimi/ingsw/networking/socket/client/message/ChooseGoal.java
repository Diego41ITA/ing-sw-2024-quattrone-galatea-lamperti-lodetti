package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.ControllerOfGameInterface;
import it.polimi.ingsw.controller.ControllerOfMatchesInterface;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ChooseGoal extends Message{
    private final ArrayList<GoalCard> goals;
    private final int num;
    public ChooseGoal(String nick, int num, List<GoalCard> goals){
        this.nickname = nick;
        this.forMainController = false;
        this.num = num;
        this.goals = new ArrayList<>(goals);
    }
    @Override
    public void execute(ControllerOfGameInterface game) throws RemoteException, GameEndedException {
        game.chooseGoal(this.goals, this.num, this.nickname);
    }

    @Override
    public ControllerOfGameInterface execute(GameObserver obs, ControllerOfMatchesInterface operation) throws RemoteException {
        return null;
    }
}
