package jeu;

public class Board {

	int[][] board;

	// Creer une grille vide
	public Board() {
		board = new int[10][10];
	}

	public Board(int[][] board) {
		this.board = board;
	}

	// Affiche la grille actuel
	public String affichage() {
		String res = "";
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				res += board[i][j] + " ";
			}
			res += "\n";
		}
		return res;
	}

	// Verifie si le point (x,y) de la grille est vide
	public boolean emptyCase(int l, int c) {
		if (board[l][c] != 0) {
			return false;
		}
		return true;
	}

	// Methode qui permet de couler un bateau
	public String hit(int l, int c) {
		if (emptyCase(l, c)) {
			return "La case (" + (l) + "," + (c) + ") est vide";
		}
		board[l][c] = 0;
		return "La case (" + (l) + "," + (c) + ") viens d'être détruite";
	}

	/*
	 * Permet d'ajouter un élément Bateau qui vérifie le test
	 * 
	 * Parcours la grille sur le segment indiqué et change la valeur de la case à 1
	 * 
	 * Renvoie true si le bateau est créer, false sinon
	 */
	public boolean addBoat(Bateau b) {
		if (this.test(b)) {
			for (int i = b.l1; i <= b.longueur + b.l1; i++) {
				board[i][b.c1] = 1;
			}
			for (int i = b.c1; i <= b.largeur + b.c1; i++) {
				board[b.l1][i] = 1;
			}
			return true;// "Bateau ajouté";
		}
		;
		return false;// "Erreur sur ce bateau"

	}

	/*
	 * Verifie si le segment du bateau existe encore ou non
	 * 
	 * Parcours le segment du bateau et retourne true si toutes les cases sont vides
	 */
	public boolean existe(Bateau b) {
		if (b.largeur == 0) {
			for (int i = b.l1; i <= b.longueur + b.l1; i++) {
				if (board[i][b.c1] == 1)
					return true;
			}
		}
		if (b.longueur == 0) {
			for (int i = b.c1; i <= b.largeur + b.c1; i++) {
				if (board[b.l1][i] == 1)
					return true;
			}
		}

		return false;

	}

	// Methode qui retourne le nombre de bateaux restants
	public int batRestant(Bateau[] bat) {
		int c = 5;
		for (int i = 0; i < 5; i++) {
			if (!existe(bat[i])) {
				c--;
			}
		}
		return c;
	}

	/*
	 * Methode qui verifie si le segment du bateau croisent sur le segment d'un
	 * bateau existant
	 * 
	 * Cette méthode vérifie aussi que le segment n'est pas adjacent à un autre
	 * segment
	 */
	public boolean test(Bateau b) {
		// test pour les lignes :
		if (b.longueur < 5 && b.longueur > 0 && b.largeur == 0) {
			for (int i = b.l1 - 1; i <= b.longueur + b.l1 + 1; i++) {
				/* on parcourt la boucle à 1 ensuite 0 ensuite encore 1 jusqu'à i+1 */
				if (i == 10)
					break;
				for (int j = b.c1 - 1; j <= b.c1 + 1; j++) {
					if (j == 10)
						break;
					if (board[Math.abs(i)][Math.abs(j)] == 1) {
						return false;
					}
				}
			}
			return true;
		}
		// test pour les colonnes :
		if (b.largeur < 5 && b.largeur > 0 && b.longueur == 0) {
			for (int j = b.c1 - 1; j <= b.largeur + b.c1 + 1; j++) {
				/* on parcourt la boucle à 1 ensuite 0 ensuite encore 1 jusqu'à i+1 */
				if (j == 10)
					break;
				for (int i = b.l1 - 1; i <= b.l1 + 1; i++) {
					if (i == 10)
						break;
					if (board[Math.abs(i)][Math.abs(j)] == 1) {
						return false;
					}
				}
			}
			return true;
		}

		return false;
	}
}
