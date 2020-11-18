package client;
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class MainClient {
	public static void main(String[] args) {
		try {
			Socket s = new Socket("127.0.0.1", 1500);
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			new ListeningThread(s).start(); // on veux pas que le programme soit pas bloquer par un input venant du serveur
			// start lance la methode run du thread une seule fois 
			Scanner sc= new Scanner(System.in);
			String msg="";
            while (msg!="fin") { //permet d'ecouter l'entree du scanner tout le temps
                msg=sc.nextLine();
                out.println(msg);
            }// l'entrée est bloquante alors que la sortie non
            
            
			sc.close();
			s.close();
			} catch(Exception e) {
			}
	}
}
