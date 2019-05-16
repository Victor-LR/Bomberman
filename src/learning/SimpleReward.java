package learning;

import map.GameState;

public class SimpleReward implements Reward {

	@Override
	public double getReward(GameState to, int nb_murs) {
		if (to.getFinPartie() == GameState.WIN_SOLO || to.getFinPartie() == GameState.WIN_SCORE)
			return  10000;
		if (to.getFinPartie() == GameState.GAME_OVER)
			return -1000;
		if (to.getFinPartie() == GameState.FIN_TOUR)
			return -1000;
//		System.out.println(to.getBrokableWals() +"		"+	from.getBrokableWals());
		if (to.getBrokableWals() == nb_murs) //tout les mur de "to" sont resté intacte
			return 0;
		else //des murs de "to" on été detruit dans "from"
			return 100;
	}

}