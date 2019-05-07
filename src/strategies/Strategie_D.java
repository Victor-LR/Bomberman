package strategies;

import java.util.ArrayList;

import agents.Agent;
import agents.AgentAction;
import agents.Agent_Bomberman;
import map.GameState;
import map.Map;
import objets.Objet;
import objets.Objet_Bomb;

public class Strategie_D extends Strategie{
	
	
	private Agent_Bomberman agent = null;
	
	public Strategie_D(GameState etat, Agent_Bomberman agent) {
		super(etat, agent);
		this.agent = agent;
	}

	
	@Override
	public AgentAction action() {
		
		AgentAction Action = null;//new AgentAction(0);
		ArrayList<AgentAction> listAction = new ArrayList<AgentAction>();
		ArrayList<Agent_Bomberman> bombermans = getEtat().getBombermans();
		ArrayList<Objet> items = getEtat().getItems();
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
				if(bombermans.get(j).getId() != getAgent().getId() & !bombermans.get(j).isDead()) {
					Agent e  = bombermans.get(j);
					
					if( bombes.size() > 0) {
					
						for(int k = 0; k < bombes.size(); k++) {
							Objet_Bomb b = bombes.get(k);
							int xb = b.getObjX();
							int yb = b.getObjY();
							
							int xecb = Math.abs(xb-getAgent().getX());
							int yecb = Math.abs(yb-getAgent().getY());
							int becart = xecb + yecb;
							
							//System.out.println(becart);
							
							if(b.getId_bbm() != getAgent().getId() & becart <= 4) {
								
								
								if(x == xb & y-2 <= yb) {
									if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()) ) 
										listAction.add(new AgentAction(Map.EAST));
									else if (getEtat().isLegalMoveBbm(new AgentAction(Map.WEST), getAgent()))
										listAction.add(new AgentAction(Map.WEST));
								}
								
								if(x == xb & y+2 >= yb) {
									if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()) ) 
										listAction.add(new AgentAction(Map.EAST));
									else if (getEtat().isLegalMoveBbm(new AgentAction(Map.WEST), getAgent()))
										listAction.add(new AgentAction(Map.WEST));
									
								}
								
								if(x-2 <= xb & y == yb) {
									if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH), getAgent()) ) 
										listAction.add(new AgentAction(Map.NORTH));
									else if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
										listAction.add(new AgentAction(Map.SOUTH));
								}
								
								if(x-2 >= xb & y == yb) {
									if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH), getAgent()) ) 
										listAction.add(new AgentAction(Map.NORTH));
									else if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
										listAction.add(new AgentAction(Map.SOUTH));
									
								}
								
							}
							else {
								int new_ec = 0;
								int aux_ecart = 0;
								int ecart = 40000;
								
								int aux_iecart = 0;
								int i_new_ec = 0;
								int iecart = 40000;
								
								if( items.size() > 0) {
									for(int l = 0; l < items.size(); l++) {
										Objet item = items.get(l);
										int xi = item.getObjX();
										int yi = item.getObjY();
										
										int xeci = Math.abs(xi-x);
										int yeci = Math.abs(yi-y);
										aux_iecart = xeci + yeci;
										
										
										if(aux_iecart <= iecart) {
											iecart = aux_iecart;
											//System.out.println("			ecart : "+ iecart );
											int depxi = Math.abs(x+Action.getVx()-xi);
											int depyi = Math.abs(y+Action.getVy()-yi);
											i_new_ec = depxi + depyi;
											//System.out.println("   new ecart : "+ i_new_ec );
										}
											
									}//fin boucle for pour items
									
									int xec = Math.abs(x-e.getX());
									int yec = Math.abs(y-e.getY());
									aux_ecart = xec + yec;
									
									if(aux_ecart <= ecart) {
										ecart = aux_ecart;
										
										int depx = Math.abs(x+Action.getVx()-e.getX());
										int depy = Math.abs(y+Action.getVy()-e.getY());
										new_ec = depx + depy;
									}
									
									if(iecart > ecart)
										if(ecart < getAgent().getRange()+1)
											listAction.add(new AgentAction(5));
										else {
											if(new_ec < ecart ) {
												if (getEtat().isLegalMoveBbm(new AgentAction(i), getAgent()) & !getEtat().getMap().isStuck(new AgentAction(i).getVx()+getAgent().getX(),new AgentAction(i).getVy()+getAgent().getY()))
													listAction.add(new AgentAction(i));
												else {
													
													getEtat().getMap().setStuck(getAgent().getX(),getAgent().getY());
													
													switch (i){
													case 2: //Map.EAST
														
														if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
															listAction.add(new AgentAction(Map.NORTH ));
														if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
															listAction.add(new AgentAction(Map.SOUTH ));
														
														if (!getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
															if (getEtat().isLegalMoveBbm(new AgentAction(Map.WEST), getAgent()))
																listAction.add(new AgentAction(Map.WEST ));
														
														
														break;
														
													case 3: //Map.WEST
														
														if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
															listAction.add(new AgentAction(Map.NORTH ));
														if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
															listAction.add(new AgentAction(Map.SOUTH ));
														
														if (!getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
															if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
																listAction.add(new AgentAction(Map.EAST ));
														
														
														break;
														
													case 0: //Map.NORTH
														
														if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
															listAction.add(new AgentAction(Map.EAST));
														if (getEtat().isLegalMoveBbm(new AgentAction( Map.WEST), getAgent()))
																listAction.add(new AgentAction( Map.WEST ));
														
														if (!getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.WEST ), getAgent()))
															if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
																listAction.add(new AgentAction(Map.SOUTH ));
														
														break;
														
													case 1: //Map.SOUTH
														
														if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
															listAction.add(new AgentAction(Map.EAST));
														if (getEtat().isLegalMoveBbm(new AgentAction( Map.WEST), getAgent()))
															listAction.add(new AgentAction( Map.WEST ));
														
														if (!getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.WEST ), getAgent()))
															if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH), getAgent()))
																listAction.add(new AgentAction(Map.NORTH ));
														
														
														break;
													default : listAction.add(new AgentAction( Map.STOP ));
													}
												}
													
											}
										}
									else if(i_new_ec < iecart ) 
											if (getEtat().isLegalMoveBbm(new AgentAction(i), getAgent()) & !getEtat().getMap().isStuck(new AgentAction(i).getVx()+getAgent().getX(),new AgentAction(i).getVy()+getAgent().getY()))
												listAction.add(new AgentAction(i));
											else {
												
												getEtat().getMap().setStuck(getAgent().getX(),getAgent().getY());
												
												switch (i){
												case 2: //Map.EAST
													
													if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
														listAction.add(new AgentAction(Map.NORTH ));
													if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
														listAction.add(new AgentAction(Map.SOUTH ));
													
													if (!getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
														if (getEtat().isLegalMoveBbm(new AgentAction(Map.WEST), getAgent()))
															listAction.add(new AgentAction(Map.WEST ));
													
													
													break;
													
												case 3: //Map.WEST
													
													if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
														listAction.add(new AgentAction(Map.NORTH ));
													if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
														listAction.add(new AgentAction(Map.SOUTH ));
													
													if (!getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
														if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
															listAction.add(new AgentAction(Map.EAST ));
													
													
													break;
													
												case 0: //Map.NORTH
													
													if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
														listAction.add(new AgentAction(Map.EAST));
													if (getEtat().isLegalMoveBbm(new AgentAction( Map.WEST), getAgent()))
															listAction.add(new AgentAction( Map.WEST ));
													
													if (!getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.WEST ), getAgent()))
														if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
															listAction.add(new AgentAction(Map.SOUTH ));
													
													break;
													
												case 1: //Map.SOUTH
													
													if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
														listAction.add(new AgentAction(Map.EAST));
													if (getEtat().isLegalMoveBbm(new AgentAction( Map.WEST), getAgent()))
														listAction.add(new AgentAction( Map.WEST ));
													
													if (!getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.WEST ), getAgent()))
														if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH), getAgent()))
															listAction.add(new AgentAction(Map.NORTH ));
													
													
													break;
												default : listAction.add(new AgentAction( Map.STOP ));
												}
											}
									
								}// fin du check items
								else {
									int xec = Math.abs(x-e.getX());
									int yec = Math.abs(y-e.getY());
									aux_ecart = xec + yec;
									
									if(aux_ecart < ecart) {
										ecart = aux_ecart;
										int depx = Math.abs(x+Action.getVx()-e.getX());
										int depy = Math.abs(y+Action.getVy()-e.getY());
										new_ec = depx + depy;
									}
									
									
									if(ecart < getAgent().getRange()+1)
										listAction.add(new AgentAction(5));
									else {
										if(new_ec < ecart ) {
											if (getEtat().isLegalMoveBbm(new AgentAction(i), getAgent()) & !getEtat().getMap().isStuck(new AgentAction(i).getVx()+getAgent().getX(),new AgentAction(i).getVy()+getAgent().getY()))
												listAction.add(new AgentAction(i));
											else {
												
												getEtat().getMap().setStuck(getAgent().getX(),getAgent().getY());
												
												switch (i){
												case 2: //Map.EAST
													
													if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
														listAction.add(new AgentAction(Map.NORTH ));
													if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
														listAction.add(new AgentAction(Map.SOUTH ));
													
													if (!getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
														if (getEtat().isLegalMoveBbm(new AgentAction(Map.WEST), getAgent()))
															listAction.add(new AgentAction(Map.WEST ));
													
													
													break;
													
												case 3: //Map.WEST
													
													if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
														listAction.add(new AgentAction(Map.NORTH ));
													if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
														listAction.add(new AgentAction(Map.SOUTH ));
													
													if (!getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
														if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
															listAction.add(new AgentAction(Map.EAST ));
													
													
													break;
													
												case 0: //Map.NORTH
													
													if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
														listAction.add(new AgentAction(Map.EAST));
													if (getEtat().isLegalMoveBbm(new AgentAction( Map.WEST), getAgent()))
															listAction.add(new AgentAction( Map.WEST ));
													
													if (!getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.WEST ), getAgent()))
														if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
															listAction.add(new AgentAction(Map.SOUTH ));
													
													break;
													
												case 1: //Map.SOUTH
													
													if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
														listAction.add(new AgentAction(Map.EAST));
													if (getEtat().isLegalMoveBbm(new AgentAction( Map.WEST), getAgent()))
														listAction.add(new AgentAction( Map.WEST ));
													
													if (!getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.WEST ), getAgent()))
														if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH), getAgent()))
															listAction.add(new AgentAction(Map.NORTH ));
													
													
													break;
												default : listAction.add(new AgentAction( Map.STOP ));
												}
											}
										}
										
									}
								}
							}
						}//fin boucle for pour bombes
						
					}//fin if check nb bombes
					else {
						int new_ec = 0;
						int aux_ecart = 0;
						int ecart = 40000;
						
						int aux_iecart = 0;
						int i_new_ec = 0;
						int iecart = 40000;
						
						if( items.size() > 0) {
							for(int k = 0; k < items.size(); k++) {
								Objet item = items.get(k);
								int xi = item.getObjX();
								int yi = item.getObjY();
								
								int xeci = Math.abs(xi-x);
								int yeci = Math.abs(yi-y);
								aux_iecart = xeci + yeci;
								
								
								if(aux_iecart <= iecart) {
									iecart = aux_iecart;
									//System.out.println("			ecart : "+ iecart );
									int depxi = Math.abs(x+Action.getVx()-xi);
									int depyi = Math.abs(y+Action.getVy()-yi);
									i_new_ec = depxi + depyi;
									//System.out.println("   new ecart : "+ i_new_ec );
								}
									
							}//fin boucle for pour items
							
							int xec = Math.abs(x-e.getX());
							int yec = Math.abs(y-e.getY());
							aux_ecart = xec + yec;
							
							if(aux_ecart <= ecart) {
								ecart = aux_ecart;
								
								int depx = Math.abs(x+Action.getVx()-e.getX());
								int depy = Math.abs(y+Action.getVy()-e.getY());
								new_ec = depx + depy;
							}
							
							if(iecart > ecart)
								if(ecart < getAgent().getRange()+1)
									listAction.add(new AgentAction(5));
								else {
									if(new_ec < ecart ) {
										if (getEtat().isLegalMoveBbm(new AgentAction(i), getAgent()) & !getEtat().getMap().isStuck(new AgentAction(i).getVx()+getAgent().getX(),new AgentAction(i).getVy()+getAgent().getY()))
											listAction.add(new AgentAction(i));
										else {
											
											getEtat().getMap().setStuck(getAgent().getX(),getAgent().getY());
											
											switch (i){
											case 2: //Map.EAST
												
												if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
													listAction.add(new AgentAction(Map.NORTH ));
												if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
													listAction.add(new AgentAction(Map.SOUTH ));
												
												if (!getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
													if (getEtat().isLegalMoveBbm(new AgentAction(Map.WEST), getAgent()))
														listAction.add(new AgentAction(Map.WEST ));
												
												
												break;
												
											case 3: //Map.WEST
												
												if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
													listAction.add(new AgentAction(Map.NORTH ));
												if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
													listAction.add(new AgentAction(Map.SOUTH ));
												
												if (!getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
													if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
														listAction.add(new AgentAction(Map.EAST ));
												
												
												break;
												
											case 0: //Map.NORTH
												
												if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
													listAction.add(new AgentAction(Map.EAST));
												if (getEtat().isLegalMoveBbm(new AgentAction( Map.WEST), getAgent()))
														listAction.add(new AgentAction( Map.WEST ));
												
												if (!getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.WEST ), getAgent()))
													if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
														listAction.add(new AgentAction(Map.SOUTH ));
												
												break;
												
											case 1: //Map.SOUTH
												
												if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
													listAction.add(new AgentAction(Map.EAST));
												if (getEtat().isLegalMoveBbm(new AgentAction( Map.WEST), getAgent()))
													listAction.add(new AgentAction( Map.WEST ));
												
												if (!getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.WEST ), getAgent()))
													if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH), getAgent()))
														listAction.add(new AgentAction(Map.NORTH ));
												
												
												break;
											default : listAction.add(new AgentAction( Map.STOP ));
											}
										}
									}
								}
							else if(i_new_ec < iecart ) {
									if (getEtat().isLegalMoveBbm(new AgentAction(i), getAgent()) & !getEtat().getMap().isStuck(new AgentAction(i).getVx()+getAgent().getX(),new AgentAction(i).getVy()+getAgent().getY()))
										listAction.add(new AgentAction(i));
									else {
										
										getEtat().getMap().setStuck(getAgent().getX(),getAgent().getY());
										
										switch (i){
										case 2: //Map.EAST
											
											if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
												listAction.add(new AgentAction(Map.NORTH ));
											if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
												listAction.add(new AgentAction(Map.SOUTH ));
											
											if (!getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
												if (getEtat().isLegalMoveBbm(new AgentAction(Map.WEST), getAgent()))
													listAction.add(new AgentAction(Map.WEST ));
											
											
											break;
											
										case 3: //Map.WEST
											
											if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
												listAction.add(new AgentAction(Map.NORTH ));
											if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
												listAction.add(new AgentAction(Map.SOUTH ));
											
											if (!getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
												if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
													listAction.add(new AgentAction(Map.EAST ));
											
											
											break;
											
										case 0: //Map.NORTH
											
											if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
												listAction.add(new AgentAction(Map.EAST));
											if (getEtat().isLegalMoveBbm(new AgentAction( Map.WEST), getAgent()))
													listAction.add(new AgentAction( Map.WEST ));
											
											if (!getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.WEST ), getAgent()))
												if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
													listAction.add(new AgentAction(Map.SOUTH ));
											
											break;
											
										case 1: //Map.SOUTH
											
											if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
												listAction.add(new AgentAction(Map.EAST));
											if (getEtat().isLegalMoveBbm(new AgentAction( Map.WEST), getAgent()))
												listAction.add(new AgentAction( Map.WEST ));
											
											if (!getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.WEST ), getAgent()))
												if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH), getAgent()))
													listAction.add(new AgentAction(Map.NORTH ));
											
											
											break;
										default : listAction.add(new AgentAction( Map.STOP ));
										}
								}
							}
									 
										
							
						}// fin du check items
						else {
							int xec = Math.abs(x-e.getX());
							int yec = Math.abs(y-e.getY());
							aux_ecart = xec + yec;
							
							if(aux_ecart < ecart) {
								ecart = aux_ecart;
								int depx = Math.abs(x+Action.getVx()-e.getX());
								int depy = Math.abs(y+Action.getVy()-e.getY());
								new_ec = depx + depy;
							}
							
							
							if(ecart < getAgent().getRange()+1)
								listAction.add(new AgentAction(5));
							else {
								if(new_ec < ecart ) {
									//System.out.println(			ecart);
									if (getEtat().isLegalMoveBbm(new AgentAction(i), getAgent()) & !getEtat().getMap().isStuck(new AgentAction(i).getVx()+getAgent().getX(),new AgentAction(i).getVy()+getAgent().getY()))
										listAction.add(new AgentAction(i));
									
									else {
										
										getEtat().getMap().setStuck(getAgent().getX(),getAgent().getY());
										
										switch (i){
										case 2: //Map.EAST
											
											if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
												listAction.add(new AgentAction(Map.NORTH ));
											if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
												listAction.add(new AgentAction(Map.SOUTH ));
											
											if (!getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
												if (getEtat().isLegalMoveBbm(new AgentAction(Map.WEST), getAgent()))
													listAction.add(new AgentAction(Map.WEST ));
											
											
											break;
											
										case 3: //Map.WEST
											
											if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
												listAction.add(new AgentAction(Map.NORTH ));
											if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
												listAction.add(new AgentAction(Map.SOUTH ));
											
											if (!getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH ), getAgent()))
												if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
													listAction.add(new AgentAction(Map.EAST ));
											
											
											break;
											
										case 0: //Map.NORTH
											
											if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
												listAction.add(new AgentAction(Map.EAST));
											if (getEtat().isLegalMoveBbm(new AgentAction( Map.WEST), getAgent()))
													listAction.add(new AgentAction( Map.WEST ));
											
											if (!getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.WEST ), getAgent()))
												if (getEtat().isLegalMoveBbm(new AgentAction(Map.SOUTH), getAgent()))
													listAction.add(new AgentAction(Map.SOUTH ));
											
											break;
											
										case 1: //Map.SOUTH
											
											if (getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()))
												listAction.add(new AgentAction(Map.EAST));
											if (getEtat().isLegalMoveBbm(new AgentAction( Map.WEST), getAgent()))
												listAction.add(new AgentAction( Map.WEST ));
											
											if (!getEtat().isLegalMoveBbm(new AgentAction(Map.EAST), getAgent()) & !getEtat().isLegalMoveBbm(new AgentAction(Map.WEST ), getAgent()))
												if (getEtat().isLegalMoveBbm(new AgentAction(Map.NORTH), getAgent()))
													listAction.add(new AgentAction(Map.NORTH ));
											
											
											break;
										default : listAction.add(new AgentAction( Map.STOP ));
										}
									}
								}
								
							}
						}
						
						
					}//fin else check bombes
					
					
				}//fin boucle for pour bomberman
				
			}//fin boucle for pour les actions
				
		}
		
		if (comptBW == 1 & comptW >= 1 )
			return new AgentAction(5);
		else {
			if (comptBW > 1 )
				return new AgentAction(5);
			else {
				if  (listAction.size() > 0) return (listAction.get((int)(Math.random()*listAction.size())));
				else return new AgentAction(5);
			}
		}
	}
	
	public Agent_Bomberman getAgent() {
		return this.agent;
	}


}
