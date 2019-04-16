import game.BombermanGame;
import graphics.Cadre_Jeu;


public class Test_main {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		BombermanGame BbmG = new BombermanGame();
		try {
			BbmG.loadFile("./layout/jeu1.lay");
			BbmG.init();
			BbmG.launch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Cadre_Jeu fenetre = new Cadre_Jeu(BbmG);
		fenetre.setVisible(true);

	}

}
