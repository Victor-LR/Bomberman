package learning.test;

import java.util.ArrayList;

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
import agents.Agent;
import agents.Agent_Bomberman;
import game.BombermanGame;
import game.Game;
import map.NextGameState;
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
//			
//			System.out.println("		Strat GameS:"+GameS.getStrats()[0]);
			
			NextGameState state_b = new NextGameState(GameS);
			

			
			GameState state = state_b.copy();


			
			//BombermanGame BbmG = new BombermanGame(state,state.getMap());
			
			int taille =3;
			Sensor s = new Sensor(new SimpleStateSensor(taille));
			Reward r = new SimpleReward();
	
			LabeledSet train = new LabeledSet(s.size());
			
			for (int i = 0; i < 250; i++) {
				AgentFitted agent_f = new AgentFitted(state.getBombermans().get(0));
	 			RewardTools.getReward(state, agent_f,r,100);
				for (Quadruplet f : agent_f.getList()) {
					train.addExample(f.getAtteint(), f.getR_obtenu());
				}
			}
			
			Perceptron p = new Perceptron(0.02,taille);  // epsilone=0.02
			//System.out.println("Nombre d'échantillon : " + train.size());
			p.setNb_iteration(1); //150 iterations
			p.train(train);
			PerceptronAgent agent_bomberman = new PerceptronAgent(state.getBombermans().get(0),s, p);
			state_b.setBomberman(0, agent_bomberman);
			
			//System.out.println(RewardTools.getAverageReward(state_b, agent_bomberman, r, 150, 1));
			
			System.out.println(state_b.getBombermans().get(0).getClass().getName());
			RewardTools.vizualize(state_b, agent_bomberman, r, 100,150);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
