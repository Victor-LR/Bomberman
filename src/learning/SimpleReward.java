package learning;

import map.GameState;

public class SimpleReward implements Reward {

	@Override
	public double getReward(GameState to, int nb_murs, int nb_points, int nb_bonus, boolean axe_bombe) {
		
		int reward = 0;
		
		if (to.getFinPartie() == GameState.WIN_SOLO || to.getFinPartie() == GameState.WIN_SCORE)
			reward += 1000;
		if (to.getFinPartie() == GameState.GAME_OVER)
			reward -= 100;
		if (to.getFinPartie() == GameState.FIN_TOUR)
			reward -= 100;
		if (to.getBombermans().get(0).getPoints() == nb_points)  
			reward -=  0;
		if (to.getBombermans().get(0).getPoints() != nb_points)
			reward +=  100;
		if (to.getBombermans().get(0).getNb_bonus() == nb_bonus)  
			reward -= 0;
		if (to.getBombermans().get(0).getNb_bonus() != nb_bonus)
			reward +=  50;
		if (axe_bombe == true)  
			reward -= 100;
		if (axe_bombe == false)
			reward +=  0;
		if (to.getBombermans().get(0).getNb_murs() == nb_murs) //tout les mur de "to" sont resté intacte
			reward -=  0;
		if (to.getBombermans().get(0).getNb_murs() != nb_murs)//des murs de "to" on été detruit dans "from"
			reward +=  10;
		else reward +=  0;
		
		return reward;
		/*
		 * la prise de bonus 
		 * l'évitement des bombes ( sortie de l'axe) quand on pourras jouer avec plusiers bbm
		 * tuer un bomberman 
		 * tuer un ennemie
		 */
	}

}