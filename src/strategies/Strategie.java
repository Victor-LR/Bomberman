package strategies;

import agents.AgentAction;
import agents.Agent_Bomberman;
import map.GameState;

public abstract class Strategie {
	
	private GameState etat = null;
	private Agent_Bomberman agent = null;
	
	public Strategie (GameState et, Agent_Bomberman ag) {
		this.etat = et;
		this.agent = ag;
	}
	
	public abstract AgentAction action();
	
	
	public GameState getEtat() {
		return this.etat;
	}
	
	public Agent_Bomberman getAgent() {
		return this.agent;
	}
}
