package jeu;

public class Bataille {

	Board b1, b2;

	// Grid pour l'exemple
	public static int[][] GRID_TO_SOLVE = { { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 } };

	public Bataille() {
	};

	public Bataille(Board b1, Board b2) {
		this.b1 = b1;
		this.b2 = b2;
	}

	public String end() {
		return "Partie finie";
	}

}
