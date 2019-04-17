package graphics;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import game.BombermanGame;
import graphics.paint_bomberman;
import graphics.paint_score;

public class Cadre_menu extends JFrame{

	private static final long serialVersionUID = 1L;
	private JButton joueur = new JButton();
	private JButton auto = new JButton();
	
	public Cadre_menu(String filename) {
		
		this.setSize(200, 100);
		this.setLocationRelativeTo(null);
		this.setTitle("Menu Jeu Bomberman");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(2,1));
		
		joueur.setText("Joueur");
		this.add("North",joueur);
		auto.setText("Automatique");
		this.add("Center",auto);
		
		BombermanGame BbmG = new BombermanGame();
		try {
			BbmG.loadFile(filename);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		creer_button(this,BbmG);
		this.dispose();
	}
	
	public void creer_button(final Cadre_menu cadre_menu, final BombermanGame BbmG){
	joueur.setFocusPainted(false);
	joueur.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evenement) {
			
			BbmG.init();
			Cadre_Jeu fenetre = new Cadre_Jeu(BbmG);
			fenetre.setVisible(true);
			BbmG.etatJeu.setMode_jeu(true);
			BbmG.launch();
			
			cadre_menu.dispose();
			}
		});
	auto.setFocusPainted(false);
	auto.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evenement) {
			

			BbmG.init();
			Cadre_Jeu fenetre = new Cadre_Jeu(BbmG);
			fenetre.setVisible(true);
			BbmG.etatJeu.setMode_jeu(false);
			BbmG.launch();
			
			cadre_menu.dispose();
			}
		});
	}
	
}
