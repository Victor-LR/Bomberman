package strategies;

import java.util.ArrayList;

import objets.Objet;
import objets.Objet_Bomb;

import map.GameState;
import map.Map;
import agents.Agent;
import agents.AgentAction;
import agents.Agent_Bomberman;
import agents.Agent_Rajion;

public class Strategie_Rajion extends Strategie {

	Agent_Rajion rajion = null;
	
	public Strategie_Rajion(GameState et, Agent_Rajion agRajion) {
		super(et, agRajion);
		this.rajion = agRajion;
		
	}

	@Override
	public AgentAction action() {
		ArrayList<AgentAction> listAction = new ArrayList<AgentAction>();
		ArrayList<Agent_Bomberman> bombermans = getEtat().getBombermans();
		
		int x = getAgent().getX();
		int y = getAgent().getY();
		
		
		//liste de bombermans triée du plus proche au plus lointain par rapport au bomberman
				ArrayList<Agent_Bomberman> bbms_proche = new ArrayList<Agent_Bomberman>();
				if (bombermans.size() != 0) bbms_proche.add(bombermans.get(0));
				else bbms_proche.add(bombermans.get(1));
				
				for(int i = 1 ; i < bombermans.size(); i++){
					Agent_Bomberman bomberman = bombermans.get(i);
					
					if( !bomberman.isDead()) {
						
						int bbm_x = bomberman.getX();
						int bbm_y = bomberman.getY();
						
						Agent_Bomberman bbm_p =  bbms_proche.get(0);
						
						int bbm_px = bbm_p.getX();
						int bbm_py = bbm_p.getY();
						
						if( Math.abs(bbm_x - x) +  Math.abs(bbm_y - y)  <  Math.abs(bbm_px - x) +  Math.abs(bbm_py - y))
							bbms_proche.set(0,bomberman);
						else bbms_proche.add(bomberman);
					}

				}
				
				for(int i = 0 ; i < bbms_proche.size(); i++){
					
					Agent_Bomberman bomberman = bbms_proche.get(i);
					
					
					int bbm_x = bomberman.getX();
					int bbm_y = bomberman.getY();
				
					int portee = 100;
					
					if ( (bbm_x >= x-portee) & (bbm_x <= x+portee)  & (bbm_y >= y-portee) & (bbm_y <= y+portee)){
					
						
						if( (bbm_x > x) & (bbm_x <= x + portee) ){
							if (getEtat().isLegalMove(new AgentAction(Map.EAST),getAgent())) return new AgentAction(Map.EAST);
							else if(getEtat().isLegalMove(new AgentAction(Map.NORTH),getAgent())) return new AgentAction(Map.NORTH);
							else if(getEtat().isLegalMove(new AgentAction(Map.SOUTH),getAgent())) return new AgentAction(Map.SOUTH);
							else if(getEtat().isLegalMove(new AgentAction(Map.WEST),getAgent())) return new AgentAction(Map.WEST);
			
							else return new AgentAction(Map.STOP);
						}
						
						if( (bbm_x < x) & (bbm_x >= x - portee) ){
							if (getEtat().isLegalMove(new AgentAction(Map.WEST),getAgent())) return new AgentAction(Map.WEST);
							else if(getEtat().isLegalMove(new AgentAction(Map.SOUTH),getAgent())) return new AgentAction(Map.SOUTH);
							else if(getEtat().isLegalMove(new AgentAction(Map.NORTH),getAgent())) return new AgentAction(Map.NORTH);
							else if(getEtat().isLegalMove(new AgentAction(Map.EAST),getAgent())) return new AgentAction(Map.EAST);
							else return new AgentAction(Map.STOP);
						}
						
						if(	(bbm_y < y)& (bbm_y >= y - portee)){
							if (getEtat().isLegalMove(new AgentAction(Map.NORTH),getAgent())) return new AgentAction(Map.NORTH);
							else if(getEtat().isLegalMove(new AgentAction(Map.WEST),getAgent())) return new AgentAction(Map.WEST);
							else if(getEtat().isLegalMove(new AgentAction(Map.EAST),getAgent())) return new AgentAction(Map.EAST);
							else if(getEtat().isLegalMove(new AgentAction(Map.SOUTH),getAgent())) return new AgentAction(Map.SOUTH);
							else return new AgentAction(Map.STOP);
						}
						
						if(	(bbm_y > y)& (bbm_y <= y + portee)){
							if (getEtat().isLegalMove(new AgentAction(Map.SOUTH),getAgent())) return new AgentAction(Map.SOUTH);
							else if(getEtat().isLegalMove(new AgentAction(Map.EAST),getAgent())) return new AgentAction(Map.EAST);
							else if(getEtat().isLegalMove(new AgentAction(Map.WEST),getAgent())) return new AgentAction(Map.WEST);
							else if(getEtat().isLegalMove(new AgentAction(Map.NORTH),getAgent())) return new AgentAction(Map.NORTH);
							else return new AgentAction(Map.STOP);
						}
					}
				}
				
				
				//Choisi une action aléatoire
				for(int i=0;i<=5;i++) {
					if (getEtat().isLegalMove(new AgentAction(i), getAgent()))
						listAction.add(new AgentAction(i));
				}	
				if(listAction.size() == 0) return new AgentAction(Map.STOP);
				else return(listAction.get((int)(Math.random()*listAction.size())));
				//return new AgentAction(Map.STOP);
			}
	

	public Agent_Rajion getAgent() {
		return this.rajion;
	}

}
