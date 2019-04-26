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
//	private JLabel choixMode;
	private JComboBox mode;
	private JButton jouer = null;
	private JButton multi = null;
	private Review review = null;
	private JPanel choixStrats = null;
	private JPanel choix = null;
	private ArrayList<JComboBox> listStrat = null;
	
	private int[] strategies = new int[10];
	
	private int nb_threads = 2000;
	
	public Cadre_menu() {
		
		this.setSize(550, 450);
		this.setLocationRelativeTo(null);
		this.setTitle("Menu Jeu Bomberman");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		BombermanGame BbmG = new BombermanGame();
		
		listStrat = new ArrayList<JComboBox>();
		
		choix = new JPanel();
		choix.setLayout(new GridLayout(1,1));
		
		choixStrats = new JPanel();
		
		JPanel panelMap = new JPanel();
		panelMap.setLayout(new GridLayout(2,2));
		
		choixStage = new JLabel("Choix du stage : ");
		panelMap.add(choixStage);
		
		File repertoire = new File("./layout/");
		File[] files=repertoire.listFiles();
		
		liste_lay = new JComboBox(files);
		panelMap.add(liste_lay);
		
		jouer = new JButton("Jouer");
		multi = new JButton("Multi");
		
		panelMap.add(jouer);
		panelMap.add(multi);
		
		choix.add(panelMap);
		
		String content = liste_lay.getSelectedItem().toString();
		
		try {
			BbmG.loadFile(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BbmG.init();
		review = new Review(BbmG);
		
		choixStrats.setLayout(new GridLayout(BbmG.etatJeu.getBombermans().size(),2));
		
		String[] nomStrat = {"Auto","Joueur1","Joueur2","A_Items","A","B","C","PVE","PVP"};
		
		for(int i =0; i<BbmG.etatJeu.getBombermans().size();i++) {
			JComboBox liste =  new JComboBox(nomStrat);
			choixStrats.add(new JLabel("Joueur n°"+(int)(BbmG.etatJeu.getBombermans().get(i).getId()+1)));
			listStrat.add(liste);
			choixStrats.add(liste);
			
		}
		
		
		this.add("South",choixStrats);
		this.add("North",choix);
		this.add("Center",review);

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
			
				
				for(int i =0; i<listStrat.size();i++) {
					System.out.println(listStrat.get(i).getSelectedItem().toString());
					switch(listStrat.get(i).getSelectedItem().toString()) {
						case "Auto":
							strategies[i]=0;
						break;
						
						case "Joueur1":
							strategies[i]=1;
						break;
						
						case "Joueur2":
							strategies[i]=2;
						break;
					
						case "A_Items":
							strategies[i]=3;
						break;
						
						case "A":
							strategies[i]=4;
						break;
						
						case "B":
							strategies[i]=5;
						break;
						
						case "C":
							strategies[i]=6;
						break;
						
						case "PVE":
							strategies[i]=7;
						break;
						
						case "PVP":
							strategies[i]=8;
						break;
					}
			

				}
				
				
				BbmG.etatJeu.setStrats(strategies);
				
				BbmG.launch();
				
				Cadre_Jeu fenetre = new Cadre_Jeu(BbmG);
				fenetre.setVisible(true);
				
				cadre_menu.dispose();
			}
		});
	
	multi.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent evenement) {
			
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
					
					for(int j =0; j<un_bbmg.etatJeu.getBombermans().size();j++) {
						switch(listStrat.get(j).getSelectedItem().toString()) {
							case "Auto":
								strategies[j]=0;
							break;
							
							case "Joueur 1":
								strategies[j]=1;
							break;
							
							case "Joueur 2":
								strategies[j]=2;
							break;
						
							case "A_Items":
								strategies[j]=3;
							break;
							
							case "A":
								strategies[j]=4;
							break;
							
							case "B":
								strategies[j]=5;
							break;
							
							case "C":
								strategies[j]=6;
							break;
							
							case "PVE":
								strategies[j]=7;
							break;
							
							case "PVP":
								strategies[j]=8;
							break;
						}
				
					}
					
					un_bbmg.setTemps(1);
					un_bbmg.new_thread();
					un_bbmg.etatJeu.setStrats(strategies);
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
			
				
				
				}
			
		});

	
	liste_lay.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evenement) {
			setSize(550,300);
			listStrat = new ArrayList<JComboBox>();
			remove(choixStrats);
			choixStrats = new JPanel();
			remove(review);
			BombermanGame game = new BombermanGame();
			try {
				game.loadFile((liste_lay.getSelectedItem().toString()));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			game.init();
			
			choixStrats.setLayout(new GridLayout(game.etatJeu.getBombermans().size(),2));
			
			String[] nomStrat = {"Auto","Joueur1","Joueur2","A_Items","A","B","C","PVE","PVP"};
			
			for(int i =0; i<game.etatJeu.getBombermans().size();i++) {
				
				JComboBox liste =  new JComboBox(nomStrat);
				choixStrats.add(new JLabel("Joueur n°"+(int)(BbmG.etatJeu.getBombermans().get(i).getId()+1)));
				listStrat.add(liste);
				choixStrats.add(liste);
			}
			add("South",choixStrats);
			
			review = new Review(game);
			add("Center",review);
			setSize(551,300);
			}
		});
	}
	
}
