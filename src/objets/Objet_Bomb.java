package objets;

import objets.Objet;

public class Objet_Bomb extends Objet {

	private int etat;
	private int id_bbm;
	private int range;
	
	
	public Objet_Bomb(ObjetType obj, int px, int py, int bbm, int rg_bbm) {
		super(obj, px, py);
		this.etat = 0;
		this.id_bbm = bbm;
		this.range = rg_bbm;
	}
	
	public int getEtat() {
		return etat;
	}


	public void setEtat(int etat) {
		this.etat = etat;
	}
	public int getId_bbm() {
		return id_bbm;
	}


	public void setId_bbm(int bbm) {
		this.id_bbm = bbm;
	}

	public int getRange() {
		return range;
	}


	public void setRange(int bbm) {
		this.range = bbm;
	}
}
