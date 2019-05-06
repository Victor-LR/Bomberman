package agents;

import map.GameState;
import map.Map;

public class Agent_Bird extends Agent {

	public Agent_Bird (int px, int py,int id) {
		super(AgentType.BIRD, px, py);
		super.setId(id);
	}
	
	public AgentAction chooseAction(GameState etatjeu) {
		return new AgentAction(Map.STOP);
	}
	
}
