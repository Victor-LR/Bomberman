package agents;

import java.util.ArrayList;

import map.GameState;
import map.Map;
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
		
//		Strategie_Bird str = new Strategie_Bird(etatjeu, this);
//		
//		return str.action();
////		ArrayList<AgentAction> listAction=new ArrayList<AgentAction>();
////		for(int i=0;i<5;i++)
////		{
////			if (etatjeu.isLegalMoveBird(new AgentAction(i), this))
////				listAction.add(new AgentAction(i));
////		}		
////		return(listAction.get((int)(Math.random()*listAction.size())));
		return new AgentAction(Map.STOP_BIRD);
	}
	
	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}
}
