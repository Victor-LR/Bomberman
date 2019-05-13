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
		
		BombermanGame BbmG = new BombermanGame();
		BbmG.setFilename("layouts/smallClassic.lay");
		BbmG.init();
		int strat[] = {0};
		BbmG.etatJeu.setStrats(strat);
		BbmG.launch();
		GameState state = BbmG.etatJeu;
		Agent_Bomberman bomberman = state.getBombermans().get(0);
		ArrayList<Agent_Ennemy> ennemies =new ArrayList<Agent_Ennemy>();
//		for(int i=0; i<state.getEnnemies().size(); i++)
//			ghosts_agents.add(new IntelligentGhost_Agent2(0.1));
		
		Reward rew = new SimpleReward();
		for (int i=1; i<=10; i++)
			System.out.println(i+" : "+RewardTools.getAverageReward(state, bomberman, ennemies, rew, 500, 100));
	
	}
}
