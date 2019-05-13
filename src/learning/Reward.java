package learning;

import map.GameState;

public interface Reward 
{
	public double getReward(GameState from,GameState to);
}