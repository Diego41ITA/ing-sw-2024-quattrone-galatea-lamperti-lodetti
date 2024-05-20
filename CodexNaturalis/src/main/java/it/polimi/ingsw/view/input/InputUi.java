package it.polimi.ingsw.view.input;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputUi implements InputParser {

    private String scannerInputString(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private boolean scannerInputBoolean(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextBoolean();
    }

    private int scannerInputInt(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    @Override
    public String getNickName() {
        return scannerInputString();
    }

    @Override
    public String getOption() {
        return scannerInputString();
    }

    @Override
    public String getGameId() {
        return scannerInputString();
    }

    @Override
    public int getNumberOfPlayer() {
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextInt();
        }catch (InputMismatchException e){
            return 0;
        }
    }

    @Override
    public boolean getSideOfTheCard() {
        return scannerInputBoolean();
    }

    @Override
    public int getCardId() {
        return scannerInputInt();
    }

    @Override
    public String getColor() {
        return scannerInputString();
    }

    @Override
    public String getTypeOfCard() {
        return scannerInputString();
    }

    @Override
    public String getDrawFromDeckOrTable() {
        return scannerInputString();
    }

    @Override
    public Point getCoordinate() {
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        return new Point(x,y);
    }


}
