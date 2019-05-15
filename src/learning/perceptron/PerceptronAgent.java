package learning.perceptron;

import java.util.ArrayList;
import agents.AgentAction;
import agents.Agent_B;
import agents.Agent_Bomberman;
import learning.Sensor;
import map.GameState;

public class PerceptronAgent implements Agent_B {

	private Sensor s;
	private Perceptron p;
	
	
	public PerceptronAgent(Sensor senseur,Perceptron perce)
	{
		p=perce;
		s=senseur;
		
	}
	
	@Override
	public AgentAction chooseAction( GameState state) {
		ArrayList<AgentAction> aa =new ArrayList<AgentAction>();
		Agent_Bomberman as = state.getBombermans().get(0);
		double scoreMax=Double.NEGATIVE_INFINITY;
		int ind=0;
		for(int i=0 ; i<4 ;i++){
			if(state.isLegalMoveBbm(new AgentAction(i), as))
				aa.add(new AgentAction(i));
		}
		for(int i=0;i<aa.size();i++){
			SparseVector v= s.getVector(state, aa.get(i));
			if(scoreMax<p.getScore(v)){
				scoreMax=p.getScore(v);
				ind=i;
			}
		}
		return aa.get(ind);
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	
}