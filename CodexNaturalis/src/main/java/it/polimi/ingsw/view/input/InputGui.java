package it.polimi.ingsw.view.input;

import it.polimi.ingsw.view.GUI.MultipleResponses;

import java.awt.*;

/**
 * This class reads an input when the user interface is graphical. To do so all the methods blocks the flow until there
 * is a valid readable input.
 */
public class InputGui implements InputParser {
    /**
     * this object contains
     */
    protected static MultipleResponses multipleResponses = MultipleResponses.getInstance();

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfPlayer() {
        try{
            return Integer.parseInt(multipleResponses.getFirst());
        }catch (NumberFormatException e){
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getSideOfTheCard() {
        String result = multipleResponses.getFirst();
        boolean var;
        var = result.equals("true");
        return var;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCardId() {
        return Integer.parseInt(multipleResponses.getFirst());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getColor() {
        return multipleResponses.getFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTypeOfCard() {
        return multipleResponses.getFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDrawFromDeckOrTable() {
        return multipleResponses.getFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point getCoordinate() {
        int x = Integer.parseInt(multipleResponses.getFirst());
        int y = Integer.parseInt(multipleResponses.getFirst());

        return new Point(x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNickName() {
        return multipleResponses.getFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOption() {
        return multipleResponses.getFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getGameId() {
        return multipleResponses.getFirst();
    }

}
