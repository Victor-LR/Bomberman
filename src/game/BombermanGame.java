package game;

import java.io.Serializable;

import map.GameState;
import map.Map;

public class BombermanGame extends Game implements InterfaceGame, Serializable {

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
	
	public BombermanGame () {

	}
	
	public BombermanGame (GameState etatJ, Map map){
		this.etatJeu = etatJ;
//		this.etatJeu.setStrats(etatJ.getStrats());
//		this.etatJeu.setCampagne(etatJ.getCampagne());
		this.map = map;
	}
	
	@Override
	public void initializeGame() {
		System.out.println("initialisation bombermanGame");
		this.etatJeu = new GameState(map,this);
	}



	@Override
	public void taketurn() {
		if(!etatJeu.getEnd())
			etatJeu.taketurn(null,-1);	
		
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

	public void setMaxTurn(int maxT) {
		this.maxTurn = maxT;
		
	}
}
