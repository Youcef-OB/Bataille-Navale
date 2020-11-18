package jeu;

public class Bataille {

	Board b1,b2;
	public static int[][]GRID_TO_SOLVE = {
		  { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
		  { 0, 1, 0, 0, 0, 0, 1, 0, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 }
		};
	
	public Bataille() {};
	public Bataille(Board b1,Board b2) {
		this.b1=b1;
		this.b2=b2;
	}
	
	public String end() {
		return "Partie finie";
	}
	
	public static void main(String[] args) {
		
		//Creation de la partie 
		Board b1= new Board();
		Board b2= new Board(GRID_TO_SOLVE);
		Bataille bataille=new Bataille(b1 ,b2);
		System.out.println(b1.affichage());
		//System.out.println(b2.affichage());
		
		//Ajout des bateaux
		Bateau[] bateaux = {new Bateau(0,0,2,0), 
							new Bateau(0,2,0,4),
							new Bateau(3,5,3,7),
							new Bateau(7,4,9,4),
							new Bateau(0,8,0,9)};
		for(Bateau b: bateaux) {
			b1.addBoat(b);
		}
		
		Bateau btest= new Bateau(3,3,3,4);
		
		System.out.println(b1.affichage());

		System.out.println(b1.test(btest));
		
		System.out.println(bateaux[1].trouveType());
		
		System.out.println(b1.hit(0, 8));
		//System.out.println(b1.hit(0, 9));
		System.out.println(b1.affichage());
		
		System.out.println(b1.lose());
		
		System.out.println(b1.batRestant(bateaux));
	}

	

	
}
