package strategies;

import java.util.ArrayList;

import agents.Agent;
import agents.AgentAction;
import agents.Agent_Bomberman;
import agents.Agent_Ennemy;
import map.GameState;
import objets.Objet_Bomb;

public class Strategie_PVP extends Strategie{
	
	private Agent_Bomberman agent = null;
	
	public Strategie_PVP(GameState etat, Agent_Bomberman agent) {
		super(etat, agent);
		this.agent = agent;
	}
	
	@Override
	public AgentAction action() {
		
		AgentAction Action = null;//new AgentAction(0);
		ArrayList<AgentAction> listAction = new ArrayList<AgentAction>();
		ArrayList<Agent_Ennemy> ennemies = getEtat().getEnnemies();
		ArrayList<Agent_Bomberman> bombermans = getEtat().getBombermans();
		ArrayList<Objet_Bomb> bombes = getEtat().getBombes();
		
		int x = getAgent().getX();
		int y = getAgent().getY();
		int comptBW = 0;
		int comptW = 0;
		
		for(int i=0;i<5;i++)
		{
			Action = new AgentAction(i);
			
			if (getEtat().getMap().isBrokable_Wall(x+Action.getVx(), y+Action.getVy())) comptBW++;
			if (getEtat().getMap().isWall(x+Action.getVx(), y+Action.getVy())) comptW++;
			
			for(int j =0 ; j < bombermans.size(); j++) {
				if(bombermans.get(j).getId() != getAgent().getId()) {
					Agent e  = bombermans.get(j);
					
					for(int k = 0; k < bombes.size(); k++) {
						Objet_Bomb b = bombes.get(k);
						if(b.getId_bbm() != getAgent().getId()) {
							int xb = b.getObjX();
							int yb = b.getObjY();
							
							if(x-2 <= xb & y == yb) {
								if (getEtat().isLegalMoveBbm(new AgentAction(2), getAgent()) & getEtat().isLegalMoveBbm(new AgentAction(3), getAgent())) {
									listAction.add(new AgentAction(2));
									listAction.add(new AgentAction(3));
								}
							}
							
							if(x+2 >= xb & y == yb) {
								if (getEtat().isLegalMoveBbm(new AgentAction(2), getAgent()) & getEtat().isLegalMoveBbm(new AgentAction(3), getAgent())) {
									listAction.add(new AgentAction(2));
									listAction.add(new AgentAction(3));
								}
							}
							
							if(x == xb & y-2 <= yb) {
								if (getEtat().isLegalMoveBbm(new AgentAction(0), getAgent()) & getEtat().isLegalMoveBbm(new AgentAction(1), getAgent())) {
									listAction.add(new AgentAction(0));
									listAction.add(new AgentAction(1));
								}
							}
							
							if(x == xb & y+2 >= yb) {
								if (getEtat().isLegalMoveBbm(new AgentAction(0), getAgent()) & getEtat().isLegalMoveBbm(new AgentAction(1), getAgent())) {
									listAction.add(new AgentAction(0));
									listAction.add(new AgentAction(1));
								}
							}
								
							
						}
					}
					
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
	
	public Agent_Bomberman getAgent() {
		return agent;
	}

}
