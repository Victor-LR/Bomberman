package agents;

import java.util.ArrayList;


import map.GameState;
import map.Map;
import strategies.Strategie;
import strategies.Strategie_A;
import strategies.Strategie_A_PVP;
import strategies.Strategie_A_items;
import strategies.Strategie_B;
import strategies.Strategie_C;
import strategies.Strategie_D;
import strategies.Strategie_PVE;
import strategies.Strategie_PVP;
import agents.Agent;

public class Agent_Bomberman extends Agent {
	
	private static final long serialVersionUID = 1L;
	private int range;
	private int points = 0;
	private int nbBombes;
	
	private ColorBomberman couleur;
	private int nbActions = 10;
	private int maladie = 10;
	private int nb_murs = 0;
	private int nb_bonus = 0;
	private boolean axe_bombe = false;


	private boolean isInvincible;
	private int etatInvincible;
	
	private boolean isSick;
	private int etatSick;
	
	private int strat = -1;
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
	

	//renvoi une action a effectué en fonction du comportement du Bomberman
	public AgentAction chooseAction(GameState etatjeu) 
	{
		AgentAction action ;
		switch (this.strat){
			
		//Controllé par le joueur
		case 1:
			action = etatjeu.getKey_action().getKaction();
			if (etatjeu.isLegalMoveBbm(action, this) || (action.getAction() == 5)){
				//etatjeu.getKey_action().setKaction(new AgentAction(Map.STOP));
				return action;
			}
			else return new AgentAction(Map.STOP);
		
		case 2:
			action = etatjeu.getKey_action_2().getKaction();
			if (etatjeu.isLegalMoveBbm(action, this) || (action.getAction() == 5)){
				//etatjeu.getKey_action_2().setKaction(new AgentAction(Map.STOP));
				return action;
			}
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
		case 9:
			this.strategie = new Strategie_D(etatjeu,this);
			return this.strategie.action();

		case 10:
			this.strategie = new Strategie_A_PVP(etatjeu,this);
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
	
	//getteur et setteur de la stratégie du bomberman
	public Strategie getStrategie() {
		return strategie;
	}

	public int getStrat() {
		return strat;
	}

	public void setStrat(int strat) {
		this.strat = strat;
	}
	
	//getteurs et setteurs des différentes informations liées au bombes du Bomberman
	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}
	
	public int getNbBombes() {
		return nbBombes;
	}
	
	public void setNbBombes(int nbBombes) {
		this.nbBombes = nbBombes;
	}
	
	// getteur et setteur du nombre d'action que peut effectuer un Bomberman
	public int getNbActions() {
		return nbActions;
	}

	public void setNbActions(int actions) {
		this.nbActions = actions;
	}
	
	//getteurs et setteurs de la couleur et des points du Bomberman
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int point) {
		this.points = point;
	}
	
	public ColorBomberman getCouleur() {
		return couleur;
	}

	public void setCouleur(ColorBomberman couleur) {
		this.couleur = couleur;
	}

	//getteurs et setteurs des différents états que peut avoir le Bomberman
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

	public int getMaladie() {
		return maladie;
	}

	public void setMaladie(int mal) {
		this.maladie = mal;
	}

	public int getNb_murs() {
		return nb_murs;
	}

	public void setNb_murs(int nb_murs) {
		this.nb_murs = nb_murs;
	}


	public int getNb_bonus() {
		return nb_bonus;
	}


	public void setNb_bonus(int nb_bonus) {
		this.nb_bonus = nb_bonus;
	}


	public boolean isAxe_bombe() {
		return axe_bombe;
	}


	public void setAxe_bombe(boolean axe_bombe) {
		this.axe_bombe = axe_bombe;
	}
}
