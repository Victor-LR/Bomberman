package learning;

import java.util.ArrayList;

import agents.AgentAction;
import agents.Agent_Bomberman;
import learning.perceptron.SparseVector;
import map.GameState;
import map.NextGameState;

public class AgentFitted {

		private Agent_Bomberman agent ;
	 	private ArrayList<Quadruplet> lis;
	 	
	 	public AgentFitted() {
			this.lis= new ArrayList<>();
		}
	
	public AgentAction getAction( NextGameState state) {
		// TODO Auto-generated method stub
		
		agent = state.getBombermans().get(0);
		Sensor s = new Sensor(new SimpleStateSensor(4));
		
		AgentAction aa = agent.chooseAction(state);
		SparseVector init = s.getVector(state, aa);
		
		GameState game = state.copy();
		state.taketurn(aa,agent.getId());
		
		SparseVector atteint = s.getVector(state, aa);
		Reward rw = new SimpleReward();
	
		double score = rw.getReward(game, state);
		
		lis.add(new Quadruplet(init, aa, atteint, score));
		return aa;
	} 
	
	public ArrayList<Quadruplet> getList() {
		return lis;
	}

}
