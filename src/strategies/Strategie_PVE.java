package strategies;

import java.util.ArrayList;

import agents.Agent;
import agents.AgentAction;
import agents.Agent_Bomberman;
import agents.Agent_Ennemy;
import map.GameState;

public class Strategie_PVE extends Strategie{
	
	private Agent_Bomberman agent = null;
	
	public Strategie_PVE(GameState etat, Agent_Bomberman agent) {
		super(etat, agent);
		this.agent = agent;
	}
	
	@Override
	public AgentAction action() {
		
		AgentAction Action = null;//new AgentAction(0);
		ArrayList<AgentAction> listAction = new ArrayList<AgentAction>();
		ArrayList<Agent> ennemies = getEtat().getAll_ennemies();
		
		int x = getAgent().getX();
		int y = getAgent().getY();
		int comptBW = 0;
		int comptW = 0; 
		int nb_bombes_bbm = 0;
		for(int i=0;i<5;i++)
		{
			Action = new AgentAction(i);
			
			if (getEtat().getMap().isBrokable_Wall(x+Action.getVx(), y+Action.getVy())) comptBW++;
			if (getEtat().getMap().isWall(x+Action.getVx(), y+Action.getVy())) comptW++;
			
			int new_ec = 0;
			int aux_ecart = 0;
			int ecart = 40000;
			
			for(int j =0 ; j < ennemies.size(); j++) {
				
				Agent e  = ennemies.get(j);
				int xec = Math.abs(x-e.getX());
				int yec = Math.abs(y-e.getY());
				
				aux_ecart = xec + yec;
				
				if(aux_ecart < ecart) {
					ecart = aux_ecart;
					int depx = Math.abs(x+Action.getVx()-e.getX());
					int depy = Math.abs(y+Action.getVy()-e.getY());
					new_ec = depx + depy;
				}
				
				if(ecart < getAgent().getRange()+2) {
					for(int k = 0 ; k<getEtat().getBombes().size(); k++) {
						if(getEtat().getBombes().get(k).getId_bbm() == getAgent().getId()) 
							nb_bombes_bbm++;
					}
					if(nb_bombes_bbm == 0)
						listAction.add(new AgentAction(5));
					else {
						if(new_ec > ecart) {
							if (getEtat().isLegalMoveBbm(new AgentAction(i), getAgent()))
									listAction.add(new AgentAction(i));
						}
					}
				}
				else {
						if(new_ec < ecart) {
						if (getEtat().isLegalMoveBbm(new AgentAction(i), getAgent()))
								listAction.add(new AgentAction(i));
					}
				}
			}
		}
		
		if (comptBW == 1 & comptW >= 1 ) {
			for(int k = 0 ; k<getEtat().getBombes().size(); k++) {
				if(getEtat().getBombes().get(k).getId_bbm() == getAgent().getId()) 
					nb_bombes_bbm++;
			}
			if(nb_bombes_bbm == 0)
				return (new AgentAction(5));
			else {
				if  (listAction.size() > 0) return (listAction.get((int)(Math.random()*listAction.size())));
				else return new AgentAction(4);
			}
		}
		else {
			if (comptBW > 1 ) {
				for(int k = 0 ; k<getEtat().getBombes().size(); k++) {
					if(getEtat().getBombes().get(k).getId_bbm() == getAgent().getId()) 
						nb_bombes_bbm++;
				}
				if(nb_bombes_bbm == 0)
						return (new AgentAction(5));
				else {
					if  (listAction.size() > 0) return (listAction.get((int)(Math.random()*listAction.size())));
					else return new AgentAction(4);
				}
			}
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
