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
                Scanner scanner = new Scanner(System.in);
                boolean exit;
                do {
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
                    System.out.println("Enter the desired configuration type (rmi/socket) followed by (gui/tui):");
                    String input = scanner.nextLine().trim();
                    String[] config = input.split(" ");
                    exit = true;
                    if (config.length != 2) {
                        System.out.println("Insufficient number of arguments");
                        exit = false;
                    }
                    if (exit) {
                        if(config[0].equalsIgnoreCase("rmi") && config[1].equalsIgnoreCase("gui")) {
                            System.out.println("game starting");
                            //lancia gameflow
                        } else if (config[0].equalsIgnoreCase("rmi") && config[1].equalsIgnoreCase("tui")) {
                            GameFlow flow = new GameFlow(new Cli(), new InputUi());
                            flow.setClient(new ClientRMI(flow));
                            flow.run();
                        } else if (config[0].equalsIgnoreCase("socket") && config[1].equalsIgnoreCase("gui")) {
                            System.out.println("game starting");
                            //lancia gameflow
                            return;
                        } else if (config[0].equalsIgnoreCase("socket") && config[1].equalsIgnoreCase("tui")) {
                            System.out.println("game starting");
                            GameFlow flow = new GameFlow(new Cli(), new InputUi());
                            flow.setClient(new ClientSocket(flow));
                            flow.run();
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
