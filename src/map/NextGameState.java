package map;

import java.util.ArrayList;

import objets.Objet;
import objets.ObjetType;
import agents.AgentAction;
import agents.Agent_Bomberman;
import game.BombermanGame;

public class NextGameState extends GameState {

	public NextGameState(GameState AncienGS) {
		super(AncienGS.getMap(), AncienGS.getBbmG());
		this.setStrats(AncienGS.getStrats());
		this.setCampagne(AncienGS.getCampagne());
	}
	
	
	//Réalise un tour du jeu 
		public void taketurn(AgentAction action, int id_bbm){
			BombermanGame game = this.getGame();
			BombermanGame BbmG = this.getBbmG();
			if(getCampagne()) {

				if(!getEnd()) {

					this.isEndCampagne(BbmG);
					bombermansTurn(action,id_bbm);
					ennemiesTurn();
					birdsTurn();
					rajionsTurn();
					towerTurn();

					
				}else {
					
					if(BbmG.etatJeu.getNum_niveau() == 1) {
						try {
							game.loadFile("./layout/niveau2.lay");
						} catch (Exception e) {
							e.printStackTrace();
						}
						game.init();
						
						game.etatJeu.setCampagne(true);		
						game.etatJeu.setNum_niveau(2);
						game.etatJeu.setStrats(BbmG.etatJeu.getStrats());
						((Agent_Bomberman) game.etatJeu.getBombermans().get(0)).setPoints(((Agent_Bomberman) BbmG.etatJeu.getBombermans().get(0)).getPoints());
						((Agent_Bomberman) game.etatJeu.getBombermans().get(0)).setRange(((Agent_Bomberman) BbmG.etatJeu.getBombermans().get(0)).getRange());
						((Agent_Bomberman) game.etatJeu.getBombermans().get(0)).setNbBombes(((Agent_Bomberman) BbmG.etatJeu.getBombermans().get(0)).getNbBombes());
						
						System.out.println(getWinner()+" stage 1");
						
						BbmG.stop();
						game.launch();
						
					}
						
					if(BbmG.etatJeu.getNum_niveau() == 2) {
						
						try {
							game.loadFile("./layout/niveau3.lay");
						} catch (Exception e) {
							e.printStackTrace();
						}
						game.init();
						
						game.etatJeu.setCampagne(true);		
						game.etatJeu.setNum_niveau(3);	
						game.etatJeu.setStrats(BbmG.etatJeu.getStrats());
						((Agent_Bomberman) game.etatJeu.getBombermans().get(0)).setPoints(((Agent_Bomberman) BbmG.etatJeu.getBombermans().get(0)).getPoints());
						((Agent_Bomberman) game.etatJeu.getBombermans().get(0)).setRange(((Agent_Bomberman) BbmG.etatJeu.getBombermans().get(0)).getRange());
						((Agent_Bomberman) game.etatJeu.getBombermans().get(0)).setNbBombes(((Agent_Bomberman) BbmG.etatJeu.getBombermans().get(0)).getNbBombes());
						
						System.out.println(getWinner()+" stage 2");
						
						BbmG.stop();
						game.launch();
						
					}
					
					if(BbmG.etatJeu.getNum_niveau() == 3) {
						BbmG.stop();
						System.out.println(getWinner()+" stage 3");
					}
				}
			}
			else if(!getEnd()) {
					this.isEnd(BbmG);
					bombermansTurn(action,id_bbm);
					ennemiesTurn();
					birdsTurn();
					rajionsTurn();
					towerTurn();
				}else {

					BbmG.stop();
				}
		}
		
		//Réalise le tour des bombermans
		
			public void bombermansTurn(AgentAction action, int id_bbm) {
				
				ArrayList<Agent_Bomberman> bombermans = this.getBombermans();
				
				if(this.getBbmG().getTurn() % 2 == 0) {

				for(int i = 0; i < bombermans.size(); i++){
					
					Agent_Bomberman bomberman = bombermans.get(i);
					AgentAction bombermanAction;
					
					//System.out.println("	Position bomberman ("+bomberman.getX()+","+bomberman.getY()+")");
					
					if(!bomberman.isDead()) {
						
			
											
						//System.out.println(bombermanAction.getAction());
						if (!bomberman.isInvincible()){
							if(isEnnemie(bomberman.getX(),bomberman.getY())) {
								bomberman.setDead(true);
							}
							
							if(isBird(bomberman.getX(),bomberman.getY())) {
								bomberman.setDead(true);
							}
							
							if(isRajion(bomberman.getX(),bomberman.getY())) {
								bomberman.setDead(true);
							}
						}
						
						for (int j = 0; j < this.getItems().size(); j++){
							
							//Boucle permettant de gérer les effets des différents items récupéré par les bombermans
							Objet item = (Objet) this.getItems().get(j);
							
							if ( (bomberman.getX() == item.getObjX()) && (bomberman.getY() == item.getObjY()) ){
								
								if(item.getType() == ObjetType.FIRE_UP & !bomberman.isInvincible()) bomberman.setRange(bomberman.getRange()+1);
								else if((item.getType() == ObjetType.FIRE_DOWN & !bomberman.isInvincible() ) && bomberman.getRange() > 1) bomberman.setRange(bomberman.getRange()-1);
								else if((item.getType() == ObjetType.BOMB_UP)) bomberman.setNbBombes(bomberman.getNbBombes()+1);
								else if((item.getType() == ObjetType.BOMB_DOWN & !bomberman.isInvincible()) && bomberman.getNbBombes() > 1) bomberman.setNbBombes(bomberman.getNbBombes()-1);
								else if((item.getType() == ObjetType.FIRE_SUIT)) {
									bomberman.setInvincible(true);
									bomberman.setEtatInv(0);
									
								}else if((item.getType() == ObjetType.SKULL & !bomberman.isInvincible())) {
									
									bomberman.setMaladie((int) (Math.random()*3));//(int) (Math.random()*3));
		//							System.out.println("	maladie : "+bomberman.getMaladie());
									bomberman.setSick(true);
									bomberman.setEtatSick(0);
								}
								
								this.getItems().remove(item);
							}
						}
						
						
						if (bomberman.isInvincible())
							if(bomberman.getEtatInv() <=20) bomberman.setEtatInv(bomberman.getEtatInv()+1);
							else bomberman.setInvincible(false);
						
						//Maladies: 0 -> diarrhée
						//			1 -> Constipation
						//			2 -> Swap
						
							if(bomberman.isSick() & bomberman.getEtatSick() <=20) {
							
								if (bomberman.getMaladie() == 0 && bomberman.getNbActions() > 2) {
									this.placeBomb(bomberman);
								}
								
								bomberman.setEtatSick(bomberman.getEtatSick()+1);
							}
							else { 
								
								int nbDead = 0;
								for (int nb = 0 ; nb < bombermans.size() ; nb++) {
									if(bombermans.get(nb).isDead()) nbDead++;
								}
									
									
								   if(bomberman.getMaladie() == 2 && bombermans.size() > nbDead-1 ) {
										int bb;
										ArrayList<Agent_Bomberman> legal_bbm = new ArrayList<Agent_Bomberman>() ; 
										for(int j = 0 ; j < bombermans.size(); j++){
											if(j != i && !bombermans.get(j).isDead())  legal_bbm.add(bombermans.get(j));
										}
										
										bb = (int) (Math.random()*legal_bbm.size());
										
										int aux_x = bomberman.getX();
										int aux_y = bomberman.getY();
										
										bomberman.setX(bombermans.get(bb).getX());
										bomberman.setY(bombermans.get(bb).getY());
										
										bombermans.get(bb).setX(aux_x);
										bombermans.get(bb).setY(aux_y);
									}
								   bomberman.setSick(false);
								   bomberman.setMaladie(10);
								 }
						
							if( i == 0){
								bombermanAction = action;
//								if(this.isLegalMoveBbm(action, bomberman)) bombermanAction = action;
//								else bombermanAction = new AgentAction(Map.STOP);
							}
							else bombermanAction = bomberman.chooseAction(this);
		
						if (bombermanAction.getAction() < 5){
								
							this.moveAgent(bomberman, bombermanAction);
							this.bombeTurn(bomberman);
							
		//					System.out.println("après deplacement	Range -> "+ bomberman.getRange());
						}
						else if ((bomberman.getStrat()== 0 || bomberman.getStrat()== 1) & i < 2) {
								if (bombermanAction.getAction() == 5 & (bomberman.getMaladie() != 0 & bomberman.getMaladie() != 1)){
									this.placeBomb(bomberman);
									this.bombeTurn(bomberman);
								} else this.bombeTurn(bomberman);
						} else {
							if (bomberman.getNbActions() > 2 & (bomberman.getMaladie() != 0 & bomberman.getMaladie() != 1)){
								this.placeBomb(bomberman);
								this.bombeTurn(bomberman);
							} else this.bombeTurn(bomberman);
						}
					
					}
						
					else this.bombeTurn(bomberman);
						
					}
				}
				
			}

}
