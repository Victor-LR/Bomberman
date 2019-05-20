package agents;


import map.GameState;
import strategies.Strategie_Bird;

public class Agent_Bird extends Agent {
	
	private int etat;

	public Agent_Bird (int px, int py,int id) {
		super(AgentType.BIRD, px, py);
		super.setId(id);
		this.etat = 0;
	}
	
	

	public AgentAction chooseAction(GameState etatjeu) 
	{
		
		Strategie_Bird str = new Strategie_Bird(etatjeu, this);	
		return str.action();
	}
	
	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}
}
