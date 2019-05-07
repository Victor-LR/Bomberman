package agents;

import map.GameState;
import map.Map;

public class Agent_Ennemy extends Agent {

	public Agent_Ennemy (int px, int py,int id) {
		super(AgentType.ENNEMY, px, py);
		super.setId(id);
	}
	
//	public AgentAction chooseAction(GameState etatjeu) {
//		return new AgentAction(Map.STOP);
//	}
	
}
