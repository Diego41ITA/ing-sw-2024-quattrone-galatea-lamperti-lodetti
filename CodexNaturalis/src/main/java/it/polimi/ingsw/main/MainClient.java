package it.polimi.ingsw.main;

import it.polimi.ingsw.model.gameDataManager.Game;
import it.polimi.ingsw.networking.rmi.ClientRMI;
import it.polimi.ingsw.networking.socket.client.ClientSocket;
import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.TUI.Cli;
import it.polimi.ingsw.view.input.InputUi;

import java.util.Scanner;
//chiede all'utente che tipo di configurazione vuole:
//1) rmi + gui
//2) rmi + tui
//3) socket + gui
//4) socket + tui
//questo main è il primo a dover essere avviato
public class MainClient {
        public static void main(String[] args) {
            try {
                int selection;
                boolean flag = true;
                Scanner scanner = new Scanner(System.in);
                System.out.println("""
                                            
                                            ██     ██  ███████  ██        ██████   ██████   ███    ███  ███████     ████████  ██████ \s
                                            ██     ██  ██       ██       ██       ██    ██  ████  ████  ██             ██    ██    ██
                                            ██  █  ██  █████    ██       ██       ██    ██  ██ ████ ██  █████          ██    ██    ██
                                            ██ ███ ██  ██       ██       ██       ██    ██  ██  ██  ██  ██             ██    ██    ██
                                             ███ ███   ███████  ███████   ██████   ██████   ██      ██  ███████        ██     ██████
                                                                                                                                     \s
                                               
                         ██████╗  ██████╗  ██████╗  ███████╗ ██╗  ██╗    ███╗   ██╗  █████╗  ████████╗ ██╗   ██╗ ██████╗   █████╗  ██╗      ██╗ ███████╗
                        ██╔════╝ ██╔═══██╗ ██╔══██╗ ██╔════╝ ╚██╗██╔╝    ████╗  ██║ ██╔══██╗ ╚══██╔══╝ ██║   ██║ ██╔══██╗ ██╔══██╗ ██║      ██║ ██╔════╝
                        ██║      ██║   ██║ ██║  ██║ █████╗    ╚███╔╝     ██╔██╗ ██║ ███████║    ██║    ██║   ██║ ██████╔╝ ███████║ ██║      ██║ ███████╗
                        ██║      ██║   ██║ ██║  ██║ ██╔══╝    ██╔██╗     ██║╚██╗██║ ██╔══██║    ██║    ██║   ██║ ██╔══██╗ ██╔══██║ ██║      ██║ ╚════██║
                        ╚██████╗ ╚██████╔╝ ██████╔╝ ███████╗ ██╔╝ ██╗    ██║ ╚████║ ██║  ██║    ██║    ╚██████╔╝ ██║  ██║ ██║  ██║ ███████╗ ██║ ███████║
                         ╚═════╝  ╚═════╝  ╚═════╝  ╚══════╝ ╚═╝  ╚═╝    ╚═╝  ╚═══╝ ╚═╝  ╚═╝    ╚═╝     ╚═════╝  ╚═╝  ╚═╝ ╚═╝  ╚═╝ ╚══════╝ ╚═╝ ╚══════╝
                                        
                                        
                                        
                                        
                                             ██████   ██████    ██████   ██    ██  ██████      ██████    █████   ██████   ███████   ██
                                            ██        ██   ██  ██    ██  ██    ██  ██   ██     ██   ██  ██       ██   ██       ██ ████
                                            ██   ███  ██████   ██    ██  ██    ██  ██████      ██████   ███████  ██████   ███████   ██
                                            ██    ██  ██   ██  ██    ██  ██    ██  ██          ██            ██  ██       ██        ██
                                             ██████   ██   ██   ██████    ██████   ██          ██        █████   ██       ███████   ██
                                
                         
                        """);
                System.out.println("Enter the desired configuration");
                do {
                    System.out.println("""
                        Select option:
                        \t 1- TUI + Socket
                        \t 2- TUI + RMI
                        \t
                        \t 3- GUI + Socket
                        \t 4- GUI + RMI
                        """);
                    String input = scanner.nextLine();
                    try {
                        selection = Integer.parseInt(input);
                        if((selection != 1 && selection != 2 && selection != 3 && selection != 4)){
                            System.out.println("invalid configuration. Try again");
                            flag = false;
                        }else{flag = true;};
                    } catch (NumberFormatException e) {
                        selection = -1;
                        System.out.println("Nan");
                    }
                } while (!flag);

                if(selection == 1) {
                    GameFlow flow = new GameFlow(new Cli(), new InputUi());
                    flow.setClient(new ClientSocket(flow));
                    flow.run();
                } else if (selection == 2) {
                    GameFlow flow = new GameFlow(new Cli(), new InputUi());
                    flow.setClient(new ClientRMI(flow));
                    flow.run();
                } else if (selection == 3) {
                    System.out.println("game starting");
                    //lancia gameflow
                    return;
                } else if (selection == 4) {
                    //
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
