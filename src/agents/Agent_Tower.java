package agents;

import map.GameState;
import map.Map;



public class Agent_Tower extends Agent {
	
	//tableau avec les cordonnées x,y et la vie des 4 pilliers
	private int[][] coord_pilliers = new int[4][3];
	
	private boolean[] pill_detruit = new boolean[4];
	
	
	private boolean hurt;
	private int etat;
	
	public Agent_Tower (int px, int py,int id) {
		super(AgentType.TOWER, px, py);
		super.setId(id);
		this.coord_pilliers[0][0] = this.getX()-1;
		this.coord_pilliers[0][1] = this.getY()-1;
		this.coord_pilliers[0][2] = 3;
		this.pill_detruit[0] = false;
		
		this.coord_pilliers[1][0] = this.getX()+1;
		this.coord_pilliers[1][1] = this.getY()-1;
		this.coord_pilliers[1][2] = 3;
		this.pill_detruit[1] = false;
		
		this.coord_pilliers[2][0] = this.getX()-1;
		this.coord_pilliers[2][1] = this.getY()+1;
		this.coord_pilliers[2][2] = 3;
		this.pill_detruit[2] = false;
		
		this.coord_pilliers[3][0] = this.getX()+1;
		this.coord_pilliers[3][1] = this.getY()+1;
		this.coord_pilliers[3][2] = 3;
		this.pill_detruit[3] = false;
		
		
		this.setEtat(0);

	}
	
	public AgentAction chooseAction(GameState etatjeu) {
		return new AgentAction(Map.STOP);
	}

	public int[][] getCoord_pilliers() {
		return coord_pilliers;
	}

	public void setCoord_pilliers(int[][] coord_pilliers) {
		this.coord_pilliers = coord_pilliers;
	}

	
	public int getPill_life(int i) {
		return this.coord_pilliers[i][2];
	}

	public void setPill_life(int i, int life) {
		this.coord_pilliers[i][2] = life;
	}
	
	
	
	public boolean getPill_detruit(int i) {
		return pill_detruit[i];
	}

	public void setPill_detruit(int i, boolean detruit) {
		this.pill_detruit[i] = detruit;
	}

	public boolean isHurt() {
		return hurt;
	}

	public void setHurt(boolean hurt) {
		this.hurt = hurt;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}



}