package learning;

import java.util.ArrayList;

import agents.AgentAction;
import agents.Agent_B;
import agents.Agent_Bomberman;
import learning.perceptron.SparseVector;
import map.GameState;
import map.NextGameState;

public class AgentFitted implements Agent_B {

		private Agent_B agent ;
	 	private ArrayList<Quadruplet> lis;
	 	
	 	public AgentFitted() {
			this.lis= new ArrayList<>();
		}
	@Override
	public AgentAction chooseAction( GameState state) {
		// TODO Auto-generated method stub
		
		agent = state.getBombermans().get(0);
		NextGameState aux_state = new NextGameState(state);
		Sensor s = new Sensor(new SimpleStateSensor(4));
		
		AgentAction aa = agent.chooseAction(state);
		SparseVector init = s.getVector(state, aa);
		
		GameState game = aux_state.copy();
		aux_state.taketurn(aa,agent.getId());
		
		SparseVector atteint = s.getVector(aux_state, aa);
		Reward rw = new SimpleReward();
	
		double score = rw.getReward(game, aux_state);
		
		lis.add(new Quadruplet(init, aa, atteint, score));
		return aa;
	} 
	
	public ArrayList<Quadruplet> getList() {
		return lis;
	}


	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
