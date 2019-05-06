package graphics;

import game.BombermanGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import map.GameState;

public class Cadre_multi extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel resultat = null;
	private ArrayList<JLabel> listlab;
	private JButton back;
	
	private int nb_threads;
	
	private ArrayList<BombermanGame> L_BbmG;
	
	java.text.DecimalFormat df = new java.text.DecimalFormat("0.##");
	
	Cadre_multi(ArrayList<BombermanGame> L_BbmG, int nb_threads){
		
		this.setL_BbmG(L_BbmG);
		this.setNb_threads(nb_threads);
		int nb_bbm = L_BbmG.get(0).getMap().getNumber_of_bombermans();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		if(nb_bbm < 10)
			this.setSize(750,nb_bbm*50+250);
		else this.setSize(1000,nb_bbm*50+250);
		
		this.setLocationRelativeTo(null);
		Border border = LineBorder.createGrayLineBorder();
		
		//this.setLayout(new GridLayout(nb_bbm +5,2));
		this.setLayout(new BorderLayout());


		
		JPanel panel_haut = new JPanel();
		panel_haut.setLayout(new GridLayout(4,1));
		
		
		JPanel panel_bas = new JPanel();
		panel_haut.setLayout(new GridLayout(3,1));
		
		
		back = new JButton("back to menu");
		panel_haut.add(this.back);
		
		JPanel panel_stat = new JPanel();
		panel_stat.setLayout(new GridLayout(1,2));
		
		JLabel label_thread = new JLabel();
		label_thread.setText("Nombre de threads lancés : "+nb_threads);
		label_thread.setHorizontalAlignment(JLabel.CENTER);
		panel_stat.add(label_thread);
		
		JLabel label_tour = new JLabel();
		label_tour.setText("Nombre de tours maximum par Jeu : "+L_BbmG.get(0).getMaxTurn());
		label_tour.setHorizontalAlignment(JLabel.CENTER);
		panel_stat.add(label_tour);
		
		panel_haut.add(panel_stat);
		
		int[][] id_du_gagnant = new int[100][4];
		int[] stat_finPartie = new int[100];
		int[] point_joueur = new int[100];
		int typeFin;
		for(int j = 0 ; j < L_BbmG.size(); j++){
			
			typeFin = L_BbmG.get(j).etatJeu.getFinPartie();
			//System.out.println(j+"				"+typeFin);
			int point_gagnant = L_BbmG.get(j).etatJeu.getBombermans().get(L_BbmG.get(j).etatJeu.getIdGagnant()).getPoints();
			
			for(int i = 0; i < nb_bbm; i++){
				point_joueur[i] +=  L_BbmG.get(j).etatJeu.getBombermans().get(i).getPoints();
			}
			
			switch(typeFin){
				case GameState.PLANTAGE :
					stat_finPartie[GameState.PLANTAGE] +=1;
					break;
					
				case GameState.WIN_SOLO :
					stat_finPartie[GameState.WIN_SOLO] +=1;
					id_du_gagnant[L_BbmG.get(j).etatJeu.getIdGagnant()][0] +=1;
					id_du_gagnant[L_BbmG.get(j).etatJeu.getIdGagnant()][3] += point_gagnant;
					break;
					
				case GameState.WIN_SCORE :
					stat_finPartie[GameState.WIN_SCORE] +=1;
					id_du_gagnant[L_BbmG.get(j).etatJeu.getIdGagnant()][1] +=1;
					id_du_gagnant[L_BbmG.get(j).etatJeu.getIdGagnant()][3] += point_gagnant;
					break;
					
				case GameState.WIN_SURVIE :
					stat_finPartie[GameState.WIN_SURVIE] +=1;
					id_du_gagnant[L_BbmG.get(j).etatJeu.getIdGagnant()][2] +=1;
					id_du_gagnant[L_BbmG.get(j).etatJeu.getIdGagnant()][3] += point_gagnant;
					break;
					
				case GameState.EX_AEQUO :
					stat_finPartie[GameState.EX_AEQUO] +=1;
					break;
					
				case GameState.GAME_OVER :
					stat_finPartie[GameState.GAME_OVER] +=1;
					break;
			}
			
		}

		
		
		System.out.println("");
		for(int n = 0 ; n < stat_finPartie.length; n++){

			String pourcentage ="<font color = #39B835 >"+df.format(((double)stat_finPartie[n]/nb_threads)*100)+"%"+"</font>";
			JLabel pan_result = new JLabel();
			
			if ( n == GameState.PLANTAGE ) {
				pan_result.setText("Il y a eu "+stat_finPartie[n]+" parties qui ont plantés");
				pan_result.setHorizontalAlignment(JLabel.CENTER);
				panel_haut.add(pan_result);
			}
			
			else if (n == GameState.WIN_SOLO  || n == GameState.WIN_SOLO  || n == GameState.WIN_SOLO ){
				
				JPanel panel_tout_joueur = new JPanel();
				if(nb_bbm < 10)
					panel_tout_joueur.setLayout(new GridLayout(nb_bbm,2));
				else panel_tout_joueur.setLayout(new GridLayout((int)Math.round((double)nb_bbm/2),2));
				
				double total;
				for(int i = 0 ; i < L_BbmG.get(0).etatJeu.getBombermans().size() ; i++){
					JPanel panel_joueur = new JPanel();
					panel_joueur.setLayout(new GridLayout(5,1,2,2));
					panel_joueur.setBorder(border);
					JLabel pan_result2 = new JLabel();
					total = id_du_gagnant[i][0] + id_du_gagnant[i][1] + id_du_gagnant[i][2];
					
					String joueur = "<font color = #009FFF >Joueur "+(i+1)+"</font>";
					String pourcent_joueur = "<font color = #39B835 >"+ df.format((total/nb_threads)*100)+"%</font>";
					
					if (L_BbmG.get(0).etatJeu.getBombermans().get(i).getStrategie() == null) 
						pan_result2.setText("<html>"+ joueur +" a gagné "+pourcent_joueur+" du temps avec la strategie <font color = #C90F0F >aléatoire</font>, dont :</html>");
					else pan_result2.setText("<html>"+ joueur +" a gagné "+ pourcent_joueur +"% du temps avec strategie <font color = #C90F0F >"+L_BbmG.get(0).etatJeu.getBombermans().get(i).getStrategie().getClass().getSimpleName()+"</font>, dont :</html>");
					pan_result2.setHorizontalAlignment(JLabel.CENTER);
					panel_joueur.add(pan_result2);
					
					if ( id_du_gagnant[i][0] != 0) {
						JLabel win_solo = new JLabel();
						win_solo.setText( "<html> <font color = #E7C019 >"+df.format((id_du_gagnant[i][0]/total)*100) +"%</font> en solo");
						win_solo.setHorizontalAlignment(JLabel.CENTER);
						panel_joueur.add(win_solo);
					}
					if ( id_du_gagnant[i][1] != 0) {
						JLabel win_score = new JLabel();
						win_score.setText("<html> <font color = #E7C019 >"+df.format((id_du_gagnant[i][1]/total)*100) +"%</font> au score");
						win_score.setHorizontalAlignment(JLabel.CENTER);
						panel_joueur.add(win_score);
					}
					if ( id_du_gagnant[i][2] != 0) {
						JLabel win_survie = new JLabel();
						win_survie.setText("<html> <font color = #E7C019 >"+df.format((id_du_gagnant[i][2]/total)*100) +"%</font> en restant le dernier en vie");
						win_survie.setHorizontalAlignment(JLabel.CENTER);
						panel_joueur.add(win_survie);
					}
					
					JLabel moy_victoire = new JLabel();
					moy_victoire.setText("<html>"+joueur+" a en moyenne "+df.format(id_du_gagnant[i][3]/total)+" points lorsqu'il gagne </html>");
					moy_victoire.setHorizontalAlignment(JLabel.CENTER);
					panel_joueur.add(moy_victoire);
					
					JLabel moy_totale = new JLabel();
					moy_totale.setText("<html>"+joueur+" a eu en moyenne "+point_joueur[i]/nb_threads+" points sur toutes les parties</html>");
					moy_totale.setHorizontalAlignment(JLabel.CENTER);
					panel_joueur.add(moy_totale);
					
					panel_tout_joueur.add(panel_joueur);
				}
				this.add("Center",panel_tout_joueur);
			
			}
				
			else if ( n ==  GameState.EX_AEQUO ) {
				pan_result.setText("<html>La partie s'est finie sur un ex aequo "+ pourcentage +" du temps</html>");
				pan_result.setHorizontalAlignment(JLabel.CENTER);
				panel_bas.add(pan_result);
			}
				
			else if ( n ==  GameState.GAME_OVER) {
				pan_result.setText("<html>Il n'y a pas eu de gagnant "+ pourcentage +" du temps</html>");
				pan_result.setHorizontalAlignment(JLabel.CENTER);
				panel_bas.add(pan_result);
			}
			this.add("North",panel_haut);
			this.add("South",panel_bas);
			
		}
			
		System.out.println("");
		
		creer_button();
	}
	
	public void creer_button(){
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				
				Cadre_menu fen = new Cadre_menu();//BbmG.getMap().getFilename());
				fen.setVisible(true);
				dispose();
				
			}
		});
	}

	public JLabel getResultat() {
		return resultat;
	}

	public void setResultat(JLabel resultat) {
		this.resultat = resultat;
	}

	public int getNb_threads() {
		return nb_threads;
	}

	public void setNb_threads(int nb_threads) {
		this.nb_threads = nb_threads;
	}

	public ArrayList<BombermanGame> getL_BbmG() {
		return L_BbmG;
	}

	public void setL_BbmG(ArrayList<BombermanGame> l_BbmG) {
		L_BbmG = l_BbmG;
	}
}
