package learning;

import java.util.ArrayList;

import org.apache.commons.lang3.SerializationUtils;

import agents.AgentAction;

import agents.Agent_Bomberman;
import learning.perceptron.SparseVector;
import map.GameState;

public class AgentFitted extends Agent_Bomberman {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		
		int nb_murs = game.getBombermans().get(0).getNb_murs();
		int nb_points = game.getBombermans().get(0).getPoints();
		int nb_bonus = game.getBombermans().get(0).getNb_bonus();
		boolean axe_bombe = game.getBombermans().get(0).isAxe_bombe();
		
		game.taketurn(aa,this.getId());
		
		SparseVector atteint = s.getVector(game, aa);
		Reward rw = new SimpleReward();
	
		double score = rw.getReward(game, nb_murs, nb_points, nb_bonus, axe_bombe);
		
		lis.add(new Quadruplet(init, aa, atteint, score));
		return aa;
	} 
	
	public ArrayList<Quadruplet> getList() {
		return lis;
	}




}
