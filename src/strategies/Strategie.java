package strategies;

import agents.Agent;
import agents.AgentAction;
import agents.Agent_Bomberman;
import map.GameState;

public abstract class Strategie {
	
	private GameState etat = null;
	private Agent agent = null;
	
	public Strategie (GameState et, Agent ag) {
		this.etat = et;
		this.agent = ag;
	}
	
	public abstract AgentAction action();
	
	
	public GameState getEtat() {
		return this.etat;
	}
	
}
