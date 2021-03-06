package learning.test;


import org.apache.commons.lang3.SerializationUtils;

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
import game.BombermanGame;
import map.GameState;
import map.Map;

public class Perceptron_0 {

	public static void main(String args[]) {
//		MazeWritable maze=new MazeWritable("layouts/smallClassic.lay");
//		GameStateWritable state = new GameStateWritable(maze);
//		Game game = new Game(state);
		
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
				
				//state.setBomberman(0, agent_f);
	 			RewardTools.getReward(state, agent_f,r,250);
	 			
	 			System.out.println(state.getBombermans().get(0).getClass().getName());
	 			
	 			System.out.println("			: "+agent_f.getList().size());
	 			
				for (Quadruplet f : agent_f.getList()) {
					
					train.addExample(f.getAtteint(), f.getR_obtenu());
				}
			}
			
			Perceptron p = new Perceptron(0.02,taille);  // epsilone=0.02
			System.out.println("Nombre d'échantillon : " + train.size());
			p.setNb_iteration(1); //150 iterations
			p.train(train);
			PerceptronAgent agent_bomberman = new PerceptronAgent(state.getBombermans().get(0),s, p);
			GameS.setBomberman(0, agent_bomberman);
			
			RewardTools.vizualize(GameS, agent_bomberman, r, 1000,150);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
