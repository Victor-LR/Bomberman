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
import agents.Agent;
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
	private JMenuItem perceptron_0 = null;
	private JMenuItem testAlgoAlea = null;
	private String content = null;
	private Boolean is_campagne = null;
	private Boolean is_perceptron = null;
	private Boolean is_testAlgo = null;
	
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
		
		System.out.println(review.getTaille_x());
		
		this.setSize(review.getTaille_x()*40, review.getTaille_y()*40+BbmG.etatJeu.getBombermans().size()*25+50);
		this.setLocationRelativeTo(null);

		creer_button(this,BbmG);
		
		System.out.println(content);
	}
	
	
	public void creer_button(final Cadre_menu cadre_menu, final BombermanGame BbmG){

		campagne.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evenement) {
				
				is_campagne = true;
				is_perceptron = false;
				is_testAlgo = false;
				
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
			
			public void actionPerformed(ActionEvent evenement) {
				
				is_campagne = false;
				is_perceptron = false;
				is_testAlgo = false;
				
				panelMap.add(choixStage);
				panelMap.add(liste_lay);
				revalidate();
			}
		});
		
		perceptron_0.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evenement) {
				
				is_campagne = false;
				is_perceptron = true;
				is_testAlgo = false;
				
				panelMap.remove(choixStage);
				panelMap.remove(liste_lay);
				
				listStrat = new ArrayList<JComboBox<String>>();
				remove(choixStrats);
				choixStrats = new JPanel();
				remove(review);
				
				content = "./layout/perceptron.lay";
				
				try {
					BbmG.loadFile(content);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				BbmG.init();
				
				review = new Review(BbmG);
				add("Center",review);
				setSize(review.getTaille_x()*40, review.getTaille_y()*40+BbmG.etatJeu.getBombermans().size()*25+50);
				setLocationRelativeTo(null);
				
				revalidate();
			}
		});
		
		testAlgoAlea.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evenement) {
				
				is_campagne = false;
				is_perceptron = false;
				is_testAlgo = true;
				
				panelMap.remove(choixStage);
				panelMap.remove(liste_lay);
				
				listStrat = new ArrayList<JComboBox<String>>();
				remove(choixStrats);
				choixStrats = new JPanel();
				remove(review);
				
				content = "./layout/perceptron.lay";
				
				try {
					BbmG.loadFile(content);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				BbmG.init();
				
				review = new Review(BbmG);
				add("Center",review);
				setSize(review.getTaille_x()*40, review.getTaille_y()*40+BbmG.etatJeu.getBombermans().size()*25+50);
				setLocationRelativeTo(null);
				
				revalidate();
			}
		});
	
		jouer.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evenement) {
				if(is_perceptron) {
					BombermanGame BG = new BombermanGame();
					
					Map map;
					try {
						map = new Map("./layout/perceptron.lay");
						GameState GameS  = new GameState(map,BG);
						int strat[] = {0};
						GameS.setStrats(strat);
						GameS.setCampagne(false);
						
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
					
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				else if(is_testAlgo) {
					BombermanGame BG = new BombermanGame();
					
					Map map;
					try {
						map = new Map("./layout/perceptron.lay");
						GameState GameS  = new GameState(map,BG);
						int strat[] = {0};
						GameS.setStrats(strat);
						GameS.setCampagne(false);
						
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
		
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
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
			
			public void actionPerformed(ActionEvent evenement) {
				if(is_perceptron) {
					BombermanGame BG = new BombermanGame();
					
					Map map;
					try {
						map = new Map("./layout/perceptron.lay");
						GameState GameS  = new GameState(map,BG);
						int strat[] = {0};
						GameS.setStrats(strat);
						GameS.setCampagne(false);
						
						GameState state = SerializationUtils.clone(GameS);

						
						int taille =3;
						Sensor s = new Sensor(new SimpleStateSensor(taille));
						Reward r = new SimpleReward();
				
						LabeledSet train = new LabeledSet(s.size());
						
						for (int i = 0; i < 250; i++) {
							AgentFitted agent_f = new AgentFitted(state.getBombermans().get(0));
							
				 			RewardTools.getReward(state, agent_f,r,250);
//				 			
//				 			System.out.println(state.getBombermans().get(0).getClass().getName());
//				 			
//				 			System.out.println("			: "+agent_f.getList().size());
				 			
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
								un_bbmg.loadFile("./layout/perceptron.lay");

							} catch (Exception e) {
								e.printStackTrace();
							}
							
							
							un_bbmg.init();
							un_bbmg.etatJeu.setCampagne(false);
							un_bbmg.etatJeu.setStrats(strat);
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

						Cadre_multi c_m = new Cadre_multi(L_BbmG,500);
						c_m.setVisible(true);
					
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				else if(is_testAlgo) {
					BombermanGame BG = new BombermanGame();
					
					Map map;
					try {
						map = new Map("./layout/perceptron.lay");
						GameState GameS  = new GameState(map,BG);
						int strat[] = {0};
						GameS.setStrats(strat);
						GameS.setCampagne(false);
						
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
						for (int i = 0 ; i < 500 ; i++){
							PerceptronAgent agent_b = SerializationUtils.clone(agent_bomberman);
							BombermanGame un_bbmg = new BombermanGame();
							
							try {
								un_bbmg.loadFile("./layout/perceptron.lay");

							} catch (Exception e) {
								e.printStackTrace();
							}
							
							
							un_bbmg.init();
							un_bbmg.etatJeu.setCampagne(false);
							un_bbmg.etatJeu.setStrats(strat);
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

						Cadre_multi c_m = new Cadre_multi(L_BbmG,500);
						c_m.setVisible(true);
		
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
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
				
//						System.out.println("niv 1  "+L_BbmG.get(j).etatJeu.getBombermans().get(0).getPoints());
						
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
//							System.out.println("	Attente Thread n°"+j);

						} catch (InterruptedException e) {
							e.printStackTrace();
							System.out.println("erreur !");
						}
						if(L_BbmG2.get(j).etatJeu.getFinPartie() == GameState.WIN_SOLO)
							index_winner2.add(j);
						else{
							L_BbmAll.add(L_BbmG2.get(j));
						}
//						System.out.println("niv 2 "+L_BbmG2.get(j).etatJeu.getBombermans().get(0).getPoints());
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
//						System.out.println("niv 3 "+L_BbmG3.get(j).etatJeu.getBombermans().get(0).getPoints());
					}
					
					System.out.println("			Fin multithreads");
		
					Cadre_multi c_m = new Cadre_multi(L_BbmAll,L_BbmAll.size());
          
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
					choixStrats.add(new JLabel("Joueur n°"+(int)(( game.etatJeu.getBombermans().get(i)).getId()+1)));
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
