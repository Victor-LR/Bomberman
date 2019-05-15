package learning.test;

import game.BombermanGame;

import java.util.ArrayList;



import learning.Reward;
import learning.RewardTools;
import learning.SimpleReward;
import map.GameState;
import map.Map;
import agents.Agent_B;
import agents.Agent_Bomberman;
import agents.Agent_Ennemy;


//Test la moyenne des points obtenues par trajectoires sur un certain nombre de parties.
public class TestGetAverageReward {
	public static void main(String args[])
	{

		
		BombermanGame BbmG = new BombermanGame();
		
		Map map;
		try {

			for (int i=1; i<=10; i++){
				map = new Map("./layout/perceptron.lay");
				GameState initial_state  = new GameState(map,BbmG);
				int strat[] = {0};
				initial_state.setStrats(strat);
				initial_state.setCampagne(false);
				Agent_Bomberman bomberman = initial_state.getBombermans().get(0);
        
				Reward rew = new SimpleReward();
				System.out.println(i+" : "+RewardTools.getAverageReward(initial_state, bomberman, rew, 150, 100));
				//RewardTools.vizualize(game,bomberman,rew,100,100);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

		
	
	}
}
