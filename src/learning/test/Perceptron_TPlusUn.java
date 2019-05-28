package learning.test;

import game.BombermanGame;
import learning.AgentFitted;
import learning.Quadruplet;
import learning.Reward;
import learning.RewardTools;
import learning.Sensor;
import learning.SimpleReward;
import learning.SimpleStateSensor;
import learning.perceptron.LabeledSet;
import learning.perceptron.Perceptron;
import learning.perceptron.PerceptronAgent;
import map.GameState;
import map.Map;

import org.apache.commons.lang3.SerializationUtils;

public class Perceptron_TPlusUn {

	public static void main(String[] args) {
		BombermanGame BG = new BombermanGame();
		
		Map map;
		try {
			map = new Map("./layout/perceptron.lay");
			GameState GameS  = new GameState(map,BG);
			int strat[] = {0};
			GameS.setStrats(strat);
			GameS.setCampagne(false);
			
			GameState state = SerializationUtils.clone(GameS);

			
			int taille =3;
			Sensor s = new Sensor(new SimpleStateSensor(taille));
			Reward r = new SimpleReward();
	
			LabeledSet train = new LabeledSet(s.size());
			
			for (int i = 0; i < 250; i++) {
				AgentFitted agent_f = new AgentFitted(state.getBombermans().get(0));
				
	 			RewardTools.getReward(state, agent_f,r,250);
	 			
	 			System.out.println(state.getBombermans().get(0).getClass().getName());
	 			
	 			System.out.println("			: "+agent_f.getList().size());
	 			
				for (Quadruplet f : agent_f.getList()) {
					
					train.addExample(f.getAtteint(), f.getR_obtenu());
				}
			}
			
			Perceptron p = new Perceptron(0.02,taille);  // epsilone=0.02
			System.out.println("Nombre d'échantillon : " + train.size());
			p.setNb_iteration(200);
			p.train(train);
			Perceptron pPlus = p.tPlusUn(train);
			PerceptronAgent agent_bomberman = new PerceptronAgent(state.getBombermans().get(0),s, pPlus);
			GameS.setBomberman(0, agent_bomberman);
			
			RewardTools.vizualize(GameS, agent_bomberman, r, 500,150);
		
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

}
