package agents;

import java.io.Serializable;

import map.Map;

public class AgentAction implements Serializable {

	private int vx;
	private int vy;
	
	private int action;
	
	// détermine le changement des coordonnées en fonction de l'action passé en paramètre
	public  AgentAction(int dir){
		this.action = dir;
		if (dir < 5) deplacement();
		else 
		{
			vx=0;
			vy=0;
		}
	}
	

	public void deplacement(){
	
		if (action==Map.STOP)
		{
			vx=0;
			vy=0;
		}
		else
		if (action==Map.STOP_BIRD)
		{
			vx=0;
			vy=0;
		}
		else
		if (action==Map.NORTH)
		{
			vx=0;
			vy=-1;
		}
		else if (action==Map.SOUTH)
		{
			vx=0;
			vy=1;			
		}
		else if (action==Map.EAST)
		{
			vy=0;
			vx=+1;
		}
		else if (action==Map.WEST)
		{
			vy=0;
			vx=-1;			
		}
		
	}
	
	public int getVx(){
		return vx;
	}
	
	public int getVy(){
		return vy;
	}
	
	public int getAction(){
		return action;
	}

}
