package agents;

import map.GameState;
import map.Map;

public class Agent_Tower extends Agent {
	public Agent_Tower (int px, int py,int id) {
		super(AgentType.TOWER, px, py);
		super.setId(id);
	}
	
	public AgentAction chooseAction(GameState etatjeu) {
		return new AgentAction(Map.STOP);
	}

}