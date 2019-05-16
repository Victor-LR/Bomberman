package learning;

import java.util.ArrayList;

import agents.AgentAction;
import agents.Agent_Bomberman;
import learning.perceptron.SparseVector;
import map.GameState;
import map.NextGameState;

public class AgentFitted extends Agent_Bomberman {

		//private Agent_B agent ;
	 	private ArrayList<Quadruplet> lis;
	 	
	 	public AgentFitted(Agent_Bomberman ag_bbm) {
	 		super(ag_bbm.getX(),ag_bbm.getY(),ag_bbm.getId());
			this.lis= new ArrayList<>();
		}

	public AgentAction chooseAction( GameState state) {
		
//		agent = state.getBombermans().get(0);
		
		NextGameState aux_state = new NextGameState(state);
		Sensor s = new Sensor(new SimpleStateSensor(4));
		
		AgentAction aa = this.chooseAction(state);
		SparseVector init = s.getVector(state, aa);
		
		GameState game = aux_state.copy();
		aux_state.taketurn(aa,this.getId());
		
		SparseVector atteint = s.getVector(aux_state, aa);
		Reward rw = new SimpleReward();
	
		double score = rw.getReward(game, aux_state);
		
		lis.add(new Quadruplet(init, aa, atteint, score));
		return aa;
	} 
	
	public ArrayList<Quadruplet> getList() {
		return lis;
	}




}
