package learning.test;

import game.BombermanGame;

import java.util.ArrayList;



import learning.Reward;
import learning.RewardTools;
import learning.SimpleReward;
import map.GameState;
import map.Map;
import agents.Agent_Bomberman;
import agents.Agent_Ennemy;


//Test la moyenne des points obtenues par trajectoires sur un certain nombre de parties.
public class TestGetAverageReward {
	public static void main(String args[])
	{
		
//		BombermanGame BbmG = new BombermanGame();
//		try {
//			BbmG.loadFile("./layout/perceptron.lay");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		BbmG.init();
//		int strat[] = {7};
//		BbmG.etatJeu.setStrats(strat);
//		BbmG.launch();
		
		BombermanGame BbmG = new BombermanGame();
		
		Map map;
		try {
			map = new Map("./layout/perceptron.lay");
			GameState game  = new GameState(map,BbmG);
			int strat[] = {0};
			game.setStrats(strat);
			game.setCampagne(false);
			Agent_Bomberman bomberman = game.getBombermans().get(0);
			ArrayList<Agent_Ennemy> ennemies =new ArrayList<Agent_Ennemy>();
			
			Reward rew = new SimpleReward();
			for (int i=1; i<=10; i++)
				System.out.println(i+" : "+RewardTools.getAverageReward(game, bomberman, ennemies, rew, 100, 100));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

		
	
	}
}
