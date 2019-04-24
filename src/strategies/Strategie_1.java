package strategies;

import java.util.ArrayList;

import agents.AgentAction;
import agents.Agent_Bomberman;
import map.GameState;

public class Strategie_1 extends Strategie{
	
	public Strategie_1(GameState etat, Agent_Bomberman agent) {
		super(etat, agent);
	}
	
	@Override
	public AgentAction action() {
		AgentAction Action = null;//new AgentAction(0);
		for(int i=0;i<=5;i++)
		{
			if (getEtat().isLegalMoveBbm(new AgentAction(i), getAgent())) {
				//System.out.println(i);
				//listAction.add(new AgentAction(i));
			} 
		}		
		return Action;
	}

}
