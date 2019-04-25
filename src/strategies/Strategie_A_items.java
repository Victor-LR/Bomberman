package strategies;

import java.util.ArrayList;

import objets.Objet;
import objets.ObjetType;
import objets.Objet_Bomb;
import map.GameState;
import map.Map;
import agents.Agent;
import agents.AgentAction;
import agents.Agent_Bomberman;

public class Strategie_A_items extends Strategie {

	public Strategie_A_items(GameState et, Agent_Bomberman ag) {
		super(et, ag);
	}

	@Override
	public AgentAction action() {
		AgentAction Action = null;
		ArrayList<AgentAction> listAction = new ArrayList<AgentAction>();
		ArrayList<Agent> ennemies = getEtat().getEnnemies();
		ArrayList<Agent_Bomberman> bombermans = getEtat().getBombermans();
		ArrayList<Objet_Bomb> bombes = getEtat().getBombes();
		ArrayList<Objet> items = getEtat().getItems();
		
		int x = getAgent().getX();
		int y = getAgent().getY();
		
		int range = getAgent().getRange();
		
		//Comportement pour éviter les bombes adverses
		for(int i = 0 ; i < bombes.size() ; i++){
			
			Objet_Bomb bombe = bombes.get(i);
			
			
			if (bombe.getId_bbm() != getAgent().getId()) {
				int bombe_x = bombe.getObjX();
				int bombe_y = bombe.getObjY();
				
				int bombe_range = bombe.getRange();
				
				if((bombe_y == y) & ( x >= bombe_x-bombe_range & x <= bombe_x+bombe_range)){
					if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH), getAgent())) listAction.add(new AgentAction(Map.NORTH));
					if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent())) listAction.add(new AgentAction(Map.SOUTH));
					
					if((x == bombe_x-bombe_range) & getEtat().isLegalMoveBbm(new AgentAction(Map.WEST), getAgent()))
						listAction.add(new AgentAction(Map.WEST));
					if((x == bombe_x+bombe_range) & getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
						listAction.add(new AgentAction(Map.EAST));
					
					if (listAction.size() == 0) return new AgentAction(Map.STOP);
					else return(listAction.get((int)(Math.random()*listAction.size()))); 
				}else 
				if((bombe_x == x) & ( y >= bombe_y-bombe_range & y <= bombe_y+bombe_range)){
					if (getEtat().isLegalMoveBbm(new AgentAction(Map.WEST), getAgent())) listAction.add(new AgentAction(Map.WEST));
					if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent())) listAction.add(new AgentAction(Map.EAST));
					
					if((y == bombe_y-bombe_range) & getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH), getAgent()))
						listAction.add(new AgentAction(Map.NORTH));
					if((y == bombe_y+bombe_range) & getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
						listAction.add(new AgentAction(Map.SOUTH));
					
					if (listAction.size() == 0) return new AgentAction(Map.STOP);
					else return(listAction.get((int)(Math.random()*listAction.size()))); 
				}
			}
			
		}
		
		//Comportement pour placer une bombe quand un ennemie est a portée
		for(int i = 0 ; i < bombermans.size(); i++){
			
			Agent_Bomberman bbm = bombermans.get(i);
			
			if(bbm.getId() != getAgent().getId() & !bbm.isDead()) {
				int bbm_x = bbm.getX();
				int bbm_y = bbm.getY();
				
				if( (bbm_x == x) & (bbm_y >= y-range & bbm_y <= y+range) ) return new AgentAction(Map.BOMB);
				else if( (bbm_y == y) & (bbm_x >= x-range & bbm_x <= x+range) ) return new AgentAction(Map.BOMB);
			}
		}
		
		//Récupère les items proches
		for(int i = 0 ; i < items.size(); i++){
			
			Objet item = items.get(i);
			
			if(item.getType() == ObjetType.FIRE_UP || item.getType() == ObjetType.BOMB_UP || item.getType() == ObjetType.FIRE_SUIT){
					
				int item_x = item.getObjX();
				int item_y = item.getObjY();
				
				int portee = 3;
				
				if ( (item_x >= x-portee) & (item_x <= x+portee)  & (item_y >= y-portee) & (item_y <= y+portee)){
				
					if( (item_x > x) & (item_x <= x + portee)){
						if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST),getAgent())) return new AgentAction(Map.EAST);
						else if(getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH),getAgent())) return new AgentAction(Map.SOUTH);
						else if(getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH),getAgent())) return new AgentAction(Map.NORTH);
						else if(getEtat().isLegalMoveBbm(new AgentAction(Map.WEST),getAgent())) return new AgentAction(Map.WEST);
						else return new AgentAction(Map.STOP);
					}
					if(	(item_y > y) & (item_y <= y + portee)){
						if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH),getAgent())) return new AgentAction(Map.SOUTH);
						else if(getEtat().isLegalMoveBbm(new AgentAction(Map.EAST),getAgent())) return new AgentAction(Map.EAST);
						else if(getEtat().isLegalMoveBbm(new AgentAction(Map.WEST),getAgent())) return new AgentAction(Map.WEST);
						else if(getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH),getAgent())) return new AgentAction(Map.NORTH);
						else return new AgentAction(Map.STOP);
					}
					if( (item_x < x) & (item_x >= x - portee)){
						if (getEtat().isLegalMoveBbm(new AgentAction(Map.WEST),getAgent())) return new AgentAction(Map.WEST);
						else if(getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH),getAgent())) return new AgentAction(Map.SOUTH);
						else if(getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH),getAgent())) return new AgentAction(Map.NORTH);
						else if(getEtat().isLegalMoveBbm(new AgentAction(Map.EAST),getAgent())) return new AgentAction(Map.EAST);
						else return new AgentAction(Map.STOP);
					}

					if(	(item_y < y) & (item_y >= y - portee)){
						if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH),getAgent())) return new AgentAction(Map.NORTH);
						else if(getEtat().isLegalMoveBbm(new AgentAction(Map.EAST),getAgent())) return new AgentAction(Map.EAST);
						else if(getEtat().isLegalMoveBbm(new AgentAction(Map.WEST),getAgent())) return new AgentAction(Map.WEST);
						else if(getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH),getAgent())) return new AgentAction(Map.SOUTH);
						else return new AgentAction(Map.STOP);
					}
				}
			}
		}
		
		
		//Choisi une action aléatoire
		for(int i=0;i<=5;i++) {
			if (getEtat().isLegalMoveBbm(new AgentAction(i), getAgent()))
				listAction.add(new AgentAction(i));
		}		
		return(listAction.get((int)(Math.random()*listAction.size())));
		//return new AgentAction(Map.STOP);
	}

}
