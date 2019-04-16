package objets;

/*import java.io.Serializable;
import java.util.ArrayList;

import agents.Agent;
import agents.AgentAction;
import agents.AgentType;*/

import objets.ObjetType;
//import map.Map;
//import map.GameState;


public class Objet {

static int current_Id = 0;
	
	private int x;
	private int y;
	private int id;
	private ObjetType objet;
	
	//private boolean isActive;
	
	
	public Objet(ObjetType obj, int px, int py){
		this.x = px;
		this.y = py;
		this.id = Objet.current_Id;
		current_Id++;
		this.objet = obj;
	}
	
	
	//Récupère la position de l'objet
	public int getObjX() {
		return x;
	}
	
	public int getObjY() {
		return y;
	}

	//Défini la position de l'objet
	public void setObjX(int x) {
		this.x = x;
	}

	public void setObjY(int y) {
		this.y = y;
	}

	//Retourne l'Id de l'objet
	public int getObjId() {
		return id;
	}

	
	public ObjetType getType() {
		return objet;
	}

	public void setType(ObjetType objet) {
		this.objet = objet;
	}

	
/*	public boolean isObjActive() {
		return isActive;
	}

	public void setObjActive(boolean isActive) {
		this.isActive = isActive;
	}*/


	
	
}
