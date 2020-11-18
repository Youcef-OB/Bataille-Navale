package jeu;

public class Board {

	int[][] board;

	public Board() {
		board = new int[10][10];
	}

	public Board(int[][] board) {
		this.board = board;
	}

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

	public boolean emptyCase(int l, int c) {
		if (board[l][c] != 0) {
			return false;
		}
		return true;
	}

	/*public String lose() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (emptyCase(i, j) == false) {
					return "La partie est tjrs en cours";
					// return false;
				}

			}
		}
		return "Vous avez perdu la partie";
		// return true;
	}*/

	public String hit(int l, int c) {
		if (emptyCase(l, c)) {
			return "La case (" + (l) + "," + (c) + ") est vide";
		}
		board[l][c] = 0;
		return "La case (" + (l) + "," + (c) + ") viens d'être détruite";
	}

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

	public boolean existe(Bateau b) { // Verifie si la segment du bateau existe encore ou non
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

	public int batRestant(Bateau[] bat) {
		int c = 5;
		for (int i = 0; i < 5; i++) {
			if (!existe(bat[i])) {
				c--;
			}
		}
		return c;
	}

	// Methode qui verifie si les coordonénes du bateau passe ou sont adjacentes à
	// des coords existentes
	public boolean test(Bateau b) {
		// test pour les lignes :
		if (b.longueur < 5 && b.longueur > 0 && b.largeur == 0) {
			for (int i = b.l1 - 1; i <= b.longueur + b.l1 + 1; i++) {
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
			for (int j = b.c1 - 1; j <= b.largeur + b.c1 + 1; j++) {// on parcourt la boucle à 1 ensuite 0 ensuite
																	// encore 1 jusqu'à i+1
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
