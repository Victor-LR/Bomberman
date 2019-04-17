import game.BombermanGame;
import graphics.Cadre_Jeu;
import graphics.Cadre_menu;


public class Test_main {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		
//		BombermanGame BbmG = new BombermanGame();
//		try {
//			BbmG.loadFile("./layout/alone.lay");
//			BbmG.init();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Cadre_Jeu fenetre = new Cadre_Jeu(BbmG);
//		fenetre.setVisible(true);
		Cadre_menu fen = new Cadre_menu("./layout/jeu1.lay");
		fen.setVisible(true);
		//BbmG.launch();
	}

}
