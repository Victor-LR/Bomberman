package game;

import map.GameState;
import map.Map;

public class BombermanGame extends Game implements InterfaceGame {

	Map map;
	public GameState etatJeu;
	
	public void loadFile(String filename) throws Exception {
		map = new Map(filename);

	}
	
	@Override
	public void initializeGame() {
		System.out.println("initialisation bombermanGame");
		this.etatJeu = new GameState(map);
	}



	@Override
	public void taketurn() {
		etatJeu.taketurn();	
	}
	
	@Override
	public void gameOver() {

	}
	
	public Map getMap(){
		return map;
	}

}
