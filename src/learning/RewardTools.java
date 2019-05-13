package learning;

import java.awt.Dimension;
import java.util.ArrayList;

import pacman.eleves.Agent;
import agents.AgentAction;
import agents.Agent_Bomberman;
import agents.Agent_Ennemy;
import pacman.eleves.AgentState;
import map.GameState;

public class RewardTools {

	public static double getReward(GameState initial_state,Agent_Bomberman bomberman_agent,ArrayList<Agent_Ennemy> ennemy_agents,Reward reward,int size_max_trajectory)
	{
		GameState s=initial_state;
		double rs=0;
		int time=0;
		boolean flag=true;		
		while(flag)
		{
			GameState iv=s;
			AgentAction action = bomberman_agent.chooseAction(s);
			s=s.nextStatePacman(action);
			
			if ((!s.isLose()) && (!s.isWin()))
			{
				ArrayList<AgentAction> gactions=new ArrayList<AgentAction>();
				for(int i=0;i<ghosts_agents.size();i++)
				{
					Agent a=ghosts_agents.get(i);
					AgentState ss=s.getGhostState(i);
					AgentAction aa=a.getAction(ss, s);
					gactions.add(a.getAction(ss,s));				
				}			
				s=s.nextStateGhosts(gactions);
				double r=reward.getReward(iv, s);
				rs+=r;
			}
			else
			{
				double r=reward.getReward(iv, s);
				rs+=r;
				flag=false;
			}
			
			if (s.getFinPartie() == GameState.GAME_OVER || s.getFinPartie() == GameState.WIN_SOLO)
				flag=false;
			
			if (time++>=(size_max_trajectory-1))
				flag=false;			
		}
		 
		return(rs);
	}	
	
	public static double getAverageReward(GameState initial_state,Agent pacman_agent,ArrayList<Agent> ghosts_agents,Reward reward,int size_max_trajectory,int nb_trajectories)
	{
		//TODO
		double cpt=0;
		for(int i=0;i<nb_trajectories;i++){
			cpt+=getReward(initial_state, pacman_agent, ghosts_agents, reward, size_max_trajectory);
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
