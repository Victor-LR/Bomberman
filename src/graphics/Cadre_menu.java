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
	private Review review = null;
	private JPanel choix = null;
	
	public Cadre_menu() {
		
		this.setSize(550, 500);
		this.setLocationRelativeTo(null);
		this.setTitle("Menu Jeu Bomberman");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(2,1));
		
		BombermanGame BbmG = new BombermanGame();
		
		choix = new JPanel();
		choix.setLayout(new GridLayout(3,1));
		
		joueur.setText("Joueur");
		choix.add("North",joueur);
		auto.setText("Automatique");
		choix.add("Center",auto);
		
		File repertoire = new File("./layout/");
		File[] files=repertoire.listFiles();

		liste_lay = new JComboBox(files);
		choix.add(liste_lay);
		
		this.add(choix);
		
		String content = liste_lay.getSelectedItem().toString();
		
		try {
			BbmG.loadFile(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BbmG.init();
		review = new Review(BbmG);
		add(review);

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
	
	liste_lay.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evenement) {
			try {
				BbmG.loadFile((liste_lay.getSelectedItem().toString()));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			BbmG.init();
			review = new Review(BbmG);
			
			}
		});
	}
	
	
}
