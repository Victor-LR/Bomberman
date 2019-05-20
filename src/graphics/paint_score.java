package graphics;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import game.BombermanGame;
import game.GameObserver;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import agents.Agent_Bomberman;


public class paint_score implements GameObserver {
	/*Paint score renvoie un JPanel construit à partir des informations du jeu récupérées grâce au GameObserver
	 * deispose aussi d'un mini menu intéragissant avec le jeu : play, pause, restart, back to menu ...
	 */
	BombermanGame BbmG;
	
	//cadre jeu dans lequel il est impléméenté
	private Cadre_Jeu c_j = null;
	private JPanel panel = null;

	//Japanels construisant paint score
	private JPanel panBoutton= null;
	private JPanel panMenu= null;
	private JPanel panScore = null;
	
	//liste de label recupérant les différents score des bombermans présents sur le jeu
	public ArrayList<JLabel> listlab;
	
	//bouton du mini menu
	private JButton stop =null;
	private JButton run =null;
	private JButton restart =null;
	private JButton back =null;
	private JSlider slider = null;
	
	//information sur le jeu 
	private JLabel turn = null;
	private JLabel nomNiveau = null;
	
	//permet de reconstruire un BombermanGame avec restart
	private int[] old_strat = new int[10];
	private boolean old_campagne = false;
	
	public paint_score(Cadre_Jeu cadre_jeu,BombermanGame bomberman){
		
		this.c_j = cadre_jeu;
		
		this.BbmG = bomberman;
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		panBoutton = new JPanel();
		panBoutton.setLayout(new FlowLayout());
		
		panMenu = new JPanel();
		panMenu.setLayout(new GridLayout(2,1));
		
		panScore = new JPanel();
		int taille_gridlayout = bomberman.etatJeu.getBombermans().size() / 5 +1;
		panScore.setLayout(new GridLayout(taille_gridlayout,1));
		
		this.BbmG.addObserver((GameObserver)this);
		
		this.old_strat = bomberman.etatJeu.getStrats();
		this.old_campagne = bomberman.etatJeu.getCampagne();
		
		listlab = new ArrayList<JLabel>();
		
		Icon icon_pause = new ImageIcon("./image/icon_pause.png");
		stop = new JButton(icon_pause);
		this.panBoutton.add(this.stop);
		
		Icon icon_run = new ImageIcon("./image/icon_run.png");
		run = new JButton(icon_run);
		this.panBoutton.add(this.run);
		
		Icon icon_restart = new ImageIcon("./image/icon_restart.png");
		restart = new JButton(icon_restart);
		this.panBoutton.add(this.restart);
		
		back = new JButton("back to menu");
		this.panBoutton.add(this.back);
		
		this.panMenu.add(panBoutton);
		
		slider = new JSlider(1, 10, 2);
		slider.setMajorTickSpacing(1);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		
		panMenu.add(slider);
		panel.add(panMenu);
		
		//attribution des couleurs pour les score en fonction des couleurs des bombermans
		ArrayList<Agent_Bomberman> bombermans = this.BbmG.etatJeu.getBombermans();
		for(int i = 0; i < bombermans.size(); i++){
			this.listlab.add(new JLabel());
			switch(bombermans.get(i).getCouleur())
	        {
	            case ROUGE :
	            	listlab.get(i).setForeground(Color.red);
	                break;
	            case VERT :
	            	listlab.get(i).setForeground(Color.green);
	                break;
	            case BLEU :
	            	listlab.get(i).setForeground(Color.blue);
	                break;
	            case JAUNE :
	            	listlab.get(i).setForeground(Color.yellow);
	                break;
	            case BLANC :
	            	listlab.get(i).setForeground(Color.white);
	                break;
	            case DEFAULT :
	            	listlab.get(i).setForeground(Color.black);
	                break;
	        }
			panScore.add(listlab.get(i));
		}
		
		panel.add(panScore);
		
		turn = new JLabel();
		this.panel.add(this.turn);
		
		nomNiveau = new JLabel("    Stage : "+BbmG.getFilename());
		this.panel.add(this.nomNiveau);
		
		activerStop();
		desactiverRun();
		desactiverRestart();
		
		creer_button();
	}
	
	public void creer_button(){
		//action de back to menu
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				

				Cadre_menu fen = new Cadre_menu();//BbmG.getMap().getFilename());

				fen.setVisible(true);
				Thread.currentThread().interrupt();
				BbmG.stop();
				c_j.dispose();
			}
		});
		
		//action du bouton restart
		restart.setFocusPainted(false);
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				
				desactiverRun();
				activerStop();
				desactiverRestart();
				try {
					BbmG.loadFile(BbmG.getMap().getFilename());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				BbmG.init();
				BbmG.etatJeu.setCampagne(false);
        
				BbmG.etatJeu.setStrats(old_strat);
				BbmG.etatJeu.setCampagne(old_campagne);
				
				BbmG.launch();
				paint_bomberman PBM = new paint_bomberman(c_j,BbmG);
		        c_j.add("Center",PBM);
			}
		});
		
		//action du bouton play
		run.setFocusPainted(false);
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				activerStop();
				desactiverRun();
				desactiverRestart();
				
				BbmG.launch();
			}
		});
		
		//action du bouton pause
		stop.setFocusPainted(false);
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				
				desactiverStop();
				activerRun();
				activerRestart();
				
				BbmG.stop();
			}
		});
		
		//action du slider (change la vitesse du jeu)
		slider.addChangeListener(new ChangeListener() {
			double temps_start = BbmG.getTemps();
		    public void stateChanged(ChangeEvent event) {
		        int value = slider.getValue();
		        BbmG.setTemps(1.0/value*temps_start);
		      }
		 });
	}
	
	//actualise le score pour les différents bombermans
	public void affichage_score(Agent_Bomberman ag,int i){
		listlab.get(i).setText("N°"+(i+1)+" score : "+ag.getPoints()+"    ");
	}

	//appel de la fonction update qui permet à chaque tpour de changer les information nécéssaire dans paint score (score, nb tour, action du menuu...)
	@Override
	public void update() {
		
		for(int i = 0; i < this.BbmG.etatJeu.getBombermans().size(); i++){
			Agent_Bomberman bomberman = (Agent_Bomberman) this.BbmG.etatJeu.getBombermans().get(i);
			affichage_score(bomberman,bomberman.getId());
			
			//System.out.println("		test score "+ bomberman.getPoints());
		}
		this.turn.setText("Tour : "+this.BbmG.getTurn()+"	");
		
	}
	
	public void activerStop() {
		stop.setEnabled(true);
	}

	public void desactiverStop() {
		stop.setEnabled(false);
	}

	public void activerRun() {
		run.setEnabled(true);
	}

	public void desactiverRun() {
		run.setEnabled(false);
	}

	public void activerRestart() {
		restart.setEnabled(true);
	}

	public void desactiverRestart() {
		restart.setEnabled(false);

	}	
	
	public JPanel getPanBoutton() {
		return panBoutton;
	}
	public void setPanBoutton(JPanel panBoutton) {
		this.panBoutton = panBoutton;
		
	}public JButton getRestart() {
		return restart;
	}
	
	public void setRestart(JButton restart) {
		this.restart = restart;
	}
	
	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
}
