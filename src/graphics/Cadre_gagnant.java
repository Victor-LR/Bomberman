package graphics;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import agents.Agent_Bomberman;
import game.BombermanGame;

public class Cadre_gagnant extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton back = null;
	private JLabel gagnant = null;
	private JFrame cadre_jeu = null;
	
	public Cadre_gagnant(String winner,int id, JFrame c_j) {
		
		cadre_jeu = c_j;
		
		this.setSize(200, 100);
		this.setLocationRelativeTo(null);
		this.setTitle("Fin du jeu");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(2,1));
		
		this.gagnant = new JLabel(winner);
		
			switch(id)
	        {
	            case 0 :
	            	gagnant.setForeground(Color.blue);
	                break;
	            case 1 :
	            	gagnant.setForeground(Color.red);
	                break;
	            case 2 :
	            	gagnant.setForeground(Color.green);
	                break;
	            case 3 :
	            	gagnant.setForeground(Color.yellow);
	                break;
	            case 4 :
	            	gagnant.setForeground(Color.white);
	                break;
	            case 5 :
	            	gagnant.setForeground(Color.black);
	                break;
	        }

			
		this.add(this.gagnant);
		
		this.back = new JButton("back to menu");
		this.add(this.back);
		
		creer_button();
		
	}
	
	public void creer_button(){
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				
				
				Cadre_menu fen = new Cadre_menu();//BbmG.getMap().getFilename());
				fen.setVisible(true);
				cadre_jeu.dispose();
				dispose();
			}
		});
	}
}
