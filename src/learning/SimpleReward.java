package learning;

import map.GameState;

public class SimpleReward implements Reward {

	@Override
	public double getReward(GameState from, GameState to) {
		if (to.getFinPartie() == GameState.WIN_SOLO || to.getFinPartie() == GameState.WIN_SCORE)
			return 100;
		if (to.getFinPartie() == GameState.GAME_OVER)
			return -1;
		if (to.getBrokableWals() == from.getBrokableWals()) //tout les mur de "to" sont resté intacte
			return 0;
		else //des murs de "to" on été detruit dans "from"
			return 100;
	}

}