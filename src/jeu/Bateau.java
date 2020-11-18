package jeu;

import java.util.HashMap;

public class Bateau {

	int largeur,longueur;
	int l1,c1,l2,c2;
	String type;
	
	
	public Bateau() {
		this.l1=0;
		this.l2=0;
		this.c1=0;
		this.c2=0;
		this.largeur=0;
		this.longueur=0;
	}
	
	public Bateau(int l1,int c1, int l2, int c2) {
		this.l1=l1;
		this.l2=l2;
		this.c1=c1;
		this.c2=c2;
		this.largeur=(Math.abs(c1-c2));
		this.longueur=(Math.abs(l1-l2));
		this.type=trouveType();
	}
	

	public String trouveType() {
		String res="Type inconnu";
		HashMap<Integer,String> typeBateaux=new HashMap<>();

		typeBateaux.put(2,"Torpilleur");
	 	typeBateaux.put(3,"Contre-torpilleurs");
	 	typeBateaux.put(4,"Croiseur");
	 	typeBateaux.put(5,"Porte-avions");
	 	
	 	if(largeur==0) {
	 		if(longueur>0 && longueur<5) {
	 			res=typeBateaux.get(longueur+1);
	 		}return res;
	 	}
	 	if(longueur==0) {
	 		if(largeur>0 && largeur<5) {
	 			res=typeBateaux.get(largeur+1);
	 		}return res;
	 	}
		return res;
	}
	
	
}
