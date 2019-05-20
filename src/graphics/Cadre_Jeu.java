package graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;



//import map.Map;
import game.BombermanGame;
import graphics.paint_bomberman;
import graphics.paint_score;

public class Cadre_Jeu extends JFrame { 
	
	//JFrame composé de deux JPanel : les scores et le jeu en lui même
	
	private static final long serialVersionUID = 1L;
    private JPanel p_bm;
	private paint_score p_sc;
	
    //Création de la fenêtre de jeu
	
	public Cadre_Jeu(BombermanGame BbmG){
		
		    this.setSize(BbmG.etatJeu.getMap().getSizeX()*50, (BbmG.etatJeu.getMap().getSizeY()*50)+45);
		    this.setTitle("Jeu Bomberman");
		    this.setLocationRelativeTo(null);

		    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         
	        
	        this.setLayout(new BorderLayout());
	     
	        p_sc = new paint_score(this,BbmG);
	        this.add("North",p_sc.getPanel());
	        
	        p_bm = new paint_bomberman(this,BbmG);
	        this.add("Center",p_bm);
	        
	        this.setVisible(true);
	}
	
    public paint_score getP_sc() {
		return p_sc;
	}

	public void setP_sc(paint_score p_sc) {
		this.p_sc = p_sc;
	}

	public JPanel getP_bm() {
		return p_bm;
	}
}
