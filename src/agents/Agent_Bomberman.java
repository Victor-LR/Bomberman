package agents;

import java.io.File;
import java.util.ArrayList;

import key.Keys;
import key.Keys_2;

import map.GameState;
import map.Map;
import strategies.Strategie;
import strategies.Strategie_A;
import strategies.Strategie_A_items;
import strategies.Strategie_B;
import strategies.Strategie_C;
import strategies.Strategie_PVE;
import strategies.Strategie_PVP;
import agents.Agent;

public class Agent_Bomberman extends Agent {
	
	private int range;
	private int points = 0;
	private int nbBombes;
	private ColorBomberman couleur;
	private int nbActions = 10;
	private int maladie = 10;

	private boolean isInvincible;
	private int etatInvincible;
	
	private boolean isSick;
	private int etatSick;
	
	private int strat = 10;
	private Strategie strategie;

	public Agent_Bomberman (int px, int py,int id) {
		super(AgentType.BOMBERMAN, px, py);
		super.setId(id);
		
		this.range = 1;
		this.points = 0;
		this.nbBombes = 1;
		
		this.isInvincible = false;
		this.etatInvincible = 0;
		
		this.isSick = false;
		this.etatSick = 0;
		
	}
	


	public AgentAction chooseAction(GameState etatjeu) 
	{
		AgentAction action ;
		switch (this.strat){
			
		//Controllé par le joueur
		case 1:
			action = etatjeu.getKey_action().getKaction();
			if (etatjeu.isLegalMoveBbm(action, this) || (action.getAction() == 5)) return action;
			else return new AgentAction(Map.STOP);
		
		case 2:
			action = etatjeu.getKey_action_2().getKaction();
			if (etatjeu.isLegalMoveBbm(action, this) || (action.getAction() == 5)) return action;
			else return new AgentAction(Map.STOP);
		
		//Action déterminé par une stratégie
		case 3:
			this.strategie = new Strategie_A_items(etatjeu,this);
			return this.strategie.action();
		case 4:
			this.strategie = new Strategie_A(etatjeu,this);
			return this.strategie.action();
		case 5:
			this.strategie = new Strategie_B(etatjeu,this);
			return this.strategie.action();
		case 6:
			this.strategie = new Strategie_C(etatjeu,this);
			return this.strategie.action();
		case 7:
			this.strategie = new Strategie_PVE(etatjeu,this);
			return this.strategie.action();
		case 8:
			this.strategie = new Strategie_PVP(etatjeu,this);
			return this.strategie.action();
			
		//Comportement aléatoire
		case 0:
			this.strategie = null;
			ArrayList<AgentAction> listAction=new ArrayList<AgentAction>();
			for(int i=0;i<=5;i++)
			{
				if (etatjeu.isLegalMoveBbm(new AgentAction(i), this)) {
					//System.out.println(i);
					listAction.add(new AgentAction(i));
				}
			}		
			this.nbActions = listAction.size();
			if(nbActions == 0) return  new AgentAction(Map.STOP);
			else return(listAction.get((int)(Math.random()*this.nbActions)));
		default:
			return new AgentAction(Map.STOP);
		}
		
		
				
	}	
	
	public Strategie getStrategie() {
		return strategie;
	}

	public int getStrat() {
		return strat;
	}

	public void setStrat(int strat) {
		this.strat = strat;
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
