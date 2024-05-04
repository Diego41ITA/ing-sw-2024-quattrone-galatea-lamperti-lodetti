package it.polimi.ingsw.main;

import java.util.Scanner;
//chiede all'utente che tipo di configurazione vuole:
//1) rmi + gui
//2) rmi + tui
//3) socket + gui
//4) socket + tui
//questo main è il primo a dover essere avviato
public class mainStart{
        public static void main(String[] args) {
            try {
                Scanner scanner = new Scanner(System.in);
                boolean exit = false;
                do {
                    System.out.println("Enter the desired configuration type (rmi/socket) followed by (gui/tui):");
                    String input = scanner.nextLine().trim();
                    String[] config = input.split(" ");
                    if (config.length != 2) {
                        System.out.println("Insufficient number of arguments");
                        exit = true;
                    }
                    if (exit) {
                        if(config[0].equals("rmi") && config[1].equals("gui")) {
                            System.out.println("game starting");
                            //lancia gameflow
                        } else if (config[0].equals("rmi") && config[1].equals("tui")) {
                            System.out.println("game starting");
                            //lancia gameflow
                        } else if (config[0].equals("socket") && config[1].equals("gui")) {
                            System.out.println("game starting");
                            //lancia gameflow
                            return;
                        } else if (config[0].equals("socket") && config[1].equals("tui")) {
                            System.out.println("game starting");
                            //lancia game flow
                        } else {
                            System.out.println("Invalid configuration, try again");
                            exit = false;
                        }
                    }
                } while(!exit);
                scanner.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
