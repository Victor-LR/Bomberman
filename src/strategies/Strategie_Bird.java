package strategies;

import java.util.ArrayList;

import agents.AgentAction;
import agents.Agent_Bird;
import agents.Agent_Bomberman;
import map.GameState;
import map.Map;
import objets.Objet_Bomb;

public class Strategie_Bird extends Strategie{
	
	private Agent_Bird agent = null;

	public Strategie_Bird(GameState et, Agent_Bird ag) {
		super(et, ag);
		this.agent = ag;
	}

	public AgentAction action() {
		
		ArrayList<Agent_Bird> birds = getEtat().getBirds();
		ArrayList<Agent_Bomberman> bombermans = getEtat().getBombermans();
		ArrayList<AgentAction> actions = new ArrayList<AgentAction>();
		AgentAction action = null;;
		
		int x = getAgent().getX();
		int y = getAgent().getY();
		
		
		
		for(int k=0;k<5;k++){
			AgentAction tmp_action = new AgentAction(k);
				
				if(getAgent().getEtat() == 0) {
					
					for(int j =0; j< bombermans.size(); j++) {
						Agent_Bomberman b = bombermans.get(j);
						int xb = b.getX();
						int yb = b.getY();
						
						int xecb = Math.abs(xb-x);
						int yecb = Math.abs(yb-y);
						int becart = xecb + yecb;
						
						if(becart < 5) {
							getAgent().setEtat(1);
							
						}
					}
					action = new AgentAction(Map.STOP_BIRD);
				}
				else {
					int new_ec = 0;
					int aux_ecart = 0;
					int ecart = 40000;
					for(int j =0; j< bombermans.size(); j++) {
						Agent_Bomberman b = bombermans.get(j);
						int xb = b.getX();
						int yb = b.getY();
						
						int xecb = Math.abs(xb-x);
						int yecb = Math.abs(yb-y);
						aux_ecart = xecb + yecb;
						
						if(aux_ecart < ecart) {
							ecart = aux_ecart;
							int depx = Math.abs(x+tmp_action.getVx()-xb);
							int depy = Math.abs(y+tmp_action.getVy()-yb);
							new_ec = depx + depy;
						}
							
						if(new_ec < ecart)
							if (getEtat().isLegalMoveBird(tmp_action, getAgent())) 
								actions.add(tmp_action);
						
						if(ecart > 8) 
							getAgent().setEtat(0);
					}
					
					
				}
				if  (actions.size() > 0) action = actions.get((int)(Math.random()*actions.size()));
			}
			
		
		return action;
	}

	public Agent_Bird getAgent() {
		return this.agent;
	}

}
