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

import java.net.SocketException;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

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
                            String ipServer;
                            do {
                                System.out.println("Write the Server Ip address:\n if you want to use the local host write \"localhost\"");
                                ipServer = scanner.nextLine();
                                if (!verifyIpAddress(ipServer)) {
                                    System.out.println("Invalid Ip address, try again");
                                }
                            } while (!verifyIpAddress(ipServer));

                            System.setProperty("java.rmi.server.hostname", ipServer);

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
                String ipServer;
                do {
                    System.out.println("Write the Server Ip address:\n if you want to use the local host write \"localhost\"");
                    ipServer = scanner.nextLine();
                    if(!verifyIpAddress(ipServer)){System.out.println("Invalid Ip address, try again");}
                } while (!verifyIpAddress(ipServer));
                System.setProperty("java.rmi.server.hostname", ipServer);
                System.out.println("Write the Client Ip address:\n if you want to play locally leave it empty");
                String ipClient = scanner.nextLine();
                System.setProperty("java.rmi.server.hostname", ipClient);
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

                //I create an instance of executor service to manage the synchronization of all the threads.
                if (selection == 1) {

                    //Thread threadFsmGame;

                    FsmGame flow = new FsmGame(new Cli(), new InputUi());
                    flow.setClient(new ClientSocket(flow, ipServer));

                    /*threadFsmGame = new Thread(flow);
                    threadFsmGame.start();
                    */
                    flow.start();

                    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
                        @Override
                        public void uncaughtException(Thread t, Throwable e){
                            System.out.println("connection to server Socket lost");
                            //threadFsmGame.interrupt();
                            flow.interrupt();
                        }
                    });

                    //threadFsmGame.join();
                    try{
                        flow.join();
                    }catch(InterruptedException e){
                        System.out.println("join() is interrupted");
                    }
                    Thread.getAllStackTraces().keySet().forEach(Thread::interrupt);

                } else if (selection == 2) {

                    //Thread threadFsmGame;

                    FsmGame flow = new FsmGame(new Cli(), new InputUi());
                    flow.setClient(new ClientRMI(flow, ipServer));

                    //threadFsmGame = new Thread(flow);
                    //threadFsmGame.start();
                    flow.start();
                    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
                        @Override
                        public void uncaughtException(Thread t, Throwable e){
                            System.out.println("connection to server RMI lost");
                            //System.out.println("you are going to be disconnected, please wait some moment and try to " +
                            //        "restore you game!!!");
                            //threadFsmGame.interrupt();
                            e.printStackTrace();
                            flow.interrupt();
                        }
                    });
                    //threadFsmGame.join();
                    try{
                        flow.join();
                    }catch(InterruptedException e){
                        System.out.println("join() is interrupted");
                    }
                    Thread.getAllStackTraces().keySet().forEach(Thread::interrupt);

                } else if (selection == 3) {
                    /*FsmGame flow = new FsmGame(new Gui(), new InputGui());
                    flow.setClient(new ClientSocket(flow, ip));
                    flow.run();*/
                    String[] arg = new String[2];
                    arg[0] = "socket";
                    arg[1] = ipServer;
                    Application.launch(Gui.class,arg);

                } else if (selection == 4) {
                    /*FsmGame flow = new FsmGame(new Gui(), new InputGui());
                    flow.setClient(new ClientRMI(flow, ip));
                    flow.run();*/
                    String[] arg = new String[2];
                    arg[0] = "rmi";
                    arg[1] = ipServer;
                    Application.launch(Gui.class, arg);
                    //Thread.getAllStackTraces().keySet().forEach(Thread::interrupt);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("application closing... Application closed");
            System.exit(0);
        }

}
