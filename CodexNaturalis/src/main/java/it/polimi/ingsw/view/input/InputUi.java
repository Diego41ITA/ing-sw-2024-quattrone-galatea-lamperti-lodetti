package it.polimi.ingsw.view.input;

import java.util.Scanner;

public class InputUi implements GetInput{

    private String scannerInput(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    @Override
    public String getNickName() {
        return scannerInput();
    }

    @Override
    public String getOption() {
        return scannerInput();
    }

    @Override
    public String getGameId() {
        return scannerInput();
    }


}
