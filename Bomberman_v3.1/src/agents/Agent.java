package agents;
//import java.io.Serializable;
import java.util.ArrayList;

import agents.AgentAction;
import agents.AgentType;
import map.Map;
import map.GameState;

//@SuppressWarnings("serial")
public class Agent{
	
	static int current_Id = 0;
	
	private int x;
	private int y;
	private int id;
	private AgentType agent;
	
	private int direction;
	
	private boolean isDead;
	
	public Agent(AgentType ag, int px, int py){
		this.x = px;
		this.y = py;
		this.id = Agent.current_Id;
		this.direction = Map.SOUTH;
		current_Id++;
		this.agent = ag;
		this.isDead = false;
	}
	
	//créé une liste d'AgentAction qui retourne une action possible aléatoire entre toutes celles possible 
	
	public AgentAction chooseAction(GameState etatjeu) 
	{
		ArrayList<AgentAction> listAction=new ArrayList<AgentAction>();
		for(int i=0;i<5;i++)
		{
			if (etatjeu.isLegalMove(new AgentAction(i), this))
				listAction.add(new AgentAction(i));
		}		
		return(listAction.get((int)(Math.random()*listAction.size())));
	}
	
	//setteur et setteur pour y
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	//setteur et setteur pour x

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	//getteur pour l'agent

	public AgentType getAgentype() {
		return agent;
	}
	
	//getteur et setteur pour l'id
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	//setteur et setteur pour la direction
	
	public int getDirection() {
		return direction;
	}


	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	//setteur et setteur pour la la variable isDead


	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	
}



/*Créer une sous classe d'agent permettant de définir un Agent Bomberman reprenant les même spécificité 
 *d'un agent (un ennemi en l'occurence) et lui rajoutant certaines méthode commme par exemple la possibilité 
 *de poser des bombes*/
