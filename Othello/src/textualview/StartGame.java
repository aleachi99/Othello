/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textualview;

import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author AlessandroAchille
 */
public class StartGame {
    public static void main (String arg[]){
        System.out.println("Benvenuto in Othello. Stai utilizzando la versione client.");
        System.out.println("Inserisci l'indirizzo IP del server (forma decimale puntata)");
        String ip = new Scanner(System.in).next();
        int port;
        try{
            System.out.println("Inserisci la porta su cui il server è in ascolto: ");
            port = new Scanner(System.in).nextInt();
            
            try{  
                Socket s = new Socket(ip,port);
                new TextualClient(s,ip,port);
                System.out.println("Socket creata!");
            }
            catch(Exception ex){ 
                System.out.println("ECCEZIONE socket inizio connessione \n " + ex); 
            }
        }
        catch(Exception ex){ 
            System.out.println("ECCEZIONE lettura porta \n "+ ex);
        }
    }
}
