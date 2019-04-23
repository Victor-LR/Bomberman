package graphics;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import game.BombermanGame;

public class Cadre_menu extends JFrame{

	private static final long serialVersionUID = 1L;
//	private JButton joueur = new JButton();
//	private JButton auto = new JButton();
	private JComboBox liste_lay;
	private JLabel choixStage;
	private JLabel choixMode;
	private JComboBox mode;
	private JButton jouer = null;
	private Review review = null;
//	private JMenuBar menu = null;
//	private JMenu jeuType = null;
//	private JMenuItem joueur = null;
//	private JMenuItem auto = null;
	private JPanel choix = null;
	
	public Cadre_menu() {
		
		this.setSize(550, 300);
		this.setLocationRelativeTo(null);
		this.setTitle("Menu Jeu Bomberman");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		BombermanGame BbmG = new BombermanGame();
		
		
		choix = new JPanel();
		choix.setLayout(new GridLayout(3,2));
		
//		menu = new JMenuBar();
//		choix.add("North",menu);
//		
//		jeuType = new JMenu("Mode");
//		menu.add(jeuType);
//		
//		joueur = new JMenuItem("Joueur");
//		joueur.setText("Joueur");
//		jeuType.add(joueur);
//		
//		auto = new JMenuItem("Auto");
//		auto.setText("Automatique");
//		jeuType.add(auto);	
		
		choixMode = new JLabel("Choix du mode de jeu : ");
		choix.add(choixMode);
		
		String[] modes = {"Joueur","Auto"};
		mode = new JComboBox(modes);
		choix.add(mode);
		
		choixStage = new JLabel("Choix du stage : ");
		choix.add(choixStage);
		
		File repertoire = new File("./layout/");
		File[] files=repertoire.listFiles();
		
		liste_lay = new JComboBox(files);
		choix.add(liste_lay);
		
		jouer = new JButton("Jouer");
		choix.add(jouer);
		
		this.add("North",choix);
		
		
		String content = liste_lay.getSelectedItem().toString();
		
		try {
			BbmG.loadFile(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BbmG.init();
		review = new Review(BbmG);
		add("Center",review);

		creer_button(this,BbmG);
		
		System.out.println(content);
	}
	
	
	public void creer_button(final Cadre_menu cadre_menu, final BombermanGame BbmG){
	//joueur.setFocusPainted(false);
	jouer.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evenement) {
			try {
				BbmG.loadFile((liste_lay.getSelectedItem().toString()));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			BbmG.init();
			
			Cadre_Jeu fenetre = new Cadre_Jeu(BbmG);
			fenetre.setVisible(true);
			
			if(mode.getSelectedItem().toString() == "Joueur")
				BbmG.etatJeu.setMode_jeu(true);
			else BbmG.etatJeu.setMode_jeu(false);
			
			BbmG.launch();
			
			cadre_menu.dispose();
			}
		});
//	//auto.setFocusPainted(false);
//	auto.addActionListener(new ActionListener() {
//		public void actionPerformed(ActionEvent evenement) {
//			try {
//				BbmG.loadFile((liste_lay.getSelectedItem().toString()));
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			BbmG.init();
//			Cadre_Jeu fenetre = new Cadre_Jeu(BbmG);
//			
//			fenetre.setVisible(true);
//			BbmG.etatJeu.setMode_jeu(false);
//			BbmG.launch();
//			
//			cadre_menu.dispose();
//			}
//		});
	
	liste_lay.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evenement) {
			setSize(550,300);
			remove(review);
			BombermanGame game = new BombermanGame();
			try {
				game.loadFile((liste_lay.getSelectedItem().toString()));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			game.init();
			
			review = new Review(game);
			add("Center",review);
			setSize(551,300);
			}
		});
	}
	
	
}
