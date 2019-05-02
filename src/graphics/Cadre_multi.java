package graphics;

import game.BombermanGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
	
	
	
	Cadre_multi(ArrayList<BombermanGame> L_BbmG, int nb_threads){
		this.listlab = new ArrayList<JLabel>();
		this.setL_BbmG(L_BbmG);
		this.setNb_threads(nb_threads);
		
		int nb_bbm = L_BbmG.get(0).getMap().getNumber_of_bombermans();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(750,nb_bbm*25+75);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(nb_bbm+3,1));
		
		
		
		back = new JButton("back to menu");
		this.add(this.back);
		
		int[] id_du_gagnant = new int[100];
		for(int j = 0 ; j < L_BbmG.size(); j++){
			if(!L_BbmG.get(j).etatJeu.isPlantage())
				id_du_gagnant[L_BbmG.get(j).etatJeu.getIdGagnant()] +=1;
			else id_du_gagnant[0] += 1;
			
		}

		System.out.println("");
		for(int n = 0 ; n < id_du_gagnant.length; n++){
			double pourcentage =((double)id_du_gagnant[n]/nb_threads)*100;
			JLabel pan_result = new JLabel();
			if( n > 1  & n < nb_bbm+2){
				this.listlab.add(pan_result);
				if (L_BbmG.get(0).etatJeu.getBombermans().get(n-2).getStrategie() == null) 
					pan_result.setText("Joueur "+(n+1-2)+" a gagné "+ pourcentage +"% du temps avec strategie : aléatoire");
				else pan_result.setText("Joueur "+(n+1-2)+" a gagné "+ pourcentage +"% du temps avec strategie : "+L_BbmG.get(0).etatJeu.getBombermans().get(n-2).getStrategie().getClass().getSimpleName());
				pan_result.setHorizontalAlignment(JLabel.CENTER);
				this.add(pan_result);
			}
			else if( n == 1){
				this.listlab.add(pan_result);
				pan_result.setText("Il n'y a pas eu de gagnant "+ pourcentage +"% du temps");
				pan_result.setHorizontalAlignment(JLabel.CENTER);
				this.add(pan_result);
			}
			else if( n == 0){
				this.listlab.add(pan_result);
				pan_result.setText("Jeu a planté "+id_du_gagnant[n] +" fois");
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
				
				//dispose();
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
