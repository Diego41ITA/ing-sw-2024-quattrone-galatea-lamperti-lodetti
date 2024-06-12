package it.polimi.ingsw.main;

import it.polimi.ingsw.networking.rmi.ClientRMI;
import it.polimi.ingsw.networking.rmi.ServerRMI;
import it.polimi.ingsw.networking.socket.client.ClientSocket;
import it.polimi.ingsw.networking.socket.server.ServerS;
import it.polimi.ingsw.view.GUI.Gui;
import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.TUI.Cli;
import it.polimi.ingsw.view.input.InputGui;
import it.polimi.ingsw.view.input.InputUi;
import javafx.application.Application;

import java.util.Scanner;

import static it.polimi.ingsw.main.IpValidator.containsOneOrTwoAndHasLengthOne;
import static it.polimi.ingsw.main.IpValidator.verifyIpAddress;

/**
 * this class starts the client and asks him which configuration he wants:
 * 1) socket + tui
 * 2) rmi + tui
 * 3) socket + gui
 * 4) rmi + tui
 */
// ********IMPORTANT leave this class as it is built*******
public class Main {
        public static void main(String[] args) {
            try {
                int selection;
                boolean flag = true;
                Scanner scanner = new Scanner(System.in);
                String type;
                do {
                    System.out.println("Please enter if you are a client or a server");
                    System.out.println("1. SERVER");
                    System.out.println("2. CLIENT");
                    type = scanner.nextLine();
                    if (type.equals("1")) {
                        try {
                            ServerRMI.startServer();

                            ServerS socket = new ServerS();
                            socket.startConnection(50000);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if(!containsOneOrTwoAndHasLengthOne(type)){
                        System.out.println("Invalid input, please enter 1 or 2");
                    }
                } while (!containsOneOrTwoAndHasLengthOne(type));
                String ip;
                do {
                    System.out.println("Write the Ip address:\n if you want to use the local host write \"localhost\"");
                    ip = scanner.nextLine();
                    if(!verifyIpAddress(ip)){System.out.println("Invalid Ip address, try again");}
                } while (!verifyIpAddress(ip));
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
                        if ((selection != 1 && selection != 2 && selection != 3 && selection != 4)) {
                            System.out.println("invalid configuration. Try again");
                            flag = false;
                        } else {
                            flag = true;
                        }
                        ;
                    } catch (NumberFormatException e) {
                        selection = -1;
                    }
                } while (!flag);

                if (selection == 1) {
                    FsmGame flow = new FsmGame(new Cli(), new InputUi());
                    flow.setClient(new ClientSocket(flow, ip));
                    flow.run(); //in this way the main class doesn't end until run() is finished
                } else if (selection == 2) {
                    FsmGame flow = new FsmGame(new Cli(), new InputUi());
                    flow.setClient(new ClientRMI(flow, ip));
                    flow.run();
                } else if (selection == 3) {
                    /*FsmGame flow = new FsmGame(new Gui(), new InputGui());
                    flow.setClient(new ClientSocket(flow, ip));
                    flow.run();*/
                    String[] arg = new String[2];
                    arg[0] = "socket";
                    arg[1] = ip;
                    Application.launch(Gui.class,arg);
                } else if (selection == 4) {
                    /*FsmGame flow = new FsmGame(new Gui(), new InputGui());
                    flow.setClient(new ClientRMI(flow, ip));
                    flow.run();*/
                    String[] arg = new String[2];
                    arg[0] = "rmi";
                    arg[1] = ip;
                    Application.launch(Gui.class, arg);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
