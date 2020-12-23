package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import jeu.Bataille;
import jeu.Bateau;
import jeu.Board;

public class ThreadJeu extends Thread {
	BufferedReader in1;
	BufferedReader in2;
	PrintWriter out1;
	PrintWriter out2;
	Board board1, board2;
	Bataille partie;
	Bateau[] BateauxCreeParJ1, BateauxCreeParJ2;

	// Ce tableau est un tableau d'exemple d'entrée de coordonées
	Bateau[] TEST = { new Bateau(0, 0, 2, 0), new Bateau(0, 2, 0, 4), new Bateau(3, 5, 3, 7), new Bateau(7, 4, 9, 4),
			new Bateau(0, 8, 0, 9) };

	public ThreadJeu(Socket client1, Socket client2) {
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

			/*
			 * Voici les 2 commandes à mettre si on veut que chaque joueur rentre ses
			 * coordonnées, pour l'exemple je les ai mis en commentaires
			 * 
			 * BateauxCreeParJ1 = ListDesBateauxJoueur(in1);
			 * 
			 * BateauxCreeParJ2 = ListDesBateauxJoueur(in2);
			 * 
			 * InitDuBoardComplet(board1, BateauxCreeParJ1);
			 * 
			 * InitDuBoardComplet(board2, BateauxCreeParJ2 );
			 */

			// On initialise les 2 boards
			InitDuBoardComplet(board1, TEST);
			InitDuBoardComplet(board2, TEST);

			out1.println(board1.affichage());
			out2.println(board2.affichage());
		} catch (Exception e) {
		}
	}

	public void run() {
		try {
			while (true) {

				ResultatFrappe(out1, in1, board2);// Le joueur 1 choisit une case à detruire du board du joueur 2
				/*
				 * Cette boucle permet d'arreter la partie ,c'est à dire le thread, si il n'y a
				 * plus de bateaux restants.
				 */
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

	/*
	 * Cette méthode creér un tableau de bateau en fonction des coordonnées mis en
	 * entrer par le joueur. Elle instancie 5 bateaux en faisant appelle à la
	 * méthode InstancieUnBateau(in) 5 fois.
	 */
	public Bateau[] ListDesBateauxJoueur(BufferedReader in) {
		Bateau[] bateaux = new Bateau[5];
		for (int i = 0; i < 5; i++) {
			bateaux[i] = InstancieUnBateau(in);
		}
		return bateaux;
	}

	/*
	 * Cette méthode prend en entrée le board du joueur concerné ainsi que sa liste
	 * de bateaux.
	 * 
	 * Elle permet d'initialiser le Board du Joueur en fonction des bateaux mis en
	 * entrer
	 * 
	 */
	public void InitDuBoardComplet(Board board, Bateau[] bateaux) {

		/*
		 * Pour chaque bateau présent dans le tableau on teste si il est possible de le
		 * rajouter
		 */
		for (int i = 0; i < 5; i++) {
			if (!board.addBoat(bateaux[i])) { // la méthode addBoat fait un test avant d'ajouter le bateau
				throw new RuntimeException("Initialisation raté à cause du bateau" + i + "Veuillez réessayer");
			}
			board.addBoat(bateaux[i]);

		}
	}

	/*
	 * Cette méthode permet de tester si le point mis en argument est compris entre
	 * 0 et 9. Cela permet de facilité le traitement de la donnée par le programme.
	 */
	public static boolean testCoord(int l, int c) {

		if (l < 10 && l >= 0) {
			return true;
		}
		if (c < 10 && c >= 0) {
			return true;
		}
		return false;
	}

	/*
	 * Cette méthode permet d'instancier un Bateau à partir de l'input d'un joueur
	 * grace à la méthode getcoord
	 */
	public Bateau InstancieUnBateau(BufferedReader in) {

		int[] point1 = getcoord(in);
		int[] point2 = getcoord(in);

		int l1 = point1[0], c1 = point1[1], l2 = point2[0], c2 = point2[1];

		Bateau Boat = new Bateau(l1, c1, l2, c2);
		return Boat;

	}

	/*
	 * Cette méthode permet de vérifier que l'input du Joueur est exactement de la
	 * forme (x;y) avec x et y dans l'intervalle [0,9] renvoie une exeption sinon.
	 * Si le test est passé on affecte l'input au coordonnées.
	 */
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

	/*
	 * Cette méthode renvoie le resultat de la frappe au joueur
	 */
	public static void ResultatFrappe(PrintWriter agresseurO, BufferedReader agresseurI, Board victime) {
		agresseurO.println("Veuillez entrer les coordonnées a frapper :");
		int[] instruction = getcoord(agresseurI);
		String res = victime.hit(instruction[0], instruction[1]);
		agresseurO.println(res);
	}
}
