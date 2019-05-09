package agents;

import java.util.ArrayList;

import strategies.Strategie_Bird;
import strategies.Strategie_Rajion;

import map.GameState;
import map.Map;

public class Agent_Rajion extends Agent {

	public Agent_Rajion(int px, int py,int id) {
		super(AgentType.RAJION, px, py);
		super.setId(id);
	}
	
	public AgentAction chooseAction(GameState etatjeu) {
		
		Strategie_Rajion strat = new Strategie_Rajion(etatjeu, this);
		
		return strat.action();

//		ArrayList<AgentAction> listAction=new ArrayList<AgentAction>();
//		for(int i=0;i<5;i++)
//		{
//			if (etatjeu.isLegalMove(new AgentAction(i), this))
//				listAction.add(new AgentAction(i));
//		}
//		if(listAction.size() == 0) return new AgentAction(Map.STOP);
//		else return(listAction.get((int)(Math.random()*listAction.size())));
		
	}

}
