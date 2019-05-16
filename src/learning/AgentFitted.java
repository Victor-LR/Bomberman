package learning;

import java.util.ArrayList;

import org.apache.commons.lang3.SerializationUtils;

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
		
//		NextGameState aux_state = new NextGameState(state);
		
		SimpleStateSensor s_init =  new SimpleStateSensor(3);
		s_init.getVector(state);
		
		Sensor s = new Sensor(s_init);
		
		
		AgentAction aa = super.chooseAction(state);
		SparseVector init = s.getVector(state, aa);
		
		GameState game = SerializationUtils.clone(state);
		
		int nb_murs = game.getBrokableWals();
		
		game.taketurn(aa,this.getId());
		
		SparseVector atteint = s.getVector(game, aa);
		Reward rw = new SimpleReward();
	
		double score = rw.getReward(game, nb_murs);
		
		lis.add(new Quadruplet(init, aa, atteint, score));
		return aa;
	} 
	
	public ArrayList<Quadruplet> getList() {
		return lis;
	}




}
