package server;

import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
	public static void main(String[] args) {
		try {
			ServerSocket ecoute = new ServerSocket(1500);
			System.out.println("Serveur lancé!");
			while (true) {
				/*
				 * Creer un Thread partie pour chaque couple de client qui se connecte sur le
				 * port 1500
				 */
				Socket client1 = ecoute.accept();
				Socket client2 = ecoute.accept();
				new ThreadJeu(client1, client2).start();
			}
		} catch (Exception e) {
		}

	}
}