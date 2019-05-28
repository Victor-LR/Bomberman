package learning;

import game.BombermanGame;
import game.Game;
import graphics.Cadre_Jeu;

import agents.AgentAction;
import agents.Agent_Bomberman;
import agents.ColorBomberman;
import map.GameState;
import map.Map;


import org.apache.commons.lang3.SerializationUtils;


public class RewardTools {


	public static double getReward(GameState initial_state, Agent_Bomberman bomberman_agent,Reward reward,int size_max_trajectory)

	{
		GameState next_game = (GameState)SerializationUtils.clone(initial_state);
		//System.out.println(next_game.getBombermans().get(0).getClass().getName());
		//bomberman_agent = next_game.getBombermans().get(0);
		double rs=0;
		int time=0;
		boolean flag=true;		
		while(flag)
		{
			bomberman_agent.setX(next_game.getBombermans().get(0).getX());
			bomberman_agent.setY(next_game.getBombermans().get(0).getY());
			AgentAction action = bomberman_agent.chooseAction(next_game);
			
			int nbmurs = next_game.getBombermans().get(0).getNb_murs();
			int nbpoints = next_game.getBombermans().get(0).getPoints();
			int nbbonus = next_game.getBombermans().get(0).getNb_bonus();
			boolean axe_bombe = next_game.getBombermans().get(0).isAxe_bombe();
					
			next_game.taketurn(action, next_game.getBombermans().get(0).getId());
			//next_game.getBbmG().setTurn(next_game.getBbmG().getTurn()+1);
			
			if (next_game.getFinPartie() != GameState.GAME_OVER & next_game.getFinPartie() != GameState.WIN_SOLO)
			{
				double r = reward.getReward(next_game, nbmurs, nbpoints, nbbonus, axe_bombe);
				rs+=r;
			}
			else
			{
				double r=reward.getReward(next_game, nbmurs, nbpoints, nbbonus, axe_bombe);
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

	public static double getAverageReward(GameState initial_state,Agent_Bomberman bomberman_agent, Reward reward,int size_max_trajectory,int nb_trajectories)

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

	
	public static void vizualize(GameState initial_state, Agent_Bomberman bomberman_agent,Reward reward,int size_max_trajectory,int timestep)
	{
		
		BombermanGame BbmG = new BombermanGame(initial_state, initial_state.getMap());
		BbmG.setTemps(timestep);
		BbmG.setMaxTurn(size_max_trajectory);
		String sep = "/";
		String stage[] = initial_state.getMap().getFilename().split(sep);
		String sep2 = ".lay";
		String stage2[] = stage[2].split(sep2);
		BbmG.setFilename(stage2[0]);
		BbmG.init();
		
		
		BbmG.etatJeu.setStrats(initial_state.getStrats());
		BbmG.etatJeu.setCampagne(initial_state.getCampagne());
		
		
		Cadre_Jeu fenetre = new Cadre_Jeu(BbmG);
		
		bomberman_agent.setCouleur(ColorBomberman.DEFAULT);
		BbmG.etatJeu.setBomberman(0, bomberman_agent);
		
		fenetre.setVisible(true);
		BbmG.launch();
//		GameState fstate=game.runUntilEnd(timestep,size_max_trajectory);				
	}	

}
