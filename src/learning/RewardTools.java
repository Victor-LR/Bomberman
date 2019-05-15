package learning;

import game.BombermanGame;
import graphics.Cadre_Jeu;

import java.awt.Dimension;
import java.util.ArrayList;

import agents.AgentAction;
import agents.Agent_Bomberman;
import agents.Agent_Ennemy;
import map.GameState;
import map.NextGameState;

public class RewardTools {

	public static double getReward(GameState initial_state,Agent_Bomberman bomberman_agent,Reward reward,int size_max_trajectory)
	{
		NextGameState next_game = new NextGameState(initial_state);
		double rs=0;
		int time=0;
		boolean flag=true;		
		while(flag)
		{
			GameState iv = next_game.copy();
			bomberman_agent = next_game.getBombermans().get(0);
			AgentAction action = bomberman_agent.chooseAction(next_game);
			next_game.taketurn(action, bomberman_agent.getId());
			
			//System.out.println(bomberman_agent.getX() +"		"+	bomberman_agent.getY());
			
			//System.out.println(iv.getBrokableWals() +"		"+	next_game.getBrokableWals());
			
			if (next_game.getFinPartie() != GameState.GAME_OVER & next_game.getFinPartie() != GameState.WIN_SOLO)
			{
				double r = reward.getReward(iv,next_game);
				rs+=r;
			}
			else
			{
				double r=reward.getReward(iv, next_game);
				rs+=r;
				flag=false;
			}
			
			if (next_game.getFinPartie() == GameState.GAME_OVER || next_game.getFinPartie() == GameState.WIN_SOLO)
				flag=false;
			
			if (time++>=(size_max_trajectory-1))
				flag=false;			
		}
		 
		return(rs);
	}	
	
	public static double getAverageReward(GameState initial_state,Agent_Bomberman bomberman_agent,Reward reward,int size_max_trajectory,int nb_trajectories)
	{
		double cpt=0;
		for(int i=0;i<nb_trajectories;i++){
			cpt+=getReward(initial_state, bomberman_agent, reward, size_max_trajectory);
		}
		return( cpt/nb_trajectories);
	}
	
	/**
	 * Permet de visualiser un agent
	 * @param initial_state
	 * @param pacman_agent
	 * @param ghosts_agents
	 * @param reward
	 * @param timestep
	 */
	
	public static void vizualize(GameState initial_state,Agent_Bomberman bomberman_agent,Reward reward,int size_max_trajectory,int timestep)
	{
		
		BombermanGame BbmG = new BombermanGame(initial_state, initial_state.getMap());
		BbmG.setTemps(timestep);
		//BbmG.init();
		
		Cadre_Jeu fenetre = new Cadre_Jeu(BbmG);
		
		fenetre.setVisible(true);
	//	BbmG.launch();
//		GameState fstate=game.runUntilEnd(timestep,size_max_trajectory);				
	}	

}
