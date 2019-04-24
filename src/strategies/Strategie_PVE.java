package strategies;

import java.util.ArrayList;

import agents.Agent;
import agents.AgentAction;
import agents.Agent_Bomberman;
import map.GameState;

public class Strategie_PVE extends Strategie{
	
	public Strategie_PVE(GameState etat, Agent_Bomberman agent) {
		super(etat, agent);
	}
	
	@Override
	public AgentAction action() {
		
		AgentAction Action = null;//new AgentAction(0);
		ArrayList<AgentAction> listAction = new ArrayList<AgentAction>();
		ArrayList<Agent> ennemies = getEtat().getEnnemies();
		
		int x = getAgent().getX();
		int y = getAgent().getY();
		int comptBW = 0;
		int comptW = 0;
		
		for(int i=0;i<5;i++)
		{
			Action = new AgentAction(i);
			
			if (getEtat().getMap().isBrokable_Wall(x+Action.getVx(), y+Action.getVy())) comptBW++;
			if (getEtat().getMap().isWall(x+Action.getVx(), y+Action.getVy())) comptW++;
			
			for(int j =0 ; j < ennemies.size(); j++) {
				Agent e  = ennemies.get(j);
				int xec = Math.abs(x-e.getX());
				int yec = Math.abs(y-e.getY());
				
				int ecart = xec + yec;
				
				int depx = Math.abs(x+Action.getVx()-e.getX());
				int depy = Math.abs(y+Action.getVy()-e.getY());
				int new_ec = depx + depy;
				
				//System.out.println("old ec : "+ ecart +" new ec : "+ new_ec);
				
				if(ecart < getAgent().getRange()+1)
					listAction.add(new AgentAction(5));
				else {
						if(new_ec < ecart) {
						if (getEtat().isLegalMoveBbm(new AgentAction(i), getAgent()))
								listAction.add(new AgentAction(i));
					}
				}
			}
		}
		
		if (comptBW == 1 & comptW >= 1 )
			return new AgentAction(5);
		else {
			if (comptBW > 1 )
				return new AgentAction(5);
			else {
				if  (listAction.size() > 0) return (listAction.get((int)(Math.random()*listAction.size())));
				else return new AgentAction(4);
			}
		}
	}

}
