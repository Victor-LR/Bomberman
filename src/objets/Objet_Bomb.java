package objets;

import objets.Objet;

public class Objet_Bomb extends Objet {

	private int etat;
	
	
	public Objet_Bomb(ObjetType obj, int px, int py) {
		super(obj, px, py);
		this.etat = 0;
		
	}
	
	public int getEtat() {
		return etat;
	}


	public void setEtat(int etat) {
		this.etat = etat;
	}
}
