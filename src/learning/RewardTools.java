package learning;

import java.awt.Dimension;
import java.util.ArrayList;

import agents.AgentAction;
import agents.Agent_Bomberman;
import agents.Agent_Ennemy;
import map.GameState;
import map.NextGameState;

public class RewardTools {

	public static double getReward(GameState initial_state,Agent_Bomberman bomberman_agent,ArrayList<Agent_Ennemy> ennemy_agents,Reward reward,int size_max_trajectory)
	{
		NextGameState next_game = new NextGameState(initial_state);
		double rs=0;
		int time=0;
		boolean flag=true;		
		while(flag)
		{
			NextGameState iv = next_game;
			bomberman_agent = (Agent_Bomberman) next_game.getBombermans().get(0);
			AgentAction action = bomberman_agent.chooseAction(next_game);
			next_game.taketurn(action, bomberman_agent.getId());
			
			System.out.println(bomberman_agent.getX() +"		"+	bomberman_agent.getY());
			
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
	
	public static double getAverageReward(GameState initial_state,Agent_Bomberman bomberman_agent,ArrayList<Agent_Ennemy> ennemy_agents,Reward reward,int size_max_trajectory,int nb_trajectories)
	{
		double cpt=0;
		for(int i=0;i<nb_trajectories;i++){
			cpt+=getReward(initial_state, bomberman_agent, ennemy_agents, reward, size_max_trajectory);
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
	/*public static void vizualize(GameState initial_state,Agent pacman_agent,ArrayList<Agent> ghosts_agents,Reward reward,int size_max_trajectory,int timestep)
	{
		Game game=new Game(initial_state);
		game.addPacmanAgent(pacman_agent);
				
		for(Agent a:ghosts_agents)
			game.addGhostAgent(a);	
		
		GamePanel panel=new GamePanel(initial_state);
		game.addObserver(panel);			
		JFrame frame = new JFrame("FrameDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(640,480));
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		GameState fstate=game.runUntilEnd(timestep,size_max_trajectory);				
	}	*/

}
