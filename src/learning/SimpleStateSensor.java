package learning;

import java.util.ArrayList;

import learning.perceptron.SparseVector;
import map.GameState;

public class SimpleStateSenseur implements StateSensor	{

	private int n;
	private SparseVector vector;
	
	
	public SimpleStateSenseur(int n){
		this.n=n;
		vector=new SparseVector(n*n* 3);
	}
	
	public SparseVector getVector(GameState s) {
		environnement(s);
		return vector;
	}
	
	
	@Override
	public int size() {
		return n*n*3;
	}

	
	public void environnement(GameState s){
		int ab1;
		int ab2;
		int or1;
		int or2;
		int N=n/2;
		int x=s.getBombermans().get(0).getX();
		int y=s.getBombermans().get(0).getX();;
		
		if (x-N <0){
			ab1=0;
		}
		else {
			ab1=x-N;
		}
		
		if (x+N >s.getMap().getSizeX()-1){
			ab2=s.getMap().getSizeX()-1;
		}
		else {
			ab2=x+N;
		}
		
		if (y-N <0){
			or1=0;
		}
		else {
			or1=y-N;
		}
		
		if (y+N >s.getMap().getSizeY()-1){
			or2=s.getMap().getSizeY()-1;
		}
		else {
			or2=y+N;
		}
		//SparseVector res = new SparseVector(size());
		int cpt=0;
		for (int i=ab1; i<= ab2; i++){
			for (int j=or1; j<= or2; j++){				
				
				if (s.isEnnemie(i, j)){ //si un fantome
					vector.setValue(cpt, 1);
				}
				else
					vector.setValue(cpt,0);
				cpt++;
				
				if (s.getMap().isWall(i, j)){ //si c'est un mur
					vector.setValue(cpt, 1);
				}
				else
					vector.setValue(cpt,0);
				cpt++;
				
				if (s.isBrokable_Wall(i, j)){ // si c'est un mur cassable
					vector.setValue(cpt, 1);
				}
				else
					vector.setValue(cpt,0);
				cpt++;
			}
		}
		
	}
	
/*
	public static void main(String args[]) throws MazeException
	{
		MazeWritable maze=new MazeWritable("layouts/bigSafeSearch.lay");
		GameStateWritable state=new GameStateWritable(maze);
		
	
		
		Agent pac=new RandomAgent();
		ArrayList<Agent> ghosts_agents=new ArrayList<Agent>();
		Reward r= new SimpleReward();	
		for(int i=0;i<state.getNumberOfGhosts();i++)
			ghosts_agents.add(new IntelligentGhost_Agent2(0.1));
		StateSensor s=new SimpleStateSenseur(3);
		SparseVector v= s.getVector(state);
		
		Sensor  sensor= new Sensor(s);
		System.out.println(" "+sensor.getVector(state, new AgentAction(1)));
	}
	*/
}