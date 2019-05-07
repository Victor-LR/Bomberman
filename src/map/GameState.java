package map;
import java.util.ArrayList;
import java.util.Random;

import agents.Agent;
import agents.AgentAction;

import agents.AgentType;

import agents.Agent_Bird;
import agents.Agent_Bomberman;
import agents.Agent_Rajion;
import agents.Agent_Tower;
import agents.ColorBomberman;
import agents.Agent_Ennemy;
import game.BombermanGame;
import key.Keys;
import key.Keys_2;
import objets.Objet;
import objets.Objet_Bomb;
import objets.ObjetType;

public class GameState {
	
	Map map;
	
	private Keys key_action;
	private Keys_2 key_action_2;

	private ArrayList<Agent_Ennemy> ennemies;
	private ArrayList<Agent_Bird> birds;
	private ArrayList<Agent_Rajion> rajions;
	private ArrayList<Agent_Bomberman> bombermans;
	private ArrayList<Objet_Bomb> bombes;
	private ArrayList<Objet> items;
	private Agent_Tower tower;

	
	private static Random numberGenerator = new Random();
	private int pourcentage = 25;
	private boolean end;
	
	private  BombermanGame BbmG;

	private String winner =null;
	private int idGagnant = 0;
	private boolean plantage = true;
	
	public final static int PLANTAGE = 0;
	public final static int WIN_SOLO = 1;
	public final static int WIN_SCORE = 2;
	public final static int WIN_SURVIE = 3;
	public final static int EX_AEQUO = 4;
	public final static int GAME_OVER = 5;
	
	
	private int finPartie = PLANTAGE;
	
	private int[] strats;
	
	private Boolean campagne = null;
	
	private int num_niveau = 0;

	private BombermanGame game = new BombermanGame();
	
	
	//Construit l'état courant de la map

	public GameState(Map map,BombermanGame BbmG){
		
		
		ennemies = new ArrayList<Agent_Ennemy>();
		bombermans = new ArrayList<Agent_Bomberman>();
		bombes = new ArrayList<Objet_Bomb>();
		birds = new ArrayList<Agent_Bird>();
		rajions = new ArrayList<Agent_Rajion>();
		items = new ArrayList<Objet>();
		tower = null;
		
		key_action = new Keys();
		key_action_2 = new Keys_2();
		
		this.campagne = false;
		
		this.map=map;
		this.BbmG=BbmG;
		this.campagne = false;

		this.end = false;
		
		ColorBomberman[] Couleurs= ColorBomberman.values();
		for(int i=0;i<map.getNumber_of_bombermans();i++)
		{
			Agent_Bomberman b = new Agent_Bomberman(map.getBomberman_start_x(i), map.getBomberman_start_y(i),i);
			ColorBomberman col;
			if ( i < Couleurs.length) col = Couleurs[i];
			else col = ColorBomberman.DEFAULT;
			b.setCouleur(col);
			bombermans.add(b);
		}

		for(int i=0;i<map.getNumber_of_ennemies();i++)
		{
			Agent_Ennemy a = new Agent_Ennemy(map.getEnnemy_start_x(i), map.getEnnemy_start_y(i),i );
			ennemies.add(a);
		}
		
		for(int i=0;i<map.getNumber_of_birds();i++)
		{
			Agent_Bird a = new Agent_Bird(map.getBird_start_x(i), map.getBird_start_y(i),i );
			birds.add(a);
		}
		
		for(int i=0;i<map.getNumber_of_rajions();i++)
		{
			Agent_Rajion r = new Agent_Rajion(map.getRajion_start_x(i), map.getRajion_start_y(i),i );
			rajions.add(r);
		}

		if(map.tower_x != 0 & map.tower_y != 0)
			this.setTower(new Agent_Tower(map.tower_x,map.tower_y,0));
		else tower = null;
		


	}
	
	//Verifie si l'action de déplacement est possible à l'état courant pour un agent
	
	public boolean isLegalMove(AgentAction action, Agent agent){
		int x = action.getVx();
		int y = action.getVy();
		
		if(map.isWall(agent.getX()+x, agent.getY()+y) || map.isBrokable_Wall(agent.getX()+x, agent.getY()+y) || isBombe(agent.getX()+x, agent.getY()+y) )
			return false;
		else return true;
	}
	 
	//Verifie si l'action de déplacement est possible à l'état courant pour un Bomberman
	
	public boolean isLegalMoveBbm(AgentAction actionbbm, Agent_Bomberman bbm){
		int x = actionbbm.getVx();
		int y = actionbbm.getVy();
			
		if(map.isWall(bbm.getX()+x, bbm.getY()+y) || map.isBrokable_Wall(bbm.getX()+x, bbm.getY()+y) || isBombe(bbm.getX()+x, bbm.getY()+y) || isBomberman(bbm.getId(),bbm.getX()+x, bbm.getY()+y) )
			return false;
		else return true;
	}
	
	//Verifie si l'action de déplacement est possible à l'état courant pour un Bird
	
	public boolean isLegalMoveBird(AgentAction actionbird, Agent_Bird bird){
		int x = actionbird.getVx();
		int y = actionbird.getVy();
				
		if(map.isWall(bird.getX()+x, bird.getY()+y) || isBombe(bird.getX()+x, bird.getY()+y) || isBombe(bird.getX()+x, bird.getY()+y) )
			return false;
		else return true;
	}
	
	//Fonction qui renvoie vrai si une bombe est sous forme physique dans le jeu (et non sous forme d'explosion)
	
	public boolean isBombe(int x, int y) {
		boolean bombeB = false;
		ArrayList<Objet_Bomb> bombes1 = this.bombes;
		for(int i=0; i< bombes1.size(); i++) {
			Objet_Bomb bombe1 = bombes1.get(i);
			if((bombe1.getObjX() == x & bombe1.getObjY() == y) & (bombe1.getEtat() < 10 )) bombeB = true;
		}
			
		return bombeB;
	}
	
	//Fonction qui renvoie vrai si un bomberman se trouve sur le prochain déplacement du bomberman qui va réaliser un déplacement 
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
			
			ArrayList<Agent_Ennemy> emis = this.ennemies;
			for(int i=0; i< emis.size(); i++) {
				Agent emi = emis.get(i);
				if((emi.getX() == x & emi.getY() == y)) en = true;
			}
			
			return en;
		}
		
		public boolean isBird(int x, int y) {
			boolean en = false;
			
			ArrayList<Agent_Bird> birds = this.birds;
			for(int i=0; i< birds.size(); i++) {
				Agent_Bird bird = birds.get(i);
				if((bird.getX() == x & bird.getY() == y)) en = true;
			}
			
			return en;
		}
		
		public boolean isRajion(int x, int y) {
			boolean en = false;
			
			ArrayList<Agent_Rajion> rajions = this.rajions;
			for(int i=0; i< rajions.size(); i++) {
				Agent_Rajion rajion = rajions.get(i);
				if((rajion.getX() == x & rajion.getY() == y)) en = true;
			}
			
			return en;
		}
		
		public boolean isTower(int x, int y) {
			for (int i= 0; i < 4 ; i++) {
				if(x == tower.getCoord_pilliers()[i][0] & y == tower.getCoord_pilliers()[i][1]) return true;
			}
			return false;
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
			bombes.add(bomb);
		}
			
	}

	
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
		
		ArrayList<Agent_Ennemy> ennemies = this.getEnnemies();
		ArrayList<Agent_Bird> birds = this.getBirds();
		ArrayList<Agent_Rajion> rajions = this.getRajions();
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
					Agent_Ennemy ennemie = ennemies.get(j);
					if(ennemie.getX() == i & ennemie.getY() == y & !ennemie.isDead()){
						ennemies.remove(j);
						bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 100);
					}
				}
				
				for(int j = 0; j<birds.size(); j++){
					Agent_Bird bird = birds.get(j);
					if(bird.getX() == i & bird.getY() == y & !bird.isDead()){
						birds.remove(j);
						bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 200);
					}
				}
				
				for(int j = 0; j<rajions.size(); j++){
					Agent_Rajion rajion = rajions.get(j);
					if(rajion.getX() == i & rajion.getY() == y & !rajion.isDead()){
						rajions.remove(j);
						//bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 100);
					}
				}
			
				for(int j = 0; j<bombes.size(); j++){
					Objet_Bomb bombe = bombes.get(j);
					if(bombe.getObjX() == i & bombe.getObjY() == y){
						bombes.get(j).setEtat(10);
					}
				}
				
				if (tower != null )
				if (!tower.isDead()){
					
					for(int j = 0; j < 4; j++){
						if(tower.getCoord_pilliers()[j][0] == i & tower.getCoord_pilliers()[j][1] == y)
							
							if(tower.getCoord_pilliers()[j][2] == 0){
								tower.setPill_detruit(j, true);
							}else{ 
								tower.setPill_life(j, tower.getPill_life(j)-1);
								tower.setHurt(true);
							}
					}
					
					if(tower.getPill_detruit(0) & tower.getPill_detruit(1) & tower.getPill_detruit(2) & tower.getPill_detruit(3)){
						tower.setDead(true);
						bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 1000);
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
				Agent_Ennemy ennemie = ennemies.get(j);
				if(ennemie.getX() == x & ennemie.getY() == i & !ennemies.get(j).isDead()){
					ennemies.remove(j);
					bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 100);
				}
			}
			
			for(int j = 0; j<birds.size(); j++){
				Agent_Bird bird = birds.get(j);
				if(bird.getX() == x & bird.getY() == i & !bird.isDead()){
					birds.remove(j);
					bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 200);
				}
			}
			
			for(int j = 0; j<rajions.size(); j++){
				Agent_Rajion rajion = rajions.get(j);
				if(rajion.getX() == x & rajion.getY() == i & !rajion.isDead()){
					rajions.remove(j);
					//bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 100);
				}
			}
				
				for(int j = 0; j<bombes.size(); j++){
					Objet_Bomb bombe = bombes.get(j);
					if(bombe.getObjX() == x & bombe.getObjY() == i ){
						bombes.get(j).setEtat(10);
					}
				}
				

			if(tower != null)	
				if (!tower.isDead()){
					
					for(int j = 0; j < 4; j++){
						if(tower.getCoord_pilliers()[j][0] == x & tower.getCoord_pilliers()[j][1] == i)
							
							if(tower.getCoord_pilliers()[j][2] == 0){
								tower.setPill_detruit(j, true);
							}else{
								tower.setPill_life(j, tower.getPill_life(j)-1);
								tower.setHurt(true);
							}
					}
					
					if(tower.getPill_detruit(0) & tower.getPill_detruit(1) & tower.getPill_detruit(2) & tower.getPill_detruit(3)){
						tower.setDead(true);
						bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 1000);
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
				Agent_Ennemy ennemie = ennemies.get(j);
				if(ennemie.getX() == i & ennemie.getY() == y & !ennemies.get(j).isDead()){
					ennemies.remove(j);
					bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 100);
				}
			}
			
			for(int j = 0; j<birds.size(); j++){
				Agent_Bird bird = birds.get(j);
				if(bird.getX() == i & bird.getY() == y & !bird.isDead()){
					birds.remove(j);
					bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 200);
				}
			}
					
				for(int j = 0; j<bombes.size(); j++){
					Objet_Bomb bombe = bombes.get(j);
					if(bombe.getObjX() == i & bombe.getObjY() == y){
						bombes.get(j).setEtat(10);
					}
				}
				
				for(int j = 0; j<rajions.size(); j++){
					Agent_Rajion rajion = rajions.get(j);
					if(rajion.getX() == i & rajion.getY() == y & !rajion.isDead()){
						rajions.remove(j);
						//bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 100);
					}
				}
				

			if(tower != null)	
				if (!tower.isDead()){
					
					for(int j = 0; j < 4; j++){
						if(tower.getCoord_pilliers()[j][0] == i & tower.getCoord_pilliers()[j][1] == y)
							
							if(tower.getCoord_pilliers()[j][2] == 0){
								tower.setPill_detruit(j, true);
							}else {
								tower.setPill_life(j, tower.getPill_life(j)-1);
								tower.setHurt(true);
							}
					}
					
					if(tower.getPill_detruit(0) & tower.getPill_detruit(1) & tower.getPill_detruit(2) & tower.getPill_detruit(3)){
						tower.setDead(true);
						bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 1000);
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
				Agent_Ennemy ennemie = ennemies.get(j);
				if(ennemie.getX() == x & ennemie.getY() == i & !ennemies.get(j).isDead()){
					ennemies.remove(j);
					bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 100);
				}
			}
			
			for(int j = 0; j<birds.size(); j++){
				Agent_Bird bird = birds.get(j);
				if(bird.getX() == x & bird.getY() == i & !bird.isDead()){
					birds.remove(j);
					bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 200);
				}
			}
				
				for(int j = 0; j<bombes.size(); j++){
					Objet_Bomb bombe = bombes.get(j);
					if(bombe.getObjX() == x & bombe.getObjY() == i){
						bombes.get(j).setEtat(10);
					}
				}
				
				for(int j = 0; j<rajions.size(); j++){
					Agent_Rajion rajion = rajions.get(j);
					if(rajion.getX() == x & rajion.getY() == i & !rajion.isDead()){
						rajions.remove(j);
						//bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 100);
					}
				}

			if(tower != null)
				if (!tower.isDead()){
					
					for(int j = 0; j < 4; j++){
						if(tower.getCoord_pilliers()[j][0] == x & tower.getCoord_pilliers()[j][1] == i)
							
							if(tower.getCoord_pilliers()[j][2] == 0){
								tower.setPill_detruit(j, true);
							}else {
								tower.setPill_life(j, tower.getPill_life(j)-1);
								tower.setHurt(true);
							}
					}
					
					if(tower.getPill_detruit(0) & tower.getPill_detruit(1) & tower.getPill_detruit(2) & tower.getPill_detruit(3)){
						tower.setDead(true);
						bombermans.get(bomb.getId_bbm()).setPoints(bombermans.get(bomb.getId_bbm()).getPoints() + 1000);
					}
				}
		}
		
	}
	
	//Réalise un tour du jeu 
	
	public void taketurn(){
		if(getCampagne()) {

			if(!getEnd()) {

				this.isEndCampagne(BbmG);
				bombermansTurn();
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
					game.etatJeu.getBombermans().get(0).setPoints(BbmG.etatJeu.getBombermans().get(0).getPoints());
					game.etatJeu.getBombermans().get(0).setRange(BbmG.etatJeu.getBombermans().get(0).getRange());
					game.etatJeu.getBombermans().get(0).setNbBombes(BbmG.etatJeu.getBombermans().get(0).getNbBombes());
					
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
					game.etatJeu.getBombermans().get(0).setPoints(BbmG.etatJeu.getBombermans().get(0).getPoints());
					game.etatJeu.getBombermans().get(0).setRange(BbmG.etatJeu.getBombermans().get(0).getRange());
					game.etatJeu.getBombermans().get(0).setNbBombes(BbmG.etatJeu.getBombermans().get(0).getNbBombes());
					
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
				bombermansTurn();
				ennemiesTurn();
				birdsTurn();
				rajionsTurn();
				towerTurn();
			}else {

				BbmG.stop();
			}
	}	
	
	

	//Réalise le tour de l'ennemi
	
	public void ennemiesTurn(){

		ArrayList<Agent_Ennemy> ennemies = this.getEnnemies();
		
		
		if(BbmG.getTurn() % 4 == 0) {
		
			for(int i = 0; i < ennemies.size(); i++){
	
				Agent ennemy = ennemies.get(i);
				
				AgentAction ennemyAction = ennemy.chooseAction(this);
				
	
				if (ennemyAction != null){
					
					this.moveAgent(ennemy, ennemyAction);
				}
				
			}
		}
	}

	
	//Réalise le tour de l'ennemi
	
	public void birdsTurn(){

		ArrayList<Agent_Bird> birds = this.getBirds();
		
		if(BbmG.getTurn() % 4 == 0) {

			for(int i = 0; i < birds.size(); i++){
	
				Agent bird = birds.get(i);
					
				AgentAction birdAction = bird.chooseAction(this);
					
	
				if (birdAction != null){
						
					this.moveAgent(bird, birdAction);
				}
					
			}
		}
	}
	
	//Réalise le tour des ennemies rajions
	
	public void rajionsTurn(){

		ArrayList<Agent_Rajion> rajions = this.getRajions();
		
		if(BbmG.getTurn() % 4 == 0) {
			
			for(int i = 0; i < rajions.size(); i++){
				
				Agent rajion = rajions.get(i);
				AgentAction rajionAction = rajion.chooseAction(this);
				if (rajionAction != null) this.moveAgent(rajion, rajionAction);
					
			}
		}
	}
	
	
	//Réalise le tour des bombermans
	
		public void bombermansTurn() {
			
			ArrayList<Agent_Bomberman> bombermans = this.getBombermans();
			
			if(BbmG.getTurn() % 2 == 0) {

			for(int i = 0; i < bombermans.size(); i++){
				
				Agent_Bomberman bomberman = bombermans.get(i);
				AgentAction bombermanAction;
				
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
								
								bomberman.setMaladie((int) (Math.random()*3));//(int) (Math.random()*3));
	//							System.out.println("	maladie : "+bomberman.getMaladie());
								bomberman.setSick(true);
								bomberman.setEtatSick(0);
							}
							
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
					
					
						bombermanAction = bomberman.chooseAction(this);
	
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
		
		
	public void towerTurn(){
		if (tower != null) {
			
			if (tower.isHurt()){
				
				tower.setEtat(tower.getEtat()+1);
				if(tower.getEtat() == 6){
					tower.setHurt(false);
					tower.setEtat(0);
				}
			}
			
			if (!tower.isDead() & getRajions().size() < 4){
				Agent_Rajion r = new Agent_Rajion(tower.getX(), tower.getY()+1 ,getRajions().size()+1 );
				rajions.add(r);
			}
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
	
	//Fonction permettant de mettre à terme au jeu quand l'un des bombermans a gagné.
	
	public void isEnd(BombermanGame game) {
		ArrayList<Agent_Bomberman> bombermans = this.getBombermans();
		ArrayList<Agent_Ennemy> ennemies = this.getEnnemies();
		ArrayList<Agent_Bird> birds = this.getBirds();
		ArrayList<Agent_Rajion> rajions = this.getRajions();
		
		int nbBbm = this.getMap().getNumber_of_bombermans();
		int compteBbm = 0;
		int compteEnn = 0;
		int compteExec = 0;
		int maxScore = 0;
		int aux ;
		
		
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
			
		}for(int i = 0; i<birds.size(); ++i) {
			if(!birds.get(i).isDead()) {
				compteEnn++;
			}
		}
		
		for(int i = 0; i<rajions.size(); ++i) {
			if(!rajions.get(i).isDead()) {
				compteEnn++;
			}
		}
		
		
		for(int i = 0; i<birds.size(); ++i) {
			if(!birds.get(i).isDead()) {
				compteEnn++;
			}
		}
		
		if(compteBbm == 0  & nbBbm == 1) {
			setEnd(true);
			game.etatJeu.setEnd(true);
			this.winner = "GAME OVER";
			this.finPartie = GAME_OVER;
		}
		
		if(compteBbm == 1 & nbBbm == 1 & compteEnn == 0) {
			setEnd(true);
			game.etatJeu.setEnd(true);
			winner = "Le joueur "+(bombermans.get(idGagnant).getId()+1)+" est le gagnant Partie SOLO";
			this.finPartie = WIN_SOLO;
			
		}
		
		
		if(compteBbm == 1 & nbBbm != 1) {
			setEnd(true);
			game.etatJeu.setEnd(true);
			this.winner = "Le joueur "+(bombermans.get(idGagnant).getId()+1)+" est le gagnant";
			this.finPartie = WIN_SURVIE;
		}
		
		if(compteBbm == 0 & nbBbm != 1) {
			setEnd(true);
			game.etatJeu.setEnd(true);
			this.winner = "GAME OVER";
			this.finPartie = GAME_OVER;
		}
		
		
		if(game.getTurn() == (game.getMaxTurn()-1) ) {
			setEnd(true);
			game.etatJeu.setEnd(true);
			
			if(nbBbm == 1) {
				setEnd(true);
				game.etatJeu.setEnd(true);
				this.winner = "GAME OVER";
				this.finPartie = GAME_OVER;

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
					setEnd(true);
					this.winner = "Le joueur "+(bombermans.get(idGagnant).getId()+1)+" est le gagnant par score = " + maxScore ;
					this.finPartie = WIN_SCORE;
				}
				else {
					setEnd(true);
					this.winner = "Il y a Ex aequo ";
					this.finPartie = EX_AEQUO;
				}
			}
			
		}
		//if (winner != null) System.out.println(this.winner);
}
	

	//lorsque le mode de jeu choisi est un mode campagne cette methode est choisi
	
	public void isEndCampagne(BombermanGame game) {
		
		ArrayList<Agent_Bomberman> bombermans = this.getBombermans();
		ArrayList<Agent_Ennemy> ennemies = this.getEnnemies();
		
		int nbBbm = this.getMap().getNumber_of_bombermans();
		int compteBbm = 0;
		int compteEnn = 0;
		
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
			setNum_niveau(3);
			game.etatJeu.setEnd(true);
			this.winner = "GAME OVER";
			this.finPartie = GAME_OVER;

		}
		
		if(compteBbm == 1 & nbBbm == 1 & compteEnn == 0) {
			setEnd(true);
			game.etatJeu.setEnd(true);
			winner = "Le joueur "+(bombermans.get(idGagnant).getId()+1)+" est le gagnant Partie SOLO";
			this.finPartie = WIN_SOLO;
		}
		
		if(game.getTurn() == (game.getMaxTurn()-1) ) {
			setEnd(true);
			setNum_niveau(3);
			game.etatJeu.setEnd(true);
			
			if(nbBbm == 1) {
				setEnd(true);
				game.etatJeu.setEnd(true);
				this.winner = "GAME OVER";
				this.finPartie = GAME_OVER;

			}
		}
			
	}

	//Renvoie un agent en fonction d'un id 

	public Agent_Ennemy getEnnemy(GameState etat, int agentId){
		
		for (Agent_Ennemy p : etat.getEnnemies()){
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
	
	//accesseur sur la liste de birds
	
	public ArrayList<Agent_Bird> getBirds(){
		return birds;
	}
	
	//accesseur sur la liste de birds
	
	public ArrayList<Agent> getAll_ennemies(){
		ArrayList<Agent> allEnnemies = new ArrayList<Agent>();
		
		for(int i=0; i < getEnnemies().size(); i++) {
			allEnnemies.add(getEnnemies().get(i));
		}
		
		for(int i=0; i < getBirds().size(); i++) {
			allEnnemies.add(getBirds().get(i));
		}
		
		return allEnnemies;
	}
		
	
	public ArrayList<Agent_Rajion> getRajions() {
		return rajions;
	}

	public ArrayList<Objet_Bomb> getBombes(){
		return bombes;
	}

	//accesseur sur la liste d'items
	
	public ArrayList<Objet> getItems(){
		return items;
	}	
	
	//accesseur sur la liste d'enemies
	
	public ArrayList<Agent_Ennemy> getEnnemies(){
		return ennemies;
	}
	
	//accesseur sur la map courante 
	
	public Map getMap(){
		return map;
	}
	
	//permet de savoir si le jeu est terminé ou non

	public boolean getEnd() {
		return end;
	}
	
	public boolean setEnd(boolean e) {
		return end = e;
	}
	
	
	//permet d'avoir la phrase du fin de jeu
	
	public String getWinner() {
		return winner;
	}

	public int getIdGagnant() {
		return idGagnant;
	}
	
	
	public Keys getKey_action() {
		return key_action;
	}

	public Keys_2 getKey_action_2() {
		return key_action_2;
	}
	
	public int[] getStrats() {
		return strats;
	}


	public boolean isPlantage() {
		return plantage;
	}
	
	public void setStrats(int[] strats) {
		this.strats = strats;
		for (int i = 0 ; i < this.bombermans.size() ; i++){
			this.bombermans.get(i).setStrat(strats[i]);
		}
	}

	public int getFinPartie() {
		return finPartie;
	}

//	public void setFinPartie(int finPartie) {
//		this.finPartie = finPartie;
//	}
	
	//permet de savoir si le mode de jeu choisi est une campagne 
						
	public Boolean getCampagne() {
		return campagne;
	}

	public void setCampagne(Boolean campagne) {
		this.campagne = campagne;
	}
	
	public int getNum_niveau() {
		return num_niveau;
	}

	public void setNum_niveau(int num_niveau) {
		this.num_niveau = num_niveau;
	}
	
	public BombermanGame getGame() {
		return game;
	}

	public Agent_Tower getTower() {
		return tower;
	}

	public void setTower(Agent_Tower agent_Tower) {
		this.tower = agent_Tower;
	}

}
