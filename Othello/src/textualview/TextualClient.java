/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textualview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import model.Scacchiera;

/**
 *
 * @author AlessandroAchille
 */
public class TextualClient {
     private PrintStream serverOutput; //l'oggetto per scrivere sulla socket
     private BufferedReader serverInput;// l'oggetto per leggere dalla socekt
     private String idClient; 
     private Scacchiera campo;
     int cordX;
     int cordY;
    /**
     * costruttore per stampare le informazioni della connessione e salvare quest'ultime nella classe del model.
     * @param s Socket della connessione stabilita
     * @param ip Stringa della porta della connessione
     * @param port Int della porta della connessione
     */
    public TextualClient(Socket s, String ip, int port){
        campo= new Scacchiera();
        try{
            serverInput = new BufferedReader (new InputStreamReader(s.getInputStream()));
            serverOutput = new PrintStream(s.getOutputStream());
        } catch (IOException exc){
            System.out.println(exc);
        }
        startGame();
    }

    
    /**
     * metodo per iniziare il gioco
     */
    private void startGame(){
        try { 
                String messaggioServer;  
                do{
                    messaggioServer=serverInput.readLine();
                    if (messaggioServer.startsWith("start: ")){
                        idClient=messaggioServer.substring(messaggioServer.length()-3, messaggioServer.length()-2);
                        System.out.println("Connesso al server. Sono il giocatore "+idClient);
                    }
                } while (!(idClient.equals("B")||idClient.equals("N")));
                
                do{
                    messaggioServer=serverInput.readLine();
                    System.out.println("ho letto");
                    System.out.println(messaggioServer);
                    if (!(messaggioServer.startsWith("end: "))){
                        if(messaggioServer.equals("round: <"+idClient+">;")){    
                            do{

                                    System.out.println("Inserisci le coordinate della prossima mossa");
                                    cordX = new Scanner(System.in).nextInt();
                                    cordY = new Scanner(System.in).nextInt();
                                    serverOutput.println("place: <"+cordX+">, <"+cordY+">;");
                                    messaggioServer=serverInput.readLine();
                                    System.out.println(messaggioServer);
                                    if (messaggioServer.equals("move not valid;")){
                                        messaggioServer=serverInput.readLine();
                                        System.out.println(messaggioServer);
                                    }

                            }while (!messaggioServer.startsWith("update: "));
                                int cordX = Integer.parseInt(messaggioServer.substring(14, 15));
                                int cordY = Integer.parseInt(messaggioServer.substring(19, 20));
                                String col = messaggioServer.substring(9, 10);
                                campo.posizionaNuovaCasella(cordX, cordY, col);
                                campo.print();
                        } else{
                            do{
                               System.out.println("Non è il mio turno.. aspetto");
                               messaggioServer=serverInput.readLine();
                               if (messaggioServer.startsWith("update: ")){
                                    int cordX = Integer.parseInt(messaggioServer.substring(14, 15));
                                    int cordY = Integer.parseInt(messaggioServer.substring(19, 20));
                                    System.out.println (messaggioServer.substring(9,10));
                                    String col = messaggioServer.substring(9,10);
                                    campo.posizionaNuovaCasella (cordX, cordY, col);
                                    campo.print();
                                    System.out.println (messaggioServer);
                               } else{
                                System.out.println(messaggioServer);
                              }
                            }while (messaggioServer.startsWith("update: ")==false);
                        }
                    } else{
                        System.out.println ("Partita Finita!");
                        System.out.println (messaggioServer);
                    }
                }while (messaggioServer.startsWith("end: ")==false); //Controlla se la partita è ancora aperta
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
