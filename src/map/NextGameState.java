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
	
	
	public GameState copy(){
		
		BombermanGame BG = new BombermanGame();
		BG = getBbmG();
		GameState GS = new GameState(this.getMap().copy(),BG);
		
		for(int i = 0; i< this.map.getSizeX(); i++) {
			for(int j = 0; j< this.map.getSizeY(); j++) {
				if(isBrokable_Wall(i, j)) 
					GS.setBrokable_Wall(i, j, true); 
				else GS.setBrokable_Wall(i, j, false);
			}
}
		
		GS.setStrats(this.getStrats());
		GS.setCampagne(this.getCampagne());
		//NextGameState NGS = new NextGameState(GS);
		return GS;
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
					
					//Agent_Bomberman bomberman = bombermans.get(i);
					
					AgentAction bombermanAction;
					
					//System.out.println("	Position bomberman ("+bomberman.getX()+","+bomberman.getY()+")");
					
					if(!bombermans.get(i).isDead()) {
						
			
											
						//System.out.println(bombermanAction.getAction());
						if (!bombermans.get(i).isInvincible()){
							if(isEnnemie(bombermans.get(i).getX(),bombermans.get(i).getY())) {
								bombermans.get(i).setDead(true);
							}
							
							if(isBird(bombermans.get(i).getX(),bombermans.get(i).getY())) {
								bombermans.get(i).setDead(true);
							}
							
							if(isRajion(bombermans.get(i).getX(),bombermans.get(i).getY())) {
								bombermans.get(i).setDead(true);
							}
						}
						
						for (int j = 0; j < this.getItems().size(); j++){
							
							//Boucle permettant de gérer les effets des différents items récupéré par les bombermans
							Objet item = (Objet) this.getItems().get(j);
							
							if ( (bombermans.get(i).getX() == item.getObjX()) && (bombermans.get(i).getY() == item.getObjY()) ){
								
								if(item.getType() == ObjetType.FIRE_UP & !bombermans.get(i).isInvincible()) bombermans.get(i).setRange(bombermans.get(i).getRange()+1);
								else if((item.getType() == ObjetType.FIRE_DOWN & !bombermans.get(i).isInvincible() ) && bombermans.get(i).getRange() > 1) bombermans.get(i).setRange(bombermans.get(i).getRange()-1);
								else if((item.getType() == ObjetType.BOMB_UP)) bombermans.get(i).setNbBombes(bombermans.get(i).getNbBombes()+1);
								else if((item.getType() == ObjetType.BOMB_DOWN & !bombermans.get(i).isInvincible()) && bombermans.get(i).getNbBombes() > 1) bombermans.get(i).setNbBombes(bombermans.get(i).getNbBombes()-1);
								else if((item.getType() == ObjetType.FIRE_SUIT)) {
									bombermans.get(i).setInvincible(true);
									bombermans.get(i).setEtatInv(0);
									
								}else if((item.getType() == ObjetType.SKULL & !bombermans.get(i).isInvincible())) {
									
									bombermans.get(i).setMaladie((int) (Math.random()*3));//(int) (Math.random()*3));
		//							System.out.println("	maladie : "+bombermans.get(i).getMaladie());
									bombermans.get(i).setSick(true);
									bombermans.get(i).setEtatSick(0);
								}
								
								this.getItems().remove(item);
							}
						}
						
						
						if (bombermans.get(i).isInvincible())
							if(bombermans.get(i).getEtatInv() <=20) bombermans.get(i).setEtatInv(bombermans.get(i).getEtatInv()+1);
							else bombermans.get(i).setInvincible(false);
						
						//Maladies: 0 -> diarrhée
						//			1 -> Constipation
						//			2 -> Swap
						
							if(bombermans.get(i).isSick() & bombermans.get(i).getEtatSick() <=20) {
							
								if (bombermans.get(i).getMaladie() == 0 && bombermans.get(i).getNbActions() > 2) {
									this.placeBomb(bombermans.get(i));
								}
								
								bombermans.get(i).setEtatSick(bombermans.get(i).getEtatSick()+1);
							}
							else { 
								
								int nbDead = 0;
								for (int nb = 0 ; nb < bombermans.size() ; nb++) {
									if(bombermans.get(nb).isDead()) nbDead++;
								}
									
									
								   if(bombermans.get(i).getMaladie() == 2 && bombermans.size() > nbDead-1 ) {
										int bb;
										ArrayList<Agent_Bomberman> legal_bbm = new ArrayList<Agent_Bomberman>() ; 
										for(int j = 0 ; j < bombermans.size(); j++){
											if(j != i && !bombermans.get(j).isDead())  legal_bbm.add(bombermans.get(j));
										}
										
										bb = (int) (Math.random()*legal_bbm.size());
										
										int aux_x = bombermans.get(i).getX();
										int aux_y = bombermans.get(i).getY();
										
										bombermans.get(i).setX(bombermans.get(bb).getX());
										bombermans.get(i).setY(bombermans.get(bb).getY());
										
										bombermans.get(bb).setX(aux_x);
										bombermans.get(bb).setY(aux_y);
									}
								   bombermans.get(i).setSick(false);
								   bombermans.get(i).setMaladie(10);
								 }
						
							if( i == 0){
								bombermanAction = action;
//								if(this.isLegalMoveBbm(action, bomberman)) bombermanAction = action;
//								else bombermanAction = new AgentAction(Map.STOP);
							}
							else bombermanAction = bombermans.get(i).chooseAction(this);
		
						if (bombermanAction.getAction() < 5){
								
							this.moveAgent(bombermans.get(i), bombermanAction);
							this.bombeTurn(bombermans.get(i));
							
		//					System.out.println("après deplacement	Range -> "+ bomberman.getRange());
						}
						else if ((bombermans.get(i).getStrat()== 0 || bombermans.get(i).getStrat()== 1) & i < 2) {
								if (bombermanAction.getAction() == 5 & (bombermans.get(i).getMaladie() != 0 & bombermans.get(i).getMaladie() != 1)){
									this.placeBomb(bombermans.get(i));
									this.bombeTurn(bombermans.get(i));
								} else this.bombeTurn(bombermans.get(i));
						} else {
							if (bombermans.get(i).getNbActions() > 2 & (bombermans.get(i).getMaladie() != 0 & bombermans.get(i).getMaladie() != 1)){
								this.placeBomb(bombermans.get(i));
								this.bombeTurn(bombermans.get(i));
							} else this.bombeTurn(bombermans.get(i));
						}
					
					}
						
					else this.bombeTurn(bombermans.get(i));
						
					}
				}
				
			}

}
