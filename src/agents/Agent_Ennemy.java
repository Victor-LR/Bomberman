package agents;

import java.util.ArrayList;

import map.GameState;
import map.Map;

public class Agent_Ennemy extends Agent {

	public Agent_Ennemy (int px, int py,int id) {
		super(AgentType.ENNEMY, px, py);
		super.setId(id);
	}
	
	public AgentAction chooseAction(GameState etatjeu) {
		
		ArrayList<AgentAction> listAction=new ArrayList<AgentAction>();
		for(int i=0;i<5;i++)
		{
			if (etatjeu.isLegalMove(new AgentAction(i), this))
				listAction.add(new AgentAction(i));
		}		

		if(listAction.size() == 0) return new AgentAction(Map.STOP);
		else return(listAction.get((int)(Math.random()*listAction.size())));
	}


	
}
