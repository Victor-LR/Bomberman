package learning;

import map.GameState;

public interface Reward 
{
	public double getReward(GameState to, int nb_murs, int nb_points, int nb_bonus);
}