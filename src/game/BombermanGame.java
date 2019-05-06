package game;

import map.GameState;
import map.Map;

public class BombermanGame extends Game implements InterfaceGame {

	Map map;
	public GameState etatJeu;
	private String filename = null;
	
	public void loadFile(String filename) throws Exception {
		map = new Map(filename);
		String sep = "/";
		String stage[] = filename.split(sep);
		String sep2 = ".lay";
		String stage2[] = stage[2].split(sep2);
		this.filename = stage2[0];
	}
	
	@Override
	public void initializeGame() {
		System.out.println("initialisation bombermanGame");
		this.etatJeu = new GameState(map,this);
	}



	@Override
	public void taketurn() {
		if(!etatJeu.getEnd())
			etatJeu.taketurn();	
		
	}
	
	@Override
	public void gameOver() {

	}
	
	public Map getMap(){
		return map;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
