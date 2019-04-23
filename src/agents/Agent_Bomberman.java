package agents;

import java.util.ArrayList;

import map.GameState;
import map.Map;
import agents.Agent;

public class Agent_Bomberman extends Agent {
	
	private int range;
	private int points = 0;
	//private ArrayList<Objet_Bomb> bombes = null;
	private int nbBombes;
	private ColorBomberman couleur;
	private int nbActions = 10;
	private int maladie = 10;

	private boolean isInvincible;
	private int etatInvincible;
	
	private boolean isSick;
	private int etatSick;

	public Agent_Bomberman (int px, int py,int id) {
		super(AgentType.BOMBERMAN, px, py);
		super.setId(id);
		this.range = 1;
		this.points = 0;
		//this.bombes = new ArrayList<Objet_Bomb>();
		this.nbBombes = 10;
		
		this.isInvincible = false;
		this.etatInvincible = 0;
		
		this.isSick = false;
		this.etatSick = 0;
		
	}
	
	public AgentAction chooseAction(GameState etatjeu,AgentAction action) 
	{
		if (etatjeu.getMode_jeu() & action != null) {
//			if (etatjeu.isLegalMoveBbm(action, this) || (action.getAction() == 5)) return action;
			
			if (etatjeu.isLegalMoveBbm(action, this) || (action.getAction() == 5)) return action;
			else return new AgentAction(Map.STOP);
			
		}else {
			ArrayList<AgentAction> listAction=new ArrayList<AgentAction>();
			for(int i=0;i<=5;i++)
			{
				if (etatjeu.isLegalMoveBbm(new AgentAction(i), this)) {
					//System.out.println(i);
					listAction.add(new AgentAction(i));
				}
			}		
			nbActions = listAction.size();
			return(listAction.get((int)(Math.random()*nbActions)));
			
			//return(new AgentAction(Map.STOP));
		}
				
	}	
	
	public int getRange() {
		return range;
	}


	public void setRange(int range) {
		this.range = range;
	}
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int point) {
		this.points = point;
	}
	
//	public ArrayList<Objet_Bomb> getBombes() {
//		return bombes;
//	}
//
//	public void setBombes(ArrayList<Objet_Bomb> bombes) {
//		this.bombes = bombes;
//	}
	
	public int getNbBombes() {
		return nbBombes;
	}

	public void setNbBombes(int nbBombes) {
		this.nbBombes = nbBombes;
	}
	
	public ColorBomberman getCouleur() {
		return couleur;
	}

	public void setCouleur(ColorBomberman couleur) {
		this.couleur = couleur;
	}

	public boolean isInvincible() {
		return isInvincible;
	}

	public void setInvincible(boolean isInvincible) {
		this.isInvincible = isInvincible;
	}

	public int getEtatInv() {
		return etatInvincible;
	}

	public void setEtatInv(int etatInvincible) {
		this.etatInvincible = etatInvincible;
	}

	public boolean isSick() {
		return isSick;
	}

	public void setSick(boolean isSick) {
		this.isSick = isSick;
	}

	public int getEtatSick() {
		return etatSick;
	}

	public void setEtatSick(int etatSick) {
		this.etatSick = etatSick;
	}
	
	public int getNbActions() {
		return nbActions;
	}


	public void setNbActions(int actions) {
		this.nbActions = actions;
	}
	
	public int getMaladie() {
		return maladie;
	}


	public void setMaladie(int mal) {
		this.maladie = mal;
	}
}
