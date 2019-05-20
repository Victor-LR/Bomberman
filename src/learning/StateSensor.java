package learning;

import learning.perceptron.SparseVector;
import agents.AgentAction;
import map.GameState;

public interface StateSensor 
{
	public int size();	
	public SparseVector getVector(GameState GS);
}
