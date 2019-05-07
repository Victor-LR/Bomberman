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
		
		for(int k=0;k<5;k++){
			AgentAction tmp_action = new AgentAction(k);
			for(int i = 0; i< birds.size(); ++i) {
				Agent_Bird bird = birds.get(i);
				
				int x = bird.getX();
				int y = bird.getY();
				
				if(bird.getEtat() == 0) {
					
					for(int j =0; j< bombermans.size(); j++) {
						Agent_Bomberman b = bombermans.get(j);
						int xb = b.getX();
						int yb = b.getY();
						
						int xecb = Math.abs(xb-x);
						int yecb = Math.abs(yb-y);
						int becart = xecb + yecb;
						
						if(becart < 3) {
							bird.setEtat(1);
							
						}
					}
					action = new AgentAction(Map.STOP_BIRD);
				}
				else {
					for(int j =0; j< bombermans.size(); j++) {
						Agent_Bomberman b = bombermans.get(j);
						int xb = b.getX();
						int yb = b.getY();
						
						int xecb = Math.abs(xb-x);
						int yecb = Math.abs(yb-y);
						int becart = xecb + yecb;
						
						
							int depx = Math.abs(x+tmp_action.getVx()-xb);
							int depy = Math.abs(y+tmp_action.getVy()-yb);
							int new_ec = depx + depy;
							
							if(new_ec < becart)
								if (getEtat().isLegalMoveBird(tmp_action, getAgent())) 
									actions.add(tmp_action);
							
						}
					}
					if  (actions.size() > 0) action = actions.get((int)(Math.random()*actions.size()));
					else action = new AgentAction(4);
				}
			}
		
		return action;
	}

	public Agent_Bird getAgent() {
		return this.agent;
	}

}
