package graphics;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import game.BombermanGame;
import graphics.paint_bomberman;
import graphics.paint_score;

public class Cadre_menu extends JFrame{

	private static final long serialVersionUID = 1L;
	private JButton joueur = new JButton();
	private JButton auto = new JButton();
	private JComboBox liste_lay;
	
	public Cadre_menu() {
		
		this.setSize(200, 100);
		this.setLocationRelativeTo(null);
		this.setTitle("Menu Jeu Bomberman");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(3,1));
		
		joueur.setText("Joueur");
		this.add("North",joueur);
		auto.setText("Automatique");
		this.add("Center",auto);
		
		File repertoire = new File("./layout/");
		File[] files=repertoire.listFiles();

		liste_lay = new JComboBox(files);
		this.add(liste_lay);
		
	
		BombermanGame BbmG = new BombermanGame();
		String content = liste_lay.getSelectedItem().toString();

		creer_button(this,BbmG);
		
		System.out.println(content);
	}
	
	
	public void creer_button(final Cadre_menu cadre_menu, final BombermanGame BbmG){
	joueur.setFocusPainted(false);
	joueur.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evenement) {
			try {
				BbmG.loadFile((liste_lay.getSelectedItem().toString()));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			try {
				BbmG.loadFile((liste_lay.getSelectedItem().toString()));
				
			} catch (Exception e) {
				e.printStackTrace();
			}

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
