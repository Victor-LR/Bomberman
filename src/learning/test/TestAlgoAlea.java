package learning.test;

import java.util.ArrayList;

import org.apache.commons.lang3.SerializationUtils;

import game.BombermanGame;
import graphics.Cadre_menu;
import graphics.Cadre_multi;
import learning.Reward;
import learning.RewardTools;
import learning.Sensor;
import learning.SimpleReward;
import learning.SimpleStateSensor;
import learning.perceptron.Perceptron;
import learning.perceptron.PerceptronAgent;
import learning.perceptron.RechercheAleatoire;
import map.GameState;
import map.Map;


public class TestAlgoAlea {
	
	public static void main(String args[])  {
	BombermanGame BG = new BombermanGame();
		
		Map map;
		try {
			map = new Map("./layout/perceptron.lay");
			GameState GameS  = new GameState(map,BG);
			int strat[] = {0};
			GameS.setStrats(strat);
			GameS.setCampagne(false);
			
			GameState state = SerializationUtils.clone(GameS);

			
			int taille =3;
			Sensor sens = new Sensor(new SimpleStateSensor(taille));
			Reward r = new SimpleReward();
			
			RechercheAleatoire ra = new RechercheAleatoire(50, 40, Math.random()*0.2-0.1, state,r, sens);
			for(int i=0;i<10; i++){
				ra.evoluer();
			}
			Perceptron perceptron = ra.getMeilleur();
			PerceptronAgent agent_bomberman = new PerceptronAgent(state.getBombermans().get(0),sens, perceptron);
			
			GameS.setBomberman(0, agent_bomberman);
			
			//System.out.println(RewardTools.getAverageReward(GameS, agent_bomberman, r, 100, 100));
			
			
			ArrayList<BombermanGame> L_BbmG = new ArrayList<BombermanGame>();
			for (int i = 0 ; i < 500 ; i++){
				PerceptronAgent agent_b = new PerceptronAgent(agent_bomberman,sens, perceptron);
				BombermanGame un_bbmg = new BombermanGame();
				
				try {
					un_bbmg.loadFile("./layout/perceptron.lay");

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				un_bbmg.init();
				un_bbmg.etatJeu.setCampagne(false);
				un_bbmg.etatJeu.setStrats(strat);
				un_bbmg.etatJeu.setBomberman(0, agent_b);
				
				un_bbmg.setTemps(1);
				un_bbmg.new_thread();
				L_BbmG.add(un_bbmg);
				un_bbmg.getThread().start();
					
				System.out.println("	Thread n°"+i);
				
			}
				
			for(int j = 0 ; j < L_BbmG.size(); j++){
				try {
					L_BbmG.get(j).getThread().join();
					System.out.println("	Attente Thread n°"+j);
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("erreur !");
				}
			}
			
			System.out.println("			Fin multithreads");

			Cadre_multi c_m = new Cadre_multi(L_BbmG,500,Cadre_menu.PERCEP_ALGO_ALEA);
			c_m.setVisible(true);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}