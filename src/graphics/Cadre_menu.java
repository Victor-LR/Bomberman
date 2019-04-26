package graphics;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

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
	private JComboBox liste_lay;
	private JLabel choixStage;
	private JLabel choixMode;
	private JComboBox mode;
	private JButton jouer = null;
	private Review review = null;
	private JPanel choix = null;
	
	private int nb_threads = 2000;
	
	public Cadre_menu() {
		
		this.setSize(550, 300);
		this.setLocationRelativeTo(null);
		this.setTitle("Menu Jeu Bomberman");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		BombermanGame BbmG = new BombermanGame();
		
		
		choix = new JPanel();
		choix.setLayout(new GridLayout(3,2));
		
		choixMode = new JLabel("Choix du mode de jeu : ");
		choix.add(choixMode);
		
		String[] modes = {"Joueur","Auto","Multi"};
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
			
			if(mode.getSelectedItem().toString() == "Multi") {
				String file =liste_lay.getSelectedItem().toString();
				ArrayList<BombermanGame> L_BbmG = new ArrayList<BombermanGame>();
				for (int i = 0 ; i < nb_threads ; i++){
					BombermanGame un_bbmg = new BombermanGame();
					try {
						un_bbmg.loadFile(file);

					} catch (Exception e) {
						e.printStackTrace();
					}
					un_bbmg.init();
//					un_bbmg.etatJeu.setMode_jeu(false);
					un_bbmg.setTemps(1);
					un_bbmg.new_thread();
					L_BbmG.add(un_bbmg);
					un_bbmg.getThread().start();
				}
					
				for(int j = 0 ; j < L_BbmG.size(); j++){
					try {
						L_BbmG.get(j).getThread().join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
	
				Cadre_multi c_m = new Cadre_multi(L_BbmG,nb_threads);
				c_m.setVisible(true);
				
				cadre_menu.dispose();
			}else {
				BombermanGame un_bbmg = new BombermanGame();
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
			}
		});

	
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
