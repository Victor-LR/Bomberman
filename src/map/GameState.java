package map;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import agents.Agent;
import agents.AgentAction;
import agents.AgentType;
import agents.Agent_Bomberman;
import agents.ColorBomberman;
import game.BombermanGame;
import graphics.Cadre_gagnant;
import key.Keys;
import key.Keys_2;
import objets.Objet;
import objets.Objet_Bomb;
import strategies.Strategie_A;
import strategies.Strategie_PVE;
import strategies.Strategie_PVP;
import objets.ObjetType;

public class GameState {
	
	Map map;
	
	public Keys key_action;
	public Keys_2 key_action_2;
	
	private ArrayList<Agent> ennemies;
	private ArrayList<Agent_Bomberman> bombermans;
	private ArrayList<Objet_Bomb> bombes;
	private ArrayList<Objet> items;
	
	private static Random numberGenerator = new Random();
	private int pourcentage = 100;
	private boolean end;
	private JFrame cadre_jeu = null;
	
	private boolean mode_jeu;
	
	private  BombermanGame BbmG;

	private String winner =null;
	private int idGagnant = 6;
	
	//Construit l'état courant de la map
	
	public GameState(Map map,BombermanGame BbmG){
		
		
		ennemies = new ArrayList<Agent>();
		bombermans = new ArrayList<Agent_Bomberman>();
		bombes = new ArrayList<Objet_Bomb>();
		items = new ArrayList<Objet>();
		
		key_action = new Keys();
		key_action_2 = new Keys_2();

		this.map=map;
		this.BbmG=BbmG;

		this.end = false;
		
		ColorBomberman[] Couleurs= ColorBomberman.values();
		for(int i=0;i<map.getNumber_of_bombermans();i++)
		{
			Agent_Bomberman b = new Agent_Bomberman(map.getBomberman_start_x(i), map.getBomberman_start_y(i),i );
			ColorBomberman col = Couleurs[i];
			b.setCouleur(col);
			bombermans.add(b);
		}

		for(int i=0;i<map.getNumber_of_ennemies();i++)
		{
			Agent a = new Agent(AgentType.ENNEMY, map.getEnnemy_start_x(i), map.getEnnemy_start_y(i) );
			ennemies.add(a);
		}

	}
	
	//Verifie si l'action de déplacement est possible à l'état courant pour un agent
	
	public boolean isLegalMove(AgentAction action, Agent agent){
		int x = action.getVx();
		int y = action.getVy();
		
		if(map.isWall(agent.getX()+x, agent.getY()+y) || map.isBrokable_Wall(agent.getX()+x, agent.getY()+y))
			return false;
		else return true;
	}
	
	//Verifie si l'action de déplacement est possible à l'état courant pour un Bomberman
	
	public boolean isLegalMoveBbm(AgentAction actionbbm, Agent_Bomberman bbm){
		int x = actionbbm.getVx();
		int y = actionbbm.getVy();
			
		if(map.isWall(bbm.getX()+x, bbm.getY()+y) || map.isBrokable_Wall(bbm.getX()+x, bbm.getY()+y))
			return false;
		else return true;
	}
	
	//Fonction qui renvoie vrai si une bombe est sous forme physique dans le jeu (et non sous forme d'explosion)
	
		public boolean isBombe(int x, int y) {
			boolean bombeB = false;
			//System.out.println("nb bombes :"+bombes1.size());
			ArrayList<Objet_Bomb> bombes1 = this.bombes;
			for(int i=0; i< bombes1.size(); i++) {
				Objet_Bomb bombe1 = bombes1.get(i);
				if((bombe1.getObjX() == x & bombe1.getObjY() == y) & (bombe1.getEtat() < 10 )) bombeB = true;
			}
			
			return bombeB;
		}
	
	//Fonction qui renvoie vrai si un bomberman se trouve sur le pochin déplacement du bomberman qui va réaliser un déplacement 
		public boolean isBomberman(int id,int x, int y) {
			boolean bb = false;
			
			ArrayList<Agent_Bomberman> bbms = this.bombermans;
			for(int i=0; i< bbms.size(); i++) {
				Agent_Bomberman bbm = bbms.get(i);
				if( id!=bbm.getId() && (bbm.getX() == x & bbm.getY() == y) & !bbm.isDead()) bb = true;
			}
			
			return bb;
		}
		
		public boolean isEnnemie(int x, int y) {
			boolean en = false;
			
			ArrayList<Agent> emis = this.ennemies;
			for(int i=0; i< emis.size(); i++) {
				Agent emi = emis.get(i);
				if((emi.getX() == x & emi.getY() == y)) en = true;
			}
			
			return en;
		}
	
	//réalise le mouvement 
	
	public void moveAgent(Agent agent, AgentAction action)
	{
		int x = agent.getX();
		int y = agent.getY();
			
	    agent.setX(x+action.getVx());
		agent.setY(y+action.getVy());
		
		if (action.getAction()!=Map.STOP) 
			agent.setDirection(action.getAction());
	}
	
	//place une bombe
	
	public void placeBomb(Agent_Bomberman agent)
	{
		//bombs = new ArrayList<Objet>();
		int x = agent.getX();
		int y = agent.getY();
		int compte = 0;

		for(int i = 0; i<bombes.size(); i++) {
			if(bombes.get(i).getId_bbm() == agent.getId()) {
				compte++;
			}
		}
		
		if(compte < agent.getNbBombes()){
			Objet_Bomb bomb = new Objet_Bomb(ObjetType.BOMB,x,y,agent.getId(),agent.getRange());
			//System.out.println("action bombe");
			bombes.add(bomb);
			//agent.getBombes().add(bomb);
		}
		//else //System.out.println("		NON action bombe");
			
	}

	//
	/*public <T> T randomElement(T[] elements){
	  return elements[numberGenerator.nextInt(elements.length)];
	}*/
	
	//Place un item
	public void placeItem(int itemx, int itemy)
	{
		ObjetType[] TypeItems= ObjetType.values();
		ObjetType UnType;
		do {
			UnType = TypeItems[numberGenerator.nextInt(TypeItems.length)];
		} while (UnType == ObjetType.BOMB);
		Objet item = new Objet(UnType,itemx,itemy);
		items.add(item);

	}

	public int test_range(int direction ,Objet_Bomb bomb)
	{
		int x = bomb.getObjX();
		int y = bomb.getObjY();
		
		int taille_range = 1;
		
		
		if (direction == Map.EAST) {
			for(int i = 0; i<=bomb.getRange(); i++){
				if(x+i<map.getSizeX()){
					if(map.isBrokable_Wall(x+i, y) || map.isWall(x+i, y)){
						taille_range = x+i;
						break;
					}
					else taille_range = x+i;
				}
			}
		}
		
		if (direction == Map.SOUTH) {
			for(int i = 0; i<=bomb.getRange(); i++){
				if(y+i < map.getSizeY()){
					if(map.isBrokable_Wall(x, y+i) || map.isWall(x, y+i)){
						taille_range = y+i;
						break;
					} else taille_range = y+i;
				}
			}
		}
		
		if (direction == Map.WEST) {
			for(int i = 0; i<=bomb.getRange(); i++){
				if(x-i>=0){
					if(map.isBrokable_Wall(x-i, y) || map.isWall(x-i, y)){
						taille_range = x-i;
						break;
					} else taille_range = x-i;
				}
			}
		}
		if (direction == Map.NORTH) {
			for(int i = 0; i<=bomb.getRange(); i++){
				if(y-i>=0){
					if(map.isBrokable_Wall(x, y-i) || map.isWall(x, y-i)){
						taille_range = y-i;
						break;
					} else taille_range = y-i;
				}
				
			}
		}
		return taille_range;
		
	}
	
	//détruit les murs adjacent de la bombe et les ennemies
	
	public void bombExplode(Objet_Bomb bomb)
	{
		int x = bomb.getObjX();
		int y = bomb.getObjY();
		
		ArrayList<Agent> ennemies = this.getEnnemies();
		ArrayList<Agent_Bomberman> bombermans = this.getBombermans();
		
		int range_limit;
		
		int r = 100;
		
		range_limit = test_range(Map.EAST,bomb);
		
		if(map.isBrokable_Wall(range_limit, y)) {
			map.setBrokable_Wall(range_limit,y,false);
			r =(int)(Math.random()*100);
			if ( r < pourcentage) placeItem(range_limit,y);
		}
		
		for(int i = x; i<= range_limit; i++){
			
				for(int j = 0; j<bombermans.size(); j++){
					Agent_Bomberman bomberman1 = bombermans.get(j);
					if(bomberman1.getX() == i & bomberman1.getY() == y & bomb.getId_bbm() != bomberman1.getId() & !bomberman1.isInvincible() & !bombermans.get(j).isDead()){
						bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 500);
						bombermans.get(j).setDead(true);
					}
				}

				for(int j = 0; j<ennemies.size(); j++){
					Agent ennemie = ennemies.get(j);
					if(ennemie.getX() == i & ennemie.getY() == y & !ennemies.get(j).isDead()){
						ennemies.remove(j);
						bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 100);
					}
				}
			
				for(int j = 0; j<bombes.size(); j++){
					Objet_Bomb bombe = bombes.get(j);
					if(bombe.getObjX() == i & bombe.getObjY() == y){
						bombes.get(j).setEtat(10);
					}
				}
				
		}
		
		range_limit = test_range(Map.SOUTH,bomb);
		
		if(map.isBrokable_Wall(x, range_limit)) {
			map.setBrokable_Wall(x,range_limit,false);
			r =(int)(Math.random()*100);
			if ( r < pourcentage) placeItem(x,range_limit);
		}
		
		for(int i = y; i<= range_limit; i++){
			
			for(int j = 0; j<bombermans.size(); j++){
				Agent_Bomberman bomberman1 = bombermans.get(j);
				if(bomberman1.getX() == x & bomberman1.getY() == i  & bomb.getId_bbm() != bomberman1.getId() & !bomberman1.isInvincible() & !bombermans.get(j).isDead()){
					bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 500);
					bombermans.get(j).setDead(true);
				}
			}

				for(int j = 0; j<ennemies.size(); j++){
					Agent ennemie = ennemies.get(j);
					if(ennemie.getX() == x & ennemie.getY() == i & !ennemies.get(j).isDead()){
						ennemies.remove(j);
						bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 100);
					}
				}
				
				for(int j = 0; j<bombes.size(); j++){
					Objet_Bomb bombe = bombes.get(j);
					if(bombe.getObjX() == x & bombe.getObjY() == i ){
						bombes.get(j).setEtat(10);
					}
				}
		}
			
		range_limit = test_range(Map.WEST,bomb);
		
		if(map.isBrokable_Wall(range_limit, y)) {
			map.setBrokable_Wall(range_limit,y,false);
			r =(int)(Math.random()*100);
			if ( r < pourcentage) placeItem(range_limit,y);
		}
		
		for(int i = x; i >= range_limit; i--){
			
			for(int j = 0; j<bombermans.size(); j++){
				Agent_Bomberman bomberman1 = bombermans.get(j);
				if(bomberman1.getX() == i & bomberman1.getY() == y  & bomb.getId_bbm()  != bomberman1.getId() & !bomberman1.isInvincible() & !bombermans.get(j).isDead()){
					bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 500);
					bombermans.get(j).setDead(true);
				}
			}
	
				for(int j = 0; j<ennemies.size(); j++){
					Agent ennemie = ennemies.get(j);
					if(ennemie.getX() == i & ennemie.getY() == y & !ennemies.get(j).isDead()){
						ennemies.remove(j);
						bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 100);
					}
				}
					
				for(int j = 0; j<bombes.size(); j++){
					Objet_Bomb bombe = bombes.get(j);
					if(bombe.getObjX() == i & bombe.getObjY() == y){
						bombes.get(j).setEtat(10);
					}
				}
		}
		
			
		range_limit = test_range(Map.NORTH,bomb);
		
		if(map.isBrokable_Wall(x, range_limit )) {
			map.setBrokable_Wall(x,range_limit,false);
			r =(int)(Math.random()*100);
			if ( r < pourcentage) placeItem(x,range_limit);
		}
		
		for(int i = y; i >= range_limit; i--){
			
			for(int j = 0; j<bombermans.size(); j++){
				Agent_Bomberman bomberman1 = bombermans.get(j);
				if(bomberman1.getX() == x & bomberman1.getY() == i  & bomb.getId_bbm()  != bomberman1.getId() & !bomberman1.isInvincible() & !bombermans.get(j).isDead()){
					bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 500);
					bombermans.get(j).setDead(true);
				}
			}

				for(int j = 0; j<ennemies.size(); j++){
					Agent ennemie = ennemies.get(j);
					if(ennemie.getX() == x & ennemie.getY() == i & !ennemies.get(j).isDead()){
						ennemies.remove(j);
						bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 100);
						//bomberman.setPoints(bomberman.getPoints() + 100);
					}
				}
				
				for(int j = 0; j<bombes.size(); j++){
					Objet_Bomb bombe = bombes.get(j);
					if(bombe.getObjX() == x & bombe.getObjY() == i){
						bombes.get(j).setEtat(10);
					}
				}
		}

	}
	
	//Réalise un tour du jeu 
	
	public void taketurn(){
		if(!getEnd()) {
			this.isEnd(BbmG);
			bombermansTurn();
			ennemiesTurn();
			
		}else {
			BbmG.stop();
		}
	}	
	
	//Réalise le tour de l'ennemi
	
	public void ennemiesTurn(){

		ArrayList<Agent> ennemies = this.getEnnemies();

		for(int i = 0; i < ennemies.size(); i++){

			Agent ennemy = ennemies.get(i);
			
			AgentAction ennemyAction = ennemy.chooseAction(this);
			

			if (ennemyAction != null){
				
				this.moveAgent(ennemy, ennemyAction);
			}
			
		}
	}
	
	
	//Réalise le tour des bombermans
	
		public void bombermansTurn() {
			
			ArrayList<Agent_Bomberman> bombermans = this.getBombermans();
			
			
			
			for(int i = 0; i < bombermans.size(); i++){
				
//				Agent_Bomberman bomberman = bombermans.get(i);
//				AgentAction bombermanAction = bomberman.chooseAction(this);
					
				
				Agent_Bomberman bomberman = bombermans.get(i);
				AgentAction bombermanAction;
				Strategie_PVP strat = new Strategie_PVP(this,bomberman);
				Strategie_A strat_A = new Strategie_A(this,bomberman);
				
				if(!bomberman.isDead()) {
					
					if (mode_jeu ) {
						switch (i) {
						case 0:
							bombermanAction = bomberman.chooseAction(this,key_action.getKaction(),null);
							//this.key_action.setKaction(new AgentAction(Map.STOP));
							break;
						case 1:
							bombermanAction = bomberman.chooseAction(this,null,strat_A);
							//this.key_action_2.setKaction(new AgentAction(Map.STOP));
							break;
						default:
							bombermanAction = bomberman.chooseAction(this,null,null);
						}
					} else {
						if(bomberman.getId() == 1)
							bombermanAction = bomberman.chooseAction(this,null,strat_A);
						else
							bombermanAction = bomberman.chooseAction(this,null,null);
					}
										
					//System.out.println(bombermanAction.getAction());
				
					if(isEnnemie(bomberman.getX(),bomberman.getY())) {
						bomberman.setDead(true);
					}
				
				for (int j = 0; j < items.size(); j++){
					
					//Boucle permettant de gérer les effets des différents items récupéré par les bombermans
					Objet item = items.get(j);
					
					if ( (bomberman.getX() == item.getObjX()) && (bomberman.getY() == item.getObjY()) ){
						
						if(item.getType() == ObjetType.FIRE_UP & !bomberman.isInvincible()) bomberman.setRange(bomberman.getRange()+1);
						else if((item.getType() == ObjetType.FIRE_DOWN & !bomberman.isInvincible() ) && bomberman.getRange() > 1) bomberman.setRange(bomberman.getRange()-1);
						else if((item.getType() == ObjetType.BOMB_UP)) bomberman.setNbBombes(bomberman.getNbBombes()+1);
						else if((item.getType() == ObjetType.BOMB_DOWN & !bomberman.isInvincible()) && bomberman.getNbBombes() > 1) bomberman.setNbBombes(bomberman.getNbBombes()-1);
						else if((item.getType() == ObjetType.FIRE_SUIT)) {
							bomberman.setInvincible(true);
							bomberman.setEtatInv(0);
							
						}else if((item.getType() == ObjetType.SKULL & !bomberman.isInvincible())) {
							
							bomberman.setMaladie((int) (Math.random()*2));//(int) (Math.random()*3));
//							System.out.println("	maladie : "+bomberman.getMaladie());
							bomberman.setSick(true);
							bomberman.setEtatSick(0);
						}
						
						//System.out.println("	range : "+bomberman.getRange());
						items.remove(item);
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
//							System.out.println("diarrhée");
							this.placeBomb(bomberman);
						}
						
						bomberman.setEtatSick(bomberman.getEtatSick()+1);
					}
					else { 
						
						int nbDead = 0;
						for (int nb = 0 ; nb < bombermans.size() ; nb++) {
							if(bombermans.get(nb).isDead()) nbDead++;
						}
							
							
						   if(bomberman.getMaladie() == 2 && bombermans.size() > nbDead-1) {
//								System.out.println("swap");
								int bb;
								do {
									bb = (int) (Math.random()*bombermans.size());
								}while(bb==i || bombermans.get(bb).isDead());
								
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
				
				
				

				if (bombermanAction.getAction() < 5){
						
					this.moveAgent(bomberman, bombermanAction);
					this.bombeTurn(bomberman);
					
//					System.out.println("après deplacement	Range -> "+ bomberman.getRange());
				}
				else if (mode_jeu & i < 2) {
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
				
//			key_action.setKaction(bomberman.chooseAction(this,new AgentAction(Map.STOP)));
			}
		}
	
	public void bombeTurn(Agent_Bomberman bomberman){

		ArrayList<Objet_Bomb> bombs = bombes;
		
		
		for(int i = 0; i < bombs.size(); i++){

			Objet_Bomb bombe = bombs.get(i);
			
			if(bombe.getId_bbm() == bomberman.getId()) {
				int etat_bombe = bombe.getEtat();
				
				if (etat_bombe >= 11) {
					bombExplode(bombe);
					bombs.remove(bombe);
					bombes.remove(bombe);
				}
				else bombe.setEtat(etat_bombe + 1);
			}
		}
	}
	
	//Fonction permettant de mettre à terme au jeu quand l'un des bomermans à gagné.
	
	public void isEnd(BombermanGame game) {
		ArrayList<Agent_Bomberman> bombermans = this.getBombermans();
		ArrayList<Agent> ennemies = this.getEnnemies();
		
		int nbBbm = this.getMap().getNumber_of_bombermans();
		int compteBbm = 0;
		int compteEnn = 0;
		int compteExec = 0;
		int maxScore = 0;
		int aux ;
		//this.idGagnant = 6;
		
		
		for(int i = 0; i<bombermans.size(); ++i) {
			if(!bombermans.get(i).isDead()) {
				compteBbm++;
				this.idGagnant = i;
			}
		}
		
		
		for(int i = 0; i<ennemies.size(); ++i) {
			if(!ennemies.get(i).isDead()) {
				compteEnn++;
			}
		}
		
		if(compteBbm == 0  & nbBbm == 1) {
			setEnd(true);
			game.etatJeu.setEnd(true);
			this.winner = "GAME OVER";
			this.idGagnant = 5;
		}
		
		if(compteBbm == 1 & nbBbm == 1 & compteEnn == 0) {
			setEnd(true);
			game.etatJeu.setEnd(true);
			winner = "Le joueur "+(bombermans.get(idGagnant).getId()+1)+" est le gagnant Partie SOLO";
		}
		
		
		if(compteBbm == 1 & nbBbm != 1) {
			setEnd(true);
			game.etatJeu.setEnd(true);
			winner = "Le joueur "+(bombermans.get(idGagnant).getId()+1)+" est le gagnant";
		}
		
		if(compteBbm == 0 & nbBbm != 1) {
			setEnd(true);
			game.etatJeu.setEnd(true);
			winner = "GAME OVER";
			this.idGagnant =5;
		}
		
		
		if(game.getTurn() == (game.getMaxTurn()) ) {
			setEnd(true);
			game.etatJeu.setEnd(true);
			
			if(nbBbm == 1) {
				setEnd(true);
				game.etatJeu.setEnd(true);
				this.winner = "GAME OVER";
				this.idGagnant =5;

			}
			else{
				
				for(int i = 0; i<bombermans.size(); ++i) {
					if(!bombermans.get(i).isDead()) {
						aux = bombermans.get(i).getPoints();
						if(maxScore<aux) {
							maxScore = aux;
							this.idGagnant = i;
						}
						
					}
				}
				
				for(int i = 0; i<bombermans.size(); ++i) {
					if(!bombermans.get(i).isDead()) {
						aux = bombermans.get(i).getPoints();
						if(maxScore == aux) {
							compteExec ++;
						}
					}
				}
				
				
				
				
				if( compteExec < 2 ) {
					this.winner = "Le joueur "+(bombermans.get(idGagnant).getId()+1)+" est le gagnant par score = " + maxScore ;
				}
				else {
					this.winner = "Il y a execo ";
					this.idGagnant = 5;
				}
			}
			
		}
		if (winner != null) System.out.println(this.winner);
}
	

	//Renvoie un agent en fonction d'un id 
	

	public Agent getAgent(GameState etat, int agentId){
		
		for (Agent p : etat.getEnnemies()){
			if(p.getId() == agentId){
				return p;
			}
		}

		return null;
	}
	
	//accesseur sur la liste de bombermans
	
	public ArrayList<Agent_Bomberman> getBombermans(){
		return bombermans;
	}
	
	public ArrayList<Objet_Bomb> getBombes(){
		return bombes;
	}

	//accesseur sur la liste d'items
	
	public ArrayList<Objet> getItems(){
		return items;
	}	
	
	//accesseur sur la liste d'enemies
	
	public ArrayList<Agent> getEnnemies(){
		return ennemies;
	}
	
	//accesseur sur la map courrante 
	
	public Map getMap(){
		return map;
	}
	
	// mode de jeu = true -> Un joueur
	// mode de jeu = false -> Automatique
	public boolean getMode_jeu() {
		return mode_jeu;
	}

	public void setMode_jeu(boolean mode_jeu) {
		this.mode_jeu = mode_jeu;
	}

	public boolean getEnd() {
		return end;
	}
	
	public boolean setEnd(boolean e) {
		return end = e;
	}
	
	public void setC_j(JFrame c_j) {
		this.cadre_jeu = c_j;
	}
	
	
	public String getWinner() {
		return winner;
	}

	public int getIdGagnant() {
		return idGagnant;
	}
}
