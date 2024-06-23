package it.polimi.ingsw.view.input;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * this class allows to read the input when they came from the input stream: in this case the chosen interface should
 * be the textual one.
 */
public class InputUi implements InputParser {

    /**
     * it reads a String
     * @return it returns the read String
     */
    private String scannerInputString(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * it reads a boolean
     * @return it returns the read Boolean
     */
    private boolean scannerInputBoolean(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextBoolean();
    }

    /**
     * it reads an int
     * @return it returns the read int
     */
    private int scannerInputInt(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNickName() {
        return scannerInputString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOption() {
        return scannerInputString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getGameId() {
        return scannerInputString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfPlayer() {
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextInt();
        }catch (InputMismatchException e){
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getSideOfTheCard() {
        try {
            return scannerInputBoolean();
        }
        catch (InputMismatchException e){
            System.out.println("Invalid input. Insert TRUE or FALSE");
            return getSideOfTheCard();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCardId() {
        try {
            return scannerInputInt();
        }catch (InputMismatchException e){
            return -1;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getColor() {
        return scannerInputString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTypeOfCard() {
        return scannerInputString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDrawFromDeckOrTable() {
        return scannerInputString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point getCoordinate() {
        Scanner scanner = new Scanner(System.in);
        try {
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        return new Point(x,y);
        }catch (InputMismatchException e){
            return new Point(0,0);
        }
    }


}
