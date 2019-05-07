package agents;

import java.util.ArrayList;

import map.GameState;
import map.Map;

public class Agent_Rajion extends Agent {

	public Agent_Rajion(int px, int py,int id) {
		super(AgentType.RAJION, px, py);
		super.setId(id);
	}
	
	public AgentAction chooseAction(GameState etatjeu) {
		
		ArrayList<AgentAction> listAction=new ArrayList<AgentAction>();
		for(int i=0;i<5;i++)
		{
			if (etatjeu.isLegalMove(new AgentAction(i), this))
				listAction.add(new AgentAction(i));
		}		
		return(listAction.get((int)(Math.random()*listAction.size())));
		
		//return new AgentAction(Map.STOP);
	}

}
