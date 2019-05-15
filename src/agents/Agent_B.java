package agents;

import map.GameState;

public interface Agent_B {
	public AgentAction chooseAction(GameState state);

	public int getId();

}
