package agents;


import strategies.Strategie_Rajion;
import map.GameState;


public class Agent_Rajion extends Agent {

	public Agent_Rajion(int px, int py,int id) {
		super(AgentType.RAJION, px, py);
		super.setId(id);
	}
	
	public AgentAction chooseAction(GameState etatjeu) {
		
		Strategie_Rajion strat = new Strategie_Rajion(etatjeu, this);
		
		return strat.action();
		
	}

}
