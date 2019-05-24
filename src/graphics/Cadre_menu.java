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

import org.apache.commons.lang3.SerializationUtils;

import map.GameState;
import map.Map;
import agents.Agent_Bomberman;
import game.BombermanGame;
import learning.AgentFitted;
import learning.Quadruplet;
import learning.Reward;
import learning.RewardTools;
import learning.Sensor;
import learning.SimpleReward;
import learning.SimpleStateSensor;
import learning.perceptron.LabeledSet;
import learning.perceptron.Perceptron;
import learning.perceptron.PerceptronAgent;
import learning.perceptron.RechercheAleatoire;


public class Cadre_menu extends JFrame{

	//JFrame menu principal : permet de choisir entre les différents modes 
	//et le différentes façon de lancer le jeu avec les stratégie adressée au bombermans
	
	private static final long serialVersionUID = 1L;
	
	private BombermanGame BbmG = null;
	
	//choix du stage
	private JComboBox<File> liste_lay;
	private JLabel choixStage;
	
	//choix de la fonçon dont on lance le jeu : normal ou multithreads
	private JButton jouer = null;
	private JButton multi = null;
	
	//Prevu du niveau choisi
	private Review review = null;
	
	private JPanel panelMap = null;
	private JPanel choixStrats = null;
	private JPanel choix = null;
	
	//Liste adaptée au nombre de bombermens sur la map : crée les listes déroulantes pour les choix des tratégies
	private ArrayList<JComboBox<String>> listStrat = null;
	
	//JMenu pour les modes
	private JMenuBar menu = null;
	private JMenu mode = null;
	private JLabel nom_mode = null;
	private JMenuItem campagne= null;
	private JMenuItem normal = null;
	private JMenuItem perceptron_0 = null;
	private JMenuItem testAlgoAlea = null;
	
	//nom du stage choisi
	private String content = null;
	
	//booléen pour le choix du mode 
	private Boolean is_campagne = null;
	private Boolean is_perceptron = null;
	private Boolean is_testAlgo = null;
	
	public final static int NON_PERCEPTRON = 0;
	public final static int PERCEPTRON_0 = 1;
	public final static int PERCEP_ALGO_ALEA = 2;
	
	private int[] strategies = new int[100];
	private int nb_threads = 10000;
	
	public Cadre_menu() {
		
		this.setTitle("Menu Jeu Bomberman");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		BbmG = new BombermanGame();
		
		menu = new JMenuBar();
		
		mode = new JMenu();
		mode.setText("Mode");
		
		nom_mode = new JLabel();
		nom_mode.setText("   :   Normal");
		
		is_campagne = false;
		is_perceptron = false;
		is_testAlgo = false;
		
		campagne = new JMenuItem();
		campagne.setText("campagne");
		
		normal = new JMenuItem();
		normal.setText("normal");
		
		perceptron_0 = new JMenuItem();
		perceptron_0.setText("perceptron_0");
		
		testAlgoAlea = new JMenuItem();
		testAlgoAlea.setText("testAlgoAlea");
		
		mode.add(campagne);
		mode.add(normal);
		mode.add(testAlgoAlea);
		mode.add(perceptron_0);
		
		menu.add(mode);
		menu.add(nom_mode);
		
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
			choixStrats.add(new JLabel("Joueur n°"+(int)(( BbmG.etatJeu.getBombermans().get(i)).getId()+1)));
			listStrat.add(liste);
			choixStrats.add(liste);
			
		}
		this.add("South",choixStrats);
		this.add("North",choix);
		this.add("Center",review);
		
		//taille de la prévu adaptée a la taille du niveau choisi
		this.setSize(review.getTaille_x()*40, review.getTaille_y()*40+BbmG.etatJeu.getBombermans().size()*25+50);
		this.setLocationRelativeTo(null);

		creer_button(this,BbmG);
	}
	
	
	public void creer_button(final Cadre_menu cadre_menu, final BombermanGame BbmG){
		//action du mode campagne
		campagne.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evenement) {
				
				menu.remove(nom_mode);

				nom_mode = new JLabel();
				nom_mode.setText("   :   Campagne");
				
				menu.add(nom_mode);
				
				is_campagne = true;
				setIs_perceptron(false);
				setIs_testAlgo(false);
				
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
					choixStrats.add(new JLabel("Joueur n°"+(int)(( BbmG.etatJeu.getBombermans().get(i)).getId()+1)));
					listStrat.add(liste);
					choixStrats.add(liste);
				}
				add("South",choixStrats);
				
				review = new Review(BbmG);
				add("Center",review);
				setSize(review.getTaille_x()*40, review.getTaille_y()*40+BbmG.etatJeu.getBombermans().size()*25+50);
				setLocationRelativeTo(null);
				revalidate();
			}
		});
		
		
		normal.addActionListener(new ActionListener() {
			//action du mode normal
			public void actionPerformed(ActionEvent evenement) {
				
				menu.remove(nom_mode);
				
				nom_mode = new JLabel();
				nom_mode.setText("   :   Normal");
				
				menu.add(nom_mode);				
				
				is_campagne = false;
				setIs_perceptron(false);
				setIs_testAlgo(false);
				
				panelMap.add(choixStage);
				panelMap.add(liste_lay);
				
				listStrat = new ArrayList<JComboBox<String>>();
				remove(choixStrats);
				choixStrats = new JPanel();
				remove(review);
				
				content = liste_lay.getSelectedItem().toString();
				
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
					choixStrats.add(new JLabel("Joueur n°"+(int)(( BbmG.etatJeu.getBombermans().get(i)).getId()+1)));
					listStrat.add(liste);
					choixStrats.add(liste);
				}
				add("South",choixStrats);
				
				review = new Review(BbmG);
				add("Center",review);
				setSize(review.getTaille_x()*40, review.getTaille_y()*40+BbmG.etatJeu.getBombermans().size()*25+50);
				setLocationRelativeTo(null);
				revalidate();
			}
		});
		
		perceptron_0.addActionListener(new ActionListener() {
			//action du mode perceptron
			public void actionPerformed(ActionEvent evenement) {
				
				menu.remove(nom_mode);
				
				nom_mode = new JLabel();
				nom_mode.setText("   :   Perceptron_0");
				
				menu.add(nom_mode);

				is_campagne = false;
				setIs_perceptron(true);
				setIs_testAlgo(false);
				
				panelMap.add(choixStage);
				panelMap.add(liste_lay);
				
				listStrat = new ArrayList<JComboBox<String>>();
				remove(choixStrats);
				choixStrats = new JPanel();
				remove(review);
				
				content = liste_lay.getSelectedItem().toString();
				
				try {
					BbmG.loadFile(content);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				BbmG.init();
				
				choixStrats.setLayout(new GridLayout(BbmG.etatJeu.getBombermans().size(),2));
				
				String[] nomStrat = {"Auto","Joueur1","Joueur2","A_Items","A","B","C","PVE","PVP","D","A PVP"};
				
				choixStrats.add(new JLabel("Joueur avec perceptron :"));
				choixStrats.add(new JLabel("Perceptron 0"));
				//choixStrats.add(liste);
				
				for(int i = 1; i<BbmG.etatJeu.getBombermans().size();i++) {
					JComboBox<String> liste =  new JComboBox<String>(nomStrat);
					choixStrats.add(new JLabel("Joueur n°"+(int)(( BbmG.etatJeu.getBombermans().get(i)).getId()+1)));
					listStrat.add(liste);
					choixStrats.add(liste);
				}
				add("South",choixStrats);
				
				review = new Review(BbmG);
				add("Center",review);
				setSize(review.getTaille_x()*40, review.getTaille_y()*40+BbmG.etatJeu.getBombermans().size()*25+50);
				setLocationRelativeTo(null);
				revalidate();
			}
		});
		
		testAlgoAlea.addActionListener(new ActionListener() {
			//action du mode test algo
			public void actionPerformed(ActionEvent evenement) {
				
				menu.remove(nom_mode);
				
				nom_mode = new JLabel();
				nom_mode.setText("   :   TestAlgoAlea");
				
				menu.add(nom_mode);
				
				is_campagne = false;
				setIs_perceptron(false);
				setIs_testAlgo(true);
				
				panelMap.add(choixStage);
				panelMap.add(liste_lay);
				
				listStrat = new ArrayList<JComboBox<String>>();
				remove(choixStrats);
				choixStrats = new JPanel();
				remove(review);
				
				content = liste_lay.getSelectedItem().toString();
				
				try {
					BbmG.loadFile(content);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				BbmG.init();
				
				choixStrats.setLayout(new GridLayout(BbmG.etatJeu.getBombermans().size(),2));
				
				String[] nomStrat = {"Auto","Joueur1","Joueur2","A_Items","A","B","C","PVE","PVP","D","A PVP"};
				
				choixStrats.add(new JLabel("Joueur avec perceptron :"));
				choixStrats.add(new JLabel("testAlgoAlea"));
				//choixStrats.add(liste);
				
				for(int i = 1; i<BbmG.etatJeu.getBombermans().size();i++) {
					JComboBox<String> liste =  new JComboBox<String>(nomStrat);
					choixStrats.add(new JLabel("Joueur n°"+(int)(( BbmG.etatJeu.getBombermans().get(i)).getId()+1)));
					listStrat.add(liste);
					choixStrats.add(liste);
				}
				add("South",choixStrats);
				
				review = new Review(BbmG);
				add("Center",review);
				setSize(review.getTaille_x()*40, review.getTaille_y()*40+BbmG.etatJeu.getBombermans().size()*25+50);
				setLocationRelativeTo(null);
				revalidate();
			}
		});
	
		jouer.addActionListener(new ActionListener() {
			/*action du bouton jouer
			 * Change en fonction du mode choisi
			 * reconnais le mode choisi en fonctio des booléen de ces derniers
			 */
			public void actionPerformed(ActionEvent evenement) {
				////////////////////////////////////////////Perceptron///////////////////////////////////////////
				if(getIs_perceptron()) {
					BombermanGame BG = new BombermanGame();
					
					Map map;
					try {
						map = new Map(content);
						GameState GameS  = new GameState(map,BG);
						strategies[0]=0;
//						 (strat);
						GameS.setCampagne(false);
						
						for(int i = 0; i<listStrat.size();i++) {
							System.out.println(listStrat.get(i).getSelectedItem().toString());
							switch(listStrat.get(i).getSelectedItem().toString()) {
								case "Auto":
									strategies[i+1]=0;
								break;
								
								case "Joueur1":
									strategies[i+1]=1;
								break;
								
								case "Joueur2":
									strategies[i+1]=2;
								break;
							
								case "A_Items":
									strategies[i+1]=3;
								break;
								
								case "A":
									strategies[i+1]=4;
								break;
								
								case "B":
									strategies[i+1]=5;
								break;
								
								case "C":
									strategies[i+1]=6;
								break;
								
								case "PVE":
									strategies[i+1]=7;
								break;
								
								case "PVP":
									strategies[i+1]=8;
								break;
									
								case "D":
									strategies[i+1]=9;
								break;
								
								case "A PVP":
									strategies[i+1]=10;
								break;
							}
						}
						GameS.setStrats(strategies);
						
						GameState state = SerializationUtils.clone(GameS);
						
						int taille =3;
						Sensor s = new Sensor(new SimpleStateSensor(taille));
						Reward r = new SimpleReward();
				
						LabeledSet train = new LabeledSet(s.size());
						
						for (int i = 0; i < 250; i++) {
							AgentFitted agent_f = new AgentFitted(state.getBombermans().get(0));
							
				 			RewardTools.getReward(state, agent_f,r,250);
				 			
				 			System.out.println(state.getBombermans().get(0).getClass().getName());
				 			
				 			System.out.println("			: "+agent_f.getList().size());
				 			
							for (Quadruplet f : agent_f.getList()) {
								
								train.addExample(f.getAtteint(), f.getR_obtenu());
							}
						}
						
						Perceptron p = new Perceptron(0.02,taille);  // epsilone=0.02
						System.out.println("Nombre d'échantillon : " + train.size());
						p.setNb_iteration(1); //150 iterations
						p.train(train);
						PerceptronAgent agent_bomberman = new PerceptronAgent(state.getBombermans().get(0),s, p);
						GameS.setBomberman(0, agent_bomberman);
						
						RewardTools.vizualize(GameS, agent_bomberman, r, 1000,20);
					
						cadre_menu.dispose();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				////////////////////////////////////////////testAlgo///////////////////////////////////////////
				else if(getIs_testAlgo()) {
					BombermanGame BG = new BombermanGame();
					
					Map map;
					try {
						map = new Map(content);
						GameState GameS  = new GameState(map,BG);
						strategies[0]=0;
//						 (strat);
						GameS.setCampagne(false);
						
						for(int i = 0; i<listStrat.size();i++) {
							System.out.println(listStrat.get(i).getSelectedItem().toString());
							switch(listStrat.get(i).getSelectedItem().toString()) {
								case "Auto":
									strategies[i+1]=0;
								break;
								
								case "Joueur1":
									strategies[i+1]=1;
								break;
								
								case "Joueur2":
									strategies[i+1]=2;
								break;
							
								case "A_Items":
									strategies[i+1]=3;
								break;
								
								case "A":
									strategies[i+1]=4;
								break;
								
								case "B":
									strategies[i+1]=5;
								break;
								
								case "C":
									strategies[i+1]=6;
								break;
								
								case "PVE":
									strategies[i+1]=7;
								break;
								
								case "PVP":
									strategies[i+1]=8;
								break;
									
								case "D":
									strategies[i+1]=9;
								break;
								
								case "A PVP":
									strategies[i+1]=10;
								break;
							}
						}
						GameS.setStrats(strategies);
						
						GameState state = SerializationUtils.clone(GameS);

						
						int taille =3;
						Sensor sens = new Sensor(new SimpleStateSensor(taille));
						Reward r = new SimpleReward();
						
						RechercheAleatoire ra = new RechercheAleatoire(50, 40, Math.random()*0.2-0.1, state,r, sens);
						for(int i=0;i<10; i++){ 
							ra.evoluer();
						}
						Perceptron perceptron = ra.getMeilleur();
						PerceptronAgent agent_bomberman = new PerceptronAgent(state.getBombermans().get(0),sens, perceptron);
						
						GameS.setBomberman(0, agent_bomberman);
						
						RewardTools.vizualize(GameS, agent_bomberman, r, 1000, 100);
						
						cadre_menu.dispose();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				////////////////////////////////////////////normal et campagne///////////////////////////////////////////
				else{
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
			}
		});
		
		multi.addActionListener(new ActionListener() {
			/* Basiquement la meme chose que Jouer mais lance en multithreads
			 * Multithread sur les perceptrons : basiquement la même chose qu'un perceptron normal,
			 * tout en en prenant en compte que la serialization n'est certainement pas la meilleurs chose pour le gain de performance.
			 * 
			 * Quand le mod choisi est le mode campagne : le multithreds differt on lance le nb thread une fois
			 * on recupere ensuite le nb de gagna tque l'on relance un deuxieme fois ainsi de suite suivant lae nombre de niveau de la campagne.
			 */
			public void actionPerformed(ActionEvent evenement) {
				////////////////////////////////////////////perceptron///////////////////////////////////////////
				if(getIs_perceptron()) {
					BombermanGame BG = new BombermanGame();
					
					Map map;
					try {
						map = new Map(content);
						GameState GameS  = new GameState(map,BG);
						strategies[0]=0;
//						 (strat);
						GameS.setCampagne(false);
						
						for(int i = 0; i<listStrat.size();i++) {
							System.out.println(listStrat.get(i).getSelectedItem().toString());
							switch(listStrat.get(i).getSelectedItem().toString()) {
								case "Auto":
									strategies[i+1]=0;
								break;
								
								case "Joueur1":
									strategies[i+1]=1;
								break;
								
								case "Joueur2":
									strategies[i+1]=2;
								break;
							
								case "A_Items":
									strategies[i+1]=3;
								break;
								
								case "A":
									strategies[i+1]=4;
								break;
								
								case "B":
									strategies[i+1]=5;
								break;
								
								case "C":
									strategies[i+1]=6;
								break;
								
								case "PVE":
									strategies[i+1]=7;
								break;
								
								case "PVP":
									strategies[i+1]=8;
								break;
									
								case "D":
									strategies[i+1]=9;
								break;
								
								case "A PVP":
									strategies[i+1]=10;
								break;
							}
						}
						GameS.setStrats(strategies);
						
						GameState state = SerializationUtils.clone(GameS);

						
						int taille =3;
						Sensor s = new Sensor(new SimpleStateSensor(taille));
						Reward r = new SimpleReward();
				
						LabeledSet train = new LabeledSet(s.size());
						
						for (int i = 0; i < 250; i++) {
							AgentFitted agent_f = new AgentFitted(state.getBombermans().get(0));
							
				 			RewardTools.getReward(state, agent_f,r,250);
				 			
							for (Quadruplet f : agent_f.getList()) {
								
								train.addExample(f.getAtteint(), f.getR_obtenu());
							}
						}
						
						Perceptron p = new Perceptron(0.02,taille);  // epsilone=0.02
						System.out.println("Nombre d'échantillon : " + train.size());
						p.setNb_iteration(1); //150 iterations
						p.train(train);
						PerceptronAgent agent_bomberman = new PerceptronAgent(state.getBombermans().get(0),s, p);
						GameS.setBomberman(0, agent_bomberman);
						
						ArrayList<BombermanGame> L_BbmG = new ArrayList<BombermanGame>();
						for (int i = 0 ; i < 500 ; i++){
							PerceptronAgent agent_b = SerializationUtils.clone(agent_bomberman);
							BombermanGame un_bbmg = new BombermanGame();
							
							try {
								un_bbmg.loadFile(content);

							} catch (Exception e) {
								e.printStackTrace();
							}
							
							
							un_bbmg.init();
							un_bbmg.etatJeu.setCampagne(false);
							un_bbmg.etatJeu.setStrats(strategies);
							un_bbmg.etatJeu.setBomberman(0, agent_b);
							
							un_bbmg.setTemps(1);
							un_bbmg.new_thread();
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

						Cadre_multi c_m = new Cadre_multi(L_BbmG,500,PERCEPTRON_0);
						c_m.setVisible(true);
					
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				////////////////////////////////////////////testAlgoALea///////////////////////////////////////////
				else if(getIs_testAlgo()) {
					BombermanGame BG = new BombermanGame();
					
					Map map;
					try {
						map = new Map(content);
						GameState GameS  = new GameState(map,BG);
						strategies[0]=0;
//						 (strat);
						GameS.setCampagne(false);
						
						for(int i = 0; i<listStrat.size();i++) {
							System.out.println(listStrat.get(i).getSelectedItem().toString());
							switch(listStrat.get(i).getSelectedItem().toString()) {
								case "Auto":
									strategies[i+1]=0;
								break;
								
								case "Joueur1":
									strategies[i+1]=1;
								break;
								
								case "Joueur2":
									strategies[i+1]=2;
								break;
							
								case "A_Items":
									strategies[i+1]=3;
								break;
								
								case "A":
									strategies[i+1]=4;
								break;
								
								case "B":
									strategies[i+1]=5;
								break;
								
								case "C":
									strategies[i+1]=6;
								break;
								
								case "PVE":
									strategies[i+1]=7;
								break;
								
								case "PVP":
									strategies[i+1]=8;
								break;
									
								case "D":
									strategies[i+1]=9;
								break;
								
								case "A PVP":
									strategies[i+1]=10;
								break;
							}
						}
						GameS.setStrats(strategies);
						
						GameState state = SerializationUtils.clone(GameS);

						
						int taille =3;
						Sensor sens = new Sensor(new SimpleStateSensor(taille));
						Reward r = new SimpleReward();
						
						RechercheAleatoire ra = new RechercheAleatoire(50, 40, Math.random()*0.2-0.1, state,r, sens);
						for(int i=0;i<10; i++){
							ra.evoluer();
						}
						Perceptron perceptron = ra.getMeilleur();
						PerceptronAgent agent_bomberman = new PerceptronAgent(state.getBombermans().get(0),sens, perceptron);
						
						GameS.setBomberman(0, agent_bomberman);
						
						ArrayList<BombermanGame> L_BbmG = new ArrayList<BombermanGame>();
						for (int i = 0 ; i < 250 ; i++){
							PerceptronAgent agent_b = SerializationUtils.clone(agent_bomberman);
							BombermanGame un_bbmg = new BombermanGame();
							
							try {
								un_bbmg.loadFile(content);

							} catch (Exception e) {
								e.printStackTrace();
							}
							
							
							un_bbmg.init();
							un_bbmg.etatJeu.setCampagne(false);
							un_bbmg.etatJeu.setStrats(strategies);
							un_bbmg.etatJeu.setBomberman(0, agent_b);
							
							un_bbmg.setTemps(1);
							un_bbmg.new_thread();
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

						Cadre_multi c_m = new Cadre_multi(L_BbmG,250,PERCEP_ALGO_ALEA);
						c_m.setVisible(true);
		
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				////////////////////////////////////////////mode campagne///////////////////////////////////////////
				else if(is_campagne) {
					ArrayList<Integer> index_winner = new ArrayList<Integer>();
					ArrayList<Integer> index_winner2 = new ArrayList<Integer>();
					ArrayList<BombermanGame> L_BbmG = new ArrayList<BombermanGame>();
					ArrayList<BombermanGame> L_BbmG2 = new ArrayList<BombermanGame>();
					ArrayList<BombermanGame> L_BbmG3 = new ArrayList<BombermanGame>();
					ArrayList<BombermanGame> L_BbmAll = new ArrayList<BombermanGame>();
					
					//on lance tout les threads demandé dans le mode campagne sur le premier niveau 
					
					for (int i = 0 ; i < nb_threads ; i++){
						BombermanGame un_bbmg = new BombermanGame();
						try {
							un_bbmg.loadFile(content);
	
						} catch (Exception e) {
							e.printStackTrace();
						}
						un_bbmg.init();
						
						
						un_bbmg.etatJeu.setCampagne(false);
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
					
					//verification des bomberman gagant le premier niveau et on joint les threads du premier niveau 
						
					for(int j = 0 ; j < L_BbmG.size(); j++){
						try {
							L_BbmG.get(j).getThread().join();
							
							System.out.println("	Attente Thread n°"+j);

						} catch (InterruptedException e) {
							e.printStackTrace();
							System.out.println("erreur !");
						}
						
						
						if(L_BbmG.get(j).etatJeu.getFinPartie() == GameState.WIN_SOLO) 
							index_winner.add(j);
						else{
							L_BbmAll.add(L_BbmG.get(j));
						}
					}
					
					//on fait jouer les bomberman gagnant en reprenant leur statistique sur le niveau 2
					
					for (int i = 0 ; i < index_winner.size() ; i++){
						BombermanGame un_bbmg = new BombermanGame();
						Agent_Bomberman old_bbmg =  L_BbmG.get(index_winner.get(i)).etatJeu.getBombermans().get(0);
						
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
						un_bbmg.etatJeu.setNum_niveau(2);
						( un_bbmg.etatJeu.getBombermans().get(0)).setPoints(points);
						( un_bbmg.etatJeu.getBombermans().get(0)).setNbBombes(bombes);
						( un_bbmg.etatJeu.getBombermans().get(0)).setRange(range);
						
						un_bbmg.setTemps(1);
						un_bbmg.new_thread();
						un_bbmg.etatJeu.setStrats(strategies);
						L_BbmG2.add(un_bbmg);
						un_bbmg.getThread().start();
					}
					
					
					//on verifie les gagnant du deuxième stage et on joint les threads du second niveau 
					
					for(int j = 0 ; j < L_BbmG2.size(); j++){
						try {
							L_BbmG2.get(j).getThread().join();

						} catch (InterruptedException e) {
							e.printStackTrace();
							System.out.println("erreur !");
						}
						if(L_BbmG2.get(j).etatJeu.getFinPartie() == GameState.WIN_SOLO)
							index_winner2.add(j);
						else{
							L_BbmAll.add(L_BbmG2.get(j));
						}
					}
					
					//on fait jouer les bomberman qui on gagné le stage précédent sur le 3eme niveau
					
					for (int i = 0 ; i < index_winner2.size() ; i++){
						BombermanGame un_bbmg = new BombermanGame();
						Agent_Bomberman old_bbmg =  L_BbmG2.get(index_winner2.get(i)).etatJeu.getBombermans().get(0);
						
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
						un_bbmg.etatJeu.setNum_niveau(3);
						( un_bbmg.etatJeu.getBombermans().get(0)).setPoints(points);
						( un_bbmg.etatJeu.getBombermans().get(0)).setNbBombes(bombes);
						( un_bbmg.etatJeu.getBombermans().get(0)).setRange(range);
						
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
						L_BbmAll.add(L_BbmG3.get(j));
					}
					
					System.out.println("			Fin multithreads");
		
					Cadre_multi c_m = new Cadre_multi(L_BbmAll,L_BbmAll.size(),NON_PERCEPTRON);
          
					c_m.setVisible(true);
					
					cadre_menu.dispose();
				}
				////////////////////////////////////////////mode normal///////////////////////////////////////////
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
		
					Cadre_multi c_m = new Cadre_multi(L_BbmG,nb_threads,NON_PERCEPTRON);
					c_m.setVisible(true);
					
					cadre_menu.dispose();
					
				}
					
					
				}
				
			});

		//action lors du choix de niveau : avec le changement de la preview
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
				
				
				
				if (getIs_perceptron()){
					choixStrats.add(new JLabel("Joueur avec perceptron "));
					choixStrats.add(new JLabel("Perceptron 0"));

					for(int i = 1; i<game.etatJeu.getBombermans().size();i++) {
						JComboBox<String> liste =  new JComboBox<String>(nomStrat);
						choixStrats.add(new JLabel("Joueur n°"+(int)(( game.etatJeu.getBombermans().get(i)).getId()+1)));
						listStrat.add(liste);
						choixStrats.add(liste);
					}
					add("South",choixStrats);
				}else if (getIs_testAlgo()){
					choixStrats.add(new JLabel("Joueur avec perceptron "));
					choixStrats.add(new JLabel("TestAlgoAlea"));
					
					for(int i = 1; i<game.etatJeu.getBombermans().size();i++) {
						JComboBox<String> liste =  new JComboBox<String>(nomStrat);
						choixStrats.add(new JLabel("Joueur n°"+(int)(( game.etatJeu.getBombermans().get(i)).getId()+1)));
						listStrat.add(liste);
						choixStrats.add(liste);
					}
					add("South",choixStrats);

				}else{
					for(int i =0; i<game.etatJeu.getBombermans().size();i++) {
						
						JComboBox<String> liste =  new JComboBox<String>(nomStrat);
						choixStrats.add(new JLabel("Joueur n°"+(int)(( game.etatJeu.getBombermans().get(i)).getId()+1)));
						listStrat.add(liste);
						choixStrats.add(liste);
					}
					add("South",choixStrats);
				}
				
				
				review = new Review(game);
				add("Center",review);
				setSize(review.getTaille_x()*40, review.getTaille_y()*40+game.etatJeu.getBombermans().size()*25+50);
				setLocationRelativeTo(null);
				revalidate();
				}
			});
	}


	public Boolean getIs_perceptron() {
		return is_perceptron;
	}


	public void setIs_perceptron(Boolean is_perceptron) {
		this.is_perceptron = is_perceptron;
	}


	public Boolean getIs_testAlgo() {
		return is_testAlgo;
	}


	public void setIs_testAlgo(Boolean is_testAlgo) {
		this.is_testAlgo = is_testAlgo;
	}
	
}