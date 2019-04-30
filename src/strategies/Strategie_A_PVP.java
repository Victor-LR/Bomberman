package strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import objets.Objet;
import objets.ObjetType;
import objets.Objet_Bomb;
import map.GameState;
import map.Map;
import agents.AgentAction;
import agents.Agent_Bomberman;

public class Strategie_A_PVP extends Strategie {

	public Strategie_A_PVP(GameState et, Agent_Bomberman ag) {
		super(et, ag);
	}

	//empêche de rentrer dans la portée d'explosion d'une bombe
	public boolean isLegalMoveBbm(AgentAction actionbbm, Agent_Bomberman bbm){
		ArrayList<Objet_Bomb> bombes = getEtat().getBombes();
		
		int action_x = actionbbm.getVx();
		int action_y = actionbbm.getVy();
		
		int x = getAgent().getX();
		int y = getAgent().getY();
			
		if (getEtat().isLegalMoveBbm(actionbbm,bbm)){
			
			for(int i = 0 ; i < bombes.size() ; i++){
				
				Objet_Bomb bombe = bombes.get(i);
				
				if (bombe.getId_bbm() != getAgent().getId()) {
					int bombe_x = bombe.getObjX();
					int bombe_y = bombe.getObjY();
					
					int bombe_range = bombe.getRange();
					
				
					if((bombe_y == y + action_y) & ( (x + action_x >= bombe_x-bombe_range) & (x + action_x <= bombe_x+bombe_range))){
						if(actionbbm.getAction() == Map.NORTH || actionbbm.getAction() == Map.SOUTH ) return false;
						else return true;
					}
					
					if((bombe_x == x + action_x) & ( (y + action_y >= bombe_y-bombe_range) & (y + action_y <= bombe_y+bombe_range))){
						if(actionbbm.getAction() == Map.EAST || actionbbm.getAction() == Map.WEST ) return false;
						else return true;
					}		
				}
			}
			return true;
			
		}else return false;
		
	}
	
	@Override
	public AgentAction action() {

		ArrayList<AgentAction> listAction = new ArrayList<AgentAction>();
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
					if (isLegalMoveBbm(new AgentAction(Map.NORTH), getAgent())) listAction.add(new AgentAction(Map.NORTH));
					if (isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent())) listAction.add(new AgentAction(Map.SOUTH));
					
					if((x == bombe_x-bombe_range) & isLegalMoveBbm(new AgentAction(Map.WEST), getAgent()))
						listAction.add(new AgentAction(Map.WEST));
					if((x == bombe_x+bombe_range) & isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
						listAction.add(new AgentAction(Map.EAST));
					
					if (listAction.size() == 0) return new AgentAction(Map.STOP);
					else return(listAction.get((int)(Math.random()*listAction.size()))); 
				}else 
				if((bombe_x == x) & ( y >= bombe_y-bombe_range & y <= bombe_y+bombe_range)){
					if (isLegalMoveBbm(new AgentAction(Map.WEST), getAgent())) listAction.add(new AgentAction(Map.WEST));
					if (isLegalMoveBbm(new AgentAction(Map.EAST), getAgent())) listAction.add(new AgentAction(Map.EAST));
					
					if((y == bombe_y-bombe_range) & isLegalMoveBbm(new AgentAction(Map.NORTH), getAgent()))
						listAction.add(new AgentAction(Map.NORTH));
					if((y == bombe_y+bombe_range) & isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
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
		
	
		
		//liste d'items triée du plus proche au plus lointain par rapport au bomberman
		ArrayList<Objet> items_proche = new ArrayList<Objet>();
		if (items.size() != 0) items_proche.add(items.get(0));
		for(int i = 1 ; i < items.size(); i++){
			
			Objet item = items.get(i);
			
			int item_x = item.getObjX();
			int item_y = item.getObjY();
			
			int portee = 3;
			
			Objet item_p =  items_proche.get(0);
			
			int item_px = item_p.getObjX();
			int item_py = item_p.getObjY();
			
			if( Math.abs(item_x - x) +  Math.abs(item_y - y)  <  Math.abs(item_px - x) +  Math.abs(item_py - y))
				items_proche.set(0,item);
			else items_proche.add(item);

		}
		
		//Récupère les items proches
		for(int i = 0 ; i < items_proche.size(); i++){
			
			Objet item = items_proche.get(i);
			
			if(item.getType() == ObjetType.FIRE_UP || item.getType() == ObjetType.BOMB_UP || item.getType() == ObjetType.FIRE_SUIT){
					
				int item_x = item.getObjX();
				int item_y = item.getObjY();
				
				int portee = 3;
				
				if ( (item_x >= x-portee) & (item_x <= x+portee)  & (item_y >= y-portee) & (item_y <= y+portee)){
				
					if( (item_x > x) & (item_x <= x + portee)){
						if (isLegalMoveBbm(new AgentAction(Map.EAST),getAgent())) return new AgentAction(Map.EAST);
						else if(isLegalMoveBbm(new AgentAction(Map.NORTH),getAgent())) return new AgentAction(Map.NORTH);
						else if(isLegalMoveBbm(new AgentAction(Map.SOUTH),getAgent())) return new AgentAction(Map.SOUTH);
						else if(isLegalMoveBbm(new AgentAction(Map.WEST),getAgent())) return new AgentAction(Map.WEST);

						else return new AgentAction(Map.STOP);
					}
					
					if( (item_x < x) & (item_x >= x - portee)){
						if (isLegalMoveBbm(new AgentAction(Map.WEST),getAgent())) return new AgentAction(Map.WEST);
						else if(isLegalMoveBbm(new AgentAction(Map.SOUTH),getAgent())) return new AgentAction(Map.SOUTH);
						else if(isLegalMoveBbm(new AgentAction(Map.NORTH),getAgent())) return new AgentAction(Map.NORTH);
						else if(isLegalMoveBbm(new AgentAction(Map.EAST),getAgent())) return new AgentAction(Map.EAST);
						else return new AgentAction(Map.STOP);
					}
					
					if(	(item_y < y) & (item_y >= y - portee)){
						if (isLegalMoveBbm(new AgentAction(Map.NORTH),getAgent())) return new AgentAction(Map.NORTH);
						else if(isLegalMoveBbm(new AgentAction(Map.WEST),getAgent())) return new AgentAction(Map.WEST);
						else if(isLegalMoveBbm(new AgentAction(Map.EAST),getAgent())) return new AgentAction(Map.EAST);
						else if(isLegalMoveBbm(new AgentAction(Map.SOUTH),getAgent())) return new AgentAction(Map.SOUTH);
						else return new AgentAction(Map.STOP);
					}
					
					if(	(item_y > y) & (item_y <= y + portee)){
						if (isLegalMoveBbm(new AgentAction(Map.SOUTH),getAgent())) return new AgentAction(Map.SOUTH);
						else if(isLegalMoveBbm(new AgentAction(Map.EAST),getAgent())) return new AgentAction(Map.EAST);
						else if(isLegalMoveBbm(new AgentAction(Map.WEST),getAgent())) return new AgentAction(Map.WEST);
						else if(isLegalMoveBbm(new AgentAction(Map.NORTH),getAgent())) return new AgentAction(Map.NORTH);
						else return new AgentAction(Map.STOP);
					}



				}
			}
		}
		
		//liste de bombermans triée du plus proche au plus lointain par rapport au bomberman
		ArrayList<Agent_Bomberman> bbms_proche = new ArrayList<Agent_Bomberman>();
		if (bombermans.size() != 0 & bombermans.get(0).getId() != getAgent().getId()) bbms_proche.add(bombermans.get(0));
		else bbms_proche.add(bombermans.get(1));
		
		for(int i = 1 ; i < bombermans.size(); i++){
			Agent_Bomberman bomberman = bombermans.get(i);
			
			if( bomberman.getId() != getAgent().getId() & !bomberman.isDead()) {
				
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
		
			int portee = bomberman.getRange()+1;
			
			if ( (bbm_x >= x-portee) & (bbm_x <= x+portee)  & (bbm_y >= y-portee) & (bbm_y <= y+portee)){
			
				
				if( (bbm_x > x) & (bbm_x <= x + portee) ){
					if (isLegalMoveBbm(new AgentAction(Map.EAST),getAgent())) return new AgentAction(Map.EAST);
					else if(isLegalMoveBbm(new AgentAction(Map.NORTH),getAgent())) return new AgentAction(Map.NORTH);
					else if(isLegalMoveBbm(new AgentAction(Map.SOUTH),getAgent())) return new AgentAction(Map.SOUTH);
					else if(isLegalMoveBbm(new AgentAction(Map.WEST),getAgent())) return new AgentAction(Map.WEST);
	
					else return new AgentAction(Map.STOP);
				}
				
				if( (bbm_x < x) & (bbm_x >= x - portee) ){
					if (isLegalMoveBbm(new AgentAction(Map.WEST),getAgent())) return new AgentAction(Map.WEST);
					else if(isLegalMoveBbm(new AgentAction(Map.SOUTH),getAgent())) return new AgentAction(Map.SOUTH);
					else if(isLegalMoveBbm(new AgentAction(Map.NORTH),getAgent())) return new AgentAction(Map.NORTH);
					else if(isLegalMoveBbm(new AgentAction(Map.EAST),getAgent())) return new AgentAction(Map.EAST);
					else return new AgentAction(Map.STOP);
				}
				
				if(	(bbm_y < y)& (bbm_y >= y - portee)){
					if (isLegalMoveBbm(new AgentAction(Map.NORTH),getAgent())) return new AgentAction(Map.NORTH);
					else if(isLegalMoveBbm(new AgentAction(Map.WEST),getAgent())) return new AgentAction(Map.WEST);
					else if(isLegalMoveBbm(new AgentAction(Map.EAST),getAgent())) return new AgentAction(Map.EAST);
					else if(isLegalMoveBbm(new AgentAction(Map.SOUTH),getAgent())) return new AgentAction(Map.SOUTH);
					else return new AgentAction(Map.STOP);
				}
				
				if(	(bbm_y > y)& (bbm_y <= y + portee)){
					if (isLegalMoveBbm(new AgentAction(Map.SOUTH),getAgent())) return new AgentAction(Map.SOUTH);
					else if(isLegalMoveBbm(new AgentAction(Map.EAST),getAgent())) return new AgentAction(Map.EAST);
					else if(isLegalMoveBbm(new AgentAction(Map.WEST),getAgent())) return new AgentAction(Map.WEST);
					else if(isLegalMoveBbm(new AgentAction(Map.NORTH),getAgent())) return new AgentAction(Map.NORTH);
					else return new AgentAction(Map.STOP);
				}
			}
		}
		
		
		//Choisi une action aléatoire
		for(int i=0;i<=5;i++) {
			if (isLegalMoveBbm(new AgentAction(i), getAgent()))
				listAction.add(new AgentAction(i));
		}	
		if(listAction.size() == 0) return new AgentAction(Map.STOP);
		else return(listAction.get((int)(Math.random()*listAction.size())));
		//return new AgentAction(Map.STOP);
	}

}
