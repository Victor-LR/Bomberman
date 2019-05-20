package learning.test;

import org.apache.commons.lang3.SerializationUtils;

import game.BombermanGame;
import learning.Reward;
import learning.RewardTools;
import learning.Sensor;
import learning.SimpleReward;
import learning.SimpleStateSensor;
import learning.perceptron.Perceptron;
import learning.perceptron.PerceptronAgent;
import learning.perceptron.RechercheAleatoire;
import map.GameState;
import map.Map;


public class TestAlgoAlea {
	
	public static void main(String args[])  {
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
			Sensor sens = new Sensor(new SimpleStateSensor(taille));
			Reward r = new SimpleReward();
			
			RechercheAleatoire ra = new RechercheAleatoire(50, 40, Math.random()*0.2-0.1, state,r, sens);
			for(int i=0;i<10; i++){
				ra.evoluer();
			}
			Perceptron perceptron = ra.getMeilleur();
			PerceptronAgent agent_bomberman = new PerceptronAgent(state.getBombermans().get(0),sens, perceptron);
			
			System.out.println(RewardTools.getAverageReward(state, agent_bomberman, r, 100, 100));
			GameS.setBomberman(0, agent_bomberman);
			
			RewardTools.vizualize(GameS, agent_bomberman, r, 1000, 150);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}