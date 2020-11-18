package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import jeu.Bataille;
import jeu.Bateau;
import jeu.Board;

public class ThreadChat extends Thread {
	BufferedReader in1;
	BufferedReader in2;
	PrintWriter out1;
	PrintWriter out2;
	Board board1, board2;
	Bataille partie;
	Bateau[] BateauxCreeParJ1, BateauxCreeParJ2;

	Bateau[] TEST = { new Bateau(0, 0, 2, 0), new Bateau(0, 2, 0, 4), new Bateau(3, 5, 3, 7), new Bateau(7, 4, 9, 4),
			new Bateau(0, 8, 0, 9) };

	public ThreadChat(Socket client1, Socket client2) {
		try {
			in1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
			in2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
			out1 = new PrintWriter(client1.getOutputStream(), true);
			out2 = new PrintWriter(client2.getOutputStream(), true);
			out1.println("Joueur numero=" + 1 + "\n");
			out2.println("Joueur numero=" + 2 + "\n");

			board1 = new Board();
			board2 = new Board();
			partie = new Bataille(board1, board2);

			// BateauxCreeParJ1 = ListDesBateauxJoueur(in1);
			// BateauxCreeParJ2 = ListDesBateauxJoueur(in2);

			InitDuBoardComplet(board1, in1, TEST);
			InitDuBoardComplet(board2, in2, TEST);

			out1.println(board1.affichage());
			out2.println(board2.affichage());
		} catch (Exception e) {
		}
	}

	public void run() {
		try {
			while (true) {

				ResultatFrappe(out1, in1, board2);// Le joueur 1 choisit une case à detruire
				if (board2.batRestant(TEST) == 0) {
					out2.println("Vous avez perdu");
					out1.println("Vous avez gagné");
					interrupt();
				} else
					out1.println(board2.batRestant(TEST));
				out2.println(board2.affichage());// Le serveur affiche au joueur2 son board apres destruction

				ResultatFrappe(out2, in2, board1);
				if (board1.batRestant(TEST) == 0) {
					out1.println("Vous avez perdu");
					out2.println("Vous avez gagné");
					interrupt();
				} else
					out1.println(board2.batRestant(TEST));
				out2.println(board1.batRestant(TEST));
				out1.println(board1.affichage());

				partie.end();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Bateau[] ListDesBateauxJoueur(BufferedReader in) {
		Bateau[] bateaux = new Bateau[5];
		for (int i = 0; i < 5; i++) {
			bateaux[i] = InstancieUnBateau(in);
		}
		return bateaux;
	}

	public void InitDuBoardComplet(Board board, BufferedReader in, Bateau[] bateaux) {

		for (int i = 0; i < 5; i++) { // on teste d'abord on envoie ensuite
			if (!board.addBoat(bateaux[i])) {
				throw new RuntimeException("Initialisation raté à cause du bateau" + i + "Veuillez réessayer");
			}
			board.addBoat(bateaux[i]);

		}
	}

	public static boolean testCoord(int l, int c) {

		if (l < 10 && l >= 0) {
			return true;
		}
		if (c < 10 && c >= 0) {
			return true;
		}
		return false;
	}

	public Bateau InstancieUnBateau(BufferedReader in) {

		int[] point1 = getcoord(in);
		int[] point2 = getcoord(in);

		int l1 = point1[0], c1 = point1[1], l2 = point2[0], c2 = point2[1];

		Bateau Boat = new Bateau(l1, c1, l2, c2);
		return Boat;

	}

	public static int[] getcoord(BufferedReader in) {
		String msg;
		int[] coord = new int[2];
		int l = Integer.MAX_VALUE, c = Integer.MAX_VALUE;
		try {
			msg = in.readLine();
			if (msg.length() != 5) {
				throw new RuntimeException(
						"Veuillez respecter le format exacte .d'ajout des coordonnées suivants : (ligne,colonne)");
			}
			l = Character.getNumericValue(msg.charAt(1));
			c = Character.getNumericValue(msg.charAt(3));
			if (!testCoord(l, c)) {
				throw new RuntimeException("Vos dernieres coordonnées sont fausses (Mettez des valeurs entre 0 et 9)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		coord[0] = l;
		coord[1] = c;
		return coord;
	}

	public static void ResultatFrappe(PrintWriter agresseurO, BufferedReader agresseurI, Board victime) {
		agresseurO.println("Veuillez entrer les coordonnées a frapper :");
		int[] instruction = getcoord(agresseurI);
		String res = victime.hit(instruction[0], instruction[1]);
		agresseurO.println(res);
	}
}
