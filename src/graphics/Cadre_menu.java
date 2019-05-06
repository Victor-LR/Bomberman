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

import agents.Agent_Bomberman;
import game.BombermanGame;

public class Cadre_menu extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private BombermanGame BbmG = null;
	
	private JComboBox<File> liste_lay;
	private JLabel choixStage;
	private JButton jouer = null;
	private JButton multi = null;
	private Review review = null;
	private JPanel panelMap = null;
	private JPanel choixStrats = null;
	private JPanel choix = null;
	private ArrayList<JComboBox<String>> listStrat = null;
	private JMenuBar menu = null;
	private JMenu mode = null;
	private JMenuItem campagne= null;
	private JMenuItem normal = null;
	private String content = null;
	private Boolean is_campagne = null;
	
	private int[] strategies = new int[100];
	
	private int nb_threads = 1000;
	
	public Cadre_menu() {
		
		
		this.setTitle("Menu Jeu Bomberman");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		BbmG = new BombermanGame();
		
		menu = new JMenuBar();
		
		mode = new JMenu();
		mode.setText("Mode");
		
		campagne = new JMenuItem();
		campagne.setText("campagne");
		is_campagne = false;
		
		
		normal = new JMenuItem();
		normal.setText("normal");
		
		mode.add(campagne);
		mode.add(normal);
		
		menu.add(mode);
		
		listStrat = new ArrayList<JComboBox<String>>();
		
		choix = new JPanel();
		choix.setLayout(new GridLayout(2,1));
		
		choix.add(menu);
		
		choixStrats = new JPanel();
		
		panelMap = new JPanel();
		panelMap.setLayout(new GridLayout(2,2));
		
		choixStage = new JLabel("Choix du stage : ");
		panelMap.add(choixStage);
		
		File repertoire = new File("./layout/");
		File[] files=repertoire.listFiles();
		
		liste_lay = new JComboBox<File>(files);
		panelMap.add(liste_lay);
		
		jouer = new JButton("Jouer");
		multi = new JButton("Multi");
		
		panelMap.add(jouer);
		panelMap.add(multi);
		
		choix.add(panelMap);
		
		content = liste_lay.getSelectedItem().toString();
		
		try {
			BbmG.loadFile(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BbmG.init();
		review = new Review(BbmG);
		
		
		choixStrats.setLayout(new GridLayout(BbmG.etatJeu.getBombermans().size(),2));
		
		String[] nomStrat = {"Auto","Joueur1","Joueur2","A_Items","A","B","C","PVE","PVP","D","A PVP"};
		
		for(int i =0; i<BbmG.etatJeu.getBombermans().size();i++) {
			JComboBox<String> liste =  new JComboBox<String>(nomStrat);
			choixStrats.add(new JLabel("Joueur n°"+(int)(BbmG.etatJeu.getBombermans().get(i).getId()+1)));
			listStrat.add(liste);
			choixStrats.add(liste);
			
		}
		
		
		this.add("South",choixStrats);
		this.add("North",choix);
		this.add("Center",review);
		
		System.out.println(review.getTaille_x());
		
		this.setSize(review.getTaille_x()*40, review.getTaille_y()*40+BbmG.etatJeu.getBombermans().size()*25+50);
		this.setLocationRelativeTo(null);

		creer_button(this,BbmG);
		
		System.out.println(content);
	}
	
	
	public void creer_button(final Cadre_menu cadre_menu, final BombermanGame BbmG){
	//joueur.setFocusPainted(false);
		campagne.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evenement) {
				
				is_campagne = true;
				
				panelMap.remove(choixStage);
				panelMap.remove(liste_lay);
				
				listStrat = new ArrayList<JComboBox<String>>();
				remove(choixStrats);
				choixStrats = new JPanel();
				remove(review);
				
				content = "./layout/niveau1.lay";
				
				try {
					BbmG.loadFile(content);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				BbmG.init();
				
				choixStrats.setLayout(new GridLayout(BbmG.etatJeu.getBombermans().size(),2));
				
				String[] nomStrat = {"Auto","Joueur1","Joueur2","A_Items","A","B","C","PVE","PVP","D","A PVP"};
				
				for(int i =0; i<BbmG.etatJeu.getBombermans().size();i++) {
					
					JComboBox<String> liste =  new JComboBox<String>(nomStrat);
					choixStrats.add(new JLabel("Joueur n°"+(int)(BbmG.etatJeu.getBombermans().get(i).getId()+1)));
					listStrat.add(liste);
					choixStrats.add(liste);
				}
				add("South",choixStrats);
				
				review = new Review(BbmG);
				add("Center",review);
				setSize(review.getTaille_x()*40, review.getTaille_y()*40+BbmG.etatJeu.getBombermans().size()*25+50);
				setLocationRelativeTo(null);
				
				revalidate();
				System.out.println("					test");
			}
		});
		
		normal.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evenement) {
				
				is_campagne = false;
				panelMap.add(choixStage);
				panelMap.add(liste_lay);
				revalidate();
				System.out.println("					test");
			}
		});
	
		jouer.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evenement) {
				
					try {
						BbmG.loadFile(content);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					BbmG.init();
					
					if(is_campagne) {
						BbmG.etatJeu.setCampagne(true);
						BbmG.etatJeu.setNum_niveau(1);
					}
					else BbmG.etatJeu.setCampagne(false);
					
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
								
							case "D":
								strategies[i]=9;
							break;
							
							case "A PVP":
								strategies[i]=10;
							break;
						}
				
	
					}
					
					
					BbmG.etatJeu.setStrats(strategies);
					
					Cadre_Jeu fenetre = new Cadre_Jeu(BbmG);
					if(is_campagne) {
						fenetre.getP_sc().getPanBoutton().remove(fenetre.getP_sc().getRestart());
					}
					fenetre.setVisible(true);
					
					BbmG.launch();
					cadre_menu.dispose();
				}
			});
		
		multi.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evenement) {
				if(is_campagne) {
					ArrayList<Integer> index_winner = new ArrayList<Integer>();
					ArrayList<Integer> index_winner2 = new ArrayList<Integer>();
					ArrayList<BombermanGame> L_BbmG = new ArrayList<BombermanGame>();
					ArrayList<BombermanGame> L_BbmG2 = new ArrayList<BombermanGame>();
					ArrayList<BombermanGame> L_BbmG3 = new ArrayList<BombermanGame>();
					
					//on lance tout les threads demandé dans le mode campagne sur le premier niveau 
					
					for (int i = 0 ; i < nb_threads ; i++){
						BombermanGame un_bbmg = new BombermanGame();
						try {
							un_bbmg.loadFile(content);
	
						} catch (Exception e) {
							e.printStackTrace();
						}
						un_bbmg.init();
						
						
						un_bbmg.etatJeu.setCampagne(true);
						un_bbmg.etatJeu.setNum_niveau(1);									
						
						
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
								
								case "D":
									strategies[j]=9;
								break;
								
								case "A PVP":
									strategies[j]=10;
								break;
							}
					
						}
						
						un_bbmg.setTemps(1);
						un_bbmg.new_thread();
						un_bbmg.etatJeu.setStrats(strategies);
						L_BbmG.add(un_bbmg);
						un_bbmg.getThread().start();
						
						System.out.println("	Thread n°"+i);
					}
					
					//verification des bomberman gagant le premier niveau e on joint les threads du premier niveau 
						
					for(int j = 0 ; j < L_BbmG.size(); j++){
						try {
							L_BbmG.get(j).getThread().join();
							
							System.out.println("	Attente Thread n°"+j);
							
							if(L_BbmG.get(j).etatJeu.getNum_niveau() != 3) 
								index_winner.add(j);
							
						} catch (InterruptedException e) {
							e.printStackTrace();
							System.out.println("erreur !");
						}
					}
					
					//on fait jouer les bomberman gagnant en reprenant leur statistique sur le niveau 2
					
					for (int i = 0 ; i < index_winner.size() ; i++){
						BombermanGame un_bbmg = new BombermanGame();
						Agent_Bomberman old_bbmg = L_BbmG.get(index_winner.get(i)).etatJeu.getBombermans().get(0);
						
						int bombes = old_bbmg.getNbBombes();		
						int range = old_bbmg.getRange();
						int points = old_bbmg.getPoints();
						
						try {
							un_bbmg.loadFile("./layout/niveau2.lay");
	
						} catch (Exception e) {
							e.printStackTrace();
						}
						un_bbmg.init();
						
						un_bbmg.etatJeu.setCampagne(true);
						un_bbmg.etatJeu.setNum_niveau(1);
						un_bbmg.etatJeu.getBombermans().get(0).setPoints(points);
						un_bbmg.etatJeu.getBombermans().get(0).setNbBombes(bombes);
						un_bbmg.etatJeu.getBombermans().get(0).setRange(range);
						
						un_bbmg.setTemps(1);
						un_bbmg.new_thread();
						un_bbmg.etatJeu.setStrats(strategies);
						L_BbmG2.add(un_bbmg);
						un_bbmg.getThread().start();
						
						System.out.println("	Thread n°"+i);
					}
					
					
					//on verifie les gagnant du deuxième stage et on jouin les thread du second niveau 
					
					for(int j = 0 ; j < L_BbmG2.size(); j++){
						try {
							L_BbmG2.get(j).getThread().join();
							System.out.println("	Attente Thread n°"+j);
							if(L_BbmG2.get(j).etatJeu.getNum_niveau() != 3)
								index_winner2.add(j);
						} catch (InterruptedException e) {
							e.printStackTrace();
							System.out.println("erreur !");
						}
					}
					
					//on fait jouer les bomberman qui on gagné le stage précédent sur le 3eme niveau
					
					for (int i = 0 ; i < index_winner2.size() ; i++){
						BombermanGame un_bbmg = new BombermanGame();
						Agent_Bomberman old_bbmg = L_BbmG.get(index_winner2.get(i)).etatJeu.getBombermans().get(0);
						
						int bombes = old_bbmg.getNbBombes();		
						int range = old_bbmg.getRange();
						int points = old_bbmg.getPoints();
						
						try {
							un_bbmg.loadFile("./layout/niveau3.lay");
	
						} catch (Exception e) {
							e.printStackTrace();
						}
						un_bbmg.init();
						
						un_bbmg.etatJeu.setCampagne(true);
						un_bbmg.etatJeu.setNum_niveau(1);
						un_bbmg.etatJeu.getBombermans().get(0).setPoints(points);
						un_bbmg.etatJeu.getBombermans().get(0).setNbBombes(bombes);
						un_bbmg.etatJeu.getBombermans().get(0).setRange(range);
						
						un_bbmg.setTemps(1);
						un_bbmg.new_thread();
						un_bbmg.etatJeu.setStrats(strategies);
						L_BbmG3.add(un_bbmg);
						un_bbmg.getThread().start();
						
						System.out.println("	Thread n°"+i);
					}
					
					for(int j = 0 ; j < L_BbmG3.size(); j++){
						try {
							L_BbmG3.get(j).getThread().join();
							System.out.println("	Attente Thread n°"+j);
						} catch (InterruptedException e) {
							e.printStackTrace();
							System.out.println("erreur !");
						}
					}
					
					System.out.println("			Fin multithreads");
		
					Cadre_multi c_m = new Cadre_multi(L_BbmG3,L_BbmG3.size());
					c_m.setVisible(true);
					
					cadre_menu.dispose();
				}
				else {
					ArrayList<BombermanGame> L_BbmG = new ArrayList<BombermanGame>();
					for (int i = 0 ; i < nb_threads ; i++){
						BombermanGame un_bbmg = new BombermanGame();
						try {
							un_bbmg.loadFile(content);
	
						} catch (Exception e) {
							e.printStackTrace();
						}
						un_bbmg.init();
														
						un_bbmg.etatJeu.setCampagne(false);
						
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
								
								case "D":
									strategies[j]=9;
								break;
								
								case "A PVP":
									strategies[j]=10;
								break;
							}
					
						}
						
						un_bbmg.setTemps(1);
						un_bbmg.new_thread();
						un_bbmg.etatJeu.setStrats(strategies);
						L_BbmG.add(un_bbmg);
						un_bbmg.getThread().start();
						
						System.out.println("	Thread n°"+i);
					}
						
					for(int j = 0 ; j < L_BbmG.size(); j++){
						try {
							L_BbmG.get(j).getThread().join();
							System.out.println("	Attente Thread n°"+j);
						} catch (InterruptedException e) {
							e.printStackTrace();
							System.out.println("erreur !");
						}
					}
					
					System.out.println("			Fin multithreads");
		
					Cadre_multi c_m = new Cadre_multi(L_BbmG,nb_threads);
					c_m.setVisible(true);
					
					cadre_menu.dispose();
					
				}
					
					
				}
				
			});
	
		
		liste_lay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				listStrat = new ArrayList<JComboBox<String>>();
				remove(choixStrats);
				choixStrats = new JPanel();
				remove(review);
				content = liste_lay.getSelectedItem().toString();
				BombermanGame game = new BombermanGame();
				try {
					game.loadFile(content);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				game.init();
				
				choixStrats.setLayout(new GridLayout(game.etatJeu.getBombermans().size(),2));
				
				String[] nomStrat = {"Auto","Joueur1","Joueur2","A_Items","A","B","C","PVE","PVP","D","A PVP"};
				
				for(int i =0; i<game.etatJeu.getBombermans().size();i++) {
					
					JComboBox<String> liste =  new JComboBox<String>(nomStrat);
					choixStrats.add(new JLabel("Joueur n°"+(int)(game.etatJeu.getBombermans().get(i).getId()+1)));
					listStrat.add(liste);
					choixStrats.add(liste);
				}
				add("South",choixStrats);
				
				review = new Review(game);
				add("Center",review);
				setSize(review.getTaille_x()*40, review.getTaille_y()*40+game.etatJeu.getBombermans().size()*25+50);
				setLocationRelativeTo(null);
				revalidate();
				}
			});
	}
	
}
