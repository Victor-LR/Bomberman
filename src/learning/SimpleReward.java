package learning;

import map.GameState;

public class SimpleReward implements Reward {

	@Override
	public double getReward(GameState to, int nb_murs, int nb_points, int nb_bonus) {
		if (to.getFinPartie() == GameState.WIN_SOLO || to.getFinPartie() == GameState.WIN_SCORE)
			return  1000;
		if (to.getFinPartie() == GameState.GAME_OVER)
			return -100;
		if (to.getFinPartie() == GameState.FIN_TOUR)
			return -100;
		if (to.getBombermans().get(0).getPoints() == nb_points)  
			return 0;
		if (to.getBombermans().get(0).getPoints() < nb_points)
			return 10;
		if (to.getBombermans().get(0).getNb_bonus() == nb_bonus)  
			return 0;
		if (to.getBombermans().get(0).getNb_bonus() < nb_bonus)
			return 50;
		if (to.getBombermans().get(0).getNb_murs() == nb_murs) //tout les mur de "to" sont resté intacte
			return 0;
		else //des murs de "to" on été detruit dans "from"
			return 1;
		
		
		
		/*
		 * la prise de bonus 
		 * l'évitement des bombes ( sortie de l'axe) quand on pourras jouer avec plusiers bbm
		 * tuer un bomberman 
		 * tuer un ennemie
		 */
		
	}

}