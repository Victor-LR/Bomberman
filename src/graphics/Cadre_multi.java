package graphics;

import game.BombermanGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
		this.listlab = new ArrayList<JLabel>();
		this.setL_BbmG(L_BbmG);
		this.setNb_threads(nb_threads);
		
		int nb_bbm = L_BbmG.get(0).getMap().getNumber_of_bombermans();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(750,nb_bbm*25*3+300); //nb_bbm*25+75
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(nb_bbm*3+4,1));
		
		
		
		back = new JButton("back to menu");
		this.add(this.back);
		
		int[][] id_du_gagnant = new int[100][3];
		int[] stat_finPartie = new int[100];
		int typeFin;
		for(int j = 0 ; j < L_BbmG.size(); j++){
			
			typeFin = L_BbmG.get(j).etatJeu.getFinPartie();
			
			switch(typeFin){
				case GameState.PLANTAGE :
					stat_finPartie[GameState.PLANTAGE] +=1;
					break;
					
				case GameState.WIN_SOLO :
					stat_finPartie[GameState.WIN_SOLO] +=1;
					id_du_gagnant[L_BbmG.get(j).etatJeu.getIdGagnant()][0] +=1;
					break;
					
				case GameState.WIN_SCORE :
					stat_finPartie[GameState.WIN_SCORE] +=1;
					id_du_gagnant[L_BbmG.get(j).etatJeu.getIdGagnant()][1] +=1;
					break;
					
				case GameState.WIN_SURVIE :
					stat_finPartie[GameState.WIN_SURVIE] +=1;
					id_du_gagnant[L_BbmG.get(j).etatJeu.getIdGagnant()][2] +=1;
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
			
			String pourcentage =df.format(((double)stat_finPartie[n]/nb_threads)*100);
			JLabel pan_result = new JLabel();
			
			if ( n == GameState.PLANTAGE ) {
				this.listlab.add(pan_result);
				pan_result.setText("Jeu a planté "+stat_finPartie[n] +" fois");
				pan_result.setHorizontalAlignment(JLabel.CENTER);
				this.add(pan_result);
			}
			
			else if (n == GameState.WIN_SOLO  || n == GameState.WIN_SOLO  || n == GameState.WIN_SOLO ){
				System.out.println(id_du_gagnant.length);

				double total;
				for(int i = 0 ; i < L_BbmG.get(0).etatJeu.getBombermans().size() ; i++){
					JLabel pan_result2 = new JLabel();
					total = id_du_gagnant[i][0] + id_du_gagnant[i][1] + id_du_gagnant[i][2];
					this.listlab.add(pan_result2);
					if (L_BbmG.get(0).etatJeu.getBombermans().get(i).getStrategie() == null) 
						pan_result2.setText("Joueur "+(i+1)+" a gagné "+ df.format((total/nb_threads)*100)+"% du temps avec strategie aléatoire, dont :");
					else pan_result2.setText("Joueur "+(i+1)+" a gagné "+ df.format((total/nb_threads)*100) +"% du temps avec strategie "+L_BbmG.get(0).etatJeu.getBombermans().get(n).getStrategie().getClass().getSimpleName()+", dont :");
					pan_result2.setHorizontalAlignment(JLabel.CENTER);
					this.add(pan_result2);
					
					if ( id_du_gagnant[i][0] != 0) {
						JLabel win_solo = new JLabel();
						this.listlab.add(win_solo);
						win_solo.setText( df.format((id_du_gagnant[i][0]/total)*100) +"% en solo");
						win_solo.setHorizontalAlignment(JLabel.CENTER);
						this.add(win_solo);
					}
					if ( id_du_gagnant[i][1] != 0) {
						JLabel win_score = new JLabel();
						this.listlab.add(win_score);
						win_score.setText(df.format((id_du_gagnant[i][1]/total)*100) +"% au score");
						win_score.setHorizontalAlignment(JLabel.CENTER);
						this.add(win_score);
					}
					if ( id_du_gagnant[i][2] != 0) {
						JLabel win_survie = new JLabel();
						this.listlab.add(win_survie);
						win_survie.setText(df.format((id_du_gagnant[i][2]/total)*100) +"% en restant le dernier en vie");
						win_survie.setHorizontalAlignment(JLabel.CENTER);
						this.add(win_survie);
					}
				}
			}
				
			else if ( n ==  GameState.EX_AEQUO ) {
				this.listlab.add(pan_result);
				pan_result.setText("La partie s'est finie sur un ex aequo "+ pourcentage +"% du temps");
				pan_result.setHorizontalAlignment(JLabel.CENTER);
				this.add(pan_result);
			}
				
			else if ( n ==  GameState.GAME_OVER) {
				this.listlab.add(pan_result);
				pan_result.setText("Il n'y a pas eu de gagnant "+ pourcentage +"% du temps");
				pan_result.setHorizontalAlignment(JLabel.CENTER);
				this.add(pan_result);
			}
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
